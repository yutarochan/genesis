/*   1:    */ package utils;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.BufferedWriter;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileNotFoundException;
/*   8:    */ import java.io.FileOutputStream;
/*   9:    */ import java.io.FilenameFilter;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.InputStreamReader;
/*  12:    */ import java.io.OutputStreamWriter;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.io.PrintWriter;
/*  15:    */ import java.io.Reader;
/*  16:    */ import java.net.HttpURLConnection;
/*  17:    */ import java.net.URL;
/*  18:    */ import java.net.URLConnection;
/*  19:    */ import java.util.regex.Matcher;
/*  20:    */ import java.util.regex.Pattern;
/*  21:    */ 
/*  22:    */ public class TextIO
/*  23:    */ {
/*  24: 15 */   public static boolean debug = false;
/*  25:    */   
/*  26:    */   public static boolean writeStringToFile(String s, File outputFile)
/*  27:    */   {
/*  28: 21 */     boolean b = false;
/*  29:    */     try
/*  30:    */     {
/*  31: 23 */       FileOutputStream fileStream = new FileOutputStream(outputFile);
/*  32: 24 */       OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileStream);
/*  33: 25 */       BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
/*  34: 26 */       writeStringToStream(s, bufferedWriter);
/*  35: 27 */       bufferedWriter.close();
/*  36: 28 */       outputStreamWriter.close();
/*  37: 29 */       fileStream.close();
/*  38: 30 */       b = true;
/*  39:    */     }
/*  40:    */     catch (Exception localException) {}
/*  41: 34 */     return b;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static boolean writeStringToURL(String s, URL url)
/*  45:    */     throws Exception
/*  46:    */   {
/*  47: 42 */     boolean b = false;
/*  48:    */     try
/*  49:    */     {
/*  50: 44 */       HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
/*  51: 45 */       urlConnection.setDoOutput(true);
/*  52: 46 */       urlConnection.setRequestMethod("PUT");
/*  53:    */       
/*  54: 48 */       PrintWriter printWriter = new PrintWriter(urlConnection.getOutputStream());
/*  55: 49 */       printWriter.print(s);
/*  56: 50 */       printWriter.flush();
/*  57: 51 */       printWriter.close();
/*  58:    */       
/*  59:    */ 
/*  60: 54 */       String response = urlConnection.getResponseMessage();
/*  61: 55 */       if (response.equals("OK")) {
/*  62: 56 */         b = true;
/*  63:    */       }
/*  64: 58 */       urlConnection.disconnect();
/*  65:    */     }
/*  66:    */     catch (IOException localIOException) {}
/*  67: 62 */     return b;
/*  68:    */   }
/*  69:    */   
/*  70:    */   private static void writeStringToStream(String s, BufferedWriter bufferedWriter)
/*  71:    */     throws Exception
/*  72:    */   {
/*  73: 66 */     if (s != null) {
/*  74: 67 */       bufferedWriter.write(s);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static String readStringFromFile(File file)
/*  79:    */     throws Exception
/*  80:    */   {
/*  81: 75 */     String s = "";
/*  82: 76 */     if (debug) {
/*  83: 76 */       System.out.println("TextIO: Reading string from " + file);
/*  84:    */     }
/*  85: 77 */     if ((debug) && (!file.exists())) {
/*  86: 77 */       System.out.println(file + " does not exist in " + file.getAbsolutePath());
/*  87:    */     }
/*  88: 78 */     if (file.exists())
/*  89:    */     {
/*  90: 79 */       FileInputStream fileStream = new FileInputStream(file);
/*  91: 80 */       InputStreamReader inputStreamReader = new InputStreamReader(fileStream);
/*  92: 81 */       BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
/*  93: 82 */       if (debug) {
/*  94: 82 */         System.out.println("TextIO: Begin stream reading... ");
/*  95:    */       }
/*  96: 83 */       s = readStringFromStream(bufferedReader);
/*  97: 84 */       if (debug) {
/*  98: 84 */         System.out.println("TextIO: End stream reading: " + s);
/*  99:    */       }
/* 100: 85 */       bufferedReader.close();
/* 101: 86 */       inputStreamReader.close();
/* 102: 87 */       fileStream.close();
/* 103:    */     }
/* 104: 89 */     return s;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static String readStringFromURL(URL url)
/* 108:    */     throws Exception
/* 109:    */   {
/* 110: 99 */     URLConnection con = url.openConnection();
/* 111:100 */     Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
/* 112:101 */     Matcher m = p.matcher(con.getContentType());
/* 113:    */     
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:107 */     String charset = m.matches() ? m.group(1) : "ISO-8859-1";
/* 119:108 */     Reader r = new InputStreamReader(con.getInputStream(), charset);
/* 120:109 */     StringBuilder buf = new StringBuilder();
/* 121:    */     for (;;)
/* 122:    */     {
/* 123:111 */       int ch = r.read();
/* 124:112 */       if (ch < 0) {
/* 125:    */         break;
/* 126:    */       }
/* 127:113 */       buf.append((char)ch);
/* 128:    */     }
/* 129:115 */     return buf.toString();
/* 130:    */   }
/* 131:    */   
/* 132:    */   private static String readStringFromStream(BufferedReader bufferedReader)
/* 133:    */   {
/* 134:139 */     StringBuffer everything = new StringBuffer("");
/* 135:    */     try
/* 136:    */     {
/* 137:    */       String nextLine;
/* 138:142 */       while ((nextLine = bufferedReader.readLine()) != null)
/* 139:    */       {
/* 140:    */         String nextLine;
/* 141:144 */         int termination = nextLine.indexOf("//");
/* 142:145 */         if (termination >= 0) {
/* 143:146 */           nextLine = nextLine.substring(0, termination);
/* 144:    */         }
/* 145:148 */         everything.append(nextLine);
/* 146:149 */         everything.append('\n');
/* 147:    */       }
/* 148:    */     }
/* 149:    */     catch (IOException e)
/* 150:    */     {
/* 151:153 */       e.printStackTrace();
/* 152:154 */       System.out.println("Problem trying to read a string from stream");
/* 153:155 */       System.out.println(e);
/* 154:    */     }
/* 155:    */     int index;
/* 156:158 */     while ((index = everything.indexOf("/*")) >= 0)
/* 157:    */     {
/* 158:    */       int index;
/* 159:159 */       int otherIndex = index + 1;
/* 160:160 */       int limit = everything.length();
/* 161:161 */       int depth = 1;
/* 162:162 */       while (limit >= otherIndex + 2)
/* 163:    */       {
/* 164:163 */         if ((everything.charAt(otherIndex) == '/') && (everything.charAt(otherIndex + 1) == '*')) {
/* 165:164 */           depth++;
/* 166:166 */         } else if ((everything.charAt(otherIndex) == '*') && (everything.charAt(otherIndex + 1) == '/')) {
/* 167:167 */           depth--;
/* 168:    */         }
/* 169:169 */         if (depth == 0)
/* 170:    */         {
/* 171:170 */           everything.delete(index, otherIndex + 2);
/* 172:171 */           break;
/* 173:    */         }
/* 174:173 */         otherIndex++;
/* 175:    */       }
/* 176:175 */       if (depth != 0) {
/* 177:176 */         System.err.println("Unbalanced comment starting at " + everything.substring(index, index + 50) + "...");
/* 178:    */       }
/* 179:    */     }
/* 180:179 */     String result = new String(everything);
/* 181:180 */     return result;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public static boolean deleteFile(File file)
/* 185:    */   {
/* 186:187 */     boolean result = true;
/* 187:188 */     if (file.exists())
/* 188:    */     {
/* 189:189 */       result = file.delete();
/* 190:190 */       if (file.exists()) {
/* 191:191 */         result = false;
/* 192:    */       }
/* 193:    */     }
/* 194:196 */     return result;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public static boolean deleteURL(URL url)
/* 198:    */   {
/* 199:203 */     boolean result = false;
/* 200:    */     try
/* 201:    */     {
/* 202:205 */       URLConnection urlConnection = url.openConnection();
/* 203:206 */       urlConnection.setDoOutput(true);
/* 204:207 */       urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/* 205:208 */       if ((urlConnection instanceof HttpURLConnection))
/* 206:    */       {
/* 207:209 */         HttpURLConnection conn = (HttpURLConnection)urlConnection;
/* 208:210 */         conn.setRequestMethod("DELETE");
/* 209:211 */         result = true;
/* 210:    */       }
/* 211:    */     }
/* 212:    */     catch (FileNotFoundException localFileNotFoundException) {}catch (IOException localIOException) {}catch (NullPointerException localNullPointerException) {}catch (Exception localException) {}
/* 213:222 */     return result;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public static File[] list(File directory, FilenameFilter filter)
/* 217:    */   {
/* 218:232 */     File[] array = directory.listFiles(filter);
/* 219:233 */     return array;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static File[] list(File directory)
/* 223:    */   {
/* 224:241 */     File[] array = directory.listFiles();
/* 225:242 */     return array;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public static void main(String[] argv)
/* 229:    */   {
/* 230:    */     try
/* 231:    */     {
/* 232:250 */       URL url1 = new URL("http://ewall.mit.edu/phw/share/uploads/hello.html");
/* 233:251 */       URL url2 = new URL("http://ewall.mit.edu/phw/share/uploads/hello.html");
/* 234:    */       
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:260 */       url1 = new URL("http://www.ai.mit.edu/people/phw/Server/test.data");
/* 243:261 */       url2 = new URL("http://www.ai.mit.edu/people/phw/Server/test.data");
/* 244:    */       
/* 245:263 */       writeStringToURL("Hello World X?", url1);
/* 246:    */       
/* 247:265 */       System.out.println("----------");
/* 248:    */       
/* 249:267 */       System.out.println("Read " + readStringFromURL(url1));
/* 250:    */     }
/* 251:    */     catch (Exception e)
/* 252:    */     {
/* 253:270 */       e.printStackTrace();
/* 254:    */     }
/* 255:    */   }
/* 256:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.TextIO
 * JD-Core Version:    0.7.0.1
 */