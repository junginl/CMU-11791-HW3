/**
 * 
 */
package edu.cmu.deiis.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kmuruges
 * 
 */
public class ScoringUtils {
  public ScoringUtils() {
  }

  public static double getWeightedScore(List<Double> scores, List<Double> scoreWeights) {
    double fscore = 0.0;
    if (scores == null || scores.size() == 0) {
      return fscore;
    }
    int nScores = scores.size();

    if (scoreWeights == null || scoreWeights.size() == 0 || nScores != scoreWeights.size()) {
      return ((double) VectorUtils.sum(scores)) / nScores;
    }
    double tScoreWt = VectorUtils.sum(scoreWeights);
    for (int i = 0; i < nScores; i++) {
      fscore += (((double) scoreWeights.get(i)) / tScoreWt) * scores.get(i);
    }

    return fscore;
  }

  public static double computeWordMatchBasedScore(List<String> vector1, List<String> vector2) {
    double score = 0.0;
    List<String> commonBOW = new ArrayList<String>(vector1);
    commonBOW.retainAll(vector2);
    score = ((double) commonBOW.size()) / vector2.size();
    return score;
  }

  public static double computeCosineSimilarity(List<String> vector1, List<String> vector2) {
    double score = 0.0;
    return score;

  }
}
