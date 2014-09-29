/*   1:    */ package carynKrakauer.generatedPatterns;
/*   2:    */ 
/*   3:    */ import bridge.utils.Pair;
/*   4:    */ import carynKrakauer.ReflectionLevelMemory;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.HashSet;
/*   9:    */ 
/*  10:    */ public class ConceptPatternMatchWrapper
/*  11:    */ {
/*  12:    */   private double value;
/*  13:    */   private ArrayList<ConceptPattern> matches;
/*  14:    */   private ArrayList<String> matchesString;
/*  15:    */   private HashMap<String, ArrayList<Pair<ConceptPattern, ConceptPattern>>> matchPairs;
/*  16:    */   int size;
/*  17:    */   
/*  18:    */   public ConceptPatternMatchWrapper(double value, ArrayList<ConceptPattern> matches, ArrayList<ConceptPattern> story1PlotUnits, ArrayList<ConceptPattern> story2PlotUnits)
/*  19:    */   {
/*  20: 29 */     this.value = value;
/*  21: 30 */     this.matches = matches;
/*  22: 32 */     if (story1PlotUnits.size() > 0) {
/*  23: 33 */       this.size = ((ConceptPattern)story1PlotUnits.get(0)).getSize();
/*  24:    */     } else {
/*  25: 36 */       this.size = -1;
/*  26:    */     }
/*  27: 39 */     this.matchesString = new ArrayList();
/*  28: 40 */     for (ConceptPattern plotUnit : matches) {
/*  29: 41 */       this.matchesString.add(plotUnit.asString());
/*  30:    */     }
/*  31: 44 */     buildMatches(story1PlotUnits, story2PlotUnits);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static ConceptPatternMatchWrapper getFullMatch(String story1, int size, ReflectionLevelMemory memory)
/*  35:    */   {
/*  36: 50 */     return new ConceptPatternMatchWrapper(1.0D, memory.getPlotUnits(story1, size), memory.getPlotUnits(story1, size), memory.getPlotUnits(story1, size));
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void buildMatches(ArrayList<ConceptPattern> plotUnits1, ArrayList<ConceptPattern> plotUnits2)
/*  40:    */   {
/*  41: 60 */     this.matchPairs = new HashMap();
/*  42:    */     
/*  43:    */ 
/*  44: 63 */     HashSet<ConceptPattern> story1Used = new HashSet();
/*  45: 64 */     HashSet<ConceptPattern> story2Used = new HashSet();
/*  46: 66 */     for (ConceptPattern match : this.matches)
/*  47:    */     {
/*  48: 67 */       ConceptPattern story1Match = null;
/*  49: 68 */       ConceptPattern story2Match = null;
/*  50: 69 */       for (ConceptPattern plotUnit : plotUnits1) {
/*  51: 70 */         if ((!story1Used.contains(plotUnit)) && (plotUnit.canAlign(match, this.size)))
/*  52:    */         {
/*  53: 71 */           story1Match = plotUnit;
/*  54: 72 */           story1Used.add(plotUnit);
/*  55: 73 */           break;
/*  56:    */         }
/*  57:    */       }
/*  58: 76 */       for (ConceptPattern plotUnit : plotUnits2) {
/*  59: 77 */         if ((!story2Used.contains(plotUnit)) && (plotUnit.canAlign(match, this.size)))
/*  60:    */         {
/*  61: 78 */           story2Match = plotUnit;
/*  62: 79 */           story2Used.add(plotUnit);
/*  63: 80 */           break;
/*  64:    */         }
/*  65:    */       }
/*  66: 84 */       String matchString = match.asString();
/*  67: 85 */       if (!this.matchPairs.containsKey(match.asString())) {
/*  68: 86 */         this.matchPairs.put(matchString, new ArrayList());
/*  69:    */       }
/*  70: 89 */       if ((story1Match == null) && (story2Match == null))
/*  71:    */       {
/*  72: 90 */         for (ConceptPattern pu : plotUnits1) {
/*  73: 91 */           System.out.println(pu.asString());
/*  74:    */         }
/*  75: 93 */         for (ConceptPattern pu : plotUnits2) {
/*  76: 94 */           System.out.println(pu.asString());
/*  77:    */         }
/*  78:    */       }
/*  79: 97 */       else if (story1Match == null)
/*  80:    */       {
/*  81: 98 */         System.out.println("IS NULL 1: " + story2Match.asString());
/*  82:    */       }
/*  83:100 */       else if (story2Match == null)
/*  84:    */       {
/*  85:101 */         System.out.println("IS NULL 2: " + story1Match.asString());
/*  86:    */       }
/*  87:103 */       ((ArrayList)this.matchPairs.get(matchString)).add(new Pair(story1Match, story2Match));
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public double getValue()
/*  92:    */   {
/*  93:112 */     return this.value;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getMatchSize()
/*  97:    */   {
/*  98:116 */     return this.matches.size();
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String asString()
/* 102:    */   {
/* 103:120 */     return this.matchesString.toString();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getMatchesAsString()
/* 107:    */   {
/* 108:124 */     return this.matchesString.toString();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public ArrayList<String> getMatchStrings()
/* 112:    */   {
/* 113:128 */     return this.matchesString;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public ArrayList<Pair<ConceptPattern, ConceptPattern>> getMatches(String matchString)
/* 117:    */   {
/* 118:132 */     return (ArrayList)this.matchPairs.get(matchString);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public ArrayList<ConceptPattern> getMatches()
/* 122:    */   {
/* 123:136 */     return this.matches;
/* 124:    */   }
/* 125:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.generatedPatterns.ConceptPatternMatchWrapper
 * JD-Core Version:    0.7.0.1
 */