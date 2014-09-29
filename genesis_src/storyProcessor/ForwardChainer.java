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
/* 18:   */ public class ForwardChainer
/* 19:   */   extends AbstractWiredBox
/* 20:   */ {
/* 21:20 */   private static boolean debug = false;
/* 22:   */   public static final String INDICATIONS_PORT = "indications port";
/* 23:   */   
/* 24:   */   public ForwardChainer()
/* 25:   */   {
/* 26:25 */     Connections.wire("to activity monitor", this, ActivityMonitor.getActivityMonitor());
/* 27:26 */     Connections.getPorts(this).addSignalProcessor("process");
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void process(Object signal)
/* 31:   */   {
/* 32:31 */     BetterSignal quadruple = BetterSignal.isSignal(signal);
/* 33:32 */     if (quadruple == null) {
/* 34:33 */       return;
/* 35:   */     }
/* 36:35 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.RULE_CHAINER_WORKING, Boolean.valueOf(true) }));
/* 37:36 */     String remark = "Forward chainer received ";
/* 38:37 */     remark = remark + ((Entity)quadruple.get(0, Entity.class)).asString();
/* 39:38 */     remark = remark + " and " + ((Collection)quadruple.get(1, Collection.class)).size() + " rules";
/* 40:39 */     remark = remark + " in story of length " + ((Entity)quadruple.get(2, Entity.class)).getElements().size();
/* 41:40 */     Mark.say(new Object[] {Boolean.valueOf(debug), remark });
/* 42:41 */     Entity element = (Entity)quadruple.get(0, Entity.class);
/* 43:42 */     ArrayList<Relation> rules = (ArrayList)quadruple.get(1, Collection.class);
/* 44:43 */     Sequence story = (Sequence)quadruple.get(2, Entity.class);
/* 45:44 */     int marker = Integer.valueOf(((Integer)quadruple.get(3, Integer.class)).intValue()).intValue();
/* 46:45 */     long anchor = System.currentTimeMillis();
/* 47:   */     
/* 48:47 */     RuleMatcher.predict(element, marker, story, this, rules);
/* 49:48 */     Timer.laptime(true, "Prediction", "Prediction from " + element.asString(), anchor, 1000L);
/* 50:49 */     Connections.getPorts(this).transmit("to activity monitor", new BetterSignal(new Object[] { ActivityMonitor.RULE_CHAINER_WORKING, Boolean.valueOf(false) }));
/* 51:   */   }
/* 52:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.ForwardChainer
 * JD-Core Version:    0.7.0.1
 */