/*  1:   */ package portico;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import java.util.StringTokenizer;
/*  7:   */ 
/*  8:   */ public class Listifier
/*  9:   */ {
/* 10:   */   public static List<String> listify(String s)
/* 11:   */   {
/* 12:11 */     ArrayList<String> result = new ArrayList();
/* 13:12 */     s = s.trim().toLowerCase();
/* 14:13 */     while ((s.charAt(s.length() - 1) == '?') || (s.charAt(s.length() - 1) == '.') || (s.charAt(s.length() - 1) == '!')) {
/* 15:14 */       s = s.substring(0, s.length() - 1);
/* 16:   */     }
/* 17:16 */     StringTokenizer tokenizer = new StringTokenizer(s);
/* 18:17 */     while (tokenizer.hasMoreTokens()) {
/* 19:18 */       result.add(tokenizer.nextToken());
/* 20:   */     }
/* 21:20 */     return result;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public static void main(String[] ignore)
/* 25:   */   {
/* 26:24 */     System.out.println(listify("This is a test...."));
/* 27:   */   }
/* 28:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     portico.Listifier
 * JD-Core Version:    0.7.0.1
 */