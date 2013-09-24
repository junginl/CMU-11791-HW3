
/* First created by JCasGen Mon Sep 23 23:15:43 EDT 2013 */
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

/** contains the lemmatized token of the word
 * Updated by JCasGen Mon Sep 23 23:16:24 EDT 2013
 * @generated */
public class LemmaToken_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (LemmaToken_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = LemmaToken_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new LemmaToken(addr, LemmaToken_Type.this);
  			   LemmaToken_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new LemmaToken(addr, LemmaToken_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = LemmaToken.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.deiis.types.LemmaToken");
 
  /** @generated */
  final Feature casFeat_lemmaToken;
  /** @generated */
  final int     casFeatCode_lemmaToken;
  /** @generated */ 
  public String getLemmaToken(int addr) {
        if (featOkTst && casFeat_lemmaToken == null)
      jcas.throwFeatMissing("lemmaToken", "edu.cmu.deiis.types.LemmaToken");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lemmaToken);
  }
  /** @generated */    
  public void setLemmaToken(int addr, String v) {
        if (featOkTst && casFeat_lemmaToken == null)
      jcas.throwFeatMissing("lemmaToken", "edu.cmu.deiis.types.LemmaToken");
    ll_cas.ll_setStringValue(addr, casFeatCode_lemmaToken, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public LemmaToken_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_lemmaToken = jcas.getRequiredFeatureDE(casType, "lemmaToken", "uima.cas.String", featOkTst);
    casFeatCode_lemmaToken  = (null == casFeat_lemmaToken) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lemmaToken).getCode();

  }
}



    