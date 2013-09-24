

/* First created by JCasGen Mon Sep 23 23:15:43 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** contains the lemmatized token of the word
 * Updated by JCasGen Mon Sep 23 23:16:24 EDT 2013
 * XML source: /usr0/home/kmuruges/Shared/Dropbox/CMU/Workspace/hw2-kmuruges/src/main/resources/descriptors/types/token-types.xml
 * @generated */
public class LemmaToken extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(LemmaToken.class);
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
  protected LemmaToken() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public LemmaToken(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public LemmaToken(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public LemmaToken(JCas jcas, int begin, int end) {
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
  //* Feature: lemmaToken

  /** getter for lemmaToken - gets 
   * @generated */
  public String getLemmaToken() {
    if (LemmaToken_Type.featOkTst && ((LemmaToken_Type)jcasType).casFeat_lemmaToken == null)
      jcasType.jcas.throwFeatMissing("lemmaToken", "edu.cmu.deiis.types.LemmaToken");
    return jcasType.ll_cas.ll_getStringValue(addr, ((LemmaToken_Type)jcasType).casFeatCode_lemmaToken);}
    
  /** setter for lemmaToken - sets  
   * @generated */
  public void setLemmaToken(String v) {
    if (LemmaToken_Type.featOkTst && ((LemmaToken_Type)jcasType).casFeat_lemmaToken == null)
      jcasType.jcas.throwFeatMissing("lemmaToken", "edu.cmu.deiis.types.LemmaToken");
    jcasType.ll_cas.ll_setStringValue(addr, ((LemmaToken_Type)jcasType).casFeatCode_lemmaToken, v);}    
  }

    