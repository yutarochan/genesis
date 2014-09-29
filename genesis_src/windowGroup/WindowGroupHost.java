/*  1:   */ package windowGroup;
/*  2:   */ 
/*  3:   */ import java.awt.BorderLayout;
/*  4:   */ import java.awt.Color;
/*  5:   */ import javax.swing.JComponent;
/*  6:   */ import javax.swing.JLabel;
/*  7:   */ import javax.swing.JMenuBar;
/*  8:   */ import javax.swing.JPanel;
/*  9:   */ 
/* 10:   */ public class WindowGroupHost
/* 11:   */   extends JPanel
/* 12:   */ {
/* 13:15 */   private JMenuBar bar = null;
/* 14:17 */   private JComponent guts = null;
/* 15:19 */   private String title = null;
/* 16:   */   
/* 17:   */   public WindowGroupHost()
/* 18:   */   {
/* 19:23 */     setLayout(new BorderLayout());
/* 20:24 */     setBackground(Color.WHITE);
/* 21:25 */     setOpaque(true);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public JComponent getGuts()
/* 25:   */   {
/* 26:29 */     return this.guts;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void setGuts(JComponent guts)
/* 30:   */   {
/* 31:33 */     this.guts = guts;
/* 32:34 */     this.guts.setBackground(Color.WHITE);
/* 33:35 */     this.guts.setOpaque(true);
/* 34:36 */     refresh();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public JMenuBar getMenuBar()
/* 38:   */   {
/* 39:40 */     return this.bar;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setMenuBar(JMenuBar menu)
/* 43:   */   {
/* 44:44 */     this.bar = menu;
/* 45:45 */     refresh();
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getTitle()
/* 49:   */   {
/* 50:50 */     return this.title;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public void setTitle(String title)
/* 54:   */   {
/* 55:54 */     this.title = title;
/* 56:55 */     refresh();
/* 57:   */   }
/* 58:   */   
/* 59:   */   private void refresh()
/* 60:   */   {
/* 61:59 */     removeAll();
/* 62:60 */     if (getMenuBar() != null) {
/* 63:61 */       add(getMenuBar(), "North");
/* 64:   */     }
/* 65:63 */     if (getTitle() != null) {
/* 66:64 */       add(new JLabel(getTitle()), "South");
/* 67:   */     }
/* 68:66 */     if (this.guts != null) {
/* 69:67 */       add(this.guts, "Center");
/* 70:   */     }
/* 71:69 */     revalidate();
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     windowGroup.WindowGroupHost
 * JD-Core Version:    0.7.0.1
 */