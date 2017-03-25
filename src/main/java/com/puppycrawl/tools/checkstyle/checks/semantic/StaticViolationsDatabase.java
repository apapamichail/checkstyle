package com.puppycrawl.tools.checkstyle.checks.semantic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public   class StaticViolationsDatabase {

	static int variableDefError =0;
	static int parameterDefError = 0;
	static int methodDefError = 0;
	static int parameterNumberViolation =0;
	static int widthCodeViolation =0;
	static int filelengthViolation = 0;
	static int methodLengthErrors = 0;
	public static void increaseVariableDefError() {
		variableDefError += 1;
	}
	public static void increaseParameterDefErrors() {
		parameterDefError += 1;
	}
	public static void increaseMethodDefErrors() {
		methodDefError += 1;
	}
	public static void increaseParameterNumberViolations() {
		parameterNumberViolation += 1;
	}
	public static void lineLengthErrors() {
		widthCodeViolation += 1;
	}
	public static void increaseFilelengthErrors() {
		filelengthViolation += 1;
	}
	
	public static void increaseMethodLengthErrors() {
		methodLengthErrors += 1;
	}

 
	public static void emptyData(){
				
		parameterNumberViolation =0;
		widthCodeViolation =0;
		filelengthViolation =0;
		methodLengthErrors = 0;
		
	}
	
	public static void appendToFileErrors() throws Exception {
		PrintWriter writer;
		 
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					"staticViolations.txt", true)));
			writer.println(parameterNumberViolation +" - "+methodLengthErrors+
					" - "+widthCodeViolation+" - " +filelengthViolation);
			writer.close();
		
	}
		
}
