/*  1:   */ package dylanHolmes;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.Vector;
/*  5:   */ import matchers.StandardMatcher;
/*  6:   */ import minilisp.LList;
/*  7:   */ import translator.Translator;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class Illustration
/* 11:   */ {
/* 12:   */   public static void main(String[] ignore)
/* 13:   */     throws Exception
/* 14:   */   {
/* 15:18 */     Translator.getTranslator().translate("Patrick is an entity");
/* 16:   */     
/* 17:20 */     Translator.getTranslator().translate("Macbeth is a person");
/* 18:   */     
/* 19:22 */     Entity x = (Entity)Translator.getTranslator().translate("Macbeth murdered Duncan").getElements().get(0);
/* 20:23 */     Entity y = (Entity)Translator.getTranslator().translate("Patrick murdered Duncan").getElements().get(0);
/* 21:24 */     Mark.say(new Object[] {x });
/* 22:25 */     Mark.say(new Object[] {y });
/* 23:26 */     LList match = StandardMatcher.getBasicMatcher().match(y, x);
/* 24:27 */     Mark.say(new Object[] {match });
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.Illustration
 * JD-Core Version:    0.7.0.1
 */