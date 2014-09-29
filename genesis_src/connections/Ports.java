/*   1:    */ package connections;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ 
/*   9:    */ public class Ports
/*  10:    */ {
/*  11: 13 */   private HashMap<String, Port> ports = new HashMap();
/*  12: 15 */   private HashMap<String, ArrayList<String>> runnables = new HashMap();
/*  13:    */   
/*  14:    */   public Port getPort(String name)
/*  15:    */   {
/*  16: 21 */     Object o = this.ports.get(name);
/*  17: 22 */     if (o != null) {
/*  18: 23 */       return (Port)o;
/*  19:    */     }
/*  20: 26 */     Port port = new Port(name);
/*  21: 27 */     this.ports.put(name, port);
/*  22: 28 */     return port;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public void transmit(String name, Object signal)
/*  26:    */   {
/*  27: 36 */     getPort(name).transmit(signal);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void transmit(Object signal)
/*  31:    */   {
/*  32: 43 */     transmit("output", signal);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void transmitWithTimer(String name, Object signal)
/*  36:    */   {
/*  37: 47 */     transmit(1000L, name, signal);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void transmitWithTimer(Object signal)
/*  41:    */   {
/*  42: 51 */     transmit(1000L, signal);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void transmit(long timeout, String name, Object signal)
/*  46:    */   {
/*  47: 55 */     TimerThread timer = new TimerThread(timeout);
/*  48: 56 */     timer.start();
/*  49: 57 */     getPort(name).transmit(signal);
/*  50: 58 */     timer.quit();
/*  51:    */   }
/*  52:    */   
/*  53:    */   private class TimerThread
/*  54:    */     extends Thread
/*  55:    */   {
/*  56:    */     long timeout;
/*  57:    */     long start;
/*  58: 66 */     boolean quit = false;
/*  59:    */     
/*  60:    */     public void quit()
/*  61:    */     {
/*  62: 69 */       this.quit = true;
/*  63:    */     }
/*  64:    */     
/*  65:    */     public TimerThread(long timeout)
/*  66:    */     {
/*  67: 73 */       this.timeout = timeout;
/*  68: 74 */       this.start = System.currentTimeMillis();
/*  69:    */     }
/*  70:    */     
/*  71:    */     public void run()
/*  72:    */     {
/*  73: 78 */       long end = System.currentTimeMillis();
/*  74: 79 */       if (end - this.start > this.timeout)
/*  75:    */       {
/*  76: 80 */         System.err.println(">>>>> Timer timed out in " + (end - this.start) + " milliseconds");
/*  77: 81 */         return;
/*  78:    */       }
/*  79: 83 */       if (this.quit) {
/*  80: 84 */         return;
/*  81:    */       }
/*  82:    */       try
/*  83:    */       {
/*  84: 87 */         Thread.sleep(100L);
/*  85:    */       }
/*  86:    */       catch (InterruptedException e)
/*  87:    */       {
/*  88: 90 */         System.err.println(">>>>> Timer sleep crashed");
/*  89:    */       }
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void transmit(long timeout, Object signal)
/*  94:    */   {
/*  95: 99 */     transmit(timeout, "output", signal);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void transmit(Object... objects) {}
/*  99:    */   
/* 100:    */   ArrayList<String> getSignalProcessors(String key)
/* 101:    */   {
/* 102:114 */     if (this.runnables.get(key) != null) {
/* 103:115 */       return (ArrayList)this.runnables.get(key);
/* 104:    */     }
/* 105:117 */     ArrayList<String> list = new ArrayList();
/* 106:118 */     this.runnables.put(key, list);
/* 107:119 */     return list;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Map<String, List<String>> getPortToMethodsMapping()
/* 111:    */   {
/* 112:123 */     return new HashMap(this.runnables);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void addSignalProcessor(String r)
/* 116:    */   {
/* 117:130 */     addSignalProcessor("input", r);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void addSignalProcessor(String portName, String methodName)
/* 121:    */   {
/* 122:134 */     if (Connections.isVerbose()) {
/* 123:135 */       System.out.println("Defining response for port " + portName);
/* 124:    */     }
/* 125:137 */     getSignalProcessors(portName).add(methodName);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public ArrayList<Port> getPorts()
/* 129:    */   {
/* 130:141 */     ArrayList<Port> result = new ArrayList();
/* 131:142 */     result.addAll(this.ports.values());
/* 132:143 */     return result;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void launch(String message)
/* 136:    */   {
/* 137:150 */     Wrapper wrapper = new Wrapper(message);
/* 138:151 */     wrapper.start();
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void launch(String port, String message)
/* 142:    */   {
/* 143:155 */     Wrapper wrapper = new Wrapper(port, message);
/* 144:156 */     wrapper.start();
/* 145:    */   }
/* 146:    */   
/* 147:    */   private class Wrapper
/* 148:    */     extends Thread
/* 149:    */   {
/* 150:    */     Object o;
/* 151:162 */     String port = "output";
/* 152:    */     
/* 153:    */     public Wrapper(Object o)
/* 154:    */     {
/* 155:165 */       this.o = o;
/* 156:    */     }
/* 157:    */     
/* 158:    */     public Wrapper(String port, Object o)
/* 159:    */     {
/* 160:169 */       this.port = port;
/* 161:170 */       this.o = o;
/* 162:    */     }
/* 163:    */     
/* 164:    */     public void run()
/* 165:    */     {
/* 166:175 */       Ports.this.transmit(this.port, this.o);
/* 167:    */     }
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.Ports
 * JD-Core Version:    0.7.0.1
 */