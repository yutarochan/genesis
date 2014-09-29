/*  1:   */ package leonidFedorov;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import storyProcessor.ReflectionDescription;
/*  5:   */ 
/*  6:   */ public class AugmentedReflectionDescription
/*  7:   */ {
/*  8:   */   ReflectionDescription reflectionDescription;
/*  9:   */   Sequence rawRules;
/* 10:   */   
/* 11:   */   public ReflectionDescription getReflectionDescription()
/* 12:   */   {
/* 13:15 */     return this.reflectionDescription;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public Sequence getRawRules()
/* 17:   */   {
/* 18:19 */     return this.rawRules;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public AugmentedReflectionDescription(ReflectionDescription reflectionDescription, Sequence rawRules)
/* 22:   */   {
/* 23:26 */     this.rawRules = rawRules;
/* 24:27 */     this.reflectionDescription = reflectionDescription;
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     leonidFedorov.AugmentedReflectionDescription
 * JD-Core Version:    0.7.0.1
 */