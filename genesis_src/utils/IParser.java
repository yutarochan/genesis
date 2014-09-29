package utils;

import bridge.reps.entities.Sequence;

public abstract interface IParser
{
  public abstract Sequence parse(String paramString)
    throws Exception;
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.IParser
 * JD-Core Version:    0.7.0.1
 */