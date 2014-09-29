/*   1:    */ package matthewFay.StoryThreading;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import ati.ParallelJPanel;
/*   5:    */ import bridge.reps.entities.Entity;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import java.awt.BorderLayout;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Set;
/*  14:    */ import javax.swing.JCheckBox;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.JScrollPane;
/*  17:    */ import javax.swing.JTabbedPane;
/*  18:    */ import javax.swing.JTable;
/*  19:    */ import javax.swing.table.TableColumn;
/*  20:    */ import javax.swing.table.TableColumnModel;
/*  21:    */ import matthewFay.StoryAlignment.TextAreaRenderer;
/*  22:    */ import matthewFay.Utilities.HashMatrix;
/*  23:    */ 
/*  24:    */ public class StoryThreadingViewer
/*  25:    */   extends JPanel
/*  26:    */   implements WiredBox
/*  27:    */ {
/*  28:    */   public static final String COMPARISON_PORT = "COMPARISON PORT";
/*  29:    */   private JTabbedPane tabbedPane;
/*  30:    */   public ParallelJPanel controlPanel;
/*  31:    */   public static JCheckBox doCompareAllEntities;
/*  32:    */   public static JCheckBox doMinimumSpanningStory;
/*  33:    */   private JPanel comparisonPanel;
/*  34:    */   
/*  35:    */   public String getName()
/*  36:    */   {
/*  37: 37 */     return "StoryThreading Viewer";
/*  38:    */   }
/*  39:    */   
/*  40:    */   public StoryThreadingViewer()
/*  41:    */   {
/*  42: 41 */     super(new BorderLayout());
/*  43:    */     
/*  44: 43 */     this.tabbedPane = new JTabbedPane();
/*  45: 44 */     this.controlPanel = new ParallelJPanel();
/*  46: 45 */     this.comparisonPanel = new JPanel(new BorderLayout());
/*  47:    */     
/*  48: 47 */     doCompareAllEntities = new JCheckBox("Compare All Entities", false);
/*  49: 48 */     this.controlPanel.addLeft(doCompareAllEntities);
/*  50: 49 */     doMinimumSpanningStory = new JCheckBox("Find Minimum Spanning Story", true);
/*  51: 50 */     this.controlPanel.addLeft(doMinimumSpanningStory);
/*  52:    */     
/*  53: 52 */     this.tabbedPane.add("Character Comparison", this.comparisonPanel);
/*  54: 53 */     this.tabbedPane.add("Control Panel", this.controlPanel);
/*  55: 54 */     add(this.tabbedPane);
/*  56:    */     
/*  57: 56 */     Connections.getPorts(this).addSignalProcessor("COMPARISON PORT", "showComparison");
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void showComparison(Object o)
/*  61:    */   {
/*  62: 60 */     BetterSignal signal = BetterSignal.isSignal(o);
/*  63: 61 */     if (signal == null) {
/*  64: 62 */       return;
/*  65:    */     }
/*  66: 64 */     this.comparisonPanel.removeAll();
/*  67: 65 */     JScrollPane table = generateComparisonTable(signal);
/*  68:    */     
/*  69: 67 */     this.comparisonPanel.add(table, "Center");
/*  70: 68 */     this.comparisonPanel.validate();
/*  71: 69 */     this.comparisonPanel.repaint();
/*  72:    */   }
/*  73:    */   
/*  74:    */   private class ThingScore
/*  75:    */   {
/*  76:    */     public Entity thing;
/*  77:    */     public float score;
/*  78:    */     
/*  79:    */     public ThingScore(Entity t, float s)
/*  80:    */     {
/*  81: 76 */       this.thing = t;
/*  82: 77 */       this.score = s;
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private JScrollPane generateComparisonTable(BetterSignal signal)
/*  87:    */   {
/*  88: 83 */     HashMatrix<Entity, Entity, Float> similarity = (HashMatrix)signal.getAll(HashMatrix.class).get(0);
/*  89: 84 */     HashMap<Entity, List<ThingScore>> scores = new HashMap();
/*  90:    */     
/*  91: 86 */     int width = 0;
/*  92: 87 */     int height = 0;
/*  93:    */     float score;
/*  94: 89 */     for (Entity entity_a : similarity.keySetRows())
/*  95:    */     {
/*  96: 90 */       List<ThingScore> thingScores = new ArrayList();
/*  97: 91 */       scores.put(entity_a, thingScores);
/*  98: 92 */       for (Entity entity_b : similarity.keySetCols())
/*  99:    */       {
/* 100: 93 */         score = ((Float)similarity.get(entity_a, entity_b)).floatValue();
/* 101: 94 */         if (score > 0.0F)
/* 102:    */         {
/* 103: 95 */           boolean added = false;
/* 104: 96 */           for (int i = 0; i < thingScores.size(); i++) {
/* 105: 97 */             if (score >= ((ThingScore)thingScores.get(i)).score)
/* 106:    */             {
/* 107: 98 */               thingScores.add(i, new ThingScore(entity_b, score));
/* 108: 99 */               added = true;
/* 109:100 */               break;
/* 110:    */             }
/* 111:    */           }
/* 112:103 */           if (!added) {
/* 113:104 */             thingScores.add(new ThingScore(entity_b, score));
/* 114:    */           }
/* 115:    */         }
/* 116:    */       }
/* 117:107 */       if (thingScores.size() >= width) {
/* 118:108 */         width = thingScores.size();
/* 119:    */       }
/* 120:    */     }
/* 121:110 */     height = scores.keySet().size();
/* 122:    */     
/* 123:112 */     Object[][] data = new Object[height][width];
/* 124:113 */     String[] cols = new String[width];
/* 125:    */     
/* 126:115 */     cols[0] = "Rank:";
/* 127:116 */     for (int i = 1; i < width; i++) {
/* 128:117 */       cols[i] = ("#" + i);
/* 129:    */     }
/* 130:120 */     int i = 0;
/* 131:121 */     int j = 0;
/* 132:122 */     for (Entity entity_a : scores.keySet())
/* 133:    */     {
/* 134:123 */       i = 0;
/* 135:124 */       data[j][i] = (entity_a.asString() + ":");
/* 136:125 */       List<ThingScore> tss = (List)scores.get(entity_a);
/* 137:126 */       i++;
/* 138:127 */       while (i < width)
/* 139:    */       {
/* 140:128 */         if (i < tss.size())
/* 141:    */         {
/* 142:129 */           ThingScore ts = (ThingScore)tss.get(i);
/* 143:130 */           data[j][i] = (ts.thing.asString() + "=" + ts.score);
/* 144:    */         }
/* 145:    */         else
/* 146:    */         {
/* 147:132 */           data[j][i] = "";
/* 148:    */         }
/* 149:134 */         i++;
/* 150:    */       }
/* 151:136 */       j++;
/* 152:    */     }
/* 153:139 */     JScrollPane scrollPane = generateTable(data, cols);
/* 154:    */     
/* 155:141 */     return scrollPane;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public static JScrollPane generateTable(Object[][] data, String[] cols)
/* 159:    */   {
/* 160:145 */     JTable alignmentTable = new JTable(data, cols);
/* 161:146 */     TextAreaRenderer textAreaRenderer = new TextAreaRenderer();
/* 162:147 */     TableColumnModel cmodel = alignmentTable.getColumnModel();
/* 163:149 */     for (int i = 0; i < cmodel.getColumnCount(); i++)
/* 164:    */     {
/* 165:150 */       cmodel.getColumn(i).setPreferredWidth(175);
/* 166:151 */       cmodel.getColumn(i).setCellRenderer(textAreaRenderer);
/* 167:    */     }
/* 168:155 */     alignmentTable.setAutoResizeMode(0);
/* 169:    */     
/* 170:157 */     JScrollPane scrollPane = new JScrollPane(alignmentTable);
/* 171:    */     
/* 172:159 */     return scrollPane;
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryThreading.StoryThreadingViewer
 * JD-Core Version:    0.7.0.1
 */