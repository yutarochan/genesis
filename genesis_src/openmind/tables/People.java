/*  1:   */ package openmind.tables;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ import java.util.Iterator;
/*  5:   */ import java.util.List;
/*  6:   */ import openmind.generate.Corrections;
/*  7:   */ import openmind.generate.OpenMindGenerator;
/*  8:   */ import openmind.generate.OpenMindTable;
/*  9:   */ 
/* 10:   */ public class People
/* 11:   */   extends OpenMindTable
/* 12:   */ {
/* 13:   */   static final String VERB_1 = "vp1";
/* 14:   */   static final String VERB_2 = "vp2";
/* 15:   */   
/* 16:   */   public String getTableName()
/* 17:   */   {
/* 18:12 */     return "people";
/* 19:   */   }
/* 20:   */   
/* 21:   */   public String[] getKeys()
/* 22:   */   {
/* 23:17 */     return new String[] { "vp1", "vp2" };
/* 24:   */   }
/* 25:   */   
/* 26:   */   public String getTriples(HashMap<String, String> data)
/* 27:   */   {
/* 28:20 */     if (!verifyMap(data)) {
/* 29:20 */       return null;
/* 30:   */     }
/* 31:21 */     String verb1 = Corrections.correctApostrophe((String)data.get("vp1"));
/* 32:22 */     String verb2 = Corrections.correctApostrophe((String)data.get("vp2"));
/* 33:   */     
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:27 */     List<String> triples = 
/* 38:28 */       OpenMindGenerator.getTriples("People " + verb1 + " if they " + verb2);
/* 39:30 */     if (triples.size() == 0) {
/* 40:30 */       return null;
/* 41:   */     }
/* 42:31 */     String[] firstVerb = ((String)triples.get(0)).split(" ");
/* 43:32 */     if ((firstVerb.length == 0) || (firstVerb[0].length() == 0)) {
/* 44:32 */       return null;
/* 45:   */     }
/* 46:35 */     String verb = firstVerb[0].substring(1);
/* 47:36 */     String modalStr = "[" + verb + " has_modal may]";
/* 48:37 */     StringBuilder sb = new StringBuilder(modalStr);
/* 49:   */     String s;
/* 50:38 */     for (Iterator localIterator = triples.iterator(); localIterator.hasNext(); sb.append(s)) {
/* 51:38 */       s = (String)localIterator.next();
/* 52:   */     }
/* 53:39 */     return sb.toString();
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.tables.People
 * JD-Core Version:    0.7.0.1
 */