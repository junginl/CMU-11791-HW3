/**
 * 
 */
package edu.cmu.deiis.util;

import java.util.List;

/**
 * @author kmuruges
 * 
 */
public class VectorUtils {
  public VectorUtils() {
  }

  public static Double sum(List<Double> list) {
    Double sum = 0.0;
    for (Double i : list)
      sum = sum + i;
    return sum;
  }
}
