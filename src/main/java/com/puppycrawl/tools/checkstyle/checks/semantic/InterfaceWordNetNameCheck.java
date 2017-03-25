package com.puppycrawl.tools.checkstyle.checks.semantic;
/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelro Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * InterfaceWordNetNameCheck is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * InterfaceWordNetNameCheck is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with InterfaceWordNetNameCheck.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * Checks if Intefaces begin with Noun
 */

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

public  class InterfaceWordNetNameCheck extends Check {
    
	private WordNetDatabase database;


	@Override
	public int[] getDefaultTokens() {
		return new int[]{TokenTypes.INTERFACE_DEF};

	}
	
    public void visitToken(DetailAST aAST) {
    	DetailAST ast = aAST.findFirstToken(TokenTypes.IDENT);
    	if ( !SemanticViolationsDatabase.classBuffer.toString().contains(ast.getText())) {
    		SemanticViolationsDatabase.addNameOfClass(ast.getText());
    	}
    	analyzeInterfaceName(ast,ast.getText());
     	
    }
    
    
    private void analyzeInterfaceName(final DetailAST ast, String className) {
		String temp = className;//first word of classname
		SemanticTools.initializeWordnet();
		/*	String blacklist = "var";
			String temp = className;//first word of classname
  			Set<String> manyWordsSynset,
  			synsetWord = edu.cmu.lti.ws4j.WS4J.findTranslations(className, POS.n);//database.getSynsets(methodName,SynsetType.VERB), manyWordsSynset;		 

			//  Concatenate the command-line arguments
			StringBuffer buffer = new StringBuffer();
			buffer.append(className);*/
 			/*if(SemanticTools.areSetsOk(synsetWord) == false || synsetWord.toString().contains(blacklist)){
 				String firstWord = SemanticTools.findWords(className)[0];
 				manyWordsSynset =  edu.cmu.lti.ws4j.WS4J.findTranslations(firstWord, POS.n);
 				if (className.toLowerCase().contains(blacklist) || 
 						SemanticTools.areSetsOk(manyWordsSynset) == false ){
 	 				log(ast.getLineNo(),"The class : "+temp +" doesnt begin with Noun ");
 	 				SemanticViolationsDatabase.increaseClassNameNotNounErrors();

 				}
 			
 			}	*/
			Synset[] manyWordsSynset, synsetWord = SemanticTools.database.getSynsets(className,
					SynsetType.NOUN);// database.getSynsets(methodName,SynsetType.VERB),
			// manyWordsSynset;

			// Concatenate the command-line arguments
			StringBuffer buffer = new StringBuffer();
			buffer.append(className);
			if (SemanticTools.areSetsOk(synsetWord) == false) {
				String firstWord = SemanticTools.findWords(className)[0];
				manyWordsSynset = SemanticTools.database.getSynsets(firstWord, SynsetType.NOUN);
				// System.out.println("firstWord is "+firstWord);
				if (SemanticTools.areSetsOk(manyWordsSynset) == false) {
					log(ast.getLineNo(), "The interface : " + temp
							+ " doesnt begin with Noun ");
					SemanticViolationsDatabase.increaseClassNameNotNounErrors();

				}
			}
			SemanticViolationsDatabase.increaseObjectCounting("class");

		}
    }
    
 
        
    
	


