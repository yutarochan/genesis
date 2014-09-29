/*   1:    */ package carynKrakauer;
/*   2:    */ 
/*   3:    */ import carynKrakauer.aligner.ReflectionAlignmentUtil;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.LinkedList;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.TreeMap;
/*  11:    */ import matthewFay.StoryAlignment.Alignment;
/*  12:    */ import matthewFay.Utilities.Pair;
/*  13:    */ import storyProcessor.ReflectionDescription;
/*  14:    */ import utils.Mark;
/*  15:    */ 
/*  16:    */ public class StoryPredictor
/*  17:    */ {
/*  18:    */   public static ArrayList<String> simplePredictStory(String cur_story_name, ArrayList<String> cur_story, HashMap<String, ArrayList<String>> stories, ReflectionLevelMemory memory)
/*  19:    */   {
/*  20: 17 */     ArrayList<String> prediction = new ArrayList();
/*  21:    */     
/*  22:    */ 
/*  23:    */ 
/*  24: 21 */     String highest_match_name = null;
/*  25: 22 */     VectorMatchWrapper highest_match = null;
/*  26: 24 */     for (String story : stories.keySet()) {
/*  27: 26 */       if (!story.equals(cur_story_name))
/*  28:    */       {
/*  29: 30 */         VectorMatchWrapper match = InOrderVectorMatcher.matchVectorsInOrder(cur_story_name, story, memory);
/*  30: 31 */         if ((highest_match == null) || (highest_match.getValue() < match.getValue()))
/*  31:    */         {
/*  32: 32 */           highest_match = match;
/*  33: 33 */           highest_match_name = story;
/*  34:    */         }
/*  35:    */       }
/*  36:    */     }
/*  37: 38 */     if ((highest_match == null) || (highest_match.getValue() == 0.0D)) {
/*  38: 39 */       return prediction;
/*  39:    */     }
/*  40: 42 */     int end_index = highest_match.getSecondEndIndex();
/*  41: 43 */     Object storyVector = (ArrayList)stories.get(highest_match_name);
/*  42: 45 */     for (int i = end_index + 1; i < ((ArrayList)storyVector).size(); i++) {
/*  43: 46 */       prediction.add((String)((ArrayList)storyVector).get(i));
/*  44:    */     }
/*  45: 49 */     return prediction;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static ArrayList<String> predictStoryWithN(String cur_story_name, ArrayList<String> cur_story, HashMap<String, ArrayList<String>> stories, int numSimilarStories, ReflectionLevelMemory memory)
/*  49:    */   {
/*  50: 56 */     ArrayList<String> prediction = new ArrayList();
/*  51:    */     
/*  52:    */ 
/*  53:    */ 
/*  54: 60 */     Map<Double, ArrayList<VectorMatchWrapper>> matches = new HashMap();
/*  55: 63 */     for (String story : stories.keySet()) {
/*  56: 65 */       if (!story.equals(cur_story_name))
/*  57:    */       {
/*  58: 69 */         VectorMatchWrapper match = InOrderVectorMatcher.matchVectorsInOrder(cur_story_name, story, memory);
/*  59: 73 */         if (!matches.containsKey(Double.valueOf(match.getValue()))) {
/*  60: 74 */           matches.put(Double.valueOf(match.getValue()), new ArrayList());
/*  61:    */         }
/*  62: 77 */         ((ArrayList)matches.get(Double.valueOf(match.getValue()))).add(match);
/*  63:    */       }
/*  64:    */     }
/*  65: 81 */     Map<Double, ArrayList<VectorMatchWrapper>> sortedMap = new TreeMap(matches);
/*  66:    */     
/*  67: 83 */     Object topNMatches = new ArrayList();
/*  68:    */     
/*  69: 85 */     int matchesFound = 0;
/*  70: 86 */     for (Iterator localIterator2 = sortedMap.keySet().iterator(); localIterator2.hasNext();)
/*  71:    */     {
/*  72: 86 */       double value = ((Double)localIterator2.next()).doubleValue();
/*  73: 87 */       for (VectorMatchWrapper match : (ArrayList)sortedMap.get(Double.valueOf(value)))
/*  74:    */       {
/*  75: 88 */         ((ArrayList)topNMatches).add(match);
/*  76: 89 */         matchesFound++;
/*  77: 90 */         if (matchesFound == numSimilarStories) {
/*  78:    */           break;
/*  79:    */         }
/*  80:    */       }
/*  81: 93 */       if (matchesFound == numSimilarStories) {
/*  82:    */         break;
/*  83:    */       }
/*  84:    */     }
/*  85: 97 */     if (((ArrayList)topNMatches).size() == 0) {
/*  86: 98 */       return prediction;
/*  87:    */     }
/*  88:101 */     ArrayList<String> union = ((VectorMatchWrapper)((ArrayList)topNMatches).get(0)).getStory2();
/*  89:102 */     Mark.say(new Object[] {union });
/*  90:103 */     Mark.say(new Object[] {((VectorMatchWrapper)((ArrayList)topNMatches).get(0)).getStory2() });
/*  91:104 */     for (int i = 1; i < ((ArrayList)topNMatches).size() - 1; i++) {
/*  92:105 */       union = InOrderVectorMatcher.matchVectorsInOrder(union, ((VectorMatchWrapper)((ArrayList)topNMatches).get(i)).getStory2()).getMatches();
/*  93:    */     }
/*  94:109 */     if ((union != null) && (union.size() != 0))
/*  95:    */     {
/*  96:111 */       VectorMatchWrapper unionMatch = InOrderVectorMatcher.matchVectorsInOrder(union, cur_story);
/*  97:    */       
/*  98:113 */       int end_index = unionMatch.getFirstEndIndex();
/*  99:114 */       for (int i = end_index + 1; i < union.size(); i++) {
/* 100:115 */         prediction.add((String)union.get(i));
/* 101:    */       }
/* 102:    */     }
/* 103:119 */     return prediction;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static ArrayList<String> predictStoryAligner(String cur_story_name, ArrayList<ReflectionDescription> cur_story, HashMap<String, ArrayList<ReflectionDescription>> stories)
/* 107:    */   {
/* 108:130 */     ArrayList<String> prediction = new ArrayList();
/* 109:    */     
/* 110:    */ 
/* 111:    */ 
/* 112:134 */     String highest_match_name = null;
/* 113:135 */     Alignment<ReflectionDescription, ReflectionDescription> highest_match = null;
/* 114:137 */     for (String story : stories.keySet()) {
/* 115:139 */       if (!story.equals(cur_story_name))
/* 116:    */       {
/* 117:143 */         Alignment<ReflectionDescription, ReflectionDescription> match = 
/* 118:144 */           ReflectionAlignmentUtil.AlignReflections(cur_story, (ArrayList)stories.get(story));
/* 119:145 */         if ((highest_match == null) || (highest_match.score < match.score))
/* 120:    */         {
/* 121:146 */           highest_match = match;
/* 122:147 */           highest_match_name = story;
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:152 */     if ((highest_match == null) || (highest_match.score == 0.0D))
/* 127:    */     {
/* 128:153 */       Mark.say(new Object[] {"No prediction found." });
/* 129:154 */       return prediction;
/* 130:    */     }
/* 131:158 */     int last_pair = -1;
/* 132:159 */     for (int i = 0; i < highest_match.size(); i++)
/* 133:    */     {
/* 134:160 */       Pair<ReflectionDescription, ReflectionDescription> pair = (Pair)highest_match.get(i);
/* 135:161 */       if ((pair.a != null) && (pair.b != null)) {
/* 136:162 */         last_pair = i;
/* 137:    */       }
/* 138:    */     }
/* 139:166 */     for (int i = last_pair + 1; i < highest_match.size(); i++)
/* 140:    */     {
/* 141:167 */       Mark.say(new Object[] {"i: " + i });
/* 142:168 */       if (((Pair)highest_match.get(i)).b != null)
/* 143:    */       {
/* 144:169 */         prediction.add(((ReflectionDescription)((Pair)highest_match.get(i)).b).getName());
/* 145:170 */         Mark.say(new Object[] {"matched!" });
/* 146:    */       }
/* 147:    */     }
/* 148:174 */     Mark.say(new Object[] {"Highest match was " + highest_match_name });
/* 149:    */     
/* 150:176 */     return prediction;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static ArrayList<String> predictStoryAlignerNStories(String cur_story_name, ArrayList<ReflectionDescription> cur_story, HashMap<String, ArrayList<ReflectionDescription>> stories, int n)
/* 154:    */   {
/* 155:182 */     if (n == 0) {
/* 156:183 */       return new ArrayList();
/* 157:    */     }
/* 158:186 */     if (n == 1) {
/* 159:187 */       return predictStoryAligner(cur_story_name, cur_story, stories);
/* 160:    */     }
/* 161:190 */     ArrayList<String> prediction = new ArrayList();
/* 162:    */     
/* 163:    */ 
/* 164:193 */     LinkedList<Alignment<ReflectionDescription, ReflectionDescription>> matches = 
/* 165:194 */       new LinkedList();
/* 166:    */     int i;
/* 167:196 */     for (String story : stories.keySet()) {
/* 168:198 */       if (!story.equals(cur_story_name))
/* 169:    */       {
/* 170:202 */         Alignment<ReflectionDescription, ReflectionDescription> match = 
/* 171:203 */           ReflectionAlignmentUtil.AlignReflections(cur_story, (ArrayList)stories.get(story));
/* 172:205 */         for (i = 0; i < matches.size(); i++) {
/* 173:206 */           if (((Alignment)matches.get(i)).score < match.score) {
/* 174:    */             break;
/* 175:    */           }
/* 176:    */         }
/* 177:210 */         matches.add(i, match);
/* 178:    */       }
/* 179:    */     }
/* 180:215 */     if ((matches.size() == 0) || (((Alignment)matches.get(0)).score == 0.0D))
/* 181:    */     {
/* 182:216 */       Mark.say(new Object[] {"No prediction found." });
/* 183:217 */       return prediction;
/* 184:    */     }
/* 185:221 */     LinkedList<Alignment<ReflectionDescription, ReflectionDescription>> highest_matches = 
/* 186:222 */       new LinkedList();
/* 187:223 */     for (int i = 0; i < n; i++) {
/* 188:224 */       highest_matches.add((Alignment)matches.get(i));
/* 189:    */     }
/* 190:228 */     Object stories_as_strings = new ArrayList();
/* 191:229 */     for (Alignment<ReflectionDescription, ReflectionDescription> match : highest_matches)
/* 192:    */     {
/* 193:230 */       ArrayList<String> string_story = new ArrayList();
/* 194:233 */       for (Pair<ReflectionDescription, ReflectionDescription> pair : match) {
/* 195:234 */         if ((pair.a != null) && (pair.b != null)) {
/* 196:235 */           string_story.add(((ReflectionDescription)pair.a).getName());
/* 197:    */         }
/* 198:    */       }
/* 199:238 */       ((ArrayList)stories_as_strings).add(string_story);
/* 200:    */     }
/* 201:241 */     ArrayList<String> union = null;
/* 202:    */     
/* 203:    */ 
/* 204:244 */     int curN = n;
/* 205:245 */     while (curN > 0)
/* 206:    */     {
/* 207:246 */       union = (ArrayList)((ArrayList)stories_as_strings).get(0);
/* 208:247 */       for (int i = 1; i < curN; i++) {
/* 209:248 */         union = InOrderVectorMatcher.matchVectorsInOrder(union, (ArrayList)((ArrayList)stories_as_strings).get(i)).getMatches();
/* 210:    */       }
/* 211:250 */       if (union.size() != 0) {
/* 212:    */         break;
/* 213:    */       }
/* 214:253 */       curN--;
/* 215:    */     }
/* 216:256 */     if (union.size() == 0) {
/* 217:257 */       return new ArrayList();
/* 218:    */     }
/* 219:260 */     ArrayList<String> curStoryString = new ArrayList();
/* 220:261 */     for (ReflectionDescription rd : cur_story) {
/* 221:262 */       curStoryString.add(rd.getName());
/* 222:    */     }
/* 223:266 */     Alignment<String, String> unionAlignment = ReflectionAlignmentUtil.AlignStrings(curStoryString, union);
/* 224:267 */     int last_pair = -1;
/* 225:268 */     for (int i = 0; i < unionAlignment.size(); i++)
/* 226:    */     {
/* 227:269 */       Pair<String, String> pair = (Pair)unionAlignment.get(i);
/* 228:270 */       if ((pair.a != null) && (pair.b != null)) {
/* 229:271 */         last_pair = i;
/* 230:    */       }
/* 231:    */     }
/* 232:275 */     for (int i = last_pair + 1; i < unionAlignment.size(); i++) {
/* 233:276 */       if (((Pair)unionAlignment.get(i)).b != null) {
/* 234:277 */         prediction.add((String)((Pair)unionAlignment.get(i)).b);
/* 235:    */       }
/* 236:    */     }
/* 237:281 */     return prediction;
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.StoryPredictor
 * JD-Core Version:    0.7.0.1
 */