/*  1:   */ package tutorials;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.Vector;
/*  5:   */ import matchers.StandardMatcher;
/*  6:   */ import matchers.Substitutor;
/*  7:   */ import minilisp.LList;
/*  8:   */ import translator.Translator;
/*  9:   */ import utils.Mark;
/* 10:   */ 
/* 11:   */ public class DemoMatcherAndSubstitutor
/* 12:   */   extends StandardMatcher
/* 13:   */ {
/* 14:   */   public static void main(String[] ignore)
/* 15:   */     throws Exception
/* 16:   */   {
/* 17:18 */     Translator.getTranslator().translate("xx is an entity");
/* 18:   */     
/* 19:20 */     Entity p = (Entity)Translator.getTranslator().translate("xx is the king").getElements().get(0);
/* 20:21 */     Entity d = (Entity)Translator.getTranslator().translate("Sam is the king").getElements().get(0);
/* 21:   */     
/* 22:23 */     Mark.say(new Object[] {"Pattern:", p });
/* 23:24 */     Mark.say(new Object[] {"Datum:  ", d });
/* 24:   */     
/* 25:26 */     Entity target = (Entity)Translator.getTranslator().translate("xx flees the country").getElements().get(0);
/* 26:   */     
/* 27:28 */     LList bindings = StandardMatcher.getBasicMatcher().match(p, d);
/* 28:   */     
/* 29:30 */     Mark.say(new Object[] {"Bindings are", bindings });
/* 30:   */     
/* 31:32 */     Entity result = Substitutor.replace(target, bindings);
/* 32:   */     
/* 33:34 */     Mark.say(new Object[] {"Original is:", target });
/* 34:   */     
/* 35:36 */     Mark.say(new Object[] {"Result is  :", result });
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tutorials.DemoMatcherAndSubstitutor
 * JD-Core Version:    0.7.0.1
 */