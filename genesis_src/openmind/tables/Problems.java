/*  1:   */ package openmind.tables;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import openmind.generate.Corrections;
/*  5:   */ import openmind.generate.OpenMindTable;
/*  6:   */ 
/*  7:   */ public class Problems
/*  8:   */   extends OpenMindTable
/*  9:   */ {
/* 10:   */   public static final String PROBLEM = "problem";
/* 11:   */   public static final String TASK = "task";
/* 12:   */   
/* 13:   */   public String getTableName()
/* 14:   */   {
/* 15: 9 */     return "problems";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String[] getKeys()
/* 19:   */   {
/* 20:12 */     return new String[] { "problem", "task" };
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getTriples(HashMap<String, String> data)
/* 24:   */   {
/* 25:15 */     String problem = Corrections.baseCorrections((String)data.get("problem")) + "+1";
/* 26:16 */     String[] taskVP = Corrections.applyVerbCorrections((String)data.get("task"));
/* 27:17 */     String taskVerb = taskVP[0] + (taskVP[0].equals("null") ? "" : "+2");
/* 28:18 */     String taskObject = taskVP[1] + (taskVP[1].equals("null") ? "" : "+3");
/* 29:   */     
/* 30:   */ 
/* 31:21 */     String triples = "[xx " + taskVerb + " " + taskObject + "]" + 
/* 32:22 */       "[" + taskVerb + " if+1 " + problem + "]" + 
/* 33:23 */       "[" + taskVerb + " has_modal can]" + 
/* 34:24 */       "[" + taskVerb + " is_negative yes]";
/* 35:25 */     return triples;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.tables.Problems
 * JD-Core Version:    0.7.0.1
 */