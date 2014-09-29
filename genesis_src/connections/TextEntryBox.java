/*   1:    */ package connections;
/*   2:    */ 
/*   3:    */ import java.awt.Container;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Font;
/*   6:    */ import java.awt.FontMetrics;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.event.KeyEvent;
/*   9:    */ import java.awt.event.KeyListener;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import javax.swing.JFrame;
/*  12:    */ import javax.swing.JTextField;
/*  13:    */ import utils.Colors;
/*  14:    */ import utils.Mark;
/*  15:    */ 
/*  16:    */ public class TextEntryBox
/*  17:    */   extends JTextField
/*  18:    */   implements WiredBox
/*  19:    */ {
/*  20:    */   public static final String CLEAR = "clear";
/*  21:    */   public static final String PRIMER = "primer";
/*  22:    */   public static final String REPLY = "reply";
/*  23:    */   private static final int desiredFontSize = 35;
/*  24:    */   private static final long serialVersionUID = -842253960229812262L;
/*  25:    */   
/*  26:    */   public TextEntryBox()
/*  27:    */   {
/*  28: 26 */     setFont(new Font(getFont().getFamily(), 1, 35));
/*  29:    */     
/*  30: 28 */     addKeyListener(new PunctuationListener());
/*  31: 29 */     setBackground(Colors.TEXT_COLOR);
/*  32: 30 */     setOpaque(true);
/*  33: 31 */     normal();
/*  34: 32 */     Connections.getPorts(this).addSignalProcessor("primer", "prime");
/*  35: 33 */     Connections.getPorts(this).addSignalProcessor("reply", "reply");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public void paintComponent(Graphics g)
/*  39:    */   {
/*  40: 37 */     String text = getText();
/*  41: 38 */     int boxWidth = getWidth();
/*  42: 39 */     if (boxWidth == 0) {
/*  43: 40 */       return;
/*  44:    */     }
/*  45: 42 */     Font font = getFont();
/*  46: 43 */     String family = font.getFamily();
/*  47:    */     for (;;)
/*  48:    */     {
/*  49: 45 */       int stringWidth = g.getFontMetrics().stringWidth(text);
/*  50: 46 */       int fontSize = g.getFont().getSize();
/*  51: 49 */       if (stringWidth < 7 * boxWidth / 8)
/*  52:    */       {
/*  53: 50 */         if (fontSize > 33) {
/*  54:    */           break;
/*  55:    */         }
/*  56: 53 */         fontSize += 2;
/*  57: 54 */         font = new Font(family, 1, fontSize);
/*  58:    */       }
/*  59:    */       else
/*  60:    */       {
/*  61: 57 */         if ((stringWidth <= boxWidth) || 
/*  62: 58 */           (fontSize <= 5)) {
/*  63:    */           break;
/*  64:    */         }
/*  65: 61 */         fontSize -= 2;
/*  66: 62 */         font = new Font(family, 1, fontSize);
/*  67:    */       }
/*  68: 67 */       setFont(font);
/*  69: 68 */       g.setFont(font);
/*  70:    */     }
/*  71: 70 */     super.paintComponent(g);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void zero()
/*  75:    */   {
/*  76: 74 */     setPreferredSize(new Dimension(0, 0));
/*  77: 75 */     setMaximumSize(new Dimension(0, 0));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void normal()
/*  81:    */   {
/*  82: 80 */     setPreferredSize(new Dimension(100, 60));
/*  83: 81 */     setMaximumSize(new Dimension(1000, 60));
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void reply(Object o)
/*  87:    */   {
/*  88: 85 */     setBackground(Colors.REPLY_COLOR);
/*  89: 86 */     if ((o != null) && ((o instanceof String)))
/*  90:    */     {
/*  91: 87 */       String message = (String)o;
/*  92: 88 */       setText(message);
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setText(String message)
/*  97:    */   {
/*  98: 95 */     Mark.say(
/*  99:    */     
/* 100: 97 */       new Object[] { "Message is " + message });super.setText(correctSeparatedPunctuation(message));
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void prime(Object o)
/* 104:    */   {
/* 105:101 */     setBackground(Colors.TEXT_COLOR);
/* 106:102 */     if ((o instanceof String))
/* 107:    */     {
/* 108:103 */       String message = (String)o;
/* 109:104 */       message = correctSeparatedPunctuation(message);
/* 110:105 */       Connections.getPorts(this).transmit("clear", message);
/* 111:106 */       Connections.getPorts(this).transmit(message);
/* 112:    */       try
/* 113:    */       {
/* 114:108 */         setText(message);
/* 115:    */       }
/* 116:    */       catch (Exception e)
/* 117:    */       {
/* 118:111 */         System.err.println("TextEntryBox.prime unable to set text '" + o + "'");
/* 119:    */       }
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   private String correctSeparatedPunctuation(String message)
/* 124:    */   {
/* 125:119 */     StringBuffer buffer = new StringBuffer(message);
/* 126:    */     int index;
/* 127:121 */     while ((index = buffer.indexOf(" .")) >= 0)
/* 128:    */     {
/* 129:    */       int index;
/* 130:122 */       buffer.deleteCharAt(index);
/* 131:    */     }
/* 132:124 */     while ((index = buffer.indexOf("..")) >= 0) {
/* 133:125 */       buffer.deleteCharAt(index);
/* 134:    */     }
/* 135:127 */     while ((index = buffer.indexOf(" ?")) >= 0) {
/* 136:128 */       buffer.deleteCharAt(index);
/* 137:    */     }
/* 138:130 */     while ((index = buffer.indexOf(" ,")) >= 0) {
/* 139:131 */       buffer.deleteCharAt(index);
/* 140:    */     }
/* 141:133 */     while ((index = buffer.indexOf(" ;")) >= 0) {
/* 142:134 */       buffer.deleteCharAt(index);
/* 143:    */     }
/* 144:136 */     while ((index = buffer.indexOf(" \n")) >= 0) {
/* 145:137 */       buffer.deleteCharAt(index);
/* 146:    */     }
/* 147:139 */     return buffer.toString();
/* 148:    */   }
/* 149:    */   
/* 150:    */   protected class PunctuationListener
/* 151:    */     implements KeyListener
/* 152:    */   {
/* 153:144 */     String terminators = "?.!\n";
/* 154:146 */     String spaces = " \n\t";
/* 155:    */     
/* 156:    */     protected PunctuationListener() {}
/* 157:    */     
/* 158:    */     public void keyPressed(KeyEvent e) {}
/* 159:    */     
/* 160:    */     public void keyReleased(KeyEvent e) {}
/* 161:    */     
/* 162:    */     public void keyTyped(KeyEvent e)
/* 163:    */     {
/* 164:158 */       char key = e.getKeyChar();
/* 165:159 */       TextEntryBox.this.setBackground(Colors.TEXT_COLOR);
/* 166:160 */       if (this.terminators.indexOf(key) >= 0)
/* 167:    */       {
/* 168:162 */         String message = TextEntryBox.this.getText() + key;
/* 169:163 */         Connections.getPorts(TextEntryBox.this).transmit("clear", message);
/* 170:164 */         Connections.getPorts(TextEntryBox.this).launch(message);
/* 171:    */       }
/* 172:166 */       else if (KeyEvent.getKeyText(e.getKeyChar()).equalsIgnoreCase("Escape"))
/* 173:    */       {
/* 174:167 */         TextEntryBox.this.setText("");
/* 175:    */       }
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static void main(String[] ignore)
/* 180:    */   {
/* 181:173 */     TextEntryBox field = new TextEntryBox();
/* 182:    */     
/* 183:    */ 
/* 184:    */ 
/* 185:177 */     field.setText("Hello world!  How are you today?");
/* 186:178 */     JFrame frame = new JFrame();
/* 187:179 */     frame.getContentPane().add(field);
/* 188:180 */     frame.setBounds(0, 0, 400, 90);
/* 189:181 */     frame.setVisible(true);
/* 190:    */     
/* 191:    */ 
/* 192:184 */     System.out.println("Correction " + new TextEntryBox().correctSeparatedPunctuation("Hello world ."));
/* 193:    */   }
/* 194:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.TextEntryBox
 * JD-Core Version:    0.7.0.1
 */