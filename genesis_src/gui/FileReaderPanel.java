/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Component;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.LayoutManager;
/*   8:    */ import java.io.File;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import javax.swing.Box;
/*  11:    */ import javax.swing.JButton;
/*  12:    */ import javax.swing.JFileChooser;
/*  13:    */ import javax.swing.JFrame;
/*  14:    */ import javax.swing.JLabel;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.JTextField;
/*  17:    */ import samples.SamplesAnchor;
/*  18:    */ 
/*  19:    */ public class FileReaderPanel
/*  20:    */   extends JPanel
/*  21:    */ {
/*  22:    */   TrafficLight trafficLight;
/*  23:    */   JFileChooser fileChooser;
/*  24: 30 */   JLabel directoryLabel = new JLabel("Directory");
/*  25: 32 */   JLabel fileLabel = new JLabel("File");
/*  26: 34 */   JTextField directoryField = new JTextField();
/*  27: 36 */   JTextField fileField = new JTextField();
/*  28:    */   JButton fileChooserButton;
/*  29:    */   JPanel buttonPanel;
/*  30:    */   JPanel radioPanel;
/*  31:    */   JLabel selectedFileLabel;
/*  32:    */   JPanel labelPanel;
/*  33:    */   File inputFile;
/*  34: 51 */   public static int noFile = 0;
/*  35: 51 */   public static int readyToRead = 1;
/*  36: 51 */   public static int running = 2;
/*  37: 51 */   public static int stopped = 3;
/*  38: 53 */   private int state = noFile;
/*  39:    */   
/*  40:    */   public FileReaderPanel()
/*  41:    */   {
/*  42: 58 */     setBackground(Color.WHITE);
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47: 63 */     add(getTrafficLight(), "East");
/*  48: 64 */     getTrafficLight().setPreferredSize(new Dimension(50, 80));
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static void main(String[] args)
/*  52:    */   {
/*  53: 69 */     FileReaderPanel reader = new FileReaderPanel();
/*  54: 70 */     JFrame frame = new JFrame("Testing");
/*  55: 71 */     frame.getContentPane().add(reader, "Center");
/*  56: 72 */     frame.setBounds(100, 100, 600, 200);
/*  57: 73 */     frame.setDefaultCloseOperation(3);
/*  58: 74 */     frame.setVisible(true);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public TrafficLight getTrafficLight()
/*  62:    */   {
/*  63: 78 */     if (this.trafficLight == null) {
/*  64: 79 */       this.trafficLight = new TrafficLight();
/*  65:    */     }
/*  66: 81 */     return this.trafficLight;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public JLabel getSelectedFileLabel()
/*  70:    */   {
/*  71: 85 */     if (this.selectedFileLabel == null) {
/*  72: 86 */       this.selectedFileLabel = new JLabel("Hello World");
/*  73:    */     }
/*  74: 88 */     return this.selectedFileLabel;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public JFileChooser getFileChooser()
/*  78:    */   {
/*  79: 92 */     String file = new SamplesAnchor().get("sample.txt");
/*  80: 93 */     return getFileChooser(file);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public JFileChooser getFileChooser(String file)
/*  84:    */   {
/*  85: 97 */     if (this.fileChooser == null)
/*  86:    */     {
/*  87: 98 */       this.fileChooser = new JFileChooser();
/*  88: 99 */       ExampleFileFilter filter = new ExampleFileFilter();
/*  89:100 */       filter.addExtension("txt");
/*  90:101 */       filter.addExtension("text");
/*  91:102 */       filter.addExtension("story");
/*  92:103 */       filter.setDescription("Sentence-containing files");
/*  93:104 */       this.fileChooser.setFileFilter(filter);
/*  94:    */     }
/*  95:106 */     this.fileChooser.setCurrentDirectory(new File(file));
/*  96:107 */     return this.fileChooser;
/*  97:    */   }
/*  98:    */   
/*  99:    */   private JPanel getLabelPanel()
/* 100:    */   {
/* 101:111 */     if (this.labelPanel == null)
/* 102:    */     {
/* 103:112 */       this.labelPanel = new JPanel();
/* 104:    */       
/* 105:114 */       Box.createGlue();
/* 106:115 */       JLabel fileLabel = new JLabel("File:");
/* 107:116 */       fileLabel.setBackground(Color.YELLOW);
/* 108:117 */       fileLabel.setOpaque(true);
/* 109:118 */       this.labelPanel.add(fileLabel);
/* 110:119 */       Box.createGlue();
/* 111:120 */       getSelectedFileLabel().setBackground(Color.YELLOW);
/* 112:121 */       getSelectedFileLabel().setOpaque(true);
/* 113:122 */       this.labelPanel.add(getSelectedFileLabel());
/* 114:123 */       Box.createGlue();
/* 115:    */     }
/* 116:125 */     return this.labelPanel;
/* 117:    */   }
/* 118:    */   
/* 119:    */   class MyLayoutManager
/* 120:    */     implements LayoutManager
/* 121:    */   {
/* 122:    */     Component trafficLight;
/* 123:    */     Component buttonPanel;
/* 124:    */     Component labelPanel;
/* 125:    */     
/* 126:    */     MyLayoutManager() {}
/* 127:    */     
/* 128:    */     public void addLayoutComponent(String identifier, Component component)
/* 129:    */     {
/* 130:136 */       if ("light".equalsIgnoreCase(identifier))
/* 131:    */       {
/* 132:137 */         this.trafficLight = component;
/* 133:138 */         FileReaderPanel.this.add(this.trafficLight);
/* 134:    */       }
/* 135:140 */       if ("buttons".equalsIgnoreCase(identifier))
/* 136:    */       {
/* 137:141 */         this.buttonPanel = component;
/* 138:142 */         FileReaderPanel.this.add(this.buttonPanel);
/* 139:    */       }
/* 140:144 */       if ("labels".equalsIgnoreCase(identifier))
/* 141:    */       {
/* 142:145 */         this.labelPanel = component;
/* 143:146 */         FileReaderPanel.this.add(this.labelPanel);
/* 144:    */       }
/* 145:    */     }
/* 146:    */     
/* 147:    */     public void removeLayoutComponent(Component component) {}
/* 148:    */     
/* 149:    */     public Dimension preferredLayoutSize(Container component)
/* 150:    */     {
/* 151:154 */       return null;
/* 152:    */     }
/* 153:    */     
/* 154:    */     public Dimension minimumLayoutSize(Container component)
/* 155:    */     {
/* 156:158 */       return null;
/* 157:    */     }
/* 158:    */     
/* 159:    */     public void layoutContainer(Container parent)
/* 160:    */     {
/* 161:162 */       int width = parent.getWidth();
/* 162:163 */       int height = parent.getHeight();
/* 163:164 */       if (this.trafficLight != null)
/* 164:    */       {
/* 165:166 */         int lightAreaWidth = width / 8;
/* 166:167 */         int lightAreaHeight = height;
/* 167:168 */         int lightWidth = lightAreaWidth;
/* 168:169 */         int lightHeight = lightAreaHeight;
/* 169:170 */         int lightHToWRatio = 3;
/* 170:171 */         if (lightAreaHeight > lightHToWRatio * lightAreaWidth)
/* 171:    */         {
/* 172:173 */           lightWidth = lightAreaWidth * 9 / 10;
/* 173:174 */           lightHeight = lightWidth * lightHToWRatio;
/* 174:    */         }
/* 175:    */         else
/* 176:    */         {
/* 177:178 */           lightHeight = lightAreaHeight * 9 / 10;
/* 178:179 */           lightWidth = lightHeight / lightHToWRatio;
/* 179:    */         }
/* 180:181 */         int xOffset = width - lightAreaWidth + (lightAreaWidth - lightWidth) / 2;
/* 181:182 */         int yOffset = (lightAreaHeight - lightHeight) / 2;
/* 182:183 */         this.trafficLight.setBounds(xOffset, yOffset, lightWidth, lightHeight);
/* 183:    */       }
/* 184:185 */       if (this.labelPanel != null)
/* 185:    */       {
/* 186:187 */         int baseWidth = 7 * width / 8;
/* 187:188 */         int baseHeight = height / 2;
/* 188:189 */         this.labelPanel.setBackground(Color.CYAN);
/* 189:190 */         this.labelPanel.setBounds(baseWidth / 16, 17 * baseHeight / 16, 14 * baseWidth / 16, 14 * baseHeight / 16);
/* 190:    */       }
/* 191:193 */       if (this.buttonPanel != null)
/* 192:    */       {
/* 193:195 */         int baseWidth = 7 * width / 8;
/* 194:196 */         int baseHeight = height / 2;
/* 195:197 */         this.buttonPanel.setBounds(baseWidth / 16, baseHeight / 16, 14 * baseWidth / 16, 14 * baseHeight / 16);
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public JButton getFileChooserButton()
/* 201:    */   {
/* 202:203 */     if (this.fileChooserButton == null) {
/* 203:204 */       this.fileChooserButton = new JButton("Choose file");
/* 204:    */     }
/* 205:206 */     return this.fileChooserButton;
/* 206:    */   }
/* 207:    */   
/* 208:    */   private int getState()
/* 209:    */   {
/* 210:210 */     return this.state;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setState(int state)
/* 214:    */   {
/* 215:214 */     this.state = state;
/* 216:215 */     if (state == readyToRead)
/* 217:    */     {
/* 218:216 */       this.trafficLight.setGreen(true);
/* 219:    */     }
/* 220:218 */     else if (state == stopped)
/* 221:    */     {
/* 222:219 */       System.out.println("Setting light to red");
/* 223:220 */       this.trafficLight.setRed(true);
/* 224:    */     }
/* 225:    */   }
/* 226:    */   
/* 227:    */   public File getInputFile()
/* 228:    */   {
/* 229:225 */     return this.inputFile;
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.FileReaderPanel
 * JD-Core Version:    0.7.0.1
 */