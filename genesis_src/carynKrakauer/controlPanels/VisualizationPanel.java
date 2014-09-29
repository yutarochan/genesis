/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.SimilarityProcessor;
/*   4:    */ import java.awt.BorderLayout;
/*   5:    */ import java.awt.event.ActionEvent;
/*   6:    */ import java.awt.event.ActionListener;
/*   7:    */ import javax.swing.BoxLayout;
/*   8:    */ import javax.swing.JButton;
/*   9:    */ import javax.swing.JComboBox;
/*  10:    */ import javax.swing.JLabel;
/*  11:    */ import javax.swing.JPanel;
/*  12:    */ import javax.swing.JTabbedPane;
/*  13:    */ 
/*  14:    */ public class VisualizationPanel
/*  15:    */   extends JPanel
/*  16:    */   implements ActionListener
/*  17:    */ {
/*  18:    */   private SimilarityProcessor similarityProcessor;
/*  19:    */   private JPanel choicePanel;
/*  20:    */   private JTabbedPane tabbedVisualizationPane;
/*  21:    */   private FeatureMatchesVisualPanel featureMatchesPanel;
/*  22:    */   private InOrderMatchesVisualPanel inOrderMatchesPanel;
/*  23:    */   private RarityVisualPanel rarityPanel;
/*  24:    */   private JComboBox<String> first_story_box;
/*  25:    */   private JComboBox<String> second_story_box;
/*  26:    */   private JButton compare_button;
/*  27: 34 */   private String default_story = "None";
/*  28:    */   private JPanel visualizationPane;
/*  29:    */   
/*  30:    */   public VisualizationPanel(SimilarityProcessor similarityProcessor)
/*  31:    */   {
/*  32: 42 */     this.similarityProcessor = similarityProcessor;
/*  33:    */     
/*  34: 44 */     setupPanel();
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setupPanel()
/*  38:    */   {
/*  39: 48 */     setLayout(new BorderLayout());
/*  40:    */     
/*  41: 50 */     this.choicePanel = new JPanel();
/*  42:    */     
/*  43:    */ 
/*  44: 53 */     this.first_story_box = new JComboBox();
/*  45: 54 */     this.first_story_box.addItem(this.default_story);
/*  46: 55 */     this.first_story_box.addActionListener(this);
/*  47: 56 */     this.similarityProcessor.addComboBox(this.first_story_box);
/*  48:    */     
/*  49: 58 */     this.second_story_box = new JComboBox();
/*  50: 59 */     this.second_story_box.addItem(this.default_story);
/*  51: 60 */     this.second_story_box.addActionListener(this);
/*  52: 61 */     this.similarityProcessor.addComboBox(this.second_story_box);
/*  53:    */     
/*  54: 63 */     this.choicePanel.add(new JLabel("First story:"));
/*  55: 64 */     this.choicePanel.add(this.first_story_box);
/*  56: 65 */     this.choicePanel.add(new JLabel("Second story:"));
/*  57: 66 */     this.choicePanel.add(this.second_story_box);
/*  58:    */     
/*  59: 68 */     JPanel compare_pane = new JPanel();
/*  60: 69 */     compare_pane.setLayout(new BoxLayout(compare_pane, 3));
/*  61: 70 */     this.compare_button = new JButton("Compare!");
/*  62: 71 */     this.compare_button.addActionListener(this);
/*  63: 72 */     compare_pane.add(this.compare_button);
/*  64:    */     
/*  65: 74 */     this.choicePanel.add(compare_pane);
/*  66:    */     
/*  67: 76 */     add(this.choicePanel, "First");
/*  68:    */     
/*  69:    */ 
/*  70: 79 */     this.featureMatchesPanel = new FeatureMatchesVisualPanel(this.similarityProcessor);
/*  71: 80 */     this.inOrderMatchesPanel = new InOrderMatchesVisualPanel(this.similarityProcessor);
/*  72: 81 */     this.rarityPanel = new RarityVisualPanel(this.similarityProcessor);
/*  73:    */     
/*  74:    */ 
/*  75: 84 */     this.tabbedVisualizationPane = new JTabbedPane();
/*  76: 85 */     this.tabbedVisualizationPane.addTab("Feature Match", null, this.featureMatchesPanel, "Match stories by feature.");
/*  77: 86 */     this.tabbedVisualizationPane.addTab("In Order Match", null, this.inOrderMatchesPanel, "Match Stories by Feature Order");
/*  78: 87 */     this.tabbedVisualizationPane.addTab("Feature Rarity", null, this.rarityPanel, "Rarity of features");
/*  79:    */     
/*  80: 89 */     add(this.tabbedVisualizationPane, "Center");
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void actionPerformed(ActionEvent e)
/*  84:    */   {
/*  85: 94 */     if (e.getSource().equals(this.compare_button))
/*  86:    */     {
/*  87: 95 */       String story1 = (String)this.first_story_box.getSelectedItem();
/*  88: 96 */       String story2 = (String)this.second_story_box.getSelectedItem();
/*  89: 98 */       if ((story1.equals(this.default_story)) || (story2.equals(this.default_story))) {
/*  90: 99 */         return;
/*  91:    */       }
/*  92:101 */       this.inOrderMatchesPanel.visualizeMatches(story1, story2);
/*  93:102 */       this.featureMatchesPanel.visualizeMatches(story1, story2);
/*  94:103 */       this.rarityPanel.visualizeMatches(story1, story2);
/*  95:    */     }
/*  96:    */   }
/*  97:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.VisualizationPanel
 * JD-Core Version:    0.7.0.1
 */