/*  1:   */ package silaSayan;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class NarrateToSelf
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public static Sequence plot;
/* 13:   */   public static String readablePlot;
/* 14:   */   public static Sequence storyUnderstood;
/* 15:18 */   public static String RAW_STORY_INPUT_PORT = "unprocessed story coming in as representations";
/* 16:19 */   public static String RAW_STORY_READABLE_INPUT_PORT = "unprocessed story coming in as string";
/* 17:20 */   public static String STORY_UNDERSTANDING_INPUT_PORT = "story and inferences";
/* 18:23 */   public static String NARRATOR_STORY_OUTPUT_PORT = "narrator's understanding of the story";
/* 19:   */   
/* 20:   */   public NarrateToSelf()
/* 21:   */   {
/* 22:27 */     setName("Narrator");
/* 23:28 */     Connections.getPorts(this).addSignalProcessor(RAW_STORY_INPUT_PORT, "savePlot");
/* 24:29 */     Connections.getPorts(this).addSignalProcessor(RAW_STORY_READABLE_INPUT_PORT, "saveReadablePlot");
/* 25:30 */     Connections.getPorts(this).addSignalProcessor(STORY_UNDERSTANDING_INPUT_PORT, "narrateToSelf");
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void savePlot(Object o)
/* 29:   */   {
/* 30:34 */     if ((o instanceof Sequence)) {
/* 31:35 */       plot = (Sequence)o;
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void saveReadablePlot(Object o)
/* 36:   */   {
/* 37:40 */     if ((o instanceof String)) {
/* 38:41 */       readablePlot = (String)o;
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void narrateToSelf(Object o)
/* 43:   */   {
/* 44:47 */     if ((o instanceof Sequence)) {
/* 45:48 */       storyUnderstood = (Sequence)o;
/* 46:   */     }
/* 47:51 */     if (storyUnderstood.getObject().functionP("The end")) {
/* 48:53 */       Connections.getPorts(this).transmit(NARRATOR_STORY_OUTPUT_PORT, storyUnderstood);
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.NarrateToSelf
 * JD-Core Version:    0.7.0.1
 */