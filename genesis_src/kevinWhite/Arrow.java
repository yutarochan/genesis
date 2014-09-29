/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Graphics;
/*   6:    */ import java.awt.Graphics2D;
/*   7:    */ import java.awt.Point;
/*   8:    */ import java.awt.Polygon;
/*   9:    */ import java.awt.geom.AffineTransform;
/*  10:    */ import javax.swing.JFrame;
/*  11:    */ import javax.swing.JPanel;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class Arrow
/*  15:    */   extends JPanel
/*  16:    */ {
/*  17:    */   private Polygon arrow;
/*  18: 19 */   private boolean isDesirable = true;
/*  19:    */   private Point center;
/*  20:    */   
/*  21:    */   public void paintComponent(Graphics g)
/*  22:    */   {
/*  23: 23 */     super.paintComponent(g);
/*  24: 24 */     int width = getWidth();
/*  25: 25 */     int height = getHeight();
/*  26: 26 */     initArrow(width, height);
/*  27: 27 */     Graphics2D reverse = (Graphics2D)g;
/*  28: 28 */     AffineTransform original = reverse.getTransform();
/*  29: 29 */     Mark.say(new Object[] {"Displayable:", Boolean.valueOf(isVisible()), "Desirable:", Boolean.valueOf(this.isDesirable) });
/*  30: 30 */     if (isVisible()) {
/*  31: 31 */       if (!this.isDesirable)
/*  32:    */       {
/*  33: 32 */         reverse.rotate(Math.toRadians(180.0D), this.center.x, this.center.y);
/*  34: 33 */         reverse.drawPolygon(this.arrow);
/*  35: 34 */         reverse.fillPolygon(this.arrow);
/*  36:    */       }
/*  37:    */       else
/*  38:    */       {
/*  39: 37 */         g.drawPolygon(this.arrow);
/*  40: 38 */         g.fillPolygon(this.arrow);
/*  41:    */       }
/*  42:    */     }
/*  43: 41 */     reverse.setTransform(original);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Arrow(boolean isDesired)
/*  47:    */   {
/*  48: 50 */     setPreferredSize(new Dimension(200, 200));
/*  49: 51 */     setBackground(Color.WHITE);
/*  50: 52 */     this.isDesirable = isDesired;
/*  51: 53 */     this.center = new Point(40, 40);
/*  52:    */     
/*  53: 55 */     this.arrow = new Polygon(new int[] { 0, 60, 60, 80, 60, 60 }, new int[] { 30, 30, 0, 40, 80, 50, 50 }, 7);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Arrow()
/*  57:    */   {
/*  58: 62 */     setBackground(Color.WHITE);
/*  59: 63 */     this.center = new Point(40, 40);
/*  60:    */     
/*  61: 65 */     this.arrow = new Polygon(new int[] { 0, 60, 60, 80, 60, 60 }, new int[] { 30, 30, 0, 40, 80, 50, 50 }, 7);
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void initArrow(int width, int height)
/*  65:    */   {
/*  66: 74 */     this.arrow = new Polygon(new int[] { width / 8, width * 5 / 8, width * 5 / 8, width * 7 / 8, width * 5 / 8, width * 5 / 8, width / 8 }, 
/*  67: 75 */       new int[] { height * 3 / 8, height * 3 / 8, height * 1 / 4, height * 1 / 2, height * 3 / 4, height * 5 / 8, height * 5 / 8 }, 7);
/*  68: 76 */     this.center = new Point(width / 2, height / 2);
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected void changeDirection(boolean desire)
/*  72:    */   {
/*  73: 84 */     this.isDesirable = desire;
/*  74: 85 */     repaint();
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected Polygon getArrow()
/*  78:    */   {
/*  79: 89 */     return this.arrow;
/*  80:    */   }
/*  81:    */   
/*  82:    */   protected Point getCenter()
/*  83:    */   {
/*  84: 93 */     return this.center;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static void main(String[] args)
/*  88:    */   {
/*  89:100 */     JFrame desirable = new JFrame("Arrow");
/*  90:101 */     desirable.setDefaultCloseOperation(3);
/*  91:102 */     Arrow arrow = new Arrow(true);
/*  92:103 */     arrow.setVisible(true);
/*  93:104 */     desirable.add(arrow);
/*  94:105 */     desirable.pack();
/*  95:106 */     desirable.setVisible(true);
/*  96:    */     
/*  97:108 */     JFrame undesirable = new JFrame("Reverse Arrow");
/*  98:109 */     undesirable.setDefaultCloseOperation(3);
/*  99:110 */     Arrow revArrow = new Arrow(false);
/* 100:111 */     revArrow.setVisible(true);
/* 101:112 */     undesirable.add(revArrow);
/* 102:113 */     undesirable.pack();
/* 103:114 */     undesirable.setVisible(true);
/* 104:    */     
/* 105:116 */     JFrame invalid = new JFrame("Invalid Arrow");
/* 106:117 */     invalid.setDefaultCloseOperation(3);
/* 107:118 */     Arrow badCenter = new Arrow(false);
/* 108:119 */     invalid.add(badCenter);
/* 109:120 */     invalid.pack();
/* 110:121 */     invalid.setVisible(true);
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.Arrow
 * JD-Core Version:    0.7.0.1
 */