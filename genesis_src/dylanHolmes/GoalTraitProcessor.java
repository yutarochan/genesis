/*   1:    */ package dylanHolmes;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import matchers.StandardMatcher;
/*  11:    */ import utils.Mark;
/*  12:    */ 
/*  13:    */ public class GoalTraitProcessor
/*  14:    */   extends AbstractWiredBox
/*  15:    */ {
/*  16:    */   public static final String INPUT_GOALS = "my input port";
/*  17:    */   public static final String OUTPUT_DISCOVERED_TRAITS = "my output port";
/*  18:    */   public static final String HTML = "HTML port";
/*  19:    */   public static final String GOALS_TAB = "Goal-directed behavior";
/*  20:    */   
/*  21:    */   public GoalTraitProcessor()
/*  22:    */   {
/*  23: 37 */     setName("Local story processor");
/*  24:    */     
/*  25: 39 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/*  26:    */     
/*  27: 41 */     Connections.getPorts(this).addSignalProcessor("my input port", "processSignal");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void sendHTML(String s)
/*  31:    */   {
/*  32: 46 */     BetterSignal message = new BetterSignal(new Object[] { "Goal-directed behavior", s });
/*  33: 47 */     Connections.getPorts(this).transmit("HTML port", message);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void processSignal(Object signal)
/*  37:    */   {
/*  38: 56 */     if ((signal instanceof BetterSignal))
/*  39:    */     {
/*  40: 57 */       Mark.say(new Object[] {"Goal trait processor loud and clear" });
/*  41:    */       
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53: 70 */       BetterSignal s = (BetterSignal)signal;
/*  54: 71 */       ArrayList<Goal> goalCatalog = (ArrayList)s.get(0, ArrayList.class);
/*  55: 72 */       ArrayList<Goal> matchedGoals = (ArrayList)s.get(1, ArrayList.class);
/*  56:    */       Goal localGoal1;
/*  57: 74 */       for (Iterator localIterator1 = matchedGoals.iterator(); localIterator1.hasNext(); localGoal1 = (Goal)localIterator1.next()) {}
/*  58: 79 */       Object alternatives = new ArrayList();
/*  59: 81 */       for (Goal g : matchedGoals)
/*  60:    */       {
/*  61: 82 */         ArrayList<Goal> c = new ArrayList();
/*  62: 83 */         c.add(g);
/*  63: 84 */         for (Goal h : goalCatalog) {
/*  64: 90 */           if ((g.name != h.name) && (StandardMatcher.getBasicMatcher().match(g.end.getSubject(), h.end) != null)) {
/*  65: 91 */             c.add(h);
/*  66:    */           }
/*  67:    */         }
/*  68: 94 */         if (c.size() > 1) {
/*  69: 94 */           ((ArrayList)alternatives).add(c);
/*  70:    */         }
/*  71:    */       }
/*  72: 98 */       for (Object alts : (ArrayList)alternatives)
/*  73:    */       {
/*  74:100 */         if ((((Goal)((ArrayList)alts).get(0)).name == "theft") && (Goal.containsNamedGoal((ArrayList)alts, "solicit")))
/*  75:    */         {
/*  76:101 */           sendHTML("Patrick is inconsiderate because he harmed Boris by stealing when he could have accomplished the same goal by asking.");
/*  77:102 */           return;
/*  78:    */         }
/*  79:104 */         if ((((Goal)((ArrayList)alts).get(0)).name == "solicit") && (Goal.containsNamedGoal((ArrayList)alts, "theft")))
/*  80:    */         {
/*  81:105 */           sendHTML("Patrick is civil because he avoided harming Boris; he achieved his goal by asking instead of stealing.");
/*  82:106 */           return;
/*  83:    */         }
/*  84:    */       }
/*  85:109 */       if (Goal.containsNamedGoal(matchedGoals, "theft")) {
/*  86:110 */         sendHTML("Patrick harmed Boris by stealing, but I don't know how else he might have accomplished his goal.");
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.GoalTraitProcessor
 * JD-Core Version:    0.7.0.1
 */