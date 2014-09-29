/*   1:    */ package matthewFay.Utilities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Set;
/*   8:    */ import utils.Mark;
/*   9:    */ 
/*  10:    */ public class HashMatrix<ROW, COL, DATA>
/*  11:    */ {
/*  12:    */   private HashMap<ROW, HashMap<COL, DATA>> hashMatrixRowCol;
/*  13:    */   private HashMap<COL, HashMap<ROW, DATA>> hashMatrixColRow;
/*  14:    */   
/*  15:    */   public HashMatrix()
/*  16:    */   {
/*  17: 15 */     this.hashMatrixRowCol = new HashMap();
/*  18: 16 */     this.hashMatrixColRow = new HashMap();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public HashMatrix(HashMatrix<ROW, COL, DATA> m)
/*  22:    */   {
/*  23: 20 */     this.hashMatrixRowCol = new HashMap();
/*  24: 21 */     this.hashMatrixColRow = new HashMap();
/*  25:    */     Iterator localIterator2;
/*  26: 23 */     for (Iterator localIterator1 = m.keySetRows().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  27:    */     {
/*  28: 23 */       ROW r = (Object)localIterator1.next();
/*  29: 24 */       localIterator2 = m.keySetCols().iterator(); continue;COL c = (Object)localIterator2.next();
/*  30: 25 */       if (m.contains(r, c)) {
/*  31: 26 */         put(r, c, m.get(r, c));
/*  32:    */       }
/*  33:    */     }
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Set<ROW> keySetRows()
/*  37:    */   {
/*  38: 33 */     return this.hashMatrixRowCol.keySet();
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Set<ROW> keySetRows(COL c)
/*  42:    */   {
/*  43: 37 */     if (this.hashMatrixColRow.containsKey(c)) {
/*  44: 38 */       return ((HashMap)this.hashMatrixColRow.get(c)).keySet();
/*  45:    */     }
/*  46: 39 */     return new HashSet();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Set<COL> keySetCols()
/*  50:    */   {
/*  51: 43 */     return this.hashMatrixColRow.keySet();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public Set<COL> keySetCols(ROW r)
/*  55:    */   {
/*  56: 47 */     if (this.hashMatrixRowCol.containsKey(r)) {
/*  57: 48 */       return ((HashMap)this.hashMatrixRowCol.get(r)).keySet();
/*  58:    */     }
/*  59: 49 */     return new HashSet();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Set<DATA> getValues()
/*  63:    */   {
/*  64: 53 */     Set<DATA> data = new HashSet();
/*  65:    */     Iterator localIterator2;
/*  66: 54 */     for (Iterator localIterator1 = this.hashMatrixRowCol.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  67:    */     {
/*  68: 54 */       ROW r = (Object)localIterator1.next();
/*  69: 55 */       localIterator2 = ((HashMap)this.hashMatrixRowCol.get(r)).keySet().iterator(); continue;COL c = (Object)localIterator2.next();
/*  70: 56 */       data.add(((HashMap)this.hashMatrixRowCol.get(r)).get(c));
/*  71:    */     }
/*  72: 59 */     return data;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void put(ROW x, COL y, DATA d)
/*  76:    */   {
/*  77: 63 */     if (!this.hashMatrixRowCol.containsKey(x)) {
/*  78: 64 */       this.hashMatrixRowCol.put(x, new HashMap());
/*  79:    */     }
/*  80: 66 */     if (((HashMap)this.hashMatrixRowCol.get(x)).containsKey(y)) {
/*  81: 67 */       ((HashMap)this.hashMatrixRowCol.get(x)).remove(y);
/*  82:    */     }
/*  83: 69 */     ((HashMap)this.hashMatrixRowCol.get(x)).put(y, d);
/*  84: 71 */     if (!this.hashMatrixColRow.containsKey(y)) {
/*  85: 72 */       this.hashMatrixColRow.put(y, new HashMap());
/*  86:    */     }
/*  87: 74 */     if (((HashMap)this.hashMatrixColRow.get(y)).containsKey(x)) {
/*  88: 75 */       ((HashMap)this.hashMatrixColRow.get(y)).remove(x);
/*  89:    */     }
/*  90: 78 */     ((HashMap)this.hashMatrixColRow.get(y)).put(x, d);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void remove(ROW x, COL y)
/*  94:    */   {
/*  95: 82 */     if (this.hashMatrixRowCol.containsKey(x))
/*  96:    */     {
/*  97: 83 */       ((HashMap)this.hashMatrixRowCol.get(x)).remove(y);
/*  98: 84 */       if (((HashMap)this.hashMatrixRowCol.get(x)).size() == 0) {
/*  99: 85 */         this.hashMatrixRowCol.remove(x);
/* 100:    */       }
/* 101:    */     }
/* 102: 87 */     if (this.hashMatrixColRow.containsKey(y))
/* 103:    */     {
/* 104: 88 */       ((HashMap)this.hashMatrixColRow.get(y)).remove(x);
/* 105: 89 */       if (((HashMap)this.hashMatrixColRow.get(y)).size() == 0) {
/* 106: 90 */         this.hashMatrixColRow.remove(y);
/* 107:    */       }
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean containsKey(ROW x, COL y)
/* 112:    */   {
/* 113: 95 */     return contains(x, y);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public boolean contains(ROW x, COL y)
/* 117:    */   {
/* 118: 99 */     if ((this.hashMatrixRowCol.containsKey(x)) && 
/* 119:100 */       (((HashMap)this.hashMatrixRowCol.get(x)).containsKey(y))) {
/* 120:101 */       return true;
/* 121:    */     }
/* 122:103 */     return false;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public DATA get(ROW x, COL y)
/* 126:    */   {
/* 127:107 */     if (!this.hashMatrixRowCol.containsKey(x)) {
/* 128:108 */       return null;
/* 129:    */     }
/* 130:109 */     if (!((HashMap)this.hashMatrixRowCol.get(x)).containsKey(y)) {
/* 131:110 */       return null;
/* 132:    */     }
/* 133:111 */     return ((HashMap)this.hashMatrixRowCol.get(x)).get(y);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void clear()
/* 137:    */   {
/* 138:115 */     this.hashMatrixRowCol.clear();
/* 139:116 */     this.hashMatrixColRow.clear();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String toCSV()
/* 143:    */   {
/* 144:120 */     String s = "";
/* 145:121 */     ArrayList<ROW> rows = new ArrayList();
/* 146:122 */     ArrayList<COL> cols = new ArrayList();
/* 147:123 */     for (ROW row : this.hashMatrixRowCol.keySet()) {
/* 148:124 */       rows.add(row);
/* 149:    */     }
/* 150:125 */     for (COL col : this.hashMatrixColRow.keySet()) {
/* 151:126 */       cols.add(col);
/* 152:    */     }
/* 153:127 */     for (ROW row : rows)
/* 154:    */     {
/* 155:128 */       if (!s.isEmpty()) {
/* 156:129 */         s = s + "\n";
/* 157:    */       }
/* 158:130 */       String line = "";
/* 159:131 */       for (COL col : cols)
/* 160:    */       {
/* 161:132 */         if (!line.isEmpty()) {
/* 162:133 */           line = line + ",";
/* 163:    */         }
/* 164:134 */         line = line + get(row, col);
/* 165:    */       }
/* 166:136 */       s = s + line;
/* 167:    */     }
/* 168:138 */     return s;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static void main(String[] args)
/* 172:    */   {
/* 173:142 */     HashMatrix<String, String, Float> h = new HashMatrix();
/* 174:    */     
/* 175:144 */     h.put("1", "1", Float.valueOf(2.0F));
/* 176:145 */     h.put("1.5", "1", Float.valueOf(2.5F));
/* 177:146 */     h.put("1", "2", Float.valueOf(3.0F));
/* 178:    */     
/* 179:148 */     Mark.say(new Object[] {h.toCSV() });
/* 180:    */   }
/* 181:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Utilities.HashMatrix
 * JD-Core Version:    0.7.0.1
 */