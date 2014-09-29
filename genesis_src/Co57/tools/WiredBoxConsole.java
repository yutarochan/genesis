/*  1:   */ package Co57.tools;
/*  2:   */ 
/*  3:   */ import Co57.services.Co57Passthrough;
/*  4:   */ import Signals.BetterSignal;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Connections.NetWireException;
/*  8:   */ import connections.Ports;
/*  9:   */ import connections.WiredBox;
/* 10:   */ import java.io.PrintStream;
/* 11:   */ import java.util.Scanner;
/* 12:   */ import utils.Mark;
/* 13:   */ 
/* 14:   */ public class WiredBoxConsole
/* 15:   */   extends AbstractWiredBox
/* 16:   */ {
/* 17:26 */   public static String WIRED_BOX_TO_CONSOLE = "WiredBoxToConsole";
/* 18:   */   
/* 19:   */   public WiredBoxConsole(String name)
/* 20:   */   {
/* 21:29 */     setName(name);
/* 22:   */     
/* 23:31 */     Connections.getPorts(this).addSignalProcessor(Co57Passthrough.ZMQ_VERB_PORT, "processEvent");
/* 24:32 */     Connections.getPorts(this).addSignalProcessor(Co57Passthrough.ZMQ_TRACE_PORT, "processTrace");
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void processEvent(Object o)
/* 28:   */   {
/* 29:36 */     BetterSignal bs = BetterSignal.isSignal(o);
/* 30:37 */     if (bs != null)
/* 31:   */     {
/* 32:38 */       String msg = (String)bs.get(0, String.class);
/* 33:39 */       processMessage(msg, "event");
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void processTrace(Object o)
/* 38:   */   {
/* 39:44 */     BetterSignal bs = BetterSignal.isSignal(o);
/* 40:45 */     if (bs != null)
/* 41:   */     {
/* 42:46 */       String msg = (String)bs.get(0, String.class);
/* 43:47 */       processMessage(msg, "trace");
/* 44:   */     }
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void processMessage(String message, String port)
/* 48:   */   {
/* 49:52 */     System.out.println("");
/* 50:53 */     System.out.println(port + " Message recieved : " + message);
/* 51:54 */     System.out.print(">>> ");
/* 52:   */   }
/* 53:   */   
/* 54:   */   public void run()
/* 55:   */   {
/* 56:58 */     Scanner in = new Scanner(System.in);
/* 57:59 */     String msg = "";
/* 58:60 */     while (!msg.equals("exit"))
/* 59:   */     {
/* 60:61 */       System.out.println("");
/* 61:62 */       System.out.print(">>> ");
/* 62:   */       
/* 63:64 */       msg = in.nextLine();
/* 64:   */       
/* 65:66 */       Connections.getPorts(this).transmit(msg);
/* 66:   */     }
/* 67:68 */     in.close();
/* 68:   */   }
/* 69:   */   
/* 70:   */   public static void main(String[] args)
/* 71:   */   {
/* 72:72 */     WiredBoxConsole console = new WiredBoxConsole(WIRED_BOX_TO_CONSOLE);
/* 73:   */     try
/* 74:   */     {
/* 75:75 */       WiredBox box = Connections.subscribe(Co57Passthrough.ZMQ_SERVER_WIRED_BOX_SERVICE_DEBUGGING);
/* 76:   */       
/* 77:77 */       Connections.wire(Co57Passthrough.ZMQ_VERB_PORT, box, Co57Passthrough.ZMQ_VERB_PORT, console);
/* 78:78 */       Connections.wire(Co57Passthrough.ZMQ_TRACE_PORT, box, Co57Passthrough.ZMQ_TRACE_PORT, console);
/* 79:79 */       Connections.wire(console, box);
/* 80:   */       
/* 81:81 */       Mark.say(new Object[] {"Connected to ZMQ-Wire Passthrough!" });
/* 82:   */     }
/* 83:   */     catch (Connections.NetWireException e)
/* 84:   */     {
/* 85:83 */       e.printStackTrace();
/* 86:   */     }
/* 87:86 */     console.run();
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.tools.WiredBoxConsole
 * JD-Core Version:    0.7.0.1
 */