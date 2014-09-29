/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ 
/*  6:   */ public class ClassPathCruncher
/*  7:   */ {
/*  8:   */   public static void main(String[] args)
/*  9:   */   {
/* 10: 7 */     String cp = System.getProperty("java.class.path");
/* 11: 8 */     String[] cpe = cp.split(":");
/* 12: 9 */     ArrayList<String> bar = new ArrayList();
/* 13:10 */     for (String e : cpe) {
/* 14:11 */       if (!e.startsWith("/usr/lib/eclipse")) {
/* 15:12 */         if (e.startsWith("/home/adk/workspace"))
/* 16:   */         {
/* 17:13 */           e = e.replace("/home/adk/workspace", "/usr/lib/Genesis");
/* 18:14 */           bar.add(e);
/* 19:   */         }
/* 20:   */         else
/* 21:   */         {
/* 22:16 */           System.out.println("BAD: " + e);
/* 23:   */         }
/* 24:   */       }
/* 25:   */     }
/* 26:20 */     cp = "";
/* 27:21 */     for (String e : bar) {
/* 28:22 */       cp = cp + e + ":";
/* 29:   */     }
/* 30:24 */     System.out.println(cp);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.ClassPathCruncher
 * JD-Core Version:    0.7.0.1
 */