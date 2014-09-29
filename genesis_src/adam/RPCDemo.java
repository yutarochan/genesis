/*  1:   */ package adam;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Connections.NetWireException;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.io.BufferedReader;
/*  7:   */ import java.io.IOException;
/*  8:   */ import java.io.InputStreamReader;
/*  9:   */ import java.io.PrintStream;
/* 10:   */ import java.net.MalformedURLException;
/* 11:   */ import java.net.URL;
/* 12:   */ 
/* 13:   */ public class RPCDemo
/* 14:   */ {
/* 15:   */   public static class RPCTester
/* 16:   */     implements WiredBox
/* 17:   */   {
/* 18:   */     public String getName()
/* 19:   */     {
/* 20:19 */       return "RPC tester";
/* 21:   */     }
/* 22:   */     
/* 23:21 */     public boolean done = false;
/* 24:   */     
/* 25:   */     public String thisIsATest(Object param1, Object param2)
/* 26:   */     {
/* 27:30 */       return "parameters: " + param1 + " " + param2;
/* 28:   */     }
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static void main(String[] args)
/* 32:   */   {
/* 33:40 */     InputStreamReader converter = new InputStreamReader(System.in);
/* 34:41 */     BufferedReader in = new BufferedReader(converter);
/* 35:42 */     URL serverURL = null;
/* 36:   */     try
/* 37:   */     {
/* 38:44 */       serverURL = new URL(Demo.wireServer);
/* 39:   */     }
/* 40:   */     catch (MalformedURLException e)
/* 41:   */     {
/* 42:46 */       e.printStackTrace();
/* 43:47 */       System.exit(1);
/* 44:   */     }
/* 45:   */     try
/* 46:   */     {
/* 47:51 */       String response = "";
/* 48:52 */       while ((response != "p") && (response != "s"))
/* 49:   */       {
/* 50:53 */         System.out.println("Choose whether you are the publisher or the subscriber: type p/s");
/* 51:54 */         response = in.readLine().toLowerCase().trim().intern();
/* 52:   */       }
/* 53:56 */       if (response == "p")
/* 54:   */       {
/* 55:57 */         System.out.println("You are the publisher\nWaiting for a subscriber to connect");
/* 56:58 */         Connections.useWireServer(serverURL);
/* 57:59 */         RPCTester c = new RPCTester();
/* 58:60 */         Connections.publish(c, "RPC Test Box");
/* 59:61 */         while (!c.done) {
/* 60:   */           try
/* 61:   */           {
/* 62:63 */             Thread.sleep(500L);
/* 63:   */           }
/* 64:   */           catch (InterruptedException e)
/* 65:   */           {
/* 66:65 */             e.printStackTrace();
/* 67:   */           }
/* 68:   */         }
/* 69:   */       }
/* 70:   */       else
/* 71:   */       {
/* 72:69 */         System.out.println("You are the subscriber. waiting for the publisher...");
/* 73:   */         
/* 74:71 */         Connections.useWireServer(serverURL);
/* 75:72 */         WiredBox proxy = Connections.subscribe("RPC Test Box", -1.0D);
/* 76:73 */         System.out.println("done waiting.");
/* 77:74 */         RPCBox box = (RPCBox)proxy;
/* 78:75 */         System.out.println("the rpc result is: " + box.rpc("thisIsATest", new Object[] { "the string I sent", Integer.valueOf(5) }));
/* 79:   */       }
/* 80:   */     }
/* 81:   */     catch (Connections.NetWireException e)
/* 82:   */     {
/* 83:79 */       e.printStackTrace();
/* 84:   */     }
/* 85:   */     catch (IOException e)
/* 86:   */     {
/* 87:81 */       e.printStackTrace();
/* 88:   */     }
/* 89:   */   }
/* 90:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.RPCDemo
 * JD-Core Version:    0.7.0.1
 */