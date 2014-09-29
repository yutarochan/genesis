/*   1:    */ package silaSayan;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.LinkedList;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.TreeMap;
/*  12:    */ import java.util.TreeSet;
/*  13:    */ import storyProcessor.ReflectionDescription;
/*  14:    */ import utils.Mark;
/*  15:    */ 
/*  16:    */ public class SummaryHelper
/*  17:    */   extends AbstractWiredBox
/*  18:    */ {
/*  19: 21 */   public static String TO_STORY_TELLER = "to story teller";
/*  20:    */   
/*  21:    */   public Sequence filterSummary(ReflectionDescription concept)
/*  22:    */   {
/*  23: 30 */     Mark.say(
/*  24:    */     
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29: 36 */       new Object[] { "  In FILTER SUMMARY!!!" });Sequence quickSummaryForConcept = new Sequence();
/*  30: 32 */     for (Entity t : concept.getStoryElementsInvolved().getAllComponents()) {
/*  31: 33 */       quickSummaryForConcept.addElement(t);
/*  32:    */     }
/*  33: 35 */     return quickSummaryForConcept;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Map<Entity, ArrayList<Entity>> rootMapper(Sequence storyLine)
/*  37:    */   {
/*  38: 39 */     Mark.say(
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66: 67 */       new Object[] { "  IN  ROOTMAPPER  !!!!!!" });Map<Entity, ArrayList<Entity>> rootMap = new TreeMap();
/*  67: 41 */     for (Entity t : storyLine.getAllComponents()) {
/*  68: 42 */       if ((t.isA("explanation")) || (t.isA("prediction")))
/*  69:    */       {
/*  70: 43 */         Mark.say(new Object[] {"CONSEQUENCE : ", t.asString() });
/*  71: 44 */         if ((t.getObject() != null) && (t.getSubject() != null))
/*  72:    */         {
/*  73: 45 */           ArrayList<Entity> values = new ArrayList();
/*  74: 46 */           Mark.say(new Object[] {"ANTECEDENTS : " });
/*  75: 47 */           for (Entity f : t.getSubject().getAllComponents())
/*  76:    */           {
/*  77: 48 */             Mark.say(new Object[] {"    ", f.asString() });
/*  78: 49 */             values.add(f);
/*  79:    */           }
/*  80: 55 */           rootMap.put(t, values);
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84: 66 */     return rootMap;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Sequence extendRootsTree(Entity consequent, Map<Entity, ArrayList<Entity>> rootMap)
/*  88:    */   {
/*  89: 70 */     Sequence relevantElements = new Sequence();
/*  90: 73 */     if (!rootMap.containsKey(consequent))
/*  91:    */     {
/*  92: 74 */       if (relevantElements.getAllComponents().isEmpty()) {
/*  93: 75 */         Mark.say(new Object[] {"EMPTY: relevantElements!!!!!!!!!!" });
/*  94:    */       } else {
/*  95: 77 */         for (Entity t : relevantElements.getAllComponents()) {
/*  96: 78 */           Mark.say(new Object[] {"Found relevant: ", t.asString() });
/*  97:    */         }
/*  98:    */       }
/*  99: 81 */       return relevantElements;
/* 100:    */     }
/* 101: 83 */     if (rootMap.containsKey(consequent)) {
/* 102: 84 */       for (Entity t : (ArrayList)rootMap.get(consequent)) {
/* 103: 85 */         if (!relevantElements.containsDeprecated(t))
/* 104:    */         {
/* 105: 86 */           relevantElements.addElement(t);
/* 106: 87 */           extendRootsTree(t, rootMap);
/* 107:    */         }
/* 108:    */       }
/* 109:    */     }
/* 110: 91 */     Mark.say(new Object[] {"DOING WEIRD RETURN!!!!!!" });
/* 111: 92 */     return relevantElements;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public Sequence populateConceptSummary(Sequence quickSummary, Map<Entity, ArrayList<Entity>> rootMap)
/* 115:    */   {
/* 116: 96 */     Mark.say(
/* 117:    */     
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:    */ 
/* 127:107 */       new Object[] { " POPULATING CONCEPT SUMMARY!!!!!" });Sequence fullConceptSummary = new Sequence();
/* 128: 98 */     for (Entity t : quickSummary.getAllComponents())
/* 129:    */     {
/* 130: 99 */       Sequence s = extendRootsTree(t, rootMap);
/* 131:100 */       fullConceptSummary.addAll(s);
/* 132:    */     }
/* 133:102 */     Mark.say(new Object[] {"FULL CONCEPT SUMMARRYYYYYY: " });
/* 134:103 */     for (Entity f : fullConceptSummary.getAllComponents()) {
/* 135:104 */       Mark.say(new Object[] {f.asString() });
/* 136:    */     }
/* 137:106 */     return fullConceptSummary;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public LinkedList<ArrayList<Object>> summarySorter(ArrayList<ArrayList<Object>> unsorted)
/* 141:    */   {
/* 142:112 */     Map<Double, ArrayList<Object>> map = new HashMap();
/* 143:113 */     int attempt = 0;
/* 144:114 */     int done = 0;
/* 145:116 */     for (ArrayList<Object> l : unsorted) {
/* 146:117 */       if (l.get(0).getClass() == Double.class)
/* 147:    */       {
/* 148:118 */         double key = ((Double)l.get(0)).doubleValue();
/* 149:119 */         ArrayList<Object> value = new ArrayList();
/* 150:    */         
/* 151:121 */         value.add(0, l.get(1));
/* 152:122 */         value.add(1, l.get(2));
/* 153:123 */         attempt++;
/* 154:    */         
/* 155:125 */         map.put(Double.valueOf(key), value);
/* 156:    */       }
/* 157:    */     }
/* 158:132 */     LinkedList<ArrayList<Object>> finalSummary = new LinkedList();
/* 159:    */     
/* 160:134 */     Object keySet = new TreeSet(map.keySet());
/* 161:136 */     for (Double key : (TreeSet)keySet)
/* 162:    */     {
/* 163:138 */       ArrayList<Object> seqToAdd = (ArrayList)map.get(key);
/* 164:    */       
/* 165:140 */       finalSummary.add(seqToAdd);
/* 166:    */     }
/* 167:142 */     if (finalSummary.isEmpty()) {
/* 168:143 */       Mark.say(new Object[] {"FINAL SUMMARY EMPTY!" });
/* 169:    */     }
/* 170:145 */     return finalSummary;
/* 171:    */   }
/* 172:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.SummaryHelper
 * JD-Core Version:    0.7.0.1
 */