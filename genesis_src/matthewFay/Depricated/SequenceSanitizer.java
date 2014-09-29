/*  1:   */ package matthewFay.Depricated;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import matchers.StandardMatcher;
/*  7:   */ import minilisp.LList;
/*  8:   */ import utils.PairOfEntities;
/*  9:   */ 
/* 10:   */ @Deprecated
/* 11:   */ public class SequenceSanitizer
/* 12:   */ {
/* 13:   */   public static boolean compareThings(Entity t1, Entity t2)
/* 14:   */   {
/* 15:19 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 16:   */     
/* 17:21 */     LList<PairOfEntities> t1t2 = matcher.match(t1, t2);
/* 18:   */     
/* 19:23 */     LList<PairOfEntities> t2t1 = matcher.match(t2, t1);
/* 20:25 */     if ((t1t2 == null) || (t2t1 == null)) {
/* 21:26 */       return false;
/* 22:   */     }
/* 23:27 */     if (t1t2.size() != t1t2.size()) {
/* 24:28 */       return false;
/* 25:   */     }
/* 26:29 */     if (t1t2.toString().equals(t2t1.toString())) {
/* 27:30 */       return true;
/* 28:   */     }
/* 29:31 */     return false;
/* 30:   */   }
/* 31:   */   
/* 32:   */   private static Sequence stripMilestones(Sequence s)
/* 33:   */   {
/* 34:35 */     for (int i = 0; i < s.getNumberOfChildren(); i++) {
/* 35:36 */       if (s.getElement(i).functionP("milestone"))
/* 36:   */       {
/* 37:37 */         s.removeElement(s.getElement(i));
/* 38:38 */         i--;
/* 39:   */       }
/* 40:   */     }
/* 41:41 */     return s;
/* 42:   */   }
/* 43:   */   
/* 44:   */   private static boolean compareThings(ArrayList<Entity> l1, ArrayList<Entity> l2)
/* 45:   */   {
/* 46:45 */     boolean allMatched = true;
/* 47:46 */     for (int i = 0; (i < l1.size()) && (allMatched); i++) {
/* 48:47 */       allMatched = compareThings((Entity)l1.get(i), (Entity)l2.get(i));
/* 49:   */     }
/* 50:49 */     return allMatched;
/* 51:   */   }
/* 52:   */   
/* 53:   */   private static Sequence sanitize(int pass, Sequence s)
/* 54:   */   {
/* 55:53 */     if (s.getNumberOfChildren() < pass * 2) {
/* 56:54 */       return s;
/* 57:   */     }
/* 58:56 */     ArrayList<Entity> setOne = new ArrayList();
/* 59:57 */     ArrayList<Entity> setTwo = new ArrayList();
/* 60:59 */     for (int i = 0; i <= s.getNumberOfChildren() - pass * 2; i++)
/* 61:   */     {
/* 62:60 */       setOne.clear();
/* 63:61 */       setTwo.clear();
/* 64:62 */       for (int iAdder = 0; iAdder < pass; iAdder++) {
/* 65:63 */         setOne.add(s.getElement(i + iAdder));
/* 66:   */       }
/* 67:65 */       for (int jAdder = 0; jAdder < pass; jAdder++) {
/* 68:66 */         setTwo.add(s.getElement(i + pass + jAdder));
/* 69:   */       }
/* 70:68 */       if (compareThings(setOne, setTwo))
/* 71:   */       {
/* 72:69 */         for (Entity t : setTwo) {
/* 73:70 */           s.removeElement(t);
/* 74:   */         }
/* 75:72 */         i--;
/* 76:   */       }
/* 77:   */     }
/* 78:76 */     return s;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public static Sequence sanitize(Sequence s, int numberOfPasses)
/* 82:   */   {
/* 83:80 */     Sequence sanitized = (Sequence)s.deepClone();
/* 84:81 */     sanitized = stripMilestones(sanitized);
/* 85:82 */     for (int pass = 1; pass <= numberOfPasses; pass++) {
/* 86:83 */       sanitized = sanitize(pass, sanitized);
/* 87:   */     }
/* 88:85 */     return sanitized;
/* 89:   */   }
/* 90:   */   
/* 91:   */   public static Sequence sanitize(Sequence s)
/* 92:   */   {
/* 93:89 */     return sanitize(s, 2);
/* 94:   */   }
/* 95:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.SequenceSanitizer
 * JD-Core Version:    0.7.0.1
 */