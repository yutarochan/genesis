/*   1:    */ package susanSong;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Port;
/*   8:    */ import connections.Ports;
/*   9:    */ import genesis.FileSourceReader;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Arrays;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.Vector;
/*  14:    */ import models.MentalModel;
/*  15:    */ import storyProcessor.ReflectionAnalysis;
/*  16:    */ import storyProcessor.ReflectionDescription;
/*  17:    */ import storyProcessor.StoryProcessor;
/*  18:    */ import utils.Mark;
/*  19:    */ 
/*  20:    */ public class LocalProcessor
/*  21:    */   extends AbstractWiredBox
/*  22:    */ {
/*  23: 14 */   public final String MY_INPUT_PORT = "default input port";
/*  24: 15 */   public final String MY_OUTPUT_PORT = "default output port";
/*  25:    */   
/*  26:    */   public LocalProcessor()
/*  27:    */   {
/*  28: 19 */     setName("Susan's local processor");
/*  29:    */     
/*  30: 21 */     ArrayList<String> filenames = new ArrayList(Arrays.asList(new String[] { "trait reflective knowledge" }));
/*  31:    */     
/*  32: 23 */     MentalModel traitProcessor = new MentalModel("Input Story Reader");
/*  33: 24 */     MentalModel personalityModel = new MentalModel("Personality Model");
/*  34: 25 */     MentalModel traitIdentifier = new MentalModel("Output Story Reader");
/*  35:    */     
/*  36: 27 */     PersonalityConstructor personalityConstructor = new PersonalityConstructor();
/*  37:    */     
/*  38: 29 */     Connections.wire("complete story events port", traitProcessor.getStoryProcessor(), "story element input port", personalityConstructor);
/*  39: 30 */     Connections.wire("default output port", traitProcessor.getStoryProcessor(), "concept pattern input port", personalityConstructor);
/*  40:    */     
/*  41:    */ 
/*  42: 33 */     Connections.wire("concept pattern output port", personalityConstructor, "port for concept injection", personalityModel);
/*  43: 35 */     for (String file : filenames)
/*  44:    */     {
/*  45: 37 */       traitProcessor.readFile(file);
/*  46: 38 */       Mark.say(new Object[] {"*** DONE READING: " + file + ".txt ***" });
/*  47:    */       
/*  48: 40 */       Sequence story = traitProcessor.getStoryProcessor().extractStory();
/*  49: 41 */       Sequence concepts = traitProcessor.getStoryProcessor().getConceptPatterns();
/*  50: 42 */       Mark.say(new Object[] {"*** NUMBER OF TRAIT CONCEPT PATTERNS EXTRACTED", Integer.valueOf(concepts.getElements().size()) });
/*  51:    */       
/*  52: 44 */       Connections.getPorts(traitProcessor.getStoryProcessor()).getPort("complete story events port").transmit(story);
/*  53:    */       
/*  54: 46 */       Connections.getPorts(traitProcessor.getStoryProcessor()).getPort("default output port").transmit(concepts);
/*  55:    */       
/*  56: 48 */       traitProcessor.getStoryProcessor().clearAllMemories();
/*  57:    */     }
/*  58: 52 */     Sequence personalityModelConcepts = personalityModel.getStoryProcessor().getConceptPatterns();
/*  59: 53 */     Mark.say(new Object[] {"*** PERSONALITY MENTAL MODEL CONCEPT PATTERN COUNT:", Integer.valueOf(personalityModelConcepts.getElements().size()) });
/*  60:    */     
/*  61: 55 */     traitIdentifier.getStoryProcessor().addPredictionRules(personalityModel.getStoryProcessor().getPredictionRules());
/*  62: 56 */     traitIdentifier.getStoryProcessor().addExplanationRules(personalityModel.getStoryProcessor().getExplanationRules());
/*  63: 57 */     traitIdentifier.getStoryProcessor().addCensorRules(personalityModel.getStoryProcessor().getCensorRules());
/*  64: 58 */     traitIdentifier.getStoryProcessor().addConceptPatterns(personalityModel.getStoryProcessor().getConceptPatterns());
/*  65:    */     
/*  66: 60 */     traitIdentifier.getFileSourceReader().readStory("dumas");
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void processSignal(Object signal)
/*  70:    */   {
/*  71: 70 */     if ((signal instanceof Entity))
/*  72:    */     {
/*  73: 71 */       Entity t = (Entity)signal;
/*  74: 72 */       if (t.sequenceP())
/*  75:    */       {
/*  76: 73 */         Sequence s = (Sequence)t;
/*  77: 74 */         Mark.say(new Object[] {"Story received:" });
/*  78: 75 */         for (Entity e : s.getElements())
/*  79:    */         {
/*  80: 77 */           Mark.say(new Object[] {"Instantiated story element:", e.asString() });
/*  81: 78 */           Connections.getPorts(this).transmit(e);
/*  82: 79 */           Connections.getPorts(this).transmit("default output port", e);
/*  83:    */         }
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void processReflectionDiscoveries(Object signal)
/*  89:    */   {
/*  90: 94 */     if ((signal instanceof ReflectionAnalysis))
/*  91:    */     {
/*  92: 95 */       ReflectionAnalysis reflectionAnalysis = (ReflectionAnalysis)signal;
/*  93:    */       Iterator localIterator2;
/*  94: 96 */       for (Iterator localIterator1 = reflectionAnalysis.getReflectionDescriptions().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  95:    */       {
/*  96: 96 */         ReflectionDescription reflectionDescription = (ReflectionDescription)localIterator1.next();
/*  97:    */         
/*  98: 98 */         String name = reflectionDescription.getName();
/*  99:    */         
/* 100:100 */         Sequence sequence = reflectionDescription.getInstantiations();
/* 101:    */         
/* 102:102 */         Mark.say(new Object[0]);
/* 103:103 */         Mark.say(new Object[] {"Reflection pattern noted:", name });
/* 104:104 */         localIterator2 = sequence.getElements().iterator(); continue;Entity t = (Entity)localIterator2.next();
/* 105:105 */         if (t.sequenceP("path"))
/* 106:    */         {
/* 107:106 */           Mark.say(new Object[] {"  Leads-to path" });
/* 108:107 */           for (Entity e : t.getElements()) {
/* 109:108 */             Mark.say(new Object[] {"    Instantiated path element:", e.asString() });
/* 110:    */           }
/* 111:    */         }
/* 112:    */         else
/* 113:    */         {
/* 114:112 */           Mark.say(new Object[] {"Instantiated reflection element:", t.asString() });
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:    */   }
/* 119:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     susanSong.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */