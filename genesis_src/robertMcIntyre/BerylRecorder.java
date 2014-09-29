/*  1:   */ package robertMcIntyre;
/*  2:   */ 
/*  3:   */ import Co57.services.Co57Passthrough;
/*  4:   */ import Signals.BetterSignal;
/*  5:   */ import adamKraft.WiredBoxB;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Connections.NetWireException;
/*  8:   */ import connections.Ports;
/*  9:   */ import connections.WiredBox;
/* 10:   */ import java.io.PrintStream;
/* 11:   */ import java.util.Vector;
/* 12:   */ import java.util.concurrent.CountDownLatch;
/* 13:   */ import utils.Mark;
/* 14:   */ 
/* 15:   */ public class BerylRecorder
/* 16:   */   implements WiredBox
/* 17:   */ {
/* 18:   */   public String getName()
/* 19:   */   {
/* 20:16 */     return "BerylRecorder";
/* 21:   */   }
/* 22:   */   
/* 23:18 */   private Vector<String> JSONQueue = new Vector();
/* 24:   */   
/* 25:   */   public Vector<String> getJSON()
/* 26:   */   {
/* 27:20 */     return new Vector(this.JSONQueue);
/* 28:   */   }
/* 29:   */   
/* 30:22 */   public CountDownLatch isDone = new CountDownLatch(1);
/* 31:   */   public final WiredBox server;
/* 32:   */   
/* 33:   */   public BerylRecorder()
/* 34:   */     throws Connections.NetWireException
/* 35:   */   {
/* 36:27 */     Connections.getPorts(this).addSignalProcessor(Co57Passthrough.ZMQ_TRACE_PORT, "processTrace");
/* 37:28 */     Connections.getPorts(this).addSignalProcessor(Co57Passthrough.ZMQ_VERB_PORT, "processVerb");
/* 38:   */     
/* 39:30 */     WiredBox server = Connections.subscribe(Co57Passthrough.ZMQ_SERVER_WIRED_BOX_SERVICE);
/* 40:31 */     this.server = server;
/* 41:32 */     Connections.wire(Co57Passthrough.ZMQ_VERB_PORT, server, Co57Passthrough.ZMQ_VERB_PORT, this);
/* 42:33 */     Connections.wire(Co57Passthrough.ZMQ_TRACE_PORT, server, Co57Passthrough.ZMQ_TRACE_PORT, this);
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void processTrace(Object o)
/* 46:   */   {
/* 47:37 */     System.out.print("Trace: ");
/* 48:38 */     processJSON(o);
/* 49:   */   }
/* 50:   */   
/* 51:   */   public void processVerb(Object o)
/* 52:   */   {
/* 53:42 */     System.out.print("Verb: ");
/* 54:43 */     processJSON(o);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void processJSON(Object o)
/* 58:   */   {
/* 59:47 */     BetterSignal signal = BetterSignal.isSignal(o);
/* 60:48 */     if (signal == null) {
/* 61:48 */       return;
/* 62:   */     }
/* 63:50 */     String json = (String)signal.get(0, String.class);
/* 64:51 */     System.out.println(json);
/* 65:52 */     this.JSONQueue.add(json);
/* 66:53 */     if (json.contains("stopped")) {
/* 67:54 */       this.isDone.countDown();
/* 68:   */     }
/* 69:   */   }
/* 70:   */   
/* 71:   */   public static Vector<String> recordMovieTrace(final String title)
/* 72:   */     throws InterruptedException
/* 73:   */   {
/* 74:   */     try
/* 75:   */     {
/* 76:62 */       BerylRecorder recorder = new BerylRecorder();
/* 77:63 */       co57Out = new WiredBoxB();
/* 78:   */     }
/* 79:   */     catch (Connections.NetWireException e1)
/* 80:   */     {
/* 81:   */       WiredBoxB co57Out;
/* 82:66 */       Mark.say(new Object[] {"Failed to connect to Co57 ZMQ passthrough box." });
/* 83:67 */       e1.printStackTrace();
/* 84:68 */       return null;
/* 85:   */     }
/* 86:   */     WiredBoxB co57Out;
/* 87:   */     BerylRecorder recorder;
/* 88:74 */     new Thread()
/* 89:   */     {
/* 90:   */       public void run()
/* 91:   */       {
/* 92:72 */         BerylRecorder.this.doMovie(title);
/* 93:   */       }
/* 94:74 */     }.start();
/* 95:75 */     recorder.isDone.await();
/* 96:76 */     return recorder.getJSON();
/* 97:   */   }
/* 98:   */   
/* 99:   */   public static void main(String[] ignore)
/* :0:   */     throws Connections.NetWireException, InterruptedException
/* :1:   */   {
/* :2:80 */     System.out.println("total JSON: " + recordMovieTrace("capture-26.mp4").size());
/* :3:   */   }
/* :4:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     robertMcIntyre.BerylRecorder
 * JD-Core Version:    0.7.0.1
 */