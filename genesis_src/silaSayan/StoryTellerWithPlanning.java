/*  1:   */ package silaSayan;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ 
/*  8:   */ public class StoryTellerWithPlanning
/*  9:   */   extends AbstractWiredBox
/* 10:   */ {
/* 11:31 */   private int SPOONFEED = 0;
/* 12:31 */   private int PRIME = 1;
/* 13:31 */   private int PREPERSUADE = 2;
/* 14:31 */   private int PERSUADE = 3;
/* 15:31 */   private int DECEIVE = 4;
/* 16:31 */   private int SUMMARIZE = 5;
/* 17:32 */   private int goal = this.SPOONFEED;
/* 18:33 */   private int STUDENT = 1;
/* 19:33 */   private int GENERIC = 0;
/* 20:34 */   private int audience = this.GENERIC;
/* 21:42 */   public static String RAW_STORY_INPUT_PORT = "unprocessed story coming in as representations";
/* 22:43 */   public static String RAW_STORY_READABLE_INPUT_PORT = "unprocessed story coming in as string";
/* 23:44 */   public static String UPDATED_STORY_INPUT_PORT = "updated version of story";
/* 24:45 */   public static String AUDIENCE_REACTION_INPUT_PORT = "simulated audience reaction";
/* 25:46 */   public static String RELEVANT_STORY_INPUT_PORT = "story relevant to goal";
/* 26:51 */   public static String UPDATED_STORY_OUTPUT_PORT = "unprocessed new story going out";
/* 27:52 */   public static String AUDIENCE_REACTION_OUTPUT_PORT = "simulated audience reaction";
/* 28:53 */   public static String RELEVANT_STORY_OUTPUT_PORT = "story relevant to goal";
/* 29:   */   private Sequence proposedStory;
/* 30:   */   private Sequence finalStory;
/* 31:   */   public static String DECLARED_AUDIENCE;
/* 32:   */   public static String GOAL;
/* 33:   */   
/* 34:   */   public StoryTellerWithPlanning()
/* 35:   */   {
/* 36:56 */     setName("My story teller");
/* 37:   */     
/* 38:58 */     Connections.getPorts(this).addSignalProcessor(RAW_STORY_INPUT_PORT, "storeStory");
/* 39:59 */     Connections.getPorts(this).addSignalProcessor(UPDATED_STORY_INPUT_PORT, "storeStory");
/* 40:60 */     Connections.getPorts(this).transmit(GOAL, Integer.valueOf(this.goal));
/* 41:61 */     Connections.getPorts(this).transmit(DECLARED_AUDIENCE, Integer.valueOf(this.audience));
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void storeStory(Object o)
/* 45:   */   {
/* 46:65 */     if ((o instanceof Sequence))
/* 47:   */     {
/* 48:66 */       this.proposedStory = ((Sequence)o);
/* 49:67 */       Connections.getPorts(this).transmit(UPDATED_STORY_OUTPUT_PORT, this.proposedStory);
/* 50:   */     }
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.StoryTellerWithPlanning
 * JD-Core Version:    0.7.0.1
 */