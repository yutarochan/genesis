/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Thread;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.List;
/*   8:    */ import translator.Translator;
/*   9:    */ import utils.Mark;
/*  10:    */ 
/*  11:    */ public class AutomatedLearner
/*  12:    */ {
/*  13: 12 */   private ArrayList<AutomatedPanel> latticeList = new ArrayList();
/*  14: 13 */   private ArrayList<Entity> sentences = new ArrayList();
/*  15: 14 */   private static Translator interpreter = new Translator();
/*  16: 15 */   private ArrayList<FasterLLConcept> conceptList = new ArrayList();
/*  17:    */   private TypeLattice lattice;
/*  18:    */   private FasterLLConcept concept;
/*  19:    */   
/*  20:    */   public AutomatedLearner(ArrayList<String> statements, boolean withUI)
/*  21:    */   {
/*  22: 27 */     for (String sentence : statements) {
/*  23:    */       try
/*  24:    */       {
/*  25: 29 */         Entity tempEntity = interpreter.translate(sentence);
/*  26: 30 */         this.sentences.add(tempEntity);
/*  27:    */       }
/*  28:    */       catch (Exception e)
/*  29:    */       {
/*  30: 32 */         Mark.say(new Object[] {"This sentence could not be parsed. Moving on to the next sentence..." });
/*  31:    */       }
/*  32:    */     }
/*  33: 36 */     parse();
/*  34: 37 */     if (withUI)
/*  35:    */     {
/*  36: 38 */       buildLatticePanels();
/*  37: 39 */       buildUI();
/*  38:    */     }
/*  39:    */     else
/*  40:    */     {
/*  41: 42 */       for (VerbData vd : VerbData.verbs.values())
/*  42:    */       {
/*  43: 43 */         String threadList = vd.getSubjectThreads();
/*  44:    */         
/*  45: 45 */         List<Thread> threads = new ArrayList();
/*  46: 46 */         String[] threadArray = threadList.split("\n\n");
/*  47: 47 */         for (String string : threadArray) {
/*  48: 50 */           threads.add(Thread.parse(string));
/*  49:    */         }
/*  50: 52 */         this.lattice = new TypeLattice(threads);
/*  51: 53 */         this.concept = new FasterLLConcept(this.lattice, vd.getVerb());
/*  52: 54 */         teachLattice(vd, threadArray);
/*  53: 55 */         this.conceptList.add(this.concept);
/*  54: 56 */         if (vd.getVerb().equals("action transfer move propel throw"))
/*  55:    */         {
/*  56: 57 */           Mark.say(new Object[] {"Testing..." });
/*  57:    */           try
/*  58:    */           {
/*  59: 59 */             FasterLLConcept.parseSimpleSentence("A cat can throw a ball.");
/*  60:    */           }
/*  61:    */           catch (Exception e)
/*  62:    */           {
/*  63: 62 */             e.printStackTrace();
/*  64:    */           }
/*  65:    */         }
/*  66:    */       }
/*  67:    */     }
/*  68:    */   }
/*  69:    */   
/*  70:    */   public AutomatedLearner(ArrayList<String> statements)
/*  71:    */   {
/*  72: 70 */     for (String sent : statements) {
/*  73:    */       try
/*  74:    */       {
/*  75: 72 */         Entity tempEntity = interpreter.translate(sent);
/*  76: 73 */         this.sentences.add(tempEntity);
/*  77:    */       }
/*  78:    */       catch (Exception e)
/*  79:    */       {
/*  80: 75 */         Mark.say(new Object[] {"This sentence could not be parsed. Moving on to the next sentence..." });
/*  81:    */       }
/*  82:    */     }
/*  83: 79 */     parse();
/*  84: 80 */     buildLatticePanels();
/*  85: 81 */     buildUI();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void parse()
/*  89:    */   {
/*  90: 89 */     for (Entity sent : this.sentences)
/*  91:    */     {
/*  92: 90 */       String subjectThread = sent.getElement(0).getSubject().getPrimedThread().toString(true);
/*  93: 91 */       String subjectName = sent.getElement(0).getSubject().asStringWithoutIndexes().split("-")[0];
/*  94:    */       
/*  95: 93 */       String verbName = sent.getElement(0).getPrimedThread().toString(true);
/*  96: 94 */       VerbData tempVerb = VerbData.getVerbData(verbName);
/*  97: 96 */       if (sent.getElement(0).hasFeature("not")) {
/*  98: 97 */         tempVerb.addSubject(subjectName, subjectThread, false);
/*  99:    */       } else {
/* 100:101 */         tempVerb.addSubject(subjectName, subjectThread, true);
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void buildLatticePanels()
/* 106:    */   {
/* 107:107 */     for (VerbData data : VerbData.verbs.values())
/* 108:    */     {
/* 109:108 */       AutomatedPanel tempPanel = new AutomatedPanel(data);
/* 110:109 */       this.latticeList.add(tempPanel);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   private void buildUI()
/* 115:    */   {
/* 116:114 */     for (AutomatedPanel lp : this.latticeList)
/* 117:    */     {
/* 118:115 */       AutomatedUI tempUI = new AutomatedUI(lp, lp.getName());
/* 119:    */       
/* 120:117 */       tempUI.setVisible(true);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void teachLattice(VerbData vd, String[] threads)
/* 125:    */   {
/* 126:128 */     for (String thread : threads) {
/* 127:129 */       if (((Boolean)vd.getSubjectMap().get(thread)).booleanValue()) {
/* 128:131 */         this.concept.learnPositive((String)vd.getThreadMap().get(thread));
/* 129:    */       } else {
/* 130:135 */         this.concept.learnNegative((String)vd.getThreadMap().get(thread));
/* 131:    */       }
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   public static String answerQuestion(String question, AutomatedPanel lp)
/* 136:    */     throws Exception
/* 137:    */   {
/* 138:141 */     Entity ansEntity = interpreter.translate(question);
/* 139:142 */     if (question.contains("?"))
/* 140:    */     {
/* 141:143 */       String ansSubject = "";
/* 142:    */       try
/* 143:    */       {
/* 144:145 */         ansSubject = ansEntity.getElement(0).getSubject().getSubject().asStringWithoutIndexes().split("-")[0];
/* 145:    */       }
/* 146:    */       catch (Exception e)
/* 147:    */       {
/* 148:149 */         ansSubject = ansEntity.getElement(0).getSubject().getFeatures().toString().split(" ")[1];
/* 149:    */       }
/* 150:151 */       if (lp.getConcept().infer(ansSubject)) {
/* 151:152 */         return "Yes.";
/* 152:    */       }
/* 153:155 */       return "No.";
/* 154:    */     }
/* 155:160 */     String ansSubject = ansEntity.getElement(0).getSubject().asStringWithoutIndexes().split("-")[0];
/* 156:161 */     if (lp.getConcept().infer(ansSubject)) {
/* 157:162 */       return "True.";
/* 158:    */     }
/* 159:164 */     if (!lp.getConcept().infer(ansSubject)) {
/* 160:165 */       return "False.";
/* 161:    */     }
/* 162:168 */     ansSubject = ansEntity.getElement(0).getObject().getFeatures().toString().split(" ")[1];
/* 163:169 */     if (lp.getConcept().infer(ansSubject)) {
/* 164:170 */       return "True.";
/* 165:    */     }
/* 166:172 */     if (!lp.getConcept().infer(ansSubject)) {
/* 167:173 */       return "False.";
/* 168:    */     }
/* 169:176 */     return "This lattice does not contain the information requested.";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public ArrayList<FasterLLConcept> getConceptList()
/* 173:    */   {
/* 174:183 */     return this.conceptList;
/* 175:    */   }
/* 176:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.AutomatedLearner
 * JD-Core Version:    0.7.0.1
 */