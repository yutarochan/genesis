/*   1:    */ package dylanHolmes;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Ports;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Iterator;
/*  11:    */ import java.util.Vector;
/*  12:    */ import matchers.StandardMatcher;
/*  13:    */ import minilisp.LList;
/*  14:    */ import translator.Translator;
/*  15:    */ import utils.Mark;
/*  16:    */ import utils.PairOfEntities;
/*  17:    */ 
/*  18:    */ public class MeansEndsProcessor
/*  19:    */   extends AbstractWiredBox
/*  20:    */ {
/*  21:    */   public static final String INPUT_COMPLETE_STORY = "my input port";
/*  22:    */   public static final String OUTPUT_GOALS = "my output port";
/*  23:    */   
/*  24:    */   public MeansEndsProcessor()
/*  25:    */   {
/*  26: 28 */     setName("Means-to-an-end detector");
/*  27:    */     
/*  28: 30 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/*  29:    */     
/*  30: 32 */     Connections.getPorts(this).addSignalProcessor("complete story analysis port", "processSignal");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void processSignal(Object signal)
/*  34:    */   {
/*  35: 43 */     if ((signal instanceof BetterSignal))
/*  36:    */     {
/*  37: 45 */       BetterSignal s = (BetterSignal)signal;
/*  38: 46 */       Sequence story = (Sequence)s.get(0, Sequence.class);
/*  39: 47 */       Sequence explicitElements = (Sequence)s.get(1, Sequence.class);
/*  40: 48 */       Sequence inferences = (Sequence)s.get(2, Sequence.class);
/*  41: 49 */       Sequence concepts = (Sequence)s.get(3, Sequence.class);
/*  42:    */       try
/*  43:    */       {
/*  44: 71 */         ArrayList<Goal> matchedGoals = new ArrayList();
/*  45:    */         
/*  46:    */ 
/*  47: 74 */         Translator.getTranslator().translate("xx is a person");
/*  48: 75 */         Translator.getTranslator().translate("yy is a person");
/*  49: 76 */         Translator.getTranslator().translate("zz is a person");
/*  50:    */         
/*  51: 78 */         Translator.getTranslator().translate("ww is an entity");
/*  52:    */         
/*  53: 80 */         Sequence prereqs = new Sequence();
/*  54: 81 */         prereqs.addElement((Entity)Translator.getTranslator().translate("yy is the king").getElements().get(0));
/*  55: 82 */         prereqs.addElement((Entity)Translator.getTranslator().translate("xx is yy's successor").getElements().get(0));
/*  56: 83 */         Goal succession = new Goal(
/*  57: 84 */           "succession", 
/*  58: 85 */           (Entity)Translator.getTranslator().translate("xx becomes king").getElements().get(0), 
/*  59: 86 */           prereqs.deepClone(), 
/*  60: 87 */           (Entity)Translator.getTranslator().translate("xx kills yy").getElements().get(0));
/*  61:    */         
/*  62:    */ 
/*  63:    */ 
/*  64: 91 */         prereqs = new Sequence();
/*  65: 92 */         prereqs.addElement((Entity)Translator.getTranslator().translate("xx wants ww").getElements().get(0));
/*  66: 93 */         prereqs.addElement((Entity)Translator.getTranslator().translate("yy has ww").getElements().get(0));
/*  67: 94 */         Goal theft = new Goal(
/*  68: 95 */           "theft", 
/*  69: 96 */           (Entity)Translator.getTranslator().translate("xx has ww").getElements().get(0), 
/*  70: 97 */           prereqs.deepClone(), 
/*  71: 98 */           (Entity)Translator.getTranslator().translate("xx takes ww from yy").getElements().get(0));
/*  72:    */         
/*  73:    */ 
/*  74:    */ 
/*  75:102 */         prereqs = new Sequence();
/*  76:103 */         prereqs.addElement((Entity)Translator.getTranslator().translate("xx wants ww").getElements().get(0));
/*  77:104 */         prereqs.addElement((Entity)Translator.getTranslator().translate("yy has ww").getElements().get(0));
/*  78:105 */         Goal solicit = new Goal(
/*  79:106 */           "solicit", 
/*  80:107 */           (Entity)Translator.getTranslator().translate("xx has ww").getElements().get(0), 
/*  81:108 */           prereqs.deepClone(), 
/*  82:109 */           (Entity)Translator.getTranslator().translate("xx asks yy for ww").getElements().get(0));
/*  83:    */         
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:114 */         ArrayList<Goal> goalCatalog = new ArrayList();
/*  88:115 */         goalCatalog.add(succession);
/*  89:116 */         goalCatalog.add(theft);
/*  90:    */         
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:125 */         Boolean remember = Boolean.valueOf(story.getElement(0).getName().charAt(0) != "f".charAt(0));
/*  99:127 */         if (remember.booleanValue()) {
/* 100:128 */           goalCatalog.add(solicit);
/* 101:    */         }
/* 102:    */         Iterator localIterator2;
/* 103:    */         Entity e;
/* 104:    */         LList<PairOfEntities> bindings;
/* 105:132 */         for (Iterator localIterator1 = goalCatalog.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 106:    */         {
/* 107:132 */           Goal g = (Goal)localIterator1.next();
/* 108:133 */           localIterator2 = story.getElements().iterator(); continue;e = (Entity)localIterator2.next();
/* 109:    */           
/* 110:135 */           bindings = StandardMatcher.getBasicMatcher().match(e, g.means);
/* 111:138 */           if (bindings != null)
/* 112:    */           {
/* 113:139 */             Goal h = g.emptyMatches();
/* 114:140 */             h.bindings = bindings;
/* 115:141 */             h.means = Goal.reTag("matched", h.means);
/* 116:142 */             matchedGoals.add(h);
/* 117:    */           }
/* 118:    */         }
/* 119:151 */         Boolean proceed = Boolean.valueOf(true);
/* 120:153 */         while (proceed.booleanValue())
/* 121:    */         {
/* 122:154 */           for (e = matchedGoals.iterator(); e.hasNext(); bindings.hasNext())
/* 123:    */           {
/* 124:154 */             Goal g = (Goal)e.next();
/* 125:155 */             bindings = story.getElements().iterator(); continue;Entity e = (Entity)bindings.next();
/* 126:156 */             Entity f = g.match(e);
/* 127:157 */             if (f != null) {
/* 128:158 */               Mark.say(new Object[] {f });
/* 129:    */             }
/* 130:    */           }
/* 131:162 */           proceed = Boolean.valueOf(false);
/* 132:    */         }
/* 133:165 */         BetterSignal op_signal = new BetterSignal(new Object[] { goalCatalog, matchedGoals });
/* 134:166 */         Mark.say(new Object[] {"Transmitting all detected goals from ", getName(), "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" });
/* 135:    */         
/* 136:    */ 
/* 137:169 */         Connections.getPorts(this).transmit("my output port", op_signal);
/* 138:    */       }
/* 139:    */       catch (Exception e1)
/* 140:    */       {
/* 141:173 */         e1.printStackTrace();
/* 142:    */       }
/* 143:    */     }
/* 144:    */   }
/* 145:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.MeansEndsProcessor
 * JD-Core Version:    0.7.0.1
 */