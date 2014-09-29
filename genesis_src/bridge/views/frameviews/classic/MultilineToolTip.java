/*  1:   */ package bridge.views.frameviews.classic;
/*  2:   */ 
/*  3:   */ import java.awt.Dimension;
/*  4:   */ import java.awt.Font;
/*  5:   */ import java.awt.Graphics;
/*  6:   */ import java.awt.Rectangle;
/*  7:   */ import java.awt.font.FontRenderContext;
/*  8:   */ import java.awt.font.TextLayout;
/*  9:   */ import java.util.StringTokenizer;
/* 10:   */ import javax.swing.JToolTip;
/* 11:   */ 
/* 12:   */ public class MultilineToolTip
/* 13:   */   extends JToolTip
/* 14:   */ {
/* 15:   */   public void setTipText(String tipText)
/* 16:   */   {
/* 17:14 */     super.setTipText(tipText);
/* 18:   */     
/* 19:   */ 
/* 20:17 */     Font f = getFont();
/* 21:   */     
/* 22:19 */     StringTokenizer st = new StringTokenizer(getTipText(), "\n\r\f");
/* 23:20 */     int height = f.getSize() * (st.countTokens() + 1);
/* 24:21 */     int width = 0;
/* 25:22 */     FontRenderContext frc = new FontRenderContext(null, false, false);
/* 26:23 */     while (st.hasMoreTokens())
/* 27:   */     {
/* 28:24 */       String s = st.nextToken();
/* 29:25 */       TextLayout tl = new TextLayout(s, f, frc);
/* 30:26 */       int swidth = (int)tl.getAdvance() + 10;
/* 31:27 */       if (swidth > width) {
/* 32:27 */         width = swidth;
/* 33:   */       }
/* 34:   */     }
/* 35:29 */     setMinimumSize(new Dimension(width, height));
/* 36:30 */     setMaximumSize(new Dimension(width, height));
/* 37:31 */     setPreferredSize(new Dimension(width, height));
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void paintComponent(Graphics g)
/* 41:   */   {
/* 42:37 */     Rectangle r = g.getClipBounds();
/* 43:38 */     g.setColor(getBackground());
/* 44:39 */     g.fillRect(r.x, r.y, r.width, r.height);
/* 45:   */     
/* 46:   */ 
/* 47:42 */     g.setColor(getForeground());
/* 48:43 */     StringTokenizer st = new StringTokenizer(getTipText(), "\n\r\f");
/* 49:44 */     int i = 0;
/* 50:45 */     while (st.hasMoreTokens())
/* 51:   */     {
/* 52:46 */       i++;
/* 53:47 */       g.drawString(st.nextToken(), 5, i * getFont().getSize() + 2);
/* 54:   */     }
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.views.frameviews.classic.MultilineToolTip
 * JD-Core Version:    0.7.0.1
 */