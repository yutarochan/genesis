/*   1:    */ package victorYarlott;
/*   2:    */ 
/*   3:    */ class Pair<T>
/*   4:    */ {
/*   5:    */   private T e1;
/*   6:    */   private T e2;
/*   7:    */   
/*   8:    */   public Pair(T e1, T e2)
/*   9:    */   {
/*  10:233 */     this.e1 = e1;
/*  11:234 */     this.e2 = e2;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public T getFirst()
/*  15:    */   {
/*  16:238 */     return this.e1;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public T getSecond()
/*  20:    */   {
/*  21:242 */     return this.e2;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public void setFirst(T e)
/*  25:    */   {
/*  26:246 */     this.e1 = e;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setSecond(T e)
/*  30:    */   {
/*  31:250 */     this.e2 = e;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String toString()
/*  35:    */   {
/*  36:254 */     return "1. " + this.e1.toString() + "\n2. " + this.e2.toString();
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int hashCode()
/*  40:    */   {
/*  41:258 */     return this.e1.hashCode() + this.e2.hashCode();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public boolean equals(Object o)
/*  45:    */   {
/*  46:262 */     if (!(o instanceof Pair)) {
/*  47:263 */       return false;
/*  48:    */     }
/*  49:265 */     Pair<?> castO = (Pair)o;
/*  50:266 */     return (getFirst().equals(castO.getFirst())) && 
/*  51:267 */       (getSecond().equals(castO.getSecond()));
/*  52:    */   }
/*  53:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     victorYarlott.Pair
 * JD-Core Version:    0.7.0.1
 */