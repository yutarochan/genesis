/*  1:   */ package bridge.utils.collections;
/*  2:   */ 
/*  3:   */ import java.util.AbstractSet;
/*  4:   */ import java.util.Collection;
/*  5:   */ import java.util.IdentityHashMap;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.Set;
/*  8:   */ 
/*  9:   */ public class IdentityHashSet<T>
/* 10:   */   extends AbstractSet<T>
/* 11:   */ {
/* 12:22 */   public static final Object DUMMY = new Object();
/* 13:24 */   protected IdentityHashMap<T, Object> backing = new IdentityHashMap();
/* 14:   */   
/* 15:   */   public IdentityHashSet() {}
/* 16:   */   
/* 17:   */   public IdentityHashSet(Collection<? extends T> c)
/* 18:   */   {
/* 19:26 */     this();
/* 20:27 */     addAll(c);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Iterator<T> iterator()
/* 24:   */   {
/* 25:30 */     return this.backing.keySet().iterator();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int size()
/* 29:   */   {
/* 30:31 */     return this.backing.size();
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void clear()
/* 34:   */   {
/* 35:32 */     this.backing.clear();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean isEmpty()
/* 39:   */   {
/* 40:33 */     return this.backing.isEmpty();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean add(T o)
/* 44:   */   {
/* 45:34 */     return this.backing.put(o, DUMMY) == null;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public boolean contains(Object o)
/* 49:   */   {
/* 50:35 */     return this.backing.containsKey(o);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean remove(Object o)
/* 54:   */   {
/* 55:36 */     return this.backing.remove(o) != null;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int hashCode()
/* 59:   */   {
/* 60:37 */     return this.backing.hashCode();
/* 61:   */   }
/* 62:   */   
/* 63:   */   public boolean equals(Object arg0)
/* 64:   */   {
/* 65:39 */     if (!(arg0 instanceof IdentityHashSet)) {
/* 66:40 */       return false;
/* 67:   */     }
/* 68:42 */     IdentityHashSet other = (IdentityHashSet)arg0;
/* 69:43 */     return this.backing.equals(other.backing);
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.collections.IdentityHashSet
 * JD-Core Version:    0.7.0.1
 */