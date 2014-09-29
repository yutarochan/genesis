/*   1:    */ package minilisp;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import utils.Mark;
/*   5:    */ 
/*   6:    */ public class LList<A>
/*   7:    */   extends Cons
/*   8:    */   implements Iterable<A>, Iterator<A>
/*   9:    */ {
/*  10:    */   private LList<A> pointer;
/*  11:    */   
/*  12:    */   public LList() {}
/*  13:    */   
/*  14:    */   public LList(A x)
/*  15:    */   {
/*  16: 19 */     super(x, new LList());
/*  17:    */   }
/*  18:    */   
/*  19:    */   public LList(A x, Object y)
/*  20:    */   {
/*  21: 23 */     super(x, new LList(y));
/*  22:    */   }
/*  23:    */   
/*  24:    */   public LList(A x, LList l)
/*  25:    */   {
/*  26: 27 */     super(x, l);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public A first()
/*  30:    */   {
/*  31: 31 */     return car();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean endP()
/*  35:    */   {
/*  36: 35 */     if (this.eol) {
/*  37: 36 */       return true;
/*  38:    */     }
/*  39: 38 */     return false;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public LList<A> rest()
/*  43:    */   {
/*  44: 42 */     if (endP()) {
/*  45: 43 */       return this;
/*  46:    */     }
/*  47: 45 */     return (LList)cdr();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public LList<A> cons(A x, LList l)
/*  51:    */   {
/*  52: 49 */     return new LList(x, l);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public LList<A> cons(A x)
/*  56:    */   {
/*  57: 53 */     return new LList(x, this);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public LList<A> append(LList<A> a)
/*  61:    */   {
/*  62: 57 */     return append(a, this);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public LList<A> append(LList<A> a, LList b)
/*  66:    */   {
/*  67: 61 */     LList result = b;
/*  68: 62 */     for (Object o : a)
/*  69:    */     {
/*  70: 63 */       A x = o;
/*  71: 64 */       result = cons(x, result);
/*  72:    */     }
/*  73: 66 */     return result;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int size()
/*  77:    */   {
/*  78: 97 */     if (endP()) {
/*  79: 98 */       return 0;
/*  80:    */     }
/*  81:100 */     return 1 + rest().size();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public static void main(String[] ignore)
/*  85:    */   {
/*  86:104 */     LList<Integer> l = new LList(new Integer(0), new Integer(10));
/*  87:105 */     Mark.say(new Object[] {"Result", l });
/*  88:106 */     Mark.say(new Object[] {"Result", l.first() });
/*  89:107 */     Mark.say(new Object[] {"Result", l.rest() });
/*  90:108 */     LList<Integer> m = l.cons(new Integer(15));
/*  91:109 */     Mark.say(new Object[] {"Result", m });
/*  92:110 */     Mark.say(new Object[] {"Result", m.rest() });
/*  93:111 */     Mark.say(new Object[] {"Result", m.rest().rest() });
/*  94:112 */     Mark.say(new Object[] {"Result", m.rest().rest().rest() });
/*  95:113 */     Mark.say(new Object[] {"Empty", new LList() });
/*  96:121 */     for (Object o : m) {
/*  97:122 */       Mark.say(new Object[] {"Next element:", o });
/*  98:    */     }
/*  99:124 */     for (Object o : m) {
/* 100:125 */       Mark.say(new Object[] {"Next element again:", o });
/* 101:    */     }
/* 102:127 */     Mark.say(new Object[] {"Size", Integer.valueOf(m.size()) });
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Iterator<A> iterator()
/* 106:    */   {
/* 107:135 */     this.pointer = this;
/* 108:136 */     return this;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean hasNext()
/* 112:    */   {
/* 113:141 */     if (this.pointer.endP()) {
/* 114:142 */       return false;
/* 115:    */     }
/* 116:144 */     return true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public A next()
/* 120:    */   {
/* 121:149 */     A result = this.pointer.first();
/* 122:150 */     this.pointer = this.pointer.rest();
/* 123:151 */     return result;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void remove()
/* 127:    */   {
/* 128:156 */     rest();
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     minilisp.LList
 * JD-Core Version:    0.7.0.1
 */