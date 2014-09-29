/*   1:    */ package victorYarlott;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Set;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class LocalProcessor
/*  15:    */   extends AbstractWiredBox
/*  16:    */ {
/*  17: 23 */   public final String MY_INPUT_PORT = "my input port";
/*  18: 25 */   public final String MY_OUTPUT_PORT = "my output port";
/*  19:    */   
/*  20:    */   public LocalProcessor()
/*  21:    */   {
/*  22: 34 */     setName("Victor's local processor");
/*  23:    */     
/*  24:    */ 
/*  25: 37 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/*  26:    */     
/*  27: 39 */     Connections.getPorts(this).addSignalProcessor("complete story analysis port", "processSignal");
/*  28:    */   }
/*  29:    */   
/*  30:    */   public void processSignal(Object signal)
/*  31:    */   {
/*  32: 71 */     if ((signal instanceof BetterSignal))
/*  33:    */     {
/*  34: 73 */       BetterSignal s = (BetterSignal)signal;
/*  35: 74 */       Sequence story = (Sequence)s.get(0, Sequence.class);
/*  36: 75 */       Sequence explicitElements = (Sequence)s.get(1, Sequence.class);
/*  37: 76 */       Sequence inferences = (Sequence)s.get(2, Sequence.class);
/*  38: 77 */       Sequence concepts = (Sequence)s.get(3, Sequence.class);
/*  39:    */       
/*  40: 79 */       Mark.say(new Object[] {"\n\n\nStory elements" });
/*  41: 80 */       for (Entity e : story.getElements()) {
/*  42: 81 */         Mark.say(new Object[] {e.asString() });
/*  43:    */       }
/*  44: 83 */       Mark.say(new Object[] {"\n\n\nExplicit story elements" });
/*  45: 84 */       for (Entity e : explicitElements.getElements()) {
/*  46: 85 */         Mark.say(new Object[] {e.asString() });
/*  47:    */       }
/*  48: 87 */       Mark.say(new Object[] {"\n\n\nInstantiated commonsense rules" });
/*  49: 88 */       for (Entity e : inferences.getElements()) {
/*  50: 89 */         Mark.say(new Object[] {e.asString() });
/*  51:    */       }
/*  52: 91 */       Mark.say(new Object[] {"\n\n\nInstantiated concept patterns" });
/*  53: 92 */       for (Entity e : concepts.getElements()) {
/*  54: 93 */         Mark.say(new Object[] {e.asString() });
/*  55:    */       }
/*  56: 97 */       List<Entity> allElements = new ArrayList();
/*  57: 98 */       allElements.addAll(story.getElements());
/*  58: 99 */       allElements.addAll(explicitElements.getElements());
/*  59:100 */       allElements.addAll(inferences.getElements());
/*  60:101 */       allElements.addAll(concepts.getElements());
/*  61:    */       
/*  62:103 */       ContradictionEngine ce = new ContradictionEngine();
/*  63:    */       
/*  64:105 */       Set<Pair<Entity>> cset = ce.getContradictions(allElements);
/*  65:    */       
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:115 */       Mark.say(new Object[] {"\n\n\nDiscovered Contradictions" });
/*  75:116 */       for (Pair<Entity> p : cset) {
/*  76:117 */         Mark.say(new Object[] {"Contradiction:\n" + p });
/*  77:    */       }
/*  78:119 */       Mark.say(new Object[] {"\n\n\nFiltered Contradictions: harm" });
/*  79:120 */       for (Pair<Entity> p : ce.findContradictionsByType(cset, "harm")) {
/*  80:121 */         Mark.say(new Object[] {"Contradiction:\n" + p });
/*  81:    */       }
/*  82:124 */       Mark.say(new Object[] {"\n\n\nFiltered Contradictions: kiss" });
/*  83:125 */       for (Pair<Entity> p : ce.findContradictionsByType(cset, "kiss")) {
/*  84:126 */         Mark.say(new Object[] {"Contradiction:\n" + p });
/*  85:    */       }
/*  86:    */     }
/*  87:    */   }
/*  88:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     victorYarlott.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */