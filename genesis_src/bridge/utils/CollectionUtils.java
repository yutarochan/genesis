/*   1:    */ package bridge.utils;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.ListIterator;
/*   9:    */ import java.util.NoSuchElementException;
/*  10:    */ import java.util.RandomAccess;
/*  11:    */ import java.util.Set;
/*  12:    */ 
/*  13:    */ public class CollectionUtils
/*  14:    */ {
/*  15:    */   public static <T> Set<T> union(Collection<? extends T> A, Collection<? extends T> B)
/*  16:    */   {
/*  17: 26 */     Set<T> result = new HashSet();
/*  18: 27 */     result.addAll(A);
/*  19: 28 */     result.addAll(B);
/*  20: 29 */     return result;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static <T> Set<T> union(Collection<? extends T> A, Collection<? extends T> B, Collection<? extends T> C)
/*  24:    */   {
/*  25: 33 */     Set<T> result = new HashSet();
/*  26: 34 */     result.addAll(A);
/*  27: 35 */     result.addAll(B);
/*  28: 36 */     result.addAll(C);
/*  29: 37 */     return result;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static <T> Set<T> union(Collection<? extends T> A, Collection<? extends T> B, Collection<? extends T> C, Collection<? extends T> D)
/*  33:    */   {
/*  34: 41 */     Set<T> result = new HashSet();
/*  35: 42 */     result.addAll(A);
/*  36: 43 */     result.addAll(B);
/*  37: 44 */     result.addAll(C);
/*  38: 45 */     result.addAll(D);
/*  39: 46 */     return result;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static <T> Set<T> intersection(Collection<? extends T> A, Collection<? extends T> B)
/*  43:    */   {
/*  44: 51 */     Set<T> result = new HashSet();
/*  45: 52 */     result.addAll(A);
/*  46: 53 */     result.retainAll(B);
/*  47: 54 */     return result;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static <T> Collection<T> difference(Collection<? extends T> A, Collection<? extends T> B)
/*  51:    */   {
/*  52: 62 */     Set<T> result = new HashSet();
/*  53: 63 */     result.addAll(A);
/*  54: 64 */     Collection<? extends T> intersection = intersection(A, B);
/*  55: 65 */     result.removeAll(intersection);
/*  56: 66 */     return result;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static <T> boolean isIntersectionEmpty(Collection<? extends T> A, Collection<? extends T> B)
/*  60:    */   {
/*  61: 71 */     for (Iterator<? extends T> i = A.iterator(); i.hasNext();)
/*  62:    */     {
/*  63: 72 */       Object element = i.next();
/*  64: 73 */       if (B.contains(element)) {
/*  65: 73 */         return false;
/*  66:    */       }
/*  67:    */     }
/*  68: 75 */     return true;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static <T> boolean isIntersectionNonempty(Collection<? extends T> A, Collection<? extends T> B)
/*  72:    */   {
/*  73: 79 */     return !isIntersectionEmpty(A, B);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static String getPrintString(Collection c)
/*  77:    */   {
/*  78: 83 */     return "[" + StringUtils.join(c, ", ") + "]";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static Object get(Collection c, int x)
/*  82:    */   {
/*  83: 87 */     if ((c instanceof List)) {
/*  84: 88 */       return ((List)c).get(x);
/*  85:    */     }
/*  86: 90 */     Iterator iC = c.iterator();
/*  87: 91 */     for (int i = 0; i < x - 1; i++) {
/*  88: 91 */       iC.next();
/*  89:    */     }
/*  90: 92 */     return iC.next();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static <T> T getFirstElement(Collection<T> c)
/*  94:    */   {
/*  95:101 */     Iterator<T> i = c.iterator();
/*  96:102 */     if (i.hasNext()) {
/*  97:103 */       return i.next();
/*  98:    */     }
/*  99:105 */     throw new NoSuchElementException();
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static <T> T removeFirstElement(Collection<T> c)
/* 103:    */   {
/* 104:114 */     Iterator<T> i = c.iterator();
/* 105:115 */     if (i.hasNext())
/* 106:    */     {
/* 107:116 */       T o = i.next();
/* 108:117 */       i.remove();
/* 109:118 */       return o;
/* 110:    */     }
/* 111:120 */     throw new NoSuchElementException();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static <T> T getFirstElement(List<T> l)
/* 115:    */   {
/* 116:125 */     if (l.isEmpty()) {
/* 117:125 */       throw new NoSuchElementException();
/* 118:    */     }
/* 119:126 */     if ((l instanceof LinkedList)) {
/* 120:126 */       return ((LinkedList)l).getFirst();
/* 121:    */     }
/* 122:127 */     return l.get(0);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public static <T> T getLastElement(List<T> l)
/* 126:    */   {
/* 127:131 */     if (l.isEmpty()) {
/* 128:131 */       throw new NoSuchElementException();
/* 129:    */     }
/* 130:132 */     if ((l instanceof LinkedList)) {
/* 131:132 */       return ((LinkedList)l).getLast();
/* 132:    */     }
/* 133:133 */     return l.get(l.size() - 1);
/* 134:    */   }
/* 135:    */   
/* 136:    */   protected static class SizeLimitedList<T>
/* 137:    */     implements List<T>
/* 138:    */   {
/* 139:    */     List<T> backing;
/* 140:    */     int sizeLimit;
/* 141:    */     
/* 142:    */     public SizeLimitedList(List<T> backing, int sizeLimit)
/* 143:    */     {
/* 144:141 */       this.backing = backing;
/* 145:142 */       this.sizeLimit = sizeLimit;
/* 146:    */     }
/* 147:    */     
/* 148:    */     public void add(int index, T element)
/* 149:    */     {
/* 150:146 */       if (this.backing.size() >= this.sizeLimit) {
/* 151:146 */         return;
/* 152:    */       }
/* 153:147 */       this.backing.add(index, element);
/* 154:    */     }
/* 155:    */     
/* 156:    */     public boolean add(T o)
/* 157:    */     {
/* 158:151 */       if (this.backing.size() >= this.sizeLimit) {
/* 159:151 */         return false;
/* 160:    */       }
/* 161:152 */       return this.backing.add(o);
/* 162:    */     }
/* 163:    */     
/* 164:    */     public boolean addAll(Collection<? extends T> c)
/* 165:    */     {
/* 166:156 */       return addAll(size(), c);
/* 167:    */     }
/* 168:    */     
/* 169:    */     public boolean addAll(int index, Collection<? extends T> c)
/* 170:    */     {
/* 171:160 */       Iterator<? extends T> iC = c.iterator();
/* 172:161 */       while (iC.hasNext())
/* 173:    */       {
/* 174:162 */         T e = iC.next();
/* 175:163 */         add(index, e);
/* 176:164 */         index++;
/* 177:    */       }
/* 178:166 */       return true;
/* 179:    */     }
/* 180:    */     
/* 181:    */     public void clear()
/* 182:    */     {
/* 183:170 */       this.backing.clear();
/* 184:    */     }
/* 185:    */     
/* 186:    */     public boolean contains(Object o)
/* 187:    */     {
/* 188:174 */       return this.backing.contains(o);
/* 189:    */     }
/* 190:    */     
/* 191:    */     public boolean containsAll(Collection c)
/* 192:    */     {
/* 193:178 */       return this.backing.containsAll(c);
/* 194:    */     }
/* 195:    */     
/* 196:    */     public boolean equals(Object o)
/* 197:    */     {
/* 198:182 */       return this.backing.equals(o);
/* 199:    */     }
/* 200:    */     
/* 201:    */     public T get(int index)
/* 202:    */     {
/* 203:186 */       return this.backing.get(index);
/* 204:    */     }
/* 205:    */     
/* 206:    */     public int hashCode()
/* 207:    */     {
/* 208:190 */       return this.backing.hashCode();
/* 209:    */     }
/* 210:    */     
/* 211:    */     public int indexOf(Object o)
/* 212:    */     {
/* 213:194 */       return this.backing.indexOf(o);
/* 214:    */     }
/* 215:    */     
/* 216:    */     public boolean isEmpty()
/* 217:    */     {
/* 218:198 */       return this.backing.isEmpty();
/* 219:    */     }
/* 220:    */     
/* 221:    */     public Iterator<T> iterator()
/* 222:    */     {
/* 223:202 */       return this.backing.iterator();
/* 224:    */     }
/* 225:    */     
/* 226:    */     public int lastIndexOf(Object o)
/* 227:    */     {
/* 228:206 */       return this.backing.lastIndexOf(o);
/* 229:    */     }
/* 230:    */     
/* 231:    */     public ListIterator<T> listIterator()
/* 232:    */     {
/* 233:210 */       return this.backing.listIterator();
/* 234:    */     }
/* 235:    */     
/* 236:    */     public ListIterator<T> listIterator(int index)
/* 237:    */     {
/* 238:214 */       return this.backing.listIterator(index);
/* 239:    */     }
/* 240:    */     
/* 241:    */     public T remove(int index)
/* 242:    */     {
/* 243:218 */       return this.backing.remove(index);
/* 244:    */     }
/* 245:    */     
/* 246:    */     public boolean remove(Object o)
/* 247:    */     {
/* 248:222 */       return this.backing.remove(o);
/* 249:    */     }
/* 250:    */     
/* 251:    */     public boolean removeAll(Collection c)
/* 252:    */     {
/* 253:226 */       return this.backing.removeAll(c);
/* 254:    */     }
/* 255:    */     
/* 256:    */     public boolean retainAll(Collection c)
/* 257:    */     {
/* 258:230 */       return this.backing.retainAll(c);
/* 259:    */     }
/* 260:    */     
/* 261:    */     public T set(int index, T element)
/* 262:    */     {
/* 263:234 */       return this.backing.set(index, element);
/* 264:    */     }
/* 265:    */     
/* 266:    */     public int size()
/* 267:    */     {
/* 268:238 */       return this.backing.size();
/* 269:    */     }
/* 270:    */     
/* 271:    */     public List<T> subList(int fromIndex, int toIndex)
/* 272:    */     {
/* 273:242 */       return this.backing.subList(fromIndex, toIndex);
/* 274:    */     }
/* 275:    */     
/* 276:    */     public Object[] toArray()
/* 277:    */     {
/* 278:246 */       return this.backing.toArray();
/* 279:    */     }
/* 280:    */     
/* 281:    */     public <T2> T2[] toArray(T2[] a)
/* 282:    */     {
/* 283:250 */       return this.backing.toArray(a);
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected static class RandomAccessSizeLimitedList<T>
/* 288:    */     extends CollectionUtils.SizeLimitedList<T>
/* 289:    */     implements RandomAccess
/* 290:    */   {
/* 291:    */     public RandomAccessSizeLimitedList(List<T> backing, int sizeLimit)
/* 292:    */     {
/* 293:255 */       super(sizeLimit);
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   public static <T> List<T> sizeLimited(List<T> list, int sizeLimit)
/* 298:    */   {
/* 299:260 */     if ((list instanceof RandomAccess)) {
/* 300:261 */       return new RandomAccessSizeLimitedList(list, sizeLimit);
/* 301:    */     }
/* 302:263 */     return new SizeLimitedList(list, sizeLimit);
/* 303:    */   }
/* 304:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.CollectionUtils
 * JD-Core Version:    0.7.0.1
 */