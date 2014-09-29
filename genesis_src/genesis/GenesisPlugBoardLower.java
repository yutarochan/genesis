/*   1:    */ package genesis;
/*   2:    */ 
/*   3:    */ import carynKrakauer.SimilarityViewer;
/*   4:    */ import carynKrakauer.controlPanels.GridPanelDisplay;
/*   5:    */ import connections.Connections;
/*   6:    */ import java.awt.event.ActionEvent;
/*   7:    */ import java.awt.event.ActionListener;
/*   8:    */ import javax.swing.JRadioButton;
/*   9:    */ import javax.swing.JTabbedPane;
/*  10:    */ import parameters.Radio;
/*  11:    */ import start.Start;
/*  12:    */ import start.StartPreprocessor;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class GenesisPlugBoardLower
/*  16:    */   extends GenesisPlugBoardUpper
/*  17:    */ {
/*  18:    */   protected void initializeWiring()
/*  19:    */   {
/*  20: 37 */     super.initializeWiring();
/*  21: 38 */     initializeTalker();
/*  22: 39 */     initializePanelSetters();
/*  23: 40 */     initializeButtons();
/*  24: 41 */     initializeBlinkingBoxes();
/*  25: 42 */     initializeMiscellaneous();
/*  26:    */   }
/*  27:    */   
/*  28:    */   private void initializeBlinkingBoxes()
/*  29:    */   {
/*  30: 48 */     getThreadBlinker();
/*  31:    */     
/*  32: 50 */     getRoleBlinker();
/*  33:    */     
/*  34: 52 */     getTrajectoryBlinker();
/*  35: 53 */     getPathElementBlinker();
/*  36: 54 */     getPlaceBlinker();
/*  37: 55 */     getTransitionBlinker();
/*  38: 56 */     getTransferBlinker();
/*  39: 57 */     getCauseBlinker();
/*  40:    */     
/*  41: 59 */     getGoalBlinker();
/*  42: 60 */     getPersuationBlinker();
/*  43: 61 */     getCoercionBlinker();
/*  44:    */     
/*  45: 63 */     getBeliefBlinker();
/*  46: 64 */     getIntentionBlinker();
/*  47: 65 */     getPredictionBlinker();
/*  48:    */     
/*  49: 67 */     getMoodBlinker();
/*  50: 68 */     getPartBlinker();
/*  51: 69 */     getPersonalityBlinker();
/*  52: 70 */     getPropertyBlinker();
/*  53: 71 */     getPossessionBlinker();
/*  54: 72 */     getJobBlinker();
/*  55: 73 */     getSocialBlinker();
/*  56: 74 */     getTimeBlinker();
/*  57: 75 */     getComparisonBlinker();
/*  58: 76 */     getPictureBlinker();
/*  59:    */   }
/*  60:    */   
/*  61:    */   private void initializeTalker()
/*  62:    */   {
/*  63: 82 */     Connections.wire(getTalker(), getResultContainer());
/*  64: 83 */     Mark.say(new Object[] {"Wired up talker" });
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void initializePanelSetters()
/*  68:    */   {
/*  69: 87 */     Connections.wire("imagine", getCommandExpert(), "set bottom panel to imagination", this);
/*  70: 88 */     Connections.wire("Starting", getMentalModel1(), "set left panel to onset", this);
/*  71: 89 */     Connections.wire(getTalker(), "set right panel to results", this);
/*  72: 90 */     Connections.wire("set pane", StartPreprocessor.getStartPreprocessor(), "set pane", this);
/*  73:    */   }
/*  74:    */   
/*  75:    */   private void initializeMiscellaneous()
/*  76:    */   {
/*  77: 94 */     Connections.wire(Start.STAGE_DIRECTION_PORT, StartPreprocessor.getStartPreprocessor(), "reset", getTextEntryBox());
/*  78: 95 */     Connections.wire(StartPreprocessor.TO_TEXT_ENTRY_BOX, StartPreprocessor.getStartPreprocessor(), "primer", getTextEntryBox());
/*  79:    */   }
/*  80:    */   
/*  81:    */   private void initializeButtons()
/*  82:    */   {
/*  83: 99 */     ActionListener l = new ModeActionListener();
/*  84:100 */     Radio.tellStoryButton.addActionListener(l);
/*  85:101 */     Radio.calculateSimilarityButton.addActionListener(l);
/*  86:102 */     Radio.normalModeButton.addActionListener(l);
/*  87:103 */     Radio.alignmentButton.addActionListener(l);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setToNormalMode()
/*  91:    */   {
/*  92:107 */     Mark.say(
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:112 */       new Object[] { "Switching to normal mode" });disconnectSimilarityComputationWires();setBottomPanel("Elaboration graph");setRightPanel("Sources");
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setToSimilarityComputingMode()
/* 101:    */   {
/* 102:115 */     Mark.say(
/* 103:    */     
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:121 */       new Object[] { "Switching to similarity computing mode" });connectSimilarityComputationWires();setBottomPanel("Similarity panel");getSimilarityViewer().getTabbedPane().setSelectedIndex(getSimilarityViewer().getTabbedPane().indexOfTab(SimilarityViewer.CONCEPT_GRID_LABEL));getSimilarityViewer().getGridPanels().getTopPane().setSelectedIndex(getSimilarityViewer().getGridPanels().getTopPane().indexOfTab("Defined Grid"));
/* 109:    */   }
/* 110:    */   
/* 111:    */   class ModeActionListener
/* 112:    */     implements ActionListener
/* 113:    */   {
/* 114:    */     ModeActionListener() {}
/* 115:    */     
/* 116:    */     public void actionPerformed(ActionEvent e)
/* 117:    */     {
/* 118:126 */       if (e.getSource() == Radio.normalModeButton) {
/* 119:127 */         GenesisPlugBoardLower.this.setToNormalMode();
/* 120:129 */       } else if (e.getSource() == Radio.tellStoryButton) {
/* 121:130 */         GenesisPlugBoardLower.this.setToStoryTellingMode();
/* 122:132 */       } else if (e.getSource() == Radio.calculateSimilarityButton) {
/* 123:133 */         GenesisPlugBoardLower.this.setToSimilarityComputingMode();
/* 124:135 */       } else if (e.getSource() == Radio.alignmentButton) {
/* 125:136 */         GenesisPlugBoardLower.this.setToAlignmentMode();
/* 126:    */       }
/* 127:    */     }
/* 128:    */   }
/* 129:    */   
/* 130:    */   private void connectSimilarityComputationWires()
/* 131:    */   {
/* 132:143 */     Connections.wire("complete story events port", getMentalModel1(), getSimilarityProcessor());
/* 133:144 */     Connections.wire("Reflection analysis port", getMentalModel1(), getSimilarityProcessor());
/* 134:    */   }
/* 135:    */   
/* 136:    */   private void disconnectSimilarityComputationWires()
/* 137:    */   {
/* 138:148 */     Connections.disconnect("complete story events port", getMentalModel1(), getSimilarityProcessor());
/* 139:149 */     Connections.disconnect("Reflection analysis port", getMentalModel1(), getSimilarityProcessor());
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setToStoryTellingMode()
/* 143:    */   {
/* 144:153 */     Mark.say(
/* 145:    */     
/* 146:    */ 
/* 147:    */ 
/* 148:157 */       new Object[] { "Switching to story telling mode" });setRightPanel("Sources");setBottomPanel("Story");
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.GenesisPlugBoardLower
 * JD-Core Version:    0.7.0.1
 */