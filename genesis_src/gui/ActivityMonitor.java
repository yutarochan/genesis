/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import connections.AbstractWiredBox;
/*   5:    */ import connections.Connections;
/*   6:    */ import connections.Ports;
/*   7:    */ import java.util.Observable;
/*   8:    */ import java.util.Observer;
/*   9:    */ 
/*  10:    */ public class ActivityMonitor
/*  11:    */   extends AbstractWiredBox
/*  12:    */   implements Observer
/*  13:    */ {
/*  14:    */   public static final String TO_ACTIVITY_MONITOR = "to activity monitor";
/*  15: 18 */   private String startServerState = "START via server";
/*  16: 20 */   public static OnOffLabel serverStartConnection = new OnOffLabel("Parser via server");
/*  17: 22 */   public static OnOffLabel directStartConnection = new OnOffLabel("Parser direct");
/*  18: 24 */   public static OnOffLabel serverStartConnectionFault = new OnOffLabel("Parser failure");
/*  19: 26 */   public static OnOffLabel serverGeneratorConnection = new OnOffLabel("Generator via server");
/*  20: 28 */   public static OnOffLabel directGeneratorConnection = new OnOffLabel("Generator direct");
/*  21: 30 */   public static OnOffLabel translatorLight = new OnOffLabel("Translator");
/*  22: 32 */   public static OnOffLabel serverWordNetConnection = new OnOffLabel("WordNet server");
/*  23: 34 */   public static OnOffLabel betaStartConnection = new OnOffLabel("Parser");
/*  24: 36 */   public static OnOffLabel betaGeneratorConnection = new OnOffLabel("Generator");
/*  25: 38 */   public static OnOffLabel commonsenseLight = new OnOffLabel("Commonsense expert");
/*  26: 40 */   public static OnOffLabel conceptLight = new OnOffLabel("Concept expert");
/*  27: 42 */   public static String START_WORKING = "start working";
/*  28: 44 */   public static String START_SERVER_WORKING = "start server working";
/*  29: 46 */   public static String START_BETA_WORKING = "start beta working";
/*  30: 48 */   public static String GENERATOR_WORKING = "generator working";
/*  31: 50 */   public static String GENERATOR_SERVER_WORKING = "generator server working";
/*  32: 52 */   public static String GENERATOR_BETA_WORKING = "generator beta working";
/*  33: 54 */   public static String TRANSLATOR_WORKING = "translator working";
/*  34: 56 */   public static String WORDNET_SERVER_WORKING = "wordnet server working";
/*  35: 58 */   public static String START_SERVER_FAULT = "start server fault";
/*  36: 60 */   public static String RULE_CHAINER_WORKING = "rule chaining";
/*  37: 62 */   public static String CONCEPT_FINDER_WORKING = "concept finder";
/*  38:    */   private static ActivityMonitor activityMonitor;
/*  39:    */   
/*  40:    */   private ActivityMonitor()
/*  41:    */   {
/*  42: 67 */     Connections.getPorts(this).addSignalProcessor("process");
/*  43:    */   }
/*  44:    */   
/*  45:    */   public static ActivityMonitor getActivityMonitor()
/*  46:    */   {
/*  47: 72 */     if (activityMonitor == null) {
/*  48: 73 */       activityMonitor = new ActivityMonitor();
/*  49:    */     }
/*  50: 75 */     return activityMonitor;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void process(Object o)
/*  54:    */   {
/*  55: 79 */     if ((o instanceof BetterSignal))
/*  56:    */     {
/*  57: 80 */       BetterSignal bs = (BetterSignal)o;
/*  58: 81 */       String name = (String)bs.get(0, String.class);
/*  59: 82 */       boolean state = ((Boolean)bs.get(1, Boolean.class)).booleanValue();
/*  60: 83 */       if (name.equals(START_SERVER_WORKING)) {
/*  61: 84 */         serverStartConnection.setState(state);
/*  62: 86 */       } else if (name.equals(START_WORKING)) {
/*  63: 87 */         directStartConnection.setState(state);
/*  64: 89 */       } else if (name.equals(GENERATOR_WORKING)) {
/*  65: 90 */         directGeneratorConnection.setState(state);
/*  66: 92 */       } else if (name.equals(GENERATOR_SERVER_WORKING)) {
/*  67: 93 */         serverGeneratorConnection.setState(state);
/*  68: 95 */       } else if (name.equals(START_SERVER_FAULT)) {
/*  69: 96 */         serverStartConnectionFault.setState(state);
/*  70: 98 */       } else if (name.equals(START_BETA_WORKING)) {
/*  71: 99 */         betaStartConnection.setState(state);
/*  72:101 */       } else if (name.equals(GENERATOR_BETA_WORKING)) {
/*  73:102 */         betaGeneratorConnection.setState(state);
/*  74:104 */       } else if (name.equals(WORDNET_SERVER_WORKING)) {
/*  75:105 */         serverWordNetConnection.setState(state);
/*  76:107 */       } else if (name.equals(TRANSLATOR_WORKING)) {
/*  77:109 */         translatorLight.setState(state);
/*  78:111 */       } else if (name.equals(RULE_CHAINER_WORKING)) {
/*  79:112 */         commonsenseLight.setState(state);
/*  80:114 */       } else if (name.equals(CONCEPT_FINDER_WORKING)) {
/*  81:115 */         conceptLight.setState(state);
/*  82:    */       }
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void update(Observable o, Object arg)
/*  87:    */   {
/*  88:123 */     process(arg);
/*  89:    */   }
/*  90:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.ActivityMonitor
 * JD-Core Version:    0.7.0.1
 */