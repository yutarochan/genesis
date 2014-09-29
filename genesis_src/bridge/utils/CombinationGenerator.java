/*   1:    */ package bridge.utils;
/*   2:    */ 
/*   3:    */ import java.math.BigInteger;
/*   4:    */ 
/*   5:    */ public class CombinationGenerator
/*   6:    */ {
/*   7:    */   private int[] a;
/*   8:    */   private int n;
/*   9:    */   private int r;
/*  10:    */   private BigInteger numLeft;
/*  11:    */   private BigInteger total;
/*  12:    */   
/*  13:    */   public CombinationGenerator(int n, int r)
/*  14:    */   {
/*  15: 43 */     if (r > n) {
/*  16: 44 */       throw new IllegalArgumentException();
/*  17:    */     }
/*  18: 46 */     if (n < 1) {
/*  19: 47 */       throw new IllegalArgumentException();
/*  20:    */     }
/*  21: 49 */     if (r <= 0) {
/*  22: 50 */       throw new IllegalArgumentException();
/*  23:    */     }
/*  24: 52 */     this.n = n;
/*  25: 53 */     this.r = r;
/*  26: 54 */     this.a = new int[r];
/*  27: 55 */     BigInteger nFact = getFactorial(n);
/*  28: 56 */     BigInteger rFact = getFactorial(r);
/*  29: 57 */     BigInteger nminusrFact = getFactorial(n - r);
/*  30: 58 */     this.total = nFact.divide(rFact.multiply(nminusrFact));
/*  31: 59 */     reset();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void reset()
/*  35:    */   {
/*  36: 67 */     for (int i = 0; i < this.a.length; i++) {
/*  37: 68 */       this.a[i] = i;
/*  38:    */     }
/*  39: 70 */     this.numLeft = new BigInteger(this.total.toString());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public BigInteger getNumLeft()
/*  43:    */   {
/*  44: 78 */     return this.numLeft;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean hasMore()
/*  48:    */   {
/*  49: 86 */     return this.numLeft.compareTo(BigInteger.ZERO) == 1;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public BigInteger getTotal()
/*  53:    */   {
/*  54: 94 */     return this.total;
/*  55:    */   }
/*  56:    */   
/*  57:    */   private static BigInteger getFactorial(int n)
/*  58:    */   {
/*  59:102 */     BigInteger fact = BigInteger.ONE;
/*  60:103 */     for (int i = n; i > 1; i--) {
/*  61:104 */       fact = fact.multiply(new BigInteger(Integer.toString(i)));
/*  62:    */     }
/*  63:106 */     return fact;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int[] getNext()
/*  67:    */   {
/*  68:115 */     if (this.numLeft.equals(this.total))
/*  69:    */     {
/*  70:116 */       this.numLeft = this.numLeft.subtract(BigInteger.ONE);
/*  71:117 */       return this.a;
/*  72:    */     }
/*  73:120 */     int i = this.r - 1;
/*  74:121 */     while (this.a[i] == this.n - this.r + i) {
/*  75:122 */       i--;
/*  76:    */     }
/*  77:124 */     this.a[i] += 1;
/*  78:125 */     for (int j = i + 1; j < this.r; j++) {
/*  79:126 */       this.a[j] = (this.a[i] + j - i);
/*  80:    */     }
/*  81:129 */     this.numLeft = this.numLeft.subtract(BigInteger.ONE);
/*  82:130 */     return this.a;
/*  83:    */   }
/*  84:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.CombinationGenerator
 * JD-Core Version:    0.7.0.1
 */