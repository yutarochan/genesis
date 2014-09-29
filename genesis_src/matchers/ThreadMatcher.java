/*   1:    */ package matchers;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Thread;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Vector;
/*   8:    */ import matchers.representations.ThreadMatchResult;
/*   9:    */ 
/*  10:    */ public class ThreadMatcher
/*  11:    */ {
/*  12:    */   public static enum MatchMode
/*  13:    */   {
/*  14: 24 */     BASIC,  SCORE;
/*  15:    */   }
/*  16:    */   
/*  17: 27 */   public MatchMode patternMatchMode = MatchMode.BASIC;
/*  18: 29 */   public boolean requireIdentityMatch = false;
/*  19: 30 */   public boolean two_way_match = false;
/*  20: 32 */   public float score_cutoff = 0.1F;
/*  21:    */   
/*  22:    */   public void useScoreMatching()
/*  23:    */   {
/*  24: 34 */     this.patternMatchMode = MatchMode.SCORE;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void useIdentityMatching()
/*  28:    */   {
/*  29: 38 */     this.requireIdentityMatch = true;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public ThreadMatchResult match(Entity element1, Entity element2)
/*  33:    */   {
/*  34: 42 */     return match(element1.getPrimedThread(), element2.getPrimedThread());
/*  35:    */   }
/*  36:    */   
/*  37:    */   public ThreadMatchResult match(Thread pattern_thread, Thread datum_thread)
/*  38:    */   {
/*  39: 54 */     ThreadMatchResult result = new ThreadMatchResult(pattern_thread, datum_thread);
/*  40: 56 */     if ((pattern_thread == null) || (datum_thread == null)) {
/*  41: 57 */       return result;
/*  42:    */     }
/*  43: 60 */     if ((pattern_thread.getType().equalsIgnoreCase("i")) && (!datum_thread.getType().equalsIgnoreCase("i"))) {
/*  44: 61 */       return result;
/*  45:    */     }
/*  46: 64 */     switch (this.patternMatchMode)
/*  47:    */     {
/*  48:    */     case BASIC: 
/*  49: 67 */       if ((pattern_thread.contains("anything")) || (datum_thread.contains("anything")))
/*  50:    */       {
/*  51: 68 */         result.score = 1.0D;
/*  52: 69 */         result.match = true;
/*  53:    */       }
/*  54: 72 */       else if (subset_match(pattern_thread, datum_thread))
/*  55:    */       {
/*  56: 73 */         result.score = 1.0D;
/*  57: 74 */         result.match = true;
/*  58:    */       }
/*  59: 75 */       else if ((this.two_way_match) && 
/*  60: 76 */         (subset_match(datum_thread, pattern_thread)))
/*  61:    */       {
/*  62: 77 */         result.score = 1.0D;
/*  63: 78 */         result.match = true;
/*  64:    */       }
/*  65: 82 */       boolean pattern_named = (pattern_thread.size() >= 2) && (((String)pattern_thread.get(pattern_thread.size() - 2)).equals("name"));
/*  66: 83 */       boolean datum_named = (datum_thread.size() >= 2) && (((String)datum_thread.get(datum_thread.size() - 2)).equals("name"));
/*  67: 84 */       if ((pattern_named) && (datum_named) && 
/*  68: 85 */         (((String)pattern_thread.get(pattern_thread.size() - 1)).equalsIgnoreCase((String)datum_thread.get(datum_thread.size() - 1)))) {
/*  69: 86 */         result.identityMatch = true;
/*  70:    */       }
/*  71: 89 */       if ((this.requireIdentityMatch) && 
/*  72: 90 */         (!result.identityMatch) && ((pattern_named) || (datum_named)))
/*  73:    */       {
/*  74: 91 */         result.score = -1.0D;
/*  75: 92 */         result.match = false;
/*  76:    */       }
/*  77: 95 */       break;
/*  78:    */     case SCORE: 
/*  79: 98 */       result.minLength = Math.min(pattern_thread.size(), datum_thread.size());
/*  80: 99 */       result.maxLength = Math.max(pattern_thread.size(), datum_thread.size());
/*  81:    */       
/*  82:101 */       List<String> telts1 = new ArrayList(pattern_thread);
/*  83:102 */       List<String> telts2 = new ArrayList(datum_thread);
/*  84:    */       
/*  85:104 */       result.matches = countMatches(pattern_thread, datum_thread);
/*  86:105 */       if (result.maxLength > 0) {
/*  87:106 */         result.score = score(pattern_thread, datum_thread);
/*  88:    */       }
/*  89:108 */       if (result.score > this.score_cutoff) {
/*  90:108 */         result.match = true;
/*  91:    */       }
/*  92:    */       break;
/*  93:    */     }
/*  94:111 */     return result;
/*  95:    */   }
/*  96:    */   
/*  97:    */   private boolean subset_match(Thread pattern, Thread datum)
/*  98:    */   {
/*  99:122 */     Vector<String> pattern_copy = new Vector(pattern);
/* 100:123 */     Vector<String> datum_copy = new Vector(datum);
/* 101:124 */     if ((pattern_copy.contains("name")) || (datum_copy.contains("name")))
/* 102:    */     {
/* 103:125 */       pattern_copy.remove(pattern_copy.size() - 1);
/* 104:126 */       datum_copy.remove(datum_copy.size() - 1);
/* 105:    */     }
/* 106:128 */     while (pattern_copy.contains("name")) {
/* 107:129 */       pattern_copy.remove(pattern_copy.size() - 1);
/* 108:    */     }
/* 109:131 */     while (datum_copy.contains("name")) {
/* 110:132 */       datum_copy.remove(datum_copy.size() - 1);
/* 111:    */     }
/* 112:134 */     if (datum_copy.containsAll(pattern_copy)) {
/* 113:135 */       return true;
/* 114:    */     }
/* 115:137 */     return false;
/* 116:    */   }
/* 117:    */   
/* 118:    */   private int countMatches(Thread thread1, Thread thread2)
/* 119:    */   {
/* 120:141 */     int l1 = thread1.size();
/* 121:142 */     int l2 = thread2.size();
/* 122:144 */     for (int matches = 0; matches < Math.min(l1, l2); matches++) {
/* 123:145 */       if (!((String)thread1.get(matches)).equalsIgnoreCase((String)thread2.get(matches))) {
/* 124:    */         break;
/* 125:    */       }
/* 126:    */     }
/* 127:150 */     return matches;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private double score(Thread thread1, Thread thread2)
/* 131:    */   {
/* 132:155 */     int matches = countMatches(thread1, thread2);
/* 133:156 */     int l1 = thread1.size();
/* 134:157 */     int l2 = thread2.size();
/* 135:158 */     return Math.pow(matches, 2.0D) / (l1 * l2);
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matchers.ThreadMatcher
 * JD-Core Version:    0.7.0.1
 */