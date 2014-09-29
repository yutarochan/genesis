/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ 
/*  6:   */ public class ProcessExternally
/*  7:   */ {
/*  8:   */   public static void processFileExternally(String path)
/*  9:   */   {
/* 10:14 */     String exec = "cmd.exe /C \"" + path + "\"";
/* 11:   */     
/* 12:16 */     Mark.say(new Object[] {"Process externally", exec });
/* 13:   */     try
/* 14:   */     {
/* 15:18 */       Runtime.getRuntime().exec(exec);
/* 16:   */     }
/* 17:   */     catch (IOException e)
/* 18:   */     {
/* 19:21 */       System.err.println("Unable to process file externally: " + path);
/* 20:22 */       e.printStackTrace();
/* 21:   */     }
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.ProcessExternally
 * JD-Core Version:    0.7.0.1
 */