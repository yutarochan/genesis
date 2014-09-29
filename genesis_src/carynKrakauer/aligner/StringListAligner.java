/*  1:   */ package carynKrakauer.aligner;
/*  2:   */ 
/*  3:   */ import matthewFay.StoryAlignment.NWAligner;
/*  4:   */ 
/*  5:   */ public class StringListAligner
/*  6:   */   extends NWAligner<String, String>
/*  7:   */ {
/*  8:   */   public StringListAligner()
/*  9:   */   {
/* 10: 8 */     setGapPenalty(0.0F);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public float sim(String a, String b)
/* 14:   */   {
/* 15:13 */     if (a.equals(b)) {
/* 16:14 */       return 1.0F;
/* 17:   */     }
/* 18:15 */     return -1.0F;
/* 19:   */   }
/* 20:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.aligner.StringListAligner
 * JD-Core Version:    0.7.0.1
 */