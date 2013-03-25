package com.timetabling.server.temp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ReadingFilesFromWEBINFTestin {
	
	private static Logger logger = Logger.getLogger( ReadingFilesFromWEBINFTestin.class.getSimpleName() );

	public static void doSomthing() {
		try {
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader("file.in"));
			String line;
			while (null != (line = reader.readLine()))
				logger.log(Level.WARNING, line);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
