/*   1:    */ package adam;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Connections.NetWireException;
/*   5:    */ import connections.Port;
/*   6:    */ import connections.Ports;
/*   7:    */ import connections.WiredBox;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.net.MalformedURLException;
/*  10:    */ import java.net.URL;
/*  11:    */ 
/*  12:    */ public class Tests
/*  13:    */ {
/*  14:    */   public static class Local
/*  15:    */     implements WiredBox
/*  16:    */   {
/*  17:    */     public void send()
/*  18:    */     {
/*  19: 15 */       Connections.getPorts(this).transmit("some crap");
/*  20: 16 */       Connections.getPorts(this).getPort("output").transmit("sent on specific port");
/*  21:    */     }
/*  22:    */     
/*  23:    */     public String getName()
/*  24:    */     {
/*  25: 21 */       return "bleh";
/*  26:    */     }
/*  27:    */     
/*  28:    */     public void localSignalHandler(Object foo)
/*  29:    */     {
/*  30: 26 */       System.out.println("Local wired box got signal " + foo.toString());
/*  31:    */     }
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static class PublishMe
/*  35:    */     implements WiredBox
/*  36:    */   {
/*  37:    */     public String getName()
/*  38:    */     {
/*  39: 35 */       return "local name";
/*  40:    */     }
/*  41:    */     
/*  42:    */     public void mySignalMethod(Object foo)
/*  43:    */     {
/*  44: 39 */       System.out.println("My signal was " + foo.toString());
/*  45:    */     }
/*  46:    */     
/*  47:    */     public void myInputSignalMethod(Object foo)
/*  48:    */     {
/*  49: 43 */       System.out.println("the signal " + foo.toString() + " was sent to my input port");
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static class OtherLocal
/*  54:    */     implements WiredBox
/*  55:    */   {
/*  56:    */     public String getName()
/*  57:    */     {
/*  58: 51 */       return "who cares";
/*  59:    */     }
/*  60:    */     
/*  61:    */     public void doStuff(Object o)
/*  62:    */     {
/*  63: 55 */       System.out.println("OtherLocal got " + o.toString());
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static void main(String[] args)
/*  68:    */   {
/*  69: 62 */     System.out.println(LibUtil.makeWiredBox(new String[0], new Object[0], new String[] { "foo" }, null));
/*  70:    */     
/*  71: 64 */     URL serverURL = null;
/*  72:    */     try
/*  73:    */     {
/*  74: 67 */       serverURL = new URL("http://localhost:8888/");
/*  75:    */     }
/*  76:    */     catch (MalformedURLException e)
/*  77:    */     {
/*  78: 69 */       e.printStackTrace();
/*  79:    */     }
/*  80:    */     try
/*  81:    */     {
/*  82: 75 */       Connections.useWireServer(serverURL);
/*  83:    */     }
/*  84:    */     catch (Connections.NetWireException e)
/*  85:    */     {
/*  86: 77 */       e.printStackTrace();
/*  87:    */     }
/*  88: 79 */     PublishMe dest = new PublishMe();
/*  89: 80 */     Connections.getPorts(dest).addSignalProcessor("myInputSignalMethod");
/*  90:    */     try
/*  91:    */     {
/*  92: 83 */       Connections.publish(dest, "Test registration");
/*  93:    */     }
/*  94:    */     catch (Connections.NetWireException e)
/*  95:    */     {
/*  96: 85 */       e.printStackTrace();
/*  97:    */     }
/*  98: 87 */     WiredBox subscribed = null;
/*  99:    */     try
/* 100:    */     {
/* 101: 90 */       subscribed = Connections.subscribe("Test registration");
/* 102:    */     }
/* 103:    */     catch (Connections.NetWireException e)
/* 104:    */     {
/* 105: 92 */       e.printStackTrace();
/* 106:    */     }
/* 107: 94 */     System.out.println("dest.getName(): " + dest.getName());
/* 108: 95 */     System.out.println("subscribed.getName(): " + subscribed.getName());
/* 109: 96 */     Local source = new Local();
/* 110: 97 */     Connections.wire(source, "mySignalMethod", subscribed);
/* 111: 98 */     Connections.wire(source, subscribed);
/* 112: 99 */     System.err.println("now testing transmit from unpublished to subscribed:");
/* 113:100 */     source.send();
/* 114:101 */     OtherLocal ol = new OtherLocal();
/* 115:102 */     Connections.getPorts(ol).addSignalProcessor("derp", "doStuff");
/* 116:103 */     Connections.wire("bleedledee", subscribed, "derp", ol);
/* 117:    */     try
/* 118:    */     {
/* 119:105 */       Thread.sleep(500L);
/* 120:    */     }
/* 121:    */     catch (InterruptedException e)
/* 122:    */     {
/* 123:107 */       e.printStackTrace();
/* 124:    */     }
/* 125:109 */     System.err.println("now testing transmit from published to subscribers:");
/* 126:110 */     Connections.getPorts(dest).transmit("bleedledee", "some test string to send to a remote subscriber");
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.Tests
 * JD-Core Version:    0.7.0.1
 */