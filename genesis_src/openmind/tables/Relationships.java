/*  1:   */ package openmind.tables;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import openmind.generate.Corrections;
/*  5:   */ import openmind.generate.OpenMindTable;
/*  6:   */ 
/*  7:   */ public class Relationships
/*  8:   */   extends OpenMindTable
/*  9:   */ {
/* 10:   */   public static final String OBJECT_1 = "obj1";
/* 11:   */   public static final String OBJECT_2 = "obj2";
/* 12:   */   public static final String RELATION = "relationship";
/* 13:   */   
/* 14:   */   public String getTableName()
/* 15:   */   {
/* 16: 9 */     return "relationships";
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String[] getKeys()
/* 20:   */   {
/* 21:13 */     return new String[] { "obj1", "obj2", "relationship" };
/* 22:   */   }
/* 23:   */   
/* 24:   */   public String getTriples(HashMap<String, String> data)
/* 25:   */   {
/* 26:18 */     String obj1 = Corrections.baseCorrections((String)data.get("obj1")) + "+1";
/* 27:19 */     String obj2 = Corrections.baseCorrections((String)data.get("obj2")) + "+3";
/* 28:20 */     String relation = Corrections.baseCorrections((String)data.get("relationship")) + "+2";
/* 29:   */     
/* 30:   */ 
/* 31:   */ 
/* 32:24 */     String triples = "[xx find+1 " + obj1 + "]" + 
/* 33:25 */       "[find+1 " + relation + " " + obj2 + "]" + 
/* 34:26 */       "[find+1 has_modal may]" + 
/* 35:27 */       "[" + relation + " has_position trailing]" + 
/* 36:28 */       "[" + obj1 + " has_det indefinite]" + 
/* 37:29 */       "[" + obj2 + " has_det indefinite]";
/* 38:30 */     return triples;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.tables.Relationships
 * JD-Core Version:    0.7.0.1
 */