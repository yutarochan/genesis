/*  1:   */ package victorYarlott;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import genesis.Genesis;
/*  7:   */ import java.util.Arrays;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class LocalGenesis
/* 11:   */   extends Genesis
/* 12:   */ {
/* 13:   */   LocalProcessor localProcessor;
/* 14:   */   
/* 15:   */   public LocalGenesis()
/* 16:   */   {
/* 17:27 */     Mark.say(new Object[] {"Victor's Genesis" });
/* 18:   */     
/* 19:   */ 
/* 20:   */ 
/* 21:   */ 
/* 22:32 */     Connections.wire("complete story analysis port", getMentalModel1(), "complete story analysis port", getLocalProcessor());
/* 23:   */     
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:   */ 
/* 31:41 */     ContradictionEngine ce = new ContradictionEngine(true, Arrays.asList(new String[] { "harm", "help" }));
/* 32:42 */     ce.getClass();Connections.wire("complete story analysis port", getMentalModel1(), "contradictions rx", ce);
/* 33:43 */     ce.getClass();Connections.wire("contradictions tx", ce, getResultContainer());
/* 34:   */   }
/* 35:   */   
/* 36:   */   public LocalProcessor getLocalProcessor()
/* 37:   */   {
/* 38:51 */     if (this.localProcessor == null) {
/* 39:52 */       this.localProcessor = new LocalProcessor();
/* 40:   */     }
/* 41:54 */     return this.localProcessor;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static void main(String[] args)
/* 45:   */   {
/* 46:63 */     LocalGenesis myGenesis = new LocalGenesis();
/* 47:64 */     myGenesis.startInFrame();
/* 48:   */   }
/* 49:   */   
/* 50:   */   private void transmit()
/* 51:   */   {
/* 52:71 */     Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "Victor's tab", "Hello world" }));
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     victorYarlott.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */