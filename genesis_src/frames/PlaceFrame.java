/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import bridge.utils.StringUtils;
/*  6:   */ import constants.RecognizedRepresentations;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ import tools.JFactory;
/*  9:   */ 
/* 10:   */ public class PlaceFrame
/* 11:   */   extends Frame
/* 12:   */ {
/* 13:13 */   public static String FRAMETYPE = (String)RecognizedRepresentations.PLACE_REPRESENTATION;
/* 14:   */   private Entity reference;
/* 15:   */   private Function place;
/* 16:   */   
/* 17:   */   public static Function createPlace(String type, Entity thing)
/* 18:   */   {
/* 19:19 */     if (!StringUtils.testType(type, Entity.placeList))
/* 20:   */     {
/* 21:20 */       System.err.println("Sorry, " + type + " is not a known place type.");
/* 22:21 */       return null;
/* 23:   */     }
/* 24:23 */     Function result = new Function(thing);
/* 25:24 */     result.addType("place");
/* 26:25 */     result.addType(type);
/* 27:26 */     return result;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public PlaceFrame(String preposition, Entity reference)
/* 31:   */   {
/* 32:38 */     this.reference = reference;
/* 33:39 */     this.place = JFactory.createPlace(preposition, reference);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public PlaceFrame(Entity t)
/* 37:   */   {
/* 38:44 */     if (t.isA(FRAMETYPE)) {
/* 39:45 */       this.place = this.place;
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public Function getPlace()
/* 44:   */   {
/* 45:50 */     return this.place;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Entity getThing()
/* 49:   */   {
/* 50:55 */     return getPlace();
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.PlaceFrame
 * JD-Core Version:    0.7.0.1
 */