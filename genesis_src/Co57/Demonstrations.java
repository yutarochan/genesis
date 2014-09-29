/*   1:    */ package Co57;
/*   2:    */ 
/*   3:    */ import Co57.services.Co57LivePortFinder;
/*   4:    */ import Co57.services.Co57LiveStreamManager;
/*   5:    */ import Co57.services.Co57Passthrough;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Scanner;
/*  14:    */ import java.util.Set;
/*  15:    */ import utils.Mark;
/*  16:    */ 
/*  17:    */ public class Demonstrations
/*  18:    */   implements WiredBox
/*  19:    */ {
/*  20:    */   public static void main(String[] args)
/*  21:    */     throws Throwable
/*  22:    */   {
/*  23: 28 */     Scanner in = new Scanner(System.in);
/*  24: 29 */     Mark.say(new Object[] {"Co57 Demonstrations" });
/*  25: 30 */     Mark.say(new Object[] {"Options:" });
/*  26: 31 */     Mark.say(new Object[] {"  A) Mind's Eye, Beryl Video Processing for Events and Traces" });
/*  27: 32 */     Mark.say(new Object[] {"  B) Live Stream data connection from anamoly detector" });
/*  28: 33 */     System.out.print(">>> ");
/*  29: 34 */     String response = in.nextLine();
/*  30: 35 */     in.close();
/*  31: 36 */     if (response.toLowerCase().contains("a"))
/*  32:    */     {
/*  33: 37 */       Mark.say(new Object[] {"Doing Mind's Eye demo..." });
/*  34: 38 */       mindsEyeDemo();
/*  35:    */     }
/*  36: 40 */     if (response.toLowerCase().contains("b"))
/*  37:    */     {
/*  38: 41 */       Mark.say(new Object[] {"Doing live stream demo..." });
/*  39: 42 */       liveStreamDemo();
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void process(Object o)
/*  44:    */   {
/*  45: 47 */     Mark.say(
/*  46: 48 */       new Object[] { "Got message: " + o });
/*  47:    */   }
/*  48:    */   
/*  49:    */   static void mindsEyeDemo()
/*  50:    */     throws Throwable
/*  51:    */   {
/*  52: 51 */     Mark.say(
/*  53:    */     
/*  54:    */ 
/*  55:    */ 
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
/*  77: 76 */       new Object[] { "DEMO: Using BerylControls to get Movie List..." });List<String> movies = BerylControls.getMovieList();Mark.say(new Object[] { "DEMO: Showing the top ten movies..." });int i = 0;
/*  78: 56 */     for (String title : movies)
/*  79:    */     {
/*  80: 57 */       Mark.say(new Object[] {"Movie found: " + title });
/*  81: 58 */       i++;
/*  82: 59 */       if (i >= 10) {
/*  83:    */         break;
/*  84:    */       }
/*  85:    */     }
/*  86: 63 */     Mark.say(new Object[] {"DEMO: Wiring in a BerylVerbTranslator to listen for Co57 Output..." });
/*  87:    */     
/*  88: 65 */     BerylVerbTranslator wireClient = new BerylVerbTranslator("BerylVerbTranslator", Co57Passthrough.ZMQ_SERVER_WIRED_BOX_SERVICE);
/*  89:    */     
/*  90: 67 */     Mark.say(new Object[] {"DEMO: Using BerylControls to start capture-26.mp4..." });
/*  91: 68 */     BerylControls.doMovie("capture-26.mp4");
/*  92:    */     
/*  93: 70 */     Mark.say(new Object[] {"DEMO: Watch for BerylVerbTranslator output, or check http://meta.csail.mit.edu:8080/ to see recent data from Co57" });
/*  94: 71 */     int sec = 0;
/*  95:    */     for (;;)
/*  96:    */     {
/*  97: 73 */       Mark.say(new Object[] {"Time:", Integer.valueOf(sec++ * 10) });
/*  98: 74 */       Thread.sleep(10000L);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   static void liveStreamDemo()
/* 103:    */   {
/* 104: 80 */     Mark.say(
/* 105:    */     
/* 106:    */ 
/* 107:    */ 
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:112 */       new Object[] { "DEMO: Creating Demo Box..." });Demonstrations demoer = new Demonstrations();Connections.getPorts(demoer).addSignalProcessor("process");Mark.say(new Object[] { "DEMO: Connecting to Co57 Port Finder..." });Map<String, Integer> ports = Co57LivePortFinder.getPorts();Iterator localIterator = ports.keySet().iterator();
/* 137: 90 */     if (localIterator.hasNext())
/* 138:    */     {
/* 139: 90 */       String name = (String)localIterator.next();
/* 140:    */       
/* 141: 92 */       int port = ((Integer)ports.get(name)).intValue();
/* 142:    */       
/* 143: 94 */       Mark.say(new Object[] {"DEMO: Connecting to Co57 on (name,port): (" + name + "," + port + ") ..." });
/* 144: 95 */       String wiredPort = Co57LiveStreamManager.connectTo(port);
/* 145:    */       
/* 146: 97 */       Mark.say(new Object[] {"DEMO: Getting Net Wired Box that Streams the output..." });
/* 147: 98 */       WiredBox streamer = Co57LiveStreamManager.getStreamer();
/* 148:    */       
/* 149:100 */       Mark.say(new Object[] {"DEMO: Connecting Demo to streamer..." });
/* 150:101 */       Connections.wire(wiredPort, streamer, demoer);
/* 151:    */     }
/* 152:    */     try
/* 153:    */     {
/* 154:    */       for (;;)
/* 155:    */       {
/* 156:107 */         Thread.sleep(10L);
/* 157:    */       }
/* 158:    */     }
/* 159:    */     catch (InterruptedException e)
/* 160:    */     {
/* 161:109 */       e.printStackTrace();
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getName()
/* 166:    */   {
/* 167:116 */     return "Demo";
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.Demonstrations
 * JD-Core Version:    0.7.0.1
 */