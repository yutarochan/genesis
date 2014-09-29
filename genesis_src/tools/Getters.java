/*  1:   */ package tools;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ 
/*  5:   */ public class Getters
/*  6:   */ {
/*  7:   */   public static Entity getObject(Entity t)
/*  8:   */   {
/*  9:17 */     return getRole("object", t);
/* 10:   */   }
/* 11:   */   
/* 12:   */   public static void replaceObject(Entity x, Entity element)
/* 13:   */   {
/* 14:24 */     Entity object = getSlot("object", x);
/* 15:25 */     object.setSubject(element);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static Entity getSlot(String marker, Entity t)
/* 19:   */   {
/* 20:32 */     if ((t.relationP()) && (t.getObject().sequenceP())) {
/* 21:33 */       for (Entity role : t.getObject().getElements()) {
/* 22:34 */         if (role.functionP(marker)) {
/* 23:35 */           return role;
/* 24:   */         }
/* 25:   */       }
/* 26:   */     }
/* 27:39 */     return null;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public static Entity getRole(String marker, Entity t)
/* 31:   */   {
/* 32:50 */     Entity slot = getSlot(marker, t);
/* 33:51 */     if (slot != null) {
/* 34:52 */       return slot.getSubject();
/* 35:   */     }
/* 36:54 */     return null;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static Entity getPathElement(String marker, Entity t)
/* 40:   */   {
/* 41:69 */     if (!t.isA("path")) {
/* 42:70 */       return null;
/* 43:   */     }
/* 44:72 */     for (Entity x : t.getElements()) {
/* 45:73 */       if (x.functionP(marker)) {
/* 46:74 */         return x.getSubject();
/* 47:   */       }
/* 48:   */     }
/* 49:77 */     return null;
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tools.Getters
 * JD-Core Version:    0.7.0.1
 */