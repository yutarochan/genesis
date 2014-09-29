/*   1:    */ package text;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import utils.Mark;
/*   5:    */ 
/*   6:    */ public class Punctuator
/*   7:    */ {
/*   8:    */   public static String conditionName(String name)
/*   9:    */   {
/*  10: 15 */     if (name.trim().isEmpty()) {
/*  11: 16 */       return "";
/*  12:    */     }
/*  13: 18 */     StringBuffer buffer = new StringBuffer(name);
/*  14:    */     int index;
/*  15: 20 */     while ((index = buffer.indexOf("_")) >= 0)
/*  16:    */     {
/*  17:    */       int index;
/*  18: 21 */       buffer.replace(index, index + 1, " ");
/*  19:    */     }
/*  20: 23 */     while ((index = buffer.indexOf("\"")) >= 0) {
/*  21: 24 */       buffer.replace(index, index + 1, "");
/*  22:    */     }
/*  23: 26 */     buffer.replace(0, 1, buffer.substring(0, 1).toUpperCase());
/*  24: 27 */     return buffer.toString();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static String removeQuotes(String name)
/*  28:    */   {
/*  29: 31 */     if (name.trim().isEmpty()) {
/*  30: 32 */       return "";
/*  31:    */     }
/*  32: 34 */     StringBuffer buffer = new StringBuffer(name);
/*  33:    */     int index;
/*  34: 36 */     while ((index = buffer.indexOf("\"")) >= 0)
/*  35:    */     {
/*  36:    */       int index;
/*  37: 37 */       buffer.replace(index, index + 1, "");
/*  38:    */     }
/*  39: 39 */     return buffer.toString();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static String punctuateAnd(List<String> elements)
/*  43:    */   {
/*  44: 43 */     return punctuateSequence(elements, "and");
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static String punctuateOr(List<String> elements)
/*  48:    */   {
/*  49: 47 */     return punctuateSequence(elements, "or");
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static String punctuateSequence(List<String> elements, String insertion)
/*  53:    */   {
/*  54: 51 */     String result = "";
/*  55: 52 */     int size = elements.size();
/*  56: 53 */     if (size == 1) {
/*  57: 54 */       result = result + (String)elements.get(0);
/*  58:    */     } else {
/*  59: 57 */       for (int i = 0; i < size; i++)
/*  60:    */       {
/*  61: 58 */         result = result + (String)elements.get(i);
/*  62: 59 */         if (i != size - 1) {
/*  63: 61 */           if (i == size - 2)
/*  64:    */           {
/*  65: 62 */             if (size == 2) {
/*  66: 63 */               result = result + " " + insertion + " ";
/*  67:    */             } else {
/*  68: 66 */               result = result + ", " + insertion + " ";
/*  69:    */             }
/*  70:    */           }
/*  71:    */           else {
/*  72: 70 */             result = result + ", ";
/*  73:    */           }
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77: 74 */     return result;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static String addPeriod(Object o)
/*  81:    */   {
/*  82: 78 */     String s = o.toString().trim();
/*  83: 79 */     if (s.isEmpty()) {
/*  84: 80 */       return "";
/*  85:    */     }
/*  86: 82 */     char last = s.charAt(s.length() - 1);
/*  87: 83 */     if (">".indexOf(last) >= 0) {
/*  88: 84 */       return s;
/*  89:    */     }
/*  90: 86 */     if (":".indexOf(last) >= 0) {
/*  91: 87 */       return s.trim() + " ";
/*  92:    */     }
/*  93: 89 */     if (".?!".indexOf(last) >= 0) {
/*  94: 90 */       return s + "  ";
/*  95:    */     }
/*  96: 92 */     return s + ".  ";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static String addSpace(Object o)
/* 100:    */   {
/* 101: 96 */     String s = o.toString().trim();
/* 102: 97 */     if (s.isEmpty()) {
/* 103: 98 */       return "";
/* 104:    */     }
/* 105:100 */     char last = s.charAt(s.length() - 1);
/* 106:101 */     if (">".indexOf(last) >= 0) {
/* 107:102 */       return s;
/* 108:    */     }
/* 109:104 */     if (":".indexOf(last) >= 0) {
/* 110:105 */       return s.trim() + " ";
/* 111:    */     }
/* 112:107 */     if (".?!".indexOf(last) >= 0) {
/* 113:108 */       return s + "  ";
/* 114:    */     }
/* 115:111 */     return s + "  ";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static String removePeriod(String s)
/* 119:    */   {
/* 120:115 */     s = s.trim();
/* 121:116 */     char last = s.charAt(s.length() - 1);
/* 122:117 */     if (".?!".indexOf(last) >= 0) {
/* 123:118 */       return s.substring(0, s.length() - 1).trim();
/* 124:    */     }
/* 125:120 */     return s;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static void main(String[] ignore)
/* 129:    */   {
/* 130:124 */     Mark.say(
/* 131:125 */       new Object[] { conditionName("Tragedy_of_Macbeth's") });
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     text.Punctuator
 * JD-Core Version:    0.7.0.1
 */