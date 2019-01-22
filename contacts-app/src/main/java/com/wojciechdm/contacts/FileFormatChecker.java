package com.wojciechdm.contacts;

import java.io.*;
import java.nio.file.InvalidPathException;
import java.util.regex.Pattern;

public class FileFormatChecker {
	
	private static final Pattern FILE_FORMAT_XML=Pattern.compile("\\s*<\\?xml.+>.*");
	private static final Pattern FILE_FORMAT_CSV=
			Pattern.compile("(?<name>\\w+?),(?<surname>\\w+?),(?<age>\\d*?),(?<city>\\w+?)(?:,(.+))");
		
	public FileFormat getFileFormat(String path) {
		String line=getFirstLine(path);
		
		if (FILE_FORMAT_XML.matcher(line).matches()) {
			return FileFormat.XML;
		} else if (FILE_FORMAT_CSV.matcher(line).matches()) {
			return FileFormat.CSV;
		} else {
			throw new InvalidPathException(path, "Inavlid file format");
		}
	}
	
	private String getFirstLine(String path) {
		String line=null;
		
		try (BufferedReader fileReader=new BufferedReader(new FileReader(path))){
			line=fileReader.readLine();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	} 
}
