/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ public class RFactory
/*  4:   */ {
/*  5:   */   public static Relation makeRoleFrameRelation(Object a, String relation, Object o)
/*  6:   */   {
/*  7:   */     Entity agent;
/*  8:   */     Entity agent;
/*  9:16 */     if ((a instanceof String)) {
/* 10:17 */       agent = new Entity((String)a);
/* 11:   */     } else {
/* 12:20 */       agent = (Entity)a;
/* 13:   */     }
/* 14:   */     Entity object;
/* 15:   */     Entity object;
/* 16:23 */     if ((o instanceof String))
/* 17:   */     {
/* 18:24 */       object = new Entity((String)o);
/* 19:   */     }
/* 20:   */     else
/* 21:   */     {
/* 22:   */       Entity object;
/* 23:26 */       if ((o instanceof Entity)) {
/* 24:27 */         object = (Entity)o;
/* 25:   */       } else {
/* 26:30 */         object = null;
/* 27:   */       }
/* 28:   */     }
/* 29:32 */     Sequence roles = new Sequence("roles");
/* 30:   */     
/* 31:34 */     Relation result = new Relation(relation, agent, roles);
/* 32:36 */     if (object != null) {
/* 33:37 */       roles.addElement(new Function("object", object));
/* 34:   */     }
/* 35:39 */     return result;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public static Relation addRoleFrameTo(Object o, Relation relation)
/* 39:   */   {
/* 40:43 */     return addRoleFrameRole("to", o, relation);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static Relation addRoleFrameFrom(Object o, Relation relation)
/* 44:   */   {
/* 45:47 */     return addRoleFrameRole("from", o, relation);
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static Relation addRoleFrameRole(String marker, Object o, Relation relation)
/* 49:   */   {
/* 50:   */     Entity object;
/* 51:   */     Entity object;
/* 52:52 */     if ((o instanceof String))
/* 53:   */     {
/* 54:53 */       object = new Entity((String)o);
/* 55:   */     }
/* 56:   */     else
/* 57:   */     {
/* 58:   */       Entity object;
/* 59:55 */       if ((o instanceof Entity)) {
/* 60:56 */         object = (Entity)o;
/* 61:   */       } else {
/* 62:59 */         object = null;
/* 63:   */       }
/* 64:   */     }
/* 65:62 */     Entity roles = relation.getObject();
/* 66:64 */     if ((roles.sequenceP()) && (object != null)) {
/* 67:65 */       roles.addElement(new Function(marker, object));
/* 68:   */     }
/* 69:67 */     return relation;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.RFactory
 * JD-Core Version:    0.7.0.1
 */