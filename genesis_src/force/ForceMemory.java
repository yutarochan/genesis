/*  1:   */ package force;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ import memory.Memory;
/*  8:   */ 
/*  9:   */ public class ForceMemory
/* 10:   */ {
/* 11:18 */   public static final String[] forceWords = ForceInterpreter.forceWords;
/* 12:19 */   public static final String[] forceThread = ForceInterpreter.forceThread;
/* 13:20 */   public static final String[] causeWords = ForceInterpreter.causeWords;
/* 14:21 */   public static final String[] activeWords = ForceInterpreter.activeWords;
/* 15:   */   
/* 16:   */   public static Memory getMemory()
/* 17:   */   {
/* 18:27 */     return Memory.getMemory();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static boolean isForceRelation(Relation force)
/* 22:   */   {
/* 23:37 */     Entity subject = force.getSubject();
/* 24:38 */     Entity object = force.getObject();
/* 25:39 */     ArrayList<Entity> neighbors = new ArrayList();
/* 26:   */     
/* 27:   */ 
/* 28:   */ 
/* 29:43 */     Relation searchRelation = new Relation(forceThread[0], subject, object);
/* 30:44 */     searchRelation.removeType("thing");
/* 31:48 */     for (int i = 1; i < forceThread.length; i++)
/* 32:   */     {
/* 33:49 */       searchRelation.addType(forceThread[i]);
/* 34:50 */       List<Entity> tempResult = getMemory().getNeighbors(searchRelation);
/* 35:53 */       for (int j = 1; j < tempResult.size(); j++) {
/* 36:54 */         if (!neighbors.contains(tempResult.get(j))) {
/* 37:55 */           neighbors.add((Entity)tempResult.get(j));
/* 38:   */         }
/* 39:   */       }
/* 40:   */     }
/* 41:60 */     Entity agonist = ForceInterpreter.getAgonist(force);
/* 42:61 */     Entity antagonist = ForceInterpreter.getAntagonist(force);
/* 43:64 */     for (int j = 0; j < neighbors.size(); j++)
/* 44:   */     {
/* 45:65 */       Entity currThing = (Entity)neighbors.get(j);
/* 46:66 */       if ((currThing instanceof Relation))
/* 47:   */       {
/* 48:67 */         Relation currRelation = (Relation)currThing;
/* 49:68 */         if ((ForceInterpreter.getAgonist(currRelation).isEqual(agonist)) && 
/* 50:69 */           (ForceInterpreter.getAntagonist(currRelation).isEqual(antagonist)) && 
/* 51:70 */           (force.getObject().isA(currRelation.getObject().getType()))) {
/* 52:71 */           return true;
/* 53:   */         }
/* 54:   */       }
/* 55:   */     }
/* 56:76 */     return false;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     force.ForceMemory
 * JD-Core Version:    0.7.0.1
 */