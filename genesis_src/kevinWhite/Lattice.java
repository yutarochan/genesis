/*  1:   */ package kevinWhite;
/*  2:   */ 
/*  3:   */ import java.util.HashSet;
/*  4:   */ import java.util.Set;
/*  5:   */ 
/*  6:   */ public abstract class Lattice<T>
/*  7:   */ {
/*  8:   */   abstract Iterable<T> getParents(T paramT);
/*  9:   */   
/* 10:   */   public Set<T> getAncestors(T node)
/* 11:   */   {
/* 12:10 */     Set<T> ancestors = new HashSet();
/* 13:11 */     ancestors.add(node);
/* 14:12 */     for (T parent : getParents(node)) {
/* 15:13 */       ancestors.addAll(getAncestors(parent));
/* 16:   */     }
/* 17:15 */     return ancestors;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean leq(T child, T parent)
/* 21:   */   {
/* 22:19 */     return getAncestors(child).contains(parent);
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.Lattice
 * JD-Core Version:    0.7.0.1
 */