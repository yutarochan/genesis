/*   1:    */ package Co57.tools;
/*   2:    */ 
/*   3:    */ import java.io.BufferedReader;
/*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.FileReader;
/*   7:    */ import java.io.IOException;
/*   8:    */ import org.jeromq.ZMQ;
/*   9:    */ import org.jeromq.ZMQ.Context;
/*  10:    */ import org.jeromq.ZMQ.Socket;
/*  11:    */ import utils.Mark;
/*  12:    */ 
/*  13:    */ public class ZMQClientExample
/*  14:    */ {
/*  15:    */   private static String loadFileToString(String fileName)
/*  16:    */     throws IOException
/*  17:    */   {
/*  18: 27 */     File file = new File(fileName);
/*  19: 28 */     StringBuffer content = new StringBuffer();
/*  20: 29 */     BufferedReader reader = null;
/*  21:    */     try
/*  22:    */     {
/*  23: 32 */       reader = new BufferedReader(new FileReader(file));
/*  24: 33 */       String s = null;
/*  25: 35 */       while ((s = reader.readLine()) != null) {
/*  26: 36 */         content.append(s).append(System.getProperty("line.separator"));
/*  27:    */       }
/*  28:    */     }
/*  29:    */     catch (FileNotFoundException e)
/*  30:    */     {
/*  31: 39 */       throw e;
/*  32:    */     }
/*  33:    */     catch (IOException e)
/*  34:    */     {
/*  35: 41 */       throw e;
/*  36:    */     }
/*  37:    */     finally
/*  38:    */     {
/*  39:    */       try
/*  40:    */       {
/*  41: 44 */         if (reader != null) {
/*  42: 45 */           reader.close();
/*  43:    */         }
/*  44:    */       }
/*  45:    */       catch (IOException e)
/*  46:    */       {
/*  47: 48 */         throw e;
/*  48:    */       }
/*  49:    */     }
/*  50: 51 */     return content.toString();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public static void main(String[] args)
/*  54:    */   {
/*  55: 55 */     Mark.say(
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
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:107 */       new Object[] { "Running ZMQ client" });String exampleFile = "";
/* 108:    */     try
/* 109:    */     {
/* 110: 59 */       exampleFile = loadFileToString("corpora/misc/VerbOutputExample.json");
/* 111: 60 */       String[] examples = exampleFile.split("\n");
/* 112:    */       
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116: 65 */       Mark.say(new Object[] {"Starting demo, connecting to local server..." });
/* 117:    */       
/* 118:    */ 
/* 119: 68 */       ZMQ.Context context = ZMQ.context(1);
/* 120: 69 */       ZMQ.Socket socket = context.socket(3);
/* 121:    */       
/* 122:    */ 
/* 123: 72 */       socket.connect("tcp://127.0.0.1:5555");
/* 124:    */       
/* 125: 74 */       Mark.say(new Object[] {"Connected!" });
/* 126: 76 */       for (int request_nbr = 0; request_nbr < examples.length; request_nbr++)
/* 127:    */       {
/* 128: 80 */         String requestString = "{\"end\":3533,\"name\":\"putdown\",\"start\":3491,\"subject\":1}";
/* 129: 81 */         requestString = examples[request_nbr];
/* 130: 82 */         byte[] request = requestString.getBytes();
/* 131:    */         
/* 132:    */ 
/* 133: 85 */         socket.send(request, 0);
/* 134:    */         
/* 135:    */ 
/* 136: 88 */         byte[] reply = socket.recv(0);
/* 137:    */         
/* 138:    */ 
/* 139: 91 */         String replyS = new String(reply);
/* 140: 92 */         replyS = replyS.substring(0, replyS.length() - 2);
/* 141:    */         try
/* 142:    */         {
/* 143: 96 */           Thread.sleep(1000L);
/* 144:    */         }
/* 145:    */         catch (InterruptedException e)
/* 146:    */         {
/* 147: 98 */           e.printStackTrace();
/* 148:    */         }
/* 149:    */       }
/* 150:102 */       socket.close();
/* 151:103 */       context.term();
/* 152:    */     }
/* 153:    */     catch (IOException e)
/* 154:    */     {
/* 155:105 */       e.printStackTrace();
/* 156:    */     }
/* 157:    */   }
/* 158:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.tools.ZMQClientExample
 * JD-Core Version:    0.7.0.1
 */