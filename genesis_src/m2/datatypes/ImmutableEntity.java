/*  1:   */ package m2.datatypes;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.List;
/*  7:   */ 
/*  8:   */ public final class ImmutableEntity
/*  9:   */ {
/* 10:   */   private final Entity t;
/* 11:   */   private final List<Bundle> flat;
/* 12:   */   
/* 13:   */   public ImmutableEntity(Entity t)
/* 14:   */   {
/* 15:25 */     this.t = t;
/* 16:26 */     List<Entity> flatThing = Chain.flattenThing(this.t);
/* 17:27 */     this.flat = new ArrayList(flatThing.size());
/* 18:28 */     for (Entity el : flatThing) {
/* 19:29 */       this.flat.add(el.getBundle());
/* 20:   */     }
/* 21:   */   }
/* 22:   */   
/* 23:   */   public Entity getThing()
/* 24:   */   {
/* 25:36 */     return this.t.deepClone();
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int hashCode()
/* 29:   */   {
/* 30:41 */     int prime = 31;
/* 31:42 */     int result = 1;
/* 32:43 */     result = 31 * result + (this.flat == null ? 0 : this.flat.hashCode());
/* 33:44 */     return result;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public boolean equals(Object obj)
/* 37:   */   {
/* 38:49 */     if (this == obj) {
/* 39:50 */       return true;
/* 40:   */     }
/* 41:51 */     if (obj == null) {
/* 42:52 */       return false;
/* 43:   */     }
/* 44:53 */     if (getClass() != obj.getClass()) {
/* 45:54 */       return false;
/* 46:   */     }
/* 47:55 */     ImmutableEntity other = (ImmutableEntity)obj;
/* 48:56 */     if (this.flat == null)
/* 49:   */     {
/* 50:57 */       if (other.flat != null) {
/* 51:58 */         return false;
/* 52:   */       }
/* 53:   */     }
/* 54:59 */     else if (!this.flat.equals(other.flat)) {
/* 55:60 */       return false;
/* 56:   */     }
/* 57:61 */     return true;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String toString()
/* 61:   */   {
/* 62:66 */     return "ImmutableEntity: " + this.flat.toString();
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.datatypes.ImmutableEntity
 * JD-Core Version:    0.7.0.1
 */