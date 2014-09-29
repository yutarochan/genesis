package m2;

import bridge.reps.entities.Entity;
import connections.WiredBox;
import java.util.List;
import java.util.Set;

public abstract interface Mem
  extends WiredBox
{
  public abstract void input(Entity paramEntity);
  
  public abstract int frequency(Entity paramEntity);
  
  public abstract List<Entity> neighbors(Entity paramEntity);
  
  public abstract List<Entity> nearNeighbors(Entity paramEntity);
  
  public abstract int getMissDistance(Entity paramEntity);
  
  public abstract boolean isPossible(Entity paramEntity);
  
  public abstract Set<Entity> getContext(Entity paramEntity);
  
  public abstract Entity getMostRecentRep(Entity paramEntity, Object paramObject);
  
  public abstract void outputAll();
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.Mem
 * JD-Core Version:    0.7.0.1
 */