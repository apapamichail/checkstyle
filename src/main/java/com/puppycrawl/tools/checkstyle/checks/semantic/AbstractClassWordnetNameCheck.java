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
 * Checks if an Abstract Class begins with Noun
 */

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;

/**
 * 
 * @author Papamichail Aggelos *** Addition check if Abstract class begins with
 *         Noun
 */
public final class AbstractClassWordnetNameCheck extends Check {
 


	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF };
	}

	@Override
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}

	@Override
	public void visitToken(final DetailAST aAST) {
		if ( TokenTypes.CLASS_DEF == aAST.getType()) {
			visitClassDef(aAST);

		}
	}
 	private void visitClassDef(final DetailAST aAST) {

		String className = aAST.findFirstToken(TokenTypes.IDENT).getText();
		if (isAbstract(aAST)) {
			analyzeClassName(aAST.findFirstToken(TokenTypes.IDENT), className);
			// if class has abstract modifier
			
		} 
	}

	/**
	 * @param aAST
	 *            class definition for check.
	 * @return true if a given class declared as abstract.
	 */
	private boolean isAbstract(final DetailAST aAST) {
		final DetailAST abstractAST = aAST.findFirstToken(TokenTypes.MODIFIERS)
				.findFirstToken(TokenTypes.ABSTRACT);

		return abstractAST != null;
	}


	private void analyzeClassName(final DetailAST ast, String className) {
		SemanticTools.initializeWordnet();
		String temp = className;// first word of classname

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
				log(ast.getLineNo(), "The abstract class : " + temp
						+ " doesnt begin with Noun ");
				SemanticViolationsDatabase.increaseClassNameNotNounErrors();

			}
		}
	}

}
