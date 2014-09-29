/*   1:    */ package adamKraft;
/*   2:    */ 
/*   3:    */ import adam.RPCBox;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Connections.NetWireError;
/*   6:    */ import connections.Connections.NetWireException;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class WiredBoxB
/*  16:    */ {
/*  17:    */   public static final String SERVICE_NAME = "Beryl Wire Endpoint";
/*  18:    */   public static final String GET_MOVIE_TITLES_METHOD = "getMovieTitles";
/*  19:    */   public static final String DO_MOVIE_METHOD = "run";
/*  20:    */   protected WiredBox service;
/*  21:    */   protected Listener listener;
/*  22:    */   
/*  23:    */   protected static class Listener
/*  24:    */     implements WiredBox
/*  25:    */   {
/*  26: 27 */     private List<Map> buffer = new ArrayList();
/*  27:    */     private Object uuid;
/*  28:    */     
/*  29:    */     public Listener()
/*  30:    */     {
/*  31: 30 */       Connections.getPorts(this).addSignalProcessor("doIt");
/*  32:    */     }
/*  33:    */     
/*  34:    */     public synchronized void reset()
/*  35:    */     {
/*  36: 34 */       this.uuid = null;
/*  37: 35 */       this.buffer = new ArrayList();
/*  38:    */     }
/*  39:    */     
/*  40:    */     public synchronized List<Map> fetchVat()
/*  41:    */     {
/*  42: 39 */       return this.buffer;
/*  43:    */     }
/*  44:    */     
/*  45:    */     public synchronized void setUUID(Object uuid)
/*  46:    */     {
/*  47: 43 */       this.uuid = uuid;
/*  48:    */     }
/*  49:    */     
/*  50:    */     public synchronized void doIt(Object arg)
/*  51:    */     {
/*  52: 47 */       if ((arg instanceof RuntimeException)) {
/*  53: 48 */         throw ((RuntimeException)arg);
/*  54:    */       }
/*  55: 50 */       if (!(arg instanceof String))
/*  56:    */       {
/*  57: 53 */         Map<Object, List<Map>> wrapper = (Map)arg;
/*  58: 54 */         if (this.uuid != null)
/*  59:    */         {
/*  60: 55 */           List<Map> farg = (List)wrapper.get(this.uuid);
/*  61: 56 */           if (farg != null) {
/*  62: 57 */             if (farg.size() == 0)
/*  63:    */             {
/*  64: 59 */               System.out.println("Warning: zero-length list not expected");
/*  65: 60 */               notifyAll();
/*  66:    */             }
/*  67:    */             else
/*  68:    */             {
/*  69: 62 */               this.buffer.addAll(farg);
/*  70: 63 */               notifyAll();
/*  71:    */             }
/*  72:    */           }
/*  73:    */         }
/*  74:    */       }
/*  75:    */     }
/*  76:    */     
/*  77:    */     public String getName()
/*  78:    */     {
/*  79: 70 */       return null;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public WiredBoxB()
/*  84:    */     throws Connections.NetWireException
/*  85:    */   {
/*  86: 79 */     this.service = Connections.subscribe("Beryl Wire Endpoint");
/*  87: 80 */     this.listener = new Listener();
/*  88: 81 */     Connections.wire(this.service, this.listener);
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected WiredBoxB(int notUsed)
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95: 91 */       this.service = Connections.subscribe("Beryl Wire Endpoint");
/*  96: 92 */       this.listener = new Listener();
/*  97: 93 */       Connections.wire(this.service, this.listener);
/*  98:    */     }
/*  99:    */     catch (Connections.NetWireException e)
/* 100:    */     {
/* 101: 96 */       Mark.err(new Object[] {"There was a NetWireException error initializing the connection to Co57. Functionality is impaired." });
/* 102: 97 */       this.service = null;
/* 103: 98 */       this.listener = null;
/* 104:    */     }
/* 105:    */     catch (Connections.NetWireError e)
/* 106:    */     {
/* 107:102 */       Mark.err(new Object[] {"There was a NetWireError error initializing the connection to Co57. Functionality is impaired." });
/* 108:103 */       this.service = null;
/* 109:104 */       this.listener = null;
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public List<String> getMovieTitles()
/* 114:    */   {
/* 115:110 */     return (List)((RPCBox)this.service).rpc("getMovieTitles", new Object[0]);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void doMovie(String title)
/* 119:    */   {
/* 120:119 */     Mark.say(
/* 121:    */     
/* 122:121 */       new Object[] { "Calling doMovie on video", title, "in WiredBoxB instance" });this.listener.setUUID(((RPCBox)this.service).rpc("run", new Object[] { title }));
/* 123:    */   }
/* 124:    */   
/* 125:    */   public List<Map> fetchVat(String movieTitle)
/* 126:    */   {
/* 127:125 */     Mark.say(
/* 128:    */     
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:144 */       new Object[] { "fetchVat working on", movieTitle });
/* 147:126 */     synchronized (this.listener)
/* 148:    */     {
/* 149:127 */       this.listener.reset();
/* 150:    */       
/* 151:    */ 
/* 152:130 */       doMovie(movieTitle);
/* 153:    */       try
/* 154:    */       {
/* 155:134 */         this.listener.wait();
/* 156:    */       }
/* 157:    */       catch (InterruptedException e)
/* 158:    */       {
/* 159:136 */         e.printStackTrace();
/* 160:    */       }
/* 161:138 */       List result = this.listener.fetchVat();
/* 162:139 */       if ((result.size() == 1) && ((result.get(0) instanceof Throwable))) {
/* 163:140 */         throw new RuntimeException((Throwable)result.get(0));
/* 164:    */       }
/* 165:142 */       return result;
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static void main(String[] args)
/* 170:    */     throws Throwable
/* 171:    */   {
/* 172:147 */     WiredBoxB c = new WiredBoxB();
/* 173:148 */     List<String> titles = c.getMovieTitles();
/* 174:149 */     int n = 1;
/* 175:150 */     if (titles.size() > n)
/* 176:    */     {
/* 177:151 */       System.out.println("doing movie " + (String)titles.get(n));
/* 178:152 */       List<Map> result = c.fetchVat((String)titles.get(n));
/* 179:153 */       System.out.println("Got VAT with " + result.size() + " elements");
/* 180:154 */       System.out.println(result);
/* 181:    */     }
/* 182:    */     else
/* 183:    */     {
/* 184:156 */       System.out.println("get movie titles returned no titles.");
/* 185:    */     }
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.WiredBoxB
 * JD-Core Version:    0.7.0.1
 */