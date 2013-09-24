package edu.cmu.deiis.annotator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.FSCollectionFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;

import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.NGram;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Token;

/**
 * NGramAnnotator annotates the Question and Answer as Unigram,Bigram and Trigram tokens using the
 * Question/Answer tokens generated in the previous pipeline.
 */
public class NGramAnnotator extends JCasAnnotator_ImplBase {

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

    logger.log(Level.CONFIG, "NGramAnnotator Initialized.");
  }

  /**
   * NGramAnnotator:process method annotates given Question/Answer into set of ngram tokens.
   * 
   * @see JCasAnnotator_ImplBase#process(JCas)
   * 
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {

    // NGram annotation for Question
    // Get the Token types of Question
    Question question = JCasUtil.selectSingle(aJCas, Question.class);
    List<Token> questionTokens = JCasUtil.selectCovered(Token.class, question);
    this.addNGramAnnotations(aJCas, questionTokens, "Unigram", 1);
    this.addNGramAnnotations(aJCas, questionTokens, "Bigram", 2);
    this.addNGramAnnotations(aJCas, questionTokens, "Trigram", 3);

    // NGram annotation for Answers
    // Get the Token types of Answer
    List<Answer> answerList = new ArrayList<Answer>(JCasUtil.select(aJCas, Answer.class));
    Iterator<Answer> iter = answerList.iterator();
    List<Token> answerTokens = null;
    while(iter.hasNext()){
      answerTokens = JCasUtil.selectCovered(Token.class, iter.next());
      this.addNGramAnnotations(aJCas, answerTokens, "Unigram", 1);
      this.addNGramAnnotations(aJCas, answerTokens, "Bigram", 2);
      this.addNGramAnnotations(aJCas, answerTokens, "Trigram", 3);
    }
    
  }

  private void addNGramAnnotations(JCas aJCas, List<Token> tokens, String ngramType, int n) {
    int tokenListSize = tokens.size();
    NGram ngram = null;
    boolean containSpChar = false;
    Token coveredToken = null;
    int startInd = -1;
    int endInd = 0;
    List<Token> coveredTokens = null;
    for (int qt = 0; qt < tokenListSize; qt++) {
      // Check whether sufficient tokens available for creating ngram
      // In other words, check qt+(n-1) th token exists
      containSpChar=false;
      if (qt + (n - 1) >= tokenListSize) {
        return;
      }
      coveredTokens = new ArrayList<Token>(0);
      ngram = new NGram(aJCas);
      for (int t = 0; t < n; t++) {
        coveredToken = tokens.get(qt + t);
        if (startInd == -1) {
          startInd = coveredToken.getBegin();
        }
        if(coveredToken.getCoveredText().equals(".")||coveredToken.getCoveredText().equals("?")){
          containSpChar=true;
        }
        coveredTokens.add(coveredToken);
        endInd = coveredToken.getEnd();
      }
      if(containSpChar){
        continue;
      }
      ngram.setBegin(startInd);
      ngram.setEnd(endInd);
      ngram.setConfidence(1.0);
      ngram.setCasProcessorId("NGramAnnotator");
      ngram.setElementType(ngramType);
      ngram.setElements(FSCollectionFactory.createFSArray(aJCas, coveredTokens));
      ngram.addToIndexes();
      startInd = -1;
      endInd = 0;
    }
  }
}
