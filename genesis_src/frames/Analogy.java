/*   1:    */ package frames;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import connections.Connections;
/*   9:    */ import connections.Ports;
/*  10:    */ import connections.WiredViewer;
/*  11:    */ import gui.GenericViewer;
/*  12:    */ import gui.WiredPanel;
/*  13:    */ import java.awt.Color;
/*  14:    */ import java.awt.GridLayout;
/*  15:    */ import java.io.PrintStream;
/*  16:    */ import java.rmi.server.UID;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Collection;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import java.util.Vector;
/*  23:    */ import magicLess.specialMath.Tableau;
/*  24:    */ import magicLess.specialMath.TransportationProblem;
/*  25:    */ import magicLess.utils.EntityUtils;
/*  26:    */ import memory.utilities.Distances;
/*  27:    */ 
/*  28:    */ public class Analogy
/*  29:    */   extends Frame
/*  30:    */ {
/*  31:    */   public static final String FRAMETYPE = "analogy";
/*  32:    */   public static final String noMatch = "NoMatch";
/*  33: 20 */   private Map<Bundle, Entity> matching = new HashMap();
/*  34: 21 */   private List<Entity> orderedBaseThings = new ArrayList();
/*  35:    */   public Entity base;
/*  36:    */   public Entity target;
/*  37: 23 */   public Entity thing = new Sequence("analogy");
/*  38:    */   
/*  39:    */   public Analogy(Entity base, Entity target)
/*  40:    */   {
/*  41: 26 */     this.base = base;
/*  42: 27 */     this.target = target;
/*  43: 28 */     fillMatching(this.base, this.target);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Analogy(Entity analogy)
/*  47:    */   {
/*  48: 32 */     this.base = analogy.getElement(0).getSubject();
/*  49: 33 */     this.target = analogy.getElement(0).getObject();
/*  50:    */     
/*  51: 35 */     fillMatching(this.base, this.target);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void fillMatching(Entity base, Entity target)
/*  55:    */   {
/*  56: 40 */     if ((base.isA("NoMatch")) || (target.isA("NoMatch")))
/*  57:    */     {
/*  58: 41 */       putMatch(base, target);
/*  59:    */       
/*  60: 43 */       return;
/*  61:    */     }
/*  62: 50 */     if (base.getPrettyPrintType() != target.getPrettyPrintType()) {
/*  63: 52 */       return;
/*  64:    */     }
/*  65: 55 */     putMatch(base, target);
/*  66: 56 */     List<Entity> baseKids = EntityUtils.getOrderedChildren(base);
/*  67: 57 */     List<Entity> targetKids = EntityUtils.getOrderedChildren(target);
/*  68: 58 */     int bkSize = baseKids.size();
/*  69: 59 */     int tkSize = targetKids.size();
/*  70: 61 */     for (int i = 0; i < tkSize - bkSize; i++) {
/*  71: 62 */       baseKids.add(new Entity("NoMatch"));
/*  72:    */     }
/*  73: 64 */     for (int i = 0; i < bkSize - tkSize; i++) {
/*  74: 65 */       targetKids.add(new Entity("NoMatch"));
/*  75:    */     }
/*  76: 67 */     assert (baseKids.size() == targetKids.size());
/*  77: 68 */     int size = baseKids.size();
/*  78: 69 */     Tableau t = new Tableau(size, size);
/*  79: 70 */     for (int i = 0; i < size; i++) {
/*  80: 71 */       for (int j = 0; j < size; j++) {
/*  81: 72 */         t.set(i, j, distance((Entity)baseKids.get(i), (Entity)targetKids.get(j)));
/*  82:    */       }
/*  83:    */     }
/*  84: 75 */     t = TransportationProblem.doHungarian(t);
/*  85: 76 */     for (int i = 0; i < size; i++) {
/*  86: 77 */       for (int j = 0; j < size; j++) {
/*  87: 78 */         if (t.isStarred(i, j)) {
/*  88: 79 */           fillMatching((Entity)baseKids.get(i), (Entity)targetKids.get(j));
/*  89:    */         }
/*  90:    */       }
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   private static double distance(Entity a, Entity b)
/*  95:    */   {
/*  96: 94 */     return Distances.distance(a, b);
/*  97:    */   }
/*  98:    */   
/*  99:    */   private void putMatch(Entity base, Entity target)
/* 100:    */   {
/* 101: 98 */     this.thing.addElement(new Relation(base, target));
/* 102: 99 */     this.matching.put(base.getBundle(), target);
/* 103:100 */     this.orderedBaseThings.add(base);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public Collection<Entity> getBaseThings()
/* 107:    */   {
/* 108:104 */     return this.orderedBaseThings;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean contains(Entity t)
/* 112:    */   {
/* 113:108 */     return (this.matching.containsKey(t.getBundle())) && (!getMatch(t).isA("NoMatch"));
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Entity getMatch(Entity t)
/* 117:    */   {
/* 118:112 */     return (Entity)this.matching.get(t.getBundle());
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Entity targetify(Entity baseyThing)
/* 122:    */   {
/* 123:117 */     if (contains(baseyThing)) {
/* 124:118 */       return getMatch(baseyThing).deepClone();
/* 125:    */     }
/* 126:120 */     Entity result = baseyThing.deepClone();
/* 127:121 */     if ((result instanceof Function))
/* 128:    */     {
/* 129:122 */       Function d = (Function)result;
/* 130:123 */       d.setSubject(targetify(d.getSubject()));
/* 131:    */     }
/* 132:125 */     if ((result instanceof Relation))
/* 133:    */     {
/* 134:126 */       Relation r = (Relation)result;
/* 135:127 */       r.setSubject(targetify(r.getSubject()));
/* 136:128 */       r.setObject(targetify(r.getObject()));
/* 137:    */     }
/* 138:130 */     if ((result instanceof Sequence))
/* 139:    */     {
/* 140:131 */       Sequence s = (Sequence)result;
/* 141:132 */       Vector<Entity> elements = new Vector();
/* 142:133 */       for (Entity t : s.getElements()) {
/* 143:134 */         elements.add(targetify(t));
/* 144:    */       }
/* 145:136 */       s.setElements(elements);
/* 146:    */     }
/* 147:138 */     return result;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String toString()
/* 151:    */   {
/* 152:143 */     StringBuilder sb = new StringBuilder();
/* 153:144 */     for (Entity t : this.orderedBaseThings) {
/* 154:145 */       sb.append(t.getName() + " -> " + getMatch(t).getName() + "\n");
/* 155:    */     }
/* 156:147 */     return sb.toString();
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Entity getThing()
/* 160:    */   {
/* 161:152 */     return this.thing;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public class AnalogyViewer
/* 165:    */     extends WiredViewer
/* 166:    */   {
/* 167:    */     public AnalogyViewer()
/* 168:    */     {
/* 169:158 */       Connections.getPorts(this).addSignalProcessor("view");
/* 170:159 */       setLayout(new GridLayout(0, 2));
/* 171:160 */       setBackground(Color.WHITE);
/* 172:161 */       setOpaque(true);
/* 173:    */     }
/* 174:    */     
/* 175:    */     public void view(Object signal)
/* 176:    */     {
/* 177:165 */       if ((signal instanceof Entity))
/* 178:    */       {
/* 179:166 */         removeAll();
/* 180:167 */         Entity thing = (Entity)signal;
/* 181:168 */         Frame frame = SmartFrameFactory.translate(thing);
/* 182:169 */         if (!(frame instanceof Analogy)) {
/* 183:170 */           System.err.println(frame.getClass().getCanonicalName());
/* 184:    */         }
/* 185:172 */         Analogy a = (Analogy)frame;
/* 186:173 */         for (Entity base : a.getBaseThings())
/* 187:    */         {
/* 188:174 */           Entity target = a.getMatch(base);
/* 189:175 */           WiredPanel baseViewer = new GenericViewer();
/* 190:176 */           WiredPanel targetViewer = new GenericViewer();
/* 191:177 */           add(baseViewer);
/* 192:178 */           add(targetViewer);
/* 193:179 */           String bport = new UID().toString();
/* 194:180 */           String tport = new UID().toString();
/* 195:181 */           Connections.wire(bport, this, baseViewer);
/* 196:182 */           Connections.wire(tport, this, targetViewer);
/* 197:183 */           Connections.getPorts(this).transmit(bport, base);
/* 198:184 */           Connections.getPorts(this).transmit(tport, target);
/* 199:    */         }
/* 200:186 */         revalidate();
/* 201:187 */         repaint();
/* 202:    */       }
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public WiredViewer getThingViewer()
/* 207:    */   {
/* 208:194 */     return new AnalogyViewer();
/* 209:    */   }
/* 210:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.Analogy
 * JD-Core Version:    0.7.0.1
 */