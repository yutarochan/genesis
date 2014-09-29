/*  1:   */ package storyProcessor;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Relation;
/*  6:   */ import bridge.reps.entities.Sequence;
/*  7:   */ import connections.AbstractWiredBox;
/*  8:   */ import connections.Connections;
/*  9:   */ import connections.Ports;
/* 10:   */ import java.util.Collection;
/* 11:   */ import java.util.Vector;
/* 12:   */ import matchers.RuleMatcher;
/* 13:   */ import start.Generator;
/* 14:   */ import utils.Mark;
/* 15:   */ 
/* 16:   */ public class ReflectionOnsetDetector
/* 17:   */   extends AbstractWiredBox
/* 18:   */ {
/* 19:21 */   private static boolean debug = false;
/* 20:   */   public static final String DISCOVERY = "discovery port";
/* 21:   */   public static final String ALERT = "alert port";
/* 22:   */   public static final String GUI = "gui port";
/* 23:   */   public static final String TAB = "tab port";
/* 24:   */   
/* 25:   */   public ReflectionOnsetDetector()
/* 26:   */   {
/* 27:32 */     Connections.getPorts(this).addSignalProcessor("process");
/* 28:33 */     Connections.getPorts(this).addSignalProcessor("discovery port", "processDiscovery");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void processDiscovery(Object signal)
/* 32:   */   {
/* 33:37 */     Mark.say(
/* 34:   */     
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:   */ 
/* 44:   */ 
/* 45:   */ 
/* 46:   */ 
/* 47:   */ 
/* 48:   */ 
/* 49:   */ 
/* 50:   */ 
/* 51:   */ 
/* 52:   */ 
/* 53:   */ 
/* 54:   */ 
/* 55:   */ 
/* 56:   */ 
/* 57:   */ 
/* 58:62 */       new Object[] { Boolean.valueOf(debug), "In ReflectionOnsetDetector.processDiscovery" });BetterSignal pair = BetterSignal.isSignal(signal);
/* 59:39 */     if (pair == null) {
/* 60:40 */       return;
/* 61:   */     }
/* 62:42 */     String name = ((Entity)pair.get(1, Entity.class)).getType();
/* 63:43 */     Entity instantiation = (Entity)pair.get(0, Entity.class);
/* 64:44 */     Entity indications = instantiation.getObject();
/* 65:48 */     for (Entity indication : indications.getElements())
/* 66:   */     {
/* 67:51 */       String remark = "The " + name + " concept predicts ";
/* 68:52 */       remark = remark + Generator.getGenerator().generate(indication);
/* 69:54 */       if (debug) {
/* 70:55 */         remark = remark + ((Entity)pair.get(0, Entity.class)).asString();
/* 71:   */       }
/* 72:57 */       Mark.say(new Object[] {Boolean.valueOf(debug), "The prediction name is", name });
/* 73:   */       
/* 74:59 */       Connections.getPorts(this).transmit("gui port", new BetterSignal(new Object[] { name }));
/* 75:60 */       Connections.getPorts(this).transmit("alert port", new BetterSignal(new Object[] { "Predictions", remark }));
/* 76:   */     }
/* 77:   */   }
/* 78:   */   
/* 79:   */   public void process(Object signal)
/* 80:   */   {
/* 81:66 */     BetterSignal triple = BetterSignal.isSignal(signal);
/* 82:67 */     if (triple == null) {
/* 83:68 */       return;
/* 84:   */     }
/* 85:70 */     String remark = "Onset detector received ";
/* 86:71 */     remark = remark + ((Entity)triple.get(0, Entity.class)).asString();
/* 87:72 */     remark = remark + " and " + ((Collection)triple.get(1, Collection.class)).size() + " rules";
/* 88:73 */     remark = remark + " in story of length " + ((Entity)triple.get(2, Entity.class)).getElements().size();
/* 89:74 */     Mark.say(new Object[] {Boolean.valueOf(debug), remark });
/* 90:75 */     Entity element = (Entity)triple.get(0, Entity.class);
/* 91:76 */     Collection<Relation> rules = (Collection)triple.get(1, Collection.class);
/* 92:77 */     Sequence story = (Sequence)triple.get(2, Entity.class);
/* 93:78 */     RuleMatcher.predict(element, 0, story, this, rules);
/* 94:   */   }
/* 95:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.ReflectionOnsetDetector
 * JD-Core Version:    0.7.0.1
 */