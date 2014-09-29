/*   1:    */ package matthewFay;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Iterator;
/*   5:    */ 
/*   6:    */ public abstract class HClusterer<T>
/*   7:    */ {
/*   8:    */   public abstract float sim(T paramT1, T paramT2);
/*   9:    */   
/*  10:    */   public static class Cluster<T>
/*  11:    */     extends ArrayList<T>
/*  12:    */   {
/*  13:  9 */     public Cluster<T> parent = null;
/*  14: 10 */     public ArrayList<Cluster<T>> children = new ArrayList();
/*  15:    */     public T centroid;
/*  16:    */     
/*  17:    */     public ArrayList<Cluster<T>> allChildren()
/*  18:    */     {
/*  19: 15 */       ArrayList<Cluster<T>> ac = new ArrayList();
/*  20: 16 */       ac.addAll(this.children);
/*  21: 17 */       Iterator localIterator = this.children.iterator();
/*  22: 17 */       if (localIterator.hasNext())
/*  23:    */       {
/*  24: 17 */         Cluster<T> child = (Cluster)localIterator.next();
/*  25: 18 */         if (child != null) {
/*  26: 19 */           return ac;
/*  27:    */         }
/*  28: 20 */         return ac;
/*  29:    */       }
/*  30: 24 */       return ac;
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public Cluster<T> cluster(ArrayList<T> elts)
/*  35:    */   {
/*  36: 35 */     ArrayList<Cluster<T>> topClusters = new ArrayList();
/*  37: 36 */     for (int x = 0; x < elts.size(); x++)
/*  38:    */     {
/*  39: 37 */       Cluster<T> cluster = new Cluster();
/*  40: 38 */       T X = elts.get(x);
/*  41: 39 */       cluster.add(X);
/*  42: 40 */       topClusters.add(cluster);
/*  43:    */     }
/*  44: 43 */     while (topClusters.size() > 1)
/*  45:    */     {
/*  46: 45 */       int maxX = -1;
/*  47: 46 */       int maxY = -1;
/*  48: 47 */       float maxSim = (1.0F / -1.0F);
/*  49: 49 */       for (int x = 0; x < topClusters.size() - 1; x++)
/*  50:    */       {
/*  51: 50 */         Cluster<T> X = (Cluster)topClusters.get(x);
/*  52: 51 */         for (int y = x + 1; y < topClusters.size(); y++)
/*  53:    */         {
/*  54: 52 */           Cluster<T> Y = (Cluster)topClusters.get(y);
/*  55: 53 */           float sim = simClusters(X, Y);
/*  56: 54 */           if (sim > maxSim)
/*  57:    */           {
/*  58: 55 */             maxX = x;
/*  59: 56 */             maxY = y;
/*  60: 57 */             maxSim = sim;
/*  61:    */           }
/*  62:    */         }
/*  63:    */       }
/*  64: 62 */       Cluster<T> A = (Cluster)topClusters.get(maxX);
/*  65: 63 */       Cluster<T> B = (Cluster)topClusters.get(maxY);
/*  66: 64 */       Cluster<T> parent = mergeClusters(A, B);
/*  67: 65 */       A.parent = parent;
/*  68: 66 */       B.parent = parent;
/*  69: 67 */       parent.children.add(A);
/*  70: 68 */       parent.children.add(B);
/*  71: 69 */       topClusters.add(parent);
/*  72: 70 */       topClusters.remove(A);
/*  73: 71 */       topClusters.remove(B);
/*  74:    */     }
/*  75: 74 */     return (Cluster)topClusters.get(0);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Cluster<T> mergeClusters(Cluster<T> A, Cluster<T> B)
/*  79:    */   {
/*  80: 78 */     Cluster<T> parent = new Cluster();
/*  81: 79 */     parent.addAll(A);
/*  82: 80 */     parent.addAll(B);
/*  83: 81 */     if (parent.size() > 2)
/*  84:    */     {
/*  85: 83 */       float maxSim = (1.0F / -1.0F);
/*  86: 84 */       for (T elt : parent)
/*  87:    */       {
/*  88: 86 */         float sim = calculateCentroidDistance(elt, parent);
/*  89: 87 */         if (sim > maxSim)
/*  90:    */         {
/*  91: 89 */           maxSim = sim;
/*  92: 90 */           parent.centroid = elt;
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98: 94 */       parent.centroid = parent.get(0);
/*  99:    */     }
/* 100: 96 */     return parent;
/* 101:    */   }
/* 102:    */   
/* 103:    */   private float calculateCentroidDistance(T a, ArrayList<T> elts)
/* 104:    */   {
/* 105:101 */     float distance = 0.0F;
/* 106:102 */     for (T elt : elts) {
/* 107:104 */       if (elt != a) {
/* 108:105 */         distance += sim(a, elt);
/* 109:    */       }
/* 110:    */     }
/* 111:107 */     return distance;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public float simClusters(Cluster<T> A, Cluster<T> B)
/* 115:    */   {
/* 116:111 */     float maxSim = (1.0F / -1.0F);
/* 117:112 */     if (A.size() > 2)
/* 118:    */     {
/* 119:113 */       if (B.size() > 2) {
/* 120:114 */         maxSim = sim(A.centroid, B.centroid);
/* 121:    */       } else {
/* 122:116 */         for (T b : B)
/* 123:    */         {
/* 124:117 */           float sim = sim(A.centroid, b);
/* 125:118 */           if (sim > maxSim) {
/* 126:119 */             maxSim = sim;
/* 127:    */           }
/* 128:    */         }
/* 129:    */       }
/* 130:    */     }
/* 131:    */     else {
/* 132:123 */       for (T a : A) {
/* 133:124 */         if (B.size() > 2)
/* 134:    */         {
/* 135:125 */           float sim = sim(a, B.centroid);
/* 136:126 */           if (sim > maxSim) {
/* 137:127 */             maxSim = sim;
/* 138:    */           }
/* 139:    */         }
/* 140:    */         else
/* 141:    */         {
/* 142:129 */           for (T b : B)
/* 143:    */           {
/* 144:130 */             float sim = sim(a, b);
/* 145:131 */             if (sim > maxSim) {
/* 146:132 */               maxSim = sim;
/* 147:    */             }
/* 148:    */           }
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:137 */     return maxSim;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public static void main(String[] args)
/* 156:    */   {
/* 157:143 */     HClusterer<Character> clusterer = new HClusterer()
/* 158:    */     {
/* 159:    */       public float sim(Character a, Character b)
/* 160:    */       {
/* 161:147 */         float aFloat = a.charValue();
/* 162:148 */         float bFloat = b.charValue();
/* 163:149 */         float diff = aFloat - bFloat;
/* 164:150 */         float sim = -Math.abs(diff);
/* 165:    */         
/* 166:152 */         return sim;
/* 167:    */       }
/* 168:157 */     };
/* 169:158 */     String strA = "ABCFGHIJKLMOPQSTUVWXYZ";
/* 170:    */     
/* 171:    */ 
/* 172:161 */     ArrayList<Character> seqA = new ArrayList(strA.length());
/* 173:162 */     for (char c : strA.toCharArray()) {
/* 174:162 */       seqA.add(Character.valueOf(c));
/* 175:    */     }
/* 176:164 */     Cluster<Character> topCluster = clusterer.cluster(seqA);
/* 177:    */   }
/* 178:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.HClusterer
 * JD-Core Version:    0.7.0.1
 */