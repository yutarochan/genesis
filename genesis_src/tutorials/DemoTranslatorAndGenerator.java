/*  1:   */ package tutorials;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.Vector;
/*  5:   */ import start.Generator;
/*  6:   */ import translator.Translator;
/*  7:   */ import utils.Mark;
/*  8:   */ 
/*  9:   */ public class DemoTranslatorAndGenerator
/* 10:   */   extends Generator
/* 11:   */ {
/* 12:   */   public static void main(String[] ignore)
/* 13:   */     throws Exception
/* 14:   */   {
/* 15:25 */     String sentence = "John marries Mary because John loves money";
/* 16:   */     
/* 17:27 */     Mark.say(new Object[] {"The input sentence is:", sentence });
/* 18:   */     
/* 19:29 */     Translator translator = Translator.getTranslator();
/* 20:   */     
/* 21:31 */     Entity entity = translator.translate(sentence);
/* 22:   */     
/* 23:33 */     Mark.say(new Object[] {"The Genesisese expression is:", entity });
/* 24:   */     
/* 25:35 */     Generator generator = Generator.getGenerator();
/* 26:   */     
/* 27:37 */     Mark.say(new Object[] {"The English generated is:" });
/* 28:39 */     for (Entity element : entity.getElements())
/* 29:   */     {
/* 30:40 */       Mark.say(new Object[] {"Element:", element });
/* 31:41 */       Mark.say(new Object[] {"Result:", generator.generate(element) });
/* 32:   */       
/* 33:43 */       Mark.say(new Object[] {generator.generate((Entity)translator.translate(sentence).getElements().get(0)) });
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tutorials.DemoTranslatorAndGenerator
 * JD-Core Version:    0.7.0.1
 */