/*  1:   */ package memory.soms.metrics;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import memory.soms.DistanceMetric;
/*  5:   */ import memory.utilities.Distances;
/*  6:   */ 
/*  7:   */ public class EntityMetric
/*  8:   */   implements DistanceMetric<Entity>
/*  9:   */ {
/* 10:   */   public double distance(Entity e1, Entity e2)
/* 11:   */   {
/* 12:13 */     return Distances.distance(e1, e2);
/* 13:   */   }
/* 14:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.metrics.EntityMetric
 * JD-Core Version:    0.7.0.1
 */