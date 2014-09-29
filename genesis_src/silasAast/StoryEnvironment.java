/*  1:   */ package silasAast;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ 
/*  7:   */ public class StoryEnvironment
/*  8:   */   extends AbstractWiredBox
/*  9:   */ {
/* 10:   */   public static String BEGIN;
/* 11:   */   public static String GOAL;
/* 12:   */   public static String DECLARED_AUDIENCE;
/* 13:   */   public static String ACTUAL_AUDIENCE;
/* 14:   */   
/* 15:   */   public void setEnvironment(String goal, String declared, String actual)
/* 16:   */   {
/* 17:42 */     Connections.getPorts(this).transmit(GOAL, goal);
/* 18:43 */     Connections.getPorts(this).transmit(DECLARED_AUDIENCE, declared);
/* 19:44 */     Connections.getPorts(this).transmit(ACTUAL_AUDIENCE, actual);
/* 20:   */   }
/* 21:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silasAast.StoryEnvironment
 * JD-Core Version:    0.7.0.1
 */