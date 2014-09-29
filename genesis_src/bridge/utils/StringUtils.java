/*   1:    */ package bridge.utils;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.text.DecimalFormat;
/*   8:    */ import java.util.Arrays;
/*   9:    */ import java.util.Collection;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Map.Entry;
/*  14:    */ import java.util.Set;
/*  15:    */ import java.util.StringTokenizer;
/*  16:    */ 
/*  17:    */ public class StringUtils
/*  18:    */ {
/*  19:    */   public static String prettyPrint(Object o)
/*  20:    */   {
/*  21: 27 */     if ((o instanceof Set))
/*  22:    */     {
/*  23: 28 */       Set s = (Set)o;
/*  24: 29 */       boolean firstEntry = true;
/*  25: 30 */       String accum = "[ ";
/*  26: 31 */       Iterator iEntries = s.iterator();
/*  27: 32 */       while (iEntries.hasNext())
/*  28:    */       {
/*  29: 33 */         Object element = iEntries.next();
/*  30: 34 */         String elString = prettyPrint(element);
/*  31: 35 */         if (firstEntry)
/*  32:    */         {
/*  33: 36 */           accum = accum + indent(elString, 2, 0) + ",\n";
/*  34: 37 */           firstEntry = false;
/*  35:    */         }
/*  36:    */         else
/*  37:    */         {
/*  38: 39 */           accum = accum + indent(elString, 2) + ",\n";
/*  39:    */         }
/*  40:    */       }
/*  41: 42 */       accum = accum + "]";
/*  42: 43 */       return accum;
/*  43:    */     }
/*  44: 44 */     if ((o instanceof Map))
/*  45:    */     {
/*  46: 45 */       Map m = (Map)o;
/*  47: 46 */       String accum = "[ ";
/*  48: 47 */       boolean firstEntry = true;
/*  49: 48 */       Iterator iEntries = m.entrySet().iterator();
/*  50: 49 */       while (iEntries.hasNext())
/*  51:    */       {
/*  52: 50 */         Map.Entry entry = (Map.Entry)iEntries.next();
/*  53: 51 */         String keyString = prettyPrint(entry.getKey());
/*  54: 52 */         String elString = indentWithHeader(prettyPrint(entry.getValue()), keyString + " => ");
/*  55: 53 */         if (firstEntry)
/*  56:    */         {
/*  57: 54 */           accum = accum + indent(elString, 2, 0) + "\n";
/*  58: 55 */           firstEntry = false;
/*  59:    */         }
/*  60:    */         else
/*  61:    */         {
/*  62: 57 */           accum = accum + indent(elString, 2) + "\n";
/*  63:    */         }
/*  64:    */       }
/*  65: 60 */       accum = accum + "]";
/*  66: 61 */       return accum;
/*  67:    */     }
/*  68: 62 */     if ((o instanceof List))
/*  69:    */     {
/*  70: 63 */       List l = (List)o;
/*  71: 64 */       String accum = "[ ";
/*  72: 65 */       boolean firstEntry = true;
/*  73: 66 */       Iterator iL = l.iterator();
/*  74: 67 */       while (iL.hasNext())
/*  75:    */       {
/*  76: 68 */         Object item = iL.next();
/*  77: 69 */         String elString = prettyPrint(item);
/*  78: 70 */         if (firstEntry)
/*  79:    */         {
/*  80: 71 */           accum = accum + indent(elString, 2, 0) + "\n";
/*  81: 72 */           firstEntry = false;
/*  82:    */         }
/*  83:    */         else
/*  84:    */         {
/*  85: 74 */           accum = accum + indent(elString, 2) + "\n";
/*  86:    */         }
/*  87:    */       }
/*  88: 77 */       accum = accum + "]";
/*  89: 78 */       return accum;
/*  90:    */     }
/*  91: 80 */     if ((o instanceof Pair))
/*  92:    */     {
/*  93: 81 */       Pair p = (Pair)o;
/*  94: 82 */       String accum = "(";
/*  95: 83 */       accum = accum + indent(prettyPrint(p.car()), 2, 1) + ",\n";
/*  96: 84 */       accum = accum + indent(prettyPrint(p.cdr()), 2) + "\n";
/*  97: 85 */       accum = accum + ")";
/*  98: 86 */       return accum;
/*  99:    */     }
/* 100: 87 */     if ((o instanceof int[]))
/* 101:    */     {
/* 102: 88 */       int[] a = (int[])o;
/* 103: 89 */       StringBuffer buffer = new StringBuffer();
/* 104: 90 */       buffer.append("[");
/* 105: 91 */       for (int i = 0; i < a.length; i++)
/* 106:    */       {
/* 107: 92 */         buffer.append(a[i]);
/* 108: 93 */         if (i < a.length - 1) {
/* 109: 93 */           buffer.append(",");
/* 110:    */         }
/* 111:    */       }
/* 112: 95 */       buffer.append("]");
/* 113: 96 */       return buffer.toString();
/* 114:    */     }
/* 115: 98 */     return o.toString();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static String indent(String toIndent, int indentSize)
/* 119:    */   {
/* 120:114 */     String indentString = repeat(" ", indentSize);
/* 121:115 */     return indent(toIndent, indentString, indentString, "", true, false);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public static String repeat(String toRepeat, int repCount)
/* 125:    */   {
/* 126:119 */     String accum = "";
/* 127:120 */     for (int i = 0; i < repCount; i++) {
/* 128:121 */       accum = accum + toRepeat;
/* 129:    */     }
/* 130:123 */     return accum;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public static String indentWithHeader(String toIndent, String header)
/* 134:    */   {
/* 135:127 */     int indentSize = tailLength(header);
/* 136:128 */     return header + indent(toIndent, indentSize, 0);
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static String indent(String toIndent, int indentSize, int firstLineIndentSize)
/* 140:    */   {
/* 141:144 */     String indentString = repeat(" ", indentSize);
/* 142:145 */     String firstLineIndentString = repeat(" ", firstLineIndentSize);
/* 143:146 */     return indent(toIndent, indentString, firstLineIndentString, "", true, false);
/* 144:    */   }
/* 145:    */   
/* 146:    */   private static String indentHelper(String whitespace, String text, String indentString, String eolIndent, boolean afterLeadingWhitespace, boolean indentBlankLines)
/* 147:    */   {
/* 148:151 */     if ((!indentBlankLines) && (text.equals(""))) {
/* 149:152 */       return whitespace + text;
/* 150:    */     }
/* 151:155 */     if (afterLeadingWhitespace) {
/* 152:156 */       return whitespace + indentString + text + eolIndent;
/* 153:    */     }
/* 154:158 */     return indentString + whitespace + text + eolIndent;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static String indent(String toIndent, String indentString, String firstLineIndentString, String eolIndent, boolean afterLeadingWhitespace, boolean indentBlankLines)
/* 158:    */   {
/* 159:179 */     String indented = "";
/* 160:180 */     StringTokenizer st = new StringTokenizer(toIndent, "\n\r\t ", true);
/* 161:    */     
/* 162:182 */     String leadingWhitespace = "";
/* 163:183 */     String text = "";
/* 164:184 */     String useIndentString = firstLineIndentString;
/* 165:186 */     while (st.hasMoreTokens())
/* 166:    */     {
/* 167:187 */       String token = st.nextToken();
/* 168:189 */       if ((token.equals("\n")) || (token.equals("\r")))
/* 169:    */       {
/* 170:191 */         indented = 
/* 171:    */         
/* 172:    */ 
/* 173:194 */           indented + indentHelper(leadingWhitespace, text, useIndentString, eolIndent, afterLeadingWhitespace, indentBlankLines) + token;
/* 174:195 */         leadingWhitespace = "";
/* 175:196 */         text = "";
/* 176:197 */         useIndentString = indentString;
/* 177:    */       }
/* 178:198 */       else if ((token.equals("\t")) || (token.equals(" ")))
/* 179:    */       {
/* 180:200 */         if (text.equals("")) {
/* 181:201 */           leadingWhitespace = leadingWhitespace + token;
/* 182:    */         } else {
/* 183:203 */           text = text + token;
/* 184:    */         }
/* 185:    */       }
/* 186:    */       else
/* 187:    */       {
/* 188:207 */         text = text + token;
/* 189:    */       }
/* 190:    */     }
/* 191:212 */     if ((!leadingWhitespace.equals("")) || (!text.equals(""))) {
/* 192:213 */       indented = 
/* 193:    */       
/* 194:215 */         indented + indentHelper(leadingWhitespace, text, useIndentString, eolIndent, afterLeadingWhitespace, indentBlankLines);
/* 195:    */     }
/* 196:218 */     return indented;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static List getLines(String toBeSplit)
/* 200:    */   {
/* 201:226 */     String[] lines = toBeSplit.split("\n\r|\n|\r");
/* 202:227 */     List results = Arrays.asList(lines);
/* 203:228 */     return results;
/* 204:    */   }
/* 205:    */   
/* 206:231 */   protected static DecimalFormat twoDigitFormatter = null;
/* 207:    */   
/* 208:    */   public static String formatDurationMilliseconds(long milliseconds)
/* 209:    */   {
/* 210:233 */     if (twoDigitFormatter == null) {
/* 211:233 */       twoDigitFormatter = new DecimalFormat("00");
/* 212:    */     }
/* 213:234 */     String sign = "";
/* 214:235 */     if (milliseconds < 0L)
/* 215:    */     {
/* 216:236 */       sign = "-";
/* 217:237 */       milliseconds = -milliseconds;
/* 218:    */     }
/* 219:240 */     if (milliseconds < 1000L) {
/* 220:241 */       return sign + milliseconds + " ms";
/* 221:    */     }
/* 222:244 */     if (milliseconds < 10000L) {
/* 223:245 */       return sign + milliseconds / 1000.0D + " s";
/* 224:    */     }
/* 225:248 */     long seconds = milliseconds / 1000L;
/* 226:249 */     if (seconds < 60L) {
/* 227:250 */       return sign + milliseconds / 1000L + " s";
/* 228:    */     }
/* 229:253 */     long minutes = seconds / 60L;
/* 230:254 */     long secRemain = seconds - minutes * 60L;
/* 231:255 */     long hours = minutes / 60L;
/* 232:256 */     long minuteRemain = minutes - hours * 60L;
/* 233:257 */     long days = hours / 24L;
/* 234:258 */     long hoursRemain = hours - 24L * days;
/* 235:    */     
/* 236:260 */     String daysString = "";
/* 237:261 */     if (days > 0L) {
/* 238:262 */       daysString = days + " days ";
/* 239:    */     }
/* 240:264 */     return 
/* 241:    */     
/* 242:    */ 
/* 243:267 */       daysString + twoDigitFormatter.format(hoursRemain) + ":" + twoDigitFormatter.format(minuteRemain) + ":" + twoDigitFormatter.format(secRemain);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public static void main(String[] args)
/* 247:    */   {
/* 248:271 */     System.out.println(getLines("Hello.  How\n are\n\ryou \ron\r\nthis \n\n\r\nfine day?"));
/* 249:    */   }
/* 250:    */   
/* 251:    */   public static int tailLength(String s)
/* 252:    */   {
/* 253:280 */     List lines = getLines(s);
/* 254:281 */     String line = (String)lines.get(lines.size() - 1);
/* 255:282 */     return line.length();
/* 256:    */   }
/* 257:    */   
/* 258:    */   public static String join(Object[] toJoin, String glue)
/* 259:    */   {
/* 260:287 */     return join(Arrays.asList(toJoin), glue);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static String join(double[] toJoin, String glue)
/* 264:    */   {
/* 265:291 */     Double[] doubles = new Double[toJoin.length];
/* 266:292 */     for (int i = 0; i < toJoin.length; i++) {
/* 267:293 */       doubles[i] = new Double(toJoin[i]);
/* 268:    */     }
/* 269:295 */     return join(doubles, glue);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public static String join(Collection toJoin, String glue)
/* 273:    */   {
/* 274:305 */     StringBuffer accum = new StringBuffer();
/* 275:306 */     Iterator iToJoin = toJoin.iterator();
/* 276:308 */     while (iToJoin.hasNext())
/* 277:    */     {
/* 278:309 */       Object joinee = iToJoin.next();
/* 279:310 */       accum.append(joinee.toString());
/* 280:311 */       if (iToJoin.hasNext()) {
/* 281:311 */         accum.append(glue);
/* 282:    */       }
/* 283:    */     }
/* 284:313 */     return accum.toString();
/* 285:    */   }
/* 286:    */   
/* 287:    */   public static String table(String[][] table, String justification, int firstDataRow, int firstDataColumn)
/* 288:    */   {
/* 289:317 */     StringBuffer result = new StringBuffer();
/* 290:    */     
/* 291:    */ 
/* 292:320 */     int nRows = table.length;
/* 293:    */     int nColumns;
/* 294:    */     int nColumns;
/* 295:322 */     if (nRows == 0) {
/* 296:323 */       nColumns = 0;
/* 297:    */     } else {
/* 298:325 */       nColumns = table[0].length;
/* 299:    */     }
/* 300:328 */     assert (nColumns <= justification.length()) : "Not enough justification information provided";
/* 301:    */     
/* 302:    */ 
/* 303:331 */     int[] columnWidths = new int[nColumns];
/* 304:332 */     for (int iColumn = 0; iColumn < nColumns; iColumn++)
/* 305:    */     {
/* 306:333 */       columnWidths[iColumn] = 0;
/* 307:334 */       for (int iRow = 0; iRow < nRows; iRow++)
/* 308:    */       {
/* 309:335 */         String cell = table[iRow][iColumn];
/* 310:336 */         columnWidths[iColumn] = Math.max(cell.length(), columnWidths[iColumn]);
/* 311:    */       }
/* 312:    */     }
/* 313:340 */     for (int iRow = 0; iRow <= nRows; iRow++)
/* 314:    */     {
/* 315:343 */       if (iRow == firstDataRow)
/* 316:    */       {
/* 317:344 */         for (int iColumn = 0; iColumn <= nColumns; iColumn++)
/* 318:    */         {
/* 319:345 */           if (iColumn == firstDataColumn) {
/* 320:346 */             result.append("+-");
/* 321:    */           }
/* 322:349 */           if (iColumn != nColumns) {
/* 323:350 */             result.append(repeat("-", columnWidths[iColumn] + 1));
/* 324:    */           }
/* 325:    */         }
/* 326:352 */         result.append("\n");
/* 327:    */       }
/* 328:354 */       if (iRow != nRows)
/* 329:    */       {
/* 330:357 */         for (int iColumn = 0; iColumn <= nColumns; iColumn++)
/* 331:    */         {
/* 332:360 */           if (iColumn == firstDataColumn) {
/* 333:361 */             result.append("| ");
/* 334:    */           }
/* 335:363 */           if (iColumn != nColumns)
/* 336:    */           {
/* 337:366 */             String cell = table[iRow][iColumn];
/* 338:367 */             int underfill = columnWidths[iColumn] - cell.length();
/* 339:    */             
/* 340:369 */             char justify = justification.charAt(iColumn);
/* 341:371 */             if (justify == 'r')
/* 342:    */             {
/* 343:372 */               result.append(repeat(" ", underfill));
/* 344:373 */               result.append(cell);
/* 345:    */             }
/* 346:374 */             else if (justify == 'c')
/* 347:    */             {
/* 348:375 */               int half = underfill / 2;
/* 349:376 */               result.append(repeat(" ", half));
/* 350:377 */               result.append(cell);
/* 351:378 */               result.append(repeat(" ", underfill - half));
/* 352:    */             }
/* 353:379 */             else if (justify == 'l')
/* 354:    */             {
/* 355:380 */               result.append(cell);
/* 356:381 */               result.append(repeat(" ", underfill));
/* 357:    */             }
/* 358:383 */             result.append(" ");
/* 359:    */           }
/* 360:    */         }
/* 361:385 */         result.append("\n");
/* 362:    */       }
/* 363:    */     }
/* 364:388 */     return result.toString();
/* 365:    */   }
/* 366:    */   
/* 367:    */   public static boolean testType(String s, String[] a)
/* 368:    */   {
/* 369:396 */     for (int i = 0; i < a.length; i++) {
/* 370:397 */       if (s.equalsIgnoreCase(a[i])) {
/* 371:398 */         return true;
/* 372:    */       }
/* 373:    */     }
/* 374:401 */     return false;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public static String convertToFixedWidth(String string, int width)
/* 378:    */   {
/* 379:410 */     StringBuffer result = new StringBuffer();
/* 380:411 */     result.append(string);
/* 381:412 */     int space = width - result.length();
/* 382:413 */     if (space > 0) {
/* 383:414 */       for (int i = 0; i < space; i++) {
/* 384:415 */         result.append(" ");
/* 385:    */       }
/* 386:    */     }
/* 387:418 */     return result.toString();
/* 388:    */   }
/* 389:    */   
/* 390:    */   public static String systemInReadln()
/* 391:    */   {
/* 392:423 */     BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
/* 393:    */     try
/* 394:    */     {
/* 395:425 */       return stdin.readLine();
/* 396:    */     }
/* 397:    */     catch (IOException e)
/* 398:    */     {
/* 399:427 */       e.printStackTrace();
/* 400:    */     }
/* 401:428 */     return null;
/* 402:    */   }
/* 403:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.StringUtils
 * JD-Core Version:    0.7.0.1
 */