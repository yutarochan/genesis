/*  1:   */ package frames.utilities;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import bridge.utils.StringUtils;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ import java.util.Vector;
/*  9:   */ 
/* 10:   */ public class AFactory
/* 11:   */ {
/* 12:10 */   public static String[] times = { "Before", "Equal", "Meets", "Overlaps", "During", "Starts", "Finishes" };
/* 13:   */   
/* 14:   */   public static Relation createTimeRelation(String type, Entity subject, Entity object)
/* 15:   */   {
/* 16:12 */     if (!StringUtils.testType(type, times))
/* 17:   */     {
/* 18:13 */       System.err.println("Sorry, argument " + type + " provided to createTimeRelation is not a valid time relation.");
/* 19:14 */       return null;
/* 20:   */     }
/* 21:16 */     Relation result = new Relation("Time Relation", subject, object);
/* 22:17 */     result.addType(type);
/* 23:18 */     return result;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static Sequence createActionSequence(String type, Vector<Entity> args)
/* 27:   */   {
/* 28:22 */     Sequence result = new Sequence("Action Sequence");
/* 29:23 */     result.addType(type);
/* 30:24 */     result.setElements(args);
/* 31:25 */     return result;
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.utilities.AFactory
 * JD-Core Version:    0.7.0.1
 */