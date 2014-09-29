/*  1:   */ package genesis;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import frames.CauseFrame;
/*  7:   */ import utils.Mark;
/*  8:   */ 
/*  9:   */ public class Quantum
/* 10:   */ {
/* 11:   */   Entity thing;
/* 12:   */   boolean truth;
/* 13:   */   
/* 14:   */   public Entity getThing()
/* 15:   */   {
/* 16:19 */     return this.thing;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean isTruth()
/* 20:   */   {
/* 21:23 */     return this.truth;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Quantum(Entity statement, Entity question, boolean truth)
/* 25:   */   {
/* 26:28 */     Sequence conjunction = new Sequence("conjuction");
/* 27:29 */     conjunction.addElement(statement.getSubject());
/* 28:30 */     this.thing = new Relation("action", conjunction, question);
/* 29:31 */     this.thing.addType(CauseFrame.FRAMETYPE);
/* 30:32 */     if (!truth) {
/* 31:33 */       question.addFeature("not");
/* 32:   */     }
/* 33:35 */     this.truth = truth;
/* 34:36 */     Mark.say(new Object[] {"Learned quantum", Boolean.valueOf(truth) });
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.Quantum
 * JD-Core Version:    0.7.0.1
 */