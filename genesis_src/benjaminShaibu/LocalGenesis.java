/*  1:   */ package benjaminShaibu;
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
/* 14:23 */     Mark.say(new Object[] {"Benjamin's local constructor" });
/* 15:   */     
/* 16:   */ 
/* 17:   */ 
/* 18:   */ 
/* 19:28 */     Connections.wire("complete story analysis port", getMentalModel1(), "complete story analysis port", getLocalProcessor());
/* 20:   */   }
/* 21:   */   
/* 22:   */   public LocalProcessor getLocalProcessor()
/* 23:   */   {
/* 24:39 */     if (this.localProcessor == null) {
/* 25:40 */       this.localProcessor = new LocalProcessor();
/* 26:   */     }
/* 27:42 */     return this.localProcessor;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static void main(String[] args)
/* 31:   */   {
/* 32:51 */     LocalGenesis myGenesis = new LocalGenesis();
/* 33:52 */     myGenesis.startInFrame();
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     benjaminShaibu.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */