package edu.cmu.deiis.annotator;

import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import edu.cmu.deiis.types.LemmaToken;
import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.POSToken;
import edu.cmu.deiis.types.Question;

/**
 * SimilarityScorer scores the Answers using the cosine similarity measure
 */
public class SimilarityScorer extends JCasAnnotator_ImplBase {

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

    logger.log(Level.CONFIG, "SimilarityScorer Initialized.");
  }

  /**
   * SimilarityScorer:process method scores an Answer with respect to the given Question.
   * 
   * @see JCasAnnotator_ImplBase#process(JCas)
   * 
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {

    Question question = JCasUtil.selectSingle(aJCas, Question.class);
    List<Answer> answerList = new ArrayList<Answer>(JCasUtil.select(aJCas, Answer.class));
    List<NGram> nGramTokens = JCasUtil.selectCovered(NGram.class, question);
    List<POSToken> posTokens = JCasUtil.selectCovered(POSToken.class, question);
    List<LemmaToken> lemmaTokens = JCasUtil.selectCovered(LemmaToken.class, question);

    boolean negMatch = false;
    int qNegCt = 0;
    Iterator<NGram> iter1 = nGramTokens.iterator();
    NGram ngram = null;
    List<String> questionUnigramBOW = new ArrayList<String>(0);
    List<String> questionBigramBOW = new ArrayList<String>(0);
    List<String> questionTrigramBOW = new ArrayList<String>(0);
    List<String> questionPOSBOW = new ArrayList<String>(0);
    List<String> questionLemmaBOW = new ArrayList<String>(0);

    while (iter1.hasNext()) {
      // Scoring
      // Get Bag of Words term-frequency vector
      ngram = iter1.next();
      if (ngram.getElementType().equals("Unigram")) {
        questionUnigramBOW.add(ngram.getCoveredText());
        if (ngram.getCoveredText().contains("not") || ngram.getCoveredText().contains("n't")) {
          qNegCt++;
        }
      } else if (ngram.getElementType().equals("Bigram")) {
        questionBigramBOW.add(ngram.getCoveredText());
      } else {
        questionTrigramBOW.add(ngram.getCoveredText());
      }
    }

    Iterator<POSToken> posIter = posTokens.iterator();
    POSToken posToken = null;
    while (posIter.hasNext()) {
      questionPOSBOW.add(posIter.next().getPosTag());
    }

    Iterator<LemmaToken> lemmaIter = lemmaTokens.iterator();
    LemmaToken lemmaToken = null;
    while (lemmaIter.hasNext()) {
      questionLemmaBOW.add(lemmaIter.next().getLemmaToken());
    }

    // Get Answers from pipeline

    Iterator<Answer> iter2 = answerList.iterator();
    Answer answer = null;
    AnswerScore answerScore = null;
    List<String> answerUnigramBOW = null;
    List<String> commonUnigramBOW = null;
    List<String> answerBigramBOW = null;
    List<String> commonBigramBOW = null;
    List<String> answerTrigramBOW = null;
    List<String> commonTrigramBOW = null;

    List<String> answerPOSBOW = null;
    List<String> commonPOSBOW = null;
    List<String> answerLemmaBOW = null;
    List<String> commonLemmaBOW = null;

    // NGramOverlap Score
    double score1 = 0.0;
    double score2 = 0.0;
    double score3 = 0.0;
    // POS Tag score
    double score4 = 0.0;
    // Lemmatization Score
    double score5 = 0.0;

    // Merged Score
    double score = 0.0;
    int aNegCt = qNegCt;
    while (iter2.hasNext()) {
      // Scoring
      // Get Bag of Words term-frequency vector
      answer = iter2.next();
      answerUnigramBOW = new ArrayList<String>(0);
      answerBigramBOW = new ArrayList<String>(0);
      answerTrigramBOW = new ArrayList<String>(0);
      answerPOSBOW = new ArrayList<String>(0);
      answerLemmaBOW = new ArrayList<String>(0);
      nGramTokens = JCasUtil.selectCovered(NGram.class, answer);
      posTokens = JCasUtil.selectCovered(POSToken.class, answer);
      lemmaTokens = JCasUtil.selectCovered(LemmaToken.class, answer);
      aNegCt = qNegCt;
      Iterator<NGram> iter3 = nGramTokens.iterator();
      while (iter3.hasNext()) {
        // Scoring
        // Get Bag of Words term-frequency vector
        ngram = iter3.next();
        if (ngram.getElementType().equals("Unigram")) {
          answerUnigramBOW.add(ngram.getCoveredText());
          if (ngram.getCoveredText().contains("not") || ngram.getCoveredText().contains("n't")) {
            aNegCt--;
          }
        } else if (ngram.getElementType().equals("Bigram")) {
          answerBigramBOW.add(ngram.getCoveredText());
        } else {
          answerTrigramBOW.add(ngram.getCoveredText());
        }
      }

      posIter = posTokens.iterator();
      while (posIter.hasNext()) {
        answerPOSBOW.add(posIter.next().getPosTag());
      }

      lemmaIter = lemmaTokens.iterator();
      while (lemmaIter.hasNext()) {
        answerLemmaBOW.add(lemmaIter.next().getLemmaToken());
      }

      // Get common BOW
      commonUnigramBOW = new ArrayList<String>(questionUnigramBOW);
      commonBigramBOW = new ArrayList<String>(questionBigramBOW);
      commonTrigramBOW = new ArrayList<String>(questionTrigramBOW);
      commonPOSBOW = new ArrayList<String>(questionPOSBOW);
      commonLemmaBOW = new ArrayList<String>(questionLemmaBOW);

      commonUnigramBOW.retainAll(answerUnigramBOW);
      commonBigramBOW.retainAll(answerBigramBOW);
      commonTrigramBOW.retainAll(answerTrigramBOW);
      commonPOSBOW.retainAll(answerPOSBOW);
      commonLemmaBOW.retainAll(answerLemmaBOW);
      // Score the Question Answer Frequency vector

      score1 = ((double) commonUnigramBOW.size()) / answerUnigramBOW.size();
      score2 = ((double) commonBigramBOW.size()) / answerBigramBOW.size();
      score3 = ((double) commonTrigramBOW.size()) / answerTrigramBOW.size();
      score4 = ((double) commonPOSBOW.size()) / answerPOSBOW.size();
      score5 = ((double) commonLemmaBOW.size()) / answerLemmaBOW.size();

      // Merge Scores
      score = ((double) 0.7 * ((score1 + score2 + score3) / 3) + 0.3 * ((score4 + score5) / 2));

      if (aNegCt != 0) {
        score = 0;
      }

      // Add AnswerScore Annotation to index
      answerScore = new AnswerScore(aJCas);
      answerScore.setBegin(answer.getBegin());
      answerScore.setEnd(answer.getEnd());
      answerScore.setCasProcessorId("SimilarityScorer");
      answerScore.setConfidence(score);
      answerScore.setScore(score);
      answerScore.setAnswer(answer);
      answerScore.addToIndexes();
    }
  }

}
