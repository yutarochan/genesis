/*   1:    */ package matthewFay.Constraints;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import com.google.common.collect.HashMultimap;
/*   5:    */ import com.google.common.collect.Multimap;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import java.util.Set;
/*  14:    */ import matthewFay.Utilities.EntityHelper;
/*  15:    */ import matthewFay.representations.BasicCharacterModel;
/*  16:    */ import minilisp.LList;
/*  17:    */ import utils.Mark;
/*  18:    */ import utils.PairOfEntities;
/*  19:    */ 
/*  20:    */ public class ConstraintSet
/*  21:    */ {
/*  22:    */   private Multimap<Entity, Entity> all_constraints;
/*  23:    */   
/*  24:    */   public Multimap<Entity, Entity> getAllConstraints()
/*  25:    */   {
/*  26: 18 */     return this.all_constraints;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ConstraintSet()
/*  30:    */   {
/*  31: 22 */     this.all_constraints = HashMultimap.create();
/*  32:    */   }
/*  33:    */   
/*  34:    */   public ConstraintSet(LList<PairOfEntities> bindings)
/*  35:    */   {
/*  36: 26 */     this.all_constraints = HashMultimap.create();
/*  37:    */     
/*  38: 28 */     addConstraints(bindings);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public ConstraintSet(List<PairOfEntities> bindings)
/*  42:    */   {
/*  43: 32 */     this.all_constraints = HashMultimap.create();
/*  44:    */     
/*  45: 34 */     addConstraints(bindings);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public ConstraintSet(ConstraintSet cs)
/*  49:    */   {
/*  50: 38 */     this.all_constraints = HashMultimap.create();
/*  51:    */     
/*  52: 40 */     this.all_constraints.putAll(cs.getAllConstraints());
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void addConstraint(Entity e, Entity constraint)
/*  56:    */   {
/*  57: 44 */     this.all_constraints.put(e, constraint);
/*  58: 45 */     if ((EntityHelper.isGeneric(e)) && (EntityHelper.isGeneric(constraint))) {
/*  59: 46 */       this.all_constraints.put(constraint, e);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean validConstraints()
/*  64:    */   {
/*  65:    */     boolean ret;
/*  66: 51 */     for (Entity entity : this.all_constraints.keySet())
/*  67:    */     {
/*  68: 52 */       constraints = this.all_constraints.get(entity);
/*  69: 54 */       if (constraints.size() > 1)
/*  70:    */       {
/*  71: 58 */         if (!EntityHelper.isGeneric(entity)) {
/*  72: 59 */           return false;
/*  73:    */         }
/*  74: 61 */         Entity target = null;
/*  75: 62 */         for (Entity constraint : constraints) {
/*  76: 63 */           if (!EntityHelper.isGeneric(constraint)) {
/*  77: 64 */             if (target == null) {
/*  78: 65 */               target = constraint;
/*  79:    */             } else {
/*  80: 67 */               return false;
/*  81:    */             }
/*  82:    */           }
/*  83:    */         }
/*  84: 71 */         if (target == null)
/*  85:    */         {
/*  86: 73 */           boolean got_one = false;
/*  87: 75 */           for (Entity generic_entity : constraints) {
/*  88: 76 */             if ((target == null) && (!got_one))
/*  89:    */             {
/*  90: 77 */               target = findTarget(generic_entity, new ArrayList());
/*  91: 78 */               got_one = true;
/*  92:    */             }
/*  93:    */             else
/*  94:    */             {
/*  95: 80 */               Entity next_target = findTarget(generic_entity, new ArrayList());
/*  96: 81 */               if (((next_target != null) || (target != null)) && (
/*  97: 82 */                 (next_target == null) || (!next_target.equals(target)))) {
/*  98: 85 */                 return false;
/*  99:    */               }
/* 100:    */             }
/* 101:    */           }
/* 102:    */         }
/* 103:    */         else
/* 104:    */         {
/* 105: 90 */           this.generics_checked.clear();
/* 106: 91 */           ret = genericValidation(entity, target);
/* 107: 92 */           if (!ret) {
/* 108: 93 */             return false;
/* 109:    */           }
/* 110:    */         }
/* 111:    */       }
/* 112:    */     }
/* 113: 96 */     simplifyConstraints();
/* 114:    */     
/* 115: 98 */     Multimap<BasicCharacterModel, Entity> double_finder = HashMultimap.create();
/* 116: 99 */     for (Collection<Entity> constraints = this.all_constraints.keySet().iterator(); constraints.hasNext(); ret.hasNext())
/* 117:    */     {
/* 118: 99 */       Entity e = (Entity)constraints.next();
/* 119:100 */       ret = this.all_constraints.get(e).iterator(); continue;Entity target = (Entity)ret.next();
/* 120:101 */       if (!EntityHelper.isGeneric(target))
/* 121:    */       {
/* 122:102 */         BasicCharacterModel origin = BasicCharacterModel.getOriginatingCharacter(e);
/* 123:103 */         if (!double_finder.get(origin).contains(target)) {
/* 124:104 */           double_finder.put(origin, target);
/* 125:    */         } else {
/* 126:106 */           return false;
/* 127:    */         }
/* 128:    */       }
/* 129:    */     }
/* 130:112 */     return true;
/* 131:    */   }
/* 132:    */   
/* 133:115 */   private Set<Entity> generics_checked = new HashSet();
/* 134:    */   
/* 135:    */   private boolean genericValidation(Entity generic, Entity target)
/* 136:    */   {
/* 137:118 */     if (this.generics_checked.contains(generic)) {
/* 138:119 */       return true;
/* 139:    */     }
/* 140:120 */     this.generics_checked.add(generic);
/* 141:    */     
/* 142:122 */     boolean ret = true;
/* 143:123 */     for (Entity other_constraint : this.all_constraints.get(generic)) {
/* 144:124 */       if (EntityHelper.isGeneric(other_constraint)) {
/* 145:125 */         ret = (ret) && (genericValidation(other_constraint, target));
/* 146:    */       } else {
/* 147:127 */         ret = (ret) && (other_constraint.equals(target));
/* 148:    */       }
/* 149:    */     }
/* 150:130 */     return ret;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<PairOfEntities> toList()
/* 154:    */   {
/* 155:134 */     if (!simplifyConstraints()) {
/* 156:135 */       Mark.err(new Object[] {"Problematic Constraint Set!" });
/* 157:    */     }
/* 158:136 */     List<PairOfEntities> llist = new ArrayList();
/* 159:    */     Iterator localIterator2;
/* 160:137 */     for (Iterator localIterator1 = this.all_constraints.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 161:    */     {
/* 162:137 */       Entity entity = (Entity)localIterator1.next();
/* 163:138 */       localIterator2 = this.all_constraints.get(entity).iterator(); continue;Entity constraint = (Entity)localIterator2.next();
/* 164:139 */       llist.add(new PairOfEntities(entity, constraint));
/* 165:    */     }
/* 166:142 */     return llist;
/* 167:    */   }
/* 168:    */   
/* 169:    */   private boolean simplifyConstraints()
/* 170:    */   {
/* 171:146 */     Map<Entity, Entity> simplified_targets = new HashMap();
/* 172:147 */     for (Entity entity : this.all_constraints.keySet())
/* 173:    */     {
/* 174:148 */       Entity target = findTarget(entity, new ArrayList());
/* 175:149 */       if (target != null) {
/* 176:150 */         simplified_targets.put(entity, target);
/* 177:    */       }
/* 178:    */     }
/* 179:152 */     for (Entity entity : simplified_targets.keySet())
/* 180:    */     {
/* 181:153 */       this.all_constraints.removeAll(entity);
/* 182:154 */       this.all_constraints.put(entity, (Entity)simplified_targets.get(entity));
/* 183:    */     }
/* 184:156 */     return true;
/* 185:    */   }
/* 186:    */   
/* 187:    */   private Entity findTarget(Entity generic_entity, Collection<Entity> checked_generics)
/* 188:    */   {
/* 189:159 */     checked_generics.add(generic_entity);
/* 190:160 */     for (Entity possible_target : this.all_constraints.get(generic_entity))
/* 191:    */     {
/* 192:161 */       if (!EntityHelper.isGeneric(possible_target)) {
/* 193:162 */         return possible_target;
/* 194:    */       }
/* 195:164 */       if (!checked_generics.contains(possible_target))
/* 196:    */       {
/* 197:165 */         Entity target = findTarget(possible_target, checked_generics);
/* 198:166 */         if (target != null) {
/* 199:167 */           return target;
/* 200:    */         }
/* 201:    */       }
/* 202:    */     }
/* 203:171 */     return null;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void addConstraints(LList<PairOfEntities> bindings)
/* 207:    */   {
/* 208:175 */     for (PairOfEntities binding : bindings) {
/* 209:176 */       addConstraint(binding.getPattern(), binding.getDatum());
/* 210:    */     }
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void addConstraints(List<PairOfEntities> bindings)
/* 214:    */   {
/* 215:181 */     for (PairOfEntities binding : bindings) {
/* 216:182 */       addConstraint(binding.getPattern(), binding.getDatum());
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public boolean equals(Object o)
/* 221:    */   {
/* 222:188 */     if ((o instanceof ConstraintSet))
/* 223:    */     {
/* 224:189 */       ConstraintSet cs = (ConstraintSet)o;
/* 225:190 */       Multimap<Entity, Entity> all_constraints2 = cs.getAllConstraints();
/* 226:192 */       if (this.all_constraints.size() != all_constraints2.size()) {
/* 227:193 */         return false;
/* 228:    */       }
/* 229:    */       Iterator localIterator2;
/* 230:195 */       for (Iterator localIterator1 = this.all_constraints.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 231:    */       {
/* 232:195 */         Entity e = (Entity)localIterator1.next();
/* 233:196 */         if (!all_constraints2.containsKey(e)) {
/* 234:197 */           return false;
/* 235:    */         }
/* 236:198 */         localIterator2 = this.all_constraints.get(e).iterator(); continue;Entity constraint = (Entity)localIterator2.next();
/* 237:199 */         if (!all_constraints2.get(e).contains(constraint)) {
/* 238:200 */           return false;
/* 239:    */         }
/* 240:    */       }
/* 241:204 */       return true;
/* 242:    */     }
/* 243:206 */     return false;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public static void main(String[] args)
/* 247:    */   {
/* 248:210 */     ConstraintSet cs = new ConstraintSet();
/* 249:    */     
/* 250:212 */     Entity alpha = new Entity("alpha");
/* 251:213 */     Entity beta = new Entity("beta");
/* 252:214 */     Entity gamma = new Entity("gamma");
/* 253:215 */     Entity omega = new Entity("omega");
/* 254:    */     
/* 255:217 */     Entity gen0 = EntityHelper.getGenericEntity();
/* 256:218 */     Entity gen1 = EntityHelper.getGenericEntity();
/* 257:219 */     Entity gen2 = EntityHelper.getGenericEntity();
/* 258:220 */     Entity gen3 = EntityHelper.getGenericEntity();
/* 259:221 */     Entity gen4 = EntityHelper.getGenericEntity();
/* 260:222 */     Entity gen5 = EntityHelper.getGenericEntity();
/* 261:    */     
/* 262:224 */     cs.addConstraint(gen0, gen1);
/* 263:225 */     cs.addConstraint(gen2, gen1);
/* 264:226 */     cs.addConstraint(gen0, omega);
/* 265:227 */     cs.addConstraint(gen2, omega);
/* 266:228 */     cs.addConstraint(gen2, gen3);
/* 267:    */     
/* 268:230 */     cs.addConstraint(gen4, gen5);
/* 269:    */     
/* 270:    */ 
/* 271:233 */     Mark.say(new Object[] {"valid?: " + cs.validConstraints() });
/* 272:234 */     Mark.say(new Object[] {cs.toList() });
/* 273:235 */     cs.simplifyConstraints();
/* 274:236 */     Mark.say(new Object[] {cs.toList() });
/* 275:    */   }
/* 276:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Constraints.ConstraintSet
 * JD-Core Version:    0.7.0.1
 */