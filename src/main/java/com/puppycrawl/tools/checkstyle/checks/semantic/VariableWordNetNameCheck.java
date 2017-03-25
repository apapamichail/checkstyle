package com.puppycrawl.tools.checkstyle.checks.semantic;
/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelro Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * VariableWordNetNameCheck is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * VariableWordNetNameCheck is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VariableWordNetNameCheck.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * Checks if variables are Nouns
 */

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;

/*import edu.smu.tspell.wordnet.Synset;
 import edu.smu.tspell.wordnet.SynsetType;
 import edu.smu.tspell.wordnet.WordNetDatabase;
 */
public class VariableWordNetNameCheck extends Check {
	WordNetDatabase database;
	/*
	 * for initializeWordnet left in comments in case of future use private
	 * WordNetDatabase database; private String wordnetPath;
	 */
	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.VARIABLE_DEF };

	}

	public void visitToken(DetailAST aAST) {
		DetailAST ast = aAST.findFirstToken(TokenTypes.IDENT);
		analyzeVariableName(ast, ast.getText(), aAST.getType());
	}

	
	 private void initializeWordnet() {
	 
	   database = WordNetDatabase.getFileInstance(); 
	   String wordnetPath =   "C:\\WordNet\\3.0\\dict"; 
	   System.setProperty("wordnet.database.dir",
			   wordnetPath);
	  
	  }
	 
	 private void analyzeVariableName(final DetailAST ast, String variableName,
                                      int type) {
		 initializeWordnet() ;
		// Concatenate the command-line arguments
		StringBuffer buffer = new StringBuffer();
		buffer.append(variableName);
		// String blacklist = "var"; a blacklist with strings wordnet can cope
		// with

		/*Set<String> manyWordsSynset, synsetWord = edu.cmu.lti.ws4j.WS4J
				.findTranslations(variableName, POS.n);
*/
		Synset[] manyWordsSynset, synsetWord = database.getSynsets(variableName,SynsetType.NOUN);
				 

		/*
		 * if( variableName.length() < 2){
		 * SemanticViolationsDatabase.increaseLocalVariableErrors();
		 * log(ast.getLineNo(), "The variable : " + buffer.toString() +
		 * " has only one character   ");
		 * 
		 * } else
		 */
		if (SemanticTools.areSetsOk(synsetWord) == false) {
			String firstWord = SemanticTools.findWords(variableName)[0];
		//	System.out.println("firstWord : "+firstWord);
			manyWordsSynset = database.getSynsets(firstWord,SynsetType.NOUN);
			//edu.cmu.lti.ws4j.WS4J.findTranslations(firstWord,POS.n);
		//	System.out.println("YO! "+manyWordsSynset);
			if ( SemanticTools.areSetsOk(manyWordsSynset) == false	|| firstWord.length() == 1) {
				log(ast.getLineNo(), "The variable : " + variableName
						+ " doesnt begin with Noun   ");

				SemanticViolationsDatabase.increaseLocalVariableErrors();

			}

		}
		SemanticViolationsDatabase.increaseObjectCounting("variable");

	} 

}
