/*   1:    */ package Co57.services;
/*   2:    */ 
/*   3:    */ import Co57.infrastructure.GenericZMQConnection;
/*   4:    */ import Co57.infrastructure.GenericZMQConnection.Type;
/*   5:    */ import Signals.BetterSignal;
/*   6:    */ import com.sun.net.httpserver.Headers;
/*   7:    */ import com.sun.net.httpserver.HttpExchange;
/*   8:    */ import com.sun.net.httpserver.HttpHandler;
/*   9:    */ import com.sun.net.httpserver.HttpServer;
/*  10:    */ import connections.AbstractWiredBox;
/*  11:    */ import connections.Connections;
/*  12:    */ import connections.Connections.NetWireException;
/*  13:    */ import connections.Ports;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.io.OutputStream;
/*  16:    */ import java.io.PrintStream;
/*  17:    */ import java.net.InetSocketAddress;
/*  18:    */ import java.net.MalformedURLException;
/*  19:    */ import java.net.URL;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Date;
/*  22:    */ import java.util.LinkedList;
/*  23:    */ import java.util.Queue;
/*  24:    */ import java.util.concurrent.Executors;
/*  25:    */ import org.jeromq.ZMQ;
/*  26:    */ import org.jeromq.ZMQ.Context;
/*  27:    */ import org.jeromq.ZMQ.Socket;
/*  28:    */ import utils.Mark;
/*  29:    */ 
/*  30:    */ public class Co57Passthrough
/*  31:    */   extends AbstractWiredBox
/*  32:    */   implements HttpHandler
/*  33:    */ {
/*  34: 49 */   public static boolean quiet_mode = false;
/*  35: 51 */   public static String ZMQ_SERVER_WIRED_BOX = "ZMQWiredBoxPassthrough";
/*  36: 52 */   public static String ZMQ_SERVER_WIRED_BOX_SERVICE = "ZMQWiredBoxPassthroughService";
/*  37: 53 */   public static String ZMQ_SERVER_WIRED_BOX_SERVICE_DEBUGGING = "ZMQWiredBoxPassthroughServiceDebugging";
/*  38: 55 */   public static String ZMQ_VERB_PORT = "verb port";
/*  39: 56 */   public static String ZMQ_TRACE_PORT = "trace port";
/*  40:    */   
/*  41:    */   public Co57Passthrough(String name)
/*  42:    */   {
/*  43: 59 */     setName(name);
/*  44:    */     
/*  45: 61 */     Connections.getPorts(this).addSignalProcessor("process");
/*  46:    */   }
/*  47:    */   
/*  48: 64 */   private GenericZMQConnection eventsFromCo57 = null;
/*  49: 65 */   private GenericZMQConnection tracesFromCo57 = null;
/*  50: 66 */   private GenericZMQConnection commandsToCo57 = null;
/*  51:    */   private ZMQ.Context contextEventsFromZMQ;
/*  52:    */   private ZMQ.Socket socketEventsFromZMQ;
/*  53:    */   private ZMQ.Context contextTracesFromZMQ;
/*  54:    */   private ZMQ.Socket socketTracesFromZMQ;
/*  55:    */   private ZMQ.Context contextCommandsToZMQ;
/*  56:    */   private ZMQ.Socket socketCommandsToZMQ;
/*  57:    */   
/*  58:    */   private void InitServer(int port)
/*  59:    */   {
/*  60: 79 */     this.eventsFromCo57 = new GenericZMQConnection(GenericZMQConnection.Type.REP, port);
/*  61:    */     
/*  62: 81 */     this.commandsToCo57 = new GenericZMQConnection(GenericZMQConnection.Type.REQ, port + 1);
/*  63:    */     
/*  64: 83 */     this.tracesFromCo57 = new GenericZMQConnection(GenericZMQConnection.Type.REP, port + 2);
/*  65:    */     
/*  66:    */ 
/*  67: 86 */     this.contextEventsFromZMQ = ZMQ.context(1);
/*  68: 87 */     this.socketEventsFromZMQ = this.contextEventsFromZMQ.socket(4);
/*  69:    */     
/*  70: 89 */     String port_s = String.valueOf(port);
/*  71:    */     
/*  72: 91 */     this.socketEventsFromZMQ.bind("tcp://*:".concat(port_s));
/*  73:    */     
/*  74:    */ 
/*  75: 94 */     this.contextCommandsToZMQ = ZMQ.context(1);
/*  76: 95 */     this.socketCommandsToZMQ = this.contextCommandsToZMQ.socket(3);
/*  77:    */     
/*  78: 97 */     port_s = String.valueOf(port + 1);
/*  79:    */     
/*  80: 99 */     this.socketCommandsToZMQ.bind("tcp://*:".concat(port_s));
/*  81:    */     
/*  82:    */ 
/*  83:102 */     this.contextTracesFromZMQ = ZMQ.context(1);
/*  84:103 */     this.socketTracesFromZMQ = this.contextTracesFromZMQ.socket(4);
/*  85:    */     
/*  86:105 */     port_s = String.valueOf(port + 2);
/*  87:    */     
/*  88:107 */     this.socketTracesFromZMQ.bind("tcp://*:".concat(port_s));
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void process(Object o)
/*  92:    */   {
/*  93:111 */     if ((o instanceof String))
/*  94:    */     {
/*  95:112 */       String msg = (String)o;
/*  96:113 */       AddToBuffer(msg);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:117 */   private ArrayList<String> buffer = new ArrayList();
/* 101:    */   
/* 102:    */   public synchronized String GetNext()
/* 103:    */   {
/* 104:119 */     if (this.buffer.size() < 1) {
/* 105:120 */       return null;
/* 106:    */     }
/* 107:121 */     String next = (String)this.buffer.get(0);
/* 108:122 */     this.buffer.remove(0);
/* 109:123 */     return next;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public synchronized void AddToBuffer(String s)
/* 113:    */   {
/* 114:127 */     this.buffer.add(s);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public synchronized void sendMessageToZMQ(String msg)
/* 118:    */   {
/* 119:131 */     if (!quiet_mode) {
/* 120:131 */       Mark.say(new Object[] { "Received Message from Wire : " + msg });
/* 121:    */     }
/* 122:132 */     Date t = new Date();
/* 123:133 */     storeWireHistory("[" + t.toString() + "] " + msg);
/* 124:134 */     if (!quiet_mode) {
/* 125:134 */       Mark.say(new Object[] { "At " + t.toString() });
/* 126:    */     }
/* 127:136 */     if (!quiet_mode) {
/* 128:136 */       Mark.say(new Object[] { "Sending Message to ZMQ : " + msg });
/* 129:    */     }
/* 130:137 */     this.commandsToCo57.request(msg);
/* 131:    */   }
/* 132:    */   
/* 133:    */   private class FromZMQProcessor
/* 134:    */     implements Runnable
/* 135:    */   {
/* 136:    */     private final GenericZMQConnection connection;
/* 137:    */     private final String wiredPort;
/* 138:    */     private final AbstractWiredBox box;
/* 139:    */     private final Queue<String> log;
/* 140:    */     
/* 141:    */     public FromZMQProcessor(String connection, AbstractWiredBox wiredPort, Queue<String> box)
/* 142:    */     {
/* 143:149 */       this.connection = connection;
/* 144:150 */       this.wiredPort = wiredPort;
/* 145:151 */       this.box = box;
/* 146:152 */       this.log = log;
/* 147:    */     }
/* 148:    */     
/* 149:    */     public void run()
/* 150:    */     {
/* 151:157 */       Mark.say(
/* 152:    */       
/* 153:    */ 
/* 154:    */ 
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:171 */         new Object[] { "Running!" });
/* 166:    */       for (;;)
/* 167:    */       {
/* 168:159 */         String reply = this.connection.getRequest();
/* 169:161 */         if (!Co57Passthrough.quiet_mode) {
/* 170:161 */           Mark.say(new Object[] { "Received message from Co57 " + this.wiredPort + " : " + reply });
/* 171:    */         }
/* 172:162 */         Date t = new Date();
/* 173:163 */         Co57Passthrough.this.storeHistory(this.log, "[" + t.toString() + "] " + reply);
/* 174:164 */         if (!Co57Passthrough.quiet_mode) {
/* 175:164 */           Mark.say(new Object[] { "At " + t.toString() });
/* 176:    */         }
/* 177:165 */         if (!Co57Passthrough.quiet_mode) {
/* 178:165 */           Mark.say(new Object[] { "Sending Message out on Wire " + this.wiredPort + " : " + reply });
/* 179:    */         }
/* 180:166 */         this.connection.reply("Recieved Message");
/* 181:    */         
/* 182:168 */         BetterSignal signal = new BetterSignal(new Object[] { reply });
/* 183:169 */         Connections.getPorts(this.box).transmit(this.wiredPort, signal);
/* 184:    */       }
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void reverseRun()
/* 189:    */   {
/* 190:177 */     String msg = null;
/* 191:    */     for (;;)
/* 192:    */     {
/* 193:    */       try
/* 194:    */       {
/* 195:181 */         Thread.sleep(1L);
/* 196:    */       }
/* 197:    */       catch (InterruptedException e)
/* 198:    */       {
/* 199:183 */         e.printStackTrace();
/* 200:    */       }
/* 201:179 */       while ((msg = GetNext()) != null)
/* 202:    */       {
/* 203:186 */         sendMessageToZMQ(msg);
/* 204:187 */         msg = null;
/* 205:    */       }
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:191 */   private boolean started = false;
/* 210:    */   
/* 211:    */   public void StartServer(int port)
/* 212:    */   {
/* 213:193 */     InitServer(port);
/* 214:195 */     if (!this.started)
/* 215:    */     {
/* 216:196 */       this.started = true;
/* 217:197 */       FromZMQProcessor eventProcessor = new FromZMQProcessor(this.eventsFromCo57, ZMQ_VERB_PORT, this, this.eventMessagesFromZMQ);
/* 218:198 */       Thread eventThread = new Thread(eventProcessor);
/* 219:199 */       eventThread.start();
/* 220:200 */       FromZMQProcessor traceProcessor = new FromZMQProcessor(this.tracesFromCo57, ZMQ_TRACE_PORT, this, this.traceMessagesFromZMQ);
/* 221:201 */       Thread traceThread = new Thread(traceProcessor);
/* 222:202 */       traceThread.start();
/* 223:203 */       if (!quiet_mode) {
/* 224:203 */         Mark.say(new Object[] { "Running ZMQ server for Message Recieving!" });
/* 225:    */       }
/* 226:    */     }
/* 227:205 */     reverseRun();
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void OneWayServer(int port)
/* 231:    */   {
/* 232:209 */     InitServer(port);
/* 233:210 */     if (!this.started)
/* 234:    */     {
/* 235:211 */       this.started = true;
/* 236:212 */       FromZMQProcessor eventProcessor = new FromZMQProcessor(this.eventsFromCo57, ZMQ_VERB_PORT, this, this.eventMessagesFromZMQ);
/* 237:213 */       Thread eventThread = new Thread(eventProcessor);
/* 238:214 */       eventThread.start();
/* 239:215 */       FromZMQProcessor traceProcessor = new FromZMQProcessor(this.tracesFromCo57, ZMQ_TRACE_PORT, this, this.traceMessagesFromZMQ);
/* 240:216 */       Thread traceThread = new Thread(traceProcessor);
/* 241:217 */       traceThread.start();
/* 242:218 */       if (!quiet_mode) {
/* 243:218 */         Mark.say(new Object[] { "Running ZMQ server for Message Recieving!" });
/* 244:    */       }
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:222 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/* 249:    */   
/* 250:    */   public static void main(String[] args)
/* 251:    */     throws Exception
/* 252:    */   {
/* 253:    */     for (;;)
/* 254:    */     {
/* 255:226 */       URL serverURL = null;
/* 256:    */       try
/* 257:    */       {
/* 258:228 */         serverURL = new URL(wireServer);
/* 259:    */       }
/* 260:    */       catch (MalformedURLException e)
/* 261:    */       {
/* 262:230 */         e.printStackTrace();
/* 263:231 */         System.exit(1);
/* 264:    */       }
/* 265:    */       try
/* 266:    */       {
/* 267:235 */         Connections.useWireServer(serverURL);
/* 268:236 */         Co57Passthrough server = new Co57Passthrough(ZMQ_SERVER_WIRED_BOX_SERVICE_DEBUGGING);
/* 269:237 */         Connections.publish(server, ZMQ_SERVER_WIRED_BOX_SERVICE_DEBUGGING);
/* 270:    */         
/* 271:239 */         server.startWebServer();
/* 272:    */         
/* 273:241 */         server.StartServer(6666);
/* 274:    */       }
/* 275:    */       catch (Connections.NetWireException e)
/* 276:    */       {
/* 277:243 */         e.printStackTrace();
/* 278:    */       }
/* 279:    */       catch (Exception e)
/* 280:    */       {
/* 281:245 */         e.printStackTrace();
/* 282:    */       }
/* 283:247 */       System.exit(1);
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:254 */   public int maxMessageHistory = 10;
/* 288:255 */   public Queue<String> messagesFromWires = new LinkedList();
/* 289:    */   
/* 290:    */   public void storeWireHistory(String toStore)
/* 291:    */   {
/* 292:257 */     while (this.messagesFromWires.size() >= this.maxMessageHistory) {
/* 293:258 */       this.messagesFromWires.poll();
/* 294:    */     }
/* 295:260 */     this.messagesFromWires.add(toStore);
/* 296:    */   }
/* 297:    */   
/* 298:262 */   public Queue<String> eventMessagesFromZMQ = new LinkedList();
/* 299:263 */   public Queue<String> traceMessagesFromZMQ = new LinkedList();
/* 300:    */   
/* 301:    */   public void storeHistory(Queue<String> log, String toStore)
/* 302:    */   {
/* 303:265 */     while (log.size() >= this.maxMessageHistory) {
/* 304:266 */       log.poll();
/* 305:    */     }
/* 306:268 */     log.add(toStore);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void startWebServer()
/* 310:    */     throws IOException
/* 311:    */   {
/* 312:273 */     InetSocketAddress addr = new InetSocketAddress(8080);
/* 313:274 */     HttpServer server = HttpServer.create(addr, 0);
/* 314:    */     
/* 315:276 */     server.createContext("/", this);
/* 316:277 */     server.setExecutor(Executors.newCachedThreadPool());
/* 317:278 */     server.start();
/* 318:279 */     System.out.println("Http Server is listening on port 8080");
/* 319:    */   }
/* 320:    */   
/* 321:282 */   public static int lame = 1;
/* 322:    */   
/* 323:    */   public void handle(HttpExchange exchange)
/* 324:    */     throws IOException
/* 325:    */   {
/* 326:286 */     String requestMethod = exchange.getRequestMethod();
/* 327:287 */     if (requestMethod.equalsIgnoreCase("GET"))
/* 328:    */     {
/* 329:288 */       Headers responseHeaders = exchange.getResponseHeaders();
/* 330:289 */       responseHeaders.set("Content-Type", "text/plain");
/* 331:290 */       exchange.sendResponseHeaders(200, 0L);
/* 332:    */       
/* 333:292 */       OutputStream responseBody = exchange.getResponseBody();
/* 334:    */       
/* 335:294 */       responseBody.write("Recent commands from Wired Box Connections \n".getBytes());
/* 336:295 */       for (String s : this.messagesFromWires) {
/* 337:296 */         responseBody.write((s + "\n").getBytes());
/* 338:    */       }
/* 339:298 */       responseBody.write("Recent events from ZMQ Connections \n".getBytes());
/* 340:299 */       for (String s : this.eventMessagesFromZMQ) {
/* 341:300 */         responseBody.write((s + "\n").getBytes());
/* 342:    */       }
/* 343:302 */       responseBody.write("Recent traces from ZMQ Connections \n".getBytes());
/* 344:303 */       for (String s : this.traceMessagesFromZMQ) {
/* 345:304 */         responseBody.write((s + "\n").getBytes());
/* 346:    */       }
/* 347:306 */       responseBody.close();
/* 348:    */     }
/* 349:    */   }
/* 350:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.services.Co57Passthrough
 * JD-Core Version:    0.7.0.1
 */