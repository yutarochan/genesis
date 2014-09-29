/*   1:    */ package windowGroup;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Component;
/*   6:    */ import java.awt.Container;
/*   7:    */ import java.awt.GridLayout;
/*   8:    */ import java.awt.event.ActionEvent;
/*   9:    */ import java.awt.event.ActionListener;
/*  10:    */ import java.awt.event.MouseAdapter;
/*  11:    */ import java.awt.event.MouseEvent;
/*  12:    */ import java.awt.event.WindowAdapter;
/*  13:    */ import java.awt.event.WindowEvent;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Iterator;
/*  16:    */ import java.util.prefs.Preferences;
/*  17:    */ import javax.swing.AbstractAction;
/*  18:    */ import javax.swing.JButton;
/*  19:    */ import javax.swing.JComponent;
/*  20:    */ import javax.swing.JFrame;
/*  21:    */ import javax.swing.JLabel;
/*  22:    */ import javax.swing.JMenu;
/*  23:    */ import javax.swing.JMenuBar;
/*  24:    */ import javax.swing.JMenuItem;
/*  25:    */ import javax.swing.JPanel;
/*  26:    */ import utils.Mark;
/*  27:    */ 
/*  28:    */ public class WindowGroupManager
/*  29:    */ {
/*  30: 23 */   private ArrayList<NameComponentPair> nameComponentPairs = new ArrayList();
/*  31: 25 */   private ArrayList<WindowGroupHost> hosts = new ArrayList();
/*  32:    */   private MyActionListener actionListener;
/*  33:    */   private MyOtherActionListener otherListener;
/*  34: 31 */   private int selectionBarLimit = 5;
/*  35:    */   private JPanel summaryPanel;
/*  36:    */   private String name;
/*  37:    */   
/*  38:    */   public WindowGroupManager() {}
/*  39:    */   
/*  40:    */   public WindowGroupManager(int menuLimit)
/*  41:    */   {
/*  42: 45 */     this.selectionBarLimit = menuLimit;
/*  43: 46 */     addJComponent(getSummaryPanel());
/*  44:    */   }
/*  45:    */   
/*  46:    */   public WindowGroupManager(String name, int menuLimit)
/*  47:    */   {
/*  48: 54 */     this.name = name;
/*  49: 55 */     this.selectionBarLimit = menuLimit;
/*  50: 56 */     addJComponent(getSummaryPanel());
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void addJComponent(JComponent component)
/*  54:    */   {
/*  55: 63 */     this.nameComponentPairs.add(new NameComponentPair(component.getName(), component));
/*  56: 64 */     refreshMenus();
/*  57:    */   }
/*  58:    */   
/*  59:    */   public WindowGroupHost getHost()
/*  60:    */   {
/*  61: 71 */     WindowGroupHost windowGroupHost = new WindowGroupHost();
/*  62: 72 */     this.hosts.add(windowGroupHost);
/*  63: 73 */     refreshMenus();
/*  64: 74 */     return windowGroupHost;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public WindowGroupHost getHost(String label)
/*  68:    */   {
/*  69: 81 */     WindowGroupHost host = getHost();
/*  70:    */     try
/*  71:    */     {
/*  72: 83 */       setGuts(host, label);
/*  73:    */     }
/*  74:    */     catch (Exception e)
/*  75:    */     {
/*  76: 85 */       Mark.err(new Object[] {"Exception in getHost(label)" });
/*  77:    */     }
/*  78: 87 */     return host;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setGuts(WindowGroupHost theHost, String name)
/*  82:    */   {
/*  83: 94 */     JComponent oldGuts = theHost.getGuts();
/*  84: 95 */     JComponent newGuts = null;
/*  85: 96 */     String oldTitle = theHost.getTitle();
/*  86: 97 */     String newTitle = null;
/*  87: 98 */     for (NameComponentPair pair : this.nameComponentPairs) {
/*  88: 99 */       if ((pair.getComponentName() != null) && (pair.getComponentName().equalsIgnoreCase(name)))
/*  89:    */       {
/*  90:100 */         newGuts = pair.getComponent();
/*  91:101 */         newTitle = pair.getComponentName();
/*  92:102 */         break;
/*  93:    */       }
/*  94:    */     }
/*  95:105 */     if (newGuts == null)
/*  96:    */     {
/*  97:106 */       Mark.err(new Object[] {"Unable to find tab labeled " + name });
/*  98:107 */       return;
/*  99:    */     }
/* 100:109 */     theHost.setTitle(newTitle);
/* 101:110 */     theHost.setGuts(newGuts);
/* 102:111 */     for (WindowGroupHost host : this.hosts) {
/* 103:112 */       if ((host != theHost) && 
/* 104:113 */         (host.getGuts() == newGuts))
/* 105:    */       {
/* 106:114 */         host.setGuts(oldGuts);
/* 107:115 */         host.setTitle(oldTitle);
/* 108:    */       }
/* 109:    */     }
/* 110:119 */     for (WindowGroupHost host : this.hosts)
/* 111:    */     {
/* 112:120 */       JComponent parent = (JComponent)host.getParent();
/* 113:121 */       host.revalidate();
/* 114:122 */       host.repaint();
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setGuts(WindowGroupHost theHost, JComponent component)
/* 119:    */   {
/* 120:131 */     JComponent oldGuts = theHost.getGuts();
/* 121:132 */     JComponent newGuts = null;
/* 122:133 */     String oldTitle = theHost.getTitle();
/* 123:134 */     String newTitle = null;
/* 124:135 */     for (NameComponentPair pair : this.nameComponentPairs) {
/* 125:136 */       if ((pair.getComponent() != null) && (pair.getComponent().equals(component)))
/* 126:    */       {
/* 127:137 */         newGuts = pair.getComponent();
/* 128:138 */         newTitle = pair.getComponentName();
/* 129:139 */         break;
/* 130:    */       }
/* 131:    */     }
/* 132:142 */     if (newGuts == null)
/* 133:    */     {
/* 134:143 */       Mark.err(new Object[] {"Unable to find tab " + component });
/* 135:144 */       return;
/* 136:    */     }
/* 137:146 */     theHost.setTitle(newTitle);
/* 138:147 */     theHost.setGuts(newGuts);
/* 139:148 */     for (WindowGroupHost host : this.hosts) {
/* 140:149 */       if ((host != theHost) && 
/* 141:150 */         (host.getGuts() == newGuts))
/* 142:    */       {
/* 143:151 */         host.setGuts(oldGuts);
/* 144:152 */         host.setTitle(oldTitle);
/* 145:    */       }
/* 146:    */     }
/* 147:156 */     for (WindowGroupHost host : this.hosts)
/* 148:    */     {
/* 149:158 */       host.revalidate();
/* 150:159 */       host.repaint();
/* 151:    */     }
/* 152:161 */     getSummaryPanel();
/* 153:162 */     this.summaryPanel.removeAll();
/* 154:163 */     for (NameComponentPair pair : this.nameComponentPairs)
/* 155:    */     {
/* 156:164 */       JComponent view = pair.getComponent();
/* 157:165 */       if ((this.summaryPanel.getParent() != view) && (this.summaryPanel != view))
/* 158:    */       {
/* 159:166 */         JPanel thumbNail = new JPanel();
/* 160:167 */         JButton label = new JButton(new ActuationAction(pair, theHost));
/* 161:168 */         if (view.getName() == null)
/* 162:    */         {
/* 163:169 */           Mark.say(new Object[] {"Missing name for", view.getClass() });
/* 164:170 */           label.setText("Hello world");
/* 165:    */         }
/* 166:173 */         label.setOpaque(true);
/* 167:174 */         thumbNail.setLayout(new BorderLayout());
/* 168:176 */         if (isViewed(view))
/* 169:    */         {
/* 170:177 */           JLabel panel = new JLabel("Showing", 0);
/* 171:178 */           panel.setBackground(Color.GRAY);
/* 172:179 */           panel.setOpaque(true);
/* 173:180 */           thumbNail.add(panel, "Center");
/* 174:    */         }
/* 175:    */         else
/* 176:    */         {
/* 177:183 */           thumbNail.add(view, "Center");
/* 178:    */         }
/* 179:185 */         thumbNail.add(label, "South");
/* 180:186 */         getSummaryPanel().add(thumbNail);
/* 181:    */       }
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   private boolean isViewed(JComponent view)
/* 186:    */   {
/* 187:196 */     for (WindowGroupHost host : this.hosts) {
/* 188:197 */       if (host.getGuts() == view) {
/* 189:198 */         return true;
/* 190:    */       }
/* 191:    */     }
/* 192:201 */     return false;
/* 193:    */   }
/* 194:    */   
/* 195:    */   private void refreshMenus()
/* 196:    */   {
/* 197:206 */     for (WindowGroupHost host : this.hosts)
/* 198:    */     {
/* 199:207 */       JMenuBar bar = new JMenuBar();
/* 200:208 */       JMenu menu = new JMenu("|||");
/* 201:    */       
/* 202:210 */       bar.add(menu);
/* 203:211 */       for (int i = 0; i < this.nameComponentPairs.size(); i++)
/* 204:    */       {
/* 205:212 */         NameComponentPair pair = (NameComponentPair)this.nameComponentPairs.get(i);
/* 206:213 */         JMenuItem item = new JMenuItem(pair.getComponentName());
/* 207:214 */         menu.add(item);
/* 208:215 */         item.addActionListener(getMenuListener());
/* 209:216 */         if (i < this.selectionBarLimit + 1)
/* 210:    */         {
/* 211:217 */           JMenu element = new JMenu(pair.getComponentName());
/* 212:218 */           bar.add(element);
/* 213:219 */           element.addMouseListener(getOtherListener());
/* 214:    */         }
/* 215:    */       }
/* 216:222 */       host.setMenuBar(bar);
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   private MyOtherActionListener getOtherListener()
/* 221:    */   {
/* 222:227 */     if (this.otherListener == null) {
/* 223:228 */       this.otherListener = new MyOtherActionListener(null);
/* 224:    */     }
/* 225:230 */     return this.otherListener;
/* 226:    */   }
/* 227:    */   
/* 228:    */   private class MyOtherActionListener
/* 229:    */     extends MouseAdapter
/* 230:    */   {
/* 231:    */     private MyOtherActionListener() {}
/* 232:    */     
/* 233:    */     public void mousePressed(MouseEvent e)
/* 234:    */     {
/* 235:235 */       Object item = e.getSource();
/* 236:236 */       JComponent newGuts = null;
/* 237:237 */       for (WindowGroupHost host : WindowGroupManager.this.hosts)
/* 238:    */       {
/* 239:238 */         JMenuBar menubar = host.getMenuBar();
/* 240:239 */         for (int x = 0; x < menubar.getComponents().length; x++)
/* 241:    */         {
/* 242:240 */           Object object = menubar.getComponents()[x];
/* 243:241 */           if ((object instanceof JMenu))
/* 244:    */           {
/* 245:242 */             JMenu menu = (JMenu)object;
/* 246:243 */             if (menu == item)
/* 247:    */             {
/* 248:244 */               newGuts = ((WindowGroupManager.NameComponentPair)WindowGroupManager.this.nameComponentPairs.get(x - 1)).getComponent();
/* 249:    */               
/* 250:246 */               Preferences.userRoot().put(host.getName(), ((WindowGroupManager.NameComponentPair)WindowGroupManager.this.nameComponentPairs.get(x - 1)).getComponentName());
/* 251:247 */               WindowGroupManager.this.setGuts(host, newGuts);
/* 252:248 */               break;
/* 253:    */             }
/* 254:    */           }
/* 255:    */         }
/* 256:    */       }
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   private MyActionListener getMenuListener()
/* 261:    */   {
/* 262:257 */     if (this.actionListener == null) {
/* 263:258 */       this.actionListener = new MyActionListener(null);
/* 264:    */     }
/* 265:260 */     return this.actionListener;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public JPanel getSummaryPanel()
/* 269:    */   {
/* 270:267 */     if (this.summaryPanel == null)
/* 271:    */     {
/* 272:268 */       this.summaryPanel = new JPanel();
/* 273:269 */       this.summaryPanel.setLayout(new GridLayout(0, 10));
/* 274:270 */       this.summaryPanel.setName("Views");
/* 275:    */     }
/* 276:272 */     return this.summaryPanel;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public static void main(String[] args)
/* 280:    */   {
/* 281:276 */     WindowGroupManager group = new WindowGroupManager("bottomPanel", 9);
/* 282:277 */     JPanel pane1 = group.getHost();
/* 283:278 */     JPanel pane2 = group.getHost();
/* 284:279 */     JPanel pane3 = group.getHost();
/* 285:    */     
/* 286:281 */     pane1.setBackground(Color.YELLOW);
/* 287:282 */     pane2.setBackground(Color.CYAN);
/* 288:283 */     pane3.setBackground(Color.MAGENTA);
/* 289:    */     
/* 290:285 */     group.addJComponent(new JLabel("A"));
/* 291:286 */     group.addJComponent(new JLabel("B"));
/* 292:287 */     group.addJComponent(new JLabel("C"));
/* 293:    */     
/* 294:289 */     JPanel panel = new JPanel();
/* 295:290 */     panel.setLayout(new GridLayout(1, 0));
/* 296:291 */     panel.add(pane1);
/* 297:292 */     panel.add(pane2);
/* 298:293 */     panel.add(pane3);
/* 299:    */     
/* 300:295 */     JFrame frame = new JFrame();
/* 301:296 */     frame.getContentPane().add(panel);
/* 302:297 */     frame.setSize(500, 350);
/* 303:298 */     frame.addWindowListener(new WindowAdapter()
/* 304:    */     {
/* 305:    */       public void windowClosing(WindowEvent e)
/* 306:    */       {
/* 307:300 */         System.exit(0);
/* 308:    */       }
/* 309:302 */     });
/* 310:303 */     frame.setVisible(true);
/* 311:    */   }
/* 312:    */   
/* 313:    */   private class NameComponentPair
/* 314:    */   {
/* 315:    */     String componentName;
/* 316:    */     JComponent component;
/* 317:    */     
/* 318:    */     public String getComponentName()
/* 319:    */     {
/* 320:312 */       return this.componentName;
/* 321:    */     }
/* 322:    */     
/* 323:    */     public JComponent getComponent()
/* 324:    */     {
/* 325:316 */       return this.component;
/* 326:    */     }
/* 327:    */     
/* 328:    */     public NameComponentPair(String tab, JComponent choice)
/* 329:    */     {
/* 330:321 */       this.componentName = tab;
/* 331:322 */       this.component = choice;
/* 332:    */     }
/* 333:    */   }
/* 334:    */   
/* 335:    */   private class MyActionListener
/* 336:    */     implements ActionListener
/* 337:    */   {
/* 338:    */     private MyActionListener() {}
/* 339:    */     
/* 340:    */     public void actionPerformed(ActionEvent e)
/* 341:    */     {
/* 342:329 */       Object item = e.getSource();
/* 343:330 */       JComponent newGuts = null;
/* 344:    */       JMenuBar menubar;
/* 345:    */       int x;
/* 346:331 */       for (Iterator localIterator = WindowGroupManager.this.hosts.iterator(); localIterator.hasNext(); x < menubar.getComponents().length)
/* 347:    */       {
/* 348:331 */         WindowGroupHost host = (WindowGroupHost)localIterator.next();
/* 349:332 */         menubar = host.getMenuBar();
/* 350:333 */         x = 0; continue;
/* 351:334 */         Object object = menubar.getComponents()[x];
/* 352:335 */         if ((object instanceof JMenu))
/* 353:    */         {
/* 354:336 */           JMenu menu = (JMenu)object;
/* 355:337 */           for (int i = 0; i < menu.getMenuComponents().length; i++)
/* 356:    */           {
/* 357:338 */             Component component = menu.getMenuComponents()[i];
/* 358:339 */             if (component == item)
/* 359:    */             {
/* 360:340 */               newGuts = ((WindowGroupManager.NameComponentPair)WindowGroupManager.this.nameComponentPairs.get(i)).getComponent();
/* 361:    */               
/* 362:342 */               Preferences.userRoot().put(host.getName(), ((WindowGroupManager.NameComponentPair)WindowGroupManager.this.nameComponentPairs.get(i)).getComponentName());
/* 363:343 */               WindowGroupManager.this.setGuts(host, newGuts);
/* 364:344 */               break;
/* 365:    */             }
/* 366:    */           }
/* 367:    */         }
/* 368:333 */         x++;
/* 369:    */       }
/* 370:    */     }
/* 371:    */   }
/* 372:    */   
/* 373:    */   private class ActuationAction
/* 374:    */     extends AbstractAction
/* 375:    */   {
/* 376:    */     WindowGroupManager.NameComponentPair pair;
/* 377:    */     WindowGroupHost host;
/* 378:    */     
/* 379:    */     public ActuationAction(WindowGroupManager.NameComponentPair pair, WindowGroupHost host)
/* 380:    */     {
/* 381:359 */       super();
/* 382:360 */       this.pair = pair;
/* 383:361 */       this.host = host;
/* 384:    */     }
/* 385:    */     
/* 386:    */     public void actionPerformed(ActionEvent e)
/* 387:    */     {
/* 388:367 */       Preferences.userRoot().put(this.host.getName(), this.pair.getComponentName());
/* 389:368 */       WindowGroupManager.this.setGuts(this.host, this.pair.getComponent());
/* 390:    */     }
/* 391:    */   }
/* 392:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     windowGroup.WindowGroupManager
 * JD-Core Version:    0.7.0.1
 */