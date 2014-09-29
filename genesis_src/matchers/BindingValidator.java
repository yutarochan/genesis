/*   1:    */ package matchers;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import matchers.representations.BindingPair;
/*   8:    */ import minilisp.LList;
/*   9:    */ import utils.PairOfEntities;
/*  10:    */ 
/*  11:    */ public class BindingValidator
/*  12:    */ {
/*  13: 16 */   private boolean enforceUniqueBindings = true;
/*  14: 18 */   private boolean patternMatch = true;
/*  15:    */   
/*  16:    */   public void setPatternMatch(boolean value)
/*  17:    */   {
/*  18: 20 */     this.patternMatch = value;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public List<BindingPair> validateBindings(List<BindingPair> bindings)
/*  22:    */   {
/*  23: 28 */     return validateBindings(bindings, new ArrayList());
/*  24:    */   }
/*  25:    */   
/*  26:    */   public List<BindingPair> validateBindings(List<BindingPair> bindings, LList<PairOfEntities> constraints)
/*  27:    */   {
/*  28: 33 */     return validateBindings(bindings, convertFromLList(constraints));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public List<BindingPair> validateBindings(List<BindingPair> bindings, List<BindingPair> constraints)
/*  32:    */   {
/*  33: 37 */     if ((bindings == null) || (bindings.size() < 1)) {
/*  34: 38 */       return null;
/*  35:    */     }
/*  36: 41 */     constraints = new ArrayList(constraints);
/*  37: 44 */     for (BindingPair binding : bindings) {
/*  38: 45 */       if (!binding.getAllowed()) {
/*  39: 46 */         constraints.add(binding);
/*  40:    */       }
/*  41:    */     }
/*  42: 49 */     HashMap<Entity, List<Entity>> mappings = new HashMap();
/*  43:    */     Entity entity1;
/*  44: 51 */     for (BindingPair pair : bindings) {
/*  45: 53 */       if (pair.getAllowed()) {
/*  46: 56 */         if (validateConstraints(pair, constraints))
/*  47:    */         {
/*  48: 57 */           entity1 = pair.getPattern();
/*  49: 58 */           Entity entity2 = pair.getDatum();
/*  50: 59 */           if (!mappings.containsKey(entity1)) {
/*  51: 60 */             mappings.put(entity1, new ArrayList());
/*  52:    */           }
/*  53: 64 */           List<Entity> matches = (List)mappings.get(entity1);
/*  54: 65 */           if (!matches.contains(entity2)) {
/*  55: 66 */             matches.add(entity2);
/*  56:    */           }
/*  57: 69 */           if ((this.enforceUniqueBindings) && (matches.size() > 1)) {
/*  58: 70 */             return null;
/*  59:    */           }
/*  60:    */         }
/*  61:    */         else
/*  62:    */         {
/*  63: 73 */           return null;
/*  64:    */         }
/*  65:    */       }
/*  66:    */     }
/*  67: 77 */     Object simplifiedBindings = new ArrayList();
/*  68: 79 */     for (BindingPair pair : constraints) {
/*  69: 80 */       if (!((List)simplifiedBindings).contains(pair)) {
/*  70: 81 */         ((List)simplifiedBindings).add(pair);
/*  71:    */       }
/*  72:    */     }
/*  73: 85 */     for (BindingPair pair : bindings) {
/*  74: 86 */       if (!((List)simplifiedBindings).contains(pair)) {
/*  75: 87 */         ((List)simplifiedBindings).add(pair);
/*  76:    */       }
/*  77:    */     }
/*  78: 90 */     return simplifiedBindings;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private boolean validateConstraints(BindingPair pair, List<BindingPair> constraints)
/*  82:    */   {
/*  83: 99 */     Entity entity1 = pair.getPattern();
/*  84:100 */     Entity entity2 = pair.getDatum();
/*  85:    */     
/*  86:102 */     boolean foundMismatch = false;
/*  87:103 */     boolean foundMatch = false;
/*  88:104 */     boolean foundNotAllowed = false;
/*  89:106 */     for (BindingPair constraint : constraints)
/*  90:    */     {
/*  91:107 */       Entity constraint1 = constraint.getPattern();
/*  92:108 */       Entity constraint2 = constraint.getDatum();
/*  93:109 */       if ((this.enforceUniqueBindings) && (constraint.getAllowed()))
/*  94:    */       {
/*  95:110 */         if ((constraint1 == entity1) && (constraint2 != entity2)) {
/*  96:111 */           foundMismatch = true;
/*  97:    */         }
/*  98:113 */         if ((constraint1 != entity1) && (constraint2 == entity2) && (!this.patternMatch)) {
/*  99:114 */           foundMismatch = true;
/* 100:    */         }
/* 101:    */       }
/* 102:117 */       if ((constraint1 == entity1) && (constraint2 == entity2)) {
/* 103:118 */         if (constraint.getAllowed()) {
/* 104:119 */           foundMatch = true;
/* 105:    */         } else {
/* 106:121 */           foundNotAllowed = true;
/* 107:    */         }
/* 108:    */       }
/* 109:    */     }
/* 110:125 */     if (foundNotAllowed) {
/* 111:126 */       return false;
/* 112:    */     }
/* 113:127 */     if ((!foundMatch) && (foundMismatch)) {
/* 114:128 */       return false;
/* 115:    */     }
/* 116:130 */     return true;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public static List<BindingPair> convertFromLList(LList<PairOfEntities> bindings)
/* 120:    */   {
/* 121:134 */     if (bindings == null) {
/* 122:135 */       return null;
/* 123:    */     }
/* 124:137 */     List<BindingPair> newBindings = new ArrayList();
/* 125:138 */     for (PairOfEntities pair : bindings) {
/* 126:139 */       newBindings.add(new BindingPair(pair));
/* 127:    */     }
/* 128:141 */     return newBindings;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static LList<PairOfEntities> convertToLList(List<BindingPair> bindings)
/* 132:    */   {
/* 133:145 */     if ((bindings == null) || (bindings.size() < 1)) {
/* 134:146 */       return null;
/* 135:    */     }
/* 136:148 */     LList<PairOfEntities> cons = new LList();
/* 137:149 */     for (BindingPair pair : bindings) {
/* 138:150 */       cons = cons.cons(pair.toPairOfEntities());
/* 139:    */     }
/* 140:152 */     return cons;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public static boolean equivalencyCheck(LList<PairOfEntities> set1, LList<PairOfEntities> set2)
/* 144:    */   {
/* 145:156 */     return equivalencyCheck(convertFromLList(set1), convertFromLList(set2));
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static boolean equivalencyCheck(List<BindingPair> set1, List<BindingPair> set2)
/* 149:    */   {
/* 150:160 */     if ((set1 != null) && (set1.size() == 0)) {
/* 151:161 */       set1 = null;
/* 152:    */     }
/* 153:162 */     if ((set2 != null) && (set2.size() == 0)) {
/* 154:163 */       set2 = null;
/* 155:    */     }
/* 156:164 */     if ((set1 == null) && (set2 == null)) {
/* 157:165 */       return true;
/* 158:    */     }
/* 159:166 */     if ((set1 == null ? 1 : 0) != (set2 == null ? 1 : 0)) {
/* 160:167 */       return false;
/* 161:    */     }
/* 162:169 */     if (set1.size() != set2.size()) {
/* 163:170 */       return false;
/* 164:    */     }
/* 165:172 */     for (BindingPair pair1 : set1)
/* 166:    */     {
/* 167:173 */       boolean found = false;
/* 168:174 */       for (BindingPair pair2 : set2) {
/* 169:175 */         if (pair1.equals(pair2))
/* 170:    */         {
/* 171:176 */           found = true;
/* 172:177 */           break;
/* 173:    */         }
/* 174:    */       }
/* 175:180 */       if (!found) {
/* 176:181 */         return false;
/* 177:    */       }
/* 178:    */     }
/* 179:183 */     return true;
/* 180:    */   }
/* 181:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.BindingValidator
 * JD-Core Version:    0.7.0.1
 */