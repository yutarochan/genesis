/*   1:    */ package connections;
/*   2:    */ 
/*   3:    */ import adam.LibUtil;
/*   4:    */ import adam.WireClientEndpoint;
/*   5:    */ import connections.views.Adapter;
/*   6:    */ import connections.views.ViewerBox;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.lang.reflect.Method;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.Iterator;
/*  12:    */ import java.util.Set;
/*  13:    */ 
/*  14:    */ public class Port
/*  15:    */ {
/*  16:    */   public static final String OUTPUT = "output";
/*  17:    */   public static final String INPUT = "input";
/*  18:    */   public static final String VIEWER = "viewer";
/*  19:    */   public static final String A = "port a";
/*  20:    */   public static final String B = "port b";
/*  21:    */   public static final String C = "port c";
/*  22:    */   public static final String D = "port d";
/*  23:    */   public static final String E = "port e";
/*  24:    */   public static final String F = "port f";
/*  25:    */   public static final String UP = "up";
/*  26:    */   public static final String DOWN = "down";
/*  27:    */   public static final String RESET = "reset";
/*  28: 38 */   private HashMap<String, ArrayList<WiredBox>> destinations = new HashMap();
/*  29: 40 */   private ArrayList<String> keys = new ArrayList();
/*  30:    */   private String sourceName;
/*  31:    */   private WiredBox sourceBox;
/*  32:    */   private ArrayList<LinkedPortPair> linkedInputPortPairs;
/*  33:    */   public ArrayList<LinkedPortPair> linkedOutputPortPairs;
/*  34:    */   
/*  35:    */   public Port(String name)
/*  36:    */   {
/*  37: 51 */     this.sourceName = name;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void attach(WiredBox sourceBox, String destinationPortName, WiredBox destinationBox)
/*  41:    */   {
/*  42: 61 */     setSourceBox(sourceBox);
/*  43: 62 */     getDestinations(destinationPortName).add(destinationBox);
/*  44: 63 */     receiveByOthers(destinationPortName, destinationBox);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void detach(String destinationName, WiredBox destination)
/*  48:    */   {
/*  49: 68 */     getDestinations(destinationName).remove(destination);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public boolean isAttached(String destinationName, WiredBox destination)
/*  53:    */   {
/*  54: 72 */     return getDestinations(destinationName).contains(destination);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public ArrayList<WiredBox> getDestinations(String name)
/*  58:    */   {
/*  59: 80 */     ArrayList<WiredBox> o = (ArrayList)this.destinations.get(name);
/*  60: 81 */     if (o != null) {
/*  61: 82 */       return o;
/*  62:    */     }
/*  63: 85 */     ArrayList<WiredBox> list = new ArrayList();
/*  64: 86 */     this.destinations.put(name, list);
/*  65: 87 */     this.keys.add(name);
/*  66: 88 */     return list;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void transmit(Object signal)
/*  70:    */   {
/*  71:    */     ArrayList<WiredBox> list;
/*  72:    */     int x;
/*  73:108 */     for (Iterator<String> i = this.keys.iterator(); i.hasNext(); x < list.size())
/*  74:    */     {
/*  75:109 */       String key = (String)i.next();
/*  76:110 */       list = (ArrayList)this.destinations.get(key);
/*  77:111 */       Adapter adapter = null;
/*  78:112 */       ViewerBox sourceViewerBox = null;
/*  79:113 */       if (!LibUtil.isLib())
/*  80:    */       {
/*  81:116 */         adapter = Adapter.makeConnectionAdapter();
/*  82:117 */         sourceViewerBox = adapter.getViewerBox(getSourceBox());
/*  83:118 */         if (sourceViewerBox != null) {
/*  84:124 */           sourceViewerBox.setTemporaryColor();
/*  85:    */         }
/*  86:    */       }
/*  87:127 */       x = 0; continue;
/*  88:128 */       WiredBox destinationBox = (WiredBox)list.get(x);
/*  89:    */       
/*  90:130 */       ViewerBox destinationViewerBox = null;
/*  91:131 */       if (!LibUtil.isLib())
/*  92:    */       {
/*  93:132 */         destinationViewerBox = adapter.getViewerBox(destinationBox);
/*  94:133 */         if (destinationViewerBox != null) {
/*  95:136 */           destinationViewerBox.setTemporaryColor();
/*  96:    */         }
/*  97:138 */         if ((sourceViewerBox != null) && (destinationViewerBox != null)) {
/*  98:144 */           if ((Connections.isVerbose()) || (sourceViewerBox.isSelected()) || (destinationViewerBox.isSelected()))
/*  99:    */           {
/* 100:145 */             String source = "Nameless source";
/* 101:146 */             if ((getSourceBox() instanceof WiredBox)) {
/* 102:147 */               source = getSourceBox().getName();
/* 103:    */             }
/* 104:149 */             String destination = "Nameless destination";
/* 105:150 */             if ((destinationBox instanceof WiredBox)) {
/* 106:151 */               destination = destinationBox.getName();
/* 107:    */             }
/* 108:153 */             if (Connections.isVerbose()) {
/* 109:154 */               System.out.println(">>> " + source + " transmitting signal <" + signal.getClass() + ">\n---\n" + signal + 
/* 110:155 */                 "\n---\nfrom port " + this.sourceName + " to port " + key + " of " + destination);
/* 111:    */             }
/* 112:    */           }
/* 113:    */         }
/* 114:    */       }
/* 115:160 */       ArrayList<String> runnables = Connections.getPorts(destinationBox).getSignalProcessors(key);
/* 116:161 */       if (Connections.isVerbose()) {
/* 117:162 */         System.out.println(">>> Runnable count is " + runnables.size() + " for " + key);
/* 118:    */       }
/* 119:164 */       for (int r = 0; r < runnables.size(); r++)
/* 120:    */       {
/* 121:165 */         Object element = runnables.get(r);
/* 122:166 */         if (Connections.isVerbose()) {
/* 123:167 */           System.out.println(">>> Signal processor is " + element);
/* 124:    */         }
/* 125:169 */         if ((element instanceof String))
/* 126:    */         {
/* 127:170 */           String methodName = (String)runnables.get(r);
/* 128:171 */           Class[] parameters = { Object.class };
/* 129:172 */           Object[] arguments = { signal };
/* 130:173 */           Class<?> c = null;
/* 131:174 */           Method m = null;
/* 132:175 */           WireClientEndpoint.getInstance().hook(getSourceBox());
/* 133:    */           try
/* 134:    */           {
/* 135:184 */             c = destinationBox.getClass();
/* 136:    */             
/* 137:186 */             m = c.getMethod(methodName, parameters);
/* 138:    */             
/* 139:188 */             m.invoke(destinationBox, arguments);
/* 140:    */           }
/* 141:    */           catch (Exception e)
/* 142:    */           {
/* 143:192 */             if (!LibUtil.isLib()) {
/* 144:193 */               destinationViewerBox.setState(2);
/* 145:    */             }
/* 146:195 */             String error = ">>> Blew out while trying to apply method named " + methodName;
/* 147:196 */             error = error + " listening to port " + key;
/* 148:197 */             error = error + " in box " + destinationBox.getName();
/* 149:198 */             error = error + " on signal of class " + signal.getClass();
/* 150:199 */             System.err.println(error);
/* 151:200 */             e.printStackTrace();
/* 152:    */           }
/* 153:    */         }
/* 154:    */         else
/* 155:    */         {
/* 156:204 */           System.err.println("Unable to process " + element + " in " + destinationBox);
/* 157:    */         }
/* 158:    */       }
/* 159:127 */       x++;
/* 160:    */     }
/* 161:209 */     if (this.linkedOutputPortPairs != null) {
/* 162:210 */       transmitToOthers(signal);
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getSourceName()
/* 167:    */   {
/* 168:215 */     return this.sourceName;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public String toString()
/* 172:    */   {
/* 173:219 */     return "<Port: " + this.sourceName + ">";
/* 174:    */   }
/* 175:    */   
/* 176:    */   public WiredBox getSourceBox()
/* 177:    */   {
/* 178:223 */     return this.sourceBox;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setSourceBox(WiredBox sourceBox)
/* 182:    */   {
/* 183:227 */     if ((this.sourceBox != null) && (sourceBox != this.sourceBox)) {
/* 184:228 */       System.err.println("Ooops, changed box associated with a port from " + this.sourceBox + " to " + sourceBox);
/* 185:    */     }
/* 186:230 */     this.sourceBox = sourceBox;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Set<String> getDestinationNames()
/* 190:    */   {
/* 191:234 */     return this.destinations.keySet();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public ArrayList<WiredBox> getTargets()
/* 195:    */   {
/* 196:238 */     ArrayList<WiredBox> result = new ArrayList();
/* 197:239 */     for (ArrayList<WiredBox> list : this.destinations.values()) {
/* 198:240 */       result.addAll(list);
/* 199:    */     }
/* 200:242 */     return result;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void receiveByOthers(String destinationPortName, WiredBox destinationBox)
/* 204:    */   {
/* 205:261 */     ArrayList<LinkedPortPair> pairs = Connections.getPorts(destinationBox).getPort(destinationPortName).getLinkedInputPortPairs();
/* 206:262 */     for (LinkedPortPair pair : pairs) {
/* 207:263 */       getDestinations(pair.destinationPortName).add(pair.destinationBox);
/* 208:    */     }
/* 209:    */   }
/* 210:    */   
/* 211:    */   public void forwardTo(WiredBox sourceBox, WiredBox destinationBox)
/* 212:    */   {
/* 213:269 */     forwardTo("input", sourceBox, destinationBox);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public void forwardTo(String portName, WiredBox sourceBox, WiredBox destinationBox)
/* 217:    */   {
/* 218:273 */     forwardTo(portName, sourceBox, portName, destinationBox);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public void forwardTo(String sourcePortName, WiredBox sourceBox, String destinationPortName, WiredBox destinationBox)
/* 222:    */   {
/* 223:277 */     getLinkedInputPortPairs().add(new LinkedPortPair(sourcePortName, sourceBox, destinationPortName, destinationBox));
/* 224:    */   }
/* 225:    */   
/* 226:    */   private ArrayList<LinkedPortPair> getLinkedInputPortPairs()
/* 227:    */   {
/* 228:284 */     if (this.linkedInputPortPairs == null) {
/* 229:285 */       this.linkedInputPortPairs = new ArrayList();
/* 230:    */     }
/* 231:287 */     return this.linkedInputPortPairs;
/* 232:    */   }
/* 233:    */   
/* 234:    */   private void transmitToOthers(Object signal)
/* 235:    */   {
/* 236:295 */     for (LinkedPortPair pair : getLinkedOutputPortPairs()) {
/* 237:296 */       Connections.getPorts(pair.destinationBox).transmit(pair.destinationPortName, signal);
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void forwardFrom(WiredBox sourceBox, WiredBox destinationBox)
/* 242:    */   {
/* 243:301 */     forwardFrom("output", sourceBox, destinationBox);
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void forwardFrom(String portName, WiredBox sourceBox, WiredBox destinationBox)
/* 247:    */   {
/* 248:305 */     forwardFrom(portName, sourceBox, portName, destinationBox);
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void forwardFrom(String sourcePortName, WiredBox sourceBox, String destinationPortName, WiredBox destinationBox)
/* 252:    */   {
/* 253:309 */     getLinkedOutputPortPairs().add(new LinkedPortPair(sourcePortName, sourceBox, destinationPortName, destinationBox));
/* 254:    */   }
/* 255:    */   
/* 256:    */   private ArrayList<LinkedPortPair> getLinkedOutputPortPairs()
/* 257:    */   {
/* 258:313 */     if (this.linkedOutputPortPairs == null) {
/* 259:314 */       this.linkedOutputPortPairs = new ArrayList();
/* 260:    */     }
/* 261:316 */     return this.linkedOutputPortPairs;
/* 262:    */   }
/* 263:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.Port
 * JD-Core Version:    0.7.0.1
 */