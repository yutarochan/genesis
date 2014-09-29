/*  1:   */ package robertMcIntyre;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.FileInputStream;
/*  5:   */ import java.io.InputStreamReader;
/*  6:   */ import java.io.PrintStream;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.Iterator;
/*  9:   */ import java.util.List;
/* 10:   */ import start.Start;
/* 11:   */ import start.Start.Triple;
/* 12:   */ 
/* 13:   */ public class StartDriver
/* 14:   */ {
/* 15:   */   public static ArrayList<Start.Triple> parseSentence(String s)
/* 16:   */   {
/* 17:15 */     Start start = Start.getStart();
/* 18:16 */     return start.getTriples(start.processSentence(s));
/* 19:   */   }
/* 20:   */   
/* 21:   */   public static String generateSetence(List<Start.Triple> triples)
/* 22:   */   {
/* 23:20 */     Start start = Start.getStart();
/* 24:   */     
/* 25:22 */     StringBuilder joinedTriples = new StringBuilder();
/* 26:   */     Start.Triple t;
/* 27:23 */     for (Iterator localIterator = triples.iterator(); localIterator.hasNext(); joinedTriples.append(t.toString())) {
/* 28:23 */       t = (Start.Triple)localIterator.next();
/* 29:   */     }
/* 30:24 */     return start.generate(joinedTriples.toString());
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static ArrayList<String> readLinesFromFile(String path)
/* 34:   */   {
/* 35:28 */     InputStreamReader isr = null;
/* 36:29 */     ArrayList<String> lines = new ArrayList();
/* 37:   */     try
/* 38:   */     {
/* 39:31 */       isr = new InputStreamReader(new FileInputStream(path), "UTF-8");
/* 40:32 */       BufferedReader rdr = new BufferedReader(isr);
/* 41:33 */       StringBuffer text = new StringBuffer();
/* 42:34 */       String line = rdr.readLine();
/* 43:35 */       while (line != null)
/* 44:   */       {
/* 45:36 */         lines.add(line);
/* 46:37 */         line = rdr.readLine();
/* 47:   */       }
/* 48:39 */       rdr.close();
/* 49:   */     }
/* 50:   */     catch (Exception e)
/* 51:   */     {
/* 52:41 */       e.printStackTrace();
/* 53:42 */       throw new IllegalStateException(e.toString());
/* 54:   */     }
/* 55:44 */     return lines;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static void main(String[] _)
/* 59:   */   {
/* 60:52 */     ArrayList<String> sents = 
/* 61:53 */       readLinesFromFile("/home/igor/data/stress-test/stress-test.sentences");
/* 62:75 */     for (String sent : sents)
/* 63:   */     {
/* 64:76 */       List<Start.Triple> triples = parseSentence(sent);
/* 65:77 */       System.out.println(sent);
/* 66:78 */       System.out.println(generateSetence(triples));
/* 67:   */       Start.Triple t;
/* 68:79 */       for (Iterator localIterator2 = triples.iterator(); localIterator2.hasNext(); System.out.println(t)) {
/* 69:79 */         t = (Start.Triple)localIterator2.next();
/* 70:   */       }
/* 71:80 */       System.out.println();
/* 72:   */     }
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     robertMcIntyre.StartDriver
 * JD-Core Version:    0.7.0.1
 */