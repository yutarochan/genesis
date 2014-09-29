package memory;

import bridge.reps.entities.Bundle;
import bridge.reps.entities.Thread;

public abstract interface ThreadMemory
{
  public abstract void add(String paramString, Thread paramThread);
  
  public abstract Bundle lookup(String paramString);
  
  public abstract Bundle lookup(String paramString1, String paramString2);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.ThreadMemory
 * JD-Core Version:    0.7.0.1
 */