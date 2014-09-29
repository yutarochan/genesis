/*  1:   */ package genesis;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ 
/*  7:   */ public class SourceDistributor
/*  8:   */   extends AbstractWiredBox
/*  9:   */ {
/* 10:   */   public static final String CONTROL = "control";
/* 11:   */   public static final String CASE = "case";
/* 12:   */   public static final String REFLECTIVE_KNOWLEDGE = "reflex";
/* 13:   */   public static final String COMMONSENSE_KNOWLEDGE = "reflect";
/* 14:   */   public static final String GENERAL_KNOWLEDGE = "general";
/* 15:24 */   String mode = "general";
/* 16:   */   
/* 17:   */   public SourceDistributor()
/* 18:   */   {
/* 19:27 */     Connections.getPorts(this).addSignalProcessor("control", "switchMode");
/* 20:28 */     Connections.getPorts(this).addSignalProcessor("process");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void switchMode(Object o)
/* 24:   */   {
/* 25:32 */     if (this.mode != o) {
/* 26:35 */       if (o == "case")
/* 27:   */       {
/* 28:36 */         this.mode = "case";
/* 29:37 */         Connections.getPorts(this).transmit("switch tab", "Story");
/* 30:   */       }
/* 31:39 */       else if (o == "reflect")
/* 32:   */       {
/* 33:40 */         this.mode = "reflect";
/* 34:   */       }
/* 35:42 */       else if (o == "reflex")
/* 36:   */       {
/* 37:43 */         this.mode = "reflex";
/* 38:   */       }
/* 39:45 */       else if (o == "general")
/* 40:   */       {
/* 41:46 */         this.mode = "general";
/* 42:   */       }
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void process(Object o)
/* 47:   */   {
/* 48:51 */     if (this.mode == "case") {
/* 49:52 */       Connections.getPorts(this).transmit("case", o);
/* 50:54 */     } else if (this.mode == "reflect") {
/* 51:55 */       Connections.getPorts(this).transmit("reflect", o);
/* 52:57 */     } else if (this.mode == "reflex") {
/* 53:58 */       Connections.getPorts(this).transmit("reflex", o);
/* 54:60 */     } else if (this.mode == "general") {
/* 55:61 */       Connections.getPorts(this).transmit("general", o);
/* 56:   */     }
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.SourceDistributor
 * JD-Core Version:    0.7.0.1
 */