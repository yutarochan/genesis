package memory.soms;

import java.util.Set;
import memory.multithreading.Watcher;

public abstract interface Som<E>
{
  public abstract Set<E> getMemory();
  
  public abstract void add(Watcher paramWatcher);
  
  public abstract void add(E paramE);
  
  public abstract Set<E> neighbors(E paramE);
  
  public abstract Som<E> clone();
  
  public abstract boolean containsEquivalent(E paramE);
  
  public abstract double getDistance(E paramE1, E paramE2);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.Som
 * JD-Core Version:    0.7.0.1
 */