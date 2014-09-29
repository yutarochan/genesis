/*   1:    */ package parameters;
/*   2:    */ 
/*   3:    */ import connections.WiredOnOffSwitch;
/*   4:    */ import connections.WiredToggleSwitch;
/*   5:    */ import javax.swing.JCheckBox;
/*   6:    */ import persistence.JCheckBoxWithMemory;
/*   7:    */ 
/*   8:    */ public class Switch
/*   9:    */ {
/*  10: 17 */   public static final JCheckBoxWithMemory showTextEntryBox = new JCheckBoxWithMemory("Show text box", true);
/*  11: 19 */   public static final JCheckBoxWithMemory showOnsetSwitch = new JCheckBoxWithMemory("Show onsets", false);
/*  12: 21 */   public static final JCheckBoxWithMemory showDisconnectedSwitch = new JCheckBoxWithMemory("Show all story elements", true);
/*  13: 23 */   public static final JCheckBoxWithMemory useSpeechCheckBox = new JCheckBoxWithMemory("Use speech output", true);
/*  14: 25 */   public static final JCheckBoxWithMemory useStartBeta = new JCheckBoxWithMemory("Use experimental START", false);
/*  15: 27 */   public static final JCheckBox testStartBeta = new JCheckBoxWithMemory("Test experimental START", false);
/*  16: 31 */   public static final JCheckBoxWithMemory level6LookForMentalModelEvidence = new JCheckBoxWithMemory(
/*  17: 32 */     "<html>Look for evidence of traits in actions<html>", false);
/*  18: 34 */   public static final JCheckBoxWithMemory level5UseMentalModels = new JCheckBoxWithMemory(
/*  19: 35 */     "<html>Tie actors to trait-specific commonsense, rules, and examples<html>", true);
/*  20: 37 */   public static final JCheckBoxWithMemory level4ConceptPatterns = new JCheckBoxWithMemory("<html>Use concept patterns (Minsky 4, Reflect)", true);
/*  21: 39 */   public static final JCheckBoxWithMemory level3ExplantionRules = new JCheckBoxWithMemory(
/*  22: 40 */     "<html>Use explanation rules (Minsky 3, Deliberate)</html>", true);
/*  23: 42 */   public static final JCheckBoxWithMemory Level2PredictionRules = new JCheckBoxWithMemory(
/*  24: 43 */     "<html>Use prediction rules (Minsky 1 & 2, React)</html>", true);
/*  25: 47 */   public static final JCheckBox useStartServer = new JCheckBox("Use Start server", true);
/*  26: 49 */   public static final JCheckBoxWithMemory findConceptsContinuously = new JCheckBoxWithMemory("Find concepts while reading", true);
/*  27: 51 */   public static final JCheckBox useWordnetCache = new JCheckBox("Use Wordnet cache", true);
/*  28: 53 */   public static final JCheckBoxWithMemory detectMultipleReflectionsSwitch = new JCheckBoxWithMemory("Find multiple interpretations", true);
/*  29: 55 */   public static final JCheckBoxWithMemory reportSubConceptsSwitch = new JCheckBoxWithMemory("Report sub concepts", false);
/*  30: 63 */   public static final JCheckBoxWithMemory summarizeViaPHW = new JCheckBoxWithMemory("Summarize", false);
/*  31: 65 */   public static final JCheckBoxWithMemory includeUnabriggedProcessing = new JCheckBoxWithMemory("Include unabriged version", true);
/*  32: 67 */   public static final JCheckBoxWithMemory postHocProcessing = new JCheckBoxWithMemory("Compress with post hoc ergo propter hoc", true);
/*  33: 69 */   public static final JCheckBoxWithMemory meansProcessing = new JCheckBoxWithMemory("Filter out means", true);
/*  34: 71 */   public static final JCheckBoxWithMemory abductionProcessing = new JCheckBoxWithMemory("Filter out abductions", true);
/*  35: 73 */   public static final JCheckBoxWithMemory includeAgentRolesInSummary = new JCheckBoxWithMemory("Include agent roles", false);
/*  36: 75 */   public static final JCheckBoxWithMemory countConceptNetWords = new JCheckBoxWithMemory("Count concept net words", false);
/*  37: 79 */   public static final JCheckBoxWithMemory showBackgroundElements = new JCheckBoxWithMemory("Don't show background elements", false);
/*  38: 81 */   public static final JCheckBoxWithMemory conceptSwitch = new JCheckBoxWithMemory("Use concepts", false);
/*  39: 83 */   public static final JCheckBox useStartCache = new JCheckBox("Use Start cache", true);
/*  40: 87 */   public static final JCheckBoxWithMemory timers = new JCheckBoxWithMemory("Show general timers");
/*  41: 89 */   public static final JCheckBoxWithMemory startTimer = new JCheckBoxWithMemory("Time start processing");
/*  42: 91 */   public static final JCheckBoxWithMemory assertionTimer = new JCheckBoxWithMemory("Time assertion processing");
/*  43: 93 */   public static final JCheckBoxWithMemory storyTimer = new JCheckBoxWithMemory("Time story processing");
/*  44: 95 */   public static final JCheckBoxWithMemory showStartProcessingDetails = new JCheckBoxWithMemory("Show start processing on console");
/*  45: 97 */   public static final JCheckBoxWithMemory showElaborationViewerDetails = new JCheckBoxWithMemory("Show elaboration details", false);
/*  46: 99 */   public static final JCheckBoxWithMemory showTranslationDetails = new JCheckBoxWithMemory("Note translation details");
/*  47:101 */   public static final JCheckBoxWithMemory useFestival = new JCheckBoxWithMemory("Use festival", false);
/*  48:105 */   public static final JCheckBoxWithMemory stepParser = new JCheckBoxWithMemory("Step the parser");
/*  49:107 */   public static final JCheckBoxWithMemory usePhysicalConceptMemory = new JCheckBoxWithMemory("Store CMEM on drive");
/*  50:109 */   public static final JCheckBoxWithMemory useUnderstand = new JCheckBoxWithMemory("Use Understand");
/*  51:111 */   public static final JCheckBoxWithMemory workWithVision = new JCheckBoxWithMemory("Work with vision");
/*  52:115 */   public static final WiredOnOffSwitch disambiguatorSwitch = new WiredToggleSwitch("Use disambiguator");
/*  53:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     parameters.Switch
 * JD-Core Version:    0.7.0.1
 */