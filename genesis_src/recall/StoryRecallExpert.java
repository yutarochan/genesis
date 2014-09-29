/*  1:   */ package recall;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import java.util.TreeSet;
/* 10:   */ import storyProcessor.ReflectionAnalysis;
/* 11:   */ import utils.Mark;
/* 12:   */ 
/* 13:   */ public class StoryRecallExpert
/* 14:   */   extends AbstractWiredBox
/* 15:   */ {
/* 16:   */   public static final String MEMORY_PORT = "memory port";
/* 17:   */   public static final String RECALL_PORT = "recall port";
/* 18:   */   private ArrayList<StoryVectorWrapper> memory;
/* 19:19 */   private ArrayList<Sequence> precedents = new ArrayList();
/* 20:   */   
/* 21:   */   public StoryRecallExpert()
/* 22:   */   {
/* 23:22 */     setName("Story memory expert");
/* 24:23 */     Connections.getPorts(this).addSignalProcessor("memory port", "processMemory");
/* 25:24 */     Connections.getPorts(this).addSignalProcessor("recall port", "processRecall");
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void processStory(Sequence story)
/* 29:   */   {
/* 30:28 */     if (!(story instanceof Sequence)) {}
/* 31:   */   }
/* 32:   */   
/* 33:   */   public void processMemory(Object signal)
/* 34:   */   {
/* 35:35 */     if (!(signal instanceof BetterSignal)) {
/* 36:36 */       return;
/* 37:   */     }
/* 38:38 */     BetterSignal bs = (BetterSignal)signal;
/* 39:39 */     Sequence story = (Sequence)bs.get(0, Sequence.class);
/* 40:40 */     ReflectionAnalysis analysis = (ReflectionAnalysis)bs.get(4, ReflectionAnalysis.class);
/* 41:   */     
/* 42:42 */     StoryVectorWrapper storyVectorWrapper = new StoryVectorWrapper(analysis);
/* 43:43 */     getMemory().add(storyVectorWrapper);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void processRecall(Object signal)
/* 47:   */   {
/* 48:49 */     if (!(signal instanceof BetterSignal)) {
/* 49:50 */       return;
/* 50:   */     }
/* 51:52 */     BetterSignal bs = (BetterSignal)signal;
/* 52:53 */     Sequence story = (Sequence)bs.get(0, Sequence.class);
/* 53:54 */     ReflectionAnalysis analysis = (ReflectionAnalysis)bs.get(4, ReflectionAnalysis.class);
/* 54:   */     
/* 55:56 */     processRecall(analysis);
/* 56:   */   }
/* 57:   */   
/* 58:   */   public void processRecall(ReflectionAnalysis analysis)
/* 59:   */   {
/* 60:60 */     this.precedents.clear();
/* 61:61 */     StoryVectorWrapper currentStoryVectorWrapper = new StoryVectorWrapper(analysis);
/* 62:63 */     if (!getMemory().isEmpty())
/* 63:   */     {
/* 64:64 */       TreeSet<MatchWrapper> bestPrecedents = StoryVectorMatcher.findBestMatches(currentStoryVectorWrapper, getMemory());
/* 65:65 */       if (!bestPrecedents.isEmpty()) {
/* 66:74 */         Connections.getPorts(this).transmit(bestPrecedents);
/* 67:   */       }
/* 68:   */     }
/* 69:   */     else
/* 70:   */     {
/* 71:78 */       Mark.say(new Object[] {"No precedents yet" });
/* 72:   */     }
/* 73:   */   }
/* 74:   */   
/* 75:   */   public ArrayList<Sequence> getPrecedents()
/* 76:   */   {
/* 77:83 */     return this.precedents;
/* 78:   */   }
/* 79:   */   
/* 80:   */   public ArrayList<StoryVectorWrapper> getMemory()
/* 81:   */   {
/* 82:87 */     if (this.memory == null) {
/* 83:88 */       this.memory = new ArrayList();
/* 84:   */     }
/* 85:90 */     return this.memory;
/* 86:   */   }
/* 87:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     recall.StoryRecallExpert
 * JD-Core Version:    0.7.0.1
 */