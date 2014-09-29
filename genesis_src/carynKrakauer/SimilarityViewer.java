/*  1:   */ package carynKrakauer;
/*  2:   */ 
/*  3:   */ import carynKrakauer.controlPanels.GridPanelDisplay;
/*  4:   */ import carynKrakauer.controlPanels.MatchingPanels;
/*  5:   */ import carynKrakauer.controlPanels.SpecialPanels;
/*  6:   */ import java.awt.BorderLayout;
/*  7:   */ import javax.swing.JComboBox;
/*  8:   */ import javax.swing.JPanel;
/*  9:   */ import javax.swing.JTabbedPane;
/* 10:   */ 
/* 11:   */ public class SimilarityViewer
/* 12:   */   extends JPanel
/* 13:   */ {
/* 14:18 */   public static String CONCEPT_GRID_LABEL = "Concept grids";
/* 15:20 */   String default_story = "-- Select a Story --";
/* 16:   */   JComboBox story_selector;
/* 17:   */   MatchingPanels matchingPanels;
/* 18:   */   GridPanelDisplay gridPanels;
/* 19:   */   SpecialPanels specialPanels;
/* 20:   */   JTabbedPane tabbedPane;
/* 21:   */   
/* 22:   */   public JTabbedPane getTabbedPane()
/* 23:   */   {
/* 24:34 */     if (this.tabbedPane == null) {
/* 25:35 */       this.tabbedPane = new JTabbedPane();
/* 26:   */     }
/* 27:37 */     return this.tabbedPane;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public SimilarityViewer()
/* 31:   */   {
/* 32:41 */     setLayout(new BorderLayout());
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void initialize(SimilarityProcessor similarityProcessor)
/* 36:   */   {
/* 37:45 */     this.matchingPanels = new MatchingPanels(similarityProcessor, this.story_selector);
/* 38:46 */     getTabbedPane().addTab("Concept Pattern Matchers", null, this.matchingPanels, "Match Concept Patterns");
/* 39:   */     
/* 40:48 */     add(getTabbedPane(), "Center");
/* 41:   */     
/* 42:50 */     this.gridPanels = new GridPanelDisplay(similarityProcessor);
/* 43:51 */     getTabbedPane().addTab(CONCEPT_GRID_LABEL, null, this.gridPanels, "View Concept Grids");
/* 44:   */     
/* 45:53 */     this.specialPanels = new SpecialPanels(similarityProcessor);
/* 46:54 */     getTabbedPane().addTab("Special", null, this.specialPanels, "Special Panels");
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void updateComparisons()
/* 50:   */   {
/* 51:58 */     this.gridPanels.updateComparisons();
/* 52:   */   }
/* 53:   */   
/* 54:   */   public GridPanelDisplay getGridPanels()
/* 55:   */   {
/* 56:62 */     return this.gridPanels;
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.SimilarityViewer
 * JD-Core Version:    0.7.0.1
 */