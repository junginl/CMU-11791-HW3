

/* First created by JCasGen Mon Sep 23 23:14:24 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Mon Sep 23 23:16:24 EDT 2013
 * XML source: /usr0/home/kmuruges/Shared/Dropbox/CMU/Workspace/hw2-kmuruges/src/main/resources/descriptors/types/token-types.xml
 * @generated */
public class POSToken extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(POSToken.class);
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
  protected POSToken() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public POSToken(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public POSToken(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public POSToken(JCas jcas, int begin, int end) {
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
  //* Feature: posTag

  /** getter for posTag - gets Contains the actual POS Tag
   * @generated */
  public String getPosTag() {
    if (POSToken_Type.featOkTst && ((POSToken_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "edu.cmu.deiis.types.POSToken");
    return jcasType.ll_cas.ll_getStringValue(addr, ((POSToken_Type)jcasType).casFeatCode_posTag);}
    
  /** setter for posTag - sets Contains the actual POS Tag 
   * @generated */
  public void setPosTag(String v) {
    if (POSToken_Type.featOkTst && ((POSToken_Type)jcasType).casFeat_posTag == null)
      jcasType.jcas.throwFeatMissing("posTag", "edu.cmu.deiis.types.POSToken");
    jcasType.ll_cas.ll_setStringValue(addr, ((POSToken_Type)jcasType).casFeatCode_posTag, v);}    
  }

    