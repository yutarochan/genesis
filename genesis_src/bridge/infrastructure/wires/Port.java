/*  1:   */ package bridge.infrastructure.wires;
/*  2:   */ 
/*  3:   */ public class Port
/*  4:   */ {
/*  5:   */   String name;
/*  6:   */   
/*  7:   */   public Port(String n)
/*  8:   */   {
/*  9:12 */     this.name = n;
/* 10:   */   }
/* 11:   */   
/* 12:   */   public String getName()
/* 13:   */   {
/* 14:16 */     return this.name;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public String toString()
/* 18:   */   {
/* 19:20 */     return this.name;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public boolean equals(Object o)
/* 23:   */   {
/* 24:23 */     if ((o instanceof String)) {
/* 25:24 */       return this.name.equalsIgnoreCase((String)o);
/* 26:   */     }
/* 27:26 */     return super.equals(o);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.infrastructure.wires.Port
 * JD-Core Version:    0.7.0.1
 */