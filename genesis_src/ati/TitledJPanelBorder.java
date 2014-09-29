/*  1:   */ package ati;
/*  2:   */ 
/*  3:   */ import java.awt.Color;
/*  4:   */ import java.awt.Component;
/*  5:   */ import java.awt.Graphics;
/*  6:   */ import java.awt.Insets;
/*  7:   */ import javax.swing.border.BevelBorder;
/*  8:   */ 
/*  9:   */ public class TitledJPanelBorder
/* 10:   */   extends BevelBorder
/* 11:   */ {
/* 12:   */   public TitledJPanelBorder()
/* 13:   */   {
/* 14:18 */     super(0);
/* 15:   */   }
/* 16:   */   
/* 17:   */   protected void paintRaisedBevel(Component c, Graphics g, int x, int y, int width, int height)
/* 18:   */   {
/* 19:22 */     Color oldColor = g.getColor();
/* 20:23 */     int h = height;
/* 21:24 */     int w = width;
/* 22:   */     
/* 23:26 */     g.translate(x, y);
/* 24:   */     
/* 25:28 */     g.setColor(getHighlightOuterColor(c));
/* 26:31 */     if (this.l != 0) {
/* 27:31 */       g.drawLine(0, 0, 0, h - 2);
/* 28:   */     }
/* 29:33 */     if (this.t != 0) {
/* 30:33 */       g.drawLine(1, 0, w - 2, 0);
/* 31:   */     }
/* 32:35 */     g.setColor(getHighlightInnerColor(c));
/* 33:38 */     if (this.l != 0) {
/* 34:38 */       g.drawLine(1, 1, 1, h - 3);
/* 35:   */     }
/* 36:40 */     if (this.t != 0) {
/* 37:40 */       g.drawLine(2, 1, w - 3, 1);
/* 38:   */     }
/* 39:42 */     g.setColor(getShadowOuterColor(c));
/* 40:45 */     if (this.b != 0) {
/* 41:45 */       g.drawLine(0, h - 1, w - 1, h - 1);
/* 42:   */     }
/* 43:47 */     if (this.r != 0) {
/* 44:47 */       g.drawLine(w - 1, 0, w - 1, h - 2);
/* 45:   */     }
/* 46:49 */     g.setColor(getShadowInnerColor(c));
/* 47:52 */     if (this.b != 0) {
/* 48:52 */       g.drawLine(1, h - 2, w - 2, h - 2);
/* 49:   */     }
/* 50:54 */     if (this.r != 0) {
/* 51:54 */       g.drawLine(w - 2, 1, w - 2, h - 3);
/* 52:   */     }
/* 53:56 */     g.translate(-x, -y);
/* 54:57 */     g.setColor(oldColor);
/* 55:   */   }
/* 56:   */   
/* 57:61 */   int b = 2;
/* 58:61 */   int t = 2;
/* 59:61 */   int r = 2;
/* 60:61 */   int l = 2;
/* 61:   */   private static final long serialVersionUID = 1L;
/* 62:   */   
/* 63:   */   public Insets getBorderInsets(Component c)
/* 64:   */   {
/* 65:63 */     return new Insets(this.t, this.l, this.b, this.r);
/* 66:   */   }
/* 67:   */   
/* 68:   */   public Insets getBorderInsets(Component c, Insets insets)
/* 69:   */   {
/* 70:71 */     insets.left = this.l;insets.top = this.t;insets.right = this.r;insets.bottom = this.b;
/* 71:72 */     return insets;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public void includeBorders(String border)
/* 75:   */   {
/* 76:76 */     this.l = (this.r = this.t = this.b = 0);
/* 77:77 */     if ((border.indexOf('l') >= 0) || (border.indexOf('L') >= 0)) {
/* 78:77 */       this.l = 2;
/* 79:   */     }
/* 80:78 */     if ((border.indexOf('r') >= 0) || (border.indexOf('R') >= 0)) {
/* 81:78 */       this.r = 2;
/* 82:   */     }
/* 83:79 */     if ((border.indexOf('t') >= 0) || (border.indexOf('T') >= 0)) {
/* 84:79 */       this.t = 2;
/* 85:   */     }
/* 86:80 */     if ((border.indexOf('b') >= 0) || (border.indexOf('B') >= 0)) {
/* 87:80 */       this.b = 2;
/* 88:   */     }
/* 89:   */   }
/* 90:   */   
/* 91:   */   public void setBorders(String border)
/* 92:   */   {
/* 93:85 */     if ((border.indexOf('l') >= 0) || (border.indexOf('L') >= 0)) {
/* 94:85 */       this.l = 0;
/* 95:   */     }
/* 96:86 */     if ((border.indexOf('r') >= 0) || (border.indexOf('R') >= 0)) {
/* 97:86 */       this.r = 0;
/* 98:   */     }
/* 99:87 */     if ((border.indexOf('t') >= 0) || (border.indexOf('T') >= 0)) {
/* :0:87 */       this.t = 0;
/* :1:   */     }
/* :2:88 */     if ((border.indexOf('b') >= 0) || (border.indexOf('B') >= 0)) {
/* :3:88 */       this.b = 0;
/* :4:   */     }
/* :5:   */   }
/* :6:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ati.TitledJPanelBorder
 * JD-Core Version:    0.7.0.1
 */