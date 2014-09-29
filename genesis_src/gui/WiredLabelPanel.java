/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import javax.swing.JLabel;
/*  6:   */ 
/*  7:   */ public class WiredLabelPanel
/*  8:   */   extends WiredPanel
/*  9:   */ {
/* 10: 8 */   JLabel text = new JLabel();
/* 11:   */   
/* 12:   */   public WiredLabelPanel()
/* 13:   */   {
/* 14:11 */     Connections.getPorts(this).addSignalProcessor("display");
/* 15:12 */     add(this.text);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void display(Object signal)
/* 19:   */   {
/* 20:16 */     this.text.setText(signal.toString());
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.WiredLabelPanel
 * JD-Core Version:    0.7.0.1
 */