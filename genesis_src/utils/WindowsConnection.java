/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ 
/*  6:   */ public class WindowsConnection
/*  7:   */ {
/*  8:   */   public static boolean run(String arg)
/*  9:   */   {
/* 10:11 */     return run(arg, null, null);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public static boolean run(String arg, File dir)
/* 14:   */   {
/* 15:15 */     return run(arg, null, dir);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static boolean run(String arg, String[] env, File dir)
/* 19:   */   {
/* 20:19 */     boolean debug = false;
/* 21:   */     try
/* 22:   */     {
/* 23:21 */       String osName = System.getProperty("os.name");
/* 24:22 */       String cmd = null;
/* 25:23 */       if ((osName.equals("Windows 7")) || (osName.equals("Windows XP")) || (osName.equals("Windows NT"))) {
/* 26:24 */         cmd = "cmd.exe /C " + arg;
/* 27:26 */       } else if (osName.equals("Windows 95")) {
/* 28:27 */         cmd = "command.com /C " + arg;
/* 29:   */       }
/* 30:29 */       Runtime rt = Runtime.getRuntime();
/* 31:30 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Executing: " + arg + "\n in directory " + dir + "\n with env=" + env });
/* 32:31 */       Process proc = rt.exec(cmd, env, dir);
/* 33:   */       
/* 34:33 */       StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");
/* 35:   */       
/* 36:35 */       StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");
/* 37:   */       
/* 38:37 */       errorGobbler.start();
/* 39:38 */       outputGobbler.start();
/* 40:   */       
/* 41:   */ 
/* 42:41 */       int exitVal = proc.waitFor();
/* 43:42 */       if (exitVal == 0)
/* 44:   */       {
/* 45:43 */         System.out.println("Successfully executed " + arg);
/* 46:44 */         return true;
/* 47:   */       }
/* 48:47 */       Mark.err(new Object[] {"Failed to execute " + arg });
/* 49:48 */       return false;
/* 50:   */     }
/* 51:   */     catch (Throwable t)
/* 52:   */     {
/* 53:52 */       t.printStackTrace();
/* 54:   */     }
/* 55:53 */     return false;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.WindowsConnection
 * JD-Core Version:    0.7.0.1
 */