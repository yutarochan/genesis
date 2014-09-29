/*  1:   */ package carynKrakauer;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public class VectorMatchWrapper
/*  6:   */ {
/*  7:   */   private double value;
/*  8:   */   private ArrayList<String> matches;
/*  9:   */   private ArrayList<String> story1;
/* 10:   */   private ArrayList<String> story2;
/* 11:   */   private int firstStartIndex;
/* 12:   */   private int firstEndIndex;
/* 13:   */   private int secondStartIndex;
/* 14:   */   private int secondEndIndex;
/* 15:   */   
/* 16:   */   public VectorMatchWrapper(double value, ArrayList<String> story1, ArrayList<String> story2, ArrayList<String> matches, int first_start_index, int first_end_index, int second_start_index, int second_end_index)
/* 17:   */   {
/* 18:30 */     this.value = value;
/* 19:31 */     this.matches = matches;
/* 20:32 */     this.firstStartIndex = first_start_index;
/* 21:33 */     this.firstEndIndex = first_end_index;
/* 22:34 */     this.secondStartIndex = second_start_index;
/* 23:35 */     this.secondEndIndex = second_end_index;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public VectorMatchWrapper(double value, ArrayList<String> matches)
/* 27:   */   {
/* 28:44 */     this.value = value;
/* 29:45 */     this.matches = matches;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public double getValue()
/* 33:   */   {
/* 34:49 */     return this.value;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public ArrayList<String> getMatches()
/* 38:   */   {
/* 39:53 */     return this.matches;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public int getFirstStartIndex()
/* 43:   */   {
/* 44:57 */     return this.firstStartIndex;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public int getFirstEndIndex()
/* 48:   */   {
/* 49:61 */     return this.firstEndIndex;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public int getSecondStartIndex()
/* 53:   */   {
/* 54:65 */     return this.secondStartIndex;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public int getSecondEndIndex()
/* 58:   */   {
/* 59:69 */     return this.secondEndIndex;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public ArrayList<String> getStory1()
/* 63:   */   {
/* 64:73 */     return this.story1;
/* 65:   */   }
/* 66:   */   
/* 67:   */   public ArrayList<String> getStory2()
/* 68:   */   {
/* 69:77 */     return this.story2;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.VectorMatchWrapper
 * JD-Core Version:    0.7.0.1
 */