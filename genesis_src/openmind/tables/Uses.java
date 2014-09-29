/*  1:   */ package openmind.tables;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import openmind.generate.Corrections;
/*  5:   */ import openmind.generate.OpenMindTable;
/*  6:   */ 
/*  7:   */ public class Uses
/*  8:   */   extends OpenMindTable
/*  9:   */ {
/* 10:   */   public static final String OBJECT_1 = "obj";
/* 11:   */   public static final String VERB = "vp";
/* 12:   */   
/* 13:   */   public String getTableName()
/* 14:   */   {
/* 15:11 */     return "uses";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String[] getKeys()
/* 19:   */   {
/* 20:14 */     return new String[] { "obj", "vp" };
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getTriples(HashMap<String, String> data)
/* 24:   */   {
/* 25:20 */     String obj = Corrections.baseCorrections((String)data.get("obj")) + "+1";
/* 26:21 */     String[] useVP = Corrections.applyVerbCorrections((String)data.get("vp"));
/* 27:22 */     String useVerb = useVP[0] + (useVP[0].equals("null") ? "" : "+2");
/* 28:23 */     String useObject = useVP[1] + (useVP[1].equals("null") ? "" : "+3");
/* 29:   */     
/* 30:   */ 
/* 31:26 */     String triples = "[use+1 if+1 want+2][xx use+1 " + 
/* 32:27 */       obj + "]" + 
/* 33:28 */       "[xx want+2 " + useVerb + "]" + 
/* 34:29 */       "[xx " + useVerb + " " + useObject + "]" + 
/* 35:30 */       "[use+1 has_modal may]" + 
/* 36:31 */       "[want+2 has_tense present]" + 
/* 37:32 */       "[" + obj + " has_det indefinite]";
/* 38:33 */     return triples;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.tables.Uses
 * JD-Core Version:    0.7.0.1
 */