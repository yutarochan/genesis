/*   1:    */ package m2;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Thread;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Set;
/*  10:    */ import lattice.Concept;
/*  11:    */ import lattice.FasterLLConcept;
/*  12:    */ import lattice.TypeLattice;
/*  13:    */ import links.words.WordNet;
/*  14:    */ 
/*  15:    */ public class LLCode
/*  16:    */ {
/*  17:    */   public static FasterLLConcept<String> getLLConcept(Set<Thread> posSet, Set<Thread> negSet)
/*  18:    */   {
/*  19: 22 */     Set<Thread> allThreads = new HashSet();
/*  20: 23 */     allThreads.addAll(posSet);
/*  21: 24 */     allThreads.addAll(negSet);
/*  22: 25 */     TypeLattice lattice = new TypeLattice(allThreads);
/*  23:    */     
/*  24:    */ 
/*  25: 28 */     FasterLLConcept<String> llcon = new FasterLLConcept(lattice);
/*  26: 29 */     for (Thread t : posSet) {
/*  27: 30 */       llcon.learnPositive(t.getType());
/*  28:    */     }
/*  29: 32 */     for (Thread t : negSet) {
/*  30: 33 */       llcon.learnNegative(t.getType());
/*  31:    */     }
/*  32: 37 */     if (negSet.isEmpty()) {
/*  33: 38 */       for (Thread t : posSet) {
/*  34: 39 */         llcon.learnNegative(t.getSupertype());
/*  35:    */       }
/*  36:    */     }
/*  37: 44 */     return llcon;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static boolean LLSearch(Concept<String> c, Thread query)
/*  41:    */   {
/*  42: 51 */     for (String parent : query) {
/*  43: 52 */       if (c.contains(parent)) {
/*  44: 53 */         return true;
/*  45:    */       }
/*  46:    */     }
/*  47: 56 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static boolean LLContains(Concept<String> c, Thread query)
/*  51:    */   {
/*  52: 64 */     return c.contains(query.getType());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static void main(String[] args)
/*  56:    */   {
/*  57:113 */     WordNet wn = new WordNet();
/*  58:    */     
/*  59:115 */     Bundle posBundle = new Bundle();
/*  60:116 */     Iterator localIterator1 = Arrays.asList(new String[] { "fish", "monkey", "cupcake" }).iterator();
/*  61:117 */     while (localIterator1.hasNext())
/*  62:    */     {
/*  63:116 */       String s = (String)localIterator1.next();
/*  64:117 */       posBundle.add(wn.lookup(s).getPrimedThread());
/*  65:    */     }
/*  66:120 */     Bundle negBundle = new Bundle();
/*  67:121 */     Iterator localIterator2 = Arrays.asList(new String[] { "tree" }).iterator();
/*  68:122 */     while (localIterator2.hasNext())
/*  69:    */     {
/*  70:121 */       String s = (String)localIterator2.next();
/*  71:122 */       negBundle.add(wn.lookup(s).getPrimedThread());
/*  72:    */     }
/*  73:    */   }
/*  74:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.LLCode
 * JD-Core Version:    0.7.0.1
 */