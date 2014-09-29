/*  1:   */ package matthewFay;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import matchers.EntityMatcher;
/*  5:   */ import matchers.original.BasicMatcherOriginal;
/*  6:   */ import matchers.representations.EntityMatchResult;
/*  7:   */ import minilisp.LList;
/*  8:   */ import start.Generator;
/*  9:   */ import translator.Translator;
/* 10:   */ import utils.Mark;
/* 11:   */ 
/* 12:   */ public class ScratchPad
/* 13:   */ {
/* 14:   */   public static void main(String[] args)
/* 15:   */     throws Exception
/* 16:   */   {}
/* 17:   */   
/* 18:   */   public static void GenesisTests()
/* 19:   */     throws Exception
/* 20:   */   {
/* 21:25 */     LList<String> strings1 = new LList("Hello");
/* 22:26 */     LList<String> strings2 = strings1.cons("World");
/* 23:27 */     LList<String> strings3 = strings1.append(strings2);
/* 24:   */     
/* 25:29 */     Mark.say(new Object[] {strings1 });
/* 26:30 */     Mark.say(new Object[] {strings2 });
/* 27:31 */     Mark.say(new Object[] {strings3 });
/* 28:   */     
/* 29:33 */     String people = "John is a person.";
/* 30:34 */     String people2 = "Mark is a person.";
/* 31:35 */     String happy = "John becomes happy.";
/* 32:36 */     String unhappy = "Mark becomes unhappy.";
/* 33:   */     
/* 34:38 */     Generator gens = Generator.getGenerator();
/* 35:39 */     gens.setStoryMode();
/* 36:40 */     Translator trans = Translator.getTranslator();
/* 37:   */     
/* 38:42 */     trans.translate(people);
/* 39:43 */     trans.translate(people2);
/* 40:44 */     Entity happy_entity = trans.translate(happy).getElement(0);
/* 41:45 */     Entity unhappy_entity = trans.translate(unhappy).getElement(0);
/* 42:46 */     Mark.say(new Object[] {happy_entity });
/* 43:47 */     Mark.say(new Object[] {unhappy_entity });
/* 44:   */     
/* 45:49 */     EntityMatcher em = new EntityMatcher();
/* 46:50 */     EntityMatchResult emr = em.match(happy_entity, unhappy_entity);
/* 47:51 */     Mark.say(new Object[] {"Result:" + emr });
/* 48:52 */     Mark.say(new Object[] {"Result (original):" + BasicMatcherOriginal.getBasicMatcher().match(happy_entity, unhappy_entity) });
/* 49:53 */     ScoreMatcher sm = new ScoreMatcher();
/* 50:54 */     Mark.say(new Object[] {"ScoreMatch:" + sm.scoreMatch(happy_entity, unhappy_entity, new LList()) });
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static void Java8Tests() {}
/* 54:   */   
/* 55:   */   public static int compareByLength(String in, String out)
/* 56:   */   {
/* 57:64 */     return in.length() - out.length();
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.ScratchPad
 * JD-Core Version:    0.7.0.1
 */