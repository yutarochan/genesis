/*   1:    */ package Co57;
/*   2:    */ 
/*   3:    */ import Co57.services.Co57Passthrough;
/*   4:    */ import adamKraft.WiredBoxB;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Connections.NetWireException;
/*   8:    */ import connections.Ports;
/*   9:    */ import connections.WiredBox;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class BerylControls
/*  15:    */   extends AbstractWiredBox
/*  16:    */ {
/*  17: 27 */   public static String BERYL_CONTROLS = "Beryl Controls";
/*  18: 29 */   public static String RESET_COMMAND = "{\"command\":\"reset\"}";
/*  19: 31 */   private static BerylControls _bc = null;
/*  20: 33 */   private WiredBoxB wiredBoxB = null;
/*  21:    */   
/*  22:    */   public static boolean reset()
/*  23:    */   {
/*  24:    */     try
/*  25:    */     {
/*  26: 37 */       BerylControls bc = getControls();
/*  27:    */       
/*  28: 39 */       bc.doReset();
/*  29:    */     }
/*  30:    */     catch (Connections.NetWireException nwe)
/*  31:    */     {
/*  32: 41 */       nwe.printStackTrace();
/*  33: 42 */       return false;
/*  34:    */     }
/*  35:    */     try
/*  36:    */     {
/*  37: 45 */       Thread.sleep(10000L);
/*  38:    */     }
/*  39:    */     catch (InterruptedException e)
/*  40:    */     {
/*  41: 47 */       e.printStackTrace();
/*  42:    */     }
/*  43: 49 */     return true;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static List<String> getMovieList()
/*  47:    */   {
/*  48:    */     try
/*  49:    */     {
/*  50: 55 */       bc = getControls();
/*  51:    */     }
/*  52:    */     catch (Connections.NetWireException nwe)
/*  53:    */     {
/*  54:    */       BerylControls bc;
/*  55: 57 */       nwe.printStackTrace();
/*  56: 58 */       return new ArrayList();
/*  57:    */     }
/*  58:    */     BerylControls bc;
/*  59: 60 */     return bc.doGetMovieList();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static void doMovie(String title)
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66: 66 */       bc = getControls();
/*  67:    */     }
/*  68:    */     catch (Connections.NetWireException nwe)
/*  69:    */     {
/*  70:    */       BerylControls bc;
/*  71: 68 */       nwe.printStackTrace(); return;
/*  72:    */     }
/*  73:    */     BerylControls bc;
/*  74: 71 */     bc.doStartMovie(title);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public List<String> doGetMovieList()
/*  78:    */   {
/*  79: 75 */     if (this.wiredBoxB == null) {
/*  80:    */       try
/*  81:    */       {
/*  82: 77 */         this.wiredBoxB = new WiredBoxB();
/*  83:    */       }
/*  84:    */       catch (Connections.NetWireException e)
/*  85:    */       {
/*  86: 79 */         Mark.err(new Object[] {"Unable to get movie list." });
/*  87: 80 */         return new ArrayList();
/*  88:    */       }
/*  89:    */     }
/*  90: 83 */     return this.wiredBoxB.getMovieTitles();
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void doStartMovie(String title)
/*  94:    */   {
/*  95: 87 */     if (this.wiredBoxB == null) {
/*  96:    */       try
/*  97:    */       {
/*  98: 89 */         this.wiredBoxB = new WiredBoxB();
/*  99:    */       }
/* 100:    */       catch (Connections.NetWireException e)
/* 101:    */       {
/* 102: 91 */         Mark.err(new Object[] {"Unable to start movie." });
/* 103: 92 */         return;
/* 104:    */       }
/* 105:    */     }
/* 106: 95 */     this.wiredBoxB.doMovie(title);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void sendMessage(String msg)
/* 110:    */   {
/* 111: 99 */     Connections.getPorts(this).transmit(msg);
/* 112:    */   }
/* 113:    */   
/* 114:    */   private static BerylControls getControls()
/* 115:    */     throws Connections.NetWireException
/* 116:    */   {
/* 117:103 */     if (_bc == null)
/* 118:    */     {
/* 119:104 */       _bc = new BerylControls();
/* 120:105 */       WiredBox box = Connections.subscribe(Co57Passthrough.ZMQ_SERVER_WIRED_BOX_SERVICE);
/* 121:    */       
/* 122:107 */       Connections.wire(box, _bc);
/* 123:108 */       Connections.wire(_bc, box);
/* 124:    */     }
/* 125:110 */     return _bc;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void doReset()
/* 129:    */   {
/* 130:114 */     Connections.getPorts(this).transmit(RESET_COMMAND);
/* 131:    */   }
/* 132:    */   
/* 133:    */   private BerylControls()
/* 134:    */   {
/* 135:118 */     setName(BERYL_CONTROLS);
/* 136:    */     try
/* 137:    */     {
/* 138:121 */       this.wiredBoxB = new WiredBoxB();
/* 139:    */     }
/* 140:    */     catch (Connections.NetWireException e)
/* 141:    */     {
/* 142:123 */       Mark.err(new Object[] {"Unable to create wired box, some features may be unavailable" });
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static void main(String[] args)
/* 147:    */   {
/* 148:128 */     reset();
/* 149:129 */     Mark.say(new Object[] {"Reset Complete" });
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     Co57.BerylControls
 * JD-Core Version:    0.7.0.1
 */