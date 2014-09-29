/*  1:   */ package johnMallery;
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
/* 14:20 */     Mark.say(new Object[] {"JCMA's Genesis's constructor" });
/* 15:   */     
/* 16:22 */     Connections.wire("complete story events port", getMentalModel1(), getLocalProcessor());
/* 17:   */     
/* 18:24 */     Connections.disconnect(getMentalModel1(), getLocalProcessor());
/* 19:   */   }
/* 20:   */   
/* 21:   */   public LocalProcessor getLocalProcessor()
/* 22:   */   {
/* 23:28 */     if (this.localProcessor == null) {
/* 24:29 */       this.localProcessor = new LocalProcessor();
/* 25:   */     }
/* 26:31 */     return this.localProcessor;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public static void main(String[] args)
/* 30:   */   {
/* 31:36 */     LocalGenesis myGenesis = new LocalGenesis();
/* 32:37 */     myGenesis.startInFrame();
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     johnMallery.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */