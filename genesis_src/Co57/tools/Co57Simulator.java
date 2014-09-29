/*   1:    */ package Co57.tools;
/*   2:    */ 
/*   3:    */ import Co57.BerylVerbTranslator;
/*   4:    */ import Co57.infrastructure.GenericZMQConnection;
/*   5:    */ import Co57.infrastructure.GenericZMQConnection.Type;
/*   6:    */ import Co57.services.Co57Passthrough;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Connections.NetWireException;
/*   9:    */ import genesis.GenesisControls;
/*  10:    */ import java.io.BufferedReader;
/*  11:    */ import java.io.File;
/*  12:    */ import java.io.FileNotFoundException;
/*  13:    */ import java.io.FileReader;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.net.MalformedURLException;
/*  16:    */ import java.net.URL;
/*  17:    */ import java.util.Random;
/*  18:    */ import javax.swing.JRadioButton;
/*  19:    */ import utils.Mark;
/*  20:    */ 
/*  21:    */ public class Co57Simulator
/*  22:    */   implements Runnable
/*  23:    */ {
/*  24:    */   private static String loadFileToString(String fileName)
/*  25:    */     throws IOException
/*  26:    */   {
/*  27: 47 */     File file = new File(fileName);
/*  28: 48 */     StringBuffer content = new StringBuffer();
/*  29: 49 */     BufferedReader reader = null;
/*  30:    */     try
/*  31:    */     {
/*  32: 52 */       reader = new BufferedReader(new FileReader(file));
/*  33: 53 */       String s = null;
/*  34: 55 */       while ((s = reader.readLine()) != null) {
/*  35: 56 */         content.append(s).append(System.getProperty("line.separator"));
/*  36:    */       }
/*  37:    */     }
/*  38:    */     catch (FileNotFoundException e)
/*  39:    */     {
/*  40: 59 */       throw e;
/*  41:    */     }
/*  42:    */     catch (IOException e)
/*  43:    */     {
/*  44: 61 */       throw e;
/*  45:    */     }
/*  46:    */     finally
/*  47:    */     {
/*  48:    */       try
/*  49:    */       {
/*  50: 64 */         if (reader != null) {
/*  51: 65 */           reader.close();
/*  52:    */         }
/*  53:    */       }
/*  54:    */       catch (IOException e)
/*  55:    */       {
/*  56: 68 */         throw e;
/*  57:    */       }
/*  58:    */     }
/*  59: 71 */     return content.toString();
/*  60:    */   }
/*  61:    */   
/*  62: 74 */   private static Co57Passthrough pass = null;
/*  63: 75 */   public static String name = null;
/*  64:    */   
/*  65:    */   public Co57Simulator()
/*  66:    */   {
/*  67: 78 */     if (pass == null)
/*  68:    */     {
/*  69: 79 */       Random r = new Random();
/*  70: 80 */       name = "ZMQLocalPassthrough" + r.nextInt();
/*  71: 81 */       pass = new Co57Passthrough(name);
/*  72: 82 */       Co57Passthrough.quiet_mode = true;
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void start()
/*  77:    */   {
/*  78: 88 */     Thread t = new Thread(this);
/*  79: 89 */     t.start();
/*  80:    */   }
/*  81:    */   
/*  82: 92 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/*  83:    */   
/*  84:    */   public void run()
/*  85:    */   {
/*  86: 95 */     if (GenesisControls.co57LocalPassthrough.isSelected())
/*  87:    */     {
/*  88: 96 */       URL serverURL = null;
/*  89:    */       try
/*  90:    */       {
/*  91: 98 */         serverURL = new URL(wireServer);
/*  92:    */       }
/*  93:    */       catch (MalformedURLException e)
/*  94:    */       {
/*  95:100 */         e.printStackTrace();
/*  96:101 */         System.exit(1);
/*  97:    */       }
/*  98:    */       try
/*  99:    */       {
/* 100:105 */         Connections.useWireServer(serverURL);
/* 101:106 */         Connections.publish(pass, name);
/* 102:    */         
/* 103:108 */         pass.OneWayServer(6555);
/* 104:    */       }
/* 105:    */       catch (Connections.NetWireException e)
/* 106:    */       {
/* 107:110 */         e.printStackTrace();
/* 108:    */       }
/* 109:    */       catch (Exception e)
/* 110:    */       {
/* 111:112 */         e.printStackTrace();
/* 112:    */       }
/* 113:    */       try
/* 114:    */       {
/* 115:115 */         Thread.sleep(1000L);
/* 116:    */       }
/* 117:    */       catch (InterruptedException e)
/* 118:    */       {
/* 119:117 */         e.printStackTrace();
/* 120:    */       }
/* 121:119 */       Mark.say(new Object[] {"BerylVerbTranslator connecting to Local passthrough..." });
/* 122:120 */       BerylVerbTranslator wireClient = new BerylVerbTranslator("BerylVerbTranslator", name);
/* 123:121 */       if (wireClient != null) {
/* 124:122 */         Mark.say(new Object[] {"BerylVerbTranslator Active" });
/* 125:    */       }
/* 126:    */     }
/* 127:124 */     if (GenesisControls.co57Passthrough.isSelected())
/* 128:    */     {
/* 129:125 */       Mark.say(new Object[] {"BerylVerbTranslator connecting to Server passthrough..." });
/* 130:126 */       BerylVerbTranslator wireClient = new BerylVerbTranslator("BerylVerbTranslator", Co57Passthrough.ZMQ_SERVER_WIRED_BOX_SERVICE);
/* 131:127 */       if (wireClient != null) {
/* 132:128 */         Mark.say(new Object[] {"BerylVerbTranslator Active" });
/* 133:    */       }
/* 134:    */     }
/* 135:130 */     if (GenesisControls.co57SimulatorAndTranslator.isSelected())
/* 136:    */     {
/* 137:131 */       Mark.say(new Object[] {"Simulating Co57 Output..." });
/* 138:132 */       simCo57();
/* 139:    */     }
/* 140:    */     else
/* 141:    */     {
/* 142:133 */       GenesisControls.co57JustTranslator.isSelected();
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void simCo57()
/* 147:    */   {
/* 148:140 */     String exampleFile = "";
/* 149:141 */     GenericZMQConnection co57 = null;
/* 150:    */     try
/* 151:    */     {
/* 152:143 */       exampleFile = loadFileToString("corpora/misc/VerbOutputExample.json");
/* 153:144 */       String[] examples = exampleFile.split("\n");
/* 154:    */       
/* 155:146 */       Mark.say(new Object[] {"Starting Co57 simulation, connecting to local passthrough..." });
/* 156:150 */       if (GenesisControls.co57Passthrough.isSelected()) {
/* 157:151 */         co57 = new GenericZMQConnection(GenericZMQConnection.Type.REQ, "meta.csail.mit.edu", 5555);
/* 158:    */       }
/* 159:153 */       if (GenesisControls.co57LocalPassthrough.isSelected()) {
/* 160:154 */         co57 = new GenericZMQConnection(GenericZMQConnection.Type.REQ, "127.0.0.1", 6555);
/* 161:    */       }
/* 162:157 */       for (int request_nbr = 0; request_nbr < examples.length; request_nbr++)
/* 163:    */       {
/* 164:158 */         String requestString = "{\"end\":3533,\"name\":\"putdown\",\"start\":3491,\"subject\":1}";
/* 165:159 */         requestString = examples[request_nbr];
/* 166:    */         
/* 167:161 */         co57.request(requestString);
/* 168:    */         try
/* 169:    */         {
/* 170:164 */           Thread.sleep(1000L);
/* 171:    */         }
/* 172:    */         catch (InterruptedException e)
/* 173:    */         {
/* 174:166 */           e.printStackTrace();
/* 175:    */         }
/* 176:    */       }
/* 177:    */     }
/* 178:    */     catch (IOException e)
/* 179:    */     {
/* 180:170 */       e.printStackTrace();
/* 181:    */     }
/* 182:    */     finally
/* 183:    */     {
/* 184:172 */       if (co57 != null)
/* 185:    */       {
/* 186:173 */         co57.kill();
/* 187:174 */         co57 = null;
/* 188:    */       }
/* 189:    */     }
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static void Simulate()
/* 193:    */   {
/* 194:181 */     Co57Simulator sim = new Co57Simulator();
/* 195:182 */     sim.start();
/* 196:    */   }
/* 197:    */   
/* 198:    */   public static void main(String[] args)
/* 199:    */     throws InterruptedException
/* 200:    */   {
/* 201:186 */     Co57Simulator sim = new Co57Simulator();
/* 202:187 */     sim.start();
/* 203:188 */     Mark.say(new Object[] {"Non-Blocking" });
/* 204:    */   }
/* 205:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.tools.Co57Simulator
 * JD-Core Version:    0.7.0.1
 */