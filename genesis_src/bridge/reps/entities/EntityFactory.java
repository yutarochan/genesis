package bridge.reps.entities;

public abstract interface EntityFactory
{
  public abstract Entity newThing();
  
  public abstract Entity newThing(String paramString);
  
  public abstract Entity newThing(boolean paramBoolean, String paramString);
  
  public abstract Function newDerivative(Entity paramEntity);
  
  public abstract Function newDerivative(String paramString, Entity paramEntity);
  
  public abstract Function newDerivative(boolean paramBoolean, String paramString, Entity paramEntity);
  
  public abstract Relation newRelation(Entity paramEntity1, Entity paramEntity2);
  
  public abstract Relation newRelation(String paramString, Entity paramEntity1, Entity paramEntity2);
  
  public abstract Relation newRelation(boolean paramBoolean, String paramString, Entity paramEntity1, Entity paramEntity2);
  
  public abstract Sequence newSequence();
  
  public abstract Sequence newSequence(String paramString);
  
  public abstract Sequence newSequence(boolean paramBoolean, String paramString);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.reps.entities.EntityFactory
 * JD-Core Version:    0.7.0.1
 */