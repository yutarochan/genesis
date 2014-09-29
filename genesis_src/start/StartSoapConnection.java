/*  1:   */ package start;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStreamReader;
/*  6:   */ import java.io.OutputStreamWriter;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ import java.net.MalformedURLException;
/*  9:   */ import java.net.URL;
/* 10:   */ import java.net.URLConnection;
/* 11:   */ import utils.Mark;
/* 12:   */ 
/* 13:   */ public class StartSoapConnection
/* 14:   */ {
/* 15:   */   String urlString;
/* 16:   */   
/* 17:   */   protected void setUrlString(String urlString)
/* 18:   */   {
/* 19:18 */     this.urlString = urlString;
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected StringBuffer processProbe(String probe)
/* 23:   */   {
/* 24:22 */     boolean debug = false;
/* 25:23 */     if (this.urlString == null) {
/* 26:24 */       System.err.println("No url string in SoapConnection.processProbe");
/* 27:   */     }
/* 28:26 */     StringBuffer buffer = new StringBuffer();
/* 29:   */     try
/* 30:   */     {
/* 31:28 */       Mark.say(new Object[] {Boolean.valueOf(debug), "A1: Processing via web", probe });
/* 32:29 */       Mark.say(new Object[] {Boolean.valueOf(debug), "A2: url", this.urlString });
/* 33:30 */       URL url = new URL(this.urlString);
/* 34:31 */       URLConnection connection = url.openConnection();
/* 35:32 */       connection.setDoOutput(true);
/* 36:   */       
/* 37:34 */       Mark.say(new Object[] {Boolean.valueOf(debug), "B" });
/* 38:35 */       OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
/* 39:36 */       out.write(probe);
/* 40:37 */       out.close();
/* 41:38 */       Mark.say(new Object[] {Boolean.valueOf(debug), "C" });
/* 42:39 */       BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 43:   */       
/* 44:41 */       Mark.say(new Object[] {Boolean.valueOf(debug), "D:", in });
/* 45:   */       String decodedString;
/* 46:43 */       while ((decodedString = in.readLine()) != null)
/* 47:   */       {
/* 48:   */         String decodedString;
/* 49:44 */         buffer.append(decodedString + "\n");
/* 50:   */       }
/* 51:46 */       in.close();
/* 52:   */       
/* 53:48 */       Mark.say(new Object[] {Boolean.valueOf(debug), "E: Returned from Start" });
/* 54:49 */       Mark.say(new Object[] {Boolean.valueOf(debug), "F: String is:", buffer.toString() });
/* 55:   */     }
/* 56:   */     catch (MalformedURLException e)
/* 57:   */     {
/* 58:52 */       Mark.err(new Object[] {"Evidently bad url" });
/* 59:   */     }
/* 60:   */     catch (IOException e)
/* 61:   */     {
/* 62:56 */       Mark.err(new Object[] {"Evidently not connected to web or START is down" });
/* 63:   */     }
/* 64:   */     catch (Exception e)
/* 65:   */     {
/* 66:59 */       Mark.err(new Object[] {"Evidently unable to process '" + probe + "'" });
/* 67:   */     }
/* 68:61 */     return buffer;
/* 69:   */   }
/* 70:   */   
/* 71:   */   protected void processProbeWithoutReturn(String probe)
/* 72:   */   {
/* 73:65 */     boolean debug = false;
/* 74:66 */     if (this.urlString == null) {
/* 75:67 */       System.err.println("No url string in SoapConnection.processProbe");
/* 76:   */     }
/* 77:   */     try
/* 78:   */     {
/* 79:70 */       Mark.say(new Object[] {Boolean.valueOf(debug), "Processing via web", probe });
/* 80:71 */       URL url = new URL(this.urlString);
/* 81:72 */       URLConnection connection = url.openConnection();
/* 82:73 */       connection.setDoOutput(true);
/* 83:   */       
/* 84:75 */       OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
/* 85:76 */       out.write(probe);
/* 86:77 */       out.close();
/* 87:   */     }
/* 88:   */     catch (MalformedURLException e)
/* 89:   */     {
/* 90:80 */       Mark.err(new Object[] {"Evidently bad url" });
/* 91:   */     }
/* 92:   */     catch (IOException e)
/* 93:   */     {
/* 94:83 */       e.printStackTrace();
/* 95:84 */       Mark.err(new Object[] {"Evidently not connected to web or START is down" });
/* 96:   */     }
/* 97:   */     catch (Exception e)
/* 98:   */     {
/* 99:87 */       Mark.err(new Object[] {"Evidently unable to process '" + probe + "'" });
/* :0:   */     }
/* :1:   */   }
/* :2:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.StartSoapConnection
 * JD-Core Version:    0.7.0.1
 */