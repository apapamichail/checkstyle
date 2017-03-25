package com.puppycrawl.tools.checkstyle.checks.semantic;


/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelro Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * JaccardSimilarity is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * JaccardSimilarity is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JaccardSimilarity.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * Return the Jaccard Similarity of two strings based provided by the spliting the strings
 * by Uppercase letters 
 */
public class JaccardSimilarityTest {
	StringBuffer plainWordsFirst;
	StringBuffer plainWordsSecond;

	public JaccardSimilarityTest() {

	}

	/**
	 * @param firstClass
	 * @param secondClass
	 * @return the result from the maximum of jaccard similarity between the
	 *         synsets and the plain words
	 */
	public double getJaccardResults(String firstClass, String secondClass) {
		plainWordsFirst = new StringBuffer();
		plainWordsSecond = new StringBuffer();
		double maxResult;

		plainWordsFirst =SemanticTools.findWordsBuffer(firstClass);
		plainWordsSecond =SemanticTools.findWordsBuffer(secondClass);

		double resultWords = JaccardSimilarity
				.jaccardSimilarity(plainWordsFirst.toString() , plainWordsSecond.toString());

		double resultWords2 = JaccardSimilarity
				.jaccardSimilarity(  plainWordsSecond.toString() , plainWordsFirst.toString() );
		if (resultWords2 > resultWords)
			maxResult = resultWords2;
		else
			maxResult = resultWords;
		return maxResult;

	}


}
