
/* First created by JCasGen Sun Sep 29 22:09:03 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Output type for CasConsumer
 * Updated by JCasGen Fri Oct 04 20:18:18 EDT 2013
 * @generated */
public class QASystemOutput_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (QASystemOutput_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = QASystemOutput_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new QASystemOutput(addr, QASystemOutput_Type.this);
  			   QASystemOutput_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new QASystemOutput(addr, QASystemOutput_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = QASystemOutput.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.QASystemOutput");



  /** @generated */
  final Feature casFeat_questionAnnotation;
  /** @generated */
  final int     casFeatCode_questionAnnotation;
  /** @generated */ 
  public int getQuestionAnnotation(int addr) {
        if (featOkTst && casFeat_questionAnnotation == null)
      jcas.throwFeatMissing("questionAnnotation", "edu.cmu.deiis.types.QASystemOutput");
    return ll_cas.ll_getRefValue(addr, casFeatCode_questionAnnotation);
  }
  /** @generated */    
  public void setQuestionAnnotation(int addr, int v) {
        if (featOkTst && casFeat_questionAnnotation == null)
      jcas.throwFeatMissing("questionAnnotation", "edu.cmu.deiis.types.QASystemOutput");
    ll_cas.ll_setRefValue(addr, casFeatCode_questionAnnotation, v);}
    
  
 
  /** @generated */
  final Feature casFeat_answerScores;
  /** @generated */
  final int     casFeatCode_answerScores;
  /** @generated */ 
  public int getAnswerScores(int addr) {
        if (featOkTst && casFeat_answerScores == null)
      jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.QASystemOutput");
    return ll_cas.ll_getRefValue(addr, casFeatCode_answerScores);
  }
  /** @generated */    
  public void setAnswerScores(int addr, int v) {
        if (featOkTst && casFeat_answerScores == null)
      jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.QASystemOutput");
    ll_cas.ll_setRefValue(addr, casFeatCode_answerScores, v);}
    
   /** @generated */
  public int getAnswerScores(int addr, int i) {
        if (featOkTst && casFeat_answerScores == null)
      jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.QASystemOutput");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_answerScores), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_answerScores), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_answerScores), i);
  }
   
  /** @generated */ 
  public void setAnswerScores(int addr, int i, int v) {
        if (featOkTst && casFeat_answerScores == null)
      jcas.throwFeatMissing("answerScores", "edu.cmu.deiis.types.QASystemOutput");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_answerScores), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_answerScores), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_answerScores), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_precisionAtK;
  /** @generated */
  final int     casFeatCode_precisionAtK;
  /** @generated */ 
  public double getPrecisionAtK(int addr) {
        if (featOkTst && casFeat_precisionAtK == null)
      jcas.throwFeatMissing("precisionAtK", "edu.cmu.deiis.types.QASystemOutput");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_precisionAtK);
  }
  /** @generated */    
  public void setPrecisionAtK(int addr, double v) {
        if (featOkTst && casFeat_precisionAtK == null)
      jcas.throwFeatMissing("precisionAtK", "edu.cmu.deiis.types.QASystemOutput");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_precisionAtK, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public QASystemOutput_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_questionAnnotation = jcas.getRequiredFeatureDE(casType, "questionAnnotation", "edu.cmu.deiis.types.Question", featOkTst);
    casFeatCode_questionAnnotation  = (null == casFeat_questionAnnotation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_questionAnnotation).getCode();

 
    casFeat_answerScores = jcas.getRequiredFeatureDE(casType, "answerScores", "uima.cas.FSArray", featOkTst);
    casFeatCode_answerScores  = (null == casFeat_answerScores) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_answerScores).getCode();

 
    casFeat_precisionAtK = jcas.getRequiredFeatureDE(casType, "precisionAtK", "uima.cas.Double", featOkTst);
    casFeatCode_precisionAtK  = (null == casFeat_precisionAtK) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_precisionAtK).getCode();

  }
}



    