/*  1:   */ package silaSayan;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import genesis.Genesis;
/*  5:   */ import storyProcessor.StoryProcessor;
/*  6:   */ import utils.Mark;
/*  7:   */ 
/*  8:   */ public class LocalGenesis
/*  9:   */   extends Genesis
/* 10:   */ {
/* 11:   */   LocalProcessor localProcessor;
/* 12:   */   
/* 13:   */   public LocalGenesis()
/* 14:   */   {
/* 15:22 */     Mark.say(new Object[] {"Sila's LocalGenesis's constructor" });
/* 16:   */     
/* 17:   */ 
/* 18:   */ 
/* 19:26 */     Connections.wire("complete story events port", getMentalModel1(), getLocalProcessor());
/* 20:   */     
/* 21:   */ 
/* 22:   */ 
/* 23:30 */     Connections.wire("new  element port", getMentalModel1(), "story 1", getLocalProcessor());
/* 24:   */     
/* 25:32 */     Connections.wire("new  element port", getMentalModel2(), "story 2", getLocalProcessor());
/* 26:   */     
/* 27:34 */     Connections.wire("next", getAnaphoraExpert(), "story plot", getLocalProcessor());
/* 28:   */     
/* 29:36 */     Connections.wire("Instantiated reflections port", getMentalModel1(), "reflection port 1", getLocalProcessor());
/* 30:37 */     Connections.wire("Instantiated reflections port", getMentalModel2(), "reflection port 2", getLocalProcessor());
/* 31:   */     
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:44 */     Connections.wire("quiescence port", getMentalModel1(), "quiescence port 1", getLocalProcessor());
/* 38:45 */     Connections.wire("quiescence port", getMentalModel2(), "quiescence port 2", getLocalProcessor());
/* 39:   */     
/* 40:47 */     Connections.wire("inferences", getMentalModel1(), "inferences", getLocalProcessor());
/* 41:48 */     Connections.wire("incremental output port", getMentalModel1(), "increment", getLocalProcessor());
/* 42:   */     
/* 43:50 */     Connections.wire("rule port", getMentalModel1(), "rules", getLocalProcessor());
/* 44:   */     
/* 45:52 */     Connections.wire(StoryProcessor.START_STORY_INFO_PORT, getMentalModel1(), "start story info 1", getLocalProcessor());
/* 46:53 */     Connections.wire(StoryProcessor.START_STORY_INFO_PORT, getMentalModel2(), "start story info 2", getLocalProcessor());
/* 47:   */     
/* 48:55 */     Connections.wire(LocalProcessor.TEACH_RULE_PORT, getLocalProcessor(), StoryProcessor.LEARNED_RULE_PORT, getMentalModel2());
/* 49:56 */     Connections.wire(LocalProcessor.NEW_RULE_MESSENGER_PORT, getLocalProcessor(), StoryProcessor.NEW_RULE_MESSENGER_PORT, getMentalModel2());
/* 50:   */     
/* 51:   */ 
/* 52:59 */     Connections.wire("switch tab", getLocalProcessor(), "switch tab", getResultContainer());
/* 53:60 */     Connections.wire(getLocalProcessor(), getResultContainer());
/* 54:   */   }
/* 55:   */   
/* 56:   */   public LocalProcessor getLocalProcessor()
/* 57:   */   {
/* 58:65 */     if (this.localProcessor == null) {
/* 59:66 */       this.localProcessor = new LocalProcessor();
/* 60:   */     }
/* 61:68 */     return this.localProcessor;
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static void main(String[] args)
/* 65:   */   {
/* 66:73 */     LocalGenesis myGenesis = new LocalGenesis();
/* 67:74 */     myGenesis.startInFrame();
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */