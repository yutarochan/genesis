/*   1:    */ package connections;
/*   2:    */ 
/*   3:    */ import adam.ErrorHandler;
/*   4:    */ import adam.LocalCodeGenStubFactory;
/*   5:    */ import adam.WireClientEndpoint;
/*   6:    */ import adam.WiredBoxStubFactory;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.net.MalformedURLException;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.IdentityHashMap;
/*  13:    */ import java.util.Observable;
/*  14:    */ 
/*  15:    */ public class Connections
/*  16:    */   extends Observable
/*  17:    */   implements Network<WiredBox>
/*  18:    */ {
/*  19:    */   private IdentityHashMap<WiredBox, Ports> portsHashMap;
/*  20: 38 */   private static boolean verbose = false;
/*  21:    */   private static Connections instance;
/*  22:    */   private ArrayList<WiredBox> boxes;
/*  23:    */   
/*  24:    */   public static void biwire(String sourceName, WiredBox source, String destinationName, WiredBox destination)
/*  25:    */   {
/*  26: 49 */     wire(sourceName, source, destinationName, destination);
/*  27: 50 */     wire(destinationName, destination, sourceName, source);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static void wire(String sourceName, WiredBox source, String destinationName, WiredBox destination)
/*  31:    */   {
/*  32: 54 */     if (getPorts(source).getPort(sourceName).isAttached(destinationName, destination))
/*  33:    */     {
/*  34: 55 */       if (isVerbose()) {
/*  35: 56 */         System.out.println("Already connected port \"" + sourceName + "\" of " + source + " to port \"" + destinationName + "\" of " + 
/*  36: 57 */           destination);
/*  37:    */       }
/*  38: 59 */       return;
/*  39:    */     }
/*  40: 61 */     getInstance().addBox(source);
/*  41: 62 */     getInstance().addBox(destination);
/*  42: 63 */     if (isVerbose()) {
/*  43: 64 */       System.out.println("Connecting port " + sourceName + " of " + source + " to port " + destinationName + " of " + destination);
/*  44:    */     }
/*  45: 66 */     getPorts(source).getPort(sourceName).attach(source, destinationName, destination);
/*  46: 67 */     getInstance().changed();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static void disconnect(String sourceName, WiredBox source, String destinationName, WiredBox destination)
/*  50:    */   {
/*  51: 71 */     if (isVerbose()) {
/*  52: 72 */       System.out.println("Disconnecting port " + sourceName + " of " + source + " from port " + destinationName + " of " + destination);
/*  53:    */     }
/*  54: 74 */     getPorts(source).getPort(sourceName).detach(destinationName, destination);
/*  55: 75 */     getInstance().changed();
/*  56:    */   }
/*  57:    */   
/*  58:    */   public static void biwire(WiredBox source, String destinationName, WiredBox destination)
/*  59:    */   {
/*  60: 79 */     wire("output", source, destinationName, destination);
/*  61: 80 */     wire(destinationName, destination, "output", source);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public static void wire(WiredBox source, String destinationName, WiredBox destination)
/*  65:    */   {
/*  66: 84 */     wire("output", source, destinationName, destination);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static void disconnect(WiredBox source, String destinationName, WiredBox destination)
/*  70:    */   {
/*  71: 88 */     disconnect("output", source, destinationName, destination);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public static void biwire(String sourceName, WiredBox source, WiredBox destination)
/*  75:    */   {
/*  76: 92 */     wire(sourceName, source, "input", destination);
/*  77: 93 */     wire("input", destination, sourceName, source);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static void wire(String sourceName, WiredBox source, WiredBox destination)
/*  81:    */   {
/*  82: 97 */     wire(sourceName, source, "input", destination);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void disconnect(String sourceName, WiredBox source, WiredBox destination)
/*  86:    */   {
/*  87:101 */     disconnect(sourceName, source, "input", destination);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public static void biwire(WiredBox source, WiredBox destination)
/*  91:    */   {
/*  92:105 */     wire("output", source, "input", destination);
/*  93:106 */     wire("input", destination, "output", source);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public static void wire(WiredBox source, WiredBox destination)
/*  97:    */   {
/*  98:110 */     wire("output", source, "input", destination);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static void disconnect(WiredBox source, WiredBox destination)
/* 102:    */   {
/* 103:114 */     disconnect("output", source, "input", destination);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static Ports getPorts(WiredBox box)
/* 107:    */   {
/* 108:118 */     Ports ports = (Ports)getInstance().getPortsHashMap().get(box);
/* 109:119 */     if (ports != null) {
/* 110:120 */       return ports;
/* 111:    */     }
/* 112:125 */     ports = new Ports();
/* 113:126 */     getInstance().getPortsHashMap().put(box, ports);
/* 114:127 */     return ports;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static boolean isVerbose()
/* 118:    */   {
/* 119:134 */     return verbose;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static void setVerbose(boolean verbose)
/* 123:    */   {
/* 124:141 */     verbose = verbose;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public static Connections getInstance()
/* 128:    */   {
/* 129:145 */     if (instance == null) {
/* 130:146 */       instance = new Connections();
/* 131:    */     }
/* 132:148 */     return instance;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static int getPortCount()
/* 136:    */   {
/* 137:152 */     return getInstance().getPortsHashMap().size();
/* 138:    */   }
/* 139:    */   
/* 140:    */   public ArrayList<WiredBox> getBoxes()
/* 141:    */   {
/* 142:156 */     if (this.boxes == null) {
/* 143:157 */       this.boxes = new ArrayList();
/* 144:    */     }
/* 145:159 */     return this.boxes;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void addBox(WiredBox box)
/* 149:    */   {
/* 150:163 */     if (getBoxes().contains(box)) {
/* 151:164 */       return;
/* 152:    */     }
/* 153:166 */     getBoxes().add(box);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void changed()
/* 157:    */   {
/* 158:170 */     setChanged();
/* 159:171 */     notifyObservers();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public IdentityHashMap<WiredBox, Ports> getPortsHashMap()
/* 163:    */   {
/* 164:175 */     if (this.portsHashMap == null) {
/* 165:176 */       this.portsHashMap = new IdentityHashMap();
/* 166:    */     }
/* 167:178 */     return this.portsHashMap;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public ArrayList<WiredBox> getTargets(WiredBox box)
/* 171:    */   {
/* 172:182 */     Ports ports = getPorts(box);
/* 173:183 */     Collection<Port> portCollection = ports.getPorts();
/* 174:184 */     ArrayList<WiredBox> result = new ArrayList();
/* 175:185 */     for (Port port : portCollection) {
/* 176:186 */       result.addAll(port.getTargets());
/* 177:    */     }
/* 178:188 */     return result;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public static void obliterateNetwork()
/* 182:    */   {
/* 183:197 */     getInstance().portsHashMap = null;
/* 184:198 */     getInstance().boxes = null;
/* 185:199 */     getInstance().changed();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static void forwardTo(WiredBox sourceBox, WiredBox destinationBox)
/* 189:    */   {
/* 190:206 */     forwardTo("input", sourceBox, destinationBox);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static void forwardTo(String portName, WiredBox sourceBox, WiredBox destinationBox)
/* 194:    */   {
/* 195:210 */     forwardTo(portName, sourceBox, portName, destinationBox);
/* 196:    */   }
/* 197:    */   
/* 198:    */   public static void forwardTo(String sourcePortName, WiredBox sourceBox, String destinationPortName, WiredBox destinationBox)
/* 199:    */   {
/* 200:214 */     getPorts(sourceBox).getPort(sourcePortName).forwardTo(sourcePortName, sourceBox, destinationPortName, destinationBox);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public static void forwardFrom(WiredBox sourceBox, WiredBox destinationBox)
/* 204:    */   {
/* 205:218 */     forwardFrom("output", sourceBox, destinationBox);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public static void forwardFrom(String portName, WiredBox sourceBox, WiredBox destinationBox)
/* 209:    */   {
/* 210:222 */     forwardFrom(portName, sourceBox, portName, destinationBox);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static void forwardFrom(String sourcePortName, WiredBox sourceBox, String destinationPortName, WiredBox destinationBox)
/* 214:    */   {
/* 215:226 */     getPorts(sourceBox).getPort(sourcePortName).forwardFrom(sourcePortName, sourceBox, destinationPortName, destinationBox);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static void useWireServer(URL serverURL)
/* 219:    */     throws Connections.NetWireException
/* 220:    */   {
/* 221:268 */     boolean changed = WireClientEndpoint.getInstance().initialize(serverURL);
/* 222:271 */     if (changed)
/* 223:    */     {
/* 224:272 */       WiredBoxStubFactory.setFactoryClass(LocalCodeGenStubFactory.class);
/* 225:273 */       WireClientEndpoint.getInstance().sayHello();
/* 226:274 */       WireClientEndpoint.getInstance().startPollingThread();
/* 227:275 */       WireClientEndpoint.setInitialized();
/* 228:    */     }
/* 229:    */   }
/* 230:    */   
/* 231:    */   public static void useWireServer(String serverURL)
/* 232:    */     throws Connections.NetWireException
/* 233:    */   {
/* 234:    */     try
/* 235:    */     {
/* 236:283 */       useWireServer(new URL(serverURL));
/* 237:    */     }
/* 238:    */     catch (MalformedURLException e)
/* 239:    */     {
/* 240:286 */       throw new NetWireException(e);
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   public static WiredBox subscribe(String globalUniqueID)
/* 245:    */     throws Connections.NetWireException
/* 246:    */   {
/* 247:303 */     return subscribe(globalUniqueID, 0.0D);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public static WiredBox subscribe(String globalUniqueID, double timeout)
/* 251:    */     throws Connections.NetWireException
/* 252:    */   {
/* 253:322 */     return subscribe(globalUniqueID, timeout, "Java");
/* 254:    */   }
/* 255:    */   
/* 256:    */   public static WiredBox subscribe(String globalUniqueID, double timeout, String apiLanguage)
/* 257:    */     throws Connections.NetWireException
/* 258:    */   {
/* 259:326 */     if (!WireClientEndpoint.isInitialized()) {
/* 260:327 */       useWireServer("http://glue.csail.mit.edu/WireServer");
/* 261:    */     }
/* 262:329 */     double RETRY_INTERVAL = 0.25D;
/* 263:330 */     double elapsed = 0.0D;
/* 264:331 */     NetWireException error = null;
/* 265:334 */     while ((timeout < 0.0D) || (elapsed <= timeout))
/* 266:    */     {
/* 267:    */       try
/* 268:    */       {
/* 269:336 */         if (WireClientEndpoint.getInstance().isConnected(globalUniqueID))
/* 270:    */         {
/* 271:337 */           WiredBox stub = WiredBoxStubFactory.getInstance().getStub(globalUniqueID);
/* 272:338 */           WireClientEndpoint.getInstance().subscribe(globalUniqueID, stub, apiLanguage);
/* 273:339 */           return stub;
/* 274:    */         }
/* 275:342 */         error = new NetWireException(globalUniqueID + " is not published, or timeout expired");
/* 276:    */       }
/* 277:    */       catch (NetWireException e)
/* 278:    */       {
/* 279:346 */         if (timeout == 0.0D)
/* 280:    */         {
/* 281:347 */           error = e;
/* 282:348 */           break;
/* 283:    */         }
/* 284:    */       }
/* 285:    */       catch (NetWireError e)
/* 286:    */       {
/* 287:352 */         if (timeout == 0.0D)
/* 288:    */         {
/* 289:353 */           error = new NetWireException(e);
/* 290:354 */           break;
/* 291:    */         }
/* 292:    */       }
/* 293:357 */       double t = System.currentTimeMillis() * 1000L;
/* 294:    */       try
/* 295:    */       {
/* 296:359 */         Thread.sleep((RETRY_INTERVAL * 1000.0D));
/* 297:360 */         elapsed += System.currentTimeMillis() * 1000L - t;
/* 298:    */       }
/* 299:    */       catch (InterruptedException localInterruptedException) {}
/* 300:    */     }
/* 301:366 */     if (error != null) {
/* 302:367 */       throw error;
/* 303:    */     }
/* 304:369 */     throw new NetWireException("timeout waiting for \"" + globalUniqueID + "\" to be published");
/* 305:    */   }
/* 306:    */   
/* 307:    */   public static void publish(WiredBox box, String globalUniqueID)
/* 308:    */     throws Connections.NetWireException
/* 309:    */   {
/* 310:385 */     publish(box, globalUniqueID, "Java");
/* 311:    */   }
/* 312:    */   
/* 313:    */   public static void publish(WiredBox box, String globalUniqueID, String apiLanguage)
/* 314:    */     throws Connections.NetWireException
/* 315:    */   {
/* 316:389 */     if (globalUniqueID.contains("|")) {
/* 317:390 */       throw new NetWireException("'|' cannot be in the ID because it is reserved");
/* 318:    */     }
/* 319:392 */     if (!WireClientEndpoint.isInitialized()) {
/* 320:393 */       useWireServer("http://glue.csail.mit.edu/WireServer");
/* 321:    */     }
/* 322:395 */     WireClientEndpoint.getInstance().publish(box, globalUniqueID, apiLanguage);
/* 323:    */   }
/* 324:    */   
/* 325:    */   public static void setLocalErrorHandler(ErrorHandler e)
/* 326:    */   {
/* 327:406 */     WireClientEndpoint.getInstance().registerErrorHandler(e);
/* 328:    */   }
/* 329:    */   
/* 330:    */   public static class NetWireException
/* 331:    */     extends Exception
/* 332:    */   {
/* 333:    */     public NetWireException(String e)
/* 334:    */     {
/* 335:413 */       super();
/* 336:    */     }
/* 337:    */     
/* 338:    */     public NetWireException(Throwable cause)
/* 339:    */     {
/* 340:417 */       super();
/* 341:    */     }
/* 342:    */   }
/* 343:    */   
/* 344:    */   public static class NetWireError
/* 345:    */     extends Error
/* 346:    */   {
/* 347:    */     public NetWireError(String e)
/* 348:    */     {
/* 349:426 */       super();
/* 350:    */     }
/* 351:    */     
/* 352:    */     public NetWireError(Throwable cause)
/* 353:    */     {
/* 354:430 */       super();
/* 355:    */     }
/* 356:    */   }
/* 357:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.Connections
 * JD-Core Version:    0.7.0.1
 */