/*  1:   */ package carynKrakauer.aligner;
/*  2:   */ 
/*  3:   */ import matthewFay.StoryAlignment.NWAligner;
/*  4:   */ import storyProcessor.ReflectionDescription;
/*  5:   */ 
/*  6:   */ public class ReflectionListAligner
/*  7:   */   extends NWAligner<ReflectionDescription, ReflectionDescription>
/*  8:   */ {
/*  9:   */   public ReflectionListAligner()
/* 10:   */   {
/* 11: 9 */     setGapPenalty(0.0F);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public float sim(ReflectionDescription a, ReflectionDescription b)
/* 15:   */   {
/* 16:14 */     if (a.getName().equals(b.getName())) {
/* 17:15 */       return 1.0F;
/* 18:   */     }
/* 19:16 */     return -1.0F;
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.aligner.ReflectionListAligner
 * JD-Core Version:    0.7.0.1
 */