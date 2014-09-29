/*   1:    */ package bridge.infrastructure.wires;
/*   2:    */ 
/*   3:    */ import bridge.utils.logging.Logger;
/*   4:    */ import java.util.Collections;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.LinkedList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Observable;
/*   9:    */ import java.util.Observer;
/*  10:    */ 
/*  11:    */ public class Wire
/*  12:    */   implements Observer
/*  13:    */ {
/*  14:  8 */   boolean verbose = false;
/*  15:    */   public Connectable source;
/*  16:    */   public Ported target;
/*  17: 12 */   public static Object VIEWER = "viewer";
/*  18: 13 */   public static Object INPUT = "input";
/*  19: 14 */   public static Object OUTPUT = "output";
/*  20: 15 */   public static Object CLEAR = "clear";
/*  21: 16 */   public static Object STOP = "stop";
/*  22: 18 */   public Object sourcePort = OUTPUT;
/*  23: 19 */   public Object targetPort = INPUT;
/*  24: 21 */   boolean connected = true;
/*  25: 23 */   private List<WireListener> wireListeners = Collections.synchronizedList(new LinkedList());
/*  26:    */   
/*  27:    */   protected void notifyWireListeners(Object transmitting, boolean stillTransmitting)
/*  28:    */   {
/*  29: 25 */     synchronized (this.wireListeners)
/*  30:    */     {
/*  31: 26 */       Iterator<WireListener> iListeners = this.wireListeners.iterator();
/*  32: 27 */       while (iListeners.hasNext())
/*  33:    */       {
/*  34: 28 */         WireListener listener = (WireListener)iListeners.next();
/*  35: 29 */         if (stillTransmitting) {
/*  36: 30 */           listener.wireStartTransmitting(this, transmitting);
/*  37:    */         } else {
/*  38: 32 */           listener.wireDoneTransmitting(this, transmitting);
/*  39:    */         }
/*  40:    */       }
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void addWireListener(WireListener wl)
/*  45:    */   {
/*  46: 38 */     this.wireListeners.add(wl);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static Wire wire(Connectable source, Ported target)
/*  50:    */   {
/*  51: 43 */     fine("Creating wire from " + OUTPUT + " to " + INPUT);
/*  52: 44 */     return new Wire(source, OUTPUT, target, INPUT);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Wire wire(Connectable source, Object sourcePort, Ported target, Object targetPort)
/*  56:    */   {
/*  57: 52 */     fine("Creating wire from " + sourcePort + " to " + targetPort);
/*  58: 53 */     return new Wire(source, sourcePort, target, targetPort);
/*  59:    */   }
/*  60:    */   
/*  61:    */   private Wire(Connectable source, Ported target)
/*  62:    */   {
/*  63: 60 */     this.source = source;this.target = target;
/*  64: 61 */     this.source.addObserver(this);
/*  65: 62 */     notifyWireCreationListeners();
/*  66:    */   }
/*  67:    */   
/*  68:    */   private Wire(Connectable source, Object sourcePort, Ported target, Object targetPort)
/*  69:    */   {
/*  70: 69 */     this.source = source;this.target = target;
/*  71: 70 */     this.source.addObserver(this);
/*  72: 71 */     this.sourcePort = sourcePort;
/*  73: 72 */     this.targetPort = targetPort;
/*  74: 73 */     notifyWireCreationListeners();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void disconnect()
/*  78:    */   {
/*  79: 76 */     this.connected = false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void connect()
/*  83:    */   {
/*  84: 77 */     this.connected = true;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void update(Observable observable, Object x)
/*  88:    */   {
/*  89: 80 */     if (!this.connected) {
/*  90: 80 */       return;
/*  91:    */     }
/*  92: 81 */     fine("x/sourcePort/targetPort = " + x + ", " + this.sourcePort + ", " + this.targetPort);
/*  93: 84 */     if (x == null) {
/*  94: 84 */       return;
/*  95:    */     }
/*  96: 86 */     if (this.sourcePort.equals(x))
/*  97:    */     {
/*  98:    */       try
/*  99:    */       {
/* 100: 88 */         sourceOutput = this.source.getOutput(this.sourcePort);
/* 101:    */       }
/* 102:    */       catch (Exception ignore)
/* 103:    */       {
/* 104:    */         Object sourceOutput;
/* 105: 91 */         warning("Wire transfer failed when calling getOutput in source " + this.source.getClass() + ", port " + this.sourcePort);
/* 106: 92 */         ignore.printStackTrace();
/* 107: 93 */         return;
/* 108:    */       }
/* 109:    */       try
/* 110:    */       {
/* 111: 96 */         if (this.verbose) {
/* 112: 97 */           info(
/* 113:    */           
/* 114: 99 */             "Moving information on wire from the " + this.source.getClass() + ", port " + this.sourcePort + ", downstream to the " + this.target.getClass() + ", port " + this.targetPort);
/* 115:    */         }
/* 116:101 */         notifyWireListeners(sourceOutput, true);
/* 117:102 */         this.target.setInput(sourceOutput, this.targetPort);
/* 118:103 */         notifyWireListeners(sourceOutput, false); return;
/* 119:    */       }
/* 120:    */       catch (Exception ignore)
/* 121:    */       {
/* 122:    */         Object sourceOutput;
/* 123:107 */         warning(
/* 124:108 */           "Wire transfer failed when calling setInput in target " + this.target.getClass() + ", port " + this.targetPort + ". Target could not accept " + sourceOutput.toString());
/* 125:109 */         ignore.printStackTrace();
/* 126:110 */         return;
/* 127:    */       }
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setVerbose(boolean b)
/* 132:    */   {
/* 133:119 */     this.verbose = b;
/* 134:    */   }
/* 135:    */   
/* 136:122 */   private static List<WireCreationListener> wireCreationListeners = Collections.synchronizedList(new LinkedList());
/* 137:    */   
/* 138:    */   public static void addWireCreationListener(WireCreationListener wcl)
/* 139:    */   {
/* 140:124 */     wireCreationListeners.add(wcl);
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected void notifyWireCreationListeners()
/* 144:    */   {
/* 145:127 */     synchronized (wireCreationListeners)
/* 146:    */     {
/* 147:128 */       Iterator<WireCreationListener> iCreationListeners = wireCreationListeners.iterator();
/* 148:129 */       while (iCreationListeners.hasNext())
/* 149:    */       {
/* 150:130 */         WireCreationListener creationListener = (WireCreationListener)iCreationListeners.next();
/* 151:131 */         creationListener.wireCreated(this);
/* 152:    */       }
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public static void fine(Object s)
/* 157:    */   {
/* 158:137 */     Logger.getLogger("wires.Wire").fine(s);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public static void info(Object s)
/* 162:    */   {
/* 163:140 */     Logger.getLogger("wires.Wire").info(s);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static void warning(Object s)
/* 167:    */   {
/* 168:143 */     Logger.getLogger("wires.Wire").warning(s);
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.infrastructure.wires.Wire
 * JD-Core Version:    0.7.0.1
 */