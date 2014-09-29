/*   1:    */ package groups;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public class Equivalence
/*  10:    */   extends Entity
/*  11:    */   implements Group
/*  12:    */ {
/*  13: 16 */   public static String EQUIVNAME = "equivalence";
/*  14: 17 */   private HashSet<Entity> elts = new HashSet();
/*  15:    */   
/*  16:    */   public Equivalence() {}
/*  17:    */   
/*  18:    */   public Equivalence(String type)
/*  19:    */   {
/*  20: 24 */     super(type);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public boolean addElt(Entity elt)
/*  24:    */   {
/*  25: 27 */     return this.elts.add(elt);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean addRel(Relation rel)
/*  29:    */   {
/*  30: 36 */     if (in(rel.getSubject())) {
/*  31: 37 */       return addElt(rel.getObject());
/*  32:    */     }
/*  33: 39 */     if (in(rel.getObject())) {
/*  34: 40 */       return addElt(rel.getSubject());
/*  35:    */     }
/*  36: 42 */     return false;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public HashSet<Entity> getElts()
/*  40:    */   {
/*  41: 45 */     return this.elts;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public HashSet<Relation> getRelationsInvolving(Entity elt)
/*  45:    */   {
/*  46: 48 */     return getRels();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public HashSet<Relation> getRels()
/*  50:    */   {
/*  51: 51 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean in(Entity elt)
/*  55:    */   {
/*  56: 54 */     return this.elts.contains(elt);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public boolean in(Relation rel)
/*  60:    */   {
/*  61: 61 */     return (in(rel.getSubject())) && (in(rel.getObject()));
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean removeElt(Entity elt)
/*  65:    */   {
/*  66: 64 */     return this.elts.remove(elt);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean removeRel(Relation rel)
/*  70:    */   {
/*  71: 70 */     return removeElt(rel.getObject());
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String whatGroup()
/*  75:    */   {
/*  76: 73 */     return EQUIVNAME;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public boolean addAll(Set<Entity> elts)
/*  80:    */   {
/*  81: 77 */     return elts.addAll(elts);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Equivalence merge(Equivalence e)
/*  85:    */   {
/*  86: 81 */     Equivalence result = new Equivalence();
/*  87: 82 */     result.addAll(getElts());
/*  88: 83 */     result.addAll(e.getElts());
/*  89: 84 */     return result;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List<Entity> getAllComponents()
/*  93:    */   {
/*  94: 88 */     List<Entity> result = super.getAllComponents();
/*  95: 89 */     result.addAll(this.elts);
/*  96: 90 */     return result;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean isEqual(Object o)
/* 100:    */   {
/* 101: 94 */     if ((o instanceof Equivalence))
/* 102:    */     {
/* 103: 95 */       Equivalence e = (Equivalence)o;
/* 104: 96 */       HashSet<Entity> eElts = e.getElts();
/* 105: 97 */       HashSet<Entity> thisElts = getElts();
/* 106: 98 */       for (Entity t : eElts)
/* 107:    */       {
/* 108: 99 */         Entity equivThing = Graph.equalHelper(t, thisElts);
/* 109:100 */         if (equivThing == null) {
/* 110:101 */           return false;
/* 111:    */         }
/* 112:103 */         thisElts.remove(equivThing);
/* 113:    */       }
/* 114:105 */       if (!thisElts.isEmpty()) {
/* 115:106 */         return false;
/* 116:    */       }
/* 117:108 */       return super.isEqual(o);
/* 118:    */     }
/* 119:110 */     return false;
/* 120:    */   }
/* 121:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     groups.Equivalence
 * JD-Core Version:    0.7.0.1
 */