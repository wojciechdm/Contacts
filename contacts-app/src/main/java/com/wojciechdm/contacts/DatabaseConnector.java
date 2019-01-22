package com.wojciechdm.contacts;

import java.sql.*;
import java.util.*;

public class DatabaseConnector {
	
	private static final String URI="localhost:3306/contacts";
	private static final String USER="user";
	private static final String PASSWORD="password";
	private static final String URL="jdbc:mysql://"+URI
			+ "?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&"
			+ "useLegacyDatetimeCode=false&serverTimezone=Europe/Warsaw&useSSL=false&"
			+ "allowPublicKeyRetrieval=true";	
	
	public void saveToDatabase(List<String> sqlStatements) {
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
		{   				
			Statement statement = connection.createStatement();
			for (String sqlStatement: sqlStatements) {
				statement.addBatch(sqlStatement);
			}									
			statement.executeBatch();			
        } catch (SQLException e) {
			e.printStackTrace();
		} 
	}	
	
	public Long getSingleLongValueFromDatabase(String sqlStatement) {
		Long longValue=null;
		ResultSet result;
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD))
		{   
			Statement statement = connection.createStatement();
			result=statement.executeQuery(sqlStatement);			
			result.next();
			longValue=result.getLong(1);			
        } catch (SQLException e) {
            e.printStackTrace();
        }		
		return longValue;
	}
}
