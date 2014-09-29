/*  1:   */ package carynKrakauer;
/*  2:   */ 
/*  3:   */ import java.util.HashMap;
/*  4:   */ 
/*  5:   */ public class RarityUtil
/*  6:   */ {
/*  7:   */   public static HashMap<String, Float> feature_rarity_scores(String title, ReflectionLevelMemory memory)
/*  8:   */   {
/*  9: 8 */     HashMap<String, Float> rarities = new HashMap();
/* 10:10 */     for (String feature : memory.getStoryReflectionStringCounts(title).keySet()) {
/* 11:11 */       rarities.put(feature, Float.valueOf(getRarity(feature, memory)));
/* 12:   */     }
/* 13:13 */     return rarities;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public static HashMap<String, Float> feature_rarity_score_in_common(String story1, String story2, ReflectionLevelMemory memory)
/* 17:   */   {
/* 18:17 */     HashMap<String, Float> rarities = new HashMap();
/* 19:19 */     for (String feature : memory.getStoryReflectionStringCounts(story1).keySet())
/* 20:   */     {
/* 21:20 */       int same_count = 0;
/* 22:21 */       float rarity = 0.0F;
/* 23:22 */       if (memory.getStoryReflectionStringCounts(story2).get(feature) != null)
/* 24:   */       {
/* 25:24 */         same_count = same_count + (((Integer)memory.getStoryReflectionStringCounts(story1).get(feature)).intValue() + ((Integer)memory.getStoryReflectionStringCounts(story2).get(feature)).intValue());
/* 26:   */         
/* 27:26 */         rarity = 1.0F - (memory.getConceptCount(feature) - same_count) / memory.getConceptCount(feature);
/* 28:28 */         if (story1.equals(story2)) {
/* 29:29 */           rarity /= 2.0F;
/* 30:   */         }
/* 31:31 */         rarities.put(feature, Float.valueOf(rarity));
/* 32:   */       }
/* 33:   */     }
/* 34:35 */     return rarities;
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static float rarity_in_common(String story1, String story2, ReflectionLevelMemory memory)
/* 38:   */   {
/* 39:39 */     float rarity_sum = 0.0F;
/* 40:40 */     float max_rarity = 0.0F;
/* 41:42 */     for (String feature : memory.getStoryReflectionStringCounts(story1).keySet())
/* 42:   */     {
/* 43:43 */       int same_count = 0;
/* 44:44 */       float rarity = 0.0F;
/* 45:45 */       if (memory.getStoryReflectionStringCounts(story2).get(feature) != null)
/* 46:   */       {
/* 47:47 */         same_count = same_count + (((Integer)memory.getStoryReflectionStringCounts(story1).get(feature)).intValue() + ((Integer)memory.getStoryReflectionStringCounts(story2).get(feature)).intValue());
/* 48:   */         
/* 49:49 */         rarity = 1.0F - (memory.getConceptCount(feature) - same_count) / memory.getConceptCount(feature);
/* 50:   */         
/* 51:51 */         rarity_sum += rarity;
/* 52:52 */         max_rarity += 1.0F;
/* 53:   */       }
/* 54:   */     }
/* 55:56 */     if (story1.equals(story2)) {
/* 56:57 */       return rarity_sum / max_rarity / 2.0F;
/* 57:   */     }
/* 58:59 */     return rarity_sum / max_rarity;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public static HashMap<String, Float> rarity_scores(String title, ReflectionLevelMemory memory)
/* 62:   */   {
/* 63:63 */     HashMap<String, Float> rarities = new HashMap();
/* 64:65 */     for (String story : memory.getStoryNames()) {
/* 65:66 */       if (!title.equals(story)) {
/* 66:69 */         for (String feature : memory.getStoryReflectionStringCounts(title).keySet())
/* 67:   */         {
/* 68:70 */           int same_count = 0;
/* 69:71 */           float rarity = 0.0F;
/* 70:72 */           if (memory.getStoryReflectionStringCounts(story).get(feature) != null)
/* 71:   */           {
/* 72:74 */             same_count = same_count + (((Integer)memory.getStoryReflectionStringCounts(title).get(feature)).intValue() + ((Integer)memory.getStoryReflectionStringCounts(story).get(feature)).intValue());
/* 73:   */             
/* 74:76 */             rarity = 1.0F - (memory.getConceptCount(feature) - same_count) / memory.getConceptCount(feature);
/* 75:77 */             rarities.put(feature, Float.valueOf(rarity));
/* 76:   */           }
/* 77:   */         }
/* 78:   */       }
/* 79:   */     }
/* 80:82 */     return rarities;
/* 81:   */   }
/* 82:   */   
/* 83:   */   private static float getRarity(String pattern, ReflectionLevelMemory memory)
/* 84:   */   {
/* 85:86 */     if (!memory.getConceptPatternStoryCounts().containsKey(pattern)) {
/* 86:87 */       return -1.0F;
/* 87:   */     }
/* 88:89 */     float rarity = ((Integer)memory.getConceptPatternStoryCounts().get(pattern)).intValue() / memory.getStoryCount();
/* 89:90 */     return 1.0F - rarity;
/* 90:   */   }
/* 91:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.RarityUtil
 * JD-Core Version:    0.7.0.1
 */