/*   1:    */ package matching;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.List;
/*   6:    */ import matchers.EntityMatcher;
/*   7:    */ import matchers.representations.EntityMatchResult;
/*   8:    */ import org.junit.Assert;
/*   9:    */ import org.junit.Test;
/*  10:    */ import start.Generator;
/*  11:    */ import translator.Translator;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class MatcherFeatureTests
/*  15:    */ {
/*  16:    */   public static final String simple0 = "Sally and Mary are cats.";
/*  17:    */   public static final String simple1 = "John and Gary are dogs.";
/*  18:    */   public static final String simple2 = "John likes Sally.";
/*  19:    */   public static final String simple3 = "Gary likes Mary.";
/*  20:    */   public static final String simple4 = "Mary likes Sally.";
/*  21:    */   public static final String simple5 = "Tony is a car.";
/*  22:    */   public static final String simple6 = "Mary likes Tony.";
/*  23:    */   
/*  24:    */   public static void testMatching(String[] sentences1, String[] sentences2, int match1, int match2, boolean match, boolean score_match)
/*  25:    */   {
/*  26: 28 */     Translator translator = Translator.getTranslator();
/*  27: 29 */     Generator generator = Generator.getGenerator();
/*  28: 30 */     generator.setStoryMode();
/*  29: 31 */     generator.flush();
/*  30:    */     
/*  31: 33 */     List<Entity> story1 = new ArrayList();
/*  32: 34 */     String[] arrayOfString = sentences1;int j = sentences1.length;
/*  33: 34 */     for (int i = 0; i < j; i++)
/*  34:    */     {
/*  35: 34 */       String sentence = arrayOfString[i];
/*  36:    */       try
/*  37:    */       {
/*  38: 36 */         Entity elt = translator.translate(sentence).getElement(0);
/*  39: 37 */         story1.add(elt);
/*  40:    */       }
/*  41:    */       catch (Exception e)
/*  42:    */       {
/*  43: 40 */         Assert.fail("Parsing Problem!");
/*  44:    */       }
/*  45:    */     }
/*  46: 44 */     generator.setStoryMode();
/*  47: 45 */     generator.flush();
/*  48:    */     
/*  49: 47 */     List<Entity> story2 = new ArrayList();
/*  50: 48 */     for (String sentence : sentences2) {
/*  51:    */       try
/*  52:    */       {
/*  53: 50 */         Entity elt = translator.translate(sentence).getElement(0);
/*  54: 51 */         story2.add(elt);
/*  55:    */       }
/*  56:    */       catch (Exception e)
/*  57:    */       {
/*  58: 54 */         Assert.fail("Parsing Problem!");
/*  59:    */       }
/*  60:    */     }
/*  61: 58 */     EntityMatcher em = new EntityMatcher();
/*  62: 59 */     if (score_match) {
/*  63: 60 */       em.useScoreMatching();
/*  64:    */     }
/*  65: 62 */     Entity elt1 = (Entity)story1.get(match1);
/*  66: 63 */     Entity elt2 = (Entity)story2.get(match2);
/*  67:    */     
/*  68: 65 */     EntityMatchResult emr = em.match(elt1, elt2);
/*  69:    */     
/*  70: 67 */     Assert.assertTrue("EntityMatcher Match Result Failure on\n" + 
/*  71: 68 */       elt1 + elt2 + "\n" + 
/*  72: 69 */       em.match(elt1, elt2) + "\n" + 
/*  73: 70 */       elt1.getSubject().getPrimedThread() + "\n" + 
/*  74: 71 */       elt2.getSubject().getPrimedThread() + "\n", em.match(elt1, elt2).isMatch() == match);
/*  75:    */   }
/*  76:    */   
/*  77:    */   @Test(timeout=20000L)
/*  78:    */   public void testSimpleMatching()
/*  79:    */   {
/*  80: 84 */     Mark.say(
/*  81:    */     
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:111 */       new Object[] { "Testing basic matching..." });testMatching(new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally.", "Tony is a car.", "Mary likes Tony." }, new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally.", "Tony is a car.", "Mary likes Tony." }, 2, 3, true, false);Mark.say(new Object[] { "Testing basic match fail..." });testMatching(new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally.", "Tony is a car.", "Mary likes Tony." }, new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally.", "Tony is a car.", "Mary likes Tony." }, 1, 3, false, false);Mark.say(new Object[] { "Testing score matching..." });testMatching(new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally.", "Tony is a car.", "Mary likes Tony." }, new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally.", "Tony is a car.", "Mary likes Tony." }, 3, 4, true, true);Mark.say(new Object[] { "Testing negative score matching..." });testMatching(new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally.", "Tony is a car.", "Mary likes Tony." }, new String[] { "Sally and Mary are cats.", "John and Gary are dogs.", "John likes Sally.", "Gary likes Mary.", "Mary likes Sally.", "Tony is a car.", "Mary likes Tony." }, 3, 6, false, true);
/* 108:    */   }
/* 109:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matching.MatcherFeatureTests
 * JD-Core Version:    0.7.0.1
 */