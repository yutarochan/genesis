/*  1:   */ package matthewFay.Utilities;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.FileReader;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import java.util.List;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class SimpleFileReader
/* 11:   */ {
/* 12:   */   private String fileName;
/* 13:   */   private List<String> lines;
/* 14:   */   
/* 15:   */   public SimpleFileReader(String fileName)
/* 16:   */   {
/* 17:17 */     this.fileName = fileName;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public boolean hasLine()
/* 21:   */   {
/* 22:21 */     if (this.lines == null)
/* 23:   */     {
/* 24:22 */       this.lines = new ArrayList();
/* 25:   */       try
/* 26:   */       {
/* 27:24 */         BufferedReader br = new BufferedReader(new FileReader(this.fileName));
/* 28:   */         String line;
/* 29:26 */         while ((line = br.readLine()) != null)
/* 30:   */         {
/* 31:   */           String line;
/* 32:27 */           if ((line.length() > 0) && (!line.startsWith("//"))) {
/* 33:28 */             this.lines.add(line);
/* 34:   */           }
/* 35:   */         }
/* 36:   */       }
/* 37:   */       catch (IOException e)
/* 38:   */       {
/* 39:33 */         Mark.say(new Object[] {e });
/* 40:34 */         return false;
/* 41:   */       }
/* 42:   */     }
/* 43:37 */     if (this.lines.size() > 0) {
/* 44:38 */       return true;
/* 45:   */     }
/* 46:39 */     return false;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String nextLine()
/* 50:   */   {
/* 51:43 */     if (!hasLine()) {
/* 52:44 */       return null;
/* 53:   */     }
/* 54:45 */     String line = (String)this.lines.get(0);
/* 55:46 */     this.lines.remove(0);
/* 56:47 */     return line;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Utilities.SimpleFileReader
 * JD-Core Version:    0.7.0.1
 */