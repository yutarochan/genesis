/*   1:    */ package matthewFay.StoryAlignment;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import matthewFay.Utilities.Pair;
/*   7:    */ 
/*   8:    */ public abstract class NWAligner<A, B>
/*   9:    */ {
/*  10: 18 */   public float gapPenalty = -1.0F;
/*  11:    */   
/*  12:    */   public void setGapPenalty(float penalty)
/*  13:    */   {
/*  14: 21 */     this.gapPenalty = penalty;
/*  15:    */   }
/*  16:    */   
/*  17:    */   public Alignment<A, B> align(List<A> as, List<B> bs)
/*  18:    */   {
/*  19: 33 */     float[][] fMatrix = computeFMatrix(as, bs, this.gapPenalty);
/*  20:    */     
/*  21: 35 */     Alignment<A, B> result = new Alignment();
/*  22:    */     
/*  23: 37 */     int i = as.size();
/*  24: 38 */     int j = bs.size();
/*  25:    */     
/*  26:    */ 
/*  27: 41 */     float scoreTotal = 0.0F;
/*  28:    */     do
/*  29:    */     {
/*  30: 44 */       float score = fMatrix[i][j];
/*  31: 45 */       float scoreDiag = fMatrix[(i - 1)][(j - 1)];
/*  32: 46 */       float scoreUp = fMatrix[i][(j - 1)];
/*  33: 47 */       float scoreLeft = fMatrix[(i - 1)][j];
/*  34: 49 */       if (score == scoreDiag + sim(as.get(i - 1), bs.get(j - 1)))
/*  35:    */       {
/*  36: 51 */         scoreTotal += sim(as.get(i - 1), bs.get(j - 1));
/*  37: 52 */         result.addFirst(new Pair(as.get(i - 1), bs.get(j - 1)));
/*  38: 53 */         i--;
/*  39: 54 */         j--;
/*  40:    */       }
/*  41: 56 */       else if (score == scoreLeft + this.gapPenalty)
/*  42:    */       {
/*  43: 57 */         scoreTotal += this.gapPenalty;
/*  44: 58 */         result.addFirst(new Pair(as.get(i - 1), null));
/*  45: 59 */         i--;
/*  46:    */       }
/*  47: 61 */       else if (score == scoreUp + this.gapPenalty)
/*  48:    */       {
/*  49: 62 */         scoreTotal += this.gapPenalty;
/*  50: 63 */         result.addFirst(new Pair(null, bs.get(j - 1)));
/*  51: 64 */         j--;
/*  52:    */       }
/*  53: 43 */       if (i <= 0) {
/*  54:    */         break;
/*  55:    */       }
/*  56: 43 */     } while (j > 0);
/*  57: 68 */     while (i > 0)
/*  58:    */     {
/*  59: 69 */       scoreTotal += this.gapPenalty;
/*  60: 70 */       result.addFirst(new Pair(as.get(i - 1), null));
/*  61: 71 */       i--;
/*  62:    */     }
/*  63: 74 */     while (j > 0)
/*  64:    */     {
/*  65: 75 */       scoreTotal += this.gapPenalty;
/*  66: 76 */       result.addFirst(new Pair(null, bs.get(j - 1)));
/*  67: 77 */       j--;
/*  68:    */     }
/*  69: 80 */     result.score = scoreTotal;
/*  70: 81 */     return result;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public float[][] computeFMatrix(List<A> as, List<B> bs, float d)
/*  74:    */   {
/*  75: 96 */     float[][] fMatrix = new float[as.size() + 1][bs.size() + 1];
/*  76: 99 */     for (int i = 0; i < as.size(); i++) {
/*  77: 99 */       fMatrix[i][0] = (d * i);
/*  78:    */     }
/*  79:100 */     for (int j = 0; j < bs.size(); j++) {
/*  80:100 */       fMatrix[0][j] = (d * j);
/*  81:    */     }
/*  82:103 */     for (int i = 1; i <= as.size(); i++) {
/*  83:104 */       for (int j = 1; j <= bs.size(); j++)
/*  84:    */       {
/*  85:105 */         float c1 = fMatrix[(i - 1)][(j - 1)] + sim(as.get(i - 1), bs.get(j - 1));
/*  86:106 */         float c2 = fMatrix[(i - 1)][j] + d;
/*  87:107 */         float c3 = fMatrix[i][(j - 1)] + d;
/*  88:108 */         fMatrix[i][j] = Math.max(c1, Math.max(c2, c3));
/*  89:    */       }
/*  90:    */     }
/*  91:141 */     return fMatrix;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public abstract float sim(A paramA, B paramB);
/*  95:    */   
/*  96:    */   public static void main(String[] args)
/*  97:    */   {
/*  98:164 */     NWAligner<Character, Character> aligner = new NWAligner()
/*  99:    */     {
/* 100:    */       public float sim(Character a, Character b)
/* 101:    */       {
/* 102:166 */         return a.equals(b) ? 4 : -7;
/* 103:    */       }
/* 104:169 */     };
/* 105:170 */     aligner.gapPenalty = -7.0F;
/* 106:    */     
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:177 */     String strA = "AGACTAGTTAC";
/* 113:178 */     String strB = "TCGATTTAC";
/* 114:    */     
/* 115:    */ 
/* 116:181 */     List<Character> seqA = new ArrayList(strA.length());
/* 117:182 */     for (char c : strA.toCharArray()) {
/* 118:182 */       seqA.add(Character.valueOf(c));
/* 119:    */     }
/* 120:183 */     List<Character> seqB = new ArrayList(strB.length());
/* 121:184 */     for (char c : strB.toCharArray()) {
/* 122:184 */       seqB.add(Character.valueOf(c));
/* 123:    */     }
/* 124:187 */     Object result = aligner.align(seqA, seqB);
/* 125:    */     
/* 126:    */ 
/* 127:190 */     StringBuilder seqAalign = new StringBuilder(((List)result).size());
/* 128:191 */     StringBuilder seqBalign = new StringBuilder(((List)result).size());
/* 129:192 */     for (Object pair : (List)result)
/* 130:    */     {
/* 131:193 */       seqAalign.append(((Pair)pair).a == null ? '-' : ((Character)((Pair)pair).a).charValue());
/* 132:194 */       seqBalign.append(((Pair)pair).b == null ? '-' : ((Character)((Pair)pair).b).charValue());
/* 133:    */     }
/* 134:196 */     System.out.println(seqAalign);
/* 135:197 */     System.out.println(seqBalign);
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.NWAligner
 * JD-Core Version:    0.7.0.1
 */