/*  1:   */ package silaSayan;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ 
/*  8:   */ public class AudienceTestRun
/*  9:   */   extends AbstractWiredBox
/* 10:   */ {
/* 11:   */   public static Sequence audienceReaction;
/* 12:15 */   public static String UPDATED_STORY_INPUT_PORT = "story from StoryTeller";
/* 13:18 */   public static String AUDIENCE_REACTION_OUTPUT_PORT = "simulated audience reaction";
/* 14:   */   
/* 15:   */   public AudienceTestRun()
/* 16:   */   {
/* 17:21 */     setName("AudienceSimulator");
/* 18:22 */     Connections.getPorts(this).addSignalProcessor(UPDATED_STORY_INPUT_PORT, "testRun");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void testRun()
/* 22:   */   {
/* 23:27 */     Connections.getPorts(this).transmit(audienceReaction);
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.AudienceTestRun
 * JD-Core Version:    0.7.0.1
 */