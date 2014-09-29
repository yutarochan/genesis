/*   1:    */ package frames.utilities;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Function;
/*   5:    */ import bridge.reps.entities.Relation;
/*   6:    */ import bridge.reps.entities.Sequence;
/*   7:    */ import bridge.utils.StringUtils;
/*   8:    */ import java.io.PrintStream;
/*   9:    */ 
/*  10:    */ public class BFactory
/*  11:    */ {
/*  12:    */   public static Function createVariableElement(String type, Entity thing)
/*  13:    */   {
/*  14: 20 */     Function result = new Function(thing);
/*  15: 21 */     result.addType(type);
/*  16: 22 */     return result;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static Function createPlaceElement(Entity thing)
/*  20:    */   {
/*  21: 28 */     Function result = new Function(thing);
/*  22: 29 */     result.addType("placeElement");result.addType("at");
/*  23: 30 */     return result;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Sequence createPath()
/*  27:    */   {
/*  28: 36 */     Sequence result = new Sequence();
/*  29: 37 */     result.addType("path");
/*  30: 38 */     result.addType("between");
/*  31:    */     
/*  32: 40 */     return result;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static Sequence extendPath(Sequence path, Function placeElement)
/*  36:    */   {
/*  37: 46 */     Sequence result = path;
/*  38: 47 */     if (!placeElement.isA("placeElement"))
/*  39:    */     {
/*  40: 47 */       System.err.println("Sorry, " + placeElement + " is not a place element.");return null;
/*  41:    */     }
/*  42: 48 */     if (!path.isA("path"))
/*  43:    */     {
/*  44: 48 */       System.err.println("Sorry " + path + " is not a path.");return null;
/*  45:    */     }
/*  46: 49 */     return extend(result, placeElement);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public static Function createTransitionElement(String type, Entity thing)
/*  50:    */   {
/*  51: 56 */     if (!StringUtils.testType(type, Entity.changeList))
/*  52:    */     {
/*  53: 56 */       System.err.println("Sorry, " + type + " is not a known transition element type.");return null;
/*  54:    */     }
/*  55: 57 */     Function result = new Function(thing);
/*  56: 58 */     result.addType("transitionElement");
/*  57: 59 */     result.addType(type);
/*  58: 60 */     return result;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public static Sequence createTransitionLadder()
/*  62:    */   {
/*  63: 66 */     Sequence result = new Sequence();
/*  64: 67 */     result.addType("transitionLadder");
/*  65: 68 */     result.addFeature("empty");
/*  66: 69 */     return result;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public static Sequence extendTransitionLadder(Sequence transitionLadder, Function transition)
/*  70:    */   {
/*  71: 75 */     Sequence result = transitionLadder;
/*  72: 76 */     if (!transition.isA("transitionElement"))
/*  73:    */     {
/*  74: 76 */       System.err.println("Sorry, " + transition + " is not a transition element.");return null;
/*  75:    */     }
/*  76: 77 */     if (!transitionLadder.isA("transitionLadder"))
/*  77:    */     {
/*  78: 77 */       System.err.println("Sorry " + transitionLadder + " is not a transitionLadder.");return null;
/*  79:    */     }
/*  80: 78 */     return extend(result, transition);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public static Sequence createTransitionSpace()
/*  84:    */   {
/*  85: 84 */     Sequence result = new Sequence();
/*  86: 85 */     result.addType("transitionSpace");
/*  87: 86 */     result.addFeature("empty");
/*  88: 87 */     return result;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static Sequence extendTransitionSpace(Sequence transitionSpace, Sequence transitionLadder)
/*  92:    */   {
/*  93: 93 */     Sequence result = transitionSpace;
/*  94: 94 */     if (!transitionLadder.isA("transitionLadder"))
/*  95:    */     {
/*  96: 95 */       System.err.println("Sorry, " + transitionLadder + " is not a transitionLadder element.");
/*  97: 96 */       System.err.println("Cannot add to " + transitionSpace);
/*  98: 97 */       return null;
/*  99:    */     }
/* 100: 99 */     if (!transitionSpace.isA("transitionSpace"))
/* 101:    */     {
/* 102: 99 */       System.err.println("Sorry " + transitionSpace + " is not a transitionSpace.");return null;
/* 103:    */     }
/* 104:100 */     return extend(result, transitionLadder);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static Relation createObjAttrPair(Entity object, Function transition)
/* 108:    */   {
/* 109:111 */     Relation result = new Relation(object, transition);
/* 110:112 */     result.addType("Transition Object Pair");
/* 111:113 */     return result;
/* 112:    */   }
/* 113:    */   
/* 114:    */   private static Sequence extend(Sequence result, Entity thing)
/* 115:    */   {
/* 116:117 */     result.addElement(thing);
/* 117:118 */     result.removeType("empty");
/* 118:119 */     return result;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.utilities.BFactory
 * JD-Core Version:    0.7.0.1
 */