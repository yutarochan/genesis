/*  1:   */ package adamKraft;
/*  2:   */ 
/*  3:   */ import adam.RPCBox;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Connections.NetWireException;
/*  8:   */ import connections.Ports;
/*  9:   */ import connections.WiredBox;
/* 10:   */ import start.StartPreprocessor;
/* 11:   */ import utils.Mark;
/* 12:   */ 
/* 13:   */ public class RemoteAnnotations
/* 14:   */   extends AbstractWiredBox
/* 15:   */ {
/* 16:   */   WiredBox remote;
/* 17:   */   private static final boolean useEnglish = true;
/* 18:   */   private static RemoteAnnotations remoteAnnotations;
/* 19:   */   
/* 20:   */   public static RemoteAnnotations getInstance()
/* 21:   */   {
/* 22:24 */     if (remoteAnnotations == null) {
/* 23:25 */       remoteAnnotations = new RemoteAnnotations();
/* 24:   */     }
/* 25:27 */     return remoteAnnotations;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void playVideo(String name)
/* 29:   */   {
/* 30:31 */     if (this.remote != null) {
/* 31:32 */       ((RPCBox)this.remote).rpc("playVideo", new Object[] { name });
/* 32:   */     } else {
/* 33:35 */       Mark.betterSay(new Object[] {"No video will be played, because I was unable to connect to the annotations source" });
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   private RemoteAnnotations()
/* 38:   */   {
/* 39:40 */     setName("Annotation receiver");
/* 40:   */     try
/* 41:   */     {
/* 42:43 */       this.remote = Connections.subscribe("English annotations");
/* 43:   */       
/* 44:   */ 
/* 45:   */ 
/* 46:   */ 
/* 47:48 */       Connections.getPorts(this).addSignalProcessor("process");
/* 48:49 */       Connections.wire(this.remote, this);
/* 49:50 */       Connections.wire(this.remote, StartPreprocessor.getStartPreprocessor());
/* 50:   */     }
/* 51:   */     catch (Connections.NetWireException e)
/* 52:   */     {
/* 53:53 */       e.printStackTrace();
/* 54:54 */       Mark.betterSay(new Object[] {"Unable to connect to the video annotations source." });
/* 55:   */     }
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void process(Object o)
/* 59:   */   {
/* 60:59 */     Mark.betterSay(
/* 61:   */     
/* 62:   */ 
/* 63:   */ 
/* 64:   */ 
/* 65:   */ 
/* 66:   */ 
/* 67:66 */       new Object[] { "Receiving from simulator", o });
/* 68:60 */     if ((o != null) && ((o instanceof Entity))) {
/* 69:61 */       Mark.betterSay(new Object[] {"Returned:", ((Entity)o).asString() });
/* 70:   */     } else {
/* 71:64 */       Mark.betterSay(new Object[] {"Returned:", o.toString() });
/* 72:   */     }
/* 73:   */   }
/* 74:   */   
/* 75:   */   public static void main(String[] igmore)
/* 76:   */   {
/* 77:69 */     RemoteAnnotations foo = new RemoteAnnotations();
/* 78:70 */     foo.playVideo("test.mov");
/* 79:71 */     Mark.say(new Object[] {"Annotation receiver running" });
/* 80:   */     try
/* 81:   */     {
/* 82:   */       for (;;)
/* 83:   */       {
/* 84:74 */         Thread.sleep(10000L);
/* 85:   */       }
/* 86:   */     }
/* 87:   */     catch (Exception localException)
/* 88:   */     {
/* 89:79 */       Mark.say(new Object[] {"Annotation receiver stopped" });
/* 90:   */     }
/* 91:   */   }
/* 92:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.RemoteAnnotations
 * JD-Core Version:    0.7.0.1
 */