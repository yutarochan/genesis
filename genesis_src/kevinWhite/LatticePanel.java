/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Font;
/*   5:    */ import java.awt.FontMetrics;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ import java.awt.Graphics2D;
/*   8:    */ import java.awt.Point;
/*   9:    */ import java.awt.Rectangle;
/*  10:    */ import java.awt.event.MouseEvent;
/*  11:    */ import java.awt.event.MouseListener;
/*  12:    */ import java.awt.geom.Point2D;
/*  13:    */ import java.awt.geom.Point2D.Double;
/*  14:    */ import java.awt.geom.Point2D.Float;
/*  15:    */ import java.awt.geom.Rectangle2D;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.HashSet;
/*  18:    */ import java.util.Iterator;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import java.util.Set;
/*  22:    */ import javax.swing.JPanel;
/*  23:    */ import javax.swing.JTextArea;
/*  24:    */ 
/*  25:    */ public class LatticePanel
/*  26:    */   extends JPanel
/*  27:    */   implements MouseListener
/*  28:    */ {
/*  29:    */   private TypeLattice lattice;
/*  30:    */   private Concept<String> concept;
/*  31:    */   private JTextArea textfield;
/*  32:    */   private float node_width;
/*  33:    */   private int node_height;
/*  34: 31 */   private float spacing = 1.5F;
/*  35:    */   private Map<String, Point2D> map;
/*  36: 34 */   private Set<String> positives = new HashSet();
/*  37: 35 */   private Set<String> negatives = new HashSet();
/*  38:    */   
/*  39:    */   public LatticePanel(TypeLattice lattice, JTextArea textfield)
/*  40:    */   {
/*  41: 38 */     setLattice(lattice);
/*  42: 39 */     addMouseListener(this);
/*  43: 40 */     this.textfield = textfield;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void setLattice(TypeLattice lattice)
/*  47:    */   {
/*  48: 44 */     this.lattice = lattice;
/*  49: 45 */     this.concept = new FasterLLConcept(lattice);
/*  50: 46 */     this.positives.clear();
/*  51: 47 */     this.negatives.clear();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public TypeLattice getLattice()
/*  55:    */   {
/*  56: 51 */     return this.lattice;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public Map<String, Point2D> getMap()
/*  60:    */   {
/*  61: 55 */     return this.map;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Concept<String> getConcept()
/*  65:    */   {
/*  66: 59 */     return this.concept;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Set<String> getPositives()
/*  70:    */   {
/*  71: 63 */     return this.positives;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Set<String> getNegatives()
/*  75:    */   {
/*  76: 67 */     return this.negatives;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void paintComponent(Graphics g)
/*  80:    */   {
/*  81: 73 */     super.paintComponent(g);
/*  82:    */     
/*  83: 75 */     Graphics2D g2d = (Graphics2D)g;
/*  84: 76 */     Font font = g2d.getFont().deriveFont(1).deriveFont(36.0F);
/*  85: 77 */     g2d.setFont(font);
/*  86:    */     
/*  87: 79 */     g2d.setColor(Color.white);
/*  88: 80 */     int height = g2d.getClipBounds().height;
/*  89: 81 */     int width = g2d.getClipBounds().width;
/*  90: 82 */     g2d.fillRect(0, 0, width, height);
/*  91:    */     
/*  92: 84 */     List<Set<String>> sorted = this.lattice.topologicalSort();
/*  93:    */     
/*  94: 86 */     int layers = sorted.size() * 2 - 1;
/*  95: 87 */     this.node_height = (height / layers);
/*  96:    */     
/*  97: 89 */     int columns = 0;
/*  98: 90 */     for (Set<String> c : sorted) {
/*  99: 91 */       columns = Math.max(columns, c.size());
/* 100:    */     }
/* 101: 94 */     this.node_width = (width / (this.spacing * columns));
/* 102:    */     
/* 103: 96 */     this.map = new HashMap();
/* 104: 97 */     int y = 0;
/* 105:    */     String type;
/* 106: 99 */     for (Object c : sorted)
/* 107:    */     {
/* 108:100 */       float x = 0.0F;
/* 109:101 */       for (Iterator localIterator3 = ((Set)c).iterator(); localIterator3.hasNext();)
/* 110:    */       {
/* 111:101 */         type = (String)localIterator3.next();
/* 112:102 */         this.map.put(type, new Point2D.Float(x, y));
/* 113:104 */         if (this.positives.size() + this.negatives.size() > 0)
/* 114:    */         {
/* 115:105 */           g2d.setColor(this.concept.contains(type) ? Color.green : Color.red);
/* 116:106 */           LatticeLearner.getImplication(g2d.getColor().equals(Color.green), type, this.positives, this.negatives);
/* 117:107 */           if ((this.positives.contains(type)) || (this.negatives.contains(type))) {
/* 118:108 */             g2d.setColor(g2d.getColor().brighter());
/* 119:    */           } else {
/* 120:110 */             g2d.setColor(g2d.getColor().darker().darker());
/* 121:    */           }
/* 122:112 */           g2d.fillOval((int)x, y, (int)this.node_width, this.node_height);
/* 123:113 */           if ((this.positives.contains(type)) || (this.negatives.contains(type))) {
/* 124:114 */             g2d.setColor(Color.DARK_GRAY);
/* 125:    */           } else {
/* 126:116 */             g2d.setColor(Color.LIGHT_GRAY);
/* 127:    */           }
/* 128:120 */           FontMetrics fm = g2d.getFontMetrics();
/* 129:121 */           int txtWidth = fm.stringWidth(type);
/* 130:122 */           float fontSize = g2d.getFont().getSize();
/* 131:124 */           if (txtWidth > this.node_width) {
/* 132:125 */             while (txtWidth > this.node_width)
/* 133:    */             {
/* 134:126 */               fontSize -= 1.0F;
/* 135:127 */               Font tempFont = g.getFont().deriveFont(fontSize);
/* 136:128 */               g.setFont(tempFont);
/* 137:129 */               txtWidth = g.getFontMetrics().stringWidth(type);
/* 138:    */             }
/* 139:    */           }
/* 140:133 */           Rectangle2D box = g2d.getFontMetrics().getStringBounds(type, g2d);
/* 141:134 */           g2d.drawString(type, (int)(x + (this.node_width - box.getWidth()) / 2.0D), y + this.node_height / 2);
/* 142:    */         }
/* 143:    */         else
/* 144:    */         {
/* 145:136 */           g2d.setColor(Color.black);
/* 146:137 */           g2d.drawOval((int)x, y, (int)this.node_width, this.node_height);
/* 147:    */           
/* 148:    */ 
/* 149:    */ 
/* 150:141 */           FontMetrics fm = g2d.getFontMetrics();
/* 151:142 */           int txtWidth = fm.stringWidth(type);
/* 152:143 */           float fontSize = g2d.getFont().getSize();
/* 153:145 */           if (txtWidth > this.node_width) {
/* 154:146 */             while (txtWidth > this.node_width)
/* 155:    */             {
/* 156:147 */               fontSize -= 1.0F;
/* 157:148 */               Font tempFont = g.getFont().deriveFont(fontSize);
/* 158:149 */               g.setFont(tempFont);
/* 159:150 */               txtWidth = g.getFontMetrics().stringWidth(type);
/* 160:    */             }
/* 161:    */           }
/* 162:154 */           Rectangle2D box = g2d.getFontMetrics().getStringBounds(type, g2d);
/* 163:155 */           g2d.drawString(type, (int)(x + (this.node_width - box.getWidth()) / 2.0D), y + this.node_height / 2);
/* 164:    */         }
/* 165:157 */         x += this.spacing * this.node_width;
/* 166:158 */         g2d.setFont(font);
/* 167:    */       }
/* 168:160 */       y += 2 * this.node_height;
/* 169:    */     }
/* 170:163 */     for (??? = this.map.keySet().iterator(); ???.hasNext(); type.hasNext())
/* 171:    */     {
/* 172:163 */       String type = (String)???.next();
/* 173:164 */       type = this.lattice.getParents(type).iterator(); continue;String parent = (String)type.next();
/* 174:165 */       Point2D p1 = (Point2D)this.map.get(parent);
/* 175:166 */       Point2D p2 = (Point2D)this.map.get(type);
/* 176:167 */       g2d.setColor(Color.gray);
/* 177:168 */       g2d.drawLine((int)(p1.getX() + this.node_width / 2.0F), (int)(p1.getY() + this.node_height), (int)(p2.getX() + this.node_width / 2.0F), (int)p2.getY());
/* 178:169 */       g2d.drawOval((int)(p2.getX() + this.node_width / 2.0F - 2.0D), (int)(p2.getY() - 2.0D), 4, 4);
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void mouseClicked(MouseEvent e)
/* 183:    */   {
/* 184:175 */     Point xy = e.getPoint();
/* 185:    */     
/* 186:177 */     String closest = "None";
/* 187:178 */     double dist = 1.7976931348623157E+308D;
/* 188:179 */     for (String type : this.map.keySet())
/* 189:    */     {
/* 190:180 */       Point2D corner = (Point2D)this.map.get(type);
/* 191:181 */       Point2D center = new Point2D.Double(corner.getX() + this.node_width / 2.0F, corner.getY() + this.node_height / 2);
/* 192:182 */       if (xy.distanceSq(center) < dist)
/* 193:    */       {
/* 194:183 */         dist = xy.distanceSq(center);
/* 195:184 */         closest = type;
/* 196:    */       }
/* 197:    */     }
/* 198:188 */     if (e.getButton() == 1)
/* 199:    */     {
/* 200:189 */       this.positives.add(closest);
/* 201:190 */       this.concept.learnPositive(closest);
/* 202:    */     }
/* 203:    */     else
/* 204:    */     {
/* 205:192 */       this.negatives.add(closest);
/* 206:193 */       this.concept.learnNegative(closest);
/* 207:    */     }
/* 208:196 */     repaint();
/* 209:197 */     this.textfield.setText(this.concept.toString());
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void mouseEntered(MouseEvent e) {}
/* 213:    */   
/* 214:    */   public void mouseExited(MouseEvent e) {}
/* 215:    */   
/* 216:    */   public void mousePressed(MouseEvent e) {}
/* 217:    */   
/* 218:    */   public void mouseReleased(MouseEvent e) {}
/* 219:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.LatticePanel
 * JD-Core Version:    0.7.0.1
 */