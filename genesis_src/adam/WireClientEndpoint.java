/*   1:    */ package adam;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import connections.Connections.NetWireError;
/*   5:    */ import connections.Connections.NetWireException;
/*   6:    */ import connections.Ports;
/*   7:    */ import connections.WiredBox;
/*   8:    */ import java.io.ByteArrayInputStream;
/*   9:    */ import java.io.ByteArrayOutputStream;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.ObjectInputStream;
/*  12:    */ import java.io.ObjectOutputStream;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.lang.reflect.Method;
/*  15:    */ import java.lang.reflect.Modifier;
/*  16:    */ import java.net.MalformedURLException;
/*  17:    */ import java.net.URL;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Collection;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.Iterator;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import java.util.Observable;
/*  25:    */ import java.util.Observer;
/*  26:    */ import java.util.Set;
/*  27:    */ import java.util.UUID;
/*  28:    */ import java.util.concurrent.ConcurrentHashMap;
/*  29:    */ import org.apache.commons.codec.binary.Base64;
/*  30:    */ import org.apache.xmlrpc.XmlRpcException;
/*  31:    */ import org.apache.xmlrpc.client.TimingOutCallback.TimeoutException;
/*  32:    */ import org.apache.xmlrpc.client.XmlRpcClient;
/*  33:    */ import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
/*  34:    */ 
/*  35:    */ public class WireClientEndpoint
/*  36:    */ {
/*  37:    */   public static final int API_VERSION = 6;
/*  38: 50 */   private Map<Thread, Map<String, Long>> sources = null;
/*  39: 51 */   private Map<WiredBox, String> uuids = null;
/*  40:    */   private Map<String, Map<String, Method>> published;
/*  41:    */   private Map<String, WiredBox> publishedBoxen;
/*  42: 54 */   private Map<String, List<WiredBox>> subscribedBoxen = new ConcurrentHashMap();
/*  43:    */   private static WireClientEndpoint inst;
/*  44:    */   private URL serverUrl;
/*  45:    */   private ErrorHandler errorHandler;
/*  46: 58 */   XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
/*  47:    */   
/*  48:    */   protected WireClientEndpoint()
/*  49:    */   {
/*  50: 61 */     this.config.setEnabledForExtensions(true);
/*  51: 62 */     this.config.setEncoding("UTF-8");
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static synchronized WireClientEndpoint getInstance()
/*  55:    */   {
/*  56: 66 */     if (inst == null) {
/*  57: 67 */       inst = new WireClientEndpoint();
/*  58:    */     }
/*  59: 69 */     return inst;
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected Map<Thread, Map<String, Long>> getSources()
/*  63:    */   {
/*  64: 73 */     if (this.sources == null) {
/*  65: 74 */       this.sources = new ConcurrentHashMap();
/*  66:    */     }
/*  67: 76 */     return this.sources;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean initialize(URL url)
/*  71:    */   {
/*  72: 80 */     String surl = url.toString();
/*  73: 81 */     surl = surl.endsWith("/") ? surl.substring(0, surl.length() - 1) : surl;
/*  74:    */     try
/*  75:    */     {
/*  76: 83 */       url = new URL(surl + "/xmlrpc");
/*  77:    */     }
/*  78:    */     catch (MalformedURLException e)
/*  79:    */     {
/*  80: 86 */       throw new Connections.NetWireError(e);
/*  81:    */     }
/*  82: 88 */     return setServerURL(url);
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected Object execute(String method, Object[] args)
/*  86:    */     throws XmlRpcException
/*  87:    */   {
/*  88: 92 */     return execute(method, args, null);
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected Object execute(String method, Object[] args, Integer timeout)
/*  92:    */     throws XmlRpcException
/*  93:    */   {
/*  94: 97 */     XmlRpcClient client = new XmlRpcClient();
/*  95:    */     
/*  96: 99 */     client.setConfig(this.config);
/*  97:100 */     if (timeout != null) {
/*  98:101 */       this.config.setReplyTimeout(timeout.intValue());
/*  99:    */     }
/* 100:103 */     return client.execute(method, args);
/* 101:    */   }
/* 102:    */   
/* 103:    */   protected synchronized boolean setServerURL(URL url)
/* 104:    */   {
/* 105:107 */     boolean changed = false;
/* 106:108 */     if ((this.serverUrl != null) && (!url.equals(this.serverUrl)))
/* 107:    */     {
/* 108:109 */       System.out.println("Warning: switching between wire servers is untested!");
/* 109:110 */       WiredBoxStubFactory.getInstance().reset();
/* 110:111 */       changed = true;
/* 111:    */     }
/* 112:112 */     else if (this.serverUrl == null)
/* 113:    */     {
/* 114:113 */       this.serverUrl = url;
/* 115:114 */       this.config.setServerURL(url);
/* 116:115 */       changed = true;
/* 117:    */     }
/* 118:118 */     return changed;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public synchronized URL getServerURL()
/* 122:    */   {
/* 123:121 */     return this.serverUrl;
/* 124:    */   }
/* 125:    */   
/* 126:124 */   private boolean initialized = false;
/* 127:    */   
/* 128:    */   public static void setInitialized()
/* 129:    */   {
/* 130:126 */     getInstance().initialized = true;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static boolean isInitialized()
/* 134:    */   {
/* 135:129 */     return getInstance().initialized;
/* 136:    */   }
/* 137:    */   
/* 138:    */   protected void call(String sourceID, String destID, String methodName, Object[] args)
/* 139:    */   {
/* 140:134 */     Object[] serArgs = new String[args.length];
/* 141:135 */     int i = 0;
/* 142:    */     try
/* 143:    */     {
/* 144:137 */       for (Object o : args)
/* 145:    */       {
/* 146:138 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 147:139 */         ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 148:140 */         oos.writeObject(o);
/* 149:141 */         oos.close();
/* 150:142 */         serArgs[i] = new String(Base64.encodeBase64(baos.toByteArray()));
/* 151:143 */         i++;
/* 152:    */       }
/* 153:145 */       execute("WireServerRPC.callIn", new Object[] { getClientId(), destID, methodName, serArgs });
/* 154:    */     }
/* 155:    */     catch (XmlRpcException e)
/* 156:    */     {
/* 157:147 */       throw new Connections.NetWireError(e);
/* 158:    */     }
/* 159:    */     catch (IOException e)
/* 160:    */     {
/* 161:149 */       throw new Connections.NetWireError(e);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   protected synchronized Object rpc(String destID, String methodName, Object[] args)
/* 166:    */   {
/* 167:    */     try
/* 168:    */     {
/* 169:164 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 170:165 */       ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 171:166 */       oos.writeObject(args);
/* 172:167 */       oos.close();
/* 173:168 */       String serArgs = new String(Base64.encodeBase64(baos.toByteArray()));
/* 174:169 */       Object result = execute("WireServerRPC.twoHopRPC", new Object[] { getClientId(), destID, methodName, serArgs });
/* 175:170 */       byte[] data = Base64.decodeBase64(((String)result).getBytes());
/* 176:171 */       ObjectInputStream ois = new ObjectInputStream(
/* 177:172 */         new ByteArrayInputStream(data));
/* 178:173 */       result = ois.readObject();
/* 179:174 */       ois.close();
/* 180:175 */       Object[] reserr = (Object[])result;
/* 181:176 */       Object res = reserr[0];
/* 182:177 */       Object err = reserr[1];
/* 183:178 */       if (err != null)
/* 184:    */       {
/* 185:179 */         Connections.NetWireError e = new Connections.NetWireError("The remote method threw an exception.");
/* 186:180 */         e.initCause(((Throwable)err).getCause());
/* 187:181 */         throw e;
/* 188:    */       }
/* 189:183 */       return res;
/* 190:    */     }
/* 191:    */     catch (IOException e)
/* 192:    */     {
/* 193:186 */       throw new Connections.NetWireError(e);
/* 194:    */     }
/* 195:    */     catch (XmlRpcException e)
/* 196:    */     {
/* 197:188 */       throw new Connections.NetWireError(e);
/* 198:    */     }
/* 199:    */     catch (ClassNotFoundException e)
/* 200:    */     {
/* 201:190 */       throw new Connections.NetWireError(e);
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected void replyError(long sequenceNumber, Throwable e)
/* 206:    */   {
/* 207:195 */     e.printStackTrace();
/* 208:196 */     System.out.println("sending error to caller...");
/* 209:197 */     chainedRPCReply(sequenceNumber, null, e);
/* 210:    */   }
/* 211:    */   
/* 212:    */   protected void replyRPCResult(long sequenceNumber, Object result)
/* 213:    */   {
/* 214:201 */     chainedRPCReply(sequenceNumber, result, null);
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected void chainedRPCReply(long seq, Object res, Throwable err)
/* 218:    */   {
/* 219:    */     try
/* 220:    */     {
/* 221:206 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 222:207 */       ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 223:208 */       oos.writeObject(new Object[] { res, err });
/* 224:209 */       oos.close();
/* 225:210 */       String serRes = new String(Base64.encodeBase64(baos.toByteArray()));
/* 226:211 */       execute("WireServerRPC.twoHopRPCReply", new Object[] { Long.valueOf(seq), serRes });
/* 227:    */     }
/* 228:    */     catch (IOException e)
/* 229:    */     {
/* 230:213 */       e.printStackTrace();
/* 231:214 */       System.out.println("bad news: error while replying to server in response to RPC");
/* 232:    */     }
/* 233:    */     catch (XmlRpcException localXmlRpcException) {}
/* 234:    */   }
/* 235:    */   
/* 236:    */   protected void call(String destID, String methodName, Object[] args)
/* 237:    */   {
/* 238:223 */     call((String)((Map)getInstance().getSources().get(Thread.currentThread())).keySet().iterator().next(), destID, methodName, args);
/* 239:    */     
/* 240:225 */     getInstance().getSources().remove(Thread.currentThread());
/* 241:    */   }
/* 242:    */   
/* 243:    */   private void validate()
/* 244:    */   {
/* 245:229 */     if (this.serverUrl == null) {
/* 246:230 */       throw new Connections.NetWireError("did you forget to call Connections.useWireServer(URL) first?");
/* 247:    */     }
/* 248:232 */     if (this.serverUrl != getInstance().getServerURL()) {
/* 249:233 */       System.out.println("Warning! using multiple wire servers is untested and may have dire consequences!");
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   public String[] getRemoteMethods(String uid)
/* 254:    */   {
/* 255:238 */     validate();
/* 256:    */     try
/* 257:    */     {
/* 258:241 */       Object[] results = (Object[])execute("WireServerRPC.getMethods", new Object[] { uid });
/* 259:242 */       String[] sres = new String[results.length];
/* 260:243 */       for (int i = 0; i < results.length; i++) {
/* 261:244 */         sres[i] = ((String)results[i]);
/* 262:    */       }
/* 263:246 */       return sres;
/* 264:    */     }
/* 265:    */     catch (XmlRpcException e)
/* 266:    */     {
/* 267:248 */       throw new Connections.NetWireError(e);
/* 268:    */     }
/* 269:    */   }
/* 270:    */   
/* 271:    */   public boolean isConnected(String uid)
/* 272:    */     throws Connections.NetWireException
/* 273:    */   {
/* 274:    */     try
/* 275:    */     {
/* 276:254 */       return ((Boolean)execute("WireServerRPC.isConnected", new Object[] { uid })).booleanValue();
/* 277:    */     }
/* 278:    */     catch (XmlRpcException e)
/* 279:    */     {
/* 280:257 */       throw new Connections.NetWireException(e);
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   public Map<String, List<String>> getPortMapping(String uid)
/* 285:    */   {
/* 286:262 */     validate();
/* 287:    */     try
/* 288:    */     {
/* 289:264 */       Object[] results = (Object[])execute("WireServerRPC.getSignalProcessors", new Object[] { uid });
/* 290:265 */       return unExplode(results);
/* 291:    */     }
/* 292:    */     catch (XmlRpcException e)
/* 293:    */     {
/* 294:267 */       throw new Connections.NetWireError(e);
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   public String getRemoteStubClassName(String uid)
/* 299:    */   {
/* 300:271 */     validate();
/* 301:    */     try
/* 302:    */     {
/* 303:273 */       return (String)execute("WireServerRPC.getClassName", new Object[] { uid });
/* 304:    */     }
/* 305:    */     catch (XmlRpcException e)
/* 306:    */     {
/* 307:275 */       throw new Connections.NetWireError(e);
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   public byte[] getRemoteStub(String uid)
/* 312:    */   {
/* 313:279 */     validate();
/* 314:    */     try
/* 315:    */     {
/* 316:281 */       return (byte[])execute("WireServerRPC.getBytecode", new Object[] { uid });
/* 317:    */     }
/* 318:    */     catch (XmlRpcException e)
/* 319:    */     {
/* 320:283 */       throw new Connections.NetWireError(e);
/* 321:    */     }
/* 322:    */   }
/* 323:    */   
/* 324:    */   public synchronized String getUUID(WiredBox sourceBox)
/* 325:    */   {
/* 326:288 */     return getInstance().getUUIDHelp(sourceBox);
/* 327:    */   }
/* 328:    */   
/* 329:    */   private String getUUIDHelp(WiredBox sourceBox)
/* 330:    */   {
/* 331:293 */     if (this.uuids == null) {
/* 332:294 */       this.uuids = new ConcurrentHashMap();
/* 333:    */     }
/* 334:296 */     if (this.uuids.containsKey(sourceBox)) {
/* 335:297 */       return (String)this.uuids.get(sourceBox);
/* 336:    */     }
/* 337:    */     String uuid;
/* 338:    */     String uuid;
/* 339:299 */     if ((sourceBox instanceof WireClientEndpoint)) {
/* 340:300 */       uuid = sourceBox.getName();
/* 341:    */     } else {
/* 342:302 */       uuid = UUID.randomUUID().toString();
/* 343:    */     }
/* 344:304 */     this.uuids.put(sourceBox, uuid);
/* 345:305 */     return uuid;
/* 346:    */   }
/* 347:    */   
/* 348:312 */   private static String myID = null;
/* 349:    */   
/* 350:    */   public String getClientId()
/* 351:    */   {
/* 352:316 */     if (myID != null) {
/* 353:316 */       return myID;
/* 354:    */     }
/* 355:    */     try
/* 356:    */     {
/* 357:318 */       String myIP = (String)execute("WireServerRPC.getCallerIP", new Object[0]);
/* 358:319 */       myID = myIP + "|" + Math.random() * 1000000.0D;
/* 359:320 */       return myID;
/* 360:    */     }
/* 361:    */     catch (XmlRpcException e)
/* 362:    */     {
/* 363:322 */       throw new Connections.NetWireError(e);
/* 364:    */     }
/* 365:    */   }
/* 366:    */   
/* 367:    */   public void hook(WiredBox sourceBox)
/* 368:    */   {
/* 369:334 */     String guuid = getUUID(sourceBox);
/* 370:335 */     Map<String, Long> m = new HashMap();
/* 371:336 */     m.put(guuid, Long.valueOf(System.currentTimeMillis()));
/* 372:337 */     getSources().put(Thread.currentThread(), m);
/* 373:    */   }
/* 374:    */   
/* 375:    */   protected Map<String, Map<String, Method>> getPublished()
/* 376:    */   {
/* 377:341 */     if (this.published == null)
/* 378:    */     {
/* 379:342 */       this.published = new HashMap();
/* 380:343 */       this.publishedBoxen = new HashMap();
/* 381:    */     }
/* 382:345 */     return this.published;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public synchronized void publish(WiredBox box, String uid, String apiLanguage)
/* 386:    */     throws Connections.NetWireException
/* 387:    */   {
/* 388:349 */     if (!this.initialized) {
/* 389:349 */       throw new Connections.NetWireException("not initialized");
/* 390:    */     }
/* 391:350 */     if ((box instanceof WireClientEndpoint)) {
/* 392:351 */       throw new Connections.NetWireException("cannot publish a box that is a proxy of another box!");
/* 393:    */     }
/* 394:353 */     if (getPublished().containsKey(uid))
/* 395:    */     {
/* 396:354 */       if (this.publishedBoxen.get(uid) == box) {
/* 397:356 */         return;
/* 398:    */       }
/* 399:358 */       throw new Connections.NetWireException("The name \"" + uid + "\" was already used to publish another wired box on this client!");
/* 400:    */     }
/* 401:361 */     Method[] meths = box.getClass().getMethods();
/* 402:362 */     HashMap<String, Method> methMap = new HashMap();
/* 403:363 */     for (Method m : meths) {
/* 404:364 */       if (!m.getName().equals("equals"))
/* 405:    */       {
/* 406:365 */         Class[] params = m.getParameterTypes();
/* 407:366 */         if ((params.length == 1) && (params[0] == Object.class) && 
/* 408:367 */           (Modifier.isPublic(m.getModifiers()))) {
/* 409:369 */           methMap.put(m.getName(), m);
/* 410:    */         }
/* 411:    */       }
/* 412:    */     }
/* 413:373 */     Map<String, List<String>> signalProcessors = Connections.getPorts(box).getPortToMethodsMapping();
/* 414:374 */     getPublished().put(uid, methMap);
/* 415:375 */     this.publishedBoxen.put(uid, box);
/* 416:376 */     String oldUUID = getUUID(box);
/* 417:377 */     this.uuids.put(box, uid);
/* 418:    */     try
/* 419:    */     {
/* 420:379 */       execute("WireServerRPC.publish", new Object[] { apiLanguage, getClientId(), uid, methMap.keySet().toArray(), explode(signalProcessors) });
/* 421:    */     }
/* 422:    */     catch (XmlRpcException e)
/* 423:    */     {
/* 424:381 */       getPublished().remove(uid);
/* 425:382 */       this.publishedBoxen.remove(uid);
/* 426:383 */       this.uuids.put(box, oldUUID);
/* 427:384 */       throw new Connections.NetWireError(e);
/* 428:    */     }
/* 429:    */   }
/* 430:    */   
/* 431:    */   private Object[] explode(Map<String, List<String>> signalProcessors)
/* 432:    */   {
/* 433:390 */     Object[] res = new Object[signalProcessors.size()];
/* 434:391 */     int i = 0;
/* 435:392 */     for (String key : signalProcessors.keySet())
/* 436:    */     {
/* 437:393 */       Object[] sub = new Object[2];
/* 438:394 */       sub[0] = key;
/* 439:395 */       sub[1] = ((List)signalProcessors.get(key)).toArray();
/* 440:396 */       res[i] = sub;
/* 441:397 */       i++;
/* 442:    */     }
/* 443:399 */     return res;
/* 444:    */   }
/* 445:    */   
/* 446:    */   private Map<String, List<String>> unExplode(Object[] o)
/* 447:    */   {
/* 448:403 */     Map<String, List<String>> result = new HashMap();
/* 449:404 */     for (Object i : o)
/* 450:    */     {
/* 451:405 */       String p = (String)((Object[])i)[0];
/* 452:406 */       for (Object n : (Object[])((Object[])i)[1])
/* 453:    */       {
/* 454:407 */         String m = (String)n;
/* 455:408 */         if (result.get(p) == null) {
/* 456:409 */           result.put(p, new ArrayList());
/* 457:    */         }
/* 458:411 */         ((List)result.get(p)).add(m);
/* 459:    */       }
/* 460:    */     }
/* 461:414 */     return result;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void registerErrorHandler(ErrorHandler h)
/* 465:    */   {
/* 466:418 */     this.errorHandler = h;
/* 467:    */   }
/* 468:    */   
/* 469:421 */   private String api = "Java";
/* 470:    */   
/* 471:    */   public void setAPIType(String language)
/* 472:    */   {
/* 473:423 */     this.api = language;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void sayHello()
/* 477:    */     throws Connections.NetWireException
/* 478:    */   {
/* 479:427 */     sayHello(System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"), this.api);
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void sayHello(String osName, String osVersion, String architecture, String lang)
/* 483:    */     throws Connections.NetWireException
/* 484:    */   {
/* 485:431 */     String clientUID = getClientId();
/* 486:    */     try
/* 487:    */     {
/* 488:433 */       execute("WireServerRPC.sayHello", new Object[] { Integer.valueOf(6), clientUID, osName, osVersion, architecture, lang });
/* 489:    */     }
/* 490:    */     catch (XmlRpcException e)
/* 491:    */     {
/* 492:435 */       throw new Connections.NetWireException(e);
/* 493:    */     }
/* 494:    */   }
/* 495:    */   
/* 496:    */   public void startPollingThread()
/* 497:    */   {
/* 498:440 */     Thread t = new Thread()
/* 499:    */     {
/* 500:    */       public void run()
/* 501:    */       {
/* 502:442 */         new WireClientEndpoint.PollingEndpoint().pollForIncomingSignals();
/* 503:    */       }
/* 504:444 */     };
/* 505:445 */     t.setDaemon(true);
/* 506:446 */     t.start();
/* 507:447 */     t = new Thread()
/* 508:    */     {
/* 509:    */       public void run()
/* 510:    */       {
/* 511:    */         for (;;)
/* 512:    */         {
/* 513:450 */           for (Thread t : new ArrayList(WireClientEndpoint.this.getSources().keySet()))
/* 514:    */           {
/* 515:451 */             Map<String, Long> m = (Map)WireClientEndpoint.this.getSources().get(t);
/* 516:452 */             if (System.currentTimeMillis() - ((Long)m.values().iterator().next()).longValue() > 5000L) {
/* 517:453 */               WireClientEndpoint.this.getSources().remove(t);
/* 518:    */             }
/* 519:    */           }
/* 520:    */           try
/* 521:    */           {
/* 522:457 */             Thread.sleep(5000L);
/* 523:    */           }
/* 524:    */           catch (InterruptedException e)
/* 525:    */           {
/* 526:459 */             e.printStackTrace();
/* 527:    */           }
/* 528:    */         }
/* 529:    */       }
/* 530:463 */     };
/* 531:464 */     t.setDaemon(true);
/* 532:465 */     t.start();
/* 533:    */   }
/* 534:    */   
/* 535:468 */   private Map<String, List<String>> boxToPortList = new HashMap();
/* 536:    */   
/* 537:    */   protected void pollForIncomingSignals()
/* 538:    */   {
/* 539:471 */     Object[] params = { getClientId() };
/* 540:    */     for (;;)
/* 541:    */     {
/* 542:    */       try
/* 543:    */       {
/* 544:474 */         Object[] result = (Object[])execute("WireServerRPC.poll", params, Integer.valueOf(5000));
/* 545:475 */         Object[] calls = (Object[])result[0];
/* 546:    */         Object[] arrayOfObject3;
/* 547:476 */         Object[] arrayOfObject2 = (arrayOfObject3 = calls).length;Object[] arrayOfObject1 = 0; continue;Object o = arrayOfObject3[arrayOfObject1];
/* 548:477 */         Object[] arr = (Object[])o;
/* 549:478 */         String boxUID = (String)arr[0];
/* 550:479 */         String methodName = (String)arr[1];
/* 551:480 */         Object orig = arr[2];
/* 552:    */         try
/* 553:    */         {
/* 554:482 */           byte[] data = Base64.decodeBase64(((String)orig).getBytes());
/* 555:483 */           ObjectInputStream ois = new ObjectInputStream(
/* 556:484 */             new ByteArrayInputStream(data));
/* 557:485 */           orig = ois.readObject();
/* 558:486 */           ois.close();
/* 559:    */         }
/* 560:    */         catch (IOException e)
/* 561:    */         {
/* 562:488 */           e.printStackTrace();
/* 563:489 */           continue;
/* 564:    */         }
/* 565:    */         catch (ClassNotFoundException e)
/* 566:    */         {
/* 567:491 */           e.printStackTrace();
/* 568:492 */           continue;
/* 569:    */         }
/* 570:494 */         final Object signal = orig;
/* 571:    */         
/* 572:496 */         final Method meth = (Method)((Map)getInstance().getPublished().get(boxUID)).get(methodName);
/* 573:497 */         final Object box = getInstance().publishedBoxen.get(boxUID);
/* 574:    */         
/* 575:499 */         Thread t = new Thread()
/* 576:    */         {
/* 577:    */           public void run()
/* 578:    */           {
/* 579:    */             try
/* 580:    */             {
/* 581:502 */               meth.invoke(box, new Object[] { signal });
/* 582:    */             }
/* 583:    */             catch (Throwable t)
/* 584:    */             {
/* 585:504 */               if (WireClientEndpoint.getInstance().errorHandler != null) {
/* 586:505 */                 WireClientEndpoint.getInstance().errorHandler.onError(t);
/* 587:    */               } else {
/* 588:507 */                 t.printStackTrace();
/* 589:    */               }
/* 590:    */               try
/* 591:    */               {
/* 592:510 */                 WireClientEndpoint.this.execute("WireServerRPC.errorBack", new Object[] { t.toString() });
/* 593:    */               }
/* 594:    */               catch (XmlRpcException e)
/* 595:    */               {
/* 596:513 */                 if (WireClientEndpoint.getInstance().errorHandler != null) {
/* 597:514 */                   WireClientEndpoint.getInstance().errorHandler.onError(e);
/* 598:    */                 } else {
/* 599:516 */                   e.printStackTrace();
/* 600:    */                 }
/* 601:    */               }
/* 602:    */             }
/* 603:    */           }
/* 604:521 */         };
/* 605:522 */         t.start();arrayOfObject1++;
/* 606:476 */         if (arrayOfObject1 >= arrayOfObject2)
/* 607:    */         {
/* 608:524 */           Object[] connectRequests = (Object[])result[1];
/* 609:525 */           Object[] arrayOfObject4 = (arr = connectRequests).length;arrayOfObject2 = 0; continue;Object o = arr[arrayOfObject2];
/* 610:    */           
/* 611:527 */           String boxUID = (String)((Object[])o)[0];
/* 612:528 */           List<String> ports = new ArrayList();
/* 613:529 */           meth = (box = (Object[])((Object[])o)[1]).length;signal = 0; continue;Object hate = box[signal];
/* 614:530 */           ports.add((String)hate);signal++;
/* 615:529 */           if (signal >= meth)
/* 616:    */           {
/* 617:532 */             signal = ports.iterator(); continue;String port = (String)signal.next();
/* 618:533 */             if (this.boxToPortList.get(boxUID) == null) {
/* 619:534 */               this.boxToPortList.put(boxUID, new ArrayList());
/* 620:    */             }
/* 621:536 */             if (!((List)this.boxToPortList.get(boxUID)).contains(port))
/* 622:    */             {
/* 623:537 */               ((List)this.boxToPortList.get(boxUID)).add(port);
/* 624:538 */               Broadcaster b = new Broadcaster(port, boxUID);
/* 625:    */               
/* 626:540 */               Connections.wire(port, (WiredBox)getInstance().publishedBoxen.get(boxUID), b);
/* 627:    */             }
/* 628:532 */             if (!signal.hasNext())
/* 629:    */             {
/* 630:525 */               arrayOfObject2++;
/* 631:525 */               if (arrayOfObject2 >= arrayOfObject4)
/* 632:    */               {
/* 633:545 */                 Object[] calloutRequests = (Object[])result[2];
/* 634:546 */                 arr = (boxUID = calloutRequests).length;arrayOfObject4 = 0; continue;Object o = boxUID[arrayOfObject4];
/* 635:547 */                 String boxUID = (String)((Object[])o)[0];
/* 636:548 */                 List<WiredBox> boxen = (List)getInstance().subscribedBoxen.get(boxUID);
/* 637:549 */                 final String port = (String)((Object[])o)[1];
/* 638:    */                 
/* 639:551 */                 Object orig = ((Object[])o)[2];
/* 640:    */                 try
/* 641:    */                 {
/* 642:553 */                   byte[] data = Base64.decodeBase64(((String)orig).getBytes());
/* 643:554 */                   ObjectInputStream ois = new ObjectInputStream(
/* 644:555 */                     new ByteArrayInputStream(data));
/* 645:556 */                   orig = ois.readObject();
/* 646:557 */                   ois.close();
/* 647:    */                 }
/* 648:    */                 catch (IOException e)
/* 649:    */                 {
/* 650:559 */                   e.printStackTrace();
/* 651:560 */                   continue;
/* 652:    */                 }
/* 653:    */                 catch (ClassNotFoundException e)
/* 654:    */                 {
/* 655:562 */                   e.printStackTrace();
/* 656:563 */                   continue;
/* 657:    */                 }
/* 658:566 */                 final Object signal = orig;
/* 659:567 */                 Iterator localIterator = boxen.iterator(); continue;final WiredBox box = (WiredBox)localIterator.next();
/* 660:568 */                 Thread t = new Thread()
/* 661:    */                 {
/* 662:    */                   public void run()
/* 663:    */                   {
/* 664:570 */                     Connections.getPorts(box).transmit(port, signal);
/* 665:    */                   }
/* 666:572 */                 };
/* 667:573 */                 t.setDaemon(false);
/* 668:574 */                 t.start();
/* 669:567 */                 if (!localIterator.hasNext())
/* 670:    */                 {
/* 671:546 */                   arrayOfObject4++;
/* 672:546 */                   if (arrayOfObject4 >= arr)
/* 673:    */                   {
/* 674:577 */                     Object[] retardedRPCRequests = (Object[])result[3];
/* 675:578 */                     boxUID = (boxUID = retardedRPCRequests).length;arr = 0; continue;Object hateJava = boxUID[arr];
/* 676:579 */                     Object[] req = (Object[])hateJava;
/* 677:580 */                     long sequenceNumber = ((Long)req[0]).longValue();
/* 678:581 */                     String methodName = ((String)req[1]).intern();
/* 679:582 */                     String boxID = (String)req[2];
/* 680:583 */                     Object orig = req[3];
/* 681:    */                     try
/* 682:    */                     {
/* 683:585 */                       byte[] data = Base64.decodeBase64(((String)orig).getBytes());
/* 684:586 */                       ObjectInputStream ois = new ObjectInputStream(
/* 685:587 */                         new ByteArrayInputStream(data));
/* 686:588 */                       orig = ois.readObject();
/* 687:589 */                       ois.close();
/* 688:    */                     }
/* 689:    */                     catch (IOException e)
/* 690:    */                     {
/* 691:591 */                       replyError(sequenceNumber, e);
/* 692:592 */                       continue;
/* 693:    */                     }
/* 694:    */                     catch (ClassNotFoundException e)
/* 695:    */                     {
/* 696:594 */                       replyError(sequenceNumber, e);
/* 697:595 */                       continue;
/* 698:    */                     }
/* 699:597 */                     Object[] args = (Object[])orig;
/* 700:598 */                     Object box = getInstance().publishedBoxen.get(boxID);
/* 701:    */                     try
/* 702:    */                     {
/* 703:606 */                       Method[] allMethods = box.getClass().getMethods();
/* 704:607 */                       Method m = null;
/* 705:    */                       Method[] arrayOfMethod1;
/* 706:608 */                       int j = (arrayOfMethod1 = allMethods).length;int i = 0; continue;Method aMethod = arrayOfMethod1[i];
/* 707:609 */                       if ((aMethod.getName() == methodName) && 
/* 708:610 */                         (aMethod.getParameterTypes().length == args.length) && (
/* 709:611 */                         (aMethod.getParameterTypes().length != 1) || (aMethod.getParameterTypes()[0] != [Ljava.lang.Object.class))) {
/* 710:612 */                         if (m == null) {
/* 711:613 */                           m = aMethod;
/* 712:    */                         } else {
/* 713:615 */                           throw new Connections.NetWireError("Adam's RPC lets you do method overload based on the NUMBER OF PARAMETERS ONLY but there were at least two methods " + 
/* 714:616 */                             methodName + " with " + args.length + " parameters!!!");
/* 715:    */                         }
/* 716:    */                       }
/* 717:608 */                       i++;
/* 718:608 */                       if (i < j) {
/* 719:    */                         continue;
/* 720:    */                       }
/* 721:621 */                       if (m == null)
/* 722:    */                       {
/* 723:622 */                         m = box.getClass().getDeclaredMethod(methodName, new Class[] { [Ljava.lang.Object.class });
/* 724:623 */                         if (m != null) {
/* 725:625 */                           replyRPCResult(sequenceNumber, m.invoke(box, new Object[] { { args } }));
/* 726:    */                         } else {
/* 727:627 */                           throw new Connections.NetWireError("There is no method named " + methodName + "!");
/* 728:    */                         }
/* 729:    */                       }
/* 730:    */                       else
/* 731:    */                       {
/* 732:630 */                         replyRPCResult(sequenceNumber, m.invoke(box, args));
/* 733:    */                       }
/* 734:    */                     }
/* 735:    */                     catch (Connections.NetWireError e)
/* 736:    */                     {
/* 737:633 */                       e.printStackTrace();
/* 738:634 */                       replyError(sequenceNumber, e);
/* 739:    */                     }
/* 740:    */                     catch (Throwable e)
/* 741:    */                     {
/* 742:637 */                       e.printStackTrace();
/* 743:638 */                       replyError(sequenceNumber, e);
/* 744:    */                     }
/* 745:578 */                     arr++;
/* 746:578 */                     if (arr >= boxUID)
/* 747:    */                     {
/* 748:    */                       continue;
/* 749:645 */                       if (!(e instanceof TimingOutCallback.TimeoutException)) {
/* 750:    */                         break;
/* 751:    */                       }
/* 752:    */                     }
/* 753:    */                   }
/* 754:    */                 }
/* 755:    */               }
/* 756:    */             }
/* 757:    */           }
/* 758:    */         }
/* 759:    */       }
/* 760:    */       catch (XmlRpcException e) {}
/* 761:    */     }
/* 762:648 */     throw new Connections.NetWireError(e);
/* 763:    */   }
/* 764:    */   
/* 765:    */   public void subscribe(String globalUniqueID, WiredBox stub, String apiLanguage)
/* 766:    */     throws Connections.NetWireException
/* 767:    */   {
/* 768:656 */     if (!this.initialized) {
/* 769:656 */       throw new Connections.NetWireException("not initialized");
/* 770:    */     }
/* 771:    */     try
/* 772:    */     {
/* 773:658 */       execute("WireServerRPC.subscribe", new Object[] { getClientId(), globalUniqueID, apiLanguage });
/* 774:659 */       List<WiredBox> boxen = (List)this.subscribedBoxen.get(globalUniqueID);
/* 775:660 */       if (boxen == null)
/* 776:    */       {
/* 777:661 */         boxen = new ArrayList();
/* 778:662 */         this.subscribedBoxen.put(globalUniqueID, boxen);
/* 779:    */       }
/* 780:664 */       boxen.add(stub);
/* 781:    */     }
/* 782:    */     catch (XmlRpcException e)
/* 783:    */     {
/* 784:666 */       throw new Connections.NetWireException(e);
/* 785:    */     }
/* 786:    */   }
/* 787:    */   
/* 788:    */   public static class PhoneHomeEndpoint
/* 789:    */     extends WireClientEndpoint
/* 790:    */   {
/* 791:    */     public PhoneHomeEndpoint()
/* 792:    */     {
/* 793:682 */       setServerURL(WireClientEndpoint.getInstance().getServerURL());
/* 794:    */     }
/* 795:    */   }
/* 796:    */   
/* 797:    */   protected static class PollingEndpoint
/* 798:    */     extends WireClientEndpoint
/* 799:    */     implements Observer
/* 800:    */   {
/* 801:687 */     private Map<String, List<String>> boxToPorts = new HashMap();
/* 802:    */     
/* 803:    */     public PollingEndpoint()
/* 804:    */     {
/* 805:689 */       setServerURL(WireClientEndpoint.getInstance().getServerURL());
/* 806:690 */       initialize();
/* 807:    */     }
/* 808:    */     
/* 809:    */     protected void initialize()
/* 810:    */     {
/* 811:693 */       Connections.getInstance().addObserver(this);
/* 812:694 */       Connections.getInstance().changed();
/* 813:    */     }
/* 814:    */     
/* 815:    */     public void update(Observable ingored0, Object ignored1)
/* 816:    */     {
/* 817:698 */       ConnectionsProxy n = new ConnectionsProxy();
/* 818:699 */       Set<WiredBox> connectionsOut = n.getProxyNodesWithConnectionsOut();
/* 819:700 */       boolean changed = false;
/* 820:    */       Iterator localIterator2;
/* 821:701 */       for (Iterator localIterator1 = connectionsOut.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 822:    */       {
/* 823:701 */         WiredBox box = (WiredBox)localIterator1.next();
/* 824:702 */         String id = WireClientEndpoint.getInstance().getUUID(box);
/* 825:703 */         localIterator2 = n.getConnectedOutPorts(box).iterator(); continue;String port = (String)localIterator2.next();
/* 826:704 */         if (this.boxToPorts.get(id) == null) {
/* 827:705 */           this.boxToPorts.put(id, new ArrayList());
/* 828:    */         }
/* 829:707 */         if (!((List)this.boxToPorts.get(id)).contains(port))
/* 830:    */         {
/* 831:708 */           changed = true;
/* 832:709 */           ((List)this.boxToPorts.get(id)).add(port);
/* 833:    */         }
/* 834:    */       }
/* 835:713 */       if (changed) {
/* 836:    */         try
/* 837:    */         {
/* 838:715 */           execute("WireServerRPC.connectOut", new Object[] { explode(), getClientId() });
/* 839:    */         }
/* 840:    */         catch (XmlRpcException e)
/* 841:    */         {
/* 842:717 */           throw new Connections.NetWireError(e);
/* 843:    */         }
/* 844:    */       }
/* 845:    */     }
/* 846:    */     
/* 847:    */     private Object[] explode()
/* 848:    */     {
/* 849:722 */       Object[] ret = this.boxToPorts.keySet().toArray();
/* 850:723 */       for (int i = 0; i < ret.length; i++)
/* 851:    */       {
/* 852:724 */         Object o = ret[i];
/* 853:725 */         String s = (String)o;
/* 854:726 */         Object[] fux = new Object[2];
/* 855:727 */         fux[0] = s;
/* 856:728 */         fux[1] = new Object[((List)this.boxToPorts.get(s)).size()];
/* 857:729 */         for (int j = 0; j < ((List)this.boxToPorts.get(s)).size(); j++) {
/* 858:730 */           ((Object[])fux[1])[j] = ((List)this.boxToPorts.get(s)).get(j);
/* 859:    */         }
/* 860:732 */         ret[i] = fux;
/* 861:    */       }
/* 862:734 */       return ret;
/* 863:    */     }
/* 864:    */   }
/* 865:    */   
/* 866:    */   protected class Broadcaster
/* 867:    */     extends WireClientEndpoint
/* 868:    */     implements WiredBox
/* 869:    */   {
/* 870:    */     private String myPort;
/* 871:    */     private String myBox;
/* 872:    */     
/* 873:    */     public Broadcaster(String myPort, String myBox)
/* 874:    */     {
/* 875:741 */       this.myPort = myPort;
/* 876:742 */       this.myBox = myBox;
/* 877:743 */       setServerURL(WireClientEndpoint.getInstance().getServerURL());
/* 878:744 */       Connections.getPorts(this).addSignalProcessor("input", "broadcast");
/* 879:    */     }
/* 880:    */     
/* 881:    */     public String getName()
/* 882:    */     {
/* 883:749 */       return "broadcaster stub for port " + this.myPort;
/* 884:    */     }
/* 885:    */     
/* 886:    */     public void broadcast(Object signal)
/* 887:    */     {
/* 888:    */       try
/* 889:    */       {
/* 890:754 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 891:755 */         ObjectOutputStream oos = new ObjectOutputStream(baos);
/* 892:756 */         oos.writeObject(signal);
/* 893:757 */         oos.close();
/* 894:758 */         signal = new String(Base64.encodeBase64(baos.toByteArray()));
/* 895:759 */         execute("WireServerRPC.broadcast", new Object[] { WireClientEndpoint.getInstance().getClientId(), this.myBox, this.myPort, signal });
/* 896:    */       }
/* 897:    */       catch (XmlRpcException e)
/* 898:    */       {
/* 899:761 */         throw new Connections.NetWireError(e);
/* 900:    */       }
/* 901:    */       catch (IOException e)
/* 902:    */       {
/* 903:763 */         throw new Connections.NetWireError(e);
/* 904:    */       }
/* 905:    */     }
/* 906:    */   }
/* 907:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.WireClientEndpoint
 * JD-Core Version:    0.7.0.1
 */