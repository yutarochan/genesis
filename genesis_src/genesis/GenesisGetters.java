/*    1:     */ package genesis;
/*    2:     */ 
/*    3:     */ import Co57.tools.Co57Simulator;
/*    4:     */ import Signals.BetterSignal;
/*    5:     */ import bridge.modules.memory.EntityMemory;
/*    6:     */ import bridge.reps.entities.Entity;
/*    7:     */ import carynKrakauer.SimilarityProcessor;
/*    8:     */ import carynKrakauer.SimilarityViewer;
/*    9:     */ import com.ascent.gui.frame.ABasicFrame;
/*   10:     */ import connections.AbstractWiredBox;
/*   11:     */ import connections.BackgroundWiredBox;
/*   12:     */ import connections.Connections;
/*   13:     */ import connections.Ports;
/*   14:     */ import connections.QueuingWiredBox;
/*   15:     */ import connections.TextEntryBox;
/*   16:     */ import connections.WiredBox;
/*   17:     */ import connections.WiredDistributorBox;
/*   18:     */ import connections.WiredOnOffSwitch;
/*   19:     */ import connections.views.Adapter;
/*   20:     */ import connections.views.ConnectionViewer;
/*   21:     */ import demos.DemoAnchor;
/*   22:     */ import dictionary.DictionaryPage;
/*   23:     */ import disambiguator.Disambiguator;
/*   24:     */ import escalation.EscalationExpert;
/*   25:     */ import expert.AgentExpert;
/*   26:     */ import expert.AnaphoraExpert;
/*   27:     */ import expert.BeliefExpert;
/*   28:     */ import expert.CauseExpert;
/*   29:     */ import expert.CoercionExpert;
/*   30:     */ import expert.CommandExpert;
/*   31:     */ import expert.ComparisonExpert;
/*   32:     */ import expert.DescribeExpert;
/*   33:     */ import expert.EntityExpert;
/*   34:     */ import expert.ExpectationExpert;
/*   35:     */ import expert.GoalExpert;
/*   36:     */ import expert.IdiomExpert;
/*   37:     */ import expert.ImaginationExpert;
/*   38:     */ import expert.IntentionExpert;
/*   39:     */ import expert.JobExpert;
/*   40:     */ import expert.MoodExpert;
/*   41:     */ import expert.PartExpert;
/*   42:     */ import expert.PathElementExpert;
/*   43:     */ import expert.PathExpert;
/*   44:     */ import expert.PersonalityExpert;
/*   45:     */ import expert.PersuationExpert;
/*   46:     */ import expert.PlaceExpert;
/*   47:     */ import expert.PossessionExpert;
/*   48:     */ import expert.PropertyExpert;
/*   49:     */ import expert.QuestionExpert;
/*   50:     */ import expert.RoleExpert;
/*   51:     */ import expert.SimpleGenerator;
/*   52:     */ import expert.SocialExpert;
/*   53:     */ import expert.StateExpert;
/*   54:     */ import expert.StoryExpert;
/*   55:     */ import expert.ThreadExpert;
/*   56:     */ import expert.TimeExpert;
/*   57:     */ import expert.TrajectoryExpert;
/*   58:     */ import expert.TransferExpert;
/*   59:     */ import expert.TransitionExpert;
/*   60:     */ import force.ForceInterpreter;
/*   61:     */ import frames.BensGauntletComponent;
/*   62:     */ import gui.BlinkingBoxPanel;
/*   63:     */ import gui.BlockViewer;
/*   64:     */ import gui.ComparisonViewer;
/*   65:     */ import gui.EventKnowledgeViewer;
/*   66:     */ import gui.FileReaderPanel;
/*   67:     */ import gui.ForceViewer;
/*   68:     */ import gui.GeometryViewer;
/*   69:     */ import gui.ImagePanel;
/*   70:     */ import gui.JMatrixPanel;
/*   71:     */ import gui.MoodViewer;
/*   72:     */ import gui.MovieViewerExternal;
/*   73:     */ import gui.NameLabel;
/*   74:     */ import gui.NewFrameViewer;
/*   75:     */ import gui.OnsetViewer;
/*   76:     */ import gui.PathElementViewer;
/*   77:     */ import gui.PictureViewer;
/*   78:     */ import gui.PlaceViewer;
/*   79:     */ import gui.ReflectionBar;
/*   80:     */ import gui.RelationViewer;
/*   81:     */ import gui.RoleViewer;
/*   82:     */ import gui.RuleViewer;
/*   83:     */ import gui.StoryRecallViewer;
/*   84:     */ import gui.StoryViewer;
/*   85:     */ import gui.TabbedTextAdapter;
/*   86:     */ import gui.TabbedTextViewer;
/*   87:     */ import gui.TalkBackViewer;
/*   88:     */ import gui.TalkingFrameViewer;
/*   89:     */ import gui.TextBox;
/*   90:     */ import gui.TextViewer;
/*   91:     */ import gui.ThreadViewer;
/*   92:     */ import gui.TimeViewer;
/*   93:     */ import gui.TrafficLight;
/*   94:     */ import gui.TrajectoryViewer;
/*   95:     */ import gui.TransferViewer;
/*   96:     */ import gui.TransitionViewer;
/*   97:     */ import gui.WiredBlinkingBox;
/*   98:     */ import gui.WiredProgressBar;
/*   99:     */ import gui.WiredSplitPane;
/*  100:     */ import gui.panels.MasterPanel;
/*  101:     */ import gui.panels.StandardPanel;
/*  102:     */ import java.awt.BorderLayout;
/*  103:     */ import java.awt.Color;
/*  104:     */ import java.awt.Component;
/*  105:     */ import java.awt.Container;
/*  106:     */ import java.awt.Dimension;
/*  107:     */ import java.awt.Font;
/*  108:     */ import java.awt.Frame;
/*  109:     */ import java.awt.Rectangle;
/*  110:     */ import java.awt.event.ActionEvent;
/*  111:     */ import java.awt.event.ActionListener;
/*  112:     */ import java.beans.PropertyChangeEvent;
/*  113:     */ import java.beans.PropertyChangeListener;
/*  114:     */ import java.io.File;
/*  115:     */ import java.io.IOException;
/*  116:     */ import java.io.PrintStream;
/*  117:     */ import java.net.MalformedURLException;
/*  118:     */ import java.net.URL;
/*  119:     */ import java.util.ArrayList;
/*  120:     */ import java.util.List;
/*  121:     */ import java.util.Vector;
/*  122:     */ import java.util.prefs.Preferences;
/*  123:     */ import javax.swing.BorderFactory;
/*  124:     */ import javax.swing.JButton;
/*  125:     */ import javax.swing.JCheckBox;
/*  126:     */ import javax.swing.JComponent;
/*  127:     */ import javax.swing.JEditorPane;
/*  128:     */ import javax.swing.JFileChooser;
/*  129:     */ import javax.swing.JFrame;
/*  130:     */ import javax.swing.JLabel;
/*  131:     */ import javax.swing.JList;
/*  132:     */ import javax.swing.JMenu;
/*  133:     */ import javax.swing.JMenuBar;
/*  134:     */ import javax.swing.JMenuItem;
/*  135:     */ import javax.swing.JOptionPane;
/*  136:     */ import javax.swing.JPanel;
/*  137:     */ import javax.swing.JRadioButton;
/*  138:     */ import javax.swing.JSplitPane;
/*  139:     */ import javax.swing.JTabbedPane;
/*  140:     */ import javax.swing.Popup;
/*  141:     */ import javax.swing.PopupFactory;
/*  142:     */ import javax.swing.border.TitledBorder;
/*  143:     */ import javax.swing.event.ListSelectionEvent;
/*  144:     */ import javax.swing.event.ListSelectionListener;
/*  145:     */ import kevinWhite.GoalPanel;
/*  146:     */ import kevinWhite.PartPanel;
/*  147:     */ import kevinWhite.PossessionPanel;
/*  148:     */ import links.videos.MovieDescription;
/*  149:     */ import links.videos.MovieManager;
/*  150:     */ import links.words.BundleGenerator;
/*  151:     */ import m2.M2;
/*  152:     */ import m2.Mem;
/*  153:     */ import m2.gui.ChainViewer;
/*  154:     */ import m2.gui.M2Viewer;
/*  155:     */ import m2.gui.PredictionsViewer;
/*  156:     */ import m2.gui.RepViewer;
/*  157:     */ import matthewFay.CharacterModeling.CharacterProcessor;
/*  158:     */ import matthewFay.ClusterProcessor;
/*  159:     */ import matthewFay.Exporter.ExperimentExportProcessor;
/*  160:     */ import matthewFay.StoryAlignment.AlignmentProcessor;
/*  161:     */ import matthewFay.StoryThreading.StoryThreadingProcessor;
/*  162:     */ import matthewFay.StoryThreading.StoryThreadingViewer;
/*  163:     */ import matthewFay.viewers.AlignmentViewer;
/*  164:     */ import matthewFay.viewers.CharacterViewer;
/*  165:     */ import matthewFay.viewers.TraitViewer;
/*  166:     */ import memory.Memory;
/*  167:     */ import memory.utilities.Distances;
/*  168:     */ import models.MentalModel;
/*  169:     */ import obsolete.mindsEye.DisgustingMoebiusTranslator;
/*  170:     */ import obsolete.mindsEye.GrandCentralStation;
/*  171:     */ import obsolete.mindsEye.MindsEyeMoviePlayer;
/*  172:     */ import obsolete.mindsEye.MindsEyeMovieViewer;
/*  173:     */ import parameters.Radio;
/*  174:     */ import parameters.Switch;
/*  175:     */ import persistence.JCheckBoxWithMemory;
/*  176:     */ import portico.Combinator;
/*  177:     */ import portico.IdiomSplitter;
/*  178:     */ import rachelChaney.RachelsPictureExpert;
/*  179:     */ import recall.StoryRecallExpert;
/*  180:     */ import silaSayan.StoryTeller;
/*  181:     */ import silaSayan.SummaryHelper;
/*  182:     */ import silasAast.GoalSpecifier;
/*  183:     */ import silasAast.GoalTracker;
/*  184:     */ import silasAast.InternalNarrator;
/*  185:     */ import silasAast.ModelEvaluator;
/*  186:     */ import silasAast.StaticAudienceModeler;
/*  187:     */ import silasAast.StoryEnvironment;
/*  188:     */ import silasAast.StoryModifier;
/*  189:     */ import silasAast.StoryPreSimulator;
/*  190:     */ import silasAast.StoryPublisher;
/*  191:     */ import silasAast.StorySimulator;
/*  192:     */ import start.Generator;
/*  193:     */ import start.Start;
/*  194:     */ import start.StartPreprocessor;
/*  195:     */ import storyProcessor.BackwardChainer;
/*  196:     */ import storyProcessor.ForwardChainer;
/*  197:     */ import storyProcessor.MindsEyeProcessor;
/*  198:     */ import storyProcessor.ReflectionOnsetDetector;
/*  199:     */ import storyProcessor.StoryProcessor;
/*  200:     */ import storyProcessor.TestStoryOutputBox;
/*  201:     */ import storyProcessor.WorkbenchConnection;
/*  202:     */ import summarizer.Summarizer;
/*  203:     */ import tomLarson.Disambiguator2;
/*  204:     */ import tomLarson.Disambiguator3;
/*  205:     */ import tomLarson.DisambiguatorViewer;
/*  206:     */ import translator.Distributor;
/*  207:     */ import translator.HardWiredTranslator;
/*  208:     */ import translator.Translator;
/*  209:     */ import tts.Talker;
/*  210:     */ import utils.Mark;
/*  211:     */ import utils.Webstart;
/*  212:     */ import windowGroup.WindowGroupHost;
/*  213:     */ import windowGroup.WindowGroupManager;
/*  214:     */ 
/*  215:     */ public class GenesisGetters
/*  216:     */   extends GenesisControls
/*  217:     */   implements WiredBox
/*  218:     */ {
/*  219: 238 */   public static int YELLOW = 1;
/*  220: 238 */   public static int GREEN = 2;
/*  221: 238 */   public static int RED = 0;
/*  222: 244 */   protected JMenu aboutMenu = new JMenu("About");
/*  223: 282 */   protected JMenu fileMenu = new JMenu("File");
/*  224: 334 */   protected JMenuBar menuBar = new JMenuBar();
/*  225:     */   
/*  226:     */   public MindsEyeMoviePlayer getMindsEyeMoviePlayer()
/*  227:     */   {
/*  228: 512 */     if (this.mindsEyeMoviePlayer == null) {
/*  229: 513 */       this.mindsEyeMoviePlayer = new MindsEyeMoviePlayer();
/*  230:     */     }
/*  231: 515 */     return this.mindsEyeMoviePlayer;
/*  232:     */   }
/*  233:     */   
/*  234:     */   public MindsEyeMovieViewer getMindsEyeMovieViewer()
/*  235:     */   {
/*  236: 519 */     return getMindsEyeMoviePlayer().getViewer();
/*  237:     */   }
/*  238:     */   
/*  239:     */   public TabbedTextAdapter getConceptAdapter()
/*  240:     */   {
/*  241: 523 */     if (this.conceptAdapter == null) {
/*  242: 524 */       this.conceptAdapter = new TabbedTextAdapter("Concept description", getConceptContainer());
/*  243:     */     }
/*  244: 526 */     return this.conceptAdapter;
/*  245:     */   }
/*  246:     */   
/*  247:     */   public TabbedTextAdapter getRemarksAdapter()
/*  248:     */   {
/*  249: 530 */     if (this.remarksAdapter == null) {
/*  250: 531 */       this.remarksAdapter = new TabbedTextAdapter("Remarks", getResultContainer());
/*  251:     */     }
/*  252: 533 */     return this.remarksAdapter;
/*  253:     */   }
/*  254:     */   
/*  255:     */   public TabbedTextAdapter getMysteryAdapter()
/*  256:     */   {
/*  257: 537 */     if (this.mysteryAdapter == null) {
/*  258: 538 */       this.mysteryAdapter = new TabbedTextAdapter("Underinterpreted", getScopeContainer());
/*  259:     */     }
/*  260: 540 */     return this.mysteryAdapter;
/*  261:     */   }
/*  262:     */   
/*  263:     */   public TabbedTextAdapter getStartProcessingViewer()
/*  264:     */   {
/*  265: 544 */     if (this.startProcessingViewer == null) {
/*  266: 545 */       this.startProcessingViewer = new TabbedTextAdapter("Start result", getScopeContainer());
/*  267:     */     }
/*  268: 547 */     return this.startProcessingViewer;
/*  269:     */   }
/*  270:     */   
/*  271: 621 */   public Color greenish = new Color(153, 255, 51);
/*  272: 649 */   String luStory = "Try asking:<br/>Did Lu kill (Shan or Goertz or an associate professor or himself) because:<ol><li>Lu is insane<li>America is individualistic<li>American media glorifies violence<li>Goertz fails to help Lu</ol>";
/*  273: 652 */   String mcIlvaneStory = "Try asking:<br/>Did McIlvane kill (supervisor or himself) because:<ol><li>McIlvane is insane<li>America is individualistic<li>American media glorifies violence<li>supervisor fails to help McIlvane</ol>";
/*  274:     */   
/*  275:     */   public TextViewer getCausalTextView()
/*  276:     */   {
/*  277: 657 */     if (this.causalTextView == null)
/*  278:     */     {
/*  279: 658 */       this.causalTextView = new TextViewer();
/*  280: 659 */       this.causalTextView.setName("Causal view");
/*  281:     */     }
/*  282: 661 */     return this.causalTextView;
/*  283:     */   }
/*  284:     */   
/*  285:     */   protected void changeState(Object o)
/*  286:     */   {
/*  287:     */     try
/*  288:     */     {
/*  289: 666 */       if (o == "Open")
/*  290:     */       {
/*  291: 668 */         getTrafficLight().setGreen(true);
/*  292: 669 */         getTextEntryBox().setEnabled(true);
/*  293: 670 */         this.experienceButton.setEnabled(true);
/*  294:     */         
/*  295: 672 */         this.disambiguationButton.setEnabled(true);
/*  296: 673 */         this.wordnetPurgeButton.setEnabled(true);
/*  297: 674 */         this.startPurgeButton.setEnabled(true);
/*  298:     */         
/*  299: 676 */         this.eraseTextButton.setEnabled(true);
/*  300: 677 */         this.testRepresentationsButton.setEnabled(true);
/*  301: 678 */         this.testStoriesButton.setEnabled(true);
/*  302: 679 */         this.rereadFile.setEnabled(true);
/*  303: 680 */         this.rerunExperiment.setEnabled(true);
/*  304: 681 */         this.loopButton.setEnabled(true);
/*  305:     */       }
/*  306: 684 */       else if (o == "Close")
/*  307:     */       {
/*  308: 685 */         getTrafficLight().setRed(true);
/*  309:     */         
/*  310:     */ 
/*  311: 688 */         this.experienceButton.setEnabled(false);
/*  312:     */         
/*  313: 690 */         this.disambiguationButton.setEnabled(false);
/*  314: 691 */         this.wordnetPurgeButton.setEnabled(false);
/*  315: 692 */         this.startPurgeButton.setEnabled(false);
/*  316:     */         
/*  317: 694 */         this.eraseTextButton.setEnabled(false);
/*  318: 695 */         this.testRepresentationsButton.setEnabled(false);
/*  319: 696 */         this.testStoriesButton.setEnabled(false);
/*  320: 697 */         this.rereadFile.setEnabled(false);
/*  321: 698 */         this.rerunExperiment.setEnabled(false);
/*  322: 699 */         this.loopButton.setEnabled(false);
/*  323:     */       }
/*  324:     */     }
/*  325:     */     catch (RuntimeException e)
/*  326:     */     {
/*  327: 704 */       System.err.println("Blew out of opening or closing interface");
/*  328: 705 */       e.printStackTrace();
/*  329:     */     }
/*  330:     */   }
/*  331:     */   
/*  332:     */   protected void closeGates() {}
/*  333:     */   
/*  334:     */   public void closeInterface()
/*  335:     */   {
/*  336: 716 */     changeState("Close");
/*  337:     */   }
/*  338:     */   
/*  339:     */   public GenesisGetters getApplication()
/*  340:     */   {
/*  341: 720 */     return this;
/*  342:     */   }
/*  343:     */   
/*  344:     */   public BackgroundWiredBox getBackgroundMemoryBox()
/*  345:     */   {
/*  346: 728 */     if (this.backgroundMemoryBox == null) {
/*  347: 729 */       this.backgroundMemoryBox = new BackgroundWiredBox();
/*  348:     */     }
/*  349: 731 */     return this.backgroundMemoryBox;
/*  350:     */   }
/*  351:     */   
/*  352:     */   public BensGauntletComponent getBensComponent()
/*  353:     */   {
/*  354: 735 */     if (this.benji == null) {
/*  355: 736 */       this.benji = new BensGauntletComponent();
/*  356:     */     }
/*  357: 738 */     return this.benji;
/*  358:     */   }
/*  359:     */   
/*  360:     */   public BlinkingBoxPanel getExpertsPanel()
/*  361:     */   {
/*  362: 742 */     if (this.blinkingBoxPanel == null)
/*  363:     */     {
/*  364: 743 */       this.blinkingBoxPanel = new BlinkingBoxPanel();
/*  365: 744 */       this.blinkingBoxPanel.setName("Experts");
/*  366:     */     }
/*  367: 747 */     return this.blinkingBoxPanel;
/*  368:     */   }
/*  369:     */   
/*  370:     */   public WiredBlinkingBox getBlockProbeBlinkingBox()
/*  371:     */   {
/*  372: 751 */     if (this.blockProbeBlinkingBox == null)
/*  373:     */     {
/*  374: 752 */       this.blockProbeBlinkingBox = new WiredBlinkingBox();
/*  375: 753 */       this.blockProbeBlinkingBox.setTitle("Blockage");
/*  376:     */       
/*  377: 755 */       this.blockProbeBlinkingBox.setMemory(getRepBlockViewer());
/*  378: 756 */       this.blockProbeBlinkingBox.setGraphic(getBlockViewer());
/*  379: 757 */       this.blockProbeBlinkingBox.setName("Blockage  blinker");
/*  380: 758 */       getExpertsPanel().add(this.blockProbeBlinkingBox);
/*  381:     */     }
/*  382: 760 */     return this.blockProbeBlinkingBox;
/*  383:     */   }
/*  384:     */   
/*  385:     */   public BlockViewer getBlockViewer()
/*  386:     */   {
/*  387: 764 */     if (this.blockViewer == null) {
/*  388: 765 */       this.blockViewer = new BlockViewer();
/*  389:     */     }
/*  390: 767 */     return this.blockViewer;
/*  391:     */   }
/*  392:     */   
/*  393:     */   NewFrameViewer getClosestThingViewer()
/*  394:     */   {
/*  395: 771 */     if (this.closestThingViewer == null)
/*  396:     */     {
/*  397: 772 */       this.closestThingViewer = new NewFrameViewer();
/*  398:     */       
/*  399: 774 */       this.closestThingViewer.setOpaque(false);
/*  400: 775 */       this.closestThingViewer.setName("Distance expert");
/*  401:     */     }
/*  402: 777 */     return this.closestThingViewer;
/*  403:     */   }
/*  404:     */   
/*  405:     */   public ForceInterpreter getCoerceInterpreter()
/*  406:     */   {
/*  407: 781 */     if (this.coerceInterpreter == null)
/*  408:     */     {
/*  409: 782 */       this.coerceInterpreter = new ForceInterpreter();
/*  410: 783 */       this.coerceInterpreter.setName("Coerce intepreter");
/*  411:     */     }
/*  412: 785 */     return this.coerceInterpreter;
/*  413:     */   }
/*  414:     */   
/*  415:     */   public WiredBlinkingBox getControlProbeBlinkingBox()
/*  416:     */   {
/*  417: 847 */     if (this.controlProbeBlinkingBox == null)
/*  418:     */     {
/*  419: 848 */       this.controlProbeBlinkingBox = new WiredBlinkingBox();
/*  420: 849 */       this.controlProbeBlinkingBox.setTitle("Control");
/*  421:     */       
/*  422: 851 */       this.controlProbeBlinkingBox.setName("Control blinker");
/*  423: 852 */       getExpertsPanel().add(this.controlProbeBlinkingBox);
/*  424:     */     }
/*  425: 854 */     return this.trajectoryBlinker;
/*  426:     */   }
/*  427:     */   
/*  428:     */   public UnderstandProcessor getDeriver()
/*  429:     */   {
/*  430: 858 */     if (this.understandProcessor == null)
/*  431:     */     {
/*  432: 859 */       this.understandProcessor = new UnderstandProcessor();
/*  433: 860 */       this.understandProcessor.setName("Deriver");
/*  434:     */     }
/*  435: 862 */     return this.understandProcessor;
/*  436:     */   }
/*  437:     */   
/*  438:     */   public DictionaryPage getDictionary()
/*  439:     */   {
/*  440: 866 */     if (this.dictionary == null)
/*  441:     */     {
/*  442: 867 */       this.dictionary = new DictionaryPage();
/*  443: 868 */       this.dictionary.setName("Dictionary");
/*  444:     */     }
/*  445: 870 */     return this.dictionary;
/*  446:     */   }
/*  447:     */   
/*  448:     */   public Disambiguator2 getDisambiguator()
/*  449:     */   {
/*  450: 874 */     if (this.disambiguator == null) {
/*  451: 875 */       this.disambiguator = new Disambiguator2(Switch.disambiguatorSwitch);
/*  452:     */     }
/*  453: 877 */     return this.disambiguator;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public Disambiguator3 getLinkDisambiguator()
/*  457:     */   {
/*  458: 881 */     if (this.linkDisambiguator == null)
/*  459:     */     {
/*  460: 882 */       this.linkDisambiguator = new Disambiguator3();
/*  461: 883 */       this.linkDisambiguator.setName("Link disambiguator");
/*  462:     */     }
/*  463: 885 */     return this.linkDisambiguator;
/*  464:     */   }
/*  465:     */   
/*  466:     */   public Disambiguator3 getStartDisambiguator()
/*  467:     */   {
/*  468: 889 */     if (this.startDisambiguator == null)
/*  469:     */     {
/*  470: 890 */       this.startDisambiguator = new Disambiguator3(Switch.disambiguatorSwitch);
/*  471: 891 */       this.startDisambiguator.setName("Start disambiguator");
/*  472:     */     }
/*  473: 893 */     return this.startDisambiguator;
/*  474:     */   }
/*  475:     */   
/*  476:     */   public Disambiguator getNewDisambiguator()
/*  477:     */   {
/*  478: 897 */     if (this.newDisambiguator == null) {
/*  479: 898 */       this.newDisambiguator = new Disambiguator();
/*  480:     */     }
/*  481: 900 */     return this.newDisambiguator;
/*  482:     */   }
/*  483:     */   
/*  484:     */   DisambiguatorViewer getDisambiguatorViewer()
/*  485:     */   {
/*  486: 904 */     if (this.disambiguatorViewer == null)
/*  487:     */     {
/*  488: 905 */       this.disambiguatorViewer = new DisambiguatorViewer();
/*  489:     */       
/*  490: 907 */       this.disambiguatorViewer.setOpaque(false);
/*  491:     */     }
/*  492: 909 */     return this.disambiguatorViewer;
/*  493:     */   }
/*  494:     */   
/*  495:     */   public Distributor getDistributionBox()
/*  496:     */   {
/*  497: 913 */     if (this.expertDistributionBox == null)
/*  498:     */     {
/*  499: 914 */       this.expertDistributionBox = new Distributor();
/*  500: 915 */       this.expertDistributionBox.setName("Distributor");
/*  501:     */     }
/*  502: 917 */     return this.expertDistributionBox;
/*  503:     */   }
/*  504:     */   
/*  505:     */   public FileReaderPanel getFileReaderFrame()
/*  506:     */   {
/*  507: 921 */     if (this.fileReaderFrame == null) {
/*  508: 922 */       this.fileReaderFrame = new FileReaderPanel();
/*  509:     */     }
/*  510: 924 */     return this.fileReaderFrame;
/*  511:     */   }
/*  512:     */   
/*  513:     */   public ForceInterpreter getCauseInterpreter()
/*  514:     */   {
/*  515: 928 */     if (this.causeInterpreter == null)
/*  516:     */     {
/*  517: 929 */       this.causeInterpreter = new ForceInterpreter();
/*  518: 930 */       this.causeInterpreter.setName("Cause intepreter");
/*  519:     */     }
/*  520: 932 */     return this.causeInterpreter;
/*  521:     */   }
/*  522:     */   
/*  523:     */   public WiredBlinkingBox getGeometryProbeBlinkingBox(GeometryViewer viewer)
/*  524:     */   {
/*  525: 956 */     if (this.geometryProbeBlinkingBox == null)
/*  526:     */     {
/*  527: 957 */       this.geometryProbeBlinkingBox = new WiredBlinkingBox(viewer);
/*  528: 958 */       this.geometryProbeBlinkingBox.setTitle("Geometry");
/*  529:     */       
/*  530: 960 */       this.geometryProbeBlinkingBox.setMemory(getRepGeometryViewer());
/*  531: 961 */       this.geometryProbeBlinkingBox.setGraphic(getGeometryViewer());
/*  532: 962 */       this.geometryProbeBlinkingBox.setName("Geometry blinker");
/*  533: 963 */       getExpertsPanel().add(this.geometryProbeBlinkingBox);
/*  534:     */     }
/*  535: 965 */     return this.geometryProbeBlinkingBox;
/*  536:     */   }
/*  537:     */   
/*  538:     */   public GeometryViewer getGeometryViewer()
/*  539:     */   {
/*  540: 969 */     if (this.geometryViewer == null) {
/*  541: 970 */       this.geometryViewer = new GeometryViewer();
/*  542:     */     }
/*  543: 972 */     return this.geometryViewer;
/*  544:     */   }
/*  545:     */   
/*  546:     */   public HardWiredTranslator getHardWiredTranslator()
/*  547:     */   {
/*  548: 976 */     if (this.hardWiredTranslator == null)
/*  549:     */     {
/*  550: 977 */       this.hardWiredTranslator = new HardWiredTranslator(this);
/*  551: 978 */       this.hardWiredTranslator.setName("Static translator");
/*  552:     */     }
/*  553: 980 */     return this.hardWiredTranslator;
/*  554:     */   }
/*  555:     */   
/*  556:     */   public Translator getNewSemanticTranslator()
/*  557:     */   {
/*  558: 984 */     if (this.translator == null)
/*  559:     */     {
/*  560: 985 */       this.translator = new Translator(this);
/*  561: 986 */       this.translator.setName("Semantic translator");
/*  562:     */     }
/*  563: 988 */     return this.translator;
/*  564:     */   }
/*  565:     */   
/*  566:     */   public WiredBlinkingBox getImagineBlinker()
/*  567:     */   {
/*  568: 992 */     if (this.imagineBlinker == null) {
/*  569: 993 */       this.imagineBlinker = new WiredBlinkingBox("Imagine", getImagineExpert(), new ThreadViewer(), getExpertsPanel());
/*  570:     */     }
/*  571: 995 */     return this.imagineBlinker;
/*  572:     */   }
/*  573:     */   
/*  574:     */   private JComponent getWiringDiagram()
/*  575:     */   {
/*  576: 999 */     if (this.wiringDiagram == null)
/*  577:     */     {
/*  578:1000 */       this.wiringDiagram = new JPanel();
/*  579:1001 */       this.wiringDiagram.setLayout(new BorderLayout());
/*  580:1002 */       this.wiringDiagram.setName("Wiring diagram");
/*  581:1003 */       Adapter adapter = Adapter.makeConnectionAdapter();
/*  582:     */       
/*  583:     */ 
/*  584:1006 */       this.wiringDiagram.add(adapter.getViewer(), "Center");
/*  585:1007 */       this.wiringDiagram.add(adapter.getViewer().getSlider(), "North");
/*  586:     */     }
/*  587:1009 */     return this.wiringDiagram;
/*  588:     */   }
/*  589:     */   
/*  590:     */   public WiredSplitPane getElaborationPanel()
/*  591:     */   {
/*  592:1013 */     if (this.elaborationPanel == null)
/*  593:     */     {
/*  594:1014 */       this.elaborationPanel = new WiredSplitPane(getMentalModel1().getPlotDiagram(), getMentalModel2().getPlotDiagram());
/*  595:1015 */       this.elaborationPanel.setName("Elaboration graph");
/*  596:     */     }
/*  597:1017 */     return this.elaborationPanel;
/*  598:     */   }
/*  599:     */   
/*  600:     */   public WiredSplitPane getInspectorPanel()
/*  601:     */   {
/*  602:1021 */     if (this.inspectorPanel == null)
/*  603:     */     {
/*  604:1022 */       this.inspectorPanel = new WiredSplitPane(getMentalModel1().getInspectionView(), getMentalModel2().getInspectionView());
/*  605:1023 */       this.inspectorPanel.setName("Inspector");
/*  606:     */     }
/*  607:1025 */     return this.inspectorPanel;
/*  608:     */   }
/*  609:     */   
/*  610:     */   public WiredSplitPane getConceptsViewerPanel()
/*  611:     */   {
/*  612:1029 */     if (this.reflectionOnsetPanel == null)
/*  613:     */     {
/*  614:1030 */       this.reflectionOnsetPanel = new WiredSplitPane(getConceptViewer1(), getConceptViewer2());
/*  615:1031 */       this.reflectionOnsetPanel.setName("Concepts");
/*  616:     */     }
/*  617:1033 */     return this.reflectionOnsetPanel;
/*  618:     */   }
/*  619:     */   
/*  620:     */   public WiredSplitPane getOnsetPanel()
/*  621:     */   {
/*  622:1037 */     if (this.onsetPanel == null)
/*  623:     */     {
/*  624:1038 */       this.onsetPanel = new WiredSplitPane(getOnsetViewer1(), getOnsetViewer2());
/*  625:1039 */       this.onsetPanel.setName("Onsets");
/*  626:     */     }
/*  627:1041 */     return this.onsetPanel;
/*  628:     */   }
/*  629:     */   
/*  630:     */   public WiredSplitPane getRecallPanel()
/*  631:     */   {
/*  632:1045 */     if (this.recallPanel == null)
/*  633:     */     {
/*  634:1046 */       this.recallPanel = new WiredSplitPane(getStoryRecallViewer1(), getStoryRecallViewer2());
/*  635:1047 */       this.recallPanel.setName("Precedent recall");
/*  636:     */     }
/*  637:1049 */     return this.recallPanel;
/*  638:     */   }
/*  639:     */   
/*  640:     */   protected NameLabel getStoryName2()
/*  641:     */   {
/*  642:1053 */     if (this.storyNameLabel2 == null) {
/*  643:1054 */       this.storyNameLabel2 = new NameLabel(new String[] { "h1", "center" });
/*  644:     */     }
/*  645:1056 */     return this.storyNameLabel2;
/*  646:     */   }
/*  647:     */   
/*  648:     */   public ReflectionBar getInstRulePlotUnitBar1()
/*  649:     */   {
/*  650:1060 */     if (this.instRulePlotUnitBar1 == null)
/*  651:     */     {
/*  652:1061 */       this.instRulePlotUnitBar1 = new ReflectionBar();
/*  653:1062 */       this.instRulePlotUnitBar1.setName("Concepts");
/*  654:1063 */       this.instRulePlotUnitBar1.setOpaque(true);
/*  655:1064 */       this.instRulePlotUnitBar1.setPreferredSize(new Dimension(500, 20));
/*  656:     */     }
/*  657:1066 */     return this.instRulePlotUnitBar1;
/*  658:     */   }
/*  659:     */   
/*  660:     */   public ReflectionBar getInstRulePlotUnitBar2()
/*  661:     */   {
/*  662:1070 */     if (this.instRulePlotUnitBar2 == null)
/*  663:     */     {
/*  664:1071 */       this.instRulePlotUnitBar2 = new ReflectionBar();
/*  665:1072 */       this.instRulePlotUnitBar2.setName("Concepts");
/*  666:1073 */       this.instRulePlotUnitBar2.setOpaque(true);
/*  667:1074 */       this.instRulePlotUnitBar2.setPreferredSize(new Dimension(500, 20));
/*  668:     */     }
/*  669:1076 */     return this.instRulePlotUnitBar2;
/*  670:     */   }
/*  671:     */   
/*  672:     */   public ReflectionBar getRulePlotUnitBar1()
/*  673:     */   {
/*  674:1080 */     if (this.rulePlotUnitBar1 == null)
/*  675:     */     {
/*  676:1081 */       this.rulePlotUnitBar1 = new ReflectionBar();
/*  677:1082 */       this.rulePlotUnitBar1.setName("Concepts");
/*  678:1083 */       this.rulePlotUnitBar1.setOpaque(true);
/*  679:1084 */       this.rulePlotUnitBar1.setPreferredSize(new Dimension(500, 20));
/*  680:     */     }
/*  681:1086 */     return this.rulePlotUnitBar1;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public ReflectionBar getRulePlotUnitBar2()
/*  685:     */   {
/*  686:1090 */     if (this.rulePlotUnitBar2 == null)
/*  687:     */     {
/*  688:1091 */       this.rulePlotUnitBar2 = new ReflectionBar();
/*  689:1092 */       this.rulePlotUnitBar2.setName("Concepts");
/*  690:1093 */       this.rulePlotUnitBar2.setOpaque(true);
/*  691:1094 */       this.rulePlotUnitBar2.setPreferredSize(new Dimension(500, 20));
/*  692:     */     }
/*  693:1096 */     return this.rulePlotUnitBar2;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public SimpleGenerator getInternalToEnglishTranslator()
/*  697:     */   {
/*  698:1100 */     if (this.internalToEnglishTranslator == null)
/*  699:     */     {
/*  700:1101 */       this.internalToEnglishTranslator = new SimpleGenerator();
/*  701:1102 */       this.internalToEnglishTranslator.setName("Internal generator");
/*  702:     */     }
/*  703:1104 */     return this.internalToEnglishTranslator;
/*  704:     */   }
/*  705:     */   
/*  706:     */   public EventKnowledgeViewer getKnowledgeWatcher()
/*  707:     */   {
/*  708:1108 */     if (this.knowledgeWatcher == null)
/*  709:     */     {
/*  710:1109 */       this.knowledgeWatcher = new EventKnowledgeViewer();
/*  711:1110 */       this.knowledgeWatcher.setName("Event viewer");
/*  712:     */     }
/*  713:1112 */     return this.knowledgeWatcher;
/*  714:     */   }
/*  715:     */   
/*  716:     */   public WiredBlinkingBox getKnowledgeWatcherBlinker()
/*  717:     */   {
/*  718:1116 */     if (this.knowledgeWatcherBlinker == null)
/*  719:     */     {
/*  720:1117 */       this.knowledgeWatcherBlinker = new WiredBlinkingBox(getKnowledgeWatcher());
/*  721:1118 */       this.knowledgeWatcherBlinker.setGraphic(getKnowledgeWatcher());
/*  722:1119 */       this.knowledgeWatcherBlinker.setName("Knowledge blinker");
/*  723:     */     }
/*  724:1121 */     return this.knowledgeWatcherBlinker;
/*  725:     */   }
/*  726:     */   
/*  727:     */   public Start getStartParser()
/*  728:     */   {
/*  729:1144 */     if (this.startParser == null)
/*  730:     */     {
/*  731:1145 */       this.startParser = Start.getStart();
/*  732:1146 */       this.startParser.setName("Start parser");
/*  733:     */     }
/*  734:1148 */     return this.startParser;
/*  735:     */   }
/*  736:     */   
/*  737:     */   NewFrameViewer getLinkViewer()
/*  738:     */   {
/*  739:1152 */     if (this.linkViewer == null)
/*  740:     */     {
/*  741:1153 */       this.linkViewer = new NewFrameViewer();
/*  742:1154 */       this.linkViewer.setOpaque(false);
/*  743:1155 */       this.linkViewer.setName("Link viewer");
/*  744:     */     }
/*  745:1157 */     return this.linkViewer;
/*  746:     */   }
/*  747:     */   
/*  748:     */   public StoryViewer getStartViewer()
/*  749:     */   {
/*  750:1161 */     if (this.startViewer == null)
/*  751:     */     {
/*  752:1162 */       this.startViewer = new StoryViewer(this);
/*  753:1163 */       this.startViewer.setOpaque(false);
/*  754:1164 */       this.startViewer.setName("Start viewer");
/*  755:     */     }
/*  756:1166 */     return this.startViewer;
/*  757:     */   }
/*  758:     */   
/*  759:     */   public Memory getMemory()
/*  760:     */   {
/*  761:1170 */     return Memory.getMemory();
/*  762:     */   }
/*  763:     */   
/*  764:     */   public Mem getM2()
/*  765:     */   {
/*  766:1174 */     return M2.getMem();
/*  767:     */   }
/*  768:     */   
/*  769:     */   public M2Viewer getM2Viewer()
/*  770:     */   {
/*  771:1178 */     if (this.m2Viewer == null)
/*  772:     */     {
/*  773:1179 */       this.m2Viewer = new M2Viewer();
/*  774:1180 */       this.m2Viewer.setOpaque(false);
/*  775:1181 */       this.m2Viewer.setName("New memory viewer");
/*  776:     */     }
/*  777:1183 */     return this.m2Viewer;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public ChainViewer getChainViewer()
/*  781:     */   {
/*  782:1187 */     if (this.chainViewer == null)
/*  783:     */     {
/*  784:1188 */       this.chainViewer = new ChainViewer();
/*  785:1189 */       this.chainViewer.setOpaque(false);
/*  786:1190 */       this.chainViewer.setName("Chain viewer");
/*  787:     */     }
/*  788:1192 */     return this.chainViewer;
/*  789:     */   }
/*  790:     */   
/*  791:     */   public PredictionsViewer getPredictionsViewer()
/*  792:     */   {
/*  793:1196 */     if (this.predViewer == null)
/*  794:     */     {
/*  795:1197 */       this.predViewer = new PredictionsViewer();
/*  796:1198 */       this.predViewer.setOpaque(false);
/*  797:1199 */       this.predViewer.setName("Prediction viewer");
/*  798:     */     }
/*  799:1201 */     return this.predViewer;
/*  800:     */   }
/*  801:     */   
/*  802:     */   public ArrayList<MovieDescription> getMovieDescriptions()
/*  803:     */   {
/*  804:1205 */     return getMovieManager().getMovieDescriptions();
/*  805:     */   }
/*  806:     */   
/*  807:     */   public MovieManager getMovieManager()
/*  808:     */   {
/*  809:1209 */     if (this.movieManager == null) {
/*  810:1210 */       this.movieManager = new MovieManager(this);
/*  811:     */     }
/*  812:1212 */     return this.movieManager;
/*  813:     */   }
/*  814:     */   
/*  815:     */   public MovieViewerExternal getExternalMovieViewer()
/*  816:     */   {
/*  817:1216 */     if (this.externalMovieViewer == null) {
/*  818:1217 */       this.externalMovieViewer = new MovieViewerExternal();
/*  819:     */     }
/*  820:1219 */     return this.externalMovieViewer;
/*  821:     */   }
/*  822:     */   
/*  823:     */   public ImagePanel getLightBulbViewer()
/*  824:     */   {
/*  825:1223 */     if (this.lightBulbViewer == null)
/*  826:     */     {
/*  827:1224 */       this.lightBulbViewer = new ImagePanel();
/*  828:1225 */       this.lightBulbViewer.setPreferredSize(new Dimension(300, 300));
/*  829:1226 */       this.lightBulbViewer.setName("Movie viewer");
/*  830:     */     }
/*  831:1228 */     return this.lightBulbViewer;
/*  832:     */   }
/*  833:     */   
/*  834:     */   public JSplitPane getSplitPane()
/*  835:     */   {
/*  836:1232 */     if (this.splitPane == null)
/*  837:     */     {
/*  838:1233 */       this.splitPane = new JSplitPane();
/*  839:1234 */       this.splitPane.setLeftComponent(getLeftPanel());
/*  840:1235 */       this.splitPane.setRightComponent(getRightPanel());
/*  841:1236 */       this.splitPane.setOneTouchExpandable(true);
/*  842:1237 */       this.splitPane.setOpaque(false);
/*  843:1238 */       this.splitPane.setResizeWeight(0.5D);
/*  844:     */       
/*  845:1240 */       this.splitPane.addPropertyChangeListener(new MyEastWestListener(null));
/*  846:     */       
/*  847:1242 */       this.splitPane.getRightComponent().setMinimumSize(new Dimension());
/*  848:     */       
/*  849:1244 */       this.splitPane.getLeftComponent().setMinimumSize(new Dimension());
/*  850:     */       
/*  851:1246 */       int dividerLocation = Preferences.userRoot().getInt("eastWestDivider", 200);
/*  852:1248 */       if (dividerLocation > 100) {
/*  853:1249 */         dividerLocation += 50;
/*  854:     */       }
/*  855:1254 */       this.splitPane.setDividerLocation(dividerLocation);
/*  856:     */     }
/*  857:1256 */     return this.splitPane;
/*  858:     */   }
/*  859:     */   
/*  860:     */   public JSplitPane getNorthSouthSplitPane()
/*  861:     */   {
/*  862:1260 */     if (this.northSouthSplitPane == null)
/*  863:     */     {
/*  864:1261 */       this.northSouthSplitPane = new JSplitPane(0);
/*  865:1262 */       this.northSouthSplitPane.setDividerSize(20);
/*  866:     */       
/*  867:1264 */       this.northSouthSplitPane.setOneTouchExpandable(true);
/*  868:     */       
/*  869:1266 */       this.northSouthSplitPane.setTopComponent(getTopPanel());
/*  870:1267 */       this.northSouthSplitPane.setBottomComponent(getBottomPanel());
/*  871:     */       
/*  872:1269 */       Dimension minimumSize = new Dimension(100, 0);
/*  873:1270 */       getTopPanel().setMinimumSize(minimumSize);
/*  874:1271 */       getBottomPanel().setMinimumSize(minimumSize);
/*  875:     */       
/*  876:1273 */       Dimension preferredSize = new Dimension(1000, 0);
/*  877:     */       
/*  878:1275 */       getTopPanel().setPreferredSize(preferredSize);
/*  879:1276 */       getBottomPanel().setPreferredSize(preferredSize);
/*  880:     */       
/*  881:     */ 
/*  882:1279 */       this.northSouthSplitPane.setResizeWeight(0.5D);
/*  883:     */       
/*  884:1281 */       this.northSouthSplitPane.addPropertyChangeListener(new MyNorthSouthListener(null));
/*  885:     */       
/*  886:1283 */       this.northSouthSplitPane.getBottomComponent().setMinimumSize(new Dimension());
/*  887:     */       
/*  888:1285 */       int dividerLocation = Preferences.userRoot().getInt("northSouthDivider", 200);
/*  889:1287 */       if (dividerLocation > 100) {
/*  890:1288 */         dividerLocation += 50;
/*  891:     */       }
/*  892:1291 */       this.northSouthSplitPane.setDividerLocation(dividerLocation);
/*  893:     */     }
/*  894:1294 */     return this.northSouthSplitPane;
/*  895:     */   }
/*  896:     */   
/*  897:     */   private class MyEastWestListener
/*  898:     */     implements PropertyChangeListener
/*  899:     */   {
/*  900:     */     private MyEastWestListener() {}
/*  901:     */     
/*  902:     */     public void propertyChange(PropertyChangeEvent evt)
/*  903:     */     {
/*  904:1301 */       if ("dividerLocation".equals(evt.getPropertyName()))
/*  905:     */       {
/*  906:1302 */         int newValue = ((Integer)evt.getNewValue()).intValue();
/*  907:     */         
/*  908:1304 */         Preferences.userRoot().putInt("eastWestDivider", newValue);
/*  909:     */       }
/*  910:     */     }
/*  911:     */   }
/*  912:     */   
/*  913:     */   private class MyNorthSouthListener
/*  914:     */     implements PropertyChangeListener
/*  915:     */   {
/*  916:     */     private MyNorthSouthListener() {}
/*  917:     */     
/*  918:     */     public void propertyChange(PropertyChangeEvent evt)
/*  919:     */     {
/*  920:1314 */       if ("dividerLocation".equals(evt.getPropertyName()))
/*  921:     */       {
/*  922:1315 */         int newValue = ((Integer)evt.getNewValue()).intValue();
/*  923:1316 */         Preferences.userRoot().putInt("northSouthDivider", newValue);
/*  924:     */       }
/*  925:     */     }
/*  926:     */   }
/*  927:     */   
/*  928:     */   public WindowGroupManager getWindowGroupManager()
/*  929:     */   {
/*  930:1323 */     if (this.windowGroupManager == null)
/*  931:     */     {
/*  932:1324 */       this.windowGroupManager = new WindowGroupManager(9);
/*  933:     */       
/*  934:1326 */       this.windowGroupManager.addJComponent(getControls());
/*  935:1327 */       this.windowGroupManager.addJComponent(getStartViewer());
/*  936:1328 */       this.windowGroupManager.addJComponent(getExpertsPanel());
/*  937:1329 */       this.windowGroupManager.addJComponent(getElaborationPanel());
/*  938:1330 */       this.windowGroupManager.addJComponent(getInspectorPanel());
/*  939:1331 */       this.windowGroupManager.addJComponent(getSourceContainer());
/*  940:1332 */       this.windowGroupManager.addJComponent(getResultContainer());
/*  941:1333 */       this.windowGroupManager.addJComponent(getSummaryContainer());
/*  942:1334 */       this.windowGroupManager.addJComponent(getStoryContainer());
/*  943:1335 */       this.windowGroupManager.addJComponent(getSimilarityViewer());
/*  944:1336 */       this.windowGroupManager.addJComponent(getDictionary());
/*  945:1337 */       this.windowGroupManager.addJComponent(getWiringDiagram());
/*  946:     */       
/*  947:1339 */       this.windowGroupManager.addJComponent(getStoryViewer1());
/*  948:1340 */       this.windowGroupManager.addJComponent(getStoryViewer2());
/*  949:1341 */       this.windowGroupManager.addJComponent(getOnsetPanel());
/*  950:1342 */       this.windowGroupManager.addJComponent(getRecallPanel());
/*  951:     */       
/*  952:1344 */       this.windowGroupManager.addJComponent(getRuleViewerPanel());
/*  953:1345 */       this.windowGroupManager.addJComponent(getInstantiatedRuleViewerPanel());
/*  954:     */       
/*  955:1347 */       this.windowGroupManager.addJComponent(getConceptsViewerPanel());
/*  956:1348 */       this.windowGroupManager.addJComponent(getInstantiatedConceptViewerPanel());
/*  957:     */       
/*  958:     */ 
/*  959:     */ 
/*  960:     */ 
/*  961:1353 */       this.windowGroupManager.addJComponent(getKnowledgeWatcherBlinker());
/*  962:     */       
/*  963:1355 */       this.windowGroupManager.addJComponent(getPredictionsViewer());
/*  964:     */       
/*  965:     */ 
/*  966:     */ 
/*  967:     */ 
/*  968:     */ 
/*  969:     */ 
/*  970:     */ 
/*  971:     */ 
/*  972:     */ 
/*  973:1365 */       this.windowGroupManager.addJComponent(getTalkBackViewer());
/*  974:     */       
/*  975:     */ 
/*  976:     */ 
/*  977:     */ 
/*  978:     */ 
/*  979:1371 */       this.windowGroupManager.addJComponent(getMentalModelViewer());
/*  980:     */       
/*  981:     */ 
/*  982:1374 */       this.windowGroupManager.addJComponent(getCausalTextView());
/*  983:     */       
/*  984:1376 */       this.windowGroupManager.addJComponent(ExperimentExportProcessor.getExperimentExportProcessor());
/*  985:     */     }
/*  986:1378 */     return this.windowGroupManager;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public WindowGroupHost getLeftPanel()
/*  990:     */   {
/*  991:1382 */     if (this.leftPanel == null)
/*  992:     */     {
/*  993:1383 */       String panelContent = Preferences.userRoot().get("leftPanel", "Controls");
/*  994:1384 */       this.leftPanel = getWindowGroupManager().getHost(panelContent);
/*  995:1385 */       this.leftPanel.setName("leftPanel");
/*  996:     */     }
/*  997:1387 */     return this.leftPanel;
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public WindowGroupHost getRightPanel()
/* 1001:     */   {
/* 1002:1391 */     if (this.rightPanel == null)
/* 1003:     */     {
/* 1004:1392 */       String panelContent = Preferences.userRoot().get("rightPanel", "Elaboration graph");
/* 1005:1393 */       this.rightPanel = getWindowGroupManager().getHost(panelContent);
/* 1006:1394 */       this.rightPanel.setName("rightPanel");
/* 1007:     */     }
/* 1008:1396 */     return this.rightPanel;
/* 1009:     */   }
/* 1010:     */   
/* 1011:     */   public WindowGroupHost getBottomPanel()
/* 1012:     */   {
/* 1013:1400 */     if (this.bottomPanel == null)
/* 1014:     */     {
/* 1015:1401 */       String panelContent = Preferences.userRoot().get("bottomPanel", "Experts");
/* 1016:1402 */       this.bottomPanel = getWindowGroupManager().getHost(panelContent);
/* 1017:1403 */       this.bottomPanel.setName("bottomPanel");
/* 1018:     */     }
/* 1019:1406 */     return this.bottomPanel;
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   public Talker getTalker()
/* 1023:     */   {
/* 1024:1410 */     if (this.talker == null) {
/* 1025:     */       try
/* 1026:     */       {
/* 1027:1412 */         this.talker = new Talker(Switch.useSpeechCheckBox);
/* 1028:1413 */         this.talker.setName("Talker");
/* 1029:     */       }
/* 1030:     */       catch (Exception e)
/* 1031:     */       {
/* 1032:1417 */         System.out.println("Failed to construct talker");
/* 1033:1418 */         e.printStackTrace();
/* 1034:     */       }
/* 1035:     */     }
/* 1036:1421 */     return this.talker;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public DistributionBox getParserDistributionBox()
/* 1040:     */   {
/* 1041:1425 */     if (this.parserDistributionBox == null)
/* 1042:     */     {
/* 1043:1426 */       this.parserDistributionBox = new DistributionBox(this);
/* 1044:1427 */       this.parserDistributionBox.setName("Parser distributor");
/* 1045:     */     }
/* 1046:1429 */     return this.parserDistributionBox;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public WiredBlinkingBox getPathProbeBlinkingBox()
/* 1050:     */   {
/* 1051:1442 */     if (this.pathProbeBlinkingBox == null)
/* 1052:     */     {
/* 1053:1443 */       this.pathProbeBlinkingBox = new WiredBlinkingBox(getPathViewer());
/* 1054:1444 */       this.pathProbeBlinkingBox.setTitle("Path");
/* 1055:     */       
/* 1056:1446 */       this.pathProbeBlinkingBox.setMemory(getRepPathViewer());
/* 1057:1447 */       this.pathProbeBlinkingBox.setGraphic(getPathViewer());
/* 1058:1448 */       this.pathProbeBlinkingBox.setName("Path blinker");
/* 1059:1449 */       getExpertsPanel().add(this.pathProbeBlinkingBox);
/* 1060:     */     }
/* 1061:1451 */     return this.pathProbeBlinkingBox;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public NewFrameViewer getPathViewer()
/* 1065:     */   {
/* 1066:1455 */     if (this.pathViewer == null)
/* 1067:     */     {
/* 1068:1456 */       this.pathViewer = new NewFrameViewer();
/* 1069:1457 */       this.pathViewer.setName("Path viewer");
/* 1070:     */     }
/* 1071:1460 */     return this.pathViewer;
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public PictureFinder getPictureFinder()
/* 1075:     */   {
/* 1076:1464 */     if (this.pictureFinder == null)
/* 1077:     */     {
/* 1078:1465 */       this.pictureFinder = new PictureFinder();
/* 1079:1466 */       this.pictureFinder.setName("Picture finder");
/* 1080:     */     }
/* 1081:1468 */     return this.pictureFinder;
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public PictureViewer getPictureViewer()
/* 1085:     */   {
/* 1086:1490 */     if (this.pictureViewer == null)
/* 1087:     */     {
/* 1088:1491 */       this.pictureViewer = new PictureViewer();
/* 1089:1492 */       this.pictureViewer.setName("Picture viewer");
/* 1090:1493 */       this.pictureViewer.setPreferredSize(new Dimension(200, 400));
/* 1091:     */       
/* 1092:1495 */       TitledBorder border = BorderFactory.createTitledBorder("Image");
/* 1093:     */       
/* 1094:     */ 
/* 1095:     */ 
/* 1096:1499 */       Font newFont = new Font("Serif", 0, 10);
/* 1097:1500 */       border.setTitleFont(newFont);
/* 1098:1501 */       this.pictureViewer.setBorder(border);
/* 1099:1502 */       this.pictureViewer.setYOffset(25);
/* 1100:1503 */       getExpertsPanel().add(this.pictureViewer);
/* 1101:     */     }
/* 1102:1505 */     return this.pictureViewer;
/* 1103:     */   }
/* 1104:     */   
/* 1105:     */   public TalkingFrameViewer getPredictionViewer()
/* 1106:     */   {
/* 1107:1509 */     if (this.predictionViewer == null)
/* 1108:     */     {
/* 1109:1510 */       this.predictionViewer = new TalkingFrameViewer(this);
/* 1110:1511 */       this.predictionViewer.setOpaque(false);
/* 1111:1512 */       this.predictionViewer.setName("Prediction talker");
/* 1112:     */     }
/* 1113:1514 */     return this.predictionViewer;
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public QueuingWiredBox getQueuingPictureBox()
/* 1117:     */   {
/* 1118:1518 */     if (this.queuingPictureBox == null)
/* 1119:     */     {
/* 1120:1519 */       this.queuingPictureBox = new QueuingWiredBox(500, 50);
/* 1121:1520 */       this.queuingPictureBox.setName("Picture queue");
/* 1122:     */     }
/* 1123:1522 */     return this.queuingPictureBox;
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public RachelsPictureExpert getRachelsPictureExpert()
/* 1127:     */   {
/* 1128:1526 */     if (this.rachelsPictureExpert == null)
/* 1129:     */     {
/* 1130:1527 */       this.rachelsPictureExpert = new RachelsPictureExpert();
/* 1131:1528 */       this.rachelsPictureExpert.setName("Picture Expert");
/* 1132:     */     }
/* 1133:1530 */     return this.rachelsPictureExpert;
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public SimpleGenerator getSimpleGenerator()
/* 1137:     */   {
/* 1138:1534 */     if (this.simpleGenerator == null)
/* 1139:     */     {
/* 1140:1535 */       this.simpleGenerator = new SimpleGenerator();
/* 1141:1536 */       this.simpleGenerator.setName("Generator");
/* 1142:     */     }
/* 1143:1538 */     return this.simpleGenerator;
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public SimpleGenerator getDescriptionGenerator()
/* 1147:     */   {
/* 1148:1542 */     if (this.descriptionGenerator == null) {
/* 1149:1543 */       this.descriptionGenerator = new SimpleGenerator();
/* 1150:     */     }
/* 1151:1545 */     return this.descriptionGenerator;
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public SomTrajectoryBox getSomTestor()
/* 1155:     */   {
/* 1156:1612 */     if (this.somTester == null)
/* 1157:     */     {
/* 1158:1613 */       this.somTester = new SomTrajectoryBox();
/* 1159:1614 */       this.somTester.setName("Cause memory");
/* 1160:     */     }
/* 1161:1616 */     return this.somTester;
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public RepViewer getRepBlockViewer()
/* 1165:     */   {
/* 1166:1650 */     if (this.repBlockViewer == null)
/* 1167:     */     {
/* 1168:1651 */       this.repBlockViewer = new RepViewer();
/* 1169:1652 */       this.repBlockViewer.setName("Blocker memory");
/* 1170:     */     }
/* 1171:1654 */     return this.repBlockViewer;
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public RepViewer getRepCauseViewer()
/* 1175:     */   {
/* 1176:1658 */     if (this.repCauseViewer == null)
/* 1177:     */     {
/* 1178:1659 */       this.repCauseViewer = new RepViewer();
/* 1179:1660 */       this.repCauseViewer.setName("Cause memory");
/* 1180:     */     }
/* 1181:1662 */     return this.repCauseViewer;
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   public RepViewer getRepCoerceViewer()
/* 1185:     */   {
/* 1186:1666 */     if (this.repCoerceViewer == null)
/* 1187:     */     {
/* 1188:1667 */       this.repCoerceViewer = new RepViewer();
/* 1189:1668 */       this.repCoerceViewer.setName("Coerce memory");
/* 1190:     */     }
/* 1191:1670 */     return this.repCoerceViewer;
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   public RepViewer getRepForceViewer()
/* 1195:     */   {
/* 1196:1674 */     if (this.repForceViewer == null)
/* 1197:     */     {
/* 1198:1675 */       this.repForceViewer = new RepViewer();
/* 1199:1676 */       this.repForceViewer.setName("Force memory");
/* 1200:     */     }
/* 1201:1678 */     return this.repForceViewer;
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   public RepViewer getRepGeometryViewer()
/* 1205:     */   {
/* 1206:1682 */     if (this.repGeometryViewer == null)
/* 1207:     */     {
/* 1208:1683 */       this.repGeometryViewer = new RepViewer();
/* 1209:1684 */       this.repGeometryViewer.setName("Geometry viewer");
/* 1210:     */     }
/* 1211:1686 */     return this.repGeometryViewer;
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public RepViewer getRepPathElementViewer()
/* 1215:     */   {
/* 1216:1690 */     if (this.repPathElementViewer == null)
/* 1217:     */     {
/* 1218:1691 */       this.repPathElementViewer = new RepViewer();
/* 1219:1692 */       this.repPathElementViewer.setName("Path element memory");
/* 1220:     */     }
/* 1221:1694 */     return this.repPathElementViewer;
/* 1222:     */   }
/* 1223:     */   
/* 1224:     */   public RepViewer getRepPathViewer()
/* 1225:     */   {
/* 1226:1698 */     if (this.repPathViewer == null)
/* 1227:     */     {
/* 1228:1699 */       this.repPathViewer = new RepViewer();
/* 1229:1700 */       this.repPathViewer.setName("Path memory");
/* 1230:     */     }
/* 1231:1702 */     return this.repPathViewer;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public RepViewer getRepPlaceViewer()
/* 1235:     */   {
/* 1236:1706 */     if (this.repPlaceViewer == null)
/* 1237:     */     {
/* 1238:1707 */       this.repPlaceViewer = new RepViewer();
/* 1239:1708 */       this.repPlaceViewer.setName("Place memory");
/* 1240:     */     }
/* 1241:1710 */     return this.repPlaceViewer;
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public RepViewer getRepRoleViewer()
/* 1245:     */   {
/* 1246:1714 */     if (this.repRoleViewer == null)
/* 1247:     */     {
/* 1248:1715 */       this.repRoleViewer = new RepViewer();
/* 1249:1716 */       this.repRoleViewer.setName("Role memory");
/* 1250:     */     }
/* 1251:1718 */     return this.repRoleViewer;
/* 1252:     */   }
/* 1253:     */   
/* 1254:     */   public RepViewer getRepActionViewer()
/* 1255:     */   {
/* 1256:1722 */     if (this.repActionViewer == null)
/* 1257:     */     {
/* 1258:1723 */       this.repActionViewer = new RepViewer();
/* 1259:1724 */       this.repActionViewer.setName("Action memory");
/* 1260:     */     }
/* 1261:1726 */     return this.repActionViewer;
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public RepViewer getRepBeliefViewer()
/* 1265:     */   {
/* 1266:1730 */     if (this.repBeliefViewer == null)
/* 1267:     */     {
/* 1268:1731 */       this.repBeliefViewer = new RepViewer();
/* 1269:1732 */       this.repBeliefViewer.setName("Belief memory");
/* 1270:     */     }
/* 1271:1734 */     return this.repBeliefViewer;
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public RepViewer getRepSocialViewer()
/* 1275:     */   {
/* 1276:1738 */     if (this.repSocialViewer == null)
/* 1277:     */     {
/* 1278:1739 */       this.repSocialViewer = new RepViewer();
/* 1279:1740 */       this.repSocialViewer.setName("Social memory");
/* 1280:     */     }
/* 1281:1742 */     return this.repSocialViewer;
/* 1282:     */   }
/* 1283:     */   
/* 1284:     */   public RepViewer getRepMoodViewer()
/* 1285:     */   {
/* 1286:1746 */     if (this.repMoodViewer == null)
/* 1287:     */     {
/* 1288:1747 */       this.repMoodViewer = new RepViewer();
/* 1289:1748 */       this.repMoodViewer.setName("Mood memory");
/* 1290:     */     }
/* 1291:1750 */     return this.repMoodViewer;
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   public RepViewer getRepJobViewer()
/* 1295:     */   {
/* 1296:1754 */     if (this.repJobViewer == null)
/* 1297:     */     {
/* 1298:1755 */       this.repJobViewer = new RepViewer();
/* 1299:1756 */       this.repJobViewer.setName("Job memory");
/* 1300:     */     }
/* 1301:1758 */     return this.repJobViewer;
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   public RepViewer getRepPropertyViewer()
/* 1305:     */   {
/* 1306:1762 */     if (this.repPropertyViewer == null)
/* 1307:     */     {
/* 1308:1763 */       this.repPropertyViewer = new RepViewer();
/* 1309:1764 */       this.repPropertyViewer.setName("Property memory");
/* 1310:     */     }
/* 1311:1766 */     return this.repPropertyViewer;
/* 1312:     */   }
/* 1313:     */   
/* 1314:     */   public RepViewer getRepComparisonViewer()
/* 1315:     */   {
/* 1316:1770 */     if (this.repComparisonViewer == null)
/* 1317:     */     {
/* 1318:1771 */       this.repComparisonViewer = new RepViewer();
/* 1319:1772 */       this.repComparisonViewer.setName("Comparison memory");
/* 1320:     */     }
/* 1321:1774 */     return this.repComparisonViewer;
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public RepViewer getRepTimeViewer()
/* 1325:     */   {
/* 1326:1778 */     if (this.repTimeViewer == null)
/* 1327:     */     {
/* 1328:1779 */       this.repTimeViewer = new RepViewer();
/* 1329:1780 */       this.repTimeViewer.setName("Time memory");
/* 1330:     */     }
/* 1331:1782 */     return this.repTimeViewer;
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   public RepViewer getRepTrajectoryViewer()
/* 1335:     */   {
/* 1336:1786 */     if (this.repTrajectoryViewer == null)
/* 1337:     */     {
/* 1338:1787 */       this.repTrajectoryViewer = new RepViewer();
/* 1339:1788 */       this.repTrajectoryViewer.setName("Trajectory memory");
/* 1340:     */     }
/* 1341:1790 */     return this.repTrajectoryViewer;
/* 1342:     */   }
/* 1343:     */   
/* 1344:     */   public RepViewer getRepTransitionViewer()
/* 1345:     */   {
/* 1346:1794 */     if (this.repTransitionViewer == null)
/* 1347:     */     {
/* 1348:1795 */       this.repTransitionViewer = new RepViewer();
/* 1349:1796 */       this.repTransitionViewer.setName("Transition memory");
/* 1350:     */     }
/* 1351:1798 */     return this.repTransitionViewer;
/* 1352:     */   }
/* 1353:     */   
/* 1354:     */   public RepViewer getRepTransferViewer()
/* 1355:     */   {
/* 1356:1802 */     if (this.repTransferViewer == null)
/* 1357:     */     {
/* 1358:1803 */       this.repTransferViewer = new RepViewer();
/* 1359:1804 */       this.repTransferViewer.setName("Transfer memory");
/* 1360:     */     }
/* 1361:1806 */     return this.repTransferViewer;
/* 1362:     */   }
/* 1363:     */   
/* 1364:     */   public TalkBackViewer getTalkBackViewer()
/* 1365:     */   {
/* 1366:1810 */     if (this.talkBackViewer == null)
/* 1367:     */     {
/* 1368:1811 */       this.talkBackViewer = new TalkBackViewer();
/* 1369:1812 */       this.talkBackViewer.setName("Play by play");
/* 1370:     */     }
/* 1371:1814 */     return this.talkBackViewer;
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   public TextBox getTextBox()
/* 1375:     */   {
/* 1376:1818 */     if (this.textBox == null)
/* 1377:     */     {
/* 1378:1819 */       this.textBox = new TextBox(this);
/* 1379:1820 */       this.textBox.setName("Talker text");
/* 1380:     */     }
/* 1381:1822 */     return this.textBox;
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   public TextEntryBox getTextEntryBox()
/* 1385:     */   {
/* 1386:1826 */     if (this.textEntryBox == null)
/* 1387:     */     {
/* 1388:1827 */       this.textEntryBox = new TextEntryBox();
/* 1389:1828 */       this.textEntryBox.setName("Text entry");
/* 1390:1829 */       if (!Switch.showTextEntryBox.isSelected()) {
/* 1391:1830 */         getTextEntryBox().zero();
/* 1392:     */       } else {
/* 1393:1833 */         getTextEntryBox().normal();
/* 1394:     */       }
/* 1395:1835 */       revalidate();
/* 1396:1836 */       getTextEntryBox().revalidate();
/* 1397:     */     }
/* 1398:1838 */     return this.textEntryBox;
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   public TrafficLight getTrafficLight()
/* 1402:     */   {
/* 1403:1842 */     if (this.trafficLight == null) {
/* 1404:1843 */       this.trafficLight = new TrafficLight();
/* 1405:     */     }
/* 1406:1845 */     return this.trafficLight;
/* 1407:     */   }
/* 1408:     */   
/* 1409:     */   public JPanel getTrafficLightPanel()
/* 1410:     */   {
/* 1411:1849 */     if (this.trafficLightPanel == null)
/* 1412:     */     {
/* 1413:1850 */       this.trafficLightPanel = new JPanel();
/* 1414:1851 */       this.trafficLightPanel.setOpaque(false);
/* 1415:1852 */       TrafficLight trafficLight = getTrafficLight();
/* 1416:1853 */       this.trafficLightPanel.add(trafficLight);
/* 1417:1854 */       trafficLight.setPreferredSize(new Dimension(45, 90));
/* 1418:1855 */       trafficLight.setName("Traffic light");
/* 1419:     */     }
/* 1420:1857 */     return this.trafficLightPanel;
/* 1421:     */   }
/* 1422:     */   
/* 1423:     */   public MentalModel getMentalModel1()
/* 1424:     */   {
/* 1425:1869 */     if (this.mentalModel1 == null)
/* 1426:     */     {
/* 1427:1870 */       this.mentalModel1 = new MentalModel("First perspective");
/* 1428:1871 */       this.mentalModel1.getStoryProcessor().setAwake(true);
/* 1429:     */     }
/* 1430:1873 */     return this.mentalModel1;
/* 1431:     */   }
/* 1432:     */   
/* 1433:     */   public MentalModel getMentalModel2()
/* 1434:     */   {
/* 1435:1877 */     if (this.mentalModel2 == null)
/* 1436:     */     {
/* 1437:1878 */       this.mentalModel2 = new MentalModel("Second perspective");
/* 1438:1879 */       this.mentalModel2.getStoryProcessor().setAwake(true);
/* 1439:     */     }
/* 1440:1881 */     return this.mentalModel2;
/* 1441:     */   }
/* 1442:     */   
/* 1443:     */   public StoryProcessor getStoryProcessorSimulation()
/* 1444:     */   {
/* 1445:1887 */     if (this.storyProcessorSimulation == null) {
/* 1446:1888 */       this.storyProcessorSimulation = new StoryProcessor();
/* 1447:     */     }
/* 1448:1890 */     return this.storyProcessorSimulation;
/* 1449:     */   }
/* 1450:     */   
/* 1451:     */   public EscalationExpert getEscalationExpert1()
/* 1452:     */   {
/* 1453:1894 */     if (this.escalationExpert1 == null) {
/* 1454:1895 */       this.escalationExpert1 = new EscalationExpert();
/* 1455:     */     }
/* 1456:1897 */     return this.escalationExpert1;
/* 1457:     */   }
/* 1458:     */   
/* 1459:     */   public EscalationExpert getEscalationExpert2()
/* 1460:     */   {
/* 1461:1901 */     if (this.escalationExpert2 == null) {
/* 1462:1902 */       this.escalationExpert2 = new EscalationExpert();
/* 1463:     */     }
/* 1464:1904 */     return this.escalationExpert2;
/* 1465:     */   }
/* 1466:     */   
/* 1467:     */   public StoryViewer getStoryViewer1()
/* 1468:     */   {
/* 1469:1908 */     if (this.storyViewer1 == null)
/* 1470:     */     {
/* 1471:1909 */       this.storyViewer1 = new StoryViewer(this);
/* 1472:1910 */       this.storyViewer1.setName("First perspective viewer");
/* 1473:     */     }
/* 1474:1912 */     return this.storyViewer1;
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   public StoryViewer getStoryViewer2()
/* 1478:     */   {
/* 1479:1916 */     if (this.storyViewer2 == null)
/* 1480:     */     {
/* 1481:1917 */       this.storyViewer2 = new StoryViewer(this);
/* 1482:1918 */       this.storyViewer2.setName("Second perspective viewer");
/* 1483:     */     }
/* 1484:1920 */     return this.storyViewer2;
/* 1485:     */   }
/* 1486:     */   
/* 1487:     */   public StoryViewer getStoryViewerForGenesisTesting()
/* 1488:     */   {
/* 1489:1924 */     if (this.storyViewerForGenesisTesting == null)
/* 1490:     */     {
/* 1491:1925 */       this.storyViewerForGenesisTesting = new StoryViewer(this);
/* 1492:1926 */       this.storyViewer2.setName("Story viewer for Genesis testing");
/* 1493:     */     }
/* 1494:1928 */     return this.storyViewerForGenesisTesting;
/* 1495:     */   }
/* 1496:     */   
/* 1497:     */   public StoryRecallViewer getStoryRecallViewer1()
/* 1498:     */   {
/* 1499:1939 */     if (this.storyRecallViewer1 == null) {
/* 1500:1940 */       this.storyRecallViewer1 = new StoryRecallViewer();
/* 1501:     */     }
/* 1502:1942 */     return this.storyRecallViewer1;
/* 1503:     */   }
/* 1504:     */   
/* 1505:     */   public StoryRecallViewer getStoryRecallViewer2()
/* 1506:     */   {
/* 1507:1946 */     if (this.storyRecallViewer2 == null) {
/* 1508:1947 */       this.storyRecallViewer2 = new StoryRecallViewer();
/* 1509:     */     }
/* 1510:1949 */     return this.storyRecallViewer2;
/* 1511:     */   }
/* 1512:     */   
/* 1513:     */   public OnsetViewer getOnsetViewer1()
/* 1514:     */   {
/* 1515:1953 */     if (this.onsetViewer1 == null) {
/* 1516:1954 */       this.onsetViewer1 = new OnsetViewer();
/* 1517:     */     }
/* 1518:1956 */     return this.onsetViewer1;
/* 1519:     */   }
/* 1520:     */   
/* 1521:     */   public OnsetViewer getOnsetViewer2()
/* 1522:     */   {
/* 1523:1960 */     if (this.onsetViewer2 == null) {
/* 1524:1961 */       this.onsetViewer2 = new OnsetViewer();
/* 1525:     */     }
/* 1526:1963 */     return this.onsetViewer2;
/* 1527:     */   }
/* 1528:     */   
/* 1529:     */   public StoryRecallExpert getStoryRecallExpert1()
/* 1530:     */   {
/* 1531:1967 */     if (this.storyRecallExpert1 == null) {
/* 1532:1968 */       this.storyRecallExpert1 = new StoryRecallExpert();
/* 1533:     */     }
/* 1534:1970 */     return this.storyRecallExpert1;
/* 1535:     */   }
/* 1536:     */   
/* 1537:     */   public StoryRecallExpert getStoryRecallExpert2()
/* 1538:     */   {
/* 1539:1974 */     if (this.storyRecallExpert2 == null) {
/* 1540:1975 */       this.storyRecallExpert2 = new StoryRecallExpert();
/* 1541:     */     }
/* 1542:1977 */     return this.storyRecallExpert2;
/* 1543:     */   }
/* 1544:     */   
/* 1545:     */   public WiredSplitPane getRuleViewerPanel()
/* 1546:     */   {
/* 1547:1981 */     if (this.ruleViewerPanel == null)
/* 1548:     */     {
/* 1549:1982 */       this.ruleViewerPanel = new WiredSplitPane(getRuleViewer1Wrapper(), getRuleViewer2Wrapper());
/* 1550:1983 */       this.ruleViewerPanel.setName("Rules");
/* 1551:     */     }
/* 1552:1985 */     return this.ruleViewerPanel;
/* 1553:     */   }
/* 1554:     */   
/* 1555:     */   public JPanel getRuleViewer1Wrapper()
/* 1556:     */   {
/* 1557:1989 */     if (this.ruleViewer1Wrapper == null)
/* 1558:     */     {
/* 1559:1990 */       this.ruleViewer1Wrapper = new JPanel();
/* 1560:1991 */       this.ruleViewer1Wrapper.setLayout(new BorderLayout());
/* 1561:1992 */       this.ruleViewer1Wrapper.add(getRuleViewer1());
/* 1562:1993 */       this.ruleViewer1Wrapper.add(getRulePlotUnitBar1(), "South");
/* 1563:     */     }
/* 1564:1995 */     return this.ruleViewer1Wrapper;
/* 1565:     */   }
/* 1566:     */   
/* 1567:     */   public JPanel getRuleViewer2Wrapper()
/* 1568:     */   {
/* 1569:1999 */     if (this.ruleViewer2Wrapper == null)
/* 1570:     */     {
/* 1571:2000 */       this.ruleViewer2Wrapper = new JPanel();
/* 1572:2001 */       this.ruleViewer2Wrapper.setLayout(new BorderLayout());
/* 1573:2002 */       this.ruleViewer2Wrapper.add(getRuleViewer2());
/* 1574:2003 */       this.ruleViewer2Wrapper.add(getRulePlotUnitBar2(), "South");
/* 1575:     */     }
/* 1576:2005 */     return this.ruleViewer2Wrapper;
/* 1577:     */   }
/* 1578:     */   
/* 1579:     */   public RuleViewer getRuleViewer1()
/* 1580:     */   {
/* 1581:2009 */     if (this.ruleViewer1 == null)
/* 1582:     */     {
/* 1583:2010 */       this.ruleViewer1 = new RuleViewer(this);
/* 1584:2011 */       this.ruleViewer1.setName("Rules");
/* 1585:     */     }
/* 1586:2013 */     return this.ruleViewer1;
/* 1587:     */   }
/* 1588:     */   
/* 1589:     */   public RuleViewer getRuleViewer2()
/* 1590:     */   {
/* 1591:2017 */     if (this.ruleViewer2 == null)
/* 1592:     */     {
/* 1593:2018 */       this.ruleViewer2 = new RuleViewer(this);
/* 1594:2019 */       this.ruleViewer2.setName("Rules");
/* 1595:     */     }
/* 1596:2021 */     return this.ruleViewer2;
/* 1597:     */   }
/* 1598:     */   
/* 1599:     */   public StoryViewer getConceptViewer1()
/* 1600:     */   {
/* 1601:2025 */     if (this.conceptViewer1 == null)
/* 1602:     */     {
/* 1603:2026 */       this.conceptViewer1 = new StoryViewer(this);
/* 1604:2027 */       this.conceptViewer1.setName("Concept viewer 1");
/* 1605:     */     }
/* 1606:2029 */     return this.conceptViewer1;
/* 1607:     */   }
/* 1608:     */   
/* 1609:     */   public StoryViewer getConceptViewer2()
/* 1610:     */   {
/* 1611:2033 */     if (this.conceptViewer2 == null)
/* 1612:     */     {
/* 1613:2034 */       this.conceptViewer2 = new StoryViewer(this);
/* 1614:2035 */       this.conceptViewer2.setName("Concept viewer 2");
/* 1615:     */     }
/* 1616:2037 */     return this.conceptViewer2;
/* 1617:     */   }
/* 1618:     */   
/* 1619:     */   public WiredSplitPane getInstantiatedConceptViewerPanel()
/* 1620:     */   {
/* 1621:2041 */     if (this.reflectionPanel == null)
/* 1622:     */     {
/* 1623:2042 */       this.reflectionPanel = new WiredSplitPane(getMentalModel1().getInstantiatedConceptViewer(), getMentalModel2().getInstantiatedConceptViewer());
/* 1624:2043 */       this.reflectionPanel.setName("Instantiated concepts");
/* 1625:     */     }
/* 1626:2045 */     return this.reflectionPanel;
/* 1627:     */   }
/* 1628:     */   
/* 1629:     */   public WiredSplitPane getInstantiatedRuleViewerPanel()
/* 1630:     */   {
/* 1631:2065 */     if (this.instantiatedRuleViewerPanel == null)
/* 1632:     */     {
/* 1633:2066 */       this.instantiatedRuleViewerPanel = new WiredSplitPane(getInstantiatedRuleViewer1Wrapper(), getInstantiatedRuleViewer2Wrapper());
/* 1634:2067 */       this.instantiatedRuleViewerPanel.setName("Instantiated rules");
/* 1635:     */     }
/* 1636:2069 */     return this.instantiatedRuleViewerPanel;
/* 1637:     */   }
/* 1638:     */   
/* 1639:     */   public JPanel getInstantiatedRuleViewer1Wrapper()
/* 1640:     */   {
/* 1641:2073 */     if (this.instantiatedRuleViewer1Wrapper == null)
/* 1642:     */     {
/* 1643:2074 */       this.instantiatedRuleViewer1Wrapper = new JPanel();
/* 1644:2075 */       this.instantiatedRuleViewer1Wrapper.setLayout(new BorderLayout());
/* 1645:2076 */       this.instantiatedRuleViewer1Wrapper.add(getMentalModel1().getInstantiatedRuleViewer());
/* 1646:2077 */       this.instantiatedRuleViewer1Wrapper.add(getInstRulePlotUnitBar1(), "South");
/* 1647:     */     }
/* 1648:2079 */     return this.instantiatedRuleViewer1Wrapper;
/* 1649:     */   }
/* 1650:     */   
/* 1651:     */   public JPanel getInstantiatedRuleViewer2Wrapper()
/* 1652:     */   {
/* 1653:2083 */     if (this.instantiatedRuleViewer2Wrapper == null)
/* 1654:     */     {
/* 1655:2084 */       this.instantiatedRuleViewer2Wrapper = new JPanel();
/* 1656:2085 */       this.instantiatedRuleViewer2Wrapper.setLayout(new BorderLayout());
/* 1657:2086 */       this.instantiatedRuleViewer2Wrapper.add(getMentalModel2().getInstantiatedRuleViewer());
/* 1658:2087 */       this.instantiatedRuleViewer2Wrapper.add(getInstRulePlotUnitBar2(), "South");
/* 1659:     */     }
/* 1660:2089 */     return this.instantiatedRuleViewer2Wrapper;
/* 1661:     */   }
/* 1662:     */   
/* 1663:     */   public void openGates()
/* 1664:     */   {
/* 1665:2109 */     System.out.println("Opening gates");
/* 1666:     */     
/* 1667:2111 */     memorySwitch.open();
/* 1668:     */   }
/* 1669:     */   
/* 1670:     */   public void openInterface()
/* 1671:     */   {
/* 1672:2115 */     changeState("Open");
/* 1673:     */   }
/* 1674:     */   
/* 1675:     */   public class SomTrajectoryBox
/* 1676:     */     extends AbstractWiredBox
/* 1677:     */   {
/* 1678:     */     public SomTrajectoryBox()
/* 1679:     */     {
/* 1680:2120 */       Connections.getPorts(this).addSignalProcessor("process");
/* 1681:     */     }
/* 1682:     */     
/* 1683:     */     public void process(Object input)
/* 1684:     */     {
/* 1685:2124 */       List<Entity> stuff = GenesisGetters.this.getMemory().getBestMatches(input);
/* 1686:2125 */       if (((input instanceof Entity)) && (stuff.size() > 0))
/* 1687:     */       {
/* 1688:2126 */         Entity t = (Entity)input;
/* 1689:2127 */         for (int i = 0; i < stuff.size(); i++) {
/* 1690:2128 */           System.out.println("Distance " + Distances.distance(t, (Entity)stuff.get(i)));
/* 1691:     */         }
/* 1692:2130 */         double d = Distances.distance(t, (Entity)stuff.get(0));
/* 1693:     */         
/* 1694:     */ 
/* 1695:2133 */         Connections.getPorts(this).transmit(stuff.get(0));
/* 1696:     */       }
/* 1697:     */     }
/* 1698:     */   }
/* 1699:     */   
/* 1700:     */   public ComparisonExpert getComparisonExpert()
/* 1701:     */   {
/* 1702:2156 */     if (this.comparisonExpert == null) {
/* 1703:2157 */       this.comparisonExpert = new ComparisonExpert();
/* 1704:     */     }
/* 1705:2159 */     return this.comparisonExpert;
/* 1706:     */   }
/* 1707:     */   
/* 1708:     */   public QuestionExpert getQuestionExpert1()
/* 1709:     */   {
/* 1710:2163 */     if (this.questionExpert1 == null) {
/* 1711:2164 */       this.questionExpert1 = new QuestionExpert(getMentalModel1());
/* 1712:     */     }
/* 1713:2166 */     return this.questionExpert1;
/* 1714:     */   }
/* 1715:     */   
/* 1716:     */   public StoryExpert getStoryExpert()
/* 1717:     */   {
/* 1718:2170 */     if (this.storyExpert == null) {
/* 1719:2171 */       this.storyExpert = new StoryExpert();
/* 1720:     */     }
/* 1721:2173 */     return this.storyExpert;
/* 1722:     */   }
/* 1723:     */   
/* 1724:     */   public CommandExpert getCommandExpert()
/* 1725:     */   {
/* 1726:2177 */     if (this.commandExpert == null) {
/* 1727:2178 */       this.commandExpert = new CommandExpert();
/* 1728:     */     }
/* 1729:2180 */     return this.commandExpert;
/* 1730:     */   }
/* 1731:     */   
/* 1732:     */   public ImaginationExpert getImagineExpert()
/* 1733:     */   {
/* 1734:2184 */     if (this.imaginationExpert == null) {
/* 1735:2185 */       this.imaginationExpert = new ImaginationExpert(this);
/* 1736:     */     }
/* 1737:2187 */     return this.imaginationExpert;
/* 1738:     */   }
/* 1739:     */   
/* 1740:     */   public DescribeExpert getDescribeExpert()
/* 1741:     */   {
/* 1742:2191 */     if (this.describeExpert == null) {
/* 1743:2192 */       this.describeExpert = new DescribeExpert();
/* 1744:     */     }
/* 1745:2194 */     return this.describeExpert;
/* 1746:     */   }
/* 1747:     */   
/* 1748:     */   public StateExpert getStateExpert()
/* 1749:     */   {
/* 1750:2198 */     if (this.stateExpert == null) {
/* 1751:2199 */       this.stateExpert = new StateExpert();
/* 1752:     */     }
/* 1753:2201 */     return this.stateExpert;
/* 1754:     */   }
/* 1755:     */   
/* 1756:     */   public static AnaphoraExpert getAnaphoraExpert()
/* 1757:     */   {
/* 1758:2205 */     if (anaphoraExpert == null) {
/* 1759:2206 */       anaphoraExpert = new AnaphoraExpert();
/* 1760:     */     }
/* 1761:2208 */     return anaphoraExpert;
/* 1762:     */   }
/* 1763:     */   
/* 1764:     */   public IdiomExpert getIdiomExpert()
/* 1765:     */   {
/* 1766:2212 */     if (this.idiomExpert == null) {
/* 1767:2213 */       this.idiomExpert = new IdiomExpert();
/* 1768:     */     }
/* 1769:2215 */     return this.idiomExpert;
/* 1770:     */   }
/* 1771:     */   
/* 1772:     */   public StateMaintainer getStateMaintainer()
/* 1773:     */   {
/* 1774:2219 */     if (this.stateMaintainer == null) {
/* 1775:2220 */       this.stateMaintainer = new StateMaintainer(this);
/* 1776:     */     }
/* 1777:2222 */     return this.stateMaintainer;
/* 1778:     */   }
/* 1779:     */   
/* 1780:     */   public FileSourceReader getFileSourceReader()
/* 1781:     */   {
/* 1782:2226 */     return FileSourceReader.getFileSourceReader();
/* 1783:     */   }
/* 1784:     */   
/* 1785:     */   public StoryTeller getStoryTeller()
/* 1786:     */   {
/* 1787:2231 */     if (this.storyTeller == null) {
/* 1788:2232 */       this.storyTeller = new StoryTeller();
/* 1789:     */     }
/* 1790:2234 */     return this.storyTeller;
/* 1791:     */   }
/* 1792:     */   
/* 1793:     */   public SummaryHelper getSummaryHelper()
/* 1794:     */   {
/* 1795:2238 */     if (this.summaryHelper == null) {
/* 1796:2239 */       this.summaryHelper = new SummaryHelper();
/* 1797:     */     }
/* 1798:2241 */     return this.summaryHelper;
/* 1799:     */   }
/* 1800:     */   
/* 1801:     */   public StoryEnvironment getStoryEnvironment()
/* 1802:     */   {
/* 1803:2245 */     if (this.storyEnvironment == null) {
/* 1804:2246 */       this.storyEnvironment = new StoryEnvironment();
/* 1805:     */     }
/* 1806:2248 */     return this.storyEnvironment;
/* 1807:     */   }
/* 1808:     */   
/* 1809:     */   public InternalNarrator getInternalNarrator()
/* 1810:     */   {
/* 1811:2252 */     if (this.internalNarrator == null) {
/* 1812:2253 */       this.internalNarrator = new InternalNarrator();
/* 1813:     */     }
/* 1814:2255 */     return this.internalNarrator;
/* 1815:     */   }
/* 1816:     */   
/* 1817:     */   public StaticAudienceModeler getStaticAudienceModeler()
/* 1818:     */   {
/* 1819:2259 */     if (this.staticAudienceModeler == null) {
/* 1820:2260 */       this.staticAudienceModeler = new StaticAudienceModeler();
/* 1821:     */     }
/* 1822:2262 */     return this.staticAudienceModeler;
/* 1823:     */   }
/* 1824:     */   
/* 1825:     */   public GoalSpecifier getGoalSpecifier()
/* 1826:     */   {
/* 1827:2266 */     if (this.goalSpecifier == null) {
/* 1828:2267 */       this.goalSpecifier = new GoalSpecifier();
/* 1829:     */     }
/* 1830:2269 */     return this.goalSpecifier;
/* 1831:     */   }
/* 1832:     */   
/* 1833:     */   public GoalTracker getGoalTracker()
/* 1834:     */   {
/* 1835:2273 */     if (this.goalTracker == null) {
/* 1836:2274 */       this.goalTracker = new GoalTracker();
/* 1837:     */     }
/* 1838:2276 */     return this.goalTracker;
/* 1839:     */   }
/* 1840:     */   
/* 1841:     */   public StoryPreSimulator getStoryPresimulator()
/* 1842:     */   {
/* 1843:2280 */     if (this.storyPresimulator == null) {
/* 1844:2281 */       this.storyPresimulator = new StoryPreSimulator();
/* 1845:     */     }
/* 1846:2283 */     return this.storyPresimulator;
/* 1847:     */   }
/* 1848:     */   
/* 1849:     */   public StorySimulator getStorySimulator()
/* 1850:     */   {
/* 1851:2287 */     if (this.storySimulator == null) {
/* 1852:2288 */       this.storySimulator = new StorySimulator();
/* 1853:     */     }
/* 1854:2290 */     return this.storySimulator;
/* 1855:     */   }
/* 1856:     */   
/* 1857:     */   public StoryModifier getStoryModifier()
/* 1858:     */   {
/* 1859:2294 */     if (this.storyModifier == null) {
/* 1860:2295 */       this.storyModifier = new StoryModifier();
/* 1861:     */     }
/* 1862:2297 */     return this.storyModifier;
/* 1863:     */   }
/* 1864:     */   
/* 1865:     */   public StoryPublisher getStoryPublisher()
/* 1866:     */   {
/* 1867:2301 */     if (this.storyPublisher == null) {
/* 1868:2302 */       this.storyPublisher = new StoryPublisher();
/* 1869:     */     }
/* 1870:2304 */     return this.storyPublisher;
/* 1871:     */   }
/* 1872:     */   
/* 1873:     */   public void initializeListeners()
/* 1874:     */   {
/* 1875:2320 */     GeneralPurposeListener l = new GeneralPurposeListener(this);
/* 1876:     */     
/* 1877:2322 */     this.disambiguationButton.addActionListener(l);
/* 1878:2323 */     this.wordnetPurgeButton.addActionListener(l);
/* 1879:2324 */     this.startPurgeButton.addActionListener(l);
/* 1880:2325 */     this.experienceButton.addActionListener(l);
/* 1881:2326 */     this.focusButton.addActionListener(l);
/* 1882:2327 */     this.clearMemoryButton.addActionListener(l);
/* 1883:2328 */     this.eraseTextButton.addActionListener(l);
/* 1884:2329 */     this.clearSummaryTableButton.addActionListener(l);
/* 1885:     */     
/* 1886:2331 */     FileReaderButtonListener m = new FileReaderButtonListener();
/* 1887:2332 */     this.testRepresentationsButton.addActionListener(m);
/* 1888:2333 */     this.testStoriesButton.addActionListener(m);
/* 1889:2334 */     this.debugButton1.addActionListener(m);
/* 1890:2335 */     this.debugButton2.addActionListener(m);
/* 1891:2336 */     this.runWorkbenchTest.addActionListener(m);
/* 1892:     */     
/* 1893:2338 */     this.rerunExperiment.addActionListener(m);
/* 1894:2339 */     this.rereadFile.addActionListener(m);
/* 1895:2340 */     this.simulateCo57Button.addActionListener(m);
/* 1896:2341 */     this.connectTestingBox.addActionListener(m);
/* 1897:2342 */     this.disconnectTestingBox.addActionListener(m);
/* 1898:2343 */     this.demoSimulator.addActionListener(m);
/* 1899:2344 */     this.debugVideoFileButton.addActionListener(m);
/* 1900:2345 */     this.loopButton.addActionListener(m);
/* 1901:2346 */     this.kjFileButton.addActionListener(m);
/* 1902:2347 */     this.kjeFileButton.addActionListener(m);
/* 1903:2348 */     this.loadVideoPrecedents.addActionListener(m);
/* 1904:2349 */     this.visionFileButton.addActionListener(m);
/* 1905:     */     
/* 1906:2351 */     RunListener n = new RunListener();
/* 1907:2352 */     nextButton.addActionListener(n);
/* 1908:2353 */     runButton.addActionListener(n);
/* 1909:     */     
/* 1910:2355 */     TestSwitchListener s = new TestSwitchListener();
/* 1911:2356 */     Switch.useStartBeta.addActionListener(s);
/* 1912:2357 */     Switch.testStartBeta.addActionListener(s);
/* 1913:     */   }
/* 1914:     */   
/* 1915:     */   private JPanel getTopPanel()
/* 1916:     */   {
/* 1917:2362 */     if (this.topPanel == null)
/* 1918:     */     {
/* 1919:2363 */       this.topPanel = new JPanel();
/* 1920:2364 */       this.topPanel.setLayout(new BorderLayout());
/* 1921:2365 */       this.topPanel.setOpaque(false);
/* 1922:2366 */       this.topPanel.add(getSplitPane(), "Center");
/* 1923:2367 */       this.topPanel.add(getTextEntryBox(), "South");
/* 1924:     */     }
/* 1925:2369 */     return this.topPanel;
/* 1926:     */   }
/* 1927:     */   
/* 1928:     */   public void initializeGraphics()
/* 1929:     */   {
/* 1930:2373 */     add(getNorthSouthSplitPane(), "Center");
/* 1931:2374 */     getExpertsPanel().setOpaque(false);
/* 1932:     */     
/* 1933:     */ 
/* 1934:2377 */     setSize(800, 550);
/* 1935:     */   }
/* 1936:     */   
/* 1937:     */   public MasterPanel getMasterPanel()
/* 1938:     */   {
/* 1939:2382 */     if (this.masterPanel == null) {
/* 1940:2383 */       this.masterPanel = new MasterPanel();
/* 1941:     */     }
/* 1942:2385 */     return this.masterPanel;
/* 1943:     */   }
/* 1944:     */   
/* 1945:     */   class RunListener
/* 1946:     */     implements ActionListener
/* 1947:     */   {
/* 1948:     */     RunListener() {}
/* 1949:     */     
/* 1950:     */     public void actionPerformed(ActionEvent e)
/* 1951:     */     {
/* 1952:2391 */       if (e.getSource() == GenesisGetters.getRunButton())
/* 1953:     */       {
/* 1954:2392 */         GenesisGetters.getNextButton().setEnabled(false);
/* 1955:2393 */         GenesisGetters.getRunButton().setEnabled(false);
/* 1956:2394 */         GenesisGetters.this.getFileSourceReader().readRemainingSentences();
/* 1957:     */       }
/* 1958:2397 */       if (e.getSource() == GenesisGetters.getNextButton()) {
/* 1959:2398 */         GenesisGetters.this.getFileSourceReader().readNextSentence();
/* 1960:     */       }
/* 1961:     */     }
/* 1962:     */   }
/* 1963:     */   
/* 1964:     */   class FileReaderButtonListener
/* 1965:     */     implements ActionListener
/* 1966:     */   {
/* 1967:     */     FileReaderButtonListener() {}
/* 1968:     */     
/* 1969:     */     public void actionPerformed(ActionEvent e)
/* 1970:     */     {
/* 1971:2405 */       GenesisGetters.this.setBottomPanel("Elaboration graph");
/* 1972:2406 */       GenesisGetters.memorySwitch.setSelected(false);
/* 1973:2407 */       if (e.getSource() == GenesisGetters.this.testRepresentationsButton)
/* 1974:     */       {
/* 1975:2408 */         GenesisGetters.this.setBottomPanel("Experts");
/* 1976:2409 */         GenesisGetters.this.getFileSourceReader().readStory("representation test.txt");
/* 1977:     */       }
/* 1978:2411 */       if (e.getSource() == GenesisGetters.this.testStoriesButton)
/* 1979:     */       {
/* 1980:2412 */         GenesisGetters.this.setBottomPanel("Elaboration graph");
/* 1981:2413 */         GenesisGetters.this.getFileSourceReader().readStory("story test.txt");
/* 1982:     */       }
/* 1983:2415 */       else if (e.getSource() == GenesisGetters.this.rereadFile)
/* 1984:     */       {
/* 1985:2416 */         GenesisGetters.this.getFileSourceReader().rerun();
/* 1986:     */       }
/* 1987:2418 */       else if (e.getSource() == GenesisGetters.this.rerunExperiment)
/* 1988:     */       {
/* 1989:2419 */         Mark.say(new Object[] {"Rerun experiment" });
/* 1990:2420 */         if (GenesisGetters.this.currentMenuItem != null) {
/* 1991:2421 */           GenesisGetters.this.currentMenuItem.doClick();
/* 1992:     */         } else {
/* 1993:2424 */           Mark.err(new Object[] {"Cannot rerun because no experiment has been run yet" });
/* 1994:     */         }
/* 1995:     */       }
/* 1996:2427 */       else if (e.getSource() == GenesisGetters.this.debugButton1)
/* 1997:     */       {
/* 1998:2428 */         GenesisGetters.this.setRightPanel("Mental Models");
/* 1999:2429 */         GenesisGetters.this.setBottomPanel("Elaboration graph");
/* 2000:2430 */         GenesisGetters.this.getFileSourceReader().readStory("debug1.txt");
/* 2001:     */       }
/* 2002:2432 */       else if (e.getSource() == GenesisGetters.this.debugButton2)
/* 2003:     */       {
/* 2004:2433 */         GenesisGetters.this.setRightPanel("Mental Models");
/* 2005:2434 */         GenesisGetters.this.setBottomPanel("Elaboration graph");
/* 2006:2435 */         GenesisGetters.this.getFileSourceReader().readStory("debug2.txt");
/* 2007:     */       }
/* 2008:2438 */       else if (e.getSource() == GenesisGetters.this.runWorkbenchTest)
/* 2009:     */       {
/* 2010:2439 */         GenesisGetters.this.setRightPanel("Mental Models");
/* 2011:2440 */         GenesisGetters.this.setBottomPanel("Elaboration graph");
/* 2012:2441 */         GenesisGetters.this.runWorkbenchTest();
/* 2013:     */       }
/* 2014:2444 */       else if (e.getSource() == GenesisGetters.this.simulateCo57Button)
/* 2015:     */       {
/* 2016:2445 */         Co57Simulator.Simulate();
/* 2017:     */       }
/* 2018:2448 */       else if (e.getSource() == GenesisGetters.this.connectTestingBox)
/* 2019:     */       {
/* 2020:2449 */         Mark.say(new Object[] {"Connecting" });
/* 2021:2450 */         GenesisGetters.this.disconnectTestingBox.setEnabled(true);
/* 2022:2451 */         GenesisGetters.this.connectTestingBox.setEnabled(false);
/* 2023:2452 */         Connections.wire("complete story analysis port", GenesisGetters.this.getMentalModel1(), TestStoryOutputBox.getBox());
/* 2024:     */       }
/* 2025:2454 */       else if (e.getSource() == GenesisGetters.this.disconnectTestingBox)
/* 2026:     */       {
/* 2027:2455 */         Mark.say(new Object[] {"Disconnecting" });
/* 2028:2456 */         GenesisGetters.this.disconnectTestingBox.setEnabled(false);
/* 2029:2457 */         GenesisGetters.this.connectTestingBox.setEnabled(true);
/* 2030:2458 */         Connections.disconnect("complete story analysis port", GenesisGetters.this.getMentalModel1(), TestStoryOutputBox.getBox());
/* 2031:     */       }
/* 2032:2460 */       else if (e.getSource() == GenesisGetters.this.demoSimulator)
/* 2033:     */       {
/* 2034:2461 */         Connections.getPorts(StartPreprocessor.getStartPreprocessor()).transmit("self", "Interpret video test");
/* 2035:     */       }
/* 2036:2463 */       else if (e.getSource() == GenesisGetters.this.debugVideoFileButton)
/* 2037:     */       {
/* 2038:2464 */         GenesisGetters.this.getFileSourceReader().readStory("debugVideo.txt");
/* 2039:     */       }
/* 2040:2466 */       else if (e.getSource() == GenesisGetters.this.loopButton)
/* 2041:     */       {
/* 2042:2467 */         GenesisGetters.this.getFileSourceReader().readStory("loop.txt");
/* 2043:     */       }
/* 2044:2473 */       else if (e.getSource() == GenesisGetters.this.kjFileButton)
/* 2045:     */       {
/* 2046:2474 */         GenesisGetters.this.getFileSourceReader().readStory("KoreanSlander.txt");
/* 2047:     */       }
/* 2048:2476 */       else if (e.getSource() == GenesisGetters.this.kjeFileButton)
/* 2049:     */       {
/* 2050:2477 */         GenesisGetters.this.getFileSourceReader().readStory("KJER.txt");
/* 2051:     */       }
/* 2052:2479 */       else if (e.getSource() == GenesisGetters.this.loadVideoPrecedents)
/* 2053:     */       {
/* 2054:2480 */         GenesisGetters.this.getFileSourceReader().readStory("debugVideoPrecedents.txt");
/* 2055:     */       }
/* 2056:2482 */       else if (e.getSource() == GenesisGetters.this.visionFileButton)
/* 2057:     */       {
/* 2058:2483 */         GenesisGetters.this.getFileSourceReader().readStory("dialog.txt");
/* 2059:     */       }
/* 2060:     */     }
/* 2061:     */   }
/* 2062:     */   
/* 2063:2491 */   private boolean workBenchConnectionWired = false;
/* 2064:     */   
/* 2065:     */   private void runWorkbenchTest()
/* 2066:     */   {
/* 2067:2494 */     if (!this.workBenchConnectionWired)
/* 2068:     */     {
/* 2069:2495 */       Mark.say(new Object[] {"Creating connection to WorkBench" });
/* 2070:2496 */       Connections.wire(WorkbenchConnection.getWorkbenchConnection(), getMentalModel1());
/* 2071:2497 */       this.workBenchConnectionWired = true;
/* 2072:     */     }
/* 2073:2499 */     getMentalModel1().clearAllMemories();
/* 2074:2500 */     WorkbenchConnection.getWorkbenchConnection().test();
/* 2075:2501 */     getMentalModel1().getStoryProcessor().stopStory();
/* 2076:     */   }
/* 2077:     */   
/* 2078:     */   class DemonstrationListener
/* 2079:     */     implements ActionListener
/* 2080:     */   {
/* 2081:     */     Component component;
/* 2082:     */     
/* 2083:     */     public DemonstrationListener(Component component)
/* 2084:     */     {
/* 2085:2508 */       this.component = component;
/* 2086:     */     }
/* 2087:     */     
/* 2088:     */     public void actionPerformed(ActionEvent event)
/* 2089:     */     {
/* 2090:2512 */       Object source = event.getSource();
/* 2091:2513 */       FileReaderPanel fileReaderPanel = GenesisGetters.this.getFileReaderFrame();
/* 2092:     */       
/* 2093:2515 */       Radio.normalModeButton.doClick();
/* 2094:     */       
/* 2095:2517 */       Switch.showOnsetSwitch.setSelected(false);
/* 2096:     */       
/* 2097:2519 */       Radio.alignmentButton.setSelected(false);
/* 2098:     */       
/* 2099:     */ 
/* 2100:2522 */       Switch.showTextEntryBox.setSelected(true);
/* 2101:2523 */       Switch.showTextEntryBox.doClick();
/* 2102:     */       
/* 2103:2525 */       Switch.showDisconnectedSwitch.setSelected(true);
/* 2104:2526 */       Switch.showDisconnectedSwitch.doClick();
/* 2105:     */       
/* 2106:2528 */       Switch.summarizeViaPHW.setEnabled(true);
/* 2107:2529 */       Switch.summarizeViaPHW.setSelected(false);
/* 2108:     */       
/* 2109:     */ 
/* 2110:2532 */       GenesisGetters.this.getNorthSouthSplitPane().setDividerLocation(0.5D);
/* 2111:     */       
/* 2112:2534 */       GenesisGetters.this.getSplitPane().setDividerLocation(0.25D);
/* 2113:     */       
/* 2114:2536 */       GenesisGetters.this.setBottomPanel("Elaboration graph");
/* 2115:2538 */       if ((source instanceof JMenuItem)) {
/* 2116:2539 */         GenesisGetters.this.currentMenuItem = ((JMenuItem)source);
/* 2117:     */       }
/* 2118:2541 */       if (source == GenesisGetters.this.hamlet)
/* 2119:     */       {
/* 2120:2542 */         GenesisGetters.this.getFileSourceReader().readStory("Hamlet1.txt");
/* 2121:     */       }
/* 2122:2544 */       else if (source == GenesisGetters.this.caesar)
/* 2123:     */       {
/* 2124:2545 */         GenesisGetters.this.getFileSourceReader().readStory("Caesar1.txt");
/* 2125:     */       }
/* 2126:2547 */       else if (source == GenesisGetters.this.hamletCaesar)
/* 2127:     */       {
/* 2128:2548 */         GenesisGetters.this.getFileSourceReader().readStory("Hamlet-Caesar.txt");
/* 2129:     */       }
/* 2130:2550 */       else if ((source == GenesisGetters.this.threeStoryItem) || (source == GenesisGetters.this.fiveStoryItem) || (source == GenesisGetters.this.tenStoryItem) || (source == GenesisGetters.this.fifteenStoryItem))
/* 2131:     */       {
/* 2132:2551 */         Radio.calculateSimilarityButton.doClick();
/* 2133:2552 */         if (source == GenesisGetters.this.threeStoryItem) {
/* 2134:2553 */           GenesisGetters.this.getFileSourceReader().readStory("_read3.txt");
/* 2135:     */         }
/* 2136:2555 */         if (source == GenesisGetters.this.fiveStoryItem) {
/* 2137:2556 */           GenesisGetters.this.getFileSourceReader().readStory("_read5.txt");
/* 2138:     */         }
/* 2139:2558 */         if (source == GenesisGetters.this.tenStoryItem) {
/* 2140:2559 */           GenesisGetters.this.getFileSourceReader().readStory("_read10.txt");
/* 2141:     */         }
/* 2142:2561 */         if (source == GenesisGetters.this.fifteenStoryItem) {
/* 2143:2562 */           GenesisGetters.this.getFileSourceReader().readStory("_read15.txt");
/* 2144:     */         }
/* 2145:     */       }
/* 2146:2565 */       else if ((source == GenesisGetters.this.spoonMacbethItem) || (source == GenesisGetters.this.teachMacbethItem) || (source == GenesisGetters.this.explainMacbethItem))
/* 2147:     */       {
/* 2148:2566 */         Radio.tellStoryButton.doClick();
/* 2149:2567 */         if (source == GenesisGetters.this.spoonMacbethItem) {
/* 2150:2568 */           Radio.spoonFeedButton.setSelected(true);
/* 2151:2570 */         } else if (source == GenesisGetters.this.explainMacbethItem) {
/* 2152:2571 */           Radio.primingButton.setSelected(true);
/* 2153:2573 */         } else if (source == GenesisGetters.this.teachMacbethItem) {
/* 2154:2574 */           Radio.primingWithIntrospectionButton.setSelected(true);
/* 2155:     */         }
/* 2156:2576 */         GenesisGetters.this.getFileSourceReader().readStory("Macbeth2sila.txt");
/* 2157:     */       }
/* 2158:2578 */       else if (source == GenesisGetters.this.macbethBasic)
/* 2159:     */       {
/* 2160:2579 */         GenesisGetters.this.getFileSourceReader().readStory("Macbeth1.txt");
/* 2161:     */       }
/* 2162:2581 */       else if (source == GenesisGetters.this.macbethWithEmotions)
/* 2163:     */       {
/* 2164:2582 */         GenesisGetters.this.getFileSourceReader().readStory("MacbethWithEmotions.txt");
/* 2165:     */       }
/* 2166:2584 */       else if (source == GenesisGetters.this.hamletRecall)
/* 2167:     */       {
/* 2168:2589 */         GenesisGetters.this.getFileSourceReader().readStory("Hamlet1.txt");
/* 2169:     */       }
/* 2170:2591 */       else if ((source == GenesisGetters.this.macbethPlusOnset) || (source == GenesisGetters.this.macbethTwoCulturesPlusOnset))
/* 2171:     */       {
/* 2172:2592 */         Switch.showOnsetSwitch.setSelected(true);
/* 2173:2593 */         GenesisGetters.this.setLeftPanel("Onsets");
/* 2174:2594 */         if (source == GenesisGetters.this.macbethPlusOnset) {
/* 2175:2595 */           GenesisGetters.this.getFileSourceReader().readStory("Macbeth1.txt");
/* 2176:     */         } else {
/* 2177:2598 */           GenesisGetters.this.getFileSourceReader().readStory("Macbeth2.txt");
/* 2178:     */         }
/* 2179:     */       }
/* 2180:2601 */       else if (source == GenesisGetters.this.teacherStudentItem)
/* 2181:     */       {
/* 2182:2602 */         GenesisGetters.this.getFileSourceReader().readStory("MacbethTeacherStudent.txt");
/* 2183:     */       }
/* 2184:2604 */       else if (source == GenesisGetters.this.macbethWithCulturalCompletion)
/* 2185:     */       {
/* 2186:2605 */         GenesisGetters.this.getFileSourceReader().readStory("Macbeth with gap.txt");
/* 2187:     */       }
/* 2188:2614 */       else if (source == GenesisGetters.this.mentalModelMacbethDemonstration)
/* 2189:     */       {
/* 2190:2615 */         GenesisGetters.this.getFileSourceReader().readStory("Traits-Macbeth.txt");
/* 2191:     */       }
/* 2192:2618 */       else if (source == GenesisGetters.this.test1Button)
/* 2193:     */       {
/* 2194:2619 */         GenesisGetters.this.getFileSourceReader().readStory("Traits-Macbeth.txt");
/* 2195:2620 */         new GenesisGetters.OpenReferenceFile(GenesisGetters.this, "Traits-Macbeth.pdf").start();
/* 2196:     */       }
/* 2197:2623 */       else if (source == GenesisGetters.this.macbethTwoCultures)
/* 2198:     */       {
/* 2199:2624 */         GenesisGetters.this.getFileSourceReader().readStory("Macbeth2.txt");
/* 2200:     */       }
/* 2201:2627 */       else if (source == GenesisGetters.this.test2Button)
/* 2202:     */       {
/* 2203:2628 */         GenesisGetters.this.getFileSourceReader().readStory("Macbeth2.txt");
/* 2204:2629 */         new GenesisGetters.OpenReferenceFile(GenesisGetters.this, "Macbeth2.pdf").start();
/* 2205:     */       }
/* 2206:2631 */       else if (source == GenesisGetters.this.whatIfMacbethDemonstration)
/* 2207:     */       {
/* 2208:2632 */         GenesisGetters.this.getFileSourceReader().readStory("WhatIf-Macbeth.txt");
/* 2209:     */       }
/* 2210:2634 */       else if ((source == GenesisGetters.this.macbethSummariesItem) || (source == GenesisGetters.this.macbethSummaryItem) || (source == GenesisGetters.this.estoniaSummariesItem) || (source == GenesisGetters.this.estoniaSummaryItem))
/* 2211:     */       {
/* 2212:2635 */         showUnconnectedElements();
/* 2213:2636 */         GenesisGetters.this.setRightPanel("Summary");
/* 2214:2637 */         Switch.summarizeViaPHW.setSelected(false);
/* 2215:2638 */         Switch.summarizeViaPHW.doClick();
/* 2216:2639 */         if (source == GenesisGetters.this.estoniaSummariesItem) {
/* 2217:2640 */           GenesisGetters.this.getFileSourceReader().readStory("Estonia2 summary.txt");
/* 2218:2642 */         } else if (source == GenesisGetters.this.estoniaSummaryItem) {
/* 2219:2643 */           GenesisGetters.this.getFileSourceReader().readStory("Estonia1 summary.txt");
/* 2220:2645 */         } else if (source == GenesisGetters.this.macbethSummaryItem) {
/* 2221:2646 */           GenesisGetters.this.getFileSourceReader().readStory("Macbeth1 summary.txt");
/* 2222:2648 */         } else if (source == GenesisGetters.this.macbethSummariesItem) {
/* 2223:2649 */           GenesisGetters.this.getFileSourceReader().readStory("Macbeth2 summary.txt");
/* 2224:     */         }
/* 2225:     */       }
/* 2226:2653 */       else if (source == GenesisGetters.this.luMurder)
/* 2227:     */       {
/* 2228:2654 */         showTextBox();
/* 2229:2655 */         GenesisGetters.this.setLeftPanel("Causal view");
/* 2230:2656 */         GenesisGetters.this.setRightPanel("Results");
/* 2231:2657 */         GenesisGetters.this.getFileSourceReader().readStory("Lu Murder Story.txt");
/* 2232:     */       }
/* 2233:2661 */       else if (source == GenesisGetters.this.mcIlvaneMurder)
/* 2234:     */       {
/* 2235:2662 */         showTextBox();
/* 2236:2663 */         GenesisGetters.this.setLeftPanel("Causal view");
/* 2237:2664 */         GenesisGetters.this.setRightPanel("Results");
/* 2238:     */         
/* 2239:2666 */         GenesisGetters.this.getFileSourceReader().readStory("McIlvane Murder Story.txt");
/* 2240:     */       }
/* 2241:2670 */       else if (source == GenesisGetters.this.primingQuestion)
/* 2242:     */       {
/* 2243:2671 */         showTextBox();
/* 2244:2672 */         Switch.showDisconnectedSwitch.setSelected(true);
/* 2245:     */         
/* 2246:2674 */         GenesisGetters.this.setRightPanel("Results");
/* 2247:     */         
/* 2248:2676 */         GenesisGetters.this.getFileSourceReader().readStory("Test.txt");
/* 2249:     */         
/* 2250:2678 */         GenesisGetters.this.suggestAfterReading("Try typing, \"Did John kill James because America is individualistic\".");
/* 2251:2679 */         GenesisGetters.this.getTextEntryBox().setText("Did John kill James because America is individualistic?");
/* 2252:     */       }
/* 2253:2681 */       else if (source == GenesisGetters.this.estonia)
/* 2254:     */       {
/* 2255:2682 */         GenesisGetters.this.getFileSourceReader().readStory("Estonia1.txt");
/* 2256:     */       }
/* 2257:2684 */       else if (source == GenesisGetters.this.estoniaWithEmotions)
/* 2258:     */       {
/* 2259:2685 */         GenesisGetters.this.getFileSourceReader().readStory("Estonia with emotions.txt");
/* 2260:     */       }
/* 2261:2687 */       else if (source == GenesisGetters.this.estoniaRussiaTwoSides)
/* 2262:     */       {
/* 2263:2688 */         Switch.showDisconnectedSwitch.setSelected(true);
/* 2264:2689 */         GenesisGetters.this.getFileSourceReader().readStory("Estonia2.txt");
/* 2265:     */       }
/* 2266:2691 */       else if (source == GenesisGetters.this.taliban)
/* 2267:     */       {
/* 2268:2692 */         GenesisGetters.this.getFileSourceReader().readStory("LostInTranslation.txt");
/* 2269:     */       }
/* 2270:2694 */       else if (source == GenesisGetters.this.estoniaGeorgia)
/* 2271:     */       {
/* 2272:2695 */         GenesisGetters.this.getFileSourceReader().readStory("GeorgiaEstonia.txt");
/* 2273:     */       }
/* 2274:2697 */       else if (source == GenesisGetters.this.tet)
/* 2275:     */       {
/* 2276:2698 */         Radio.alignmentButton.doClick();
/* 2277:2699 */         GenesisGetters.this.getFileSourceReader().readStory("tet.txt");
/* 2278:     */       }
/* 2279:2701 */       else if (source == GenesisGetters.this.macbeth2align)
/* 2280:     */       {
/* 2281:2702 */         Radio.alignmentButton.doClick();
/* 2282:2703 */         GenesisGetters.this.getFileSourceReader().readStory("Macbeth2.txt");
/* 2283:     */       }
/* 2284:2705 */       else if (source == GenesisGetters.this.maclionking)
/* 2285:     */       {
/* 2286:2706 */         GenesisGetters.this.getFileSourceReader().readStory("Mac lion king.txt");
/* 2287:     */       }
/* 2288:2708 */       else if (source == GenesisGetters.this.nearmisstraitdemo)
/* 2289:     */       {
/* 2290:2709 */         GenesisGetters.this.getFileSourceReader().readStory("near_miss_trait_learning.txt");
/* 2291:     */       }
/* 2292:2711 */       else if (source == GenesisGetters.this.manWhoLaughs)
/* 2293:     */       {
/* 2294:2712 */         showUnconnectedElements();
/* 2295:2713 */         GenesisGetters.this.getFileSourceReader().readStory("ManWhoLaughs3.txt");
/* 2296:     */       }
/* 2297:2715 */       else if (source == GenesisGetters.this.annaKarenina)
/* 2298:     */       {
/* 2299:2716 */         showUnconnectedElements();
/* 2300:2717 */         GenesisGetters.this.getFileSourceReader().readStory("anna.txt");
/* 2301:     */       }
/* 2302:2719 */       else if (source == GenesisGetters.this.hajiMurat)
/* 2303:     */       {
/* 2304:2720 */         showUnconnectedElements();
/* 2305:2721 */         GenesisGetters.this.getFileSourceReader().readStory("haji-murat-by-phw.txt");
/* 2306:     */       }
/* 2307:2723 */       else if (source == GenesisGetters.this.causesItem)
/* 2308:     */       {
/* 2309:2724 */         showUnconnectedElements();
/* 2310:2725 */         GenesisGetters.this.getFileSourceReader().readStory("causeExamples.txt");
/* 2311:     */       }
/* 2312:2740 */       else if (source == GenesisGetters.this.basicGeneratorItem)
/* 2313:     */       {
/* 2314:2741 */         GenesisGetters.this.setBottomPanel("Results");
/* 2315:2742 */         Generator.getGenerator().runBasicTests();
/* 2316:     */       }
/* 2317:2744 */       else if (source == GenesisGetters.this.advancedGeneratorItem)
/* 2318:     */       {
/* 2319:2745 */         GenesisGetters.this.setBottomPanel("Results");
/* 2320:2746 */         Generator.getGenerator().runSubordinateTests();
/* 2321:     */       }
/* 2322:     */       else
/* 2323:     */       {
/* 2324:2750 */         callRecordingMethod(source);
/* 2325:     */       }
/* 2326:     */     }
/* 2327:     */     
/* 2328:     */     private void callRecordingMethod(Object source)
/* 2329:     */     {
/* 2330:2755 */       if (source == GenesisGetters.this.recordMacbeth)
/* 2331:     */       {
/* 2332:2756 */         initializeRecording();
/* 2333:2757 */         Switch.showDisconnectedSwitch.setSelected(false);
/* 2334:2758 */         Switch.showDisconnectedSwitch.doClick();
/* 2335:2759 */         GenesisGetters.this.getFileSourceReader().readStory("Macbeth1.txt");
/* 2336:     */       }
/* 2337:2761 */       else if (source == GenesisGetters.this.recordMacbeth2)
/* 2338:     */       {
/* 2339:2762 */         initializeRecording();
/* 2340:2763 */         GenesisGetters.this.getFileSourceReader().readStory("Macbeth2.txt");
/* 2341:     */       }
/* 2342:2766 */       else if (source == GenesisGetters.this.recordEstonia2)
/* 2343:     */       {
/* 2344:2767 */         initializeRecording();
/* 2345:2768 */         GenesisGetters.this.getFileSourceReader().readStory("Estonia2.txt");
/* 2346:     */       }
/* 2347:2770 */       else if (source == GenesisGetters.this.recordSellOut2)
/* 2348:     */       {
/* 2349:2771 */         initializeRecording();
/* 2350:2772 */         GenesisGetters.this.getFileSourceReader().readStory("LostInTranslation.txt");
/* 2351:     */       }
/* 2352:2774 */       else if (source == GenesisGetters.this.recordMacbethWithQuestion)
/* 2353:     */       {
/* 2354:2775 */         initializeRecordingOfLeftAndBottom();
/* 2355:2776 */         showTextBox();
/* 2356:2777 */         GenesisGetters.this.getTextEntryBox().setText("Why did Macduff kill Macbeth?");
/* 2357:2778 */         GenesisGetters.this.setLeftPanel("Results");
/* 2358:2779 */         GenesisGetters.this.getFileSourceReader().readStory("Macbeth2.txt");
/* 2359:     */       }
/* 2360:2781 */       else if (source == GenesisGetters.this.recordMacbethWithMentalModels)
/* 2361:     */       {
/* 2362:2782 */         initializeRecording();
/* 2363:2783 */         GenesisGetters.this.getFileSourceReader().readStory("Traits-Macbeth.txt");
/* 2364:     */       }
/* 2365:2785 */       else if (source == GenesisGetters.this.recordLu)
/* 2366:     */       {
/* 2367:2786 */         initializeRecordingOfLeftAndBottom();
/* 2368:2787 */         showTextBox();
/* 2369:2788 */         GenesisGetters.this.setLeftPanel("Causal view");
/* 2370:     */         
/* 2371:2790 */         GenesisGetters.this.getFileSourceReader().readStory("Lu Murder Story.txt");
/* 2372:2791 */         GenesisGetters.this.suggestAfterReading(GenesisGetters.this.luStory);
/* 2373:2792 */         GenesisGetters.this.getTextEntryBox().setText("Did Lu kill Shan because America is individualistic?");
/* 2374:     */       }
/* 2375:     */     }
/* 2376:     */     
/* 2377:     */     private void showTextBox()
/* 2378:     */     {
/* 2379:2798 */       Switch.showTextEntryBox.setSelected(false);
/* 2380:2799 */       Switch.showTextEntryBox.doClick();
/* 2381:     */     }
/* 2382:     */     
/* 2383:     */     private void initializeRecording()
/* 2384:     */     {
/* 2385:2803 */       setToVideoRecordingDimensions(new Rectangle(0, 0, 1600, 1200));
/* 2386:2804 */       GenesisGetters.this.getNorthSouthSplitPane().setDividerLocation(0.0D);
/* 2387:     */       
/* 2388:2806 */       GenesisGetters.this.setBottomPanel("Elaboration graph");
/* 2389:2807 */       pause();
/* 2390:     */     }
/* 2391:     */     
/* 2392:     */     private void initializeRecordingOfLeftAndBottom()
/* 2393:     */     {
/* 2394:2811 */       setToVideoRecordingDimensions(new Rectangle(0, 0, 1600, 1200));
/* 2395:2812 */       GenesisGetters.this.getNorthSouthSplitPane().setDividerLocation(0.3D);
/* 2396:2813 */       GenesisGetters.this.getSplitPane().setDividerLocation(1.0D);
/* 2397:2814 */       GenesisGetters.this.setBottomPanel("Elaboration graph");
/* 2398:2815 */       pause();
/* 2399:     */     }
/* 2400:     */     
/* 2401:     */     private void pause()
/* 2402:     */     {
/* 2403:     */       try
/* 2404:     */       {
/* 2405:2820 */         Thread.sleep(3000L);
/* 2406:     */       }
/* 2407:     */       catch (InterruptedException localInterruptedException) {}
/* 2408:     */     }
/* 2409:     */     
/* 2410:     */     public void showUnconnectedElements()
/* 2411:     */     {
/* 2412:2827 */       Switch.showDisconnectedSwitch.setSelected(false);
/* 2413:2828 */       Switch.showDisconnectedSwitch.doClick();
/* 2414:     */     }
/* 2415:     */     
/* 2416:     */     private void prepareSwitchesForTest()
/* 2417:     */     {
/* 2418:2832 */       Switch.showTextEntryBox.setSelected(false);
/* 2419:2833 */       Switch.useStartCache.setSelected(false);
/* 2420:2834 */       Switch.useWordnetCache.setSelected(false);
/* 2421:2835 */       Switch.useStartBeta.setSelected(false);
/* 2422:2836 */       Switch.testStartBeta.setSelected(false);
/* 2423:     */     }
/* 2424:     */     
/* 2425:     */     private void setToVideoRecordingDimensions(Rectangle r)
/* 2426:     */     {
/* 2427:2840 */       Mark.say(
/* 2428:     */       
/* 2429:     */ 
/* 2430:     */ 
/* 2431:2844 */         new Object[] { "Adjusting size" });ABasicFrame.getFrame().setBounds(r);ABasicFrame.getFrame().validate();
/* 2432:     */     }
/* 2433:     */   }
/* 2434:     */   
/* 2435:     */   class OpenReferenceFile
/* 2436:     */     extends Thread
/* 2437:     */   {
/* 2438:     */     String string;
/* 2439:     */     
/* 2440:     */     public OpenReferenceFile(String string)
/* 2441:     */     {
/* 2442:2852 */       this.string = string;
/* 2443:     */     }
/* 2444:     */     
/* 2445:     */     public void run() {}
/* 2446:     */   }
/* 2447:     */   
/* 2448:     */   private void suggestAfterReading(String suggestion)
/* 2449:     */   {
/* 2450:2863 */     Mark.say(
/* 2451:     */     
/* 2452:2865 */       new Object[] { "Entering suggestAfterReading" });new Suggest(suggestion).start();
/* 2453:     */   }
/* 2454:     */   
/* 2455:     */   class Suggest
/* 2456:     */     extends Thread
/* 2457:     */   {
/* 2458:     */     String suggestion;
/* 2459:     */     
/* 2460:     */     public Suggest(String s)
/* 2461:     */     {
/* 2462:2871 */       this.suggestion = s;
/* 2463:     */     }
/* 2464:     */     
/* 2465:     */     public void run()
/* 2466:     */     {
/* 2467:2875 */       if (GenesisGetters.this.getFileSourceReader().getTheReaderThread() != null) {
/* 2468:     */         try
/* 2469:     */         {
/* 2470:2877 */           Mark.say(new Object[] {"Pausing..." });
/* 2471:2878 */           GenesisGetters.this.getFileSourceReader().getTheReaderThread().join();
/* 2472:2879 */           Mark.say(new Object[] {"Going..." });
/* 2473:2880 */           GenesisGetters.this.getResultContainer().process(new BetterSignal(new Object[] { "Suggestion", "clear" }));
/* 2474:     */           
/* 2475:2882 */           GenesisGetters.this.getResultContainer().process(new BetterSignal(new Object[] { "Suggestion", this.suggestion }));
/* 2476:     */         }
/* 2477:     */         catch (InterruptedException e)
/* 2478:     */         {
/* 2479:2886 */           e.printStackTrace();
/* 2480:     */         }
/* 2481:     */       }
/* 2482:     */     }
/* 2483:     */   }
/* 2484:     */   
/* 2485:     */   class TestSwitchListener
/* 2486:     */     implements ActionListener
/* 2487:     */   {
/* 2488:     */     TestSwitchListener() {}
/* 2489:     */     
/* 2490:     */     public void actionPerformed(ActionEvent event)
/* 2491:     */     {
/* 2492:2895 */       Object source = event.getSource();
/* 2493:2896 */       if (source == Switch.useStartBeta)
/* 2494:     */       {
/* 2495:2897 */         if (Switch.useStartBeta.isSelected())
/* 2496:     */         {
/* 2497:2898 */           Mark.say(new Object[] {"Use" });
/* 2498:2899 */           Switch.useStartServer.setSelected(false);
/* 2499:2900 */           Switch.useStartCache.setSelected(false);
/* 2500:     */         }
/* 2501:     */         else
/* 2502:     */         {
/* 2503:2903 */           Switch.useStartServer.setSelected(true);
/* 2504:2904 */           Switch.useStartCache.setSelected(true);
/* 2505:2905 */           Switch.testStartBeta.setSelected(false);
/* 2506:     */         }
/* 2507:     */       }
/* 2508:2908 */       else if ((source == Switch.testStartBeta) && 
/* 2509:2909 */         (Switch.testStartBeta.isSelected()))
/* 2510:     */       {
/* 2511:2910 */         Mark.say(new Object[] {"Test" });
/* 2512:2911 */         Switch.useStartBeta.setSelected(true);
/* 2513:2912 */         Switch.useStartServer.setSelected(false);
/* 2514:2913 */         Switch.useStartCache.setSelected(false);
/* 2515:     */       }
/* 2516:     */     }
/* 2517:     */   }
/* 2518:     */   
/* 2519:     */   class GeneralPurposeListener
/* 2520:     */     implements ActionListener
/* 2521:     */   {
/* 2522:     */     Component component;
/* 2523:     */     
/* 2524:     */     public GeneralPurposeListener(Component component)
/* 2525:     */     {
/* 2526:2923 */       this.component = component;
/* 2527:     */     }
/* 2528:     */     
/* 2529:     */     public void actionPerformed(ActionEvent event)
/* 2530:     */     {
/* 2531:2927 */       Object source = event.getSource();
/* 2532:2928 */       FileReaderPanel fileReaderPanel = GenesisGetters.this.getFileReaderFrame();
/* 2533:     */       
/* 2534:2930 */       Switch.showOnsetSwitch.setSelected(false);
/* 2535:2932 */       if (source == GenesisGetters.this.printMenuItem)
/* 2536:     */       {
/* 2537:2933 */         GenesisGetters.this.printMe();
/* 2538:     */       }
/* 2539:2938 */       else if (source == Radio.alignmentButton)
/* 2540:     */       {
/* 2541:2939 */         Mark.say(new Object[] {"Hello Patrick" });
/* 2542:     */       }
/* 2543:2945 */       else if (source == Switch.showTextEntryBox)
/* 2544:     */       {
/* 2545:2946 */         if (!Switch.showTextEntryBox.isSelected()) {
/* 2546:2947 */           GenesisGetters.this.getTextEntryBox().zero();
/* 2547:     */         } else {
/* 2548:2950 */           GenesisGetters.this.getTextEntryBox().normal();
/* 2549:     */         }
/* 2550:2952 */         GenesisGetters.this.revalidate();
/* 2551:2953 */         GenesisGetters.this.getTextEntryBox().revalidate();
/* 2552:     */       }
/* 2553:2955 */       else if (source == GenesisGetters.this.contributorMenuItem)
/* 2554:     */       {
/* 2555:2956 */         showContributors();
/* 2556:     */       }
/* 2557:2958 */       else if (source == GenesisGetters.this.experienceButton)
/* 2558:     */       {
/* 2559:2959 */         Switch.disambiguatorSwitch.setSelected(false);
/* 2560:2960 */         GenesisGetters.memorySwitch.setSelected(false);
/* 2561:2961 */         GenesisGetters.this.getMovieManager().loadMovieDescriptions();
/* 2562:2962 */         GenesisGetters.this.experienceButton.setBackground(GenesisGetters.this.greenish);
/* 2563:2963 */         GenesisGetters.memorySwitch.setSelected(true);
/* 2564:     */         
/* 2565:2965 */         Switch.useSpeechCheckBox.setSelected(true);
/* 2566:     */       }
/* 2567:2967 */       else if (source == GenesisGetters.this.disambiguationButton)
/* 2568:     */       {
/* 2569:2968 */         String file = new DemoAnchor().get("disambiguation.txt");
/* 2570:2969 */         File selected = new File(file);
/* 2571:2970 */         GenesisGetters.memorySwitch.setSelected(true);
/* 2572:2971 */         if (selected.exists())
/* 2573:     */         {
/* 2574:2973 */           GenesisGetters.this.getFileSourceReader().readStory(selected);
/* 2575:2974 */           GenesisGetters.this.disambiguationButton.setBackground(GenesisGetters.this.greenish);
/* 2576:     */         }
/* 2577:2976 */         Switch.disambiguatorSwitch.setSelected(true);
/* 2578:2977 */         GenesisGetters.this.getNewDisambiguator().flushLibrary("");
/* 2579:     */       }
/* 2580:2979 */       else if (source == GenesisGetters.this.wordnetPurgeButton)
/* 2581:     */       {
/* 2582:2980 */         BundleGenerator.purgeWordnetCache();
/* 2583:2981 */         BundleGenerator.writeWordnetCache();
/* 2584:     */       }
/* 2585:2983 */       else if (source == GenesisGetters.this.startPurgeButton)
/* 2586:     */       {
/* 2587:2984 */         Start.purgeStartCache();
/* 2588:     */       }
/* 2589:2986 */       else if (source == GenesisGetters.this.clearSummaryTableButton)
/* 2590:     */       {
/* 2591:2987 */         Summarizer.getSummarizer().initializeTable();
/* 2592:     */       }
/* 2593:3010 */       else if (source == GenesisGetters.this.genesisStories)
/* 2594:     */       {
/* 2595:3011 */         Mark.say(new Object[] {"Switch to genesis stories" });
/* 2596:3012 */         GenesisGetters.this.setBottomPanel("Elaboration graph");
/* 2597:3013 */         GenesisGetters.this.setRightPanel("Sources");
/* 2598:     */       }
/* 2599:3017 */       else if (source == GenesisGetters.this.readDirectoryItem)
/* 2600:     */       {
/* 2601:3018 */         Mark.say(new Object[] {"Entering readDirectory listener" });
/* 2602:3019 */         String directory = Preferences.userRoot().get("StoryRoot", "c:/");
/* 2603:3020 */         Mark.say(new Object[] {"Preferred directory is", directory });
/* 2604:3021 */         JFileChooser chooser = fileReaderPanel.getFileChooser(directory);
/* 2605:3022 */         chooser.setFileSelectionMode(1);
/* 2606:3023 */         int returnVal = chooser.showOpenDialog(this.component);
/* 2607:3024 */         if (returnVal == 0)
/* 2608:     */         {
/* 2609:3025 */           File selected = chooser.getSelectedFile();
/* 2610:3026 */           Mark.say(new Object[] {"You chose to use this directory as the root: " + selected.getAbsolutePath() });
/* 2611:3027 */           Preferences.userRoot().put("StoryRoot", selected.getAbsolutePath());
/* 2612:     */         }
/* 2613:     */         else
/* 2614:     */         {
/* 2615:3030 */           System.out.println("You did not chose to select a root directory");
/* 2616:     */         }
/* 2617:     */       }
/* 2618:3033 */       else if (source == GenesisGetters.this.readStoryItem)
/* 2619:     */       {
/* 2620:3034 */         Mark.say(new Object[] {"Entering readStory listener" });
/* 2621:3035 */         String defaultDirectory = Preferences.userRoot().get("StoryRoot", "C:/");
/* 2622:3036 */         String path = Preferences.userRoot().get("StoryName", defaultDirectory);
/* 2623:3037 */         Mark.say(new Object[] {"Preferred story is", path });
/* 2624:3038 */         JFileChooser chooser = fileReaderPanel.getFileChooser(path);
/* 2625:3039 */         chooser.setFileSelectionMode(0);
/* 2626:3040 */         int returnVal = chooser.showOpenDialog(this.component);
/* 2627:3041 */         if (returnVal == 0)
/* 2628:     */         {
/* 2629:3042 */           File selected = conditionFileName(chooser.getSelectedFile());
/* 2630:     */           
/* 2631:3044 */           GenesisGetters.this.selectedFile = selected;
/* 2632:     */           
/* 2633:3046 */           Mark.say(new Object[] {"You chose to open this file: " + selected.getName() });
/* 2634:     */           
/* 2635:3048 */           Preferences.userRoot().put("StoryName", selected.getAbsolutePath());
/* 2636:3049 */           if (selected.exists())
/* 2637:     */           {
/* 2638:3050 */             GenesisGetters.this.getFileSourceReader().readStory(selected);
/* 2639:     */           }
/* 2640:     */           else
/* 2641:     */           {
/* 2642:3053 */             System.err.println(selected + " does not exist!");
/* 2643:3054 */             fileReaderPanel.setState(FileReaderPanel.stopped);
/* 2644:     */           }
/* 2645:     */         }
/* 2646:     */         else
/* 2647:     */         {
/* 2648:3058 */           System.out.println("You did not chose to open a file");
/* 2649:     */         }
/* 2650:     */       }
/* 2651:3061 */       else if (source == GenesisGetters.this.focusButton)
/* 2652:     */       {
/* 2653:3062 */         selectFocusDirectory();
/* 2654:     */       }
/* 2655:3065 */       else if (source == GenesisGetters.this.eraseTextButton)
/* 2656:     */       {
/* 2657:3066 */         GenesisGetters.this.getSourceContainer().clear();
/* 2658:3067 */         GenesisGetters.this.getResultContainer().clear();
/* 2659:3068 */         GenesisGetters.this.getConceptContainer().clear();
/* 2660:     */       }
/* 2661:3071 */       else if (source == GenesisGetters.this.clearMemoryButton)
/* 2662:     */       {
/* 2663:3076 */         System.err.println("ERROR: clear button not yet supported with the new memory -- talk to Sam");
/* 2664:     */         
/* 2665:     */ 
/* 2666:3079 */         GenesisGetters.this.getCauseExpert().clearRuleMemory();
/* 2667:     */       }
/* 2668:     */     }
/* 2669:     */     
/* 2670:3085 */     private boolean initializeMindsEye = false;
/* 2671:     */     
/* 2672:     */     private File conditionFileName(File file)
/* 2673:     */     {
/* 2674:3092 */       String name = file.getPath();
/* 2675:3093 */       if (name.indexOf('.') < 0)
/* 2676:     */       {
/* 2677:3094 */         name = name + ".txt";
/* 2678:3095 */         return new File(name);
/* 2679:     */       }
/* 2680:3097 */       return file;
/* 2681:     */     }
/* 2682:     */     
/* 2683:     */     private void selectFocusDirectory()
/* 2684:     */     {
/* 2685:     */       try
/* 2686:     */       {
/* 2687:3105 */         Vector<MovieDescription> descriptions = new Vector();
/* 2688:3106 */         if (GenesisGetters.this.getMovieManager().getMovieDescriptions() == null)
/* 2689:     */         {
/* 2690:3107 */           GenesisGetters.this.focusButton.setEnabled(false);
/* 2691:3108 */           return;
/* 2692:     */         }
/* 2693:3110 */         for (MovieDescription d : GenesisGetters.this.getMovieManager().getMovieDescriptions()) {
/* 2694:3111 */           if (d.getUrl() != null) {
/* 2695:3112 */             descriptions.add(d);
/* 2696:     */           }
/* 2697:     */         }
/* 2698:3115 */         JList list = new JList(descriptions);
/* 2699:3116 */         int height = GenesisGetters.this.getHeight();
/* 2700:3117 */         int width = GenesisGetters.this.getWidth();
/* 2701:3118 */         Dimension d = list.getPreferredSize();
/* 2702:3119 */         Popup popup = PopupFactory.getSharedInstance().getPopup(GenesisGetters.this, list, (width - d.width) / 2, (height - d.height) / 2);
/* 2703:3120 */         list.addListSelectionListener(new MovieSelectionListener(descriptions, popup));
/* 2704:3121 */         Mark.c(new Object[] {list });
/* 2705:3122 */         popup.show();
/* 2706:     */       }
/* 2707:     */       catch (RuntimeException e)
/* 2708:     */       {
/* 2709:3125 */         e.printStackTrace();
/* 2710:     */       }
/* 2711:     */     }
/* 2712:     */     
/* 2713:     */     class MovieSelectionListener
/* 2714:     */       implements ListSelectionListener
/* 2715:     */     {
/* 2716:     */       Vector<MovieDescription> movieDescriptions;
/* 2717:     */       Popup popup;
/* 2718:     */       
/* 2719:     */       public MovieSelectionListener(Popup descriptions)
/* 2720:     */       {
/* 2721:3138 */         this.movieDescriptions = descriptions;
/* 2722:3139 */         this.popup = popup;
/* 2723:     */       }
/* 2724:     */       
/* 2725:     */       public void valueChanged(ListSelectionEvent event)
/* 2726:     */       {
/* 2727:3144 */         if (event.getValueIsAdjusting()) {
/* 2728:3145 */           return;
/* 2729:     */         }
/* 2730:3147 */         int index = event.getFirstIndex();
/* 2731:3148 */         MovieDescription description = (MovieDescription)this.movieDescriptions.get(index);
/* 2732:3149 */         this.popup.hide();
/* 2733:3150 */         GenesisGetters.this.getLightBulbViewer().setMovie(description.getUrl());
/* 2734:     */       }
/* 2735:     */     }
/* 2736:     */     
/* 2737:     */     private void showContributors()
/* 2738:     */     {
/* 2739:3155 */       String s = "";
/* 2740:3156 */       s = s + "<html><center>";
/* 2741:3157 */       s = s + GenesisGetters.this.contributors;
/* 2742:3158 */       s = s + "</center></html>";
/* 2743:3159 */       JLabel label = new JLabel(s);
/* 2744:3160 */       JOptionPane.showMessageDialog(GenesisGetters.this, label, "Contributors", 1);
/* 2745:     */     }
/* 2746:     */   }
/* 2747:     */   
/* 2748:3165 */   String contributors = "Patrick Winston, ";
/* 2749:     */   
/* 2750:     */   private void addContributor(String name, String contribution)
/* 2751:     */   {
/* 2752:3170 */     this.contributors = (this.contributors + name + ", ");
/* 2753:     */   }
/* 2754:     */   
/* 2755:     */   private void addContributor(String name)
/* 2756:     */   {
/* 2757:3174 */     addContributor(name, "");
/* 2758:     */   }
/* 2759:     */   
/* 2760:     */   private void computeContributors()
/* 2761:     */   {
/* 2762:3178 */     addContributor("Hiba Awad", "Morris-Peng stories and analysis");
/* 2763:3179 */     addContributor("Jake Beal", "Let's do it");
/* 2764:3180 */     addContributor("Adam Belay", "Onset of plot units");
/* 2765:3181 */     addContributor("Rachel Chaney", "Pictures");
/* 2766:3182 */     addContributor("Harold Cooper", "Syntax to semantics");
/* 2767:3183 */     addBreak();
/* 2768:3184 */     addContributor("Matthew Fay", "Alignment");
/* 2769:     */     
/* 2770:3186 */     addContributor("Mark Finlayson", "Infrastructure");
/* 2771:3187 */     addContributor("Sam Glidden", "Memory");
/* 2772:3188 */     addContributor("Mike Klein", "Syntax to semantics");
/* 2773:3189 */     addContributor("Adam Kraft", "Syntax to semantics");
/* 2774:     */     
/* 2775:3191 */     addBreak();
/* 2776:3192 */     addContributor("Caryn Krakauer", "Story comparison using concepts");
/* 2777:     */     
/* 2778:3194 */     addContributor("Benjamin Lamothe", "Representations");
/* 2779:3195 */     addContributor("Tom Larsen", "Disambiguation");
/* 2780:3196 */     addContributor("Capen Low", "Story understanding");
/* 2781:3197 */     addContributor("Марина Морозова", "Classics stories");
/* 2782:3198 */     addContributor("David Nackoul", "Plot unit translation");
/* 2783:3199 */     addBreak();
/* 2784:3200 */     addContributor("Sila Sayan", "Story telling");
/* 2785:     */     
/* 2786:3202 */     addContributor("Susan Song", "Personality traits");
/* 2787:3203 */     addContributor("Erek Speed", "Visual stories (the cat problem)");
/* 2788:3204 */     addContributor("Kevin White", "Graphics panels");
/* 2789:     */     
/* 2790:3206 */     addContributor("and others.");
/* 2791:     */   }
/* 2792:     */   
/* 2793:     */   private void addBreak()
/* 2794:     */   {
/* 2795:3210 */     this.contributors += "<br/>";
/* 2796:     */   }
/* 2797:     */   
/* 2798:     */   public JMenuBar getMenuBar()
/* 2799:     */   {
/* 2800:3215 */     GeneralPurposeListener gListener = new GeneralPurposeListener(this);
/* 2801:     */     
/* 2802:3217 */     DemonstrationListener dListener = new DemonstrationListener(this);
/* 2803:     */     
/* 2804:     */ 
/* 2805:3220 */     this.readStoryItem.addActionListener(gListener);
/* 2806:3221 */     this.readDirectoryItem.addActionListener(gListener);
/* 2807:     */     
/* 2808:3223 */     this.macbethTwoCultures.addActionListener(dListener);
/* 2809:3224 */     this.macbethWithCulturalCompletion.addActionListener(dListener);
/* 2810:     */     
/* 2811:     */ 
/* 2812:3227 */     this.mentalModelMacbethDemonstration.addActionListener(dListener);
/* 2813:3228 */     this.whatIfMacbethDemonstration.addActionListener(dListener);
/* 2814:3229 */     this.macbethWithEmotions.addActionListener(dListener);
/* 2815:     */     
/* 2816:3231 */     this.luMurder.addActionListener(dListener);
/* 2817:3232 */     this.mcIlvaneMurder.addActionListener(dListener);
/* 2818:3233 */     this.primingQuestion.addActionListener(dListener);
/* 2819:     */     
/* 2820:3235 */     this.macbethBasic.addActionListener(dListener);
/* 2821:3236 */     this.hamletRecall.addActionListener(dListener);
/* 2822:3237 */     this.macbethPlusOnset.addActionListener(dListener);
/* 2823:3238 */     this.macbethTwoCulturesPlusOnset.addActionListener(dListener);
/* 2824:     */     
/* 2825:3240 */     this.hamlet.addActionListener(dListener);
/* 2826:3241 */     this.caesar.addActionListener(dListener);
/* 2827:3242 */     this.hamletCaesar.addActionListener(dListener);
/* 2828:3243 */     this.annaKarenina.addActionListener(dListener);
/* 2829:3244 */     this.hajiMurat.addActionListener(dListener);
/* 2830:3245 */     this.manWhoLaughs.addActionListener(dListener);
/* 2831:     */     
/* 2832:3247 */     this.estonia.addActionListener(dListener);
/* 2833:3248 */     this.estoniaWithEmotions.addActionListener(dListener);
/* 2834:3249 */     this.estoniaRussiaTwoSides.addActionListener(dListener);
/* 2835:3250 */     this.estoniaGeorgia.addActionListener(dListener);
/* 2836:3251 */     this.taliban.addActionListener(dListener);
/* 2837:3252 */     this.tet.addActionListener(dListener);
/* 2838:3253 */     this.macbeth2align.addActionListener(dListener);
/* 2839:3254 */     this.maclionking.addActionListener(dListener);
/* 2840:3255 */     this.nearmisstraitdemo.addActionListener(dListener);
/* 2841:     */     
/* 2842:3257 */     this.basicGeneratorItem.addActionListener(dListener);
/* 2843:3258 */     this.advancedGeneratorItem.addActionListener(dListener);
/* 2844:     */     
/* 2845:3260 */     this.teacherStudentItem.addActionListener(dListener);
/* 2846:     */     
/* 2847:3262 */     this.spoonMacbethItem.addActionListener(dListener);
/* 2848:3263 */     this.explainMacbethItem.addActionListener(dListener);
/* 2849:3264 */     this.teachMacbethItem.addActionListener(dListener);
/* 2850:     */     
/* 2851:3266 */     this.macbethSummaryItem.addActionListener(dListener);
/* 2852:3267 */     this.macbethSummariesItem.addActionListener(dListener);
/* 2853:3268 */     this.estoniaSummariesItem.addActionListener(dListener);
/* 2854:3269 */     this.estoniaSummaryItem.addActionListener(dListener);
/* 2855:     */     
/* 2856:3271 */     this.threeStoryItem.addActionListener(dListener);
/* 2857:3272 */     this.fiveStoryItem.addActionListener(dListener);
/* 2858:3273 */     this.tenStoryItem.addActionListener(dListener);
/* 2859:3274 */     this.fifteenStoryItem.addActionListener(dListener);
/* 2860:     */     
/* 2861:3276 */     this.test1Button.addActionListener(dListener);
/* 2862:3277 */     this.test2Button.addActionListener(dListener);
/* 2863:     */     
/* 2864:3279 */     this.causesItem.addActionListener(dListener);
/* 2865:     */     
/* 2866:3281 */     this.genesisStories.addActionListener(gListener);
/* 2867:     */     
/* 2868:     */ 
/* 2869:3284 */     this.printMenuItem.addActionListener(gListener);
/* 2870:3285 */     this.loadAnnotationsItem.addActionListener(gListener);
/* 2871:     */     
/* 2872:3287 */     this.demonstrationMenu.add(this.personalityMenu);
/* 2873:3288 */     this.demonstrationMenu.add(this.emotionMenu);
/* 2874:3289 */     this.demonstrationMenu.add(this.whatIfMenu);
/* 2875:3290 */     this.demonstrationMenu.add(this.eastWestMenu);
/* 2876:     */     
/* 2877:3292 */     this.demonstrationMenu.add(this.shakespeareMenu);
/* 2878:     */     
/* 2879:3294 */     this.demonstrationMenu.add(this.classicsMenu);
/* 2880:     */     
/* 2881:3296 */     this.demonstrationMenu.add(this.conflictsMenu);
/* 2882:     */     
/* 2883:     */ 
/* 2884:     */ 
/* 2885:     */ 
/* 2886:     */ 
/* 2887:3302 */     this.demonstrationMenu.add(this.gapsMenu);
/* 2888:3303 */     this.demonstrationMenu.add(this.plotGenerationMenu);
/* 2889:     */     
/* 2890:     */ 
/* 2891:3306 */     this.personalityMenu.add(this.mentalModelMacbethDemonstration);
/* 2892:     */     
/* 2893:3308 */     this.whatIfMenu.add(this.whatIfMacbethDemonstration);
/* 2894:     */     
/* 2895:3310 */     this.emotionMenu.add(this.macbethWithEmotions);
/* 2896:3311 */     this.emotionMenu.add(this.estoniaWithEmotions);
/* 2897:     */     
/* 2898:3313 */     this.eastWestMenu.add(this.luMurder);
/* 2899:3314 */     this.eastWestMenu.add(this.mcIlvaneMurder);
/* 2900:     */     
/* 2901:     */ 
/* 2902:3317 */     this.tellStoryMenu.add(this.teacherStudentItem);
/* 2903:3318 */     this.tellStoryMenu.add(this.spoonMacbethItem);
/* 2904:3319 */     this.tellStoryMenu.add(this.explainMacbethItem);
/* 2905:3320 */     this.tellStoryMenu.add(this.teachMacbethItem);
/* 2906:     */     
/* 2907:3322 */     this.demonstrationMenu.add(this.tellStoryMenu);
/* 2908:     */     
/* 2909:3324 */     this.summaryStoryMenu.add(this.macbethSummaryItem);
/* 2910:3325 */     this.summaryStoryMenu.add(this.macbethSummariesItem);
/* 2911:3326 */     this.summaryStoryMenu.add(this.estoniaSummaryItem);
/* 2912:3327 */     this.summaryStoryMenu.add(this.estoniaSummariesItem);
/* 2913:     */     
/* 2914:3329 */     this.demonstrationMenu.add(this.summaryStoryMenu);
/* 2915:     */     
/* 2916:3331 */     this.similarityMenu.add(this.threeStoryItem);
/* 2917:3332 */     this.similarityMenu.add(this.fiveStoryItem);
/* 2918:3333 */     this.similarityMenu.add(this.tenStoryItem);
/* 2919:3334 */     this.similarityMenu.add(this.fifteenStoryItem);
/* 2920:3336 */     if (!Webstart.isWebStart()) {
/* 2921:3337 */       this.demonstrationMenu.add(this.similarityMenu);
/* 2922:     */     }
/* 2923:3340 */     this.featuresMenu.add(this.causesItem);
/* 2924:     */     
/* 2925:3342 */     this.generatorMenu.add(this.basicGeneratorItem);
/* 2926:3343 */     this.generatorMenu.add(this.advancedGeneratorItem);
/* 2927:     */     
/* 2928:     */ 
/* 2929:     */ 
/* 2930:3347 */     this.demonstrationMenu.add(this.featuresMenu);
/* 2931:     */     
/* 2932:3349 */     this.demonstrationMenu.add(this.generatorMenu);
/* 2933:     */     
/* 2934:3351 */     this.shakespeareMenu.add(this.macbethBasic);
/* 2935:3352 */     this.shakespeareMenu.add(this.macbethTwoCultures);
/* 2936:3353 */     this.shakespeareMenu.add(this.macbethPlusOnset);
/* 2937:3354 */     this.shakespeareMenu.add(this.macbethTwoCulturesPlusOnset);
/* 2938:     */     
/* 2939:     */ 
/* 2940:     */ 
/* 2941:3358 */     this.shakespeareMenu.add(this.hamletRecall);
/* 2942:3359 */     this.shakespeareMenu.add(this.hamlet);
/* 2943:3360 */     this.shakespeareMenu.add(this.caesar);
/* 2944:3361 */     this.shakespeareMenu.add(this.hamletCaesar);
/* 2945:     */     
/* 2946:3363 */     this.classicsMenu.add(this.manWhoLaughs);
/* 2947:3364 */     this.classicsMenu.add(this.annaKarenina);
/* 2948:3365 */     this.classicsMenu.add(this.hajiMurat);
/* 2949:     */     
/* 2950:3367 */     this.conflictsMenu.add(this.estonia);
/* 2951:3368 */     this.conflictsMenu.add(this.estoniaRussiaTwoSides);
/* 2952:3369 */     this.conflictsMenu.add(this.taliban);
/* 2953:3370 */     this.conflictsMenu.add(this.estoniaGeorgia);
/* 2954:     */     
/* 2955:     */ 
/* 2956:     */ 
/* 2957:     */ 
/* 2958:     */ 
/* 2959:3376 */     this.gapsMenu.add(this.tet);
/* 2960:3377 */     this.gapsMenu.add(this.macbeth2align);
/* 2961:     */     
/* 2962:3379 */     this.plotGenerationMenu.add(this.maclionking);
/* 2963:3380 */     this.plotGenerationMenu.add(this.nearmisstraitdemo);
/* 2964:     */     
/* 2965:     */ 
/* 2966:     */ 
/* 2967:     */ 
/* 2968:     */ 
/* 2969:     */ 
/* 2970:     */ 
/* 2971:3388 */     this.readMenu.add(this.readStoryItem);
/* 2972:3389 */     this.readMenu.add(this.readStoryItem);
/* 2973:3390 */     this.readMenu.add(this.readDirectoryItem);
/* 2974:3391 */     this.menuBar.add(this.demonstrationMenu);
/* 2975:3392 */     this.menuBar.add(this.readMenu);
/* 2976:     */     
/* 2977:3394 */     this.recordMacbeth.addActionListener(dListener);
/* 2978:3395 */     this.recordMacbeth2.addActionListener(dListener);
/* 2979:3396 */     this.recordMacbethWithQuestion.addActionListener(dListener);
/* 2980:3397 */     this.recordMacbethWithMentalModels.addActionListener(dListener);
/* 2981:3398 */     this.recordEstonia2.addActionListener(dListener);
/* 2982:3399 */     this.recordSellOut2.addActionListener(dListener);
/* 2983:3400 */     this.recordLu.addActionListener(dListener);
/* 2984:     */     
/* 2985:3402 */     this.recordMenu.add(this.recordMacbeth);
/* 2986:3403 */     this.recordMenu.add(this.recordMacbeth2);
/* 2987:3404 */     this.recordMenu.add(this.recordMacbethWithQuestion);
/* 2988:3405 */     this.recordMenu.add(this.recordMacbethWithMentalModels);
/* 2989:3406 */     this.recordMenu.add(this.recordEstonia2);
/* 2990:3407 */     this.recordMenu.add(this.recordSellOut2);
/* 2991:3408 */     this.recordMenu.add(this.recordLu);
/* 2992:     */     
/* 2993:3410 */     this.menuBar.add(this.recordMenu);
/* 2994:3413 */     if (Webstart.isWebStart()) {
/* 2995:     */       try
/* 2996:     */       {
/* 2997:3417 */         URL url = new URL("http://people.csail.mit.edu/phw/genesis-runs.html");
/* 2998:3418 */         JEditorPane pane = new JEditorPane(url);
/* 2999:3419 */         getMainMatrix().add(pane, 0, 0, 1, 1);
/* 3000:     */       }
/* 3001:     */       catch (MalformedURLException e)
/* 3002:     */       {
/* 3003:3426 */         e.printStackTrace();
/* 3004:     */       }
/* 3005:     */       catch (IOException e)
/* 3006:     */       {
/* 3007:3429 */         e.printStackTrace();
/* 3008:     */       }
/* 3009:     */       catch (Exception e)
/* 3010:     */       {
/* 3011:3432 */         e.printStackTrace();
/* 3012:     */       }
/* 3013:     */     }
/* 3014:3462 */     Switch.showTextEntryBox.addActionListener(gListener);
/* 3015:3463 */     Radio.alignmentButton.addActionListener(gListener);
/* 3016:     */     
/* 3017:     */ 
/* 3018:     */ 
/* 3019:     */ 
/* 3020:     */ 
/* 3021:     */ 
/* 3022:     */ 
/* 3023:     */ 
/* 3024:     */ 
/* 3025:     */ 
/* 3026:     */ 
/* 3027:     */ 
/* 3028:3476 */     this.menuBar.add(this.aboutMenu);
/* 3029:3477 */     this.aboutMenu.add(this.contributorMenuItem);
/* 3030:3478 */     this.contributorMenuItem.addActionListener(gListener);
/* 3031:     */     
/* 3032:     */ 
/* 3033:     */ 
/* 3034:     */ 
/* 3035:     */ 
/* 3036:     */ 
/* 3037:     */ 
/* 3038:     */ 
/* 3039:     */ 
/* 3040:     */ 
/* 3041:     */ 
/* 3042:     */ 
/* 3043:     */ 
/* 3044:     */ 
/* 3045:     */ 
/* 3046:     */ 
/* 3047:     */ 
/* 3048:     */ 
/* 3049:     */ 
/* 3050:     */ 
/* 3051:     */ 
/* 3052:     */ 
/* 3053:     */ 
/* 3054:     */ 
/* 3055:     */ 
/* 3056:     */ 
/* 3057:     */ 
/* 3058:     */ 
/* 3059:     */ 
/* 3060:     */ 
/* 3061:     */ 
/* 3062:     */ 
/* 3063:     */ 
/* 3064:     */ 
/* 3065:     */ 
/* 3066:     */ 
/* 3067:     */ 
/* 3068:     */ 
/* 3069:     */ 
/* 3070:     */ 
/* 3071:     */ 
/* 3072:     */ 
/* 3073:     */ 
/* 3074:     */ 
/* 3075:     */ 
/* 3076:     */ 
/* 3077:     */ 
/* 3078:     */ 
/* 3079:     */ 
/* 3080:     */ 
/* 3081:     */ 
/* 3082:     */ 
/* 3083:     */ 
/* 3084:     */ 
/* 3085:     */ 
/* 3086:     */ 
/* 3087:     */ 
/* 3088:     */ 
/* 3089:     */ 
/* 3090:     */ 
/* 3091:     */ 
/* 3092:     */ 
/* 3093:     */ 
/* 3094:     */ 
/* 3095:     */ 
/* 3096:     */ 
/* 3097:     */ 
/* 3098:     */ 
/* 3099:     */ 
/* 3100:     */ 
/* 3101:     */ 
/* 3102:     */ 
/* 3103:     */ 
/* 3104:     */ 
/* 3105:     */ 
/* 3106:     */ 
/* 3107:3555 */     return this.menuBar;
/* 3108:     */   }
/* 3109:     */   
/* 3110:     */   public TabbedTextViewer getSourceContainer()
/* 3111:     */   {
/* 3112:3559 */     if (this.sourceContainer == null)
/* 3113:     */     {
/* 3114:3560 */       this.sourceContainer = new TabbedTextViewer();
/* 3115:3561 */       this.sourceContainer.setName("Sources");
/* 3116:3562 */       this.sourceContainer.switchTab("Commonsense knowledge");
/* 3117:     */     }
/* 3118:3564 */     return this.sourceContainer;
/* 3119:     */   }
/* 3120:     */   
/* 3121:     */   public TabbedTextViewer getScopeContainer()
/* 3122:     */   {
/* 3123:3568 */     if (this.scopeContainer == null)
/* 3124:     */     {
/* 3125:3569 */       this.scopeContainer = new TabbedTextViewer();
/* 3126:3570 */       this.scopeContainer.setName("Scope");
/* 3127:     */     }
/* 3128:3572 */     return this.scopeContainer;
/* 3129:     */   }
/* 3130:     */   
/* 3131:     */   public TabbedTextViewer getResultContainer()
/* 3132:     */   {
/* 3133:3576 */     if (this.resultContainer == null)
/* 3134:     */     {
/* 3135:3577 */       this.resultContainer = new TabbedTextViewer();
/* 3136:3578 */       this.resultContainer.setName("Results");
/* 3137:     */     }
/* 3138:3580 */     return this.resultContainer;
/* 3139:     */   }
/* 3140:     */   
/* 3141:     */   public TabbedTextViewer getSummaryContainer()
/* 3142:     */   {
/* 3143:3584 */     if (this.summaryContainer == null)
/* 3144:     */     {
/* 3145:3585 */       this.summaryContainer = new TabbedTextViewer();
/* 3146:3586 */       this.summaryContainer.setName("Summary");
/* 3147:     */     }
/* 3148:3588 */     return this.summaryContainer;
/* 3149:     */   }
/* 3150:     */   
/* 3151:     */   public TabbedTextViewer getStoryContainer()
/* 3152:     */   {
/* 3153:3592 */     if (this.storyContainer == null)
/* 3154:     */     {
/* 3155:3593 */       this.storyContainer = new TabbedTextViewer();
/* 3156:3594 */       this.storyContainer.setName("Story");
/* 3157:     */     }
/* 3158:3596 */     return this.storyContainer;
/* 3159:     */   }
/* 3160:     */   
/* 3161:     */   public TabbedTextViewer getConceptContainer()
/* 3162:     */   {
/* 3163:3600 */     if (this.conceptContainer == null)
/* 3164:     */     {
/* 3165:3601 */       this.conceptContainer = new TabbedTextViewer();
/* 3166:3602 */       this.conceptContainer.setName("Concepts");
/* 3167:     */     }
/* 3168:3604 */     return this.conceptContainer;
/* 3169:     */   }
/* 3170:     */   
/* 3171:     */   public static void main(String[] ignore)
/* 3172:     */   {
/* 3173:3637 */     JFrame frame = new JFrame();
/* 3174:3638 */     JEditorPane editor = null;
/* 3175:     */     try
/* 3176:     */     {
/* 3177:3640 */       URL url = new URL("http://people.csail.mit.edu/phw/genesis-runs.html");
/* 3178:3641 */       editor = new JEditorPane(url);
/* 3179:     */     }
/* 3180:     */     catch (MalformedURLException e)
/* 3181:     */     {
/* 3182:3644 */       e.printStackTrace();
/* 3183:     */     }
/* 3184:     */     catch (IOException e)
/* 3185:     */     {
/* 3186:3647 */       e.printStackTrace();
/* 3187:     */     }
/* 3188:3649 */     frame.getContentPane().add(editor);
/* 3189:3650 */     frame.pack();
/* 3190:3651 */     frame.setVisible(true);
/* 3191:     */   }
/* 3192:     */   
/* 3193:     */   public EntityMemory getThingMemory()
/* 3194:     */   {
/* 3195:3656 */     if (this.thingMemory == null) {
/* 3196:3657 */       this.thingMemory = new EntityMemory();
/* 3197:     */     }
/* 3198:3659 */     return this.thingMemory;
/* 3199:     */   }
/* 3200:     */   
/* 3201:     */   public IdiomSplitter getIdiomSplitter()
/* 3202:     */   {
/* 3203:3663 */     if (this.idiomSplitter == null)
/* 3204:     */     {
/* 3205:3664 */       this.idiomSplitter = new IdiomSplitter();
/* 3206:3665 */       this.idiomSplitter.setName("Idiom splitter");
/* 3207:     */     }
/* 3208:3667 */     return this.idiomSplitter;
/* 3209:     */   }
/* 3210:     */   
/* 3211:     */   public Combinator getCombinator()
/* 3212:     */   {
/* 3213:3671 */     if (this.combinator == null)
/* 3214:     */     {
/* 3215:3672 */       this.combinator = new Combinator();
/* 3216:3673 */       this.combinator.setName("Combinator");
/* 3217:     */     }
/* 3218:3675 */     return this.combinator;
/* 3219:     */   }
/* 3220:     */   
/* 3221:     */   public void setLeftPanelToOnset(Object ignore)
/* 3222:     */   {
/* 3223:3691 */     if (!Switch.showOnsetSwitch.isSelected()) {
/* 3224:3692 */       return;
/* 3225:     */     }
/* 3226:3694 */     setLeftPanel("Onsets");
/* 3227:     */   }
/* 3228:     */   
/* 3229:     */   public void setRightPanelToResults(Object ignore)
/* 3230:     */   {
/* 3231:3698 */     setRightPanel("Results");
/* 3232:     */   }
/* 3233:     */   
/* 3234:     */   public void setBottomPanelToImagination(Object ignore)
/* 3235:     */   {
/* 3236:3702 */     Mark.say(
/* 3237:     */     
/* 3238:3704 */       new Object[] { "C" });setBottomPanel("Imagination");
/* 3239:     */   }
/* 3240:     */   
/* 3241:     */   public GenesisGetters()
/* 3242:     */   {
/* 3243:3722 */     setName("Getters");
/* 3244:     */     
/* 3245:     */ 
/* 3246:3725 */     Connections.getPorts(this).addSignalProcessor("set right panel to results", "setRightPanelToResults");
/* 3247:     */     
/* 3248:     */ 
/* 3249:3728 */     Connections.getPorts(this).addSignalProcessor("set left panel to onset", "setLeftPanelToOnset");
/* 3250:     */     
/* 3251:3730 */     Connections.getPorts(this).addSignalProcessor("set pane", "setPanel");
/* 3252:     */     
/* 3253:     */ 
/* 3254:     */ 
/* 3255:3734 */     computeContributors();
/* 3256:     */   }
/* 3257:     */   
/* 3258:     */   public void setLeftPanel(String identifier)
/* 3259:     */   {
/* 3260:3738 */     getWindowGroupManager().setGuts(getLeftPanel(), identifier);
/* 3261:     */     
/* 3262:3740 */     Preferences.userRoot().put("leftPanel", identifier);
/* 3263:     */   }
/* 3264:     */   
/* 3265:     */   public void setRightPanel(String identifier)
/* 3266:     */   {
/* 3267:3744 */     getWindowGroupManager().setGuts(getRightPanel(), identifier);
/* 3268:     */     
/* 3269:3746 */     Preferences.userRoot().put("rightPanel", identifier);
/* 3270:     */   }
/* 3271:     */   
/* 3272:     */   public void setBottomPanel(String identifier)
/* 3273:     */   {
/* 3274:3750 */     getWindowGroupManager().setGuts(getBottomPanel(), identifier);
/* 3275:     */     
/* 3276:3752 */     Preferences.userRoot().put("bottomPanel", identifier);
/* 3277:     */   }
/* 3278:     */   
/* 3279:     */   public void setPanel(Object o)
/* 3280:     */   {
/* 3281:3756 */     if ((o instanceof BetterSignal))
/* 3282:     */     {
/* 3283:3757 */       BetterSignal signal = (BetterSignal)o;
/* 3284:3758 */       if (signal.size() == 2)
/* 3285:     */       {
/* 3286:3759 */         String identifier = (String)signal.get(1, String.class);
/* 3287:3760 */         if (signal.get(0, String.class) == "leftPanel") {
/* 3288:3761 */           setLeftPanel(identifier);
/* 3289:3764 */         } else if (signal.get(0, String.class) == "rightPanel") {
/* 3290:3765 */           setRightPanel(identifier);
/* 3291:3768 */         } else if (signal.get(0, String.class) == "bottomPanel") {
/* 3292:3769 */           setBottomPanel(identifier);
/* 3293:     */         }
/* 3294:     */       }
/* 3295:     */     }
/* 3296:     */   }
/* 3297:     */   
/* 3298:     */   public WiredBlinkingBox getAgentBlinker()
/* 3299:     */   {
/* 3300:3777 */     if (this.agentBlinker == null) {
/* 3301:3778 */       this.agentBlinker = new WiredBlinkingBox("Agent", getAgentExpert(), new RelationViewer(), getExpertsPanel());
/* 3302:     */     }
/* 3303:3780 */     return this.agentBlinker;
/* 3304:     */   }
/* 3305:     */   
/* 3306:     */   public AgentExpert getAgentExpert()
/* 3307:     */   {
/* 3308:3784 */     if (this.agentExpert == null) {
/* 3309:3785 */       this.agentExpert = new AgentExpert();
/* 3310:     */     }
/* 3311:3787 */     return this.agentExpert;
/* 3312:     */   }
/* 3313:     */   
/* 3314:     */   public WiredBlinkingBox getPictureBlinker()
/* 3315:     */   {
/* 3316:3791 */     if (this.pictureBlinker == null)
/* 3317:     */     {
/* 3318:3792 */       this.pictureBlinker = new WiredBlinkingBox("Picture", getRachelsPictureExpert(), new PictureViewer(), getExpertsPanel());
/* 3319:3793 */       this.pictureBlinker.setBlinkSwitch(false);
/* 3320:     */     }
/* 3321:3796 */     return this.pictureBlinker;
/* 3322:     */   }
/* 3323:     */   
/* 3324:     */   public WiredBlinkingBox getRoleBlinker()
/* 3325:     */   {
/* 3326:3800 */     if (this.roleBlinker == null) {
/* 3327:3801 */       this.roleBlinker = new WiredBlinkingBox("Role frame", getRoleExpert(), new RoleViewer(), getExpertsPanel());
/* 3328:     */     }
/* 3329:3803 */     return this.roleBlinker;
/* 3330:     */   }
/* 3331:     */   
/* 3332:     */   public RoleExpert getRoleExpert()
/* 3333:     */   {
/* 3334:3807 */     if (this.roleExpert == null) {
/* 3335:3808 */       this.roleExpert = new RoleExpert();
/* 3336:     */     }
/* 3337:3810 */     return this.roleExpert;
/* 3338:     */   }
/* 3339:     */   
/* 3340:     */   public WiredBlinkingBox getJobBlinker()
/* 3341:     */   {
/* 3342:3814 */     if (this.jobBlinker == null) {
/* 3343:3815 */       this.jobBlinker = new WiredBlinkingBox("Job", getJobExpert(), new RelationViewer(), getExpertsPanel());
/* 3344:     */     }
/* 3345:3817 */     return this.jobBlinker;
/* 3346:     */   }
/* 3347:     */   
/* 3348:     */   public JobExpert getJobExpert()
/* 3349:     */   {
/* 3350:3821 */     if (this.jobExpert == null) {
/* 3351:3822 */       this.jobExpert = new JobExpert();
/* 3352:     */     }
/* 3353:3824 */     return this.jobExpert;
/* 3354:     */   }
/* 3355:     */   
/* 3356:     */   public WiredBlinkingBox getPersonalityBlinker()
/* 3357:     */   {
/* 3358:3828 */     if (this.personalityBlinker == null) {
/* 3359:3829 */       this.personalityBlinker = new WiredBlinkingBox("Personality", getPersonalityExpert(), new RelationViewer(), getExpertsPanel());
/* 3360:     */     }
/* 3361:3831 */     return this.personalityBlinker;
/* 3362:     */   }
/* 3363:     */   
/* 3364:     */   public WiredBlinkingBox getPropertyBlinker()
/* 3365:     */   {
/* 3366:3835 */     if (this.propertyBlinker == null) {
/* 3367:3836 */       this.propertyBlinker = new WiredBlinkingBox("Property", getPropertyExpert(), new RelationViewer(), getExpertsPanel());
/* 3368:     */     }
/* 3369:3838 */     return this.propertyBlinker;
/* 3370:     */   }
/* 3371:     */   
/* 3372:     */   public PersonalityExpert getPersonalityExpert()
/* 3373:     */   {
/* 3374:3842 */     if (this.personalityExpert == null) {
/* 3375:3843 */       this.personalityExpert = new PersonalityExpert();
/* 3376:     */     }
/* 3377:3845 */     return this.personalityExpert;
/* 3378:     */   }
/* 3379:     */   
/* 3380:     */   public PropertyExpert getPropertyExpert()
/* 3381:     */   {
/* 3382:3849 */     if (this.propertyExpert == null) {
/* 3383:3850 */       this.propertyExpert = new PropertyExpert();
/* 3384:     */     }
/* 3385:3852 */     return this.propertyExpert;
/* 3386:     */   }
/* 3387:     */   
/* 3388:     */   public WiredBlinkingBox getPartBlinker()
/* 3389:     */   {
/* 3390:3856 */     if (this.partBlinker == null) {
/* 3391:3857 */       this.partBlinker = new WiredBlinkingBox("Part", getPartExpert(), new PartPanel(), getExpertsPanel());
/* 3392:     */     }
/* 3393:3859 */     return this.partBlinker;
/* 3394:     */   }
/* 3395:     */   
/* 3396:     */   public PartExpert getPartExpert()
/* 3397:     */   {
/* 3398:3863 */     if (this.partExpert == null) {
/* 3399:3864 */       this.partExpert = new PartExpert();
/* 3400:     */     }
/* 3401:3866 */     return this.partExpert;
/* 3402:     */   }
/* 3403:     */   
/* 3404:     */   public WiredBlinkingBox getComparisonBlinker()
/* 3405:     */   {
/* 3406:3870 */     if (this.comparisonBlinker == null) {
/* 3407:3871 */       this.comparisonBlinker = new WiredBlinkingBox("Comparison", getComparisonExpert(), new ComparisonViewer(), getExpertsPanel());
/* 3408:     */     }
/* 3409:3873 */     return this.comparisonBlinker;
/* 3410:     */   }
/* 3411:     */   
/* 3412:     */   public WiredBlinkingBox getSocialBlinker()
/* 3413:     */   {
/* 3414:3877 */     if (this.socialBlinker == null) {
/* 3415:3878 */       this.socialBlinker = new WiredBlinkingBox("Social", getSocialExpert(), new NewFrameViewer(), getExpertsPanel());
/* 3416:     */     }
/* 3417:3880 */     return this.socialBlinker;
/* 3418:     */   }
/* 3419:     */   
/* 3420:     */   public SocialExpert getSocialExpert()
/* 3421:     */   {
/* 3422:3884 */     if (this.socialExpert == null) {
/* 3423:3885 */       this.socialExpert = new SocialExpert();
/* 3424:     */     }
/* 3425:3887 */     return this.socialExpert;
/* 3426:     */   }
/* 3427:     */   
/* 3428:     */   public WiredBlinkingBox getMoodBlinker()
/* 3429:     */   {
/* 3430:3891 */     if (this.moodBlinker == null) {
/* 3431:3892 */       this.moodBlinker = new WiredBlinkingBox("Mood", getMoodExpert(), new MoodViewer(), getExpertsPanel());
/* 3432:     */     }
/* 3433:3894 */     return this.moodBlinker;
/* 3434:     */   }
/* 3435:     */   
/* 3436:     */   public MoodExpert getMoodExpert()
/* 3437:     */   {
/* 3438:3898 */     if (this.moodExpert == null) {
/* 3439:3899 */       this.moodExpert = new MoodExpert();
/* 3440:     */     }
/* 3441:3901 */     return this.moodExpert;
/* 3442:     */   }
/* 3443:     */   
/* 3444:     */   public WiredBlinkingBox getBeliefBlinker()
/* 3445:     */   {
/* 3446:3905 */     if (this.beliefBlinker == null) {
/* 3447:3906 */       this.beliefBlinker = new WiredBlinkingBox("Belief", getBeliefExpert(), new NewFrameViewer(), getExpertsPanel());
/* 3448:     */     }
/* 3449:3908 */     return this.beliefBlinker;
/* 3450:     */   }
/* 3451:     */   
/* 3452:     */   public BeliefExpert getBeliefExpert()
/* 3453:     */   {
/* 3454:3912 */     if (this.beliefExpert == null) {
/* 3455:3913 */       this.beliefExpert = new BeliefExpert();
/* 3456:     */     }
/* 3457:3915 */     return this.beliefExpert;
/* 3458:     */   }
/* 3459:     */   
/* 3460:     */   public WiredBlinkingBox getPredictionBlinker()
/* 3461:     */   {
/* 3462:3919 */     if (this.predictionBlinker == null) {
/* 3463:3920 */       this.predictionBlinker = new WiredBlinkingBox("Prediction", getPredictionExpert(), new NewFrameViewer(), getExpertsPanel());
/* 3464:     */     }
/* 3465:3922 */     return this.predictionBlinker;
/* 3466:     */   }
/* 3467:     */   
/* 3468:     */   public ExpectationExpert getPredictionExpert()
/* 3469:     */   {
/* 3470:3926 */     if (this.expectationExpert == null) {
/* 3471:3927 */       this.expectationExpert = new ExpectationExpert();
/* 3472:     */     }
/* 3473:3929 */     return this.expectationExpert;
/* 3474:     */   }
/* 3475:     */   
/* 3476:     */   public WiredBlinkingBox getIntentionBlinker()
/* 3477:     */   {
/* 3478:3933 */     if (this.intentionBlinker == null) {
/* 3479:3934 */       this.intentionBlinker = new WiredBlinkingBox("Intention", getIntentionExpert(), new NewFrameViewer(), getExpertsPanel());
/* 3480:     */     }
/* 3481:3936 */     return this.intentionBlinker;
/* 3482:     */   }
/* 3483:     */   
/* 3484:     */   public IntentionExpert getIntentionExpert()
/* 3485:     */   {
/* 3486:3940 */     if (this.intentionExpert == null) {
/* 3487:3941 */       this.intentionExpert = new IntentionExpert();
/* 3488:     */     }
/* 3489:3943 */     return this.intentionExpert;
/* 3490:     */   }
/* 3491:     */   
/* 3492:     */   public WiredBlinkingBox getPersuationBlinker()
/* 3493:     */   {
/* 3494:3947 */     if (this.persuationBlinker == null) {
/* 3495:3948 */       this.persuationBlinker = new WiredBlinkingBox("Persuation", getPersuationExpert(), new NewFrameViewer(), getExpertsPanel());
/* 3496:     */     }
/* 3497:3950 */     return this.persuationBlinker;
/* 3498:     */   }
/* 3499:     */   
/* 3500:     */   public PersuationExpert getPersuationExpert()
/* 3501:     */   {
/* 3502:3954 */     if (this.persuationExpert == null) {
/* 3503:3955 */       this.persuationExpert = new PersuationExpert();
/* 3504:     */     }
/* 3505:3957 */     return this.persuationExpert;
/* 3506:     */   }
/* 3507:     */   
/* 3508:     */   public WiredBlinkingBox getCoercionBlinker()
/* 3509:     */   {
/* 3510:3961 */     if (this.coercionBlinker == null) {
/* 3511:3962 */       this.coercionBlinker = new WiredBlinkingBox("Coercion", getCoerceInterpreter(), new ForceViewer(), getExpertsPanel());
/* 3512:     */     }
/* 3513:3964 */     return this.coercionBlinker;
/* 3514:     */   }
/* 3515:     */   
/* 3516:     */   public CoercionExpert getCoercionExpert()
/* 3517:     */   {
/* 3518:3968 */     if (this.coercionExpert == null) {
/* 3519:3969 */       this.coercionExpert = new CoercionExpert();
/* 3520:     */     }
/* 3521:3971 */     return this.coercionExpert;
/* 3522:     */   }
/* 3523:     */   
/* 3524:     */   public WiredBlinkingBox getPossessionBlinker()
/* 3525:     */   {
/* 3526:3975 */     if (this.possessionBlinker == null) {
/* 3527:3976 */       this.possessionBlinker = new WiredBlinkingBox("Possession", getPossessionExpert(), new PossessionPanel(), getExpertsPanel());
/* 3528:     */     }
/* 3529:3978 */     return this.possessionBlinker;
/* 3530:     */   }
/* 3531:     */   
/* 3532:     */   public PossessionExpert getPossessionExpert()
/* 3533:     */   {
/* 3534:3982 */     if (this.possessionExpert == null) {
/* 3535:3983 */       this.possessionExpert = new PossessionExpert();
/* 3536:     */     }
/* 3537:3985 */     return this.possessionExpert;
/* 3538:     */   }
/* 3539:     */   
/* 3540:     */   public WiredBlinkingBox getThreadBlinker()
/* 3541:     */   {
/* 3542:3989 */     if (this.threadBlinker == null) {
/* 3543:3990 */       this.threadBlinker = new WiredBlinkingBox("Class", getThreadExpert(), new ThreadViewer(), getExpertsPanel());
/* 3544:     */     }
/* 3545:3992 */     return this.threadBlinker;
/* 3546:     */   }
/* 3547:     */   
/* 3548:     */   public ThreadExpert getThreadExpert()
/* 3549:     */   {
/* 3550:3996 */     if (this.threadExpert == null) {
/* 3551:3997 */       this.threadExpert = new ThreadExpert();
/* 3552:     */     }
/* 3553:3999 */     return this.threadExpert;
/* 3554:     */   }
/* 3555:     */   
/* 3556:     */   public WiredBlinkingBox getTrajectoryBlinker()
/* 3557:     */   {
/* 3558:4003 */     if (this.trajectoryBlinker == null) {
/* 3559:4004 */       this.trajectoryBlinker = new WiredBlinkingBox("Trajectory", getTrajectoryExpert(), new TrajectoryViewer(), getExpertsPanel());
/* 3560:     */     }
/* 3561:4006 */     return this.trajectoryBlinker;
/* 3562:     */   }
/* 3563:     */   
/* 3564:     */   public TrajectoryExpert getTrajectoryExpert()
/* 3565:     */   {
/* 3566:4010 */     if (this.trajectoryExpert == null) {
/* 3567:4011 */       this.trajectoryExpert = new TrajectoryExpert();
/* 3568:     */     }
/* 3569:4013 */     return this.trajectoryExpert;
/* 3570:     */   }
/* 3571:     */   
/* 3572:     */   public PathExpert getPathExpert()
/* 3573:     */   {
/* 3574:4017 */     if (this.pathExpert == null) {
/* 3575:4018 */       this.pathExpert = new PathExpert();
/* 3576:     */     }
/* 3577:4020 */     return this.pathExpert;
/* 3578:     */   }
/* 3579:     */   
/* 3580:     */   public WiredBlinkingBox getPathElementBlinker()
/* 3581:     */   {
/* 3582:4024 */     if (this.pathElementBlinkder == null) {
/* 3583:4025 */       this.pathElementBlinkder = new WiredBlinkingBox("Path", getPathElementExpert(), new PathElementViewer(), getExpertsPanel());
/* 3584:     */     }
/* 3585:4027 */     return this.pathElementBlinkder;
/* 3586:     */   }
/* 3587:     */   
/* 3588:     */   public PathElementExpert getPathElementExpert()
/* 3589:     */   {
/* 3590:4031 */     if (this.pathElementExpert == null) {
/* 3591:4032 */       this.pathElementExpert = new PathElementExpert();
/* 3592:     */     }
/* 3593:4034 */     return this.pathElementExpert;
/* 3594:     */   }
/* 3595:     */   
/* 3596:     */   public WiredBlinkingBox getPlaceBlinker()
/* 3597:     */   {
/* 3598:4038 */     if (this.placeBlinker == null) {
/* 3599:4039 */       this.placeBlinker = new WiredBlinkingBox("Place", getPlaceExpert(), new PlaceViewer(), getExpertsPanel());
/* 3600:     */     }
/* 3601:4041 */     return this.placeBlinker;
/* 3602:     */   }
/* 3603:     */   
/* 3604:     */   public PlaceExpert getPlaceExpert()
/* 3605:     */   {
/* 3606:4045 */     if (this.placeExpert == null) {
/* 3607:4046 */       this.placeExpert = new PlaceExpert();
/* 3608:     */     }
/* 3609:4048 */     return this.placeExpert;
/* 3610:     */   }
/* 3611:     */   
/* 3612:     */   public WiredBlinkingBox getCauseBlinker()
/* 3613:     */   {
/* 3614:4052 */     if (this.causeProbeBlinkingBox == null) {
/* 3615:4053 */       this.causeProbeBlinkingBox = new WiredBlinkingBox("Cause", getCauseExpert(), new NewFrameViewer(), getExpertsPanel());
/* 3616:     */     }
/* 3617:4055 */     return this.causeProbeBlinkingBox;
/* 3618:     */   }
/* 3619:     */   
/* 3620:     */   public CauseExpert getCauseExpert()
/* 3621:     */   {
/* 3622:4059 */     if (this.causeExpert == null) {
/* 3623:4060 */       this.causeExpert = new CauseExpert();
/* 3624:     */     }
/* 3625:4062 */     return this.causeExpert;
/* 3626:     */   }
/* 3627:     */   
/* 3628:     */   public WiredBlinkingBox getGoalBlinker()
/* 3629:     */   {
/* 3630:4066 */     if (this.goalBlinker == null) {
/* 3631:4067 */       this.goalBlinker = new WiredBlinkingBox("Goal", getGoalExpert(), new GoalPanel(), getExpertsPanel());
/* 3632:     */     }
/* 3633:4069 */     return this.goalBlinker;
/* 3634:     */   }
/* 3635:     */   
/* 3636:     */   public GoalExpert getGoalExpert()
/* 3637:     */   {
/* 3638:4073 */     if (this.goalExpert == null) {
/* 3639:4074 */       this.goalExpert = new GoalExpert();
/* 3640:     */     }
/* 3641:4076 */     return this.goalExpert;
/* 3642:     */   }
/* 3643:     */   
/* 3644:     */   public WiredBlinkingBox getTransitionBlinker()
/* 3645:     */   {
/* 3646:4080 */     if (this.transitionBlinker == null) {
/* 3647:4081 */       this.transitionBlinker = new WiredBlinkingBox("Transition", getTransitionExpert(), new TransitionViewer(), getExpertsPanel());
/* 3648:     */     }
/* 3649:4083 */     return this.transitionBlinker;
/* 3650:     */   }
/* 3651:     */   
/* 3652:     */   public TransitionExpert getTransitionExpert()
/* 3653:     */   {
/* 3654:4087 */     if (this.transitionExpert == null) {
/* 3655:4088 */       this.transitionExpert = new TransitionExpert();
/* 3656:     */     }
/* 3657:4090 */     return this.transitionExpert;
/* 3658:     */   }
/* 3659:     */   
/* 3660:     */   public WiredBlinkingBox getTimeBlinker()
/* 3661:     */   {
/* 3662:4094 */     if (this.timeBlinker == null) {
/* 3663:4095 */       this.timeBlinker = new WiredBlinkingBox("Time", getTimeExpert(), new TimeViewer(), getExpertsPanel());
/* 3664:     */     }
/* 3665:4097 */     return this.timeBlinker;
/* 3666:     */   }
/* 3667:     */   
/* 3668:     */   public TimeExpert getTimeExpert()
/* 3669:     */   {
/* 3670:4101 */     if (this.timeExpert == null) {
/* 3671:4102 */       this.timeExpert = new TimeExpert();
/* 3672:     */     }
/* 3673:4104 */     return this.timeExpert;
/* 3674:     */   }
/* 3675:     */   
/* 3676:     */   public WiredBlinkingBox getTransferBlinker()
/* 3677:     */   {
/* 3678:4108 */     if (this.transferBlinker == null) {
/* 3679:4109 */       this.transferBlinker = new WiredBlinkingBox("Transfer", getTransferExpert(), new TransferViewer(), getExpertsPanel());
/* 3680:     */     }
/* 3681:4111 */     return this.transferBlinker;
/* 3682:     */   }
/* 3683:     */   
/* 3684:     */   public TransferExpert getTransferExpert()
/* 3685:     */   {
/* 3686:4115 */     if (this.transferExpert == null) {
/* 3687:4116 */       this.transferExpert = new TransferExpert();
/* 3688:     */     }
/* 3689:4118 */     return this.transferExpert;
/* 3690:     */   }
/* 3691:     */   
/* 3692:     */   public ReflectionOnsetDetector getOnsetDetector1()
/* 3693:     */   {
/* 3694:4122 */     if (this.onsetDetector1 == null)
/* 3695:     */     {
/* 3696:4123 */       this.onsetDetector1 = new ReflectionOnsetDetector();
/* 3697:4124 */       this.onsetDetector1.setName("Onset detector 1");
/* 3698:     */     }
/* 3699:4126 */     return this.onsetDetector1;
/* 3700:     */   }
/* 3701:     */   
/* 3702:     */   public ReflectionOnsetDetector getOnsetDetector2()
/* 3703:     */   {
/* 3704:4130 */     if (this.onsetDetector2 == null)
/* 3705:     */     {
/* 3706:4131 */       this.onsetDetector2 = new ReflectionOnsetDetector();
/* 3707:4132 */       this.onsetDetector2.setName("Onset detector 2");
/* 3708:     */     }
/* 3709:4134 */     return this.onsetDetector2;
/* 3710:     */   }
/* 3711:     */   
/* 3712:     */   public JPanel getPersonalGuiPanel()
/* 3713:     */   {
/* 3714:4138 */     if (this.personalGuiPanel == null)
/* 3715:     */     {
/* 3716:4139 */       this.personalGuiPanel = new JPanel();
/* 3717:4140 */       this.personalGuiPanel.setName("Personal panel");
/* 3718:4141 */       getWindowGroupManager().addJComponent(this.personalGuiPanel);
/* 3719:     */     }
/* 3720:4143 */     return this.personalGuiPanel;
/* 3721:     */   }
/* 3722:     */   
/* 3723:     */   public SimilarityViewer getSimilarityViewer()
/* 3724:     */   {
/* 3725:4147 */     if (this.similarityViewer == null)
/* 3726:     */     {
/* 3727:4148 */       this.similarityViewer = new SimilarityViewer();
/* 3728:4149 */       this.similarityViewer.initialize(getSimilarityProcessor());
/* 3729:4150 */       this.similarityViewer.setName("Similarity panel");
/* 3730:     */     }
/* 3731:4152 */     return this.similarityViewer;
/* 3732:     */   }
/* 3733:     */   
/* 3734:     */   public SimilarityProcessor getSimilarityProcessor()
/* 3735:     */   {
/* 3736:4156 */     if (this.similarityProcessor == null) {
/* 3737:4157 */       this.similarityProcessor = new SimilarityProcessor(getSimilarityViewer());
/* 3738:     */     }
/* 3739:4159 */     return this.similarityProcessor;
/* 3740:     */   }
/* 3741:     */   
/* 3742:     */   public JPanel getPersonalButtonPanel()
/* 3743:     */   {
/* 3744:4163 */     if (this.personalButtonPanel == null)
/* 3745:     */     {
/* 3746:4164 */       this.personalButtonPanel = new JPanel();
/* 3747:4165 */       getControls().addTab("Personal controls", this.personalButtonPanel);
/* 3748:     */     }
/* 3749:4168 */     return this.personalButtonPanel;
/* 3750:     */   }
/* 3751:     */   
/* 3752:     */   public MindsEyeProcessor getMindsEyeProcessor()
/* 3753:     */   {
/* 3754:4181 */     if (this.mindsEyeProcessor == null) {
/* 3755:4182 */       this.mindsEyeProcessor = new MindsEyeProcessor();
/* 3756:     */     }
/* 3757:4184 */     return this.mindsEyeProcessor;
/* 3758:     */   }
/* 3759:     */   
/* 3760:4202 */   private CharacterProcessor characterProcessor = null;
/* 3761:     */   
/* 3762:     */   public CharacterProcessor getCharacterProcessor()
/* 3763:     */   {
/* 3764:4205 */     if (this.characterProcessor == null) {
/* 3765:4206 */       this.characterProcessor = new CharacterProcessor();
/* 3766:     */     }
/* 3767:4208 */     return this.characterProcessor;
/* 3768:     */   }
/* 3769:     */   
/* 3770:     */   public AlignmentViewer getAlignmentViewer()
/* 3771:     */   {
/* 3772:4212 */     if (this.alignmentViewer == null)
/* 3773:     */     {
/* 3774:4213 */       this.alignmentViewer = new AlignmentViewer();
/* 3775:4214 */       getWindowGroupManager().addJComponent(this.alignmentViewer);
/* 3776:     */     }
/* 3777:4216 */     return this.alignmentViewer;
/* 3778:     */   }
/* 3779:     */   
/* 3780:     */   public CharacterViewer getCharacterViewer()
/* 3781:     */   {
/* 3782:4220 */     if (this.characterViewer == null)
/* 3783:     */     {
/* 3784:4221 */       this.characterViewer = new CharacterViewer();
/* 3785:4222 */       getWindowGroupManager().addJComponent(this.characterViewer);
/* 3786:     */     }
/* 3787:4224 */     return this.characterViewer;
/* 3788:     */   }
/* 3789:     */   
/* 3790:     */   public TraitViewer getTraitViewer()
/* 3791:     */   {
/* 3792:4228 */     if (this.traitViewer == null)
/* 3793:     */     {
/* 3794:4229 */       this.traitViewer = TraitViewer.getTraitViewer();
/* 3795:4230 */       getWindowGroupManager().addJComponent(this.traitViewer);
/* 3796:     */     }
/* 3797:4232 */     return this.traitViewer;
/* 3798:     */   }
/* 3799:     */   
/* 3800:     */   public StoryThreadingViewer getStoryThreadingViewer()
/* 3801:     */   {
/* 3802:4238 */     if (this.storyThreadingViewer == null)
/* 3803:     */     {
/* 3804:4239 */       this.storyThreadingViewer = new StoryThreadingViewer();
/* 3805:4240 */       getWindowGroupManager().addJComponent(this.storyThreadingViewer);
/* 3806:     */     }
/* 3807:4242 */     return this.storyThreadingViewer;
/* 3808:     */   }
/* 3809:     */   
/* 3810:     */   public AlignmentProcessor getAlignmentProcessor()
/* 3811:     */   {
/* 3812:4248 */     if (this.alignmentProcessor == null) {
/* 3813:4248 */       this.alignmentProcessor = new AlignmentProcessor();
/* 3814:     */     }
/* 3815:4249 */     return this.alignmentProcessor;
/* 3816:     */   }
/* 3817:     */   
/* 3818:4252 */   private StoryThreadingProcessor storyThreadingProcessor = null;
/* 3819:     */   private ForwardChainer forwardChainer1;
/* 3820:     */   private ForwardChainer forwardChainer2;
/* 3821:     */   private BackwardChainer backwardChainer1;
/* 3822:     */   private BackwardChainer backwardChainer2;
/* 3823:     */   private ReflectionOnsetDetector onsetDetector1;
/* 3824:     */   private ReflectionOnsetDetector onsetDetector2;
/* 3825:     */   private CauseExpert causeExpert;
/* 3826:     */   private TimeExpert timeExpert;
/* 3827:     */   private CoercionExpert coercionExpert;
/* 3828:     */   private TrajectoryExpert trajectoryExpert;
/* 3829:     */   private PathExpert pathExpert;
/* 3830:     */   private PathElementExpert pathElementExpert;
/* 3831:     */   private PlaceExpert placeExpert;
/* 3832:     */   private TransitionExpert transitionExpert;
/* 3833:     */   private TransferExpert transferExpert;
/* 3834:     */   private RoleExpert roleExpert;
/* 3835:     */   private AgentExpert agentExpert;
/* 3836:     */   private BeliefExpert beliefExpert;
/* 3837:     */   private ExpectationExpert expectationExpert;
/* 3838:     */   private PersuationExpert persuationExpert;
/* 3839:     */   private IntentionExpert intentionExpert;
/* 3840:     */   private SocialExpert socialExpert;
/* 3841:     */   private ThreadExpert threadExpert;
/* 3842:     */   private MoodExpert moodExpert;
/* 3843:     */   private PropertyExpert propertyExpert;
/* 3844:     */   private PersonalityExpert personalityExpert;
/* 3845:     */   private JobExpert jobExpert;
/* 3846:     */   private PartExpert partExpert;
/* 3847:     */   private PossessionExpert possessionExpert;
/* 3848:     */   private ComparisonExpert comparisonExpert;
/* 3849:     */   private QuestionExpert questionExpert1;
/* 3850:     */   private StoryExpert storyExpert;
/* 3851:     */   private CommandExpert commandExpert;
/* 3852:     */   private GoalExpert goalExpert;
/* 3853:     */   private ImaginationExpert imaginationExpert;
/* 3854:     */   private DescribeExpert describeExpert;
/* 3855:     */   private StateExpert stateExpert;
/* 3856:     */   private static AnaphoraExpert anaphoraExpert;
/* 3857:     */   private IdiomExpert idiomExpert;
/* 3858:     */   private StoryRecallExpert storyRecallExpert1;
/* 3859:     */   private StoryRecallExpert storyRecallExpert2;
/* 3860:     */   private StoryRecallViewer storyRecallViewer1;
/* 3861:     */   private StoryRecallViewer storyRecallViewer2;
/* 3862:     */   private IdiomSplitter idiomSplitter;
/* 3863:     */   private Combinator combinator;
/* 3864:     */   private WiredDistributorBox inputDistributor;
/* 3865:     */   private OnsetViewer onsetViewer1;
/* 3866:     */   private OnsetViewer onsetViewer2;
/* 3867:     */   private JPanel ruleViewer1Wrapper;
/* 3868:     */   private JPanel ruleViewer2Wrapper;
/* 3869:     */   private RuleViewer ruleViewer1;
/* 3870:     */   private RuleViewer ruleViewer2;
/* 3871:     */   private EscalationExpert escalationExpert1;
/* 3872:     */   private EscalationExpert escalationExpert2;
/* 3873:     */   private JPanel instantiatedRuleViewer1Wrapper;
/* 3874:     */   private JPanel instantiatedRuleViewer2Wrapper;
/* 3875:     */   private RuleViewer instRuleViewer1;
/* 3876:     */   private RuleViewer instRuleViewer2;
/* 3877:     */   private TabbedTextViewer sourceContainer;
/* 3878:     */   private TabbedTextViewer resultContainer;
/* 3879:     */   private TabbedTextViewer summaryContainer;
/* 3880:     */   private TabbedTextViewer storyContainer;
/* 3881:     */   private TabbedTextViewer scopeContainer;
/* 3882:     */   private TabbedTextViewer conceptContainer;
/* 3883:     */   protected WindowGroupManager windowGroupManager;
/* 3884:     */   private WindowGroupHost leftPanel;
/* 3885:     */   private WindowGroupHost rightPanel;
/* 3886:     */   private WindowGroupHost bottomPanel;
/* 3887:     */   private EntityMemory thingMemory;
/* 3888:     */   private WiredSplitPane elaborationPanel;
/* 3889:     */   private WiredSplitPane inspectorPanel;
/* 3890:     */   private WiredSplitPane reflectionPanel;
/* 3891:     */   private WiredSplitPane reflectionOnsetPanel;
/* 3892:     */   private WiredSplitPane onsetPanel;
/* 3893:     */   private WiredSplitPane ruleViewerPanel;
/* 3894:     */   private WiredSplitPane instantiatedRuleViewerPanel;
/* 3895:     */   private WiredSplitPane recallPanel;
/* 3896:     */   static final String ARROW = "Arrow port";
/* 3897:     */   public static final String CLOSE = "Close";
/* 3898:     */   static final String MAGIC = "Magic port";
/* 3899:     */   public static final String OPEN = "Open";
/* 3900:     */   static final String STUB = "Stub port";
/* 3901:     */   static final String VIEW = "Viewer port";
/* 3902:     */   private BackgroundWiredBox backgroundMemoryBox;
/* 3903:     */   protected BensGauntletComponent benji;
/* 3904:     */   private BlinkingBoxPanel blinkingBoxPanel;
/* 3905:     */   private JPanel buttonPanel;
/* 3906:     */   private WiredBlinkingBox blockProbeBlinkingBox;
/* 3907:     */   private BlockViewer blockViewer;
/* 3908:     */   private WiredBlinkingBox causeProbeBlinkingBox;
/* 3909:     */   private NewFrameViewer closestThingViewer;
/* 3910:     */   private WiredBlinkingBox coercionBlinker;
/* 3911:     */   private WiredBlinkingBox controlProbeBlinkingBox;
/* 3912:     */   private DictionaryPage dictionary;
/* 3913:     */   private Disambiguator2 disambiguator;
/* 3914:     */   private Disambiguator3 linkDisambiguator;
/* 3915:     */   private Disambiguator3 startDisambiguator;
/* 3916:     */   private Disambiguator newDisambiguator;
/* 3917:     */   private DisambiguatorViewer disambiguatorViewer;
/* 3918:     */   private NewFrameViewer everythingViewer;
/* 3919:     */   private Distributor expertDistributionBox;
/* 3920:     */   protected StateMaintainer stateMaintainer;
/* 3921:     */   private FileReaderPanel fileReaderFrame;
/* 3922:     */   private ForceInterpreter coerceInterpreter;
/* 3923:     */   private ForceInterpreter causeInterpreter;
/* 3924:     */   private WiredBlinkingBox forceProbeBlinkingBox;
/* 3925:     */   private ForceViewer forceViewer;
/* 3926:     */   private WiredBlinkingBox geometryProbeBlinkingBox;
/* 3927:     */   private GeometryViewer geometryViewer;
/* 3928:     */   private HardWiredTranslator hardWiredTranslator;
/* 3929:     */   private Translator translator;
/* 3930:     */   private WiredBlinkingBox imagineBlinker;
/* 3931:     */   private JTabbedPane inputTabbedPane;
/* 3932:     */   private SimpleGenerator internalToEnglishTranslator;
/* 3933:     */   private EventKnowledgeViewer knowledgeWatcher;
/* 3934:     */   private WiredBlinkingBox knowledgeWatcherBlinker;
/* 3935:     */   private Start startParser;
/* 3936:     */   private NewFrameViewer linkViewer;
/* 3937:     */   private StoryViewer startViewer;
/* 3938:     */   private M2Viewer m2Viewer;
/* 3939:     */   private ChainViewer chainViewer;
/* 3940:     */   private PredictionsViewer predViewer;
/* 3941:     */   protected MovieManager movieManager;
/* 3942:     */   private ImagePanel lightBulbViewer;
/* 3943:     */   private MovieViewerExternal externalMovieViewer;
/* 3944:     */   private JSplitPane northSouthSplitPane;
/* 3945:     */   private JPanel topPanel;
/* 3946:     */   private JTabbedPane outputTabbedPane;
/* 3947:     */   private DistributionBox parserDistributionBox;
/* 3948:     */   private WiredBlinkingBox parserProbeBlinkingBox;
/* 3949:     */   private WiredBlinkingBox pathElementBlinkder;
/* 3950:     */   private WiredBlinkingBox pathProbeBlinkingBox;
/* 3951:     */   private NewFrameViewer pathViewer;
/* 3952:     */   private PictureFinder pictureFinder;
/* 3953:     */   private WiredBlinkingBox pictureProbeBlinkingBox;
/* 3954:     */   private PictureViewer pictureViewer;
/* 3955:     */   private WiredBlinkingBox placeBlinker;
/* 3956:     */   private TalkingFrameViewer predictionViewer;
/* 3957:     */   private QueuingWiredBox queuingPictureBox;
/* 3958:     */   private RachelsPictureExpert rachelsPictureExpert;
/* 3959:     */   private WiredBlinkingBox roleBlinker;
/* 3960:     */   private WiredBlinkingBox pictureBlinker;
/* 3961:     */   private WiredBlinkingBox beliefBlinker;
/* 3962:     */   private WiredBlinkingBox predictionBlinker;
/* 3963:     */   private WiredBlinkingBox persuationBlinker;
/* 3964:     */   private WiredBlinkingBox intentionBlinker;
/* 3965:     */   private WiredBlinkingBox socialBlinker;
/* 3966:     */   SimpleGenerator simpleGenerator;
/* 3967:     */   SimpleGenerator descriptionGenerator;
/* 3968:     */   private SomTrajectoryBox somTester;
/* 3969:     */   private JSplitPane splitPane;
/* 3970:     */   private TalkBackViewer talkBackViewer;
/* 3971:     */   protected Talker talker;
/* 3972:     */   private TextEntryBox textEntryBox;
/* 3973:     */   private WiredBlinkingBox threadBlinker;
/* 3974:     */   private WiredBlinkingBox moodBlinker;
/* 3975:     */   private WiredBlinkingBox jobBlinker;
/* 3976:     */   private WiredBlinkingBox agentBlinker;
/* 3977:     */   private WiredBlinkingBox partBlinker;
/* 3978:     */   private WiredBlinkingBox possessionBlinker;
/* 3979:     */   private WiredBlinkingBox propertyBlinker;
/* 3980:     */   private WiredBlinkingBox personalityBlinker;
/* 3981:     */   private WiredBlinkingBox comparisonBlinker;
/* 3982:     */   private WiredBlinkingBox timeBlinker;
/* 3983:     */   WiredProgressBar storyProgressBar2;
/* 3984:     */   private StoryTeller storyTeller;
/* 3985:     */   private SummaryHelper summaryHelper;
/* 3986:     */   private StoryEnvironment storyEnvironment;
/* 3987:     */   private InternalNarrator internalNarrator;
/* 3988:     */   private StaticAudienceModeler staticAudienceModeler;
/* 3989:     */   private GoalSpecifier goalSpecifier;
/* 3990:     */   private GoalTracker goalTracker;
/* 3991:     */   private StoryPreSimulator storyPresimulator;
/* 3992:     */   private StorySimulator storySimulator;
/* 3993:     */   private StoryModifier storyModifier;
/* 3994:     */   private StoryPublisher storyPublisher;
/* 3995:     */   private ModelEvaluator modelEvaluator;
/* 3996:     */   private SimilarityProcessor similarityProcessor;
/* 3997:     */   TrafficLight trafficLight;
/* 3998:     */   JPanel trafficLightPanel;
/* 3999:     */   private WiredBlinkingBox trajectoryBlinker;
/* 4000:     */   private WiredBlinkingBox transitionBlinker;
/* 4001:     */   private WiredBlinkingBox goalBlinker;
/* 4002:     */   private WiredBlinkingBox transferBlinker;
/* 4003:     */   private UnderstandProcessor understandProcessor;
/* 4004:     */   protected TabbedTextAdapter remarksAdapter;
/* 4005:     */   protected TabbedTextAdapter mysteryAdapter;
/* 4006:     */   protected TabbedTextAdapter startProcessingViewer;
/* 4007:     */   protected TabbedTextAdapter conceptAdapter;
/* 4008:     */   protected static final String IDIOM = "idiom";
/* 4009:     */   protected static final String SET_RIGHT_PANEL_TO_RESULTS = "set right panel to results";
/* 4010:     */   protected static final String SET_BOTTOM_PANEL_TO_IMAGINATION = "set bottom panel to imagination";
/* 4011:     */   protected static final String SET_LEFT_PANEL_TO_ONSET = "set left panel to onset";
/* 4012:     */   private MindsEyeMovieViewer mindsEyeMovieViewer;
/* 4013:     */   private MindsEyeMoviePlayer mindsEyeMoviePlayer;
/* 4014:     */   private SimilarityViewer similarityViewer;
/* 4015:     */   private NameLabel storyNameLabel1;
/* 4016:     */   private NameLabel storyNameLabel2;
/* 4017:     */   private JMenuItem currentMenuItem;
/* 4018:     */   private JPanel personalGuiPanel;
/* 4019:     */   private JPanel personalButtonPanel;
/* 4020:     */   private File selectedFile;
/* 4021:     */   private TextViewer causalTextView;
/* 4022:     */   private Summarizer summarizer;
/* 4023:     */   private JPanel wiringDiagram;
/* 4024:     */   private JPanel plotDiagram1;
/* 4025:     */   private JPanel plotDiagram2;
/* 4026:     */   private RepViewer repBlockViewer;
/* 4027:     */   private RepViewer repCauseViewer;
/* 4028:     */   private RepViewer repCoerceViewer;
/* 4029:     */   private RepViewer repForceViewer;
/* 4030:     */   private RepViewer repGeometryViewer;
/* 4031:     */   private RepViewer repPathElementViewer;
/* 4032:     */   private RepViewer repPathViewer;
/* 4033:     */   private RepViewer repPlaceViewer;
/* 4034:     */   private RepViewer repRoleViewer;
/* 4035:     */   private RepViewer repActionViewer;
/* 4036:     */   private RepViewer repBeliefViewer;
/* 4037:     */   private RepViewer repSocialViewer;
/* 4038:     */   private RepViewer repMoodViewer;
/* 4039:     */   private RepViewer repJobViewer;
/* 4040:     */   private RepViewer repPropertyViewer;
/* 4041:     */   private RepViewer repComparisonViewer;
/* 4042:     */   private RepViewer repTimeViewer;
/* 4043:     */   private RepViewer repTrajectoryViewer;
/* 4044:     */   private RepViewer repTransitionViewer;
/* 4045:     */   private RepViewer repTransferViewer;
/* 4046:     */   private TextBox textBox;
/* 4047:     */   private MentalModel mentalModel1;
/* 4048:     */   private MentalModel mentalModel2;
/* 4049:     */   private StoryProcessor storyProcessorSimulation;
/* 4050:     */   private StoryViewer storyViewer1;
/* 4051:     */   private StoryViewer storyViewer2;
/* 4052:     */   private StoryViewer storyViewerForGenesisTesting;
/* 4053:     */   private StoryViewer instantiatedConceptViewer1;
/* 4054:     */   private StoryViewer instantiatedConceptViewer2;
/* 4055:     */   private StoryViewer conceptViewer1;
/* 4056:     */   private StoryViewer conceptViewer2;
/* 4057:     */   private ReflectionBar instRulePlotUnitBar1;
/* 4058:     */   private ReflectionBar instRulePlotUnitBar2;
/* 4059:     */   private ReflectionBar rulePlotUnitBar1;
/* 4060:     */   private ReflectionBar rulePlotUnitBar2;
/* 4061:     */   private AlignmentViewer alignmentViewer;
/* 4062:     */   private CharacterViewer characterViewer;
/* 4063:     */   private TraitViewer traitViewer;
/* 4064:     */   private MasterPanel masterPanel;
/* 4065:     */   private StandardPanel standardPanel;
/* 4066:     */   MindsEyeProcessor mindsEyeProcessor;
/* 4067:     */   private StoryThreadingViewer storyThreadingViewer;
/* 4068:     */   private AlignmentProcessor alignmentProcessor;
/* 4069:     */   private ClusterProcessor clusterProcessor;
/* 4070:     */   private GrandCentralStation grandCentralStation;
/* 4071:     */   private DisgustingMoebiusTranslator disgustingMoebiusTranslator;
/* 4072:     */   EntityExpert entityExpert;
/* 4073:     */   
/* 4074:     */   public StoryThreadingProcessor getStoryThreadingProcessor()
/* 4075:     */   {
/* 4076:4255 */     if (this.storyThreadingProcessor == null) {
/* 4077:4256 */       this.storyThreadingProcessor = new StoryThreadingProcessor();
/* 4078:     */     }
/* 4079:4258 */     return this.storyThreadingProcessor;
/* 4080:     */   }
/* 4081:     */   
/* 4082:     */   public ClusterProcessor getClusterProcessor()
/* 4083:     */   {
/* 4084:4264 */     if (this.clusterProcessor == null) {
/* 4085:4265 */       this.clusterProcessor = new ClusterProcessor();
/* 4086:     */     }
/* 4087:4267 */     return this.clusterProcessor;
/* 4088:     */   }
/* 4089:     */   
/* 4090:     */   public GrandCentralStation getGrandCentralStation()
/* 4091:     */   {
/* 4092:4275 */     if (this.grandCentralStation == null)
/* 4093:     */     {
/* 4094:4276 */       this.grandCentralStation = new GrandCentralStation();
/* 4095:4277 */       this.grandCentralStation.setName("Grand Central Station");
/* 4096:     */     }
/* 4097:4279 */     return this.grandCentralStation;
/* 4098:     */   }
/* 4099:     */   
/* 4100:     */   public DisgustingMoebiusTranslator getDisgustingMoebiusTranslator()
/* 4101:     */   {
/* 4102:4285 */     if (this.disgustingMoebiusTranslator == null)
/* 4103:     */     {
/* 4104:4286 */       this.disgustingMoebiusTranslator = new DisgustingMoebiusTranslator();
/* 4105:4287 */       this.disgustingMoebiusTranslator.setName("IMPACT to Genesis Translator");
/* 4106:     */     }
/* 4107:4289 */     return this.disgustingMoebiusTranslator;
/* 4108:     */   }
/* 4109:     */   
/* 4110:     */   public EntityExpert getEntityExpert()
/* 4111:     */   {
/* 4112:4295 */     if (this.entityExpert == null) {
/* 4113:4296 */       this.entityExpert = new EntityExpert();
/* 4114:     */     }
/* 4115:4298 */     return this.entityExpert;
/* 4116:     */   }
/* 4117:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.GenesisGetters
 * JD-Core Version:    0.7.0.1
 */