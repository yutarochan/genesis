/*   1:    */ package magicLess.specialMath;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class TransportationProblem
/*   9:    */ {
/*  10:    */   private static final double EPSILON = 1.E-009D;
/*  11:    */   
/*  12:    */   public static Tableau doHungarian(Tableau input)
/*  13:    */   {
/*  14: 19 */     input = input.clone();
/*  15: 20 */     assert (input.getRowLength() == input.getColLength());
/*  16: 21 */     hung1(input);
/*  17: 22 */     return input;
/*  18:    */   }
/*  19:    */   
/*  20:    */   private static void hung1(Tableau t)
/*  21:    */   {
/*  22: 29 */     int dim = t.getColLength();
/*  23: 30 */     for (int i = 0; i < dim; i++)
/*  24:    */     {
/*  25: 31 */       double smallest = -1.0D;
/*  26: 32 */       for (int j = 0; j < dim; j++)
/*  27:    */       {
/*  28: 33 */         assert (t.get(i, j) >= 0.0D);
/*  29: 34 */         if ((smallest < 0.0D) || (t.get(i, j) < smallest)) {
/*  30: 35 */           smallest = t.get(i, j);
/*  31:    */         }
/*  32:    */       }
/*  33: 37 */       for (int j = 0; j < dim; j++) {
/*  34: 38 */         t.set(i, j, t.get(i, j) - smallest);
/*  35:    */       }
/*  36:    */     }
/*  37: 41 */     hung2(t, dim);
/*  38:    */   }
/*  39:    */   
/*  40:    */   private static void hung2(Tableau t, int dim)
/*  41:    */   {
/*  42: 47 */     HashMap<Integer, Boolean> rowHash = new HashMap();
/*  43: 48 */     HashMap<Integer, Boolean> colHash = new HashMap();
/*  44: 49 */     for (int i = 0; i < dim; i++) {
/*  45: 50 */       if (!rowHash.containsKey(Integer.valueOf(i))) {
/*  46: 51 */         for (int j = 0; j < dim; j++) {
/*  47: 52 */           if (!colHash.containsKey(Integer.valueOf(j)))
/*  48:    */           {
/*  49: 53 */             boolean rowOK = !t.rowHasStarredCell(i);
/*  50: 54 */             boolean colOK = !t.colHasStarredCell(j);
/*  51: 55 */             if ((rowOK) && (colOK))
/*  52:    */             {
/*  53: 56 */               if (t.get(i, j) < 1.E-009D)
/*  54:    */               {
/*  55: 57 */                 t.star(i, j);
/*  56: 58 */                 colHash.put(Integer.valueOf(j), Boolean.valueOf(true));
/*  57: 59 */                 rowHash.put(Integer.valueOf(i), Boolean.valueOf(true));
/*  58:    */               }
/*  59:    */             }
/*  60: 62 */             else if (rowOK)
/*  61:    */             {
/*  62: 63 */               colHash.put(Integer.valueOf(j), Boolean.valueOf(true));
/*  63:    */             }
/*  64: 65 */             else if (colOK)
/*  65:    */             {
/*  66: 66 */               rowHash.put(Integer.valueOf(i), Boolean.valueOf(true));
/*  67:    */             }
/*  68:    */             else
/*  69:    */             {
/*  70: 69 */               rowHash.put(Integer.valueOf(i), Boolean.valueOf(true));
/*  71: 70 */               colHash.put(Integer.valueOf(j), Boolean.valueOf(true));
/*  72:    */             }
/*  73:    */           }
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77: 74 */     hung3(t, dim);
/*  78:    */   }
/*  79:    */   
/*  80:    */   private static void hung3(Tableau t, int dim)
/*  81:    */   {
/*  82: 81 */     int markCount = 0;
/*  83: 82 */     for (int j = 0; j < dim; j++) {
/*  84: 83 */       if (t.colHasStarredCell(j))
/*  85:    */       {
/*  86: 84 */         markCount++;
/*  87: 85 */         t.markCol(j);
/*  88:    */       }
/*  89:    */     }
/*  90: 88 */     if (markCount == dim) {
/*  91: 88 */       return;
/*  92:    */     }
/*  93: 89 */     hung4(t, dim);
/*  94:    */   }
/*  95:    */   
/*  96:    */   private static void hung4(Tableau t, int dim)
/*  97:    */   {
/*  98: 99 */     int[] coords = null;
/*  99:    */     do
/* 100:    */     {
/* 101:101 */       coords = findUnmarkedZero(t, dim);
/* 102:102 */       if (coords != null)
/* 103:    */       {
/* 104:103 */         t.prime(coords[0], coords[1]);
/* 105:104 */         int[] starredCoords = findStarredCoords(coords[0], t, dim);
/* 106:105 */         if (starredCoords != null)
/* 107:    */         {
/* 108:106 */           t.markRow(coords[0]);
/* 109:107 */           t.unmarkCol(starredCoords[1]);
/* 110:    */         }
/* 111:    */         else
/* 112:    */         {
/* 113:110 */           hung5(t, dim, coords[0], coords[1]);
/* 114:111 */           return;
/* 115:    */         }
/* 116:    */       }
/* 117:114 */     } while (coords != null);
/* 118:116 */     hung6(t, dim, getSmallestUnmarkedVal(t, dim));
/* 119:    */   }
/* 120:    */   
/* 121:    */   private static double getSmallestUnmarkedVal(Tableau t, int dim)
/* 122:    */   {
/* 123:119 */     double smallest = (1.0D / 0.0D);
/* 124:120 */     for (int i = 0; i < dim; i++) {
/* 125:121 */       if (!t.rowIsMarked(i)) {
/* 126:122 */         for (int j = 0; j < dim; j++) {
/* 127:123 */           if (!t.colIsMarked(j)) {
/* 128:124 */             smallest = Math.min(smallest, t.get(i, j));
/* 129:    */           }
/* 130:    */         }
/* 131:    */       }
/* 132:    */     }
/* 133:127 */     return smallest;
/* 134:    */   }
/* 135:    */   
/* 136:    */   private static int[] findStarredCoords(int rowNum, Tableau t, int dim)
/* 137:    */   {
/* 138:130 */     for (int j = 0; j < dim; j++) {
/* 139:131 */       if (t.isStarred(rowNum, j)) {
/* 140:132 */         return new int[] { rowNum, j };
/* 141:    */       }
/* 142:    */     }
/* 143:135 */     return null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private static int[] findUnmarkedZero(Tableau t, int dim)
/* 147:    */   {
/* 148:138 */     for (int i = 0; i < dim; i++) {
/* 149:139 */       if (!t.rowIsMarked(i)) {
/* 150:140 */         for (int j = 0; j < dim; j++) {
/* 151:141 */           if ((!t.colIsMarked(j)) && 
/* 152:142 */             (t.get(i, j) < 1.E-009D)) {
/* 153:143 */             return new int[] { i, j };
/* 154:    */           }
/* 155:    */         }
/* 156:    */       }
/* 157:    */     }
/* 158:147 */     return null;
/* 159:    */   }
/* 160:    */   
/* 161:    */   private static void hung5(Tableau t, int dim, int zeroI, int zeroJ)
/* 162:    */   {
/* 163:159 */     List<int[]> series = new ArrayList();
/* 164:160 */     int k = 0;
/* 165:161 */     int[] last = { zeroI, zeroJ };
/* 166:    */     do
/* 167:    */     {
/* 168:163 */       series.add(last);
/* 169:164 */       if (k % 2 == 0) {
/* 170:165 */         last = getStarredCoordsInCol(last[1], t, dim);
/* 171:    */       } else {
/* 172:167 */         last = getPrimedCoordsInRow(last[0], t, dim);
/* 173:    */       }
/* 174:169 */       k++;
/* 175:170 */     } while (last != null);
/* 176:172 */     for (int[] coords : series) {
/* 177:173 */       if (t.isStarred(coords[0], coords[1])) {
/* 178:173 */         t.unstar(coords[0], coords[1]);
/* 179:    */       }
/* 180:    */     }
/* 181:176 */     for (int[] coords : series) {
/* 182:177 */       if (t.isPrimed(coords[0], coords[1])) {
/* 183:177 */         t.star(coords[0], coords[1]);
/* 184:    */       }
/* 185:    */     }
/* 186:180 */     for (int i = 0; i < dim; i++) {
/* 187:181 */       for (int j = 0; j < dim; j++) {
/* 188:182 */         t.unprime(i, j);
/* 189:    */       }
/* 190:    */     }
/* 191:186 */     t.clearAllMarks();
/* 192:187 */     hung3(t, dim);
/* 193:    */   }
/* 194:    */   
/* 195:    */   private static int[] getStarredCoordsInCol(int j, Tableau t, int dim)
/* 196:    */   {
/* 197:190 */     for (int i = 0; i < dim; i++) {
/* 198:191 */       if (t.isStarred(i, j)) {
/* 199:191 */         return new int[] { i, j };
/* 200:    */       }
/* 201:    */     }
/* 202:193 */     return null;
/* 203:    */   }
/* 204:    */   
/* 205:    */   private static int[] getPrimedCoordsInRow(int i, Tableau t, int dim)
/* 206:    */   {
/* 207:196 */     for (int j = 0; j < dim; j++) {
/* 208:197 */       if (t.isPrimed(i, j)) {
/* 209:197 */         return new int[] { i, j };
/* 210:    */       }
/* 211:    */     }
/* 212:199 */     return null;
/* 213:    */   }
/* 214:    */   
/* 215:    */   private static void hung6(Tableau t, int dim, double smallest)
/* 216:    */   {
/* 217:207 */     for (int i = 0; i < dim; i++)
/* 218:    */     {
/* 219:208 */       boolean rm = t.rowIsMarked(i);
/* 220:209 */       for (int j = 0; j < dim; j++)
/* 221:    */       {
/* 222:210 */         boolean cnm = !t.colIsMarked(j);
/* 223:211 */         if (rm)
/* 224:    */         {
/* 225:212 */           if (!cnm) {
/* 226:213 */             t.set(i, j, t.get(i, j) + smallest);
/* 227:    */           }
/* 228:    */         }
/* 229:215 */         else if (cnm) {
/* 230:216 */           t.set(i, j, t.get(i, j) - smallest);
/* 231:    */         }
/* 232:    */       }
/* 233:    */     }
/* 234:221 */     hung4(t, dim);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public static void main(String[] args)
/* 238:    */   {
/* 239:225 */     Tableau t = new Tableau(3, 3);
/* 240:226 */     t.set(0, 0, 1.0D);
/* 241:227 */     t.set(0, 1, 3.0D);
/* 242:228 */     t.set(0, 2, 4.0D);
/* 243:229 */     t.set(1, 0, 6.0D);
/* 244:230 */     t.set(1, 1, 5.0D);
/* 245:231 */     t.set(1, 2, 7.0D);
/* 246:232 */     t.set(2, 0, 10.0D);
/* 247:233 */     t.set(2, 1, 11.0D);
/* 248:234 */     t.set(2, 2, 42.0D);
/* 249:235 */     System.out.println(t);
/* 250:236 */     System.out.println(doHungarian(t));
/* 251:237 */     t.set(0, 0, 1.0D);
/* 252:238 */     t.set(0, 1, 2.0D);
/* 253:239 */     t.set(0, 2, 3.0D);
/* 254:240 */     t.set(1, 0, 3.0D);
/* 255:241 */     t.set(1, 1, 5.0D);
/* 256:242 */     t.set(1, 2, 6.0D);
/* 257:243 */     t.set(2, 0, 7.0D);
/* 258:244 */     t.set(2, 1, 8.0D);
/* 259:245 */     t.set(2, 2, 9.0D);
/* 260:246 */     System.out.println(t);
/* 261:247 */     System.out.println(doHungarian(t));
/* 262:    */   }
/* 263:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.specialMath.TransportationProblem
 * JD-Core Version:    0.7.0.1
 */