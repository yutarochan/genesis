/*   1:    */ package tools;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import bridge.reps.entities.Thread;
/*   8:    */ import utils.Mark;
/*   9:    */ 
/*  10:    */ public class Innerese
/*  11:    */ {
/*  12:    */   public static Relation makeBecause(Entity result, Entity... causes)
/*  13:    */   {
/*  14: 18 */     Sequence s = new Sequence("conjuction");
/*  15: 19 */     for (Entity cause : causes) {
/*  16: 20 */       s.addElement(cause);
/*  17:    */     }
/*  18: 22 */     return new Relation("cause", s, result);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static Relation makeCause(Sequence antecedents, Entity consequent)
/*  22:    */   {
/*  23: 28 */     Relation result = new Relation("cause", antecedents, consequent);
/*  24: 29 */     return result;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static Relation makeCause(Entity... things)
/*  28:    */   {
/*  29: 33 */     Sequence antecedents = new Sequence("conjuction");
/*  30: 34 */     for (int i = 0; i < things.length - 1; i++) {
/*  31: 35 */       antecedents.addElement(things[i]);
/*  32:    */     }
/*  33: 37 */     Entity consequent = things[(things.length - 1)];
/*  34: 38 */     Relation result = new Relation("cause", antecedents, consequent);
/*  35: 39 */     return result;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static Relation makePredictionRule(Entity... things)
/*  39:    */   {
/*  40: 43 */     Relation result = makeCause(things);
/*  41: 44 */     result.addType("prediction");
/*  42: 45 */     return result;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Relation makePredictionRule(Sequence antecedents, Entity consequent)
/*  46:    */   {
/*  47: 49 */     Relation result = makeCause(antecedents, consequent);
/*  48: 50 */     result.addType("prediction");
/*  49: 51 */     return result;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Relation makeExplanationRule(Entity... things)
/*  53:    */   {
/*  54: 55 */     Relation result = makeCause(things);
/*  55: 56 */     result.addType("explanation");
/*  56: 57 */     return result;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Relation makeExplanationRule(Sequence antecedents, Entity consequent)
/*  60:    */   {
/*  61: 61 */     Relation result = makeCause(antecedents, consequent);
/*  62: 62 */     result.addType("explanation");
/*  63: 63 */     return result;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public static Relation makeRoleFrame(String verb, Entity subject)
/*  67:    */   {
/*  68: 69 */     return new Relation(verb, subject, new Sequence("roles"));
/*  69:    */   }
/*  70:    */   
/*  71:    */   public static Relation makeRoleFrame(String verb, Entity subject, Entity object)
/*  72:    */   {
/*  73: 73 */     Relation roleFrame = makeRoleFrame(verb, subject);
/*  74: 74 */     return addRole(roleFrame, "object", object);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public static Relation makeRoleFrame(Thread thread, Entity subject)
/*  78:    */   {
/*  79: 78 */     return new Relation(thread, subject, new Sequence("roles"));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static Relation makeRoleFrame(Thread thread, Entity subject, Entity object)
/*  83:    */   {
/*  84: 82 */     Relation roleFrame = new Relation(thread, subject, new Sequence("roles"));
/*  85: 83 */     return addRole(roleFrame, "object", object);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public static Relation addRole(Entity entity, String role, Entity filler)
/*  89:    */   {
/*  90: 87 */     if (isRoleFrame(entity))
/*  91:    */     {
/*  92: 88 */       Relation roleFrame = (Relation)entity;
/*  93: 89 */       Sequence s = (Sequence)roleFrame.getObject();
/*  94: 90 */       if (getRole(roleFrame, role) != null)
/*  95:    */       {
/*  96: 91 */         Mark.err(new Object[] {"Role frame", roleFrame, "alreay has a", role, "role" });
/*  97: 92 */         return roleFrame;
/*  98:    */       }
/*  99: 94 */       s.addElement(new Function(role, filler));
/* 100: 95 */       return roleFrame;
/* 101:    */     }
/* 102: 97 */     Mark.err(new Object[] {"Relation", entity, "is not a role frame" });
/* 103: 98 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static Entity getRole(Relation roleFrame, String role)
/* 107:    */   {
/* 108:102 */     if (roleFrame.getObject().sequenceP("roles")) {
/* 109:103 */       for (Entity e : roleFrame.getObject().getElements()) {
/* 110:104 */         if (e.functionP(role)) {
/* 111:105 */           return e.getSubject();
/* 112:    */         }
/* 113:    */       }
/* 114:    */     }
/* 115:109 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public static boolean isRoleFrame(Entity x)
/* 119:    */   {
/* 120:113 */     if ((x.relationP()) && (x.getObject() != null) && (x.getObject().sequenceP("roles"))) {
/* 121:114 */       return true;
/* 122:    */     }
/* 123:116 */     return false;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static Relation makeEvent(String subject, String verb, Object object, Object indirectObject)
/* 127:    */   {
/* 128:123 */     Entity subjectThing = new Entity(subject);
/* 129:124 */     Entity objectThing = null;
/* 130:125 */     Entity indirectObjectThing = null;
/* 131:126 */     if ((object instanceof Entity)) {
/* 132:127 */       objectThing = (Entity)object;
/* 133:129 */     } else if ((object instanceof String)) {
/* 134:130 */       objectThing = new Entity((String)object);
/* 135:    */     }
/* 136:132 */     subjectThing.addFeature("indefinite");
/* 137:133 */     if (objectThing != null) {
/* 138:134 */       objectThing.addFeature("indefinite");
/* 139:    */     }
/* 140:137 */     if ((indirectObject instanceof Entity)) {
/* 141:138 */       indirectObjectThing = (Entity)indirectObject;
/* 142:140 */     } else if ((indirectObject instanceof String)) {
/* 143:141 */       indirectObjectThing = new Entity((String)indirectObject);
/* 144:    */     }
/* 145:144 */     Sequence roles = new Sequence("roles");
/* 146:146 */     if (verb.equalsIgnoreCase("putdown")) {
/* 147:147 */       verb = "put_down";
/* 148:149 */     } else if ((verb.equalsIgnoreCase("pickup")) || (verb.equalsIgnoreCase("pickup2"))) {
/* 149:150 */       verb = "pick_up";
/* 150:    */     }
/* 151:152 */     if ((object != null) && (objectThing != null))
/* 152:    */     {
/* 153:153 */       if (objectThing.isA("something")) {
/* 154:154 */         objectThing.addFeature("none");
/* 155:    */       }
/* 156:156 */       roles.addElement(new Function("object", objectThing));
/* 157:    */     }
/* 158:158 */     if ((indirectObject != null) && (indirectObjectThing != null))
/* 159:    */     {
/* 160:159 */       if (indirectObjectThing.isA("something")) {
/* 161:160 */         indirectObjectThing.addFeature("none");
/* 162:    */       }
/* 163:163 */       if ("replace".equalsIgnoreCase(verb)) {
/* 164:164 */         roles.addElement(new Function("with", indirectObjectThing));
/* 165:166 */       } else if ("give".equalsIgnoreCase(verb)) {
/* 166:167 */         roles.addElement(new Function("to", indirectObjectThing));
/* 167:169 */       } else if ("take".equalsIgnoreCase(verb)) {
/* 168:170 */         roles.addElement(new Function("from", indirectObjectThing));
/* 169:    */       } else {
/* 170:173 */         roles.addElement(new Function("to", indirectObjectThing));
/* 171:    */       }
/* 172:    */     }
/* 173:178 */     Relation event = new Relation(verb, subjectThing, roles);
/* 174:179 */     return event;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static void main(String[] ignore)
/* 178:    */     throws Throwable
/* 179:    */   {
/* 180:206 */     Entity t1 = new Entity("John");
/* 181:    */     
/* 182:208 */     Entity t2 = new Entity("Mary");
/* 183:    */     
/* 184:210 */     Mark.say(new Object[] {makeRoleFrame("love", t1).asString() });
/* 185:    */     
/* 186:212 */     Mark.say(new Object[] {makeRoleFrame("love", t1, t2).asString() });
/* 187:    */   }
/* 188:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tools.Innerese
 * JD-Core Version:    0.7.0.1
 */