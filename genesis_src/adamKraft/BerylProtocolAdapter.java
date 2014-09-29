/*   1:    */ package adamKraft;
/*   2:    */ 
/*   3:    */ import adam.RPCBox;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Connections.NetWireException;
/*   6:    */ import connections.Ports;
/*   7:    */ import connections.WiredBox;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.UUID;
/*  14:    */ 
/*  15:    */ public class BerylProtocolAdapter
/*  16:    */   implements WiredBox
/*  17:    */ {
/*  18:    */   public static final String NAME = "Beryl protocol adapter";
/*  19:    */   protected static final String GET_MOVIE_TITLES_METHOD = "get_filenames";
/*  20:    */   public static final String DO_MOVIE_METHOD = "run";
/*  21:    */   public static final String BERYL_SERVICE_NAME = "beryl.co57.com";
/*  22:    */   public WiredBox stub;
/*  23:    */   protected WiredBox beryl;
/*  24: 23 */   protected List<Request> requests = new ArrayList();
/*  25: 24 */   protected boolean go = true;
/*  26: 25 */   protected boolean gorillaRules = false;
/*  27:    */   
/*  28:    */   public static class Stub
/*  29:    */     implements WiredBox
/*  30:    */   {
/*  31:    */     BerylProtocolAdapter owner;
/*  32:    */     
/*  33:    */     public String getName()
/*  34:    */     {
/*  35: 54 */       return null;
/*  36:    */     }
/*  37:    */     
/*  38:    */     public void proc(Object signal)
/*  39:    */     {
/*  40: 59 */       this.owner.proc(signal);
/*  41:    */     }
/*  42:    */     
/*  43:    */     public Stub(BerylProtocolAdapter owner)
/*  44:    */     {
/*  45: 64 */       this.owner = owner;
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   private static class Request
/*  50:    */   {
/*  51: 70 */     public UUID guid = UUID.randomUUID();
/*  52:    */     public String movieTitle;
/*  53: 72 */     public boolean issued = false;
/*  54:    */     
/*  55:    */     public Request(String title)
/*  56:    */     {
/*  57: 74 */       this.movieTitle = title;
/*  58:    */     }
/*  59:    */     
/*  60: 77 */     private List<Map> buffer = new ArrayList();
/*  61:    */     
/*  62:    */     public List<Map> enqueue(List<Map> in)
/*  63:    */     {
/*  64: 79 */       this.buffer.addAll(in);
/*  65: 80 */       return in.size() == 0 ? this.buffer : null;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public List<Map> altEnqueue(List<Map> in)
/*  69:    */     {
/*  70: 83 */       this.buffer.addAll(in);
/*  71: 84 */       return this.buffer;
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String getName()
/*  76:    */   {
/*  77: 91 */     return "Beryl protocol adapter";
/*  78:    */   }
/*  79:    */   
/*  80:    */   public synchronized List<String> getMovieTitles()
/*  81:    */   {
/*  82: 96 */     return (List)((RPCBox)this.beryl).rpc("get_filenames", new Object[0]);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public synchronized Object run(String movieTitle)
/*  86:    */   {
/*  87:100 */     Request r = new Request(movieTitle);
/*  88:101 */     this.requests.add(r);
/*  89:102 */     notifyAll();
/*  90:103 */     return r.guid;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected void doMovie(String title)
/*  94:    */   {
/*  95:107 */     ((RPCBox)this.beryl).rpc("run", new Object[] { title });
/*  96:    */   }
/*  97:    */   
/*  98:    */   private synchronized void serviceRequests()
/*  99:    */     throws InterruptedException
/* 100:    */   {
/* 101:111 */     while (this.go)
/* 102:    */     {
/* 103:112 */       wait();
/* 104:113 */       if ((this.requests.size() > 0) && 
/* 105:114 */         (!((Request)this.requests.get(0)).issued))
/* 106:    */       {
/* 107:115 */         doMovie(((Request)this.requests.get(0)).movieTitle);
/* 108:116 */         ((Request)this.requests.get(0)).issued = true;
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected void enqueue(List<Map> in, Request r)
/* 114:    */   {
/* 115:    */     List<Map> send;
/* 116:    */     List<Map> send;
/* 117:133 */     if (this.gorillaRules) {
/* 118:134 */       send = r.altEnqueue(in);
/* 119:    */     } else {
/* 120:136 */       send = r.enqueue(in);
/* 121:    */     }
/* 122:138 */     if (send != null)
/* 123:    */     {
/* 124:139 */       UUID guid = r.guid;
/* 125:140 */       Map<Object, List> wrapper = new HashMap();
/* 126:141 */       wrapper.put(guid, send);
/* 127:142 */       Connections.getPorts(this).transmit(wrapper);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public synchronized void proc(Object signal)
/* 132:    */   {
/* 133:148 */     if (signal != null)
/* 134:    */     {
/* 135:149 */       if (this.requests.size() > 0)
/* 136:    */       {
/* 137:150 */         List<Map> l = (List)signal;
/* 138:    */         
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:156 */         enqueue(l, (Request)this.requests.get(0));
/* 144:157 */         if ((l.size() == 0) || (this.gorillaRules))
/* 145:    */         {
/* 146:158 */           this.requests.remove(0);
/* 147:159 */           notifyAll();
/* 148:    */         }
/* 149:    */       }
/* 150:    */       else
/* 151:    */       {
/* 152:163 */         Connections.getPorts(this).transmit(
/* 153:164 */           new RuntimeException("somebody else is using the Beryl server right now. don't expect normal behavior."));
/* 154:    */       }
/* 155:    */     }
/* 156:    */     else {
/* 157:167 */       System.out.println("got null signal from the wires");
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public BerylProtocolAdapter()
/* 162:    */     throws Connections.NetWireException
/* 163:    */   {
/* 164:172 */     this(true);
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected BerylProtocolAdapter(boolean connectBeryl)
/* 168:    */     throws Connections.NetWireException
/* 169:    */   {
/* 170:176 */     if (connectBeryl)
/* 171:    */     {
/* 172:177 */       this.beryl = Connections.subscribe("beryl.co57.com");
/* 173:178 */       this.stub = new Stub(this);
/* 174:179 */       Connections.getPorts(this.stub).addSignalProcessor("proc");
/* 175:180 */       Connections.wire(this.beryl, this.stub);
/* 176:    */     }
/* 177:192 */     new Thread()
/* 178:    */     {
/* 179:    */       public void run()
/* 180:    */       {
/* 181:    */         try
/* 182:    */         {
/* 183:185 */           BerylProtocolAdapter.this.serviceRequests();
/* 184:    */         }
/* 185:    */         catch (InterruptedException e)
/* 186:    */         {
/* 187:188 */           e.printStackTrace();
/* 188:189 */           BerylProtocolAdapter.this.go = false;
/* 189:    */         }
/* 190:    */       }
/* 191:    */     }.start();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static void main(String[] args)
/* 195:    */     throws Throwable
/* 196:    */   {
/* 197:198 */     BerylProtocolAdapter box = new BerylProtocolAdapter();
/* 198:199 */     Connections.publish(box, "Beryl protocol adapter");
/* 199:    */     do
/* 200:    */     {
/* 201:201 */       Thread.sleep(1000L);
/* 202:202 */     } while (box.go);
/* 203:203 */     System.out.println("stopped");
/* 204:    */   }
/* 205:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.BerylProtocolAdapter
 * JD-Core Version:    0.7.0.1
 */