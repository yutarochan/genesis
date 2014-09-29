/*  1:   */ package lattice;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.Set;
/*  6:   */ 
/*  7:   */ public class SlowerLLConcept<T>
/*  8:   */   implements Concept<T>
/*  9:   */ {
/* 10:   */   private Lattice<T> lattice;
/* 11: 9 */   private Set<T> positives = new HashSet();
/* 12:10 */   private Set<T> negatives = new HashSet();
/* 13:   */   
/* 14:   */   public SlowerLLConcept(Lattice<T> lattice)
/* 15:   */   {
/* 16:13 */     this.lattice = lattice;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void learnNegative(T negative)
/* 20:   */   {
/* 21:17 */     this.negatives.add(negative);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void learnPositive(T positive)
/* 25:   */   {
/* 26:22 */     this.positives.add(positive);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean contains(T node)
/* 30:   */   {
/* 31:27 */     for (T negative : this.negatives) {
/* 32:28 */       if (this.lattice.leq(negative, node)) {
/* 33:29 */         return false;
/* 34:   */       }
/* 35:   */     }
/* 36:34 */     for (T positive : this.positives) {
/* 37:35 */       if (this.lattice.leq(positive, node)) {
/* 38:36 */         return true;
/* 39:   */       }
/* 40:   */     }
/* 41:40 */     boolean result = false;
/* 42:41 */     for (Object parent : this.lattice.getParents(node)) {
/* 43:42 */       result = (result) || (contains(parent));
/* 44:   */     }
/* 45:44 */     return result;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Set<T> maximalElements()
/* 49:   */   {
/* 50:48 */     Set<T> maxes = new HashSet();
/* 51:49 */     for (T positive : this.positives) {
/* 52:50 */       for (Iterator localIterator2 = this.lattice.getAncestors(positive).iterator(); localIterator2.hasNext();)
/* 53:   */       {
/* 54:50 */         node = (Object)localIterator2.next();
/* 55:51 */         if (contains(node))
/* 56:   */         {
/* 57:52 */           maxes.add(node);
/* 58:53 */           break;
/* 59:   */         }
/* 60:   */       }
/* 61:   */     }
/* 62:58 */     Set<T> toRemove = new HashSet();
/* 63:   */     Iterator localIterator3;
/* 64:59 */     for (T node = maxes.iterator(); node.hasNext(); localIterator3.hasNext())
/* 65:   */     {
/* 66:59 */       Object a = (Object)node.next();
/* 67:60 */       localIterator3 = maxes.iterator(); continue;Object b = (Object)localIterator3.next();
/* 68:61 */       if ((!a.equals(b)) && (this.lattice.leq(a, b))) {
/* 69:62 */         toRemove.add(a);
/* 70:   */       }
/* 71:   */     }
/* 72:66 */     maxes.removeAll(toRemove);
/* 73:67 */     return maxes;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public String toString()
/* 77:   */   {
/* 78:71 */     return maximalElements().toString();
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     lattice.SlowerLLConcept
 * JD-Core Version:    0.7.0.1
 */