/*  1:   */ package connections.views;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.awt.event.ActionEvent;
/*  7:   */ import java.awt.event.ActionListener;
/*  8:   */ import javax.swing.JMenuItem;
/*  9:   */ 
/* 10:   */ public class WiredMenuItem
/* 11:   */   extends JMenuItem
/* 12:   */   implements WiredBox, ActionListener
/* 13:   */ {
/* 14:18 */   private String signal = "Clicked";
/* 15:   */   
/* 16:   */   public WiredMenuItem(String string)
/* 17:   */   {
/* 18:21 */     super(string);
/* 19:22 */     addActionListener(this);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void actionPerformed(ActionEvent e)
/* 23:   */   {
/* 24:27 */     Connections.getPorts(this).transmit(this.signal);
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.views.WiredMenuItem
 * JD-Core Version:    0.7.0.1
 */