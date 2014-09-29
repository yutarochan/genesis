/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.net.URL;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ 
/*  8:   */ public class SeedlingHack
/*  9:   */ {
/* 10:   */   static
/* 11:   */   {
/* 12:17 */     File[][] tmp = new File[2][];
/* 13:18 */     String base = "one_man_two_man_red_man_blue_man";
/* 14:19 */     File[] dataDirs = { new File(SeedlingHack.class.getResource(base + "/vsr2/rep").getPath()), 
/* 15:20 */       new File(SeedlingHack.class.getResource(base + "/vsr3/rep").getPath()) };
/* 16:21 */     int i = 0;
/* 17:22 */     for (File dataDir : dataDirs)
/* 18:   */     {
/* 19:23 */       tmp[i] = dataDir.listFiles();
/* 20:24 */       i++;
/* 21:   */     }
/* 22:   */   }
/* 23:   */   
/* 24:26 */   public static final File[] vsr2 = tmp[0];
/* 25:27 */   public static final File[] vsr3 = tmp[1];
/* 26:   */   
/* 27:   */   public static File[] allExamplesOf(String verb)
/* 28:   */   {
/* 29:31 */     ArrayList<File> results = new ArrayList();
/* 30:32 */     File[][] tmp = { vsr2, vsr3 };
/* 31:33 */     for (File[] ddir : tmp) {
/* 32:34 */       for (File d : ddir) {
/* 33:35 */         if (d.getName().toLowerCase().contains(verb.toLowerCase())) {
/* 34:36 */           results.add(d);
/* 35:   */         }
/* 36:   */       }
/* 37:   */     }
/* 38:40 */     return (File[])results.toArray(new File[results.size()]);
/* 39:   */   }
/* 40:   */   
/* 41:   */   public static void main(String[] args)
/* 42:   */   {
/* 43:44 */     File[] files = allExamplesOf("give");
/* 44:45 */     System.out.println("barf");
/* 45:   */   }
/* 46:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.SeedlingHack
 * JD-Core Version:    0.7.0.1
 */