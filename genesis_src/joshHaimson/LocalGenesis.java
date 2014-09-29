/*  1:   */ package joshHaimson;
/*  2:   */ 
/*  3:   */ import ati.ParallelJPanel;
/*  4:   */ import connections.Connections;
/*  5:   */ import genesis.Genesis;
/*  6:   */ import utils.Mark;
/*  7:   */ 
/*  8:   */ public class LocalGenesis
/*  9:   */   extends Genesis
/* 10:   */ {
/* 11:   */   LocalProcessor localProcessor;
/* 12:   */   
/* 13:   */   public LocalGenesis()
/* 14:   */   {
/* 15:23 */     Mark.say(new Object[] {"Local constructor" });
/* 16:   */     
/* 17:   */ 
/* 18:   */ 
/* 19:   */ 
/* 20:28 */     Connections.wire("complete story analysis port", getMentalModel1(), "complete story analysis port", getLocalProcessor());
/* 21:   */     
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:35 */     getDebuggingPanel().addRight(this.runWorkbenchTest);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public LocalProcessor getLocalProcessor()
/* 31:   */   {
/* 32:44 */     if (this.localProcessor == null) {
/* 33:45 */       this.localProcessor = new LocalProcessor();
/* 34:   */     }
/* 35:47 */     return this.localProcessor;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static void main(String[] args)
/* 39:   */   {
/* 40:56 */     LocalGenesis myGenesis = new LocalGenesis();
/* 41:57 */     myGenesis.startInFrame();
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     joshHaimson.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */