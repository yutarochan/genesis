/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import java.util.Vector;
/*  6:   */ import matchers.StandardMatcher;
/*  7:   */ import minilisp.LList;
/*  8:   */ 
/*  9:   */ public class StoryMethods
/* 10:   */ {
/* 11:   */   public static boolean isPHW()
/* 12:   */   {
/* 13:24 */     return (!Webstart.isWebStart()) && ("phw".equalsIgnoreCase(System.getProperty("user.name")));
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static boolean isInferred(Entity t, Sequence story)
/* 17:   */   {
/* 18:32 */     for (Entity element : story.getElements()) {
/* 19:33 */       if ((element.relationP("cause")) && 
/* 20:34 */         (t == element.getObject())) {
/* 21:35 */         return true;
/* 22:   */       }
/* 23:   */     }
/* 24:39 */     return false;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static Vector<Entity> distance(Entity s, Entity d, Sequence story)
/* 28:   */   {
/* 29:46 */     if ((s == null) || (d == null)) {
/* 30:47 */       return new Vector();
/* 31:   */     }
/* 32:49 */     Vector<Vector<Entity>> queue = new Vector();
/* 33:50 */     Vector<Entity> extendedList = new Vector();
/* 34:   */     
/* 35:52 */     Vector<Entity> initialPath = new Vector();
/* 36:53 */     initialPath.add(s);
/* 37:54 */     queue.add(initialPath);
/* 38:56 */     while (!queue.isEmpty())
/* 39:   */     {
/* 40:57 */       Vector<Entity> path = (Vector)queue.firstElement();
/* 41:58 */       queue.remove(0);
/* 42:59 */       Entity lastElement = (Entity)path.lastElement();
/* 43:67 */       if (d.hash().equals(lastElement.hash()))
/* 44:   */       {
/* 45:68 */         Mark.say(new Object[] {"Evidently", d.asString(), "matches", lastElement.asString(), lastElement.hash() });
/* 46:69 */         return path;
/* 47:   */       }
/* 48:71 */       if (!extendedList.contains(lastElement))
/* 49:   */       {
/* 50:75 */         extendedList.add(lastElement);
/* 51:78 */         for (Entity element : story.getElements()) {
/* 52:79 */           if ((element.relationP("cause")) || (element.relationP("explanation")))
/* 53:   */           {
/* 54:80 */             Vector<Entity> antecedents = element.getSubject().getElements();
/* 55:81 */             boolean match = false;
/* 56:82 */             for (Entity antecedent : antecedents)
/* 57:   */             {
/* 58:83 */               LList<PairOfEntities> matchings = StandardMatcher.getBasicMatcher().match(antecedent, lastElement);
/* 59:84 */               if (matchings != null) {
/* 60:85 */                 match = true;
/* 61:   */               }
/* 62:   */             }
/* 63:89 */             if (match)
/* 64:   */             {
/* 65:90 */               Vector<Entity> newPath = new Vector();
/* 66:91 */               newPath.addAll(path);
/* 67:92 */               newPath.add(element.getObject());
/* 68:93 */               queue.add(newPath);
/* 69:   */             }
/* 70:   */           }
/* 71:   */         }
/* 72:   */       }
/* 73:   */     }
/* 74:99 */     return new Vector();
/* 75:   */   }
/* 76:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.StoryMethods
 * JD-Core Version:    0.7.0.1
 */