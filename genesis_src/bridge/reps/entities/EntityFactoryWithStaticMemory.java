/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import bridge.modules.memory.BasicMemory;
/*  4:   */ 
/*  5:   */ public class EntityFactoryWithStaticMemory
/*  6:   */   extends EntityFactoryWithMemory
/*  7:   */ {
/*  8:16 */   private static EntityFactoryWithStaticMemory _instance = null;
/*  9:   */   
/* 10:   */   protected EntityFactoryWithStaticMemory()
/* 11:   */   {
/* 12:25 */     super(BasicMemory.getStaticMemory());
/* 13:   */   }
/* 14:   */   
/* 15:   */   public static EntityFactoryWithStaticMemory getInstance()
/* 16:   */   {
/* 17:32 */     if (_instance == null) {
/* 18:32 */       _instance = new EntityFactoryWithStaticMemory();
/* 19:   */     }
/* 20:33 */     return _instance;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public EntityFactoryWithStaticMemory(EntityFactory delegate)
/* 24:   */   {
/* 25:48 */     super(BasicMemory.getStaticMemory(), delegate);
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.EntityFactoryWithStaticMemory
 * JD-Core Version:    0.7.0.1
 */