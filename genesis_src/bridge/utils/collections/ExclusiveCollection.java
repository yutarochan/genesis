package bridge.utils.collections;

import java.util.Collection;

public abstract interface ExclusiveCollection<T>
  extends Collection<T>
{
  public static final int STRICT = 0;
  public static final int FAMILY = 1;
  
  public abstract boolean testType(Object paramObject);
  
  public abstract Class getType();
  
  public abstract int getRestriction();
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.utils.collections.ExclusiveCollection
 * JD-Core Version:    0.7.0.1
 */