package com.puppycrawl.tools.checkstyle.checks.semantic;

/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelro Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * WordnetTreeRelationTest is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * WordnetTreeRelationTest is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WordnetTreeRelationTest.  If not, see <http://www.gnu.org/licenses/>.
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * 
 * This class uses the wordnet's algorithms Lin and Path to calculate the
 * similarity beetween the names of two classes
 */


public class WordnetTreeRelationTest {

	private String plainWordsFirstClass[];
	private String plainWordsSecondClass[];

	public WordnetTreeRelationTest() {

	}

	public double[] getTreeRelationResults(String firstClass, String secondClass) {

		plainWordsFirstClass = SemanticTools.findWords(firstClass);
		plainWordsSecondClass = SemanticTools.findWords(secondClass);
		double results[] = algorithmsResults(plainWordsFirstClass,
				plainWordsSecondClass);
		if (plainWordsFirstClass.length == 0
				|| plainWordsSecondClass.length == 0) {
			results[0] = 0;
			results[1] = 0;
		}

		return results;

	}

	/**
	 * @param s1
	 * @param secondClassWords
	 * @return the output of the Lin and Path algorithms result is between 0 and
	 *         1
	 */
	private double[] algorithmsResults(String firstClassWords[],
			String secondClassWords[]) {

		int rows = firstClassWords.length, collumns = secondClassWords.length;
		double linResults[][] = new double[rows][collumns];
		double pathResults[][] = new double[rows][collumns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < collumns; j++) {
				linResults[i][j] = fixInfinityProblem(edu.cmu.lti.ws4j.WS4J
						.runLIN(firstClassWords[i], secondClassWords[j]));
				pathResults[i][j] = fixInfinityProblem(edu.cmu.lti.ws4j.WS4J
						.runPATH(firstClassWords[i], secondClassWords[j]));
			}
		}
		double results[] = new double[2];
		results[0] = analyzeResults(linResults, rows, collumns);
		results[1] = analyzeResults(pathResults, rows, collumns);
		return results;

	}

	/**
	 * @param result
	 * @return if two words are the same Lin and Path return
	 *         7976931348623157E308 not 1 this here
	 */
	private double fixInfinityProblem(double result) {

		double d = 1.7976931348623157E308;
		if (result == d)
			return 1.0;
		else
			return result;

	}

	private double analyzeResults(double results[][], int rows, int collumns) {
		double result = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < collumns; j++) {
				result += results[i][j];
				// System.out.println("RESULT IS : "+results[i][j] +"\n");
			}
		}

		return (double) result / (rows * collumns);

	}

}
