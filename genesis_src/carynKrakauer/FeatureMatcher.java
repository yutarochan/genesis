/*   1:    */ package carynKrakauer;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Vector;
/*   9:    */ import storyProcessor.ReflectionDescription;
/*  10:    */ import utils.Mark;
/*  11:    */ 
/*  12:    */ public class FeatureMatcher
/*  13:    */ {
/*  14:    */   public static VectorMatchWrapper match_features(String story1, int index1, String story2, int index2, ReflectionLevelMemory memory)
/*  15:    */   {
/*  16: 13 */     ReflectionDescription rd1 = (ReflectionDescription)memory.getStoryReflectionDescriptions(story1).get(index1);
/*  17: 14 */     ReflectionDescription rd2 = (ReflectionDescription)memory.getStoryReflectionDescriptions(story2).get(index2);
/*  18:    */     
/*  19: 16 */     HashMap<String, Integer> ref_hash_1 = new HashMap();
/*  20: 17 */     for (Entity t : rd1.getStoryElementsInvolved().getElements()) {
/*  21: 18 */       if (ref_hash_1.containsKey(t.getName())) {
/*  22: 19 */         ref_hash_1.put(t.getType(), Integer.valueOf(((Integer)ref_hash_1.get(t.getName())).intValue() + 1));
/*  23:    */       } else {
/*  24: 22 */         ref_hash_1.put(t.getType(), Integer.valueOf(1));
/*  25:    */       }
/*  26:    */     }
/*  27: 25 */     HashMap<String, Integer> ref_hash_2 = new HashMap();
/*  28: 26 */     for (Entity t : rd2.getStoryElementsInvolved().getElements()) {
/*  29: 27 */       if (ref_hash_2.containsKey(t.getName())) {
/*  30: 28 */         ref_hash_2.put(t.getType(), Integer.valueOf(((Integer)ref_hash_2.get(t.getName())).intValue() + 1));
/*  31:    */       } else {
/*  32: 31 */         ref_hash_2.put(t.getType(), Integer.valueOf(1));
/*  33:    */       }
/*  34:    */     }
/*  35: 34 */     return VectorMatcher.MatchVectors(ref_hash_1, ref_hash_2);
/*  36:    */   }
/*  37:    */   
/*  38:    */   private static void match_vectors_within_features(String title, ReflectionLevelMemory memory)
/*  39:    */   {
/*  40: 40 */     ArrayList<HashMap<String, Integer>> story1 = new ArrayList();
/*  41: 41 */     for (ReflectionDescription rd : memory.getStoryReflectionDescriptions(title))
/*  42:    */     {
/*  43: 42 */       HashMap<String, Integer> ref_hash = new HashMap();
/*  44: 43 */       for (Entity t : rd.getStoryElementsInvolved().getElements()) {
/*  45: 44 */         if (ref_hash.containsKey(t.getName())) {
/*  46: 45 */           ref_hash.put(t.getType(), Integer.valueOf(((Integer)ref_hash.get(t.getName())).intValue() + 1));
/*  47:    */         } else {
/*  48: 48 */           ref_hash.put(t.getType(), Integer.valueOf(1));
/*  49:    */         }
/*  50:    */       }
/*  51: 50 */       story1.add(ref_hash);
/*  52:    */     }
/*  53: 53 */     for (String story : memory.getStoryNames()) {
/*  54: 55 */       if (!title.equals(story))
/*  55:    */       {
/*  56: 59 */         ArrayList<HashMap<String, Integer>> story2 = new ArrayList();
/*  57: 60 */         for (ReflectionDescription rd : memory.getStoryReflectionDescriptions(story))
/*  58:    */         {
/*  59: 61 */           HashMap<String, Integer> ref_hash = new HashMap();
/*  60: 62 */           for (Entity t : rd.getStoryElementsInvolved().getElements()) {
/*  61: 63 */             if (ref_hash.containsKey(t.getName())) {
/*  62: 64 */               ref_hash.put(t.getType(), Integer.valueOf(((Integer)ref_hash.get(t.getName())).intValue() + 1));
/*  63:    */             } else {
/*  64: 66 */               ref_hash.put(t.getType(), Integer.valueOf(1));
/*  65:    */             }
/*  66:    */           }
/*  67: 68 */           story2.add(ref_hash);
/*  68:    */         }
/*  69: 72 */         for (int i = 0; i < story1.size(); i++) {
/*  70: 73 */           for (int j = 0; j < story2.size(); j++)
/*  71:    */           {
/*  72: 74 */             String ref1 = ((ReflectionDescription)memory.getStoryReflectionDescriptions(title).get(i)).getName();
/*  73: 75 */             String ref2 = ((ReflectionDescription)memory.getStoryReflectionDescriptions(story).get(j)).getName();
/*  74: 76 */             if (ref1.equals(ref2))
/*  75:    */             {
/*  76: 77 */               VectorMatchWrapper match = VectorMatcher.MatchVectors((HashMap)story1.get(i), (HashMap)story2.get(j));
/*  77: 78 */               if (match.getValue() < 1.0D)
/*  78:    */               {
/*  79: 79 */                 Mark.say(new Object[] {title + " compared to " + story + "'s: " + ref1 + " " + match.getValue() });
/*  80: 80 */                 Mark.say(new Object[] {"    " + story1.get(i) });
/*  81: 81 */                 Mark.say(new Object[] {"    " + story2.get(j) });
/*  82: 82 */                 Mark.say(new Object[] {"    " + match.getMatches() });
/*  83:    */               }
/*  84:    */             }
/*  85:    */           }
/*  86:    */         }
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static VectorMatchWrapper get_feature_leading_to_comparison(String story1, int index1, String story2, int index2, ReflectionLevelMemory memory)
/*  92:    */   {
/*  93: 93 */     return get_feature_leading_to_comparison((ReflectionDescription)memory.getStoryReflectionDescriptions(story1).get(index1), 
/*  94: 94 */       (ReflectionDescription)memory.getStoryReflectionDescriptions(story2).get(index2));
/*  95:    */   }
/*  96:    */   
/*  97:    */   private static VectorMatchWrapper get_feature_leading_to_comparison(ReflectionDescription rd1, ReflectionDescription rd2)
/*  98:    */   {
/*  99: 98 */     HashMap<String, Integer> rd_hash_1 = new HashMap();
/* 100: 99 */     HashMap<String, Integer> rd_hash_2 = new HashMap();
/* 101:    */     
/* 102:101 */     Mark.say(new Object[] {"elements: " });
/* 103:    */     Iterator localIterator2;
/* 104:102 */     for (Iterator localIterator1 = rd1.getStoryElementsInvolved().getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 105:    */     {
/* 106:102 */       Entity t = (Entity)localIterator1.next();
/* 107:    */       
/* 108:104 */       localIterator2 = t.getObjectOf().iterator(); continue;Entity t2 = (Entity)localIterator2.next();
/* 109:105 */       if (t2.getType().equals("prediction")) {
/* 110:107 */         for (Entity t3 : t2.getAllComponents()) {
/* 111:109 */           if (t3.getType().equals("conjuction")) {
/* 112:110 */             for (Entity t4 : t3.getChildren()) {
/* 113:112 */               if (rd_hash_1.get(t4.getType()) != null) {
/* 114:113 */                 rd_hash_1.put(t4.getType(), Integer.valueOf(((Integer)rd_hash_1.get(t4.getType())).intValue() + 1));
/* 115:    */               } else {
/* 116:115 */                 rd_hash_1.put(t4.getType(), Integer.valueOf(1));
/* 117:    */               }
/* 118:    */             }
/* 119:    */           }
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:123 */     for (localIterator1 = rd2.getStoryElementsInvolved().getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 124:    */     {
/* 125:123 */       Entity t = (Entity)localIterator1.next();
/* 126:    */       
/* 127:125 */       localIterator2 = t.getObjectOf().iterator(); continue;Entity t2 = (Entity)localIterator2.next();
/* 128:126 */       if (t2.getType().equals("prediction")) {
/* 129:128 */         for (Entity t3 : t2.getAllComponents()) {
/* 130:130 */           if (t3.getType().equals("conjuction")) {
/* 131:131 */             for (Entity t4 : t3.getChildren()) {
/* 132:133 */               if (rd_hash_2.get(t4.getType()) != null) {
/* 133:134 */                 rd_hash_2.put(t4.getType(), Integer.valueOf(((Integer)rd_hash_2.get(t4.getType())).intValue() + 1));
/* 134:    */               } else {
/* 135:136 */                 rd_hash_2.put(t4.getType(), Integer.valueOf(1));
/* 136:    */               }
/* 137:    */             }
/* 138:    */           }
/* 139:    */         }
/* 140:    */       }
/* 141:    */     }
/* 142:144 */     return VectorMatcher.MatchVectors(rd_hash_1, rd_hash_2);
/* 143:    */   }
/* 144:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.FeatureMatcher
 * JD-Core Version:    0.7.0.1
 */