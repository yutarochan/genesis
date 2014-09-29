/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Thread;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Font;
/*   6:    */ import java.awt.FontMetrics;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.Graphics2D;
/*   9:    */ import java.awt.Rectangle;
/*  10:    */ import java.awt.geom.Point2D;
/*  11:    */ import java.awt.geom.Point2D.Float;
/*  12:    */ import java.awt.geom.Rectangle2D;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.HashSet;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Set;
/*  20:    */ import javax.swing.JPanel;
/*  21:    */ import javax.swing.JTextArea;
/*  22:    */ 
/*  23:    */ public class AutomatedPanel
/*  24:    */   extends JPanel
/*  25:    */ {
/*  26:    */   private final String name;
/*  27:    */   private final VerbData verbData;
/*  28:    */   private TypeLattice panelLattice;
/*  29:    */   private FasterLLConcept concept;
/*  30:    */   private JTextArea textfield;
/*  31:    */   private float node_width;
/*  32:    */   private int node_height;
/*  33: 31 */   private float spacing = 1.5F;
/*  34:    */   private Map<String, Point2D> map;
/*  35: 34 */   private Set<String> positives = new HashSet();
/*  36: 35 */   private Set<String> negatives = new HashSet();
/*  37:    */   private String threadList;
/*  38:    */   private String[] threadArray;
/*  39:    */   
/*  40:    */   public AutomatedPanel(VerbData data)
/*  41:    */   {
/*  42: 44 */     this.verbData = data;
/*  43: 45 */     String[] nameArray = this.verbData.getVerb().split(" ");
/*  44: 46 */     this.name = nameArray[(nameArray.length - 1)];
/*  45: 47 */     this.threadList = this.verbData.getSubjectThreads();
/*  46:    */     
/*  47: 49 */     List<Thread> threads = new ArrayList();
/*  48: 50 */     this.threadArray = this.threadList.split("\n\n");
/*  49: 51 */     for (String string : this.threadArray) {
/*  50: 53 */       threads.add(Thread.parse(string));
/*  51:    */     }
/*  52: 55 */     this.panelLattice = new TypeLattice(threads);
/*  53: 56 */     this.concept = new FasterLLConcept(this.panelLattice, this.verbData.getVerb());
/*  54: 57 */     this.positives.clear();
/*  55: 58 */     this.negatives.clear();
/*  56:    */     
/*  57: 60 */     this.textfield = new JTextArea();
/*  58: 61 */     this.textfield.setEditable(false);
/*  59: 62 */     this.textfield.setFont(this.textfield.getFont().deriveFont(20.0F));
/*  60: 63 */     this.textfield.setWrapStyleWord(true);
/*  61:    */   }
/*  62:    */   
/*  63:    */   private void teachLattice(String[] threads)
/*  64:    */   {
/*  65: 71 */     for (String thread : threads) {
/*  66: 72 */       if (((Boolean)this.verbData.getSubjectMap().get(thread)).booleanValue())
/*  67:    */       {
/*  68: 73 */         this.positives.add((String)this.verbData.getThreadMap().get(thread));
/*  69: 74 */         this.concept.learnPositive((String)this.verbData.getThreadMap().get(thread));
/*  70:    */       }
/*  71:    */       else
/*  72:    */       {
/*  73: 77 */         this.negatives.add((String)this.verbData.getThreadMap().get(thread));
/*  74: 78 */         this.concept.learnNegative((String)this.verbData.getThreadMap().get(thread));
/*  75:    */       }
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void paintComponent(Graphics g)
/*  80:    */   {
/*  81: 85 */     super.paintComponent(g);
/*  82:    */     
/*  83: 87 */     Graphics2D g2d = (Graphics2D)g;
/*  84: 88 */     Font font = g2d.getFont().deriveFont(1).deriveFont(36.0F);
/*  85: 89 */     g2d.setFont(font);
/*  86:    */     
/*  87: 91 */     g2d.setColor(Color.white);
/*  88: 92 */     int height = g2d.getClipBounds().height;
/*  89: 93 */     int width = g2d.getClipBounds().width;
/*  90: 94 */     g2d.fillRect(0, 0, width, height);
/*  91:    */     
/*  92: 96 */     List<Set<String>> sorted = this.panelLattice.topologicalSort();
/*  93:    */     
/*  94: 98 */     int layers = sorted.size() * 2 - 1;
/*  95: 99 */     this.node_height = (height / layers);
/*  96:    */     
/*  97:101 */     int columns = 0;
/*  98:102 */     for (Set<String> c : sorted) {
/*  99:103 */       columns = Math.max(columns, c.size());
/* 100:    */     }
/* 101:106 */     this.node_width = (width / (this.spacing * columns));
/* 102:    */     
/* 103:108 */     this.map = new HashMap();
/* 104:109 */     int y = 0;
/* 105:    */     String type;
/* 106:111 */     for (Object c : sorted)
/* 107:    */     {
/* 108:112 */       float x = 0.0F;
/* 109:113 */       for (Iterator localIterator3 = ((Set)c).iterator(); localIterator3.hasNext();)
/* 110:    */       {
/* 111:113 */         type = (String)localIterator3.next();
/* 112:114 */         this.map.put(type, new Point2D.Float(x, y));
/* 113:116 */         if (this.positives.size() + this.negatives.size() > 0)
/* 114:    */         {
/* 115:117 */           g2d.setColor(this.concept.contains(type) ? Color.green : Color.red);
/* 116:118 */           if ((this.positives.contains(type)) || (this.negatives.contains(type))) {
/* 117:119 */             g2d.setColor(g2d.getColor().brighter());
/* 118:    */           } else {
/* 119:121 */             g2d.setColor(g2d.getColor().darker().darker());
/* 120:    */           }
/* 121:123 */           g2d.fillOval((int)x, y, (int)this.node_width, this.node_height);
/* 122:124 */           if ((this.positives.contains(type)) || (this.negatives.contains(type))) {
/* 123:125 */             g2d.setColor(Color.DARK_GRAY);
/* 124:    */           } else {
/* 125:127 */             g2d.setColor(Color.LIGHT_GRAY);
/* 126:    */           }
/* 127:131 */           FontMetrics fm = g2d.getFontMetrics();
/* 128:132 */           int txtWidth = fm.stringWidth(type);
/* 129:133 */           float fontSize = g2d.getFont().getSize();
/* 130:135 */           if (txtWidth > this.node_width) {
/* 131:136 */             while (txtWidth > this.node_width)
/* 132:    */             {
/* 133:137 */               fontSize -= 1.0F;
/* 134:138 */               Font tempFont = g.getFont().deriveFont(fontSize);
/* 135:139 */               g.setFont(tempFont);
/* 136:140 */               txtWidth = g.getFontMetrics().stringWidth(type);
/* 137:    */             }
/* 138:    */           }
/* 139:144 */           Rectangle2D box = g2d.getFontMetrics().getStringBounds(type, g2d);
/* 140:145 */           g2d.drawString(type, (int)(x + (this.node_width - box.getWidth()) / 2.0D), y + this.node_height / 2);
/* 141:    */         }
/* 142:    */         else
/* 143:    */         {
/* 144:147 */           g2d.setColor(Color.black);
/* 145:148 */           g2d.drawOval((int)x, y, (int)this.node_width, this.node_height);
/* 146:    */           
/* 147:    */ 
/* 148:    */ 
/* 149:152 */           FontMetrics fm = g2d.getFontMetrics();
/* 150:153 */           int txtWidth = fm.stringWidth(type);
/* 151:154 */           float fontSize = g2d.getFont().getSize();
/* 152:156 */           if (txtWidth > this.node_width) {
/* 153:157 */             while (txtWidth > this.node_width)
/* 154:    */             {
/* 155:158 */               fontSize -= 1.0F;
/* 156:159 */               Font tempFont = g.getFont().deriveFont(fontSize);
/* 157:160 */               g.setFont(tempFont);
/* 158:161 */               txtWidth = g.getFontMetrics().stringWidth(type);
/* 159:    */             }
/* 160:    */           }
/* 161:165 */           Rectangle2D box = g2d.getFontMetrics().getStringBounds(type, g2d);
/* 162:166 */           g2d.drawString(type, (int)(x + (this.node_width - box.getWidth()) / 2.0D), y + this.node_height / 2);
/* 163:    */         }
/* 164:168 */         x += this.spacing * this.node_width;
/* 165:169 */         g2d.setFont(font);
/* 166:    */       }
/* 167:171 */       y += 2 * this.node_height;
/* 168:    */     }
/* 169:174 */     for (??? = this.map.keySet().iterator(); ???.hasNext(); type.hasNext())
/* 170:    */     {
/* 171:174 */       String type = (String)???.next();
/* 172:175 */       type = this.panelLattice.getParents(type).iterator(); continue;String parent = (String)type.next();
/* 173:176 */       Point2D p1 = (Point2D)this.map.get(parent);
/* 174:177 */       Point2D p2 = (Point2D)this.map.get(type);
/* 175:178 */       g2d.setColor(Color.gray);
/* 176:179 */       g2d.drawLine((int)(p1.getX() + this.node_width / 2.0F), (int)(p1.getY() + this.node_height), (int)(p2.getX() + this.node_width / 2.0F), (int)p2.getY());
/* 177:180 */       g2d.drawOval((int)(p2.getX() + this.node_width / 2.0F - 2.0D), (int)(p2.getY() - 2.0D), 4, 4);
/* 178:    */     }
/* 179:184 */     if ((this.positives.size() == 0) && (this.negatives.size() == 0))
/* 180:    */     {
/* 181:185 */       teachLattice(this.threadArray);
/* 182:186 */       repaint();
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public TypeLattice getLattice()
/* 187:    */   {
/* 188:191 */     return this.panelLattice;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public JTextArea getTextField()
/* 192:    */   {
/* 193:195 */     return this.textfield;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public Map<String, Point2D> getMap()
/* 197:    */   {
/* 198:199 */     return this.map;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String getThreadList()
/* 202:    */   {
/* 203:203 */     return this.threadList;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public String getName()
/* 207:    */   {
/* 208:207 */     return this.name;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public FasterLLConcept getConcept()
/* 212:    */   {
/* 213:211 */     return this.concept;
/* 214:    */   }
/* 215:    */   
/* 216:    */   public Set<String> getPositives()
/* 217:    */   {
/* 218:215 */     return this.positives;
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Set<String> getNegatives()
/* 222:    */   {
/* 223:219 */     return this.negatives;
/* 224:    */   }
/* 225:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.AutomatedPanel
 * JD-Core Version:    0.7.0.1
 */