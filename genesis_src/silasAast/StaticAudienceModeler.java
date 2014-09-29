/*  1:   */ package silasAast;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import models.MentalModel;
/*  8:   */ 
/*  9:   */ public class StaticAudienceModeler
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:15 */   public static String DECLARED_AUDIENCE = "declared audience";
/* 13:16 */   public static String DECLARED_COMMONSENSE = "commonsense rules for declared audience";
/* 14:17 */   public static String DECLARED_CONCEPTS = "reflective rules for declared audience";
/* 15:   */   public static String AUDIENCE_COMMONSENSE_OUT;
/* 16:   */   public static String AUDIENCE_REFLECTIVE_OUT;
/* 17:25 */   public Sequence commonsenseRules = new Sequence();
/* 18:26 */   public Sequence conceptRules = new Sequence();
/* 19:   */   public String declaredAudience;
/* 20:   */   
/* 21:   */   public StaticAudienceModeler()
/* 22:   */   {
/* 23:30 */     Connections.getPorts(this).addSignalProcessor(DECLARED_AUDIENCE, "modelAudience");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void modelAudience(Object audience)
/* 27:   */   {
/* 28:35 */     if ((audience instanceof String)) {
/* 29:36 */       this.declaredAudience = ((String)audience);
/* 30:   */     }
/* 31:39 */     String commonsenseFileName = this.declaredAudience + "Commonsense";
/* 32:40 */     String conceptsFileName = this.declaredAudience + "Reflective";
/* 33:   */     
/* 34:42 */     MentalModel commonsenseModel = new MentalModel("NarratorModel", commonsenseFileName);
/* 35:43 */     MentalModel conceptModel = new MentalModel("NarratorModel", conceptsFileName);
/* 36:   */     
/* 37:45 */     this.commonsenseRules = commonsenseModel.getCommonsenseRules();
/* 38:46 */     this.conceptRules = conceptModel.getConceptPatterns();
/* 39:   */     
/* 40:48 */     Connections.getPorts(this).transmit(AUDIENCE_COMMONSENSE_OUT, this.commonsenseRules);
/* 41:49 */     Connections.getPorts(this).transmit(AUDIENCE_REFLECTIVE_OUT, this.conceptRules);
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silasAast.StaticAudienceModeler
 * JD-Core Version:    0.7.0.1
 */