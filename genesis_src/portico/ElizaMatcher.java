/*   1:    */ package portico;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class ElizaMatcher
/*   9:    */ {
/*  10:    */   private HashMap<String, String> bindings;
/*  11:    */   private static ElizaMatcher elizaMatcher;
/*  12:    */   
/*  13:    */   public static ElizaMatcher getElizaMatcher()
/*  14:    */   {
/*  15: 20 */     if (elizaMatcher == null) {
/*  16: 21 */       elizaMatcher = new ElizaMatcher();
/*  17:    */     }
/*  18: 23 */     return elizaMatcher;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public boolean match(String p, String d)
/*  22:    */   {
/*  23: 30 */     return match(Listifier.listify(p), Listifier.listify(d));
/*  24:    */   }
/*  25:    */   
/*  26:    */   public boolean match(List<String> p, List<String> d)
/*  27:    */   {
/*  28: 34 */     getBindings().clear();
/*  29: 35 */     return performMatch(p, d);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public HashMap<String, String> getBindings()
/*  33:    */   {
/*  34: 39 */     if (this.bindings == null) {
/*  35: 40 */       this.bindings = new HashMap();
/*  36:    */     }
/*  37: 42 */     return this.bindings;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean findMatch(List<String> pattern, List<String> datum)
/*  41:    */   {
/*  42: 46 */     getBindings().clear();
/*  43: 47 */     boolean result = performMatch(pattern, datum);
/*  44:    */     
/*  45: 49 */     return result;
/*  46:    */   }
/*  47:    */   
/*  48:    */   private void addBinding(String variable, String value)
/*  49:    */   {
/*  50: 53 */     Object result = getBindings().get(variable);
/*  51:    */     String newResult;
/*  52:    */     String newResult;
/*  53: 55 */     if (result == null) {
/*  54: 56 */       newResult = value;
/*  55:    */     } else {
/*  56: 59 */       newResult = result + " " + value;
/*  57:    */     }
/*  58: 62 */     getBindings().put(variable, newResult);
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void clearBinding(String variable)
/*  62:    */   {
/*  63: 66 */     getBindings().remove(variable);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public boolean performMatch(List<String> pattern, List<String> datum)
/*  67:    */   {
/*  68: 71 */     if ((pattern.isEmpty()) && (datum.isEmpty())) {
/*  69: 72 */       return true;
/*  70:    */     }
/*  71: 74 */     if (pattern.isEmpty()) {
/*  72: 75 */       return false;
/*  73:    */     }
/*  74: 77 */     String p = (String)pattern.get(0);
/*  75:    */     
/*  76: 79 */     char pChar = p.charAt(0);
/*  77: 81 */     if (datum.isEmpty())
/*  78:    */     {
/*  79: 82 */       if ((pattern.size() == 1) && ((p.equals("*")) || (pChar == '*'))) {
/*  80: 83 */         return true;
/*  81:    */       }
/*  82: 85 */       return false;
/*  83:    */     }
/*  84: 87 */     String d = (String)datum.get(0);
/*  85:101 */     if (p.equals("*"))
/*  86:    */     {
/*  87:102 */       List<String> newPattern = new ArrayList();
/*  88:103 */       newPattern.addAll(pattern);
/*  89:104 */       newPattern.remove(0);
/*  90:105 */       List<String> newDatum = new ArrayList();
/*  91:106 */       newDatum.addAll(datum);
/*  92:107 */       newDatum.remove(0);
/*  93:108 */       return (performMatch(newPattern, datum)) || (performMatch(pattern, newDatum));
/*  94:    */     }
/*  95:110 */     if (pChar == '*')
/*  96:    */     {
/*  97:111 */       List<String> newPattern = new ArrayList();
/*  98:112 */       newPattern.addAll(pattern);
/*  99:113 */       newPattern.remove(0);
/* 100:114 */       List<String> newDatum = new ArrayList();
/* 101:115 */       newDatum.addAll(datum);
/* 102:116 */       newDatum.remove(0);
/* 103:117 */       addBinding(p, d);
/* 104:118 */       boolean result = (performMatch(newPattern, datum)) || (performMatch(pattern, newDatum));
/* 105:119 */       if (!result) {
/* 106:120 */         clearBinding(p);
/* 107:    */       }
/* 108:122 */       return result;
/* 109:    */     }
/* 110:124 */     if (p.equals("?"))
/* 111:    */     {
/* 112:125 */       List<String> newPattern = new ArrayList();
/* 113:126 */       newPattern.addAll(pattern);
/* 114:127 */       newPattern.remove(0);
/* 115:128 */       List<String> newDatum = new ArrayList();
/* 116:129 */       newDatum.addAll(datum);
/* 117:130 */       newDatum.remove(0);
/* 118:131 */       return performMatch(newPattern, newDatum);
/* 119:    */     }
/* 120:133 */     if (pChar == '?')
/* 121:    */     {
/* 122:134 */       List<String> newPattern = new ArrayList();
/* 123:135 */       newPattern.addAll(pattern);
/* 124:136 */       newPattern.remove(0);
/* 125:137 */       List<String> newDatum = new ArrayList();
/* 126:138 */       newDatum.addAll(datum);
/* 127:139 */       newDatum.remove(0);
/* 128:140 */       addBinding(p, d);
/* 129:141 */       boolean result = performMatch(newPattern, newDatum);
/* 130:142 */       if (!result) {
/* 131:143 */         clearBinding(p);
/* 132:    */       }
/* 133:145 */       return result;
/* 134:    */     }
/* 135:147 */     if (p.equalsIgnoreCase(d))
/* 136:    */     {
/* 137:148 */       List<String> restPattern = new ArrayList();
/* 138:149 */       restPattern.addAll(pattern);
/* 139:150 */       restPattern.remove(0);
/* 140:151 */       List<String> restDatum = new ArrayList();
/* 141:152 */       restDatum.addAll(datum);
/* 142:153 */       restDatum.remove(0);
/* 143:154 */       return performMatch(restPattern, restDatum);
/* 144:    */     }
/* 145:156 */     return false;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void main(String[] ignore)
/* 149:    */   {
/* 150:163 */     List<String> pattern = Listifier.listify("Transfer knowledge from ?s to ?t");
/* 151:164 */     List<String> sentence = Listifier.listify("Transfer knowledge from \"horseshit\" to \"new situation\".");
/* 152:165 */     ElizaMatcher elizaMatcher = new ElizaMatcher();
/* 153:166 */     System.out.println("Result: " + elizaMatcher.performMatch(pattern, sentence));
/* 154:167 */     System.out.println("Bindings: " + elizaMatcher.getBindings());
/* 155:    */   }
/* 156:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     portico.ElizaMatcher
 * JD-Core Version:    0.7.0.1
 */