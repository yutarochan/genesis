/*  1:   */ package storyProcessor;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import expert.SimpleGenerator;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ import text.Html;
/*  9:   */ import text.Punctuator;
/* 10:   */ 
/* 11:   */ public class ReflectionTranslator
/* 12:   */ {
/* 13:   */   public static String translateReflection(ReflectionDescription completion)
/* 14:   */   {
/* 15:20 */     String answer = "";
/* 16:21 */     List<Entity> done = new ArrayList();
/* 17:22 */     for (Entity t : completion.getStoryElementsInvolved().getElements()) {
/* 18:23 */       if (!done.contains(t))
/* 19:   */       {
/* 20:26 */         String s = SimpleGenerator.generate(t);
/* 21:   */         
/* 22:28 */         answer = answer + Punctuator.addPeriod(s);
/* 23:29 */         done.add(t);
/* 24:   */       }
/* 25:   */     }
/* 26:31 */     int size = answer.length();
/* 27:32 */     if (size > 0) {
/* 28:33 */       return Html.h1(new StringBuilder("Found instance of ").append(Punctuator.conditionName(completion.getName())).toString()) + Html.normal(answer.substring(0, size - 2));
/* 29:   */     }
/* 30:35 */     return answer;
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.ReflectionTranslator
 * JD-Core Version:    0.7.0.1
 */