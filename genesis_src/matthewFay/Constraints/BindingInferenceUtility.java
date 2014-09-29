/*   1:    */ package matthewFay.Constraints;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import com.google.common.collect.HashMultimap;
/*   5:    */ import com.google.common.collect.ImmutableList;
/*   6:    */ import com.google.common.collect.Multimap;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.Collections;
/*  10:    */ import java.util.Comparator;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Set;
/*  15:    */ import matthewFay.Utilities.EntityHelper;
/*  16:    */ import utils.Mark;
/*  17:    */ import utils.PairOfEntities;
/*  18:    */ 
/*  19:    */ public class BindingInferenceUtility
/*  20:    */ {
/*  21:    */   private Multimap<Entity, Entity> binding_map;
/*  22:    */   private Multimap<Entity, Entity> inverse_binding_map;
/*  23:    */   private Set<Entity> known_entities;
/*  24:    */   private Set<Entity> required_entities;
/*  25:    */   private Set<Entity> all_targets;
/*  26:    */   
/*  27:    */   public BindingInferenceUtility(Iterable<PairOfEntities> bindings)
/*  28:    */   {
/*  29: 31 */     this.binding_map = HashMultimap.create();
/*  30: 32 */     this.inverse_binding_map = HashMultimap.create();
/*  31:    */     
/*  32: 34 */     this.known_entities = new HashSet();
/*  33: 35 */     this.required_entities = new HashSet();
/*  34: 36 */     this.all_targets = new HashSet();
/*  35:    */     
/*  36: 38 */     add(bindings);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void add(Iterable<PairOfEntities> bindings)
/*  40:    */   {
/*  41: 42 */     for (PairOfEntities binding : bindings) {
/*  42: 43 */       add(binding);
/*  43:    */     }
/*  44:    */   }
/*  45:    */   
/*  46:    */   public void add(PairOfEntities binding)
/*  47:    */   {
/*  48: 48 */     Entity pattern = binding.getPattern();
/*  49: 49 */     Entity datum = binding.getDatum();
/*  50:    */     
/*  51: 51 */     this.known_entities.add(pattern);
/*  52: 52 */     this.known_entities.add(datum);
/*  53:    */     
/*  54: 54 */     this.binding_map.put(pattern, datum);
/*  55: 55 */     this.inverse_binding_map.put(datum, pattern);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void inferEquivalentGenerics()
/*  59:    */   {
/*  60: 63 */     List<PairOfEntities> new_bindings = new ArrayList();
/*  61: 65 */     for (Entity datum : this.inverse_binding_map.keySet()) {
/*  62: 66 */       if (!EntityHelper.isGeneric(datum))
/*  63:    */       {
/*  64: 67 */         List<Entity> generic_entities = new ArrayList(this.inverse_binding_map.get(datum));
/*  65: 69 */         for (int i = 0; i < generic_entities.size(); i++)
/*  66:    */         {
/*  67: 70 */           Entity generic_datum_i = (Entity)generic_entities.get(i);
/*  68: 72 */           if (EntityHelper.isGeneric(generic_datum_i)) {
/*  69: 73 */             for (int j = i + 1; j < generic_entities.size(); j++)
/*  70:    */             {
/*  71: 74 */               Entity generic_datum_j = (Entity)generic_entities.get(j);
/*  72: 76 */               if (EntityHelper.isGeneric(generic_datum_j))
/*  73:    */               {
/*  74: 77 */                 new_bindings.add(new PairOfEntities(generic_datum_i, generic_datum_j));
/*  75: 78 */                 new_bindings.add(new PairOfEntities(generic_datum_j, generic_datum_i));
/*  76:    */               }
/*  77:    */             }
/*  78:    */           }
/*  79:    */         }
/*  80:    */       }
/*  81:    */     }
/*  82: 86 */     add(new_bindings);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void inferTargetsFromChaining()
/*  86:    */   {
/*  87: 94 */     List<PairOfEntities> new_bindings = new ArrayList();
/*  88: 97 */     for (Entity generic_pattern : this.binding_map.keySet())
/*  89:    */     {
/*  90: 98 */       List<Entity> completed_entities = new ArrayList();
/*  91: 99 */       completed_entities.add(generic_pattern);
/*  92:100 */       if (EntityHelper.isGeneric(generic_pattern)) {
/*  93:102 */         for (Entity target_datum : this.binding_map.get(generic_pattern)) {
/*  94:103 */           if (!EntityHelper.isGeneric(target_datum)) {
/*  95:104 */             for (Entity generic_datum : this.binding_map.get(generic_pattern)) {
/*  96:105 */               if (EntityHelper.isGeneric(generic_datum)) {
/*  97:106 */                 new_bindings.addAll(fillTargetsFromChaining(generic_datum, target_datum, completed_entities));
/*  98:    */               }
/*  99:    */             }
/* 100:    */           }
/* 101:    */         }
/* 102:    */       }
/* 103:    */     }
/* 104:114 */     add(new_bindings);
/* 105:    */   }
/* 106:    */   
/* 107:    */   private List<PairOfEntities> fillTargetsFromChaining(Entity generic, Entity target, List<Entity> completed_entities)
/* 108:    */   {
/* 109:118 */     List<PairOfEntities> new_bindings = new ArrayList();
/* 110:120 */     if (completed_entities.contains(generic)) {
/* 111:121 */       return new_bindings;
/* 112:    */     }
/* 113:122 */     completed_entities.add(generic);
/* 114:    */     
/* 115:124 */     new_bindings.add(new PairOfEntities(generic, target));
/* 116:125 */     for (Entity datum : this.binding_map.get(generic)) {
/* 117:126 */       if (EntityHelper.isGeneric(datum)) {
/* 118:127 */         new_bindings.addAll(fillTargetsFromChaining(datum, target, completed_entities));
/* 119:129 */       } else if (!target.equals(datum)) {
/* 120:130 */         Mark.err(new Object[] {"Binding Conflict!!" });
/* 121:    */       }
/* 122:    */     }
/* 123:135 */     return new_bindings;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void filterBindings(List<Entity> pattern_filter, List<Entity> datum_filter)
/* 127:    */   {
/* 128:139 */     List<Entity> pattern_keys = new ArrayList(this.binding_map.keySet());
/* 129:140 */     List<Entity> datum_keys = new ArrayList(this.inverse_binding_map.keySet());
/* 130:142 */     for (Entity pattern : pattern_keys) {
/* 131:143 */       if (!pattern_filter.contains(pattern))
/* 132:    */       {
/* 133:145 */         List<Entity> datums = new ArrayList(this.binding_map.get(pattern));
/* 134:146 */         for (Entity datum : datums)
/* 135:    */         {
/* 136:147 */           this.binding_map.remove(pattern, datum);
/* 137:148 */           this.inverse_binding_map.remove(datum, pattern);
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:153 */     for (Entity datum : datum_keys) {
/* 142:154 */       if (!datum_filter.contains(datum))
/* 143:    */       {
/* 144:155 */         List<Entity> patterns = new ArrayList(this.inverse_binding_map.get(datum));
/* 145:156 */         for (Entity pattern : patterns)
/* 146:    */         {
/* 147:157 */           this.binding_map.remove(pattern, datum);
/* 148:158 */           this.inverse_binding_map.remove(datum, pattern);
/* 149:    */         }
/* 150:    */       }
/* 151:    */     }
/* 152:163 */     this.known_entities.clear();
/* 153:164 */     for (Entity pattern : this.binding_map.keySet()) {
/* 154:165 */       this.known_entities.add(pattern);
/* 155:    */     }
/* 156:167 */     for (Entity datum : this.inverse_binding_map.keySet()) {
/* 157:168 */       this.known_entities.add(datum);
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void requireBindingsFor(List<Entity> entities)
/* 162:    */   {
/* 163:173 */     for (Entity entity : entities) {
/* 164:174 */       requireBindingsFor(entity);
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void requireBindingsFor(Entity entity)
/* 169:    */   {
/* 170:179 */     this.required_entities.add(entity);
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void addTargets(List<Entity> targets)
/* 174:    */   {
/* 175:183 */     for (Entity target : targets) {
/* 176:184 */       addTarget(target);
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void addTarget(Entity target)
/* 181:    */   {
/* 182:189 */     this.all_targets.add(target);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public List<PairOfEntities> getTwoWayBindings()
/* 186:    */   {
/* 187:193 */     List<PairOfEntities> bindings = getBindings();
/* 188:    */     
/* 189:195 */     List<PairOfEntities> all_bindings = new ArrayList();
/* 190:197 */     for (PairOfEntities binding : bindings)
/* 191:    */     {
/* 192:198 */       if (!all_bindings.contains(binding)) {
/* 193:199 */         all_bindings.add(binding);
/* 194:    */       }
/* 195:200 */       PairOfEntities inv = new PairOfEntities(binding.getDatum(), binding.getPattern());
/* 196:201 */       if (!all_bindings.contains(inv)) {
/* 197:202 */         all_bindings.add(inv);
/* 198:    */       }
/* 199:    */     }
/* 200:205 */     Collections.sort(all_bindings, new Comparator()
/* 201:    */     {
/* 202:    */       public int compare(PairOfEntities o1, PairOfEntities o2)
/* 203:    */       {
/* 204:209 */         String s1 = o1.toString();
/* 205:210 */         String s2 = o2.toString();
/* 206:    */         
/* 207:212 */         return s1.compareTo(s2);
/* 208:    */       }
/* 209:216 */     });
/* 210:217 */     return all_bindings;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<PairOfEntities> getBindings()
/* 214:    */   {
/* 215:221 */     List<PairOfEntities> bindings = new ArrayList();
/* 216:    */     
/* 217:    */ 
/* 218:224 */     Set<Entity> all_entities = new HashSet();
/* 219:225 */     all_entities.addAll(this.known_entities);
/* 220:226 */     all_entities.addAll(this.required_entities);
/* 221:    */     Entity target;
/* 222:228 */     for (Entity required : this.required_entities) {
/* 223:229 */       if (!this.known_entities.contains(required))
/* 224:    */       {
/* 225:232 */         for (Entity target : all_entities)
/* 226:    */         {
/* 227:233 */           PairOfEntities binding = new PairOfEntities(required, target);
/* 228:234 */           if (!bindings.contains(binding)) {
/* 229:235 */             bindings.add(binding);
/* 230:    */           }
/* 231:236 */           binding = new PairOfEntities(target, required);
/* 232:237 */           if (!bindings.contains(binding)) {
/* 233:238 */             bindings.add(binding);
/* 234:    */           }
/* 235:    */         }
/* 236:240 */         for (??? = this.all_targets.iterator(); ???.hasNext();)
/* 237:    */         {
/* 238:240 */           target = (Entity)???.next();
/* 239:241 */           PairOfEntities binding = new PairOfEntities(required, target);
/* 240:242 */           if (!bindings.contains(binding)) {
/* 241:243 */             bindings.add(binding);
/* 242:    */           }
/* 243:244 */           binding = new PairOfEntities(target, required);
/* 244:245 */           if (!bindings.contains(binding)) {
/* 245:246 */             bindings.add(binding);
/* 246:    */           }
/* 247:    */         }
/* 248:    */       }
/* 249:    */     }
/* 250:251 */     for (??? = this.binding_map.keySet().iterator(); ???.hasNext(); target.hasNext())
/* 251:    */     {
/* 252:251 */       Entity pattern = (Entity)???.next();
/* 253:252 */       target = this.binding_map.get(pattern).iterator(); continue;Entity datum = (Entity)target.next();
/* 254:253 */       if (!pattern.equals(datum))
/* 255:    */       {
/* 256:254 */         PairOfEntities binding = new PairOfEntities(pattern, datum);
/* 257:255 */         if (!bindings.contains(binding)) {
/* 258:256 */           bindings.add(binding);
/* 259:    */         }
/* 260:    */       }
/* 261:    */     }
/* 262:262 */     Collections.sort(bindings, new Comparator()
/* 263:    */     {
/* 264:    */       public int compare(PairOfEntities o1, PairOfEntities o2)
/* 265:    */       {
/* 266:266 */         String s1 = o1.toString();
/* 267:267 */         String s2 = o2.toString();
/* 268:    */         
/* 269:269 */         return s1.compareTo(s2);
/* 270:    */       }
/* 271:273 */     });
/* 272:274 */     return bindings;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public static void main(String[] args)
/* 276:    */   {
/* 277:278 */     Entity gen0 = EntityHelper.getGenericEntity();
/* 278:279 */     Entity gen1 = EntityHelper.getGenericEntity();
/* 279:280 */     Entity gen2 = EntityHelper.getGenericEntity();
/* 280:281 */     Entity gen3 = EntityHelper.getGenericEntity();
/* 281:282 */     Entity gen4 = EntityHelper.getGenericEntity();
/* 282:283 */     Entity gen5 = EntityHelper.getGenericEntity();
/* 283:284 */     Entity gen6 = EntityHelper.getGenericEntity();
/* 284:    */     
/* 285:286 */     Entity mark = new Entity("mark");
/* 286:287 */     Entity mary = new Entity("mary");
/* 287:288 */     Entity sally = new Entity("sally");
/* 288:    */     
/* 289:290 */     List<PairOfEntities> bindings = new ArrayList();
/* 290:291 */     bindings.add(new PairOfEntities(gen0, mark));
/* 291:292 */     bindings.add(new PairOfEntities(gen1, mark));
/* 292:293 */     bindings.add(new PairOfEntities(gen2, mary));
/* 293:294 */     bindings.add(new PairOfEntities(gen3, mary));
/* 294:295 */     bindings.add(new PairOfEntities(gen3, gen5));
/* 295:296 */     bindings.add(new PairOfEntities(gen5, gen4));
/* 296:297 */     bindings.add(new PairOfEntities(gen4, mary));
/* 297:    */     
/* 298:299 */     BindingInferenceUtility biu = new BindingInferenceUtility(bindings);
/* 299:300 */     biu.inferTargetsFromChaining();
/* 300:301 */     biu.inferEquivalentGenerics();
/* 301:302 */     biu.inferTargetsFromChaining();
/* 302:303 */     biu.inferEquivalentGenerics();
/* 303:304 */     biu.inferTargetsFromChaining();
/* 304:305 */     biu.inferEquivalentGenerics();
/* 305:306 */     biu.requireBindingsFor(gen6);
/* 306:307 */     Mark.say(new Object[] {"Bindings: \n" + bindings });
/* 307:308 */     Mark.say(new Object[] {"Bindings after inference: \n" + biu.getBindings() });
/* 308:309 */     Mark.say(new Object[] {"Two Way Bindings after inference: \n" + biu.getTwoWayBindings() });
/* 309:    */     
/* 310:311 */     biu.filterBindings(ImmutableList.of(gen0), ImmutableList.of(mark));
/* 311:312 */     biu.addTarget(sally);
/* 312:313 */     Mark.say(new Object[] {"Bindings after filter: \n" + biu.getBindings() });
/* 313:314 */     Mark.say(new Object[] {"Two way Bindings after filter: \n" + biu.getTwoWayBindings() });
/* 314:    */   }
/* 315:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Constraints.BindingInferenceUtility
 * JD-Core Version:    0.7.0.1
 */