/*   1:    */ package memory.XMem;
/*   2:    */ 
/*   3:    */ import ADTs.DirectedGraph;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import frames.Analogy;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.Comparator;
/*  10:    */ import java.util.HashSet;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.List;
/*  13:    */ import matchers.Substitutor;
/*  14:    */ import memory.Memory;
/*  15:    */ import memory.RepProcessor;
/*  16:    */ import memory.utilities.Distances;
/*  17:    */ 
/*  18:    */ public class XMem
/*  19:    */ {
/*  20: 21 */   private final DirectedGraph<Entity, Integer> graph = new DirectedGraph();
/*  21:    */   
/*  22:    */   public synchronized void add(Entity t1, Entity t2)
/*  23:    */   {
/*  24: 32 */     int edge = 1;
/*  25: 33 */     if (this.graph.contains(t1, t2)) {
/*  26: 34 */       edge = ((Integer)this.graph.getEdgeBetween(t1, t2)).intValue() + 1;
/*  27:    */     }
/*  28: 36 */     this.graph.addEdge(t1, t2, Integer.valueOf(edge));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public synchronized void remove(Entity t1, Entity t2)
/*  32:    */   {
/*  33: 49 */     this.graph.removeEdge(t1, t2);
/*  34: 50 */     if ((this.graph.getSuccessors(t1).isEmpty()) && (this.graph.getPredecessors(t1).isEmpty())) {
/*  35: 51 */       this.graph.removeNode(t1);
/*  36:    */     }
/*  37: 53 */     if ((this.graph.getSuccessors(t2).isEmpty()) && (this.graph.getPredecessors(t2).isEmpty())) {
/*  38: 54 */       this.graph.removeNode(t2);
/*  39:    */     }
/*  40:    */   }
/*  41:    */   
/*  42:    */   public synchronized boolean check(Entity t1, Entity t2)
/*  43:    */   {
/*  44: 71 */     if (this.graph.getSuccessors(t1).contains(t2)) {
/*  45: 72 */       return true;
/*  46:    */     }
/*  47: 75 */     Memory m = Memory.getMemory();
/*  48: 76 */     List<Entity> t1Neighbors = m.getNeighbors(t1);
/*  49: 77 */     List<Entity> t2Neighbors = m.getNeighbors(t2);
/*  50:    */     Iterator localIterator2;
/*  51: 80 */     for (Iterator localIterator1 = t1Neighbors.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  52:    */     {
/*  53: 80 */       Entity start = (Entity)localIterator1.next();
/*  54: 81 */       localIterator2 = t2Neighbors.iterator(); continue;Entity end = (Entity)localIterator2.next();
/*  55: 82 */       if (this.graph.getSuccessors(start).contains(end)) {
/*  56: 83 */         return true;
/*  57:    */       }
/*  58:    */     }
/*  59: 87 */     return false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public synchronized List<Entity> predict(Entity tRaw)
/*  63:    */   {
/*  64: 99 */     final Entity thing = getMatch(tRaw);
/*  65:100 */     List<Entity> matches = new ArrayList();
/*  66:102 */     if (this.graph.contains(thing))
/*  67:    */     {
/*  68:104 */       List<Entity> preds = new ArrayList(this.graph.getSuccessors(thing));
/*  69:105 */       Collections.sort(preds, new Comparator()
/*  70:    */       {
/*  71:    */         public int compare(Entity t1, Entity t2)
/*  72:    */         {
/*  73:107 */           if (((Integer)XMem.this.graph.getEdgeBetween(thing, t1)).intValue() < ((Integer)XMem.this.graph.getEdgeBetween(thing, t2)).intValue()) {
/*  74:108 */             return -1;
/*  75:    */           }
/*  76:109 */           if (((Integer)XMem.this.graph.getEdgeBetween(thing, t1)).intValue() > ((Integer)XMem.this.graph.getEdgeBetween(thing, t2)).intValue()) {
/*  77:109 */             return 1;
/*  78:    */           }
/*  79:110 */           return 0;
/*  80:    */         }
/*  81:    */       });
/*  82:114 */       for (Entity pred : preds) {
/*  83:115 */         matches.add(RepProcessor.wrap(pred));
/*  84:    */       }
/*  85:    */     }
/*  86:    */     else
/*  87:    */     {
/*  88:120 */       Memory m = Memory.getMemory();
/*  89:    */       
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:125 */       double cutoff = 0.05D;
/*  94:126 */       List<Entity> neighbors = new ArrayList();
/*  95:127 */       for (Entity potential : this.graph.getNodes())
/*  96:    */       {
/*  97:128 */         double distance = Distances.distance(potential, thing);
/*  98:129 */         System.out.println("Distance is " + distance + ", threshold " + cutoff);
/*  99:130 */         if (distance < cutoff) {
/* 100:132 */           neighbors.add(potential);
/* 101:    */         }
/* 102:    */       }
/* 103:135 */       Collections.sort(neighbors, new Comparator()
/* 104:    */       {
/* 105:    */         public int compare(Entity t1, Entity t2)
/* 106:    */         {
/* 107:137 */           if (Distances.distance(t1, thing) < Distances.distance(t2, thing)) {
/* 108:138 */             return -1;
/* 109:    */           }
/* 110:139 */           if (Distances.distance(t1, thing) > Distances.distance(t2, thing)) {
/* 111:139 */             return 1;
/* 112:    */           }
/* 113:140 */           return 0;
/* 114:    */         }
/* 115:    */       });
/* 116:144 */       if (neighbors.size() > 0)
/* 117:    */       {
/* 118:145 */         final Entity base = (Entity)neighbors.get(0);
/* 119:146 */         Object basies = new ArrayList(this.graph.getSuccessors(base));
/* 120:    */         
/* 121:148 */         Collections.sort((List)basies, new Comparator()
/* 122:    */         {
/* 123:    */           public int compare(Entity t1, Entity t2)
/* 124:    */           {
/* 125:150 */             if (((Integer)XMem.this.graph.getEdgeBetween(base, t1)).intValue() < ((Integer)XMem.this.graph.getEdgeBetween(base, t2)).intValue()) {
/* 126:151 */               return -1;
/* 127:    */             }
/* 128:152 */             if (((Integer)XMem.this.graph.getEdgeBetween(base, t1)).intValue() > ((Integer)XMem.this.graph.getEdgeBetween(base, t2)).intValue()) {
/* 129:152 */               return 1;
/* 130:    */             }
/* 131:153 */             return 0;
/* 132:    */           }
/* 133:158 */         });
/* 134:159 */         Analogy analogy = new Analogy(base, thing);
/* 135:163 */         for (Entity basey : (List)basies) {
/* 136:171 */           matches.add(RepProcessor.wrap(Substitutor.substitute(thing, base, basey)));
/* 137:    */         }
/* 138:    */       }
/* 139:    */     }
/* 140:176 */     return matches;
/* 141:    */   }
/* 142:    */   
/* 143:    */   private Entity getMatch(Entity thing)
/* 144:    */   {
/* 145:183 */     for (Entity t : this.graph.getNodes()) {
/* 146:184 */       if (Distances.distance(thing, t) == 0.0D) {
/* 147:185 */         return t;
/* 148:    */       }
/* 149:    */     }
/* 150:188 */     return thing;
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.XMem.XMem
 * JD-Core Version:    0.7.0.1
 */