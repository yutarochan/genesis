/*  1:   */ package magicLess.distancemetrics;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.HashMap;
/*  6:   */ import java.util.List;
/*  7:   */ import java.util.Set;
/*  8:   */ import magicLess.utils.EntityUtils;
/*  9:   */ 
/* 10:   */ public class EntityPointWithHeuristicDistance
/* 11:   */   extends Point<Entity>
/* 12:   */ {
/* 13:   */   public static final double EPSILON = 1.E-009D;
/* 14:   */   public static final double MAX_DIST = 1.0D;
/* 15:   */   private Entity thinger;
/* 16:   */   
/* 17:   */   public EntityPointWithHeuristicDistance(Entity bleat)
/* 18:   */   {
/* 19:13 */     this.thinger = bleat;
/* 20:   */   }
/* 21:   */   
/* 22:   */   protected double getDistance(Entity a, Entity b)
/* 23:   */   {
/* 24:17 */     if (!EntityUtils.getRepType(a).equals(EntityUtils.getRepType(b))) {
/* 25:18 */       return 1.0D;
/* 26:   */     }
/* 27:20 */     double bDist = Operations.distance(a.getBundle(), b.getBundle());
/* 28:21 */     if (bDist > 1.E-009D) {
/* 29:25 */       return bDist;
/* 30:   */     }
/* 31:28 */     if ((!EntityUtils.hasComponents(a)) && (!EntityUtils.hasComponents(b))) {
/* 32:29 */       return 0.0D;
/* 33:   */     }
/* 34:31 */     if (EntityUtils.hasComponents(a) != EntityUtils.hasComponents(b)) {
/* 35:32 */       return 1.0D;
/* 36:   */     }
/* 37:35 */     List<Point<Entity>> aa = new ArrayList(a.getDescendants().size());
/* 38:36 */     List<Point<Entity>> bb = new ArrayList(b.getDescendants().size());
/* 39:37 */     for (Entity t : a.getDescendants()) {
/* 40:38 */       aa.add(new EntityPointWithHeuristicDistance(t));
/* 41:   */     }
/* 42:40 */     for (Entity t : b.getDescendants()) {
/* 43:41 */       bb.add(new EntityPointWithHeuristicDistance(t));
/* 44:   */     }
/* 45:44 */     HashMap optimalPairing = Operations.hungarian(aa, bb);
/* 46:   */     
/* 47:   */ 
/* 48:47 */     double accum = 0.0D;
/* 49:48 */     for (Object k : optimalPairing.keySet()) {
/* 50:49 */       accum += Operations.distance((Point)k, (Point)optimalPairing.get(k));
/* 51:   */     }
/* 52:53 */     return accum / Math.min(aa.size(), bb.size());
/* 53:   */   }
/* 54:   */   
/* 55:   */   public Entity getWrapped()
/* 56:   */   {
/* 57:59 */     return this.thinger;
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     magicLess.distancemetrics.EntityPointWithHeuristicDistance
 * JD-Core Version:    0.7.0.1
 */