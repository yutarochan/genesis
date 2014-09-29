/*  1:   */ package transitionSpace;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ 
/*  5:   */ public class Ladder
/*  6:   */   extends HashMap<String, HashMap<String, String>>
/*  7:   */ {
/*  8:   */   public static final String O1 = "Alpha";
/*  9:   */   public static final String O2 = "Bravo";
/* 10:   */   public static final String O3 = "Charlie";
/* 11:   */   public static final String P1 = "pattern object 1";
/* 12:   */   public static final String P2 = "pattern object 2";
/* 13:   */   public static final String P3 = "pattern object 3";
/* 14:   */   public static final String ML = "moving left";
/* 15:   */   public static final String MR = "moving right";
/* 16:   */   public static final String APPEAR = "appear";
/* 17:   */   public static final String DISAPPEAR = "disappear";
/* 18:   */   public static final String INCREASE = "increase";
/* 19:   */   public static final String DECREASE = "decrease";
/* 20:   */   public static final String CHANGE = "change";
/* 21:   */   public static final String NOT_APPEAR = "not appear";
/* 22:   */   public static final String NOT_DISAPPEAR = "not disappear";
/* 23:   */   public static final String NOT_INCREASE = "not increase";
/* 24:   */   public static final String NOT_DECREASE = "not decrease";
/* 25:   */   public static final String NOT_CHANGE = "not change";
/* 26:   */   
/* 27:   */   public void addTransition(String object, String label, String change)
/* 28:   */   {
/* 29:57 */     put(object, label, change);
/* 30:   */   }
/* 31:   */   
/* 32:   */   private void put(String object, String label, String change)
/* 33:   */   {
/* 34:61 */     getLabelMap(object).put(label, change);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public String get(String object, String label)
/* 38:   */   {
/* 39:65 */     return (String)getLabelMap(object).get(label);
/* 40:   */   }
/* 41:   */   
/* 42:   */   private HashMap<String, String> getLabelMap(String object)
/* 43:   */   {
/* 44:69 */     HashMap<String, String> map = (HashMap)get(object);
/* 45:70 */     if (map == null)
/* 46:   */     {
/* 47:71 */       map = new HashMap();
/* 48:72 */       put(object, map);
/* 49:   */     }
/* 50:74 */     return map;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     transitionSpace.Ladder
 * JD-Core Version:    0.7.0.1
 */