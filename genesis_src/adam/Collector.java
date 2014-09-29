package adam;

public abstract interface Collector
{
  public abstract String getName();
  
  public abstract void call(Object paramObject, String paramString);
  
  public abstract Object callNormalMethod(String paramString, Object[] paramArrayOfObject);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adam.Collector
 * JD-Core Version:    0.7.0.1
 */