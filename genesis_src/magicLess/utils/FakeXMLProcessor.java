/*   1:    */ package magicLess.utils;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.FileReader;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.io.Reader;
/*   8:    */ import java.io.StringReader;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.regex.Matcher;
/*  11:    */ import java.util.regex.Pattern;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public abstract class FakeXMLProcessor
/*  15:    */ {
/*  16: 19 */   public static String XML_MUMBO_JUMBO = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n";
/*  17: 21 */   private int lineCounter = 0;
/*  18: 22 */   private String fileN = "";
/*  19:    */   
/*  20:    */   public int getLineNumber()
/*  21:    */   {
/*  22: 24 */     return this.lineCounter;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void setLineCounter(int lc)
/*  26:    */   {
/*  27: 27 */     this.lineCounter = lc;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getFileName()
/*  31:    */   {
/*  32: 30 */     return this.fileN;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Object processFile(String fileName)
/*  36:    */   {
/*  37: 34 */     this.fileN = fileName;
/*  38:    */     try
/*  39:    */     {
/*  40: 36 */       BufferedReader in = new BufferedReader(new FileReader(fileName));
/*  41:    */       
/*  42: 38 */       this.lineCounter = 0;
/*  43: 39 */       Object[] res = processNextChunk(in);
/*  44: 40 */       if (res != null) {
/*  45: 40 */         return res[1];
/*  46:    */       }
/*  47: 41 */       return null;
/*  48:    */     }
/*  49:    */     catch (IOException f)
/*  50:    */     {
/*  51: 43 */       Mark.say(new Object[] {"Caught IOException while processing " + fileName + "." });
/*  52:    */     }
/*  53: 46 */     return null;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Object processReader(Reader in, int lineC)
/*  57:    */   {
/*  58: 50 */     this.fileN = "input-stream";
/*  59:    */     try
/*  60:    */     {
/*  61: 52 */       this.lineCounter = lineC;
/*  62: 53 */       Object[] res = processNextChunk(in);
/*  63: 54 */       if (res != null) {
/*  64: 54 */         return res[1];
/*  65:    */       }
/*  66: 55 */       return null;
/*  67:    */     }
/*  68:    */     catch (IOException f)
/*  69:    */     {
/*  70: 57 */       Mark.say(new Object[] {"Caught IOException while processing " + this.fileN + "." });
/*  71: 58 */       Mark.say(new Object[] {f });
/*  72:    */     }
/*  73: 60 */     return null;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Object processReader(Reader in)
/*  77:    */   {
/*  78: 64 */     return processReader(in, 0);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public Object processString(String s)
/*  82:    */   {
/*  83: 68 */     return processReader(new StringReader(s), 0);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public Object processString(String s, int line)
/*  87:    */   {
/*  88: 71 */     return processReader(new StringReader(s), line);
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected Object[] processNextChunk(Reader in)
/*  92:    */     throws IOException
/*  93:    */   {
/*  94: 82 */     if (ready(in))
/*  95:    */     {
/*  96: 83 */       String tagLine = getFirstTag(in);
/*  97: 84 */       if (tagLine == null) {
/*  98: 84 */         return null;
/*  99:    */       }
/* 100: 85 */       String meat = getUntilClose(in, tagLine);
/* 101: 86 */       this.lineCounter -= numLines(meat);
/* 102: 87 */       Reader sr = new BufferedReader(new StringReader(meat));
/* 103:    */       
/* 104:    */ 
/* 105: 90 */       Object soup = dispatch(sr, tagLine);
/* 106: 91 */       if (soup == null) {
/* 107: 91 */         return null;
/* 108:    */       }
/* 109: 92 */       return new Object[] { tagLine, soup };
/* 110:    */     }
/* 111: 93 */     return null;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private int numLines(String s)
/* 115:    */   {
/* 116: 97 */     int j = 0;
/* 117: 98 */     for (int i = 0; i < s.length(); i++) {
/* 118: 99 */       if (s.charAt(i) == '\n') {
/* 119: 99 */         j++;
/* 120:    */       }
/* 121:    */     }
/* 122:101 */     return j;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private String getUntilClose(Reader in, String tagLine)
/* 126:    */     throws IOException
/* 127:    */   {
/* 128:105 */     HashMap<String, Integer> tagCounts = new HashMap();
/* 129:106 */     tagCounts.put(tagLine, Integer.valueOf(1));
/* 130:107 */     String chunk = "";
/* 131:108 */     String top = "";
/* 132:109 */     while (ready(in))
/* 133:    */     {
/* 134:110 */       String readc = readChar(in);
/* 135:111 */       if (readc == null) {
/* 136:    */         break;
/* 137:    */       }
/* 138:112 */       top = top + readc;
/* 139:113 */       if (isOpenComment(top))
/* 140:    */       {
/* 141:114 */         top = "";
/* 142:115 */         eatXMLComment(in);
/* 143:    */       }
/* 144:117 */       else if (isXMLVoodoo(top))
/* 145:    */       {
/* 146:118 */         top = "";
/* 147:119 */         eatXMLVoodoo(in);
/* 148:    */       }
/* 149:121 */       if (isOpenTag(top))
/* 150:    */       {
/* 151:122 */         if (tagCounts.containsKey(getTagLabel(top))) {
/* 152:123 */           tagCounts.put(getTagLabel(top), Integer.valueOf(((Integer)tagCounts.get(getTagLabel(top))).intValue() + 1));
/* 153:    */         } else {
/* 154:125 */           tagCounts.put(getTagLabel(top), Integer.valueOf(1));
/* 155:    */         }
/* 156:127 */         chunk = chunk + top;
/* 157:128 */         top = "";
/* 158:    */       }
/* 159:129 */       else if (isCloseTag(top))
/* 160:    */       {
/* 161:130 */         if (tagCounts.containsKey(getTagLabel(top))) {
/* 162:131 */           tagCounts.put(getTagLabel(top), Integer.valueOf(((Integer)tagCounts.get(getTagLabel(top))).intValue() - 1));
/* 163:    */         } else {
/* 164:133 */           tagCounts.put(getTagLabel(top), Integer.valueOf(-1));
/* 165:    */         }
/* 166:135 */         if (isZeroed(tagCounts)) {
/* 167:136 */           return chunk;
/* 168:    */         }
/* 169:138 */         chunk = chunk + top;
/* 170:139 */         top = "";
/* 171:    */       }
/* 172:141 */       else if (!isValidPartialTag(top))
/* 173:    */       {
/* 174:142 */         chunk = chunk + top;
/* 175:143 */         top = "";
/* 176:    */       }
/* 177:145 */       if (!isConsistent(tagCounts)) {
/* 178:146 */         Mark.say(new Object[] {"probably bad markup syntax near line " + this.lineCounter + " in file " + this.fileN });
/* 179:    */       }
/* 180:    */     }
/* 181:149 */     Mark.say(new Object[] {"no end tag near line " + this.lineCounter + " in file " + this.fileN });
/* 182:150 */     return chunk;
/* 183:    */   }
/* 184:    */   
/* 185:    */   private boolean isConsistent(HashMap<String, Integer> h)
/* 186:    */   {
/* 187:153 */     for (Integer i : h.values()) {
/* 188:154 */       if (i.intValue() < 0) {
/* 189:154 */         return false;
/* 190:    */       }
/* 191:    */     }
/* 192:156 */     return true;
/* 193:    */   }
/* 194:    */   
/* 195:    */   private boolean isZeroed(HashMap<String, Integer> h)
/* 196:    */   {
/* 197:159 */     for (Integer i : h.values()) {
/* 198:160 */       if (i.intValue() != 0) {
/* 199:161 */         return false;
/* 200:    */       }
/* 201:    */     }
/* 202:164 */     return true;
/* 203:    */   }
/* 204:    */   
/* 205:    */   protected abstract Object dispatch(Reader paramReader, String paramString)
/* 206:    */     throws IOException;
/* 207:    */   
/* 208:    */   private String getFirstTag(Reader in)
/* 209:    */     throws IOException
/* 210:    */   {
/* 211:184 */     String partial = "";
/* 212:185 */     while (ready(in))
/* 213:    */     {
/* 214:186 */       String readc = readChar(in);
/* 215:187 */       if (readc == null) {
/* 216:    */         break;
/* 217:    */       }
/* 218:188 */       partial = partial + readc;
/* 219:189 */       if (isOpenComment(partial))
/* 220:    */       {
/* 221:190 */         partial = "";
/* 222:191 */         eatXMLComment(in);
/* 223:    */       }
/* 224:193 */       else if (isXMLVoodoo(partial))
/* 225:    */       {
/* 226:194 */         partial = "";
/* 227:195 */         eatXMLVoodoo(in);
/* 228:    */       }
/* 229:    */       else
/* 230:    */       {
/* 231:197 */         if (isOpenTag(partial)) {
/* 232:198 */           return getTagLabel(partial);
/* 233:    */         }
/* 234:200 */         if (isCloseTag(partial))
/* 235:    */         {
/* 236:201 */           Mark.say(new Object[] {"unexpected close-tag near line " + this.lineCounter + " in file " + this.fileN });
/* 237:202 */           throw new IOException();
/* 238:    */         }
/* 239:203 */         if (!isValidPartialTag(partial)) {
/* 240:204 */           partial = "";
/* 241:    */         }
/* 242:    */       }
/* 243:    */     }
/* 244:207 */     return null;
/* 245:    */   }
/* 246:    */   
/* 247:    */   protected String readChar(Reader in)
/* 248:    */     throws IOException
/* 249:    */   {
/* 250:219 */     int c = in.read();
/* 251:220 */     if (c == 10) {
/* 252:221 */       this.lineCounter += 1;
/* 253:    */     }
/* 254:223 */     return c == -1 ? null : String.valueOf((char)c);
/* 255:    */   }
/* 256:    */   
/* 257:    */   protected String getString(Reader in)
/* 258:    */     throws IOException
/* 259:    */   {
/* 260:227 */     String s = "";
/* 261:228 */     while (ready(in))
/* 262:    */     {
/* 263:229 */       String readc = readChar(in);
/* 264:230 */       if (readc == null) {
/* 265:    */         break;
/* 266:    */       }
/* 267:231 */       s = s + readc;
/* 268:    */     }
/* 269:233 */     return unescape(s);
/* 270:    */   }
/* 271:    */   
/* 272:    */   private boolean isOpenTag(String input)
/* 273:    */   {
/* 274:237 */     input = input.trim();
/* 275:238 */     return input.matches("<[^/]*>");
/* 276:    */   }
/* 277:    */   
/* 278:    */   private boolean isCloseTag(String input)
/* 279:    */   {
/* 280:241 */     input = input.trim();
/* 281:242 */     return input.matches("</.*>");
/* 282:    */   }
/* 283:    */   
/* 284:    */   private boolean isXMLVoodoo(String input)
/* 285:    */   {
/* 286:245 */     input = input.trim();
/* 287:246 */     return input.matches("<\\?");
/* 288:    */   }
/* 289:    */   
/* 290:    */   private boolean isOpenComment(String input)
/* 291:    */   {
/* 292:249 */     input = input.trim();
/* 293:250 */     return input.matches("<!--");
/* 294:    */   }
/* 295:    */   
/* 296:    */   private boolean isValidPartialCloseXMLVoodoo(String input)
/* 297:    */   {
/* 298:253 */     input = input.trim();
/* 299:254 */     return input.matches("\\?");
/* 300:    */   }
/* 301:    */   
/* 302:    */   private boolean isValidPartialCloseXMLComment(String input)
/* 303:    */   {
/* 304:257 */     input = input.trim();
/* 305:258 */     return (input.equals("-")) || (input.equals("--"));
/* 306:    */   }
/* 307:    */   
/* 308:    */   private boolean isCloseXMLVoodoo(String input)
/* 309:    */   {
/* 310:261 */     input = input.trim();
/* 311:262 */     return input.equals("?>");
/* 312:    */   }
/* 313:    */   
/* 314:    */   private boolean isCloseXMLComment(String input)
/* 315:    */   {
/* 316:265 */     input = input.trim();
/* 317:266 */     return input.equals("-->");
/* 318:    */   }
/* 319:    */   
/* 320:    */   private boolean isValidPartialTag(String input)
/* 321:    */   {
/* 322:269 */     input = input.trim();
/* 323:270 */     return input.matches("<[^<]*");
/* 324:    */   }
/* 325:    */   
/* 326:    */   private void eatXMLVoodoo(Reader in)
/* 327:    */     throws IOException
/* 328:    */   {
/* 329:273 */     String partial = "";
/* 330:274 */     while (ready(in))
/* 331:    */     {
/* 332:275 */       String readc = readChar(in);
/* 333:276 */       if (readc == null) {
/* 334:    */         break;
/* 335:    */       }
/* 336:277 */       partial = partial + readc;
/* 337:278 */       if (isOpenComment(partial))
/* 338:    */       {
/* 339:279 */         Mark.say(new Object[] {"malformed XML? (xml comment inside xml voodoo near line " + this.lineCounter + " of file " + this.fileN });
/* 340:280 */         eatXMLComment(in);
/* 341:281 */         partial = "";
/* 342:    */       }
/* 343:283 */       else if (isXMLVoodoo(partial))
/* 344:    */       {
/* 345:284 */         Mark.say(new Object[] {"malformed XML? (nested xml voodoo near line " + this.lineCounter + " of file " + this.fileN });
/* 346:285 */         eatXMLVoodoo(in);
/* 347:286 */         partial = "";
/* 348:    */       }
/* 349:    */       else
/* 350:    */       {
/* 351:288 */         if (isCloseXMLVoodoo(partial)) {
/* 352:289 */           return;
/* 353:    */         }
/* 354:291 */         if (!isValidPartialCloseXMLVoodoo(partial)) {
/* 355:292 */           partial = "";
/* 356:    */         }
/* 357:    */       }
/* 358:    */     }
/* 359:296 */     throw new IOException("couldn't swallow the folowing XML Voodoo: <?" + partial);
/* 360:    */   }
/* 361:    */   
/* 362:    */   private void eatXMLComment(Reader in)
/* 363:    */     throws IOException
/* 364:    */   {
/* 365:299 */     String partial = "";
/* 366:300 */     while (ready(in))
/* 367:    */     {
/* 368:301 */       String readc = readChar(in);
/* 369:302 */       if (readc == null) {
/* 370:    */         break;
/* 371:    */       }
/* 372:303 */       partial = partial + readc;
/* 373:304 */       if (isOpenComment(partial))
/* 374:    */       {
/* 375:305 */         Mark.say(new Object[] {"malformed XML? (xml comment inside xml comment near line " + this.lineCounter + " of file " + this.fileN });
/* 376:306 */         eatXMLComment(in);
/* 377:307 */         partial = "";
/* 378:    */       }
/* 379:309 */       else if (isXMLVoodoo(partial))
/* 380:    */       {
/* 381:310 */         Mark.say(new Object[] {"malformed XML? (nested xml voodoo near line " + this.lineCounter + " of file " + this.fileN });
/* 382:311 */         eatXMLVoodoo(in);
/* 383:312 */         partial = "";
/* 384:    */       }
/* 385:    */       else
/* 386:    */       {
/* 387:314 */         if (isCloseXMLComment(partial)) {
/* 388:315 */           return;
/* 389:    */         }
/* 390:317 */         if (!isValidPartialCloseXMLComment(partial)) {
/* 391:318 */           partial = "";
/* 392:    */         }
/* 393:    */       }
/* 394:    */     }
/* 395:322 */     throw new IOException("couldn't swallow the folowing XML comment: <!--" + partial);
/* 396:    */   }
/* 397:    */   
/* 398:    */   private String getTagLabel(String tag)
/* 399:    */   {
/* 400:325 */     tag = tag.trim();
/* 401:326 */     if (isCloseTag(tag)) {
/* 402:327 */       return tag.substring(2, tag.length() - 1).trim();
/* 403:    */     }
/* 404:329 */     if (isOpenTag(tag)) {
/* 405:330 */       return tag.substring(1, tag.length() - 1).trim();
/* 406:    */     }
/* 407:332 */     Mark.err(new Object[] {"getTagLabel called on non-tag" });
/* 408:333 */     return null;
/* 409:    */   }
/* 410:    */   
/* 411:    */   protected boolean ready(Reader in)
/* 412:    */     throws IOException
/* 413:    */   {
/* 414:346 */     in.mark(2);
/* 415:347 */     int c = in.read();
/* 416:348 */     if (c == -1) {
/* 417:348 */       return false;
/* 418:    */     }
/* 419:350 */     in.reset();
/* 420:351 */     return true;
/* 421:    */   }
/* 422:    */   
/* 423:354 */   private static Pattern escaper = Pattern.compile("([^a-zA-z0-9])");
/* 424:    */   
/* 425:    */   private static String escapeRE(String str)
/* 426:    */   {
/* 427:356 */     String res = escaper.matcher(str).replaceAll("\\\\$1");
/* 428:357 */     if (res.equals("\\")) {
/* 429:358 */       res = "\\\\";
/* 430:    */     }
/* 431:360 */     return res;
/* 432:    */   }
/* 433:    */   
/* 434:    */   public static String escape(String s)
/* 435:    */   {
/* 436:364 */     s = s.replaceAll("\\&", "&amp;");
/* 437:365 */     s = s.replaceAll(">", "&gt;");
/* 438:366 */     s = s.replaceAll("<", "&lt;");
/* 439:367 */     s = s.replaceAll("\"", "&quot;");
/* 440:368 */     s = s.replaceAll("'", "&apos;");
/* 441:369 */     return s;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public static String unescape(String s)
/* 445:    */   {
/* 446:372 */     s = s.replaceAll("\\&amp;", "&");
/* 447:373 */     s = s.replaceAll("\\&gt;", ">");
/* 448:374 */     s = s.replaceAll("\\&lt;", "<");
/* 449:375 */     s = s.replaceAll("\\&quot;", "\"");
/* 450:376 */     s = s.replaceAll("\\&apos;", "'");
/* 451:377 */     return s;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public static String escape(Object s)
/* 455:    */   {
/* 456:381 */     return escape(s.toString());
/* 457:    */   }
/* 458:    */   
/* 459:    */   public static void main(String[] args)
/* 460:    */   {
/* 461:384 */     System.out.println(escape("<\"TAG!\" you're it!>"));
/* 462:385 */     System.out.println(unescape(escape("<\"TAG!\" you're it!>")));
/* 463:    */   }
/* 464:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.utils.FakeXMLProcessor
 * JD-Core Version:    0.7.0.1
 */