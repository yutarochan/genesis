/*   1:    */ package adamKraft;
/*   2:    */ 
/*   3:    */ import adamKraft.videoUtils.MovieReader;
/*   4:    */ import java.awt.Desktop;
/*   5:    */ import java.io.BufferedReader;
/*   6:    */ import java.io.File;
/*   7:    */ import java.io.FileOutputStream;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.io.InputStream;
/*  10:    */ import java.io.InputStreamReader;
/*  11:    */ import java.io.OutputStream;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.net.Authenticator;
/*  14:    */ import java.net.MalformedURLException;
/*  15:    */ import java.net.PasswordAuthentication;
/*  16:    */ import java.net.URI;
/*  17:    */ import java.net.URISyntaxException;
/*  18:    */ import java.net.URL;
/*  19:    */ import java.net.URLConnection;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Arrays;
/*  22:    */ import java.util.Collections;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.HashSet;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Random;
/*  27:    */ import java.util.Set;
/*  28:    */ import javax.swing.text.MutableAttributeSet;
/*  29:    */ import javax.swing.text.html.HTML.Tag;
/*  30:    */ import javax.swing.text.html.HTMLEditorKit.Parser;
/*  31:    */ import javax.swing.text.html.HTMLEditorKit.ParserCallback;
/*  32:    */ import javax.swing.text.html.parser.ParserDelegator;
/*  33:    */ import utils.Mark;
/*  34:    */ import utils.WindowsConnection;
/*  35:    */ 
/*  36:    */ public class VideoBrowser
/*  37:    */ {
/*  38:    */   public static final String user = "mindseye";
/*  39:    */   public static final String pass = "mindseye";
/*  40: 47 */   public static final String CORPUS_URL_STRING = "http://mindseye-videos.csail.mit.edu/C-D2B_and_eval/";
/*  41:    */   public static final URL CORPUS_URL;
/*  42: 60 */   private Random rand = new Random(System.currentTimeMillis());
/*  43:    */   
/*  44:    */   static class HTMLIndexer
/*  45:    */     extends HTMLEditorKit.ParserCallback
/*  46:    */   {
/*  47: 63 */     boolean isTR = false;
/*  48: 65 */     boolean isTD = false;
/*  49: 67 */     boolean isA = false;
/*  50: 69 */     List<String> titles = new ArrayList();
/*  51:    */     
/*  52:    */     public String[] getTitles()
/*  53:    */     {
/*  54: 72 */       return (String[])this.titles.toArray(new String[this.titles.size()]);
/*  55:    */     }
/*  56:    */     
/*  57:    */     public void handleText(char[] data, int pos)
/*  58:    */     {
/*  59: 76 */       if ((this.isTR) && 
/*  60: 77 */         (this.isTD) && 
/*  61: 78 */         (this.isA) && 
/*  62: 79 */         (String.valueOf(data).intern() != "Parent Directory") && (
/*  63: 80 */         (String.valueOf(data).endsWith("/")) || (String.valueOf(data).toLowerCase().endsWith(".mov")) || (String.valueOf(data).toLowerCase().endsWith(".mp4")) || 
/*  64: 81 */         (String.valueOf(data).toLowerCase().endsWith(".avi")))) {
/*  65: 82 */         this.titles.add(String.valueOf(data));
/*  66:    */       }
/*  67:    */     }
/*  68:    */     
/*  69:    */     public void handleComment(char[] data, int pos) {}
/*  70:    */     
/*  71:    */     private void setFlags(HTML.Tag t, boolean flag)
/*  72:    */     {
/*  73: 91 */       String tag = t.toString().toUpperCase().intern();
/*  74: 92 */       if (tag == "A") {
/*  75: 93 */         this.isA = flag;
/*  76: 95 */       } else if (tag == "TR") {
/*  77: 96 */         this.isTR = flag;
/*  78: 98 */       } else if (tag == "TD") {
/*  79: 99 */         this.isTD = flag;
/*  80:    */       }
/*  81:    */     }
/*  82:    */     
/*  83:    */     public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos)
/*  84:    */     {
/*  85:104 */       setFlags(t, true);
/*  86:    */     }
/*  87:    */     
/*  88:    */     public void handleEndTag(HTML.Tag t, int pos)
/*  89:    */     {
/*  90:108 */       setFlags(t, false);
/*  91:    */     }
/*  92:    */     
/*  93:    */     public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {}
/*  94:    */     
/*  95:    */     public void handleError(String errorMsg, int pos)
/*  96:    */     {
/*  97:116 */       System.out.println("Parsing error: " + errorMsg + " at " + pos);
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static class MyAuthenticator
/* 102:    */     extends Authenticator
/* 103:    */   {
/* 104:    */     protected PasswordAuthentication getPasswordAuthentication()
/* 105:    */     {
/* 106:122 */       return new PasswordAuthentication("mindseye", "mindseye".toCharArray());
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   private BufferedReader openURL(URL foo)
/* 111:    */     throws IOException
/* 112:    */   {
/* 113:127 */     Mark.say(
/* 114:    */     
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:137 */       new Object[] { foo });URLConnection yc = foo.openConnection();String userPassword = "mindseye:mindseye";yc.setRequestProperty("Authorization", "Basic " + userPassword);BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));return in;
/* 124:    */   }
/* 125:    */   
/* 126:139 */   private String[] categories = null;
/* 127:    */   
/* 128:    */   public String[] getCategories()
/* 129:    */     throws IOException
/* 130:    */   {
/* 131:142 */     if (this.categories != null) {
/* 132:143 */       return (String[])Arrays.copyOf(this.categories, this.categories.length);
/* 133:    */     }
/* 134:146 */     BufferedReader in = openURL(CORPUS_URL);
/* 135:    */     
/* 136:    */ 
/* 137:149 */     HTMLEditorKit.Parser parser = new ParserDelegator();
/* 138:150 */     HTMLIndexer idx = new HTMLIndexer();
/* 139:151 */     parser.parse(in, idx, true);
/* 140:152 */     in.close();
/* 141:153 */     this.categories = idx.getTitles();
/* 142:154 */     return getCategories();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String[] getVideoTitles(String category)
/* 146:    */     throws IOException
/* 147:    */   {
/* 148:160 */     return getVideoTitles(category, new String[] { "" });
/* 149:    */   }
/* 150:    */   
/* 151:    */   private URL getMovieURL(String category, String file)
/* 152:    */   {
/* 153:    */     try
/* 154:    */     {
/* 155:165 */       return new URL(CORPUS_URL_STRING + category + "/" + file);
/* 156:    */     }
/* 157:    */     catch (MalformedURLException e)
/* 158:    */     {
/* 159:168 */       e.printStackTrace();
/* 160:    */     }
/* 161:169 */     return null;
/* 162:    */   }
/* 163:    */   
/* 164:    */   private URL getSubDir(String sub)
/* 165:    */   {
/* 166:    */     try
/* 167:    */     {
/* 168:175 */       return new URL(CORPUS_URL_STRING + sub);
/* 169:    */     }
/* 170:    */     catch (MalformedURLException e)
/* 171:    */     {
/* 172:178 */       e.printStackTrace();
/* 173:    */     }
/* 174:179 */     return null;
/* 175:    */   }
/* 176:    */   
/* 177:183 */   private HashMap<String, String[]> titleCache = new HashMap();
/* 178:    */   public static final Set<String> VERBS;
/* 179:    */   public static final String[] CD1B_TITLES;
/* 180:    */   private static VideoBrowser singleton;
/* 181:    */   
/* 182:    */   public String[] getVideoTitles(String category, String... titleSubstrings)
/* 183:    */     throws IOException
/* 184:    */   {
/* 185:    */     String[] all;
/* 186:    */     String[] all;
/* 187:194 */     if (this.titleCache.containsKey(category))
/* 188:    */     {
/* 189:195 */       all = (String[])this.titleCache.get(category);
/* 190:    */     }
/* 191:    */     else
/* 192:    */     {
/* 193:198 */       URL subdir = getSubDir(category);
/* 194:199 */       BufferedReader in = openURL(subdir);
/* 195:    */       
/* 196:    */ 
/* 197:202 */       parser = new ParserDelegator();
/* 198:203 */       idx = new HTMLIndexer();
/* 199:204 */       parser.parse(in, idx, true);
/* 200:205 */       in.close();
/* 201:206 */       all = idx.getTitles();
/* 202:207 */       this.titleCache.put(category, all);
/* 203:    */     }
/* 204:209 */     List<String> filt = new ArrayList();
/* 205:    */     String[] arrayOfString1;
/* 206:210 */     HTMLIndexer idx = (arrayOfString1 = all).length;
/* 207:210 */     for (HTMLEditorKit.Parser parser = 0; parser < idx; parser++)
/* 208:    */     {
/* 209:210 */       String t = arrayOfString1[parser];
/* 210:211 */       boolean use = true;
/* 211:212 */       for (String titleSubstring : titleSubstrings) {
/* 212:213 */         if (!t.toLowerCase().contains(titleSubstring.toLowerCase()))
/* 213:    */         {
/* 214:214 */           use = false;
/* 215:215 */           break;
/* 216:    */         }
/* 217:    */       }
/* 218:218 */       if (use) {
/* 219:219 */         filt.add(t);
/* 220:    */       }
/* 221:    */     }
/* 222:223 */     return (String[])filt.toArray(new String[filt.size()]);
/* 223:    */   }
/* 224:    */   
/* 225:    */   protected void downloadVideo(String category, String title)
/* 226:    */     throws IOException
/* 227:    */   {
/* 228:227 */     System.out.println("Downloading file " + category + "/" + title + " ...");
/* 229:228 */     File temp = getFile(title + ".PART");
/* 230:229 */     OutputStream out = new FileOutputStream(temp);
/* 231:230 */     InputStream in = getMovieURL(category, title).openConnection().getInputStream();
/* 232:231 */     byte[] buf = new byte[1048576];
/* 233:    */     int len;
/* 234:233 */     while ((len = in.read(buf)) > 0)
/* 235:    */     {
/* 236:    */       int len;
/* 237:234 */       out.write(buf, 0, len);
/* 238:    */     }
/* 239:236 */     in.close();
/* 240:237 */     out.close();
/* 241:238 */     in = null;
/* 242:239 */     out = null;
/* 243:240 */     NoBullshitFileIO.rename(temp.getPath(), getFile(title).getPath());
/* 244:    */     
/* 245:    */ 
/* 246:243 */     System.out.println("Done downloading.");
/* 247:    */   }
/* 248:    */   
/* 249:    */   protected File getFile(String title)
/* 250:    */   {
/* 251:247 */     File tempdir = new File(System.getProperty("user.home") + File.separator + "DARPA_VIDEO_CACHE");
/* 252:248 */     Mark.say(new Object[] {"The darpa cache directory is:", tempdir.toString() });
/* 253:249 */     if (!tempdir.exists())
/* 254:    */     {
/* 255:250 */       System.out.println("making DARPA video cache directory");
/* 256:251 */       tempdir.mkdir();
/* 257:    */     }
/* 258:253 */     File file = new File(System.getProperty("user.home") + File.separator + "DARPA_VIDEO_CACHE" + File.separator + title);
/* 259:254 */     Mark.say(new Object[] {"Returning file:", file.getPath() });
/* 260:255 */     return file;
/* 261:    */   }
/* 262:    */   
/* 263:    */   protected URL getVideo(String title)
/* 264:    */     throws IOException
/* 265:    */   {
/* 266:259 */     return getFile(title).toURI().toURL();
/* 267:    */   }
/* 268:    */   
/* 269:    */   protected boolean inCache(String title)
/* 270:    */   {
/* 271:264 */     File f = getFile(title);
/* 272:265 */     return f.exists();
/* 273:    */   }
/* 274:    */   
/* 275:    */   public synchronized URL getVideo(String category, String title)
/* 276:    */     throws IOException
/* 277:    */   {
/* 278:269 */     if (!inCache(title)) {
/* 279:270 */       downloadVideo(category, title);
/* 280:    */     }
/* 281:272 */     return getVideo(title);
/* 282:    */   }
/* 283:    */   
/* 284:    */   protected String getCacheName(String title)
/* 285:    */     throws IOException
/* 286:    */   {
/* 287:276 */     if (inCache(title)) {
/* 288:    */       try
/* 289:    */       {
/* 290:278 */         String path = getVideo(title).toURI().getPath();
/* 291:279 */         if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {}
/* 292:280 */         return path.replaceFirst("/", "").replace("/", "\\");
/* 293:    */       }
/* 294:    */       catch (URISyntaxException e)
/* 295:    */       {
/* 296:285 */         throw new RuntimeException(e);
/* 297:    */       }
/* 298:    */     }
/* 299:    */     try
/* 300:    */     {
/* 301:290 */       for (String category : getCategories()) {
/* 302:291 */         for (String catTitle : getVideoTitles(category)) {
/* 303:292 */           if (catTitle.toLowerCase().intern() == title.toLowerCase().intern()) {
/* 304:293 */             return getVideo(category, catTitle).getPath();
/* 305:    */           }
/* 306:    */         }
/* 307:    */       }
/* 308:    */     }
/* 309:    */     catch (IOException e)
/* 310:    */     {
/* 311:301 */       throw new IOException(title + " not in cache, and offline");
/* 312:    */     }
/* 313:303 */     throw new IOException(title + " does not exist on the server");
/* 314:    */   }
/* 315:    */   
/* 316:    */   public MovieReader getMovie(String title)
/* 317:    */     throws IOException
/* 318:    */   {
/* 319:309 */     return new MovieReader(getCacheName(title));
/* 320:    */   }
/* 321:    */   
/* 322:    */   @Deprecated
/* 323:    */   public void showInBrowser(URL url)
/* 324:    */   {
/* 325:    */     try
/* 326:    */     {
/* 327:315 */       if (System.getProperty("os.name").toLowerCase().contains("dows")) {
/* 328:331 */         showWithLosedows(url);
/* 329:    */       } else {
/* 330:335 */         Desktop.getDesktop().browse(url.toURI());
/* 331:    */       }
/* 332:    */     }
/* 333:    */     catch (IOException e)
/* 334:    */     {
/* 335:339 */       e.printStackTrace();
/* 336:    */     }
/* 337:    */     catch (URISyntaxException e)
/* 338:    */     {
/* 339:342 */       e.printStackTrace();
/* 340:    */     }
/* 341:    */   }
/* 342:    */   
/* 343:    */   @Deprecated
/* 344:    */   public void showInBrowser(String category, String title)
/* 345:    */     throws IOException
/* 346:    */   {
/* 347:348 */     Mark.say(
/* 348:    */     
/* 349:    */ 
/* 350:351 */       new Object[] { "Video is at", getVideo(category, title) });showInBrowser(getVideo(category, title));
/* 351:    */   }
/* 352:    */   
/* 353:    */   private void showWithLosedows(URL video)
/* 354:    */   {
/* 355:355 */     String file = video.getFile();
/* 356:356 */     int last = file.lastIndexOf('/');
/* 357:357 */     File dir = new File(file.substring(0, last + 1));
/* 358:358 */     String name = file.substring(last + 1);
/* 359:359 */     Mark.say(new Object[] {"Directory is", dir.getPath() });
/* 360:360 */     Mark.say(new Object[] {"Name is", name });
/* 361:361 */     WindowsConnection.run(name, dir);
/* 362:    */   }
/* 363:    */   
/* 364:    */   @Deprecated
/* 365:    */   public void showRandomVideoWithKeywords(String... keywords)
/* 366:    */     throws IOException
/* 367:    */   {
/* 368:366 */     String[] categories = getCategories();
/* 369:367 */     HashMap<String, String[]> everything = new HashMap();
/* 370:368 */     for (String cat : categories)
/* 371:    */     {
/* 372:369 */       String[] titles = getVideoTitles(cat, keywords);
/* 373:370 */       if (titles.length > 0) {
/* 374:371 */         everything.put(cat, titles);
/* 375:    */       }
/* 376:    */     }
/* 377:374 */     if (everything.size() > 0)
/* 378:    */     {
/* 379:375 */       String category = everything.keySet().toArray()[this.rand.nextInt(everything.keySet().size())].toString();
/* 380:376 */       String title = ((String[])everything.get(category))[this.rand.nextInt(((String[])everything.get(category)).length)];
/* 381:377 */       showInBrowser(category, title);
/* 382:    */     }
/* 383:    */     else
/* 384:    */     {
/* 385:380 */       List<String> kw = Arrays.asList(keywords);
/* 386:381 */       System.out.println("no videos found for keywords " + kw);
/* 387:    */     }
/* 388:    */   }
/* 389:    */   
/* 390:    */   @Deprecated
/* 391:    */   public void showVideoWithMostlyKeywords(String... keywords)
/* 392:    */     throws IOException
/* 393:    */   {
/* 394:387 */     Mark.say(
/* 395:    */     
/* 396:    */ 
/* 397:    */ 
/* 398:    */ 
/* 399:    */ 
/* 400:    */ 
/* 401:    */ 
/* 402:    */ 
/* 403:    */ 
/* 404:    */ 
/* 405:    */ 
/* 406:    */ 
/* 407:    */ 
/* 408:    */ 
/* 409:    */ 
/* 410:    */ 
/* 411:    */ 
/* 412:    */ 
/* 413:    */ 
/* 414:    */ 
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:    */ 
/* 419:    */ 
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:    */ 
/* 424:    */ 
/* 425:    */ 
/* 426:    */ 
/* 427:    */ 
/* 428:    */ 
/* 429:    */ 
/* 430:    */ 
/* 431:    */ 
/* 432:    */ 
/* 433:426 */       new Object[] { "Showing with keywords:" });
/* 434:388 */     for (String keyword : keywords) {
/* 435:389 */       Mark.say(new Object[] {keyword });
/* 436:    */     }
/* 437:391 */     String[] categories = getCategories();
/* 438:392 */     Object everything = new HashMap();
/* 439:    */     String[] titles;
/* 440:393 */     for (String cat : categories)
/* 441:    */     {
/* 442:394 */       titles = getVideoTitles(cat, keywords);
/* 443:395 */       if (titles.length > 0) {
/* 444:396 */         ((HashMap)everything).put(cat, titles);
/* 445:    */       }
/* 446:    */     }
/* 447:399 */     String title = null;
/* 448:400 */     String category = null;
/* 449:401 */     int size = -1;
/* 450:402 */     if (((HashMap)everything).size() > 0)
/* 451:    */     {
/* 452:    */       String[] arrayOfString4;
/* 453:403 */       String[] arrayOfString3 = (arrayOfString4 = categories).length;
/* 454:403 */       for (titles = 0; titles < arrayOfString3; titles++)
/* 455:    */       {
/* 456:403 */         String cat = arrayOfString4[titles];
/* 457:404 */         String[] titles = (String[])((HashMap)everything).get(cat);
/* 458:405 */         if (titles != null) {
/* 459:406 */           for (String t : titles)
/* 460:    */           {
/* 461:407 */             int count = getOrderedVerbs(t).size();
/* 462:409 */             if ((size < 0) || (count < size))
/* 463:    */             {
/* 464:410 */               title = t;
/* 465:411 */               category = cat;
/* 466:412 */               size = getOrderedVerbs(t).size();
/* 467:    */             }
/* 468:    */           }
/* 469:    */         }
/* 470:    */       }
/* 471:419 */       Mark.say(new Object[] {"Winning Cat/Title", category, title, Integer.valueOf(getOrderedVerbs(title).size()) });
/* 472:420 */       showInBrowser(category, title);
/* 473:    */     }
/* 474:    */     else
/* 475:    */     {
/* 476:423 */       Object kw = Arrays.asList(keywords);
/* 477:424 */       System.out.println("no videos found for keywords " + kw);
/* 478:    */     }
/* 479:    */   }
/* 480:    */   
/* 481:    */   @Deprecated
/* 482:    */   public static List<String> getOrderedVerbs(String videoTitle)
/* 483:    */   {
/* 484:442 */     List<String> orderedVerbs = new ArrayList();
/* 485:443 */     String[] titleWords = videoTitle.toLowerCase().split("_");
/* 486:444 */     for (String word : titleWords) {
/* 487:445 */       for (String verb : VERBS) {
/* 488:446 */         if (word.startsWith(verb)) {
/* 489:447 */           orderedVerbs.add(verb);
/* 490:    */         }
/* 491:    */       }
/* 492:    */     }
/* 493:451 */     return orderedVerbs;
/* 494:    */   }
/* 495:    */   
/* 496:    */   static
/* 497:    */   {
/* 498:    */     URL foo;
/* 499:    */     try
/* 500:    */     {
/* 501: 50 */       foo = new URL(CORPUS_URL_STRING);
/* 502:    */     }
/* 503:    */     catch (MalformedURLException e)
/* 504:    */     {
/* 505:    */       URL foo;
/* 506: 53 */       e.printStackTrace();
/* 507: 54 */       foo = null;
/* 508:    */     }
/* 509: 56 */     CORPUS_URL = foo;
/* 510: 57 */     Authenticator.setDefault(new MyAuthenticator());
/* 511:    */     
/* 512:    */ 
/* 513:    */ 
/* 514:    */ 
/* 515:    */ 
/* 516:    */ 
/* 517:    */ 
/* 518:    */ 
/* 519:    */ 
/* 520:    */ 
/* 521:    */ 
/* 522:    */ 
/* 523:    */ 
/* 524:    */ 
/* 525:    */ 
/* 526:    */ 
/* 527:    */ 
/* 528:    */ 
/* 529:    */ 
/* 530:    */ 
/* 531:    */ 
/* 532:    */ 
/* 533:    */ 
/* 534:    */ 
/* 535:    */ 
/* 536:    */ 
/* 537:    */ 
/* 538:    */ 
/* 539:    */ 
/* 540:    */ 
/* 541:    */ 
/* 542:    */ 
/* 543:    */ 
/* 544:    */ 
/* 545:    */ 
/* 546:    */ 
/* 547:    */ 
/* 548:    */ 
/* 549:    */ 
/* 550:    */ 
/* 551:    */ 
/* 552:    */ 
/* 553:    */ 
/* 554:    */ 
/* 555:    */ 
/* 556:    */ 
/* 557:    */ 
/* 558:    */ 
/* 559:    */ 
/* 560:    */ 
/* 561:    */ 
/* 562:    */ 
/* 563:    */ 
/* 564:    */ 
/* 565:    */ 
/* 566:    */ 
/* 567:    */ 
/* 568:    */ 
/* 569:    */ 
/* 570:    */ 
/* 571:    */ 
/* 572:    */ 
/* 573:    */ 
/* 574:    */ 
/* 575:    */ 
/* 576:    */ 
/* 577:    */ 
/* 578:    */ 
/* 579:    */ 
/* 580:    */ 
/* 581:    */ 
/* 582:    */ 
/* 583:    */ 
/* 584:    */ 
/* 585:    */ 
/* 586:    */ 
/* 587:    */ 
/* 588:    */ 
/* 589:    */ 
/* 590:    */ 
/* 591:    */ 
/* 592:    */ 
/* 593:    */ 
/* 594:    */ 
/* 595:    */ 
/* 596:    */ 
/* 597:    */ 
/* 598:    */ 
/* 599:    */ 
/* 600:    */ 
/* 601:    */ 
/* 602:    */ 
/* 603:    */ 
/* 604:    */ 
/* 605:    */ 
/* 606:    */ 
/* 607:    */ 
/* 608:    */ 
/* 609:    */ 
/* 610:    */ 
/* 611:    */ 
/* 612:    */ 
/* 613:    */ 
/* 614:    */ 
/* 615:    */ 
/* 616:    */ 
/* 617:    */ 
/* 618:    */ 
/* 619:    */ 
/* 620:    */ 
/* 621:    */ 
/* 622:    */ 
/* 623:    */ 
/* 624:    */ 
/* 625:    */ 
/* 626:    */ 
/* 627:    */ 
/* 628:    */ 
/* 629:    */ 
/* 630:    */ 
/* 631:    */ 
/* 632:    */ 
/* 633:    */ 
/* 634:    */ 
/* 635:    */ 
/* 636:    */ 
/* 637:    */ 
/* 638:    */ 
/* 639:    */ 
/* 640:    */ 
/* 641:    */ 
/* 642:    */ 
/* 643:    */ 
/* 644:    */ 
/* 645:    */ 
/* 646:    */ 
/* 647:    */ 
/* 648:    */ 
/* 649:    */ 
/* 650:    */ 
/* 651:    */ 
/* 652:    */ 
/* 653:    */ 
/* 654:    */ 
/* 655:    */ 
/* 656:    */ 
/* 657:    */ 
/* 658:    */ 
/* 659:    */ 
/* 660:    */ 
/* 661:    */ 
/* 662:    */ 
/* 663:    */ 
/* 664:    */ 
/* 665:    */ 
/* 666:    */ 
/* 667:    */ 
/* 668:    */ 
/* 669:    */ 
/* 670:    */ 
/* 671:    */ 
/* 672:    */ 
/* 673:    */ 
/* 674:    */ 
/* 675:    */ 
/* 676:    */ 
/* 677:    */ 
/* 678:    */ 
/* 679:    */ 
/* 680:    */ 
/* 681:    */ 
/* 682:    */ 
/* 683:    */ 
/* 684:    */ 
/* 685:    */ 
/* 686:    */ 
/* 687:    */ 
/* 688:    */ 
/* 689:    */ 
/* 690:    */ 
/* 691:    */ 
/* 692:    */ 
/* 693:    */ 
/* 694:    */ 
/* 695:    */ 
/* 696:    */ 
/* 697:    */ 
/* 698:    */ 
/* 699:    */ 
/* 700:    */ 
/* 701:    */ 
/* 702:    */ 
/* 703:    */ 
/* 704:    */ 
/* 705:    */ 
/* 706:    */ 
/* 707:    */ 
/* 708:    */ 
/* 709:    */ 
/* 710:    */ 
/* 711:    */ 
/* 712:    */ 
/* 713:    */ 
/* 714:    */ 
/* 715:    */ 
/* 716:    */ 
/* 717:    */ 
/* 718:    */ 
/* 719:    */ 
/* 720:    */ 
/* 721:    */ 
/* 722:    */ 
/* 723:    */ 
/* 724:    */ 
/* 725:    */ 
/* 726:    */ 
/* 727:    */ 
/* 728:    */ 
/* 729:    */ 
/* 730:    */ 
/* 731:    */ 
/* 732:    */ 
/* 733:    */ 
/* 734:    */ 
/* 735:    */ 
/* 736:    */ 
/* 737:    */ 
/* 738:    */ 
/* 739:    */ 
/* 740:    */ 
/* 741:    */ 
/* 742:    */ 
/* 743:    */ 
/* 744:    */ 
/* 745:    */ 
/* 746:    */ 
/* 747:    */ 
/* 748:    */ 
/* 749:    */ 
/* 750:    */ 
/* 751:    */ 
/* 752:    */ 
/* 753:    */ 
/* 754:    */ 
/* 755:    */ 
/* 756:    */ 
/* 757:    */ 
/* 758:    */ 
/* 759:    */ 
/* 760:    */ 
/* 761:    */ 
/* 762:    */ 
/* 763:    */ 
/* 764:    */ 
/* 765:    */ 
/* 766:    */ 
/* 767:    */ 
/* 768:    */ 
/* 769:    */ 
/* 770:    */ 
/* 771:    */ 
/* 772:    */ 
/* 773:    */ 
/* 774:    */ 
/* 775:    */ 
/* 776:    */ 
/* 777:    */ 
/* 778:    */ 
/* 779:    */ 
/* 780:    */ 
/* 781:    */ 
/* 782:    */ 
/* 783:    */ 
/* 784:    */ 
/* 785:    */ 
/* 786:    */ 
/* 787:    */ 
/* 788:    */ 
/* 789:    */ 
/* 790:    */ 
/* 791:    */ 
/* 792:    */ 
/* 793:    */ 
/* 794:    */ 
/* 795:    */ 
/* 796:    */ 
/* 797:    */ 
/* 798:    */ 
/* 799:    */ 
/* 800:    */ 
/* 801:    */ 
/* 802:    */ 
/* 803:    */ 
/* 804:    */ 
/* 805:    */ 
/* 806:    */ 
/* 807:    */ 
/* 808:    */ 
/* 809:    */ 
/* 810:    */ 
/* 811:    */ 
/* 812:    */ 
/* 813:    */ 
/* 814:    */ 
/* 815:    */ 
/* 816:    */ 
/* 817:    */ 
/* 818:    */ 
/* 819:    */ 
/* 820:    */ 
/* 821:    */ 
/* 822:    */ 
/* 823:    */ 
/* 824:    */ 
/* 825:    */ 
/* 826:    */ 
/* 827:    */ 
/* 828:    */ 
/* 829:    */ 
/* 830:    */ 
/* 831:    */ 
/* 832:    */ 
/* 833:    */ 
/* 834:    */ 
/* 835:    */ 
/* 836:    */ 
/* 837:    */ 
/* 838:    */ 
/* 839:    */ 
/* 840:    */ 
/* 841:    */ 
/* 842:    */ 
/* 843:    */ 
/* 844:    */ 
/* 845:    */ 
/* 846:    */ 
/* 847:    */ 
/* 848:    */ 
/* 849:    */ 
/* 850:    */ 
/* 851:    */ 
/* 852:    */ 
/* 853:    */ 
/* 854:    */ 
/* 855:    */ 
/* 856:    */ 
/* 857:    */ 
/* 858:    */ 
/* 859:    */ 
/* 860:    */ 
/* 861:    */ 
/* 862:    */ 
/* 863:    */ 
/* 864:    */ 
/* 865:    */ 
/* 866:    */ 
/* 867:    */ 
/* 868:    */ 
/* 869:    */ 
/* 870:    */ 
/* 871:    */ 
/* 872:    */ 
/* 873:    */ 
/* 874:    */ 
/* 875:    */ 
/* 876:    */ 
/* 877:    */ 
/* 878:    */ 
/* 879:    */ 
/* 880:    */ 
/* 881:    */ 
/* 882:    */ 
/* 883:430 */     HashSet<String> s = new HashSet();
/* 884:    */     String[] arrayOfString;
/* 885:434 */     int j = (arrayOfString = new String[] { "approach", "carry", "dig", "fall", "give", "hit", "lift", "push", "run", "touch", "arrive", "catch", "drop", "flee", "go", "hold", "move", "putdown", "snatch", "turn", "attach", "chase", "enter", "fly", "hand", "kick", "open", "raise", "stop", "walk", "bounce", "close", "exchange", "follow", "haul", "jump", "pass", "receive", "take", "bury", "collide", "exit", "get", "have", "leave", "pickup", "replace", "throw" }).length;
/* 886:434 */     for (int i = 0; i < j; i++)
/* 887:    */     {
/* 888:434 */       String v = arrayOfString[i];
/* 889:435 */       s.add(v);
/* 890:    */     }
/* 891:437 */     VERBS = Collections.unmodifiableSet(s);
/* 892:    */     
/* 893:    */ 
/* 894:    */ 
/* 895:    */ 
/* 896:    */ 
/* 897:    */ 
/* 898:    */ 
/* 899:    */ 
/* 900:    */ 
/* 901:    */ 
/* 902:    */ 
/* 903:    */ 
/* 904:    */ 
/* 905:    */ 
/* 906:    */ 
/* 907:    */ 
/* 908:    */ 
/* 909:    */ 
/* 910:456 */     ArrayList<String> tmp = new ArrayList();
/* 911:    */     try
/* 912:    */     {
/* 913:458 */       BufferedReader javabullshit = new BufferedReader(new InputStreamReader(Co57ConnectTest.class.getResource("cd1b_A-E_filenames.txt")
/* 914:459 */         .openStream()));
/* 915:460 */       String s2 = null;
/* 916:    */       do
/* 917:    */       {
/* 918:462 */         s2 = javabullshit.readLine();
/* 919:463 */         if (s2 != null) {
/* 920:464 */           tmp.add(s2);
/* 921:    */         }
/* 922:467 */       } while (s2 != null);
/* 923:    */     }
/* 924:    */     catch (IOException e)
/* 925:    */     {
/* 926:470 */       e.printStackTrace();
/* 927:    */     }
/* 928:472 */     CD1B_TITLES = (String[])tmp.toArray(new String[0]);
/* 929:    */   }
/* 930:    */   
/* 931:    */   public static VideoBrowser getVideoBrowser()
/* 932:    */   {
/* 933:478 */     if (singleton == null) {
/* 934:479 */       singleton = new VideoBrowser();
/* 935:    */     }
/* 936:481 */     return singleton;
/* 937:    */   }
/* 938:    */   
/* 939:    */   public static void main(String[] args)
/* 940:    */     throws Exception
/* 941:    */   {
/* 942:485 */     VideoBrowser v = new VideoBrowser();
/* 943:486 */     for (String verb : VERBS) {
/* 944:487 */       System.out.println(verb + ", ");
/* 945:    */     }
/* 946:489 */     System.out.println(System.getProperty("java.class.path"));
/* 947:491 */     for (String title : v.getCategories()) {
/* 948:492 */       System.out.println(title);
/* 949:    */     }
/* 950:495 */     for (String title : v.getVideoTitles("Country_Road_1/")) {
/* 951:496 */       System.out.println(title);
/* 952:    */     }
/* 953:510 */     String aTitle = "Arrive1_Action1_Approach1_Attach8_Leave5_A1_C2_Act1_2_URBAN1_BR_MIDD_4629afa8-c5af-11df-95a6-e80688cb869a.mov";
/* 954:    */     
/* 955:512 */     System.out.println("aTitle: " + aTitle);
/* 956:513 */     System.out.println("aTitle has the following verbs " + getOrderedVerbs(aTitle));
/* 957:    */     
/* 958:515 */     System.out.println(VERBS);
/* 959:    */   }
/* 960:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.VideoBrowser
 * JD-Core Version:    0.7.0.1
 */