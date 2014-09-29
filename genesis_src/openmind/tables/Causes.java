/*  1:   */ package openmind.tables;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import openmind.generate.Corrections;
/*  5:   */ import openmind.generate.OpenMindTable;
/*  6:   */ 
/*  7:   */ public class Causes
/*  8:   */   extends OpenMindTable
/*  9:   */ {
/* 10:   */   public static final String OBJECT_1 = "obj1";
/* 11:   */   public static final String PROPERTY_1 = "prop1";
/* 12:   */   public static final String OBJECT_2 = "obj2";
/* 13:   */   public static final String PROPERTY_2 = "prop2";
/* 14:   */   
/* 15:   */   public String getTableName()
/* 16:   */   {
/* 17:10 */     return "causes";
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String[] getKeys()
/* 21:   */   {
/* 22:18 */     return new String[] { "obj1", "prop1", "obj2", "prop2" };
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String getTriples(HashMap<String, String> data)
/* 26:   */   {
/* 27:22 */     String obj1 = Corrections.baseCorrections((String)data.get("obj1")) + "+1";
/* 28:23 */     String prop1 = Corrections.baseCorrections((String)data.get("prop1")) + "+2";
/* 29:24 */     String obj2 = Corrections.baseCorrections((String)data.get("obj2")) + "+3";
/* 30:25 */     String prop2 = Corrections.baseCorrections((String)data.get("prop2")) + "+4";
/* 31:   */     
/* 32:   */ 
/* 33:28 */     String triples = "[is+2 if+2 is+1][" + 
/* 34:29 */       obj1 + " is+1 " + prop1 + "]" + 
/* 35:30 */       "[" + obj2 + " is+2 " + prop2 + "]" + 
/* 36:31 */       "[" + obj1 + " has_property+1 " + prop1 + "]" + 
/* 37:32 */       "[" + obj2 + " has_property+2 " + prop2 + "]";
/* 38:   */     
/* 39:34 */     return triples;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.tables.Causes
 * JD-Core Version:    0.7.0.1
 */