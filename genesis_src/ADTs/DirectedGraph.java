/*   1:    */ 
/*   2:    */ 
/*   3:    */ java.util.HashMap
/*   4:    */ java.util.HashSet
/*   5:    */ java.util.Iterator
/*   6:    */ java.util.Map
/*   7:    */ java.util.Set
/*   8:    */ 
/*   9:    */ DirectedGraph, 
/*  10:    */ 
/*  11:    */   , , starts
/*  12:    */   , , ends
/*  13:    */   
/*  14:    */   DirectedGraph
/*  15:    */   
/*  16: 28 */     starts = new HashMap();
/*  17: 29 */     this.ends = new HashMap();
/*  18: 30 */     assert (checkRep());
/*  19:    */   }
/*  20:    */   
/*  21:    */   public DirectedGraph(DirectedGraph<N, E> g)
/*  22:    */   {
/*  23: 40 */     this.starts = new HashMap(g.starts);
/*  24: 41 */     this.ends = new HashMap(g.ends);
/*  25: 42 */     assert (checkRep());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public HashSet<N> getSuccessors(N node)
/*  29:    */   {
/*  30: 58 */     assert (checkRep());
/*  31: 59 */     if (this.starts.containsKey(node)) {
/*  32: 60 */       return new HashSet(((Map)this.starts.get(node)).keySet());
/*  33:    */     }
/*  34: 62 */     return new HashSet();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public HashSet<N> getPredecessors(N node)
/*  38:    */   {
/*  39: 78 */     assert (checkRep());
/*  40: 79 */     if (this.ends.containsKey(node)) {
/*  41: 80 */       return new HashSet(((Map)this.ends.get(node)).keySet());
/*  42:    */     }
/*  43: 82 */     return new HashSet();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean addNode(N node)
/*  47:    */   {
/*  48: 93 */     assert (checkRep());
/*  49: 94 */     if (!this.starts.containsKey(node))
/*  50:    */     {
/*  51: 95 */       this.starts.put(node, new HashMap());
/*  52: 96 */       this.ends.put(node, new HashMap());
/*  53: 97 */       return true;
/*  54:    */     }
/*  55: 99 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void addEdge(N startNode, N endNode, E label)
/*  59:    */   {
/*  60:115 */     assert (checkRep());
/*  61:116 */     if (!contains(startNode)) {
/*  62:116 */       addNode(startNode);
/*  63:    */     }
/*  64:117 */     if (!contains(endNode)) {
/*  65:117 */       addNode(endNode);
/*  66:    */     }
/*  67:118 */     ((Map)this.starts.get(startNode)).put(endNode, label);
/*  68:119 */     ((Map)this.ends.get(endNode)).put(startNode, label);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean contains(N node)
/*  72:    */   {
/*  73:131 */     assert (checkRep());
/*  74:132 */     return this.starts.containsKey(node);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public boolean contains(N startNode, N endNode)
/*  78:    */   {
/*  79:144 */     assert (checkRep());
/*  80:145 */     if (this.starts.containsKey(startNode)) {
/*  81:146 */       return ((Map)this.starts.get(startNode)).containsKey(endNode);
/*  82:    */     }
/*  83:148 */     return false;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void removeNode(N node)
/*  87:    */   {
/*  88:158 */     assert (checkRep());
/*  89:160 */     for (N sNode : ((Map)this.starts.get(node)).keySet()) {
/*  90:161 */       ((Map)this.ends.get(sNode)).remove(node);
/*  91:    */     }
/*  92:163 */     for (N eNode : ((Map)this.ends.get(node)).keySet()) {
/*  93:164 */       ((Map)this.starts.get(eNode)).remove(node);
/*  94:    */     }
/*  95:167 */     this.starts.remove(node);
/*  96:168 */     this.ends.remove(node);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void removeEdge(N startNode, N endNode)
/* 100:    */   {
/* 101:179 */     assert (checkRep());
/* 102:180 */     if (contains(startNode, endNode))
/* 103:    */     {
/* 104:181 */       ((Map)this.starts.get(startNode)).remove(endNode);
/* 105:182 */       ((Map)this.ends.get(endNode)).remove(startNode);
/* 106:    */     }
/* 107:    */   }
/* 108:    */   
/* 109:    */   public E getEdgeBetween(N startNode, N endNode)
/* 110:    */   {
/* 111:194 */     assert (checkRep());
/* 112:195 */     if ((contains(startNode)) && (((Map)this.starts.get(startNode)).containsKey(endNode))) {
/* 113:196 */       return ((Map)this.starts.get(startNode)).get(endNode);
/* 114:    */     }
/* 115:198 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public HashSet<N> getNodes()
/* 119:    */   {
/* 120:210 */     assert (checkRep());
/* 121:    */     
/* 122:212 */     return new HashSet(this.starts.keySet());
/* 123:    */   }
/* 124:    */   
/* 125:    */   public int size()
/* 126:    */   {
/* 127:222 */     assert (checkRep());
/* 128:223 */     return this.starts.keySet().size();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String toString()
/* 132:    */   {
/* 133:227 */     String s = "";
/* 134:228 */     for (N node : getNodes())
/* 135:    */     {
/* 136:229 */       s = s + "\n{ " + node + " ";
/* 137:230 */       for (N suc : getSuccessors(node)) {
/* 138:231 */         s = s + "\n   ( " + getEdgeBetween(node, suc) + " " + suc + " )";
/* 139:    */       }
/* 140:233 */       s = s + "\n} ";
/* 141:    */     }
/* 142:235 */     return s;
/* 143:    */   }
/* 144:    */   
/* 145:    */   private boolean checkRep()
/* 146:    */   {
/* 147:245 */     boolean check1 = false;boolean check2 = true;boolean check3 = true;
/* 148:247 */     if (this.starts.keySet().equals(this.ends.keySet())) {
/* 149:248 */       check1 = true;
/* 150:    */     }
/* 151:    */     Iterator localIterator2;
/* 152:250 */     for (Iterator localIterator1 = this.starts.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 153:    */     {
/* 154:250 */       N node1 = (Object)localIterator1.next();
/* 155:251 */       localIterator2 = ((Map)this.starts.get(node1)).keySet().iterator(); continue;N node2 = (Object)localIterator2.next();
/* 156:252 */       if (!((Map)this.ends.get(node2)).containsKey(node1)) {
/* 157:253 */         check2 = false;
/* 158:    */       }
/* 159:255 */       if (!((Map)this.starts.get(node1)).get(node2).equals(((Map)this.ends.get(node2)).get(node1))) {
/* 160:256 */         check3 = false;
/* 161:    */       }
/* 162:    */     }
/* 163:260 */     return (check1) && (check2) && (check3);
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     ADTs.DirectedGraph
 * JD-Core Version:    0.7.0.1
 */