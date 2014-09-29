/*   1:    */ package memory;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import constants.RecognizedRepresentations;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import magicLess.utils.EntityUtils;
/*  12:    */ import memory.XMem.XMem;
/*  13:    */ 
/*  14:    */ public class EntitySpace
/*  15:    */ {
/*  16: 23 */   private Map<Integer, Entity> things = new HashMap();
/*  17: 25 */   private Map<Entity, Entity> subThings = new HashMap();
/*  18: 26 */   private Map<Entity, Set<Entity>> superThings = new HashMap();
/*  19: 28 */   private Map<Integer, Set<Entity>> potentials = new HashMap();
/*  20: 30 */   private XMem xmem = new XMem();
/*  21:    */   
/*  22:    */   public synchronized void add(Entity tRaw)
/*  23:    */   {
/*  24: 35 */     Entity t = RepProcessor.unwrap(tRaw);
/*  25: 36 */     this.things.put(Integer.valueOf(t.getID()), t);
/*  26: 37 */     this.superThings.put(t, new HashSet());
/*  27: 39 */     for (Integer i : getSubIDs(t)) {
/*  28: 40 */       if (this.things.containsKey(i))
/*  29:    */       {
/*  30: 41 */         this.subThings.put((Entity)this.things.get(i), t);
/*  31: 42 */         ((Set)this.superThings.get(t)).add((Entity)this.things.get(i));
/*  32:    */       }
/*  33: 45 */       else if (this.potentials.containsKey(i))
/*  34:    */       {
/*  35: 46 */         ((Set)this.potentials.get(i)).add(t);
/*  36:    */       }
/*  37:    */       else
/*  38:    */       {
/*  39: 49 */         Set<Entity> tset = new HashSet();
/*  40: 50 */         tset.add(t);
/*  41: 51 */         this.potentials.put(i, tset);
/*  42:    */       }
/*  43:    */     }
/*  44: 58 */     if ((this.potentials.containsKey(Integer.valueOf(t.getID()))) && 
/*  45: 59 */       (this.potentials.containsKey(Integer.valueOf(t.getID())))) {
/*  46: 60 */       for (Entity superRep : (Set)this.potentials.get(Integer.valueOf(t.getID())))
/*  47:    */       {
/*  48: 61 */         this.subThings.put(t, superRep);
/*  49: 62 */         ((Set)this.superThings.get(superRep)).add(t);
/*  50:    */       }
/*  51:    */     }
/*  52: 86 */     if (EntityUtils.getRepType(t) == RecognizedRepresentations.CAUSE_THING)
/*  53:    */     {
/*  54: 87 */       processCause(t);
/*  55:    */     }
/*  56: 89 */     else if ((this.subThings.get(t) != null) && 
/*  57: 90 */       (EntityUtils.getRepType((Entity)this.subThings.get(t)) == RecognizedRepresentations.CAUSE_THING))
/*  58:    */     {
/*  59: 92 */       Entity cause = (Entity)this.subThings.get(t);
/*  60: 93 */       processCause(cause);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   private void processCause(Entity cause)
/*  65:    */   {
/*  66: 98 */     Set<Entity> subs = (Set)this.superThings.get(cause);
/*  67: 99 */     Entity subject = cause.getSubject();
/*  68:100 */     Entity object = cause.getObject();
/*  69:101 */     Entity t1 = null;
/*  70:102 */     Entity t2 = null;
/*  71:103 */     for (Entity sub : subs) {
/*  72:104 */       if (sub.getID() == subject.getID()) {
/*  73:105 */         t1 = sub;
/*  74:107 */       } else if (sub.getID() == object.getID()) {
/*  75:108 */         t2 = sub;
/*  76:    */       }
/*  77:    */     }
/*  78:111 */     if ((t1 != null) && (t2 != null))
/*  79:    */     {
/*  80:112 */       this.xmem.add(t1, t2);
/*  81:113 */       if (Memory.DEBUG) {
/*  82:113 */         System.out.println("[MEMORY] Added to XMem");
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public synchronized boolean contains(Entity thing)
/*  88:    */   {
/*  89:126 */     Entity t = RepProcessor.unwrap(thing);
/*  90:127 */     return this.things.containsKey(Integer.valueOf(t.getID()));
/*  91:    */   }
/*  92:    */   
/*  93:    */   public synchronized List<Entity> predict(Entity thing)
/*  94:    */   {
/*  95:133 */     return this.xmem.predict(RepProcessor.unwrap(thing));
/*  96:    */   }
/*  97:    */   
/*  98:    */   private Set<Integer> getSubIDs(Entity t)
/*  99:    */   {
/* 100:138 */     Set<Integer> ids = new HashSet();
/* 101:139 */     Set<Entity> children = t.getDescendants();
/* 102:140 */     for (Entity c : children) {
/* 103:141 */       ids.add(Integer.valueOf(c.getID()));
/* 104:    */     }
/* 105:143 */     return ids;
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.EntitySpace
 * JD-Core Version:    0.7.0.1
 */