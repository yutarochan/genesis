/*   1:    */ package bridge.utils.collections;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.AbstractSet;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public class ExclusiveSet<T>
/*  10:    */   extends AbstractSet<T>
/*  11:    */   implements Set<T>, Cloneable, Serializable, ExclusiveCollection<T>
/*  12:    */ {
/*  13:    */   private final Set<T> set;
/*  14:    */   private Class type;
/*  15:    */   private final int restriction;
/*  16:    */   
/*  17:    */   protected Set<T> getWrappedSet()
/*  18:    */   {
/*  19: 44 */     return this.set;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ExclusiveSet(Set<T> s, Class t)
/*  23:    */   {
/*  24: 60 */     this.set = s;
/*  25: 61 */     this.type = t;
/*  26: 62 */     this.restriction = 0;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ExclusiveSet(Set<T> s, Class t, int r)
/*  30:    */   {
/*  31: 66 */     this.set = s;
/*  32: 67 */     this.type = t;
/*  33: 68 */     if ((r == 0) || (r == 1)) {
/*  34: 69 */       this.restriction = r;
/*  35:    */     } else {
/*  36: 71 */       throw new RuntimeException("Restriction " + r + " not valid.");
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean testType(Object element)
/*  41:    */   {
/*  42: 76 */     if (this.restriction == 0) {
/*  43: 77 */       return element.getClass() == getType();
/*  44:    */     }
/*  45: 78 */     if (this.restriction == 1) {
/*  46: 79 */       return getType().isInstance(element);
/*  47:    */     }
/*  48: 81 */     return false;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Class getType()
/*  52:    */   {
/*  53: 89 */     return this.type;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getRestriction()
/*  57:    */   {
/*  58: 93 */     return this.restriction;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean equals(Object o)
/*  62:    */   {
/*  63: 97 */     return super.equals(o);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public int size()
/*  67:    */   {
/*  68:100 */     return this.set.size();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isEmpty()
/*  72:    */   {
/*  73:101 */     return this.set.isEmpty();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean contains(Object element)
/*  77:    */   {
/*  78:102 */     return this.set.contains(element);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean add(T element)
/*  82:    */   {
/*  83:111 */     if (testType(element)) {
/*  84:112 */       return this.set.add(element);
/*  85:    */     }
/*  86:114 */     throw new RuntimeException(
/*  87:115 */       "Tried to add object of type \"" + 
/*  88:116 */       element.getClass().getName() + 
/*  89:117 */       "\" to a RestrictedMembershipClass " + 
/*  90:118 */       "with type restriction \"" + 
/*  91:119 */       this.type.getName() + "\".");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean remove(Object element)
/*  95:    */   {
/*  96:123 */     return this.set.remove(element);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Iterator<T> iterator()
/* 100:    */   {
/* 101:125 */     return this.set.iterator();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean containsAll(Collection c)
/* 105:    */   {
/* 106:128 */     return this.set.containsAll(c);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean addAll(Collection<? extends T> c)
/* 110:    */   {
/* 111:138 */     for (Iterator i = c.iterator(); i.hasNext();)
/* 112:    */     {
/* 113:139 */       Object object = i.next();
/* 114:140 */       if (!testType(object)) {
/* 115:141 */         throw new RuntimeException(
/* 116:142 */           "Tried to add collection containing object of type \"" + 
/* 117:    */           
/* 118:144 */           object.getClass().getName() + 
/* 119:145 */           "\" to a " + 
/* 120:146 */           "RestrictedMembershipClass " + 
/* 121:147 */           "with type restriction \"" + 
/* 122:148 */           this.type.getName() + "\"");
/* 123:    */       }
/* 124:    */     }
/* 125:151 */     return this.set.addAll(c);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean removeAll(Collection c)
/* 129:    */   {
/* 130:153 */     return this.set.removeAll(c);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public boolean retainAll(Collection c)
/* 134:    */   {
/* 135:154 */     return this.set.retainAll(c);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void clear()
/* 139:    */   {
/* 140:155 */     this.set.clear();
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Object[] toArray()
/* 144:    */   {
/* 145:158 */     return this.set.toArray();
/* 146:    */   }
/* 147:    */   
/* 148:    */   public <T2> T2[] toArray(T2[] a)
/* 149:    */   {
/* 150:159 */     return this.set.toArray(a);
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.collections.ExclusiveSet
 * JD-Core Version:    0.7.0.1
 */