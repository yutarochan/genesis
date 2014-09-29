/*  1:   */ package openmind.generate;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import openmind.tables.Causes;
/*  7:   */ import openmind.tables.Desires;
/*  8:   */ import openmind.tables.Locations;
/*  9:   */ import openmind.tables.Parts;
/* 10:   */ import openmind.tables.People;
/* 11:   */ import openmind.tables.Problems;
/* 12:   */ import openmind.tables.Proximity;
/* 13:   */ import openmind.tables.Relationships;
/* 14:   */ import openmind.tables.StateChange;
/* 15:   */ import openmind.tables.Uses;
/* 16:   */ 
/* 17:   */ public class Output
/* 18:   */ {
/* 19:   */   public static final String CAUSES = "source/openmind/rules/causes.txt";
/* 20:   */   public static final String DESIRES = "source/openmind/rules/desires.txt";
/* 21:   */   public static final String LOCATIONS = "source/openmind/rules/locations.txt";
/* 22:   */   public static final String PARTS = "source/openmind/rules/parts.txt";
/* 23:   */   public static final String PEOPLE = "source/openmind/rules/people.txt";
/* 24:   */   public static final String PROBLEMS = "source/openmind/rules/problems.txt";
/* 25:   */   public static final String PROXIMITY = "source/openmind/rules/proximity.txt";
/* 26:   */   public static final String RELATIONSHIPS = "source/openmind/rules/relationships.txt";
/* 27:   */   public static final String STATE = "source/openmind/rules/statechange.txt";
/* 28:   */   public static final String USES = "source/openmind/rules/uses.txt";
/* 29:31 */   public static final HashMap<String, OpenMindTable> fileMap = new HashMap();
/* 30:   */   
/* 31:   */   static
/* 32:   */   {
/* 33:34 */     fileMap.put("source/openmind/rules/causes.txt", new Causes());
/* 34:35 */     fileMap.put("source/openmind/rules/desires.txt", new Desires());
/* 35:36 */     fileMap.put("source/openmind/rules/locations.txt", new Locations());
/* 36:37 */     fileMap.put("source/openmind/rules/parts.txt", new Parts());
/* 37:38 */     fileMap.put("source/openmind/rules/people.txt", new People());
/* 38:39 */     fileMap.put("source/openmind/rules/problems.txt", new Problems());
/* 39:40 */     fileMap.put("source/openmind/rules/proximity.txt", new Proximity());
/* 40:41 */     fileMap.put("source/openmind/rules/relationships.txt", new Relationships());
/* 41:42 */     fileMap.put("source/openmind/rules/statechange.txt", new StateChange());
/* 42:43 */     fileMap.put("source/openmind/rules/uses.txt", new Uses());
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static void writeAllTables()
/* 46:   */   {
/* 47:46 */     for (String dest : fileMap.keySet()) {
/* 48:47 */       OpenMindGenerator.outputAllRulesToFile(
/* 49:48 */         new File(dest), (OpenMindTable)fileMap.get(dest));
/* 50:   */     }
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static void main(String[] ignore)
/* 54:   */   {
/* 55:54 */     for (String sentence : OpenMindGenerator.getRules(new Uses(), 0, 10)) {
/* 56:55 */       System.out.println(sentence);
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.generate.Output
 * JD-Core Version:    0.7.0.1
 */