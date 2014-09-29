/*   1:    */ package genesis;
/*   2:    */ 
/*   3:    */ import connections.WiredOnOffSwitch;
/*   4:    */ import connections.WiredToggleSwitch;
/*   5:    */ import javax.swing.JMenu;
/*   6:    */ import javax.swing.JMenuItem;
/*   7:    */ import javax.swing.JRadioButton;
/*   8:    */ import parameters.Radio;
/*   9:    */ 
/*  10:    */ public class GenesisMenus
/*  11:    */   extends GenesisFoundation
/*  12:    */ {
/*  13: 17 */   protected JMenu demonstrationMenu = new JMenu("Demonstrate");
/*  14: 19 */   protected JMenu readMenu = new JMenu("Read");
/*  15: 21 */   protected JMenu recordMenu = new JMenu("Record");
/*  16: 25 */   protected JMenu parserMenu = new JMenu("Parser");
/*  17: 27 */   protected JMenuItem contributorMenuItem = new JMenuItem("Contributors");
/*  18: 29 */   protected JMenuItem readStoryItem = new JMenuItem("Select story");
/*  19: 31 */   protected JMenuItem readDirectoryItem = new JMenuItem("Select story directory");
/*  20: 36 */   protected JMenu personalityMenu = new JMenu("Personality traits");
/*  21: 38 */   protected JMenu emotionMenu = new JMenu("Emotional impact");
/*  22: 40 */   protected JMenu whatIfMenu = new JMenu("What-if processing");
/*  23: 42 */   protected JMenu eastWestMenu = new JMenu("Morris-Peng");
/*  24: 44 */   protected JMenu shakespeareMenu = new JMenu("Shakespeare");
/*  25: 46 */   protected JMenu classicsMenu = new JMenu("Classics");
/*  26: 48 */   protected JMenu conflictsMenu = new JMenu("Conflict analysis");
/*  27: 50 */   protected JMenu featuresMenu = new JMenu("Features");
/*  28: 52 */   protected JMenu gapsMenu = new JMenu("Gap filling & Alignment");
/*  29: 54 */   protected JMenu plotGenerationMenu = new JMenu("Learning & Plot Generation");
/*  30: 56 */   protected JMenuItem luMurder = new JMenuItem("Lu muder");
/*  31: 58 */   protected JMenuItem mcIlvaneMurder = new JMenuItem("McIlvane muder");
/*  32: 60 */   protected JMenuItem primingQuestion = new JMenuItem("Priming question");
/*  33: 62 */   protected JMenuItem hamlet = new JMenuItem("Hamlet");
/*  34: 64 */   protected JMenuItem caesar = new JMenuItem("Caesar");
/*  35: 66 */   protected JMenuItem hamletCaesar = new JMenuItem("Hamlet and Caesar together");
/*  36: 68 */   protected JMenuItem estonia = new JMenuItem("Estonia v Russia");
/*  37: 70 */   protected JMenuItem estoniaWithEmotions = new JMenuItem("Estonia v Russia, emotional content");
/*  38: 72 */   protected JMenuItem estoniaRussiaTwoSides = new JMenuItem("Estonia v Russia, two points of view");
/*  39: 74 */   protected JMenuItem manWhoLaughs = new JMenuItem("The man who laughe---Hugo");
/*  40: 76 */   protected JMenuItem annaKarenina = new JMenuItem("Anna Karenina---Tolstoy");
/*  41: 78 */   protected JMenuItem hajiMurat = new JMenuItem("Haji Murat---Tolstoy");
/*  42: 80 */   protected JMenuItem taliban = new JMenuItem("Taliban v Villiagers, two points of view");
/*  43: 82 */   protected JMenuItem estoniaGeorgia = new JMenuItem("Estonia v Russia and Georgia v Russia");
/*  44: 84 */   protected JMenuItem tet = new JMenuItem("Tet Offensive v Yom Kippur War");
/*  45: 86 */   protected JMenuItem macbeth2align = new JMenuItem("Macbeth Two Perspectives");
/*  46: 88 */   protected JMenuItem maclionking = new JMenuItem("Mac Lion King");
/*  47: 90 */   protected JMenuItem nearmisstraitdemo = new JMenuItem("Near miss trait learning");
/*  48: 92 */   protected JMenuItem macbethBasic = new JMenuItem("Macbeth");
/*  49: 94 */   protected JMenuItem recordMacbeth = new JMenuItem("Macbeth");
/*  50: 96 */   protected JMenuItem recordMacbeth2 = new JMenuItem("Macbeth with two cultures");
/*  51: 98 */   protected JMenuItem recordEstonia2 = new JMenuItem("Estonia with two viewpoints");
/*  52:100 */   protected JMenuItem recordSellOut2 = new JMenuItem("Lost in translation");
/*  53:102 */   protected JMenuItem recordLu = new JMenuItem("Lu murder with priming question");
/*  54:104 */   protected JMenuItem recordMacbethWithMentalModels = new JMenuItem("Macbeth with mental models");
/*  55:106 */   protected JMenuItem recordMacbethWithQuestion = new JMenuItem("Macbeth with question");
/*  56:108 */   protected JMenuItem macbethPlusOnset = new JMenuItem("Macbeth, with onset detection");
/*  57:110 */   protected JMenuItem hamletRecall = new JMenuItem("Hamlet, with precedent recall");
/*  58:112 */   protected JMenuItem macbethTwoCulturesPlusOnset = new JMenuItem("Macbeth, two cultures, with onset detection");
/*  59:114 */   protected JMenuItem macbethTwoCultures = new JMenuItem("Macbeth, two cultures");
/*  60:116 */   protected JMenuItem macbethWithCulturalCompletion = new JMenuItem("Macbeth, cultural completion");
/*  61:118 */   protected JMenuItem mentalModelDemonstration = new JMenuItem("Trait development");
/*  62:120 */   protected JMenuItem mentalModelMacbethDemonstration = new JMenuItem("Macbeth, with traits");
/*  63:122 */   protected JMenuItem whatIfMacbethDemonstration = new JMenuItem("Macbeth, with traits and what if");
/*  64:124 */   protected JMenuItem macbethWithEmotions = new JMenuItem("Macbeth, with emotional content");
/*  65:126 */   protected JMenuItem playByPlayMenuItem = new JMenuItem("Play by play");
/*  66:128 */   protected JMenu generatorMenu = new JMenu("English generator");
/*  67:130 */   protected JMenuItem basicGeneratorItem = new JMenuItem("Basic");
/*  68:132 */   protected JMenuItem advancedGeneratorItem = new JMenuItem("Advanced");
/*  69:134 */   protected JMenu tellStoryMenu = new JMenu("Story telling");
/*  70:136 */   protected JMenuItem teacherStudentItem = new JMenuItem("Compare teacher and student");
/*  71:138 */   protected JMenuItem spoonMacbethItem = new JMenuItem("Spoon-feed Macbeth");
/*  72:140 */   protected JMenuItem explainMacbethItem = new JMenuItem("Explain Macbeth");
/*  73:142 */   protected JMenuItem teachMacbethItem = new JMenuItem("Teach Macbeth");
/*  74:144 */   protected JMenu summaryStoryMenu = new JMenu("Story summary");
/*  75:146 */   protected JMenuItem macbethSummaryItem = new JMenuItem("Macbeth from one perspective");
/*  76:148 */   protected JMenuItem macbethSummariesItem = new JMenuItem("Macbeth from two perspectives");
/*  77:150 */   protected JMenuItem estoniaSummaryItem = new JMenuItem("Estonia vs. Russia");
/*  78:152 */   protected JMenuItem estoniaSummariesItem = new JMenuItem("Estonia vs. Russia from two perspectives");
/*  79:154 */   protected JMenu similarityMenu = new JMenu("Story comparison");
/*  80:156 */   protected JMenuItem threeStoryItem = new JMenuItem("Three conflicts");
/*  81:158 */   protected JMenuItem fiveStoryItem = new JMenuItem("Five conflicts");
/*  82:160 */   protected JMenuItem tenStoryItem = new JMenuItem("Ten conflicts");
/*  83:162 */   protected JMenuItem fifteenStoryItem = new JMenuItem("Fifteen conflicts");
/*  84:164 */   protected JMenuItem causesItem = new JMenuItem("Cause examples");
/*  85:166 */   protected JMenuItem loadAnnotationsItem = new JMenuItem("Load event annotations");
/*  86:168 */   protected JMenuItem printMenuItem = new JMenuItem("Print");
/*  87:170 */   protected JMenu viewMenu = new JMenu("View");
/*  88:172 */   protected JMenuItem genesisStories = new JMenuItem("Genesis/stories");
/*  89:174 */   protected JMenuItem genesisVision = new JMenuItem("Genesis/vision");
/*  90:176 */   public static final WiredOnOffSwitch memorySwitch = new WiredOnOffSwitch("Use memory");
/*  91:178 */   protected WiredToggleSwitch useExternalMovieViewer = new WiredToggleSwitch("Use external movie viewer");
/*  92:    */   
/*  93:    */   public static JRadioButton getSpoonFeedButton()
/*  94:    */   {
/*  95:183 */     return Radio.spoonFeedButton;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public static JRadioButton getPrimingButton()
/*  99:    */   {
/* 100:187 */     return Radio.primingButton;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public static JRadioButton getPrimingWithIntrospectionButton()
/* 104:    */   {
/* 105:191 */     return Radio.primingWithIntrospectionButton;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public static JRadioButton getSummarize()
/* 109:    */   {
/* 110:195 */     return Radio.summarize;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static JRadioButton getConceptSummaryButton()
/* 114:    */   {
/* 115:199 */     return Radio.conceptSummary;
/* 116:    */   }
/* 117:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.GenesisMenus
 * JD-Core Version:    0.7.0.1
 */