/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ 
/*  5:   */ public class Recognizers
/*  6:   */ {
/*  7:   */   public static Entity theThing(Object o)
/*  8:   */   {
/*  9:14 */     if ((o instanceof Entity)) {
/* 10:15 */       return (Entity)o;
/* 11:   */     }
/* 12:17 */     return null;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static Entity theSubject(Object o)
/* 16:   */   {
/* 17:21 */     if ((o instanceof Entity)) {
/* 18:22 */       return ((Entity)o).getSubject();
/* 19:   */     }
/* 20:24 */     return null;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static Entity theObject(Object o)
/* 24:   */   {
/* 25:28 */     if ((o instanceof Entity)) {
/* 26:29 */       return ((Entity)o).getObject();
/* 27:   */     }
/* 28:31 */     return null;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static boolean action(Object o)
/* 32:   */   {
/* 33:35 */     if ((o instanceof Entity))
/* 34:   */     {
/* 35:36 */       Entity t = (Entity)o;
/* 36:37 */       if (t.isA("action")) {
/* 37:38 */         return true;
/* 38:   */       }
/* 39:   */     }
/* 40:41 */     return false;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static boolean agent(Object o)
/* 44:   */   {
/* 45:45 */     if ((o instanceof Entity))
/* 46:   */     {
/* 47:46 */       Entity t = (Entity)o;
/* 48:47 */       if ((t.relationP("agent")) && (t.getSubject().entityP("entity")) && (t.getObject().isA("action"))) {
/* 49:48 */         return true;
/* 50:   */       }
/* 51:   */     }
/* 52:51 */     return false;
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.Recognizers
 * JD-Core Version:    0.7.0.1
 */