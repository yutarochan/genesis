/*  1:   */ package dylanHolmes;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import genesis.Genesis;
/*  5:   */ import utils.Mark;
/*  6:   */ 
/*  7:   */ public class LocalGenesis
/*  8:   */   extends Genesis
/*  9:   */ {
/* 10:   */   MeansEndsProcessor meProcessor;
/* 11:   */   GoalTraitProcessor gtProcessor;
/* 12:   */   LacunaProcessor lcProcessor;
/* 13:   */   
/* 14:   */   public LocalGenesis()
/* 15:   */   {
/* 16:28 */     Mark.say(new Object[] {"Dylan's local constructor" });
/* 17:   */     
/* 18:   */ 
/* 19:   */ 
/* 20:   */ 
/* 21:33 */     Connections.wire("complete story analysis port", getMentalModel1(), "complete story analysis port", getMeansProcessor());
/* 22:   */     
/* 23:35 */     Connections.wire("complete story analysis port", getMentalModel1(), "my input port", getLacunaProcessor());
/* 24:   */     
/* 25:   */ 
/* 26:38 */     Connections.wire("my output port", getMeansProcessor(), "my input port", getGoalTraitProcessor());
/* 27:   */     
/* 28:40 */     Connections.wire("HTML port", getGoalTraitProcessor(), getResultContainer());
/* 29:   */     
/* 30:42 */     Connections.wire("my output port", getGoalTraitProcessor(), getMentalModel2());
/* 31:   */     
/* 32:   */ 
/* 33:45 */     Connections.wire("my output port", getLacunaProcessor(), "port for story sequence injection", getMentalModel2());
/* 34:   */   }
/* 35:   */   
/* 36:   */   public MeansEndsProcessor getMeansProcessor()
/* 37:   */   {
/* 38:51 */     return this.meProcessor = this.meProcessor == null ? new MeansEndsProcessor() : this.meProcessor;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public LacunaProcessor getLacunaProcessor()
/* 42:   */   {
/* 43:55 */     return this.lcProcessor = this.lcProcessor == null ? new LacunaProcessor() : this.lcProcessor;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public GoalTraitProcessor getGoalTraitProcessor()
/* 47:   */   {
/* 48:59 */     return this.gtProcessor = this.gtProcessor == null ? new GoalTraitProcessor() : this.gtProcessor;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public static void main(String[] args)
/* 52:   */   {
/* 53:68 */     LocalGenesis myGenesis = new LocalGenesis();
/* 54:   */     
/* 55:70 */     myGenesis.startInFrame();
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */