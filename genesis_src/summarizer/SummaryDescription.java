/*   1:    */ package summarizer;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Set;
/*   8:    */ import storyProcessor.ReflectionDescription;
/*   9:    */ 
/*  10:    */ public class SummaryDescription
/*  11:    */ {
/*  12: 16 */   Set<Entity> randomSummary = new HashSet();
/*  13: 18 */   Set<Entity> completeStory = new HashSet();
/*  14: 20 */   Set<Entity> essential = new HashSet();
/*  15: 22 */   Set<Entity> connected = new HashSet();
/*  16: 24 */   Set<Entity> concept = new HashSet();
/*  17: 26 */   Set<Entity> dominant = new HashSet();
/*  18: 28 */   Set<Entity> special = new HashSet();
/*  19: 30 */   List<Entity> questions = new ArrayList();
/*  20:    */   List<ReflectionDescription> conceptDescriptions;
/*  21:    */   
/*  22:    */   public Set<Entity> getRandomSummary()
/*  23:    */   {
/*  24: 35 */     return this.randomSummary;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void setRandom(Set<Entity> random)
/*  28:    */   {
/*  29: 39 */     this.randomSummary = random;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public Set<Entity> getCompleteStory()
/*  33:    */   {
/*  34: 43 */     return this.completeStory;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setCompleteStory(Set<Entity> x)
/*  38:    */   {
/*  39: 47 */     this.completeStory = x;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Set<Entity> getEssential()
/*  43:    */   {
/*  44: 51 */     return this.essential;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setEssential(Set<Entity> x)
/*  48:    */   {
/*  49: 55 */     this.essential = x;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Set<Entity> getConnected()
/*  53:    */   {
/*  54: 59 */     return this.connected;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setConnected(Set<Entity> x)
/*  58:    */   {
/*  59: 63 */     this.connected = x;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Set<Entity> getConcept()
/*  63:    */   {
/*  64: 67 */     return this.concept;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setConcept(Set<Entity> x)
/*  68:    */   {
/*  69: 71 */     this.concept = x;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Set<Entity> getDominant()
/*  73:    */   {
/*  74: 75 */     return this.dominant;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setDominant(Set<Entity> dominant)
/*  78:    */   {
/*  79: 79 */     this.dominant = dominant;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Set<Entity> getSpecial()
/*  83:    */   {
/*  84: 83 */     return this.special;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setSpecial(Set<Entity> x)
/*  88:    */   {
/*  89: 87 */     this.special = x;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public HashSet<Entity> getQuestions()
/*  93:    */   {
/*  94: 91 */     HashSet<Entity> result = new HashSet();
/*  95: 92 */     result.addAll(this.questions);
/*  96: 93 */     return result;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setQuestions(List<Entity> questions)
/* 100:    */   {
/* 101: 97 */     this.questions = questions;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void clear()
/* 105:    */   {
/* 106:101 */     getRandomSummary().clear();
/* 107:    */     
/* 108:103 */     getEssential().clear();
/* 109:    */     
/* 110:105 */     getConnected().clear();
/* 111:    */     
/* 112:107 */     getConcept().clear();
/* 113:    */     
/* 114:109 */     getDominant().clear();
/* 115:110 */     getQuestions().clear();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setConceptDescriptions(List<ReflectionDescription> conceptDescriptions)
/* 119:    */   {
/* 120:115 */     this.conceptDescriptions = conceptDescriptions;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public List<ReflectionDescription> getConceptDescriptions()
/* 124:    */   {
/* 125:119 */     return this.conceptDescriptions;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     summarizer.SummaryDescription
 * JD-Core Version:    0.7.0.1
 */