package com.wojciechdm.contacts;

import java.io.*;

public class CsvContactsReader implements ContactsReader {
	
	private BufferedReader bufferedReader;
	private String[] readedData;
	
	public CsvContactsReader(String path) throws FileNotFoundException {
		this.bufferedReader=new BufferedReader(new FileReader(path));		
	}
	
	@Override
	public boolean hasMoreData() throws IOException{
		readedData=new String[10];
		String line=null;
		
		for (int personsCounter=0; personsCounter<10; personsCounter++) {
			line=bufferedReader.readLine();
			if(line!=null) {
				readedData[personsCounter]=line;
			} else {
				break;
			}
		}
		return readedData[0]!=null;
	}
	
	@Override
	public String[] readData(){
		return readedData;
	}
	
	@Override
	public void close() throws IOException {		
		bufferedReader.close();		
	}
}
