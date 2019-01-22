package com.wojciechdm.contacts;

import java.io.*;

public class XmlContactsReader implements ContactsReader {
	
	private BufferedReader bufferedReader;
	private StringBuilder tempReadedData;
	private String[] readedData;
		
	public XmlContactsReader(String path) throws FileNotFoundException{
		this.tempReadedData=new StringBuilder();		
		this.bufferedReader=new BufferedReader(new FileReader(path));		
	}
	
	@Override
	public boolean hasMoreData() throws IOException {
		String line=null;		
		String dataUpperCase;
		boolean containsPersonEndTag=false;		
		int indexOfLastPersonEndTag;
		int indexOfFirstPersonStartTag;
		readedData=new String[1];
		
		tenPersonsDataLoop:
		for (int personsCounter=0; personsCounter<10; personsCounter++) {
			do {
				line=bufferedReader.readLine();
				if(line!=null) {
					tempReadedData.append(line);
					containsPersonEndTag=line.toUpperCase().contains("</PERSON>");					
				} else {
					break tenPersonsDataLoop;
				}
			} while (!containsPersonEndTag);
		}					
		if (tempReadedData.length()>0) {
			readedData[0]=tempReadedData.toString();
			dataUpperCase=readedData[0].toUpperCase();
			if (dataUpperCase.contains("<PERSON>") & dataUpperCase.contains("</PERSON>")) {
				indexOfLastPersonEndTag=dataUpperCase.lastIndexOf("</PERSON>")+"</PERSON>".length();
				indexOfFirstPersonStartTag=dataUpperCase.indexOf("<PERSON>");
				readedData[0]=readedData[0].substring(indexOfFirstPersonStartTag,indexOfLastPersonEndTag);
				tempReadedData.delete(0, indexOfLastPersonEndTag);				
				readedData[0]="<PERSONS>"+readedData[0]+"</PERSONS>";
			} else {
				readedData[0]=null;
			}
		}					
		return readedData[0]!=null;
	}

	@Override
	public String[] readData() {
		return readedData;
	}
	
	@Override
	public void close() throws IOException {		
		bufferedReader.close();		
	}

}
