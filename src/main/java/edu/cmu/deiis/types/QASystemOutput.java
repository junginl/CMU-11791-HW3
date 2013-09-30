

/* First created by JCasGen Sun Sep 29 22:09:03 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;


/** Output type for CasConsumer
 * Updated by JCasGen Sun Sep 29 22:37:38 EDT 2013
 * XML source: /usr0/home/kmuruges/Shared/Dropbox/CMU/Workspace/hw3-kmuruges/src/main/resources/descriptors/types/output-types.xml
 * @generated */
public class QASystemOutput extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(QASystemOutput.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected QASystemOutput() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public QASystemOutput(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public QASystemOutput(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public QASystemOutput(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
  //*--------------*
  //* Feature: questionAnnotation

  /** getter for questionAnnotation - gets Actual Question Annotation type
   * @generated */
  public Question getQuestionAnnotation() {
    if (QASystemOutput_Type.featOkTst && ((QASystemOutput_Type)jcasType).casFeat_questionAnnotation == null)
      jcasType.jcas.throwFeatMissing("questionAnnotation", "edu.cmu.deiis.types.QASystemOutput");
    return (Question)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_questionAnnotation)));}
    
  /** setter for questionAnnotation - sets Actual Question Annotation type 
   * @generated */
  public void setQuestionAnnotation(Question v) {
    if (QASystemOutput_Type.featOkTst && ((QASystemOutput_Type)jcasType).casFeat_questionAnnotation == null)
      jcasType.jcas.throwFeatMissing("questionAnnotation", "edu.cmu.deiis.types.QASystemOutput");
    jcasType.ll_cas.ll_setRefValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_questionAnnotation, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: answerScores

  /** getter for answerScores - gets List of AnswerScore types specific to a Question type.
   * @generated */
  public FSArray getAnswerScores() {
    if (QASystemOutput_Type.featOkTst && ((QASystemOutput_Type)jcasType).casFeat_answerScores == null)
      jcasType.jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.QASystemOutput");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_answerScores)));}
    
  /** setter for answerScores - sets List of AnswerScore types specific to a Question type. 
   * @generated */
  public void setAnswerScores(FSArray v) {
    if (QASystemOutput_Type.featOkTst && ((QASystemOutput_Type)jcasType).casFeat_answerScores == null)
      jcasType.jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.QASystemOutput");
    jcasType.ll_cas.ll_setRefValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_answerScores, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for answerScores - gets an indexed value - List of AnswerScore types specific to a Question type.
   * @generated */
  public AnswerScore getAnswerScores(int i) {
    if (QASystemOutput_Type.featOkTst && ((QASystemOutput_Type)jcasType).casFeat_answerScores == null)
      jcasType.jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.QASystemOutput");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_answerScores), i);
    return (AnswerScore)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_answerScores), i)));}

  /** indexed setter for answerScores - sets an indexed value - List of AnswerScore types specific to a Question type.
   * @generated */
  public void setAnswerScores(int i, AnswerScore v) { 
    if (QASystemOutput_Type.featOkTst && ((QASystemOutput_Type)jcasType).casFeat_answerScores == null)
      jcasType.jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.QASystemOutput");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_answerScores), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_answerScores), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: precisionAtK

  /** getter for precisionAtK - gets (Precision at K measure) Evaluation metric used for the QA System.
   * @generated */
  public double getPrecisionAtK() {
    if (QASystemOutput_Type.featOkTst && ((QASystemOutput_Type)jcasType).casFeat_precisionAtK == null)
      jcasType.jcas.throwFeatMissing("precisionAtK", "edu.cmu.deiis.types.QASystemOutput");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_precisionAtK);}
    
  /** setter for precisionAtK - sets (Precision at K measure) Evaluation metric used for the QA System. 
   * @generated */
  public void setPrecisionAtK(double v) {
    if (QASystemOutput_Type.featOkTst && ((QASystemOutput_Type)jcasType).casFeat_precisionAtK == null)
      jcasType.jcas.throwFeatMissing("precisionAtK", "edu.cmu.deiis.types.QASystemOutput");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((QASystemOutput_Type)jcasType).casFeatCode_precisionAtK, v);}    
  }

    