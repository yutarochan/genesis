package matthewFay.StoryAlignment;

import matthewFay.Utilities.EntityHelper.MatchNode;

public abstract interface NodeScorer
{
  public abstract float score(EntityHelper.MatchNode paramMatchNode);
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryAlignment.NodeScorer
 * JD-Core Version:    0.7.0.1
 */