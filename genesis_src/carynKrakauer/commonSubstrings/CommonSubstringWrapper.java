/*  1:   */ package carynKrakauer.commonSubstrings;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public class CommonSubstringWrapper
/*  6:   */ {
/*  7:   */   ArrayList<String> reflections;
/*  8:   */   ArrayList<Integer> counts;
/*  9:   */   
/* 10:   */   public CommonSubstringWrapper()
/* 11:   */   {
/* 12:11 */     this.reflections = new ArrayList();
/* 13:12 */     this.counts = new ArrayList();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void addReflection(String reflection, int count)
/* 17:   */   {
/* 18:16 */     this.reflections.add(reflection);
/* 19:17 */     this.counts.add(Integer.valueOf(count));
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int size()
/* 23:   */   {
/* 24:21 */     return this.reflections.size();
/* 25:   */   }
/* 26:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.commonSubstrings.CommonSubstringWrapper
 * JD-Core Version:    0.7.0.1
 */