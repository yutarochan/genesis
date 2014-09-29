/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import adam.Demo;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Connections.NetWireException;
/*  7:   */ import connections.Ports;
/*  8:   */ import connections.WiredBox;
/*  9:   */ import java.io.PrintStream;
/* 10:   */ import java.net.MalformedURLException;
/* 11:   */ import java.net.URL;
/* 12:   */ 
/* 13:   */ public class NetWireExample
/* 14:   */ {
/* 15:   */   public static class BundleClient
/* 16:   */     implements WiredBox
/* 17:   */   {
/* 18:   */     public String getName()
/* 19:   */     {
/* 20:23 */       return "bundle client";
/* 21:   */     }
/* 22:   */     
/* 23:   */     public void sendToServer(Entity input)
/* 24:   */     {
/* 25:26 */       Connections.getPorts(this).transmit(input);
/* 26:   */     }
/* 27:   */     
/* 28:   */     public void receiveFromServer(Object blob)
/* 29:   */     {
/* 30:29 */       System.out.println("got object of class " + blob.getClass() + " from back from server:");
/* 31:30 */       System.out.println(blob);
/* 32:   */     }
/* 33:   */     
/* 34:   */     public BundleClient()
/* 35:   */       throws Connections.NetWireException
/* 36:   */     {
/* 37:33 */       String wireServer = Demo.wireServer;
/* 38:34 */       URL serverURL = null;
/* 39:   */       try
/* 40:   */       {
/* 41:36 */         serverURL = new URL(wireServer);
/* 42:   */       }
/* 43:   */       catch (MalformedURLException e)
/* 44:   */       {
/* 45:38 */         e.printStackTrace();
/* 46:39 */         System.exit(1);
/* 47:   */       }
/* 48:41 */       System.out.println("using wire server " + wireServer);
/* 49:42 */       Connections.useWireServer(serverURL);
/* 50:43 */       WiredBox serverProxy = Connections.subscribe("Example Python Bundle Server");
/* 51:44 */       Connections.wire(this, serverProxy);
/* 52:45 */       Connections.wire(serverProxy, this);
/* 53:46 */       Connections.getPorts(this).addSignalProcessor("receiveFromServer");
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   public static void main(String[] asdf)
/* 58:   */   {
/* 59:   */     try
/* 60:   */     {
/* 61:53 */       BundleClient b = new BundleClient();
/* 62:54 */       System.out.println("sending Things to server:");
/* 63:55 */       for (String name : new String[] { "foo", "bar", "baz", "bax", "qux", "quux", "quuux" })
/* 64:   */       {
/* 65:56 */         Entity t = new Entity(name);
/* 66:57 */         b.sendToServer(t);
/* 67:   */       }
/* 68:   */     }
/* 69:   */     catch (Connections.NetWireException e)
/* 70:   */     {
/* 71:60 */       e.printStackTrace();
/* 72:   */     }
/* 73:   */   }
/* 74:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.NetWireExample
 * JD-Core Version:    0.7.0.1
 */