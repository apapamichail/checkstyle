package com.puppycrawl.tools.checkstyle.checks.semantic;
/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelor Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * ClassWordnetNameCheck is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * ClassWordnetNameCheck is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ClassWordnetNameCheck.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * Checks if a Class begins with Noun
 */

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;



public class ClassWordnetNameCheck extends Check {

	/* for initializeWordnet */
	static DetailAST ast;
	WordNetDatabase database;
	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF };

	}

	public void visitToken(DetailAST aAST) {
		String name = aAST.findFirstToken(TokenTypes.IDENT).getText();
		analyzeClassName(aAST, name);
		SemanticViolationsDatabase.addNameOfClass(name);
		if (!SemanticViolationsDatabase.classBuffer.toString().contains(name)) {
			SemanticViolationsDatabase.addNameOfClass(aAST.findFirstToken(
					TokenTypes.IDENT).getText());
		}

	}

	private void analyzeClassName(final DetailAST ast, String className) {
		initializeWordnet();
		String blacklist = "var";
		String temp = className;// first word of classname
	/*	Set<String> manyWordsSynset, synsetWord = edu.cmu.lti.ws4j.WS4J
				.findTranslations(className, POS.n);// database.getSynsets(methodName,SynsetType.VERB),
													// manyWordsSynset;

		// Concatenate the command-line arguments
		StringBuffer buffer = new StringBuffer();
		buffer.append(className);
		if (SemanticTools.areSetsOk(synsetWord) == false) {
			String firstWord = SemanticTools.findWords(className)[0];
			manyWordsSynset = edu.cmu.lti.ws4j.WS4J.findHasInstances(firstWord,
					POS.n);
		//	System.out.println("firstWord is "+firstWord);
			if ( SemanticTools.areSetsOk(manyWordsSynset) == false) {
				log(ast.getLineNo(), "The class : " + temp
						+ " doesnt begin with Noun ");
				SemanticViolationsDatabase.increaseClassNameNotNounErrors();

			}

		}*/
		Synset[] manyWordsSynset, synsetWord = database.getSynsets(className, SynsetType.NOUN);// database.getSynsets(methodName,SynsetType.VERB),
													// manyWordsSynset;

		// Concatenate the command-line arguments
		StringBuffer buffer = new StringBuffer();
		buffer.append(className);
		if (SemanticTools.areSetsOk(synsetWord) == false) {
			String firstWord = SemanticTools.findWords(className)[0];
			manyWordsSynset = database.getSynsets(firstWord,
					SynsetType.NOUN);
		//	System.out.println("firstWord is "+firstWord);
			if ( SemanticTools.areSetsOk(manyWordsSynset) == false) {
				log(ast.getLineNo(), "The class : " + temp
						+ " doesnt begin with Noun ");
				SemanticViolationsDatabase.increaseClassNameNotNounErrors();

			}
			
		}
		SemanticViolationsDatabase.increaseObjectCounting("class");

	}
	 private void initializeWordnet() {
		 
		   database = WordNetDatabase.getFileInstance(); 
		   String wordnetPath =   "C:\\WordNet\\3.0\\dict"; 
		   System.setProperty("wordnet.database.dir",
				   wordnetPath);
		  
		  }

}
