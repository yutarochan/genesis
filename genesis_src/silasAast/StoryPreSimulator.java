/*  1:   */ package silasAast;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class StoryPreSimulator
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public static String AUDIENCE_COMMONSENSE_IN;
/* 13:   */   public static String AUDIENCE_REFLECTIVE_IN;
/* 14:   */   public static String TEXT_IN;
/* 15:   */   public static String AUDIENCE_COMMONSENSE_OUT;
/* 16:   */   public static String AUDIENCE_REFLECTIVE_OUT;
/* 17:   */   public static String SIMULATION_INFO_OUT;
/* 18:40 */   private Sequence commonsenseRules = new Sequence();
/* 19:41 */   private Sequence conceptRules = new Sequence();
/* 20:42 */   private Sequence modifiedStory = new Sequence();
/* 21:   */   
/* 22:   */   public StoryPreSimulator()
/* 23:   */   {
/* 24:45 */     Connections.getPorts(this).addSignalProcessor(AUDIENCE_COMMONSENSE_IN, "storeCommonsense");
/* 25:46 */     Connections.getPorts(this).addSignalProcessor(AUDIENCE_REFLECTIVE_IN, "storeReflective");
/* 26:47 */     Connections.getPorts(this).addSignalProcessor(TEXT_IN, "storeModifiedStory");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void storeCommonsense(Object o)
/* 30:   */   {
/* 31:51 */     if ((o instanceof Sequence)) {
/* 32:52 */       this.commonsenseRules = ((Sequence)o);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void storeReflective(Object o)
/* 37:   */   {
/* 38:58 */     if ((o instanceof Sequence)) {
/* 39:59 */       this.conceptRules = ((Sequence)o);
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void storeModifiedStory(Object o)
/* 44:   */   {
/* 45:65 */     if ((o instanceof Sequence))
/* 46:   */     {
/* 47:66 */       this.modifiedStory = ((Sequence)o);
/* 48:67 */       BetterSignal simulatorWrap = new BetterSignal(new Object[] { this.commonsenseRules, this.conceptRules, this.modifiedStory });
/* 49:68 */       Connections.getPorts(this).transmit(SIMULATION_INFO_OUT, simulatorWrap);
/* 50:   */     }
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silasAast.StoryPreSimulator
 * JD-Core Version:    0.7.0.1
 */