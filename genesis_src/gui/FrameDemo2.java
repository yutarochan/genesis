/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ import java.awt.Image;
/*   8:    */ import java.awt.Point;
/*   9:    */ import java.awt.Toolkit;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import java.awt.event.WindowAdapter;
/*  13:    */ import java.awt.image.BufferedImage;
/*  14:    */ import java.io.PrintStream;
/*  15:    */ import java.net.URL;
/*  16:    */ import javax.swing.BorderFactory;
/*  17:    */ import javax.swing.Box;
/*  18:    */ import javax.swing.BoxLayout;
/*  19:    */ import javax.swing.ButtonGroup;
/*  20:    */ import javax.swing.ImageIcon;
/*  21:    */ import javax.swing.JButton;
/*  22:    */ import javax.swing.JComponent;
/*  23:    */ import javax.swing.JDialog;
/*  24:    */ import javax.swing.JFrame;
/*  25:    */ import javax.swing.JLabel;
/*  26:    */ import javax.swing.JPanel;
/*  27:    */ import javax.swing.JRadioButton;
/*  28:    */ import javax.swing.JRootPane;
/*  29:    */ import javax.swing.SwingUtilities;
/*  30:    */ import javax.swing.UIManager;
/*  31:    */ 
/*  32:    */ public class FrameDemo2
/*  33:    */   extends WindowAdapter
/*  34:    */   implements ActionListener
/*  35:    */ {
/*  36: 46 */   private Point lastLocation = null;
/*  37: 47 */   private int maxX = 500;
/*  38: 48 */   private int maxY = 500;
/*  39: 51 */   private static JButton defaultButton = null;
/*  40:    */   protected static final String NO_DECORATIONS = "no_dec";
/*  41:    */   protected static final String LF_DECORATIONS = "laf_dec";
/*  42:    */   protected static final String WS_DECORATIONS = "ws_dec";
/*  43:    */   protected static final String CREATE_WINDOW = "new_win";
/*  44:    */   protected static final String DEFAULT_ICON = "def_icon";
/*  45:    */   protected static final String FILE_ICON = "file_icon";
/*  46:    */   protected static final String PAINT_ICON = "paint_icon";
/*  47: 63 */   protected boolean noDecorations = false;
/*  48: 66 */   protected boolean specifyIcon = false;
/*  49: 69 */   protected boolean createIcon = false;
/*  50:    */   
/*  51:    */   public FrameDemo2()
/*  52:    */   {
/*  53: 73 */     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*  54: 74 */     this.maxX = (screenSize.width - 50);
/*  55: 75 */     this.maxY = (screenSize.height - 50);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void showNewWindow()
/*  59:    */   {
/*  60: 80 */     JFrame frame = new MyFrame();
/*  61: 86 */     if (this.noDecorations) {
/*  62: 87 */       frame.setUndecorated(true);
/*  63:    */     }
/*  64: 91 */     if (this.lastLocation != null)
/*  65:    */     {
/*  66: 93 */       this.lastLocation.translate(40, 40);
/*  67: 94 */       if ((this.lastLocation.x > this.maxX) || (this.lastLocation.y > this.maxY)) {
/*  68: 95 */         this.lastLocation.setLocation(0, 0);
/*  69:    */       }
/*  70: 97 */       frame.setLocation(this.lastLocation);
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 99 */       this.lastLocation = frame.getLocation();
/*  75:    */     }
/*  76:106 */     if (this.specifyIcon) {
/*  77:107 */       if (this.createIcon) {
/*  78:108 */         frame.setIconImage(createFDImage());
/*  79:    */       } else {
/*  80:110 */         frame.setIconImage(getFDImage());
/*  81:    */       }
/*  82:    */     }
/*  83:115 */     frame.setSize(new Dimension(170, 100));
/*  84:116 */     frame.setVisible(true);
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected JComponent createOptionControls()
/*  88:    */   {
/*  89:121 */     JLabel label1 = new JLabel("Decoration options for subsequently created frames:");
/*  90:122 */     ButtonGroup bg1 = new ButtonGroup();
/*  91:123 */     JLabel label2 = new JLabel("Icon options:");
/*  92:124 */     ButtonGroup bg2 = new ButtonGroup();
/*  93:    */     
/*  94:    */ 
/*  95:127 */     JRadioButton rb1 = new JRadioButton();
/*  96:128 */     rb1.setText("Look and feel decorated");
/*  97:129 */     rb1.setActionCommand("laf_dec");
/*  98:130 */     rb1.addActionListener(this);
/*  99:131 */     rb1.setSelected(true);
/* 100:132 */     bg1.add(rb1);
/* 101:    */     
/* 102:134 */     JRadioButton rb2 = new JRadioButton();
/* 103:135 */     rb2.setText("Window system decorated");
/* 104:136 */     rb2.setActionCommand("ws_dec");
/* 105:137 */     rb2.addActionListener(this);
/* 106:138 */     bg1.add(rb2);
/* 107:    */     
/* 108:140 */     JRadioButton rb3 = new JRadioButton();
/* 109:141 */     rb3.setText("No decorations");
/* 110:142 */     rb3.setActionCommand("no_dec");
/* 111:143 */     rb3.addActionListener(this);
/* 112:144 */     bg1.add(rb3);
/* 113:    */     
/* 114:    */ 
/* 115:147 */     JRadioButton rb4 = new JRadioButton();
/* 116:148 */     rb4.setText("Default icon");
/* 117:149 */     rb4.setActionCommand("def_icon");
/* 118:150 */     rb4.addActionListener(this);
/* 119:151 */     rb4.setSelected(true);
/* 120:152 */     bg2.add(rb4);
/* 121:    */     
/* 122:154 */     JRadioButton rb5 = new JRadioButton();
/* 123:155 */     rb5.setText("Icon from a JPEG file");
/* 124:156 */     rb5.setActionCommand("file_icon");
/* 125:157 */     rb5.addActionListener(this);
/* 126:158 */     bg2.add(rb5);
/* 127:    */     
/* 128:160 */     JRadioButton rb6 = new JRadioButton();
/* 129:161 */     rb6.setText("Painted icon");
/* 130:162 */     rb6.setActionCommand("paint_icon");
/* 131:163 */     rb6.addActionListener(this);
/* 132:164 */     bg2.add(rb6);
/* 133:    */     
/* 134:    */ 
/* 135:167 */     Box box = Box.createVerticalBox();
/* 136:168 */     box.add(label1);
/* 137:169 */     box.add(Box.createVerticalStrut(5));
/* 138:170 */     box.add(rb1);
/* 139:171 */     box.add(rb2);
/* 140:172 */     box.add(rb3);
/* 141:    */     
/* 142:174 */     box.add(Box.createVerticalStrut(15));
/* 143:175 */     box.add(label2);
/* 144:176 */     box.add(Box.createVerticalStrut(5));
/* 145:177 */     box.add(rb4);
/* 146:178 */     box.add(rb5);
/* 147:179 */     box.add(rb6);
/* 148:    */     
/* 149:    */ 
/* 150:182 */     box.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
/* 151:    */     
/* 152:184 */     return box;
/* 153:    */   }
/* 154:    */   
/* 155:    */   protected JComponent createButtonPane()
/* 156:    */   {
/* 157:189 */     JButton button = new JButton("New window");
/* 158:190 */     button.setActionCommand("new_win");
/* 159:191 */     button.addActionListener(this);
/* 160:192 */     defaultButton = button;
/* 161:    */     
/* 162:    */ 
/* 163:195 */     JPanel pane = new JPanel();
/* 164:196 */     pane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
/* 165:197 */     pane.add(button);
/* 166:    */     
/* 167:199 */     return pane;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void actionPerformed(ActionEvent e)
/* 171:    */   {
/* 172:204 */     String command = e.getActionCommand();
/* 173:207 */     if ("new_win".equals(command))
/* 174:    */     {
/* 175:208 */       showNewWindow();
/* 176:    */     }
/* 177:211 */     else if ("no_dec".equals(command))
/* 178:    */     {
/* 179:212 */       this.noDecorations = true;
/* 180:213 */       JFrame.setDefaultLookAndFeelDecorated(false);
/* 181:    */     }
/* 182:214 */     else if ("ws_dec".equals(command))
/* 183:    */     {
/* 184:215 */       this.noDecorations = false;
/* 185:216 */       JFrame.setDefaultLookAndFeelDecorated(false);
/* 186:    */     }
/* 187:217 */     else if ("laf_dec".equals(command))
/* 188:    */     {
/* 189:218 */       this.noDecorations = false;
/* 190:219 */       JFrame.setDefaultLookAndFeelDecorated(true);
/* 191:    */     }
/* 192:222 */     else if ("def_icon".equals(command))
/* 193:    */     {
/* 194:223 */       this.specifyIcon = false;
/* 195:    */     }
/* 196:224 */     else if ("file_icon".equals(command))
/* 197:    */     {
/* 198:225 */       this.specifyIcon = true;
/* 199:226 */       this.createIcon = false;
/* 200:    */     }
/* 201:227 */     else if ("paint_icon".equals(command))
/* 202:    */     {
/* 203:228 */       this.specifyIcon = true;
/* 204:229 */       this.createIcon = true;
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   protected static Image createFDImage()
/* 209:    */   {
/* 210:236 */     BufferedImage bi = new BufferedImage(16, 16, 1);
/* 211:    */     
/* 212:    */ 
/* 213:239 */     Graphics g = bi.getGraphics();
/* 214:240 */     g.setColor(Color.BLACK);
/* 215:241 */     g.fillRect(0, 0, 15, 15);
/* 216:242 */     g.setColor(Color.RED);
/* 217:243 */     g.fillOval(5, 3, 6, 6);
/* 218:    */     
/* 219:    */ 
/* 220:246 */     g.dispose();
/* 221:    */     
/* 222:    */ 
/* 223:249 */     return bi;
/* 224:    */   }
/* 225:    */   
/* 226:    */   protected static Image getFDImage()
/* 227:    */   {
/* 228:254 */     URL imgURL = FrameDemo2.class.getResource("images/FD.jpg");
/* 229:255 */     if (imgURL != null) {
/* 230:256 */       return new ImageIcon(imgURL).getImage();
/* 231:    */     }
/* 232:258 */     return null;
/* 233:    */   }
/* 234:    */   
/* 235:    */   private static void createAndShowGUI()
/* 236:    */   {
/* 237:    */     try
/* 238:    */     {
/* 239:270 */       UIManager.setLookAndFeel(
/* 240:271 */         UIManager.getCrossPlatformLookAndFeelClassName());
/* 241:    */     }
/* 242:    */     catch (Exception localException) {}
/* 243:274 */     System.out.println("Mark A: " + UIManager.getCrossPlatformLookAndFeelClassName());
/* 244:    */     
/* 245:    */ 
/* 246:277 */     JFrame.setDefaultLookAndFeelDecorated(true);
/* 247:278 */     JDialog.setDefaultLookAndFeelDecorated(true);
/* 248:    */     
/* 249:    */ 
/* 250:281 */     JFrame frame = new JFrame("FrameDemo2");
/* 251:    */     
/* 252:283 */     frame.setDefaultCloseOperation(3);
/* 253:    */     
/* 254:    */ 
/* 255:286 */     FrameDemo2 demo = new FrameDemo2();
/* 256:    */     
/* 257:    */ 
/* 258:289 */     Container contentPane = frame.getContentPane();
/* 259:290 */     contentPane.add(demo.createOptionControls(), 
/* 260:291 */       "Center");
/* 261:292 */     contentPane.add(demo.createButtonPane(), 
/* 262:293 */       "Last");
/* 263:294 */     frame.getRootPane().setDefaultButton(defaultButton);
/* 264:    */     
/* 265:    */ 
/* 266:297 */     frame.pack();
/* 267:298 */     frame.setLocationRelativeTo(null);
/* 268:299 */     frame.setVisible(true);
/* 269:    */   }
/* 270:    */   
/* 271:    */   public static void main(String[] args)
/* 272:    */   {
/* 273:306 */     SwingUtilities.invokeLater(new Runnable()
/* 274:    */     {
/* 275:    */       public void run() {}
/* 276:    */     });
/* 277:    */   }
/* 278:    */   
/* 279:    */   class MyFrame
/* 280:    */     extends JFrame
/* 281:    */     implements ActionListener
/* 282:    */   {
/* 283:    */     public MyFrame()
/* 284:    */     {
/* 285:317 */       super();
/* 286:318 */       setDefaultCloseOperation(2);
/* 287:    */       
/* 288:    */ 
/* 289:321 */       JButton button = new JButton("Close window");
/* 290:322 */       button.addActionListener(this);
/* 291:    */       
/* 292:    */ 
/* 293:325 */       Container contentPane = getContentPane();
/* 294:326 */       contentPane.setLayout(new BoxLayout(contentPane, 
/* 295:327 */         3));
/* 296:328 */       contentPane.add(Box.createVerticalGlue());
/* 297:329 */       contentPane.add(button);
/* 298:330 */       button.setAlignmentX(0.5F);
/* 299:331 */       contentPane.add(Box.createVerticalStrut(5));
/* 300:    */     }
/* 301:    */     
/* 302:    */     public void actionPerformed(ActionEvent e)
/* 303:    */     {
/* 304:337 */       setVisible(false);
/* 305:338 */       dispose();
/* 306:    */     }
/* 307:    */   }
/* 308:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.FrameDemo2
 * JD-Core Version:    0.7.0.1
 */