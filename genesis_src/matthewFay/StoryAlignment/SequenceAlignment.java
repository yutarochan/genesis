/*  1:   */ package matthewFay.StoryAlignment;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import matthewFay.Utilities.EntityHelper;
/*  5:   */ import matthewFay.Utilities.Pair;
/*  6:   */ import minilisp.LList;
/*  7:   */ import utils.PairOfEntities;
/*  8:   */ 
/*  9:   */ public class SequenceAlignment
/* 10:   */   extends Alignment<Entity, Entity>
/* 11:   */ {
/* 12:12 */   public LList<PairOfEntities> bindings = null;
/* 13:   */   
/* 14:   */   public SequenceAlignment(Alignment<Entity, Entity> alignment)
/* 15:   */   {
/* 16:15 */     this.score = alignment.score;
/* 17:16 */     addAll(alignment);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void fillGaps()
/* 21:   */   {
/* 22:20 */     for (int i = 0; i < size(); i++)
/* 23:   */     {
/* 24:21 */       Pair<Entity, Entity> pair = (Pair)get(i);
/* 25:22 */       if ((pair.a == null) && (pair.b != null))
/* 26:   */       {
/* 27:24 */         Entity eltToAdd = ((Entity)pair.b).deepClone(false);
/* 28:25 */         eltToAdd = EntityHelper.findAndReplace(eltToAdd, this.bindings, true, true);
/* 29:26 */         pair.a = eltToAdd;
/* 30:27 */         ((Entity)pair.a).addFeature("GapFilled");
/* 31:   */       }
/* 32:29 */       if ((pair.a != null) && (pair.b == null))
/* 33:   */       {
/* 34:32 */         Entity eltToAdd = ((Entity)pair.a).deepClone(false);
/* 35:33 */         eltToAdd = EntityHelper.findAndReplace(eltToAdd, this.bindings, true);
/* 36:34 */         pair.b = eltToAdd;
/* 37:35 */         ((Entity)pair.b).addFeature("GapFilled");
/* 38:   */       }
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void selectiveFillGaps(Entity required_entity)
/* 43:   */   {
/* 44:41 */     for (int i = 0; i < size(); i++)
/* 45:   */     {
/* 46:42 */       Pair<Entity, Entity> pair = (Pair)get(i);
/* 47:43 */       if ((pair.a == null) && (pair.b != null) && 
/* 48:44 */         (EntityHelper.contains(required_entity, (Entity)pair.b)))
/* 49:   */       {
/* 50:46 */         Entity eltToAdd = ((Entity)pair.b).deepClone(false);
/* 51:47 */         eltToAdd = EntityHelper.findAndReplace(eltToAdd, this.bindings, true);
/* 52:48 */         pair.a = eltToAdd;
/* 53:   */       }
/* 54:52 */       if ((pair.a != null) && (pair.b == null) && 
/* 55:53 */         (EntityHelper.contains(required_entity, (Entity)pair.a)))
/* 56:   */       {
/* 57:56 */         Entity eltToAdd = ((Entity)pair.a).deepClone(false);
/* 58:57 */         eltToAdd = EntityHelper.findAndReplace(eltToAdd, this.bindings, true);
/* 59:58 */         pair.b = eltToAdd;
/* 60:   */       }
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.SequenceAlignment
 * JD-Core Version:    0.7.0.1
 */