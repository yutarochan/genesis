/*   1:    */ package carynKrakauer;
/*   2:    */ 
/*   3:    */ import carynKrakauer.generatedPatterns.ConceptPattern;
/*   4:    */ import carynKrakauer.generatedPatterns.ConceptPatternMatchWrapper;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import utils.Mark;
/*   9:    */ 
/*  10:    */ public class InOrderVectorMatcher
/*  11:    */ {
/*  12:    */   public static VectorMatchWrapper matchVectorsInOrder(ArrayList<String> story1Reflections, ArrayList<String> story2Reflections, ArrayList<String> matches)
/*  13:    */   {
/*  14: 16 */     int origMaxLength = Math.max(story1Reflections.size(), story2Reflections.size());
/*  15: 17 */     ArrayList<String> v1 = new ArrayList();
/*  16: 18 */     for (String s : story1Reflections) {
/*  17: 19 */       if (matches.contains(s)) {
/*  18: 20 */         v1.add(s);
/*  19:    */       }
/*  20:    */     }
/*  21: 24 */     ArrayList<String> v2 = new ArrayList();
/*  22: 25 */     for (String s : story2Reflections) {
/*  23: 26 */       if (matches.contains(s)) {
/*  24: 27 */         v2.add(s);
/*  25:    */       }
/*  26:    */     }
/*  27: 31 */     int max_length = 0;
/*  28: 32 */     Object max_vector = new ArrayList();
/*  29: 33 */     int first_start_index = -1;
/*  30: 34 */     int first_end_index = -1;
/*  31: 35 */     int second_start_index = -1;
/*  32: 36 */     int second_end_index = -1;
/*  33: 38 */     for (int i = 0; i < v1.size(); i++)
/*  34:    */     {
/*  35: 39 */       int cur_length = 0;
/*  36: 40 */       int cur_first_start = -1;
/*  37: 41 */       int cur_first_end = -1;
/*  38: 42 */       int cur_second_start = -1;
/*  39: 43 */       int cur_second_end = -1;
/*  40: 44 */       ArrayList<String> cur_vector = new ArrayList();
/*  41:    */       
/*  42: 46 */       int second_start = 0;
/*  43: 48 */       for (int first_index = i; first_index < v1.size(); first_index++) {
/*  44: 50 */         for (int second_index = second_start; second_index < v2.size(); second_index++) {
/*  45: 51 */           if (((String)v1.get(first_index)).equals(v2.get(second_index)))
/*  46:    */           {
/*  47: 52 */             if (cur_first_start == -1) {
/*  48: 53 */               cur_first_start = first_index;
/*  49:    */             }
/*  50: 55 */             if (first_index > cur_first_end) {
/*  51: 56 */               cur_first_end = first_index;
/*  52:    */             }
/*  53: 58 */             if (second_index == -1) {
/*  54: 59 */               cur_second_start = second_index;
/*  55:    */             }
/*  56: 61 */             if (second_index > cur_second_end) {
/*  57: 62 */               cur_second_end = second_index;
/*  58:    */             }
/*  59: 64 */             cur_length++;
/*  60: 65 */             cur_vector.add((String)v1.get(first_index));
/*  61: 66 */             second_start = second_index + 1;
/*  62: 67 */             break;
/*  63:    */           }
/*  64:    */         }
/*  65:    */       }
/*  66: 73 */       if (cur_length > max_length)
/*  67:    */       {
/*  68: 74 */         max_length = cur_length;
/*  69: 75 */         max_vector = cur_vector;
/*  70: 76 */         first_start_index = cur_first_start;
/*  71: 77 */         first_end_index = cur_first_end;
/*  72: 78 */         second_start_index = cur_second_start;
/*  73: 79 */         second_end_index = cur_second_end;
/*  74:    */       }
/*  75:    */     }
/*  76: 83 */     return new VectorMatchWrapper(max_length / origMaxLength, v1, v2, (ArrayList)max_vector, 
/*  77: 84 */       first_start_index, first_end_index, second_start_index, second_end_index);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static VectorMatchWrapper matchVectorsInOrder(String story1, String story2, ReflectionLevelMemory memory)
/*  81:    */   {
/*  82: 89 */     ArrayList<String> story1Reflections = memory.getStoryReflectionStrings(story1);
/*  83: 90 */     ArrayList<String> story2Reflections = memory.getStoryReflectionStrings(story2);
/*  84: 91 */     VectorMatchWrapper matchWrapper = VectorMatcher.match_story_vectors(story1, story2, memory);
/*  85: 92 */     ArrayList<String> matches = matchWrapper.getMatches();
/*  86: 93 */     return matchVectorsInOrder(story1Reflections, story2Reflections, matches);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static VectorMatchWrapper matchVectorsInOrder(ArrayList<String> story1Reflections, ArrayList<String> story2Reflections)
/*  90:    */   {
/*  91: 99 */     ArrayList<String> matches = VectorMatcher.MatchVectors(
/*  92:100 */       VectorMatcher.getStoryReflectionStringCounts(story1Reflections), 
/*  93:101 */       VectorMatcher.getStoryReflectionStringCounts(story2Reflections)).getMatches();
/*  94:102 */     return matchVectorsInOrder(story1Reflections, story2Reflections, matches);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static ConceptPatternMatchWrapper matchGenVectorsInOrder(String story1, String story2, int size, ReflectionLevelMemory memory)
/*  98:    */   {
/*  99:106 */     HashMap<String, ArrayList<ConceptPattern>> genConceptPatternUnits = memory.getGenConceptPatternUnits(size);
/* 100:107 */     ArrayList<ConceptPattern> story1Reflections = (ArrayList)genConceptPatternUnits.get(story1);
/* 101:108 */     ArrayList<ConceptPattern> story2Reflections = (ArrayList)genConceptPatternUnits.get(story2);
/* 102:    */     
/* 103:110 */     int origMaxLength = Math.max(story1Reflections.size(), story2Reflections.size());
/* 104:    */     
/* 105:112 */     Mark.say(new Object[] {"story titles, " + story1 + " " + story2 });
/* 106:113 */     ConceptPatternMatchWrapper matchWrapper = memory.getPlotUnitComparison(story1, story2, size);
/* 107:114 */     ArrayList<ConceptPattern> matches = matchWrapper.getMatches();
/* 108:    */     
/* 109:116 */     ArrayList<ConceptPattern> v1 = new ArrayList();
/* 110:    */     ConceptPattern mCon;
/* 111:117 */     for (ConceptPattern con : story1Reflections) {
/* 112:118 */       for (Iterator localIterator2 = matches.iterator(); localIterator2.hasNext();)
/* 113:    */       {
/* 114:118 */         mCon = (ConceptPattern)localIterator2.next();
/* 115:119 */         if (mCon.canAlign(con, size))
/* 116:    */         {
/* 117:120 */           v1.add(mCon);
/* 118:121 */           break;
/* 119:    */         }
/* 120:    */       }
/* 121:    */     }
/* 122:126 */     ArrayList<ConceptPattern> v2 = new ArrayList();
/* 123:127 */     for (ConceptPattern con : story2Reflections) {
/* 124:128 */       for (ConceptPattern mCon : matches) {
/* 125:129 */         if (mCon.canAlign(con, size))
/* 126:    */         {
/* 127:130 */           v2.add(mCon);
/* 128:131 */           break;
/* 129:    */         }
/* 130:    */       }
/* 131:    */     }
/* 132:136 */     int max_length = 0;
/* 133:137 */     ArrayList<ConceptPattern> max_vector = new ArrayList();
/* 134:139 */     for (int i = 0; i < v1.size(); i++)
/* 135:    */     {
/* 136:140 */       int cur_length = 0;
/* 137:141 */       int cur_first_start = -1;
/* 138:142 */       int cur_first_end = -1;
/* 139:143 */       int cur_second_end = -1;
/* 140:144 */       ArrayList<ConceptPattern> cur_vector = new ArrayList();
/* 141:    */       
/* 142:146 */       int second_start = 0;
/* 143:148 */       for (int first_index = i; first_index < v1.size(); first_index++) {
/* 144:150 */         for (int second_index = second_start; second_index < v2.size(); second_index++) {
/* 145:151 */           if (((ConceptPattern)v1.get(first_index)).canAlign((ConceptPattern)v2.get(second_index), size))
/* 146:    */           {
/* 147:152 */             if (cur_first_start == -1) {
/* 148:153 */               cur_first_start = first_index;
/* 149:    */             }
/* 150:155 */             if (first_index > cur_first_end) {
/* 151:156 */               cur_first_end = first_index;
/* 152:    */             }
/* 153:158 */             if (second_index > cur_second_end) {
/* 154:159 */               cur_second_end = second_index;
/* 155:    */             }
/* 156:161 */             cur_length++;
/* 157:162 */             cur_vector.add((ConceptPattern)v1.get(first_index));
/* 158:163 */             second_start = second_index + 1;
/* 159:164 */             break;
/* 160:    */           }
/* 161:    */         }
/* 162:    */       }
/* 163:170 */       if (cur_length > max_length)
/* 164:    */       {
/* 165:171 */         max_length = cur_length;
/* 166:172 */         max_vector = cur_vector;
/* 167:    */       }
/* 168:    */     }
/* 169:176 */     return new ConceptPatternMatchWrapper(max_length / origMaxLength, max_vector, v1, v2);
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.InOrderVectorMatcher
 * JD-Core Version:    0.7.0.1
 */