/*  1:   */ package susanSong;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Relation;
/*  6:   */ import bridge.reps.entities.Thread;
/*  7:   */ 
/*  8:   */ public class TraitProcessor
/*  9:   */ {
/* 10:   */   public static Relation getTraitIdentifier(Entity t)
/* 11:   */   {
/* 12:11 */     if (t.relationP("cause"))
/* 13:   */     {
/* 14:12 */       Relation r = (Relation)t;
/* 15:13 */       Entity o = r.getObject();
/* 16:15 */       if (o.relationP("property"))
/* 17:   */       {
/* 18:16 */         Relation identifier = (Relation)o;
/* 19:17 */         Entity subject = identifier.getSubject();
/* 20:18 */         Entity object = identifier.getObject();
/* 21:19 */         for (Thread thread : object.getBundle()) {
/* 22:21 */           if (thread.getThreadType().equals("ad_word"))
/* 23:   */           {
/* 24:22 */             object.getBundle().clear();
/* 25:23 */             object.getBundle().add(thread);
/* 26:24 */             return new Relation("property", subject, object);
/* 27:   */           }
/* 28:   */         }
/* 29:   */       }
/* 30:   */     }
/* 31:29 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public static Entity getTraitDescriptor(Entity t)
/* 35:   */   {
/* 36:33 */     if (getTraitIdentifier(t) != null) {
/* 37:34 */       return t.getSubject();
/* 38:   */     }
/* 39:36 */     return null;
/* 40:   */   }
/* 41:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     susanSong.TraitProcessor
 * JD-Core Version:    0.7.0.1
 */