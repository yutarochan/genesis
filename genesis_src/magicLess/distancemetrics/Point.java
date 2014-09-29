/*   1:    */ package magicLess.distancemetrics;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.HashMap;
/*   5:    */ 
/*   6:    */ public abstract class Point<Type>
/*   7:    */ {
/*   8: 14 */   public static boolean cacheEnabled = true;
/*   9:    */   
/*  10:    */   public abstract Type getWrapped();
/*  11:    */   
/*  12:    */   protected abstract double getDistance(Type paramType1, Type paramType2);
/*  13:    */   
/*  14:    */   public double getDistanceTo(Point<Type> other)
/*  15:    */   {
/*  16: 29 */     if (cacheEnabled)
/*  17:    */     {
/*  18: 30 */       if (getDistCache().containsKey(other.getWrapped())) {
/*  19: 32 */         return ((Double)getDistCache().get(other.getWrapped())).doubleValue();
/*  20:    */       }
/*  21: 35 */       Double d = Double.valueOf(getDistance(getWrapped(), other.getWrapped()));
/*  22: 36 */       getDistCache().put(other.getWrapped(), d);
/*  23: 37 */       return d.doubleValue();
/*  24:    */     }
/*  25: 40 */     return getDistance(getWrapped(), other.getWrapped());
/*  26:    */   }
/*  27:    */   
/*  28: 42 */   private HashMap<Type, Double> distCache = null;
/*  29:    */   
/*  30:    */   protected HashMap<Type, Double> getDistCache()
/*  31:    */   {
/*  32: 45 */     if ((this.distCache == null) || (!isValid()))
/*  33:    */     {
/*  34: 46 */       this.distCache = new HashMap();
/*  35: 47 */       validate();
/*  36:    */     }
/*  37: 49 */     return this.distCache;
/*  38:    */   }
/*  39:    */   
/*  40: 52 */   private Integer lastHash = null;
/*  41:    */   
/*  42:    */   protected boolean isValid()
/*  43:    */   {
/*  44: 55 */     if (this.lastHash == null) {
/*  45: 55 */       return false;
/*  46:    */     }
/*  47: 56 */     if (getHashOfWrapped() != this.lastHash.intValue()) {
/*  48: 56 */       return false;
/*  49:    */     }
/*  50: 57 */     return true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   private int getHashOfWrapped()
/*  54:    */   {
/*  55: 60 */     return getWrapped().hashCode();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int hashCode()
/*  59:    */   {
/*  60: 64 */     return getHashOfWrapped();
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void validate()
/*  64:    */   {
/*  65: 67 */     this.lastHash = new Integer(getHashOfWrapped());
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String toString()
/*  69:    */   {
/*  70: 70 */     return "<Point>" + getWrapped().toString() + "</Point>";
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static class MutInt
/*  74:    */   {
/*  75:    */     public int foo;
/*  76:    */     
/*  77:    */     public int hashCode()
/*  78:    */     {
/*  79: 80 */       return this.foo;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   private static class Test
/*  84:    */     extends Point<Point.MutInt>
/*  85:    */   {
/*  86:    */     private Point.MutInt f;
/*  87:    */     
/*  88:    */     protected double getDistance(Point.MutInt a, Point.MutInt b)
/*  89:    */     {
/*  90: 85 */       return Math.abs(a.foo - b.foo);
/*  91:    */     }
/*  92:    */     
/*  93:    */     public Point.MutInt getWrapped()
/*  94:    */     {
/*  95: 89 */       if (this.f == null) {
/*  96: 89 */         this.f = new Point.MutInt(null);
/*  97:    */       }
/*  98: 90 */       return this.f;
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static void main(String[] args)
/* 103:    */   {
/* 104: 95 */     Test a = new Test(null);
/* 105: 96 */     Test b = new Test(null);
/* 106: 97 */     a.getWrapped().foo = 5;b.getWrapped().foo = 4;
/* 107: 98 */     System.out.println(a.getDistanceTo(b));
/* 108: 99 */     System.out.println(a.getDistanceTo(b));
/* 109:100 */     a.getWrapped().foo = 6;
/* 110:101 */     System.out.println(a.getDistanceTo(b));
/* 111:102 */     System.out.println(a.getDistanceTo(b));
/* 112:103 */     b.getWrapped().foo = 3;
/* 113:104 */     System.out.println(a.getDistanceTo(b));
/* 114:105 */     System.out.println(a.getDistanceTo(b));
/* 115:    */   }
/* 116:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.distancemetrics.Point
 * JD-Core Version:    0.7.0.1
 */