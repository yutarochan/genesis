/*   1:    */ package bridge.views.frameviews.classic;
/*   2:    */ 
/*   3:    */ import bridge.adapters.EntityToViewerTranslator;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Sequence;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import gui.NegatableJPanel;
/*  10:    */ import java.awt.Color;
/*  11:    */ import java.awt.Container;
/*  12:    */ import java.awt.Dimension;
/*  13:    */ import java.awt.GridLayout;
/*  14:    */ import java.awt.Insets;
/*  15:    */ import java.awt.Rectangle;
/*  16:    */ import java.util.Vector;
/*  17:    */ import javax.swing.JFrame;
/*  18:    */ import javax.swing.JScrollPane;
/*  19:    */ import javax.swing.JToolTip;
/*  20:    */ import javax.swing.JViewport;
/*  21:    */ import javax.swing.border.Border;
/*  22:    */ import javax.swing.border.LineBorder;
/*  23:    */ import javax.swing.border.TitledBorder;
/*  24:    */ 
/*  25:    */ public class FrameViewer
/*  26:    */   extends JScrollPane
/*  27:    */   implements WiredBox
/*  28:    */ {
/*  29:    */   public static final int SCROLL_ALWAYS = 2;
/*  30:    */   public static final int SCROLL_AS_NEEDED = 1;
/*  31:    */   public static final int SCROLL_NEVER = 0;
/*  32: 25 */   protected int index = 0;
/*  33:    */   protected String title;
/*  34: 29 */   protected Vector panels = new Vector();
/*  35:    */   protected GridLayout layout;
/*  36: 33 */   protected TitledBorder border = new TitledBorder("");
/*  37: 35 */   protected int id = -1;
/*  38:    */   protected NegatableJPanel bodyPanel;
/*  39:    */   protected int scrollMode;
/*  40:    */   
/*  41:    */   public NegatableJPanel getBodyPanel()
/*  42:    */   {
/*  43: 40 */     if (this.bodyPanel == null) {
/*  44: 41 */       this.bodyPanel = new NegatableJPanel();
/*  45:    */     }
/*  46: 43 */     return this.bodyPanel;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public FrameViewer()
/*  50:    */   {
/*  51: 50 */     this.layout = new GridLayout(1, 0);
/*  52: 51 */     getBodyPanel().setLayout(this.layout);
/*  53: 52 */     setOpaque(false);
/*  54: 53 */     getBodyPanel().setOpaque(true);
/*  55: 54 */     getBodyPanel().setBackground(Color.WHITE);
/*  56: 55 */     getViewport().setOpaque(true);
/*  57: 56 */     setViewportView(getBodyPanel());
/*  58: 57 */     setAtPreferredSize();
/*  59: 58 */     setScrollable(0);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public FrameViewer(String t)
/*  63:    */   {
/*  64: 62 */     this();
/*  65: 63 */     this.title = t;
/*  66: 64 */     this.border.setTitle(this.title);
/*  67: 65 */     setBorder(this.border);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public FrameViewer(String t, int scrollable)
/*  71:    */   {
/*  72: 69 */     this(t);
/*  73: 70 */     setScrollable(scrollable);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public FrameViewer(String t, Color color)
/*  77:    */   {
/*  78: 75 */     this();
/*  79: 76 */     this.title = t;
/*  80: 77 */     this.border = new TitledBorder(new LineBorder(color, 2), this.title);
/*  81: 78 */     setBorder(this.border);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setScrollable(int scrollMode)
/*  85:    */   {
/*  86: 84 */     if (scrollMode == 2) {
/*  87: 85 */       setHorizontalScrollBarPolicy(32);
/*  88: 87 */     } else if (scrollMode == 1) {
/*  89: 88 */       setHorizontalScrollBarPolicy(30);
/*  90: 90 */     } else if (scrollMode == 0) {
/*  91: 91 */       setHorizontalScrollBarPolicy(31);
/*  92:    */     } else {
/*  93: 94 */       throw new IllegalArgumentException("setScrollable called with bad argument -- expected SCROLL_ALWAYS, SCROLL_AS_NEEDED, or SCROLL_NEVER");
/*  94:    */     }
/*  95: 96 */     setVerticalScrollBarPolicy(21);
/*  96: 97 */     this.scrollMode = scrollMode;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Vector getPanels()
/* 100:    */   {
/* 101:101 */     return this.panels;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setID(int i)
/* 105:    */   {
/* 106:105 */     this.id = i;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public int getID()
/* 110:    */   {
/* 111:109 */     return this.id;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setInput(Entity t)
/* 115:    */   {
/* 116:113 */     setInput(EntityToViewerTranslator.translate(t));
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setInput(FrameBundle b)
/* 120:    */   {
/* 121:117 */     setInput(this.index, b);
/* 122:118 */     revalidate();
/* 123:119 */     repaint();
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setStory(Sequence s)
/* 127:    */   {
/* 128:123 */     Vector<Entity> v = s.getElements();
/* 129:124 */     Vector<FrameBundle> bundles = new Vector();
/* 130:125 */     for (Entity t : v) {
/* 131:126 */       bundles.add(EntityToViewerTranslator.translate(t, EntityToViewerTranslator.SHOW_NO_THREADS));
/* 132:    */     }
/* 133:128 */     setInputVector(bundles);
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setInputVector(Vector v)
/* 137:    */   {
/* 138:132 */     clearData();
/* 139:133 */     if (v == null) {
/* 140:134 */       return;
/* 141:    */     }
/* 142:136 */     for (int i = 0; i < v.size(); i++) {
/* 143:137 */       if (((v.elementAt(i) instanceof FrameBundle)) || ((v.elementAt(i) instanceof Entity))) {}
/* 144:    */     }
/* 145:143 */     for (int i = 0; i < v.size(); i++) {
/* 146:144 */       if ((v.elementAt(i) instanceof FrameBundle))
/* 147:    */       {
/* 148:145 */         FrameBundle bundle = (FrameBundle)v.elementAt(i);
/* 149:146 */         setInput(i, bundle);
/* 150:    */       }
/* 151:148 */       else if ((v.elementAt(i) instanceof Entity))
/* 152:    */       {
/* 153:149 */         Entity t = (Entity)v.elementAt(i);
/* 154:150 */         FrameBundle bundle = EntityToViewerTranslator.translate(t);
/* 155:151 */         setInput(i, bundle);
/* 156:    */       }
/* 157:    */     }
/* 158:154 */     revalidate();
/* 159:155 */     repaint();
/* 160:    */   }
/* 161:    */   
/* 162:    */   private void setInput(int panel, FrameBundle b)
/* 163:    */   {
/* 164:159 */     if (panel >= this.panels.size()) {
/* 165:160 */       for (int i = this.panels.size(); i <= panel; i++)
/* 166:    */       {
/* 167:161 */         BasicFrameViewer bfv = new BasicFrameViewer();
/* 168:162 */         this.panels.add(bfv);
/* 169:163 */         getBodyPanel().add(bfv);
/* 170:164 */         revalidate();
/* 171:165 */         repaint();
/* 172:    */       }
/* 173:    */     }
/* 174:168 */     BasicFrameViewer target = (BasicFrameViewer)this.panels.elementAt(panel);
/* 175:    */     
/* 176:170 */     target.setInput(b);
/* 177:171 */     repaint();
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setTitle(String t)
/* 181:    */   {
/* 182:179 */     this.title = t;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getTitle()
/* 186:    */   {
/* 187:183 */     return this.title;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setBorder(String t)
/* 191:    */   {
/* 192:187 */     setTitle(t);
/* 193:188 */     getBodyPanel().setBorder(new TitledBorder(this.title));
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setBorderColor(Color color)
/* 197:    */   {
/* 198:192 */     this.border = new TitledBorder(new LineBorder(color, 2), this.title);
/* 199:193 */     getBodyPanel().setBorder(this.border);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void clearData()
/* 203:    */   {
/* 204:197 */     this.index = 0;
/* 205:198 */     this.panels.clear();
/* 206:199 */     getBodyPanel().removeAll();
/* 207:200 */     repaint();
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setAtPreferredSize()
/* 211:    */   {
/* 212:206 */     if (this.panels == null) {
/* 213:206 */       return;
/* 214:    */     }
/* 215:207 */     for (int i = 0; i < this.panels.size(); i++)
/* 216:    */     {
/* 217:208 */       BasicFrameViewer bfv = (BasicFrameViewer)this.panels.get(i);
/* 218:209 */       Dimension size = bfv.getPreferredSize();
/* 219:210 */       size.height = Math.min(size.height, getViewportBorderBounds().height);
/* 220:211 */       bfv.setSize(size);
/* 221:    */     }
/* 222:213 */     Dimension preferredSize = getPreferredSize();
/* 223:214 */     preferredSize.height = Math.min(preferredSize.height, getViewportBorderBounds().height);
/* 224:215 */     getBodyPanel().setSize(preferredSize);
/* 225:    */     
/* 226:217 */     getBodyPanel().repaint();
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void reshape(int x, int y, int w, int h)
/* 230:    */   {
/* 231:225 */     setAtPreferredSize();
/* 232:226 */     super.reshape(x, y, w, h);
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void repaint()
/* 236:    */   {
/* 237:234 */     setAtPreferredSize();
/* 238:235 */     super.repaint();
/* 239:    */   }
/* 240:    */   
/* 241:    */   public Dimension getPreferredSize()
/* 242:    */   {
/* 243:239 */     int prefWidth = 0;
/* 244:240 */     int prefHeight = 0;
/* 245:242 */     for (int i = 0; i < getPanels().size(); i++)
/* 246:    */     {
/* 247:243 */       BasicFrameViewer bfv = (BasicFrameViewer)this.panels.get(i);
/* 248:244 */       prefWidth += bfv.getPreferredSize().width;
/* 249:245 */       prefHeight = Math.max(prefHeight, bfv.getPreferredSize().height);
/* 250:    */     }
/* 251:247 */     if (getBodyPanel().getBorder() != null)
/* 252:    */     {
/* 253:248 */       Insets insets = getBodyPanel().getBorder().getBorderInsets(this);
/* 254:249 */       prefWidth = prefWidth + insets.left + insets.right;
/* 255:    */     }
/* 256:253 */     return new Dimension(prefWidth, prefHeight);
/* 257:    */   }
/* 258:    */   
/* 259:    */   public JToolTip createToolTip()
/* 260:    */   {
/* 261:260 */     return new MultilineToolTip();
/* 262:    */   }
/* 263:    */   
/* 264:    */   public static void main(String[] ignore)
/* 265:    */   {
/* 266:264 */     JFrame frame = new JFrame();
/* 267:265 */     FrameViewer view = new FrameViewer();
/* 268:266 */     frame.getContentPane().add(view);
/* 269:267 */     Entity t = new Entity("Patrick");
/* 270:268 */     Function d = new Function(t);
/* 271:269 */     d.addType("Foo");
/* 272:270 */     d.addType("Bar");
/* 273:271 */     Relation r = new Relation(d, d);
/* 274:272 */     r.addType("X");
/* 275:273 */     r.addType("Y");
/* 276:274 */     r.addType("Z");
/* 277:275 */     view.setInput(r);
/* 278:276 */     frame.setBounds(0, 0, 200, 200);
/* 279:277 */     frame.setVisible(true);
/* 280:    */   }
/* 281:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.views.frameviews.classic.FrameViewer
 * JD-Core Version:    0.7.0.1
 */