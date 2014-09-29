/*  1:   */ package openmind.tables;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import openmind.generate.Corrections;
/*  5:   */ import openmind.generate.OpenMindTable;
/*  6:   */ 
/*  7:   */ public class Locations
/*  8:   */   extends OpenMindTable
/*  9:   */ {
/* 10:   */   public static final String OBJECT = "obj";
/* 11:   */   public static final String ROOM = "room";
/* 12:   */   
/* 13:   */   public String getTableName()
/* 14:   */   {
/* 15:10 */     return "locations";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public String[] getKeys()
/* 19:   */   {
/* 20:13 */     return new String[] { "obj", "room" };
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getTriples(HashMap<String, String> data)
/* 24:   */   {
/* 25:16 */     String room = Corrections.baseCorrections((String)data.get("room")) + "+1";
/* 26:17 */     String object = Corrections.baseCorrections((String)data.get("obj")) + "+2";
/* 27:   */     
/* 28:   */ 
/* 29:20 */     String triples = 
/* 30:21 */       "[xx is-a+3 " + room + "]" + 
/* 31:22 */       "[xx contain+1 " + object + "]" + 
/* 32:23 */       "[contain+1 if+2 is-a+3]" + 
/* 33:24 */       "[contain+1 has_modal may]" + 
/* 34:25 */       "[" + room + " has_det indefinite]" + 
/* 35:26 */       "[" + object + " has_det indefinite]" + 
/* 36:27 */       "[is-a+3 has_tense present]";
/* 37:28 */     return triples;
/* 38:   */   }
/* 39:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.tables.Locations
 * JD-Core Version:    0.7.0.1
 */