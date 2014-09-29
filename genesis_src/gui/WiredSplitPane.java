/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.awt.Component;
/*  7:   */ import java.awt.Dimension;
/*  8:   */ import javax.swing.JComponent;
/*  9:   */ import javax.swing.JSplitPane;
/* 10:   */ 
/* 11:   */ public class WiredSplitPane
/* 12:   */   extends JSplitPane
/* 13:   */   implements WiredBox
/* 14:   */ {
/* 15:17 */   public static final Object SHOW_LEFT = "Show first perspective only";
/* 16:19 */   public static final Object SHOW_RIGHT = "Show second perspective only";
/* 17:21 */   public static final Object SHOW_BOTH = "Show both perspectives";
/* 18:   */   private JComponent left;
/* 19:   */   private JComponent right;
/* 20:   */   
/* 21:   */   public WiredSplitPane(JComponent left, JComponent right)
/* 22:   */   {
/* 23:28 */     this.left = left;
/* 24:29 */     this.right = right;
/* 25:30 */     setPreferredWidth(left, 1000);
/* 26:31 */     setPreferredWidth(right, 1000);
/* 27:32 */     setMinimumWidth(left, 0);
/* 28:33 */     setMinimumWidth(right, 0);
/* 29:34 */     setMinimumHeight(left, 0);
/* 30:35 */     setMinimumHeight(right, 0);
/* 31:36 */     setLeftComponent(left);
/* 32:37 */     setRightComponent(right);
/* 33:38 */     setOneTouchExpandable(true);
/* 34:39 */     leftOnly();
/* 35:40 */     Connections.getPorts(this).addSignalProcessor("process");
/* 36:   */   }
/* 37:   */   
/* 38:   */   private void reset()
/* 39:   */   {
/* 40:44 */     validate();
/* 41:45 */     repaint();
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void both()
/* 45:   */   {
/* 46:49 */     setResizeWeight(0.5D);
/* 47:50 */     setDividerLocation(0.5D);
/* 48:51 */     reset();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void leftOnly()
/* 52:   */   {
/* 53:55 */     setResizeWeight(1.0D);
/* 54:56 */     setDividerLocation(1.0D);
/* 55:57 */     reset();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void rightOnly()
/* 59:   */   {
/* 60:61 */     setResizeWeight(0.0D);
/* 61:62 */     setDividerLocation(0.0D);
/* 62:63 */     invalidate();
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void process(Object object)
/* 66:   */   {
/* 67:67 */     if (object == SHOW_BOTH) {
/* 68:68 */       both();
/* 69:70 */     } else if (object == SHOW_LEFT) {
/* 70:71 */       leftOnly();
/* 71:73 */     } else if (object == SHOW_RIGHT) {
/* 72:74 */       rightOnly();
/* 73:   */     }
/* 74:   */   }
/* 75:   */   
/* 76:   */   protected void setPreferredWidth(Component c, int w)
/* 77:   */   {
/* 78:79 */     int h = c.getPreferredSize().height;
/* 79:80 */     c.setPreferredSize(new Dimension(w, h));
/* 80:   */   }
/* 81:   */   
/* 82:   */   protected void setPreferredHeight(Component c, int h)
/* 83:   */   {
/* 84:84 */     int w = c.getPreferredSize().width;
/* 85:85 */     c.setPreferredSize(new Dimension(w, h));
/* 86:   */   }
/* 87:   */   
/* 88:   */   protected void setMinimumWidth(Component c, int w)
/* 89:   */   {
/* 90:89 */     int h = c.getMinimumSize().height;
/* 91:90 */     c.setMinimumSize(new Dimension(w, h));
/* 92:   */   }
/* 93:   */   
/* 94:   */   protected void setMinimumHeight(Component c, int h)
/* 95:   */   {
/* 96:94 */     int w = c.getMinimumSize().width;
/* 97:95 */     c.setMinimumSize(new Dimension(w, h));
/* 98:   */   }
/* 99:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.WiredSplitPane
 * JD-Core Version:    0.7.0.1
 */