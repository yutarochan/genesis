/*   1:    */ package matthewFay;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Random;
/*   7:    */ 
/*   8:    */ public abstract class KClusterer<T>
/*   9:    */ {
/*  10:    */   private int k;
/*  11:    */   
/*  12:    */   private void genStats(Cluster<T> cluster)
/*  13:    */   {
/*  14: 19 */     float sum = 0.0F;
/*  15: 20 */     for (T t : cluster) {
/*  16: 21 */       sum += sim(t, cluster.Centroid);
/*  17:    */     }
/*  18: 23 */     cluster.averageSim = (sum / cluster.size());
/*  19: 24 */     sum = 0.0F;
/*  20: 25 */     for (T t : cluster)
/*  21:    */     {
/*  22: 26 */       float score = sim(t, cluster.Centroid);
/*  23: 27 */       sum += (score - cluster.averageSim) * (score - cluster.averageSim);
/*  24:    */     }
/*  25: 29 */     cluster.variance = (sum / cluster.size());
/*  26:    */   }
/*  27:    */   
/*  28:    */   public KClusterer(int k)
/*  29:    */   {
/*  30: 34 */     this.k = k;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public ArrayList<Cluster<T>> cluster(ArrayList<T> elts)
/*  34:    */   {
/*  35: 43 */     ArrayList<Cluster<T>> clusters = new ArrayList();
/*  36: 44 */     int kIter = 0;
/*  37: 45 */     for (kIter = 0; kIter < this.k; kIter++) {
/*  38: 46 */       clusters.add(new Cluster());
/*  39:    */     }
/*  40: 48 */     kIter = 0;
/*  41: 49 */     Random r = new Random();
/*  42: 51 */     while (!elts.isEmpty())
/*  43:    */     {
/*  44: 52 */       int eltIndex = r.nextInt(elts.size());
/*  45: 53 */       T toAdd = elts.get(eltIndex);
/*  46: 54 */       ((Cluster)clusters.get(kIter)).add(toAdd);
/*  47: 55 */       elts.remove(eltIndex);
/*  48: 56 */       kIter = (kIter + 1) % this.k;
/*  49:    */     }
/*  50: 62 */     boolean swapOccured = true;
/*  51:    */     ArrayList<T> tempList;
/*  52: 64 */     for (; swapOccured; kIter < this.k)
/*  53:    */     {
/*  54: 66 */       swapOccured = false;
/*  55: 71 */       for (kIter = 0; kIter < this.k; kIter++)
/*  56:    */       {
/*  57: 73 */         Cluster<T> cluster = (Cluster)clusters.get(kIter);
/*  58: 74 */         float maxDistance = (1.0F / -1.0F);
/*  59: 75 */         for (T elt : cluster)
/*  60:    */         {
/*  61: 77 */           float distance = calculateCentroidDistance(elt, cluster);
/*  62: 78 */           if (distance > maxDistance)
/*  63:    */           {
/*  64: 80 */             maxDistance = distance;
/*  65: 81 */             cluster.Centroid = elt;
/*  66:    */           }
/*  67:    */         }
/*  68:    */       }
/*  69: 90 */       kIter = 0; continue;
/*  70:    */       
/*  71: 92 */       Cluster<T> cluster = (Cluster)clusters.get(kIter);
/*  72:    */       
/*  73: 94 */       tempList = new ArrayList();
/*  74: 95 */       for (T elt : cluster) {
/*  75: 97 */         tempList.add(elt);
/*  76:    */       }
/*  77:    */       int kIter2;
/*  78:100 */       for (??? = tempList.iterator(); ???.hasNext(); kIter2 < this.k)
/*  79:    */       {
/*  80:100 */         T elt = (Object)???.next();
/*  81:101 */         Cluster<T> currentCluster = cluster;
/*  82:102 */         elt.equals(cluster.Centroid);
/*  83:103 */         float distance = sim(elt, cluster.Centroid);
/*  84:    */         
/*  85:105 */         kIter2 = 0; continue;
/*  86:107 */         if (kIter2 != kIter)
/*  87:    */         {
/*  88:109 */           Cluster<T> targetCluster = (Cluster)clusters.get(kIter2);
/*  89:    */           
/*  90:111 */           float targetDistance = sim(elt, targetCluster.Centroid);
/*  91:112 */           if (targetDistance > distance)
/*  92:    */           {
/*  93:115 */             distance = targetDistance;
/*  94:116 */             currentCluster.remove(elt);
/*  95:117 */             targetCluster.add(elt);
/*  96:118 */             currentCluster = targetCluster;
/*  97:119 */             swapOccured = true;
/*  98:    */           }
/*  99:    */         }
/* 100:105 */         kIter2++;
/* 101:    */       }
/* 102: 90 */       kIter++;
/* 103:    */     }
/* 104:127 */     for (Cluster<T> cluster : clusters) {
/* 105:128 */       genStats(cluster);
/* 106:    */     }
/* 107:130 */     return clusters;
/* 108:    */   }
/* 109:    */   
/* 110:    */   private float calculateCentroidDistance(T a, ArrayList<T> elts)
/* 111:    */   {
/* 112:135 */     float distance = 0.0F;
/* 113:136 */     for (T elt : elts) {
/* 114:138 */       if (elt != a) {
/* 115:139 */         distance += sim(a, elt);
/* 116:    */       }
/* 117:    */     }
/* 118:141 */     return distance;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public abstract float sim(T paramT1, T paramT2);
/* 122:    */   
/* 123:    */   public static void main(String[] args)
/* 124:    */   {
/* 125:148 */     KClusterer<Character> clusterer = new KClusterer(5)
/* 126:    */     {
/* 127:    */       public float sim(Character a, Character b)
/* 128:    */       {
/* 129:152 */         float aFloat = a.charValue();
/* 130:153 */         float bFloat = b.charValue();
/* 131:154 */         float diff = aFloat - bFloat;
/* 132:155 */         float sim = -Math.abs(diff);
/* 133:    */         
/* 134:157 */         return sim;
/* 135:    */       }
/* 136:162 */     };
/* 137:163 */     String strA = "ABCFGHIJKLMOPQSTUVWXYZ";
/* 138:    */     
/* 139:    */ 
/* 140:166 */     ArrayList<Character> seqA = new ArrayList(strA.length());
/* 141:167 */     for (char c : strA.toCharArray()) {
/* 142:167 */       seqA.add(Character.valueOf(c));
/* 143:    */     }
/* 144:169 */     ArrayList<Cluster<Character>> clusters = clusterer.cluster(seqA);
/* 145:    */     
/* 146:171 */     String out = "Clustering Output\n";
/* 147:172 */     for (??? = clusters.iterator(); ((Iterator)???).hasNext();)
/* 148:    */     {
/* 149:172 */       Object cluster = (Cluster)((Iterator)???).next();
/* 150:    */       
/* 151:174 */       out = out + "Cluster, avg, var" + ((Cluster)cluster).averageSim + ", " + ((Cluster)cluster).variance + "\n";
/* 152:175 */       out = out + "Centroid: " + ((Cluster)cluster).Centroid + "\n";
/* 153:176 */       for (Character c : (Cluster)cluster) {
/* 154:178 */         out = out + c;
/* 155:    */       }
/* 156:180 */       out = out + "\n";
/* 157:    */     }
/* 158:183 */     System.out.println(out);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static class Cluster<T>
/* 162:    */     extends ArrayList<T>
/* 163:    */   {
/* 164:    */     public T Centroid;
/* 165:    */     public float averageSim;
/* 166:    */     public float variance;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.KClusterer
 * JD-Core Version:    0.7.0.1
 */