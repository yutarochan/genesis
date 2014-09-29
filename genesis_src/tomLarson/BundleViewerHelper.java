/*   1:    */ package tomLarson;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.awt.BasicStroke;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.FontMetrics;
/*   8:    */ import java.awt.Graphics;
/*   9:    */ import java.awt.Graphics2D;
/*  10:    */ import java.text.AttributedString;
/*  11:    */ import java.util.Set;
/*  12:    */ import javax.swing.JApplet;
/*  13:    */ 
/*  14:    */ public class BundleViewerHelper
/*  15:    */   extends JApplet
/*  16:    */ {
/*  17:    */   private static final long serialVersionUID = 1L;
/*  18:    */   public static final int SCROLL_ALWAYS = 2;
/*  19:    */   public static final int SCROLL_AS_NEEDED = 1;
/*  20:    */   public static final int SCROLL_NEVER = 0;
/*  21:    */   static final int maxCharHeight = 15;
/*  22:    */   static final int minFontSize = 6;
/*  23: 33 */   static final Color bg = Color.white;
/*  24: 34 */   static final Color fg = Color.black;
/*  25: 35 */   static final Color red = Color.red;
/*  26: 36 */   static final Color white = Color.white;
/*  27: 38 */   static final BasicStroke stroke = new BasicStroke(2.0F);
/*  28: 39 */   static final BasicStroke wideStroke = new BasicStroke(8.0F);
/*  29: 41 */   static final float[] dash1 = { 10.0F };
/*  30: 42 */   static final BasicStroke dashed = new BasicStroke(1.0F, 
/*  31: 43 */     0, 
/*  32: 44 */     0, 
/*  33: 45 */     10.0F, dash1, 0.0F);
/*  34:    */   String test;
/*  35:    */   Dimension totalSize;
/*  36:    */   FontMetrics fontMetrics;
/*  37:    */   ThreadTree tree;
/*  38:    */   double sum;
/*  39:    */   
/*  40:    */   public BundleViewerHelper()
/*  41:    */   {
/*  42: 54 */     init();
/*  43: 55 */     this.tree = null;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void init()
/*  47:    */   {
/*  48: 60 */     setBackground(bg);
/*  49: 61 */     setForeground(fg);
/*  50:    */   }
/*  51:    */   
/*  52:    */   private void display(Node node, Graphics2D g2, int x, int y, int offset)
/*  53:    */   {
/*  54: 67 */     AttributedString s = new AttributedString(node.getName());
/*  55: 68 */     g2.drawString(s.getIterator(), x, y);
/*  56:    */     
/*  57: 70 */     int deltay = 20;
/*  58: 71 */     int deltax = 20;
/*  59:    */     
/*  60:    */ 
/*  61: 74 */     Set<Node> children = node.getChildren();
/*  62: 75 */     int numChildren = children.size();
/*  63: 76 */     boolean odd = numChildren % 2 == 1;
/*  64: 77 */     int halfway = numChildren / 2;
/*  65: 78 */     double half = 1.0D * numChildren / 2.0D;
/*  66: 80 */     for (Node child : children)
/*  67:    */     {
/*  68: 81 */       int newDeltax = deltax - offset * 3;
/*  69: 82 */       int factor = numChildren > half ? 1 : -1;
/*  70: 83 */       factor = (numChildren == halfway + 1) && (odd) ? 0 : factor;
/*  71: 84 */       double fromMid = factor * numChildren;
/*  72:    */       
/*  73: 86 */       int newx = x + (int)fromMid * newDeltax + offset * deltax;
/*  74: 87 */       g2.drawLine(x, y, newx, y + deltay);
/*  75: 88 */       Dimension d = getSize();
/*  76: 89 */       display(child, g2, newx, y + deltay, offset + (int)fromMid);
/*  77: 90 */       numChildren--;
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void paint(Graphics g)
/*  82:    */   {
/*  83: 95 */     if (this.tree != null)
/*  84:    */     {
/*  85: 96 */       Graphics2D g2 = (Graphics2D)g;
/*  86: 97 */       Dimension d = getSize();
/*  87: 98 */       g2.clearRect(0, 0, d.width, d.height);
/*  88: 99 */       int x = d.width / 2;
/*  89:100 */       int y = 10;
/*  90:    */       
/*  91:102 */       Node head = this.tree.getHead();
/*  92:103 */       if (head != null) {
/*  93:104 */         display(head, g2, x, y, 0);
/*  94:    */       }
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void run(Entity s) {}
/*  99:    */   
/* 100:    */   public void setInput(ThreadTree t)
/* 101:    */   {
/* 102:125 */     this.tree = t;
/* 103:126 */     if (t.isEmpty()) {
/* 104:126 */       this.sum = 0.0D;
/* 105:    */     } else {
/* 106:127 */       this.sum = getSum(t.getHead());
/* 107:    */     }
/* 108:128 */     repaint();
/* 109:    */   }
/* 110:    */   
/* 111:    */   private double getSum(Node n)
/* 112:    */   {
/* 113:133 */     double sum = n.getWeight();
/* 114:134 */     Set<Node> children = n.getChildren();
/* 115:135 */     for (Node child : children) {
/* 116:136 */       sum += getSum(child);
/* 117:    */     }
/* 118:138 */     return sum;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.BundleViewerHelper
 * JD-Core Version:    0.7.0.1
 */