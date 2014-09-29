/*  1:   */ package frames.utilities;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import bridge.reps.entities.Relation;
/*  6:   */ import bridge.utils.StringUtils;
/*  7:   */ import groups.Graph;
/*  8:   */ import java.io.PrintStream;
/*  9:   */ import java.util.Set;
/* 10:   */ 
/* 11:   */ public class ADRLFactory
/* 12:   */ {
/* 13:13 */   public static String[] elementTypes = { "state", "goal", "action", "actor action", "actor goal" };
/* 14:14 */   public static String[] stateTypes = { "claim", "result" };
/* 15:15 */   public static String[] relationTypes = { "causal", "state", "action", "goal", "temporal" };
/* 16:16 */   public static String[] causalRelationTypes = { "causes", "enables", "gates" };
/* 17:17 */   public static String[] stateRelationTypes = { "strengthens", "weakens", "invalidates", "validates", "presupposes", "sub-state" };
/* 18:18 */   public static String[] actionRelationTypes = { "prevents", "accomplishes", "hinders", "aids" };
/* 19:19 */   public static String[] temporalRelationTypes = { "parallel to", "after", "before" };
/* 20:20 */   public static String subgoalType = "subgoal of";
/* 21:21 */   public static String ELEMENTNAME = "adrl element";
/* 22:22 */   public static String RELATIONNAME = "adrl relation";
/* 23:23 */   public static String SCENARIONAME = "adrl scneario";
/* 24:   */   
/* 25:   */   public static Graph makeNewScenario()
/* 26:   */   {
/* 27:26 */     Graph scenario = new Graph(SCENARIONAME);
/* 28:27 */     return scenario;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static Function makeADRLElement(String type, Entity frame)
/* 32:   */   {
/* 33:31 */     if (!StringUtils.testType(type, elementTypes))
/* 34:   */     {
/* 35:32 */       System.err.println("Type " + type + " provided to makeADRLElement is not a valid element type.");
/* 36:33 */       return null;
/* 37:   */     }
/* 38:35 */     Function result = new Function(ELEMENTNAME, frame);
/* 39:36 */     result.addType(type);
/* 40:37 */     return result;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public static Relation makeADRLRelation(String subType, Entity subject, Entity object)
/* 44:   */   {
/* 45:41 */     Relation result = new Relation(subject, object);
/* 46:   */     String type;
/* 47:43 */     if (StringUtils.testType(subType, causalRelationTypes))
/* 48:   */     {
/* 49:44 */       type = "causal";
/* 50:   */     }
/* 51:   */     else
/* 52:   */     {
/* 53:   */       String type;
/* 54:46 */       if (StringUtils.testType(subType, stateRelationTypes))
/* 55:   */       {
/* 56:47 */         type = "state";
/* 57:   */       }
/* 58:   */       else
/* 59:   */       {
/* 60:   */         String type;
/* 61:49 */         if (StringUtils.testType(subType, actionRelationTypes))
/* 62:   */         {
/* 63:50 */           type = "action";
/* 64:   */         }
/* 65:   */         else
/* 66:   */         {
/* 67:   */           String type;
/* 68:52 */           if (StringUtils.testType(subType, temporalRelationTypes))
/* 69:   */           {
/* 70:53 */             type = "temporal";
/* 71:   */           }
/* 72:   */           else
/* 73:   */           {
/* 74:   */             String type;
/* 75:55 */             if (subType == subgoalType)
/* 76:   */             {
/* 77:56 */               type = "goal";
/* 78:   */             }
/* 79:   */             else
/* 80:   */             {
/* 81:59 */               System.err.println("Type " + subType + " provided to makeADRLRelation is not valid.");
/* 82:60 */               return null;
/* 83:   */             }
/* 84:   */           }
/* 85:   */         }
/* 86:   */       }
/* 87:   */     }
/* 88:   */     String type;
/* 89:62 */     result.addType(RELATIONNAME);
/* 90:63 */     result.addType(type);
/* 91:64 */     result.addType(subType);
/* 92:65 */     return result;
/* 93:   */   }
/* 94:   */   
/* 95:   */   public static boolean elementCheck(Set<Function> elts)
/* 96:   */   {
/* 97:69 */     for (Function e : elts) {
/* 98:70 */       if (!e.isA(ELEMENTNAME)) {
/* 99:71 */         return false;
/* :0:   */       }
/* :1:   */     }
/* :2:74 */     return true;
/* :3:   */   }
/* :4:   */   
/* :5:   */   public static boolean relationCheck(Set<Relation> rels)
/* :6:   */   {
/* :7:78 */     for (Relation r : rels) {
/* :8:79 */       if (!r.isA(RELATIONNAME)) {
/* :9:80 */         return false;
/* ;0:   */       }
/* ;1:   */     }
/* ;2:83 */     return true;
/* ;3:   */   }
/* ;4:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.utilities.ADRLFactory
 * JD-Core Version:    0.7.0.1
 */