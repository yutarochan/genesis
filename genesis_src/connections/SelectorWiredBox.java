/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ import javax.swing.AbstractButton;
/*  4:   */ 
/*  5:   */ public class SelectorWiredBox
/*  6:   */   extends AbstractWiredBox
/*  7:   */ {
/*  8:   */   public static final String A = "portA";
/*  9:   */   public static final String B = "portB";
/* 10:   */   public static final String C = "portC";
/* 11:   */   private AbstractButton buttonA;
/* 12:   */   private AbstractButton buttonB;
/* 13:   */   private AbstractButton buttonC;
/* 14:   */   
/* 15:   */   public SelectorWiredBox()
/* 16:   */   {
/* 17:19 */     Connections.getPorts(this).addSignalProcessor("portA", "processA");
/* 18:20 */     Connections.getPorts(this).addSignalProcessor("portB", "processB");
/* 19:21 */     Connections.getPorts(this).addSignalProcessor("portC", "processC");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void addButtonA(AbstractButton b)
/* 23:   */   {
/* 24:24 */     this.buttonA = b;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void addButtonB(AbstractButton b)
/* 28:   */   {
/* 29:28 */     this.buttonB = b;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void addButtonC(AbstractButton b)
/* 33:   */   {
/* 34:31 */     this.buttonC = b;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void processA(Object signal)
/* 38:   */   {
/* 39:35 */     if ((this.buttonA != null) && (this.buttonA.isSelected())) {
/* 40:36 */       Connections.getPorts(this).transmit(signal);
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void processB(Object signal)
/* 45:   */   {
/* 46:40 */     if ((this.buttonB != null) && (this.buttonB.isSelected())) {
/* 47:41 */       Connections.getPorts(this).transmit(signal);
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void processC(Object signal)
/* 52:   */   {
/* 53:45 */     if ((this.buttonC != null) && (this.buttonC.isSelected())) {
/* 54:46 */       Connections.getPorts(this).transmit(signal);
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.SelectorWiredBox
 * JD-Core Version:    0.7.0.1
 */