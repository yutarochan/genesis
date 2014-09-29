/*  1:   */ package silaSayan;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.LinkedList;
/*  7:   */ 
/*  8:   */ public class SummarySupport
/*  9:   */ {
/* 10:   */   public void extractSummaryFromStory(Sequence explicitStory, Sequence reflectionElements, LinkedList<ArrayList<Object>> storyCompilation)
/* 11:   */   {
/* 12:16 */     for (ArrayList<Object> recordedElement : storyCompilation) {
/* 13:17 */       reflectionElements.containsDeprecated((Entity)recordedElement.get(0));
/* 14:   */     }
/* 15:   */   }
/* 16:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.SummarySupport
 * JD-Core Version:    0.7.0.1
 */