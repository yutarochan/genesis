/*   1:    */ package michaelBehr;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.net.URL;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Map;
/*  11:    */ import javax.xml.parsers.DocumentBuilder;
/*  12:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  13:    */ import javax.xml.parsers.ParserConfigurationException;
/*  14:    */ import org.w3c.dom.Document;
/*  15:    */ import org.w3c.dom.Element;
/*  16:    */ import org.w3c.dom.ls.DOMImplementationLS;
/*  17:    */ import org.w3c.dom.ls.LSSerializer;
/*  18:    */ import org.xml.sax.SAXException;
/*  19:    */ import start.Generator;
/*  20:    */ import start.StartEntity;
/*  21:    */ import tools.Innerese;
/*  22:    */ import translator.Translator;
/*  23:    */ import utils.Mark;
/*  24:    */ import utils.TextIO;
/*  25:    */ 
/*  26:    */ public class Annotation
/*  27:    */   implements AnnotationElement
/*  28:    */ {
/*  29:    */   File annotationFile;
/*  30:    */   Track[] tracks;
/*  31:    */   String name;
/*  32:    */   int width;
/*  33:    */   int height;
/*  34:    */   int frameCount;
/*  35: 39 */   private static String SOMETHING = "something";
/*  36: 41 */   private static String OBJECT = "object";
/*  37: 43 */   private static boolean debug = false;
/*  38: 45 */   public static HashMap<String, Integer> verbs = new HashMap();
/*  39:    */   Map<String, Track> trackMap;
/*  40:    */   
/*  41:    */   public static void extractEnglish(String name)
/*  42:    */     throws Exception
/*  43:    */   {
/*  44: 51 */     extractEnglish(new File(name));
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static void extractEnglish(File file)
/*  48:    */     throws Exception
/*  49:    */   {
/*  50: 55 */     Mark.say(
/*  51:    */     
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85: 90 */       new Object[] { "---->" });Mark.say(new Object[] { Boolean.valueOf(debug), "Working on file", file });Annotation annotation = new Annotation(file);Track[] tracks = annotation.getTracks();Mark.say(new Object[] { Boolean.valueOf(debug), "Tracks:", Integer.valueOf(tracks.length) });
/*  86: 60 */     for (Track track : tracks)
/*  87:    */     {
/*  88: 62 */       Segment[] segments = track.getSegments();
/*  89: 63 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Segments:", Integer.valueOf(segments.length) });
/*  90: 64 */       for (Segment segment : segments)
/*  91:    */       {
/*  92: 66 */         int start = segment.getStartFrame();
/*  93: 67 */         int end = segment.getEndFrame();
/*  94:    */         
/*  95: 69 */         Label[] labels = segment.getLabels();
/*  96: 70 */         Mark.say(new Object[] {Boolean.valueOf(debug), "Labels:", Integer.valueOf(labels.length) });
/*  97: 72 */         for (Label label : labels)
/*  98:    */         {
/*  99: 74 */           String value = label.getSubject();
/* 100:    */           
/* 101: 76 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Value\n", value, "\n" });
/* 102:    */           try
/* 103:    */           {
/* 104: 79 */             Entity t = Translator.getTranslator().translate(condition(value), Integer.valueOf(start / 30), Integer.valueOf(end / 30));
/* 105: 80 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Translation", t.asString() });
/* 106: 81 */             String echo = Generator.getGenerator().playByPlay(t, "present", false);
/* 107: 82 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Done:", Integer.valueOf(start / 30), Integer.valueOf(end / 30), value, "\n", echo });
/* 108:    */           }
/* 109:    */           catch (Exception e)
/* 110:    */           {
/* 111: 85 */             Mark.say(new Object[] {Boolean.valueOf(debug), "Unable to process", value, "in", Integer.valueOf(start / 30), Integer.valueOf(end / 30) });
/* 112:    */           }
/* 113:    */         }
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static ArrayList<BetterSignal> extractRoles(File file)
/* 119:    */     throws Exception
/* 120:    */   {
/* 121: 93 */     return extractRoles(file, 0);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static ArrayList<BetterSignal> extractRoles(File file, int offset)
/* 125:    */     throws Exception
/* 126:    */   {
/* 127: 98 */     HashMap<String, String> map = new HashMap();
/* 128: 99 */     int limit = 10;
/* 129:100 */     boolean useLimit = false;
/* 130:    */     
/* 131:102 */     ArrayList<BetterSignal> signals = new ArrayList();
/* 132:103 */     Annotation co57annotation = new Annotation(file);
/* 133:104 */     Track[] co57tracks = co57annotation.getTracks();
/* 134:105 */     int trackIdx = 0;
/* 135:107 */     for (Track track : co57tracks)
/* 136:    */     {
/* 137:108 */       Segment[] segments = track.getSegments();
/* 138:    */       
/* 139:110 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Working on track", track.getUid(), "with segments", Integer.valueOf(segments.length) });
/* 140:112 */       for (Segment segment : segments)
/* 141:    */       {
/* 142:114 */         Label[] labels = segment.getLabels();
/* 143:    */         
/* 144:    */ 
/* 145:117 */         int start = segment.getStartFrame();
/* 146:118 */         int end = segment.getEndFrame();
/* 147:120 */         for (Label label : labels)
/* 148:    */         {
/* 149:122 */           String trackType = track.getEntityType();
/* 150:123 */           String verb = label.getValue();
/* 151:124 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Track type, verb", trackType, verb });
/* 152:    */           
/* 153:126 */           Track subjectTrack = label.getSubjectTrack();
/* 154:127 */           Track directTrack = label.getDirectObjectTrack();
/* 155:128 */           Track indirectTrack = label.getIndirectObjectTrack();
/* 156:    */           
/* 157:130 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Subject track", subjectTrack });
/* 158:131 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Direct track", directTrack });
/* 159:132 */           Mark.say(new Object[] {Boolean.valueOf(debug), "Indirect track", indirectTrack });
/* 160:    */           
/* 161:    */ 
/* 162:    */ 
/* 163:136 */           Object verbCount = verbs.get(verb);
/* 164:137 */           if (verbCount == null) {
/* 165:138 */             verbs.put(verb, Integer.valueOf(1));
/* 166:    */           } else {
/* 167:141 */             verbs.put(verb, Integer.valueOf(((Integer)verbs.get(verb)).intValue() + 1));
/* 168:    */           }
/* 169:146 */           StartEntity directTrackEntity = null;
/* 170:    */           
/* 171:148 */           String subjectTrackType = null;
/* 172:149 */           if (subjectTrack != null) {
/* 173:150 */             subjectTrackType = subjectTrack.getEntityType();
/* 174:    */           }
/* 175:152 */           String directTrackType = null;
/* 176:    */           
/* 177:154 */           String directTrackFeatures = null;
/* 178:155 */           if (directTrack != null)
/* 179:    */           {
/* 180:156 */             directTrackType = directTrack.getEntityType();
/* 181:157 */             directTrackFeatures = directTrack.getFeatures();
/* 182:    */           }
/* 183:160 */           else if ((verb.equalsIgnoreCase("putdown")) || (verb.equalsIgnoreCase("pickup2")))
/* 184:    */           {
/* 185:161 */             if (directTrackFeatures == null) {
/* 186:162 */               directTrackType = SOMETHING;
/* 187:    */             } else {
/* 188:165 */               directTrackType = OBJECT;
/* 189:    */             }
/* 190:    */           }
/* 191:168 */           String indirectTrackType = null;
/* 192:    */           
/* 193:170 */           String indirectTrackFeatures = null;
/* 194:171 */           if (indirectTrack != null)
/* 195:    */           {
/* 196:172 */             indirectTrackType = indirectTrack.getEntityType();
/* 197:173 */             indirectTrackFeatures = indirectTrack.getFeatures();
/* 198:    */           }
/* 199:177 */           String subjectTrackId = label.getSubject();
/* 200:178 */           String directTrackId = label.getDirectObject();
/* 201:179 */           String indirectTrackId = label.getIndirectObject();
/* 202:180 */           Mark.say(new Object[] {Boolean.valueOf(debug), "IDs", subjectTrackId, directTrackId, indirectTrackId });
/* 203:    */           
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:187 */           Entity directThing = null;
/* 210:188 */           if (directTrackType != null) {
/* 211:189 */             directThing = new Entity(directTrackType);
/* 212:    */           }
/* 213:191 */           if (directTrackFeatures != null)
/* 214:    */           {
/* 215:192 */             String[] features = directTrackFeatures.split(" ");
/* 216:193 */             for (String feature : features) {
/* 217:194 */               directThing.addFeature(feature);
/* 218:    */             }
/* 219:    */           }
/* 220:198 */           Entity indirectThing = null;
/* 221:199 */           if (indirectTrackType != null) {
/* 222:200 */             indirectThing = new Entity(indirectTrackType);
/* 223:    */           }
/* 224:202 */           if (indirectTrackFeatures != null)
/* 225:    */           {
/* 226:203 */             String[] features = indirectTrackFeatures.split(" ");
/* 227:204 */             for (String feature : features) {
/* 228:205 */               indirectThing.addFeature(feature);
/* 229:    */             }
/* 230:    */           }
/* 231:209 */           Entity eventThing = Innerese.makeEvent(subjectTrackType, verb, directThing, indirectThing);
/* 232:    */           
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:219 */           String flattened = eventThing.hash();
/* 242:    */           
/* 243:221 */           String playByPlay = (String)map.get(flattened);
/* 244:223 */           if (playByPlay == null)
/* 245:    */           {
/* 246:224 */             playByPlay = Generator.getGenerator().playByPlay(eventThing, "present", false).trim();
/* 247:225 */             map.put(flattened, playByPlay);
/* 248:226 */             Mark.say(new Object[] {"Play by play statement", playByPlay });
/* 249:    */           }
/* 250:229 */           label.setValue(playByPlay);
/* 251:231 */           if (playByPlay != null) {
/* 252:232 */             signals.add(new BetterSignal(new Object[] { playByPlay, Integer.valueOf(start), Integer.valueOf(end), verb }));
/* 253:    */           }
/* 254:235 */           if ((useLimit) && (signals.size() > limit)) {
/* 255:236 */             return signals;
/* 256:    */           }
/* 257:    */         }
/* 258:    */       }
/* 259:262 */       trackIdx++;
/* 260:    */     }
/* 261:265 */     Mark.say(new Object[] {"Found", Integer.valueOf(trackIdx), "tracks" });
/* 262:    */     
/* 263:267 */     String result = co57annotation.toXMLString();
/* 264:268 */     String path = file.getPath();
/* 265:269 */     String destination = System.getProperty("user.home");
/* 266:270 */     destination = destination + File.separator + "Co57_MIT_Results" + File.separator + file.getName();
/* 267:271 */     Mark.say(new Object[] {"Want to write string of length", Integer.valueOf(result.length()) });
/* 268:272 */     Mark.say(new Object[] {"Source     ", path });
/* 269:273 */     Mark.say(new Object[] {"Destination", destination });
/* 270:    */     
/* 271:275 */     File destinationFile = new File(destination);
/* 272:276 */     destinationFile.getParentFile().mkdirs();
/* 273:    */     
/* 274:278 */     TextIO.writeStringToFile(result, destinationFile);
/* 275:    */     
/* 276:    */ 
/* 277:    */ 
/* 278:282 */     return signals;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public static void main(String[] ignore)
/* 282:    */     throws Throwable
/* 283:    */   {
/* 284:291 */     File co57file = new File(Annotation.class.getResource("co57-sample.xml").getFile());
/* 285:    */     
/* 286:293 */     extractRoles(co57file);
/* 287:    */   }
/* 288:    */   
/* 289:    */   private static String condition(String value)
/* 290:    */   {
/* 291:297 */     char p = value.charAt(value.length() - 1);
/* 292:298 */     boolean pruned = false;
/* 293:299 */     if (".:;?".indexOf(p) >= 0)
/* 294:    */     {
/* 295:300 */       pruned = true;
/* 296:301 */       value = value.substring(0, value.length() - 1);
/* 297:    */     }
/* 298:303 */     String[] words = value.split(" ");
/* 299:304 */     String result = "";
/* 300:305 */     for (String w : words)
/* 301:    */     {
/* 302:306 */       int l = w.length() - 1;
/* 303:307 */       char c = w.charAt(l);
/* 304:308 */       if (Character.isDigit(c)) {
/* 305:309 */         w = w.subSequence(0, l) + "-" + c;
/* 306:    */       }
/* 307:311 */       result = result + w + " ";
/* 308:    */     }
/* 309:313 */     result = result.trim();
/* 310:314 */     if (pruned) {
/* 311:315 */       result = result + p;
/* 312:    */     }
/* 313:317 */     Mark.say(new Object[] {"Conditioned:", result });
/* 314:318 */     return result;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public Annotation(String argName, int argWidth, int argHeight, int argFrameCount, Track[] argTracks)
/* 318:    */   {
/* 319:322 */     setName(argName);
/* 320:323 */     setWidth(argWidth);
/* 321:324 */     setHeight(argHeight);
/* 322:325 */     setFrameCount(argFrameCount);
/* 323:326 */     setTracks(argTracks);
/* 324:327 */     setAnnotationFile(null);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public Annotation(File argAnnotationFile)
/* 328:    */     throws AnnotationParseException, IOException, SAXException
/* 329:    */   {
/* 330:333 */     setAnnotationFile(argAnnotationFile);
/* 331:    */     
/* 332:335 */     Document annotationDocument = AnnotationUtils.parse(this.annotationFile);
/* 333:336 */     Element root = annotationDocument.getDocumentElement();
/* 334:    */     
/* 335:338 */     Element[] videoElements = AnnotationUtils.getTypedChildren(root, "video");
/* 336:339 */     if (videoElements.length != 1) {
/* 337:340 */       throw new AnnotationParseException("Wrong number of video elements");
/* 338:    */     }
/* 339:342 */     Element videoElement = videoElements[0];
/* 340:343 */     setName(videoElement.getAttribute("name"));
/* 341:344 */     String attWidth = videoElement.getAttribute("width");
/* 342:345 */     if (attWidth.equals("")) {
/* 343:346 */       setWidth(0);
/* 344:    */     } else {
/* 345:349 */       setWidth(Integer.parseInt(videoElement.getAttribute("width")));
/* 346:    */     }
/* 347:351 */     String attHeight = videoElement.getAttribute("height");
/* 348:352 */     if (attHeight.equals("")) {
/* 349:353 */       setHeight(0);
/* 350:    */     } else {
/* 351:356 */       setHeight(Integer.parseInt(videoElement.getAttribute("height")));
/* 352:    */     }
/* 353:358 */     String attFrameCount = videoElement.getAttribute("frameCount");
/* 354:359 */     if (attFrameCount.equals("")) {
/* 355:360 */       setFrameCount(0);
/* 356:    */     } else {
/* 357:363 */       setFrameCount(Integer.parseInt(videoElement.getAttribute("frameCount")));
/* 358:    */     }
/* 359:366 */     Element[] trackLists = AnnotationUtils.getTypedChildren(root, "trackList");
/* 360:367 */     if (trackLists.length != 1) {
/* 361:368 */       throw new AnnotationParseException("Wrong number of track lists");
/* 362:    */     }
/* 363:370 */     Element[] trackElements = AnnotationUtils.getTypedChildren(trackLists[0], "track");
/* 364:    */     
/* 365:372 */     Track[] newTracks = new Track[trackElements.length];
/* 366:373 */     for (int i = 0; i < trackElements.length; i++) {
/* 367:374 */       newTracks[i] = new Track(trackElements[i]);
/* 368:    */     }
/* 369:376 */     setTracks(newTracks);
/* 370:    */     
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:381 */     this.trackMap = new HashMap();
/* 375:382 */     for (Track t : getTracks()) {
/* 376:383 */       this.trackMap.put(t.getUid(), t);
/* 377:    */     }
/* 378:385 */     for (Track t : getTracks()) {
/* 379:386 */       for (Segment s : t.getSegments()) {
/* 380:387 */         for (Label l : s.getLabels())
/* 381:    */         {
/* 382:388 */           if (l.getDirectObject() != null) {
/* 383:389 */             l.setDirectObjectTrack((Track)this.trackMap.get(l.getDirectObject()));
/* 384:    */           }
/* 385:391 */           if (l.getIndirectObject() != null) {
/* 386:393 */             l.setIndirectObjectTrack((Track)this.trackMap.get(l.getIndirectObject()));
/* 387:    */           }
/* 388:    */         }
/* 389:    */       }
/* 390:    */     }
/* 391:    */   }
/* 392:    */   
/* 393:    */   public Element toElement(Document doc)
/* 394:    */   {
/* 395:401 */     Element out = doc.createElement("annotation");
/* 396:    */     
/* 397:403 */     Element video = doc.createElement("video");
/* 398:404 */     video.setAttribute("name", getName());
/* 399:405 */     video.setAttribute("width", Integer.toString(getWidth()));
/* 400:406 */     video.setAttribute("height", Integer.toString(getHeight()));
/* 401:407 */     video.setAttribute("frameCount", Integer.toString(getFrameCount()));
/* 402:408 */     out.appendChild(video);
/* 403:    */     
/* 404:410 */     out.appendChild(AnnotationUtils.makeListElement("trackList", getTracks(), doc));
/* 405:    */     
/* 406:412 */     return out;
/* 407:    */   }
/* 408:    */   
/* 409:    */   public Document toDocument()
/* 410:    */   {
/* 411:    */     try
/* 412:    */     {
/* 413:417 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 414:418 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 415:419 */       Document document = builder.newDocument();
/* 416:420 */       document.appendChild(toElement(document));
/* 417:421 */       return document;
/* 418:    */     }
/* 419:    */     catch (ParserConfigurationException e)
/* 420:    */     {
/* 421:424 */       if (!$assertionsDisabled) {
/* 422:424 */         throw new AssertionError();
/* 423:    */       }
/* 424:    */     }
/* 425:425 */     return null;
/* 426:    */   }
/* 427:    */   
/* 428:    */   public String toXMLString()
/* 429:    */   {
/* 430:430 */     Document document = toDocument();
/* 431:431 */     DOMImplementationLS domImplLS = (DOMImplementationLS)document.getImplementation();
/* 432:432 */     LSSerializer serializer = domImplLS.createLSSerializer();
/* 433:433 */     return serializer.writeToString(document.getDocumentElement());
/* 434:    */   }
/* 435:    */   
/* 436:    */   public final File getAnnotationFile()
/* 437:    */   {
/* 438:442 */     return this.annotationFile;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public final void setAnnotationFile(File argAnnotationFile)
/* 442:    */   {
/* 443:452 */     this.annotationFile = argAnnotationFile;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public final Track[] getTracks()
/* 447:    */   {
/* 448:461 */     return this.tracks;
/* 449:    */   }
/* 450:    */   
/* 451:    */   public final void setTracks(Track[] argTracks)
/* 452:    */   {
/* 453:471 */     this.tracks = argTracks;
/* 454:    */   }
/* 455:    */   
/* 456:    */   public final String getName()
/* 457:    */   {
/* 458:480 */     return this.name;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public final void setName(String argName)
/* 462:    */   {
/* 463:490 */     this.name = argName;
/* 464:    */   }
/* 465:    */   
/* 466:    */   public final int getWidth()
/* 467:    */   {
/* 468:499 */     return this.width;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public final void setWidth(int argWidth)
/* 472:    */   {
/* 473:509 */     this.width = argWidth;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public final int getHeight()
/* 477:    */   {
/* 478:518 */     return this.height;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public final void setHeight(int argHeight)
/* 482:    */   {
/* 483:528 */     this.height = argHeight;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public final int getFrameCount()
/* 487:    */   {
/* 488:537 */     return this.frameCount;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public final void setFrameCount(int argFrameCount)
/* 492:    */   {
/* 493:547 */     this.frameCount = argFrameCount;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public String toString()
/* 497:    */   {
/* 498:554 */     int sbSize = 1000;
/* 499:555 */     String variableSeparator = "  ";
/* 500:556 */     StringBuffer sb = new StringBuffer(1000);
/* 501:    */     
/* 502:558 */     sb.append("annotationFile=").append(this.annotationFile);
/* 503:559 */     sb.append("  ");
/* 504:560 */     sb.append("tracks=").append(this.tracks);
/* 505:561 */     sb.append("  ");
/* 506:562 */     sb.append("name=").append(this.name);
/* 507:563 */     sb.append("  ");
/* 508:564 */     sb.append("width=").append(this.width);
/* 509:565 */     sb.append("  ");
/* 510:566 */     sb.append("height=").append(this.height);
/* 511:567 */     sb.append("  ");
/* 512:568 */     sb.append("frameCount=").append(this.frameCount);
/* 513:    */     
/* 514:570 */     return sb.toString();
/* 515:    */   }
/* 516:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     michaelBehr.Annotation
 * JD-Core Version:    0.7.0.1
 */