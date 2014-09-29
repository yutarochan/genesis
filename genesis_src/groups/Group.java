package groups;

import bridge.reps.entities.Entity;
import bridge.reps.entities.Relation;
import java.util.AbstractCollection;
import java.util.List;

public abstract interface Group
{
  public abstract String whatGroup();
  
  public abstract AbstractCollection<Entity> getElts();
  
  public abstract AbstractCollection<Relation> getRels();
  
  public abstract AbstractCollection<Relation> getRelationsInvolving(Entity paramEntity);
  
  public abstract boolean addElt(Entity paramEntity);
  
  public abstract boolean addRel(Relation paramRelation);
  
  public abstract boolean in(Entity paramEntity);
  
  public abstract boolean in(Relation paramRelation);
  
  public abstract boolean removeElt(Entity paramEntity);
  
  public abstract boolean removeRel(Relation paramRelation);
  
  public abstract boolean isEqual(Object paramObject);
  
  public abstract List<Entity> getAllComponents();
}


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     groups.Group
 * JD-Core Version:    0.7.0.1
 */