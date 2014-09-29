/*  1:   */ package alexanderCherian;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import genesis.Genesis;
/*  5:   */ import utils.Mark;
/*  6:   */ 
/*  7:   */ public class LocalGenesis
/*  8:   */   extends Genesis
/*  9:   */ {
/* 10:   */   LocalProcessor localProcessor;
/* 11:   */   Sample sample;
/* 12:   */   
/* 13:   */   public LocalGenesis()
/* 14:   */   {
/* 15:25 */     Mark.say(new Object[] {"Local constructor" });
/* 16:   */     
/* 17:   */ 
/* 18:   */ 
/* 19:   */ 
/* 20:30 */     Connections.wire("complete story analysis port", getMentalModel1(), "complete story analysis port", getLocalProcessor());
/* 21:   */     
/* 22:   */ 
/* 23:   */ 
/* 24:   */ 
/* 25:35 */     Connections.wire("my output port", getLocalProcessor(), "my input", getSample());
/* 26:   */   }
/* 27:   */   
/* 28:   */   public LocalProcessor getLocalProcessor()
/* 29:   */   {
/* 30:43 */     if (this.localProcessor == null) {
/* 31:44 */       this.localProcessor = new LocalProcessor();
/* 32:   */     }
/* 33:46 */     return this.localProcessor;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Sample getSample()
/* 37:   */   {
/* 38:50 */     if (this.sample == null) {
/* 39:51 */       this.sample = new Sample();
/* 40:   */     }
/* 41:53 */     return this.sample;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static void main(String[] args)
/* 45:   */   {
/* 46:62 */     LocalGenesis myGenesis = new LocalGenesis();
/* 47:63 */     myGenesis.startInFrame();
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     alexanderCherian.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */