/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.Ports;
/*   7:    */ import gui.images.GuiImagesAnchor;
/*   8:    */ import java.awt.Color;
/*   9:    */ import java.awt.Container;
/*  10:    */ import java.awt.Font;
/*  11:    */ import java.awt.FontMetrics;
/*  12:    */ import java.awt.Graphics;
/*  13:    */ import java.awt.Graphics2D;
/*  14:    */ import java.awt.Image;
/*  15:    */ import java.awt.MediaTracker;
/*  16:    */ import java.awt.Toolkit;
/*  17:    */ import java.io.PrintStream;
/*  18:    */ import java.util.Hashtable;
/*  19:    */ import java.util.Vector;
/*  20:    */ import javax.swing.BorderFactory;
/*  21:    */ import javax.swing.JFrame;
/*  22:    */ 
/*  23:    */ public class TransitionViewer
/*  24:    */   extends NegatableJPanel
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 8425249526548473816L;
/*  27:    */   String role;
/*  28:    */   String name;
/*  29:    */   private Ports ports;
/*  30:    */   private Hashtable roleFileAssoc;
/*  31:    */   
/*  32:    */   public TransitionViewer()
/*  33:    */   {
/*  34: 38 */     this.roleFileAssoc = new Hashtable();
/*  35: 39 */     this.roleFileAssoc.put("appear", "appear.png");
/*  36: 40 */     this.roleFileAssoc.put("disappear", "disappear.png");
/*  37: 41 */     this.roleFileAssoc.put("increase", "increase.png");
/*  38: 42 */     this.roleFileAssoc.put("decrease", "decrease.png");
/*  39: 43 */     this.roleFileAssoc.put("change", "change.png");
/*  40: 44 */     this.roleFileAssoc.put("notAppear", "not_appear.png");
/*  41: 45 */     this.roleFileAssoc.put("notDisappear", "not_disappear.png");
/*  42: 46 */     this.roleFileAssoc.put("notIncrease", "not_increase.png");
/*  43: 47 */     this.roleFileAssoc.put("notDecrease", "not_decrease.png");
/*  44: 48 */     this.roleFileAssoc.put("notChange", "not_change.png");
/*  45:    */     
/*  46: 50 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/*  47: 51 */     setOpaque(false);
/*  48:    */   }
/*  49:    */   
/*  50:    */   private void setParameters(String role)
/*  51:    */   {
/*  52: 55 */     this.role = role;
/*  53: 56 */     repaint();
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void clearData()
/*  57:    */   {
/*  58: 60 */     this.role = null;
/*  59: 61 */     this.name = null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Ports getPorts()
/*  63:    */   {
/*  64: 65 */     if (this.ports == null) {
/*  65: 66 */       this.ports = new Ports();
/*  66:    */     }
/*  67: 68 */     return this.ports;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void view(Object signal)
/*  71:    */   {
/*  72: 73 */     if ((signal instanceof Sequence))
/*  73:    */     {
/*  74: 74 */       Sequence space = (Sequence)signal;
/*  75: 75 */       if (space.isA("transitionSpace"))
/*  76:    */       {
/*  77: 76 */         Sequence ladder = (Sequence)space.getElement(0);
/*  78: 77 */         Function transition = (Function)ladder.getElement(0);
/*  79: 78 */         setParameters(matchTransitionRole(transition.getTypes()));
/*  80:    */       }
/*  81:    */     }
/*  82: 81 */     else if ((signal instanceof Function))
/*  83:    */     {
/*  84: 82 */       Function transition = (Function)signal;
/*  85: 83 */       setParameters(matchTransitionRole(transition.getTypes()));
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89: 86 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/*  90: 87 */         signal + " in TransitionViewer");
/*  91:    */     }
/*  92: 89 */     setTruthValue(signal);
/*  93:    */   }
/*  94:    */   
/*  95:    */   private String matchTransitionRole(Vector types)
/*  96:    */   {
/*  97: 98 */     for (int i = types.size() - 1; i >= 0; i--) {
/*  98: 99 */       if (this.roleFileAssoc.containsKey(types.get(i))) {
/*  99:100 */         return (String)types.get(i);
/* 100:    */       }
/* 101:    */     }
/* 102:103 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void paint(Graphics graphics)
/* 106:    */   {
/* 107:107 */     super.paint(graphics);
/* 108:108 */     Graphics2D g = (Graphics2D)graphics;
/* 109:109 */     int height = getHeight();
/* 110:110 */     int width = getWidth();
/* 111:111 */     FontMetrics fm = g.getFontMetrics();
/* 112:    */     
/* 113:113 */     Toolkit toolkit = Toolkit.getDefaultToolkit();
/* 114:114 */     MediaTracker mediaTracker = new MediaTracker(this);
/* 115:115 */     String file = null;
/* 116:    */     
/* 117:    */ 
/* 118:118 */     int imageTextSeparator = height - 5 - fm.getDescent();
/* 119:119 */     int imageSpace = 20;
/* 120:120 */     int textSpace = 5;
/* 121:    */     
/* 122:122 */     String fontName = "Sans_Serif";
/* 123:123 */     int fontStyle = 0;
/* 124:124 */     int fontSize = 10;
/* 125:125 */     Font font = new Font(fontName, fontStyle, fontSize);
/* 126:127 */     if ((width <= 0) || (height <= 0)) {
/* 127:128 */       return;
/* 128:    */     }
/* 129:130 */     if (this.role == null) {
/* 130:131 */       return;
/* 131:    */     }
/* 132:134 */     g.setFont(font);
/* 133:140 */     while (fm.stringWidth(this.role) > width - textSpace)
/* 134:    */     {
/* 135:141 */       fontSize--;
/* 136:142 */       font = new Font(fontName, fontStyle, fontSize);
/* 137:143 */       g.setFont(font);
/* 138:144 */       fm = g.getFontMetrics();
/* 139:    */     }
/* 140:146 */     g.drawString(this.role, textSpace, imageTextSeparator);
/* 141:    */     
/* 142:    */ 
/* 143:149 */     file = (String)this.roleFileAssoc.get(this.role);
/* 144:150 */     file = new GuiImagesAnchor().get(file);
/* 145:151 */     if (file != null)
/* 146:    */     {
/* 147:152 */       if (width > imageTextSeparator) {
/* 148:153 */         imageSpace = imageTextSeparator / 5;
/* 149:    */       } else {
/* 150:156 */         imageSpace = width / 5;
/* 151:    */       }
/* 152:158 */       int imageWidth = width - 2 * imageSpace;
/* 153:159 */       int imageHeight = imageTextSeparator - 2 * imageSpace;
/* 154:160 */       Image displayImage = toolkit.createImage(file);
/* 155:161 */       mediaTracker.addImage(displayImage, 0);
/* 156:    */       try
/* 157:    */       {
/* 158:163 */         mediaTracker.waitForID(0);
/* 159:    */       }
/* 160:    */       catch (Exception e)
/* 161:    */       {
/* 162:166 */         e.printStackTrace();
/* 163:    */       }
/* 164:169 */       double imageProportion = displayImage.getWidth(null) / displayImage.getHeight(null);
/* 165:170 */       if (imageHeight * imageProportion > imageWidth)
/* 166:    */       {
/* 167:171 */         imageHeight = (int)(imageWidth / imageProportion);
/* 168:172 */         int x = (width - imageWidth) / 2;
/* 169:173 */         int y = (imageTextSeparator - imageHeight) / 2;
/* 170:174 */         g.drawImage(displayImage, x, y, imageWidth, imageHeight, null);
/* 171:    */       }
/* 172:    */       else
/* 173:    */       {
/* 174:177 */         imageWidth = (int)(imageHeight * imageProportion);
/* 175:178 */         int x = (width - imageWidth) / 2;
/* 176:179 */         int y = (imageTextSeparator - imageHeight) / 2;
/* 177:180 */         g.drawImage(displayImage, x, y, imageWidth, imageHeight, null);
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static void main(String[] args)
/* 183:    */   {
/* 184:187 */     JFrame frame = new JFrame();
/* 185:188 */     TransitionViewer view = new TransitionViewer();
/* 186:189 */     Entity t = new Entity("Ray");
/* 187:190 */     Function d = new Function("appeared", t);
/* 188:    */     
/* 189:192 */     frame.getContentPane().add(view);
/* 190:193 */     frame.setBounds(0, 0, 200, 200);
/* 191:194 */     frame.setVisible(true);
/* 192:    */     
/* 193:    */ 
/* 194:197 */     d.addType("increase");
/* 195:    */     
/* 196:199 */     view.view(d);
/* 197:    */   }
/* 198:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TransitionViewer
 * JD-Core Version:    0.7.0.1
 */