/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ public abstract class AbstractWiredBox
/*  4:   */   implements WiredBox
/*  5:   */ {
/*  6: 9 */   private String name = null;
/*  7:   */   
/*  8:   */   public AbstractWiredBox() {}
/*  9:   */   
/* 10:   */   public AbstractWiredBox(String name)
/* 11:   */   {
/* 12:16 */     this();
/* 13:17 */     setName(name);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getName()
/* 17:   */   {
/* 18:21 */     return this.name;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setName(String name)
/* 22:   */   {
/* 23:25 */     this.name = name;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String toString()
/* 27:   */   {
/* 28:29 */     return getName();
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.AbstractWiredBox
 * JD-Core Version:    0.7.0.1
 */