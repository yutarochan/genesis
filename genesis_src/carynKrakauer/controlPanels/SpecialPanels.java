/*  1:   */ package carynKrakauer.controlPanels;
/*  2:   */ 
/*  3:   */ import carynKrakauer.SimilarityProcessor;
/*  4:   */ import java.awt.BorderLayout;
/*  5:   */ import javax.swing.JPanel;
/*  6:   */ import javax.swing.JTabbedPane;
/*  7:   */ 
/*  8:   */ public class SpecialPanels
/*  9:   */   extends JPanel
/* 10:   */ {
/* 11:   */   FeaturePanel featurePanel;
/* 12:   */   VisualizationPanel visualize_panel;
/* 13:   */   private JTabbedPane tabbedPane;
/* 14:   */   
/* 15:   */   public SpecialPanels(SimilarityProcessor similarityProcessor)
/* 16:   */   {
/* 17:21 */     setLayout(new BorderLayout());
/* 18:   */     
/* 19:23 */     this.tabbedPane = new JTabbedPane();
/* 20:24 */     add(this.tabbedPane, "Center");
/* 21:   */     
/* 22:26 */     this.featurePanel = new FeaturePanel(similarityProcessor);
/* 23:27 */     this.tabbedPane.addTab("Feature Matcher", null, this.featurePanel, 
/* 24:28 */       "Match Features");
/* 25:   */     
/* 26:30 */     this.visualize_panel = new VisualizationPanel(similarityProcessor);
/* 27:31 */     this.tabbedPane.addTab("Order Matcher", null, this.visualize_panel, "Visualize order matches");
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.SpecialPanels
 * JD-Core Version:    0.7.0.1
 */