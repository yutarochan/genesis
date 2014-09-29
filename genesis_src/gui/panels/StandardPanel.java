/*   1:    */ package gui.panels;
/*   2:    */ 
/*   3:    */ import connections.WiredBox;
/*   4:    */ import java.awt.BorderLayout;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.event.ActionEvent;
/*   8:    */ import java.awt.event.ActionListener;
/*   9:    */ import javax.swing.Box.Filler;
/*  10:    */ import javax.swing.BoxLayout;
/*  11:    */ import javax.swing.JButton;
/*  12:    */ import javax.swing.JFrame;
/*  13:    */ import javax.swing.JPanel;
/*  14:    */ 
/*  15:    */ public class StandardPanel
/*  16:    */   extends JPanel
/*  17:    */   implements WiredBox
/*  18:    */ {
/*  19:    */   private JPanel buttonPanel;
/*  20: 20 */   public static int buttonFillerWidth = 5;
/*  21: 22 */   public static int buttonPanelHeight = 20;
/*  22:    */   private JButton percent0;
/*  23:    */   private JButton percent25;
/*  24:    */   private JButton percent50;
/*  25:    */   private JButton percent75;
/*  26:    */   private JButton percent100;
/*  27:    */   private JButton percentEqual;
/*  28:    */   private JButton removePanel;
/*  29:    */   private JButton addPanel;
/*  30:    */   private MyButtonListener myButtonListener;
/*  31: 42 */   private int heightPercent = 100;
/*  32:    */   
/*  33:    */   public StandardPanel()
/*  34:    */   {
/*  35: 45 */     setBackground(Color.YELLOW);
/*  36: 46 */     setLayout(new BorderLayout());
/*  37: 47 */     add(getButtonPanel(), "North");
/*  38:    */   }
/*  39:    */   
/*  40:    */   public JPanel getButtonPanel()
/*  41:    */   {
/*  42: 51 */     if (this.buttonPanel == null)
/*  43:    */     {
/*  44: 52 */       this.buttonPanel = new JPanel();
/*  45: 53 */       this.buttonPanel.setBackground(Color.gray);
/*  46: 54 */       this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, 0));
/*  47: 55 */       Dimension filler = new Dimension(buttonFillerWidth, buttonPanelHeight);
/*  48: 56 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  49: 57 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  50: 58 */       this.buttonPanel.add(getPercent0());
/*  51: 59 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  52: 60 */       this.buttonPanel.add(getPercent25());
/*  53: 61 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  54: 62 */       this.buttonPanel.add(getPercent50());
/*  55: 63 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  56: 64 */       this.buttonPanel.add(getPercent75());
/*  57: 65 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  58: 66 */       this.buttonPanel.add(getPercent100());
/*  59:    */       
/*  60: 68 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  61: 69 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  62: 70 */       this.buttonPanel.add(getPercentEqual());
/*  63:    */       
/*  64: 72 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  65: 73 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  66: 74 */       this.buttonPanel.add(new Box.Filler(filler, filler, filler));
/*  67: 75 */       this.buttonPanel.add(getAddPanel());
/*  68: 76 */       this.buttonPanel.add(getRemovePanel());
/*  69:    */     }
/*  70: 78 */     return this.buttonPanel;
/*  71:    */   }
/*  72:    */   
/*  73:    */   class LocalButton
/*  74:    */     extends JButton
/*  75:    */   {
/*  76:    */     private Dimension transformHeight(int height, Dimension input)
/*  77:    */     {
/*  78: 83 */       return new Dimension(input.width, height);
/*  79:    */     }
/*  80:    */     
/*  81:    */     public LocalButton(String label)
/*  82:    */     {
/*  83: 87 */       super();
/*  84: 88 */       setMinimumSize(transformHeight(StandardPanel.buttonPanelHeight, getMinimumSize()));
/*  85: 89 */       setPreferredSize(transformHeight(StandardPanel.buttonPanelHeight, getPreferredSize()));
/*  86: 90 */       setMaximumSize(transformHeight(StandardPanel.buttonPanelHeight, getMaximumSize()));
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public JButton getAddPanel()
/*  91:    */   {
/*  92: 95 */     if (this.addPanel == null)
/*  93:    */     {
/*  94: 96 */       this.addPanel = new LocalButton("+");
/*  95: 97 */       this.addPanel.addActionListener(getListener());
/*  96:    */     }
/*  97: 99 */     return this.addPanel;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public JButton getRemovePanel()
/* 101:    */   {
/* 102:103 */     if (this.removePanel == null)
/* 103:    */     {
/* 104:104 */       this.removePanel = new LocalButton("x");
/* 105:105 */       this.removePanel.addActionListener(getListener());
/* 106:    */     }
/* 107:107 */     return this.removePanel;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public JButton getPercentEqual()
/* 111:    */   {
/* 112:111 */     if (this.percentEqual == null)
/* 113:    */     {
/* 114:112 */       this.percentEqual = new LocalButton("=");
/* 115:113 */       this.percentEqual.addActionListener(getListener());
/* 116:    */     }
/* 117:115 */     return this.percentEqual;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public JButton getPercent0()
/* 121:    */   {
/* 122:119 */     if (this.percent0 == null)
/* 123:    */     {
/* 124:120 */       this.percent0 = new LocalButton("0%");
/* 125:121 */       this.percent0.addActionListener(getListener());
/* 126:    */     }
/* 127:123 */     return this.percent0;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public JButton getPercent25()
/* 131:    */   {
/* 132:127 */     if (this.percent25 == null)
/* 133:    */     {
/* 134:128 */       this.percent25 = new LocalButton("25%");
/* 135:129 */       this.percent25.addActionListener(getListener());
/* 136:    */     }
/* 137:131 */     return this.percent25;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public JButton getPercent50()
/* 141:    */   {
/* 142:135 */     if (this.percent50 == null)
/* 143:    */     {
/* 144:136 */       this.percent50 = new LocalButton("50%");
/* 145:137 */       this.percent50.addActionListener(getListener());
/* 146:    */     }
/* 147:139 */     return this.percent50;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public JButton getPercent75()
/* 151:    */   {
/* 152:143 */     if (this.percent75 == null)
/* 153:    */     {
/* 154:144 */       this.percent75 = new LocalButton("75%");
/* 155:145 */       this.percent75.addActionListener(getListener());
/* 156:    */     }
/* 157:147 */     return this.percent75;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public JButton getPercent100()
/* 161:    */   {
/* 162:151 */     if (this.percent100 == null)
/* 163:    */     {
/* 164:152 */       this.percent100 = new LocalButton("100%");
/* 165:153 */       this.percent100.addActionListener(getListener());
/* 166:    */     }
/* 167:155 */     return this.percent100;
/* 168:    */   }
/* 169:    */   
/* 170:    */   private MyButtonListener getListener()
/* 171:    */   {
/* 172:159 */     if (this.myButtonListener == null) {
/* 173:160 */       this.myButtonListener = new MyButtonListener();
/* 174:    */     }
/* 175:162 */     return this.myButtonListener;
/* 176:    */   }
/* 177:    */   
/* 178:    */   class MyButtonListener
/* 179:    */     implements ActionListener
/* 180:    */   {
/* 181:    */     MyButtonListener() {}
/* 182:    */     
/* 183:    */     public void actionPerformed(ActionEvent e)
/* 184:    */     {
/* 185:167 */       if (e.getSource() == StandardPanel.this.getPercent0()) {
/* 186:168 */         StandardPanel.this.setHeightPercentAndFire(0);
/* 187:170 */       } else if (e.getSource() == StandardPanel.this.getPercent25()) {
/* 188:171 */         StandardPanel.this.setHeightPercentAndFire(25);
/* 189:173 */       } else if (e.getSource() == StandardPanel.this.getPercent50()) {
/* 190:174 */         StandardPanel.this.setHeightPercentAndFire(50);
/* 191:176 */       } else if (e.getSource() == StandardPanel.this.getPercent75()) {
/* 192:177 */         StandardPanel.this.setHeightPercentAndFire(75);
/* 193:179 */       } else if (e.getSource() == StandardPanel.this.getPercent100()) {
/* 194:180 */         StandardPanel.this.setHeightPercentAndFire(100);
/* 195:182 */       } else if (e.getSource() == StandardPanel.this.getPercentEqual()) {
/* 196:183 */         StandardPanel.this.firePropertyChange("equal", -1, 0);
/* 197:185 */       } else if (e.getSource() == StandardPanel.this.getRemovePanel()) {
/* 198:186 */         StandardPanel.this.firePropertyChange("remove", -1, 0);
/* 199:188 */       } else if (e.getSource() == StandardPanel.this.getAddPanel()) {
/* 200:189 */         StandardPanel.this.firePropertyChange("add", -1, 0);
/* 201:    */       }
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public int getHeightPercent()
/* 206:    */   {
/* 207:195 */     return this.heightPercent;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setHeightPercent(int heightPercent)
/* 211:    */   {
/* 212:199 */     this.heightPercent = heightPercent;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setHeightPercentAndFire(int newValue)
/* 216:    */   {
/* 217:205 */     int oldValue = -1;
/* 218:206 */     setHeightPercent(newValue);
/* 219:207 */     firePropertyChange("height", oldValue, newValue);
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static void main(String[] args)
/* 223:    */   {
/* 224:211 */     StandardPanel panel = new StandardPanel();
/* 225:212 */     JFrame frame = new JFrame();
/* 226:213 */     frame.setContentPane(panel);
/* 227:214 */     frame.setBounds(0, 0, 500, 400);
/* 228:215 */     frame.setVisible(true);
/* 229:    */   }
/* 230:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.panels.StandardPanel
 * JD-Core Version:    0.7.0.1
 */