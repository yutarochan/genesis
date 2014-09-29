/*   1:    */ package Co57.infrastructure;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Scanner;
/*   5:    */ import org.jeromq.ZMQ;
/*   6:    */ import org.jeromq.ZMQ.Context;
/*   7:    */ import org.jeromq.ZMQ.Socket;
/*   8:    */ 
/*   9:    */ public class GenericZMQConnection
/*  10:    */ {
/*  11:    */   Type type;
/*  12:    */   
/*  13:    */   public static enum Type
/*  14:    */   {
/*  15: 17 */     REQ,  REP,  SUB,  PUB;
/*  16:    */   }
/*  17:    */   
/*  18: 19 */   boolean haveRequest = false;
/*  19:    */   ZMQ.Socket socket;
/*  20:    */   ZMQ.Context context;
/*  21:    */   String host;
/*  22:    */   int port;
/*  23: 24 */   public static boolean debug = false;
/*  24:    */   
/*  25:    */   public GenericZMQConnection(Type type, int port)
/*  26:    */   {
/*  27: 36 */     Initialize(type, "*", port);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public GenericZMQConnection(Type type, String host, int port)
/*  31:    */   {
/*  32: 40 */     Initialize(type, host, port);
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void Initialize(Type type, String host, int port)
/*  36:    */   {
/*  37: 44 */     this.type = type;
/*  38: 45 */     this.host = host;
/*  39: 46 */     this.port = port;
/*  40:    */     
/*  41: 48 */     this.context = ZMQ.context(1);
/*  42: 49 */     switch (type)
/*  43:    */     {
/*  44:    */     case PUB: 
/*  45: 51 */       this.socket = this.context.socket(3);
/*  46: 52 */       break;
/*  47:    */     case REP: 
/*  48: 54 */       this.socket = this.context.socket(4);
/*  49: 55 */       break;
/*  50:    */     case SUB: 
/*  51: 57 */       this.socket = this.context.socket(1);
/*  52: 58 */       break;
/*  53:    */     case REQ: 
/*  54: 60 */       this.socket = this.context.socket(2);
/*  55: 61 */       this.socket.subscribe("");
/*  56:    */     }
/*  57: 64 */     if (this.socket == null) {
/*  58: 65 */       new RuntimeException("Couldn't create socket!");
/*  59:    */     }
/*  60: 67 */     String port_s = String.valueOf(port);
/*  61: 68 */     if (host.contains("*")) {
/*  62: 69 */       this.socket.bind("tcp://*:" + port_s);
/*  63:    */     } else {
/*  64: 71 */       this.socket.connect("tcp://" + host + ":" + port_s);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void kill()
/*  69:    */   {
/*  70: 75 */     this.socket.close();
/*  71: 76 */     this.context.term();
/*  72: 77 */     this.type = null;
/*  73: 78 */     this.host = null;
/*  74: 79 */     this.port = -1;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public synchronized String request(String request)
/*  78:    */   {
/*  79: 84 */     if (this.type != Type.REQ) {
/*  80: 85 */       return "";
/*  81:    */     }
/*  82: 86 */     if (debug) {
/*  83: 87 */       System.out.println("Send request: " + request);
/*  84:    */     }
/*  85: 88 */     this.socket.send(request.getBytes());
/*  86: 89 */     byte[] reply = this.socket.recv(0);
/*  87: 90 */     String reply_s = new String(reply);
/*  88: 91 */     if (debug) {
/*  89: 92 */       System.out.println("Recieved reply (" + reply_s.length() + "): " + reply_s);
/*  90:    */     }
/*  91: 93 */     return reply_s;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void say(String message)
/*  95:    */   {
/*  96: 97 */     if (this.type != Type.PUB) {
/*  97: 98 */       return;
/*  98:    */     }
/*  99: 99 */     if (debug) {
/* 100:100 */       System.out.println("Send message: " + message);
/* 101:    */     }
/* 102:101 */     this.socket.send(message.getBytes());
/* 103:    */   }
/* 104:    */   
/* 105:    */   public synchronized String getRequest()
/* 106:    */   {
/* 107:105 */     if ((this.type != Type.REP) || (this.haveRequest)) {
/* 108:106 */       return "";
/* 109:    */     }
/* 110:107 */     byte[] request = this.socket.recv(0);
/* 111:108 */     String request_s = new String(request);
/* 112:109 */     if (debug) {
/* 113:110 */       System.out.println("Recieved request (" + request_s.length() + "): " + request_s);
/* 114:    */     }
/* 115:111 */     this.haveRequest = true;
/* 116:112 */     return request_s;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public synchronized void reply(String reply)
/* 120:    */   {
/* 121:116 */     if ((this.type != Type.REP) || (!this.haveRequest)) {
/* 122:117 */       return;
/* 123:    */     }
/* 124:118 */     this.socket.send(reply.getBytes());
/* 125:119 */     if (debug) {
/* 126:120 */       System.out.println("Sent reply: " + reply);
/* 127:    */     }
/* 128:121 */     this.haveRequest = false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String listen()
/* 132:    */   {
/* 133:125 */     if (this.type != Type.SUB) {
/* 134:126 */       return "";
/* 135:    */     }
/* 136:127 */     byte[] output = this.socket.recv(1);
/* 137:128 */     if ((output == null) || (output.length < 3)) {
/* 138:129 */       return "";
/* 139:    */     }
/* 140:130 */     String output_s = new String(output);
/* 141:131 */     if (debug) {
/* 142:132 */       System.out.println("Recieved output (" + output_s.length() + "): " + output_s);
/* 143:    */     }
/* 144:133 */     return output_s;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public static void REQDemo()
/* 148:    */   {
/* 149:137 */     GenericZMQConnection portFinder = new GenericZMQConnection(Type.REQ, 5700);
/* 150:138 */     String request = "";
/* 151:139 */     String reply = "";
/* 152:140 */     Scanner in = new Scanner(System.in);
/* 153:    */     for (;;)
/* 154:    */     {
/* 155:142 */       System.out.println("Request>>> ");
/* 156:143 */       request = in.nextLine();
/* 157:144 */       if (request.length() < 1) {
/* 158:145 */         request = "{'action':'getPorts'}";
/* 159:    */       }
/* 160:147 */       if (request.equals("exit")) {
/* 161:    */         break;
/* 162:    */       }
/* 163:149 */       reply = portFinder.request(request);
/* 164:150 */       System.out.println("Reply>> \"" + reply + "\"");
/* 165:    */     }
/* 166:152 */     in.close();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static void REPDemo()
/* 170:    */   {
/* 171:156 */     GenericZMQConnection portFinder = new GenericZMQConnection(Type.REQ, "meta.csail.mit.edu", 5700);
/* 172:157 */     String request = "";
/* 173:158 */     String reply = "";
/* 174:159 */     Scanner in = new Scanner(System.in);
/* 175:    */     for (;;)
/* 176:    */     {
/* 177:161 */       request = portFinder.getRequest();
/* 178:162 */       System.out.println("Request>> \"" + request + "\"");
/* 179:163 */       System.out.println("Reply>>> ");
/* 180:164 */       reply = in.nextLine();
/* 181:165 */       if (reply.length() < 1) {
/* 182:166 */         reply = "[{\"name\":\"axis\",\"port\":5711},{\"name\":\"harvard\",\"port\":5710},{\"name\":\"harvard2\",\"port\":5712}]";
/* 183:    */       }
/* 184:168 */       if (reply.equals("exit")) {
/* 185:    */         break;
/* 186:    */       }
/* 187:170 */       portFinder.reply(reply);
/* 188:    */     }
/* 189:172 */     in.close();
/* 190:    */   }
/* 191:    */   
/* 192:    */   public static void SUBDemo(int port)
/* 193:    */   {
/* 194:176 */     System.out.println("Subscribe Demo");
/* 195:177 */     GenericZMQConnection portFinder = new GenericZMQConnection(Type.SUB, port);
/* 196:    */     for (;;)
/* 197:    */     {
/* 198:179 */       String output = portFinder.listen();
/* 199:180 */       if (output.length() > 2) {
/* 200:181 */         System.out.println("Received: " + output);
/* 201:    */       }
/* 202:    */       try
/* 203:    */       {
/* 204:183 */         Thread.sleep(1L);
/* 205:    */       }
/* 206:    */       catch (InterruptedException e)
/* 207:    */       {
/* 208:185 */         e.printStackTrace();
/* 209:    */       }
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static void PUBDemo(String host, int port)
/* 214:    */   {
/* 215:191 */     System.out.println("Publish Demo");
/* 216:192 */     GenericZMQConnection portFinder = new GenericZMQConnection(Type.PUB, host, port);
/* 217:193 */     String message = "";
/* 218:194 */     Scanner in = new Scanner(System.in);
/* 219:    */     for (;;)
/* 220:    */     {
/* 221:196 */       System.out.println("Say>>> ");
/* 222:197 */       message = in.nextLine();
/* 223:198 */       if (message.length() < 1) {
/* 224:199 */         message = "{'action':'getPorts'}";
/* 225:    */       }
/* 226:201 */       if (message.equals("exit")) {
/* 227:    */         break;
/* 228:    */       }
/* 229:203 */       portFinder.say(message);
/* 230:    */     }
/* 231:205 */     in.close();
/* 232:    */   }
/* 233:    */   
/* 234:    */   public static void main(String[] args)
/* 235:    */   {
/* 236:209 */     SUBDemo(5711);
/* 237:    */   }
/* 238:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.infrastructure.GenericZMQConnection
 * JD-Core Version:    0.7.0.1
 */