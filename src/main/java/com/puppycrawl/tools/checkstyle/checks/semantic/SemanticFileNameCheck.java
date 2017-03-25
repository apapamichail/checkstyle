package com.puppycrawl.tools.checkstyle.checks.semantic;

import java.io.File;
import java.util.LinkedList;

public class SemanticFileNameCheck {
	static public boolean theCheckIsDone;
//	private String myfileName = com.puppycrawl.tools.checkstyle.TreeWalker.fileName;
	LinkedList<String> classNames = new LinkedList<String>(),
			mySynsetList = new LinkedList<String>(),
			mySynonymList = new LinkedList<String>();

	JaccardSimilarityTest jaccardRelation = new JaccardSimilarityTest();
	WordnetTreeRelationTest wordnetRelation = new WordnetTreeRelationTest();
	private final double relationThreshold = 0.4;
	public LinkedList<String> log ;

	public SemanticFileNameCheck(String mainClassName){
	 
	}
	
	public String[]  listFilesForFolder(final File folder) {
		print("the actual path is : "+ folder.getAbsolutePath());
		StringBuffer myfiles = new StringBuffer();
		String[] fileEntry  = folder.list();
	    for (int i=0; i < fileEntry.length; i++) {
	        	if(fileEntry[i].contains(".java")){
	        		myfiles.append(fileEntry[i].split(".java")[0] + " ");
	        //		print(fileEntry[i].split(".java")[0]);
	        	}
	    } 
	    return  myfiles.toString().split(" ");
	}

	private String getPath(String filename){
		String[] buffer = filename.split("\\\\");
		StringBuffer sbuffer = new StringBuffer();
		for(int i=0; i < buffer.length-1; i++){
			sbuffer.append(buffer[i]+"\\");
		}
		return sbuffer.toString();
	}
 
	public void addClassNames(String line) {
		if (line.contains(".java")) {
		//	if (line.contains(myfileName) == false) {
		//		classNames.add(line.split("java")[0]);
		//	}
		}
	}

	private void print(String s) {
		System.out.println(s);

	}
	
	public LinkedList<String> getResult() {
		return log;

	}
}
