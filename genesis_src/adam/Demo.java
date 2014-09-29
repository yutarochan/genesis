/*  1:   */ package adam;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Connections.NetWireException;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredBox;
/*  7:   */ import java.io.BufferedReader;
/*  8:   */ import java.io.IOException;
/*  9:   */ import java.io.InputStreamReader;
/* 10:   */ import java.io.PrintStream;
/* 11:   */ import java.net.MalformedURLException;
/* 12:   */ import java.net.URL;
/* 13:   */ 
/* 14:   */ public class Demo
/* 15:   */ {
/* 16:17 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/* 17:   */   
/* 18:   */   public static class Chatterbox
/* 19:   */     implements WiredBox
/* 20:   */   {
/* 21:   */     public String getName()
/* 22:   */     {
/* 23:22 */       return "Chatterbox: a demonstration of net wire capability";
/* 24:   */     }
/* 25:   */     
/* 26:   */     public void display(Object o)
/* 27:   */     {
/* 28:26 */       System.out.println("the other user wrote: " + o.toString());
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void main(String[] args)
/* 33:   */   {
/* 34:33 */     InputStreamReader converter = new InputStreamReader(System.in);
/* 35:34 */     BufferedReader in = new BufferedReader(converter);
/* 36:35 */     URL serverURL = null;
/* 37:   */     try
/* 38:   */     {
/* 39:37 */       serverURL = new URL(wireServer);
/* 40:   */     }
/* 41:   */     catch (MalformedURLException e)
/* 42:   */     {
/* 43:39 */       e.printStackTrace();
/* 44:40 */       System.exit(1);
/* 45:   */     }
/* 46:   */     try
/* 47:   */     {
/* 48:44 */       String response = "";
/* 49:45 */       while ((response != "p") && (response != "s"))
/* 50:   */       {
/* 51:46 */         System.out.println("Choose whether you are the publisher or the subscriber: type p/s");
/* 52:47 */         response = in.readLine().toLowerCase().trim().intern();
/* 53:   */       }
/* 54:49 */       if (response == "p")
/* 55:   */       {
/* 56:50 */         System.out.println("You are the publisher\nWait for a subscriber to connect, then:");
/* 57:51 */         Connections.useWireServer(serverURL);
/* 58:52 */         Connections.useWireServer(serverURL);
/* 59:53 */         Chatterbox c = new Chatterbox();
/* 60:54 */         Connections.getPorts(c).addSignalProcessor("display");
/* 61:55 */         Connections.publish(c, "the published Chatterbox instance");
/* 62:56 */         System.out.println("type some lines to be sent to the subscriber");
/* 63:57 */         while (response.toLowerCase() != "quit")
/* 64:   */         {
/* 65:58 */           response = in.readLine().trim().intern();
/* 66:59 */           Connections.getPorts(c).transmit(response);
/* 67:60 */           System.out.println("Sent!");
/* 68:   */         }
/* 69:   */       }
/* 70:   */       else
/* 71:   */       {
/* 72:63 */         System.out.println("You are the subscriber. waiting for the publisher...");
/* 73:   */         
/* 74:65 */         Connections.useWireServer(serverURL);
/* 75:66 */         WiredBox proxy = Connections.subscribe("the published Chatterbox instance", -1.0D);
/* 76:   */         
/* 77:68 */         System.out.println("done waiting.");
/* 78:69 */         Chatterbox mine = new Chatterbox();
/* 79:70 */         Connections.getPorts(mine).addSignalProcessor("display");
/* 80:71 */         Connections.wire(proxy, mine);
/* 81:72 */         Connections.wire(mine, proxy);
/* 82:73 */         System.out.println("type some lines to be sent to the publisher");
/* 83:74 */         while (response.toLowerCase() != "quit")
/* 84:   */         {
/* 85:75 */           response = in.readLine().trim().intern();
/* 86:76 */           Connections.getPorts(mine).transmit(response);
/* 87:77 */           System.out.println("Sent!");
/* 88:   */         }
/* 89:   */       }
/* 90:   */     }
/* 91:   */     catch (Connections.NetWireException e)
/* 92:   */     {
/* 93:82 */       e.printStackTrace();
/* 94:   */     }
/* 95:   */     catch (IOException e)
/* 96:   */     {
/* 97:84 */       e.printStackTrace();
/* 98:   */     }
/* 99:   */   }
/* :0:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.Demo
 * JD-Core Version:    0.7.0.1
 */