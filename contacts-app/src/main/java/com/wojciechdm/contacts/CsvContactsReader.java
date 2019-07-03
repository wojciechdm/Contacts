package com.wojciechdm.contacts;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Objects;

@Slf4j
class CsvContactsReader implements ContactsReader {

  private final File file;
  private BufferedReader bufferedReader;

  CsvContactsReader(File file) {
    this.file = file;
  }

  @Override
  public String[] readData() {

    String[] readedData = new String[10];

    try {
      if (Objects.isNull(bufferedReader)) {
        bufferedReader = new BufferedReader(new FileReader(file, Charset.forName("ISO-8859-2")));
      }

      for (int i = 0; i < 10; i++) {
        String line = bufferedReader.readLine();
        if (Objects.nonNull(line)) {
          readedData[i] = line;
        } else {
          break;
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
