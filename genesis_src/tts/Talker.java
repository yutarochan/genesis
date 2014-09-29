/*   1:    */ package tts;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import genesis.GenesisGetters;
/*  10:    */ import java.awt.BorderLayout;
/*  11:    */ import java.awt.Color;
/*  12:    */ import java.awt.Font;
/*  13:    */ import java.io.File;
/*  14:    */ import java.io.FileOutputStream;
/*  15:    */ import java.io.IOException;
/*  16:    */ import java.io.InputStream;
/*  17:    */ import java.io.OutputStreamWriter;
/*  18:    */ import java.io.PrintStream;
/*  19:    */ import java.net.MalformedURLException;
/*  20:    */ import java.net.URL;
/*  21:    */ import java.net.URLConnection;
/*  22:    */ import java.net.URLEncoder;
/*  23:    */ import java.util.Iterator;
/*  24:    */ import java.util.Vector;
/*  25:    */ import javax.swing.JCheckBox;
/*  26:    */ import javax.swing.JFrame;
/*  27:    */ import javax.swing.JLabel;
/*  28:    */ import javax.swing.JPanel;
/*  29:    */ import start.Generator;
/*  30:    */ import text.Html;
/*  31:    */ import translator.Translator;
/*  32:    */ import utils.Mark;
/*  33:    */ import utils.TextIO;
/*  34:    */ import utils.WindowsConnection;
/*  35:    */ import windowGroup.WindowGroupManager;
/*  36:    */ 
/*  37:    */ public class Talker
/*  38:    */   extends JPanel
/*  39:    */   implements WiredBox
/*  40:    */ {
/*  41: 32 */   private boolean processWithDragon = false;
/*  42:    */   public static final String PREDICTION = "prediction";
/*  43:    */   public static final String CLEAR = "clear";
/*  44:    */   public static final String SPEAK = "speak";
/*  45:    */   private JLabel label;
/*  46: 42 */   private static int fileNumber = 0;
/*  47:    */   private static Talker talker;
/*  48:    */   private long previousInput;
/*  49:    */   GenesisGetters tabs;
/*  50:    */   private JCheckBox checkBox;
/*  51:    */   
/*  52:    */   public static Talker getTalker()
/*  53:    */   {
/*  54: 51 */     if (talker == null) {
/*  55: 52 */       talker = new Talker();
/*  56:    */     }
/*  57: 54 */     return talker;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Talker() {}
/*  61:    */   
/*  62:    */   public Talker(JCheckBox checkBox)
/*  63:    */   {
/*  64: 62 */     setLayout(new BorderLayout());
/*  65: 63 */     this.checkBox = checkBox;
/*  66: 64 */     add(getLabel(), "Center");
/*  67: 65 */     Connections.getPorts(this).addSignalProcessor("prediction", "predict");
/*  68: 66 */     Connections.getPorts(this).addSignalProcessor("clear", "clear");
/*  69: 67 */     Connections.getPorts(this).addSignalProcessor("speak", "speakOnly");
/*  70: 68 */     Connections.getPorts(this).addSignalProcessor("speak");
/*  71:    */   }
/*  72:    */   
/*  73:    */   public GenesisGetters getTabs()
/*  74:    */   {
/*  75: 78 */     return this.tabs;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setTabs(GenesisGetters tabs)
/*  79:    */   {
/*  80: 82 */     this.tabs = tabs;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public JLabel getLabel()
/*  84:    */   {
/*  85: 86 */     if (this.label == null)
/*  86:    */     {
/*  87: 87 */       this.label = new JLabel("", 2);
/*  88:    */       
/*  89: 89 */       this.label.setBackground(Color.PINK);
/*  90: 90 */       this.label.setOpaque(true);
/*  91: 91 */       Font font = this.label.getFont();
/*  92: 92 */       this.label.setFont(new Font(font.getFamily(), font.getStyle(), 25));
/*  93:    */     }
/*  94: 94 */     return this.label;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void useless(Vector<String> v)
/*  98:    */   {
/*  99:    */     String str;
/* 100: 98 */     for (Iterator localIterator = v.iterator(); localIterator.hasNext(); str = (String)localIterator.next()) {}
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void clear(Object object)
/* 104:    */   {
/* 105:115 */     Mark.say(
/* 106:    */     
/* 107:117 */       new Object[] { "Receiving", object, "in clear" });Connections.getPorts(this).transmit("clear", object);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void predict(Object text)
/* 111:    */   {
/* 112:120 */     Mark.say(
/* 113:    */     
/* 114:122 */       new Object[] { "Receiving", text, "in predict" });speakAux(text, true);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public synchronized void speak(Object object)
/* 118:    */   {
/* 119:125 */     speakAux(object, true);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public synchronized void speakOnly(Object object)
/* 123:    */   {
/* 124:130 */     Mark.say(
/* 125:    */     
/* 126:132 */       new Object[] { "Receiving", object, "in speakOnly" });speakAux(object, false);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public synchronized void speakAux(Object object, boolean write)
/* 130:    */   {
/* 131:136 */     boolean debug = false;
/* 132:    */     
/* 133:138 */     long currentInput = System.currentTimeMillis();
/* 134:    */     
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:144 */     this.previousInput = currentInput;
/* 140:    */     
/* 141:146 */     double offset = 0.0D;
/* 142:147 */     if (this.processWithDragon)
/* 143:    */     {
/* 144:148 */       newSpeak(object);
/* 145:149 */       return;
/* 146:    */     }
/* 147:151 */     if (getTabs() != null) {
/* 148:152 */       getTabs().getWindowGroupManager().setGuts(getTabs().getLeftPanel(), getTabs().getLightBulbViewer());
/* 149:    */     }
/* 150:155 */     String text = "";
/* 151:157 */     if ((object instanceof BetterSignal))
/* 152:    */     {
/* 153:158 */       BetterSignal signal = (BetterSignal)object;
/* 154:    */       
/* 155:160 */       Object firstArgument = signal.get(0, Object.class);
/* 156:161 */       Object secondArgument = signal.get(1, Object.class);
/* 157:162 */       if ((secondArgument instanceof String)) {
/* 158:163 */         text = (String)signal.get(1, String.class);
/* 159:165 */       } else if ((secondArgument instanceof Entity)) {
/* 160:    */         try
/* 161:    */         {
/* 162:169 */           if ((firstArgument instanceof Double)) {
/* 163:170 */             offset = ((Double)firstArgument).doubleValue();
/* 164:    */           }
/* 165:172 */           text = Generator.getGenerator().generate((Entity)secondArgument);
/* 166:173 */           text = Html.strip(text);
/* 167:    */         }
/* 168:    */         catch (Exception e)
/* 169:    */         {
/* 170:176 */           Mark.say(new Object[] {"Unable to generate English from", ((Entity)secondArgument).asString() });
/* 171:    */         }
/* 172:    */       }
/* 173:    */     }
/* 174:180 */     else if ((object instanceof Entity))
/* 175:    */     {
/* 176:    */       try
/* 177:    */       {
/* 178:182 */         text = Generator.getGenerator().generate((Entity)object);
/* 179:183 */         text = Html.strip(text);
/* 180:    */       }
/* 181:    */       catch (Exception e)
/* 182:    */       {
/* 183:186 */         Mark.say(new Object[] {"Unable to generate English from", ((Entity)object).asString() });
/* 184:    */       }
/* 185:    */     }
/* 186:189 */     else if ((object instanceof String))
/* 187:    */     {
/* 188:190 */       text = Html.strip(text);
/* 189:191 */       text = compress((String)object);
/* 190:    */     }
/* 191:    */     else
/* 192:    */     {
/* 193:194 */       Mark.err(new Object[] {"Argument", object, "to Talker.speak is not a Thing or a String" });
/* 194:195 */       return;
/* 195:    */     }
/* 196:197 */     if (write)
/* 197:    */     {
/* 198:198 */       Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "Speech", "clear" }));
/* 199:199 */       Connections.getPorts(this).transmit(new BetterSignal(new Object[] { "Speech", (offset > 0.0D ? String.format("%.1f ", new Object[] { Double.valueOf(offset) }) : "") + text }));
/* 200:    */     }
/* 201:202 */     if ((offset > 0.0D) && (text.indexOf("give") <= 0)) {
/* 202:203 */       return;
/* 203:    */     }
/* 204:206 */     if ((this.checkBox == null) || (this.checkBox.isSelected()))
/* 205:    */     {
/* 206:211 */       File file = new File(System.getProperty("user.home") + "/textToSpeechFile" + ".txt");
/* 207:212 */       if (file.exists()) {
/* 208:213 */         file.delete();
/* 209:    */       }
/* 210:216 */       String strippedText = Html.strip(text);
/* 211:220 */       if (strippedText.trim().equals("clear")) {
/* 212:221 */         return;
/* 213:    */       }
/* 214:224 */       TextIO.writeStringToFile(strippedText, file);
/* 215:    */       
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:233 */       String e = "\"c:\\program files\\jampal\\ptts\" -u " + file.getPath();
/* 224:    */       
/* 225:235 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Text:", strippedText });
/* 226:236 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Command:", e });
/* 227:    */       
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:242 */       boolean won = WindowsConnection.run(e);
/* 233:246 */       if (!won)
/* 234:    */       {
/* 235:248 */         String message = "If you want to hear spoken text, install Jampal. ";
/* 236:249 */         message = message + "\nYou can find it on the web by searching for Jampal. ";
/* 237:250 */         message = message + "\nLoad it in the recommended directory.";
/* 238:251 */         message = message + "\nOtherwise, you should probably shut off speech output.";
/* 239:252 */         System.err.println(message);
/* 240:    */       }
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   private String formatForSpeech(String text)
/* 245:    */   {
/* 246:277 */     return addPeriod(text);
/* 247:    */   }
/* 248:    */   
/* 249:    */   public static String addPeriod(String s)
/* 250:    */   {
/* 251:281 */     if ((s == null) || (s.isEmpty())) {
/* 252:282 */       return s;
/* 253:    */     }
/* 254:284 */     if (".!?".indexOf(s.charAt(s.length() - 1)) < 0) {
/* 255:285 */       return s + '.';
/* 256:    */     }
/* 257:287 */     return s;
/* 258:    */   }
/* 259:    */   
/* 260:    */   private String compress(String s)
/* 261:    */   {
/* 262:291 */     StringBuffer b = new StringBuffer(s);
/* 263:    */     int i;
/* 264:293 */     while ((i = b.indexOf("  ")) > 0)
/* 265:    */     {
/* 266:    */       int i;
/* 267:294 */       b.deleteCharAt(i);
/* 268:    */     }
/* 269:296 */     return b.toString().trim();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setFrame(JFrame frame) {}
/* 273:    */   
/* 274:    */   class TestBox
/* 275:    */     extends AbstractWiredBox
/* 276:    */   {
/* 277:    */     TestBox() {}
/* 278:    */     
/* 279:    */     public void test()
/* 280:    */     {
/* 281:316 */       Connections.getPorts(this).transmit("I expect that a bird flew");
/* 282:317 */       Connections.getPorts(this).transmit("Hello world");
/* 283:    */     }
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void sleep(int i)
/* 287:    */   {
/* 288:    */     try
/* 289:    */     {
/* 290:327 */       Thread.sleep(i);
/* 291:    */     }
/* 292:    */     catch (InterruptedException e)
/* 293:    */     {
/* 294:330 */       e.printStackTrace();
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void sleep(String minis)
/* 299:    */   {
/* 300:    */     try
/* 301:    */     {
/* 302:336 */       int time = Integer.parseInt(minis);
/* 303:337 */       sleep(time);
/* 304:    */     }
/* 305:    */     catch (NumberFormatException e)
/* 306:    */     {
/* 307:340 */       e.printStackTrace();
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   public synchronized void newSpeak(Object object)
/* 312:    */   {
/* 313:345 */     Mark.say(
/* 314:    */     
/* 315:    */ 
/* 316:    */ 
/* 317:    */ 
/* 318:    */ 
/* 319:    */ 
/* 320:    */ 
/* 321:    */ 
/* 322:    */ 
/* 323:    */ 
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:    */ 
/* 328:    */ 
/* 329:    */ 
/* 330:    */ 
/* 331:    */ 
/* 332:    */ 
/* 333:    */ 
/* 334:    */ 
/* 335:    */ 
/* 336:    */ 
/* 337:    */ 
/* 338:    */ 
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:    */ 
/* 354:    */ 
/* 355:    */ 
/* 356:    */ 
/* 357:389 */       new Object[] { "Entering newSpeak" });
/* 358:346 */     if (getTabs() != null) {
/* 359:347 */       getTabs().getWindowGroupManager().setGuts(getTabs().getLeftPanel(), getTabs().getLightBulbViewer());
/* 360:    */     }
/* 361:350 */     String text = "";
/* 362:351 */     if ((object instanceof BetterSignal))
/* 363:    */     {
/* 364:352 */       BetterSignal signal = (BetterSignal)object;
/* 365:353 */       text = (String)signal.get(1, String.class);
/* 366:    */     }
/* 367:355 */     else if ((object instanceof Entity))
/* 368:    */     {
/* 369:    */       try
/* 370:    */       {
/* 371:357 */         text = Generator.getGenerator().generate((Entity)object);
/* 372:    */       }
/* 373:    */       catch (Exception e)
/* 374:    */       {
/* 375:360 */         Mark.say(new Object[] {"Unable to generate English from", ((Entity)object).asString() });
/* 376:    */       }
/* 377:    */     }
/* 378:363 */     else if ((object instanceof String))
/* 379:    */     {
/* 380:364 */       text = (String)object;
/* 381:    */     }
/* 382:    */     else
/* 383:    */     {
/* 384:367 */       Mark.err(new Object[] {"Argument", object, "to Talker.speak is not a Thing or a String" });
/* 385:368 */       return;
/* 386:    */     }
/* 387:370 */     Connections.getPorts(this).transmit("switch tab", "Speech");
/* 388:371 */     Connections.getPorts(this).transmit(text);
/* 389:372 */     if ((this.checkBox == null) || (this.checkBox.isSelected()))
/* 390:    */     {
/* 391:373 */       text = Html.strip(text);
/* 392:374 */       text = compress(text);
/* 393:    */       try
/* 394:    */       {
/* 395:377 */         processWaveRequest(text);
/* 396:    */       }
/* 397:    */       catch (Exception e)
/* 398:    */       {
/* 399:380 */         e.printStackTrace();
/* 400:381 */         String message = "If you want to hear spoken text, install Jampal. ";
/* 401:382 */         message = message + "\nYou can find it on the web by searching for Jampal. ";
/* 402:383 */         message = message + "\nLoad it in the recommended directory.";
/* 403:384 */         message = message + "\nOtherwise, you should probably shut off voice output.";
/* 404:385 */         System.err.println(message);
/* 405:    */       }
/* 406:    */     }
/* 407:    */   }
/* 408:    */   
/* 409:    */   protected void processWaveRequest(String probe)
/* 410:    */   {
/* 411:399 */     String urlString = "http://people.csail.mit.edu/cyphers/cgi/zed.cgi";
/* 412:    */     try
/* 413:    */     {
/* 414:402 */       URL url = new URL(urlString);
/* 415:403 */       URLConnection connection = url.openConnection();
/* 416:404 */       connection.setDoOutput(true);
/* 417:    */       
/* 418:406 */       OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
/* 419:407 */       String command = "synth_string=" + URLEncoder.encode(probe, "UTF-8");
/* 420:    */       
/* 421:409 */       out.write(command);
/* 422:    */       
/* 423:411 */       out.close();
/* 424:    */       
/* 425:    */ 
/* 426:414 */       InputStream in = connection.getInputStream();
/* 427:    */       
/* 428:    */ 
/* 429:    */ 
/* 430:418 */       File file = new File(System.getProperty("user.home") + "/textToSpeechFile" + ".wav");
/* 431:    */       
/* 432:    */ 
/* 433:    */ 
/* 434:422 */       FileOutputStream outputFile = new FileOutputStream(file);
/* 435:    */       int input;
/* 436:424 */       while ((input = in.read()) != -1)
/* 437:    */       {
/* 438:    */         int input;
/* 439:425 */         outputFile.write(input);
/* 440:    */       }
/* 441:428 */       in.close();
/* 442:429 */       outputFile.flush();
/* 443:430 */       outputFile.close();
/* 444:    */     }
/* 445:    */     catch (MalformedURLException e)
/* 446:    */     {
/* 447:445 */       Mark.betterErr(new Object[] {"Evidently bad url" });
/* 448:    */     }
/* 449:    */     catch (IOException e)
/* 450:    */     {
/* 451:448 */       Mark.betterErr(new Object[] {"Evidently not connected to web or START is down" });
/* 452:449 */       e.printStackTrace();
/* 453:    */     }
/* 454:    */     catch (Exception e)
/* 455:    */     {
/* 456:452 */       Mark.betterErr(new Object[] {"Evidently unable to process '" + probe + "'" });
/* 457:    */     }
/* 458:    */   }
/* 459:    */   
/* 460:    */   public static void main(String[] args)
/* 461:    */     throws Exception
/* 462:    */   {
/* 463:457 */     Entity t = Translator.getTranslator().translate("A dog gave a ball to a cat");
/* 464:458 */     Entity t2 = (Entity)t.getElements().get(0);
/* 465:459 */     Mark.say(new Object[] {"Argument is", t2.asString() });
/* 466:460 */     String s = Generator.getGenerator().generate(t2);
/* 467:461 */     Mark.say(new Object[] {"English is", s });
/* 468:462 */     getTalker().speak(t2);
/* 469:    */   }
/* 470:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tts.Talker
 * JD-Core Version:    0.7.0.1
 */