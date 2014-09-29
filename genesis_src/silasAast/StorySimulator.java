/*  1:   */ package silasAast;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class StorySimulator
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public static String SIMULATION_SPECS_IN;
/* 13:   */   public static String SIMULATION_RESULTS_IN;
/* 14:   */   public static String EXPORT_COMMONSENSE;
/* 15:   */   public static String EXPORT_REFLECTIVE;
/* 16:   */   public static String EXPORT_TEXT;
/* 17:   */   public static String SIMULATION_RESULTS_OUT;
/* 18:   */   private BetterSignal bundle;
/* 19:   */   private Sequence commonsense;
/* 20:   */   private Sequence reflective;
/* 21:   */   private Sequence modifiedStory;
/* 22:   */   private Sequence simulatedAudienceReaction;
/* 23:   */   
/* 24:   */   public StorySimulator()
/* 25:   */   {
/* 26:44 */     Connections.getPorts(this).addSignalProcessor(SIMULATION_SPECS_IN, "unwrapSimulationInfo");
/* 27:45 */     Connections.getPorts(this).addSignalProcessor(SIMULATION_RESULTS_IN, "processSimulationResults");
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void unwrapSimulationInfo(Object o)
/* 31:   */   {
/* 32:49 */     if ((o instanceof BetterSignal))
/* 33:   */     {
/* 34:50 */       this.bundle = ((BetterSignal)o);
/* 35:51 */       this.commonsense = ((Sequence)this.bundle.get(0, Sequence.class));
/* 36:52 */       this.reflective = ((Sequence)this.bundle.get(1, Sequence.class));
/* 37:53 */       this.modifiedStory = ((Sequence)this.bundle.get(2, Sequence.class));
/* 38:   */       
/* 39:55 */       Connections.getPorts(this).transmit(EXPORT_COMMONSENSE, this.commonsense);
/* 40:56 */       Connections.getPorts(this).transmit(EXPORT_REFLECTIVE, this.reflective);
/* 41:57 */       Connections.getPorts(this).transmit(EXPORT_TEXT, this.modifiedStory);
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void processSimulationResults(Object o)
/* 46:   */   {
/* 47:62 */     if ((o instanceof Sequence))
/* 48:   */     {
/* 49:63 */       this.simulatedAudienceReaction = ((Sequence)o);
/* 50:64 */       Connections.getPorts(this).transmit(SIMULATION_RESULTS_OUT, this.simulatedAudienceReaction);
/* 51:   */     }
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silasAast.StorySimulator
 * JD-Core Version:    0.7.0.1
 */