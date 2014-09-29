/*   1:    */ package carynKrakauer.generatedPatterns;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import carynKrakauer.ReflectionLevelMemory;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ 
/*   8:    */ public class ConceptPatternFinder
/*   9:    */ {
/*  10:    */   public static ArrayList<ConceptPattern> findConceptPatterns(int size, String story, ReflectionLevelMemory memory)
/*  11:    */   {
/*  12: 21 */     ArrayList<ConceptPattern> output = new ArrayList();
/*  13: 22 */     ArrayList<PlotEvent> plotEvents = memory.getPlotEvents(story);
/*  14: 23 */     ArrayList<Integer> indices = new ArrayList();
/*  15: 25 */     for (int startIndex = 0; startIndex < plotEvents.size() - (size - 1); startIndex++)
/*  16:    */     {
/*  17: 26 */       indices = new ArrayList();
/*  18: 27 */       for (int i = 0; i < size; i++) {
/*  19: 28 */         indices.add(Integer.valueOf(startIndex + i));
/*  20:    */       }
/*  21: 31 */       while (((Integer)indices.get(indices.size() - 2)).intValue() < plotEvents.size())
/*  22:    */       {
/*  23: 33 */         ArrayList<PlotEvent> plotPattern = new ArrayList();
/*  24: 34 */         for (int index = 0; index < indices.size(); index++) {
/*  25: 35 */           plotPattern.add((PlotEvent)plotEvents.get(((Integer)indices.get(index)).intValue()));
/*  26:    */         }
/*  27:    */         do
/*  28:    */         {
/*  29: 40 */           for (int indexI = indices.size() - 2; indexI >= 0; indexI--)
/*  30:    */           {
/*  31: 41 */             if (indexI != 0)
/*  32:    */             {
/*  33: 42 */               indices.set(indexI, Integer.valueOf(((Integer)indices.get(indexI)).intValue() + 1));
/*  34: 43 */               if (((Integer)indices.get(indexI)).intValue() >= plotEvents.size())
/*  35:    */               {
/*  36: 44 */                 indices.set(indexI, Integer.valueOf(((Integer)indices.get(indexI - 1)).intValue() + 1));
/*  37: 45 */                 indices.set(indexI - 1, Integer.valueOf(((Integer)indices.get(indexI - 1)).intValue() + 1));
/*  38:    */               }
/*  39: 47 */               if ((indexI - 1 == 0) && (((Integer)indices.get(indices.size() - 2)).intValue() >= plotEvents.size())) {
/*  40:    */                 break;
/*  41:    */               }
/*  42:    */             }
/*  43: 51 */             if (indices.size() == 2) {
/*  44: 52 */               indices.set(0, Integer.valueOf(((Integer)indices.get(0)).intValue() + 1));
/*  45:    */             }
/*  46:    */           }
/*  47: 56 */         } while ((!isUnique(indices)) && 
/*  48:    */         
/*  49:    */ 
/*  50:    */ 
/*  51: 60 */           (((Integer)indices.get(0)).intValue() < plotEvents.size()));
/*  52: 66 */         boolean allwork = true;
/*  53: 67 */         for (PlotEvent pe : plotPattern) {
/*  54: 68 */           if ((pe.getType().equals("start")) || 
/*  55: 69 */             (pe.getType().equals("classification")) || 
/*  56: 70 */             (pe.getType().equals("explanation")) || 
/*  57: 71 */             (pe.getType().equals("cause")) || 
/*  58: 72 */             (pe.getType().equals("prediction")))
/*  59:    */           {
/*  60: 73 */             allwork = false;
/*  61: 74 */             break;
/*  62:    */           }
/*  63:    */         }
/*  64: 77 */         if (allwork)
/*  65:    */         {
/*  66: 83 */           boolean success = true;
/*  67: 84 */           for (PlotEvent e : plotPattern)
/*  68:    */           {
/*  69: 85 */             List<Entity> eParents = e.getParents();
/*  70: 86 */             boolean foundRelation = false;
/*  71: 87 */             for (PlotEvent e2 : plotPattern) {
/*  72: 88 */               if (e != e2)
/*  73:    */               {
/*  74: 92 */                 for (Entity t : eParents) {
/*  75: 93 */                   if (t.isEqual(e2.getThing()))
/*  76:    */                   {
/*  77: 94 */                     foundRelation = true;
/*  78: 95 */                     break;
/*  79:    */                   }
/*  80:    */                 }
/*  81: 98 */                 if (foundRelation) {
/*  82:    */                   break;
/*  83:    */                 }
/*  84:102 */                 List<Entity> e2Parents = e2.getParents();
/*  85:103 */                 for (Entity t : e2Parents) {
/*  86:104 */                   if (e.getThing().isEqual(t))
/*  87:    */                   {
/*  88:105 */                     foundRelation = true;
/*  89:106 */                     break;
/*  90:    */                   }
/*  91:    */                 }
/*  92:    */               }
/*  93:    */             }
/*  94:110 */             if (!foundRelation)
/*  95:    */             {
/*  96:111 */               success = false;
/*  97:112 */               break;
/*  98:    */             }
/*  99:    */           }
/* 100:115 */           if ((success) && (max(indices) < plotEvents.size()))
/* 101:    */           {
/* 102:116 */             ConceptPattern plotUnit = new ConceptPattern(plotPattern);
/* 103:117 */             output.add(plotUnit);
/* 104:    */           }
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:124 */     return output;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private static int max(List<Integer> indices)
/* 112:    */   {
/* 113:134 */     int max = -1;
/* 114:135 */     for (Integer i : indices) {
/* 115:136 */       if (i.intValue() > max) {
/* 116:137 */         max = i.intValue();
/* 117:    */       }
/* 118:    */     }
/* 119:139 */     return max;
/* 120:    */   }
/* 121:    */   
/* 122:    */   private static boolean isUnique(List<Integer> indices)
/* 123:    */   {
/* 124:149 */     for (int i = 0; i < indices.size(); i++) {
/* 125:150 */       for (int j = i + 1; j < indices.size(); j++) {
/* 126:151 */         if (((Integer)indices.get(i)).equals(indices.get(j))) {
/* 127:152 */           return false;
/* 128:    */         }
/* 129:    */       }
/* 130:    */     }
/* 131:156 */     return true;
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.generatedPatterns.ConceptPatternFinder
 * JD-Core Version:    0.7.0.1
 */