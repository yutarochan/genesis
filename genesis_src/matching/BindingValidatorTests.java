/*  1:   */ package matching;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import matchers.BindingValidator;
/*  7:   */ import matchers.EntityMatcher;
/*  8:   */ import matchers.representations.BindingPair;
/*  9:   */ import matchers.representations.EntityMatchResult;
/* 10:   */ import org.junit.Assert;
/* 11:   */ import org.junit.Test;
/* 12:   */ import start.Generator;
/* 13:   */ import translator.Translator;
/* 14:   */ 
/* 15:   */ public class BindingValidatorTests
/* 16:   */ {
/* 17:   */   @Test(timeout=200000L)
/* 18:   */   public void testExclusion()
/* 19:   */   {
/* 20:24 */     Translator translator = Translator.getTranslator();
/* 21:25 */     Generator generator = Generator.getGenerator();
/* 22:26 */     generator.setStoryMode();
/* 23:27 */     generator.flush();
/* 24:   */     
/* 25:29 */     String[] sentences1 = { "Macbeth is a person.", "Duncan is a person.", "Macbeth kills Duncan." };
/* 26:30 */     String[] sentences2 = { "Hamlet is a person.", "Claudius is a person.", "Hamlet kills Claudius." };
/* 27:   */     
/* 28:32 */     List<Entity> story1 = new ArrayList();
/* 29:33 */     for (String sentence : sentences1) {
/* 30:   */       try
/* 31:   */       {
/* 32:35 */         Entity elt = translator.translate(sentence).getElement(0);
/* 33:36 */         story1.add(elt);
/* 34:   */       }
/* 35:   */       catch (Exception e)
/* 36:   */       {
/* 37:39 */         Assert.fail("Parsing Problem!");
/* 38:   */       }
/* 39:   */     }
/* 40:43 */     generator.setStoryMode();
/* 41:44 */     generator.flush();
/* 42:   */     
/* 43:46 */     List<Entity> story2 = new ArrayList();
/* 44:47 */     for (String sentence : sentences2) {
/* 45:   */       try
/* 46:   */       {
/* 47:49 */         Entity elt = translator.translate(sentence).getElement(0);
/* 48:50 */         story2.add(elt);
/* 49:   */       }
/* 50:   */       catch (Exception e)
/* 51:   */       {
/* 52:53 */         Assert.fail("Parsing Problem!");
/* 53:   */       }
/* 54:   */     }
/* 55:57 */     Entity macbeth = null;
/* 56:58 */     Entity duncan = null;
/* 57:59 */     Entity hamlet = null;
/* 58:60 */     Entity claudius = null;
/* 59:   */     try
/* 60:   */     {
/* 61:62 */       macbeth = ((Entity)story1.get(0)).getObject();
/* 62:63 */       duncan = ((Entity)story1.get(1)).getObject();
/* 63:64 */       hamlet = ((Entity)story2.get(0)).getObject();
/* 64:65 */       claudius = ((Entity)story2.get(1)).getObject();
/* 65:   */     }
/* 66:   */     catch (Exception e)
/* 67:   */     {
/* 68:67 */       Assert.fail("Failed to extract entities, parser may have changed!");
/* 69:   */     }
/* 70:70 */     EntityMatcher em = new EntityMatcher();
/* 71:71 */     BindingValidator bv = new BindingValidator();
/* 72:   */     
/* 73:73 */     EntityMatchResult emr = em.match((Entity)story1.get(2), (Entity)story2.get(2));
/* 74:74 */     Assert.assertNotNull("Binding Validation failed unexpectedly!", bv.validateBindings(emr.bindings));
/* 75:   */     
/* 76:76 */     BindingPair irreleventExclusion = new BindingPair(false, duncan, hamlet, 1.0D);
/* 77:77 */     emr.bindings.add(irreleventExclusion);
/* 78:78 */     Assert.assertNotNull("BindingValidator failed to ignore irrelevent exlusion properly!", bv.validateBindings(emr.bindings));
/* 79:   */     
/* 80:80 */     BindingPair exclusion = new BindingPair(false, macbeth, hamlet, 1.0D);
/* 81:81 */     emr.bindings.add(exclusion);
/* 82:   */     
/* 83:83 */     Assert.assertNull("BindingValidator failed to apply exlusion properly!", bv.validateBindings(emr.bindings));
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matching.BindingValidatorTests
 * JD-Core Version:    0.7.0.1
 */