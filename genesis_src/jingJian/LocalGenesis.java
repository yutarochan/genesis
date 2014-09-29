/*  1:   */ package jingJian;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import genesis.Genesis;
/*  5:   */ import utils.Mark;
/*  6:   */ 
/*  7:   */ public class LocalGenesis
/*  8:   */   extends Genesis
/*  9:   */ {
/* 10:   */   LocalProcessor localProcessor;
/* 11:   */   SelfReflectionProcessor selfReflectionProcessor1;
/* 12:   */   
/* 13:   */   public LocalGenesis()
/* 14:   */   {
/* 15:21 */     Mark.say(new Object[] {"Jing Jian's constructor" });
/* 16:   */     
/* 17:   */ 
/* 18:   */ 
/* 19:25 */     Connections.wire("new  element port", getMentalModel1(), "Story input port", getSelfReflectionProcessor1());
/* 20:   */     
/* 21:27 */     Mark.say(new Object[] {"Warning, code beyond here has been pruned to remove compile errors!" });
/* 22:   */     
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:33 */     Connections.wire("to completion detector port", getMentalModel1(), "reflection analysis input port", getSelfReflectionProcessor1());
/* 28:   */     
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:40 */     Connections.disconnect(getMentalModel1(), getLocalProcessor());
/* 35:   */   }
/* 36:   */   
/* 37:   */   public SelfReflectionProcessor getSelfReflectionProcessor1()
/* 38:   */   {
/* 39:44 */     if (this.selfReflectionProcessor1 == null) {
/* 40:45 */       this.selfReflectionProcessor1 = new SelfReflectionProcessor();
/* 41:   */     }
/* 42:47 */     return this.selfReflectionProcessor1;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public LocalProcessor getLocalProcessor()
/* 46:   */   {
/* 47:51 */     if (this.localProcessor == null) {
/* 48:52 */       this.localProcessor = new LocalProcessor();
/* 49:   */     }
/* 50:54 */     return this.localProcessor;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static void main(String[] args)
/* 54:   */   {
/* 55:59 */     LocalGenesis myGenesis = new LocalGenesis();
/* 56:60 */     myGenesis.startInFrame();
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     jingJian.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */