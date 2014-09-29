/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ 
/*  6:   */ public class QuestionFrame
/*  7:   */   extends Frame
/*  8:   */ {
/*  9:12 */   public static String FRAMETYPE = "question";
/* 10:   */   private Type type;
/* 11:   */   private Entity query;
/* 12:   */   
/* 13:   */   public static enum Type
/* 14:   */   {
/* 15:16 */     whereIs,  whatIs,  did,  fromWhereDid,  toWhereDid;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public QuestionFrame(Type t, Entity query)
/* 19:   */   {
/* 20:23 */     this.type = t;
/* 21:24 */     this.query = query;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public QuestionFrame(Entity thing)
/* 25:   */   {
/* 26:28 */     for (Type type : Type.values()) {
/* 27:29 */       if (thing.isA(type.toString())) {
/* 28:30 */         this.type = type;
/* 29:   */       }
/* 30:   */     }
/* 31:33 */     assert (this.type != null);
/* 32:34 */     assert ((thing instanceof Function));
/* 33:35 */     this.query = thing.getSubject();
/* 34:   */   }
/* 35:   */   
/* 36:   */   public Entity getThing()
/* 37:   */   {
/* 38:40 */     Function result = new Function(this.query);
/* 39:41 */     result.addTypes("thing", FRAMETYPE + " " + this.type.toString());
/* 40:42 */     return result;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Type getType()
/* 44:   */   {
/* 45:46 */     return this.type;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Entity getQuery()
/* 49:   */   {
/* 50:50 */     return this.query;
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.QuestionFrame
 * JD-Core Version:    0.7.0.1
 */