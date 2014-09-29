package bridge.modules.memory;

import bridge.reps.entities.Entity;

public abstract interface MemoryForget
{
  public abstract boolean forget(Entity paramEntity);
  
  public abstract boolean isForgettable(Entity paramEntity);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.modules.memory.MemoryForget
 * JD-Core Version:    0.7.0.1
 */