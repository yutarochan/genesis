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
/* 15:   */ public class GeneratedGridPanel
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
/* 30:   */   public GeneratedGridPanel(SimilarityProcessor similarityProcessor)
/* 31:   */   {
/* 32:34 */     this.similarityProcessor = similarityProcessor;
/* 33:35 */     this.memory = similarityProcessor.getMemory();
/* 34:36 */     setLayout(new BorderLayout());
/* 35:   */     
/* 36:   */ 
/* 37:39 */     this.menuPanel = new JPanel();
/* 38:40 */     this.menuPanel.setLayout(new BorderLayout());
/* 39:   */     
/* 40:42 */     this.model = new SpinnerNumberModel(2, 
/* 41:43 */       2, 
/* 42:44 */       2147483647, 
/* 43:45 */       1);
/* 44:46 */     this.spinner = new JSpinner(this.model);
/* 45:47 */     this.menuPanel.add(this.spinner);
/* 46:   */     
/* 47:49 */     this.displayPlotComparisons = new JButton("Plot Grid");
/* 48:50 */     this.displayPlotComparisons.addActionListener(this);
/* 49:51 */     this.menuPanel.add(this.displayPlotComparisons, "East");
/* 50:52 */     add(this.menuPanel, "First");
/* 51:   */     
/* 52:   */ 
/* 53:55 */     this.visualizations = new JPanel();
/* 54:56 */     this.visualizations.setLayout(new GridLayout(2, 1));
/* 55:57 */     this.visualPanel = new GeneratedGridVisualPanel(this.memory, 2, false, false, true);
/* 56:58 */     this.keywordPanel = new KeywordVisualPanel(this.memory, false, true);
/* 57:59 */     this.visualizations.add(this.visualPanel);
/* 58:60 */     this.visualizations.add(this.keywordPanel);
/* 59:   */     
/* 60:62 */     add(this.visualizations, "Center");
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void actionPerformed(ActionEvent event)
/* 64:   */   {
/* 65:67 */     if (event.getSource() == this.displayPlotComparisons) {
/* 66:68 */       updateComparisons();
/* 67:   */     }
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void updateComparisons()
/* 71:   */   {
/* 72:73 */     this.visualPanel.update((Integer)this.spinner.getValue());
/* 73:   */     
/* 74:75 */     this.keywordPanel.updateUI();
/* 75:76 */     this.keywordPanel.repaint();
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.GeneratedGridPanel
 * JD-Core Version:    0.7.0.1
 */