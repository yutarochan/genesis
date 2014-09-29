/*  1:   */ package matthewFay.StoryAlignment;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.Comparator;
/*  6:   */ import utils.Mark;
/*  7:   */ 
/*  8:   */ public class SortableAlignmentList
/*  9:   */   extends ArrayList<Alignment>
/* 10:   */ {
/* 11:   */   public void sort()
/* 12:   */   {
/* 13:15 */     Collections.sort(this, new Comparator()
/* 14:   */     {
/* 15:   */       public int compare(Alignment o1, Alignment o2)
/* 16:   */       {
/* 17:19 */         if (o1.score < o2.score) {
/* 18:20 */           return 1;
/* 19:   */         }
/* 20:21 */         if (o1.score > o2.score) {
/* 21:22 */           return -1;
/* 22:   */         }
/* 23:23 */         return 0;
/* 24:   */       }
/* 25:   */     });
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static void main(String[] args)
/* 29:   */   {
/* 30:29 */     SortableAlignmentList list = new SortableAlignmentList();
/* 31:30 */     Alignment<Float, Float> a = new Alignment();
/* 32:31 */     a.score = 500.0F;
/* 33:32 */     Alignment<Float, Float> b = new Alignment();
/* 34:33 */     b.score = 200.0F;
/* 35:34 */     Alignment<Float, Float> c = new Alignment();
/* 36:35 */     c.score = 700.0F;
/* 37:36 */     Alignment<Float, Float> d = new Alignment();
/* 38:37 */     d.score = 0.0F;
/* 39:   */     
/* 40:39 */     list.add(a);
/* 41:40 */     list.add(b);
/* 42:41 */     list.add(c);
/* 43:42 */     list.add(d);
/* 44:   */     
/* 45:44 */     list.sort();
/* 46:45 */     for (Alignment x : list)
/* 47:   */     {
/* 48:46 */       Mark.say(new Object[] {Float.valueOf(x.score) });
/* 49:47 */       Mark.say(new Object[] {x });
/* 50:   */     }
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.SortableAlignmentList
 * JD-Core Version:    0.7.0.1
 */