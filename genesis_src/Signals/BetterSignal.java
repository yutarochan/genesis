/*   1:    */ package Signals;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import genesis.Genesis;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.io.Serializable;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collection;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ 
/*  14:    */ public class BetterSignal
/*  15:    */   implements Serializable, Iterable<Object>
/*  16:    */ {
/*  17:    */   public BetterSignal() {}
/*  18:    */   
/*  19:    */   public static class NoSuchElementException
/*  20:    */     extends RuntimeException
/*  21:    */   {
/*  22:    */     public NoSuchElementException(int idx)
/*  23:    */     {
/*  24: 27 */       super();
/*  25:    */     }
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static class WrongTypeException
/*  29:    */     extends RuntimeException
/*  30:    */   {
/*  31:    */     public WrongTypeException(int idx, Class<?> asked, Class<?> actual)
/*  32:    */     {
/*  33: 32 */       super();
/*  34:    */     }
/*  35:    */   }
/*  36:    */   
/*  37: 35 */   private List<Object> l = new ArrayList();
/*  38:    */   
/*  39:    */   public BetterSignal(Object... args)
/*  40:    */   {
/*  41: 38 */     for (Object wrapped : args) {
/*  42: 39 */       this.l.add(wrapped);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public synchronized <T> T get(int n, Class<T> type)
/*  47:    */   {
/*  48: 44 */     if (n < this.l.size())
/*  49:    */     {
/*  50: 45 */       if (type.isInstance(this.l.get(n))) {
/*  51: 46 */         return this.l.get(n);
/*  52:    */       }
/*  53: 48 */       throw new WrongTypeException(n, type, this.l.get(n).getClass());
/*  54:    */     }
/*  55: 51 */     throw new NoSuchElementException(n);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public synchronized void add(Object o)
/*  59:    */   {
/*  60: 55 */     this.l.add(o);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public synchronized void put(int n, Object o)
/*  64:    */   {
/*  65: 58 */     while (this.l.size() <= n) {
/*  66: 59 */       this.l.add(null);
/*  67:    */     }
/*  68: 61 */     this.l.set(n, o);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public synchronized <T> boolean elementIsType(int n, Class<T> type)
/*  72:    */   {
/*  73: 64 */     if (n < this.l.size())
/*  74:    */     {
/*  75: 65 */       if (this.l.get(n) != null) {
/*  76: 67 */         return type.isInstance(this.l.get(n));
/*  77:    */       }
/*  78: 69 */       return false;
/*  79:    */     }
/*  80: 72 */     throw new NoSuchElementException(n);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public synchronized int size()
/*  84:    */   {
/*  85: 76 */     return this.l.size();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static BetterSignal isSignal(Object object)
/*  89:    */   {
/*  90: 80 */     if ((object instanceof BetterSignal)) {
/*  91: 81 */       return (BetterSignal)object;
/*  92:    */     }
/*  93: 83 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static void main(String[] args)
/*  97:    */   {
/*  98: 87 */     BetterSignal s = new BetterSignal(new Object[] {
/*  99: 88 */       new Entity(), 
/* 100: 89 */       new Function(new Entity()), 
/* 101: 90 */       new Sequence(), 
/* 102: 91 */       new ArrayList() });
/* 103: 92 */     Entity first = (Entity)s.get(0, Entity.class);
/* 104: 93 */     Function second = (Function)s.get(1, Function.class);
/* 105: 94 */     Sequence third = (Sequence)s.get(2, Sequence.class);
/* 106: 95 */     Collection<?> fourth = (Collection)s.get(3, Collection.class);
/* 107:    */     
/* 108: 97 */     System.out.println("Got: " + first + ", " + second + ", " + third + ", " + fourth);
/* 109: 98 */     System.out.println("elementIsType(3,Collection.class): " + s.elementIsType(3, Collection.class));
/* 110: 99 */     System.out.println("elementIsType(3,ArrayList.class): " + s.elementIsType(3, ArrayList.class));
/* 111:100 */     s.add(null);
/* 112:101 */     System.out.println("null behaves correctly: " + (!s.elementIsType(4, Genesis.class)));
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Iterator<Object> iterator()
/* 116:    */   {
/* 117:107 */     return this.l.iterator();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public synchronized <T> List<T> getAll(Class<T> type)
/* 121:    */   {
/* 122:112 */     ArrayList<T> tempList = new ArrayList();
/* 123:113 */     for (Object o : this.l) {
/* 124:114 */       if (type.isInstance(o)) {
/* 125:115 */         tempList.add(o);
/* 126:    */       }
/* 127:    */     }
/* 128:117 */     return tempList;
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Signals.BetterSignal
 * JD-Core Version:    0.7.0.1
 */