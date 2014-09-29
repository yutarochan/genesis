/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import magicLess.utils.EntityUtils;
/*  5:   */ 
/*  6:   */ public class FrameFactory
/*  7:   */ {
/*  8:   */   public static Frame translate(Entity frameThing)
/*  9:   */   {
/* 10:13 */     String type = getFrameType(frameThing);
/* 11:14 */     if (type.equals(ADRLFrame.FRAMETYPE)) {
/* 12:15 */       return new ADRLFrame(frameThing);
/* 13:   */     }
/* 14:17 */     if (type.equals(BlockFrame.FRAMETYPE)) {
/* 15:18 */       return new BlockFrame(frameThing);
/* 16:   */     }
/* 17:20 */     if (type.equals(Frame.FRAMETYPE)) {
/* 18:21 */       return new CAFrame(frameThing);
/* 19:   */     }
/* 20:23 */     if (type.equals(CDFrame.FRAMETYPE)) {
/* 21:24 */       return new CDFrame(frameThing);
/* 22:   */     }
/* 23:26 */     if (type.equals(ForceFrame.FRAMETYPE)) {
/* 24:27 */       return new ForceFrame(frameThing);
/* 25:   */     }
/* 26:29 */     if (type.equals(GeometryFrame.FRAMETYPE)) {
/* 27:30 */       return new GeometryFrame(frameThing);
/* 28:   */     }
/* 29:32 */     if (type.equals(PathElementFrame.FRAMETYPE)) {
/* 30:33 */       return new PathElementFrame(frameThing);
/* 31:   */     }
/* 32:35 */     if (type.equals(PlaceFrame.FRAMETYPE)) {
/* 33:36 */       return new PlaceFrame(frameThing);
/* 34:   */     }
/* 35:38 */     if (type.equals(TimeFrame.FRAMETYPE)) {
/* 36:39 */       return new TimeFrame(frameThing);
/* 37:   */     }
/* 38:41 */     if (type.equals(TrajectoryFrame.FRAMETYPE)) {
/* 39:42 */       return new TrajectoryFrame(frameThing);
/* 40:   */     }
/* 41:44 */     if (type.equals(TransitionFrame.FRAMETYPE)) {
/* 42:45 */       return new TransitionFrame(frameThing);
/* 43:   */     }
/* 44:47 */     if (type.equals(EventFrame.FRAMETYPE)) {
/* 45:48 */       return new EventFrame(frameThing);
/* 46:   */     }
/* 47:50 */     if (type.equals(QuestionFrame.FRAMETYPE)) {
/* 48:51 */       return new QuestionFrame(frameThing);
/* 49:   */     }
/* 50:54 */     return null;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public static Entity translate(Frame frame)
/* 54:   */   {
/* 55:65 */     return (Entity)frame.getThing().clone();
/* 56:   */   }
/* 57:   */   
/* 58:   */   public static String getFrameType(Entity frameThing)
/* 59:   */   {
/* 60:74 */     return (String)EntityUtils.getRepType(frameThing);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public static String getFrameType(Frame frame)
/* 64:   */   {
/* 65:84 */     return getFrameType(frame.getThing());
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.FrameFactory
 * JD-Core Version:    0.7.0.1
 */