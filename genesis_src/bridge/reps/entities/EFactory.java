/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ 
/*  5:   */ public class EFactory
/*  6:   */ {
/*  7:   */   public static Sequence createEventLadder()
/*  8:   */   {
/*  9:11 */     Sequence result = new Sequence();
/* 10:12 */     result.addType("eventLadder");
/* 11:13 */     return result;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public static Sequence extendEventLadder(Sequence eventLadder, Function event)
/* 15:   */   {
/* 16:17 */     if (!eventLadder.isA("eventLadder"))
/* 17:   */     {
/* 18:17 */       System.err.println("Sorry " + eventLadder + " is not a trajectoryLadder.");return null;
/* 19:   */     }
/* 20:18 */     eventLadder.addElement(event);
/* 21:19 */     return eventLadder;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static Sequence createEventSpace()
/* 25:   */   {
/* 26:23 */     Sequence result = new Sequence();
/* 27:24 */     result.addType("eventSpace");
/* 28:25 */     return result;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static Sequence extendEventSpace(Sequence eventSpace, Function eventLadder)
/* 32:   */   {
/* 33:29 */     if (!eventSpace.isA("eventSpace"))
/* 34:   */     {
/* 35:29 */       System.err.println("Sorry " + eventSpace + " is not a trajectoryLadder.");return null;
/* 36:   */     }
/* 37:30 */     eventSpace.addElement(eventLadder);
/* 38:31 */     return eventSpace;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.EFactory
 * JD-Core Version:    0.7.0.1
 */