/*  1:   */ package bridge.reps.entities;
/*  2:   */ 
/*  3:   */ import bridge.modules.memory.BasicMemory;
/*  4:   */ 
/*  5:   */ public class EntityFactoryWithMemory
/*  6:   */   extends EntityFactoryAdapter
/*  7:   */ {
/*  8:   */   protected BasicMemory memory;
/*  9:   */   
/* 10:   */   public EntityFactoryWithMemory(BasicMemory memory)
/* 11:   */   {
/* 12:21 */     this(memory, EntityFactoryDefault.getInstance());
/* 13:   */   }
/* 14:   */   
/* 15:   */   public EntityFactoryWithMemory(BasicMemory memory, EntityFactory delegate)
/* 16:   */   {
/* 17:37 */     super(delegate);
/* 18:38 */     this.memory = memory;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public BasicMemory getMemory()
/* 22:   */   {
/* 23:46 */     return this.memory;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected Entity configure(Entity t)
/* 27:   */   {
/* 28:54 */     this.memory.store(t);
/* 29:55 */     return t;
/* 30:   */   }
/* 31:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.EntityFactoryWithMemory
 * JD-Core Version:    0.7.0.1
 */