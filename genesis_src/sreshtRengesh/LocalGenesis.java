/*  1:   */ package sreshtRengesh;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import genesis.Genesis;
/*  5:   */ import utils.Mark;
/*  6:   */ 
/*  7:   */ public class LocalGenesis
/*  8:   */   extends Genesis
/*  9:   */ {
/* 10:   */   LocalProcessor localProcessor;
/* 11:18 */   public static String STORY = "Story";
/* 12:20 */   public static String METAPHOR = "Metaphor";
/* 13:   */   
/* 14:   */   public LocalGenesis()
/* 15:   */   {
/* 16:24 */     Mark.say(new Object[] {"Sresht's local constructor" });
/* 17:   */     
/* 18:26 */     Connections.wire("complete story analysis port", getMentalModel1(), STORY, getLocalProcessor());
/* 19:27 */     Connections.wire("complete story analysis port", getMentalModel2(), METAPHOR, getLocalProcessor());
/* 20:   */   }
/* 21:   */   
/* 22:   */   public LocalProcessor getLocalProcessor()
/* 23:   */   {
/* 24:34 */     if (this.localProcessor == null) {
/* 25:35 */       this.localProcessor = new LocalProcessor();
/* 26:   */     }
/* 27:37 */     return this.localProcessor;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static void main(String[] args)
/* 31:   */   {
/* 32:42 */     LocalGenesis myGenesis = new LocalGenesis();
/* 33:43 */     myGenesis.startInFrame();
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     sreshtRengesh.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */