/*  1:   */ package m2;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Set;
/*  8:   */ import m2.datatypes.Chain;
/*  9:   */ import m2.datatypes.ImmutableEntity;
/* 10:   */ 
/* 11:   */ public class Methods
/* 12:   */ {
/* 13:   */   public static List<Entity> convertFromIthingList(List<ImmutableEntity> l)
/* 14:   */   {
/* 15:24 */     List<Entity> result = new ArrayList();
/* 16:25 */     for (ImmutableEntity item : l) {
/* 17:26 */       result.add(item.getThing());
/* 18:   */     }
/* 19:28 */     return result;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static Set<Entity> convertFromIthingSet(Set<ImmutableEntity> l)
/* 23:   */   {
/* 24:32 */     Set<Entity> result = new HashSet();
/* 25:33 */     for (ImmutableEntity item : l) {
/* 26:34 */       result.add(item.getThing());
/* 27:   */     }
/* 28:36 */     return result;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static boolean containsThing(Entity parent, Entity t)
/* 32:   */   {
/* 33:43 */     ImmutableEntity ithing = new ImmutableEntity(t);
/* 34:44 */     List<Entity> subThings = Chain.flattenThing(parent);
/* 35:45 */     for (Entity sub : subThings)
/* 36:   */     {
/* 37:46 */       ImmutableEntity isub = new ImmutableEntity(sub);
/* 38:47 */       if (isub.equals(ithing)) {
/* 39:47 */         return true;
/* 40:   */       }
/* 41:   */     }
/* 42:49 */     return false;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public static boolean containsIthing(ImmutableEntity parent, ImmutableEntity t)
/* 46:   */   {
/* 47:57 */     List<Entity> subThings = Chain.flattenThing(parent.getThing());
/* 48:58 */     for (Entity sub : subThings)
/* 49:   */     {
/* 50:59 */       ImmutableEntity isub = new ImmutableEntity(sub);
/* 51:62 */       if (isub.equals(t)) {
/* 52:63 */         return true;
/* 53:   */       }
/* 54:   */     }
/* 55:66 */     return false;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.Methods
 * JD-Core Version:    0.7.0.1
 */