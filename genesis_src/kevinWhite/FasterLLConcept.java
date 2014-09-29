/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Thread;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Enumeration;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.Set;
/*  12:    */ import links.words.BundleGenerator;
/*  13:    */ import translator.Translator;
/*  14:    */ 
/*  15:    */ public class FasterLLConcept
/*  16:    */   implements Concept<String>
/*  17:    */ {
/*  18:    */   private TypeLattice lattice;
/*  19: 28 */   private Set<String> positiveAncestors = new HashSet();
/*  20: 29 */   private Set<String> negativeAncestors = new HashSet();
/*  21:    */   private String conceptName;
/*  22: 31 */   public boolean empty = true;
/*  23:    */   
/*  24:    */   public FasterLLConcept(TypeLattice lattice)
/*  25:    */   {
/*  26: 34 */     this.lattice = lattice;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public FasterLLConcept(TypeLattice lattice, String name)
/*  30:    */   {
/*  31: 38 */     this.lattice = lattice;
/*  32: 39 */     this.conceptName = name;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void learnNegative(String negative)
/*  36:    */   {
/*  37: 46 */     this.negativeAncestors.addAll(this.lattice.getAncestors(negative));
/*  38: 47 */     this.empty = false;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void learnPositive(String positive)
/*  42:    */   {
/*  43: 54 */     this.positiveAncestors.addAll(this.lattice.getAncestors(positive));
/*  44: 55 */     this.empty = false;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean contains(String node)
/*  48:    */   {
/*  49: 59 */     if (this.negativeAncestors.contains(node)) {
/*  50: 60 */       return false;
/*  51:    */     }
/*  52: 62 */     if (this.positiveAncestors.contains(node)) {
/*  53: 63 */       return true;
/*  54:    */     }
/*  55: 65 */     boolean result = false;
/*  56: 66 */     for (String parent : this.lattice.getParents(node)) {
/*  57: 67 */       result = (result) || (contains(parent));
/*  58:    */     }
/*  59: 69 */     return result;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Set<String> maximalElements()
/*  63:    */   {
/*  64: 75 */     Set<String> maxes = new HashSet();
/*  65: 76 */     for (String node : this.positiveAncestors) {
/*  66: 77 */       if (contains(node)) {
/*  67: 78 */         maxes.add(node);
/*  68:    */       }
/*  69:    */     }
/*  70: 82 */     Set<String> toRemove = new HashSet();
/*  71:    */     Iterator localIterator3;
/*  72: 83 */     for (Iterator localIterator2 = maxes.iterator(); localIterator2.hasNext(); localIterator3.hasNext())
/*  73:    */     {
/*  74: 83 */       String a = (String)localIterator2.next();
/*  75: 84 */       localIterator3 = maxes.iterator(); continue;String b = (String)localIterator3.next();
/*  76: 85 */       if ((!a.equals(b)) && (this.lattice.leq(a, b))) {
/*  77: 86 */         toRemove.add(a);
/*  78:    */       }
/*  79:    */     }
/*  80: 90 */     maxes.removeAll(toRemove);
/*  81: 91 */     return maxes;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected boolean infer(String word)
/*  85:    */     throws Exception
/*  86:    */   {
/*  87:102 */     Bundle wBundle = BundleGenerator.getBundle(word.toString());
/*  88:103 */     Thread wThread = wBundle.getPrimedThread();
/*  89:104 */     this.lattice.updateAncestry(wThread);
/*  90:105 */     for (int i = wThread.size() - 1; i >= 0; i--)
/*  91:    */     {
/*  92:106 */       String ele = (String)wThread.elementAt(i);
/*  93:107 */       if (this.negativeAncestors.contains(wThread.elementAt(i)))
/*  94:    */       {
/*  95:108 */         learnNegative(ele);
/*  96:109 */         return false;
/*  97:    */       }
/*  98:111 */       if (this.positiveAncestors.contains(wThread.elementAt(i)))
/*  99:    */       {
/* 100:112 */         learnPositive(ele);
/* 101:113 */         return true;
/* 102:    */       }
/* 103:    */     }
/* 104:116 */     return false;
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected boolean infer(Thread wThread)
/* 108:    */   {
/* 109:127 */     this.lattice.updateAncestry(wThread);
/* 110:128 */     for (int i = wThread.size() - 1; i >= 0; i--)
/* 111:    */     {
/* 112:129 */       String ele = (String)wThread.elementAt(i);
/* 113:130 */       if (this.negativeAncestors.contains(wThread.elementAt(i)))
/* 114:    */       {
/* 115:131 */         learnNegative(ele);
/* 116:132 */         return false;
/* 117:    */       }
/* 118:134 */       if (this.positiveAncestors.contains(wThread.elementAt(i)))
/* 119:    */       {
/* 120:135 */         learnPositive(ele);
/* 121:136 */         return true;
/* 122:    */       }
/* 123:    */     }
/* 124:139 */     return false;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String interpretSimpleSentence(HashMap<String, Object> sentData)
/* 128:    */   {
/* 129:143 */     return reduceBundle((Bundle)sentData.get("noun_bundle"), (String)sentData.get("verb"), ((Boolean)sentData.get("feature")).booleanValue());
/* 130:    */   }
/* 131:    */   
/* 132:    */   public static HashMap<String, Object> parseSimpleSentence(String sentence)
/* 133:    */     throws Exception
/* 134:    */   {
/* 135:160 */     HashMap map = new HashMap();
/* 136:161 */     Translator translator = new Translator();
/* 137:162 */     Entity entity = translator.translate(sentence);
/* 138:163 */     Entity subject = entity.getElement(0).getSubject();
/* 139:164 */     Bundle noun_bundle = subject.getBundle();
/* 140:165 */     String verb = entity.getElement(0).getPrimedThread().toString(true);
/* 141:166 */     ArrayList features = entity.getElement(0).getFeatures();
/* 142:167 */     Boolean feature = Boolean.valueOf(!entity.getElement(0).hasFeature("not"));
/* 143:168 */     map.put("noun_bundle", noun_bundle);
/* 144:169 */     map.put("verb", verb);
/* 145:170 */     map.put("feature", feature);
/* 146:171 */     map.put("noun", subject);
/* 147:    */     
/* 148:    */ 
/* 149:174 */     return map;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String reduceBundle(Bundle noun_bundle, String verb, boolean feature)
/* 153:    */   {
/* 154:187 */     ArrayList<String> goodMeanings = new ArrayList();
/* 155:188 */     Bundle finBundle = noun_bundle.copy();
/* 156:189 */     Enumeration<Thread> threads = noun_bundle.elements();
/* 157:190 */     while (threads.hasMoreElements())
/* 158:    */     {
/* 159:191 */       Thread wThread = (Thread)threads.nextElement();
/* 160:192 */       if (((feature) && (infer(wThread))) || ((!feature) && (!infer(wThread)))) {
/* 161:193 */         goodMeanings.add(wThread.toString(true));
/* 162:    */       } else {
/* 163:195 */         finBundle.removeThread(wThread);
/* 164:    */       }
/* 165:    */     }
/* 166:198 */     return goodMeanings.toString();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String toString()
/* 170:    */   {
/* 171:202 */     return maximalElements().toString();
/* 172:    */   }
/* 173:    */   
/* 174:    */   public TypeLattice getLattice()
/* 175:    */   {
/* 176:206 */     return this.lattice;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Set<String> getPositives()
/* 180:    */   {
/* 181:210 */     return this.positiveAncestors;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Set<String> getNegatives()
/* 185:    */   {
/* 186:214 */     return this.negativeAncestors;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getName()
/* 190:    */   {
/* 191:218 */     return this.conceptName;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public static void main(String[] args) {}
/* 195:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.FasterLLConcept
 * JD-Core Version:    0.7.0.1
 */