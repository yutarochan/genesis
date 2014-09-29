/*   1:    */ package frames.utilities;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.utils.StringUtils;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ 
/*   9:    */ public class CAFactory
/*  10:    */ {
/*  11: 11 */   public static String graphType = "commonsense algorithm";
/*  12: 12 */   public static String eventType = "ca event";
/*  13: 13 */   public static String relationType = "ca relation";
/*  14: 14 */   public static final String[] causalTypes = { "causes", "enables" };
/*  15: 15 */   public static final String[] causalSubtypes = { "one-shot", "continuous", "gated", "coupled" };
/*  16: 16 */   public static final String[] eventTypes = { "action", "state", "statechange", "tendency", "want" };
/*  17:    */   
/*  18:    */   public static Function makeNewEvent(String type, Entity event)
/*  19:    */   {
/*  20: 19 */     if (!StringUtils.testType(type, eventTypes))
/*  21:    */     {
/*  22: 20 */       System.err.println("Sorry, " + type + " is not a recognized commonsense event type.");
/*  23: 21 */       return null;
/*  24:    */     }
/*  25: 23 */     Function result = new Function(eventType, event);
/*  26: 24 */     result.addType(type);
/*  27: 25 */     return result;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static Relation makeNewLink(String type, String subtype, Entity subject, Entity object)
/*  31:    */   {
/*  32: 28 */     System.err.println("Type: " + type);
/*  33: 29 */     System.err.println("Subject: " + subject + ", " + subject.isA("action"));
/*  34: 30 */     System.err.println("Object: " + object + ", " + object.isA("statechange"));
/*  35: 32 */     if ((subtype != "one-shot") && (subtype != "continuous") && (subtype != ""))
/*  36:    */     {
/*  37: 33 */       System.err.println("Invalid subtype to CAFactory.makeNewLink.");
/*  38: 34 */       return null;
/*  39:    */     }
/*  40: 36 */     if (type == "causes") {
/*  41: 37 */       if ((subject.isA("action")) || (subject.isA("tendency")))
/*  42:    */       {
/*  43: 38 */         if ((object.isA("state")) || ((object.isA("statechange")) && (subtype == "continuous"))) {
/*  44: 39 */           return makeLink(type, subtype, subject, object);
/*  45:    */         }
/*  46:    */       }
/*  47:    */       else
/*  48:    */       {
/*  49: 42 */         System.err.println("Sorry, the subject of a causal relation must be an action or tendency, and the state must be a state or statechange.");
/*  50: 43 */         return null;
/*  51:    */       }
/*  52:    */     }
/*  53: 47 */     if (type == "enables")
/*  54:    */     {
/*  55: 48 */       if ((subject.isA("state")) && ((object.isA("action")) || (object.isA("tendency")))) {
/*  56: 49 */         return makeLink(type, subtype, subject, object);
/*  57:    */       }
/*  58: 51 */       System.err.println("Sorry, the subject of an enablement relation must be a state, and the object must be an action or tendency.");
/*  59: 52 */       return null;
/*  60:    */     }
/*  61: 56 */     if (type == "coupled")
/*  62:    */     {
/*  63: 57 */       if (((subject.isA("state")) || (subject.isA("statechange"))) && ((object.isA("state")) || (object.isA("statechange")))) {
/*  64: 58 */         return makeLink(type, subject, object);
/*  65:    */       }
/*  66: 60 */       System.err.println("Error, all components of a coupled relation must either be a state or statechange.");
/*  67: 61 */       return null;
/*  68:    */     }
/*  69: 65 */     if (type == "byproduct")
/*  70:    */     {
/*  71: 66 */       if ((subject.isA("action")) && ((object.isA("state")) || (object.isA("statechange")))) {
/*  72: 67 */         return makeLink(type, subtype, subject, object);
/*  73:    */       }
/*  74: 69 */       System.err.println("Error, the subject of a byproduct relation must be an action, and the object must be a state or statechange.");
/*  75: 70 */       return null;
/*  76:    */     }
/*  77: 74 */     if (type == "antagonize")
/*  78:    */     {
/*  79: 75 */       if (((subject.isA("state")) || (subject.isA("statechange"))) && ((object.isA("state")) || (object.isA("statechange")))) {
/*  80: 76 */         return makeLink(type, subtype, subject, object);
/*  81:    */       }
/*  82: 78 */       System.err.println("Error, all components of an antagonize relation must either be states or statechanges.");
/*  83:    */     }
/*  84: 82 */     if (type == "goal couple")
/*  85:    */     {
/*  86: 83 */       if (((subject.isA("want")) || (subject.isA("state"))) && (object.isA("state"))) {
/*  87: 84 */         return makeLink(type, subject, object);
/*  88:    */       }
/*  89: 86 */       System.err.println("Error, the subject of a goal couple must be a want, and the object must be a state.");
/*  90: 87 */       return null;
/*  91:    */     }
/*  92: 91 */     if (type == "disenables")
/*  93:    */     {
/*  94: 92 */       if (((subject.isA("action")) || (subject.isA("tendency"))) && ((object.isA("state")) || (object.isA("statechange")))) {
/*  95: 93 */         return makeLink(type, subtype, subject, object);
/*  96:    */       }
/*  97: 95 */       System.err.println("Error, the subject of a disenables relation must be an action or tendency, and the object must be a state or statechange.");
/*  98: 96 */       return null;
/*  99:    */     }
/* 100:100 */     if (type == "induces")
/* 101:    */     {
/* 102:101 */       if (((subject.isA("state")) || (subject.isA("statechange"))) && (object.isA("want"))) {
/* 103:102 */         return makeLink(type, subject, object);
/* 104:    */       }
/* 105:104 */       System.err.println("Error, the subject of a induces relation must be a state or statechange, and the object must be a want.");
/* 106:105 */       return null;
/* 107:    */     }
/* 108:109 */     if (type == "optimizes")
/* 109:    */     {
/* 110:110 */       if ((subject.isA("state")) && (object.isA("action"))) {
/* 111:111 */         return makeLink(type, subject, object);
/* 112:    */       }
/* 113:113 */       System.err.println("Error, the subject of an optimizes relation must be a state, and the object must be an action.");
/* 114:114 */       return null;
/* 115:    */     }
/* 116:118 */     if (type == "gates")
/* 117:    */     {
/* 118:119 */       if ((subject.isA("state")) && (object.isA(relationType)))
/* 119:    */       {
/* 120:120 */         object.addType("gated");
/* 121:121 */         return makeLink(type, subject, object);
/* 122:    */       }
/* 123:123 */       System.err.println("Error, the subject of a gates relation must be a state, and the object must be a valid ca relation.");
/* 124:    */     }
/* 125:127 */     if (type == "concurrent")
/* 126:    */     {
/* 127:128 */       if ((subject.isA("action")) && (object.isA(relationType))) {
/* 128:129 */         return makeLink(type, subject, object);
/* 129:    */       }
/* 130:131 */       System.err.println("Error, the subject of a concurrent relation must be an action, and the object must be a valid ca relation.");
/* 131:    */     }
/* 132:135 */     if (type == "compound")
/* 133:    */     {
/* 134:136 */       if ((subject.isA("state")) && (object.isA("state"))) {
/* 135:137 */         return makeLink(type, subject, object);
/* 136:    */       }
/* 137:139 */       System.err.println("Error, a compound link can only be between states.");
/* 138:    */     }
/* 139:142 */     System.err.println("Sorry, " + type + " is not a know ca event type.");
/* 140:143 */     return null;
/* 141:    */   }
/* 142:    */   
/* 143:    */   private static Relation makeLink(String type, String subtype, Entity subject, Entity object)
/* 144:    */   {
/* 145:147 */     Relation result = new Relation(relationType, subject, object);
/* 146:148 */     result.addType(type);
/* 147:149 */     result.addType(subtype);
/* 148:150 */     return result;
/* 149:    */   }
/* 150:    */   
/* 151:    */   private static Relation makeLink(String type, Entity subject, Entity object)
/* 152:    */   {
/* 153:154 */     Relation result = new Relation(relationType, subject, object);
/* 154:155 */     result.addType(type);
/* 155:156 */     return result;
/* 156:    */   }
/* 157:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.utilities.CAFactory
 * JD-Core Version:    0.7.0.1
 */