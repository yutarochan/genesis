/*   1:    */ package start;
/*   2:    */ 
/*   3:    */ import connections.AbstractWiredBox;
/*   4:    */ import connections.Connections;
/*   5:    */ import constants.GenesisConstants;
/*   6:    */ import java.io.UnsupportedEncodingException;
/*   7:    */ import java.net.URLEncoder;
/*   8:    */ import utils.Mark;
/*   9:    */ 
/*  10:    */ public class StartServerBox
/*  11:    */   extends AbstractWiredBox
/*  12:    */ {
/*  13: 13 */   private boolean debug = false;
/*  14: 15 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/*  15:    */   private static StartServerBox startServerBox;
/*  16:    */   
/*  17:    */   public String getName()
/*  18:    */   {
/*  19: 23 */     return "StartServerBox: a demonstration of net wire capability";
/*  20:    */   }
/*  21:    */   
/*  22:    */   public String remoteParse(String text, String mode, String url, String uuid)
/*  23:    */   {
/*  24: 30 */     String header = "query=";
/*  25: 31 */     String trailer = "&pa=" + mode + "&action=compute-lf&server=" + url;
/*  26: 32 */     String encodedString = "";
/*  27:    */     try
/*  28:    */     {
/*  29: 34 */       encodedString = URLEncoder.encode(text, "UTF-8");
/*  30:    */     }
/*  31:    */     catch (UnsupportedEncodingException e)
/*  32:    */     {
/*  33: 37 */       e.printStackTrace();
/*  34:    */     }
/*  35: 39 */     String probe = header + encodedString + trailer + "&uuid=" + uuid;
/*  36: 40 */     String response = StartFoundation.getStartFoundation().processParseRequest(probe);
/*  37: 41 */     return response;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Object remoteGenerate(String text, String uuid)
/*  41:    */   {
/*  42: 47 */     String encodedString = null;
/*  43:    */     try
/*  44:    */     {
/*  45: 50 */       encodedString = URLEncoder.encode(text, "UTF-8");
/*  46:    */     }
/*  47:    */     catch (UnsupportedEncodingException e1)
/*  48:    */     {
/*  49: 53 */       Mark.say(new Object[] {"Unable to encode in PhraseFactory.generate" });
/*  50: 54 */       return null;
/*  51:    */     }
/*  52: 57 */     String header = "server=e-genesis&te=formated-text&de=n&action=generate&query=";
/*  53:    */     
/*  54: 59 */     String request = header + encodedString + "&uuid=" + uuid;
/*  55:    */     
/*  56: 61 */     String response = StartFoundation.getStartFoundation().processGeneratorRequest(header + encodedString);
/*  57: 62 */     if (response != null) {
/*  58: 63 */       return response.trim();
/*  59:    */     }
/*  60: 65 */     return null;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static StartServerBox getStartServerBox()
/*  64:    */   {
/*  65: 69 */     if (startServerBox == null)
/*  66:    */     {
/*  67: 70 */       startServerBox = new StartServerBox();
/*  68:    */       try
/*  69:    */       {
/*  70: 72 */         Connections.publish(startServerBox, GenesisConstants.server);
/*  71: 73 */         Mark.say(new Object[] {"Created Start server" });
/*  72:    */       }
/*  73:    */       catch (Exception e)
/*  74:    */       {
/*  75: 77 */         Mark.err(new Object[] {"Failed to create Start server" });
/*  76:    */       }
/*  77:    */     }
/*  78: 81 */     return startServerBox;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static StartServerBox getStartServerBox(String name)
/*  82:    */   {
/*  83: 85 */     if (startServerBox == null)
/*  84:    */     {
/*  85: 86 */       startServerBox = new StartServerBox();
/*  86:    */       try
/*  87:    */       {
/*  88: 88 */         Connections.publish(startServerBox, name);
/*  89: 89 */         Mark.say(new Object[] {"Created Start server" });
/*  90:    */       }
/*  91:    */       catch (Exception e)
/*  92:    */       {
/*  93: 93 */         Mark.err(new Object[] {"Failed to create Start server" });
/*  94:    */       }
/*  95:    */     }
/*  96: 97 */     return startServerBox;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static void main(String[] ignore)
/* 100:    */     throws Exception
/* 101:    */   {
/* 102:101 */     getStartServerBox("Start server test");
/* 103:102 */     Thread.sleep(5000L);
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.StartServerBox
 * JD-Core Version:    0.7.0.1
 */