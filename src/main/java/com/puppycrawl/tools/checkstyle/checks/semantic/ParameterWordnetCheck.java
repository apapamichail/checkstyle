package com.puppycrawl.tools.checkstyle.checks.semantic;

/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelro Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * ParameterWordnetCheck is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * ParameterWordnetCheck is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ParameterWordnetCheck.  If not, see <http://www.gnu.org/licenses/>.
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * 
 * Checks if parameters are of type Noun
 */

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;

public class ParameterWordnetCheck extends Check {

 	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.PARAMETER_DEF };

	}

	public void visitToken(DetailAST aAST) {
		DetailAST ast = aAST.findFirstToken(TokenTypes.IDENT);
		analyzeparameterName(ast, ast.getText(), aAST.getType());
	}

	

	private void analyzeparameterName(final DetailAST ast,
			String parameterName, int type) {
		SemanticTools.initializeWordnet();
		// Concatenate the command-line arguments
		StringBuffer buffer = new StringBuffer();
		buffer.append(parameterName);
		//String blacklist = "var";
 
		
		Synset[] manyWordsSynset, synsetWord = SemanticTools.database.getSynsets(parameterName,SynsetType.NOUN);

 
 		 
		if (SemanticTools.areSetsOk(synsetWord) == false) {
			String firstWord = SemanticTools.findWords(parameterName)[0];
			manyWordsSynset = SemanticTools.database.getSynsets(firstWord, SynsetType.NOUN);  
 			if ( SemanticTools.areSetsOk(manyWordsSynset) == false) {
				log(ast.getLineNo(), "The parameter : " + parameterName
						+ " doesnt begin with Noun   ");
 
				SemanticViolationsDatabase.increaseParameterErrors();
				
		}
		
		 
			 
		 


		}
		SemanticViolationsDatabase.increaseObjectCounting("parameter");

	}

  

}
