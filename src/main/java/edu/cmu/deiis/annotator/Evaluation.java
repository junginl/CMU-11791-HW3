package edu.cmu.deiis.annotator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.Question;

/**
 * Evaluation computes the precision at N for each Answers
 */
public class Evaluation extends JCasAnnotator_ImplBase {

  private double avgPrecision = 0.0;

  private int nDoc = 0;

  private Logger logger;

  /**
   * This method initializes the UIMA logger object and get the configuration parameters for the
   * annotator.
   * 
   * @throws ResourceInitializationException
   * 
   * @see org.apache.uima.fit.component.JCasAnnotator_ImplBase#initialize(org.apache.uima.UimaContext)
   */
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    // Initialize logger
    logger = getContext().getLogger();

    logger.log(Level.CONFIG, "Evaluation Initialized.");
  }

  /**
   * Evaluation:process method compute Precision@N for each QA Pair.
   * 
   * @see JCasAnnotator_ImplBase#process(JCas)
   * 
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Question question = JCasUtil.selectSingle(aJCas, Question.class);
    List<AnswerScore> answerScoreList = new ArrayList<AnswerScore>(JCasUtil.select(aJCas,
            AnswerScore.class));
    // N used in Precision@N
    int nValue = 0;
    int cValue = 0;
    double precisionAtN = 0.0;

    System.out.println("Question: " + question.getCoveredText());

    Collections.sort(answerScoreList, new Comparator<AnswerScore>() {
      @Override
      public int compare(AnswerScore ansS1, AnswerScore ansS2) {
        return ansS1.getScore() > ansS2.getScore() ? -1 : 1;
      }
    });

    Iterator<AnswerScore> iter = answerScoreList.iterator();
    Answer answer = null;
    AnswerScore answerScore = null;

    while (iter.hasNext()) {
      answerScore = iter.next();
      answer = answerScore.getAnswer();
      if (answer.getIsCorrect()) {
        nValue++;
        System.out.println("+ " + this.roundTwoDecimals(answerScore.getScore()) + " "
                + answer.getCoveredText());
      } else {
        System.out.println("- " + this.roundTwoDecimals(answerScore.getScore()) + " "
                + answer.getCoveredText());
      }
    }

    for (int at = 0; at < nValue; at++) {
      answer = answerScoreList.get(at).getAnswer();
      if (answer.getIsCorrect()) {
        cValue++;
      }
    }

    precisionAtN = ((double) cValue) / nValue;
    System.out.println("Precision at " + nValue + ": " + this.roundTwoDecimals(precisionAtN));
    nDoc++;
    avgPrecision += precisionAtN;

  }

  /**
   * Perfrom Final statistic- Compute Average Precision
   */
  @Override
  public void collectionProcessComplete() throws AnalysisEngineProcessException {
    System.out.println("Average Precision: " + this.roundTwoDecimals(avgPrecision / nDoc));
  }

  /**
   * 
   * Get the 2 decimal point rounded double value for pretty print
   * 
   * @param d
   * @return
   */
  double roundTwoDecimals(double d) {
    DecimalFormat twoDForm = new DecimalFormat("#.##");
    return Double.valueOf(twoDForm.format(d));
  }

}
