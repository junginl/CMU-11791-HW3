package edu.cmu.deiis.annotator;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;
import org.apache.uima.util.Logger;
import edu.cmu.deiis.types.Answer;
import edu.cmu.deiis.types.LemmaToken;
import edu.cmu.deiis.types.POSToken;
import edu.cmu.deiis.types.Question;
import edu.cmu.deiis.types.Token;
import edu.cmu.deiis.util.NLPUtils;
import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetBeginAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.CharacterOffsetEndAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * TokenAnnotator annotates the question and a set of answers into their tokens
 */
public class TokenAnnotator extends JCasAnnotator_ImplBase {

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

    logger.log(Level.CONFIG, "TokenAnnotator Initialized.");
    System.setErr(new PrintStream(new OutputStream() {
      public void write(int b) {
      }
    }));
  }

  /**
   * TokenAnnotator:process method annotates given input into set of tokens.
   * 
   * @see JCasAnnotator_ImplBase#process(JCas)
   * 
   */
  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {

    // Offset to remove the Question/Answer tag
    int posInd = 0;
    int qOffset = 2;
    int aOffset = 4;
    int endLineCharSize = 2;
    // Get the question from the pipeline
    Question question = JCasUtil.selectSingle(aJCas, Question.class);
    logger.log(Level.FINE, "Question: Start: " + question.getBegin() + ",End: " + question.getEnd()
            + ",Annotated Text" + question.getCoveredText());

    Properties props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma");
    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

    // Tokenize the question text
    // create an empty (StanfordNLP) Annotation object just with the given question text
    String questionSent = question.getCoveredText();
    String questionText = questionSent.substring(qOffset);
    Annotation document = new Annotation(questionText);
    // run all Annotators on this text
    pipeline.annotate(document);
    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
    String word = null;
    String pos = null;
    String lemma = null;
    int startInd = 0;
    int endInd = 0;
    for (CoreMap sentence : sentences) {
      // traversing the words in the current sentence
      // a CoreLabel is a CoreMap with additional token-specific methods
      for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
        // this is the text of the token
        word = token.get(TextAnnotation.class);
        try {
          if (NLPUtils.isStopWord(word)) {
            System.out.println("Skipping : " + word);
            continue;
          }
        } catch (URISyntaxException e) {
          System.err.println("Warning: Cannot check for stopwords");
        }
        // Get POS tag of the token
        pos = token.get(PartOfSpeechAnnotation.class);
        // Get Lemma of the token
        lemma = token.get(LemmaAnnotation.class);

        // Create Token annotation object
        Token wordtoken = new Token(aJCas);
        POSToken posToken = new POSToken(aJCas);
        LemmaToken lemmaToken = new LemmaToken(aJCas);

        // Get the start and end index (with offset) from Stanford NLP tokenizer
        startInd = posInd + token.get(CharacterOffsetBeginAnnotation.class) + qOffset;
        endInd = posInd + token.get(CharacterOffsetEndAnnotation.class) + qOffset;
        wordtoken.setBegin(startInd);
        wordtoken.setEnd(endInd);
        wordtoken.setCasProcessorId("TokenAnnotator");
        wordtoken.setConfidence(1.0);
        // Write the token to the annotator index
        wordtoken.addToIndexes();
        // Get the start and end index (with offset) from Stanford NLP POS tagger
        posToken.setPosTag(pos);
        posToken.setBegin(startInd);
        posToken.setEnd(endInd);
        // Write the postoken to the annotator index
        posToken.addToIndexes();
        // Get the start and end index (with offset) from Stanford NLP lemmatizer
        lemmaToken.setLemmaToken(lemma);
        lemmaToken.setBegin(startInd);
        lemmaToken.setEnd(endInd);
        // Write the lemmatoken to the annotator index
        lemmaToken.addToIndexes();
      }
      posInd += questionSent.length() + endLineCharSize;
      // Get Answers from pipeline
      List<Answer> answerList = new ArrayList<Answer>(JCasUtil.select(aJCas, Answer.class));
      Iterator<Answer> iter = answerList.iterator();
      Answer answer = null;
      String answerSent = null;
      String answerText = null;

      // Tokenize the answer text
      while (iter.hasNext()) {
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        pipeline = new StanfordCoreNLP(props);
        answer = iter.next();
        logger.log(Level.FINE, "Answer: Start: " + answer.getBegin() + ",End: " + answer.getEnd()
                + ",Annotated Text" + answer.getCoveredText() + "(Key: " + answer.getIsCorrect()
                + ")");
        answerSent = answer.getCoveredText();
        answerText = answerSent.substring(aOffset);
        document = new Annotation(answerText);
        // run all Annotators on this text
        pipeline.annotate(document);
        sentences = document.get(SentencesAnnotation.class);
        for (CoreMap sentence1 : sentences) {
          // traversing the words in the current sentence
          // a CoreLabel is a CoreMap with additional token-specific methods
          for (CoreLabel token : sentence1.get(TokensAnnotation.class)) {
            // get the text of the token
            word = token.get(TextAnnotation.class);
            try {
              if (NLPUtils.isStopWord(word)) {
                continue;
              }
            } catch (URISyntaxException e) {
              System.err.println("Warning: Cannot check for stopwords");
            }
            // get the POS tag of the token
            pos = token.get(PartOfSpeechAnnotation.class);
            // Get lemma of the token
            lemma = token.get(LemmaAnnotation.class);
            // Create Token annotation object
            Token wordtoken = new Token(aJCas);
            POSToken posToken = new POSToken(aJCas);
            LemmaToken lemmaToken = new LemmaToken(aJCas);
            // Get the start and end index (with offset) from Stanford NLP tokenizer
            startInd = posInd + token.get(CharacterOffsetBeginAnnotation.class) + aOffset;
            endInd = posInd + token.get(CharacterOffsetEndAnnotation.class) + aOffset;
            wordtoken.setBegin(startInd);
            wordtoken.setEnd(endInd);
            wordtoken.setCasProcessorId("TokenAnnotator");
            wordtoken.setConfidence(1.0);
            // Write the token to the annotator index
            wordtoken.addToIndexes();
            // Get the start and end index (with offset) from Stanford NLP tagger
            posToken.setPosTag(pos);
            posToken.setBegin(startInd);
            posToken.setEnd(endInd);
            // Write the postoken to the annotator index
            posToken.addToIndexes();

            // Get the start and end index (with offset) from Stanford NLP lemmatizer
            lemmaToken.setLemmaToken(lemma);
            lemmaToken.setBegin(startInd);
            lemmaToken.setEnd(endInd);
            // Write the postoken to the annotator index
            lemmaToken.addToIndexes();
          }
        }
        posInd += answerSent.length() + endLineCharSize;
      }
    }
  }
}
