package com.wojciechdm.contacts;

import java.io.IOException;

interface ContactsReader extends AutoCloseable {

  String[] readData();

  @Override
  void close() throws IOException;
}
