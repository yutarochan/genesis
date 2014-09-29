/*  1:   */ package kevinWhite;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Thread;
/*  4:   */ import java.util.HashMap;
/*  5:   */ 
/*  6:   */ public class ConceptManager
/*  7:   */ {
/*  8:16 */   private HashMap<String, FasterLLConcept> conceptMap = new HashMap();
/*  9:   */   
/* 10:   */   private void addConcept(String name)
/* 11:   */   {
/* 12:27 */     TypeLattice tl = new TypeLattice();
/* 13:28 */     FasterLLConcept flc = new FasterLLConcept(tl, name);
/* 14:29 */     this.conceptMap.put(name, flc);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void addConcept(FasterLLConcept concept)
/* 18:   */   {
/* 19:33 */     this.conceptMap.put(concept.getName(), concept);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void updateConcept(String name, Thread th, boolean positive)
/* 23:   */   {
/* 24:43 */     FasterLLConcept flc = (FasterLLConcept)this.conceptMap.get(name);
/* 25:44 */     TypeLattice tl = flc.getLattice();
/* 26:45 */     tl.updateAncestry(th);
/* 27:46 */     if (positive) {
/* 28:47 */       flc.learnPositive((String)th.lastElement());
/* 29:   */     } else {
/* 30:50 */       flc.learnNegative((String)th.lastElement());
/* 31:   */     }
/* 32:   */   }
/* 33:   */   
/* 34:   */   public FasterLLConcept removeConcept(String name)
/* 35:   */   {
/* 36:55 */     return (FasterLLConcept)this.conceptMap.remove(name);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public FasterLLConcept getConcept(String name)
/* 40:   */   {
/* 41:59 */     if (!this.conceptMap.containsKey(name)) {
/* 42:60 */       addConcept(name);
/* 43:   */     }
/* 44:62 */     return (FasterLLConcept)this.conceptMap.get(name);
/* 45:   */   }
/* 46:   */   
/* 47:   */   public HashMap<String, FasterLLConcept> getConcepts()
/* 48:   */   {
/* 49:66 */     return this.conceptMap;
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.ConceptManager
 * JD-Core Version:    0.7.0.1
 */