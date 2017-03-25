package com.puppycrawl.tools.checkstyle.checks.semantic;
/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelro Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * MethodWordNetNameCheck is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * MethodWordNetNameCheck is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MethodWordNetNameCheck.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * Checks if methods begin with verb
 */

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public class MethodWordNetNameCheck extends Check {
	
	private WordNetDatabase database;





	/*for initializeWordnet*/
	@Override
	public int[] getDefaultTokens() {
		
		return new int[]{TokenTypes.METHOD_DEF};

	}

    public void visitToken(DetailAST aAST) {
    	
    	DetailAST ast = aAST.findFirstToken(TokenTypes.IDENT);
    	analyzeMethodName(ast,ast.getText());
 
    	
    }

    private void initializeWordnet() {
   	 
 	   database = WordNetDatabase.getFileInstance(); 
 	   String wordnetPath =   "C:\\WordNet\\3.0\\dict"; 
 	   System.setProperty("wordnet.database.dir",
 			   wordnetPath);
 	  
 	  }
    
     private void analyzeMethodName(final DetailAST ast, String methodName) {
    	 initializeWordnet() ;
  			//  Concatenate the command-line arguments
    		/*	String blacklist = "var";
  			Set<String> synsetWord = edu.cmu.lti.ws4j.WS4J.findTranslations(methodName, POS.v);//database.getSynsets(methodName,SynsetType.VERB), manyWordsSynset;		 
 
  			if(SemanticTools.areSetsOk(synsetWord) == false || synsetWord.toString().contains(blacklist) ){
  				String firstWord = SemanticTools.findWords(methodName)[0];
  				Set<String>	manyWordsSynset =edu.cmu.lti.ws4j.WS4J.findTranslations(firstWord, POS.v);
  			//	System.out.println("firstWord : "+firstWord);
  	  		

  				if( SemanticTools.areSetsOk(manyWordsSynset) == false ||
  						manyWordsSynset.toString().contains(blacklist)) {
  	 				log(ast.getLineNo(),"The method : "+methodName +" doesnt begin with Verb");
  	 				SemanticViolationsDatabase.increaseMethodNameNotVerbErrors();
  	 			}
  			}*/
    		//  Concatenate the command-line arguments
 
		
  			
		Synset[] manyWordsSynset, synsetWord = database.getSynsets(methodName,SynsetType.VERB);
		 

		
		if (SemanticTools.areSetsOk(synsetWord) == false) {
			String firstWord = SemanticTools.findWords(methodName)[0];
		//	System.out.println("firstWord : "+firstWord);
			manyWordsSynset = database.getSynsets(firstWord,SynsetType.VERB);
			//edu.cmu.lti.ws4j.WS4J.findTranslations(firstWord,POS.n);
		//	System.out.println("YO! "+manyWordsSynset);
			if ( SemanticTools.areSetsOk(manyWordsSynset) == false	|| firstWord.length() == 1) {
				log(ast.getLineNo(),"The method : "+methodName +" doesnt begin with Verb");
				SemanticViolationsDatabase.increaseMethodNameNotVerbErrors();

			}

		}
		SemanticViolationsDatabase.increaseObjectCounting("method");
	
  		
     }
     
      
    
     
   
     public String getFirstWord(final String name,final DetailAST ast) {
     	
    	String returnFirstWord = "";
     	char temp[];
     	temp = name.toCharArray();
     		for(int i = 1; i < name.length(); i++) {
     			if(temp[i] < 'a'  ){//then we have UpperCase letter   			 
      					returnFirstWord = name.toString().substring(0, i);
      					break;
      				}
     			}
     		return returnFirstWord;
     		
     }
     
}
	
