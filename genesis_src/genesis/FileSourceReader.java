/*   1:    */ package genesis;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import connections.AbstractWiredBox;
/*   5:    */ import connections.Connections;
/*   6:    */ import connections.Ports;
/*   7:    */ import java.io.File;
/*   8:    */ import java.io.FileInputStream;
/*   9:    */ import java.io.FileNotFoundException;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.InputStream;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.net.MalformedURLException;
/*  14:    */ import java.net.URI;
/*  15:    */ import java.net.URL;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Collection;
/*  18:    */ import javax.swing.JButton;
/*  19:    */ import org.apache.commons.io.IOUtils;
/*  20:    */ import parameters.Switch;
/*  21:    */ import persistence.JCheckBoxWithMemory;
/*  22:    */ import utils.Mark;
/*  23:    */ import utils.PathFinder;
/*  24:    */ import utils.Timer;
/*  25:    */ 
/*  26:    */ public class FileSourceReader
/*  27:    */   extends AbstractWiredBox
/*  28:    */ {
/*  29:    */   private ArrayList<String> sentenceQueue;
/*  30:    */   public static final int INCREMENTAL = 0;
/*  31:    */   public static final int TOTAL = 1;
/*  32: 38 */   private int mode = 1;
/*  33:    */   public static final String STATE = "state";
/*  34:    */   public static final String PAUSE = "pause";
/*  35:    */   public static final String HAS_QUEUE = "hasQueue";
/*  36:    */   public static final String STATUS = "status port";
/*  37:    */   private static FileSourceReader reader;
/*  38:    */   private int sentenceCount;
/*  39:    */   private int processedSentenceCount;
/*  40:    */   private ArrayList<String> experiments;
/*  41:    */   private String experiment;
/*  42:    */   private ProcessSentences theReaderThread;
/*  43:    */   private URL lastStory;
/*  44:    */   
/*  45:    */   public ProcessSentences getTheReaderThread()
/*  46:    */   {
/*  47: 63 */     return this.theReaderThread;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public static FileSourceReader getFileSourceReader()
/*  51:    */   {
/*  52: 67 */     if (reader == null) {
/*  53: 68 */       reader = new FileSourceReader();
/*  54:    */     }
/*  55: 70 */     return reader;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public FileSourceReader()
/*  59:    */   {
/*  60: 77 */     setName("File reader");
/*  61: 78 */     Connections.getPorts(this).addSignalProcessor("pause", "pause");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void readStoryWithoutNewThread(InputStream storyStream, URL url)
/*  65:    */   {
/*  66: 86 */     getSentenceQueue().addAll(0, getStoryLines(storyStream, url));
/*  67: 87 */     initiateReadingWithoutNewThread();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void readStory(InputStream storyStream, URL url)
/*  71:    */   {
/*  72: 91 */     setSentenceQueue(getStoryLines(storyStream, url));
/*  73: 92 */     initiateReading();
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void readStoryWithoutNewThread(String storyReference)
/*  77:    */   {
/*  78:    */     try
/*  79:    */     {
/*  80: 97 */       URL storyURL = PathFinder.storyURL(storyReference);
/*  81: 98 */       this.lastStory = storyURL;
/*  82: 99 */       InputStream storyStream = storyURL.openStream();
/*  83:100 */       readStoryWithoutNewThread(storyStream, storyURL);
/*  84:    */     }
/*  85:    */     catch (IOException e)
/*  86:    */     {
/*  87:103 */       Mark.err(new Object[] {"Could not find story : " + storyReference });
/*  88:104 */       e.printStackTrace();
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void readStory(String storyReference)
/*  93:    */   {
/*  94:    */     try
/*  95:    */     {
/*  96:110 */       URL storyURL = PathFinder.storyURL(storyReference);
/*  97:111 */       this.lastStory = storyURL;
/*  98:112 */       InputStream storyStream = storyURL.openStream();
/*  99:113 */       readStory(storyStream, storyURL);
/* 100:    */     }
/* 101:    */     catch (IOException e)
/* 102:    */     {
/* 103:116 */       Mark.err(new Object[] {"Could not find story : " + storyReference });
/* 104:117 */       e.printStackTrace();
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void readStory(File story)
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:123 */       this.lastStory = story.toURI().toURL();
/* 113:124 */       readStory(new FileInputStream(story), this.lastStory);
/* 114:    */     }
/* 115:    */     catch (FileNotFoundException|MalformedURLException e)
/* 116:    */     {
/* 117:127 */       Mark.err(new Object[] {"Could not find : " + story });
/* 118:128 */       e.printStackTrace();
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   private ArrayList<String> getStoryLines(InputStream storyStream, URL url)
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:134 */       String storyString = IOUtils.toString(storyStream);
/* 127:    */       
/* 128:    */ 
/* 129:137 */       storyStream.close();
/* 130:    */       
/* 131:    */ 
/* 132:140 */       storyString = removeComments(storyString);
/* 133:    */       
/* 134:142 */       ArrayList<String> sentences = splitText(storyString);
/* 135:144 */       if (getSentenceQueue().isEmpty()) {
/* 136:145 */         Connections.getPorts(this).transmit("hasQueue", Boolean.FALSE);
/* 137:    */       } else {
/* 138:148 */         Connections.getPorts(this).transmit("hasQueue", Boolean.TRUE);
/* 139:    */       }
/* 140:150 */       return sentences;
/* 141:    */     }
/* 142:    */     catch (Exception e)
/* 143:    */     {
/* 144:153 */       System.err.println("Unable to read sentences from Story.");
/* 145:154 */       e.printStackTrace();
/* 146:    */     }
/* 147:155 */     return null;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public ArrayList<String> getSentenceQueue()
/* 151:    */   {
/* 152:164 */     if (this.sentenceQueue == null) {
/* 153:165 */       this.sentenceQueue = new ArrayList();
/* 154:    */     }
/* 155:167 */     return this.sentenceQueue;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setSentenceQueue(ArrayList<String> sentenceQueue)
/* 159:    */   {
/* 160:171 */     this.sentenceQueue = sentenceQueue;
/* 161:172 */     if (sentenceQueue != null) {
/* 162:173 */       this.sentenceCount = sentenceQueue.size();
/* 163:    */     }
/* 164:175 */     this.processedSentenceCount = 0;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void rerun()
/* 168:    */   {
/* 169:179 */     if (this.lastStory == null) {
/* 170:180 */       Mark.err(new Object[] {"No previous story has been read." });
/* 171:    */     } else {
/* 172:    */       try
/* 173:    */       {
/* 174:184 */         readStory(this.lastStory.openStream(), this.lastStory);
/* 175:    */       }
/* 176:    */       catch (IOException e)
/* 177:    */       {
/* 178:187 */         Mark.err(new Object[] {"Could not reread " + this.lastStory });
/* 179:188 */         e.printStackTrace();
/* 180:    */       }
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean hasQueue()
/* 185:    */   {
/* 186:194 */     return !getSentenceQueue().isEmpty();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void pause(Object object)
/* 190:    */   {
/* 191:198 */     if (object == "Pause") {
/* 192:199 */       this.mode = 0;
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void stop(Object object)
/* 197:    */   {
/* 198:204 */     if (object == "Stop") {
/* 199:205 */       this.mode = 0;
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void readNextSentence()
/* 204:    */   {
/* 205:210 */     this.mode = 0;
/* 206:    */     
/* 207:    */ 
/* 208:213 */     new ProcessSentences().start();
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void readRemainingSentences()
/* 212:    */   {
/* 213:218 */     this.mode = 1;
/* 214:219 */     GenesisControls.getNextButton().setEnabled(false);
/* 215:220 */     GenesisControls.getRunButton().setEnabled(false);
/* 216:    */     
/* 217:222 */     new ProcessSentences().start();
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void initiateReadingWithoutNewThread()
/* 221:    */   {
/* 222:227 */     setExperiment(null);
/* 223:228 */     GenesisGetters.getNextButton().setEnabled(false);
/* 224:229 */     GenesisGetters.getRunButton().setEnabled(false);
/* 225:230 */     if (getSentenceQueue().isEmpty()) {
/* 226:231 */       return;
/* 227:    */     }
/* 228:233 */     setExperiments(null);
/* 229:234 */     processSentences();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void initiateReading()
/* 233:    */   {
/* 234:246 */     setExperiment(null);
/* 235:247 */     GenesisGetters.getNextButton().setEnabled(false);
/* 236:248 */     GenesisGetters.getRunButton().setEnabled(false);
/* 237:249 */     if (getSentenceQueue().isEmpty()) {
/* 238:250 */       return;
/* 239:    */     }
/* 240:252 */     setExperiments(null);
/* 241:253 */     String sentence = (String)getSentenceQueue().get(0);
/* 242:254 */     if (sentence.startsWith("Run what-if experiments "))
/* 243:    */     {
/* 244:255 */       getSentenceQueue().remove(0);
/* 245:256 */       new ProcessExperiment(sentence).start();
/* 246:    */     }
/* 247:    */     else
/* 248:    */     {
/* 249:259 */       readRemainingSentences();
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   public void setMode(int mode)
/* 254:    */   {
/* 255:264 */     this.mode = mode;
/* 256:    */   }
/* 257:    */   
/* 258:    */   class ProcessExperiment
/* 259:    */     extends Thread
/* 260:    */   {
/* 261:    */     String firstSentence;
/* 262:    */     
/* 263:    */     public ProcessExperiment(String starter)
/* 264:    */     {
/* 265:271 */       this.firstSentence = starter;
/* 266:    */     }
/* 267:    */     
/* 268:    */     public void run()
/* 269:    */     {
/* 270:275 */       boolean debug = false;
/* 271:    */       
/* 272:277 */       FileSourceReader.this.getExperiments().addAll(FileSourceReader.this.extractExperiments(this.firstSentence.substring("Run what-if experiments ".length())));
/* 273:278 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Running experiments!!!!!", FileSourceReader.this.getExperiments() });
/* 274:279 */       ArrayList<String> handle = new ArrayList();
/* 275:280 */       handle.addAll(FileSourceReader.this.getSentenceQueue());
/* 276:281 */       for (String experiment : FileSourceReader.this.getExperiments())
/* 277:    */       {
/* 278:282 */         Mark.say(new Object[] {">>> Working on experiment", experiment });
/* 279:283 */         FileSourceReader.this.setExperiment(experiment);
/* 280:284 */         FileSourceReader.this.setSentenceQueue(null);
/* 281:285 */         FileSourceReader.this.getSentenceQueue().addAll(handle);
/* 282:286 */         FileSourceReader.this.processedSentenceCount = 0;
/* 283:287 */         FileSourceReader.this.sentenceCount = FileSourceReader.this.getSentenceQueue().size();
/* 284:288 */         Mark.say(new Object[] {Boolean.valueOf(debug), "There are", Integer.valueOf(FileSourceReader.this.sentenceCount), "sentences to process" });
/* 285:289 */         FileSourceReader.this.processSentences();
/* 286:    */         try
/* 287:    */         {
/* 288:291 */           int sec = 5;
/* 289:292 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Pausing at end of experiment for", Integer.valueOf(5), "seconds." });
/* 290:293 */           Thread.sleep(sec * 1000);
/* 291:    */         }
/* 292:    */         catch (InterruptedException e)
/* 293:    */         {
/* 294:297 */           Mark.err(new Object[] {"Blew out of sleep in FileSourceReader.startProcessing" });
/* 295:    */         }
/* 296:    */       }
/* 297:300 */       Mark.say(new Object[] {"Experiments complete" });
/* 298:    */     }
/* 299:    */   }
/* 300:    */   
/* 301:    */   class ProcessSentences
/* 302:    */     extends Thread
/* 303:    */   {
/* 304:    */     ProcessSentences() {}
/* 305:    */     
/* 306:    */     public void run()
/* 307:    */     {
/* 308:306 */       FileSourceReader.this.processSentences();
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   public ArrayList<String> extractExperiments(String remainder)
/* 313:    */   {
/* 314:312 */     ArrayList<String> result = new ArrayList();
/* 315:313 */     for (String experiment : remainder.split(" "))
/* 316:    */     {
/* 317:314 */       experiment = experiment.trim();
/* 318:315 */       if (".,:;".indexOf(experiment.charAt(experiment.length() - 1)) >= 0) {
/* 319:316 */         experiment = experiment.substring(0, experiment.length() - 1);
/* 320:    */       }
/* 321:318 */       if ((!experiment.equals("and")) && (!experiment.equals("or"))) {
/* 322:321 */         result.add(experiment);
/* 323:    */       }
/* 324:    */     }
/* 325:323 */     return result;
/* 326:    */   }
/* 327:    */   
/* 328:    */   private void processSentences()
/* 329:    */   {
/* 330:327 */     boolean debug = false;
/* 331:328 */     long startTime = System.currentTimeMillis();
/* 332:    */     
/* 333:    */ 
/* 334:    */ 
/* 335:332 */     Connections.getPorts(this).transmit("state", "Close");
/* 336:333 */     Connections.getPorts(this).transmit("status port", new BetterSignal(new Object[] { Integer.valueOf(this.processedSentenceCount), Integer.valueOf(this.sentenceCount) }));
/* 337:    */     label715:
/* 338:334 */     while (!getSentenceQueue().isEmpty())
/* 339:    */     {
/* 340:336 */       String sentence = getNextSentence();
/* 341:338 */       if (sentence.startsWith("Run what-if experiments ")) {
/* 342:339 */         Mark.err(new Object[] {"Ooops, the expression\"", "Run what-if experiments ", "\" can only appear as the first expression read." });
/* 343:    */       }
/* 344:341 */       if (sentence.startsWith("Assume if experiment "))
/* 345:    */       {
/* 346:342 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Noted experiment", getExperiment() });
/* 347:343 */         if (this.experiment != null)
/* 348:    */         {
/* 349:344 */           boolean included = false;
/* 350:345 */           int colonIndex = sentence.indexOf(":");
/* 351:346 */           if (colonIndex < 0)
/* 352:    */           {
/* 353:347 */             Mark.err(new Object[] {"No colon in", "Assume if experiment ", "expression:", sentence });
/* 354:348 */             throw new RuntimeException();
/* 355:    */           }
/* 356:350 */           for (String x : extractExperiments(sentence.substring("Assume if experiment ".length(), colonIndex)))
/* 357:    */           {
/* 358:351 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Working on|", x, "|", getExperiment(), "|" });
/* 359:352 */             if (getExperiment().equalsIgnoreCase(x))
/* 360:    */             {
/* 361:353 */               included = true;
/* 362:    */               
/* 363:355 */               break;
/* 364:    */             }
/* 365:    */           }
/* 366:358 */           if (!included) {
/* 367:    */             continue;
/* 368:    */           }
/* 369:359 */           sentence = sentence.substring(colonIndex + 1).trim();
/* 370:360 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Experiment", getExperiment(), "included, reading", sentence });
/* 371:    */         }
/* 372:    */       }
/* 373:368 */       if ((sentence.startsWith("Insert file ")) && (!sentence.startsWith("Insert file Start experiment")))
/* 374:    */       {
/* 375:369 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Sentence starts with prefix", "Insert file ", sentence });
/* 376:370 */         String storyName = sentence.substring("Insert file ".length()).trim() + "txt";
/* 377:371 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Name |", storyName, "|" });
/* 378:    */         try
/* 379:    */         {
/* 380:374 */           URL url = PathFinder.storyURL(storyName);
/* 381:375 */           InputStream insertionStoryStream = PathFinder.storyURL(storyName).openStream();
/* 382:376 */           Object insertions = getStoryLines(insertionStoryStream, url);
/* 383:377 */           this.sentenceCount += ((ArrayList)insertions).size();
/* 384:    */           
/* 385:379 */           Mark.say(new Object[] {"Inserting file via", sentence });
/* 386:380 */           getSentenceQueue().addAll(0, (Collection)insertions);
/* 387:    */         }
/* 388:    */         catch (Exception e)
/* 389:    */         {
/* 390:384 */           Mark.err(new Object[] {"Could not find referenced story : " + storyName });
/* 391:385 */           e.printStackTrace();
/* 392:    */           break label715;
/* 393:    */         }
/* 394:    */       }
/* 395:390 */       else if (sentence.equalsIgnoreCase("Pause."))
/* 396:    */       {
/* 397:391 */         if (this.experiment != null)
/* 398:    */         {
/* 399:392 */           Mark.say(new Object[] {"\"Pause.\" not recognized when running what-if mode experiment", this.experiment, ".  Delaying a few seconds instead." });
/* 400:393 */           sentence = "Delay.";
/* 401:    */         }
/* 402:    */         else
/* 403:    */         {
/* 404:398 */           GenesisControls.getNextButton().setEnabled(true);
/* 405:399 */           GenesisControls.getRunButton().setEnabled(true);
/* 406:400 */           Mark.say(new Object[] {"Breaking on pause" });
/* 407:401 */           break;
/* 408:    */         }
/* 409:    */       }
/* 410:404 */       else if (sentence.equalsIgnoreCase("Delay."))
/* 411:    */       {
/* 412:405 */         int sec = 3;
/* 413:    */         try
/* 414:    */         {
/* 415:408 */           Thread.sleep(sec * 1000);
/* 416:    */         }
/* 417:    */         catch (InterruptedException localInterruptedException) {}
/* 418:    */       }
/* 419:415 */       Connections.getPorts(this).transmit(sentence);
/* 420:416 */       Connections.getPorts(this).transmit("status port", new BetterSignal(new Object[] { Integer.valueOf(this.processedSentenceCount), Integer.valueOf(this.sentenceCount) }));
/* 421:417 */       if (this.mode == 0) {
/* 422:    */         break;
/* 423:    */       }
/* 424:    */     }
/* 425:421 */     if (getSentenceQueue().isEmpty()) {
/* 426:422 */       Connections.getPorts(this).transmit("hasQueue", Boolean.FALSE);
/* 427:    */     } else {
/* 428:425 */       Connections.getPorts(this).transmit("hasQueue", Boolean.TRUE);
/* 429:    */     }
/* 430:427 */     Connections.getPorts(this).transmit("state", "Open");
/* 431:429 */     if (getSentenceQueue().isEmpty()) {
/* 432:430 */       Connections.getPorts(this).transmit("hasQueue", Boolean.FALSE);
/* 433:    */     }
/* 434:432 */     Timer.laptime(Switch.storyTimer.isSelected(), "Total processing time", startTime);
/* 435:    */   }
/* 436:    */   
/* 437:    */   private String getNextSentence()
/* 438:    */   {
/* 439:436 */     String sentence = (String)getSentenceQueue().get(0);
/* 440:437 */     ArrayList<String> newSentenceQueue = new ArrayList();
/* 441:438 */     for (int i = 1; i < getSentenceQueue().size(); i++) {
/* 442:439 */       newSentenceQueue.add((String)getSentenceQueue().get(i));
/* 443:    */     }
/* 444:441 */     this.processedSentenceCount += 1;
/* 445:442 */     this.sentenceQueue = newSentenceQueue;
/* 446:443 */     return sentence;
/* 447:    */   }
/* 448:    */   
/* 449:    */   private String removeComments(String string)
/* 450:    */   {
/* 451:447 */     int extra = "\n".length();
/* 452:448 */     if (string == null) {
/* 453:450 */       return null;
/* 454:    */     }
/* 455:452 */     StringBuffer sb = new StringBuffer(string);
/* 456:    */     int index1;
/* 457:455 */     while ((index1 = sb.indexOf("/*")) >= 0)
/* 458:    */     {
/* 459:    */       int index1;
/* 460:456 */       int index2 = findMatchingDelimiter(sb, index1);
/* 461:    */       
/* 462:458 */       sb.delete(index1, index2 + 2);
/* 463:    */     }
/* 464:460 */     while ((index1 = sb.indexOf("//")) >= 0)
/* 465:    */     {
/* 466:461 */       int index2 = sb.indexOf("\n", index1);
/* 467:462 */       if (index2 < 0) {
/* 468:463 */         sb.delete(index1, sb.length() + 1);
/* 469:    */       } else {
/* 470:467 */         sb.delete(index1, index2 + extra);
/* 471:    */       }
/* 472:    */     }
/* 473:470 */     return sb.toString();
/* 474:    */   }
/* 475:    */   
/* 476:    */   private int findMatchingDelimiter(StringBuffer sb, int index1)
/* 477:    */   {
/* 478:474 */     return sb.indexOf("*/");
/* 479:    */   }
/* 480:    */   
/* 481:    */   private ArrayList<String> splitText(String string)
/* 482:    */   {
/* 483:478 */     ArrayList<String> result = new ArrayList();
/* 484:479 */     int searchStart = 0;
/* 485:    */     for (;;)
/* 486:    */     {
/* 487:481 */       int index = -1;
/* 488:482 */       int quoteIndex = string.indexOf('"', searchStart);
/* 489:483 */       int index1 = string.indexOf('.', searchStart);
/* 490:484 */       int index2 = string.indexOf('?', searchStart);
/* 491:490 */       if (index1 >= 0) {
/* 492:491 */         index = index1;
/* 493:    */       }
/* 494:493 */       if ((index2 >= 0) && ((index1 < 0) || (index2 < index1))) {
/* 495:494 */         index = index2;
/* 496:    */       }
/* 497:496 */       if (index < 0) {
/* 498:    */         break;
/* 499:    */       }
/* 500:500 */       if ((quoteIndex >= 0) && (quoteIndex < index))
/* 501:    */       {
/* 502:501 */         searchStart = string.indexOf('"', quoteIndex + 1) + 1;
/* 503:    */       }
/* 504:    */       else
/* 505:    */       {
/* 506:505 */         String sentence = string.substring(0, index + 1).trim();
/* 507:507 */         if ((sentence.startsWith("Stop")) && (sentence.equals("Stop."))) {
/* 508:    */           break;
/* 509:    */         }
/* 510:511 */         Mark.say(new Object[] {Boolean.valueOf(Switch.showStartProcessingDetails.isSelected()), "Sentence:", sentence });
/* 511:    */         
/* 512:    */ 
/* 513:514 */         result.add(sentence);
/* 514:515 */         string = string.substring(index + 1);
/* 515:516 */         searchStart = 0;
/* 516:    */       }
/* 517:    */     }
/* 518:518 */     return result;
/* 519:    */   }
/* 520:    */   
/* 521:    */   public ArrayList<String> getExperiments()
/* 522:    */   {
/* 523:522 */     if (this.experiments == null) {
/* 524:523 */       this.experiments = new ArrayList();
/* 525:    */     }
/* 526:525 */     return this.experiments;
/* 527:    */   }
/* 528:    */   
/* 529:    */   public void setExperiments(ArrayList<String> experiments)
/* 530:    */   {
/* 531:529 */     this.experiments = experiments;
/* 532:    */   }
/* 533:    */   
/* 534:    */   public String getExperiment()
/* 535:    */   {
/* 536:533 */     return this.experiment;
/* 537:    */   }
/* 538:    */   
/* 539:    */   public void setExperiment(String experiment)
/* 540:    */   {
/* 541:537 */     this.experiment = experiment;
/* 542:    */   }
/* 543:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.FileSourceReader
 * JD-Core Version:    0.7.0.1
 */