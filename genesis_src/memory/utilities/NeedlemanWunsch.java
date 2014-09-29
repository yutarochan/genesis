/*   1:    */ package memory.utilities;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class NeedlemanWunsch
/*  10:    */ {
/*  11:    */   public static Map<Entity, Entity> pair(List<Entity> l1, List<Entity> l2)
/*  12:    */   {
/*  13: 26 */     Map<Entity, Entity> mapping = new HashMap();
/*  14:    */     
/*  15: 28 */     double d = 0.0D;
/*  16:    */     
/*  17: 30 */     double[][] sm = generateSimilarityMatrix(l1, l2);
/*  18:    */     
/*  19: 32 */     double[][] f = new double[l1.size() + 1][l2.size() + 1];
/*  20: 33 */     for (int i = 0; i < l1.size(); i++) {
/*  21: 34 */       f[i][0] = (d * i);
/*  22:    */     }
/*  23: 36 */     for (int j = 0; j < l2.size(); j++) {
/*  24: 37 */       f[0][j] = (d * j);
/*  25:    */     }
/*  26: 40 */     for (int i = 1; i < l1.size() + 1; i++) {
/*  27: 41 */       for (int j = 1; j < l2.size() + 1; j++)
/*  28:    */       {
/*  29: 42 */         double choice1 = f[(i - 1)][(j - 1)] + sm[(i - 1)][(j - 1)];
/*  30: 43 */         double choice2 = f[(i - 1)][j] + d;
/*  31: 44 */         double choice3 = f[i][(j - 1)] + d;
/*  32:    */         
/*  33: 46 */         double best1 = Math.max(choice1, choice2);
/*  34: 47 */         f[i][j] = Math.max(best1, choice3);
/*  35:    */       }
/*  36:    */     }
/*  37: 61 */     int i = l1.size();
/*  38: 62 */     int j = l2.size();
/*  39:    */     do
/*  40:    */     {
/*  41: 65 */       double score = f[i][j];
/*  42: 66 */       double scoreDiag = f[(i - 1)][(j - 1)];
/*  43:    */       
/*  44: 68 */       double scoreLeft = f[(i - 1)][j];
/*  45: 69 */       if (score == scoreDiag + sm[(i - 1)][(j - 1)])
/*  46:    */       {
/*  47: 72 */         mapping.put((Entity)l1.get(i - 1), (Entity)l2.get(j - 1));
/*  48: 73 */         i--;
/*  49: 74 */         j--;
/*  50:    */       }
/*  51: 76 */       else if (score == scoreLeft + d)
/*  52:    */       {
/*  53: 79 */         i--;
/*  54:    */       }
/*  55:    */       else
/*  56:    */       {
/*  57: 84 */         j--;
/*  58:    */       }
/*  59: 64 */       if (i <= 0) {
/*  60:    */         break;
/*  61:    */       }
/*  62: 64 */     } while (j > 0);
/*  63: 88 */     while (i > 0) {
/*  64: 91 */       i--;
/*  65:    */     }
/*  66: 93 */     while (j > 0) {
/*  67: 96 */       j--;
/*  68:    */     }
/*  69: 98 */     return mapping;
/*  70:    */   }
/*  71:    */   
/*  72:    */   private static double[][] generateSimilarityMatrix(List<Entity> l1, List<Entity> l2)
/*  73:    */   {
/*  74:103 */     double[][] m = new double[l1.size()][l2.size()];
/*  75:104 */     for (int i = 0; i < l1.size(); i++) {
/*  76:105 */       for (int j = 0; j < l2.size(); j++)
/*  77:    */       {
/*  78:108 */         double temp = 1.0D - Distances.distance((Entity)l1.get(i), (Entity)l2.get(j));
/*  79:109 */         m[i][j] = (temp * temp);
/*  80:    */       }
/*  81:    */     }
/*  82:112 */     return m;
/*  83:    */   }
/*  84:    */   
/*  85:    */   private static void printMatrix(double[][] arr)
/*  86:    */   {
/*  87:116 */     for (int i = 0; i < arr.length; i++)
/*  88:    */     {
/*  89:117 */       if (i == 0)
/*  90:    */       {
/*  91:119 */         System.out.print("+");
/*  92:120 */         for (int j = 0; j < arr[0].length; j++) {
/*  93:121 */           System.out.print("\t\t" + j);
/*  94:    */         }
/*  95:    */       }
/*  96:124 */       System.out.print("\n");
/*  97:125 */       System.out.print(i + "\t\t");
/*  98:126 */       for (int j = 0; j < arr[0].length; j++)
/*  99:    */       {
/* 100:127 */         Double val = Double.valueOf(arr[i][j]);
/* 101:128 */         String output = val.toString();
/* 102:129 */         if (output.length() > 6) {
/* 103:130 */           output = output.substring(0, 6);
/* 104:    */         }
/* 105:132 */         System.out.print(output + "\t\t");
/* 106:    */       }
/* 107:    */     }
/* 108:135 */     System.out.println("");
/* 109:136 */     System.out.println("");
/* 110:    */   }
/* 111:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.utilities.NeedlemanWunsch
 * JD-Core Version:    0.7.0.1
 */