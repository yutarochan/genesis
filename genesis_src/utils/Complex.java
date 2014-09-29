/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ public class Complex
/*  4:   */ {
/*  5:   */   public double r;
/*  6:   */   public double i;
/*  7:   */   
/*  8:   */   public Complex(double r, double i)
/*  9:   */   {
/* 10:15 */     this.r = r;
/* 11:16 */     this.i = i;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Complex(double angle)
/* 15:   */   {
/* 16:20 */     this.r = Math.cos(angle);
/* 17:21 */     this.i = Math.sin(angle);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public double norm()
/* 21:   */   {
/* 22:25 */     return Math.hypot(this.r, this.i);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public double squaredNorm()
/* 26:   */   {
/* 27:29 */     return this.r * this.r + this.i * this.i;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Complex add(Complex o)
/* 31:   */   {
/* 32:33 */     return new Complex(this.r + o.r, this.i + o.i);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public Complex multiply(Complex o)
/* 36:   */   {
/* 37:37 */     return new Complex(this.r * o.r - this.i * o.i, this.i * o.r + this.r * o.i);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public Complex conjugate()
/* 41:   */   {
/* 42:41 */     return new Complex(this.r, -this.i);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Complex scale(double factor)
/* 46:   */   {
/* 47:45 */     return new Complex(this.r * factor, this.i * factor);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public Complex moebiusTransformation(double theta, Complex c)
/* 51:   */   {
/* 52:49 */     Complex num = add(c.scale(-1.0D));
/* 53:50 */     Complex den = new Complex(1.0D, 0.0D).add(c.conjugate().multiply(this).scale(-1.0D));
/* 54:51 */     Complex numProd = den.conjugate();
/* 55:52 */     double denProd = den.multiply(den.conjugate()).r;
/* 56:53 */     return num.multiply(numProd).scale(1.0D / denProd);
/* 57:   */   }
/* 58:   */   
/* 59:   */   public String toString()
/* 60:   */   {
/* 61:57 */     return this.r + (this.i < 0.0D ? "-" : "+") + this.i + "i";
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.Complex
 * JD-Core Version:    0.7.0.1
 */