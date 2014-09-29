package bridge.infrastructure.wires;

public abstract interface WireListener
{
  public abstract void wireStartTransmitting(Wire paramWire, Object paramObject);
  
  public abstract void wireDoneTransmitting(Wire paramWire, Object paramObject);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.infrastructure.wires.WireListener
 * JD-Core Version:    0.7.0.1
 */