/*  1:   */ package tools;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Function;
/*  6:   */ import bridge.reps.entities.Relation;
/*  7:   */ import bridge.reps.entities.Sequence;
/*  8:   */ import bridge.utils.StringUtils;
/*  9:   */ import java.io.PrintStream;
/* 10:   */ import links.words.BundleGenerator;
/* 11:   */ 
/* 12:   */ public class JFactory
/* 13:   */ {
/* 14:   */   public static Function createPlace(String type, Entity thing)
/* 15:   */   {
/* 16:21 */     if (!StringUtils.testType(type, Entity.placeList))
/* 17:   */     {
/* 18:22 */       System.err.println("Sorry, " + type + " is not a known place type.");
/* 19:23 */       return null;
/* 20:   */     }
/* 21:25 */     Function result = new Function(thing);
/* 22:26 */     result.addType("place");
/* 23:27 */     result.addType(type);
/* 24:28 */     return result;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public static Function createPathElement(String type, Entity thing)
/* 28:   */   {
/* 29:35 */     if (!StringUtils.testType(type, Entity.pathList))
/* 30:   */     {
/* 31:36 */       System.err.println("Sorry, " + type + " is not a known path element type.");
/* 32:37 */       return null;
/* 33:   */     }
/* 34:39 */     Function result = new Function(thing);
/* 35:40 */     result.addType("pathElement");
/* 36:41 */     result.addType(type);
/* 37:42 */     return result;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public static Sequence createPath()
/* 41:   */   {
/* 42:49 */     Sequence result = new Sequence();
/* 43:50 */     result.addType("path");
/* 44:   */     
/* 45:52 */     return result;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static Entity createPath(String indicator, Entity reference)
/* 49:   */   {
/* 50:56 */     Sequence path = createPath();
/* 51:57 */     addPathElement(path, createPathElement(indicator, reference));
/* 52:58 */     return path;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public static void addPathElement(Sequence path, Function pathElement)
/* 56:   */   {
/* 57:66 */     path.addElement(pathElement);
/* 58:   */   }
/* 59:   */   
/* 60:   */   public static Relation createTrajectory(Entity mover, String moveType, Entity path)
/* 61:   */   {
/* 62:73 */     Sequence roles = new Sequence("roles");
/* 63:74 */     roles.addElement(new Function("object", path));
/* 64:75 */     Relation r = new Relation("action", mover, roles);
/* 65:76 */     Bundle bundle = BundleGenerator.getBundle(moveType);
/* 66:77 */     r.setBundle(bundle);
/* 67:78 */     return r;
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tools.JFactory
 * JD-Core Version:    0.7.0.1
 */