/*  1:   */ package carynKrakauer.controlPanels;
/*  2:   */ 
/*  3:   */ import carynKrakauer.SimilarityProcessor;
/*  4:   */ import java.awt.BorderLayout;
/*  5:   */ import javax.swing.JComboBox;
/*  6:   */ import javax.swing.JPanel;
/*  7:   */ import javax.swing.JTabbedPane;
/*  8:   */ 
/*  9:   */ public class MatchingPanels
/* 10:   */   extends JPanel
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 1L;
/* 13:   */   private MatchesPanel matchesPanel;
/* 14:   */   private GeneratedMatchesPanel sizeNMatchesPanel;
/* 15:   */   private JTabbedPane tabbedPane;
/* 16:   */   
/* 17:   */   public MatchingPanels(SimilarityProcessor similarityProcessor, JComboBox story_selector)
/* 18:   */   {
/* 19:29 */     setLayout(new BorderLayout());
/* 20:   */     
/* 21:31 */     this.tabbedPane = new JTabbedPane();
/* 22:   */     
/* 23:   */ 
/* 24:34 */     this.matchesPanel = new MatchesPanel(similarityProcessor, story_selector);
/* 25:35 */     this.tabbedPane.addTab("Defined Patterns", null, this.matchesPanel, 
/* 26:36 */       "Compare with defined concept patterns");
/* 27:   */     
/* 28:38 */     this.sizeNMatchesPanel = new GeneratedMatchesPanel(similarityProcessor, story_selector);
/* 29:39 */     this.tabbedPane.addTab("Generated Patterns", null, 
/* 30:40 */       this.sizeNMatchesPanel, 
/* 31:41 */       "Compare with generated concept patterns.");
/* 32:   */     
/* 33:43 */     add(this.tabbedPane, "Center");
/* 34:   */   }
/* 35:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.MatchingPanels
 * JD-Core Version:    0.7.0.1
 */