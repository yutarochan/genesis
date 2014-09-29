/*   1:    */ package magicLess.specialMath;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class Tableau
/*   9:    */ {
/*  10:    */   private static final double EPSILON = 1.E-009D;
/*  11:    */   private List<Boolean> markedRows;
/*  12:    */   private List<Boolean> markedCols;
/*  13:    */   private List<List<Cell>> cList;
/*  14:    */   private int rowLength;
/*  15:    */   private int colLength;
/*  16:    */   
/*  17:    */   private final class Cell
/*  18:    */   {
/*  19:    */     Double dval;
/*  20:  8 */     public boolean starred = false;
/*  21:  9 */     public boolean primed = false;
/*  22:    */     
/*  23:    */     public Cell(double d)
/*  24:    */     {
/*  25: 11 */       this.dval = Double.valueOf(d);
/*  26:    */     }
/*  27:    */     
/*  28:    */     public Cell(Double d)
/*  29:    */     {
/*  30: 14 */       this.dval = d;
/*  31:    */     }
/*  32:    */     
/*  33:    */     public Cell()
/*  34:    */     {
/*  35: 17 */       this.dval = Double.valueOf(0.0D);
/*  36:    */     }
/*  37:    */     
/*  38:    */     public Cell(Cell c)
/*  39:    */     {
/*  40: 20 */       this.starred = c.starred;
/*  41: 21 */       this.primed = c.primed;
/*  42: 22 */       this.dval = c.dval;
/*  43:    */     }
/*  44:    */     
/*  45:    */     public String toString()
/*  46:    */     {
/*  47: 25 */       String s = String.format("%2.2f", new Object[] { Double.valueOf(this.dval.doubleValue()) });
/*  48: 26 */       if (this.starred) {
/*  49: 26 */         s = s + "*";
/*  50:    */       }
/*  51: 27 */       if (this.primed) {
/*  52: 27 */         s = s + "'";
/*  53:    */       }
/*  54: 28 */       return s;
/*  55:    */     }
/*  56:    */     
/*  57:    */     public double doubleValue()
/*  58:    */     {
/*  59: 31 */       return this.dval.doubleValue();
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public Tableau clone()
/*  64:    */   {
/*  65: 42 */     Tableau t = new Tableau(this.colLength, this.rowLength);
/*  66: 43 */     t.markedCols = new ArrayList(this.markedCols);
/*  67: 44 */     t.markedRows = new ArrayList(this.markedRows);
/*  68: 45 */     t.cList = new ArrayList(this.colLength);
/*  69: 46 */     for (int i = 0; i < this.colLength; i++)
/*  70:    */     {
/*  71: 47 */       List<Cell> cd = new ArrayList(this.rowLength);
/*  72: 48 */       for (int j = 0; j < this.rowLength; j++) {
/*  73: 49 */         cd.add(getCell(i, j));
/*  74:    */       }
/*  75: 51 */       t.cList.add(cd);
/*  76:    */     }
/*  77: 53 */     return t;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Tableau(int numRows, int numCols)
/*  81:    */   {
/*  82: 56 */     this.rowLength = numCols;
/*  83: 57 */     this.colLength = numRows;
/*  84: 58 */     this.cList = new ArrayList(numRows);
/*  85: 59 */     for (int i = 0; i < numRows; i++)
/*  86:    */     {
/*  87: 60 */       List<Cell> cd = new ArrayList(numCols);
/*  88: 61 */       for (int j = 0; j < this.rowLength; j++) {
/*  89: 62 */         cd.add(new Cell(0.0D));
/*  90:    */       }
/*  91: 64 */       this.cList.add(cd);
/*  92:    */     }
/*  93: 66 */     this.markedCols = new ArrayList(numCols);
/*  94: 67 */     this.markedRows = new ArrayList(numRows);
/*  95: 68 */     for (int i = 0; i < numRows; i++) {
/*  96: 69 */       this.markedRows.add(Boolean.valueOf(false));
/*  97:    */     }
/*  98: 71 */     for (int i = 0; i < numCols; i++) {
/*  99: 72 */       this.markedCols.add(Boolean.valueOf(false));
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean rowHasStarredCell(int rowNum)
/* 104:    */   {
/* 105: 78 */     List<Cell> row = getRowCells(rowNum);
/* 106: 79 */     for (Cell c : row) {
/* 107: 80 */       if (c.starred) {
/* 108: 80 */         return true;
/* 109:    */       }
/* 110:    */     }
/* 111: 82 */     return false;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public int getRowLength()
/* 115:    */   {
/* 116: 85 */     return this.rowLength;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void markRow(int rowNum)
/* 120:    */   {
/* 121: 88 */     assert ((rowNum >= 0) && (rowNum < this.colLength));
/* 122: 89 */     this.markedRows.set(rowNum, Boolean.valueOf(true));
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void unmarkRow(int rowNum)
/* 126:    */   {
/* 127: 92 */     assert ((rowNum >= 0) && (rowNum < this.colLength));
/* 128: 93 */     this.markedRows.set(rowNum, Boolean.valueOf(false));
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void unmarkAllRows()
/* 132:    */   {
/* 133: 96 */     for (int i = 0; i < this.colLength; i++) {
/* 134: 97 */       this.markedRows.set(i, Boolean.valueOf(false));
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean rowIsMarked(int rowNum)
/* 139:    */   {
/* 140:101 */     assert ((rowNum >= 0) && (rowNum < this.colLength));
/* 141:102 */     return ((Boolean)this.markedRows.get(rowNum)).booleanValue();
/* 142:    */   }
/* 143:    */   
/* 144:    */   private List<Cell> getRowCells(int rowNum)
/* 145:    */   {
/* 146:105 */     assert ((rowNum >= 0) && (rowNum < this.colLength));
/* 147:106 */     return new ArrayList((Collection)this.cList.get(rowNum));
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean colHasStarredCell(int colNum)
/* 151:    */   {
/* 152:111 */     List<Cell> col = getColCells(colNum);
/* 153:112 */     for (Cell c : col) {
/* 154:113 */       if (c.starred) {
/* 155:113 */         return true;
/* 156:    */       }
/* 157:    */     }
/* 158:115 */     return false;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getColLength()
/* 162:    */   {
/* 163:118 */     return this.colLength;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void markCol(int colNum)
/* 167:    */   {
/* 168:121 */     assert ((colNum >= 0) && (colNum < this.rowLength));
/* 169:122 */     this.markedCols.set(colNum, Boolean.valueOf(true));
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void unmarkCol(int colNum)
/* 173:    */   {
/* 174:125 */     assert ((colNum >= 0) && (colNum < this.rowLength));
/* 175:126 */     this.markedCols.set(colNum, Boolean.valueOf(false));
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void unmarkAllCols()
/* 179:    */   {
/* 180:129 */     for (int i = 0; i < this.rowLength; i++) {
/* 181:130 */       this.markedCols.set(i, Boolean.valueOf(false));
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean colIsMarked(int colNum)
/* 186:    */   {
/* 187:134 */     assert ((colNum >= 0) && (colNum < this.rowLength));
/* 188:135 */     return ((Boolean)this.markedCols.get(colNum)).booleanValue();
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<Cell> getColCells(int colNum)
/* 192:    */   {
/* 193:138 */     assert ((colNum >= 0) && (colNum < this.rowLength));
/* 194:139 */     List<Cell> ret = new ArrayList(this.colLength);
/* 195:140 */     for (int i = 0; i < this.colLength; i++) {
/* 196:141 */       ret.add((Cell)((List)this.cList.get(i)).get(colNum));
/* 197:    */     }
/* 198:143 */     return ret;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void clearAllMarks()
/* 202:    */   {
/* 203:149 */     unmarkAllCols();
/* 204:150 */     unmarkAllRows();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String toString()
/* 208:    */   {
/* 209:153 */     StringBuffer s = new StringBuffer(5 * this.rowLength * this.colLength);
/* 210:154 */     s.append("Tableau:\n");
/* 211:155 */     for (int i = 0; i < this.rowLength; i++) {
/* 212:156 */       if (colIsMarked(i)) {
/* 213:156 */         s.append("c\t");
/* 214:    */       } else {
/* 215:157 */         s.append("\t");
/* 216:    */       }
/* 217:    */     }
/* 218:159 */     s.append("\n");
/* 219:160 */     for (int i = 0; i < this.colLength; i++)
/* 220:    */     {
/* 221:161 */       for (Cell d : getRowCells(i)) {
/* 222:162 */         s.append(d + "\t");
/* 223:    */       }
/* 224:164 */       s.delete(s.lastIndexOf("\t"), s.lastIndexOf("\t") + 1);
/* 225:165 */       if (rowIsMarked(i)) {
/* 226:165 */         s.append(" c");
/* 227:    */       } else {
/* 228:166 */         s.append("\t");
/* 229:    */       }
/* 230:167 */       s.append("\n");
/* 231:    */     }
/* 232:169 */     return s.toString();
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void set(int rowNum, int colNum, double val)
/* 236:    */   {
/* 237:172 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 238:173 */     if ((Double.valueOf(val).equals(Double.valueOf((0.0D / 0.0D)))) || (Double.valueOf(val).equals(Double.valueOf((-1.0D / 0.0D)))) || (Double.valueOf(val).equals(Double.valueOf((1.0D / 0.0D))))) {
/* 239:174 */       throw new IllegalArgumentException("Somebody tried to put the value " + val + " into a Hungarian tableau!");
/* 240:    */     }
/* 241:176 */     boolean starred = getCell(rowNum, colNum).starred;
/* 242:177 */     boolean primed = getCell(rowNum, colNum).primed;
/* 243:178 */     ((List)this.cList.get(rowNum)).set(colNum, new Cell(val));
/* 244:179 */     getCell(rowNum, colNum).primed = primed;
/* 245:180 */     getCell(rowNum, colNum).starred = starred;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void set(int rowNum, int colNum, Cell val)
/* 249:    */   {
/* 250:184 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 251:185 */     ((List)this.cList.get(rowNum)).set(colNum, new Cell(val));
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void star(int rowNum, int colNum)
/* 255:    */   {
/* 256:188 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 257:189 */     assert (get(rowNum, colNum) < 1.E-009D);
/* 258:190 */     ((Cell)((List)this.cList.get(rowNum)).get(colNum)).starred = true;
/* 259:191 */     ((Cell)((List)this.cList.get(rowNum)).get(colNum)).primed = false;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void prime(int rowNum, int colNum)
/* 263:    */   {
/* 264:194 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 265:195 */     assert (get(rowNum, colNum) < 1.E-009D);
/* 266:196 */     ((Cell)((List)this.cList.get(rowNum)).get(colNum)).primed = true;
/* 267:197 */     ((Cell)((List)this.cList.get(rowNum)).get(colNum)).starred = false;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void unstar(int rowNum, int colNum)
/* 271:    */   {
/* 272:200 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 273:201 */     assert (get(rowNum, colNum) < 1.E-009D);
/* 274:202 */     ((Cell)((List)this.cList.get(rowNum)).get(colNum)).starred = false;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void unprime(int rowNum, int colNum)
/* 278:    */   {
/* 279:205 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 280:    */     
/* 281:207 */     ((Cell)((List)this.cList.get(rowNum)).get(colNum)).primed = false;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public boolean isPrimed(int rowNum, int colNum)
/* 285:    */   {
/* 286:210 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 287:211 */     return ((Cell)((List)this.cList.get(rowNum)).get(colNum)).primed;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public boolean isStarred(int rowNum, int colNum)
/* 291:    */   {
/* 292:214 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 293:215 */     return ((Cell)((List)this.cList.get(rowNum)).get(colNum)).starred;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public double get(int rowNum, int colNum)
/* 297:    */   {
/* 298:218 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 299:219 */     return ((Cell)((List)this.cList.get(rowNum)).get(colNum)).doubleValue();
/* 300:    */   }
/* 301:    */   
/* 302:    */   public Cell getCell(int rowNum, int colNum)
/* 303:    */   {
/* 304:222 */     assert ((rowNum >= 0) && (colNum >= 0) && (rowNum < this.colLength) && (colNum < this.rowLength));
/* 305:223 */     return (Cell)((List)this.cList.get(rowNum)).get(colNum);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public static void main(String[] args)
/* 309:    */   {
/* 310:227 */     Tableau t = new Tableau(3, 4);
/* 311:228 */     t.markRow(1);
/* 312:229 */     t.markCol(1);
/* 313:230 */     t.markCol(3);
/* 314:231 */     t.star(1, 1);
/* 315:232 */     t.prime(2, 2);
/* 316:233 */     t.set(1, 3, 42.0D);
/* 317:234 */     System.out.println(t);
/* 318:    */     
/* 319:236 */     System.out.println(t.clone());
/* 320:    */   }
/* 321:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.specialMath.Tableau
 * JD-Core Version:    0.7.0.1
 */