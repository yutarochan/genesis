/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.WiredViewer;
/*  5:   */ import constants.RecognizedRepresentations;
/*  6:   */ import gui.NewFrameViewer;
/*  7:   */ 
/*  8:   */ public abstract class Frame
/*  9:   */ {
/* 10:17 */   public static final String FRAMETYPE = (String)RecognizedRepresentations.CAUSE_THING;
/* 11:   */   
/* 12:   */   public abstract Entity getThing();
/* 13:   */   
/* 14:   */   public WiredViewer getThingViewer()
/* 15:   */   {
/* 16:23 */     return new NewFrameViewer();
/* 17:   */   }
/* 18:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.Frame
 * JD-Core Version:    0.7.0.1
 */