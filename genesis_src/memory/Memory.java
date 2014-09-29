/*   1:    */ package memory;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import connections.AbstractWiredBox;
/*   5:    */ import connections.Connections;
/*   6:    */ import connections.Ports;
/*   7:    */ import constants.RecognizedRepresentations;
/*   8:    */ import frames.Frame;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Set;
/*  13:    */ import javax.swing.SwingUtilities;
/*  14:    */ import magicLess.utils.EntityUtils;
/*  15:    */ import memory.time.TimeLine;
/*  16:    */ 
/*  17:    */ @Deprecated
/*  18:    */ public final class Memory
/*  19:    */   extends AbstractWiredBox
/*  20:    */ {
/*  21: 26 */   public static boolean DEBUG = false;
/*  22:    */   public static final String PORT_STIMULUS = "Input stimulus port";
/*  23:    */   public static final String PORT_RESPONSE = "Response to stimulus output port";
/*  24:    */   public static final String PORT_CONTEXT = "Port for frames temporally near a Frame";
/*  25:    */   public static final String PORT_REPTREE = "input a rep tree from an entire sentence";
/*  26:    */   public static final String PORT_PREDICTIONS = "output port for predictions";
/*  27:    */   private static Memory memory;
/*  28:    */   
/*  29:    */   public static Memory getMemory()
/*  30:    */   {
/*  31: 40 */     if (memory == null)
/*  32:    */     {
/*  33: 41 */       memory = new Memory();
/*  34: 42 */       memory.setName("Memory");
/*  35:    */     }
/*  36: 44 */     return memory;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void clearSOMs()
/*  40:    */   {
/*  41: 51 */     this.soms = new SomSpace();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void addRepTree(final Entity t)
/*  45:    */   {
/*  46: 62 */     Thread thread = new Thread()
/*  47:    */     {
/*  48:    */       public void run()
/*  49:    */       {
/*  50: 64 */         if (Memory.DEBUG) {
/*  51: 64 */           System.out.println("[MEMORY] Received Rep Tree: " + EntityUtils.getRepType(t));
/*  52:    */         }
/*  53: 67 */         if (EntityUtils.getRepType(t).equals(RecognizedRepresentations.QUESTION_THING))
/*  54:    */         {
/*  55: 68 */           if (Memory.DEBUG) {
/*  56: 68 */             System.out.println("[MEMORY] Input is not being stored in the SOMs.");
/*  57:    */           }
/*  58: 69 */           return;
/*  59:    */         }
/*  60: 76 */         Set<Entity> family = RepProcessor.extractSubReps(t);
/*  61: 77 */         family.add(t);
/*  62: 78 */         for (Entity elt : family) {
/*  63: 80 */           if (!Memory.this.things.contains(elt)) {
/*  64:    */             try
/*  65:    */             {
/*  66: 82 */               Memory.this.add(elt);
/*  67:    */             }
/*  68:    */             catch (RuntimeException e)
/*  69:    */             {
/*  70: 85 */               e.printStackTrace();
/*  71: 86 */               System.err.println("Exception in memory system");
/*  72:    */             }
/*  73:    */           }
/*  74:    */         }
/*  75:    */       }
/*  76:112 */     };
/*  77:113 */     thread.setPriority(1);
/*  78:114 */     thread.start();
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void addRep(final Entity t)
/*  82:    */   {
/*  83:124 */     Thread thread = new Thread()
/*  84:    */     {
/*  85:    */       public void run()
/*  86:    */       {
/*  87:126 */         Memory.this.add(t);
/*  88:    */       }
/*  89:128 */     };
/*  90:129 */     thread.setPriority(1);
/*  91:130 */     thread.start();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<Entity> getNeighbors(Entity t)
/*  95:    */   {
/*  96:144 */     List<Entity> n = this.soms.getNeighbors(t);
/*  97:145 */     n.add(0, t);
/*  98:146 */     return n;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public List<Entity> getNearest(Entity t, int n)
/* 102:    */   {
/* 103:163 */     List<Entity> nearest = this.soms.getNearest(t, n);
/* 104:164 */     nearest.add(0, t);
/* 105:165 */     return nearest;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public boolean containsInSOMs(Entity t)
/* 109:    */   {
/* 110:176 */     return this.soms.contains(t);
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getFrequencyInSOMs(Entity t)
/* 114:    */   {
/* 115:187 */     return this.soms.getFrequency(t);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int[] getFrequencyInSOMs(List<Entity> t)
/* 119:    */   {
/* 120:191 */     int[] result = new int[t.size()];
/* 121:193 */     for (int i = 0; i < t.size(); i++) {
/* 122:194 */       result[i] = this.soms.getFrequency((Entity)t.get(i));
/* 123:    */     }
/* 124:196 */     return result;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void wireAddRepTree(Object input)
/* 128:    */   {
/* 129:209 */     if (!(input instanceof Entity))
/* 130:    */     {
/* 131:210 */       System.err.println("[MEMORY] Expected Rep Tree, but received: " + input);
/* 132:211 */       return;
/* 133:    */     }
/* 134:213 */     addRepTree((Entity)input);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void wireAddThing(Object input)
/* 138:    */   {
/* 139:218 */     if ((input instanceof Entity))
/* 140:    */     {
/* 141:219 */       addRep((Entity)input);
/* 142:    */     }
/* 143:221 */     else if ((input instanceof Frame))
/* 144:    */     {
/* 145:222 */       if (DEBUG) {
/* 146:222 */         System.out.println("[MEMORY] Frame transmitted to Memory");
/* 147:    */       }
/* 148:223 */       System.out.println("[MEMORY] doesn't know how to handle frames");
/* 149:    */     }
/* 150:226 */     else if (DEBUG)
/* 151:    */     {
/* 152:226 */       System.out.println("Unknown object received by memory: " + input.toString());
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void wireGetNeighbors(Object input)
/* 157:    */   {
/* 158:231 */     if ((input instanceof Entity))
/* 159:    */     {
/* 160:232 */       List<Entity> n = getNeighbors((Entity)input);
/* 161:233 */       Connections.getPorts(getMemory()).transmit("Response to stimulus output port", n);
/* 162:    */     }
/* 163:236 */     else if (DEBUG)
/* 164:    */     {
/* 165:236 */       System.out.println("[Memory] Received invalid object on PORT_STIMULUS: " + input);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void outputSOM(final String frameType)
/* 170:    */   {
/* 171:248 */     if (DEBUG) {
/* 172:248 */       System.out.println("[MEMORY] Transmitting SOM of type " + frameType + " out to GUI Components");
/* 173:    */     }
/* 174:251 */     SwingUtilities.invokeLater(new Runnable()
/* 175:    */     {
/* 176:    */       public void run()
/* 177:    */       {
/* 178:255 */         Connections.getPorts(Memory.getMemory()).transmit(frameType, Memory.this.soms.getSom(frameType));
/* 179:    */       }
/* 180:    */     });
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void outputSOM(String destination, String frameType)
/* 184:    */   {
/* 185:267 */     Connections.getPorts(getMemory()).transmit(destination, this.soms.getSom(frameType));
/* 186:    */   }
/* 187:    */   
/* 188:288 */   private SomSpace soms = new SomSpace();
/* 189:291 */   private EntitySpace things = new EntitySpace();
/* 190:294 */   private final TimeLine timeline = new TimeLine();
/* 191:    */   
/* 192:    */   private Memory()
/* 193:    */   {
/* 194:300 */     Connections.getPorts(this).addSignalProcessor("wireAddThing");
/* 195:301 */     Connections.getPorts(this).addSignalProcessor("input a rep tree from an entire sentence", "wireAddRepTree");
/* 196:    */     
/* 197:303 */     Connections.getPorts(this).addSignalProcessor("Port for frames temporally near a Frame", "wireGetContext");
/* 198:305 */     if (DEBUG) {
/* 199:305 */       System.out.println("[MEMORY] Debugging print statements are ON! To turn off, set the DEBUG flag in Memory.java to false.");
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   private void add(Entity t)
/* 204:    */   {
/* 205:316 */     this.things.add(t);
/* 206:    */     
/* 207:    */ 
/* 208:319 */     this.soms.add(t);
/* 209:    */     
/* 210:321 */     List<Entity> predictions = this.things.predict(t);
/* 211:322 */     if (predictions.size() > 0) {
/* 212:325 */       Connections.getPorts(getMemory()).transmit("output port for predictions", predictions.get(0));
/* 213:    */     }
/* 214:328 */     if (DEBUG) {
/* 215:329 */       System.out.println("[MEMORY] added " + t.getID() + " to memory");
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   @Deprecated
/* 220:    */   public void clear()
/* 221:    */   {
/* 222:346 */     this.soms = new SomSpace();
/* 223:    */   }
/* 224:    */   
/* 225:    */   @Deprecated
/* 226:    */   private void add(Frame f)
/* 227:    */   {
/* 228:355 */     this.soms.add(f.getThing());
/* 229:    */   }
/* 230:    */   
/* 231:    */   @Deprecated
/* 232:    */   public List<Entity> getBestMatches(Object input)
/* 233:    */   {
/* 234:363 */     if ((input instanceof Entity))
/* 235:    */     {
/* 236:365 */       Entity t = (Entity)input;
/* 237:366 */       List<Entity> n = this.soms.getNeighbors(t);
/* 238:367 */       return n;
/* 239:    */     }
/* 240:369 */     return new ArrayList();
/* 241:    */   }
/* 242:    */   
/* 243:    */   @Deprecated
/* 244:    */   public boolean containsInMemory(Object input)
/* 245:    */   {
/* 246:376 */     if ((input instanceof Entity)) {
/* 247:377 */       return this.soms.contains((Entity)input);
/* 248:    */     }
/* 249:379 */     return false;
/* 250:    */   }
/* 251:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.Memory
 * JD-Core Version:    0.7.0.1
 */