/*  1:   */ package matthewFay.StoryAlignment;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import utils.Mark;
/*  5:   */ 
/*  6:   */ public class NWStringListAligner
/*  7:   */   extends NWAligner<String, String>
/*  8:   */ {
/*  9:   */   public NWStringListAligner()
/* 10:   */   {
/* 11: 9 */     setGapPenalty(0.0F);
/* 12:   */   }
/* 13:   */   
/* 14:   */   public float sim(String a, String b)
/* 15:   */   {
/* 16:14 */     if (a.equals(b)) {
/* 17:15 */       return 1.0F;
/* 18:   */     }
/* 19:16 */     return -1.0F;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static void main(String[] args)
/* 23:   */   {
/* 24:20 */     ArrayList<String> list1 = new ArrayList();
/* 25:21 */     ArrayList<String> list2 = new ArrayList();
/* 26:   */     
/* 27:23 */     list1.add("Hello");
/* 28:24 */     list1.add("World");
/* 29:25 */     list1.add("Foo");
/* 30:   */     
/* 31:27 */     list2.add("Hello");
/* 32:   */     
/* 33:29 */     list2.add("Foo");
/* 34:30 */     list2.add("Bar");
/* 35:   */     
/* 36:32 */     NWStringListAligner aligner = new NWStringListAligner();
/* 37:33 */     Mark.say(new Object[] {aligner.align(list1, list2) });
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.NWStringListAligner
 * JD-Core Version:    0.7.0.1
 */