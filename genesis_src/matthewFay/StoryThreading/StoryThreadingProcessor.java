/*   1:    */ package matthewFay.StoryThreading;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Collections;
/*  11:    */ import java.util.Comparator;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Set;
/*  15:    */ import javax.swing.JCheckBox;
/*  16:    */ import matthewFay.StoryAlignment.Alignment;
/*  17:    */ import matthewFay.StoryAlignment.AlignmentProcessor;
/*  18:    */ import matthewFay.Utilities.EntityHelper;
/*  19:    */ import matthewFay.Utilities.HashMatrix;
/*  20:    */ import minilisp.LList;
/*  21:    */ import utils.Mark;
/*  22:    */ import utils.PairOfEntities;
/*  23:    */ 
/*  24:    */ public class StoryThreadingProcessor
/*  25:    */   extends AbstractWiredBox
/*  26:    */ {
/*  27:    */   public static final String STORY_INPUT_PORT = "story input port";
/*  28:    */   public static final String STORY_SITCHED_OUTPUT_PORT = "story stitched output port";
/*  29:    */   public static final String COMPARISON_PORT = "comparison port";
/*  30: 27 */   private static String THREADING_PROCESSOR = "Story Threading Processor";
/*  31:    */   
/*  32:    */   public StoryThreadingProcessor()
/*  33:    */   {
/*  34: 29 */     setName(THREADING_PROCESSOR);
/*  35:    */     
/*  36: 31 */     Connections.getPorts(this).addSignalProcessor("story input port", "processStory");
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void processStory(Object input)
/*  40:    */   {
/*  41: 35 */     Sequence story = (Sequence)input;
/*  42:    */     
/*  43: 37 */     String story_title = EntityHelper.getStoryTitle(story);
/*  44: 38 */     HashMap<Entity, Sequence> threads = getStoryThreads(story);
/*  45:    */     
/*  46: 40 */     Sequence stitchedStory = stitchStory(threads);
/*  47:    */     
/*  48: 42 */     Connections.getPorts(this).transmit(new BetterSignal(new Object[] { threads }));
/*  49: 43 */     Connections.getPorts(this).transmit("story stitched output port", new BetterSignal(new Object[] { stitchedStory }));
/*  50: 46 */     for (Entity entity : threads.keySet())
/*  51:    */     {
/*  52: 47 */       this.entityToStory.put(entity, story_title);
/*  53: 48 */       this.allEntities.add(entity);
/*  54:    */     }
/*  55: 50 */     if (this.storyThreads.containsKey(story_title)) {
/*  56: 51 */       this.storyThreads.remove(story_title);
/*  57:    */     }
/*  58: 52 */     this.storyThreads.put(story_title, threads);
/*  59: 54 */     if (StoryThreadingViewer.doMinimumSpanningStory.isSelected())
/*  60:    */     {
/*  61: 56 */       MinimumSpanningCharaterSet mscs = new MinimumSpanningCharaterSet(threads);
/*  62: 57 */       mscs.constructStoryGraph();
/*  63: 58 */       Mark.say(new Object[] {"Done with MSCS" });
/*  64:    */     }
/*  65: 60 */     if (StoryThreadingViewer.doCompareAllEntities.isSelected()) {
/*  66: 61 */       compareAllEntities();
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70: 64 */   public ArrayList<Entity> allEntities = new ArrayList();
/*  71: 65 */   public HashMap<Entity, String> entityToStory = new HashMap();
/*  72: 66 */   public HashMap<String, HashMap<Entity, Sequence>> storyThreads = new HashMap();
/*  73:    */   
/*  74:    */   public void compareAllEntities()
/*  75:    */   {
/*  76: 70 */     HashMatrix<Entity, Entity, Float> similarity = new HashMatrix();
/*  77: 71 */     for (int i = 0; i < this.allEntities.size(); i++) {
/*  78: 72 */       for (int j = i; j < this.allEntities.size(); j++)
/*  79:    */       {
/*  80: 73 */         Entity entity_i = (Entity)this.allEntities.get(i);
/*  81: 74 */         Entity entity_j = (Entity)this.allEntities.get(j);
/*  82: 75 */         String story_title_i = (String)this.entityToStory.get(entity_i);
/*  83: 76 */         String story_title_j = (String)this.entityToStory.get(entity_j);
/*  84: 77 */         Sequence thread_i = (Sequence)((HashMap)this.storyThreads.get(story_title_i)).get(entity_i);
/*  85: 78 */         Sequence thread_j = (Sequence)((HashMap)this.storyThreads.get(story_title_j)).get(entity_j);
/*  86:    */         
/*  87:    */ 
/*  88: 81 */         AlignmentProcessor ap = new AlignmentProcessor();
/*  89: 82 */         Mark.say(new Object[] {"Aligning: ", entity_i.asString(), " and ", entity_j.asString() });
/*  90: 83 */         LList<PairOfEntities> bindings = new LList();
/*  91: 84 */         bindings = bindings.cons(new PairOfEntities(entity_i, entity_j));
/*  92: 85 */         float score = ap.alignStories(thread_i, thread_j, bindings).score;
/*  93: 86 */         similarity.put(entity_i, entity_j, Float.valueOf(score));
/*  94: 87 */         if (!similarity.contains(entity_j, entity_i)) {
/*  95: 88 */           similarity.put(entity_j, entity_i, Float.valueOf(score));
/*  96:    */         }
/*  97:    */       }
/*  98:    */     }
/*  99: 91 */     Connections.getPorts(this).transmit("comparison port", new BetterSignal(new Object[] { similarity }));
/* 100:    */     
/* 101: 93 */     String csv = "";
/* 102: 94 */     for (int i = 0; i < this.allEntities.size(); i++)
/* 103:    */     {
/* 104: 95 */       Entity entity_i = (Entity)this.allEntities.get(i);
/* 105: 96 */       if (csv.isEmpty()) {
/* 106: 97 */         csv = entity_i.asString();
/* 107:    */       } else {
/* 108: 99 */         csv = csv + "," + entity_i.asString();
/* 109:    */       }
/* 110:    */     }
/* 111:102 */     for (int j = 0; j < this.allEntities.size(); j++)
/* 112:    */     {
/* 113:103 */       Entity entity_j = (Entity)this.allEntities.get(j);
/* 114:104 */       csv = csv + "\n" + entity_j.asString();
/* 115:105 */       for (int i = 0; i < this.allEntities.size(); i++)
/* 116:    */       {
/* 117:106 */         Entity entity_i = (Entity)this.allEntities.get(i);
/* 118:107 */         if (similarity.contains(entity_i, entity_j))
/* 119:    */         {
/* 120:108 */           float score = ((Float)similarity.get(entity_j, entity_i)).floatValue();
/* 121:109 */           csv = csv + "," + score;
/* 122:110 */           Mark.say(new Object[] {entity_j.asString(), " : ", entity_i.asString(), " = ", Float.valueOf(score) });
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:114 */     Mark.say(new Object[] {csv });
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean containsEntity(Entity story, Entity entity)
/* 130:    */   {
/* 131:118 */     if ((story.getType().equals("appear")) && 
/* 132:119 */       (story.functionP()) && 
/* 133:120 */       (story.getSubject().isA("gap"))) {
/* 134:121 */       return false;
/* 135:    */     }
/* 136:123 */     if (story.functionP())
/* 137:    */     {
/* 138:124 */       Entity subject = story.getSubject();
/* 139:125 */       if (containsEntity(subject, entity)) {
/* 140:126 */         return true;
/* 141:    */       }
/* 142:    */     }
/* 143:127 */     else if (story.featureP())
/* 144:    */     {
/* 145:128 */       Mark.say(new Object[] {"Features not handled yet..." });
/* 146:    */     }
/* 147:129 */     else if (story.relationP())
/* 148:    */     {
/* 149:130 */       if (story.getSubject().entityP("you"))
/* 150:    */       {
/* 151:131 */         if (!story.getObject().functionP("story")) {
/* 152:131 */           story.getObject().functionP("reflection");
/* 153:    */         }
/* 154:    */       }
/* 155:134 */       else if (!story.isA("classification"))
/* 156:    */       {
/* 157:135 */         Entity subject = story.getSubject();
/* 158:136 */         Entity object = story.getObject();
/* 159:137 */         if (containsEntity(subject, entity)) {
/* 160:138 */           return true;
/* 161:    */         }
/* 162:139 */         if (containsEntity(object, entity)) {
/* 163:140 */           return true;
/* 164:    */         }
/* 165:    */       }
/* 166:    */     }
/* 167:142 */     else if (story.sequenceP())
/* 168:    */     {
/* 169:143 */       for (int i = 0; i < story.getNumberOfChildren(); i++) {
/* 170:144 */         if (containsEntity(story.getElement(i), entity)) {
/* 171:145 */           return true;
/* 172:    */         }
/* 173:    */       }
/* 174:    */     }
/* 175:147 */     else if ((story.entityP()) && 
/* 176:148 */       (story.equals(entity)))
/* 177:    */     {
/* 178:149 */       return true;
/* 179:    */     }
/* 180:151 */     return false;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public int countEntities(Entity story)
/* 184:    */   {
/* 185:155 */     List<Entity> entities = EntityHelper.getAllEntities(story);
/* 186:156 */     return entities.size();
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Sequence stitchStory(HashMap<Entity, Sequence> threads)
/* 190:    */   {
/* 191:160 */     Sequence story = new Sequence();
/* 192:161 */     ArrayList<Entity> entities = new ArrayList();
/* 193:162 */     HashMap<Entity, Integer> thread_its = new HashMap();
/* 194:163 */     for (Object entity : threads.keySet().toArray())
/* 195:    */     {
/* 196:164 */       entities.add((Entity)entity);
/* 197:165 */       thread_its.put((Entity)entity, Integer.valueOf(0));
/* 198:    */     }
/* 199:167 */     Collections.sort(entities, new Comparator()
/* 200:    */     {
/* 201:    */       public int compare(Entity o1, Entity o2)
/* 202:    */       {
/* 203:171 */         return o1.asString().compareTo(o2.asString());
/* 204:    */       }
/* 205:175 */     });
/* 206:176 */     int e_it = 0;
/* 207:177 */     Object event_occurances = new HashMap();
/* 208:178 */     while (entities.size() > 0)
/* 209:    */     {
/* 210:179 */       if (e_it >= entities.size()) {
/* 211:180 */         e_it = 0;
/* 212:    */       }
/* 213:181 */       Entity entity = (Entity)entities.get(e_it);
/* 214:182 */       int thread_it = ((Integer)thread_its.get(entity)).intValue();
/* 215:183 */       if (thread_it >= ((Sequence)threads.get(entity)).getNumberOfChildren())
/* 216:    */       {
/* 217:184 */         entities.remove(e_it);
/* 218:    */       }
/* 219:    */       else
/* 220:    */       {
/* 221:187 */         Entity event = ((Sequence)threads.get(entity)).getElement(thread_it);
/* 222:188 */         int e_count = countEntities(event);
/* 223:    */         
/* 224:190 */         int occurances = 0;
/* 225:191 */         if (((HashMap)event_occurances).containsKey(event)) {
/* 226:192 */           occurances = ((Integer)((HashMap)event_occurances).get(event)).intValue();
/* 227:    */         }
/* 228:194 */         occurances++;
/* 229:195 */         if (occurances == e_count)
/* 230:    */         {
/* 231:197 */           story.addElement(event);
/* 232:198 */           thread_it++;
/* 233:199 */           thread_its.remove(entity);
/* 234:200 */           thread_its.put(entity, Integer.valueOf(thread_it));
/* 235:201 */           ((HashMap)event_occurances).remove(event);
/* 236:202 */           ((HashMap)event_occurances).put(event, Integer.valueOf(occurances));
/* 237:    */         }
/* 238:204 */         else if (occurances > e_count)
/* 239:    */         {
/* 240:206 */           thread_it++;
/* 241:207 */           thread_its.remove(entity);
/* 242:208 */           thread_its.put(entity, Integer.valueOf(thread_it));
/* 243:209 */           ((HashMap)event_occurances).remove(event);
/* 244:210 */           ((HashMap)event_occurances).put(event, Integer.valueOf(occurances));
/* 245:    */         }
/* 246:    */         else
/* 247:    */         {
/* 248:214 */           ((HashMap)event_occurances).remove(event);
/* 249:215 */           ((HashMap)event_occurances).put(event, Integer.valueOf(occurances));
/* 250:216 */           e_it++;
/* 251:    */         }
/* 252:    */       }
/* 253:    */     }
/* 254:220 */     return story;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public HashMap<Entity, Sequence> getStoryThreads(Sequence story)
/* 258:    */   {
/* 259:226 */     List<Entity> entities = EntityHelper.getAllEntities(story);
/* 260:    */     
/* 261:    */ 
/* 262:    */ 
/* 263:230 */     HashMap<Entity, Sequence> threads = new HashMap();
/* 264:231 */     for (Entity entity : entities)
/* 265:    */     {
/* 266:232 */       Sequence st = new Sequence();
/* 267:233 */       for (int i = 0; i < story.getNumberOfChildren(); i++) {
/* 268:234 */         if (containsEntity(story.getElement(i), entity)) {
/* 269:235 */           st.addElement(story.getElement(i));
/* 270:    */         }
/* 271:    */       }
/* 272:238 */       threads.put(entity, st);
/* 273:    */     }
/* 274:241 */     return threads;
/* 275:    */   }
/* 276:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryThreading.StoryThreadingProcessor
 * JD-Core Version:    0.7.0.1
 */