/*   1:    */ package Co57.services;
/*   2:    */ 
/*   3:    */ import Co57.infrastructure.GenericZMQConnection;
/*   4:    */ import Co57.infrastructure.GenericZMQConnection.Type;
/*   5:    */ import adam.RPCBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.Set;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class Co57LiveStreamManager
/*  15:    */   implements WiredBox
/*  16:    */ {
/*  17:    */   private static final String service = "Co57LiveStreamManager";
/*  18:    */   private HashMap<Integer, GenericZMQConnection> liveStreams;
/*  19:    */   
/*  20:    */   private Co57LiveStreamManager()
/*  21:    */   {
/*  22: 28 */     Mark.say(new Object[] {"Starting Co57 Live Stream Manager" });
/*  23: 29 */     this.liveStreams = new HashMap();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static void main(String[] args) {}
/*  27:    */   
/*  28:    */   private synchronized void addStreamer(int port, GenericZMQConnection box)
/*  29:    */   {
/*  30: 49 */     this.liveStreams.put(Integer.valueOf(port), box);
/*  31:    */   }
/*  32:    */   
/*  33:    */   private synchronized void stream()
/*  34:    */   {
/*  35: 55 */     HashMap<Integer, GenericZMQConnection> streams = (HashMap)this.liveStreams.clone();
/*  36: 56 */     for (Iterator localIterator = streams.keySet().iterator(); localIterator.hasNext();)
/*  37:    */     {
/*  38: 56 */       int port = ((Integer)localIterator.next()).intValue();
/*  39: 57 */       GenericZMQConnection stream = (GenericZMQConnection)streams.get(Integer.valueOf(port));
/*  40: 58 */       String update = stream.listen();
/*  41: 59 */       if (update.length() > 2)
/*  42:    */       {
/*  43: 60 */         Mark.say(new Object[] {update });
/*  44: 61 */         Connections.getPorts(this).transmit("Stream" + port, update);
/*  45:    */       }
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void connect(int port)
/*  50:    */   {
/*  51: 67 */     if (!this.liveStreams.containsKey(Integer.valueOf(port)))
/*  52:    */     {
/*  53: 68 */       GenericZMQConnection streamer = new GenericZMQConnection(GenericZMQConnection.Type.SUB, port);
/*  54: 69 */       addStreamer(port, streamer);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58: 73 */   private static RPCBox remoteManager = null;
/*  59:    */   
/*  60:    */   public static String connectTo(int port)
/*  61:    */   {
/*  62: 80 */     if (remoteManager == null) {
/*  63:    */       try
/*  64:    */       {
/*  65: 82 */         WiredBox remoteWiredBox = Connections.subscribe("Co57LiveStreamManager");
/*  66: 83 */         remoteManager = (RPCBox)remoteWiredBox;
/*  67:    */       }
/*  68:    */       catch (Exception e)
/*  69:    */       {
/*  70: 85 */         e.printStackTrace();
/*  71: 86 */         Mark.say(new Object[] {"Failed to Connect to Co57!" });
/*  72: 87 */         return null;
/*  73:    */       }
/*  74:    */     }
/*  75: 90 */     remoteManager.rpc("connect", new Object[] { Integer.valueOf(port) });
/*  76: 91 */     return "Stream" + port;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public static WiredBox getStreamer()
/*  80:    */   {
/*  81: 95 */     if (remoteManager == null) {
/*  82:    */       try
/*  83:    */       {
/*  84: 97 */         WiredBox remoteWiredBox = Connections.subscribe("Co57LiveStreamManager");
/*  85: 98 */         remoteManager = (RPCBox)remoteWiredBox;
/*  86:    */       }
/*  87:    */       catch (Exception e)
/*  88:    */       {
/*  89:100 */         e.printStackTrace();
/*  90:101 */         Mark.say(new Object[] {"Failed to Connect to Co57!" });
/*  91:102 */         return null;
/*  92:    */       }
/*  93:    */     }
/*  94:105 */     return (WiredBox)remoteManager;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getName()
/*  98:    */   {
/*  99:110 */     return "Co57LiveStreamManager";
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.services.Co57LiveStreamManager
 * JD-Core Version:    0.7.0.1
 */