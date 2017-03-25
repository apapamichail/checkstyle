package com.puppycrawl.tools.checkstyle.checks.semantic;
/* Copyright � 2015-2016 Angelos A. Papamichail .This file is part of my extension of Checkstyle of my Bachelro Thesis 
 * called : "Insertion of Semantic Checks In Static Analysis of Code", in greek "Eisagwgh Shmasiologikwn Sumbaseswn sthn 
 * Statikh Analush Kwdika"
 *   
 *
 * SemanticSimilarityCheck is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * SemanticSimilarityCheck is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SemanticSimilarityCheck.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * -------------------------------------------------------------------------------- 
 * ----			Description			-----
 * 
This class is responsible for taking the class from WordNetNameCheck
 * and find her Jaccard and Lin/Path similarity with the other classes in  the 
 * DetailAST tree.
 * it will return the full log of the commited checks with LinkedList<String> getResult()
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class SemanticSimilarityCheck {
	double LINRelationThreshold;
	double PathRelationThreshold;
	double jaccardSynsetRelationThreshold;
	double jaccardWordRelationThreshold;
	private static final double DEFAULT_VALUE = 0.2;
	private double value = DEFAULT_VALUE;
	public static boolean theCheckIsDone = false;
	JaccardSimilarityTest jacRelation;
	WordnetTreeRelationTest wordnetRelation;
	double jaccardResult;
	double wordnetResult[];
	private PrintWriter classNamesSimilarityViolationsJaccard,
			classNamesSimilarityViolationsLIN,
			classNamesSimilarityViolationsPATH;

	public SemanticSimilarityCheck(double threshold) throws IOException {
		jacRelation = new JaccardSimilarityTest();
		wordnetRelation = new WordnetTreeRelationTest();
		LINRelationThreshold = 0.4;
		PathRelationThreshold = 0.4;
		jaccardSynsetRelationThreshold = 0.4;
		jaccardWordRelationThreshold = 0.4;

		// the threshold is for filtering the errors purposes, for this reason
		// it isnt necessary
		classNamesSimilarityViolationsJaccard=	new PrintWriter(new BufferedWriter(new FileWriter(
						"classNamesSimilarityViolationsJaccard.txt", true)));
		classNamesSimilarityViolationsLIN = new PrintWriter(new BufferedWriter(
				new FileWriter("classNamesSimilarityViolationsLIN.txt", true)));
		classNamesSimilarityViolationsPATH = new PrintWriter(
				new BufferedWriter(new FileWriter(
						"classNamesSimilarityViolationsPATH.txt", true)));
		String classNames[] = SemanticViolationsDatabase.getClassNames();
		for (int i = 0; i < classNames.length; i++) {

			classNamesSimilarityViolationsJaccard.append(classNames[i] + " - ");
			classNamesSimilarityViolationsLIN.append(classNames[i] + " - ");
			classNamesSimilarityViolationsPATH.append(classNames[i] + " - ");

		}
		classNamesSimilarityViolationsJaccard.append("\n");

		classNamesSimilarityViolationsLIN.append("\n");
		classNamesSimilarityViolationsPATH.append("\n");
		for (int i = 0; i < classNames.length; i++) {
			System.out.println("step i : " + i + "\n");
			classNamesSimilarityViolationsJaccard.append(classNames[i] + "-");
			classNamesSimilarityViolationsLIN.append(classNames[i] + "-");
			classNamesSimilarityViolationsPATH.append(classNames[i] + "-");
			for (int j = 0; j < classNames.length; j++) {
				if (j > i) {
					wordnetResult = wordnetRelation.getTreeRelationResults(
							classNames[i], classNames[j]);
					jaccardResult = jacRelation.getJaccardResults(
							classNames[i], classNames[j]);
					classNamesSimilarityViolationsJaccard.append(jaccardResult
							+ " - ");
					classNamesSimilarityViolationsLIN.append(round(
							wordnetResult[0], 3) + " - ");
					classNamesSimilarityViolationsPATH.append(round(
							wordnetResult[1], 3) + " - ");

				} else {
					classNamesSimilarityViolationsJaccard.append(" - ");
					classNamesSimilarityViolationsLIN.append(" - ");
					classNamesSimilarityViolationsPATH.append(" - ");
				}

			}
			classNamesSimilarityViolationsJaccard.append("\n");
			classNamesSimilarityViolationsLIN.append("\n");
			classNamesSimilarityViolationsPATH.append("\n");

		}

		classNamesSimilarityViolationsJaccard.close();
		classNamesSimilarityViolationsLIN.close();
		classNamesSimilarityViolationsPATH.close();

	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public void countErrors() {

		if (jaccardResult > value) {

		}

		if (wordnetResult[0] > value) {
		}
		if (wordnetResult[1] > value) {
		}

	}

}
