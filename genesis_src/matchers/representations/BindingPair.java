/*  1:   */ package matchers.representations;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import java.util.List;
/*  6:   */ import utils.PairOfEntities;
/*  7:   */ 
/*  8:   */ public class BindingPair
/*  9:   */ {
/* 10:11 */   private List<Entity> entitySet = new ArrayList();
/* 11:13 */   private boolean allowed = true;
/* 12:14 */   private double threadScore = 0.0D;
/* 13:   */   
/* 14:   */   public double getScore()
/* 15:   */   {
/* 16:16 */     return this.threadScore;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public BindingPair(Entity entity1, Entity entity2, double score)
/* 20:   */   {
/* 21:20 */     this.entitySet.add(entity1);
/* 22:21 */     this.entitySet.add(entity2);
/* 23:22 */     this.threadScore = score;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public BindingPair(boolean allowed, Entity entity1, Entity entity2, double score)
/* 27:   */   {
/* 28:26 */     this.allowed = allowed;
/* 29:27 */     this.entitySet.add(entity1);
/* 30:28 */     this.entitySet.add(entity2);
/* 31:29 */     this.threadScore = score;
/* 32:   */   }
/* 33:   */   
/* 34:   */   @Deprecated
/* 35:   */   public BindingPair(PairOfEntities pair)
/* 36:   */   {
/* 37:34 */     this.entitySet.add(pair.getPattern());
/* 38:35 */     this.entitySet.add(pair.getDatum());
/* 39:   */   }
/* 40:   */   
/* 41:   */   @Deprecated
/* 42:   */   public PairOfEntities toPairOfEntities()
/* 43:   */   {
/* 44:40 */     return new PairOfEntities(getPattern(), getDatum());
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Entity getPattern()
/* 48:   */   {
/* 49:44 */     return (Entity)this.entitySet.get(0);
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Entity getDatum()
/* 53:   */   {
/* 54:48 */     return (Entity)this.entitySet.get(1);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public boolean getAllowed()
/* 58:   */   {
/* 59:52 */     return this.allowed;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public Entity get(int i)
/* 63:   */   {
/* 64:56 */     if (this.entitySet.size() > i) {
/* 65:57 */       return (Entity)this.entitySet.get(i);
/* 66:   */     }
/* 67:59 */     return null;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public List<Entity> getEntities(int i)
/* 71:   */   {
/* 72:63 */     return this.entitySet;
/* 73:   */   }
/* 74:   */   
/* 75:   */   public int size()
/* 76:   */   {
/* 77:67 */     return this.entitySet.size();
/* 78:   */   }
/* 79:   */   
/* 80:   */   public boolean equals(Object o)
/* 81:   */   {
/* 82:71 */     if ((o instanceof BindingPair))
/* 83:   */     {
/* 84:72 */       BindingPair binding = (BindingPair)o;
/* 85:73 */       if (size() != binding.size()) {
/* 86:74 */         return false;
/* 87:   */       }
/* 88:75 */       for (int i = 0; i < size(); i++) {
/* 89:76 */         if (!get(i).equals(binding.get(i))) {
/* 90:77 */           return false;
/* 91:   */         }
/* 92:   */       }
/* 93:79 */       return true;
/* 94:   */     }
/* 95:81 */     return false;
/* 96:   */   }
/* 97:   */   
/* 98:   */   public String toString()
/* 99:   */   {
/* :0:85 */     String str = "{ ";
/* :1:86 */     str = str + "'binding':[";
/* :2:87 */     str = str + "'" + get(0) + "', ";
/* :3:88 */     str = str + "'" + get(1) + "'], ";
/* :4:89 */     str = str + "'allowed':" + this.allowed + ", ";
/* :5:90 */     str = str + this.threadScore + " ";
/* :6:91 */     str = str + "}";
/* :7:92 */     return str;
/* :8:   */   }
/* :9:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.representations.BindingPair
 * JD-Core Version:    0.7.0.1
 */