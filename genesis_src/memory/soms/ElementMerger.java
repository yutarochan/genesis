package memory.soms;

import java.util.Set;

public abstract interface ElementMerger<T>
{
  public abstract Set<T> merge(T paramT, Set<T> paramSet);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.soms.ElementMerger
 * JD-Core Version:    0.7.0.1
 */