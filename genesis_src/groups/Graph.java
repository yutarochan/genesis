/*   1:    */ package groups;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Vector;
/*  10:    */ 
/*  11:    */ public class Graph
/*  12:    */   extends Entity
/*  13:    */   implements Group
/*  14:    */ {
/*  15: 16 */   public static String GRAPHNAME = "graph";
/*  16: 17 */   private HashSet<Entity> elts = new HashSet();
/*  17: 18 */   private HashSet<Relation> rels = new HashSet();
/*  18:    */   
/*  19:    */   public Graph() {}
/*  20:    */   
/*  21:    */   public Graph(String type)
/*  22:    */   {
/*  23: 24 */     super(type);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean addElt(Entity elt)
/*  27:    */   {
/*  28: 28 */     saveState();
/*  29: 29 */     this.elts.add(elt);
/*  30: 30 */     fireNotification();
/*  31: 31 */     return true;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public boolean addRel(Relation rel)
/*  35:    */   {
/*  36: 35 */     saveState();
/*  37: 37 */     if ((in(rel.getSubject())) && (in(rel.getObject())))
/*  38:    */     {
/*  39: 38 */       this.rels.add(rel);
/*  40: 39 */       fireNotification();
/*  41: 40 */       return true;
/*  42:    */     }
/*  43: 42 */     return false;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public HashSet<Entity> getElts()
/*  47:    */   {
/*  48: 46 */     return this.elts;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public HashSet<Relation> getRels()
/*  52:    */   {
/*  53: 49 */     return this.rels;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Vector<Relation> getRelationsInvolving(Entity elt)
/*  57:    */   {
/*  58: 57 */     Vector<Relation> relations = new Vector();
/*  59: 58 */     if (!this.elts.contains(elt)) {
/*  60: 59 */       System.err.println("Thing " + elt + " not a node in graph " + this);
/*  61:    */     } else {
/*  62: 61 */       for (Relation r : this.rels) {
/*  63: 62 */         if ((r.getSubject().equals(elt)) || (r.getObject().equals(elt))) {
/*  64: 63 */           relations.add(r);
/*  65:    */         }
/*  66:    */       }
/*  67:    */     }
/*  68: 67 */     return relations;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean in(Entity elt)
/*  72:    */   {
/*  73: 70 */     return this.elts.contains(elt);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean in(Relation rel)
/*  77:    */   {
/*  78: 73 */     return this.elts.contains(rel);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean removeElt(Entity elt)
/*  82:    */   {
/*  83: 76 */     saveState();
/*  84: 77 */     boolean b = this.elts.remove(elt);
/*  85: 78 */     if (b)
/*  86:    */     {
/*  87: 79 */       this.rels.removeAll(getRelationsInvolving(elt));
/*  88: 80 */       fireNotification();
/*  89:    */     }
/*  90: 82 */     return b;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean removeRel(Relation rel)
/*  94:    */   {
/*  95: 85 */     saveState();
/*  96: 86 */     boolean b = this.rels.remove(rel);
/*  97: 87 */     if (b) {
/*  98: 88 */       fireNotification();
/*  99:    */     }
/* 100: 90 */     return b;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String whatGroup()
/* 104:    */   {
/* 105: 93 */     return GRAPHNAME;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Object clone()
/* 109:    */   {
/* 110: 96 */     Graph graph = new Graph();
/* 111: 97 */     Bundle bundle = (Bundle)getBundle().clone();
/* 112: 98 */     graph.setBundle(bundle);
/* 113: 99 */     HashSet<Entity> s = getElts();
/* 114:100 */     for (Entity t : s) {
/* 115:101 */       graph.addElt(t);
/* 116:    */     }
/* 117:103 */     HashSet<Relation> t = getRels();
/* 118:104 */     for (Relation r : t) {
/* 119:105 */       graph.addRel(r);
/* 120:    */     }
/* 121:107 */     Object m = getModifiers();
/* 122:108 */     for (Entity u : (Vector)m) {
/* 123:109 */       graph.addModifier(u);
/* 124:    */     }
/* 125:111 */     return graph;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public List<Entity> getAllComponents()
/* 129:    */   {
/* 130:114 */     List<Entity> result = super.getAllComponents();
/* 131:115 */     result.addAll(getElts());
/* 132:116 */     result.addAll(getRels());
/* 133:117 */     return result;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isEqual(Object o)
/* 137:    */   {
/* 138:131 */     if ((o instanceof Graph))
/* 139:    */     {
/* 140:132 */       Graph g = (Graph)o;
/* 141:133 */       HashSet<Entity> gElts = g.getElts();
/* 142:134 */       HashSet<Entity> thisElts = getElts();
/* 143:135 */       for (Entity t : gElts)
/* 144:    */       {
/* 145:136 */         Entity equivThing = equalHelper(t, thisElts);
/* 146:137 */         if (equivThing == null) {
/* 147:138 */           return false;
/* 148:    */         }
/* 149:140 */         thisElts.remove(equivThing);
/* 150:    */       }
/* 151:142 */       if (!thisElts.isEmpty()) {
/* 152:143 */         return false;
/* 153:    */       }
/* 154:146 */       HashSet<Relation> gRels = g.getRels();
/* 155:147 */       Object thisRels = getRels();
/* 156:148 */       for (Relation r : gRels)
/* 157:    */       {
/* 158:149 */         Relation equivRel = equalHelper(r, (HashSet)thisRels);
/* 159:150 */         if (equivRel == null) {
/* 160:151 */           return false;
/* 161:    */         }
/* 162:153 */         ((HashSet)thisRels).remove(equivRel);
/* 163:    */       }
/* 164:155 */       if (((HashSet)thisRels).isEmpty()) {
/* 165:156 */         return super.isEqual(g);
/* 166:    */       }
/* 167:    */     }
/* 168:159 */     return false;
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected static Entity equalHelper(Entity x, HashSet<Entity> things)
/* 172:    */   {
/* 173:163 */     for (Entity t : things) {
/* 174:164 */       if (x.isEqual(t)) {
/* 175:165 */         return t;
/* 176:    */       }
/* 177:    */     }
/* 178:168 */     return null;
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected static Relation equalHelper(Relation x, HashSet<Relation> rels)
/* 182:    */   {
/* 183:172 */     for (Relation r : rels) {
/* 184:173 */       if (x.isEqual(r)) {
/* 185:174 */         return r;
/* 186:    */       }
/* 187:    */     }
/* 188:177 */     return null;
/* 189:    */   }
/* 190:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     groups.Graph
 * JD-Core Version:    0.7.0.1
 */