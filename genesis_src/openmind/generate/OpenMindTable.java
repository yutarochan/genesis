/*  1:   */ package openmind.generate;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ 
/*  5:   */ public abstract class OpenMindTable
/*  6:   */ {
/*  7:   */   public abstract String getTableName();
/*  8:   */   
/*  9:   */   public abstract String[] getKeys();
/* 10:   */   
/* 11:   */   public abstract String getTriples(HashMap<String, String> paramHashMap);
/* 12:   */   
/* 13:   */   public boolean verifyMap(HashMap<String, String> data)
/* 14:   */   {
/* 15:15 */     for (String key : getKeys()) {
/* 16:16 */       if (!data.containsKey(key)) {
/* 17:16 */         return false;
/* 18:   */       }
/* 19:   */     }
/* 20:17 */     return true;
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     openmind.generate.OpenMindTable
 * JD-Core Version:    0.7.0.1
 */