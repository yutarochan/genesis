/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.ReflectionLevelMemory;
/*   4:    */ import carynKrakauer.SimilarityProcessor;
/*   5:    */ import java.awt.GridLayout;
/*   6:    */ import javax.swing.JPanel;
/*   7:    */ import javax.swing.JTabbedPane;
/*   8:    */ 
/*   9:    */ public class GridPanelDisplay
/*  10:    */   extends JPanel
/*  11:    */ {
/*  12:    */   private static final long serialVersionUID = 1L;
/*  13:    */   SimilarityProcessor localProcessor;
/*  14:    */   ReflectionLevelMemory memory;
/*  15:    */   GridVisualPanel defGrid;
/*  16:    */   GeneratedGridVisualPanel genGrid;
/*  17:    */   GeneratedGridVisualPanel vecGenGrid;
/*  18:    */   KeywordVisualPanel keywordGrid;
/*  19:    */   HumanGridVisualPanel humanGrid;
/*  20:    */   InOrderGridPanel inOrderGrid;
/*  21:    */   GeneratedInOrderGridPanel genInOrderGrid;
/*  22:    */   GridVisualPanel defGrid2;
/*  23:    */   GeneratedGridVisualPanel genGrid2;
/*  24:    */   GeneratedGridVisualPanel vecGenGrid2;
/*  25:    */   KeywordVisualPanel keywordGrid2;
/*  26:    */   HumanGridVisualPanel humanGrid2;
/*  27:    */   InOrderGridPanel inOrderGrid2;
/*  28:    */   GeneratedInOrderGridPanel genInOrderGrid2;
/*  29:    */   JTabbedPane topPane;
/*  30:    */   JTabbedPane botPane;
/*  31:    */   public static final String DEFINED_GRID = "Defined Grid";
/*  32:    */   
/*  33:    */   public GridPanelDisplay(SimilarityProcessor localProcessor)
/*  34:    */   {
/*  35: 58 */     this.localProcessor = localProcessor;
/*  36: 59 */     this.memory = localProcessor.getMemory();
/*  37: 60 */     setLayout(new GridLayout(2, 1));
/*  38:    */     
/*  39: 62 */     this.topPane = new JTabbedPane();
/*  40: 63 */     this.botPane = new JTabbedPane();
/*  41: 64 */     add(this.topPane);
/*  42: 65 */     add(this.botPane);
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46: 69 */     this.defGrid = new GridVisualPanel(this.memory, false, true);
/*  47: 70 */     this.genGrid = new GeneratedGridVisualPanel(this.memory, 2, false, false, true);
/*  48: 71 */     this.vecGenGrid = new GeneratedGridVisualPanel(this.memory, 2, true, false, true);
/*  49: 72 */     this.keywordGrid = new KeywordVisualPanel(this.memory, false, true);
/*  50: 73 */     this.humanGrid = new HumanGridVisualPanel(this.memory, false, true);
/*  51: 74 */     this.inOrderGrid = new InOrderGridPanel(this.memory, false, true);
/*  52: 75 */     this.genInOrderGrid = new GeneratedInOrderGridPanel(this.memory, false, true, 2);
/*  53:    */     
/*  54: 77 */     this.defGrid2 = new GridVisualPanel(this.memory, false, true);
/*  55: 78 */     this.genGrid2 = new GeneratedGridVisualPanel(this.memory, 2, false, false, true);
/*  56: 79 */     this.vecGenGrid2 = new GeneratedGridVisualPanel(this.memory, 2, true, false, true);
/*  57: 80 */     this.keywordGrid2 = new KeywordVisualPanel(this.memory, false, true);
/*  58: 81 */     this.humanGrid2 = new HumanGridVisualPanel(this.memory, false, true);
/*  59: 82 */     this.inOrderGrid2 = new InOrderGridPanel(this.memory, false, true);
/*  60: 83 */     this.genInOrderGrid2 = new GeneratedInOrderGridPanel(this.memory, false, true, 2);
/*  61:    */     
/*  62: 85 */     this.topPane.addTab("Keyword Grid", this.keywordGrid);
/*  63: 86 */     this.topPane.addTab("Defined Grid", this.defGrid);
/*  64: 87 */     this.topPane.addTab("Generated Grid", this.genGrid);
/*  65: 88 */     this.topPane.addTab("Genterated Grid - VectorMatch", this.vecGenGrid);
/*  66: 89 */     this.topPane.addTab("Human Grid", this.humanGrid);
/*  67: 90 */     this.topPane.addTab("In-Order Grid", this.inOrderGrid);
/*  68: 91 */     this.topPane.addTab("Generated In-Order Grid", this.genInOrderGrid);
/*  69:    */     
/*  70: 93 */     this.botPane.addTab("Keyword Grid", this.keywordGrid2);
/*  71: 94 */     this.botPane.addTab("Defined Grid", this.defGrid2);
/*  72: 95 */     this.botPane.addTab("Generated Grid", this.genGrid2);
/*  73: 96 */     this.botPane.addTab("Genterated Grid - VectorMatch", this.vecGenGrid2);
/*  74: 97 */     this.botPane.addTab("Human Grid", this.humanGrid2);
/*  75: 98 */     this.botPane.addTab("In-Order Grid", this.inOrderGrid2);
/*  76: 99 */     this.botPane.addTab("Generated In-Order Grid", this.genInOrderGrid2);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void updateComparisons()
/*  80:    */   {
/*  81:103 */     this.defGrid.updateUI();
/*  82:104 */     this.defGrid.repaint();
/*  83:    */     
/*  84:106 */     this.genGrid.updateUI();
/*  85:107 */     this.genGrid.repaint();
/*  86:    */     
/*  87:109 */     this.vecGenGrid.updateUI();
/*  88:110 */     this.vecGenGrid.repaint();
/*  89:    */     
/*  90:112 */     this.keywordGrid.updateUI();
/*  91:113 */     this.keywordGrid.repaint();
/*  92:    */     
/*  93:115 */     this.humanGrid.updateUI();
/*  94:116 */     this.humanGrid.repaint();
/*  95:    */     
/*  96:118 */     this.inOrderGrid.updateUI();
/*  97:119 */     this.inOrderGrid.repaint();
/*  98:    */     
/*  99:121 */     this.defGrid2.updateUI();
/* 100:122 */     this.defGrid2.repaint();
/* 101:    */     
/* 102:124 */     this.genGrid2.updateUI();
/* 103:125 */     this.genGrid2.repaint();
/* 104:    */     
/* 105:127 */     this.vecGenGrid2.updateUI();
/* 106:128 */     this.vecGenGrid2.repaint();
/* 107:    */     
/* 108:130 */     this.keywordGrid2.updateUI();
/* 109:131 */     this.keywordGrid2.repaint();
/* 110:    */     
/* 111:133 */     this.humanGrid2.updateUI();
/* 112:134 */     this.humanGrid2.repaint();
/* 113:    */     
/* 114:136 */     this.inOrderGrid2.updateUI();
/* 115:137 */     this.inOrderGrid2.repaint();
/* 116:    */   }
/* 117:    */   
/* 118:    */   public JTabbedPane getTopPane()
/* 119:    */   {
/* 120:141 */     return this.topPane;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public JTabbedPane getBotPane()
/* 124:    */   {
/* 125:145 */     return this.botPane;
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.GridPanelDisplay
 * JD-Core Version:    0.7.0.1
 */