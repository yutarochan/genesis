/*  1:   */ package carynKrakauer.controlPanels;
/*  2:   */ 
/*  3:   */ import carynKrakauer.ReflectionLevelMemory;
/*  4:   */ import carynKrakauer.SimilarityProcessor;
/*  5:   */ import java.awt.BorderLayout;
/*  6:   */ import java.awt.GridLayout;
/*  7:   */ import javax.swing.JPanel;
/*  8:   */ 
/*  9:   */ public class GridPanel
/* 10:   */   extends JPanel
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 1L;
/* 13:   */   SimilarityProcessor similarityProcessor;
/* 14:   */   ReflectionLevelMemory memory;
/* 15:   */   JPanel visualizations;
/* 16:   */   GridVisualPanel visualPanel;
/* 17:   */   KeywordVisualPanel keywordPanel;
/* 18:   */   
/* 19:   */   public GridPanel(SimilarityProcessor similarityProcessor)
/* 20:   */   {
/* 21:26 */     this.similarityProcessor = similarityProcessor;
/* 22:27 */     this.memory = similarityProcessor.getMemory();
/* 23:28 */     setLayout(new BorderLayout());
/* 24:   */     
/* 25:   */ 
/* 26:31 */     this.visualizations = new JPanel();
/* 27:32 */     this.visualizations.setLayout(new GridLayout(2, 1));
/* 28:33 */     this.visualPanel = new GridVisualPanel(this.memory, false, true);
/* 29:34 */     this.keywordPanel = new KeywordVisualPanel(this.memory, false, true);
/* 30:35 */     this.visualizations.add(this.visualPanel);
/* 31:36 */     this.visualizations.add(this.keywordPanel);
/* 32:   */     
/* 33:38 */     add(this.visualizations, "Center");
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void updateComparisons()
/* 37:   */   {
/* 38:42 */     this.visualPanel.updateUI();
/* 39:43 */     this.visualPanel.repaint();
/* 40:   */     
/* 41:45 */     this.keywordPanel.updateUI();
/* 42:46 */     this.keywordPanel.repaint();
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.GridPanel
 * JD-Core Version:    0.7.0.1
 */