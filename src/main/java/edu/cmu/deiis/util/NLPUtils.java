/**
 * 
 */
package edu.cmu.deiis.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 * @author kmuruges
 * 
 */
public class NLPUtils {
  private static List<String> englishStopWords = null;

  private static void initializeStopWordList(String filePath) throws URISyntaxException {
    try {
      // TODO: Find a easiest way to get absolute path of the file
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      URL url = classLoader.getResource("");
      String absFilePath = new File(url.toURI()).getParentFile().getParentFile().getAbsolutePath()
              + "/" + filePath;
      File file = new File(absFilePath);
      englishStopWords = FileUtils.readLines(file);
    } catch (IOException e) {
      System.err.println("Warning: Cannot read stopword list. Skipping operation");
    }
  }

  public static boolean isStopWord(String word) throws URISyntaxException {
    // Check whether the stopword list is initialized
    if (englishStopWords == null) {
      NLPUtils.initializeStopWordList("src/main/resources/stopwords/english.stop.txt");
      // if its still not initialized, return default: false for all words.
      if (englishStopWords == null) {
        return false;
      }
    }
    if (englishStopWords.contains(word)) {
      return true;
    }
    return false;
  }

}
