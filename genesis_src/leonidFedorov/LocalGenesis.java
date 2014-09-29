/*   1:    */ package leonidFedorov;
/*   2:    */ 
/*   3:    */ import connections.Connections;
/*   4:    */ import genesis.Genesis;
/*   5:    */ import start.Start;
/*   6:    */ import start.StartPreprocessor;
/*   7:    */ import utils.Mark;
/*   8:    */ 
/*   9:    */ public class LocalGenesis
/*  10:    */   extends Genesis
/*  11:    */ {
/*  12:    */   LocalProcessor localProcessor;
/*  13:    */   RuleToReflectionMatcher ruleToReflectionMatcher;
/*  14:    */   
/*  15:    */   public LocalGenesis()
/*  16:    */   {
/*  17: 29 */     Mark.say(new Object[] {"Leonid's LocalGenesis's constructor" });
/*  18:    */     
/*  19:    */ 
/*  20:    */ 
/*  21:    */ 
/*  22:    */ 
/*  23:    */ 
/*  24:    */ 
/*  25:    */ 
/*  26:    */ 
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34: 46 */     Connections.wire("rule port", getMentalModel1(), "Raw commonsense rule listener port 1", getRuleToReflectionMatcher());
/*  35:    */     
/*  36: 48 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "Raw commonsense rule listener port 1", getRuleToReflectionMatcher());
/*  37:    */     
/*  38:    */ 
/*  39: 51 */     Connections.wire("rule port", getMentalModel2(), "Raw commonsense rule listener port 2", getRuleToReflectionMatcher());
/*  40:    */     
/*  41: 53 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "Raw commonsense rule listener port 2", getRuleToReflectionMatcher());
/*  42:    */     
/*  43: 55 */     Mark.say(new Object[] {"Warning, code beyond here has been pruned to remove compile errors!" });
/*  44:    */     
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49: 61 */     Connections.wire("elaboration display port", getInstRulePlotUnitBar1(), "Single instantiated reflection port 1", getRuleToReflectionMatcher());
/*  50:    */     
/*  51: 63 */     Connections.wire("elaboration display port", getInstRulePlotUnitBar2(), "Single instantiated reflection port 2", getRuleToReflectionMatcher());
/*  52:    */     
/*  53: 65 */     Mark.say(new Object[] {"Warning, code beyond here has been pruned to remove compile errors!" });
/*  54:    */     
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59: 71 */     Connections.wire("elaboration display port", getRulePlotUnitBar1(), "Single raw reflection port 1", getRuleToReflectionMatcher());
/*  60:    */     
/*  61: 73 */     Connections.wire("elaboration display port", getRulePlotUnitBar2(), "Single raw reflection port 2", getRuleToReflectionMatcher());
/*  62:    */     
/*  63:    */ 
/*  64: 76 */     Connections.wire("raw rule port 1", getRuleToReflectionMatcher(), "final-inference", getRuleViewer1());
/*  65:    */     
/*  66: 78 */     Connections.wire("raw rule port 2", getRuleToReflectionMatcher(), "final-inference", getRuleViewer2());
/*  67:    */     
/*  68:    */ 
/*  69:    */ 
/*  70: 82 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "bias listener port 2", getRuleToReflectionMatcher());
/*  71:    */     
/*  72: 84 */     Mark.say(new Object[] {"Warning, code beyond here has been pruned to remove compile errors!" });
/*  73:    */   }
/*  74:    */   
/*  75:    */   public LocalProcessor getLocalProcessor()
/*  76:    */   {
/*  77: 91 */     if (this.localProcessor == null) {
/*  78: 92 */       this.localProcessor = new LocalProcessor();
/*  79:    */     }
/*  80: 94 */     return this.localProcessor;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public RuleToReflectionMatcher getRuleToReflectionMatcher()
/*  84:    */   {
/*  85: 98 */     if (this.ruleToReflectionMatcher == null) {
/*  86: 99 */       this.ruleToReflectionMatcher = new RuleToReflectionMatcher();
/*  87:    */     }
/*  88:101 */     return this.ruleToReflectionMatcher;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static void main(String[] args)
/*  92:    */   {
/*  93:105 */     LocalGenesis myGenesis = new LocalGenesis();
/*  94:106 */     myGenesis.startInFrame();
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     leonidFedorov.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */