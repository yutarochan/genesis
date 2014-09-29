package memory;

import bridge.reps.entities.Entity;
import java.util.List;

public abstract interface MemHub
{
  public abstract MemHub getMemHub();
  
  public abstract void add(Entity paramEntity);
  
  public abstract void remove(Entity paramEntity);
  
  public abstract void reset();
  
  public abstract List<Entity> getNeighbors(Entity paramEntity);
  
  public abstract List<Entity> getNearestNeighbors(Entity paramEntity, int paramInt);
  
  public abstract boolean contains(Entity paramEntity);
  
  public abstract int getFrequency(Entity paramEntity);
  
  public abstract List<Entity> makePredictions(Entity paramEntity);
  
  public abstract boolean checkCorrespondence(Entity paramEntity1, Entity paramEntity2);
  
  public abstract List<Entity> getDescription(Entity paramEntity);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.MemHub
 * JD-Core Version:    0.7.0.1
 */