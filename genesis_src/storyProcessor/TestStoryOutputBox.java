/*  1:   */ package storyProcessor;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Relation;
/*  6:   */ import bridge.reps.entities.Sequence;
/*  7:   */ import connections.AbstractWiredBox;
/*  8:   */ import connections.Connections;
/*  9:   */ import connections.Ports;
/* 10:   */ import java.util.Vector;
/* 11:   */ import tools.Filters;
/* 12:   */ import utils.Mark;
/* 13:   */ 
/* 14:   */ public class TestStoryOutputBox
/* 15:   */   extends AbstractWiredBox
/* 16:   */ {
/* 17:   */   private static TestStoryOutputBox box;
/* 18:   */   
/* 19:   */   public static TestStoryOutputBox getBox()
/* 20:   */   {
/* 21:21 */     if (box == null) {
/* 22:22 */       box = new TestStoryOutputBox();
/* 23:   */     }
/* 24:24 */     return box;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public TestStoryOutputBox()
/* 28:   */   {
/* 29:28 */     Connections.getPorts(this).addSignalProcessor("processStory");
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void processStory(Object object)
/* 33:   */   {
/* 34:32 */     if ((object instanceof BetterSignal))
/* 35:   */     {
/* 36:33 */       BetterSignal bs = (BetterSignal)object;
/* 37:34 */       Sequence story = (Sequence)bs.get(0, Sequence.class);
/* 38:   */       
/* 39:   */ 
/* 40:   */ 
/* 41:38 */       Vector<Relation> harms = Filters.findActionsBy("paul", Filters.findHarmingActions(story));
/* 42:39 */       for (Entity harm : harms) {
/* 43:40 */         Mark.say(new Object[] {"Paul's harms:", harm });
/* 44:   */       }
/* 45:43 */       Vector<Relation> helps = Filters.findActionsBy("paul", Filters.findHelpingActions(story));
/* 46:44 */       for (Entity help : helps) {
/* 47:45 */         Mark.say(new Object[] {"Paul's helps:", help });
/* 48:   */       }
/* 49:48 */       harms = Filters.findActionsBy("peter", Filters.findHarmingActions(story));
/* 50:49 */       for (Entity harm : harms) {
/* 51:50 */         Mark.say(new Object[] {"Peter's harms:", harm });
/* 52:   */       }
/* 53:53 */       helps = Filters.findActionsBy("peter", Filters.findHelpingActions(story));
/* 54:54 */       for (Entity help : helps) {
/* 55:55 */         Mark.say(new Object[] {"Peter's helps:", help });
/* 56:   */       }
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.TestStoryOutputBox
 * JD-Core Version:    0.7.0.1
 */