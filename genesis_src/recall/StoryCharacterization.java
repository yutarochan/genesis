/*  1:   */ package recall;
/*  2:   */ 
/*  3:   */ import java.util.TreeSet;
/*  4:   */ 
/*  5:   */ public class StoryCharacterization
/*  6:   */ {
/*  7:   */   TreeSet<MatchContribution> contributions;
/*  8:   */   String name;
/*  9:   */   
/* 10:   */   public StoryCharacterization(String name, TreeSet<MatchContribution> contributions)
/* 11:   */   {
/* 12:17 */     this.name = name;
/* 13:18 */     this.contributions = contributions;
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     recall.StoryCharacterization
 * JD-Core Version:    0.7.0.1
 */