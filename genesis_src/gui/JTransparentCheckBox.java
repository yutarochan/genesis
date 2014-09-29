/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import javax.swing.Action;
/*  4:   */ import javax.swing.Icon;
/*  5:   */ import javax.swing.JCheckBox;
/*  6:   */ 
/*  7:   */ public class JTransparentCheckBox
/*  8:   */   extends JCheckBox
/*  9:   */ {
/* 10:   */   public JTransparentCheckBox()
/* 11:   */   {
/* 12:13 */     setOpaque(false);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public JTransparentCheckBox(Icon icon)
/* 16:   */   {
/* 17:17 */     super(icon);
/* 18:18 */     setOpaque(false);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public JTransparentCheckBox(String text)
/* 22:   */   {
/* 23:22 */     super(text);
/* 24:23 */     setOpaque(false);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public JTransparentCheckBox(Action a)
/* 28:   */   {
/* 29:27 */     super(a);
/* 30:28 */     setOpaque(false);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public JTransparentCheckBox(Icon icon, boolean selected)
/* 34:   */   {
/* 35:32 */     super(icon, selected);
/* 36:33 */     setOpaque(false);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public JTransparentCheckBox(String text, boolean selected)
/* 40:   */   {
/* 41:37 */     super(text, selected);
/* 42:38 */     setOpaque(false);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public JTransparentCheckBox(String text, Icon icon)
/* 46:   */   {
/* 47:42 */     super(text, icon);
/* 48:43 */     setOpaque(false);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public JTransparentCheckBox(String text, Icon icon, boolean selected)
/* 52:   */   {
/* 53:47 */     super(text, icon, selected);
/* 54:48 */     setOpaque(false);
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.JTransparentCheckBox
 * JD-Core Version:    0.7.0.1
 */