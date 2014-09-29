/*   1:    */ package tomLarson;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.awt.BasicStroke;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.FontMetrics;
/*   8:    */ import java.awt.Graphics;
/*   9:    */ import java.awt.Graphics2D;
/*  10:    */ import java.awt.font.TextAttribute;
/*  11:    */ import java.text.AttributedString;
/*  12:    */ import java.util.Set;
/*  13:    */ import javax.swing.JApplet;
/*  14:    */ 
/*  15:    */ public class DisambiguatorViewerHelper
/*  16:    */   extends JApplet
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = 1L;
/*  19:    */   public static final int SCROLL_ALWAYS = 2;
/*  20:    */   public static final int SCROLL_AS_NEEDED = 1;
/*  21:    */   public static final int SCROLL_NEVER = 0;
/*  22:    */   static final int maxCharHeight = 15;
/*  23:    */   static final int minFontSize = 6;
/*  24: 33 */   static final Color bg = Color.white;
/*  25: 34 */   static final Color fg = Color.black;
/*  26: 35 */   static final Color red = Color.red;
/*  27: 36 */   static final Color white = Color.white;
/*  28: 38 */   static final BasicStroke stroke = new BasicStroke(2.0F);
/*  29: 39 */   static final BasicStroke wideStroke = new BasicStroke(8.0F);
/*  30: 41 */   static final float[] dash1 = { 10.0F };
/*  31: 42 */   static final BasicStroke dashed = new BasicStroke(1.0F, 
/*  32: 43 */     0, 
/*  33: 44 */     0, 
/*  34: 45 */     10.0F, dash1, 0.0F);
/*  35:    */   String test;
/*  36:    */   Dimension totalSize;
/*  37:    */   FontMetrics fontMetrics;
/*  38:    */   ThreadTree tree;
/*  39:    */   double sum;
/*  40:    */   
/*  41:    */   public DisambiguatorViewerHelper()
/*  42:    */   {
/*  43: 54 */     init();
/*  44: 55 */     this.tree = null;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void init()
/*  48:    */   {
/*  49: 60 */     setBackground(bg);
/*  50: 61 */     setForeground(fg);
/*  51:    */   }
/*  52:    */   
/*  53:    */   private void display(Node node, Graphics2D g2, int x, int y, int offset)
/*  54:    */   {
/*  55: 67 */     AttributedString s = new AttributedString(node.getName());
/*  56: 68 */     double weight = node.getWeight();
/*  57: 69 */     double ratio = weight / this.sum;
/*  58:    */     int red;
/*  59:    */     int red;
/*  60:    */     int blue;
/*  61: 72 */     if (ratio <= 0.1D)
/*  62:    */     {
/*  63: 73 */       int blue = (int)((0.1D - ratio) * 2550.0D);
/*  64: 74 */       red = 0;
/*  65:    */     }
/*  66:    */     else
/*  67:    */     {
/*  68: 77 */       red = (int)((ratio - 0.1D) * 2550.0D);
/*  69: 78 */       if (red >= 255) {
/*  70: 78 */         red = 255;
/*  71:    */       }
/*  72: 79 */       blue = 0;
/*  73:    */     }
/*  74: 86 */     s.addAttribute(TextAttribute.FOREGROUND, new Color(red, 0, blue));
/*  75: 87 */     g2.drawString(s.getIterator(), x, y);
/*  76:    */     
/*  77: 89 */     int deltay = 20;
/*  78: 90 */     int deltax = 20;
/*  79:    */     
/*  80:    */ 
/*  81: 93 */     Set<Node> children = node.getChildren();
/*  82: 94 */     int numChildren = children.size();
/*  83: 95 */     boolean odd = numChildren % 2 == 1;
/*  84: 96 */     int halfway = numChildren / 2;
/*  85: 97 */     double half = 1.0D * numChildren / 2.0D;
/*  86: 99 */     for (Node child : children)
/*  87:    */     {
/*  88:100 */       int newDeltax = deltax - offset * 3;
/*  89:101 */       int factor = numChildren > half ? 1 : -1;
/*  90:102 */       factor = (numChildren == halfway + 1) && (odd) ? 0 : factor;
/*  91:103 */       double fromMid = factor * numChildren;
/*  92:    */       
/*  93:105 */       int newx = x + (int)fromMid * newDeltax + offset * deltax;
/*  94:106 */       g2.drawLine(x, y, newx, y + deltay);
/*  95:107 */       Dimension d = getSize();
/*  96:108 */       display(child, g2, newx, y + deltay, offset + (int)fromMid);
/*  97:109 */       numChildren--;
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void paint(Graphics g)
/* 102:    */   {
/* 103:114 */     if (this.tree != null)
/* 104:    */     {
/* 105:115 */       Graphics2D g2 = (Graphics2D)g;
/* 106:116 */       Dimension d = getSize();
/* 107:117 */       g2.clearRect(0, 0, d.width, d.height);
/* 108:118 */       int x = d.width / 2;
/* 109:119 */       int y = 10;
/* 110:    */       
/* 111:121 */       Node head = this.tree.getHead();
/* 112:122 */       if (head != null) {
/* 113:123 */         display(head, g2, x, y, 0);
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void run(Entity s) {}
/* 119:    */   
/* 120:    */   public void setInput(ThreadTree t)
/* 121:    */   {
/* 122:144 */     this.tree = t;
/* 123:145 */     if (t.isEmpty()) {
/* 124:145 */       this.sum = 0.0D;
/* 125:    */     } else {
/* 126:146 */       this.sum = getSum(t.getHead());
/* 127:    */     }
/* 128:147 */     repaint();
/* 129:    */   }
/* 130:    */   
/* 131:    */   private double getSum(Node n)
/* 132:    */   {
/* 133:152 */     double sum = n.getWeight();
/* 134:153 */     Set<Node> children = n.getChildren();
/* 135:154 */     for (Node child : children) {
/* 136:155 */       sum += getSum(child);
/* 137:    */     }
/* 138:157 */     return sum;
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.DisambiguatorViewerHelper
 * JD-Core Version:    0.7.0.1
 */