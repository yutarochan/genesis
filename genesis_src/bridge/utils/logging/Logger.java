/*   1:    */ package bridge.utils.logging;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.util.Vector;
/*   5:    */ 
/*   6:    */ public class Logger
/*   7:    */ {
/*   8: 91 */   String identifier = "";
/*   9: 93 */   String parentIdentifier = "";
/*  10: 95 */   Level level = null;
/*  11: 97 */   private static Vector<Logger> loggers = new Vector();
/*  12:    */   
/*  13:    */   private Logger(String s)
/*  14:    */   {
/*  15:100 */     this.identifier = s;
/*  16:101 */     setParentString(this.identifier);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static Logger getLogger(String name)
/*  20:    */   {
/*  21:105 */     for (int i = 0; i < loggers.size(); i++)
/*  22:    */     {
/*  23:106 */       Logger logger = (Logger)loggers.elementAt(i);
/*  24:107 */       if (logger.identifier.equalsIgnoreCase(name)) {
/*  25:108 */         return logger;
/*  26:    */       }
/*  27:    */     }
/*  28:111 */     Logger newLogger = new Logger(name);
/*  29:112 */     newLogger.setLevel(Level.OFF);
/*  30:113 */     loggers.add(newLogger);
/*  31:114 */     return newLogger;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setLevel(Level level)
/*  35:    */   {
/*  36:118 */     this.level = level;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean isLoggable(Level level)
/*  40:    */   {
/*  41:122 */     return level.intValue() > getLevel().intValue();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void log(Level level, Object s)
/*  45:    */   {
/*  46:126 */     if (getLevel().intValue() > level.intValue()) {
/*  47:127 */       return;
/*  48:    */     }
/*  49:129 */     String output = "Logger @ " + level.getName() + ": " + s.toString();
/*  50:130 */     System.out.println(output);
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void severe(Object s)
/*  54:    */   {
/*  55:134 */     if (getLevel().intValue() > Level.SEVERE.intValue()) {
/*  56:135 */       return;
/*  57:    */     }
/*  58:137 */     String output = "Logger @ severe: " + s.toString();
/*  59:138 */     System.err.println(output);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void warning(Object s)
/*  63:    */   {
/*  64:142 */     if (getLevel().intValue() > Level.WARNING.intValue()) {
/*  65:143 */       return;
/*  66:    */     }
/*  67:145 */     String output = "Logger @ warning: " + s.toString();
/*  68:146 */     System.err.println(output);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void info(Object s)
/*  72:    */   {
/*  73:150 */     if (getLevel().intValue() > Level.INFO.intValue()) {
/*  74:151 */       return;
/*  75:    */     }
/*  76:153 */     String output = "Logger @ info: " + s.toString();
/*  77:154 */     System.out.println(output);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void config(Object s)
/*  81:    */   {
/*  82:158 */     if (getLevel().intValue() > Level.CONFIG.intValue()) {
/*  83:159 */       return;
/*  84:    */     }
/*  85:161 */     String output = "Logger @ config: " + s.toString();
/*  86:162 */     System.out.println(output);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void fine(Object s)
/*  90:    */   {
/*  91:166 */     if (getLevel().intValue() > Level.FINE.intValue()) {
/*  92:167 */       return;
/*  93:    */     }
/*  94:169 */     String output = "Logger @ fine: " + s.toString();
/*  95:170 */     System.out.println(output);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void finer(Object s)
/*  99:    */   {
/* 100:174 */     if (getLevel().intValue() > Level.FINER.intValue()) {
/* 101:175 */       return;
/* 102:    */     }
/* 103:177 */     String output = "Logger @ finer: " + s.toString();
/* 104:178 */     System.out.println(output);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void finest(Object s)
/* 108:    */   {
/* 109:182 */     if (getLevel().intValue() > Level.FINEST.intValue()) {
/* 110:183 */       return;
/* 111:    */     }
/* 112:185 */     String output = "Logger @ finest: " + s.toString();
/* 113:186 */     System.out.println(output);
/* 114:    */   }
/* 115:    */   
/* 116:    */   private void setParentString(String s)
/* 117:    */   {
/* 118:190 */     int index = s.lastIndexOf('.');
/* 119:191 */     if (index >= 0) {
/* 120:192 */       this.parentIdentifier = s.substring(0, index);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private Logger getParent()
/* 125:    */   {
/* 126:197 */     for (int i = 0; i < loggers.size(); i++)
/* 127:    */     {
/* 128:198 */       Logger logger = (Logger)loggers.elementAt(i);
/* 129:199 */       if (logger.identifier.equalsIgnoreCase(this.parentIdentifier)) {
/* 130:200 */         return logger;
/* 131:    */       }
/* 132:    */     }
/* 133:203 */     return null;
/* 134:    */   }
/* 135:    */   
/* 136:    */   private Level getLevel()
/* 137:    */   {
/* 138:207 */     if (this.level != null) {
/* 139:208 */       return this.level;
/* 140:    */     }
/* 141:210 */     Logger parent = getParent();
/* 142:211 */     if (parent != null) {
/* 143:212 */       return parent.getLevel();
/* 144:    */     }
/* 145:214 */     return Level.INFO;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public static void fine(Object o, Object m)
/* 149:    */   {
/* 150:220 */     String identifier = (o instanceof String) ? (String)o : o.getClass().toString();
/* 151:221 */     getLogger("debugging").fine(trimClassDescription(identifier) + m.toString());
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static void info(Object o, Object m)
/* 155:    */   {
/* 156:225 */     String identifier = (o instanceof String) ? (String)o : o.getClass().toString();
/* 157:226 */     getLogger("debugging").info(trimClassDescription(identifier) + m.toString());
/* 158:    */   }
/* 159:    */   
/* 160:    */   public static void warning(Object o, Object m)
/* 161:    */   {
/* 162:230 */     String identifier = (o instanceof String) ? (String)o : o.getClass().toString();
/* 163:231 */     getLogger("debugging").warning(trimClassDescription(identifier) + m.toString());
/* 164:    */   }
/* 165:    */   
/* 166:    */   private static String trimClassDescription(String s)
/* 167:    */   {
/* 168:235 */     int i = s.indexOf(' ');
/* 169:236 */     if (i >= 0) {
/* 170:238 */       return "(" + s.substring(i + 1) + ") ";
/* 171:    */     }
/* 172:240 */     return s.trim() + " ";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public String toString()
/* 176:    */   {
/* 177:244 */     return "Logger " + this.identifier;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public static void main(String[] ignore)
/* 181:    */   {
/* 182:248 */     System.out.println("A");
/* 183:249 */     getLogger("hello").setLevel(Level.WARNING);
/* 184:250 */     getLogger("hello.world").warning("Warning level logger triggered");
/* 185:251 */     getLogger("hello.world").info("Info level logger triggered");
/* 186:252 */     getLogger("hello.world").fine("Fine level logger triggered");
/* 187:253 */     System.out.println("B");
/* 188:254 */     getLogger("hello").setLevel(Level.INFO);
/* 189:255 */     getLogger("hello.world").warning("Warning level logger triggered");
/* 190:256 */     getLogger("hello.world").info("Info level logger triggered");
/* 191:257 */     getLogger("hello.world").fine("Fine level logger triggered");
/* 192:258 */     System.out.println("C");
/* 193:259 */     getLogger("hello").setLevel(Level.FINE);
/* 194:260 */     getLogger("hello.world").warning("Warning level logger triggered");
/* 195:261 */     getLogger("hello.world").info("Info level logger triggered");
/* 196:262 */     getLogger("hello.world").fine("Fine level logger triggered");
/* 197:263 */     System.out.println("D");
/* 198:264 */     getLogger("hello.world").setLevel(Level.WARNING);
/* 199:265 */     getLogger("hello.world").warning("Warning level logger triggered");
/* 200:266 */     getLogger("hello.world").info("Info level logger triggered");
/* 201:267 */     getLogger("hello.world").fine("Fine level logger triggered");
/* 202:    */     
/* 203:269 */     info(new Object(), "Shorthand test");
/* 204:270 */     info("String message", "Shorthand test");
/* 205:    */   }
/* 206:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.logging.Logger
 * JD-Core Version:    0.7.0.1
 */