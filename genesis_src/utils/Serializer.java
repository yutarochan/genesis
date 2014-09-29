/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import com.thoughtworks.xstream.XStream;
/*  4:   */ import java.io.BufferedInputStream;
/*  5:   */ import java.io.File;
/*  6:   */ import java.io.FileNotFoundException;
/*  7:   */ import java.io.FileOutputStream;
/*  8:   */ import java.io.IOException;
/*  9:   */ import java.io.InputStream;
/* 10:   */ import java.io.PrintStream;
/* 11:   */ import java.net.URL;
/* 12:   */ import java.util.zip.GZIPInputStream;
/* 13:   */ import java.util.zip.GZIPOutputStream;
/* 14:   */ 
/* 15:   */ public class Serializer<T>
/* 16:   */ {
/* 17:10 */   private static XStream xml = new XStream();
/* 18:   */   
/* 19:   */   public String toXML(T t)
/* 20:   */   {
/* 21:13 */     return xml.toXML(t);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public T fromXML(String s)
/* 25:   */   {
/* 26:18 */     return xml.fromXML(s);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void save(T t, File file)
/* 30:   */   {
/* 31:   */     try
/* 32:   */     {
/* 33:23 */       GZIPOutputStream os = new GZIPOutputStream(new FileOutputStream(file));
/* 34:   */       
/* 35:25 */       os.write(toXML(t).getBytes("UTF-8"));
/* 36:   */       
/* 37:27 */       os.finish();
/* 38:28 */       os.close();
/* 39:   */     }
/* 40:   */     catch (FileNotFoundException x)
/* 41:   */     {
/* 42:30 */       System.err.println("Serializer " + x.getMessage());
/* 43:   */     }
/* 44:   */     catch (IOException x)
/* 45:   */     {
/* 46:32 */       System.err.println("Serializer " + x.getMessage());
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   public T load(URL url)
/* 51:   */   {
/* 52:   */     try
/* 53:   */     {
/* 54:38 */       System.out.println("URL = " + url);
/* 55:   */       
/* 56:40 */       GZIPInputStream zipStream = new GZIPInputStream(url.openStream());
/* 57:41 */       InputStream in = new BufferedInputStream(zipStream);
/* 58:   */       
/* 59:43 */       StringBuilder sb = new StringBuilder();
/* 60:44 */       byte[] buf = new byte[1024];
/* 61:   */       int len;
/* 62:46 */       while ((len = in.read(buf)) > 0)
/* 63:   */       {
/* 64:   */         int len;
/* 65:47 */         sb.append(new String(buf, 0, len, "UTF-8"));
/* 66:   */       }
/* 67:50 */       return fromXML(sb.toString());
/* 68:   */     }
/* 69:   */     catch (FileNotFoundException x)
/* 70:   */     {
/* 71:53 */       System.err.println("Serializer: " + x.getMessage());
/* 72:   */     }
/* 73:   */     catch (IOException x)
/* 74:   */     {
/* 75:55 */       System.err.println("Serializer: " + x.getMessage());
/* 76:   */     }
/* 77:57 */     return null;
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.Serializer
 * JD-Core Version:    0.7.0.1
 */