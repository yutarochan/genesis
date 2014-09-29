/*  1:   */ package kevinWhite;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import org.junit.Assert;
/*  6:   */ import org.junit.Test;
/*  7:   */ 
/*  8:   */ public class FasterLLConceptTest
/*  9:   */ {
/* 10:   */   @Test
/* 11:   */   public void testParseSimpleSentencePositive()
/* 12:   */     throws Exception
/* 13:   */   {
/* 14:15 */     String sentence = "Fish can swim.";
/* 15:16 */     String expected_noun = "fish";
/* 16:17 */     String expected_verb = "action travel swim";
/* 17:18 */     Boolean expected_feature = Boolean.valueOf(true);
/* 18:19 */     HashMap result = FasterLLConcept.parseSimpleSentence(sentence);
/* 19:20 */     Entity noun_entity = (Entity)result.get("noun");
/* 20:21 */     String noun = noun_entity.toEnglish();
/* 21:22 */     String verb = (String)result.get("verb");
/* 22:23 */     Boolean feature = (Boolean)result.get("feature");
/* 23:24 */     Assert.assertEquals(expected_noun, noun);
/* 24:25 */     Assert.assertEquals(expected_verb, verb);
/* 25:26 */     Assert.assertEquals(expected_feature, feature);
/* 26:   */   }
/* 27:   */   
/* 28:   */   @Test
/* 29:   */   public void testParseSimpleSentenceNegative()
/* 30:   */     throws Exception
/* 31:   */   {
/* 32:31 */     String sentence = "Fish cannot swim.";
/* 33:32 */     String expected_noun = "fish";
/* 34:33 */     String expected_verb = "action travel swim";
/* 35:34 */     Boolean expected_feature = Boolean.valueOf(false);
/* 36:35 */     HashMap result = FasterLLConcept.parseSimpleSentence(sentence);
/* 37:36 */     Entity noun_entity = (Entity)result.get("noun");
/* 38:37 */     String noun = noun_entity.toEnglish();
/* 39:38 */     String verb = (String)result.get("verb");
/* 40:39 */     Boolean feature = (Boolean)result.get("feature");
/* 41:40 */     Assert.assertEquals(expected_noun, noun);
/* 42:41 */     Assert.assertEquals(expected_verb, verb);
/* 43:42 */     Assert.assertEquals(expected_feature, feature);
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.FasterLLConceptTest
 * JD-Core Version:    0.7.0.1
 */