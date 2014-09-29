/*  1:   */ package lexicons;
/*  2:   */ 
/*  3:   */ import java.io.BufferedInputStream;
/*  4:   */ import java.io.BufferedReader;
/*  5:   */ import java.io.InputStream;
/*  6:   */ import java.io.InputStreamReader;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ import java.net.URL;
/*  9:   */ import java.util.HashSet;
/* 10:   */ 
/* 11:   */ public class WorkingVocabulary
/* 12:   */   extends HashSet<Object>
/* 13:   */ {
/* 14:   */   private static final long serialVersionUID = 1L;
/* 15:   */   private static WorkingVocabulary singleton;
/* 16:   */   
/* 17:   */   public static WorkingVocabulary getWorkingVocabulary()
/* 18:   */   {
/* 19:25 */     if (singleton == null)
/* 20:   */     {
/* 21:26 */       singleton = new WorkingVocabulary();
/* 22:27 */       getWorkingVocabulary().readBasicEnglish();
/* 23:   */     }
/* 24:29 */     return singleton;
/* 25:   */   }
/* 26:   */   
/* 27:   */   private void readBasicEnglish()
/* 28:   */   {
/* 29:33 */     URL url = WorkingVocabulary.class.getResource("BasicEnglish.txt");
/* 30:   */     try
/* 31:   */     {
/* 32:36 */       InputStream is = new BufferedInputStream(url.openStream());
/* 33:37 */       System.out.println("InputStream = " + is);
/* 34:38 */       InputStreamReader reader = new InputStreamReader(is);
/* 35:   */       
/* 36:40 */       BufferedReader br = new BufferedReader(reader);
/* 37:   */       String line;
/* 38:42 */       while ((line = br.readLine()) != null)
/* 39:   */       {
/* 40:   */         String line;
/* 41:43 */         readTheLine(line);
/* 42:   */       }
/* 43:   */     }
/* 44:   */     catch (Exception e)
/* 45:   */     {
/* 46:47 */       System.err.println("Unable to find Basic English file");
/* 47:48 */       e.printStackTrace();
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   private void readTheLine(String line)
/* 52:   */   {
/* 53:53 */     if (line.length() == 0) {
/* 54:54 */       return;
/* 55:   */     }
/* 56:56 */     if (line.startsWith("%")) {
/* 57:57 */       return;
/* 58:   */     }
/* 59:60 */     splitLine(line.trim());
/* 60:   */   }
/* 61:   */   
/* 62:   */   private void splitLine(String line)
/* 63:   */   {
/* 64:65 */     int index = 0;
/* 65:66 */     while ((index = line.indexOf(' ')) > 0)
/* 66:   */     {
/* 67:67 */       String word = stripPunctuation(line.substring(0, index));
/* 68:68 */       line = stripPunctuation(line.substring(index + 1).trim());
/* 69:69 */       getWorkingVocabulary().add(word);
/* 70:   */     }
/* 71:72 */     getWorkingVocabulary().add(line);
/* 72:   */   }
/* 73:   */   
/* 74:   */   private String stripPunctuation(String string)
/* 75:   */   {
/* 76:77 */     int index = string.length() - 1;
/* 77:78 */     if ((index >= 0) && (",.;?!".indexOf(string.charAt(index)) >= 0)) {
/* 78:79 */       return stripPunctuation(string.substring(0, index));
/* 79:   */     }
/* 80:81 */     return string;
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     lexicons.WorkingVocabulary
 * JD-Core Version:    0.7.0.1
 */