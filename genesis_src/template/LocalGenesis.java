/*  1:   */ package template;
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
/* 14:21 */     Mark.say(new Object[] {"Student's constructor" });
/* 15:   */     
/* 16:23 */     Connections.wire("complete story events port", getMentalModel1(), getLocalProcessor());
/* 17:   */   }
/* 18:   */   
/* 19:   */   public LocalProcessor getLocalProcessor()
/* 20:   */   {
/* 21:29 */     if (this.localProcessor == null) {
/* 22:30 */       this.localProcessor = new LocalProcessor();
/* 23:   */     }
/* 24:32 */     return this.localProcessor;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static void main(String[] args)
/* 28:   */   {
/* 29:37 */     LocalGenesis myGenesis = new LocalGenesis();
/* 30:38 */     myGenesis.startInFrame();
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     template.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */