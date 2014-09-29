/*  1:   */ package carynKrakauer.aligner;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import matthewFay.StoryAlignment.Alignment;
/*  5:   */ import matthewFay.Utilities.Pair;
/*  6:   */ import storyProcessor.ReflectionDescription;
/*  7:   */ import utils.Mark;
/*  8:   */ 
/*  9:   */ public class ReflectionAlignmentUtil
/* 10:   */ {
/* 11:   */   public static Alignment<ReflectionDescription, ReflectionDescription> AlignReflections(ArrayList<ReflectionDescription> story1, ArrayList<ReflectionDescription> story2)
/* 12:   */   {
/* 13:13 */     return alignReflections(story1, story2);
/* 14:   */   }
/* 15:   */   
/* 16:   */   private static Alignment<ReflectionDescription, ReflectionDescription> alignReflections(ArrayList<ReflectionDescription> story1, ArrayList<ReflectionDescription> story2)
/* 17:   */   {
/* 18:18 */     ReflectionListAligner aligner = new ReflectionListAligner();
/* 19:   */     
/* 20:20 */     Alignment<ReflectionDescription, ReflectionDescription> alignment = aligner.align(story1, story2);
/* 21:22 */     for (int i = 0; i < alignment.size(); i++) {
/* 22:23 */       Pair localPair = (Pair)alignment.get(i);
/* 23:   */     }
/* 24:29 */     return alignment;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static Alignment<String, String> AlignStrings(ArrayList<String> story1, ArrayList<String> story2)
/* 28:   */   {
/* 29:33 */     StringListAligner aligner = new StringListAligner();
/* 30:   */     
/* 31:35 */     Alignment<String, String> alignment = aligner.align(story1, story2);
/* 32:   */     
/* 33:37 */     Mark.say(new Object[] {alignment });
/* 34:   */     
/* 35:39 */     return alignment;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.aligner.ReflectionAlignmentUtil
 * JD-Core Version:    0.7.0.1
 */