/*   1:    */ package utils;
/*   2:    */ 
/*   3:    */ import java.util.HashMap;
/*   4:    */ import javax.swing.JCheckBoxMenuItem;
/*   5:    */ 
/*   6:    */ public class Timer
/*   7:    */ {
/*   8: 14 */   private static HashMap<String, Long> timeMap = new HashMap();
/*   9:    */   
/*  10:    */   private static String seconds(long millis)
/*  11:    */   {
/*  12: 17 */     return String.valueOf(millis / 1000L);
/*  13:    */   }
/*  14:    */   
/*  15:    */   private static String hundredths(long millis)
/*  16:    */   {
/*  17: 21 */     int hundredths = (int)(millis / 10L);
/*  18: 22 */     int sec = (int)(millis / 1000L);
/*  19: 23 */     hundredths -= 100 * sec;
/*  20: 24 */     if (hundredths < 10) {
/*  21: 25 */       return sec + ".0" + hundredths;
/*  22:    */     }
/*  23: 27 */     return sec + "." + hundredths;
/*  24:    */   }
/*  25:    */   
/*  26:    */   private static String tenths(long millis)
/*  27:    */   {
/*  28: 31 */     int tenths = (int)(millis / 100L);
/*  29: 32 */     int sec = tenths / 10;
/*  30: 33 */     tenths -= 10 * sec;
/*  31: 34 */     return sec + "." + tenths;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static void laptime(boolean trigger, String message, long startTime)
/*  35:    */   {
/*  36: 38 */     laptime(trigger, message, message, startTime);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public static void laptime(JCheckBoxMenuItem trigger, String message, long startTime)
/*  40:    */   {
/*  41: 42 */     laptime(trigger.isSelected(), message, message, startTime);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static void laptime(JCheckBoxMenuItem trigger, String key, String message, long startTime)
/*  45:    */   {
/*  46: 46 */     laptime(trigger.isSelected(), key, message, startTime);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static String laptime(String key, long startTime)
/*  50:    */   {
/*  51: 50 */     return calulateTime(key, startTime, true);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static void laptime(boolean trigger, String key, String message, long startTime)
/*  55:    */   {
/*  56: 54 */     if (trigger) {
/*  57: 55 */       String str = calulateTime(key, startTime, true);
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static void laptime(boolean trigger, String key, String message, long startTime, long threshold)
/*  62:    */   {
/*  63: 60 */     if (System.currentTimeMillis() > startTime + threshold) {
/*  64: 61 */       laptime(trigger, key, message, startTime);
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static void time(boolean trigger, String message, long startTime)
/*  69:    */   {
/*  70: 66 */     time(trigger, message, message, startTime);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static void time(JCheckBoxMenuItem trigger, String message, long startTime)
/*  74:    */   {
/*  75: 70 */     time(trigger.isSelected(), message, message, startTime);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static void time(JCheckBoxMenuItem trigger, String key, String message, long startTime)
/*  79:    */   {
/*  80: 74 */     time(trigger.isSelected(), key, message, startTime);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static String time(String key, long startTime)
/*  84:    */   {
/*  85: 78 */     return calulateTime(key, startTime, false);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static void time(boolean trigger, String key, String message, long startTime, long threshold)
/*  89:    */   {
/*  90: 82 */     if (System.currentTimeMillis() > startTime + threshold) {
/*  91: 83 */       time(trigger, key, message, startTime);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static void time(boolean trigger, String key, String message, long startTime)
/*  96:    */   {
/*  97: 88 */     if (trigger) {
/*  98: 89 */       String str = calulateTime(key, startTime, false);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   private static String calulateTime(String key, long startTime, boolean showCumulative)
/* 103:    */   {
/* 104: 96 */     Long cumulative = (Long)timeMap.get(key);
/* 105: 97 */     if (cumulative == null) {
/* 106: 98 */       cumulative = new Long(0L);
/* 107:    */     }
/* 108:100 */     long nowTime = System.currentTimeMillis();
/* 109:101 */     long delta = nowTime - startTime;
/* 110:102 */     cumulative = Long.valueOf(cumulative.longValue() + delta);
/* 111:103 */     timeMap.put(key, cumulative);
/* 112:    */     String result;
/* 113:    */     String result;
/* 114:106 */     if (showCumulative) {
/* 115:107 */       result = hundredths(delta) + " sec / " + tenths(cumulative.longValue()) + " sec";
/* 116:    */     } else {
/* 117:110 */       result = tenths(delta) + " sec";
/* 118:    */     }
/* 119:112 */     return result;
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.Timer
 * JD-Core Version:    0.7.0.1
 */