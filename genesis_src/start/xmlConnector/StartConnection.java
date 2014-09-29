/*   1:    */ package start.xmlConnector;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.OutputStreamWriter;
/*   7:    */ import java.io.UnsupportedEncodingException;
/*   8:    */ import java.net.MalformedURLException;
/*   9:    */ import java.net.URL;
/*  10:    */ import java.net.URLConnection;
/*  11:    */ import java.net.URLEncoder;
/*  12:    */ import start.StartFoundation;
/*  13:    */ import translator.Translator;
/*  14:    */ import utils.Mark;
/*  15:    */ 
/*  16:    */ public class StartConnection
/*  17:    */ {
/*  18:    */   private static final String DEFAULT_SERVER = "genesis";
/*  19:    */   private static final String EXPERIMENTAL_SERVER = "e-genesis";
/*  20:    */   private static final String PARSE = "parse";
/*  21:    */   private static final String STORY_MODE = "use-kb";
/*  22: 27 */   private String mode = "use-kb";
/*  23:    */   
/*  24:    */   public StartConnection useParseMode()
/*  25:    */   {
/*  26: 30 */     this.mode = "parse";
/*  27: 31 */     return this;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public StartConnection useStoryMode()
/*  31:    */   {
/*  32: 35 */     this.mode = "use-kb";
/*  33: 36 */     return this;
/*  34:    */   }
/*  35:    */   
/*  36: 39 */   private String server = "e-genesis";
/*  37:    */   
/*  38:    */   public StartConnection useDefaultServer()
/*  39:    */   {
/*  40: 42 */     this.server = "genesis";
/*  41: 43 */     return this;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public StartConnection useExperimentalServer()
/*  45:    */   {
/*  46: 47 */     this.server = "e-genesis";
/*  47: 48 */     return this;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String parse(String sentence)
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 65 */       sentence = URLEncoder.encode(sentence, "UTF-8");
/*  55:    */     }
/*  56:    */     catch (UnsupportedEncodingException e)
/*  57:    */     {
/*  58: 68 */       e.printStackTrace();
/*  59:    */     }
/*  60: 71 */     String query = "query=" + sentence;
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64: 75 */     String params = "&pa=" + this.mode + "&action=compute-lf&te=XML&server=" + this.server;
/*  65:    */     
/*  66: 77 */     String request = query + params;
/*  67:    */     
/*  68: 79 */     Mark.say(new Object[] {"Request is", request });
/*  69:    */     
/*  70: 81 */     StringBuffer buffer = new StringBuffer();
/*  71:    */     try
/*  72:    */     {
/*  73: 84 */       URL url = new URL(StartFoundation.urlString);
/*  74: 85 */       URLConnection connection = url.openConnection();
/*  75: 86 */       connection.setDoOutput(true);
/*  76:    */       
/*  77: 88 */       OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
/*  78: 89 */       out.write(request);
/*  79: 90 */       out.close();
/*  80:    */       
/*  81: 92 */       BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/*  82:    */       String decodedString;
/*  83: 94 */       while ((decodedString = in.readLine()) != null)
/*  84:    */       {
/*  85:    */         String decodedString;
/*  86: 95 */         Mark.say(new Object[] {decodedString });
/*  87: 96 */         buffer.append(decodedString + "\n");
/*  88:    */       }
/*  89: 98 */       in.close();
/*  90:    */     }
/*  91:    */     catch (MalformedURLException e)
/*  92:    */     {
/*  93:102 */       Mark.err(new Object[] {"Evidently bad start url" });
/*  94:    */     }
/*  95:    */     catch (IOException e)
/*  96:    */     {
/*  97:105 */       Mark.err(new Object[] {"Evidently not connected to web or START is down" });
/*  98:    */     }
/*  99:    */     catch (Exception e)
/* 100:    */     {
/* 101:108 */       Mark.err(new Object[] {"Evidently unable to process '" + request + "'" });
/* 102:    */     }
/* 103:110 */     return buffer.toString();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static void main(String[] args)
/* 107:    */     throws Exception
/* 108:    */   {
/* 109:119 */     Mark.say(
/* 110:120 */       new Object[] { Translator.getTranslator().translate("John loves Mary") });
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.xmlConnector.StartConnection
 * JD-Core Version:    0.7.0.1
 */