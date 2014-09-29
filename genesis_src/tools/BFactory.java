/*   1:    */ package tools;
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
/*  14: 21 */     Function result = new Function(thing);
/*  15: 22 */     result.addType(type);
/*  16: 23 */     return result;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static Function createPlaceElement(Entity thing)
/*  20:    */   {
/*  21: 30 */     Function result = new Function(thing);
/*  22: 31 */     result.addType("placeElement");
/*  23: 32 */     result.addType("at");
/*  24: 33 */     return result;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public static Sequence createPath()
/*  28:    */   {
/*  29: 40 */     Sequence result = new Sequence();
/*  30: 41 */     result.addType("path");
/*  31: 42 */     result.addType("between");
/*  32: 43 */     result.addType("empty", "features");
/*  33: 44 */     return result;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static Sequence extendPath(Sequence path, Function placeElement)
/*  37:    */   {
/*  38: 51 */     Sequence result = path;
/*  39: 52 */     if (!placeElement.isA("placeElement"))
/*  40:    */     {
/*  41: 53 */       System.err.println("Sorry, " + placeElement + " is not a place element.");
/*  42: 54 */       return null;
/*  43:    */     }
/*  44: 56 */     if (!path.isA("path"))
/*  45:    */     {
/*  46: 57 */       System.err.println("Sorry " + path + " is not a path.");
/*  47: 58 */       return null;
/*  48:    */     }
/*  49: 60 */     return extend(result, placeElement);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public static Function createTransitionElement(String type, Entity thing)
/*  53:    */   {
/*  54: 67 */     if (!StringUtils.testType(type, Entity.changeList))
/*  55:    */     {
/*  56: 68 */       System.err.println("Sorry, " + type + " is not a known transition element type.");
/*  57: 69 */       return null;
/*  58:    */     }
/*  59: 71 */     Function result = new Function(thing);
/*  60: 72 */     result.addType("transitionElement");
/*  61: 73 */     result.addType(type);
/*  62: 74 */     return result;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public static Relation createTransitionObject(Entity object, Function transition)
/*  66:    */   {
/*  67:134 */     Relation result = new Relation(object, transition);
/*  68:135 */     result.addType("transition object");
/*  69:136 */     return result;
/*  70:    */   }
/*  71:    */   
/*  72:    */   private static Sequence extend(Sequence result, Entity thing)
/*  73:    */   {
/*  74:142 */     result.addElement(thing);
/*  75:143 */     result.removeType("empty");
/*  76:144 */     return result;
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tools.BFactory
 * JD-Core Version:    0.7.0.1
 */