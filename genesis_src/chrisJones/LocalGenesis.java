/*  1:   */ package chrisJones;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import genesis.Genesis;
/*  5:   */ import utils.Mark;
/*  6:   */ 
/*  7:   */ public class LocalGenesis
/*  8:   */   extends Genesis
/*  9:   */ {
/* 10:   */   LocalProcessor localProcessor;
/* 11:   */   
/* 12:   */   public LocalGenesis()
/* 13:   */   {
/* 14:20 */     Mark.say(new Object[] {"Chris's local constructor" });
/* 15:   */     
/* 16:22 */     Connections.wire("complete story analysis port", getMentalModel1(), "complete story analysis port", getLocalProcessor());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public LocalProcessor getLocalProcessor()
/* 20:   */   {
/* 21:28 */     if (this.localProcessor == null) {
/* 22:29 */       this.localProcessor = new LocalProcessor();
/* 23:   */     }
/* 24:31 */     return this.localProcessor;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static void main(String[] args)
/* 28:   */   {
/* 29:36 */     LocalGenesis myGenesis = new LocalGenesis();
/* 30:37 */     myGenesis.startInFrame();
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     chrisJones.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */