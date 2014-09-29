/*   1:    */ package jingJian;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import java.util.Vector;
/*  10:    */ import translator.Translator;
/*  11:    */ import utils.Mark;
/*  12:    */ 
/*  13:    */ public class SelfReflectionProcessor
/*  14:    */   extends AbstractWiredBox
/*  15:    */ {
/*  16:    */   public static final String STORY_INPUT = "Story input port";
/*  17:    */   public static final String REFLECTION_INPUT = "Reflection input port";
/*  18:    */   public static final String STEP_REFLECTION_INPUT = "Step by step reflection input port";
/*  19:    */   public static final String REFLECTION_ANALYSIS_INPUT = "reflection analysis input port";
/*  20:    */   public static final String RULES = "Rules input port";
/*  21:    */   public static final String PATTERNS = "Patterns input port";
/*  22:    */   public static final String CHARACTER_DETECTION = "Character detection output port";
/*  23:    */   public static final String CHARACTER_DETECTION_RESULT = "Character detection result input port";
/*  24: 47 */   private Sequence character_intro_pattern = null;
/*  25: 49 */   private MentalModel defaultMind = new MentalModel();
/*  26:    */   
/*  27:    */   public SelfReflectionProcessor()
/*  28:    */   {
/*  29: 53 */     Connections.getPorts(this).addSignalProcessor("Story input port", "processStory");
/*  30: 54 */     Connections.getPorts(this).addSignalProcessor("Reflection input port", "processReflections");
/*  31: 55 */     Connections.getPorts(this).addSignalProcessor("Step by step reflection input port", "processStepReflections");
/*  32: 56 */     Connections.getPorts(this).addSignalProcessor("Character detection result input port", "processCharacterDetectionReflections");
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void processStory(Object object)
/*  36:    */   {
/*  37: 60 */     if (!(object instanceof Entity)) {
/*  38: 61 */       return;
/*  39:    */     }
/*  40: 63 */     Entity element = (Entity)object;
/*  41: 64 */     Mark.say(new Object[] {"Entering JJ's processor, new element is", element.asString() });
/*  42: 65 */     BetterSignal triple = new BetterSignal();
/*  43: 66 */     triple.add(getCharacter_intro_pattern1());
/*  44: 67 */     triple.add(element);
/*  45: 68 */     triple.add(new Sequence());
/*  46:    */     
/*  47: 70 */     Mark.say(new Object[] {"TEST getting reflections :: " + ((Entity)triple.get(0, Entity.class)).asString() });
/*  48: 71 */     Mark.say(new Object[] {"TEST getting story :: " + ((Entity)triple.get(1, Entity.class)).asString() });
/*  49: 72 */     Mark.say(new Object[] {"TEST getting inferences :: " + ((Entity)triple.get(2, Entity.class)).asString() });
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void processReflections(Object object)
/*  53:    */   {
/*  54: 86 */     if (!(object instanceof Sequence)) {
/*  55: 87 */       return;
/*  56:    */     }
/*  57: 89 */     Sequence reflections = (Sequence)object;
/*  58: 90 */     Mark.say(new Object[] {"Entering JJ's process, reflection count is", Integer.valueOf(reflections.getElements().size()) });
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void processCharacterDetectionReflections(Object object)
/*  62:    */   {
/*  63: 96 */     if (!(object instanceof Sequence)) {
/*  64: 97 */       return;
/*  65:    */     }
/*  66: 99 */     Sequence reflections = (Sequence)object;
/*  67:100 */     Mark.say(new Object[] {"Entering JJ's process, reflection count is", Integer.valueOf(reflections.getElements().size()) });
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void processStepReflections(Object object)
/*  71:    */   {
/*  72:104 */     if (!(object instanceof Entity)) {
/*  73:105 */       return;
/*  74:    */     }
/*  75:107 */     Entity reflection = (Entity)object;
/*  76:108 */     Mark.say(new Object[] {"Entering JJ's process, new step by step reflection is", reflection.asString() });
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void processTestReflections(Object signal)
/*  80:    */   {
/*  81:112 */     BetterSignal triple = BetterSignal.isSignal(signal);
/*  82:113 */     if (triple == null) {
/*  83:114 */       return;
/*  84:    */     }
/*  85:116 */     Mark.say(new Object[] {"Entering JJ's test process, new test reflection is" });
/*  86:    */   }
/*  87:    */   
/*  88:    */   public MentalModel getDefaultMind()
/*  89:    */   {
/*  90:121 */     if (this.defaultMind == null) {
/*  91:122 */       this.defaultMind = new MentalModel();
/*  92:    */     }
/*  93:124 */     return this.defaultMind;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public Sequence getCharacter_intro_pattern1()
/*  97:    */   {
/*  98:128 */     if (this.character_intro_pattern == null)
/*  99:    */     {
/* 100:129 */       String character_intro = "xx is a person.";
/* 101:    */       try
/* 102:    */       {
/* 103:131 */         this.character_intro_pattern = ((Sequence)Translator.getTranslator().translate(character_intro));
/* 104:    */       }
/* 105:    */       catch (Exception e)
/* 106:    */       {
/* 107:134 */         e.printStackTrace();
/* 108:    */       }
/* 109:    */     }
/* 110:137 */     return this.character_intro_pattern;
/* 111:    */   }
/* 112:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     jingJian.SelfReflectionProcessor
 * JD-Core Version:    0.7.0.1
 */