/*  1:   */ package matthewFay.Constraints;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import com.google.common.collect.HashMultimap;
/*  5:   */ import com.google.common.collect.Multimap;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.Collection;
/*  8:   */ import java.util.Iterator;
/*  9:   */ import java.util.List;
/* 10:   */ import java.util.Set;
/* 11:   */ import utils.PairOfEntities;
/* 12:   */ 
/* 13:   */ public class BindingSet
/* 14:   */ {
/* 15:16 */   private Multimap<Entity, Entity> binding_map = HashMultimap.create();
/* 16:   */   
/* 17:   */   public BindingSet(List<PairOfEntities> bindings)
/* 18:   */   {
/* 19:19 */     for (PairOfEntities binding : bindings) {
/* 20:20 */       this.binding_map.put(binding.getPattern(), binding.getDatum());
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:   */   public Entity getDatum(Entity pattern)
/* 25:   */   {
/* 26:25 */     Entity datum = null;
/* 27:26 */     if (this.binding_map.containsKey(pattern))
/* 28:   */     {
/* 29:27 */       Collection<Entity> datums = this.binding_map.get(pattern);
/* 30:28 */       if (datums.size() == 1) {
/* 31:29 */         for (Entity e : datums) {
/* 32:30 */           datum = e;
/* 33:   */         }
/* 34:   */       }
/* 35:   */     }
/* 36:32 */     return datum;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public Entity getPattern(Entity datum)
/* 40:   */   {
/* 41:36 */     List<Entity> patterns = new ArrayList();
/* 42:37 */     for (Entity pattern : this.binding_map.keySet()) {
/* 43:38 */       if (this.binding_map.get(pattern).contains(datum)) {
/* 44:39 */         patterns.add(pattern);
/* 45:   */       }
/* 46:   */     }
/* 47:42 */     if (patterns.size() == 1) {
/* 48:43 */       return (Entity)patterns.get(0);
/* 49:   */     }
/* 50:44 */     return null;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static List<PairOfEntities> getInvertedBindings(List<PairOfEntities> bindings)
/* 54:   */   {
/* 55:48 */     ArrayList<PairOfEntities> inverted_bindings = new ArrayList();
/* 56:50 */     for (PairOfEntities binding : bindings)
/* 57:   */     {
/* 58:51 */       Entity datum = binding.getPattern();
/* 59:52 */       Entity pattern = binding.getDatum();
/* 60:53 */       inverted_bindings.add(new PairOfEntities(pattern, datum));
/* 61:   */     }
/* 62:56 */     return inverted_bindings;
/* 63:   */   }
/* 64:   */   
/* 65:   */   public List<PairOfEntities> getBindings()
/* 66:   */   {
/* 67:60 */     ArrayList<PairOfEntities> bindings = new ArrayList();
/* 68:   */     Iterator localIterator2;
/* 69:62 */     for (Iterator localIterator1 = this.binding_map.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 70:   */     {
/* 71:62 */       Entity pattern = (Entity)localIterator1.next();
/* 72:63 */       localIterator2 = this.binding_map.get(pattern).iterator(); continue;Entity datum = (Entity)localIterator2.next();
/* 73:64 */       bindings.add(new PairOfEntities(pattern, datum));
/* 74:   */     }
/* 75:68 */     return bindings;
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Constraints.BindingSet
 * JD-Core Version:    0.7.0.1
 */