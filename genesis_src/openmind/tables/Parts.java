/*  1:   */ package openmind.tables;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import openmind.generate.Corrections;
/*  5:   */ import openmind.generate.OpenMindTable;
/*  6:   */ 
/*  7:   */ public class Parts
/*  8:   */   extends OpenMindTable
/*  9:   */ {
/* 10:   */   public static final String PART = "part";
/* 11:   */   public static final String WHOLE = "obj";
/* 12:   */   
/* 13:   */   public String getTableName()
/* 14:   */   {
/* 15:10 */     return "parts";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String[] getKeys()
/* 19:   */   {
/* 20:13 */     return new String[] { "part", "obj" };
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getTriples(HashMap<String, String> data)
/* 24:   */   {
/* 25:16 */     String part = Corrections.baseCorrections((String)data.get("part")) + "+1";
/* 26:17 */     String whole = Corrections.baseCorrections((String)data.get("obj")) + "+2";
/* 27:   */     
/* 28:19 */     String triples = "[xx is-a+3 " + whole + "]" + 
/* 29:20 */       "[xx contain+1 " + part + "]" + 
/* 30:21 */       "[contain+1 if+2 is-a+3]" + 
/* 31:22 */       "[contain+1 has_modal may]" + 
/* 32:23 */       "[" + whole + " has_det indefinite]" + 
/* 33:24 */       "[" + part + " has_det indefinite]" + 
/* 34:25 */       "[is-a+3 has_tense present]";
/* 35:   */     
/* 36:27 */     return triples;
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.tables.Parts
 * JD-Core Version:    0.7.0.1
 */