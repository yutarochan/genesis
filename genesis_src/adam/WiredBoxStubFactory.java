/*  1:   */ package adam;
/*  2:   */ 
/*  3:   */ import connections.Connections.NetWireException;
/*  4:   */ import connections.WiredBox;
/*  5:   */ 
/*  6:   */ public abstract class WiredBoxStubFactory
/*  7:   */ {
/*  8:   */   public static final String GEN_CLASS_PREFIX = "GeneratedWiredBoxStub_";
/*  9:   */   private static Class<?> clazz;
/* 10:   */   private static WiredBoxStubFactory fact;
/* 11:   */   
/* 12:   */   public abstract WiredBox getStub(String paramString)
/* 13:   */     throws Connections.NetWireException;
/* 14:   */   
/* 15:   */   public abstract void reset();
/* 16:   */   
/* 17:   */   public static void setFactoryClass(Class<?> c)
/* 18:   */   {
/* 19:25 */     clazz = c;
/* 20:26 */     fact = null;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static WiredBoxStubFactory getInstance()
/* 24:   */   {
/* 25:29 */     if (fact == null) {
/* 26:   */       try
/* 27:   */       {
/* 28:31 */         fact = (WiredBoxStubFactory)clazz.newInstance();
/* 29:   */       }
/* 30:   */       catch (InstantiationException e)
/* 31:   */       {
/* 32:33 */         e.printStackTrace();
/* 33:   */       }
/* 34:   */       catch (IllegalAccessException e)
/* 35:   */       {
/* 36:35 */         e.printStackTrace();
/* 37:   */       }
/* 38:   */     }
/* 39:38 */     return fact;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.WiredBoxStubFactory
 * JD-Core Version:    0.7.0.1
 */