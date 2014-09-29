/*   1:    */ package frames;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.utils.StringUtils;
/*   7:    */ import groups.Graph;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.Set;
/*  11:    */ 
/*  12:    */ public class ADRLFrame
/*  13:    */   extends Frame
/*  14:    */ {
/*  15: 18 */   public static String FRAMETYPE = "adrlScenario";
/*  16: 19 */   public static String ELEMENTNAME = "adrlElement";
/*  17: 20 */   public static String RELATIONNAME = "adrlRelation";
/*  18: 22 */   public static String[] elementTypes = { "state", "goal", "action", "actor action", "actor goal" };
/*  19: 23 */   public static String[] stateTypes = { "claim", "result" };
/*  20: 24 */   public static String[] relationSupertypes = { "causal", "state", "action", "goal", "temporal" };
/*  21: 25 */   public static String[] causalRelationTypes = { "causes", "enables", "gates" };
/*  22: 26 */   public static String[] stateRelationTypes = { "strengthens", "weakens", "invalidates", "validates", "presupposes", "substate" };
/*  23: 27 */   public static String[] actionRelationTypes = { "prevents", "accomplishes", "hinders", "aids" };
/*  24: 28 */   public static String[] temporalRelationTypes = { "parallel to", "after", "before" };
/*  25:    */   
/*  26:    */   public static Graph makeNewScenario()
/*  27:    */   {
/*  28: 31 */     return new Graph(FRAMETYPE);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public static Function makeADRLElement(String type, Entity frame)
/*  32:    */   {
/*  33: 35 */     if (!StringUtils.testType(type, elementTypes))
/*  34:    */     {
/*  35: 36 */       System.err.println("Type " + type + " provided to makeADRLElement is not a valid element type.");
/*  36: 37 */       return null;
/*  37:    */     }
/*  38: 39 */     Function result = new Function(ELEMENTNAME, frame);
/*  39: 40 */     result.addType(type);
/*  40: 41 */     return result;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public static String elementType(Function element)
/*  44:    */   {
/*  45: 45 */     if (element.isA(ELEMENTNAME)) {
/*  46: 46 */       return element.getType();
/*  47:    */     }
/*  48: 48 */     System.err.println("Sorry, " + element + " is not an ADRL element.");
/*  49: 49 */     return null;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static String relationType(Relation relation)
/*  53:    */   {
/*  54: 54 */     if (relation.isA(RELATIONNAME)) {
/*  55: 55 */       return relation.getType();
/*  56:    */     }
/*  57: 57 */     System.err.println("Sorry, " + relation + " is not an ADRL relation.");
/*  58: 58 */     return null;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static String relationSupertype(Relation relation)
/*  62:    */   {
/*  63: 63 */     if (relation.isA(RELATIONNAME)) {
/*  64: 64 */       return relation.getSupertype();
/*  65:    */     }
/*  66: 66 */     System.err.println("Sorry, " + relation + " is not an ADRL relation.");
/*  67: 67 */     return null;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public static Relation mkeADRLRelation(String type, Entity subject, Entity object)
/*  71:    */   {
/*  72: 72 */     Relation result = new Relation(subject, object);
/*  73: 74 */     if (StringUtils.testType(type, causalRelationTypes))
/*  74:    */     {
/*  75: 75 */       result.addType("causal");
/*  76:    */     }
/*  77: 77 */     else if (StringUtils.testType(type, stateRelationTypes))
/*  78:    */     {
/*  79: 78 */       result.addType("state");
/*  80:    */     }
/*  81: 80 */     else if (StringUtils.testType(type, actionRelationTypes))
/*  82:    */     {
/*  83: 81 */       result.addType("action");
/*  84:    */     }
/*  85: 83 */     else if (StringUtils.testType(type, temporalRelationTypes))
/*  86:    */     {
/*  87: 84 */       result.addType("temporal");
/*  88:    */     }
/*  89: 86 */     else if (type == "subgoal of")
/*  90:    */     {
/*  91: 87 */       result.addType("goal");
/*  92:    */     }
/*  93:    */     else
/*  94:    */     {
/*  95: 90 */       System.err.println("Type, " + type + ", provided to makeADRLRelation is not valid.");
/*  96: 91 */       return null;
/*  97:    */     }
/*  98: 93 */     result.addType(type);
/*  99: 94 */     return result;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static boolean elementCheck(Set<Function> elements)
/* 103:    */   {
/* 104: 98 */     Iterator<Function> i = elements.iterator();
/* 105: 99 */     while (i.hasNext())
/* 106:    */     {
/* 107:100 */       Function current = (Function)i.next();
/* 108:101 */       if (!StringUtils.testType(elementType(current), elementTypes)) {
/* 109:102 */         return false;
/* 110:    */       }
/* 111:    */     }
/* 112:105 */     return true;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static boolean relationCheck(Set<Relation> relations)
/* 116:    */   {
/* 117:109 */     Iterator<Relation> i = relations.iterator();
/* 118:110 */     while (i.hasNext())
/* 119:    */     {
/* 120:111 */       Relation current = (Relation)i.next();
/* 121:112 */       if (!StringUtils.testType(relationSupertype(current), relationSupertypes)) {
/* 122:113 */         return false;
/* 123:    */       }
/* 124:    */     }
/* 125:116 */     return true;
/* 126:    */   }
/* 127:    */   
/* 128:120 */   private Graph scenario = makeNewScenario();
/* 129:    */   
/* 130:    */   public ADRLFrame(Entity t)
/* 131:    */   {
/* 132:123 */     if (t.isA(FRAMETYPE)) {
/* 133:124 */       this.scenario = this.scenario;
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public ADRLFrame(ADRLFrame f)
/* 138:    */   {
/* 139:129 */     this((Graph)f.getThing().clone());
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Entity getThing()
/* 143:    */   {
/* 144:133 */     return this.scenario;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String toString()
/* 148:    */   {
/* 149:137 */     return this.scenario.toString();
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.ADRLFrame
 * JD-Core Version:    0.7.0.1
 */