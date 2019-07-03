package com.wojciechdm.contacts;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

@Slf4j
class XmlContactsReader implements ContactsReader {

  private final File file;
  private BufferedReader bufferedReader;
  private StringBuilder tempReadedData = new StringBuilder();

  XmlContactsReader(File file) {
    this.file = file;
  }

  @Override
  public String[] readData() {

        String[] readedData = new String[1];

    try {
      if (Objects.isNull(bufferedReader)) {
        bufferedReader = new BufferedReader(new FileReader(file, Charset.forName("ISO-8859-2")));
      }

      tenPersonsDataLoop:
      for (int personsCounter = 0; personsCounter < 10; personsCounter++) {

        boolean containsPersonEndTag;

        do {
          String line = bufferedReader.readLine();

          if (Objects.nonNull(line)) {
            tempReadedData.append(line.trim());
            containsPersonEndTag = line.toUpperCase().contains("</PERSON>");
          } else {
            break tenPersonsDataLoop;
          }

        } while (!containsPersonEndTag);
      }

      if (tempReadedData.length() > 0) {
        readedData[0] = tempReadedData.toString();
        String dataUpperCase = readedData[0].toUpperCase();

        if (dataUpperCase.contains("<PERSON>") && dataUpperCase.contains("</PERSON>")) {
          int indexOfLastPersonEndTag = dataUpperCase.lastIndexOf("</PERSON>") + "</PERSON>".length();
          int indexOfFirstPersonStartTag = dataUpperCase.indexOf("<PERSON>");
          readedData[0] =
              readedData[0].substring(indexOfFirstPersonStartTag, indexOfLastPersonEndTag);
          tempReadedData.delete(0, indexOfLastPersonEndTag);
          readedData[0] = "<PERSONS>" + readedData[0] + "</PERSONS>";
        } else {
          readedData[0] = null;
        }

      }
    } catch (IOException exception) {
      log.error("Problem with file while reading data", exception);
    }

    return readedData;
  }

  @Override
  public void close() throws IOException {
    bufferedReader.close();
  }
}
