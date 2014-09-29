/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ 
/*  5:   */ public class Tool
/*  6:   */ {
/*  7:   */   public static Entity extractObject(Entity t)
/*  8:   */   {
/*  9:14 */     if (t.relationP("action")) {
/* 10:15 */       return extractObject(t.getObject());
/* 11:   */     }
/* 12:17 */     if (t.sequenceP("roles")) {
/* 13:18 */       for (Entity x : t.getElements()) {
/* 14:19 */         if (x.isA("object")) {
/* 15:20 */           return x.getSubject();
/* 16:   */         }
/* 17:   */       }
/* 18:   */     }
/* 19:24 */     return null;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static Entity extractPath(Entity r)
/* 23:   */   {
/* 24:28 */     Entity path = extractObject(r);
/* 25:29 */     if (path.sequenceP("path")) {
/* 26:30 */       return path;
/* 27:   */     }
/* 28:32 */     return null;
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.Tool
 * JD-Core Version:    0.7.0.1
 */