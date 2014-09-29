/*  1:   */ package memory;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import constants.RecognizedRepresentations;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Set;
/*  8:   */ import java.util.Vector;
/*  9:   */ import magicLess.utils.EntityUtils;
/* 10:   */ 
/* 11:   */ public class RepProcessor
/* 12:   */ {
/* 13:   */   public static Set<Entity> extractSubReps(Entity t)
/* 14:   */   {
/* 15:25 */     Set<Entity> results = new HashSet();
/* 16:26 */     Set<Entity> children = t.getDescendants();
/* 17:27 */     for (Entity c : children) {
/* 18:28 */       if (RecognizedRepresentations.ALL_THING_REPS.contains(EntityUtils.getRepType(c))) {
/* 19:29 */         results.add(c);
/* 20:   */       }
/* 21:   */     }
/* 22:38 */     return results;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static Set<Entity> extractAtoms(Entity t)
/* 26:   */   {
/* 27:51 */     Entity clone = t.deepClone();
/* 28:52 */     Set<Entity> children = clone.getDescendants();
/* 29:53 */     Set<Entity> results = new HashSet();
/* 30:54 */     for (Entity c : children) {
/* 31:55 */       if (c.entityP())
/* 32:   */       {
/* 33:56 */         if (c.getTypes().contains("entity")) {
/* 34:57 */           results.add(c);
/* 35:   */         }
/* 36:59 */         if (c.getTypes().contains("action")) {
/* 37:60 */           results.add(c);
/* 38:   */         }
/* 39:   */       }
/* 40:   */     }
/* 41:63 */     return results;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public static Entity wrap(Entity thing)
/* 45:   */   {
/* 46:88 */     return thing;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static Entity unwrap(Entity thing)
/* 50:   */   {
/* 51:96 */     return thing;
/* 52:   */   }
/* 53:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.RepProcessor
 * JD-Core Version:    0.7.0.1
 */