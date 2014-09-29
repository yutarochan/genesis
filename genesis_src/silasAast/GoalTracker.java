/*  1:   */ package silasAast;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.Iterator;
/* 10:   */ import java.util.Vector;
/* 11:   */ import matchers.StandardMatcher;
/* 12:   */ 
/* 13:   */ public class GoalTracker
/* 14:   */   extends AbstractWiredBox
/* 15:   */ {
/* 16:   */   public static String GOAL_REACTION;
/* 17:   */   public static String SIMULATED_REACTION;
/* 18:   */   public static String GAP_LIST;
/* 19:   */   public static String FINAL_TEXT_OUT;
/* 20:31 */   private int MATCH_MODE = 0;
/* 21:   */   private Entity oneBeforeMissing;
/* 22:   */   public static Sequence goalReaction;
/* 23:   */   public static Sequence simulatedReaction;
/* 24:36 */   public ArrayList<ArrayList<Entity>> gapList = new ArrayList();
/* 25:   */   public static boolean done;
/* 26:   */   public static Sequence finalText;
/* 27:   */   
/* 28:   */   public GoalTracker()
/* 29:   */   {
/* 30:42 */     Connections.getPorts(this).addSignalProcessor(GOAL_REACTION, "storeGoal");
/* 31:43 */     Connections.getPorts(this).addSignalProcessor(SIMULATED_REACTION, "prepareGoalEvaluation");
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void storeGoal(Object o)
/* 35:   */   {
/* 36:47 */     if ((o instanceof Sequence)) {
/* 37:48 */       goalReaction = (Sequence)o;
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void prepareGoalEvaluation(Object o)
/* 42:   */   {
/* 43:53 */     if ((o instanceof Sequence))
/* 44:   */     {
/* 45:54 */       simulatedReaction = (Sequence)o;
/* 46:55 */       if ((goalReaction != null) && (this.MATCH_MODE == 0)) {
/* 47:56 */         gapMatcher(goalReaction, simulatedReaction);
/* 48:   */       }
/* 49:   */     }
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void gapMatcher(Sequence goal, Sequence actual)
/* 53:   */   {
/* 54:62 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 55:63 */     int counter = 0;
/* 56:   */     Iterator localIterator2;
/* 57:65 */     for (Iterator localIterator1 = goal.getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 58:   */     {
/* 59:65 */       Entity goalElement = (Entity)localIterator1.next();
/* 60:66 */       counter++;
/* 61:67 */       ArrayList<Entity> toAdd = new ArrayList();
/* 62:68 */       localIterator2 = actual.getElements().iterator(); continue;Entity simElement = (Entity)localIterator2.next();
/* 63:69 */       if (matcher.matchAll(goalElement, simElement) == null)
/* 64:   */       {
/* 65:70 */         Entity lastElement = goal.getElement(counter - 1);
/* 66:71 */         toAdd.add(lastElement);
/* 67:72 */         toAdd.add(goalElement);
/* 68:73 */         this.gapList.add(toAdd);
/* 69:   */       }
/* 70:   */     }
/* 71:77 */     Connections.getPorts(this).transmit(GAP_LIST, this.gapList);
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silasAast.GoalTracker
 * JD-Core Version:    0.7.0.1
 */