/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import bridge.utils.logging.Logger;
/*  4:   */ 
/*  5:   */ public class NameGenerator
/*  6:   */ {
/*  7:11 */   protected static int memory = 0;
/*  8:   */   public static final String LOGGER_GROUP = "thing";
/*  9:   */   public static final String LOGGER_INSTANCE = "NameGenerator";
/* 10:   */   public static final String LOGGER = "thing.NameGenerator";
/* 11:   */   
/* 12:   */   public static String getNewName()
/* 13:   */   {
/* 14:16 */     String result = "-" + memory;
/* 15:17 */     increment();
/* 16:18 */     return result;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static int getNewID()
/* 20:   */   {
/* 21:24 */     int result = memory;
/* 22:25 */     increment();
/* 23:26 */     return result;
/* 24:   */   }
/* 25:   */   
/* 26:   */   private static void increment()
/* 27:   */   {
/* 28:30 */     fine("Incrementing memory from " + memory);
/* 29:31 */     memory += 1;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public static void clearNameMemory()
/* 33:   */   {
/* 34:36 */     memory = 0;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void setNameMemory(int i)
/* 38:   */   {
/* 39:39 */     memory = Math.max(memory, i);
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static void registerLoadedThing(String suffix)
/* 43:   */   {
/* 44:45 */     Integer instancenum = new Integer(suffix);
/* 45:46 */     int inum = -instancenum.intValue();
/* 46:47 */     if (inum + 1 > memory) {
/* 47:47 */       memory = inum + 1;
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected static void finest(Object s)
/* 52:   */   {
/* 53:56 */     Logger.getLogger("thing.NameGenerator").finest("NameGenerator: " + s);
/* 54:   */   }
/* 55:   */   
/* 56:   */   protected static void finer(Object s)
/* 57:   */   {
/* 58:59 */     Logger.getLogger("thing.NameGenerator").finer("NameGenerator: " + s);
/* 59:   */   }
/* 60:   */   
/* 61:   */   protected static void fine(Object s)
/* 62:   */   {
/* 63:62 */     Logger.getLogger("thing.NameGenerator").fine("NameGenerator: " + s);
/* 64:   */   }
/* 65:   */   
/* 66:   */   protected static void config(Object s)
/* 67:   */   {
/* 68:65 */     Logger.getLogger("thing.NameGenerator").config("NameGenerator: " + s);
/* 69:   */   }
/* 70:   */   
/* 71:   */   protected static void info(Object s)
/* 72:   */   {
/* 73:68 */     Logger.getLogger("thing.NameGenerator").info("NameGenerator: " + s);
/* 74:   */   }
/* 75:   */   
/* 76:   */   protected static void warning(Object s)
/* 77:   */   {
/* 78:71 */     Logger.getLogger("thing.NameGenerator").warning("NameGenerator: " + s);
/* 79:   */   }
/* 80:   */   
/* 81:   */   protected static void severe(Object s)
/* 82:   */   {
/* 83:74 */     Logger.getLogger("thing.NameGenerator").severe("NameGenerator: " + s);
/* 84:   */   }
/* 85:   */   
/* 86:   */   public static int extractIDFromName(String name)
/* 87:   */   {
/* 88:78 */     if (name == null) {
/* 89:79 */       return -1;
/* 90:   */     }
/* 91:81 */     int index = name.lastIndexOf('-');
/* 92:82 */     if (index >= 0) {
/* 93:83 */       return Integer.parseInt(name.substring(index + 1));
/* 94:   */     }
/* 95:85 */     return -1;
/* 96:   */   }
/* 97:   */   
/* 98:   */   public static String extractSuffixFromName(String name)
/* 99:   */   {
/* :0:89 */     if (name == null) {
/* :1:90 */       return null;
/* :2:   */     }
/* :3:92 */     int index = name.lastIndexOf('-');
/* :4:93 */     if (index >= 0) {
/* :5:94 */       return name.substring(index);
/* :6:   */     }
/* :7:96 */     return null;
/* :8:   */   }
/* :9:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.NameGenerator
 * JD-Core Version:    0.7.0.1
 */