/*   1:    */ package matchers.representations;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import matchers.BindingValidator;
/*   6:    */ import minilisp.LList;
/*   7:    */ import utils.PairOfEntities;
/*   8:    */ 
/*   9:    */ public class EntityMatchResult
/*  10:    */ {
/*  11: 11 */   public List<BindingPair> bindings = new ArrayList();
/*  12: 12 */   public double score = -1.0D;
/*  13: 13 */   public boolean structureMatch = false;
/*  14: 14 */   public boolean semanticMatch = false;
/*  15: 15 */   public boolean inversion = false;
/*  16:    */   
/*  17:    */   public boolean isMatch()
/*  18:    */   {
/*  19: 18 */     return (this.structureMatch) && (this.semanticMatch) && (!this.inversion);
/*  20:    */   }
/*  21:    */   
/*  22:    */   public boolean isInversion()
/*  23:    */   {
/*  24: 22 */     return (this.structureMatch) && (this.semanticMatch) && (this.inversion);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public EntityMatchResult() {}
/*  28:    */   
/*  29:    */   public EntityMatchResult(double score, boolean inversion, boolean structureMatch, List<BindingPair> bindings)
/*  30:    */   {
/*  31: 30 */     this.bindings.addAll(bindings);
/*  32: 31 */     this.score = score;
/*  33: 32 */     this.inversion = inversion;
/*  34: 33 */     this.structureMatch = structureMatch;
/*  35: 35 */     if (score < 0.0D) {
/*  36: 36 */       this.semanticMatch = false;
/*  37:    */     } else {
/*  38: 38 */       this.semanticMatch = true;
/*  39:    */     }
/*  40: 40 */     if ((!isMatch()) && (!isInversion())) {
/*  41: 41 */       this.bindings.clear();
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public EntityMatchResult(boolean match, double score, boolean inversion, boolean structureMatch, List<BindingPair> bindings)
/*  46:    */   {
/*  47: 45 */     this.bindings.addAll(bindings);
/*  48: 46 */     this.score = score;
/*  49: 47 */     this.inversion = inversion;
/*  50: 48 */     this.structureMatch = structureMatch;
/*  51: 49 */     this.semanticMatch = match;
/*  52: 51 */     if (score < 0.0D) {
/*  53: 52 */       this.semanticMatch = false;
/*  54:    */     }
/*  55: 54 */     if ((!isMatch()) && (!isInversion())) {
/*  56: 55 */       this.bindings.clear();
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public String toString()
/*  61:    */   {
/*  62: 60 */     String str = "";
/*  63: 61 */     str = str + "(match = " + this.semanticMatch + ")\n";
/*  64: 62 */     str = str + "(inversion = " + this.inversion + ")\n";
/*  65: 63 */     str = str + "(score: " + this.score + ")\n";
/*  66: 64 */     str = str + "(structureMatch: " + this.structureMatch + ")\n";
/*  67: 65 */     str = str + "(bindings:\n";
/*  68: 66 */     if (this.bindings != null) {
/*  69: 67 */       for (BindingPair pair : this.bindings) {
/*  70: 68 */         str = str + pair + "\n";
/*  71:    */       }
/*  72:    */     } else {
/*  73: 70 */       str = str + "null\n";
/*  74:    */     }
/*  75: 72 */     str = str + ")\n";
/*  76: 73 */     str = str + "(match = " + this.semanticMatch + ")";
/*  77: 74 */     return str;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public LList<PairOfEntities> toLList()
/*  81:    */   {
/*  82: 79 */     if (isMatch()) {
/*  83: 80 */       return BindingValidator.convertToLList(this.bindings);
/*  84:    */     }
/*  85: 82 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public LList<PairOfEntities> toNegationLList()
/*  89:    */   {
/*  90: 87 */     if (isInversion()) {
/*  91: 88 */       return BindingValidator.convertToLList(this.bindings);
/*  92:    */     }
/*  93: 90 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void validateBindings()
/*  97:    */   {
/*  98: 95 */     BindingValidator bv = new BindingValidator();
/*  99: 96 */     this.bindings = bv.validateBindings(this.bindings);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void validateBindings(LList<PairOfEntities> constraints)
/* 103:    */   {
/* 104:100 */     BindingValidator bv = new BindingValidator();
/* 105:101 */     this.bindings = bv.validateBindings(this.bindings, constraints);
/* 106:    */   }
/* 107:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.representations.EntityMatchResult
 * JD-Core Version:    0.7.0.1
 */