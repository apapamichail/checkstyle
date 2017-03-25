package com.puppycrawl.tools.checkstyle.checks.semantic;
/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelro Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * SemanticViolationsDatabase is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * SemanticViolationsDatabase is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SemanticViolationsDatabase.  If not, see <http://www.gnu.org/licenses/>.
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * 
 * Keeps in memory all kind of errors, updates/initializes accordingly and 
 * can write them to files
 * 
 */



import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SemanticViolationsDatabase {
	static int fileBegunWriting = 0;
	// The below Data is stored by file
	static int localVariableErrors = 0;
	static int localVariableObjects = 0;
	static int parametersErrors = 0;
	static int parametersObjects = 0;
	static int methodErrors = 0;
	static int methodObjects = 0;
	static int classObjects = 0;
	static int classNameSimilarityErrors = 0;
	static int classNameSemanticErrorsJaccardSynset = 0;
	static int classNameSemanticErrorsJaccardWord = 0;
	static int classNameSemanticErrorsLIN = 0;
	static int classNameSemanticErrorsPATH = 0;
	static int totalErrorsInClass = 0;
	static int lineLengthErrors = 0;
	static int methodLengthErrors = 0;
	// SemantiError Flags
	static int jaccardSynset = 1;
	static int jaccardWord = 2;
	static int wordnetLIN = 3;
	static int wordnetPATH = 4;

	public static int FileLength = 0;
	static StringBuffer className = new StringBuffer();
	static StringBuffer classBuffer = new StringBuffer();
	// End of file per file storing
	// Data for whole project
	static int ProjectVariableErrors = 0;
	static int ProjectVariableObjects = 0;
	static int ProjectParametersErrors = 0;
	static int ProjectParametersObjects = 0;
	static int ProjectMethodErrors = 0;
	static int ProjectMethodObjects = 0;
	static int ProjectInterfaceErrors = 0;
	static int ProjectInterfaceObjects = 0;
	static int ProjectAbstractErrors = 0;
	static int ProjectAbstractObjects = 0;
	static int ClassNameNotNounErrors = 0;

	static int ProjectClassNameSemanticErrorsJaccardSynset = 0;
	static int ProjectClassNameSemanticErrorsJaccardWord = 0;
	static int ProjectClassNameSemanticErrorsLIN = 0;
	static int ProjectClassNameSemanticErrorsPATH = 0;
	static int ProjectClassNameSemanticNotNounErrors = 0;
	static int ProjectClassObjects = 0;
	static int ProjectClassNameSimilarityErrors = 0;
	static int ProjectSemanticViolations = 0;
	public static int ProjectCodeLength = 0;
	static int classCounters = 0;
	// Correlations
	static double violationsWithCurrentLength = 0; // if i ==1 for class 1
	// if i> 1 for j < i
	static int currentFileLength = 0;
	static double totalViolations = 0;
	static boolean main=true;
	
	public static void addNameOfClass(String name) {
	
		//if(classBuffer.toString().contains(name) == false){
			classBuffer.append(name + " ");
		//	System.out.println("classBuffer. : "+classBuffer.toString());
			if(main){
				className.append(name);
				main = false;
			}
		//}
		//System.out.println(classBuffer.toString() +"_______________--------------------___________"+className.toString());
	}

	public static String[] getClassNames() {
 		return classBuffer.toString().split(" ");
	}

	public static void increaseLocalVariableErrors() {
		localVariableErrors += 1;
	}

	public static void increaseLineLengthErrors() {
		lineLengthErrors += 1;
	}

	public static void increaseMethodLengthErrors() {
		methodLengthErrors += 1;
	}

	public static void increaseParameterErrors() {
		parametersErrors += 1;
	}

	public static void increaseMethodNameNotVerbErrors() {
		methodErrors += 1;
	}

	public static void increaseClassNameNotNounErrors() {
		ClassNameNotNounErrors += 1;
	}

	public static void increaseClassNameSemanticErrorJaccardSynset() {
		classNameSemanticErrorsJaccardSynset += 1;
	}

	public static void increaseClassNameSemanticErrorJaccardWords() {
		classNameSemanticErrorsJaccardWord += 1;
	}

	public static void increaseClassNameSemanticErrorLIN() {
		classNameSemanticErrorsLIN += 1;
	}

	public static void increaseClassNameSemanticErrorPATH() {
		classNameSemanticErrorsPATH += 1;
	}

	public static void increaseClassNameSimilarityErrors() {
		classNameSimilarityErrors += 1;
	}

	public static void increaseObjectCounting(String type) {

		if (type.equals("variable")) {
			localVariableObjects += 1;
		} else if (type.equals("parameter")) {
			parametersObjects += 1;
		} else if (type.equals("method")) {
			methodObjects += 1;
		} else if (type.equals("class")) {
			classObjects += 1;
		}

	}

	public static void appendErrorsToFile() throws Exception {
		PrintWriter writer;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					"violationsInformationPerClass.txt", true)));
			if (className.toString().length() > 1) {
				updateTotalSumsOfErrors();
				writer.println(className.toString() + "-" + localVariableErrors
						+ "-" + localVariableObjects + "-" + parametersErrors
						+ "-" + parametersObjects + "-" + methodErrors + "-"
						+ methodObjects + "-" +
						/*
						 * classNameSemanticErrorsJaccardSynset+"-"+
						 * classNameSemanticErrorsJaccardWord+"-" +
						 * classNameSemanticErrorsLIN+"-"+
						 * classNameSemanticErrorsPATH+"-"+
						 * classNameSimilarityErrors +"-" +
						 */
						ClassNameNotNounErrors + "-" + classObjects + "-"
						+ currentFileLength + "-" + FileLength + "-"
						+ violationsWithCurrentLength + "-"
						+ (double) totalErrorsInClass / FileLength + "-"
						+ methodLengthErrors + "-" + lineLengthErrors);

				StaticViolationsDatabase.appendToFileErrors();
				StaticViolationsDatabase.emptyData();
				emptyClassData();
 			}
			writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	public static void updateTotalSumsOfErrors() {
		classCounters += 1;
		currentFileLength += FileLength;
		totalViolations += localVariableErrors + parametersErrors
				+ methodErrors + classNameSemanticErrorsJaccardSynset
				+ classNameSemanticErrorsJaccardWord
				+ classNameSemanticErrorsLIN + classNameSemanticErrorsPATH
				+ classNameSimilarityErrors + ClassNameNotNounErrors;
		violationsWithCurrentLength = ((totalViolations) / (double) currentFileLength);
		totalErrorsInClass = localVariableErrors + parametersErrors
				+ methodErrors + /*
								 * classNameSemanticErrorsJaccardSynset+
								 * classNameSemanticErrorsJaccardWord
								 * +classNameSemanticErrorsLIN+
								 * classNameSemanticErrorsPATH
								 * +classNameSimilarityErrors
								 */+ClassNameNotNounErrors;
 		BigDecimal bd = new BigDecimal(violationsWithCurrentLength).setScale(3,
				RoundingMode.HALF_EVEN);
		violationsWithCurrentLength = bd.doubleValue();
	}

	public static void setFileLength(int length) {
		FileLength = length;
	}

	public static void emptyClassData() {
		main = true;
		updateProjectData();
		localVariableErrors = 0;
		localVariableObjects = 0;
		parametersErrors = 0;
		parametersObjects = 0;
		methodErrors = 0;
		methodObjects = 0;
		classNameSemanticErrorsJaccardSynset = 0;
		classNameSemanticErrorsJaccardWord = 0;
		classNameSemanticErrorsLIN = 0;
		classNameSemanticErrorsPATH = 0;
		ClassNameNotNounErrors = 0;
		classObjects = 0;
		classNameSimilarityErrors = 0;
		className.delete(0, className.length());
		methodLengthErrors = 0;
		lineLengthErrors = 0;

	}

	public static void updateProjectData() {

		ProjectVariableErrors += localVariableErrors;
		ProjectVariableObjects += localVariableObjects;
		ProjectParametersErrors += parametersErrors;
		ProjectParametersObjects += parametersObjects;
		ProjectMethodErrors += methodErrors;
		ProjectMethodObjects += methodObjects;

		ProjectClassNameSemanticErrorsJaccardSynset += classNameSemanticErrorsJaccardSynset;
		ProjectClassNameSemanticErrorsJaccardWord += classNameSemanticErrorsJaccardWord;
		ProjectClassNameSemanticErrorsLIN += classNameSemanticErrorsLIN;
		ProjectClassNameSemanticErrorsPATH += classNameSemanticErrorsPATH;
		ProjectClassNameSemanticNotNounErrors += ClassNameNotNounErrors;
		ProjectClassObjects += classObjects;
		ProjectClassNameSimilarityErrors += classNameSimilarityErrors;
		ProjectSemanticViolations += classNameSemanticErrorsJaccardSynset
				+ classNameSemanticErrorsJaccardWord
				+ classNameSemanticErrorsLIN + classNameSemanticErrorsPATH
				+ ClassNameNotNounErrors;
		ProjectCodeLength += FileLength;

	}

	public static void writeToFileProjectData() {

		PrintWriter writer;
		try {
			writer = new PrintWriter(new BufferedWriter(new FileWriter(
					"violationsInformationProject.txt", true)));
			writer.println("Local variable errors - Local variable objects  - Parameter errors -Parameter objects - Method name errors - Method name objects - "
					+ "jaccard synset semantic - class name jaccard word - LIN semantic errors - PATH semantic errors - class name similarity errors - Class not noun errors "
					+ "- Total Semantic Errors -Total Errors - Total sum of lines - Total Classes \n");

			writer.println(ProjectVariableErrors + "-" + ProjectVariableObjects
					+ "-" + ProjectParametersErrors + "-"
					+ ProjectParametersObjects + "-" + ProjectMethodErrors
					+ "-" + ProjectMethodObjects + "-"
					+ ProjectClassNameSemanticErrorsJaccardSynset + "-"
					+ ProjectClassNameSemanticErrorsJaccardWord + "-"
					+ ProjectClassNameSemanticErrorsLIN + "-"
					+ ProjectClassNameSemanticErrorsPATH + "-"
					+ ProjectClassNameSimilarityErrors + "-"
					+ ProjectClassNameSemanticNotNounErrors + "-"
					+ ProjectSemanticViolations + "-"
					+ ProjectClassNameSemanticNotNounErrors
					+ ProjectClassObjects + totalViolations + "-"
					+ ProjectCodeLength + "-" + ProjectClassObjects);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
