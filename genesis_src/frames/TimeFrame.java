/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import bridge.utils.StringUtils;
/*  6:   */ import constants.RecognizedRepresentations;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ 
/*  9:   */ public class TimeFrame
/* 10:   */   extends Frame
/* 11:   */ {
/* 12:11 */   public static String[] times = { "before", "equal", "meets", "overlaps", "during", "starts", "finishes" };
/* 13:12 */   public static String FRAMETYPE = (String)RecognizedRepresentations.TIME_REPRESENTATION;
/* 14:   */   private Relation timeRelation;
/* 15:   */   
/* 16:   */   public static Relation createTimeRelation(String type, Entity event1, Entity event2)
/* 17:   */   {
/* 18:14 */     if (!StringUtils.testType(type, times))
/* 19:   */     {
/* 20:15 */       System.err.println("Sorry, argument " + type + " provided to createTimeRelation is not a valid time relation.");
/* 21:16 */       return null;
/* 22:   */     }
/* 23:18 */     Relation result = new Relation(FRAMETYPE, event1, event2);
/* 24:19 */     result.addType(type);
/* 25:20 */     return result;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public static Entity getEvent1(Relation timeRelation)
/* 29:   */   {
/* 30:24 */     if (timeRelation.isA(FRAMETYPE)) {
/* 31:25 */       return timeRelation.getSubject();
/* 32:   */     }
/* 33:27 */     System.err.println("Sorry, " + timeRelation + " is not a valid time relation.");
/* 34:28 */     return null;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static Entity getEvent2(Relation timeRelation)
/* 38:   */   {
/* 39:32 */     if (timeRelation.isA(FRAMETYPE)) {
/* 40:33 */       return timeRelation.getObject();
/* 41:   */     }
/* 42:35 */     System.err.println("Sorry, " + timeRelation + " is not a valid time relation.");
/* 43:36 */     return null;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public static String getTimeType(Relation timeRelation)
/* 47:   */   {
/* 48:40 */     if (timeRelation.isA(FRAMETYPE)) {
/* 49:41 */       return timeRelation.getType();
/* 50:   */     }
/* 51:43 */     System.err.println("Sorry, " + timeRelation + " is not a valid time relation.");
/* 52:44 */     return "";
/* 53:   */   }
/* 54:   */   
/* 55:   */   public TimeFrame(Entity t)
/* 56:   */   {
/* 57:54 */     if (t.isA(FRAMETYPE)) {
/* 58:55 */       this.timeRelation = ((Relation)t);
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   public Entity getThing()
/* 63:   */   {
/* 64:60 */     return this.timeRelation;
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.TimeFrame
 * JD-Core Version:    0.7.0.1
 */