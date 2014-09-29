/* 1:  */ package memory.soms.mergers;
/* 2:  */ 
/* 3:  */ import bridge.reps.entities.Entity;
/* 4:  */ import java.util.Set;
/* 5:  */ import memory.soms.ElementMerger;
/* 6:  */ 
/* 7:  */ public class NullMerger
/* 8:  */   implements ElementMerger<Entity>
/* 9:  */ {
/* ::  */   public Set<Entity> merge(Entity seed, Set<Entity> neighbors)
/* ;:  */   {
/* <:9 */     return neighbors;
/* =:  */   }
/* >:  */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.mergers.NullMerger
 * JD-Core Version:    0.7.0.1
 */