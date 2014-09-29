/*   1:    */ package start;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public class StartEntity
/*   6:    */   extends RoleFrame
/*   7:    */ {
/*   8: 14 */   private ArrayList<String> features = new ArrayList();
/*   9: 16 */   private ArrayList<Restriction> restrictions = new ArrayList();
/*  10: 18 */   private ArrayList<String> relations = new ArrayList();
/*  11: 20 */   private String determiner = "definite";
/*  12: 22 */   private String number = null;
/*  13: 24 */   private Object possessor = null;
/*  14: 26 */   private ArrayList<RoleFrame> thats = new ArrayList();
/*  15: 28 */   private ArrayList<RoleFrame> whiches = new ArrayList();
/*  16: 30 */   private ArrayList<RoleFrame> whos = new ArrayList();
/*  17: 32 */   private ArrayList<RoleFrame> whoms = new ArrayList();
/*  18:    */   
/*  19:    */   public static StartEntity makeStartEntity(Object object)
/*  20:    */   {
/*  21: 35 */     return new StartEntity(object);
/*  22:    */   }
/*  23:    */   
/*  24:    */   public StartEntity(Object object)
/*  25:    */   {
/*  26: 39 */     super(object);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public StartEntity restrict(String preposition, StartEntity startEntity)
/*  30:    */   {
/*  31: 43 */     this.restrictions.add(new Restriction(preposition, startEntity));
/*  32: 44 */     return this;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public StartEntity that(RoleFrame that)
/*  36:    */   {
/*  37: 48 */     this.thats.add(that);
/*  38: 49 */     return this;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public StartEntity which(RoleFrame which)
/*  42:    */   {
/*  43: 53 */     this.whiches.add(which);
/*  44: 54 */     return this;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public StartEntity who(RoleFrame who)
/*  48:    */   {
/*  49: 58 */     this.whos.add(who);
/*  50: 59 */     return this;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public StartEntity whom(RoleFrame whom)
/*  54:    */   {
/*  55: 63 */     this.whoms.add(whom);
/*  56: 64 */     return this;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public StartEntity feature(String feature)
/*  60:    */   {
/*  61: 68 */     return addFeature(feature);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public StartEntity addFeature(String feature)
/*  65:    */   {
/*  66: 74 */     this.features.add(feature);
/*  67: 75 */     return this;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public StartEntity indefinite()
/*  71:    */   {
/*  72: 79 */     return makeIndefinite();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public StartEntity makeIndefinite()
/*  76:    */   {
/*  77: 84 */     this.determiner = "indefinite";
/*  78: 85 */     return this;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public StartEntity definite()
/*  82:    */   {
/*  83: 89 */     return makeDefinite();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public StartEntity makeDefinite()
/*  87:    */   {
/*  88: 94 */     this.determiner = "definite";
/*  89: 95 */     return this;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public StartEntity noDeterminer()
/*  93:    */   {
/*  94: 99 */     return makeNoDeterminer();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public StartEntity makeNoDeterminer()
/*  98:    */   {
/*  99:104 */     this.determiner = "null";
/* 100:105 */     return this;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public StartEntity another()
/* 104:    */   {
/* 105:109 */     return makeAnother();
/* 106:    */   }
/* 107:    */   
/* 108:    */   public StartEntity makeAnother()
/* 109:    */   {
/* 110:113 */     makeNoDeterminer();
/* 111:114 */     addFeature("another");
/* 112:115 */     return this;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public StartEntity plural()
/* 116:    */   {
/* 117:119 */     return makePlural();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public StartEntity makePlural()
/* 121:    */   {
/* 122:124 */     this.number = "plural";
/* 123:125 */     return this;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public StartEntity singular()
/* 127:    */   {
/* 128:129 */     return makeSingular();
/* 129:    */   }
/* 130:    */   
/* 131:    */   public StartEntity makeSingular()
/* 132:    */   {
/* 133:134 */     this.number = "singular";
/* 134:135 */     return this;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public StartEntity possessor(Object possessor)
/* 138:    */   {
/* 139:141 */     this.possessor = possessor;
/* 140:142 */     return this;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public StartEntity makePossessor(Object possessor)
/* 144:    */   {
/* 145:146 */     return possessor(possessor);
/* 146:    */   }
/* 147:    */   
/* 148:    */   public StartEntity addPossessor(Object possessor)
/* 149:    */   {
/* 150:150 */     return possessor(extractHead(possessor));
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String getRendering()
/* 154:    */   {
/* 155:154 */     String rendering = this.rendering;
/* 156:155 */     rendering = rendering + makeProperty(this.head, "has_det", this.determiner);
/* 157:156 */     for (String feature : this.features) {
/* 158:157 */       rendering = rendering + makeProperty(this.head, extractHead("has_property"), feature);
/* 159:    */     }
/* 160:159 */     if (this.number != null) {
/* 161:160 */       rendering = rendering + makeProperty(this.head, "has_number", this.number);
/* 162:    */     }
/* 163:162 */     if (this.possessor != null) {
/* 164:163 */       rendering = rendering + makeProperty(this.head, "related-to", extractHead(this.possessor));
/* 165:    */     }
/* 166:165 */     for (RoleFrame r : this.thats)
/* 167:    */     {
/* 168:166 */       rendering = rendering + makeProperty(this.head, extractHead("has_rel_clause"), r.getHead());
/* 169:167 */       rendering = rendering + makeProperty(r.getHead(), "has_clause_type", "that");
/* 170:168 */       rendering = rendering + r.getRendering();
/* 171:    */     }
/* 172:170 */     for (RoleFrame r : this.whiches)
/* 173:    */     {
/* 174:171 */       rendering = rendering + makeProperty(this.head, extractHead("has_rel_clause"), r.getHead());
/* 175:172 */       rendering = rendering + makeProperty(r.getHead(), "has_clause_type", "which");
/* 176:173 */       rendering = rendering + r.getRendering();
/* 177:    */     }
/* 178:176 */     for (RoleFrame r : this.whos)
/* 179:    */     {
/* 180:177 */       rendering = rendering + makeProperty(this.head, extractHead("has_rel_clause"), r.getHead());
/* 181:178 */       rendering = rendering + makeProperty(r.getHead(), "has_clause_type", "who");
/* 182:179 */       rendering = rendering + r.getRendering();
/* 183:    */     }
/* 184:182 */     for (RoleFrame r : this.whoms)
/* 185:    */     {
/* 186:183 */       rendering = rendering + makeProperty(this.head, extractHead("has_rel_clause"), r.getHead());
/* 187:184 */       rendering = rendering + makeProperty(r.getHead(), "has_clause_type", "whom");
/* 188:185 */       rendering = rendering + r.getRendering();
/* 189:    */     }
/* 190:187 */     for (Restriction r : this.restrictions)
/* 191:    */     {
/* 192:188 */       rendering = rendering + makeProperty(this.head, r.getConnection(), r.getEntity().getHead());
/* 193:189 */       rendering = rendering + r.getEntity().getRendering();
/* 194:    */     }
/* 195:191 */     return rendering;
/* 196:    */   }
/* 197:    */   
/* 198:    */   private class Restriction
/* 199:    */   {
/* 200:    */     private String connection;
/* 201:    */     private StartEntity startEntity;
/* 202:    */     
/* 203:    */     public String getConnection()
/* 204:    */     {
/* 205:198 */       return this.connection;
/* 206:    */     }
/* 207:    */     
/* 208:    */     public StartEntity getEntity()
/* 209:    */     {
/* 210:202 */       return this.startEntity;
/* 211:    */     }
/* 212:    */     
/* 213:    */     public Restriction(String connection, StartEntity startEntity)
/* 214:    */     {
/* 215:208 */       this.connection = connection;
/* 216:209 */       this.startEntity = startEntity;
/* 217:    */     }
/* 218:    */     
/* 219:    */     public String toString()
/* 220:    */     {
/* 221:213 */       return "[" + this.connection + " " + this.startEntity.getRendering() + "]";
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public static void main(String[] ignore)
/* 226:    */     throws Exception
/* 227:    */   {
/* 228:219 */     Generator generator = Generator.getGenerator();
/* 229:    */     
/* 230:221 */     StartEntity x = new StartEntity("man-1").addFeature("tall");
/* 231:222 */     StartEntity y = new StartEntity("man-2").addFeature("short");
/* 232:223 */     StartEntity c = new StartEntity("woman");
/* 233:224 */     StartEntity p1 = new StartEntity("package");
/* 234:225 */     StartEntity p2 = new StartEntity("package");
/* 235:226 */     StartEntity p3 = new StartEntity("package").restrict("with", new StartEntity("ribbon").indefinite());
/* 236:227 */     StartEntity d = new StartEntity("child");
/* 237:    */     
/* 238:229 */     p1.that(new RoleFrame(c, "bring", p1).past());
/* 239:    */     
/* 240:231 */     p2.which(new RoleFrame(c, "bring", p2).past());
/* 241:    */     
/* 242:233 */     d.whom(new RoleFrame(c, "bring", d).past());
/* 243:    */     
/* 244:    */ 
/* 245:    */ 
/* 246:    */ 
/* 247:    */ 
/* 248:239 */     RoleFrame g1That = new RoleFrame(x, "give", p1).past();
/* 249:    */     
/* 250:241 */     g1That.addRole("to", y);
/* 251:    */     
/* 252:243 */     g1That.addModifier("now");
/* 253:    */     
/* 254:245 */     RoleFrame g2Which = new RoleFrame(x, "give", p2).past();
/* 255:    */     
/* 256:247 */     g2Which.addRole("to", y);
/* 257:    */     
/* 258:249 */     g2Which.addModifier("now");
/* 259:    */     
/* 260:251 */     RoleFrame g3 = new RoleFrame(y, "kiss", d).past();
/* 261:253 */     for (int i = 0; i < 10; i++) {
/* 262:266 */       generator.test(g3, "The short man kissed the child whom the woman brought.", true);
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void reset()
/* 267:    */   {
/* 268:272 */     this.thats.clear();
/* 269:273 */     this.whiches.clear();
/* 270:274 */     this.whos.clear();
/* 271:275 */     this.whoms.clear();
/* 272:    */   }
/* 273:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     start.StartEntity
 * JD-Core Version:    0.7.0.1
 */