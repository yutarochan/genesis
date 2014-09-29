/*  1:   */ package carynKrakauer.controlPanels;
/*  2:   */ 
/*  3:   */ import carynKrakauer.ReflectionLevelMemory;
/*  4:   */ import carynKrakauer.SimilarityProcessor;
/*  5:   */ import java.awt.BorderLayout;
/*  6:   */ import java.awt.GridLayout;
/*  7:   */ import java.awt.event.ActionEvent;
/*  8:   */ import java.awt.event.ActionListener;
/*  9:   */ import javax.swing.JButton;
/* 10:   */ import javax.swing.JPanel;
/* 11:   */ import javax.swing.JSpinner;
/* 12:   */ import javax.swing.SpinnerModel;
/* 13:   */ import javax.swing.SpinnerNumberModel;
/* 14:   */ 
/* 15:   */ public class GeneratedGridlPanelVector
/* 16:   */   extends JPanel
/* 17:   */   implements ActionListener
/* 18:   */ {
/* 19:   */   private static final long serialVersionUID = 1L;
/* 20:   */   SimilarityProcessor similarityProcessor;
/* 21:   */   ReflectionLevelMemory memory;
/* 22:   */   JPanel menuPanel;
/* 23:   */   JButton displayPlotComparisons;
/* 24:   */   JPanel visualizations;
/* 25:   */   GeneratedGridVisualPanel visualPanel;
/* 26:   */   KeywordVisualPanel keywordPanel;
/* 27:   */   SpinnerModel model;
/* 28:   */   JSpinner spinner;
/* 29:   */   
/* 30:   */   public GeneratedGridlPanelVector(SimilarityProcessor localProcessor)
/* 31:   */   {
/* 32:32 */     this.similarityProcessor = localProcessor;
/* 33:33 */     this.memory = localProcessor.getMemory();
/* 34:34 */     setLayout(new BorderLayout());
/* 35:   */     
/* 36:   */ 
/* 37:37 */     this.menuPanel = new JPanel();
/* 38:38 */     this.menuPanel.setLayout(new BorderLayout());
/* 39:   */     
/* 40:40 */     this.model = new SpinnerNumberModel(2, 
/* 41:41 */       2, 
/* 42:42 */       2147483647, 
/* 43:43 */       1);
/* 44:44 */     this.spinner = new JSpinner(this.model);
/* 45:45 */     this.menuPanel.add(this.spinner);
/* 46:   */     
/* 47:47 */     this.displayPlotComparisons = new JButton("Plot Grid");
/* 48:48 */     this.displayPlotComparisons.addActionListener(this);
/* 49:49 */     this.menuPanel.add(this.displayPlotComparisons, "East");
/* 50:50 */     add(this.menuPanel, "First");
/* 51:   */     
/* 52:   */ 
/* 53:53 */     this.visualizations = new JPanel();
/* 54:54 */     this.visualizations.setLayout(new GridLayout(2, 1));
/* 55:55 */     this.visualPanel = new GeneratedGridVisualPanel(this.memory, 2, true, false, true);
/* 56:56 */     this.keywordPanel = new KeywordVisualPanel(this.memory, false, true);
/* 57:57 */     this.visualizations.add(this.visualPanel);
/* 58:58 */     this.visualizations.add(this.keywordPanel);
/* 59:   */     
/* 60:60 */     add(this.visualizations, "Center");
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void actionPerformed(ActionEvent event)
/* 64:   */   {
/* 65:65 */     if (event.getSource() == this.displayPlotComparisons) {
/* 66:66 */       updateComparisons();
/* 67:   */     }
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void updateComparisons()
/* 71:   */   {
/* 72:71 */     this.visualPanel.update((Integer)this.spinner.getValue());
/* 73:   */     
/* 74:73 */     this.keywordPanel.updateUI();
/* 75:74 */     this.keywordPanel.repaint();
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.GeneratedGridlPanelVector
 * JD-Core Version:    0.7.0.1
 */