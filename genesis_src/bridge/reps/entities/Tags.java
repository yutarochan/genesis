/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Vector;
/*   5:    */ 
/*   6:    */ public class Tags
/*   7:    */ {
/*   8: 34 */   private static boolean CONCISE = false;
/*   9: 35 */   public static boolean DEBUG = false;
/*  10:    */   StringBuffer remainder;
/*  11:    */   String left;
/*  12:    */   String right;
/*  13:    */   String nextResult;
/*  14:    */   
/*  15:    */   public Tags(String s, String t)
/*  16:    */   {
/*  17: 45 */     this.remainder = new StringBuffer(s);
/*  18: 46 */     this.left = ("<" + t + ">");
/*  19: 47 */     this.right = ("</" + t + ">");
/*  20: 48 */     next();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String next()
/*  24:    */   {
/*  25: 55 */     String thisResult = this.nextResult;
/*  26: 56 */     this.nextResult = null;
/*  27: 57 */     int depth = 0;
/*  28: 58 */     int anchor = this.remainder.indexOf(this.left);
/*  29: 59 */     int start = anchor + 1;
/*  30:    */     for (;;)
/*  31:    */     {
/*  32: 61 */       int nextLeft = this.remainder.indexOf(this.left, start);
/*  33: 62 */       int nextRight = this.remainder.indexOf(this.right, start);
/*  34: 63 */       if (nextRight < 0) {
/*  35:    */         break;
/*  36:    */       }
/*  37: 64 */       if ((nextLeft < 0) || (nextRight < nextLeft))
/*  38:    */       {
/*  39: 65 */         if (depth == 0)
/*  40:    */         {
/*  41: 67 */           this.nextResult = this.remainder
/*  42: 68 */             .substring(anchor + this.left.length(), nextRight);
/*  43:    */           
/*  44: 70 */           this.remainder.delete(0, nextRight + this.right.length());
/*  45:    */           
/*  46: 72 */           break;
/*  47:    */         }
/*  48: 74 */         depth++;
/*  49: 75 */         start = nextRight + 1;
/*  50:    */       }
/*  51:    */       else
/*  52:    */       {
/*  53: 78 */         depth--;
/*  54: 79 */         start = nextLeft + 1;
/*  55:    */       }
/*  56:    */     }
/*  57: 82 */     return thisResult;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean hasNext()
/*  61:    */   {
/*  62: 86 */     if (this.nextResult != null) {
/*  63: 86 */       return true;
/*  64:    */     }
/*  65: 87 */     return false;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static boolean hasNext(String s, String tag)
/*  69:    */   {
/*  70: 94 */     if (s.indexOf(tag) >= 0) {
/*  71: 94 */       return true;
/*  72:    */     }
/*  73: 95 */     return false;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public static String next(String s, String tag)
/*  77:    */   {
/*  78: 99 */     int start = s.indexOf("<" + tag + ">");
/*  79:100 */     if (start < 0) {
/*  80:101 */       System.err.println("Bad data to Tags.next---" + 
/*  81:102 */         tag + " not found");
/*  82:    */     }
/*  83:104 */     return "";
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static String untagTopLevelString(String tag, String s)
/*  87:    */   {
/*  88:113 */     String result = null;
/*  89:114 */     int depth = 0;
/*  90:115 */     if (s == null) {
/*  91:115 */       return result;
/*  92:    */     }
/*  93:    */     for (;;)
/*  94:    */     {
/*  95:117 */       int left = s.indexOf("<");
/*  96:118 */       int right = s.indexOf(">", left);
/*  97:119 */       if ((left < 0) || (right < 0)) {
/*  98:119 */         return null;
/*  99:    */       }
/* 100:120 */       String observedTag = s.substring(left + 1, right);
/* 101:121 */       if (s.charAt(left + 1) == '/')
/* 102:    */       {
/* 103:121 */         depth--;
/* 104:    */       }
/* 105:    */       else
/* 106:    */       {
/* 107:123 */         if ((tag.equals(observedTag)) && (depth == 0)) {
/* 108:124 */           return untagString(tag, s.substring(left));
/* 109:    */         }
/* 110:127 */         depth++;
/* 111:    */       }
/* 112:130 */       s = s.substring(right + 1);
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static String untagString(String tag, String s, int n)
/* 117:    */   {
/* 118:144 */     String result = null;
/* 119:145 */     if (s == null) {
/* 120:145 */       return result;
/* 121:    */     }
/* 122:147 */     for (int i = 0; i < n - 1; i++)
/* 123:    */     {
/* 124:148 */       int index = s.indexOf("<" + tag + ">");
/* 125:149 */       s = s.substring(index + tag.length() + 2);
/* 126:    */     }
/* 127:151 */     int first = s.indexOf("<" + tag + ">");
/* 128:152 */     int last = untagStringHelper(tag, s);
/* 129:153 */     if ((first >= 0) && (last > first)) {
/* 130:154 */       result = s.substring(first + 2 + tag.length(), last).trim();
/* 131:    */     }
/* 132:156 */     return result;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static String untagString(String tag, String s)
/* 136:    */   {
/* 137:165 */     String result = null;
/* 138:166 */     if (s == null) {
/* 139:166 */       return result;
/* 140:    */     }
/* 141:167 */     int first = s.indexOf("<" + tag + ">");
/* 142:168 */     int last = untagStringHelper(tag, s);
/* 143:169 */     if ((first >= 0) && (last > first)) {
/* 144:170 */       result = s.substring(first + 2 + tag.length(), last).trim();
/* 145:    */     }
/* 146:172 */     return result;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public static int untagStringHelper(String tag, String s)
/* 150:    */   {
/* 151:175 */     return untagStringHelper(tag, s, 0);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static int untagStringHelper(String tag, String s, int initial)
/* 155:    */   {
/* 156:179 */     int counter = 1;
/* 157:181 */     if (s == null) {
/* 158:181 */       return -1;
/* 159:    */     }
/* 160:183 */     int starter = s.indexOf("<" + tag + ">", initial);
/* 161:185 */     if (starter < 0) {
/* 162:185 */       return -1;
/* 163:    */     }
/* 164:187 */     int index = starter + 1;
/* 165:    */     do
/* 166:    */     {
/* 167:189 */       int closer = s.indexOf("</" + tag + ">", index);
/* 168:191 */       if (closer < 0) {
/* 169:191 */         return -1;
/* 170:    */       }
/* 171:193 */       int second = s.indexOf("<" + tag + ">", index + 1);
/* 172:195 */       if (second < 0)
/* 173:    */       {
/* 174:196 */         counter--;
/* 175:197 */         index = closer + 1;
/* 176:    */       }
/* 177:201 */       else if (second < closer)
/* 178:    */       {
/* 179:202 */         counter++;
/* 180:203 */         index = second + 1;
/* 181:    */       }
/* 182:    */       else
/* 183:    */       {
/* 184:208 */         counter--;
/* 185:209 */         index = closer + 1;
/* 186:    */       }
/* 187:214 */     } while (counter != 0);
/* 188:214 */     return index - 1;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public static String tag(String t, Object o)
/* 192:    */   {
/* 193:263 */     if ((CONCISE) && (o == null)) {
/* 194:263 */       return "";
/* 195:    */     }
/* 196:264 */     if (o == null) {
/* 197:264 */       return addTag(t, "null");
/* 198:    */     }
/* 199:265 */     return addTag(t, o);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public static String tag(String t, String s)
/* 203:    */   {
/* 204:271 */     if ((CONCISE) && (s.length() == 0)) {
/* 205:271 */       return "";
/* 206:    */     }
/* 207:272 */     return addTag(t, s);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public static String tagNoLine(String t, String s)
/* 211:    */   {
/* 212:278 */     if ((CONCISE) && (s.length() == 0)) {
/* 213:278 */       return "";
/* 214:    */     }
/* 215:279 */     return addTagNoLine(t, s);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public static String tag(Vector<?> v)
/* 219:    */   {
/* 220:315 */     String result = "";
/* 221:316 */     for (int i = 0; i < v.size(); i++) {
/* 222:317 */       result = result + v.elementAt(i).toString() + " ";
/* 223:    */     }
/* 224:319 */     return result.trim();
/* 225:    */   }
/* 226:    */   
/* 227:    */   private static String addSpaces(String s)
/* 228:    */   {
/* 229:326 */     StringBuffer b = new StringBuffer("");
/* 230:327 */     for (int i = 0; i < s.length(); i++)
/* 231:    */     {
/* 232:328 */       char c = s.charAt(i);
/* 233:329 */       if (c == '\n') {
/* 234:329 */         b.append("\n ");
/* 235:    */       } else {
/* 236:330 */         b.append(c);
/* 237:    */       }
/* 238:    */     }
/* 239:332 */     return b.toString();
/* 240:    */   }
/* 241:    */   
/* 242:    */   private static String addTag(String t, Object o)
/* 243:    */   {
/* 244:338 */     String middle = addSpaces(o.toString());
/* 245:339 */     if (middle.length() == 0) {
/* 246:339 */       return "\n<" + t + ">" + "</" + t + ">";
/* 247:    */     }
/* 248:340 */     return "\n<" + t + ">" + (middle.charAt(0) == '\n' ? "" : "\n ") + middle + "\n</" + t + ">";
/* 249:    */   }
/* 250:    */   
/* 251:    */   private static String addTagNoLine(String t, Object o)
/* 252:    */   {
/* 253:346 */     String middle = addSpaces(o.toString());
/* 254:347 */     if (middle.length() == 0) {
/* 255:347 */       return "\n<" + t + ">" + "</" + t + ">";
/* 256:    */     }
/* 257:348 */     return "\n<" + t + ">" + middle + "</" + t + ">";
/* 258:    */   }
/* 259:    */   
/* 260:    */   public static void main(String[] argv)
/* 261:    */   {
/* 262:354 */     CONCISE = true;
/* 263:355 */     String s = tag("thing", "foo");
/* 264:356 */     s = s + tag("thing", "bar");
/* 265:357 */     System.out.println(s);
/* 266:358 */     for (Tags iterator = new Tags(s, "thing"); iterator.hasNext();) {
/* 267:359 */       System.out.println(iterator.next());
/* 268:    */     }
/* 269:362 */     System.out.println("Testing untagging functionality -- MAF.16.Jan.04");
/* 270:363 */     Entity t1 = new Entity("Mark");
/* 271:364 */     Entity t2 = new Entity("Steph");
/* 272:365 */     Relation r1 = new Relation("siblings", t1, t2);
/* 273:366 */     Thread d = new Thread();
/* 274:367 */     d.addType("related");
/* 275:368 */     r1.addThread(d);
/* 276:369 */     System.out.println("Relation we have is: " + r1.toString());
/* 277:    */     
/* 278:371 */     String bundle = untagString("bundle", r1.toString());
/* 279:372 */     System.out.println("\nGetting bundle:\n\n" + bundle);
/* 280:    */     
/* 281:374 */     String thread = untagString("thread", bundle);
/* 282:375 */     System.out.println("\nGetting thread with untagString():\n\n" + thread);
/* 283:    */     
/* 284:377 */     thread = untagString("thread", bundle, 1);
/* 285:378 */     System.out.println("\nGetting first thread:\n\n" + thread);
/* 286:    */     
/* 287:380 */     thread = untagString("thread", bundle, 2);
/* 288:381 */     System.out.println("\nGetting second thread:\n\n" + thread);
/* 289:    */     
/* 290:383 */     thread = untagString("thread", bundle, 3);
/* 291:384 */     System.out.println("\nGetting third thread:\n\n" + thread);
/* 292:    */   }
/* 293:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.Tags
 * JD-Core Version:    0.7.0.1
 */