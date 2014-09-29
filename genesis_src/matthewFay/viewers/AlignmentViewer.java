/*   1:    */ package matthewFay.viewers;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import ati.ParallelJPanel;
/*   5:    */ import bridge.reps.entities.Entity;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import edu.uci.ics.jung.algorithms.layout.TreeLayout;
/*  10:    */ import edu.uci.ics.jung.graph.Forest;
/*  11:    */ import edu.uci.ics.jung.visualization.RenderContext;
/*  12:    */ import edu.uci.ics.jung.visualization.VisualizationViewer;
/*  13:    */ import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
/*  14:    */ import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
/*  15:    */ import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
/*  16:    */ import java.awt.BorderLayout;
/*  17:    */ import java.awt.Color;
/*  18:    */ import java.awt.Container;
/*  19:    */ import java.awt.Dimension;
/*  20:    */ import java.awt.event.ActionEvent;
/*  21:    */ import java.awt.event.ActionListener;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Iterator;
/*  24:    */ import java.util.List;
/*  25:    */ import javax.swing.JButton;
/*  26:    */ import javax.swing.JCheckBox;
/*  27:    */ import javax.swing.JFrame;
/*  28:    */ import javax.swing.JPanel;
/*  29:    */ import javax.swing.JScrollPane;
/*  30:    */ import javax.swing.JTabbedPane;
/*  31:    */ import javax.swing.JTable;
/*  32:    */ import javax.swing.JViewport;
/*  33:    */ import javax.swing.table.TableColumn;
/*  34:    */ import javax.swing.table.TableColumnModel;
/*  35:    */ import matthewFay.StoryAlignment.AlignmentProcessor;
/*  36:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  37:    */ import matthewFay.StoryAlignment.TextAreaRenderer;
/*  38:    */ import matthewFay.Utilities.EntityHelper.MatchNode;
/*  39:    */ import matthewFay.Utilities.Pair;
/*  40:    */ import minilisp.LList;
/*  41:    */ import start.Generator;
/*  42:    */ import utils.PairOfEntities;
/*  43:    */ 
/*  44:    */ public class AlignmentViewer
/*  45:    */   extends JPanel
/*  46:    */   implements WiredBox, ActionListener
/*  47:    */ {
/*  48:    */   public static final String REFLECTION_ALIGNMENT_PORT = "REFLECTION ALIGNMENT PORT";
/*  49:    */   public static final String GRAPH_PORT = "Graph Port";
/*  50:    */   private JTabbedPane tabbedPane;
/*  51:    */   public static JPanel alignmentPanel;
/*  52:    */   private JPanel reflectionPanel;
/*  53:    */   private JPanel bindingsPanel;
/*  54:    */   private JPanel treePanel;
/*  55:    */   private ParallelJPanel controlPanel;
/*  56: 41 */   public static JCheckBox useReflections = new JCheckBox("Use Reflective Knowledge", true);
/*  57: 42 */   public static JCheckBox debugTreeGeneration = new JCheckBox("Debug Tree Generation", false);
/*  58: 43 */   public static JCheckBox generateNiceOutput = new JCheckBox("Generate Nice English Output", true);
/*  59: 44 */   public static JCheckBox gapFilling = new JCheckBox("Collaborative Gap Filling", false);
/*  60: 45 */   public static JCheckBox simultaneousMatchingAndAlignment = new JCheckBox("Simultaneous Matching+Alignment", true);
/*  61: 46 */   public static JCheckBox simpleScorer = new JCheckBox("Simple Scorer", true);
/*  62: 48 */   public static JButton rerunLastAlignmentButton = new JButton("Rerun Alignment!");
/*  63:    */   
/*  64:    */   public String getName()
/*  65:    */   {
/*  66: 51 */     return "Alignment Viewer";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public AlignmentViewer()
/*  70:    */   {
/*  71: 55 */     super(new BorderLayout());
/*  72: 56 */     this.tabbedPane = new JTabbedPane();
/*  73: 57 */     alignmentPanel = new JPanel(new BorderLayout());
/*  74: 58 */     this.reflectionPanel = new JPanel(new BorderLayout());
/*  75: 59 */     this.bindingsPanel = new JPanel(new BorderLayout());
/*  76: 60 */     this.treePanel = new JPanel(new BorderLayout());
/*  77: 61 */     this.controlPanel = new ParallelJPanel();
/*  78:    */     
/*  79:    */ 
/*  80: 64 */     this.controlPanel.addLeft(generateNiceOutput);
/*  81: 65 */     this.controlPanel.addLeft(gapFilling);
/*  82:    */     
/*  83: 67 */     this.controlPanel.addCenter(useReflections);
/*  84: 68 */     this.controlPanel.addCenter(simpleScorer);
/*  85:    */     
/*  86: 70 */     this.controlPanel.addRight(debugTreeGeneration);
/*  87: 71 */     this.controlPanel.addRight(simultaneousMatchingAndAlignment);
/*  88: 72 */     this.controlPanel.addRight(rerunLastAlignmentButton);
/*  89:    */     
/*  90: 74 */     simultaneousMatchingAndAlignment.setEnabled(false);
/*  91:    */     
/*  92: 76 */     rerunLastAlignmentButton.setActionCommand("rerun");
/*  93: 77 */     rerunLastAlignmentButton.addActionListener(this);
/*  94:    */     
/*  95: 79 */     this.tabbedPane.add("Story Alignment", alignmentPanel);
/*  96: 80 */     this.tabbedPane.add("Reflection Alignment", this.reflectionPanel);
/*  97: 81 */     this.tabbedPane.add("Bindings", this.bindingsPanel);
/*  98: 82 */     this.tabbedPane.add("Match Tree", this.treePanel);
/*  99: 83 */     this.tabbedPane.add("Control Panel", this.controlPanel);
/* 100: 84 */     add(this.tabbedPane);
/* 101:    */     
/* 102: 86 */     Connections.getPorts(this).addSignalProcessor("renderAlignment");
/* 103: 87 */     Connections.getPorts(this).addSignalProcessor("REFLECTION ALIGNMENT PORT", "renderReflectionAlignment");
/* 104: 88 */     Connections.getPorts(this).addSignalProcessor("Graph Port", "renderGraph");
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void renderGraph(Object o)
/* 108:    */   {
/* 109: 93 */     Forest<EntityHelper.MatchNode, Integer> graph = null;
/* 110: 94 */     BetterSignal signal = BetterSignal.isSignal(o);
/* 111: 95 */     if (signal == null) {
/* 112: 96 */       return;
/* 113:    */     }
/* 114:    */     try
/* 115:    */     {
/* 116: 98 */       graph = (Forest)signal.get(0, Forest.class);
/* 117:    */     }
/* 118:    */     catch (Exception e)
/* 119:    */     {
/* 120:100 */       e.printStackTrace();
/* 121:101 */       return;
/* 122:    */     }
/* 123:103 */     this.treePanel.removeAll();
/* 124:    */     
/* 125:105 */     this.treePanel.add(generateVisualTree(graph), "Center");
/* 126:106 */     this.treePanel.validate();
/* 127:107 */     this.treePanel.repaint();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void renderAlignment(Object o)
/* 131:    */   {
/* 132:111 */     BetterSignal signal = BetterSignal.isSignal(o);
/* 133:112 */     if (signal == null) {
/* 134:113 */       return;
/* 135:    */     }
/* 136:114 */     alignmentPanel.removeAll();
/* 137:115 */     this.bindingsPanel.removeAll();
/* 138:116 */     JScrollPane table = generateTable(signal);
/* 139:117 */     JScrollPane bindingsTable = generateBindingsTable(signal);
/* 140:    */     
/* 141:119 */     alignmentPanel.add(table, "Center");
/* 142:120 */     alignmentPanel.validate();
/* 143:121 */     alignmentPanel.repaint();
/* 144:122 */     this.bindingsPanel.add(bindingsTable, "Center");
/* 145:123 */     this.bindingsPanel.validate();
/* 146:124 */     this.bindingsPanel.repaint();
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void renderReflectionAlignment(Object o)
/* 150:    */   {
/* 151:128 */     BetterSignal signal = BetterSignal.isSignal(o);
/* 152:129 */     if (signal == null) {
/* 153:130 */       return;
/* 154:    */     }
/* 155:131 */     this.reflectionPanel.removeAll();
/* 156:132 */     JScrollPane table = generateTable(signal);
/* 157:    */     
/* 158:134 */     this.reflectionPanel.add(table, "Center");
/* 159:135 */     this.reflectionPanel.validate();
/* 160:136 */     this.reflectionPanel.repaint();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static JScrollPane generateBindingsTable(BetterSignal alignmentSignal)
/* 164:    */   {
/* 165:140 */     int totalElements = 0;
/* 166:141 */     List<SequenceAlignment> alignments = alignmentSignal.getAll(SequenceAlignment.class);
/* 167:142 */     for (SequenceAlignment alignment : alignments) {
/* 168:143 */       totalElements = Math.max(alignment.bindings.size(), totalElements);
/* 169:    */     }
/* 170:146 */     Object[][] data = new Object[alignments.size() * 2][totalElements + 1];
/* 171:147 */     String[] eltSlots = new String[totalElements + 1];
/* 172:    */     
/* 173:149 */     eltSlots[0] = "Story";
/* 174:150 */     for (Integer i = Integer.valueOf(0); i.intValue() < totalElements; i = Integer.valueOf(i.intValue() + 1)) {
/* 175:151 */       eltSlots[(i.intValue() + 1)] = ("Binding " + i.toString());
/* 176:    */     }
/* 177:154 */     int currentAlignment = 0;
/* 178:155 */     for (SequenceAlignment alignment : alignments)
/* 179:    */     {
/* 180:156 */       data[(2 * currentAlignment)][0] = (alignment.bName + "Score: " + alignment.score);
/* 181:157 */       data[(2 * currentAlignment + 1)][0] = (alignment.aName + "Score: " + alignment.score);
/* 182:    */       
/* 183:159 */       int pairCount = 0;
/* 184:160 */       List<PairOfEntities> revList = new ArrayList();
/* 185:161 */       for (PairOfEntities pair : alignment.bindings) {
/* 186:162 */         revList.add(pair);
/* 187:    */       }
/* 188:164 */       for (int pair = revList.size() - 1; pair >= 0; pair--)
/* 189:    */       {
/* 190:165 */         data[(2 * currentAlignment)][(pairCount + 1)] = ((PairOfEntities)revList.get(pair)).getDatum().asString();
/* 191:166 */         data[(2 * currentAlignment + 1)][(pairCount + 1)] = ((PairOfEntities)revList.get(pair)).getPattern().asString();
/* 192:167 */         pairCount++;
/* 193:    */       }
/* 194:169 */       currentAlignment++;
/* 195:    */     }
/* 196:172 */     return generateTable(data, eltSlots);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static JScrollPane generateTable(SequenceAlignment sequence_alignment, boolean highlight_alignment)
/* 200:    */   {
/* 201:176 */     JScrollPane table = generateTable(new BetterSignal(new Object[] { sequence_alignment }));
/* 202:178 */     if (highlight_alignment) {
/* 203:179 */       for (int i = 0; i < sequence_alignment.size(); i++) {
/* 204:180 */         if ((((Pair)sequence_alignment.get(i)).a != null) && (((Pair)sequence_alignment.get(i)).b != null))
/* 205:    */         {
/* 206:181 */           setCellColor(table, 0, i + 1, new Color(128, 192, 128));
/* 207:182 */           setCellColor(table, 1, i + 1, new Color(128, 192, 128));
/* 208:    */         }
/* 209:    */       }
/* 210:    */     }
/* 211:187 */     return table;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public static JScrollPane generateTable(BetterSignal alignmentSignal)
/* 215:    */   {
/* 216:192 */     Generator generator = Generator.getGenerator();
/* 217:    */     
/* 218:194 */     int totalElements = 0;
/* 219:195 */     List<SequenceAlignment> alignments = alignmentSignal.getAll(SequenceAlignment.class);
/* 220:196 */     for (SequenceAlignment alignment : alignments) {
/* 221:197 */       totalElements = Math.max(alignment.size(), totalElements);
/* 222:    */     }
/* 223:200 */     Object[][] data = new Object[alignments.size() * 2][totalElements + 1];
/* 224:201 */     String[] eltSlots = new String[totalElements + 1];
/* 225:    */     
/* 226:203 */     eltSlots[0] = "Story";
/* 227:204 */     for (Integer i = Integer.valueOf(0); i.intValue() < totalElements; i = Integer.valueOf(i.intValue() + 1)) {
/* 228:205 */       eltSlots[(i.intValue() + 1)] = ("Element " + i.toString());
/* 229:    */     }
/* 230:208 */     int currentAlignment = 0;
/* 231:209 */     for (SequenceAlignment alignment : alignments)
/* 232:    */     {
/* 233:211 */       data[(currentAlignment * 2)][0] = (alignment.bName + " Score: " + alignment.score);
/* 234:    */       
/* 235:213 */       data[(currentAlignment * 2 + 1)][0] = (alignment.aName + " Score: " + alignment.score);
/* 236:215 */       for (i = Integer.valueOf(0); i.intValue() < alignment.size(); i = Integer.valueOf(i.intValue() + 1))
/* 237:    */       {
/* 238:216 */         if (((Pair)alignment.get(i.intValue())).b != null)
/* 239:    */         {
/* 240:217 */           if (generateNiceOutput.isSelected()) {
/* 241:    */             try
/* 242:    */             {
/* 243:219 */               String output = generator.generate((Entity)((Pair)alignment.get(i.intValue())).b);
/* 244:220 */               if (output != null) {
/* 245:221 */                 data[(currentAlignment * 2)][(i.intValue() + 1)] = generator.generate((Entity)((Pair)alignment.get(i.intValue())).b);
/* 246:    */               } else {
/* 247:223 */                 data[(currentAlignment * 2)][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).b).asString();
/* 248:    */               }
/* 249:    */             }
/* 250:    */             catch (Exception e)
/* 251:    */             {
/* 252:225 */               data[(currentAlignment * 2)][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).b).asString();
/* 253:    */             }
/* 254:    */           } else {
/* 255:228 */             data[(currentAlignment * 2)][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).b).asString();
/* 256:    */           }
/* 257:    */         }
/* 258:    */         else {
/* 259:232 */           data[(currentAlignment * 2)][(i.intValue() + 1)] = "---";
/* 260:    */         }
/* 261:234 */         if (((Pair)alignment.get(i.intValue())).a != null)
/* 262:    */         {
/* 263:235 */           if (generateNiceOutput.isSelected()) {
/* 264:    */             try
/* 265:    */             {
/* 266:237 */               String output = generator.generate((Entity)((Pair)alignment.get(i.intValue())).a);
/* 267:238 */               if (output != null) {
/* 268:239 */                 data[(currentAlignment * 2 + 1)][(i.intValue() + 1)] = output;
/* 269:    */               } else {
/* 270:241 */                 data[(currentAlignment * 2 + 1)][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).a).asString();
/* 271:    */               }
/* 272:    */             }
/* 273:    */             catch (Exception e)
/* 274:    */             {
/* 275:243 */               data[(currentAlignment * 2 + 1)][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).a).asString();
/* 276:    */             }
/* 277:    */           } else {
/* 278:246 */             data[(currentAlignment * 2 + 1)][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).a).asString();
/* 279:    */           }
/* 280:    */         }
/* 281:    */         else {
/* 282:249 */           data[(currentAlignment * 2 + 1)][(i.intValue() + 1)] = "---";
/* 283:    */         }
/* 284:    */       }
/* 285:253 */       currentAlignment++;
/* 286:    */     }
/* 287:256 */     JScrollPane scrollPane = generateTable(data, eltSlots);
/* 288:    */     SequenceAlignment alignment;
/* 289:    */     Integer i;
/* 290:257 */     for (Integer i = alignments.iterator(); i.hasNext(); i.intValue() < alignment.size())
/* 291:    */     {
/* 292:257 */       alignment = (SequenceAlignment)i.next();
/* 293:258 */       i = Integer.valueOf(0); continue;
/* 294:259 */       if ((((Pair)alignment.get(i.intValue())).b != null) && 
/* 295:260 */         (((Entity)((Pair)alignment.get(i.intValue())).b).hasFeature("GapFilled"))) {
/* 296:261 */         setCellColor(scrollPane, (currentAlignment - 1) * 2, i.intValue() + 1, new Color(128, 192, 128));
/* 297:    */       }
/* 298:264 */       if ((((Pair)alignment.get(i.intValue())).a != null) && 
/* 299:265 */         (((Entity)((Pair)alignment.get(i.intValue())).a).hasFeature("GapFilled"))) {
/* 300:266 */         setCellColor(scrollPane, (currentAlignment - 1) * 2 + 1, i.intValue() + 1, new Color(128, 192, 128));
/* 301:    */       }
/* 302:258 */       i = Integer.valueOf(i.intValue() + 1);
/* 303:    */     }
/* 304:272 */     return scrollPane;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public static void setCellColor(JScrollPane scrollPane, int row, int column, Color color)
/* 308:    */   {
/* 309:279 */     ((TextAreaRenderer)((JTable)scrollPane.getViewport().getView()).getCellRenderer(row, column)).setColor(row, column, color);
/* 310:    */   }
/* 311:    */   
/* 312:    */   public static JScrollPane generateTable(Object[][] data, String[] cols)
/* 313:    */   {
/* 314:283 */     JTable alignmentTable = new JTable(data, cols);
/* 315:284 */     TextAreaRenderer textAreaRenderer = new TextAreaRenderer();
/* 316:285 */     TableColumnModel cmodel = alignmentTable.getColumnModel();
/* 317:287 */     for (int i = 0; i < cmodel.getColumnCount(); i++)
/* 318:    */     {
/* 319:288 */       cmodel.getColumn(i).setPreferredWidth(175);
/* 320:289 */       cmodel.getColumn(i).setCellRenderer(textAreaRenderer);
/* 321:    */     }
/* 322:293 */     alignmentTable.setAutoResizeMode(0);
/* 323:    */     
/* 324:295 */     JScrollPane scrollPane = new JScrollPane(alignmentTable);
/* 325:    */     
/* 326:297 */     return scrollPane;
/* 327:    */   }
/* 328:    */   
/* 329:300 */   private static JFrame frame = null;
/* 330:301 */   public static boolean firstPopout = true;
/* 331:    */   
/* 332:    */   public static void popoutVisualTree(Forest<EntityHelper.MatchNode, Integer> graph)
/* 333:    */   {
/* 334:303 */     VisualizationViewer<EntityHelper.MatchNode, Integer> vv = generateVisualTree(graph);
/* 335:304 */     if (frame == null)
/* 336:    */     {
/* 337:305 */       frame = new JFrame("Matcher Graph View");
/* 338:    */       
/* 339:307 */       frame.getContentPane().removeAll();
/* 340:308 */       frame.getContentPane().add(vv);
/* 341:    */       
/* 342:310 */       frame.setPreferredSize(new Dimension(2000, 800));
/* 343:311 */       frame.pack();
/* 344:312 */       frame.setVisible(true);
/* 345:    */     }
/* 346:    */     else
/* 347:    */     {
/* 348:314 */       frame.getContentPane().removeAll();
/* 349:315 */       frame.getContentPane().add(vv);
/* 350:    */       
/* 351:    */ 
/* 352:318 */       frame.validate();
/* 353:319 */       frame.setVisible(true);
/* 354:    */     }
/* 355:321 */     if (firstPopout)
/* 356:    */     {
/* 357:    */       try
/* 358:    */       {
/* 359:323 */         Thread.sleep(2000L);
/* 360:    */       }
/* 361:    */       catch (InterruptedException e)
/* 362:    */       {
/* 363:325 */         e.printStackTrace();
/* 364:    */       }
/* 365:327 */       firstPopout = false;
/* 366:    */     }
/* 367:    */   }
/* 368:    */   
/* 369:    */   private static VisualizationViewer<EntityHelper.MatchNode, Integer> generateVisualTree(Forest<EntityHelper.MatchNode, Integer> graph)
/* 370:    */   {
/* 371:334 */     TreeLayout<EntityHelper.MatchNode, Integer> layout = new TreeLayout(graph);
/* 372:335 */     VisualizationViewer<EntityHelper.MatchNode, Integer> vv = 
/* 373:336 */       new VisualizationViewer(layout);
/* 374:337 */     vv.setPreferredSize(new Dimension(1600, 1000));
/* 375:    */     
/* 376:    */ 
/* 377:340 */     ToStringLabeller<EntityHelper.MatchNode> vertexPaint = new ToStringLabeller()
/* 378:    */     {
/* 379:    */       public String transform(EntityHelper.MatchNode t)
/* 380:    */       {
/* 381:342 */         if ((t.bindingSet != null) && (t.bindingSet.size() > 0))
/* 382:    */         {
/* 383:343 */           if ((t.story1_entities.size() < 1) && (t.story2_entities.size() < 1)) {
/* 384:344 */             return Float.toString(t.score);
/* 385:    */           }
/* 386:346 */           return "Score: " + Float.toString(t.score) + "\n" + ((PairOfEntities)t.bindingSet.first()).toString();
/* 387:    */         }
/* 388:348 */         return "root";
/* 389:    */       }
/* 390:350 */     };
/* 391:351 */     vv.getRenderContext().setVertexLabelTransformer(vertexPaint);
/* 392:    */     
/* 393:353 */     DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
/* 394:354 */     gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
/* 395:355 */     vv.setGraphMouse(gm);
/* 396:    */     
/* 397:    */ 
/* 398:358 */     return vv;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public void actionPerformed(ActionEvent e)
/* 402:    */   {
/* 403:379 */     if (e.getActionCommand().endsWith("rerun")) {
/* 404:380 */       AlignmentProcessor.getAlignmentProcessor().rerunLastAlignment();
/* 405:    */     }
/* 406:    */   }
/* 407:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.viewers.AlignmentViewer
 * JD-Core Version:    0.7.0.1
 */