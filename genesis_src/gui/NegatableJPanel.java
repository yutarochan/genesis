/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.WiredViewer;
/*  5:   */ import java.awt.BasicStroke;
/*  6:   */ import java.awt.Color;
/*  7:   */ import java.awt.Graphics;
/*  8:   */ import java.awt.Graphics2D;
/*  9:   */ import java.io.PrintStream;
/* 10:   */ 
/* 11:   */ public class NegatableJPanel
/* 12:   */   extends WiredViewer
/* 13:   */ {
/* 14:16 */   private boolean negated = false;
/* 15:   */   
/* 16:   */   public boolean isNegated()
/* 17:   */   {
/* 18:19 */     return this.negated;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setNegated(boolean negated)
/* 22:   */   {
/* 23:23 */     this.negated = negated;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected void setTruthValue(Object object)
/* 27:   */   {
/* 28:28 */     if ((object instanceof Entity))
/* 29:   */     {
/* 30:29 */       Entity signal = (Entity)object;
/* 31:30 */       if (signal.hasFeature("not"))
/* 32:   */       {
/* 33:31 */         setNegated(true);
/* 34:32 */         return;
/* 35:   */       }
/* 36:   */     }
/* 37:36 */     setNegated(false);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void paintComponent(Graphics graphics)
/* 41:   */   {
/* 42:40 */     super.paintComponent(graphics);
/* 43:41 */     if (isNegated())
/* 44:   */     {
/* 45:42 */       Graphics2D g = (Graphics2D)graphics;
/* 46:43 */       int h = getHeight();
/* 47:44 */       int w = getWidth();
/* 48:45 */       int d = w / 10;
/* 49:   */       
/* 50:47 */       int offset = 3 * d / 2;
/* 51:   */       
/* 52:49 */       int delta = (int)(d * (1.0D - Math.cos(0.7853981633974483D)) / 2.0D);
/* 53:50 */       Color handle = g.getColor();
/* 54:51 */       g.setColor(Color.RED);
/* 55:52 */       g.setStroke(new BasicStroke(2.0F));
/* 56:53 */       g.drawLine(w - offset + d - delta, h - offset + delta, w - offset + delta, h - offset + d - delta);
/* 57:54 */       g.setStroke(new BasicStroke(3.0F));
/* 58:55 */       g.drawOval(w - offset, h - offset, d, d);
/* 59:56 */       g.setColor(handle);
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void view(Object object)
/* 64:   */   {
/* 65:62 */     System.err.println("NegatableJPanel.view should not be called");
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.NegatableJPanel
 * JD-Core Version:    0.7.0.1
 */