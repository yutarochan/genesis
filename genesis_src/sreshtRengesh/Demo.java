/*  1:   */ package sreshtRengesh;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.Vector;
/*  6:   */ import translator.Translator;
/*  7:   */ import utils.Mark;
/*  8:   */ 
/*  9:   */ public class Demo
/* 10:   */ {
/* 11:   */   public static ArrayList<Entity> find(String type, Entity e)
/* 12:   */   {
/* 13:18 */     ArrayList<Entity> result = new ArrayList();
/* 14:19 */     if (e.entityP(type))
/* 15:   */     {
/* 16:20 */       result.add(e);
/* 17:   */     }
/* 18:22 */     else if (e.functionP())
/* 19:   */     {
/* 20:23 */       result.addAll(find(type, e.getSubject()));
/* 21:   */     }
/* 22:25 */     else if (e.relationP())
/* 23:   */     {
/* 24:26 */       result.addAll(find(type, e.getSubject()));
/* 25:27 */       result.addAll(find(type, e.getObject()));
/* 26:   */     }
/* 27:29 */     else if (e.sequenceP())
/* 28:   */     {
/* 29:30 */       for (Entity element : e.getElements()) {
/* 30:31 */         result.addAll(find(type, element));
/* 31:   */       }
/* 32:   */     }
/* 33:34 */     return result;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static void main(String[] args)
/* 37:   */     throws Exception
/* 38:   */   {
/* 39:38 */     Translator translator = Translator.getTranslator();
/* 40:39 */     Entity result = translator.translate("Mustafa and Scar are persons");
/* 41:40 */     Mark.say(new Object[] {"Compound result", result });
/* 42:41 */     result = translator.translate("Mustafa killed Scar");
/* 43:42 */     Mark.say(new Object[] {"Complete result", result });
/* 44:   */     
/* 45:44 */     Mark.say(new Object[] {"Everything", find("person", result) });
/* 46:   */     
/* 47:46 */     result = (Entity)result.getElements().get(0);
/* 48:   */     
/* 49:48 */     Mark.say(new Object[] {"Kill example", result, Boolean.valueOf(result.isA("help")) });
/* 50:   */     
/* 51:50 */     result = translator.translate("Mustafa assisted Scar");
/* 52:   */     
/* 53:52 */     result = (Entity)result.getElements().get(0);
/* 54:   */     
/* 55:54 */     Mark.say(new Object[] {"Assist example", result, Boolean.valueOf(result.isA("help")) });
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     sreshtRengesh.Demo
 * JD-Core Version:    0.7.0.1
 */