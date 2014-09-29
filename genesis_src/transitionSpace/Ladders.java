/*  1:   */ package transitionSpace;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ 
/*  5:   */ public class Ladders
/*  6:   */   extends ArrayList<Ladder>
/*  7:   */ {
/*  8:   */   public void addLadder(Ladder ladder)
/*  9:   */   {
/* 10:13 */     if (size() > 9) {
/* 11:14 */       remove(0);
/* 12:   */     }
/* 13:16 */     add(ladder);
/* 14:   */   }
/* 15:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     transitionSpace.Ladders
 * JD-Core Version:    0.7.0.1
 */