/*  1:   */ package lattice;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.Set;
/*  6:   */ 
/*  7:   */ public class FasterLLConcept<T>
/*  8:   */   implements Concept<T>
/*  9:   */ {
/* 10:   */   private Lattice<T> lattice;
/* 11:10 */   private Set<T> positiveAncestors = new HashSet();
/* 12:11 */   private Set<T> negativeAncestors = new HashSet();
/* 13:   */   
/* 14:   */   public FasterLLConcept(Lattice<T> lattice)
/* 15:   */   {
/* 16:14 */     this.lattice = lattice;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void learnNegative(T negative)
/* 20:   */   {
/* 21:18 */     this.negativeAncestors.addAll(this.lattice.getAncestors(negative));
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void learnPositive(T positive)
/* 25:   */   {
/* 26:22 */     this.positiveAncestors.addAll(this.lattice.getAncestors(positive));
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean contains(T node)
/* 30:   */   {
/* 31:26 */     if (this.negativeAncestors.contains(node)) {
/* 32:27 */       return false;
/* 33:   */     }
/* 34:29 */     if (this.positiveAncestors.contains(node)) {
/* 35:30 */       return true;
/* 36:   */     }
/* 37:32 */     boolean result = false;
/* 38:33 */     for (T parent : this.lattice.getParents(node)) {
/* 39:34 */       result = (result) || (contains(parent));
/* 40:   */     }
/* 41:36 */     return result;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public Set<T> maximalElements()
/* 45:   */   {
/* 46:40 */     Set<T> maxes = new HashSet();
/* 47:41 */     for (T node : this.positiveAncestors) {
/* 48:42 */       if (contains(node)) {
/* 49:43 */         maxes.add(node);
/* 50:   */       }
/* 51:   */     }
/* 52:47 */     Set<T> toRemove = new HashSet();
/* 53:   */     Iterator localIterator3;
/* 54:48 */     for (Iterator localIterator2 = maxes.iterator(); localIterator2.hasNext(); localIterator3.hasNext())
/* 55:   */     {
/* 56:48 */       Object a = (Object)localIterator2.next();
/* 57:49 */       localIterator3 = maxes.iterator(); continue;T b = (Object)localIterator3.next();
/* 58:50 */       if ((!a.equals(b)) && (this.lattice.leq(a, b))) {
/* 59:51 */         toRemove.add(a);
/* 60:   */       }
/* 61:   */     }
/* 62:55 */     maxes.removeAll(toRemove);
/* 63:56 */     return maxes;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public String toString()
/* 67:   */   {
/* 68:60 */     return maximalElements().toString();
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     lattice.FasterLLConcept
 * JD-Core Version:    0.7.0.1
 */