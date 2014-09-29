/*   1:    */ package demonstrations;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.Vector;
/*   5:    */ import matchers.StandardMatcher;
/*   6:    */ import minilisp.LList;
/*   7:    */ import start.Generator;
/*   8:    */ import start.RoleFrame;
/*   9:    */ import start.Start;
/*  10:    */ import translator.Translator;
/*  11:    */ import utils.Mark;
/*  12:    */ import utils.PairOfEntities;
/*  13:    */ 
/*  14:    */ public class ParserMatcher
/*  15:    */ {
/*  16:    */   public static void testMatch()
/*  17:    */     throws Exception
/*  18:    */   {
/*  19: 20 */     Generator generator = Generator.getGenerator();
/*  20: 21 */     Translator translator = Translator.getTranslator();
/*  21: 22 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/*  22:    */     
/*  23:    */ 
/*  24: 25 */     translator.translate("xx is a person");
/*  25: 26 */     translator.translate("yy is a person");
/*  26: 27 */     translator.translate("zz is a person");
/*  27:    */     
/*  28: 29 */     translator.translate("John is a person");
/*  29: 30 */     translator.translate("Mary is a person");
/*  30: 31 */     translator.translate("Sally is a person");
/*  31:    */     
/*  32: 33 */     Mark.say(new Object[] {"Example 1:", matcher.match(translator.translate("xx killed yy"), translator.translate("John killed Mary")) });
/*  33:    */     
/*  34: 35 */     Mark.say(new Object[] {"Example 2:", matcher.match(translator.translate("xx killed yy"), translator.translate("John loves Mary")) });
/*  35:    */     
/*  36: 37 */     translator.translate("tt is a tool");
/*  37:    */     
/*  38: 39 */     Mark.say(new Object[] {"Example 3:", matcher.match(translator.translate("xx has a tt"), translator.translate("John has a knife")) });
/*  39:    */     
/*  40: 41 */     Mark.say(new Object[] {"Example 4:", matcher.match(translator.translate("xx has a tt"), translator.translate("John has a ball")) });
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static void testParse(String sentence)
/*  44:    */     throws Exception
/*  45:    */   {
/*  46: 47 */     Mark.say(
/*  47:    */     
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59: 60 */       new Object[] { "Input:", sentence });Start start = Start.getStart();Translator translator = Translator.getTranslator();Entity sequence2 = translator.translate(sentence);Entity t = (Entity)sequence2.getElements().firstElement();Mark.say(new Object[] { "Translation:", t.asString() });Generator generator = Generator.getGenerator();String output2 = generator.generate(t);Mark.say(new Object[] { output2 });
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void testPatternMatch()
/*  63:    */     throws Exception
/*  64:    */   {
/*  65: 63 */     Translator translator = Translator.getTranslator();
/*  66: 64 */     translator.translate("xx is an object");
/*  67: 65 */     translator.translate("yy is an object");
/*  68: 66 */     Entity pattern = translator.translate("Is xx approaching yy?");
/*  69: 67 */     Entity probe = translator.translate("Is the second object approaching the first object?");
/*  70: 68 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/*  71: 69 */     LList<PairOfEntities> bindings = matcher.match(pattern, probe);
/*  72: 70 */     Mark.say(new Object[] {bindings });
/*  73:    */     
/*  74: 72 */     Entity p0 = translator.translate("Advance video to frame 0").getElement(2);
/*  75: 73 */     Entity p1 = translator.translate("Advance video to frame 300").getElement(2);
/*  76:    */     
/*  77: 75 */     bindings = matcher.match(p0, p1);
/*  78: 76 */     Mark.say(new Object[] {bindings });
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static void main(String[] ignore)
/*  82:    */     throws Exception
/*  83:    */   {
/*  84: 81 */     String test = "Macbeth killed the king with a knife.";
/*  85: 82 */     test = "The first person approached the second person.";
/*  86: 83 */     test = "Lady Macbeth wanted George to give the knife to Macbeth";
/*  87: 84 */     test = "Duncan rewarded Macbeth";
/*  88: 85 */     test = "Mark took the cup from Sally";
/*  89: 86 */     test = "Mark threw the cup toward Sally";
/*  90: 87 */     test = "Mark threw the cup towards Sally";
/*  91: 88 */     test = "Is the first object approaching the second object?";
/*  92: 89 */     test = "The second person is larger than the first person.";
/*  93: 90 */     test = "An engine is part of a car.";
/*  94: 91 */     test = "Russia wanted to harm Georgia.";
/*  95: 92 */     test = "Patrick tried to control Boris.";
/*  96: 93 */     test = "The USA believed the Viet Cong would not attack the USA.";
/*  97: 94 */     test = "The USA believed the Viet Cong knew the USA would defeat the Viet Cong.";
/*  98:    */     
/*  99:    */ 
/* 100: 97 */     test = "Macbeth harmed Duncan.";
/* 101:    */     
/* 102:    */ 
/* 103:    */ 
/* 104:101 */     RoleFrame p1 = new RoleFrame("man").addFeature("large").makeIndefinite();
/* 105:102 */     RoleFrame f1 = new RoleFrame(p1, "hide").past();
/* 106:103 */     RoleFrame f2 = new RoleFrame("truck", "stop").past();
/* 107:    */     
/* 108:    */ 
/* 109:106 */     Translator translator = Translator.getTranslator();
/* 110:    */     
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:111 */     Entity t = translator.translate("Duncan is king");
/* 115:    */     
/* 116:113 */     Mark.say(new Object[] {"Innerese:", t.asString() });
/* 117:114 */     Mark.say(new Object[] {"Generator:", Generator.getGenerator().generate((Entity)t.getElements().firstElement()) });
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     demonstrations.ParserMatcher
 * JD-Core Version:    0.7.0.1
 */