/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import bridge.utils.StringUtils;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.util.Vector;
/*  6:   */ 
/*  7:   */ public class AFactory
/*  8:   */ {
/*  9: 8 */   public static String[] times = { "before", "meets", "overlaps", "during", "starts", "finishes", "equal" };
/* 10:   */   
/* 11:   */   public static Relation createTimeRelation(String type, Entity subject, Entity object)
/* 12:   */   {
/* 13:11 */     if (!StringUtils.testType(type, times))
/* 14:   */     {
/* 15:12 */       System.err.println("Sorry, argument " + type + " provided to createTimeRelation is not a valid time relation.");
/* 16:13 */       return null;
/* 17:   */     }
/* 18:15 */     Relation result = new Relation("time", subject, object);
/* 19:16 */     result.addType(type);
/* 20:17 */     return result;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static Sequence createActionSequence(String type, Vector<Entity> args)
/* 24:   */   {
/* 25:21 */     Sequence result = new Sequence("Action Sequence");
/* 26:22 */     result.addType(type);
/* 27:23 */     result.setElements(args);
/* 28:24 */     return result;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.AFactory
 * JD-Core Version:    0.7.0.1
 */