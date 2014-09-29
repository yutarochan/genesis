/*  1:   */ package leonidFedorov;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import minilisp.LList;
/*  7:   */ import utils.Mark;
/*  8:   */ import utils.PairOfEntities;
/*  9:   */ 
/* 10:   */ public class MatchedReflection
/* 11:   */ {
/* 12: 9 */   private boolean debug = false;
/* 13:   */   LList<PairOfEntities> bindings;
/* 14:   */   Sequence rawRules;
/* 15:   */   Entity reflection;
/* 16:17 */   private Relation rrRelation = null;
/* 17:   */   
/* 18:   */   public MatchedReflection() {}
/* 19:   */   
/* 20:   */   public MatchedReflection(LList<PairOfEntities> bindings, Sequence rawRules, Entity reflection)
/* 21:   */   {
/* 22:25 */     this.bindings = bindings;
/* 23:26 */     this.rawRules = rawRules;
/* 24:27 */     this.reflection = reflection;
/* 25:   */   }
/* 26:   */   
/* 27:   */   private boolean isValidToAdd(Entity rule)
/* 28:   */   {
/* 29:31 */     if ((rule instanceof Entity))
/* 30:   */     {
/* 31:32 */       if (!this.rawRules.containsDeprecated(rule))
/* 32:   */       {
/* 33:33 */         Mark.say(new Object[] {Boolean.valueOf(this.debug), ":::==>This rule is already attached to reflection", rule });
/* 34:34 */         return false;
/* 35:   */       }
/* 36:   */     }
/* 37:   */     else
/* 38:   */     {
/* 39:38 */       Mark.err(new Object[] {"Attempting to add something that is not a Thing" });
/* 40:39 */       return false;
/* 41:   */     }
/* 42:41 */     return true;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Relation getMatchedReflection()
/* 46:   */   {
/* 47:45 */     this.rrRelation = new Relation(this.reflection, this.rawRules);
/* 48:46 */     return this.rrRelation;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public Sequence addRule(Entity rule)
/* 52:   */   {
/* 53:50 */     if (isValidToAdd(rule)) {
/* 54:51 */       this.rawRules.addElement(rule);
/* 55:   */     }
/* 56:53 */     return this.rawRules;
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String getName()
/* 60:   */   {
/* 61:57 */     return this.reflection.getName();
/* 62:   */   }
/* 63:   */   
/* 64:   */   public LList<PairOfEntities> getBindings()
/* 65:   */   {
/* 66:61 */     return this.bindings;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public Sequence getRawRules()
/* 70:   */   {
/* 71:65 */     return this.rawRules;
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     leonidFedorov.MatchedReflection
 * JD-Core Version:    0.7.0.1
 */