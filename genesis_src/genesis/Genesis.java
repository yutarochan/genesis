/*   1:    */ package genesis;
/*   2:    */ 
/*   3:    */ import adamKraft.FallbackBundleGenerator;
/*   4:    */ import connections.Connections;
/*   5:    */ import connections.Connections.NetWireError;
/*   6:    */ import connections.Connections.NetWireException;
/*   7:    */ import java.awt.BorderLayout;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.awt.Container;
/*  10:    */ import java.awt.Rectangle;
/*  11:    */ import java.awt.event.ActionEvent;
/*  12:    */ import java.awt.event.ActionListener;
/*  13:    */ import java.awt.event.KeyEvent;
/*  14:    */ import java.awt.event.KeyListener;
/*  15:    */ import java.awt.event.WindowEvent;
/*  16:    */ import java.awt.event.WindowListener;
/*  17:    */ import java.io.PrintStream;
/*  18:    */ import javax.swing.JButton;
/*  19:    */ import javax.swing.JCheckBox;
/*  20:    */ import javax.swing.JFrame;
/*  21:    */ import javax.swing.JPanel;
/*  22:    */ import links.words.BundleGenerator;
/*  23:    */ import parameters.Switch;
/*  24:    */ import start.Start;
/*  25:    */ import utils.Mark;
/*  26:    */ import utils.Webstart;
/*  27:    */ 
/*  28:    */ public class Genesis
/*  29:    */   extends GenesisPlugBoardLower
/*  30:    */   implements WindowListener
/*  31:    */ {
/*  32:    */   JPanel trafficLightPanel;
/*  33:    */   private static final long serialVersionUID = -5573053212665932691L;
/*  34:    */   
/*  35:    */   public static void main(String[] args)
/*  36:    */   {
/*  37: 24 */     if (args.length != 0) {
/*  38: 25 */       Webstart.setWebStart(true);
/*  39:    */     } else {
/*  40: 28 */       Webstart.setWebStart(false);
/*  41:    */     }
/*  42: 30 */     Genesis genesis = new Genesis();
/*  43: 31 */     genesis.startInFrame();
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Genesis()
/*  47:    */   {
/*  48: 38 */     BundleGenerator.setSingletonClass(FallbackBundleGenerator.class);
/*  49:    */     
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59: 49 */     setLayout(new BorderLayout());
/*  60:    */     try
/*  61:    */     {
/*  62: 51 */       Connections.useWireServer("http://glue.csail.mit.edu/WireServer");
/*  63: 52 */       Mark.say(new Object[] {"You are connected to the wire server at http://glue.csail.mit.edu/WireServer" });
/*  64: 53 */       Mark.say(new Object[] {"You may browse to http://glue.csail.mit.edu/WireServer to view a graph of distributed components." });
/*  65:    */     }
/*  66:    */     catch (Connections.NetWireException e)
/*  67:    */     {
/*  68: 61 */       printNetworkError(e);
/*  69:    */     }
/*  70:    */     catch (Connections.NetWireError e)
/*  71:    */     {
/*  72: 64 */       printNetworkError(e);
/*  73:    */     }
/*  74: 67 */     Connections.setVerbose(false);
/*  75:    */   }
/*  76:    */   
/*  77:    */   class MyKeyListener
/*  78:    */     implements KeyListener
/*  79:    */   {
/*  80:    */     MyKeyListener() {}
/*  81:    */     
/*  82:    */     public void keyTyped(KeyEvent ke)
/*  83:    */     {
/*  84: 76 */       if ((ke.isControlDown()) && 
/*  85: 77 */         (ke.getKeyCode() == 114))
/*  86:    */       {
/*  87: 78 */         ke.consume();
/*  88: 79 */         Mark.say(new Object[] {"Hello ctrl r!" });
/*  89:    */       }
/*  90:    */     }
/*  91:    */     
/*  92:    */     public void keyPressed(KeyEvent e)
/*  93:    */     {
/*  94: 87 */       Mark.say(
/*  95:    */       
/*  96: 89 */         new Object[] { "Hello ctrl r!" });
/*  97:    */     }
/*  98:    */     
/*  99:    */     public void keyReleased(KeyEvent e)
/* 100:    */     {
/* 101: 93 */       Mark.say(
/* 102:    */       
/* 103: 95 */         new Object[] { "Hello ctrl r!" });
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   private void printNetworkError(Throwable e)
/* 108:    */   {
/* 109: 99 */     e.printStackTrace();
/* 110:100 */     System.err.println("For the impatient:");
/* 111:101 */     System.err.println(e.toString());
/* 112:102 */     if (e.getCause() != null)
/* 113:    */     {
/* 114:103 */       System.err.println("\tCause: " + e.getCause());
/* 115:    */       
/* 116:    */ 
/* 117:    */ 
/* 118:107 */       System.err.println("This likely means one of the following:\n\t--> you need to update Propagators,\n\t--> you don't have a working Internet connection, or,\n\t--> the wire server is down for development or maintenance.");
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void start()
/* 123:    */   {
/* 124:115 */     Mark.say(
/* 125:    */     
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:143 */       new Object[] { "Start called" });Mark.say(new Object[] { "Memory Max = " + Runtime.getRuntime().maxMemory() });
/* 153:117 */     if (Switch.useWordnetCache.isSelected()) {
/* 154:    */       try
/* 155:    */       {
/* 156:    */         try
/* 157:    */         {
/* 158:120 */           BundleGenerator.readWordnetCache();
/* 159:    */         }
/* 160:    */         catch (Exception e)
/* 161:    */         {
/* 162:123 */           Mark.err(new Object[] {"Strange blowout in Genesis.start at point 1" });
/* 163:124 */           BundleGenerator.purgeWordnetCache();
/* 164:125 */           BundleGenerator.writeWordnetCache();
/* 165:    */         }
/* 166:133 */         if (!Switch.useStartCache.isSelected()) {
/* 167:    */           break label106;
/* 168:    */         }
/* 169:    */       }
/* 170:    */       catch (Exception e)
/* 171:    */       {
/* 172:130 */         Mark.err(new Object[] {"Strange blowout in Genesis.start at point 2" });
/* 173:    */       }
/* 174:    */     }
/* 175:134 */     Start.readStartCaches();
/* 176:    */     label106:
/* 177:136 */     initializeWiring();
/* 178:137 */     Mark.say(new Object[] {"Wiring initialized" });
/* 179:138 */     initializeListeners();
/* 180:139 */     Mark.say(new Object[] {"Listeners initialized" });
/* 181:140 */     initializeGraphics();
/* 182:141 */     Mark.say(new Object[] {"Graphics initialized" });
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void startInFrame()
/* 186:    */   {
/* 187:146 */     start();
/* 188:147 */     JFrame frame = new JFrame();
/* 189:148 */     frame.setTitle("Genesis");
/* 190:149 */     frame.getContentPane().setBackground(Color.WHITE);
/* 191:150 */     frame.getContentPane().setLayout(new BorderLayout());
/* 192:151 */     frame.getContentPane().add(this);
/* 193:152 */     frame.setJMenuBar(getMenuBar());
/* 194:153 */     frame.setBounds(0, 0, 1024, 768);
/* 195:154 */     frame.addWindowListener(this);
/* 196:155 */     frame.setDefaultCloseOperation(3);
/* 197:    */     
/* 198:157 */     frame.setVisible(true);
/* 199:158 */     Mark.say(new Object[] {"Solo initialized" });
/* 200:159 */     GenesisControls.makeSmallVideoRecordingButton.addActionListener(new MyActionListener(frame));
/* 201:160 */     GenesisControls.makeLargeVideoRecordingButton.addActionListener(new MyActionListener(frame));
/* 202:    */   }
/* 203:    */   
/* 204:    */   private void setToVideoRecordingDimensions(JFrame frame, Rectangle rectangle)
/* 205:    */   {
/* 206:164 */     Mark.say(
/* 207:    */     
/* 208:    */ 
/* 209:    */ 
/* 210:168 */       new Object[] { "Adjusting size" });frame.setBounds(rectangle);frame.invalidate();
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void windowActivated(WindowEvent e) {}
/* 214:    */   
/* 215:    */   public void windowClosed(WindowEvent e) {}
/* 216:    */   
/* 217:    */   public class MyActionListener
/* 218:    */     implements ActionListener
/* 219:    */   {
/* 220:    */     JFrame frame;
/* 221:    */     
/* 222:    */     public MyActionListener(JFrame frame)
/* 223:    */     {
/* 224:174 */       this.frame = frame;
/* 225:    */     }
/* 226:    */     
/* 227:    */     public void actionPerformed(ActionEvent e)
/* 228:    */     {
/* 229:180 */       if (e.getSource() == Genesis.makeSmallVideoRecordingButton) {
/* 230:181 */         Genesis.this.setToVideoRecordingDimensions(this.frame, new Rectangle(0, 0, 1024, 768));
/* 231:183 */       } else if (e.getSource() == Genesis.makeMediumVideoRecordingButton) {
/* 232:184 */         Genesis.this.setToVideoRecordingDimensions(this.frame, new Rectangle(0, 0, 1280, 1024));
/* 233:186 */       } else if (e.getSource() == Genesis.makeLargeVideoRecordingButton) {
/* 234:187 */         Genesis.this.setToVideoRecordingDimensions(this.frame, new Rectangle(0, 0, 1600, 1200));
/* 235:    */       }
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void windowClosing(WindowEvent e)
/* 240:    */   {
/* 241:210 */     Mark.say(
/* 242:    */     
/* 243:    */ 
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:    */ 
/* 249:218 */       new Object[] { "Stop called" });
/* 250:211 */     if (Switch.useStartCache.isSelected()) {
/* 251:212 */       Start.writeStartCaches();
/* 252:    */     }
/* 253:214 */     if (Switch.useWordnetCache.isSelected()) {
/* 254:215 */       BundleGenerator.writeWordnetCache();
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void windowDeactivated(WindowEvent e) {}
/* 259:    */   
/* 260:    */   public void windowDeiconified(WindowEvent e) {}
/* 261:    */   
/* 262:    */   public void windowIconified(WindowEvent e) {}
/* 263:    */   
/* 264:    */   public void windowOpened(WindowEvent e) {}
/* 265:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.Genesis
 * JD-Core Version:    0.7.0.1
 */