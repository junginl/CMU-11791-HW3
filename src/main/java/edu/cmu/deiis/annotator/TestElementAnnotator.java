package edu.cmu.deiis.annotator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.Question;

/**
 * TestElementAnnotator annotates an input data (QA pair) into question and a set of answers.
 */
public class TestElementAnnotator extends JCasAnnotator_ImplBase {

  private String questionPatternString;
  private String answersPatternString;
  private Logger logger;
  
  
  /** 
   * This method initializes the UIMA logger object and get the configuration parameters for the annotator
   * 
   * @see org.apache.uima.fit.component.JCasAnnotator_ImplBase#initialize(org.apache.uima.UimaContext)
   */
  public void initialize(UimaContext aContext) throws ResourceInitializationException {
    super.initialize(aContext);
    // Initialize logger
    logger = getContext().getLogger();
    
    // Read configuration parameter values
    setQuestionPatternString((String) getContext().getConfigParameterValue("questionPatternString"));
    setAnswersPatternString((String) getContext().getConfigParameterValue("answersPatternString"));
    
    logger.log(Level.CONFIG, "Regex pattern strings initilaized.");
    logger.log(Level.CONFIG, "TestElementAnnotator Initialized.");

  }

  /**
   * This method annotates question and answers using the format specified
   * in the documentation.
   * 
   * @see JCasAnnotator_ImplBase#process(JCas)
   * 
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    Pattern questionPattern = Pattern.compile(this.getQuestionPatternString(), Pattern.CASE_INSENSITIVE);
    Pattern answersPattern = Pattern.compile(this.getAnswersPatternString(), Pattern.CASE_INSENSITIVE);
    int answerCt = 0;
    // get document text
    String docText = aJCas.getDocumentText();
    // Search for match question pattern
    Matcher matcher = questionPattern.matcher(docText);
    if (matcher.find()) {
      // Create annotation of type Question
      Question question = new Question(aJCas);
      question.setBegin(matcher.start());
      question.setEnd(matcher.end());
      question.setConfidence(1.0);
      question.setCasProcessorId("TestElementAnnotator");
      question.addToIndexes();
    } else {
      logger.log(Level.WARNING, "No question found for the given input data.");
    }

    // Search for match question pattern
    matcher = answersPattern.matcher(docText);
    Answer answer = null;
    while (matcher.find()) {
      // Create annotation of type Answers
      answer = new Answer(aJCas);
      answer.setBegin(matcher.start());
      answer.setEnd(matcher.end());
      answer.setConfidence(1.0);
      answer.setCasProcessorId("TestElementAnnotator");
      // Check if the true result of the answer is correct. If so, change the default value (false)
      // to true.
      if ((matcher.group()).charAt(2) == '1') {
        answer.setIsCorrect(true);
      }
      answer.addToIndexes();
      answerCt++;
    }
    if (answerCt == 0) {
      logger.log(Level.WARNING, "No answers found for the given input data.");
    }

  }

  /**
   * @return the questionPatternString
   */
  public String getQuestionPatternString() {
    return questionPatternString;
  }

  /**
   * @param questionPatternString the questionPatternString to set
   */
  public void setQuestionPatternString(String questionPatternString) {
    this.questionPatternString = questionPatternString;
  }

  /**
   * @return the answersPatternString
   */
  public String getAnswersPatternString() {
    return answersPatternString;
  }

  /**
   * @param answersPatternString the answersPatternString to set
   */
  public void setAnswersPatternString(String answersPatternString) {
    this.answersPatternString = answersPatternString;
  }
}
