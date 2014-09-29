/*  1:   */ package kevinWhite;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Thread;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import java.util.HashSet;
/*  7:   */ import java.util.Iterator;
/*  8:   */ import java.util.Set;
/*  9:   */ import links.words.BundleGenerator;
/* 10:   */ 
/* 11:   */ public class SlowerLLConcept<T>
/* 12:   */   implements Concept<T>
/* 13:   */ {
/* 14:   */   private Lattice<T> lattice;
/* 15:13 */   private Set<T> positives = new HashSet();
/* 16:14 */   private Set<T> negatives = new HashSet();
/* 17:   */   
/* 18:   */   public SlowerLLConcept(Lattice<T> lattice)
/* 19:   */   {
/* 20:17 */     this.lattice = lattice;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void learnNegative(T negative)
/* 24:   */   {
/* 25:21 */     this.negatives.add(negative);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void learnPositive(T positive)
/* 29:   */   {
/* 30:26 */     this.positives.add(positive);
/* 31:   */   }
/* 32:   */   
/* 33:   */   public boolean contains(T node)
/* 34:   */   {
/* 35:31 */     for (T negative : this.negatives) {
/* 36:32 */       if (this.lattice.leq(negative, node)) {
/* 37:33 */         return false;
/* 38:   */       }
/* 39:   */     }
/* 40:38 */     for (T positive : this.positives) {
/* 41:39 */       if (this.lattice.leq(positive, node)) {
/* 42:40 */         return true;
/* 43:   */       }
/* 44:   */     }
/* 45:44 */     boolean result = false;
/* 46:45 */     for (Object parent : this.lattice.getParents(node)) {
/* 47:46 */       result = (result) || (contains(parent));
/* 48:   */     }
/* 49:48 */     return result;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Set<T> maximalElements()
/* 53:   */   {
/* 54:52 */     Set<T> maxes = new HashSet();
/* 55:53 */     for (T positive : this.positives) {
/* 56:54 */       for (Iterator localIterator2 = this.lattice.getAncestors(positive).iterator(); localIterator2.hasNext();)
/* 57:   */       {
/* 58:54 */         node = (Object)localIterator2.next();
/* 59:55 */         if (contains(node))
/* 60:   */         {
/* 61:56 */           maxes.add(node);
/* 62:57 */           break;
/* 63:   */         }
/* 64:   */       }
/* 65:   */     }
/* 66:62 */     Set<T> toRemove = new HashSet();
/* 67:   */     Iterator localIterator3;
/* 68:63 */     for (T node = maxes.iterator(); node.hasNext(); localIterator3.hasNext())
/* 69:   */     {
/* 70:63 */       Object a = (Object)node.next();
/* 71:64 */       localIterator3 = maxes.iterator(); continue;Object b = (Object)localIterator3.next();
/* 72:65 */       if ((!a.equals(b)) && (this.lattice.leq(a, b))) {
/* 73:66 */         toRemove.add(a);
/* 74:   */       }
/* 75:   */     }
/* 76:70 */     maxes.removeAll(toRemove);
/* 77:71 */     return maxes;
/* 78:   */   }
/* 79:   */   
/* 80:   */   protected boolean infer(String word)
/* 81:   */     throws Exception
/* 82:   */   {
/* 83:75 */     Bundle wBundle = BundleGenerator.getBundle(word);
/* 84:76 */     Thread wThread = wBundle.getPrimedThread();
/* 85:77 */     for (int i = wThread.size() - 1; i >= 0; i--)
/* 86:   */     {
/* 87:78 */       if (this.positives.contains(wThread.elementAt(i))) {
/* 88:79 */         return true;
/* 89:   */       }
/* 90:82 */       if (this.negatives.contains(wThread.elementAt(i))) {
/* 91:83 */         return false;
/* 92:   */       }
/* 93:85 */       System.out.println((String)wThread.elementAt(i));
/* 94:   */     }
/* 95:87 */     return false;
/* 96:   */   }
/* 97:   */   
/* 98:   */   public String toString()
/* 99:   */   {
/* :0:91 */     return maximalElements().toString();
/* :1:   */   }
/* :2:   */   
/* :3:   */   public Set<T> getPositives()
/* :4:   */   {
/* :5:95 */     return this.positives;
/* :6:   */   }
/* :7:   */   
/* :8:   */   public Set<T> getNegatives()
/* :9:   */   {
/* ;0:99 */     return this.negatives;
/* ;1:   */   }
/* ;2:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.SlowerLLConcept
 * JD-Core Version:    0.7.0.1
 */