/*   1:    */ package tools;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Vector;
/*   7:    */ import matchers.StandardMatcher;
/*   8:    */ import utils.Mark;
/*   9:    */ 
/*  10:    */ public class Predicates
/*  11:    */ {
/*  12:    */   public static boolean isSometimes(Entity e)
/*  13:    */   {
/*  14: 21 */     if (e.hasProperty("idiom", "sometimes")) {
/*  15: 22 */       return true;
/*  16:    */     }
/*  17: 24 */     if ((e.getObject() != null) && (e.getObject().hasProperty("idiom", "sometimes"))) {
/*  18: 25 */       return true;
/*  19:    */     }
/*  20: 27 */     return false;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static boolean isCheck(Entity e)
/*  24:    */   {
/*  25: 34 */     if (e.hasProperty("idiom", "check")) {
/*  26: 35 */       return true;
/*  27:    */     }
/*  28: 37 */     return false;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static boolean isCause(Entity e)
/*  32:    */   {
/*  33: 44 */     if ((e.relationP("cause")) && (e.isNotA("prepare")) && (e.isNotA("help")) && 
/*  34: 45 */       (e.getSubject().isA("conjuction"))) {
/*  35: 46 */       return true;
/*  36:    */     }
/*  37: 48 */     return false;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static boolean isExplanation(Entity e)
/*  41:    */   {
/*  42: 52 */     if ((isCause(e)) && (e.isA("explanation")) && (e.getSubject().isA("conjuction"))) {
/*  43: 53 */       return true;
/*  44:    */     }
/*  45: 55 */     return false;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static boolean isAbduction(Entity e)
/*  49:    */   {
/*  50: 59 */     if ((isCause(e)) && (e.isA("abduction")) && (e.getSubject().isA("conjuction"))) {
/*  51: 60 */       return true;
/*  52:    */     }
/*  53: 62 */     return false;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public static boolean isMeans(Entity e)
/*  57:    */   {
/*  58: 66 */     if ((isCause(e)) && (e.isA("means")) && (e.getSubject().isA("conjuction"))) {
/*  59: 67 */       return true;
/*  60:    */     }
/*  61: 69 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static boolean isCauseWord(Entity e)
/*  65:    */   {
/*  66: 73 */     if ((e.isA("cause")) && (e.isNotA("move")) && (e.isNotA("help")) && (e.isNotA("prepare"))) {
/*  67: 74 */       return true;
/*  68:    */     }
/*  69: 76 */     return false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static boolean isAntecedentOfAbduction(Entity e)
/*  73:    */   {
/*  74: 80 */     Vector<Entity> sequences = e.getElementOf();
/*  75: 81 */     if (!sequences.isEmpty()) {
/*  76: 82 */       for (Entity x : sequences) {
/*  77: 83 */         if (x.isA("conjuction"))
/*  78:    */         {
/*  79: 84 */           Vector<Function> candidates = x.getSubjectOf("abduction");
/*  80: 85 */           if (!candidates.isEmpty()) {
/*  81: 86 */             return true;
/*  82:    */           }
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86: 91 */     return false;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static boolean isAction(Entity e)
/*  90:    */   {
/*  91: 95 */     return e.relationP("action");
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static boolean contained(Entity element, Collection<Entity> antecedents)
/*  95:    */   {
/*  96:102 */     for (Entity antecedent : antecedents) {
/*  97:103 */       if (equals(element, antecedent)) {
/*  98:105 */         return true;
/*  99:    */       }
/* 100:    */     }
/* 101:108 */     return false;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public static boolean equals(Entity element1, Entity element2)
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:116 */       if (element1 == element2) {
/* 109:117 */         return true;
/* 110:    */       }
/* 111:119 */       if (StandardMatcher.getIdentityMatcher().match(element1, element2) != null) {
/* 112:121 */         return true;
/* 113:    */       }
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:125 */       Mark.say(new Object[] {"Blew out of Predicates.equals", element1, element2 });
/* 118:    */     }
/* 119:127 */     return false;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static boolean isRoleFrame(Entity entity)
/* 123:    */   {
/* 124:131 */     return (entity.getObject() != null) && (entity.getObject().sequenceP("roles"));
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tools.Predicates
 * JD-Core Version:    0.7.0.1
 */