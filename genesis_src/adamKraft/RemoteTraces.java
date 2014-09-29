/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import adam.RPCBox;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Connections.NetWireException;
/*  7:   */ import connections.Ports;
/*  8:   */ import connections.WiredBox;
/*  9:   */ import start.StartPreprocessor;
/* 10:   */ import utils.Mark;
/* 11:   */ 
/* 12:   */ public class RemoteTraces
/* 13:   */   extends AbstractWiredBox
/* 14:   */ {
/* 15:   */   private WiredBox remote;
/* 16:   */   public static final String serviceName = "Co57 Example";
/* 17:   */   private static RemoteTraces remoteTraces;
/* 18:   */   
/* 19:   */   public static RemoteTraces getInstance()
/* 20:   */   {
/* 21:26 */     if (remoteTraces == null) {
/* 22:27 */       remoteTraces = new RemoteTraces();
/* 23:   */     }
/* 24:29 */     return remoteTraces;
/* 25:   */   }
/* 26:   */   
/* 27:   */   private RemoteTraces()
/* 28:   */   {
/* 29:42 */     Mark.say(new Object[] {"Initializing Co57 connection to attention traces" });
/* 30:43 */     setName("Annotation receiver");
/* 31:   */     try
/* 32:   */     {
/* 33:45 */       this.remote = Connections.subscribe("Co57 Example");
/* 34:46 */       Connections.getPorts(this).addSignalProcessor("process");
/* 35:   */       
/* 36:48 */       Connections.wire(this, StartPreprocessor.getStartPreprocessor());
/* 37:49 */       Connections.wire(this.remote, this);
/* 38:   */     }
/* 39:   */     catch (Connections.NetWireException e)
/* 40:   */     {
/* 41:52 */       e.printStackTrace();
/* 42:53 */       Mark.betterSay(new Object[] {"Unable to connect to the video annotations source." });
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void runStub()
/* 47:   */   {
/* 48:58 */     ((RPCBox)this.remote).rpc("go", new Object[0]);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void process(Object o)
/* 52:   */   {
/* 53:68 */     Connections.getPorts(this).transmit(o);
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.RemoteTraces
 * JD-Core Version:    0.7.0.1
 */