/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ import java.awt.event.ActionEvent;
/*  4:   */ import java.awt.event.ActionListener;
/*  5:   */ import javax.swing.JButton;
/*  6:   */ 
/*  7:   */ public class JButtonBox
/*  8:   */   extends JButton
/*  9:   */   implements WiredBox, ActionListener
/* 10:   */ {
/* 11:   */   private static final long serialVersionUID = -4407622739728274352L;
/* 12:   */   public static final String ENABLE = "enable";
/* 13:   */   public static final String VISIBLE = "visible";
/* 14:23 */   public static String output = "pushed";
/* 15:   */   
/* 16:   */   public JButtonBox(String string)
/* 17:   */   {
/* 18:26 */     super(string);
/* 19:27 */     addActionListener(this);
/* 20:28 */     Connections.getPorts(this).addSignalProcessor("enable", "enable");
/* 21:29 */     Connections.getPorts(this).addSignalProcessor("visible", "visible");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void enable(Object object)
/* 25:   */   {
/* 26:34 */     if (object == Boolean.TRUE) {
/* 27:35 */       setEnabled(true);
/* 28:37 */     } else if (object == Boolean.FALSE) {
/* 29:38 */       setEnabled(false);
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void visible(Object object)
/* 34:   */   {
/* 35:44 */     if (object == Boolean.TRUE) {
/* 36:45 */       setVisible(true);
/* 37:47 */     } else if (object == Boolean.FALSE) {
/* 38:48 */       setVisible(false);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void actionPerformed(ActionEvent e)
/* 43:   */   {
/* 44:53 */     Connections.getPorts(this).transmit(output);
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.JButtonBox
 * JD-Core Version:    0.7.0.1
 */