/*   1:    */ package frames;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import bridge.utils.StringUtils;
/*   7:    */ import constants.RecognizedRepresentations;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ 
/*  10:    */ public class TransitionFrame
/*  11:    */   extends Frame
/*  12:    */ {
/*  13: 11 */   public static String FRAMETYPE = (String)RecognizedRepresentations.TRANSITION_REPRESENTATION;
/*  14: 12 */   public static String[] changeList = { "increase", "decrease", "change", 
/*  15: 13 */     "appear", "disappear", "notIncrease", "notDecrease", "notChange", 
/*  16: 14 */     "notAppear", "notDisappear", "blank" };
/*  17:    */   
/*  18:    */   public static Function createVariableElement(String type, Entity thing)
/*  19:    */   {
/*  20: 17 */     Function result = new Function(thing);
/*  21: 18 */     result.addType(type);
/*  22: 19 */     return result;
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static Function createPlaceElement(Entity thing)
/*  26:    */   {
/*  27: 25 */     Function result = new Function(thing);
/*  28: 26 */     result.addType("placeElement");result.addType("at");
/*  29: 27 */     return result;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public static Sequence createPath()
/*  33:    */   {
/*  34: 35 */     Sequence result = new Sequence();
/*  35: 36 */     result.addType("path");
/*  36: 37 */     result.addType("between");
/*  37:    */     
/*  38: 39 */     return result;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Sequence extendPath(Sequence path, Function placeElement)
/*  42:    */   {
/*  43: 46 */     Sequence result = path;
/*  44: 47 */     if (!placeElement.isA("placeElement"))
/*  45:    */     {
/*  46: 47 */       System.err.println("Sorry, " + placeElement + " is not a place element.");return null;
/*  47:    */     }
/*  48: 48 */     if (!path.isA("path"))
/*  49:    */     {
/*  50: 48 */       System.err.println("Sorry " + path + " is not a path.");return null;
/*  51:    */     }
/*  52: 49 */     return extend(result, placeElement);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public static Function createTransitionElement(String type, Entity thing)
/*  56:    */   {
/*  57: 56 */     if (!StringUtils.testType(type, changeList))
/*  58:    */     {
/*  59: 56 */       System.err.println("Sorry, " + type + " is not a known transition element type.");return null;
/*  60:    */     }
/*  61: 57 */     Function result = new Function(thing);
/*  62: 58 */     result.addType(FRAMETYPE);
/*  63: 59 */     result.addType(type);
/*  64: 60 */     return result;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static Sequence createTransitionLadder()
/*  68:    */   {
/*  69: 67 */     Sequence result = new Sequence();
/*  70: 68 */     result.addType("transitionLadder");
/*  71:    */     
/*  72: 70 */     return result;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Sequence extendTransitionLadder(Sequence transitionLadder, Function transition)
/*  76:    */   {
/*  77: 77 */     Sequence result = transitionLadder;
/*  78: 78 */     if (!transition.isA("transitionElement"))
/*  79:    */     {
/*  80: 78 */       System.err.println("Sorry, " + transition + " is not a transition element.");return null;
/*  81:    */     }
/*  82: 79 */     if (!transitionLadder.isA("transitionLadder"))
/*  83:    */     {
/*  84: 79 */       System.err.println("Sorry " + transitionLadder + " is not a transitionLadder.");return null;
/*  85:    */     }
/*  86: 80 */     return extend(result, transition);
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static Sequence createTransitionSpace()
/*  90:    */   {
/*  91: 87 */     Sequence result = new Sequence();
/*  92: 88 */     result.addType("transitionSpace");
/*  93:    */     
/*  94: 90 */     return result;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public static Sequence extendTransitionSpace(Sequence transitionSpace, Sequence transitionLadder)
/*  98:    */   {
/*  99: 97 */     Sequence result = transitionSpace;
/* 100: 98 */     if (!transitionLadder.isA("transitionLadder"))
/* 101:    */     {
/* 102: 99 */       System.err.println("Sorry, " + transitionLadder + " is not a transitionLadder element.");
/* 103:100 */       System.err.println("Cannot add to " + transitionSpace);
/* 104:101 */       return null;
/* 105:    */     }
/* 106:103 */     if (!transitionSpace.isA("transitionSpace"))
/* 107:    */     {
/* 108:103 */       System.err.println("Sorry " + transitionSpace + " is not a transitionSpace.");return null;
/* 109:    */     }
/* 110:104 */     return extend(result, transitionLadder);
/* 111:    */   }
/* 112:    */   
/* 113:    */   private static Sequence extend(Sequence result, Entity thing)
/* 114:    */   {
/* 115:109 */     result.addElement(thing);
/* 116:110 */     result.removeType("empty");
/* 117:111 */     return result;
/* 118:    */   }
/* 119:    */   
/* 120:115 */   private Function transition = null;
/* 121:    */   
/* 122:    */   public TransitionFrame(Entity t)
/* 123:    */   {
/* 124:118 */     if (t.isA(FRAMETYPE)) {
/* 125:119 */       this.transition = ((Function)t);
/* 126:    */     }
/* 127:    */   }
/* 128:    */   
/* 129:    */   public TransitionFrame(Entity transitioner, String type)
/* 130:    */   {
/* 131:123 */     this.transition = createTransitionElement(type, transitioner);
/* 132:    */   }
/* 133:    */   
/* 134:    */   private String mapChange(String word)
/* 135:    */   {
/* 136:127 */     if ("increased".equalsIgnoreCase(word)) {
/* 137:127 */       return "increase";
/* 138:    */     }
/* 139:128 */     if ("decreased".equalsIgnoreCase(word)) {
/* 140:128 */       return "decrease";
/* 141:    */     }
/* 142:129 */     if ("changed".equalsIgnoreCase(word)) {
/* 143:129 */       return "change";
/* 144:    */     }
/* 145:130 */     if ("appeared".equalsIgnoreCase(word)) {
/* 146:130 */       return "appear";
/* 147:    */     }
/* 148:131 */     if ("disappeared".equalsIgnoreCase(word)) {
/* 149:131 */       return "disappear";
/* 150:    */     }
/* 151:132 */     return word;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public TransitionFrame(TransitionFrame t)
/* 155:    */   {
/* 156:136 */     this.transition = ((Function)t.getTransition().clone());
/* 157:    */   }
/* 158:    */   
/* 159:    */   public String toString()
/* 160:    */   {
/* 161:139 */     if (this.transition != null) {
/* 162:140 */       return this.transition.toString();
/* 163:    */     }
/* 164:142 */     return "";
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Function getTransition()
/* 168:    */   {
/* 169:145 */     return this.transition;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Entity getThing()
/* 173:    */   {
/* 174:149 */     return getTransition();
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.TransitionFrame
 * JD-Core Version:    0.7.0.1
 */