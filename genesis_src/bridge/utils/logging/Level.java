/*  1:   */ package bridge.utils.logging;
/*  2:   */ 
/*  3:   */ public class Level
/*  4:   */ {
/*  5: 4 */   public static final Level OFF = new Level("off", 7);
/*  6: 6 */   public static final Level SEVERE = new Level("severe", 6);
/*  7: 8 */   public static final Level WARNING = new Level("warning", 5);
/*  8:10 */   public static final Level INFO = new Level("info", 4);
/*  9:12 */   public static final Level CONFIG = new Level("config", 3);
/* 10:14 */   public static final Level FINE = new Level("fine", 2);
/* 11:16 */   public static final Level FINER = new Level("finer", 1);
/* 12:18 */   public static final Level FINEST = new Level("finest", 0);
/* 13:20 */   public static final Level All = new Level("all", -1);
/* 14:   */   final int myLevel;
/* 15:   */   final String name;
/* 16:   */   
/* 17:   */   protected Level(String name, int level)
/* 18:   */   {
/* 19:27 */     this.myLevel = level;
/* 20:28 */     this.name = name;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String toString()
/* 24:   */   {
/* 25:32 */     return this.name;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int intValue()
/* 29:   */   {
/* 30:36 */     return this.myLevel;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getName()
/* 34:   */   {
/* 35:40 */     return this.name;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.logging.Level
 * JD-Core Version:    0.7.0.1
 */