/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import bridge.utils.StringUtils;
/*  6:   */ import constants.RecognizedRepresentations;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ 
/*  9:   */ public class PathElementFrame
/* 10:   */   extends Frame
/* 11:   */ {
/* 12:11 */   public static String FRAMETYPE = (String)RecognizedRepresentations.PATH_ELEMENT_THING;
/* 13:   */   private Entity reference;
/* 14:   */   private Function pathElement;
/* 15:   */   
/* 16:   */   public static Function createPathElement(String type, Entity thing)
/* 17:   */   {
/* 18:13 */     if (!StringUtils.testType(type, Entity.pathList))
/* 19:   */     {
/* 20:14 */       System.err.println("Sorry, " + type + " is not a known path element type.");
/* 21:15 */       return null;
/* 22:   */     }
/* 23:17 */     Function result = new Function(thing);
/* 24:18 */     result.addType("pathElement");
/* 25:19 */     result.addType(type);
/* 26:20 */     return result;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public PathElementFrame(String preposition, Entity reference)
/* 30:   */   {
/* 31:29 */     this.reference = reference;
/* 32:30 */     this.pathElement = createPathElement(preposition, reference);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public PathElementFrame(Entity t)
/* 36:   */   {
/* 37:34 */     if (t.isA(FRAMETYPE)) {
/* 38:35 */       this.pathElement = ((Function)t);
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public Function getPathElement()
/* 43:   */   {
/* 44:39 */     return this.pathElement;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Entity getThing()
/* 48:   */   {
/* 49:43 */     return this.pathElement;
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.PathElementFrame
 * JD-Core Version:    0.7.0.1
 */