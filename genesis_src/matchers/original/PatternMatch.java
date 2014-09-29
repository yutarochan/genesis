/*  1:   */ package matchers.original;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.util.Collection;
/*  5:   */ 
/*  6:   */ public class PatternMatch
/*  7:   */ {
/*  8:   */   public static boolean match(Entity newThing, Entity oldThing)
/*  9:   */   {
/* 10:15 */     if ((newThing.entityP()) && (oldThing.entityP()) && (matchTypesAndSign(newThing, oldThing))) {
/* 11:16 */       return newThing == oldThing;
/* 12:   */     }
/* 13:18 */     if ((newThing.functionP()) && (oldThing.functionP()) && (matchTypesAndSign(newThing, oldThing))) {
/* 14:19 */       return match(newThing.getSubject(), oldThing.getSubject());
/* 15:   */     }
/* 16:21 */     if ((newThing.relationP()) && (oldThing.relationP()) && (matchTypesAndSign(newThing, oldThing))) {
/* 17:22 */       return (match(newThing.getSubject(), oldThing.getSubject())) && (match(newThing.getObject(), oldThing.getObject()));
/* 18:   */     }
/* 19:24 */     if ((newThing.sequenceP()) && (oldThing.sequenceP()) && (matchTypesAndSign(newThing, oldThing)))
/* 20:   */     {
/* 21:25 */       Collection<Entity> newElements = newThing.getElements();
/* 22:26 */       Collection<Entity> oldElements = newThing.getElements();
/* 23:27 */       if (newElements.size() != oldElements.size()) {
/* 24:28 */         return false;
/* 25:   */       }
/* 26:30 */       for (Entity newElement : newElements)
/* 27:   */       {
/* 28:31 */         boolean result = false;
/* 29:32 */         for (Entity oldElement : oldElements) {
/* 30:33 */           if (match(newElement, oldElement))
/* 31:   */           {
/* 32:34 */             result = true;
/* 33:35 */             break;
/* 34:   */           }
/* 35:   */         }
/* 36:38 */         if (!result) {
/* 37:39 */           return false;
/* 38:   */         }
/* 39:   */       }
/* 40:43 */       return true;
/* 41:   */     }
/* 42:45 */     return false;
/* 43:   */   }
/* 44:   */   
/* 45:   */   private static boolean matchTypesAndSign(Entity newThing, Entity oldThing)
/* 46:   */   {
/* 47:50 */     return false;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.original.PatternMatch
 * JD-Core Version:    0.7.0.1
 */