/*  1:   */ package kevinWhite;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ 
/*  5:   */ public class VerbData
/*  6:   */ {
/*  7:   */   private String verb;
/*  8:13 */   private HashMap<String, Boolean> subjectMap = new HashMap();
/*  9:14 */   private HashMap<String, String> threadMap = new HashMap();
/* 10:15 */   public static HashMap<String, VerbData> verbs = new HashMap();
/* 11:   */   
/* 12:   */   private VerbData(String verb)
/* 13:   */   {
/* 14:24 */     this.verb = verb;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static VerbData getVerbData(String verb)
/* 18:   */   {
/* 19:34 */     if (!verbs.containsKey(verb)) {
/* 20:35 */       verbs.put(verb, new VerbData(verb));
/* 21:   */     }
/* 22:37 */     return (VerbData)verbs.get(verb);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void addSubject(String subjectName, String subjectThread, boolean capability)
/* 26:   */   {
/* 27:49 */     this.subjectMap.put(subjectThread, Boolean.valueOf(capability));
/* 28:50 */     this.threadMap.put(subjectThread, subjectName);
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getSubjectThreads()
/* 32:   */   {
/* 33:59 */     String threadStrings = "";
/* 34:60 */     for (String subject : this.subjectMap.keySet()) {
/* 35:61 */       threadStrings = threadStrings.concat(subject + "\n\n");
/* 36:   */     }
/* 37:63 */     return threadStrings;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public String getVerb()
/* 41:   */   {
/* 42:67 */     return this.verb;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public HashMap<String, Boolean> getSubjectMap()
/* 46:   */   {
/* 47:71 */     return this.subjectMap;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public HashMap<String, String> getThreadMap()
/* 51:   */   {
/* 52:75 */     return this.threadMap;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.VerbData
 * JD-Core Version:    0.7.0.1
 */