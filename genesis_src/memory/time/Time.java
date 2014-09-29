package memory.time;

import bridge.reps.entities.Entity;

public abstract interface Time
{
  public abstract void addBefore(Entity paramEntity1, Entity paramEntity2);
  
  public abstract void addMeets(Entity paramEntity1, Entity paramEntity2);
  
  public abstract void addStarts(Entity paramEntity1, Entity paramEntity2);
  
  public abstract void addFinishes(Entity paramEntity1, Entity paramEntity2);
  
  public abstract void addOverlaps(Entity paramEntity1, Entity paramEntity2);
  
  public abstract void addDuring(Entity paramEntity1, Entity paramEntity2);
  
  public abstract void addEquals(Entity paramEntity1, Entity paramEntity2);
  
  public abstract TimeRelation getRelation(Entity paramEntity1, Entity paramEntity2);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.time.Time
 * JD-Core Version:    0.7.0.1
 */