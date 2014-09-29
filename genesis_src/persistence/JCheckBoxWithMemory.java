/*  1:   */ package persistence;
/*  2:   */ 
/*  3:   */ import java.awt.event.ItemEvent;
/*  4:   */ import java.awt.event.ItemListener;
/*  5:   */ import java.util.prefs.Preferences;
/*  6:   */ import javax.swing.JCheckBox;
/*  7:   */ 
/*  8:   */ public class JCheckBoxWithMemory
/*  9:   */   extends JCheckBox
/* 10:   */ {
/* 11:   */   public JCheckBoxWithMemory(String name)
/* 12:   */   {
/* 13:17 */     this(name, true);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public JCheckBoxWithMemory(String name, boolean state)
/* 17:   */   {
/* 18:21 */     super(name);
/* 19:22 */     setOpaque(false);
/* 20:23 */     addItemListener(new CheckBoxListener(this));
/* 21:   */     
/* 22:25 */     setSelected(Preferences.userRoot().getBoolean(name, state));
/* 23:   */   }
/* 24:   */   
/* 25:   */   class CheckBoxListener
/* 26:   */     implements ItemListener
/* 27:   */   {
/* 28:   */     JCheckBoxWithMemory button;
/* 29:   */     
/* 30:   */     public CheckBoxListener(JCheckBoxWithMemory button)
/* 31:   */     {
/* 32:33 */       this.button = button;
/* 33:   */     }
/* 34:   */     
/* 35:   */     public void itemStateChanged(ItemEvent e)
/* 36:   */     {
/* 37:37 */       String name = this.button.getText();
/* 38:   */       
/* 39:39 */       Preferences.userRoot().putBoolean(name, this.button.isSelected());
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     persistence.JCheckBoxWithMemory
 * JD-Core Version:    0.7.0.1
 */