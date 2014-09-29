package connections;

import java.util.Observable;

public abstract class BasicNetwork<B extends WiredBox>
  extends Observable
  implements Network<B>, WiredBox
{}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.BasicNetwork
 * JD-Core Version:    0.7.0.1
 */