/*  1:   */ package m2.storage;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.HashSet;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import java.util.Set;
/* 10:   */ import m2.Methods;
/* 11:   */ import m2.datatypes.Chain;
/* 12:   */ import m2.datatypes.ImmutableEntity;
/* 13:   */ 
/* 14:   */ public class Raw
/* 15:   */ {
/* 16:30 */   private Map<ImmutableEntity, Integer> rawFreq = Collections.synchronizedMap(new HashMap());
/* 17:35 */   private Map<ImmutableEntity, Set<ImmutableEntity>> contextCache = Collections.synchronizedMap(new HashMap());
/* 18:   */   
/* 19:   */   public void add(Entity in)
/* 20:   */   {
/* 21:39 */     ImmutableEntity t = new ImmutableEntity(in);
/* 22:41 */     if (this.rawFreq.containsKey(t)) {
/* 23:42 */       this.rawFreq.put(t, Integer.valueOf(((Integer)this.rawFreq.get(t)).intValue() + 1));
/* 24:   */     } else {
/* 25:45 */       this.rawFreq.put(t, Integer.valueOf(1));
/* 26:   */     }
/* 27:48 */     List<Entity> subThings = Chain.flattenThing(in);
/* 28:49 */     for (Entity sub : subThings)
/* 29:   */     {
/* 30:50 */       ImmutableEntity isub = new ImmutableEntity(sub);
/* 31:51 */       if (this.contextCache.containsKey(isub)) {
/* 32:52 */         ((Set)this.contextCache.get(isub)).add(t);
/* 33:   */       }
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int frequency(Entity in)
/* 38:   */   {
/* 39:62 */     ImmutableEntity t = new ImmutableEntity(in);
/* 40:63 */     Integer freq = (Integer)this.rawFreq.get(t);
/* 41:64 */     if (freq == null) {
/* 42:65 */       return 0;
/* 43:   */     }
/* 44:67 */     return freq.intValue();
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Set<Entity> getContext(Entity t)
/* 48:   */   {
/* 49:78 */     ImmutableEntity ithing = new ImmutableEntity(t);
/* 50:79 */     if (this.contextCache.containsKey(ithing)) {
/* 51:80 */       return Methods.convertFromIthingSet((Set)this.contextCache.get(ithing));
/* 52:   */     }
/* 53:84 */     Set<ImmutableEntity> results = new HashSet();
/* 54:85 */     synchronized (this.rawFreq)
/* 55:   */     {
/* 56:86 */       for (ImmutableEntity item : this.rawFreq.keySet()) {
/* 57:87 */         if (Methods.containsIthing(item, ithing)) {
/* 58:88 */           results.add(item);
/* 59:   */         }
/* 60:   */       }
/* 61:   */     }
/* 62:92 */     this.contextCache.put(ithing, results);
/* 63:93 */     return Methods.convertFromIthingSet(results);
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.storage.Raw
 * JD-Core Version:    0.7.0.1
 */