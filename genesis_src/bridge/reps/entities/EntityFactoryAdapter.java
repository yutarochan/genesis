/*   1:    */ package bridge.reps.entities;
/*   2:    */ 
/*   3:    */ public abstract class EntityFactoryAdapter
/*   4:    */   implements EntityFactory
/*   5:    */ {
/*   6:    */   protected EntityFactory delegate;
/*   7:    */   
/*   8:    */   public EntityFactoryAdapter()
/*   9:    */   {
/*  10: 25 */     this(EntityFactoryDefault.getInstance());
/*  11:    */   }
/*  12:    */   
/*  13:    */   public EntityFactoryAdapter(EntityFactory delegate)
/*  14:    */   {
/*  15: 40 */     this.delegate = delegate;
/*  16:    */   }
/*  17:    */   
/*  18:    */   protected abstract Entity configure(Entity paramEntity);
/*  19:    */   
/*  20:    */   protected Function configure(Function d)
/*  21:    */   {
/*  22: 46 */     return (Function)configure(d);
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected Relation configure(Relation d)
/*  26:    */   {
/*  27: 50 */     return (Relation)configure(d);
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected Sequence configure(Sequence d)
/*  31:    */   {
/*  32: 54 */     return (Sequence)configure(d);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Entity newThing()
/*  36:    */   {
/*  37: 61 */     return configure(this.delegate.newThing());
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Entity newThing(String type)
/*  41:    */   {
/*  42: 68 */     return configure(this.delegate.newThing(type));
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Entity newThing(boolean readOnly, String suffix)
/*  46:    */   {
/*  47: 75 */     return configure(this.delegate.newThing(readOnly, suffix));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Function newDerivative(Entity t)
/*  51:    */   {
/*  52: 82 */     return configure(this.delegate.newDerivative(t));
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Function newDerivative(String string, Entity t)
/*  56:    */   {
/*  57: 89 */     return configure(this.delegate.newDerivative(string, t));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Function newDerivative(boolean readOnly, String suffix, Entity subject)
/*  61:    */   {
/*  62: 97 */     return configure(this.delegate.newDerivative(readOnly, suffix, subject));
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Relation newRelation(Entity subject, Entity object)
/*  66:    */   {
/*  67:104 */     return configure(this.delegate.newRelation(subject, object));
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Relation newRelation(String type, Entity subject, Entity object)
/*  71:    */   {
/*  72:111 */     return configure(this.delegate.newRelation(type, subject, object));
/*  73:    */   }
/*  74:    */   
/*  75:    */   public Relation newRelation(boolean readOnly, String suffix, Entity subject, Entity object)
/*  76:    */   {
/*  77:119 */     return configure(this.delegate.newRelation(readOnly, suffix, subject, object));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Sequence newSequence()
/*  81:    */   {
/*  82:126 */     return configure(this.delegate.newSequence());
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Sequence newSequence(String type)
/*  86:    */   {
/*  87:133 */     return configure(this.delegate.newSequence(type));
/*  88:    */   }
/*  89:    */   
/*  90:    */   public Sequence newSequence(boolean readOnly, String suffix)
/*  91:    */   {
/*  92:140 */     return configure(this.delegate.newSequence(readOnly, suffix));
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.EntityFactoryAdapter
 * JD-Core Version:    0.7.0.1
 */