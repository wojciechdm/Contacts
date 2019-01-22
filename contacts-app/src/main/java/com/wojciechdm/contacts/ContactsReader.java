package com.wojciechdm.contacts;

import java.io.IOException;

public interface ContactsReader extends AutoCloseable{

	boolean hasMoreData() throws IOException;
	String[] readData();
	void close() throws IOException;
}
