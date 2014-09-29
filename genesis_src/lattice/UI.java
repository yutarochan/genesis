/*   1:    */ package lattice;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Thread;
/*   4:    */ import java.awt.BorderLayout;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Container;
/*   7:    */ import java.awt.Font;
/*   8:    */ import java.awt.FontMetrics;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.awt.Graphics2D;
/*  11:    */ import java.awt.GridLayout;
/*  12:    */ import java.awt.Point;
/*  13:    */ import java.awt.Rectangle;
/*  14:    */ import java.awt.event.KeyEvent;
/*  15:    */ import java.awt.event.KeyListener;
/*  16:    */ import java.awt.event.MouseEvent;
/*  17:    */ import java.awt.event.MouseListener;
/*  18:    */ import java.awt.geom.Point2D;
/*  19:    */ import java.awt.geom.Point2D.Double;
/*  20:    */ import java.awt.geom.Point2D.Float;
/*  21:    */ import java.awt.geom.Rectangle2D;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.HashSet;
/*  25:    */ import java.util.Iterator;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import java.util.Set;
/*  29:    */ import javax.swing.JFrame;
/*  30:    */ import javax.swing.JPanel;
/*  31:    */ import javax.swing.JSplitPane;
/*  32:    */ import javax.swing.JTextArea;
/*  33:    */ import javax.swing.UIManager;
/*  34:    */ import javax.swing.UnsupportedLookAndFeelException;
/*  35:    */ 
/*  36:    */ public class UI
/*  37:    */   extends JFrame
/*  38:    */   implements KeyListener
/*  39:    */ {
/*  40:    */   JTextArea text;
/*  41:    */   JTextArea analysis;
/*  42:    */   LatticePanel lp;
/*  43:    */   
/*  44:    */   class LatticePanel
/*  45:    */     extends JPanel
/*  46:    */     implements MouseListener
/*  47:    */   {
/*  48:    */     private TypeLattice lattice;
/*  49:    */     private Concept<String> concept;
/*  50:    */     private JTextArea textfield;
/*  51:    */     private float node_width;
/*  52:    */     private int node_height;
/*  53: 40 */     private Set<String> positives = new HashSet();
/*  54: 41 */     private Set<String> negatives = new HashSet();
/*  55:    */     
/*  56:    */     public LatticePanel(TypeLattice lattice, JTextArea textfield)
/*  57:    */     {
/*  58: 44 */       setLattice(lattice);
/*  59: 45 */       addMouseListener(this);
/*  60: 46 */       this.textfield = textfield;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public void setLattice(TypeLattice lattice)
/*  64:    */     {
/*  65: 50 */       this.lattice = lattice;
/*  66: 51 */       this.concept = new FasterLLConcept(lattice);
/*  67: 52 */       this.positives.clear();
/*  68: 53 */       this.negatives.clear();
/*  69:    */     }
/*  70:    */     
/*  71: 56 */     private float spacing = 1.5F;
/*  72:    */     private Map<String, Point2D> map;
/*  73:    */     
/*  74:    */     public void paintComponent(Graphics g)
/*  75:    */     {
/*  76: 60 */       super.paintComponent(g);
/*  77:    */       
/*  78: 62 */       Graphics2D g2d = (Graphics2D)g;
/*  79: 63 */       Font font = g2d.getFont().deriveFont(1).deriveFont(20.0F);
/*  80: 64 */       g2d.setFont(font);
/*  81:    */       
/*  82: 66 */       g2d.setColor(Color.white);
/*  83: 67 */       int height = g2d.getClipBounds().height;
/*  84: 68 */       int width = g2d.getClipBounds().width;
/*  85: 69 */       g2d.fillRect(0, 0, width, height);
/*  86:    */       
/*  87: 71 */       List<Set<String>> sorted = this.lattice.topologicalSort();
/*  88:    */       
/*  89: 73 */       int layers = sorted.size() * 2 - 1;
/*  90: 74 */       this.node_height = (height / layers);
/*  91:    */       
/*  92: 76 */       int columns = 0;
/*  93: 77 */       for (Set<String> c : sorted) {
/*  94: 78 */         columns = Math.max(columns, c.size());
/*  95:    */       }
/*  96: 81 */       this.node_width = (width / (this.spacing * columns));
/*  97:    */       
/*  98: 83 */       this.map = new HashMap();
/*  99: 84 */       int y = 0;
/* 100:    */       String type;
/* 101: 86 */       for (Object c : sorted)
/* 102:    */       {
/* 103: 87 */         float x = 0.0F;
/* 104: 88 */         for (Iterator localIterator3 = ((Set)c).iterator(); localIterator3.hasNext();)
/* 105:    */         {
/* 106: 88 */           type = (String)localIterator3.next();
/* 107: 89 */           this.map.put(type, new Point2D.Float(x, y));
/* 108: 91 */           if (this.positives.size() + this.negatives.size() > 0)
/* 109:    */           {
/* 110: 92 */             g2d.setColor(this.concept.contains(type) ? Color.green : Color.red);
/* 111: 93 */             if ((this.positives.contains(type)) || (this.negatives.contains(type))) {
/* 112: 94 */               g2d.setColor(g2d.getColor().brighter());
/* 113:    */             } else {
/* 114: 96 */               g2d.setColor(g2d.getColor().darker().darker());
/* 115:    */             }
/* 116: 98 */             g2d.fillOval((int)x, y, (int)this.node_width, this.node_height);
/* 117: 99 */             if ((this.positives.contains(type)) || (this.negatives.contains(type))) {
/* 118:100 */               g2d.setColor(Color.DARK_GRAY);
/* 119:    */             } else {
/* 120:102 */               g2d.setColor(Color.LIGHT_GRAY);
/* 121:    */             }
/* 122:105 */             Rectangle2D box = g2d.getFontMetrics().getStringBounds(type, g2d);
/* 123:106 */             g2d.drawString(type, (int)(x + (this.node_width - box.getWidth()) / 2.0D), y + this.node_height / 2);
/* 124:    */           }
/* 125:    */           else
/* 126:    */           {
/* 127:108 */             g2d.setColor(Color.black);
/* 128:109 */             g2d.drawOval((int)x, y, (int)this.node_width, this.node_height);
/* 129:    */             
/* 130:111 */             Rectangle2D box = g2d.getFontMetrics().getStringBounds(type, g2d);
/* 131:112 */             g2d.drawString(type, (int)(x + (this.node_width - box.getWidth()) / 2.0D), y + this.node_height / 2);
/* 132:    */           }
/* 133:116 */           x += this.spacing * this.node_width;
/* 134:    */         }
/* 135:118 */         y += 2 * this.node_height;
/* 136:    */       }
/* 137:121 */       for (??? = this.map.keySet().iterator(); ???.hasNext(); type.hasNext())
/* 138:    */       {
/* 139:121 */         String type = (String)???.next();
/* 140:122 */         type = this.lattice.getParents(type).iterator(); continue;String parent = (String)type.next();
/* 141:123 */         Point2D p1 = (Point2D)this.map.get(parent);
/* 142:124 */         Point2D p2 = (Point2D)this.map.get(type);
/* 143:125 */         g2d.setColor(Color.gray);
/* 144:126 */         g2d.drawLine((int)(p1.getX() + this.node_width / 2.0F), (int)(p1.getY() + this.node_height), (int)(p2.getX() + this.node_width / 2.0F), (int)p2.getY());
/* 145:127 */         g2d.drawOval((int)(p2.getX() + this.node_width / 2.0F - 2.0D), (int)(p2.getY() - 2.0D), 4, 4);
/* 146:    */       }
/* 147:    */     }
/* 148:    */     
/* 149:    */     public void mouseClicked(MouseEvent e)
/* 150:    */     {
/* 151:133 */       Point xy = e.getPoint();
/* 152:    */       
/* 153:135 */       String closest = "None";
/* 154:136 */       double dist = 1.7976931348623157E+308D;
/* 155:137 */       for (String type : this.map.keySet())
/* 156:    */       {
/* 157:138 */         Point2D corner = (Point2D)this.map.get(type);
/* 158:139 */         Point2D center = new Point2D.Double(corner.getX() + this.node_width / 2.0F, corner.getY() + this.node_height / 2);
/* 159:140 */         if (xy.distanceSq(center) < dist)
/* 160:    */         {
/* 161:141 */           dist = xy.distanceSq(center);
/* 162:142 */           closest = type;
/* 163:    */         }
/* 164:    */       }
/* 165:146 */       if (e.getButton() == 1)
/* 166:    */       {
/* 167:147 */         this.positives.add(closest);
/* 168:148 */         this.concept.learnPositive(closest);
/* 169:    */       }
/* 170:    */       else
/* 171:    */       {
/* 172:150 */         this.negatives.add(closest);
/* 173:151 */         this.concept.learnNegative(closest);
/* 174:    */       }
/* 175:154 */       repaint();
/* 176:155 */       this.textfield.setText(this.concept.toString());
/* 177:    */     }
/* 178:    */     
/* 179:    */     public void mouseEntered(MouseEvent e) {}
/* 180:    */     
/* 181:    */     public void mouseExited(MouseEvent e) {}
/* 182:    */     
/* 183:    */     public void mousePressed(MouseEvent e) {}
/* 184:    */     
/* 185:    */     public void mouseReleased(MouseEvent e) {}
/* 186:    */   }
/* 187:    */   
/* 188:    */   public UI()
/* 189:    */   {
/* 190:179 */     this.text = new JTextArea("entity animate animal mammal cat\nmammal dog\nanimal fish\nanimate plant\nentity inanimate rug\ninanimate car\n");
/* 191:    */     
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:186 */     this.analysis = new JTextArea();
/* 198:187 */     this.analysis.setEditable(false);
/* 199:188 */     this.analysis.setFont(this.analysis.getFont().deriveFont(20.0F));
/* 200:189 */     this.analysis.setWrapStyleWord(true);
/* 201:    */     
/* 202:191 */     JPanel right = new JPanel(new BorderLayout());
/* 203:192 */     right.add(this.text, "Center");
/* 204:193 */     right.add(this.analysis, "South");
/* 205:    */     
/* 206:195 */     List<Thread> threads = new ArrayList();
/* 207:196 */     for (String string : this.text.getText().split("\n")) {
/* 208:197 */       threads.add(Thread.parse(string));
/* 209:    */     }
/* 210:199 */     TypeLattice lattice = new TypeLattice(threads);
/* 211:200 */     this.lp = new LatticePanel(lattice, this.analysis);
/* 212:201 */     JSplitPane split = new JSplitPane(1, this.lp, right);
/* 213:202 */     split.setResizeWeight(0.8D);
/* 214:203 */     split.setContinuousLayout(true);
/* 215:    */     
/* 216:205 */     getContentPane().setLayout(new GridLayout());
/* 217:206 */     getContentPane().add(split);
/* 218:    */     
/* 219:208 */     this.text.addKeyListener(this);
/* 220:    */     
/* 221:210 */     setSize(800, 600);
/* 222:211 */     setDefaultCloseOperation(3);
/* 223:    */   }
/* 224:    */   
/* 225:    */   public static void main(String[] args)
/* 226:    */   {
/* 227:    */     try
/* 228:    */     {
/* 229:216 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/* 230:    */     }
/* 231:    */     catch (UnsupportedLookAndFeelException localUnsupportedLookAndFeelException) {}catch (IllegalAccessException localIllegalAccessException) {}catch (InstantiationException localInstantiationException) {}catch (ClassNotFoundException localClassNotFoundException) {}
/* 232:227 */     UI ui = new UI();
/* 233:228 */     ui.setVisible(true);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void keyPressed(KeyEvent e) {}
/* 237:    */   
/* 238:    */   public void keyReleased(KeyEvent e) {}
/* 239:    */   
/* 240:    */   public void keyTyped(KeyEvent e)
/* 241:    */   {
/* 242:243 */     if (KeyEvent.getKeyText(e.getKeyChar()).equalsIgnoreCase("Enter"))
/* 243:    */     {
/* 244:244 */       List<Thread> threads = new ArrayList();
/* 245:245 */       for (String string : this.text.getText().split("\n")) {
/* 246:246 */         threads.add(Thread.parse(string));
/* 247:    */       }
/* 248:248 */       this.lp.setLattice(new TypeLattice(threads));
/* 249:249 */       this.lp.repaint();
/* 250:    */     }
/* 251:    */   }
/* 252:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     lattice.UI
 * JD-Core Version:    0.7.0.1
 */