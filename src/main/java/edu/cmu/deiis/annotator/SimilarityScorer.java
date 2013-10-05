package edu.cmu.deiis.annotator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.ResultSpecification;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import org.cleartk.ne.type.NamedEntityMention;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.AnswerScore;
import edu.cmu.deiis.types.LemmaToken;
import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.POSToken;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.util.ScoringUtils;

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

    List<String> questionUnigramBOW = new ArrayList<String>(0);
    List<String> questionBigramBOW = new ArrayList<String>(0);
    List<String> questionTrigramBOW = new ArrayList<String>(0);
    List<String> questionPOSBOW = new ArrayList<String>(0);
    List<String> questionLemmaBOW = new ArrayList<String>(0);
    List<String> questionNEBOW = new ArrayList<String>(0);
    Answer answer = null;
    AnswerScore answerScore = null;
    List<String> answerUnigramBOW = null;
    List<String> answerBigramBOW = null;
    List<String> answerTrigramBOW = null;
    List<String> answerPOSBOW = null;
    List<String> answerLemmaBOW = null;
    List<String> answerNEBOW = null;
    List<Double> scores = null;
    List<Double> scoreWeights = new ArrayList<Double>(0);
    
    
    // Fetch the res[ective annotations from JCas
    Question question = JCasUtil.selectSingle(aJCas, Question.class);
    boolean remoteAnnotStatus = false;
    List<Answer> answerList = new ArrayList<Answer>(JCasUtil.select(aJCas, Answer.class));
    List<NGram> nGramTokens = JCasUtil.selectCovered(NGram.class, question);
    List<POSToken> posTokens = JCasUtil.selectCovered(POSToken.class, question);
    List<LemmaToken> lemmaTokens = JCasUtil.selectCovered(LemmaToken.class, question);
    ResultSpecification resultSpec = getResultSpecification();
    // Check the status of the remote annotation service call
    remoteAnnotStatus = resultSpec.containsType("org.cleartk.ne.type.NamedEntityMention",
            aJCas.getDocumentLanguage());

    List<NamedEntityMention> neList = new ArrayList<NamedEntityMention>(0);
    if (remoteAnnotStatus) {
      neList = new ArrayList<NamedEntityMention>(0);
      neList = JCasUtil.selectCovered(NamedEntityMention.class, question);
    }

    

    Iterator<NGram> iter1 = nGramTokens.iterator();
    NGram ngram = null;
    int qNegCt = 0;
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

    // Question POS Token
    Iterator<POSToken> posIter = posTokens.iterator();
    while (posIter.hasNext()) {
      questionPOSBOW.add(posIter.next().getPosTag());
    }

    // Question Lemma Tokens
    Iterator<LemmaToken> lemmaIter = lemmaTokens.iterator();
    while (lemmaIter.hasNext()) {
      questionLemmaBOW.add(lemmaIter.next().getLemmaToken());
    }

    // Question Named Entity Mentions from Remote Service
    Iterator<NamedEntityMention> neIter = neList.iterator();
    while (neIter.hasNext()) {
      questionNEBOW.add(neIter.next().getCoveredText());
    }

    // Get Answers from pipeline
    Iterator<Answer> iter2 = answerList.iterator();
    

    // Merged Score/ Weighted Score
    double score = 0.0;
    int aNegCt = qNegCt;
    while (iter2.hasNext()) {

      // Scoring
      // Get Bag of Words term-frequency vector
      answer = iter2.next();
      scores = new ArrayList<Double>(0);
      scoreWeights = new ArrayList<Double>(0);
      answerUnigramBOW = new ArrayList<String>(0);
      answerBigramBOW = new ArrayList<String>(0);
      answerTrigramBOW = new ArrayList<String>(0);
      answerPOSBOW = new ArrayList<String>(0);
      answerLemmaBOW = new ArrayList<String>(0);
      answerNEBOW = new ArrayList<String>(0);

      nGramTokens = JCasUtil.selectCovered(NGram.class, answer);
      posTokens = JCasUtil.selectCovered(POSToken.class, answer);
      lemmaTokens = JCasUtil.selectCovered(LemmaToken.class, answer);
      neList = new ArrayList<NamedEntityMention>(0);
      if (remoteAnnotStatus) {
        if (JCasUtil.contains(aJCas, answer, NamedEntityMention.class)) {
          neList = JCasUtil.selectCovered(NamedEntityMention.class, answer);
        }
      }

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

      // Answer POS Tokens
      posIter = posTokens.iterator();
      while (posIter.hasNext()) {
        answerPOSBOW.add(posIter.next().getPosTag());
      }

      // Answer Lemma Tokens
      lemmaIter = lemmaTokens.iterator();
      while (lemmaIter.hasNext()) {
        answerLemmaBOW.add(lemmaIter.next().getLemmaToken());
      }

      // Answer Named Entity Mentions from Remote Service
      neIter = neList.iterator();
      while (neIter.hasNext()) {
        answerNEBOW.add(neIter.next().getCoveredText());
      }

      // Score the Question Answer Frequency vector using Word-based Matching

      if (answerUnigramBOW.size() != 0) {
        scores.add(ScoringUtils.computeWordMatchBasedScore(questionUnigramBOW, answerUnigramBOW));
        scoreWeights.add(3.0);
      }
      if (answerBigramBOW.size() != 0) {
        scores.add(ScoringUtils.computeWordMatchBasedScore(questionBigramBOW, answerBigramBOW));
        scoreWeights.add(1.0);
      }
      if (answerTrigramBOW.size() != 0) {
        scores.add(ScoringUtils.computeWordMatchBasedScore(questionTrigramBOW, answerTrigramBOW));
        scoreWeights.add(1.0);
      }
      if (answerPOSBOW.size() != 0) {
        scores.add(ScoringUtils.computeWordMatchBasedScore(questionPOSBOW, answerPOSBOW));
        scoreWeights.add(2.0);
      }
      if (answerLemmaBOW.size() != 0) {
        scores.add(ScoringUtils.computeWordMatchBasedScore(questionLemmaBOW, answerLemmaBOW));
        scoreWeights.add(2.0);
      }
      if (answerNEBOW.size() != 0) {
        scores.add(ScoringUtils.computeWordMatchBasedScore(questionNEBOW, answerNEBOW));
        scoreWeights.add(2.0);
      }

      // Merge Scores
      score = ((double) ScoringUtils.getWeightedScore(scores, scoreWeights));

      // Apply Heuristics on the candidate answers
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
