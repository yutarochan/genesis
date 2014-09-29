/*  1:   */ package silaSayan.narrativeTools;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import java.util.HashSet;
/*  9:   */ import java.util.Iterator;
/* 10:   */ import java.util.Set;
/* 11:   */ import java.util.Vector;
/* 12:   */ import matchers.StandardMatcher;
/* 13:   */ import summarizer.Summarizer;
/* 14:   */ 
/* 15:   */ public class StoryElementSubtractor
/* 16:   */   extends AbstractWiredBox
/* 17:   */ {
/* 18:   */   public static final String COMPARISON_RESULT_INPUT_PORT = "comparison results";
/* 19:   */   public static final String ORIGINAL_STORY_PORT = "original story";
/* 20:   */   public static final String AUDIENCE_COMPLETE_STORY_PORT = "audience elaboration";
/* 21:   */   public static final String EDITED_STORY_PORT = "edited story";
/* 22:22 */   private Sequence storyToEdit = new Sequence();
/* 23:23 */   private Sequence audienceElaboration = new Sequence();
/* 24:24 */   private Sequence undesirableAudienceInferences = new Sequence();
/* 25:25 */   private Sequence elementsToSubtract = new Sequence();
/* 26:26 */   private Sequence editedStory = new Sequence();
/* 27:28 */   private boolean gotOriginalStory = false;
/* 28:29 */   private boolean gotAudienceElaboration = false;
/* 29:30 */   private boolean gotComparisonResults = false;
/* 30:   */   
/* 31:   */   public StoryElementSubtractor()
/* 32:   */   {
/* 33:34 */     setName("Story Element Subtractor");
/* 34:35 */     Connections.getPorts(this).addSignalProcessor("original story", "processOriginalStory");
/* 35:36 */     Connections.getPorts(this).addSignalProcessor("audience elaboration", "processAudienceElaboration");
/* 36:37 */     Connections.getPorts(this).addSignalProcessor("comparison results", "processComparisonResults");
/* 37:   */   }
/* 38:   */   
/* 39:   */   public void processOriginalStory(Object o)
/* 40:   */   {
/* 41:40 */     if ((o instanceof Sequence))
/* 42:   */     {
/* 43:41 */       this.storyToEdit = ((Sequence)o);
/* 44:42 */       this.gotOriginalStory = true;
/* 45:   */     }
/* 46:   */   }
/* 47:   */   
/* 48:   */   public void processAudienceElaboration(Object o)
/* 49:   */   {
/* 50:46 */     if ((o instanceof Sequence))
/* 51:   */     {
/* 52:47 */       this.audienceElaboration = ((Sequence)o);
/* 53:48 */       this.gotAudienceElaboration = true;
/* 54:   */     }
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void processComparisonResults(Object o)
/* 58:   */   {
/* 59:52 */     if ((o instanceof Sequence))
/* 60:   */     {
/* 61:53 */       this.undesirableAudienceInferences = ((Sequence)o);
/* 62:54 */       this.gotComparisonResults = true;
/* 63:   */     }
/* 64:56 */     if ((this.gotComparisonResults) && (this.gotOriginalStory) && (this.gotAudienceElaboration)) {
/* 65:57 */       traceUndesirableStoryElements(this.undesirableAudienceInferences, this.audienceElaboration);
/* 66:   */     }
/* 67:   */   }
/* 68:   */   
/* 69:   */   public void traceUndesirableStoryElements(Sequence undesirableInferences, Sequence audienceElaboration)
/* 70:   */   {
/* 71:61 */     Set<Entity> undesirableRootsSet = new HashSet();
/* 72:62 */     Summarizer summarizer = Summarizer.getSummarizer();
/* 73:   */     Iterator localIterator2;
/* 74:63 */     for (Iterator localIterator1 = undesirableInferences.getElements().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 75:   */     {
/* 76:63 */       Entity unwanted = (Entity)localIterator1.next();
/* 77:64 */       StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 78:65 */       localIterator2 = audienceElaboration.getElements().iterator(); continue;Entity f = (Entity)localIterator2.next();
/* 79:66 */       matcher.match(unwanted, f);
/* 80:   */     }
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.narrativeTools.StoryElementSubtractor
 * JD-Core Version:    0.7.0.1
 */