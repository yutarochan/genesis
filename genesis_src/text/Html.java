/*   1:    */ package text;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.List;
/*   5:    */ import utils.Mark;
/*   6:    */ 
/*   7:    */ public class Html
/*   8:    */ {
/*   9: 14 */   public static String biggest = "style=\"font-family: times; font-size: 40px\"";
/*  10: 16 */   public static String bigger = "style=\"font-family: times; font-size: 30px\"";
/*  11: 18 */   public static String normal = "style=\"font-family: times; font-size: 25px\"";
/*  12:    */   
/*  13:    */   public static String surround(String tag, String c, String s)
/*  14:    */   {
/*  15: 21 */     return "<" + tag + " " + c + ">" + s + "</" + tag + ">";
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static String surround(String tag, String s)
/*  19:    */   {
/*  20: 25 */     return "<" + tag + ">" + s + "</" + tag + ">";
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static String size5(String s)
/*  24:    */   {
/*  25: 29 */     return "<font size=\"+5\">" + s + "</font>";
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static String size4(String s)
/*  29:    */   {
/*  30: 33 */     return "<font size=\"+4\">" + s + "</font>";
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static String size3(String s)
/*  34:    */   {
/*  35: 37 */     return "<font size=\"+3\">" + s + "</font>";
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static String size2(String s)
/*  39:    */   {
/*  40: 41 */     return "<font size=\"+2\">" + s + "</font>";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static String h1(String s)
/*  44:    */   {
/*  45: 45 */     return surround("h1", biggest, s);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static String h2(String s)
/*  49:    */   {
/*  50: 49 */     return surround("h2", bigger, s);
/*  51:    */   }
/*  52:    */   
/*  53:    */   private static String atSize(int pixels, String s)
/*  54:    */   {
/*  55: 53 */     String result = "<font style=\"font-family: times; font-size: ";
/*  56: 54 */     result = result + pixels;
/*  57: 55 */     result = result + "px\"> ";
/*  58: 56 */     result = result + s;
/*  59: 57 */     result = result + "</font>";
/*  60: 58 */     return result;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static String normal(String s)
/*  64:    */   {
/*  65: 62 */     return atSize(25, s);
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static String large(String s)
/*  69:    */   {
/*  70: 66 */     return "<font size=\"+2\">" + s + "</font>";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static String Large(String s)
/*  74:    */   {
/*  75: 70 */     return "<font size=\"+3\">" + s + "</font>";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static String LARGE(String s)
/*  79:    */   {
/*  80: 74 */     return "<font size=\"+4\">" + s + "</font>";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static String huge(String s)
/*  84:    */   {
/*  85: 78 */     return "<font size=\"+5\">" + s + "</font>";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static String Huge(String s)
/*  89:    */   {
/*  90: 82 */     return "<font size=\"+6\">" + s + "</font>";
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static String p(String s)
/*  94:    */   {
/*  95: 86 */     return "\n" + surround("p", s);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static String br(String s)
/*  99:    */   {
/* 100: 90 */     return "<br/>" + s;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static String line(String s)
/* 104:    */   {
/* 105: 94 */     return s + "<br/>";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static String bold(String s)
/* 109:    */   {
/* 110: 98 */     return surround("b", s);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static String html(String s)
/* 114:    */   {
/* 115:102 */     return surround("html", s);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static String ital(String s)
/* 119:    */   {
/* 120:106 */     return surround("i", s);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static String convertLf(String s)
/* 124:    */   {
/* 125:110 */     return s.replaceAll("\n", "<br/>");
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static String remove_(String s)
/* 129:    */   {
/* 130:114 */     StringBuffer b = new StringBuffer(s);
/* 131:    */     for (;;)
/* 132:    */     {
/* 133:116 */       int index = b.indexOf("_");
/* 134:117 */       if (index < 0) {
/* 135:    */         break;
/* 136:    */       }
/* 137:118 */       b.replace(index, index + 1, " ");
/* 138:    */     }
/* 139:123 */     return b.toString();
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static String strip(String s)
/* 143:    */   {
/* 144:127 */     StringBuffer b = new StringBuffer(s);
/* 145:    */     for (;;)
/* 146:    */     {
/* 147:129 */       int index1 = b.indexOf("<");
/* 148:130 */       if (index1 < 0) {
/* 149:    */         break;
/* 150:    */       }
/* 151:131 */       int index2 = b.indexOf(">", index1);
/* 152:132 */       if (index2 < 0) {
/* 153:    */         break;
/* 154:    */       }
/* 155:133 */       b.replace(index1, index2 + 1, " ");
/* 156:    */     }
/* 157:140 */     return b.toString();
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static String red(String s)
/* 161:    */   {
/* 162:144 */     return "<font color=\"red\">" + s + "</font>";
/* 163:    */   }
/* 164:    */   
/* 165:    */   public static String gray(String s)
/* 166:    */   {
/* 167:148 */     return "<font color=\"gray\">" + s + "</font>";
/* 168:    */   }
/* 169:    */   
/* 170:    */   public static String green(String s)
/* 171:    */   {
/* 172:152 */     return "<font color=\"green\">" + s + "</font>";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static String blue(String s)
/* 176:    */   {
/* 177:156 */     return "<font color=\"blue\">" + s + "</font>";
/* 178:    */   }
/* 179:    */   
/* 180:    */   public static String capitalize(String s)
/* 181:    */   {
/* 182:160 */     if ((s == null) || (s.isEmpty())) {
/* 183:161 */       return s;
/* 184:    */     }
/* 185:163 */     return s.substring(0, 1).toUpperCase() + s.substring(1);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static String bullet(String description)
/* 189:    */   {
/* 190:167 */     return surround("li", description);
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static String twoDigits(String s)
/* 194:    */   {
/* 195:171 */     if (s.length() == 0) {
/* 196:172 */       return "00";
/* 197:    */     }
/* 198:174 */     if (s.length() == 1) {
/* 199:175 */       return "0" + s;
/* 200:    */     }
/* 201:177 */     return s;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public static String table(String... strings)
/* 205:    */   {
/* 206:181 */     return table(Arrays.asList(strings));
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static String table(List<String> strings)
/* 210:    */   {
/* 211:185 */     String result = "<table>";
/* 212:186 */     result = result + tableHeading(strings);
/* 213:187 */     result = result + "</table>";
/* 214:188 */     return result;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static String tableWithPadding(int padding, String... strings)
/* 218:    */   {
/* 219:192 */     return tableWithPadding(padding, Arrays.asList(strings));
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static String tableWithPadding(int padding, List<String> strings)
/* 223:    */   {
/* 224:196 */     String result = "<table cellpadding=\"" + padding + "\">";
/* 225:197 */     result = result + tableHeading(strings);
/* 226:198 */     result = result + "</table>";
/* 227:199 */     return result;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public static String tableHeading(String... strings)
/* 231:    */   {
/* 232:203 */     return tableHeading(Arrays.asList(strings));
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static String tableHeading(List<String> strings)
/* 236:    */   {
/* 237:207 */     String result = "<tr>";
/* 238:208 */     for (String string : strings) {
/* 239:209 */       result = result + "<th>" + string + "</th>";
/* 240:    */     }
/* 241:211 */     result = result + "</tr>";
/* 242:212 */     return result;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public static String tableAddRow(String table, String... elements)
/* 246:    */   {
/* 247:216 */     return tableAddRow(table, Arrays.asList(elements));
/* 248:    */   }
/* 249:    */   
/* 250:    */   public static String tableAddRow(String table, List<String> elements)
/* 251:    */   {
/* 252:220 */     String suffix = "</table>";
/* 253:221 */     String start = table.trim();
/* 254:222 */     if (start.endsWith(suffix))
/* 255:    */     {
/* 256:223 */       start = start.substring(0, start.length() - suffix.length());
/* 257:224 */       return start + tableRow(elements) + suffix;
/* 258:    */     }
/* 259:226 */     Mark.err(new Object[] {"Adding to string that is not a table in Html.tableAddRow" });
/* 260:227 */     return null;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static String tableRow(String... strings)
/* 264:    */   {
/* 265:231 */     return tableRow(Arrays.asList(strings));
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static String tableRow(List<String> strings)
/* 269:    */   {
/* 270:235 */     String result = "<tr>";
/* 271:236 */     for (String string : strings) {
/* 272:237 */       result = result + "<td>" + string + "</td>";
/* 273:    */     }
/* 274:239 */     result = result + "</tr>";
/* 275:240 */     return result;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public static String size(int size, String stuff)
/* 279:    */   {
/* 280:244 */     return "<font size=" + size + ">" + stuff + "</font>";
/* 281:    */   }
/* 282:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     text.Html
 * JD-Core Version:    0.7.0.1
 */