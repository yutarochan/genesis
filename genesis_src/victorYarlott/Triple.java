/*   1:    */ package victorYarlott;
/*   2:    */ 
/*   3:    */ class Triple<T>
/*   4:    */ {
/*   5:    */   private T e1;
/*   6:    */   private T e2;
/*   7:    */   private T e3;
/*   8:    */   
/*   9:    */   public Triple(T e1, T e2, T e3)
/*  10:    */   {
/*  11:278 */     this.e1 = e1;
/*  12:279 */     this.e2 = e2;
/*  13:280 */     this.e3 = e3;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public T getFirst()
/*  17:    */   {
/*  18:284 */     return this.e1;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public T getSecond()
/*  22:    */   {
/*  23:288 */     return this.e2;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public T getThird()
/*  27:    */   {
/*  28:292 */     return this.e3;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setFirst(T e)
/*  32:    */   {
/*  33:296 */     this.e1 = e;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setSecond(T e)
/*  37:    */   {
/*  38:300 */     this.e2 = e;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void setThird(T e)
/*  42:    */   {
/*  43:304 */     this.e3 = e;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String toString()
/*  47:    */   {
/*  48:308 */     return 
/*  49:309 */       "1. " + this.e1.toString() + "\n2. " + this.e2.toString() + "\n3. " + this.e3.toString();
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int hashCode()
/*  53:    */   {
/*  54:313 */     return this.e1.hashCode() + this.e2.hashCode() + this.e3.hashCode();
/*  55:    */   }
/*  56:    */   
/*  57:    */   public boolean equals(Object o)
/*  58:    */   {
/*  59:317 */     if (!(o instanceof Triple)) {
/*  60:318 */       return false;
/*  61:    */     }
/*  62:320 */     Triple<?> castO = (Triple)o;
/*  63:321 */     return (getFirst().equals(castO.getFirst())) && 
/*  64:322 */       (getSecond().equals(castO.getSecond())) && 
/*  65:323 */       (getThird().equals(castO.getThird()));
/*  66:    */   }
/*  67:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     victorYarlott.Triple
 * JD-Core Version:    0.7.0.1
 */