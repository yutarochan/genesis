/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Toolkit;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import javax.swing.JButton;
/*   8:    */ import javax.swing.JDialog;
/*   9:    */ import javax.swing.JFrame;
/*  10:    */ import javax.swing.JSlider;
/*  11:    */ import javax.swing.JTextArea;
/*  12:    */ import javax.swing.LookAndFeel;
/*  13:    */ import javax.swing.UIDefaults;
/*  14:    */ import javax.swing.UIManager;
/*  15:    */ import javax.swing.UIManager.LookAndFeelInfo;
/*  16:    */ import javax.swing.plaf.basic.BasicLookAndFeel;
/*  17:    */ 
/*  18:    */ public class PurpleLookAndFeel
/*  19:    */   extends BasicLookAndFeel
/*  20:    */ {
/*  21:    */   public String getName()
/*  22:    */   {
/*  23: 15 */     return "the purple test";
/*  24:    */   }
/*  25:    */   
/*  26:    */   protected void initSystemColorDefaults(UIDefaults table)
/*  27:    */   {
/*  28: 19 */     String[] defaultSystemColors = { "desktop", "#000000", 
/*  29:    */     
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33: 24 */       "activeCaption", "#00FF00", 
/*  34:    */       
/*  35:    */ 
/*  36:    */ 
/*  37: 28 */       "activeCaptionText", "#000000", 
/*  38:    */       
/*  39:    */ 
/*  40:    */ 
/*  41: 32 */       "activeCaptionBorder", "#000000", 
/*  42:    */       
/*  43:    */ 
/*  44:    */ 
/*  45: 36 */       "inactiveCaption", "#808080", 
/*  46:    */       
/*  47:    */ 
/*  48:    */ 
/*  49: 40 */       "inactiveCaptionText", "#C0C0C0", 
/*  50:    */       
/*  51:    */ 
/*  52:    */ 
/*  53: 44 */       "inactiveCaptionBorder", "#C0C0C0", 
/*  54:    */       
/*  55:    */ 
/*  56:    */ 
/*  57: 48 */       "window", "#FF0000", 
/*  58: 49 */       "windowBorder", "#0000FF", 
/*  59: 50 */       "windowText", "#0000FF", 
/*  60: 51 */       "menu", "#C0C0C0", 
/*  61: 52 */       "menuPressedItemB", "#000080", 
/*  62: 53 */       "menuPressedItemF", "#FFFFFF", 
/*  63:    */       
/*  64:    */ 
/*  65:    */ 
/*  66: 57 */       "menuText", "#000000", 
/*  67: 58 */       "textText", "#FF0000", 
/*  68: 59 */       "textHighlight", "#000080", 
/*  69: 60 */       "textHighlightText", "#FFFFFF", 
/*  70: 61 */       "textInactiveText", "#808080", 
/*  71: 62 */       "control", "#0000FF", 
/*  72:    */       
/*  73:    */ 
/*  74:    */ 
/*  75: 66 */       "controlText", "#00FF00", 
/*  76: 67 */       "controlHighlight", "#C0C0C0", 
/*  77:    */       
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82: 73 */       "controlLtHighlight", "#000000", 
/*  83: 74 */       "controlShadow", "#AA0000", 
/*  84: 75 */       "controlDkShadow", "#000000", 
/*  85: 76 */       "scrollbar", "#00FF00", 
/*  86: 77 */       "info", "#FFFFE1", 
/*  87: 78 */       "infoText", "#000000" };
/*  88:    */     
/*  89:    */ 
/*  90: 81 */     loadSystemColors(table, defaultSystemColors, isNativeLookAndFeel());
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean isSupportedLookAndFeel()
/*  94:    */   {
/*  95: 86 */     return true;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static void main(String[] args)
/*  99:    */   {
/* 100:    */     int i;
/* 101:    */     StackTraceElement[] arrayOfStackTraceElement;
/* 102:    */     int i;
/* 103:    */     try
/* 104:    */     {
/* 105: 92 */       PurpleLookAndFeel laf = new PurpleLookAndFeel();
/* 106:    */       
/* 107:    */ 
/* 108: 95 */       UIManager.LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
/* 109: 96 */       for (i = 0; i < info.length; i++) {
/* 110: 97 */         System.out.println("Feel: " + info[i]);
/* 111:    */       }
/* 112:100 */       UIManager.setLookAndFeel(laf);
/* 113:    */       
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:116 */       System.out.println("The look and feel, system class name: " + UIManager.getSystemLookAndFeelClassName());
/* 129:    */       
/* 130:118 */       System.out.println("The look and feel, look and feel class name: " + UIManager.getLookAndFeel());
/* 131:    */       
/* 132:120 */       System.out.println("The look and feel, name: " + UIManager.getLookAndFeel().getName());
/* 133:    */     }
/* 134:    */     catch (Exception x)
/* 135:    */     {
/* 136:123 */       i = (arrayOfStackTraceElement = x.getStackTrace()).length;i = 0;
/* 137:    */     }
/* 138:123 */     for (; i < i; i++)
/* 139:    */     {
/* 140:123 */       StackTraceElement e = arrayOfStackTraceElement[i];
/* 141:124 */       System.out.println(e);
/* 142:    */     }
/* 143:128 */     JFrame.setDefaultLookAndFeelDecorated(true);
/* 144:129 */     JDialog.setDefaultLookAndFeelDecorated(true);
/* 145:130 */     System.setProperty("sun.awt.noerasebackground", "true");
/* 146:    */     
/* 147:132 */     JFrame jframe = new JFrame("Hello world");
/* 148:    */     
/* 149:    */ 
/* 150:    */ 
/* 151:136 */     JFrame jframe2 = new JFrame("Goodby world");
/* 152:    */     
/* 153:138 */     jframe.getToolkit().beep();
/* 154:    */     
/* 155:    */ 
/* 156:    */ 
/* 157:    */ 
/* 158:143 */     jframe.setLayout(new BorderLayout());
/* 159:    */     
/* 160:145 */     jframe.getContentPane().add(new JButton("Hello."), "North");
/* 161:146 */     jframe.getContentPane().add(new JSlider(19, 42), "South");
/* 162:147 */     jframe.getContentPane().add(new JTextArea("Foo\nBar"), "Center");
/* 163:    */     
/* 164:149 */     jframe.setSize(640, 480);
/* 165:150 */     jframe.setDefaultCloseOperation(3);
/* 166:151 */     jframe.setVisible(true);
/* 167:    */     
/* 168:    */ 
/* 169:154 */     jframe2.setBounds(100, 100, 640, 480);
/* 170:155 */     jframe2.setDefaultCloseOperation(3);
/* 171:156 */     jframe2.setVisible(true);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getDescription()
/* 175:    */   {
/* 176:161 */     return null;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String getID()
/* 180:    */   {
/* 181:166 */     return null;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean isNativeLookAndFeel()
/* 185:    */   {
/* 186:171 */     return false;
/* 187:    */   }
/* 188:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.PurpleLookAndFeel
 * JD-Core Version:    0.7.0.1
 */