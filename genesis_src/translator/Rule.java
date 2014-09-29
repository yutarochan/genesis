/*  1:   */ package translator;
/*  2:   */ 
/*  3:   */ public class Rule
/*  4:   */ {
/*  5:   */   private String name;
/*  6:   */   BasicRule basicRule;
/*  7:   */   
/*  8:   */   public Rule() {}
/*  9:   */   
/* 10:   */   public Rule(String name)
/* 11:   */   {
/* 12:19 */     this.name = name;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public Rule(BasicRule basicRule)
/* 16:   */   {
/* 17:23 */     addRunnable(basicRule);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getName()
/* 21:   */   {
/* 22:27 */     return this.name;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public String toString()
/* 26:   */   {
/* 27:31 */     return getName();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void addRunnable(BasicRule rule)
/* 31:   */   {
/* 32:38 */     this.name = rule.getClass().getSimpleName();
/* 33:39 */     this.basicRule = rule;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public BasicRule getRunnable()
/* 37:   */   {
/* 38:43 */     return this.basicRule;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     translator.Rule
 * JD-Core Version:    0.7.0.1
 */