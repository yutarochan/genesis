/*  1:   */ package carynKrakauer.aligner;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Thread;
/*  6:   */ import matthewFay.StoryAlignment.NWAligner;
/*  7:   */ import utils.Mark;
/*  8:   */ 
/*  9:   */ public class ConceptPatternAlignmentScorer
/* 10:   */   extends NWAligner<Entity, Entity>
/* 11:   */ {
/* 12:   */   public float sim(Entity a, Entity b)
/* 13:   */   {
/* 14:11 */     if (a == b) {
/* 15:12 */       return 1.0F;
/* 16:   */     }
/* 17:14 */     if (a.getType().equals(b.getType())) {
/* 18:15 */       return 1.0F;
/* 19:   */     }
/* 20:18 */     Thread threadA = (Thread)a.getBundle().firstElement();
/* 21:19 */     Thread threadB = (Thread)b.getBundle().firstElement();
/* 22:22 */     if ((threadA.size() > 2) && (threadB.size() > 2) && (
/* 23:23 */       (((String)threadA.get(threadA.size() - 2)).equals(threadB.get(threadB.size() - 2))) || 
/* 24:24 */       (((String)threadA.lastElement()).equals(threadB.get(threadB.size() - 2))) || 
/* 25:25 */       (((String)threadA.get(threadA.size() - 2)).equals(threadB.lastElement()))))
/* 26:   */     {
/* 27:28 */       Mark.say(new Object[] {"CARYN: found parents to be the same" });
/* 28:29 */       Mark.say(new Object[] {"Thread: " + threadA + " " + threadB });
/* 29:30 */       Mark.say(new Object[] {"Thread 2ndToLast: " + (String)threadA.get(threadA.size() - 2) + " " + (String)threadB.get(threadB.size() - 2) });
/* 30:31 */       Mark.say(new Object[] {"Thread last: " + (String)threadA.lastElement() + " " + (String)threadB.lastElement() });
/* 31:32 */       Mark.say(new Object[] {"" });
/* 32:   */       
/* 33:   */ 
/* 34:35 */       return 1.0F;
/* 35:   */     }
/* 36:39 */     return -1.0F;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.aligner.ConceptPatternAlignmentScorer
 * JD-Core Version:    0.7.0.1
 */