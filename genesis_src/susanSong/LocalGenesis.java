/*  1:   */ package susanSong;
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
/* 14:21 */     Mark.say(new Object[] {"Susan's Genesis's constructor" });
/* 15:   */     
/* 16:23 */     Connections.wire("complete story events port", getMentalModel1(), getLocalProcessor());
/* 17:   */     
/* 18:25 */     Connections.wire("Reflection analysis port", getMentalModel1(), "Reflection analysis port", getLocalProcessor());
/* 19:   */     
/* 20:   */ 
/* 21:28 */     Connections.disconnect(getMentalModel1(), getLocalProcessor());
/* 22:   */   }
/* 23:   */   
/* 24:   */   public LocalProcessor getLocalProcessor()
/* 25:   */   {
/* 26:32 */     if (this.localProcessor == null) {
/* 27:33 */       this.localProcessor = new LocalProcessor();
/* 28:   */     }
/* 29:35 */     return this.localProcessor;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void main(String[] args)
/* 33:   */   {
/* 34:40 */     LocalGenesis myGenesis = new LocalGenesis();
/* 35:41 */     myGenesis.startInFrame();
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     susanSong.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */