/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.awt.event.KeyEvent;
/*  7:   */ import java.awt.event.KeyListener;
/*  8:   */ import javax.swing.JTextField;
/*  9:   */ 
/* 10:   */ public class WiredTextField
/* 11:   */   extends JTextField
/* 12:   */   implements WiredBox, KeyListener
/* 13:   */ {
/* 14:   */   public WiredTextField()
/* 15:   */   {
/* 16:16 */     addKeyListener(this);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void keyPressed(KeyEvent e) {}
/* 20:   */   
/* 21:   */   public void keyReleased(KeyEvent e) {}
/* 22:   */   
/* 23:   */   public void keyTyped(KeyEvent e)
/* 24:   */   {
/* 25:28 */     char c = e.getKeyChar();
/* 26:29 */     String text = getText() + c;
/* 27:35 */     if ((c == '.') || (c == '?') || (c == '!') || (KeyEvent.getKeyText(c).equalsIgnoreCase("enter"))) {
/* 28:36 */       Connections.getPorts(this).transmit(stripPunctuation(text));
/* 29:   */     }
/* 30:39 */     if (KeyEvent.getKeyText(c).equalsIgnoreCase("escape")) {
/* 31:40 */       setText("");
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   private String stripPunctuation(String text)
/* 36:   */   {
/* 37:45 */     if (text.isEmpty()) {
/* 38:46 */       return text;
/* 39:   */     }
/* 40:48 */     if (".?!*".indexOf(text.charAt(text.length() - 1)) >= 0) {
/* 41:49 */       return stripPunctuation(text.substring(0, text.length() - 1));
/* 42:   */     }
/* 43:51 */     return text;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void stimulate(String s)
/* 47:   */   {
/* 48:55 */     setText(s);
/* 49:56 */     Connections.getPorts(this).transmit(stripPunctuation(s));
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.WiredTextField
 * JD-Core Version:    0.7.0.1
 */