/*  1:   */ package adam;
/*  2:   */ 
/*  3:   */ public class CppCollector
/*  4:   */   implements Collector
/*  5:   */ {
/*  6:   */   public native void call(Object paramObject, String paramString);
/*  7:   */   
/*  8:   */   public native Object callNormalMethod(String paramString, Object[] paramArrayOfObject);
/*  9:   */   
/* 10:   */   public String getName()
/* 11:   */   {
/* 12:13 */     return "C/C++ call-out stub";
/* 13:   */   }
/* 14:   */   
/* 15:16 */   private boolean hasFTablePtr = false;
/* 16:   */   private byte[] fTablePtr;
/* 17:   */   
/* 18:   */   public boolean hasFunctionTable()
/* 19:   */   {
/* 20:19 */     return this.hasFTablePtr;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public byte[] getFunctionTable()
/* 24:   */   {
/* 25:22 */     return this.fTablePtr;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public byte getFunctionPtrByte(int offset)
/* 29:   */   {
/* 30:25 */     return this.fTablePtr[offset];
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void putFunctionTable(byte[] fTablePtr)
/* 34:   */   {
/* 35:28 */     this.hasFTablePtr = true;
/* 36:29 */     this.fTablePtr = fTablePtr;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.CppCollector
 * JD-Core Version:    0.7.0.1
 */