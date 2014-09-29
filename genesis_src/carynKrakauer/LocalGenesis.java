/*   1:    */ package carynKrakauer;
/*   2:    */ 
/*   3:    */ import carynKrakauer.controlPanels.GridPanelDisplay;
/*   4:    */ import carynKrakauer.controlPanels.MatchingPanels;
/*   5:    */ import carynKrakauer.controlPanels.SpecialPanels;
/*   6:    */ import connections.Connections;
/*   7:    */ import genesis.Genesis;
/*   8:    */ import java.awt.BorderLayout;
/*   9:    */ import javax.swing.JComboBox;
/*  10:    */ import javax.swing.JPanel;
/*  11:    */ import javax.swing.JTabbedPane;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class LocalGenesis
/*  15:    */   extends Genesis
/*  16:    */ {
/*  17: 26 */   String default_story = "-- Select a Story --";
/*  18:    */   SimilarityProcessor similarityProcessor;
/*  19:    */   JPanel myPanel;
/*  20:    */   JComboBox<String> story_selector;
/*  21:    */   MatchingPanels matchingPanels;
/*  22:    */   GridPanelDisplay gridPanels;
/*  23:    */   SpecialPanels specialPanels;
/*  24:    */   
/*  25:    */   public JPanel getMyPanel()
/*  26:    */   {
/*  27: 40 */     if (this.myPanel == null) {
/*  28: 41 */       this.myPanel = getSimilarityViewer();
/*  29:    */     }
/*  30: 43 */     return this.myPanel;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void updateJComboBox(String new_story)
/*  34:    */   {
/*  35: 48 */     this.story_selector.addItem(new_story);
/*  36: 49 */     this.story_selector.updateUI();
/*  37:    */   }
/*  38:    */   
/*  39:    */   private void setupSimilarityGuiPanel()
/*  40:    */   {
/*  41: 53 */     getMyPanel();
/*  42:    */     
/*  43: 55 */     this.myPanel.setLayout(new BorderLayout());
/*  44:    */     
/*  45: 57 */     JTabbedPane tabbed_pane = new JTabbedPane();
/*  46:    */     
/*  47: 59 */     this.matchingPanels = new MatchingPanels(this.similarityProcessor, this.story_selector);
/*  48: 60 */     tabbed_pane.addTab("Concept Pattern Matchers", null, 
/*  49: 61 */       this.matchingPanels, 
/*  50: 62 */       "Match Concept Patterns");
/*  51:    */     
/*  52: 64 */     this.myPanel.add(tabbed_pane, "Center");
/*  53:    */     
/*  54: 66 */     this.gridPanels = new GridPanelDisplay(this.similarityProcessor);
/*  55: 67 */     tabbed_pane.addTab("Concept Grids", null, 
/*  56: 68 */       this.gridPanels, 
/*  57: 69 */       "View Concept Grids");
/*  58:    */     
/*  59:    */ 
/*  60: 72 */     this.specialPanels = new SpecialPanels(this.similarityProcessor);
/*  61: 73 */     tabbed_pane.addTab("Special", null, 
/*  62: 74 */       this.specialPanels, 
/*  63: 75 */       "Special Panels");
/*  64:    */   }
/*  65:    */   
/*  66:    */   public LocalGenesis()
/*  67:    */   {
/*  68: 80 */     Mark.say(new Object[] {"Caryn's LocalGenesis's constructor" });
/*  69: 81 */     getSimilarityProcessor();
/*  70: 82 */     getSimilarityViewer();
/*  71: 83 */     setupSimilarityGuiPanel();
/*  72:    */     
/*  73:    */ 
/*  74: 86 */     Connections.wire("complete story events port", getMentalModel1(), getSimilarityProcessor());
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78: 90 */     Connections.wire("Reflection analysis port", getMentalModel1(), getSimilarityProcessor());
/*  79:    */   }
/*  80:    */   
/*  81:    */   public SimilarityProcessor getSimilarityProcessor()
/*  82:    */   {
/*  83: 95 */     if (this.similarityProcessor == null) {
/*  84: 96 */       this.similarityProcessor = new SimilarityProcessor();
/*  85:    */     }
/*  86: 98 */     return this.similarityProcessor;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public static void main(String[] args)
/*  90:    */   {
/*  91:102 */     LocalGenesis myGenesis = new LocalGenesis();
/*  92:103 */     myGenesis.startInFrame();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void updateComparisons()
/*  96:    */   {
/*  97:107 */     this.gridPanels.updateComparisons();
/*  98:    */   }
/*  99:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */