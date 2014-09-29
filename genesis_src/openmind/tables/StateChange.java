/*  1:   */ package openmind.tables;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import openmind.generate.Corrections;
/*  5:   */ import openmind.generate.OpenMindTable;
/*  6:   */ 
/*  7:   */ public class StateChange
/*  8:   */   extends OpenMindTable
/*  9:   */ {
/* 10:   */   public static final String OBJECT_1 = "object1";
/* 11:   */   public static final String OBJECT_2 = "object2";
/* 12:   */   public static final String PROPERTY_1 = "state1";
/* 13:   */   public static final String PROPERTY_2 = "state2";
/* 14:   */   public static final String ACTION = "action";
/* 15:   */   
/* 16:   */   public String getTableName()
/* 17:   */   {
/* 18:10 */     return "statechange";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String[] getKeys()
/* 22:   */   {
/* 23:18 */     return new String[] { "object1", "object2", "state1", "state2", "action" };
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getTriples(HashMap<String, String> data)
/* 27:   */   {
/* 28:22 */     if (!verifyMap(data)) {
/* 29:22 */       return null;
/* 30:   */     }
/* 31:23 */     String action = Corrections.baseCorrections((String)data.get("action")) + "+5";
/* 32:24 */     String obj1 = Corrections.baseCorrections((String)data.get("object1")) + "+6";
/* 33:25 */     String prop1 = Corrections.baseCorrections((String)data.get("state1")) + "+7";
/* 34:26 */     String obj2 = Corrections.baseCorrections((String)data.get("object2")) + "+8";
/* 35:27 */     String prop2 = Corrections.baseCorrections((String)data.get("state2")) + "+9";
/* 36:   */     
/* 37:   */ 
/* 38:30 */     String triples = "[become+1 if+1 is+2][" + 
/* 39:31 */       obj2 + " become+1 " + prop2 + "]" + 
/* 40:32 */       "[" + obj2 + " has_property+1 " + prop2 + "]" + 
/* 41:33 */       "[" + obj1 + " is+2 " + prop1 + "]" + 
/* 42:34 */       "[" + obj1 + " has_property+2 " + prop1 + "]" + 
/* 43:35 */       "[if+1 is_main yes]" + 
/* 44:36 */       "[become+1 has_tense present]" + 
/* 45:37 */       "[xx " + action + " " + obj1 + "]" + 
/* 46:38 */       "[" + action + " is_main yes]" + 
/* 47:39 */       "[" + action + " has_conjunction and]";
/* 48:40 */     return triples;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.tables.StateChange
 * JD-Core Version:    0.7.0.1
 */