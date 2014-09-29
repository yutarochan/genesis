/*  1:   */ package storyProcessor;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Relation;
/*  6:   */ import bridge.reps.entities.Sequence;
/*  7:   */ import connections.AbstractWiredBox;
/*  8:   */ import connections.Connections;
/*  9:   */ import connections.Ports;
/* 10:   */ import gui.ActivityMonitor;
/* 11:   */ import java.util.ArrayList;
/* 12:   */ import java.util.Collection;
/* 13:   */ import java.util.Vector;
/* 14:   */ import matchers.RuleMatcher;
/* 15:   */ import utils.Mark;
/* 16:   */ import utils.Timer;
/* 17:   */ 
/* 18:   */ public class BackwardChainer
/* 19:   */   extends AbstractWiredBox
/* 20:   */ {
/* 21:   */   public BackwardChainer()
/* 22:   */   {
/* 23:21 */     Connections.wire("to activity monitor", this, ActivityMonitor.getActivityMonitor());
/* 24:22 */     Connections.getPorts(this).addSignalProcessor("process");
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void process(Object signal)
/* 28:   */   {
/* 29:26 */     boolean debug = false;
/* 30:27 */     Mark.say(new Object[] {Boolean.valueOf(debug), "In backward chainer" });
/* 31:28 */     BetterSignal triple = BetterSignal.isSignal(signal);
/* 32:29 */     if (triple == null) {
/* 33:30 */       return;
/* 34:   */     }
/* 35:32 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.RULE_CHAINER_WORKING, Boolean.valueOf(true) }));
/* 36:33 */     String remark = "Backward chainer received ";
/* 37:34 */     remark = remark + ((Entity)triple.get(0, Entity.class)).asString();
/* 38:35 */     remark = remark + " and " + ((Collection)triple.get(1, Collection.class)).size() + " rules";
/* 39:36 */     remark = remark + " in story of length " + ((Entity)triple.get(2, Entity.class)).getElements().size();
/* 40:37 */     Mark.say(new Object[] {Boolean.valueOf(debug), remark });
/* 41:38 */     Entity element = (Entity)triple.get(0, Entity.class);
/* 42:39 */     ArrayList<Relation> rules = (ArrayList)triple.get(1, Collection.class);
/* 43:40 */     Sequence story = (Sequence)triple.get(2, Entity.class);
/* 44:41 */     long anchor = System.currentTimeMillis();
/* 45:   */     
/* 46:43 */     RuleMatcher.explain(element, story, this, rules);
/* 47:44 */     Timer.laptime(debug, "Explanation", "Explanation of " + element.asString(), anchor, 1000L);
/* 48:45 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.RULE_CHAINER_WORKING, Boolean.valueOf(false) }));
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.BackwardChainer
 * JD-Core Version:    0.7.0.1
 */