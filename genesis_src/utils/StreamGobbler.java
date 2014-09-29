/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ import java.io.InputStreamReader;
/*  7:   */ 
/*  8:   */ class StreamGobbler
/*  9:   */   extends Thread
/* 10:   */ {
/* 11:   */   InputStream is;
/* 12:   */   String type;
/* 13:   */   
/* 14:   */   StreamGobbler(InputStream is, String type)
/* 15:   */   {
/* 16:66 */     this.is = is;
/* 17:67 */     this.type = type;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void run()
/* 21:   */   {
/* 22:   */     try
/* 23:   */     {
/* 24:72 */       InputStreamReader isr = new InputStreamReader(this.is);
/* 25:73 */       BufferedReader br = new BufferedReader(isr);
/* 26:74 */       String line = null;
/* 27:75 */       while ((line = br.readLine()) != null) {}
/* 28:   */     }
/* 29:   */     catch (IOException ioe)
/* 30:   */     {
/* 31:80 */       ioe.printStackTrace();
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.StreamGobbler
 * JD-Core Version:    0.7.0.1
 */