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
/* 14:   */ public class GuiDemo
/* 15:   */   extends Demo
/* 16:   */ {
/* 17:   */   public static void main(String[] args)
/* 18:   */   {
/* 19:17 */     InputStreamReader converter = new InputStreamReader(System.in);
/* 20:18 */     BufferedReader in = new BufferedReader(converter);
/* 21:19 */     URL serverURL = null;
/* 22:   */     try
/* 23:   */     {
/* 24:21 */       serverURL = new URL(wireServer);
/* 25:   */     }
/* 26:   */     catch (MalformedURLException e)
/* 27:   */     {
/* 28:23 */       e.printStackTrace();
/* 29:24 */       System.exit(1);
/* 30:   */     }
/* 31:   */     try
/* 32:   */     {
/* 33:27 */       String response = "";
/* 34:28 */       System.out.println("choose what instance you are (start with 1, ascending)");
/* 35:29 */       response = in.readLine().toLowerCase().trim().intern();
/* 36:30 */       int instance = Integer.valueOf(response).intValue();
/* 37:31 */       Demo.Chatterbox myPublished = new Demo.Chatterbox();
/* 38:32 */       Connections.getPorts(myPublished).addSignalProcessor("display");
/* 39:33 */       Connections.useWireServer(serverURL);
/* 40:34 */       Connections.publish(myPublished, "the published Chatterbox instance number " + instance);
/* 41:35 */       WiredBox proxy = null;
/* 42:36 */       Demo.Chatterbox[] myBoxen = new Demo.Chatterbox[instance - 1];
/* 43:37 */       for (int i = instance - 2; i >= 0; i--)
/* 44:   */       {
/* 45:38 */         proxy = Connections.subscribe("the published Chatterbox instance number " + (i + 1), -1.0D);
/* 46:39 */         Demo.Chatterbox mine = new Demo.Chatterbox();
/* 47:40 */         Connections.getPorts(mine).addSignalProcessor("display");
/* 48:41 */         Connections.wire(proxy, mine);
/* 49:42 */         Connections.wire(mine, proxy);
/* 50:43 */         myBoxen[i] = mine;
/* 51:   */       }
/* 52:   */       for (;;)
/* 53:   */       {
/* 54:46 */         for (int i = 0; i < instance - 1; i++)
/* 55:   */         {
/* 56:47 */           Connections.getPorts(myBoxen[i]).transmit("a test string from instance " + instance);
/* 57:   */           try
/* 58:   */           {
/* 59:49 */             Thread.sleep(1000L);
/* 60:   */           }
/* 61:   */           catch (InterruptedException localInterruptedException) {}
/* 62:   */         }
/* 63:   */         try
/* 64:   */         {
/* 65:55 */           Thread.sleep(10000L);
/* 66:   */         }
/* 67:   */         catch (InterruptedException localInterruptedException1) {}
/* 68:   */       }
/* 69:   */     }
/* 70:   */     catch (Connections.NetWireException e)
/* 71:   */     {
/* 72:62 */       e.printStackTrace();
/* 73:   */     }
/* 74:   */     catch (IOException e)
/* 75:   */     {
/* 76:64 */       e.printStackTrace();
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.GuiDemo
 * JD-Core Version:    0.7.0.1
 */