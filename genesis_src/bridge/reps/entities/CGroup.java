/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.Set;
/*  8:   */ 
/*  9:   */ public class CGroup
/* 10:   */ {
/* 11:21 */   private HashMap<Pair, Correspondence> cors = new HashMap();
/* 12:   */   
/* 13:   */   public Collection<Correspondence> getCorrespondences()
/* 14:   */   {
/* 15:24 */     HashSet<Correspondence> result = new HashSet();
/* 16:25 */     result.addAll(this.cors.values());
/* 17:26 */     return result;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void putCorrespondence(Entity a, Entity b, Correspondence c)
/* 21:   */   {
/* 22:30 */     this.cors.put(new Pair(a, b), c);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public Set<Correspondence> correspondencess()
/* 26:   */   {
/* 27:34 */     Set<Correspondence> result = new HashSet();
/* 28:35 */     result.addAll(this.cors.values());
/* 29:36 */     return result;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Correspondence getCorrespondence(Entity a, Entity b)
/* 33:   */   {
/* 34:40 */     return (Correspondence)this.cors.get(new Pair(a, b));
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Correspondence getCorrespondence(int id1, int id2)
/* 38:   */   {
/* 39:46 */     for (Iterator<Pair> i = this.cors.keySet().iterator(); i.hasNext();)
/* 40:   */     {
/* 41:47 */       Pair pair = (Pair)i.next();
/* 42:49 */       if (((pair.a.getID() == id1 ? 1 : 0) & (pair.b.getID() == id2 ? 1 : 0) | (pair.a.getID() == id2 ? 1 : 0) & (pair.b.getID() == id1 ? 1 : 0)) != 0) {
/* 43:50 */         return (Correspondence)this.cors.get(pair);
/* 44:   */       }
/* 45:   */     }
/* 46:54 */     return null;
/* 47:   */   }
/* 48:   */   
/* 49:   */   private class Pair
/* 50:   */   {
/* 51:   */     Entity a;
/* 52:   */     Entity b;
/* 53:   */     
/* 54:   */     public Pair(Entity a, Entity b)
/* 55:   */     {
/* 56:60 */       this.a = a;
/* 57:61 */       this.b = b;
/* 58:   */     }
/* 59:   */     
/* 60:   */     public boolean equals(Object o)
/* 61:   */     {
/* 62:65 */       if (o.getClass() != Pair.class) {
/* 63:66 */         return false;
/* 64:   */       }
/* 65:69 */       if (o.hashCode() == hashCode()) {
/* 66:70 */         return true;
/* 67:   */       }
/* 68:72 */       return false;
/* 69:   */     }
/* 70:   */     
/* 71:   */     public int hashCode()
/* 72:   */     {
/* 73:77 */       return this.a.hashCode() + this.b.hashCode();
/* 74:   */     }
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.CGroup
 * JD-Core Version:    0.7.0.1
 */