/*   1:    */ package matthewFay.Depricated;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import minilisp.LList;
/*   6:    */ import start.Generator;
/*   7:    */ import translator.Translator;
/*   8:    */ import utils.Mark;
/*   9:    */ import utils.PairOfEntities;
/*  10:    */ 
/*  11:    */ @Deprecated
/*  12:    */ public class PartialMatcher
/*  13:    */ {
/*  14:    */   public static float match(Entity thing1, Entity thing2, LList<PairOfEntities> bindings)
/*  15:    */   {
/*  16: 24 */     if (bindings == null) {
/*  17: 25 */       bindings = new LList();
/*  18:    */     }
/*  19: 26 */     if ((thing1.functionP()) && (thing2.functionP()))
/*  20:    */     {
/*  21: 28 */       Entity subject1 = thing1.getSubject();
/*  22: 29 */       Entity subject2 = thing2.getSubject();
/*  23:    */       
/*  24: 31 */       float subjectMatch = match(subject1, subject2, bindings);
/*  25:    */       
/*  26: 33 */       float derivativeMatch = 1.0F;
/*  27:    */       
/*  28: 35 */       return (subjectMatch + derivativeMatch) / 2.0F;
/*  29:    */     }
/*  30: 36 */     if ((thing1.featureP()) && (thing2.featureP()))
/*  31:    */     {
/*  32: 38 */       Mark.say(new Object[] {"Features not handled yet..." });
/*  33: 39 */       return 1.0F;
/*  34:    */     }
/*  35: 40 */     if ((thing1.relationP()) && (thing2.relationP()))
/*  36:    */     {
/*  37: 42 */       Entity subject1 = thing1.getSubject();
/*  38: 43 */       Entity subject2 = thing2.getSubject();
/*  39: 44 */       float subjectMatch = match(subject1, subject2, bindings);
/*  40:    */       
/*  41: 46 */       Entity object1 = thing1.getObject();
/*  42: 47 */       Entity object2 = thing2.getObject();
/*  43: 48 */       float objectMatch = match(object1, object2, bindings);
/*  44:    */       
/*  45: 50 */       float relationMatch = thing1.getType() == thing2.getType() ? 1 : 0;
/*  46:    */       
/*  47: 52 */       return (subjectMatch + objectMatch + relationMatch) / 3.0F;
/*  48:    */     }
/*  49: 53 */     if ((thing1.sequenceP()) && (thing2.sequenceP()))
/*  50:    */     {
/*  51: 55 */       int childrenCount1 = thing1.getNumberOfChildren();
/*  52: 56 */       int childrenCount2 = thing2.getNumberOfChildren();
/*  53:    */       
/*  54:    */ 
/*  55: 59 */       int min = childrenCount1 < childrenCount2 ? childrenCount1 : childrenCount2;
/*  56: 60 */       int max = childrenCount1 < childrenCount2 ? childrenCount2 : childrenCount1;
/*  57:    */       
/*  58: 62 */       float childrenScoreSum = 0.0F;
/*  59: 63 */       for (int i = 0; i < min; i++)
/*  60:    */       {
/*  61: 64 */         Entity child1 = thing1.getElement(i);
/*  62: 65 */         Entity child2 = thing2.getElement(i);
/*  63: 66 */         childrenScoreSum += match(child1, child2, bindings);
/*  64:    */       }
/*  65: 70 */       float sequenceMatch = 1.0F;
/*  66:    */       
/*  67: 72 */       return (childrenScoreSum + sequenceMatch) / (1 + max);
/*  68:    */     }
/*  69: 73 */     if ((thing1.entityP()) && (thing2.entityP()))
/*  70:    */     {
/*  71: 74 */       if (OneToOneMatcher.getOneToOneMatcher().match(thing1, thing2, bindings) != null) {
/*  72: 75 */         return 1.0F;
/*  73:    */       }
/*  74: 77 */       return 0.0F;
/*  75:    */     }
/*  76: 79 */     Mark.say(new Object[] {"Non match, or non-detected Thing Type" });
/*  77: 80 */     Mark.say(new Object[] {"Thing1: ", thing1.asString() });
/*  78: 81 */     Mark.say(new Object[] {"Thing2: ", thing2.asString() });
/*  79: 82 */     return 0.0F;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static void main(String[] args)
/*  83:    */     throws Exception
/*  84:    */   {
/*  85: 87 */     Generator generator = Generator.getGenerator();
/*  86: 88 */     Translator translator = Translator.getTranslator();
/*  87:    */     
/*  88: 90 */     generator.setStoryMode();
/*  89:    */     
/*  90: 92 */     Entity s2_A = translator.translate("Matt is a person").getElement(0);
/*  91: 93 */     Entity s2_B = translator.translate("Mary is a person").getElement(0);
/*  92: 94 */     Entity s2_C = translator.translate("The bowl is an object").getElement(0);
/*  93: 95 */     Entity s2_D = translator.translate("Matt owns the bowl").getElement(0);
/*  94: 96 */     Entity s2_E = translator.translate("Matt gives the bowl to Mary").getElement(0);
/*  95: 97 */     Entity s2_F = translator.translate("Mary owns the bowl").getElement(0);
/*  96: 98 */     Entity story_s2 = new Sequence();
/*  97: 99 */     story_s2.addElement(s2_A);
/*  98:100 */     story_s2.addElement(s2_B);
/*  99:101 */     story_s2.addElement(s2_C);
/* 100:102 */     story_s2.addElement(s2_D);
/* 101:103 */     story_s2.addElement(s2_E);
/* 102:104 */     story_s2.addElement(s2_F);
/* 103:    */     
/* 104:106 */     generator.flush();
/* 105:    */     
/* 106:108 */     Entity take_A = translator.translate("Mark is a person").getElement(0);
/* 107:109 */     Entity take_B = translator.translate("Sally is a person").getElement(0);
/* 108:110 */     Entity take_C = translator.translate("The cup is an object").getElement(0);
/* 109:111 */     Entity take_D = translator.translate("Mark owns the cup").getElement(0);
/* 110:112 */     Entity take_E = translator.translate("Sally takes the cup from Mark").getElement(0);
/* 111:113 */     Entity take_F = translator.translate("Sally owns the cup").getElement(0);
/* 112:114 */     Entity story_take = new Sequence();
/* 113:115 */     story_take.addElement(take_A);
/* 114:116 */     story_take.addElement(take_B);
/* 115:117 */     story_take.addElement(take_C);
/* 116:118 */     story_take.addElement(take_D);
/* 117:119 */     story_take.addElement(take_E);
/* 118:120 */     story_take.addElement(take_F);
/* 119:    */     
/* 120:122 */     generator.flush();
/* 121:    */     
/* 122:124 */     Entity matt = s2_D.getSubject();
/* 123:125 */     Entity mark = take_D.getSubject();
/* 124:126 */     Entity cup = take_D.getObject();
/* 125:127 */     Entity bowl = s2_D.getObject();
/* 126:    */     
/* 127:    */ 
/* 128:130 */     Mark.say(new Object[] {take_E.asString() });
/* 129:    */     
/* 130:132 */     Mark.say(new Object[] {cup.asString(), bowl.asString() });
/* 131:    */     
/* 132:134 */     PartialMatcher matcher = new PartialMatcher();
/* 133:135 */     Mark.say(new Object[] {"Partial Match Score: ", Float.valueOf(match(matt, mark, null)) });
/* 134:136 */     Mark.say(new Object[] {"Partial Match Score: ", Float.valueOf(match(cup, bowl, null)) });
/* 135:137 */     Mark.say(new Object[] {"Partial Match Score: ", Float.valueOf(match(s2_E, take_E, null)) });
/* 136:138 */     Mark.say(new Object[] {"Partial Match Score: ", Float.valueOf(match(story_s2, story_take, null)) });
/* 137:    */   }
/* 138:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.PartialMatcher
 * JD-Core Version:    0.7.0.1
 */