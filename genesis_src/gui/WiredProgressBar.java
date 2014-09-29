/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredBox;
/*  7:   */ import javax.swing.JProgressBar;
/*  8:   */ 
/*  9:   */ public class WiredProgressBar
/* 10:   */   extends JProgressBar
/* 11:   */   implements WiredBox
/* 12:   */ {
/* 13:   */   public WiredProgressBar()
/* 14:   */   {
/* 15:16 */     setMinimum(0);
/* 16:17 */     setStringPainted(true);
/* 17:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void process(Object o)
/* 21:   */   {
/* 22:22 */     if (!(o instanceof BetterSignal)) {
/* 23:23 */       return;
/* 24:   */     }
/* 25:25 */     BetterSignal signal = (BetterSignal)o;
/* 26:   */     
/* 27:27 */     setMaximum(((Integer)signal.get(1, Integer.class)).intValue());
/* 28:28 */     setValue(((Integer)signal.get(0, Integer.class)).intValue());
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.WiredProgressBar
 * JD-Core Version:    0.7.0.1
 */