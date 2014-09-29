/*   1:    */ package dylanHolmes;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.Vector;
/*  11:    */ import matchers.StandardMatcher;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class LacunaProcessor
/*  15:    */   extends AbstractWiredBox
/*  16:    */ {
/*  17:    */   public static final String INPUT_COMPLETE_STORY = "my input port";
/*  18:    */   public static final String OUTPUT_REDUCED_STORY = "my output port";
/*  19: 28 */   public Sequence explicitElements = new Sequence();
/*  20: 30 */   public Sequence lacunae = new Sequence();
/*  21:    */   
/*  22:    */   public LacunaProcessor()
/*  23:    */   {
/*  24: 37 */     setName("Lacuna processor");
/*  25: 38 */     Connections.getPorts(this).addSignalProcessor("my input port", "replaceStory");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void outputReducedStory()
/*  29:    */   {
/*  30: 46 */     Mark.say(
/*  31:    */     
/*  32:    */ 
/*  33:    */ 
/*  34:    */ 
/*  35:    */ 
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:    */ 
/*  51:    */ 
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:    */ 
/*  56:    */ 
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77: 93 */       new Object[] { "outputting reduced story" });Sequence reducedStory = new Sequence();
/*  78:    */     Entity lacuna;
/*  79: 57 */     for (Entity e : this.explicitElements.getElements())
/*  80:    */     {
/*  81: 58 */       Boolean excise = Boolean.valueOf(false);
/*  82: 59 */       for (Iterator localIterator2 = this.lacunae.getElements().iterator(); localIterator2.hasNext();)
/*  83:    */       {
/*  84: 59 */         lacuna = (Entity)localIterator2.next();
/*  85: 60 */         Mark.say(new Object[] {"lacuna: " + lacuna.asString() });
/*  86: 61 */         if (!excise.booleanValue()) {
/*  87: 62 */           excise = Boolean.valueOf(excise.booleanValue() | StandardMatcher.getBasicMatcher().matchAnyPart(lacuna, e) != null);
/*  88:    */         }
/*  89:    */       }
/*  90: 68 */       if (!excise.booleanValue()) {
/*  91: 69 */         reducedStory.addElement(e);
/*  92:    */       }
/*  93:    */     }
/*  94: 73 */     BetterSignal op_signal = new BetterSignal(new Object[] { this.lacunae, this.explicitElements, reducedStory });
/*  95: 74 */     Mark.say(new Object[] {"Transmitting reduced story from ", getName(), "!" });
/*  96:    */     
/*  97: 76 */     Connections.getPorts(this).transmit("my output port", op_signal);
/*  98:    */     
/*  99:    */ 
/* 100: 79 */     Sequence sampleResult = new Sequence();
/* 101: 81 */     for (Entity e : this.explicitElements.getElements()) {
/* 102: 83 */       if (!e.isA("kill")) {
/* 103: 84 */         sampleResult.addElement(e);
/* 104:    */       }
/* 105:    */     }
/* 106: 91 */     Connections.getPorts(this).transmit("my output port", sampleResult);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void addLacuna(Object signal)
/* 110:    */   {
/* 111: 96 */     if ((signal instanceof BetterSignal))
/* 112:    */     {
/* 113: 97 */       BetterSignal s = (BetterSignal)signal;
/* 114: 98 */       Entity lacuna = (Entity)s.get(0, Entity.class);
/* 115: 99 */       this.lacunae.addElement(lacuna);
/* 116:100 */       outputReducedStory();
/* 117:    */     }
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void replaceStory(Object signal)
/* 121:    */   {
/* 122:107 */     if ((signal instanceof BetterSignal))
/* 123:    */     {
/* 124:108 */       Mark.say(new Object[] {"Lacuna processor" });
/* 125:    */       
/* 126:110 */       BetterSignal s = (BetterSignal)signal;
/* 127:111 */       Sequence story = (Sequence)s.get(0, Sequence.class);
/* 128:112 */       Sequence explicitElements = (Sequence)s.get(1, Sequence.class);
/* 129:113 */       Sequence inferences = (Sequence)s.get(2, Sequence.class);
/* 130:114 */       Sequence concepts = (Sequence)s.get(3, Sequence.class);
/* 131:    */       
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:125 */       this.explicitElements = explicitElements;
/* 142:126 */       outputReducedStory();
/* 143:    */     }
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.LacunaProcessor
 * JD-Core Version:    0.7.0.1
 */