/*  1:   */ package Co57.tools;
/*  2:   */ 
/*  3:   */ import Co57.infrastructure.GenericZMQConnection;
/*  4:   */ import Co57.infrastructure.GenericZMQConnection.Type;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import java.util.Scanner;
/*  7:   */ import utils.Mark;
/*  8:   */ 
/*  9:   */ public class ZMQConsole
/* 10:   */   implements Runnable
/* 11:   */ {
/* 12:28 */   GenericZMQConnection zmqToWiresConnection = null;
/* 13:29 */   GenericZMQConnection zmqFromWireConnection = null;
/* 14:   */   
/* 15:   */   public ZMQConsole(String host, int port)
/* 16:   */   {
/* 17:31 */     InitClient(host, port);
/* 18:   */   }
/* 19:   */   
/* 20:   */   private void InitClient(String host, int port)
/* 21:   */   {
/* 22:35 */     this.zmqToWiresConnection = new GenericZMQConnection(GenericZMQConnection.Type.REQ, host, port);
/* 23:36 */     this.zmqFromWireConnection = new GenericZMQConnection(GenericZMQConnection.Type.REP, host, port + 1);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void runTo()
/* 27:   */   {
/* 28:41 */     Scanner in = new Scanner(System.in);
/* 29:42 */     System.out.println("");
/* 30:43 */     System.out.print(">>> ");
/* 31:44 */     String msg = in.nextLine();
/* 32:45 */     while (!msg.equals("exit"))
/* 33:   */     {
/* 34:46 */       String reply = this.zmqToWiresConnection.request(msg);
/* 35:47 */       System.out.println("Recieved reply: [" + reply + "]");
/* 36:   */       
/* 37:49 */       System.out.println("");
/* 38:50 */       System.out.print(">>> ");
/* 39:51 */       msg = in.nextLine();
/* 40:   */     }
/* 41:53 */     in.close();
/* 42:54 */     System.exit(0);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void run()
/* 46:   */   {
/* 47:   */     for (;;)
/* 48:   */     {
/* 49:60 */       String request = this.zmqFromWireConnection.getRequest();
/* 50:61 */       System.out.println("");
/* 51:62 */       System.out.println("Recieved Request: [" + request + "]");
/* 52:   */       
/* 53:64 */       this.zmqFromWireConnection.reply("Recieved Message");
/* 54:65 */       System.out.println("Sent 'Recieved Message' Confirmation");
/* 55:66 */       System.out.print(">>> ");
/* 56:   */     }
/* 57:   */   }
/* 58:   */   
/* 59:70 */   private boolean started = false;
/* 60:   */   
/* 61:   */   public void StartClient()
/* 62:   */   {
/* 63:72 */     if (!this.started)
/* 64:   */     {
/* 65:73 */       this.started = true;
/* 66:74 */       Thread t = new Thread(this);
/* 67:75 */       t.start();
/* 68:   */     }
/* 69:77 */     runTo();
/* 70:   */   }
/* 71:   */   
/* 72:   */   public static void main(String[] args)
/* 73:   */   {
/* 74:81 */     ZMQConsole console = new ZMQConsole("localhost", 6666);
/* 75:82 */     Mark.say(new Object[] {"Connected to ZMQ-Wire Passthrough!" });
/* 76:83 */     console.StartClient();
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.tools.ZMQConsole
 * JD-Core Version:    0.7.0.1
 */