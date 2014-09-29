/*   1:    */ package connections.views;
/*   2:    */ 
/*   3:    */ import connections.BasicNetwork;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Network;
/*   6:    */ import connections.Port;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.Test;
/*   9:    */ import connections.WiredBox;
/*  10:    */ import connections.WiredOnOffSwitch;
/*  11:    */ import connections.WiredToggleSwitch;
/*  12:    */ import java.awt.Component;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.Observable;
/*  17:    */ import java.util.Observer;
/*  18:    */ import java.util.Set;
/*  19:    */ import javax.swing.event.ChangeEvent;
/*  20:    */ import javax.swing.event.ChangeListener;
/*  21:    */ 
/*  22:    */ public class Adapter
/*  23:    */   implements Observer
/*  24:    */ {
/*  25:    */   private ArrayList<Integer> levelList;
/*  26:    */   private HashMap<WiredBox, ViewerBox> boxToBoxViewerMap;
/*  27: 21 */   private boolean depth = false;
/*  28:    */   private ConnectionViewer viewer;
/*  29:    */   private Network<WiredBox> network;
/*  30:    */   private static Adapter networkAdapter;
/*  31:    */   private static Adapter connectionAdapter;
/*  32:    */   
/*  33:    */   public static Adapter makeConnectionAdapter()
/*  34:    */   {
/*  35: 32 */     if (connectionAdapter == null)
/*  36:    */     {
/*  37: 33 */       connectionAdapter = new Adapter();
/*  38: 34 */       Connections.getInstance().addObserver(connectionAdapter);
/*  39: 35 */       Connections.getInstance().changed();
/*  40: 36 */       connectionAdapter.network = Connections.getInstance();
/*  41:    */     }
/*  42: 38 */     return connectionAdapter;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Adapter makeNetworkAdapter(BasicNetwork<? extends WiredBox> network)
/*  46:    */   {
/*  47: 45 */     if (networkAdapter == null)
/*  48:    */     {
/*  49: 46 */       networkAdapter = new Adapter();
/*  50: 47 */       network.addObserver(networkAdapter);
/*  51:    */     }
/*  52: 49 */     return networkAdapter;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public ConnectionViewer getViewer()
/*  56:    */   {
/*  57: 53 */     if (this.viewer == null) {
/*  58: 54 */       this.viewer = new ConnectionViewer();
/*  59:    */     }
/*  60: 56 */     return this.viewer;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setNetwork(Network<WiredBox> network)
/*  64:    */   {
/*  65: 60 */     this.network = network;
/*  66: 61 */     processNetwork();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void update(Observable o, Object ignore)
/*  70:    */   {
/*  71: 67 */     this.network = ((Network)o);
/*  72: 68 */     processNetwork();
/*  73:    */   }
/*  74:    */   
/*  75:    */   private void processNetwork()
/*  76:    */   {
/*  77: 72 */     getLevelList().clear();
/*  78: 73 */     getBoxToBoxViewerMap().clear();
/*  79: 74 */     getViewer().clear();
/*  80: 75 */     ArrayList<WiredBox> boxes = new ArrayList();
/*  81: 76 */     ArrayList<WiredBox> targets = new ArrayList();
/*  82: 77 */     ArrayList<WiredBox> roots = new ArrayList();
/*  83: 78 */     if (this.network == null) {
/*  84: 79 */       return;
/*  85:    */     }
/*  86: 81 */     for (WiredBox box : this.network.getBoxes())
/*  87:    */     {
/*  88: 82 */       boxes.add(box);
/*  89: 83 */       targets.addAll(this.network.getTargets(box));
/*  90:    */     }
/*  91: 85 */     roots.addAll(boxes);
/*  92: 86 */     for (WiredBox target : targets) {
/*  93: 87 */       roots.remove(target);
/*  94:    */     }
/*  95: 92 */     for (WiredBox root : roots) {
/*  96: 93 */       search(root, boxes, this.network);
/*  97:    */     }
/*  98: 96 */     while (!boxes.isEmpty())
/*  99:    */     {
/* 100: 99 */       ArrayList<WiredBox> remainder = new ArrayList();
/* 101:100 */       remainder.addAll(boxes);
/* 102:101 */       box = (WiredBox)remainder.get(0);
/* 103:102 */       search((WiredBox)box, boxes, this.network);
/* 104:    */     }
/* 105:    */     Iterator localIterator2;
/* 106:112 */     for (Object box = this.network.getBoxes().iterator(); ((Iterator)box).hasNext(); localIterator2.hasNext())
/* 107:    */     {
/* 108:112 */       WiredBox box = (WiredBox)((Iterator)box).next();
/* 109:113 */       ViewerBox viewerSource = (ViewerBox)getBoxToBoxViewerMap().get(box);
/* 110:114 */       localIterator2 = Connections.getPorts(box).getPorts().iterator(); continue;Port port = (Port)localIterator2.next();
/* 111:115 */       Set<String> destinationNames = port.getDestinationNames();
/* 112:116 */       viewerSource.getOutputPortNames().addAll(destinationNames);
/* 113:    */       Iterator localIterator4;
/* 114:117 */       for (Iterator localIterator3 = destinationNames.iterator(); localIterator3.hasNext(); localIterator4.hasNext())
/* 115:    */       {
/* 116:117 */         String name = (String)localIterator3.next();
/* 117:118 */         localIterator4 = port.getDestinations(name).iterator(); continue;WiredBox destination = (WiredBox)localIterator4.next();
/* 118:119 */         ViewerBox viewerTarget = (ViewerBox)getBoxToBoxViewerMap().get(destination);
/* 119:120 */         viewerTarget.getInputPortNames().add(name);
/* 120:121 */         getViewer().addWire(new ViewerWire(port, viewerSource, name, viewerTarget));
/* 121:    */       }
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   private void search(WiredBox root, ArrayList<WiredBox> boxes, Network<WiredBox> network)
/* 126:    */   {
/* 127:130 */     ArrayList<WiredBox> targets = new ArrayList();
/* 128:131 */     targets.add(root);
/* 129:    */     
/* 130:    */ 
/* 131:134 */     targets = intersection(targets, boxes);
/* 132:    */     
/* 133:136 */     int column = 0;
/* 134:138 */     while (!targets.isEmpty())
/* 135:    */     {
/* 136:142 */       if (this.depth)
/* 137:    */       {
/* 138:143 */         process((WiredBox)targets.get(0), column);
/* 139:144 */         boxes.remove(targets.get(0));
/* 140:    */       }
/* 141:    */       else
/* 142:    */       {
/* 143:147 */         process(targets, column);
/* 144:148 */         boxes.removeAll(targets);
/* 145:    */       }
/* 146:151 */       ArrayList<WiredBox> nextTargets = new ArrayList();
/* 147:152 */       for (WiredBox target : targets) {
/* 148:154 */         nextTargets = union(nextTargets, intersection(boxes, network.getTargets(target)));
/* 149:    */       }
/* 150:157 */       targets = intersection(nextTargets, boxes);
/* 151:158 */       column++;
/* 152:    */     }
/* 153:160 */     setMaximumLevelInColumn(0, getMaximumLevel());
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void process(ArrayList<WiredBox> targets, int column)
/* 157:    */   {
/* 158:164 */     for (WiredBox box : targets) {
/* 159:165 */       process(box, column);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   private void process(WiredBox box, int column)
/* 164:    */   {
/* 165:170 */     int level = getMaximumLevelInColumn(column);
/* 166:171 */     String name = "---";
/* 167:172 */     if ((box instanceof Component)) {
/* 168:173 */       name = ((Component)box).getName();
/* 169:175 */     } else if ((box instanceof WiredBox)) {
/* 170:176 */       name = box.getName();
/* 171:    */     }
/* 172:178 */     if (name == null) {
/* 173:179 */       name = "---";
/* 174:    */     }
/* 175:183 */     ViewerBox viewerBox = new ViewerBox(level, column, name, box);
/* 176:184 */     if ((box instanceof WiredOnOffSwitch))
/* 177:    */     {
/* 178:185 */       WiredOnOffSwitch theBox = (WiredOnOffSwitch)box;
/* 179:186 */       theBox.addChangeListener(new SwitchStateListener(theBox, viewerBox));
/* 180:187 */       if (theBox.isSelected()) {
/* 181:188 */         viewerBox.setSwitchState(ViewerBox.ON_SWITCH);
/* 182:    */       } else {
/* 183:191 */         viewerBox.setSwitchState(ViewerBox.OFF_SWITCH);
/* 184:    */       }
/* 185:    */     }
/* 186:194 */     if ((box instanceof WiredToggleSwitch)) {
/* 187:195 */       viewerBox.setToggleSwitch(true);
/* 188:    */     }
/* 189:197 */     viewerBox.addObserver(getViewer());
/* 190:198 */     getBoxToBoxViewerMap().put(box, viewerBox);
/* 191:199 */     getViewer().addBox(viewerBox);
/* 192:200 */     incrementMaximumLevelInColumn(column);
/* 193:    */   }
/* 194:    */   
/* 195:    */   private class SwitchStateListener
/* 196:    */     implements ChangeListener
/* 197:    */   {
/* 198:    */     WiredOnOffSwitch switchBox;
/* 199:    */     ViewerBox viewerBox;
/* 200:    */     
/* 201:    */     public SwitchStateListener(WiredOnOffSwitch switchBox, ViewerBox viewerBox)
/* 202:    */     {
/* 203:209 */       this.switchBox = switchBox;
/* 204:210 */       this.viewerBox = viewerBox;
/* 205:    */     }
/* 206:    */     
/* 207:    */     public void stateChanged(ChangeEvent e)
/* 208:    */     {
/* 209:214 */       if (this.switchBox.isSelected()) {
/* 210:215 */         this.viewerBox.setSwitchState(ViewerBox.ON_SWITCH);
/* 211:    */       } else {
/* 212:218 */         this.viewerBox.setSwitchState(ViewerBox.OFF_SWITCH);
/* 213:    */       }
/* 214:220 */       Adapter.this.getViewer().repaint();
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   private ArrayList<WiredBox> intersection(ArrayList<WiredBox> targets, ArrayList<WiredBox> boxes)
/* 219:    */   {
/* 220:225 */     ArrayList<WiredBox> result = new ArrayList();
/* 221:226 */     for (WiredBox target : targets) {
/* 222:227 */       if ((boxes.contains(target)) && (!result.contains(target))) {
/* 223:228 */         result.add(target);
/* 224:    */       }
/* 225:    */     }
/* 226:231 */     return result;
/* 227:    */   }
/* 228:    */   
/* 229:    */   private ArrayList<WiredBox> union(ArrayList<WiredBox> listA, ArrayList<WiredBox> listB)
/* 230:    */   {
/* 231:235 */     ArrayList<WiredBox> result = new ArrayList();
/* 232:236 */     for (WiredBox candidate : listA) {
/* 233:237 */       if (!result.contains(candidate)) {
/* 234:238 */         result.add(candidate);
/* 235:    */       }
/* 236:    */     }
/* 237:241 */     for (WiredBox candidate : listB) {
/* 238:242 */       if (!result.contains(candidate)) {
/* 239:243 */         result.add(candidate);
/* 240:    */       }
/* 241:    */     }
/* 242:246 */     return result;
/* 243:    */   }
/* 244:    */   
/* 245:    */   private void incrementMaximumLevelInColumn(int column)
/* 246:    */   {
/* 247:250 */     int current = getMaximumLevelInColumn(column);
/* 248:251 */     current++;
/* 249:252 */     getLevelList().set(column, Integer.valueOf(current));
/* 250:    */   }
/* 251:    */   
/* 252:    */   private void setMaximumLevelInColumn(int column, int max)
/* 253:    */   {
/* 254:256 */     getMaximumLevelInColumn(column);
/* 255:257 */     getLevelList().set(column, Integer.valueOf(max));
/* 256:    */   }
/* 257:    */   
/* 258:    */   private int getMaximumLevelInColumn(int column)
/* 259:    */   {
/* 260:261 */     ArrayList<Integer> list = getLevelList();
/* 261:262 */     int currentSize = list.size();
/* 262:263 */     if (currentSize < column + 1) {
/* 263:264 */       for (int i = 0; i < column + 1 - currentSize; i++) {
/* 264:265 */         list.add(Integer.valueOf(0));
/* 265:    */       }
/* 266:    */     }
/* 267:268 */     return ((Integer)list.get(column)).intValue();
/* 268:    */   }
/* 269:    */   
/* 270:    */   private int getMaximumLevel()
/* 271:    */   {
/* 272:272 */     int result = 0;
/* 273:273 */     ArrayList<Integer> list = getLevelList();
/* 274:274 */     for (int i = 0; i < list.size(); i++) {
/* 275:275 */       result = Math.max(result, ((Integer)list.get(i)).intValue());
/* 276:    */     }
/* 277:277 */     return result;
/* 278:    */   }
/* 279:    */   
/* 280:    */   private ArrayList<Integer> getLevelList()
/* 281:    */   {
/* 282:281 */     if (this.levelList == null) {
/* 283:282 */       this.levelList = new ArrayList();
/* 284:    */     }
/* 285:284 */     return this.levelList;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public HashMap<WiredBox, ViewerBox> getBoxToBoxViewerMap()
/* 289:    */   {
/* 290:288 */     if (this.boxToBoxViewerMap == null) {
/* 291:289 */       this.boxToBoxViewerMap = new HashMap();
/* 292:    */     }
/* 293:291 */     return this.boxToBoxViewerMap;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public ViewerBox getViewerBox(WiredBox box)
/* 297:    */   {
/* 298:295 */     return (ViewerBox)getBoxToBoxViewerMap().get(box);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public static void main(String[] args)
/* 302:    */   {
/* 303:299 */     Test.main(args);
/* 304:    */   }
/* 305:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.views.Adapter
 * JD-Core Version:    0.7.0.1
 */