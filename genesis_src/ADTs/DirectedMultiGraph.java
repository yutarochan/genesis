/*   1:    */ package ADTs;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ 
/*  10:    */ public class DirectedMultiGraph<N, E>
/*  11:    */ {
/*  12:    */   private Map<N, Map<N, Set<E>>> starts;
/*  13:    */   private Map<N, Map<N, Set<E>>> ends;
/*  14:    */   
/*  15:    */   public DirectedMultiGraph()
/*  16:    */   {
/*  17: 40 */     this.starts = new HashMap();
/*  18: 41 */     this.ends = new HashMap();
/*  19: 42 */     assert (checkRep());
/*  20:    */   }
/*  21:    */   
/*  22:    */   public DirectedMultiGraph(DirectedMultiGraph<N, E> g)
/*  23:    */   {
/*  24: 52 */     this.starts = new HashMap(g.starts);
/*  25: 53 */     this.ends = new HashMap(g.ends);
/*  26: 54 */     assert (checkRep());
/*  27:    */   }
/*  28:    */   
/*  29:    */   public HashSet<N> getSuccessors(N node)
/*  30:    */   {
/*  31: 70 */     assert (checkRep());
/*  32: 71 */     return new HashSet(((Map)this.starts.get(node)).keySet());
/*  33:    */   }
/*  34:    */   
/*  35:    */   public HashSet<N> getPredecessors(N node)
/*  36:    */   {
/*  37: 87 */     assert (checkRep());
/*  38: 88 */     return new HashSet(((Map)this.ends.get(node)).keySet());
/*  39:    */   }
/*  40:    */   
/*  41:    */   public boolean addNode(N node)
/*  42:    */   {
/*  43: 99 */     assert (checkRep());
/*  44:100 */     if (!this.starts.containsKey(node))
/*  45:    */     {
/*  46:101 */       this.starts.put(node, new HashMap());
/*  47:102 */       this.ends.put(node, new HashMap());
/*  48:103 */       return true;
/*  49:    */     }
/*  50:105 */     return false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void addEdge(N startNode, N endNode, E label)
/*  54:    */   {
/*  55:119 */     assert (checkRep());
/*  56:120 */     if (!contains(startNode)) {
/*  57:120 */       addNode(startNode);
/*  58:    */     }
/*  59:121 */     if (!contains(endNode)) {
/*  60:121 */       addNode(endNode);
/*  61:    */     }
/*  62:122 */     if (((Map)this.starts.get(startNode)).containsKey(endNode))
/*  63:    */     {
/*  64:125 */       ((Set)((Map)this.starts.get(startNode)).get(endNode)).add(label);
/*  65:126 */       ((Set)((Map)this.ends.get(endNode)).get(startNode)).add(label);
/*  66:    */     }
/*  67:    */     else
/*  68:    */     {
/*  69:130 */       HashSet<E> temp = new HashSet();
/*  70:131 */       temp.add(label);
/*  71:132 */       ((Map)this.starts.get(startNode)).put(endNode, temp);
/*  72:133 */       ((Map)this.ends.get(endNode)).put(startNode, temp);
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public boolean contains(N node)
/*  77:    */   {
/*  78:146 */     assert (checkRep());
/*  79:147 */     return this.starts.containsKey(node);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean contains(N startNode, N endNode, E label)
/*  83:    */   {
/*  84:162 */     assert (checkRep());
/*  85:163 */     if (this.starts.containsKey(startNode)) {
/*  86:164 */       return ((Set)((Map)this.starts.get(startNode)).get(endNode)).contains(label);
/*  87:    */     }
/*  88:166 */     return false;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void removeNode(N node)
/*  92:    */   {
/*  93:176 */     assert (checkRep());
/*  94:178 */     for (N sNode : ((Map)this.starts.get(node)).keySet()) {
/*  95:179 */       ((Map)this.ends.get(sNode)).remove(node);
/*  96:    */     }
/*  97:181 */     for (N eNode : ((Map)this.ends.get(node)).keySet()) {
/*  98:182 */       ((Map)this.starts.get(eNode)).remove(node);
/*  99:    */     }
/* 100:185 */     this.starts.remove(node);
/* 101:186 */     this.ends.remove(node);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void removeEdge(N startNode, N endNode, E label)
/* 105:    */   {
/* 106:199 */     assert (checkRep());
/* 107:200 */     if (contains(startNode, endNode, label))
/* 108:    */     {
/* 109:201 */       ((Set)((Map)this.starts.get(startNode)).get(endNode)).remove(label);
/* 110:202 */       ((Set)((Map)this.ends.get(endNode)).get(startNode)).remove(label);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public HashSet<E> getEdgesBetween(N startNode, N endNode)
/* 115:    */   {
/* 116:217 */     assert (checkRep());
/* 117:219 */     if ((contains(startNode)) && (((Map)this.starts.get(startNode)).containsKey(endNode))) {
/* 118:220 */       return new HashSet((Collection)((Map)this.starts.get(startNode)).get(endNode));
/* 119:    */     }
/* 120:222 */     return new HashSet();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public HashSet<N> getNodes()
/* 124:    */   {
/* 125:234 */     assert (checkRep());
/* 126:    */     
/* 127:236 */     return new HashSet(this.starts.keySet());
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int size()
/* 131:    */   {
/* 132:246 */     assert (checkRep());
/* 133:247 */     return this.starts.keySet().size();
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String toString()
/* 137:    */   {
/* 138:251 */     String s = "";
/* 139:252 */     for (N node : getNodes())
/* 140:    */     {
/* 141:253 */       s = s + "\n{ " + node + " ";
/* 142:    */       Iterator localIterator3;
/* 143:254 */       for (Iterator localIterator2 = getSuccessors(node).iterator(); localIterator2.hasNext(); localIterator3.hasNext())
/* 144:    */       {
/* 145:254 */         N suc = (Object)localIterator2.next();
/* 146:255 */         localIterator3 = getEdgesBetween(node, suc).iterator(); continue;E edge = (Object)localIterator3.next();
/* 147:256 */         s = s + "\n   ( " + edge + " " + suc + " )";
/* 148:    */       }
/* 149:259 */       s = s + "\n} ";
/* 150:    */     }
/* 151:261 */     return s;
/* 152:    */   }
/* 153:    */   
/* 154:    */   private boolean checkRep()
/* 155:    */   {
/* 156:271 */     boolean check1 = false;boolean check2 = true;boolean check3 = true;
/* 157:273 */     if (this.starts.keySet().equals(this.ends.keySet())) {
/* 158:274 */       check1 = true;
/* 159:    */     }
/* 160:    */     Iterator localIterator2;
/* 161:276 */     for (Iterator localIterator1 = this.starts.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 162:    */     {
/* 163:276 */       N node1 = (Object)localIterator1.next();
/* 164:277 */       localIterator2 = ((Map)this.starts.get(node1)).keySet().iterator(); continue;N node2 = (Object)localIterator2.next();
/* 165:278 */       if (!((Map)this.ends.get(node2)).containsKey(node1)) {
/* 166:279 */         check2 = false;
/* 167:    */       }
/* 168:281 */       if (!((Set)((Map)this.starts.get(node1)).get(node2)).equals(((Map)this.ends.get(node2)).get(node1))) {
/* 169:282 */         check3 = false;
/* 170:    */       }
/* 171:    */     }
/* 172:286 */     return (check1) && (check2) && (check3);
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ADTs.DirectedMultiGraph
 * JD-Core Version:    0.7.0.1
 */