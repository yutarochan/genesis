/*  1:   */ package dylanHolmes;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import matchers.StandardMatcher;
/*  8:   */ import minilisp.LList;
/*  9:   */ import utils.PairOfEntities;
/* 10:   */ 
/* 11:   */ public class Goal
/* 12:   */ {
/* 13:   */   public String name;
/* 14:14 */   public Boolean preventative = Boolean.valueOf(false);
/* 15:   */   public Entity end;
/* 16:   */   public Entity prereqs;
/* 17:   */   public Entity means;
/* 18:   */   public LList<PairOfEntities> bindings;
/* 19:   */   public String successful;
/* 20:   */   
/* 21:   */   public static Entity pairWithNull(Entity e)
/* 22:   */   {
/* 23:23 */     return new Function("unmatched", e);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static Entity pairWithMatched(Entity e)
/* 27:   */   {
/* 28:27 */     return new Function("matched", e.getSubject());
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static Entity reTag(String s, Entity e)
/* 32:   */   {
/* 33:32 */     e.addType(s);
/* 34:33 */     return e;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public Goal emptyMatches()
/* 38:   */   {
/* 39:40 */     Entity tmp_end = pairWithNull(this.end);
/* 40:41 */     Entity tmp_prereqs = new Sequence();
/* 41:42 */     for (Entity e : this.prereqs.getElements()) {
/* 42:43 */       tmp_prereqs.addElement(pairWithNull(e));
/* 43:   */     }
/* 44:45 */     Entity tmp_means = pairWithNull(this.means);
/* 45:   */     
/* 46:47 */     return new Goal(this.name, tmp_end, tmp_prereqs, tmp_means);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public Entity match(Entity e)
/* 50:   */   {
/* 51:52 */     ArrayList<Entity> candidates = new ArrayList();
/* 52:53 */     candidates.add(this.means);
/* 53:54 */     candidates.addAll(this.prereqs.getElements());
/* 54:56 */     for (Entity c : candidates) {
/* 55:58 */       if (c.getType() == "unmatched")
/* 56:   */       {
/* 57:66 */         LList<PairOfEntities> bindings = StandardMatcher.getBasicMatcher().match(e, c.getSubject());
/* 58:67 */         if (bindings != null)
/* 59:   */         {
/* 60:68 */           this.bindings = bindings;
/* 61:69 */           c = reTag("matched", c);
/* 62:70 */           return c;
/* 63:   */         }
/* 64:   */       }
/* 65:   */     }
/* 66:78 */     return null;
/* 67:   */   }
/* 68:   */   
/* 69:   */   public Goal(Entity goal, Entity prereqs, Entity means)
/* 70:   */   {
/* 71:83 */     this.end = goal;
/* 72:84 */     this.prereqs = prereqs;
/* 73:85 */     this.means = means;
/* 74:   */   }
/* 75:   */   
/* 76:   */   public Goal(String name, Entity goal, Entity prereqs, Entity means)
/* 77:   */   {
/* 78:88 */     this.name = name;
/* 79:89 */     this.end = goal;
/* 80:90 */     this.prereqs = prereqs;
/* 81:91 */     this.means = means;
/* 82:   */   }
/* 83:   */   
/* 84:   */   public static boolean containsNamedGoal(ArrayList<Goal> alts, String s)
/* 85:   */   {
/* 86:95 */     for (Goal g : alts) {
/* 87:96 */       if (g.name == s) {
/* 88:96 */         return true;
/* 89:   */       }
/* 90:   */     }
/* 91:98 */     return false;
/* 92:   */   }
/* 93:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.Goal
 * JD-Core Version:    0.7.0.1
 */