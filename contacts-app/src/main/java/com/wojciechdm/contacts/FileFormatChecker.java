package com.wojciechdm.contacts;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.util.regex.Pattern;

@Slf4j
class FileFormatChecker {

  private static final Pattern FILE_FORMAT_XML = Pattern.compile("\\s*<\\?xml.+>.*");
  private static final Pattern FILE_FORMAT_CSV =
      Pattern.compile("(?<name>\\w+?),(?<surname>\\w+?),(?<age>\\d*?),(?<city>\\w+?)(?:,(.+))");

  FileFormat recognizeFileFormat(File file) {
    String line = readFirstLine(file);

    if (FILE_FORMAT_XML.matcher(line).matches()) {
      return FileFormat.XML;
    } else if (FILE_FORMAT_CSV.matcher(line).matches()) {
      return FileFormat.CSV;
    } else {
      throw new InvalidPathException(file.getAbsolutePath(), "Invalid file format");
    }
  }

  private String readFirstLine(File file) {

    try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {

      return fileReader.readLine();

    } catch (IOException exception) {
      log.error("Problem with read file while recognizing format", exception);
    }
    return "";
  }
}
