/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.modules.memory.BasicMemory;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import constants.RecognizedRepresentations;
/*  6:   */ 
/*  7:   */ public class ThreadFrame
/*  8:   */   extends Frame
/*  9:   */ {
/* 10:12 */   public static String FRAMETYPE = (String)RecognizedRepresentations.THREAD_THING;
/* 11:   */   private String subclass;
/* 12:   */   private String superclass;
/* 13:   */   private Entity thing;
/* 14:20 */   private BasicMemory memory = BasicMemory.getStaticMemory();
/* 15:   */   
/* 16:   */   public ThreadFrame(String subclass, String superclass)
/* 17:   */   {
/* 18:23 */     this.subclass = subclass;
/* 19:24 */     this.superclass = superclass;
/* 20:25 */     this.thing = new Entity();
/* 21:26 */     this.thing.addType(superclass);
/* 22:27 */     this.thing.addType(subclass);
/* 23:   */     
/* 24:   */ 
/* 25:30 */     this.memory.extendVia(this.thing, "thing");
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Entity getThing()
/* 29:   */   {
/* 30:35 */     return this.thing;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public Entity getThingRepresentation()
/* 34:   */   {
/* 35:39 */     return this.thing;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String toString()
/* 39:   */   {
/* 40:43 */     if (getThing() != null) {
/* 41:44 */       return getThing().toString();
/* 42:   */     }
/* 43:46 */     return "";
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.ThreadFrame
 * JD-Core Version:    0.7.0.1
 */