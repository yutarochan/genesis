/*  1:   */ package silaSayan.Evaluators;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import connections.AbstractWiredBox;
/*  7:   */ import connections.Connections;
/*  8:   */ import connections.Ports;
/*  9:   */ import java.util.Vector;
/* 10:   */ import matchers.StandardMatcher;
/* 11:   */ 
/* 12:   */ public class FullStateComparator
/* 13:   */   extends AbstractWiredBox
/* 14:   */ {
/* 15:   */   public static final String NARRATOR_COMPLETE_STORY_PORT = "narrator elaboration";
/* 16:   */   public static final String AUDIENCE_COMPLETE_STORY_PORT = "audience elaboration";
/* 17:   */   public static final String COMPARATOR_OUTPUT_PORT = "comparison result";
/* 18:17 */   private Sequence narratorElaboration = new Sequence();
/* 19:18 */   private Sequence audienceElaboration = new Sequence();
/* 20:19 */   private Sequence aberrationsFromGoalState = new Sequence();
/* 21:   */   
/* 22:   */   public FullStateComparator()
/* 23:   */   {
/* 24:22 */     setName("Full State Comparator");
/* 25:23 */     Connections.getPorts(this).addSignalProcessor("narrator elaboration", "processNarratorElaboration");
/* 26:24 */     Connections.getPorts(this).addSignalProcessor("audience elaboration", "processAudienceElaboration");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void processNarratorElaboration(Object o)
/* 30:   */   {
/* 31:28 */     if ((o instanceof Sequence)) {
/* 32:29 */       this.narratorElaboration = ((Sequence)o);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void processAudienceElaboration(Object o)
/* 37:   */   {
/* 38:34 */     if ((o instanceof Sequence)) {
/* 39:35 */       this.audienceElaboration = ((Sequence)o);
/* 40:   */     }
/* 41:38 */     if ((!this.narratorElaboration.getElements().isEmpty()) && (!this.audienceElaboration.getElements().isEmpty())) {
/* 42:39 */       compareFullState(this.narratorElaboration, this.audienceElaboration);
/* 43:   */     }
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void compareFullState(Sequence narratorElaboration, Sequence audienceElaboration)
/* 47:   */   {
/* 48:44 */     if (narratorElaboration.equals(audienceElaboration)) {
/* 49:45 */       return;
/* 50:   */     }
/* 51:47 */     StandardMatcher matcher = StandardMatcher.getBasicMatcher();
/* 52:49 */     for (Entity e : audienceElaboration.getElements())
/* 53:   */     {
/* 54:50 */       int matchCount = 0;
/* 55:51 */       for (Entity f : narratorElaboration.getElements()) {
/* 56:52 */         if (matcher.match(e, f) != null)
/* 57:   */         {
/* 58:53 */           matchCount++;
/* 59:54 */           break;
/* 60:   */         }
/* 61:   */       }
/* 62:57 */       if ((matchCount == 0) && 
/* 63:58 */         (!this.aberrationsFromGoalState.contains(e))) {
/* 64:61 */         this.aberrationsFromGoalState.addElement(e);
/* 65:   */       }
/* 66:   */     }
/* 67:66 */     BetterSignal comparisonResult = new BetterSignal(new Object[] { this.aberrationsFromGoalState });
/* 68:67 */     Connections.getPorts(this).transmit("comparison result", comparisonResult);
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     silaSayan.Evaluators.FullStateComparator
 * JD-Core Version:    0.7.0.1
 */