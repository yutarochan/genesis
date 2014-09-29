/*  1:   */ package jingJian;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Relation;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ 
/*  7:   */ public class MentalModel
/*  8:   */ {
/*  9: 9 */   public ArrayList<Relation> rules = null;
/* 10:10 */   public ArrayList<Sequence> reflections = null;
/* 11:11 */   public ArrayList<Sequence> facts = null;
/* 12:   */   
/* 13:   */   public MentalModel(Sequence rules, Sequence reflections, Sequence facts)
/* 14:   */   {
/* 15:14 */     this.rules = new ArrayList();
/* 16:15 */     this.reflections = new ArrayList();
/* 17:16 */     this.facts = new ArrayList();
/* 18:17 */     addRules(rules);
/* 19:18 */     addReflections(reflections);
/* 20:19 */     addFacts(facts);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public MentalModel()
/* 24:   */   {
/* 25:23 */     this.rules = new ArrayList();
/* 26:24 */     this.reflections = new ArrayList();
/* 27:25 */     this.facts = new ArrayList();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public Sequence getRules()
/* 31:   */   {
/* 32:29 */     Sequence gotRules = new Sequence();
/* 33:30 */     for (Relation r : this.rules) {
/* 34:31 */       gotRules.addElement(r.cloneForResolver());
/* 35:   */     }
/* 36:33 */     return gotRules;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void addRule(Relation rule)
/* 40:   */   {
/* 41:37 */     this.rules.add((Relation)rule.cloneForResolver());
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void addRules(Sequence rules)
/* 45:   */   {
/* 46:41 */     for (int i = 0; i < rules.getNumberOfChildren(); i++) {
/* 47:42 */       addRule((Relation)rules.getElement(i));
/* 48:   */     }
/* 49:   */   }
/* 50:   */   
/* 51:   */   public Sequence getReflections()
/* 52:   */   {
/* 53:47 */     Sequence gotReflections = new Sequence();
/* 54:48 */     for (Sequence s : this.reflections) {
/* 55:49 */       gotReflections.addElement(s.cloneForResolver());
/* 56:   */     }
/* 57:51 */     return gotReflections;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public void addReflection(Sequence reflection)
/* 61:   */   {
/* 62:55 */     this.reflections.add((Sequence)reflection.cloneForResolver());
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void addReflections(Sequence reflections)
/* 66:   */   {
/* 67:59 */     for (int i = 0; i < reflections.getNumberOfChildren(); i++) {
/* 68:60 */       addReflection((Sequence)reflections.getElement(i));
/* 69:   */     }
/* 70:   */   }
/* 71:   */   
/* 72:   */   public Sequence getFacts()
/* 73:   */   {
/* 74:64 */     Sequence gotFacts = new Sequence();
/* 75:65 */     for (Sequence r : this.facts) {
/* 76:66 */       gotFacts.addElement(r.cloneForResolver());
/* 77:   */     }
/* 78:68 */     return gotFacts;
/* 79:   */   }
/* 80:   */   
/* 81:   */   public void addFact(Sequence fact)
/* 82:   */   {
/* 83:72 */     this.facts.add((Sequence)fact.cloneForResolver());
/* 84:   */   }
/* 85:   */   
/* 86:   */   public void addFacts(Sequence facts)
/* 87:   */   {
/* 88:76 */     for (int i = 0; i < facts.getNumberOfChildren(); i++) {
/* 89:77 */       addRule((Relation)facts.getElement(i));
/* 90:   */     }
/* 91:   */   }
/* 92:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     jingJian.MentalModel
 * JD-Core Version:    0.7.0.1
 */