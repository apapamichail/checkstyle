package com.puppycrawl.tools.checkstyle.checks.semantic;
/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelro Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * SemanticTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * SemanticTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SemanticTools.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * 
 * This class provides a set of tools for Semantic analysis like the "get the first word from a string"
 * similarity beetween the names of two classes
 */

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.ws4j.WS4J;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class SemanticTools {
	public static WordNetDatabase database;
	
    public static void initializeWordnet() {
      	 
  	   database = WordNetDatabase.getFileInstance(); 
  	   String wordnetPath =   "C:\\WordNet\\3.0\\dict"; 
  	   System.setProperty("wordnet.database.dir",
  			   wordnetPath);
  	  
  	  }

	/**
	 * @param String
	 *            mainWords[]
	 * @return a string with all the synsets for every word in the array
	 *         mainWords[]
	 */
	public static String concatWords(String[] mainWords) {

		StringBuffer buffer = new StringBuffer();
		buffer.append(" ");
		for (String mainWord : mainWords) {
			Object words[] = WS4J.findSynonyms(mainWord, POS.n).toArray();

			if (words.length > 0) {

				for (int j = 0; j < words.length; j++) {
					buffer.append(words[j] + " ");
				}
			} else
				return "null";
		}
		return buffer.toString();

	}

	/**
	 * @param name
	 * @return all words separated with UpperCase in a class name
	 */
	public static String[] findWords(String name) {

		char buffer[] = name.toCharArray();
		StringBuffer b = new StringBuffer();
		String a[] = new String[2] ;
		a[0] = name;
		int i=1;
		boolean foundWords=false;
		int beginIndex = 0;
 		while(i < buffer.length){
			if(buffer[i] < 'a'){
				foundWords = true;
				b.append(name.substring(beginIndex, i)+" ");
				beginIndex = i;
		 //		System.out.println("b : "+b.toString());

			}
			i++;
		}
 		if(foundWords){
 			b.append(name.subSequence(beginIndex, buffer.length));
 			return b.toString().split(" ");

 		}
 		else return a;

	}
	
	public static StringBuffer findWordsBuffer(String name) {
		String array[] = findWords(name);
		StringBuffer className = new StringBuffer();

		for(int i=0; i < array.length; i++){
			className.append(array[i]+" ");
			
		}
		return className;

	}

	public static String  findString(String name) {

		char buffer[] = name.toCharArray();
		StringBuffer b = new StringBuffer();
		int i=0;
		int beginIndex = 0;
 		while(i < buffer.length){
			if(buffer[i] < 'a'){
				
				beginIndex = i;
				int j  = i + 1;
				while(j <buffer.length  ){
					 if( j == buffer.length -1){
							b.append(name.subSequence(beginIndex, j+1)+" ");
							break;
					 }
					 else if(buffer[j] < 'a' ){
						 if(name.subSequence(beginIndex, j).length() >2){
								b.append(name.subSequence(beginIndex, j)+" ");
								break;
						 }
						 
					}
					j++;
				}
			
			}
			i++;
		}
 	 	return b.toString();

	}
	
	 public static String getFirstWord(final StringBuffer name,final DetailAST ast) {
	    	
	    	String returnFirstWord = "";
	    	char temp[];
	    	 
	    		temp = name.toString().toCharArray();
	    		for(int i = 1; i < name.length(); i++) {
	    	     	if(temp[i] < 'a'  ){//then we have UpperCase letter   			 
	     					returnFirstWord = name.toString().substring(0, i);
	     					break;
	     				}
	     				 
	    			}
	    		
			return returnFirstWord;
	    		
	    }
	 
	 public static boolean areSetsOk(Synset[] set) {
		 if(set == null)
			 return false;
		 
	     if(set.length == 0 )
	     		return false;
	     else return true;
	     	
	     }

	/* Set<String> set) {
	   	 
	     	if(set == null || set.size() == 0 )
	     		return false;
	     	else return true;*/
}
