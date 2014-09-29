/*   1:    */ package frames;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import bridge.utils.StringUtils;
/*   7:    */ import java.io.PrintStream;
/*   8:    */ import java.util.Vector;
/*   9:    */ 
/*  10:    */ public class CDFrame
/*  11:    */   extends Frame
/*  12:    */ {
/*  13:  9 */   public static String FRAMETYPE = "conceptualization";
/*  14: 10 */   public static String[] actions = { "atrans", "ptrans", "propel", "move", "grasp", "ingest", "expel", "mtrans", "mbuild", "speak", "attend" };
/*  15:    */   private Sequence conceptualization;
/*  16:    */   
/*  17:    */   public static Sequence makeConceptualization(Entity actor, String action, Entity object, Function direction, Entity instrument)
/*  18:    */   {
/*  19: 13 */     if (!StringUtils.testType(action, actions))
/*  20:    */     {
/*  21: 14 */       System.err.println("Error, " + action + " is not a Conceptual Dependency Theory primitive act.");
/*  22: 15 */       return null;
/*  23:    */     }
/*  24: 17 */     Sequence result = new Sequence(FRAMETYPE);
/*  25: 18 */     result.addType(action);
/*  26: 19 */     Vector<Entity> elts = new Vector();
/*  27: 20 */     elts.insertElementAt(actor, 0);
/*  28: 21 */     elts.insertElementAt(object, 1);
/*  29: 22 */     elts.insertElementAt(direction, 2);
/*  30: 23 */     elts.insertElementAt(instrument, 3);
/*  31: 24 */     result.setElements(elts);
/*  32: 25 */     return result;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static Entity getActor(Sequence conceptualization)
/*  36:    */   {
/*  37: 29 */     if (!conceptualization.isA(FRAMETYPE))
/*  38:    */     {
/*  39: 30 */       System.err.println("Error:  Argument to getActor must be a conceptualization.");
/*  40: 31 */       return null;
/*  41:    */     }
/*  42: 33 */     return conceptualization.getElement(0);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static Entity getObject(Sequence conceptualization)
/*  46:    */   {
/*  47: 37 */     if (!conceptualization.isA(FRAMETYPE))
/*  48:    */     {
/*  49: 38 */       System.err.println("Error: Argument to getObject must be a conceptualization.");
/*  50: 39 */       return null;
/*  51:    */     }
/*  52: 41 */     return conceptualization.getElement(1);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Function getDirection(Sequence conceptualization)
/*  56:    */   {
/*  57: 45 */     if (!conceptualization.isA(FRAMETYPE))
/*  58:    */     {
/*  59: 46 */       System.err.println("Error: Argument to getDirection must be a conceptualization.");
/*  60: 47 */       return null;
/*  61:    */     }
/*  62: 49 */     return (Function)conceptualization.getElement(2);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static String getAction(Sequence conceptualization)
/*  66:    */   {
/*  67: 53 */     if (!conceptualization.isA(FRAMETYPE))
/*  68:    */     {
/*  69: 54 */       System.err.println("Error: Argument to getAction must be a conceptualization.");
/*  70: 55 */       return "";
/*  71:    */     }
/*  72: 57 */     return conceptualization.getType();
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Entity getInstrument(Sequence conceptualization)
/*  76:    */   {
/*  77: 61 */     if (!conceptualization.isA(FRAMETYPE))
/*  78:    */     {
/*  79: 62 */       System.err.println("Error: Argument to getInstrument must be a conceptualization.");
/*  80: 63 */       return null;
/*  81:    */     }
/*  82: 65 */     return conceptualization.getElement(3);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public static void setActor(Sequence conceptualization, Entity actor)
/*  86:    */   {
/*  87: 69 */     if (!conceptualization.isA(FRAMETYPE))
/*  88:    */     {
/*  89: 70 */       System.err.println("Error:  Argument to setActor must be a conceptualization.");
/*  90: 71 */       return;
/*  91:    */     }
/*  92: 73 */     conceptualization.setElementAt(actor, 0);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static void setAction(Sequence conceptualization, String action)
/*  96:    */   {
/*  97: 78 */     if (!conceptualization.isA(FRAMETYPE))
/*  98:    */     {
/*  99: 79 */       System.err.println("Error:  Argument to setAction must be a conceptualization.");
/* 100: 80 */       return;
/* 101:    */     }
/* 102: 82 */     conceptualization.removeType(conceptualization.getType());
/* 103: 83 */     conceptualization.addType(action);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static void setObject(Sequence conceptualization, Entity object)
/* 107:    */   {
/* 108: 88 */     if (!conceptualization.isA(FRAMETYPE))
/* 109:    */     {
/* 110: 89 */       System.err.println("Error:  Argument to setObject must be a conceptualization.");
/* 111: 90 */       return;
/* 112:    */     }
/* 113: 92 */     conceptualization.setElementAt(object, 1);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static void setDirection(Sequence conceptualization, Function direction)
/* 117:    */   {
/* 118: 97 */     if (!conceptualization.isA(FRAMETYPE))
/* 119:    */     {
/* 120: 98 */       System.err.println("Error:  Argument to setDirection must be a conceptualization.");
/* 121: 99 */       return;
/* 122:    */     }
/* 123:101 */     conceptualization.setElementAt(direction, 2);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static void setInstrument(Sequence conceptualization, Entity instrument)
/* 127:    */   {
/* 128:106 */     if (!conceptualization.isA(FRAMETYPE))
/* 129:    */     {
/* 130:107 */       System.err.println("Error:  Argument to setInstrument must be a conceptualization.");
/* 131:108 */       return;
/* 132:    */     }
/* 133:110 */     if (conceptualization.isA("active"))
/* 134:    */     {
/* 135:111 */       conceptualization.setElementAt(instrument, 3);
/* 136:112 */       return;
/* 137:    */     }
/* 138:114 */     System.err.println("Sorry, can't set instrument in a stative conceptualization");
/* 139:    */   }
/* 140:    */   
/* 141:    */   public CDFrame(Entity t)
/* 142:    */   {
/* 143:120 */     if (t.isA(FRAMETYPE)) {
/* 144:121 */       this.conceptualization = ((Sequence)t);
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public Entity getThing()
/* 149:    */   {
/* 150:125 */     return this.conceptualization;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String toString()
/* 154:    */   {
/* 155:129 */     if (this.conceptualization != null) {
/* 156:130 */       return this.conceptualization.toString();
/* 157:    */     }
/* 158:132 */     return "";
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.CDFrame
 * JD-Core Version:    0.7.0.1
 */