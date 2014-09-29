/*   1:    */ package ADTs;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public class UniGraph<N, E>
/*  10:    */ {
/*  11:    */   private Map<N, Map<N, E>> map;
/*  12:    */   
/*  13:    */   public UniGraph()
/*  14:    */   {
/*  15: 28 */     this.map = new HashMap();
/*  16: 29 */     assert (checkRep());
/*  17:    */   }
/*  18:    */   
/*  19:    */   public UniGraph(UniGraph<N, E> g)
/*  20:    */   {
/*  21: 39 */     this.map = new HashMap(g.map);
/*  22: 40 */     assert (checkRep());
/*  23:    */   }
/*  24:    */   
/*  25:    */   public HashSet<N> getSuccessors(N node)
/*  26:    */   {
/*  27: 56 */     assert (checkRep());
/*  28: 57 */     return new HashSet(((Map)this.map.get(node)).keySet());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean addNode(N node)
/*  32:    */   {
/*  33: 68 */     assert (checkRep());
/*  34: 69 */     if (!this.map.containsKey(node))
/*  35:    */     {
/*  36: 70 */       this.map.put(node, new HashMap());
/*  37: 71 */       return true;
/*  38:    */     }
/*  39: 73 */     return false;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void addEdge(N startNode, N endNode, E label)
/*  43:    */   {
/*  44: 89 */     assert (checkRep());
/*  45: 90 */     if (!contains(startNode)) {
/*  46: 90 */       addNode(startNode);
/*  47:    */     }
/*  48: 91 */     if (!contains(endNode)) {
/*  49: 91 */       addNode(endNode);
/*  50:    */     }
/*  51: 92 */     ((Map)this.map.get(startNode)).put(endNode, label);
/*  52: 93 */     ((Map)this.map.get(endNode)).put(startNode, label);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean contains(N node)
/*  56:    */   {
/*  57:105 */     assert (checkRep());
/*  58:106 */     return this.map.containsKey(node);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public boolean contains(N startNode, N endNode, E label)
/*  62:    */   {
/*  63:121 */     assert (checkRep());
/*  64:122 */     if ((this.map.containsKey(startNode)) && (this.map.containsKey(endNode))) {
/*  65:123 */       return ((Map)this.map.get(startNode)).get(endNode).equals(label);
/*  66:    */     }
/*  67:125 */     return false;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void removeNode(N node)
/*  71:    */   {
/*  72:135 */     assert (checkRep());
/*  73:137 */     for (N endNode : getSuccessors(node)) {
/*  74:138 */       ((Map)this.map.get(endNode)).remove(node);
/*  75:    */     }
/*  76:140 */     this.map.remove(node);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void removeEdge(N startNode, N endNode)
/*  80:    */   {
/*  81:151 */     assert (checkRep());
/*  82:152 */     if ((contains(startNode)) && (((Map)this.map.get(startNode)).containsKey(endNode)))
/*  83:    */     {
/*  84:153 */       ((Map)this.map.get(startNode)).remove(endNode);
/*  85:154 */       ((Map)this.map.get(endNode)).remove(startNode);
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   public E getEdge(N startNode, N endNode)
/*  90:    */   {
/*  91:166 */     assert (checkRep());
/*  92:167 */     if ((contains(startNode)) && (((Map)this.map.get(startNode)).containsKey(endNode))) {
/*  93:168 */       return ((Map)this.map.get(startNode)).get(endNode);
/*  94:    */     }
/*  95:170 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public HashSet<N> getNodes()
/*  99:    */   {
/* 100:182 */     assert (checkRep());
/* 101:    */     
/* 102:184 */     return new HashSet(this.map.keySet());
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int size()
/* 106:    */   {
/* 107:194 */     assert (checkRep());
/* 108:195 */     return this.map.keySet().size();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String toString()
/* 112:    */   {
/* 113:199 */     String s = "";
/* 114:200 */     for (N node : getNodes())
/* 115:    */     {
/* 116:201 */       s = s + "\n{ " + node + " ";
/* 117:202 */       for (N suc : getSuccessors(node)) {
/* 118:203 */         s = s + "\n   ( " + getEdge(node, suc) + " " + suc + " )";
/* 119:    */       }
/* 120:205 */       s = s + "\n} ";
/* 121:    */     }
/* 122:207 */     return s;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private boolean checkRep()
/* 126:    */   {
/* 127:    */     Iterator localIterator2;
/* 128:217 */     for (Iterator localIterator1 = this.map.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 129:    */     {
/* 130:217 */       N n = (Object)localIterator1.next();
/* 131:218 */       localIterator2 = ((Map)this.map.get(n)).keySet().iterator(); continue;N suc = (Object)localIterator2.next();
/* 132:219 */       if (!((Map)this.map.get(suc)).containsKey(n)) {
/* 133:220 */         return false;
/* 134:    */       }
/* 135:223 */       if (!((Map)this.map.get(n)).get(suc).equals(((Map)this.map.get(suc)).get(n))) {
/* 136:224 */         return false;
/* 137:    */       }
/* 138:    */     }
/* 139:230 */     return true;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ADTs.UniGraph
 * JD-Core Version:    0.7.0.1
 */