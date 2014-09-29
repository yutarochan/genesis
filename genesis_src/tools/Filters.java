/*   1:    */ package tools;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import java.util.Vector;
/*   7:    */ import links.words.BundleGenerator;
/*   8:    */ import matchers.StandardMatcher;
/*   9:    */ import minilisp.LList;
/*  10:    */ import storyProcessor.ConceptExpert;
/*  11:    */ import utils.Mark;
/*  12:    */ import utils.PairOfEntities;
/*  13:    */ 
/*  14:    */ public class Filters
/*  15:    */ {
/*  16:    */   public static Vector<Relation> findActorWhoseActionLeadsToEffect(Entity effect, Sequence sequence)
/*  17:    */   {
/*  18: 21 */     Vector<Entity> story = sequence.getElements();
/*  19: 22 */     Vector<Entity> inferences = keepOnlyInferences(story);
/*  20: 23 */     Vector<Relation> results = new Vector();
/*  21: 25 */     for (int i = 0; i < story.size(); i++)
/*  22:    */     {
/*  23: 28 */       Entity x = (Entity)story.get(i);
/*  24: 29 */       if (Predicates.isAction(x)) {
/*  25: 31 */         for (int r = i + 1; r < story.size(); r++)
/*  26:    */         {
/*  27: 32 */           Entity candidate = (Entity)story.get(r);
/*  28:    */           
/*  29: 34 */           LList<PairOfEntities> bindings = StandardMatcher.getBasicMatcher().match(effect, candidate);
/*  30: 40 */           if (bindings != null) {
/*  31: 42 */             if (ConceptExpert.connected(x, candidate, inferences) != null) {
/*  32: 43 */               results.add((Relation)x);
/*  33:    */             }
/*  34:    */           }
/*  35:    */         }
/*  36:    */       }
/*  37:    */     }
/*  38: 49 */     return results;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Vector<Relation> findActionsBy(Object object, Vector<Relation> actions)
/*  42:    */   {
/*  43:    */     String actor;
/*  44:    */     String actor;
/*  45: 54 */     if ((object instanceof Entity)) {
/*  46: 55 */       actor = ((Entity)object).getType();
/*  47:    */     } else {
/*  48: 58 */       actor = object.toString();
/*  49:    */     }
/*  50: 60 */     Vector<Relation> result = new Vector();
/*  51: 61 */     for (Relation e : actions) {
/*  52: 62 */       if (e.getSubject().getType().equals(actor)) {
/*  53: 63 */         result.addElement(e);
/*  54:    */       }
/*  55:    */     }
/*  56: 66 */     return result;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Vector<Relation> findHarmingActions(Sequence story)
/*  60:    */   {
/*  61: 70 */     Relation harm = getHarm();
/*  62: 71 */     return findActorWhoseActionLeadsToEffect(harm, story);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static Vector<Relation> findHelpingActions(Sequence story)
/*  66:    */   {
/*  67: 75 */     Entity actor = getPerson("xx");
/*  68: 76 */     Relation help = getHelp();
/*  69: 77 */     return findActorWhoseActionLeadsToEffect(help, story);
/*  70:    */   }
/*  71:    */   
/*  72:    */   private static Vector<Entity> keepOnlyInferences(Vector<Entity> story)
/*  73:    */   {
/*  74: 81 */     Vector<Entity> inferences = new Vector();
/*  75: 82 */     for (Entity e : story) {
/*  76: 83 */       if (Predicates.isCause(e)) {
/*  77: 84 */         inferences.addElement(e);
/*  78:    */       }
/*  79:    */     }
/*  80: 87 */     return inferences;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static Entity getPerson(String name)
/*  84:    */   {
/*  85: 91 */     Entity xx = new Entity(BundleGenerator.getBundle("person"));
/*  86: 92 */     xx.addType("name");
/*  87: 93 */     xx.addType(name);
/*  88: 94 */     return xx;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static Relation getHarm()
/*  92:    */   {
/*  93: 98 */     Relation r = Constructors.makeRoleFrame(getPerson("xx"), "harm", getPerson("yy"));
/*  94: 99 */     r.limitToRoot("action");
/*  95:100 */     return r;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static Relation getHelp()
/*  99:    */   {
/* 100:104 */     Relation r = Constructors.makeRoleFrame(getPerson("xx"), "help", getPerson("yy"));
/* 101:105 */     r.limitToRoot("action");
/* 102:106 */     return r;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public static void main(String[] ignore)
/* 106:    */   {
/* 107:110 */     Mark.say(
/* 108:111 */       new Object[] { getHarm().toXML() });
/* 109:    */   }
/* 110:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tools.Filters
 * JD-Core Version:    0.7.0.1
 */