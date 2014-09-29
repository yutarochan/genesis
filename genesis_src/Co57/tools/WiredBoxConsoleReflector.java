/*  1:   */ package Co57.tools;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ 
/*  7:   */ public class WiredBoxConsoleReflector
/*  8:   */   extends WiredBoxConsole
/*  9:   */ {
/* 10:16 */   public static String WIRED_BOX_TO_CONSOLE = "WiredBoxToConsole";
/* 11:   */   
/* 12:   */   public WiredBoxConsoleReflector(String name)
/* 13:   */   {
/* 14:19 */     super(name);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void processMessage(String message, String port)
/* 18:   */   {
/* 19:24 */     super.processMessage(message, port);
/* 20:25 */     System.out.println("");
/* 21:26 */     Connections.getPorts(this).transmit(message);
/* 22:27 */     System.out.println("Message reflected!");
/* 23:28 */     System.out.print(">>> ");
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.tools.WiredBoxConsoleReflector
 * JD-Core Version:    0.7.0.1
 */