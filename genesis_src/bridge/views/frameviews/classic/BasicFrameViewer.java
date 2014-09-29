/*   1:    */ package bridge.views.frameviews.classic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.Font;
/*   7:    */ import java.awt.FontMetrics;
/*   8:    */ import java.awt.Graphics;
/*   9:    */ import java.awt.Point;
/*  10:    */ import java.awt.event.MouseEvent;
/*  11:    */ import java.awt.event.MouseListener;
/*  12:    */ import java.awt.event.MouseMotionListener;
/*  13:    */ import java.awt.event.WindowAdapter;
/*  14:    */ import java.awt.event.WindowEvent;
/*  15:    */ import java.awt.geom.Rectangle2D;
/*  16:    */ import java.io.PrintStream;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Vector;
/*  20:    */ import javax.swing.JComponent;
/*  21:    */ import javax.swing.JFrame;
/*  22:    */ import javax.swing.JTextArea;
/*  23:    */ import javax.swing.Popup;
/*  24:    */ import javax.swing.PopupFactory;
/*  25:    */ import javax.swing.SwingUtilities;
/*  26:    */ 
/*  27:    */ public class BasicFrameViewer
/*  28:    */   extends JComponent
/*  29:    */   implements MouseMotionListener, MouseListener
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32: 40 */   public static boolean DEBUG_FULL_FONT_INFO = false;
/*  33: 42 */   private FrameBundle data = null;
/*  34:    */   private static final double shrinkFactor = 0.9D;
/*  35:    */   private static final double padFactor = 1.25D;
/*  36: 48 */   private String banner = null;
/*  37: 50 */   private int preferredWidth = 0;
/*  38: 57 */   private Map<bridge.utils.Rectangle, String> pointMap = null;
/*  39:    */   private Point point;
/*  40:    */   static Popup popup;
/*  41:    */   
/*  42:    */   public void setBanner(String s)
/*  43:    */   {
/*  44: 70 */     this.banner = s;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setInput(FrameBundle b)
/*  48:    */   {
/*  49: 74 */     this.data = b;
/*  50: 75 */     repaint();
/*  51:    */   }
/*  52:    */   
/*  53:    */   public BasicFrameViewer()
/*  54:    */   {
/*  55: 80 */     this.pointMap = null;
/*  56: 81 */     this.pointMap = new HashMap();
/*  57: 82 */     addMouseMotionListener(this);
/*  58: 83 */     addMouseListener(this);
/*  59: 84 */     this.point = new Point();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void paintComponent(Graphics g)
/*  63:    */   {
/*  64: 92 */     this.pointMap.clear();
/*  65: 93 */     int height = getHeight();
/*  66: 94 */     int width = getWidth();
/*  67: 96 */     if (this.banner != null)
/*  68:    */     {
/*  69: 97 */       paintBanner(g, width, height, this.banner);
/*  70: 98 */       return;
/*  71:    */     }
/*  72:101 */     this.preferredWidth = 0;
/*  73:102 */     if (this.data == null) {
/*  74:103 */       return;
/*  75:    */     }
/*  76:105 */     double hfactor = getBundleHeightFactor(this.data);
/*  77:106 */     if (DEBUG_FULL_FONT_INFO) {
/*  78:107 */       System.out.println("Bundle Size: " + hfactor);
/*  79:    */     }
/*  80:109 */     paintFrameBundle(g, this.data, 0, 0, width, height, Math.min(height / hfactor, 25.0D));
/*  81:    */   }
/*  82:    */   
/*  83:    */   private void paintBanner(Graphics g, int w, int h, String m)
/*  84:    */   {
/*  85:114 */     Font f = g.getFont();
/*  86:115 */     String name = f.getName();
/*  87:116 */     int style = f.getStyle();
/*  88:117 */     int wSpan = 0;
/*  89:118 */     int hSpan = 0;
/*  90:119 */     for (int size = 30; size > 0; size -= 2)
/*  91:    */     {
/*  92:120 */       g.setFont(new Font(name, style, size));
/*  93:121 */       FontMetrics fm = g.getFontMetrics();
/*  94:122 */       wSpan = fm.stringWidth(m);
/*  95:123 */       hSpan = fm.getAscent();
/*  96:124 */       if ((w > wSpan) && (h > hSpan)) {
/*  97:    */         break;
/*  98:    */       }
/*  99:    */     }
/* 100:128 */     g.drawString(this.banner, (w - wSpan) / 2, (h + hSpan) / 2);
/* 101:    */   }
/* 102:    */   
/* 103:    */   private void paintFrameBundle(Graphics g, FrameBundle bundle, int x, int y, int width, int height, double preciseFontHeight)
/* 104:    */   {
/* 105:135 */     int topPrefWidth = 0;int bottomPrefWidth = 0;int maxPreferredWidth = 0;
/* 106:138 */     if ((height == 0) || (width == 0)) {
/* 107:139 */       return;
/* 108:    */     }
/* 109:143 */     if (this.data == null) {
/* 110:144 */       return;
/* 111:    */     }
/* 112:148 */     double nextFontHeight = preciseFontHeight * 0.9D;
/* 113:149 */     int fontHeight = (int)preciseFontHeight;
/* 114:150 */     int halfFontHeight = (int)(preciseFontHeight * 0.5D);
/* 115:151 */     int quarterFontHeight = (int)(preciseFontHeight * 0.25D);
/* 116:152 */     int threeQuarterFontHeight = (int)(preciseFontHeight * 0.75D);
/* 117:153 */     int topFontHeight = Math.min(fontHeight, height / 3);
/* 118:154 */     int bottomFontHeight = Math.min(threeQuarterFontHeight, height / 4);
/* 119:155 */     int lineWidth = 5;
/* 120:158 */     if (topFontHeight > 0)
/* 121:    */     {
/* 122:159 */       g.setFont(g.getFont().deriveFont(topFontHeight));
/* 123:160 */       if (DEBUG_FULL_FONT_INFO)
/* 124:    */       {
/* 125:161 */         FontMetricz fm = new FontMetricz(g.getFont().deriveFont(topFontHeight));
/* 126:162 */         System.out.println("FH: " + fm.getHeight());
/* 127:    */       }
/* 128:164 */       FontMetrics fm = g.getFontMetrics();
/* 129:165 */       topPrefWidth = fm.getStringBounds(bundle.getTop(), g).getBounds().width;
/* 130:166 */       topPrefWidth = x + fontHeight + lineWidth + topPrefWidth;
/* 131:167 */       g.drawString(bundle.getTop(), x + fontHeight + lineWidth, y + topFontHeight);
/* 132:168 */       if (bundle.isNegated())
/* 133:    */       {
/* 134:169 */         Color handle = g.getColor();
/* 135:170 */         String theTop = bundle.getTop();
/* 136:171 */         int xOrigin = x + fontHeight + lineWidth;
/* 137:172 */         int yOrigin = y + fm.getHeight() / 2;
/* 138:173 */         int stringWidth = fm.stringWidth(theTop);
/* 139:174 */         g.setColor(Color.RED);
/* 140:175 */         g.fillRect(xOrigin, yOrigin, stringWidth, 2);
/* 141:176 */         g.setColor(handle);
/* 142:    */       }
/* 143:    */     }
/* 144:181 */     if (bottomFontHeight > 0)
/* 145:    */     {
/* 146:182 */       g.setFont(g.getFont().deriveFont(bottomFontHeight));
/* 147:183 */       if (DEBUG_FULL_FONT_INFO)
/* 148:    */       {
/* 149:184 */         FontMetricz fm = new FontMetricz(g.getFont().deriveFont(bottomFontHeight));
/* 150:185 */         System.out.println("FB: " + fm.getHeight());
/* 151:    */       }
/* 152:187 */       bottomPrefWidth = g.getFontMetrics().getStringBounds(bundle.getBottom(), g).getBounds().width;
/* 153:188 */       bottomPrefWidth = x + fontHeight + lineWidth + bottomPrefWidth;
/* 154:    */       
/* 155:    */ 
/* 156:    */ 
/* 157:192 */       g.drawString(bundle.getBottom(), x + fontHeight + lineWidth, y + height - bottomFontHeight);
/* 158:    */       
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:197 */       this.pointMap.put(new bridge.utils.Rectangle(x + halfFontHeight, y + quarterFontHeight, lineWidth, height - halfFontHeight), bundle.getListenerBottom());
/* 163:    */     }
/* 164:201 */     Color colorHandle = g.getColor();
/* 165:202 */     g.setColor(bundle.getBarColor());
/* 166:203 */     g.fillRect(x + halfFontHeight, y + quarterFontHeight, lineWidth, height - halfFontHeight);
/* 167:204 */     g.setColor(colorHandle);
/* 168:    */     
/* 169:    */ 
/* 170:207 */     maxPreferredWidth = Math.max(bottomPrefWidth, topPrefWidth);
/* 171:208 */     this.preferredWidth = Math.max(maxPreferredWidth, this.preferredWidth);
/* 172:    */     
/* 173:    */ 
/* 174:    */ 
/* 175:212 */     int xOffset = fontHeight;
/* 176:213 */     double yOffset = preciseFontHeight * 1.25D;
/* 177:214 */     double bundleHeight = height - 2.0D * yOffset;
/* 178:215 */     double sum = 0.0D;
/* 179:216 */     Vector bundles = bundle.getFrameBundles();
/* 180:217 */     double total = 0.0D;
/* 181:218 */     for (int i = 0; i < bundles.size(); i++)
/* 182:    */     {
/* 183:219 */       FrameBundle innerBundle = (FrameBundle)bundles.elementAt(i);
/* 184:220 */       total += getBundleHeightFactor(innerBundle);
/* 185:    */     }
/* 186:222 */     for (int i = 0; i < bundles.size(); i++)
/* 187:    */     {
/* 188:223 */       FrameBundle innerBundle = (FrameBundle)bundles.elementAt(i);
/* 189:224 */       int thisBundleHeight = (int)(bundleHeight * (getBundleHeightFactor(innerBundle) / total));
/* 190:225 */       paintFrameBundle(g, innerBundle, x + xOffset, (int)(y + yOffset + sum), (int)(width - yOffset), thisBundleHeight, nextFontHeight);
/* 191:226 */       sum += thisBundleHeight;
/* 192:    */     }
/* 193:    */   }
/* 194:    */   
/* 195:    */   private double getBundleHeightFactor(FrameBundle bundle)
/* 196:    */   {
/* 197:231 */     double bhf = 2.5D;
/* 198:232 */     Vector bundles = bundle.getFrameBundles();
/* 199:233 */     for (int i = 0; i < bundles.size(); i++)
/* 200:    */     {
/* 201:234 */       FrameBundle innerBundle = (FrameBundle)bundles.elementAt(i);
/* 202:235 */       bhf += 0.9D * getBundleHeightFactor(innerBundle);
/* 203:    */     }
/* 204:237 */     return bhf;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public Dimension getPreferredSize()
/* 208:    */   {
/* 209:241 */     return new Dimension(this.preferredWidth, getHeight());
/* 210:    */   }
/* 211:    */   
/* 212:    */   public static void main(String[] args)
/* 213:    */   {
/* 214:245 */     JFrame frame = new JFrame();
/* 215:246 */     FrameViewer display = new FrameViewer();
/* 216:247 */     Vector v = new Vector();
/* 217:248 */     v.add("Go");
/* 218:249 */     v.add("Move");
/* 219:250 */     v.add("Thing");
/* 220:251 */     FrameBundle bundle1 = new FrameBundle("Go", v, true);
/* 221:252 */     FrameBundle bundle2 = new FrameBundle("Bird", v, false);
/* 222:253 */     FrameBundle bundle3 = new FrameBundle("Tree", v, true);
/* 223:254 */     bundle1.addFrameBundle(bundle2);
/* 224:255 */     bundle2.addFrameBundle(bundle3);
/* 225:256 */     System.out.println("Depth: " + bundle1.depth());
/* 226:257 */     System.out.println("Depth: " + bundle2.depth());
/* 227:258 */     System.out.println("Depth: " + bundle3.depth());
/* 228:259 */     display.setInput(bundle1);
/* 229:260 */     frame.getContentPane().add(display, "Center");
/* 230:261 */     frame.setSize(600, 400);
/* 231:262 */     frame.show();
/* 232:263 */     frame.addWindowListener(new WindowAdapter()
/* 233:    */     {
/* 234:    */       public void windowClosing(WindowEvent e)
/* 235:    */       {
/* 236:265 */         System.exit(0);
/* 237:    */       }
/* 238:    */     });
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void mouseDragged(MouseEvent arg0) {}
/* 242:    */   
/* 243:    */   public void mouseMoved(MouseEvent e)
/* 244:    */   {
/* 245:281 */     int x = e.getX();
/* 246:282 */     int y = e.getY();
/* 247:283 */     this.point.setLocation(x, y);
/* 248:    */     
/* 249:285 */     String threads = "";
/* 250:287 */     for (bridge.utils.Rectangle rect : this.pointMap.keySet()) {
/* 251:288 */       if (rect.contains(x, y)) {
/* 252:289 */         threads = (String)this.pointMap.get(rect);
/* 253:    */       }
/* 254:    */     }
/* 255:293 */     if ((threads.isEmpty()) && 
/* 256:294 */       (popup != null)) {
/* 257:295 */       popup.hide();
/* 258:    */     }
/* 259:302 */     if (!threads.equals(""))
/* 260:    */     {
/* 261:303 */       String[] splitThreads = threads.split(",");
/* 262:304 */       threads = "";
/* 263:305 */       for (String thread : splitThreads)
/* 264:    */       {
/* 265:306 */         threads = threads.concat(thread);
/* 266:307 */         threads = threads.concat("\n");
/* 267:    */       }
/* 268:313 */       SwingUtilities.convertPointToScreen(this.point, this);
/* 269:314 */       if (popup != null) {
/* 270:315 */         popup.hide();
/* 271:    */       }
/* 272:317 */       popup = PopupFactory.getSharedInstance().getPopup(this, new JTextArea(threads), (int)this.point.getX(), (int)this.point.getY());
/* 273:318 */       popup.show();
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void mouseClicked(MouseEvent e) {}
/* 278:    */   
/* 279:    */   public void mousePressed(MouseEvent e) {}
/* 280:    */   
/* 281:    */   public void mouseReleased(MouseEvent e) {}
/* 282:    */   
/* 283:    */   public void mouseEntered(MouseEvent e) {}
/* 284:    */   
/* 285:    */   public void mouseExited(MouseEvent e) {}
/* 286:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.views.frameviews.classic.BasicFrameViewer
 * JD-Core Version:    0.7.0.1
 */