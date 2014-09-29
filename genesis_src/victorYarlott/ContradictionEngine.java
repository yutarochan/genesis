/*   1:    */ package victorYarlott;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.HashMap;
/*  11:    */ import java.util.HashSet;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import java.util.Set;
/*  15:    */ import start.Generator;
/*  16:    */ import text.Html;
/*  17:    */ 
/*  18:    */ public class ContradictionEngine
/*  19:    */   extends AbstractWiredBox
/*  20:    */ {
/*  21: 21 */   public final String CONTRADICTIONS_INPUT_PORT = "contradictions rx";
/*  22: 22 */   public final String CONTRADICTIONS_OUTPUT_PORT = "contradictions tx";
/*  23:    */   private boolean filter;
/*  24:    */   private List<String> filters;
/*  25:    */   
/*  26:    */   public ContradictionEngine()
/*  27:    */   {
/*  28: 29 */     this(false, new ArrayList());
/*  29:    */   }
/*  30:    */   
/*  31:    */   public ContradictionEngine(boolean filter, List<String> filters)
/*  32:    */   {
/*  33: 33 */     this.filter = filter;
/*  34: 34 */     this.filters = filters;
/*  35: 35 */     Connections.getPorts(this).addSignalProcessor("contradictions rx", "processSignal");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Set<Pair<Entity>> getContradictions(List<Entity> entities)
/*  39:    */   {
/*  40: 52 */     Map<Triple<String>, Entity> entityMap = new HashMap();
/*  41: 53 */     Set<Pair<Entity>> contradictions = new HashSet();
/*  42: 54 */     for (Entity e : entities) {
/*  43: 55 */       if (e.relationP())
/*  44:    */       {
/*  45: 56 */         Triple<String> etri = new Triple(e.getType(), e
/*  46: 57 */           .getSubject().asString(), e.getObject().asString());
/*  47: 58 */         if (entityMap.get(etri) != null)
/*  48:    */         {
/*  49: 62 */           if ((((Entity)entityMap.get(etri)).hasFeature("not") ^ e.hasFeature("not"))) {
/*  50: 64 */             contradictions.add(new Pair(
/*  51: 65 */               (Entity)entityMap.get(etri), e));
/*  52:    */           }
/*  53:    */         }
/*  54:    */         else {
/*  55: 68 */           entityMap.put(etri, e);
/*  56:    */         }
/*  57:    */       }
/*  58:    */     }
/*  59: 72 */     return contradictions;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean containsContradiction(List<Entity> entities)
/*  63:    */   {
/*  64: 90 */     Map<Triple<Object>, Entity> entityMap = new HashMap();
/*  65: 91 */     for (Entity e : entities) {
/*  66: 92 */       if (e.relationP())
/*  67:    */       {
/*  68: 93 */         Triple<Object> etri = new Triple(e.getType(), 
/*  69: 94 */           e.getSubject(), e.getObject());
/*  70: 95 */         if (entityMap.get(etri) != null) {
/*  71: 96 */           return true;
/*  72:    */         }
/*  73: 98 */         entityMap.put(etri, e);
/*  74:    */       }
/*  75:    */     }
/*  76:102 */     return false;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Set<Pair<Entity>> findContradictionsByType(List<Entity> entities, String type)
/*  80:    */   {
/*  81:115 */     return findContradictionsByType(getContradictions(entities), type);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Set<Pair<Entity>> findContradictionsByType(Set<Pair<Entity>> contradictions, String type)
/*  85:    */   {
/*  86:128 */     Set<Pair<Entity>> filteredContradictions = new HashSet();
/*  87:129 */     for (Pair<Entity> p : contradictions) {
/*  88:130 */       if (((Entity)p.getFirst()).getType().equals(type)) {
/*  89:131 */         filteredContradictions.add(p);
/*  90:    */       }
/*  91:    */     }
/*  92:134 */     return filteredContradictions;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String getContradictionType(Pair<Entity> pair)
/*  96:    */   {
/*  97:138 */     return ((Entity)pair.getFirst()).getType();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean getFilter()
/* 101:    */   {
/* 102:142 */     return this.filter;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setFilter(boolean filter)
/* 106:    */   {
/* 107:146 */     this.filter = filter;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<String> getFilters()
/* 111:    */   {
/* 112:150 */     return this.filters;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setFilters(List<String> filters)
/* 116:    */   {
/* 117:154 */     this.filters = filters;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void addFilter(String filter)
/* 121:    */   {
/* 122:158 */     this.filters.add(filter);
/* 123:    */   }
/* 124:    */   
/* 125:    */   public boolean removeFilter(String filter)
/* 126:    */   {
/* 127:162 */     return this.filters.remove(filter);
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void processSignal(Object signal)
/* 131:    */   {
/* 132:166 */     if ((signal instanceof BetterSignal))
/* 133:    */     {
/* 134:167 */       BetterSignal s = (BetterSignal)signal;
/* 135:168 */       Sequence story = (Sequence)s.get(0, Sequence.class);
/* 136:169 */       Sequence explicitElements = (Sequence)s.get(1, Sequence.class);
/* 137:170 */       Sequence inferences = (Sequence)s.get(2, Sequence.class);
/* 138:171 */       Sequence concepts = (Sequence)s.get(3, Sequence.class);
/* 139:    */       
/* 140:173 */       List<Entity> allElements = new ArrayList();
/* 141:174 */       allElements.addAll(story.getElements());
/* 142:175 */       allElements.addAll(explicitElements.getElements());
/* 143:176 */       allElements.addAll(inferences.getElements());
/* 144:177 */       allElements.addAll(concepts.getElements());
/* 145:179 */       if (this.filter) {
/* 146:180 */         transmitFilteredContradictions(allElements);
/* 147:    */       } else {
/* 148:182 */         transmitUnfilteredContradictions(allElements);
/* 149:    */       }
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   private void transmitFilteredContradictions(List<Entity> elements)
/* 154:    */   {
/* 155:188 */     for (String filter : this.filters)
/* 156:    */     {
/* 157:189 */       Set<Pair<Entity>> cset = findContradictionsByType(elements, filter);
/* 158:190 */       if (!cset.isEmpty())
/* 159:    */       {
/* 160:191 */         BetterSignal tx = new BetterSignal(new Object[] { "Contradictions", Html.h2("Displaying Contradictions of Type: " + filter) });
/* 161:192 */         Connections.getPorts(this).transmit("contradictions tx", tx);
/* 162:193 */         for (Pair<Entity> p : cset)
/* 163:    */         {
/* 164:194 */           tx = new BetterSignal(new Object[] { "Contradictions", prettyprint(p, false) });
/* 165:195 */           Connections.getPorts(this).transmit("contradictions tx", tx);
/* 166:    */         }
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   private void transmitUnfilteredContradictions(List<Entity> elements)
/* 172:    */   {
/* 173:202 */     Set<Pair<Entity>> cset = getContradictions(elements);
/* 174:203 */     for (Pair<Entity> p : cset)
/* 175:    */     {
/* 176:204 */       BetterSignal tx = new BetterSignal(new Object[] { "Contradictions", prettyprint(p) });
/* 177:205 */       Connections.getPorts(this).transmit("contradictions tx", tx);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public String prettyprint(Pair<Entity> pair)
/* 182:    */   {
/* 183:210 */     return prettyprint(pair, true);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String prettyprint(Pair<Entity> pair, boolean printType)
/* 187:    */   {
/* 188:214 */     String prettyString = "";
/* 189:215 */     Generator gen = Generator.getGenerator();
/* 190:216 */     if (printType) {
/* 191:217 */       prettyString = prettyString + Html.bold(Html.normal(new StringBuilder("Contradiction found (").append(getContradictionType(pair)).append("):\n").toString()));
/* 192:    */     } else {
/* 193:219 */       prettyString = prettyString + Html.bold(Html.normal("Contradiction found:\n"));
/* 194:    */     }
/* 195:221 */     prettyString = prettyString + Html.normal(new StringBuilder(">>> ").append(gen.generate((Entity)pair.getFirst())).append("\n").toString());
/* 196:222 */     prettyString = prettyString + Html.normal(new StringBuilder(">>> ").append(gen.generate((Entity)pair.getSecond())).append("\n").toString());
/* 197:223 */     return prettyString;
/* 198:    */   }
/* 199:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     victorYarlott.ContradictionEngine
 * JD-Core Version:    0.7.0.1
 */