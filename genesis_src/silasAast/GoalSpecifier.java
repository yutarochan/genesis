/*  1:   */ package silasAast;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class GoalSpecifier
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:26 */   private String goal = "SUMMARY";
/* 13:29 */   private Sequence narratorView = new Sequence();
/* 14:30 */   private Sequence reflections = new Sequence();
/* 15:31 */   private Sequence desiredReaction = new Sequence();
/* 16:   */   public static String NARRATOR_UNDERSTANDING;
/* 17:   */   public static String DESIRED_AUDIENCE_REACTION;
/* 18:   */   
/* 19:   */   public GoalSpecifier()
/* 20:   */   {
/* 21:41 */     Connections.getPorts(this).addSignalProcessor(NARRATOR_UNDERSTANDING, "setGoal");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setGoal(BetterSignal signal)
/* 25:   */   {
/* 26:45 */     if ((signal instanceof BetterSignal))
/* 27:   */     {
/* 28:46 */       this.narratorView = ((Sequence)signal.get(0, Sequence.class));
/* 29:47 */       this.reflections = ((Sequence)signal.get(1, Sequence.class));
/* 30:49 */       if (this.goal == "SUMMARY")
/* 31:   */       {
/* 32:50 */         this.desiredReaction = SummarySpecifier.setGoal(this.narratorView, this.reflections);
/* 33:51 */         Connections.getPorts(this).transmit(DESIRED_AUDIENCE_REACTION, this.desiredReaction);
/* 34:   */       }
/* 35:53 */       else if (this.goal == "SPOONFEED")
/* 36:   */       {
/* 37:54 */         this.desiredReaction = SpoonfeedSpecifier.setGoal(this.narratorView);
/* 38:55 */         Connections.getPorts(this).transmit(DESIRED_AUDIENCE_REACTION, this.desiredReaction);
/* 39:   */       }
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silasAast.GoalSpecifier
 * JD-Core Version:    0.7.0.1
 */