/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ public class EntityFactoryDefault
/*   4:    */   implements EntityFactory
/*   5:    */ {
/*   6: 17 */   private static EntityFactoryDefault _instance = null;
/*   7:    */   
/*   8:    */   public static EntityFactoryDefault getInstance()
/*   9:    */   {
/*  10: 31 */     if (_instance == null) {
/*  11: 32 */       synchronized (EntityFactoryDefault.class)
/*  12:    */       {
/*  13: 33 */         if (_instance == null) {
/*  14: 33 */           _instance = new EntityFactoryDefault();
/*  15:    */         }
/*  16:    */       }
/*  17:    */     }
/*  18: 36 */     return _instance;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Entity newThing()
/*  22:    */   {
/*  23: 43 */     return new Entity();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Entity newThing(String type)
/*  27:    */   {
/*  28: 50 */     return new Entity(type);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Entity newThing(boolean readOnly, String suffix)
/*  32:    */   {
/*  33: 57 */     return new Entity(readOnly, suffix);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Function newDerivative(Entity t)
/*  37:    */   {
/*  38: 64 */     return new Function(t);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public Function newDerivative(String string, Entity t)
/*  42:    */   {
/*  43: 71 */     return new Function(string, t);
/*  44:    */   }
/*  45:    */   
/*  46:    */   public Function newDerivative(boolean readOnly, String suffix, Entity subject)
/*  47:    */   {
/*  48: 79 */     return new Function(readOnly, suffix, subject);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Relation newRelation(Entity subject, Entity object)
/*  52:    */   {
/*  53: 86 */     return new Relation(subject, object);
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Relation newRelation(String type, Entity subject, Entity object)
/*  57:    */   {
/*  58: 93 */     return new Relation(type, subject, object);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public Relation newRelation(boolean readOnly, String suffix, Entity subject, Entity object)
/*  62:    */   {
/*  63:101 */     return new Relation(readOnly, suffix, subject, object);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Sequence newSequence()
/*  67:    */   {
/*  68:108 */     return new Sequence();
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Sequence newSequence(String type)
/*  72:    */   {
/*  73:115 */     return new Sequence(type);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public Sequence newSequence(boolean readOnly, String suffix)
/*  77:    */   {
/*  78:122 */     return new Sequence(readOnly, suffix);
/*  79:    */   }
/*  80:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.EntityFactoryDefault
 * JD-Core Version:    0.7.0.1
 */