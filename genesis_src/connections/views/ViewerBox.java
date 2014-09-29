/*   1:    */ package connections.views;
/*   2:    */ 
/*   3:    */ import connections.WiredBox;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.util.Observable;
/*   6:    */ import java.util.Set;
/*   7:    */ import java.util.TreeSet;
/*   8:    */ import utils.Colors;
/*   9:    */ 
/*  10:    */ public class ViewerBox
/*  11:    */   extends Observable
/*  12:    */   implements ColoredBox
/*  13:    */ {
/*  14:    */   public static final int VIRGIN = 0;
/*  15:    */   public static final int ACTUATED = 1;
/*  16:    */   public static final int BLEW_OUT = 2;
/*  17:    */   public static final String PAINT = "Paint";
/*  18:    */   public static final String REDO = "Redo";
/*  19:    */   private Set<String> inputPortNames;
/*  20:    */   private Set<String> outputPortNames;
/*  21: 30 */   private int state = 0;
/*  22: 32 */   private boolean selected = false;
/*  23: 34 */   private int width = 110;
/*  24: 34 */   private int height = 80;
/*  25: 38 */   private int deltaX = 50;
/*  26: 38 */   private int deltaY = 25;
/*  27:    */   private int x;
/*  28:    */   private int y;
/*  29:    */   private String text;
/*  30:    */   private WiredBox source;
/*  31: 48 */   public static Color defaultColor = Color.WHITE;
/*  32: 50 */   public static Color activeColor = Color.GREEN;
/*  33: 52 */   public static Color rootColor = Color.PINK;
/*  34: 54 */   public static Color usedColor = Colors.USED_COLOR;
/*  35: 56 */   private Color color = defaultColor;
/*  36: 58 */   private Color permanentColor = defaultColor;
/*  37: 60 */   private boolean visible = true;
/*  38: 62 */   public static int NEITHER = -1;
/*  39: 62 */   public static int OFF_SWITCH = 0;
/*  40: 62 */   public static int ON_SWITCH = 1;
/*  41: 64 */   private int switchState = NEITHER;
/*  42: 66 */   private boolean toggleSwitch = false;
/*  43: 68 */   private boolean negative = false;
/*  44: 70 */   private boolean dotted = false;
/*  45:    */   
/*  46:    */   public ViewerBox(int row, int column, String label, WiredBox source)
/*  47:    */   {
/*  48: 74 */     this.x = (column * (this.width + this.deltaX));
/*  49: 75 */     this.y = (row * (this.height + this.deltaY));
/*  50: 76 */     this.text = label;
/*  51: 77 */     this.source = source;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getX()
/*  55:    */   {
/*  56: 81 */     return this.x;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int getY()
/*  60:    */   {
/*  61: 85 */     return this.y;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String getText()
/*  65:    */   {
/*  66: 89 */     return this.text;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getWidth()
/*  70:    */   {
/*  71: 93 */     return this.width;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getHeight()
/*  75:    */   {
/*  76: 97 */     return this.height;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public int getDeltaX()
/*  80:    */   {
/*  81:101 */     return this.deltaX;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public int getDeltaY()
/*  85:    */   {
/*  86:105 */     return this.deltaY;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Color getColor()
/*  90:    */   {
/*  91:109 */     return this.color;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setColor(Color color)
/*  95:    */   {
/*  96:113 */     this.color = color;
/*  97:114 */     changed("Paint");
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setPermanentColor(Color color)
/* 101:    */   {
/* 102:118 */     this.permanentColor = color;
/* 103:119 */     setColor(color);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void changed(String x)
/* 107:    */   {
/* 108:123 */     setChanged();
/* 109:124 */     notifyObservers(x);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void resetColor()
/* 113:    */   {
/* 114:128 */     setColor(this.permanentColor);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public synchronized void setTemporaryColor() {}
/* 118:    */   
/* 119:    */   public Class<? extends WiredBox> getSourceClass()
/* 120:    */   {
/* 121:137 */     return this.source.getClass();
/* 122:    */   }
/* 123:    */   
/* 124:    */   public WiredBox getSource()
/* 125:    */   {
/* 126:141 */     return this.source;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setSelected(boolean b)
/* 130:    */   {
/* 131:145 */     this.selected = b;
/* 132:146 */     if ((!this.selected) && (this.state == 2)) {
/* 133:147 */       this.state = 1;
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isSelected()
/* 138:    */   {
/* 139:152 */     return (this.selected) || (this.state == 2);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public int getState()
/* 143:    */   {
/* 144:156 */     return this.state;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setState(int state)
/* 148:    */   {
/* 149:160 */     this.state = state;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setVisible(boolean b)
/* 153:    */   {
/* 154:164 */     this.visible = b;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isVisible()
/* 158:    */   {
/* 159:168 */     return this.visible;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public int getSwitchState()
/* 163:    */   {
/* 164:173 */     return this.switchState;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setSwitchState(int switchState)
/* 168:    */   {
/* 169:177 */     this.switchState = switchState;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public boolean isToggleSwitch()
/* 173:    */   {
/* 174:183 */     return this.toggleSwitch;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setToggleSwitch(boolean toggleSwitch)
/* 178:    */   {
/* 179:187 */     this.toggleSwitch = toggleSwitch;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean isNegative()
/* 183:    */   {
/* 184:191 */     return this.negative;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setNegative(boolean negative)
/* 188:    */   {
/* 189:195 */     this.negative = negative;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public boolean isDotted()
/* 193:    */   {
/* 194:199 */     return this.dotted;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setDotted(boolean dotted)
/* 198:    */   {
/* 199:203 */     this.dotted = dotted;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public Set<String> getOutputPortNames()
/* 203:    */   {
/* 204:207 */     if (this.outputPortNames == null) {
/* 205:208 */       this.outputPortNames = new TreeSet();
/* 206:    */     }
/* 207:210 */     return this.outputPortNames;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setOutputPortNames(Set<String> outputPortNames)
/* 211:    */   {
/* 212:214 */     this.outputPortNames = outputPortNames;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Set<String> getInputPortNames()
/* 216:    */   {
/* 217:218 */     if (this.inputPortNames == null) {
/* 218:219 */       this.inputPortNames = new TreeSet();
/* 219:    */     }
/* 220:221 */     return this.inputPortNames;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void setInputPortNames(Set<String> inputPortNames)
/* 224:    */   {
/* 225:225 */     this.inputPortNames = inputPortNames;
/* 226:    */   }
/* 227:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.views.ViewerBox
 * JD-Core Version:    0.7.0.1
 */