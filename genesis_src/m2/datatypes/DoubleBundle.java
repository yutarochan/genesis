/*   1:    */ package m2.datatypes;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Thread;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import lattice.FasterLLConcept;
/*   9:    */ import m2.LLCode;
/*  10:    */ 
/*  11:    */ public class DoubleBundle
/*  12:    */ {
/*  13: 23 */   private Set<Thread> posSet = new HashSet();
/*  14: 24 */   private Set<Thread> negSet = new HashSet();
/*  15:    */   private FasterLLConcept<String> llcon;
/*  16:    */   
/*  17:    */   public String toString()
/*  18:    */   {
/*  19: 31 */     String s = "";
/*  20: 32 */     for (Thread t : this.posSet) {
/*  21: 33 */       s = s + "/" + (String)t.lastElement();
/*  22:    */     }
/*  23: 35 */     for (Thread t : this.negSet) {
/*  24: 36 */       s = s + "/!" + (String)t.lastElement();
/*  25:    */     }
/*  26: 38 */     s = s.substring(1, s.length());
/*  27: 39 */     return s;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Thread getPosSingle()
/*  31:    */   {
/*  32: 47 */     if (this.posSet.size() == 1)
/*  33:    */     {
/*  34: 48 */       List<Thread> tempList = new ArrayList(this.posSet);
/*  35: 49 */       return (Thread)tempList.get(0);
/*  36:    */     }
/*  37: 51 */     return null;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Thread getNegSingle()
/*  41:    */   {
/*  42: 55 */     if (this.negSet.size() == 1)
/*  43:    */     {
/*  44: 56 */       List<Thread> tempList = new ArrayList(this.negSet);
/*  45: 57 */       return (Thread)tempList.get(0);
/*  46:    */     }
/*  47: 59 */     return null;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void addPos(Thread t)
/*  51:    */   {
/*  52: 69 */     this.posSet.add(t);
/*  53: 70 */     this.llcon = LLCode.getLLConcept(this.posSet, this.negSet);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void addNeg(Thread t)
/*  57:    */   {
/*  58: 78 */     this.negSet.add(t);
/*  59: 79 */     this.llcon = LLCode.getLLConcept(this.posSet, this.negSet);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int matches(Thread t)
/*  63:    */   {
/*  64: 98 */     if (this.negSet.contains(t)) {
/*  65: 99 */       return 0;
/*  66:    */     }
/*  67:101 */     if (LLCode.LLSearch(this.llcon, t)) {
/*  68:102 */       return 2;
/*  69:    */     }
/*  70:104 */     return 1;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean containsPos(Thread t)
/*  74:    */   {
/*  75:109 */     return this.posSet.contains(t);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean containsNeg(Thread t)
/*  79:    */   {
/*  80:113 */     return this.negSet.contains(t);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Set<Thread> getPosSet()
/*  84:    */   {
/*  85:117 */     return new HashSet(this.posSet);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Set<Thread> getNegSet()
/*  89:    */   {
/*  90:121 */     return new HashSet(this.negSet);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int hashCode()
/*  94:    */   {
/*  95:134 */     int PRIME = 31;
/*  96:135 */     int result = 1;
/*  97:136 */     result = 31 * result + (this.negSet == null ? 0 : this.negSet.hashCode());
/*  98:137 */     result = 31 * result + (this.posSet == null ? 0 : this.posSet.hashCode());
/*  99:138 */     return result;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean equals(Object obj)
/* 103:    */   {
/* 104:146 */     if (this == obj) {
/* 105:147 */       return true;
/* 106:    */     }
/* 107:148 */     if (obj == null) {
/* 108:149 */       return false;
/* 109:    */     }
/* 110:150 */     if (getClass() != obj.getClass()) {
/* 111:151 */       return false;
/* 112:    */     }
/* 113:152 */     DoubleBundle other = (DoubleBundle)obj;
/* 114:153 */     if (this.negSet == null)
/* 115:    */     {
/* 116:154 */       if (other.negSet != null) {
/* 117:155 */         return false;
/* 118:    */       }
/* 119:    */     }
/* 120:156 */     else if (!this.negSet.equals(other.negSet)) {
/* 121:157 */       return false;
/* 122:    */     }
/* 123:158 */     if (this.posSet == null)
/* 124:    */     {
/* 125:159 */       if (other.posSet != null) {
/* 126:160 */         return false;
/* 127:    */       }
/* 128:    */     }
/* 129:161 */     else if (!this.posSet.equals(other.posSet)) {
/* 130:162 */       return false;
/* 131:    */     }
/* 132:163 */     return true;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public DoubleBundle clone()
/* 136:    */   {
/* 137:169 */     DoubleBundle clone = new DoubleBundle();
/* 138:170 */     clone.posSet = new HashSet(this.posSet);
/* 139:171 */     clone.negSet = new HashSet(this.negSet);
/* 140:172 */     clone.llcon = LLCode.getLLConcept(this.posSet, this.negSet);
/* 141:173 */     return clone;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int getDistance(DoubleBundle db2)
/* 145:    */   {
/* 146:186 */     for (Thread other : db2.negSet) {
/* 147:187 */       if (this.posSet.contains(other)) {
/* 148:188 */         return 2;
/* 149:    */       }
/* 150:    */     }
/* 151:191 */     for (Thread other : db2.posSet) {
/* 152:192 */       if (this.negSet.contains(other)) {
/* 153:193 */         return 2;
/* 154:    */       }
/* 155:    */     }
/* 156:196 */     for (Thread other : db2.posSet) {
/* 157:197 */       if (this.posSet.contains(other)) {
/* 158:198 */         return 0;
/* 159:    */       }
/* 160:    */     }
/* 161:201 */     for (Thread other : db2.negSet) {
/* 162:202 */       if (this.negSet.contains(other)) {
/* 163:203 */         return 0;
/* 164:    */       }
/* 165:    */     }
/* 166:205 */     return 1;
/* 167:    */   }
/* 168:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.datatypes.DoubleBundle
 * JD-Core Version:    0.7.0.1
 */