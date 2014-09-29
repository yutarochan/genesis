/*  1:   */ package bridge.utils;
/*  2:   */ 
/*  3:   */ import java.awt.Point;
/*  4:   */ 
/*  5:   */ public class Rectangle
/*  6:   */ {
/*  7:   */   private int upperleftx;
/*  8:   */   private int upperlefty;
/*  9:   */   private int width;
/* 10:   */   private int height;
/* 11:   */   
/* 12:   */   public Rectangle(int upperleftx, int upperlefty, int width, int height)
/* 13:   */   {
/* 14:34 */     this.upperleftx = upperleftx;
/* 15:35 */     this.upperlefty = upperlefty;
/* 16:36 */     this.width = width;
/* 17:37 */     this.height = height;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean contains(int x, int y)
/* 21:   */   {
/* 22:48 */     return (x >= this.upperleftx) && 
/* 23:49 */       (x <= this.upperleftx + this.width) && 
/* 24:50 */       (y >= this.upperlefty) && (
/* 25:51 */       y <= this.upperlefty + this.height);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Point getCenter()
/* 29:   */   {
/* 30:55 */     return new Point(this.upperleftx + this.width / 2, this.upperlefty + this.height / 2);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean equals(Rectangle rect)
/* 34:   */   {
/* 35:64 */     return (this.upperleftx == rect.upperleftx) && 
/* 36:65 */       (this.upperlefty == rect.upperlefty) && 
/* 37:66 */       (this.width == rect.width) && 
/* 38:67 */       (this.height == rect.height);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String toString()
/* 42:   */   {
/* 43:71 */     String result = "";
/* 44:72 */     result = result + "upper left corner: (" + this.upperleftx + "," + this.upperlefty + ")\n";
/* 45:73 */     result = result + "width: " + this.width + ", height: " + this.height;
/* 46:74 */     return result;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.Rectangle
 * JD-Core Version:    0.7.0.1
 */