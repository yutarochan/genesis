package connections;

import java.util.ArrayList;

public abstract interface Network<B extends WiredBox>
{
  public abstract ArrayList<B> getBoxes();
  
  public abstract ArrayList<B> getTargets(B paramB);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.Network
 * JD-Core Version:    0.7.0.1
 */