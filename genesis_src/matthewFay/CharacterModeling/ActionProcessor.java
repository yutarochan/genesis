/*   1:    */ package matthewFay.CharacterModeling;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import java.util.ArrayList;
/*   9:    */ import java.util.List;
/*  10:    */ import matchers.StandardMatcher;
/*  11:    */ import matthewFay.Utilities.SimpleFileReader;
/*  12:    */ import start.Generator;
/*  13:    */ import translator.Translator;
/*  14:    */ import utils.Mark;
/*  15:    */ 
/*  16:    */ public class ActionProcessor
/*  17:    */   extends AbstractWiredBox
/*  18:    */ {
/*  19:    */   public static final String PLOT_PLAY_BY_PLAY_PORT = "plot play by play port";
/*  20:    */   public static final String STAGE_DIRECTION_PORT = "reset port";
/*  21:    */   
/*  22:    */   public ActionProcessor()
/*  23:    */   {
/*  24: 30 */     setName("CharacterActionProcessor");
/*  25:    */     
/*  26: 32 */     Connections.getPorts(this).addSignalProcessor("reset port", "reset");
/*  27: 33 */     Connections.getPorts(this).addSignalProcessor("plot play by play port", "processPlotElement");
/*  28: 34 */     reset("reset");
/*  29:    */   }
/*  30:    */   
/*  31: 37 */   List<Entity> actionSet = new ArrayList();
/*  32: 39 */   boolean actionAdditionMode = false;
/*  33:    */   
/*  34:    */   public void reset(Object o)
/*  35:    */   {
/*  36: 42 */     Mark.say(
/*  37:    */     
/*  38:    */ 
/*  39:    */ 
/*  40: 46 */       new Object[] { "CCC" });o.equals("reset");
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void loadActionSet(String fileName)
/*  44:    */   {
/*  45: 49 */     SimpleFileReader r = new SimpleFileReader(fileName);
/*  46:    */     
/*  47: 51 */     boolean preconditions = true;
/*  48: 52 */     Translator translator = Translator.getTranslator();
/*  49: 53 */     Generator generator = Generator.getGenerator();
/*  50: 54 */     generator.setStoryMode();
/*  51: 55 */     generator.flush();
/*  52:    */     String line;
/*  53: 58 */     while ((line = r.nextLine()) != null)
/*  54:    */     {
/*  55:    */       String line;
/*  56: 60 */       if (line.contains("Start action library.")) {
/*  57: 61 */         preconditions = false;
/*  58: 64 */       } else if (line.contains("End action library.")) {
/*  59: 65 */         preconditions = true;
/*  60:    */       } else {
/*  61:    */         try
/*  62:    */         {
/*  63: 70 */           Entity element = translator.translate(line).getElement(0);
/*  64: 71 */           if (!preconditions) {
/*  65: 72 */             this.actionSet.add(element);
/*  66:    */           }
/*  67:    */         }
/*  68:    */         catch (Exception e)
/*  69:    */         {
/*  70: 77 */           e.printStackTrace();
/*  71:    */         }
/*  72:    */       }
/*  73:    */     }
/*  74: 81 */     generator.flush();
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void processPlotElement(Object o)
/*  78:    */   {
/*  79: 86 */     BetterSignal s = BetterSignal.isSignal(o);
/*  80: 87 */     if (s == null) {
/*  81: 87 */       return;
/*  82:    */     }
/*  83: 89 */     Entity element = (Entity)s.get(0, Entity.class);
/*  84: 92 */     if (isActionSetStart(element))
/*  85:    */     {
/*  86: 93 */       this.actionAdditionMode = true;
/*  87: 94 */       return;
/*  88:    */     }
/*  89: 96 */     if (isActionSetEnd(element))
/*  90:    */     {
/*  91: 97 */       this.actionAdditionMode = false;
/*  92: 98 */       return;
/*  93:    */     }
/*  94:102 */     if (this.actionAdditionMode) {
/*  95:102 */       this.actionSet.add(element);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public static boolean isActionSetStart(Entity element)
/* 100:    */   {
/* 101:106 */     if ((element.relationP("start")) && 
/* 102:107 */       (element.getObject().sequenceP("roles")) && 
/* 103:108 */       (element.getObject().getElement(0).functionP("object")) && 
/* 104:109 */       (element.getObject().getElement(0).getSubject().entityP("action_library"))) {
/* 105:110 */       return true;
/* 106:    */     }
/* 107:115 */     return false;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public static boolean isActionSetEnd(Entity element)
/* 111:    */   {
/* 112:119 */     if ((element.relationP("end")) && 
/* 113:120 */       (element.getObject().sequenceP("roles")) && 
/* 114:121 */       (element.getObject().getElement(0).functionP("object")) && 
/* 115:122 */       (element.getObject().getElement(0).getSubject().entityP("action_library"))) {
/* 116:123 */       return true;
/* 117:    */     }
/* 118:128 */     return false;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static void main(String[] args)
/* 122:    */     throws Exception
/* 123:    */   {
/* 124:132 */     ActionProcessor ap = new ActionProcessor();
/* 125:133 */     ap.loadActionSet("c:\\users\\matthew\\git\\genesis\\corpora\\stories\\matthewFay\\charactermodelling\\Scratch.txt");
/* 126:    */     
/* 127:135 */     Translator translator = Translator.getTranslator();
/* 128:136 */     Generator generator = Generator.getGenerator();
/* 129:137 */     generator.setStoryMode();
/* 130:138 */     generator.flush();
/* 131:    */     
/* 132:140 */     Entity definition = translator.translate("Macbeth is a character.").getElement(0);
/* 133:141 */     definition = translator.translate("Macbeth is a person.").getElement(0);
/* 134:142 */     Entity definition2 = translator.translate("Duncan is a person.").getElement(0);
/* 135:143 */     Entity event = translator.translate("Macbeth may kill Duncan.").getElement(0);
/* 136:145 */     for (Entity action : ap.actionSet)
/* 137:    */     {
/* 138:146 */       Mark.say(new Object[] {"Matching... " + event + ", " + action });
/* 139:147 */       Mark.say(new Object[] {"Result: " + StandardMatcher.getBasicMatcher().match(action, event) });
/* 140:    */     }
/* 141:    */   }
/* 142:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.CharacterModeling.ActionProcessor
 * JD-Core Version:    0.7.0.1
 */