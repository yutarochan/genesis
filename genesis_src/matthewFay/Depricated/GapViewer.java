/*   1:    */ package matthewFay.Depricated;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import java.awt.BorderLayout;
/*  10:    */ import java.awt.Color;
/*  11:    */ import java.awt.Container;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.swing.JFrame;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.JScrollPane;
/*  17:    */ import javax.swing.JTabbedPane;
/*  18:    */ import javax.swing.JTable;
/*  19:    */ import javax.swing.JViewport;
/*  20:    */ import javax.swing.table.TableColumn;
/*  21:    */ import javax.swing.table.TableColumnModel;
/*  22:    */ import matthewFay.Demo;
/*  23:    */ import matthewFay.StoryAlignment.Alignment;
/*  24:    */ import matthewFay.StoryAlignment.RankedSequenceAlignmentSet;
/*  25:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  26:    */ import matthewFay.StoryAlignment.TextAreaRenderer;
/*  27:    */ import matthewFay.Utilities.Pair;
/*  28:    */ import start.Generator;
/*  29:    */ import utils.Mark;
/*  30:    */ 
/*  31:    */ @Deprecated
/*  32:    */ public class GapViewer
/*  33:    */   extends JPanel
/*  34:    */   implements WiredBox
/*  35:    */ {
/*  36: 25 */   public static boolean generateNiceOutput = true;
/*  37:    */   public static final String ADDGAP_PORT = "addGap";
/*  38:    */   public static final String CLEARVIEW_PORT = "clearview";
/*  39:    */   private JTabbedPane tabbedPane;
/*  40:    */   private JPanel filledGapPanel;
/*  41:    */   private List<JPanel> gapDetailPanels;
/*  42: 34 */   private boolean finishedGapFilling = false;
/*  43:    */   
/*  44:    */   public String getName()
/*  45:    */   {
/*  46: 37 */     return "Gap Viewer";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public GapViewer()
/*  50:    */   {
/*  51: 41 */     super(new BorderLayout());
/*  52: 42 */     this.tabbedPane = new JTabbedPane();
/*  53: 43 */     this.gapDetailPanels = new ArrayList();
/*  54: 44 */     this.filledGapPanel = new JPanel(new BorderLayout());
/*  55:    */     
/*  56: 46 */     this.tabbedPane.add("Finished Sequence", this.filledGapPanel);
/*  57: 47 */     add(this.tabbedPane);
/*  58:    */     
/*  59: 49 */     Connections.getPorts(this).addSignalProcessor("renderSequence");
/*  60: 50 */     Connections.getPorts(this).addSignalProcessor("clearview", "clearView");
/*  61: 51 */     Connections.getPorts(this).addSignalProcessor("addGap", "renderGap");
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void renderSequence(Object o)
/*  65:    */   {
/*  66: 56 */     Sequence s = null;
/*  67: 57 */     ArrayList<Integer> importantPoints = null;
/*  68: 58 */     if ((o instanceof Sequence)) {
/*  69: 59 */       s = (Sequence)o;
/*  70:    */     }
/*  71: 61 */     BetterSignal signal = BetterSignal.isSignal(o);
/*  72: 62 */     if (signal != null)
/*  73:    */     {
/*  74:    */       try
/*  75:    */       {
/*  76: 64 */         s = (Sequence)signal.get(0, Sequence.class);
/*  77:    */       }
/*  78:    */       catch (Exception e)
/*  79:    */       {
/*  80: 66 */         return;
/*  81:    */       }
/*  82:    */       try
/*  83:    */       {
/*  84: 70 */         importantPoints = (ArrayList)signal.get(1, ArrayList.class);
/*  85:    */       }
/*  86:    */       catch (Exception e)
/*  87:    */       {
/*  88: 72 */         importantPoints = null;
/*  89:    */       }
/*  90:    */     }
/*  91: 75 */     if (s == null) {
/*  92: 76 */       return;
/*  93:    */     }
/*  94: 77 */     this.filledGapPanel.removeAll();
/*  95: 78 */     JScrollPane table = generateTableFromSequence(s, "Gaps Filled");
/*  96: 79 */     if (importantPoints != null) {
/*  97: 80 */       for (Integer i : importantPoints) {
/*  98: 81 */         setCellColor(table, 0, i.intValue() + 1, new Color(128, 192, 128));
/*  99:    */       }
/* 100:    */     }
/* 101: 84 */     this.filledGapPanel.add(table, "Center");
/* 102: 85 */     this.filledGapPanel.validate();
/* 103: 86 */     this.filledGapPanel.repaint();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void renderGap(Object o)
/* 107:    */   {
/* 108: 91 */     if (this.finishedGapFilling)
/* 109:    */     {
/* 110: 92 */       this.finishedGapFilling = false;
/* 111: 93 */       clearView();
/* 112:    */     }
/* 113: 95 */     RankedSequenceAlignmentSet<Entity, Entity> alignments = null;
/* 114: 96 */     if ((o instanceof RankedSequenceAlignmentSet)) {
/* 115: 97 */       alignments = (RankedSequenceAlignmentSet)o;
/* 116:    */     }
/* 117: 99 */     BetterSignal signal = BetterSignal.isSignal(o);
/* 118:100 */     if (signal != null) {
/* 119:    */       try
/* 120:    */       {
/* 121:102 */         alignments = (RankedSequenceAlignmentSet)signal.get(0, RankedSequenceAlignmentSet.class);
/* 122:    */       }
/* 123:    */       catch (Exception e)
/* 124:    */       {
/* 125:104 */         return;
/* 126:    */       }
/* 127:    */     }
/* 128:107 */     if (alignments == null) {
/* 129:108 */       return;
/* 130:    */     }
/* 131:109 */     JPanel alignmentPanel = new JPanel(new BorderLayout());
/* 132:110 */     alignmentPanel.add(generateFullTable(alignments), "Center");
/* 133:111 */     this.tabbedPane.add("Gap " + this.tabbedPane.getTabCount(), alignmentPanel);
/* 134:112 */     this.tabbedPane.validate();
/* 135:113 */     this.tabbedPane.repaint();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void clearView()
/* 139:    */   {
/* 140:117 */     this.filledGapPanel.removeAll();
/* 141:118 */     this.filledGapPanel.validate();
/* 142:119 */     this.filledGapPanel.repaint();
/* 143:120 */     while (this.tabbedPane.getTabCount() > 1) {
/* 144:121 */       this.tabbedPane.remove(1);
/* 145:    */     }
/* 146:122 */     this.tabbedPane.validate();
/* 147:123 */     this.tabbedPane.repaint();
/* 148:124 */     this.gapDetailPanels.clear();
/* 149:    */   }
/* 150:    */   
/* 151:    */   public static JScrollPane generateTable(SequenceAlignment alignment)
/* 152:    */   {
/* 153:128 */     Generator generator = Generator.getGenerator();
/* 154:    */     
/* 155:130 */     int totalElements = alignment.size();
/* 156:    */     
/* 157:132 */     Object[][] data = new Object[2][totalElements + 1];
/* 158:133 */     String[] eltSlots = new String[totalElements + 1];
/* 159:    */     
/* 160:135 */     eltSlots[0] = "Story";
/* 161:136 */     data[0][0] = alignment.bName;
/* 162:137 */     data[1][0] = alignment.aName;
/* 163:139 */     for (Integer i = Integer.valueOf(0); i.intValue() < totalElements; i = Integer.valueOf(i.intValue() + 1))
/* 164:    */     {
/* 165:140 */       eltSlots[(i.intValue() + 1)] = ("Element " + i.toString());
/* 166:141 */       if (((Pair)alignment.get(i.intValue())).b != null)
/* 167:    */       {
/* 168:142 */         if (generateNiceOutput) {
/* 169:    */           try
/* 170:    */           {
/* 171:144 */             data[0][(i.intValue() + 1)] = generator.generate((Entity)((Pair)alignment.get(i.intValue())).b);
/* 172:    */           }
/* 173:    */           catch (Exception e)
/* 174:    */           {
/* 175:146 */             data[0][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).b).asString();
/* 176:    */           }
/* 177:    */         } else {
/* 178:149 */           data[0][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).b).asString();
/* 179:    */         }
/* 180:    */       }
/* 181:    */       else {
/* 182:152 */         data[0][(i.intValue() + 1)] = "---";
/* 183:    */       }
/* 184:153 */       if (((Pair)alignment.get(i.intValue())).a != null)
/* 185:    */       {
/* 186:154 */         if (generateNiceOutput) {
/* 187:    */           try
/* 188:    */           {
/* 189:156 */             data[1][(i.intValue() + 1)] = generator.generate((Entity)((Pair)alignment.get(i.intValue())).a);
/* 190:    */           }
/* 191:    */           catch (Exception e)
/* 192:    */           {
/* 193:158 */             data[1][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).a).asString();
/* 194:    */           }
/* 195:    */         } else {
/* 196:161 */           data[1][(i.intValue() + 1)] = ((Entity)((Pair)alignment.get(i.intValue())).a).asString();
/* 197:    */         }
/* 198:    */       }
/* 199:    */       else {
/* 200:164 */         data[1][(i.intValue() + 1)] = "---";
/* 201:    */       }
/* 202:    */     }
/* 203:167 */     return generateTable(data, eltSlots);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static JScrollPane generateTable(RankedSequenceAlignmentSet<Entity, Entity> rankedAlignmentSet)
/* 207:    */   {
/* 208:171 */     Generator generator = Generator.getGenerator();
/* 209:    */     
/* 210:173 */     int totalAlignments = rankedAlignmentSet.size();
/* 211:174 */     int totalElements = 0;
/* 212:175 */     if (totalAlignments > 0) {
/* 213:176 */       totalElements = rankedAlignmentSet.getMaxLength();
/* 214:    */     }
/* 215:179 */     Object[][] data = new Object[totalAlignments + 1][totalElements + 1];
/* 216:180 */     String[] eltSlots = new String[totalElements + 1];
/* 217:    */     
/* 218:182 */     eltSlots[0] = "Story";
/* 219:184 */     for (Integer eltIter = Integer.valueOf(0); eltIter.intValue() < totalElements; eltIter = Integer.valueOf(eltIter.intValue() + 1))
/* 220:    */     {
/* 221:185 */       eltSlots[(eltIter.intValue() + 1)] = ("Element " + eltIter.toString());
/* 222:186 */       for (int alignmentIter = 0; alignmentIter < totalAlignments; alignmentIter++) {
/* 223:187 */         if (alignmentIter == 0)
/* 224:    */         {
/* 225:188 */           data[0][0] = ((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).bName;
/* 226:189 */           data[1][0] = ((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).aName;
/* 227:190 */           if (((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).b != null)
/* 228:    */           {
/* 229:191 */             if (generateNiceOutput) {
/* 230:    */               try
/* 231:    */               {
/* 232:193 */                 data[0][(eltIter.intValue() + 1)] = generator.generate((Entity)((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).b);
/* 233:    */               }
/* 234:    */               catch (Exception e)
/* 235:    */               {
/* 236:195 */                 data[0][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).b).asString();
/* 237:    */               }
/* 238:    */             } else {
/* 239:198 */               data[0][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).b).asString();
/* 240:    */             }
/* 241:    */           }
/* 242:    */           else {
/* 243:201 */             data[0][(eltIter.intValue() + 1)] = "---";
/* 244:    */           }
/* 245:202 */           if (((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).a != null)
/* 246:    */           {
/* 247:203 */             if (generateNiceOutput) {
/* 248:    */               try
/* 249:    */               {
/* 250:205 */                 data[1][(eltIter.intValue() + 1)] = generator.generate((Entity)((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).a);
/* 251:    */               }
/* 252:    */               catch (Exception e)
/* 253:    */               {
/* 254:207 */                 data[1][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).a).asString();
/* 255:    */               }
/* 256:    */             } else {
/* 257:210 */               data[1][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).a).asString();
/* 258:    */             }
/* 259:    */           }
/* 260:    */           else {
/* 261:213 */             data[1][(eltIter.intValue() + 1)] = "---";
/* 262:    */           }
/* 263:    */         }
/* 264:    */         else
/* 265:    */         {
/* 266:215 */           data[(alignmentIter + 1)][0] = ((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).aName;
/* 267:216 */           if (((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).a != null)
/* 268:    */           {
/* 269:217 */             if (generateNiceOutput) {
/* 270:    */               try
/* 271:    */               {
/* 272:219 */                 data[(alignmentIter + 1)][(eltIter.intValue() + 1)] = generator.generate((Entity)((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).a);
/* 273:    */               }
/* 274:    */               catch (Exception e)
/* 275:    */               {
/* 276:221 */                 data[(alignmentIter + 1)][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).a).asString();
/* 277:    */               }
/* 278:    */             } else {
/* 279:224 */               data[(alignmentIter + 1)][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)rankedAlignmentSet.get(alignmentIter)).get(eltIter.intValue())).a).asString();
/* 280:    */             }
/* 281:    */           }
/* 282:    */           else {
/* 283:227 */             data[(alignmentIter + 1)][(eltIter.intValue() + 1)] = "---";
/* 284:    */           }
/* 285:    */         }
/* 286:    */       }
/* 287:    */     }
/* 288:232 */     return generateTable(data, eltSlots);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public static JScrollPane generateFullTable(List<SequenceAlignment> alignmentSets)
/* 292:    */   {
/* 293:236 */     Generator generator = Generator.getGenerator();
/* 294:    */     
/* 295:238 */     int totalAlignments = alignmentSets.size();
/* 296:239 */     int totalElements = 0;
/* 297:240 */     for (Alignment<Entity, Entity> alignment : alignmentSets) {
/* 298:241 */       totalElements = alignment.size() > totalElements ? alignment.size() : totalElements;
/* 299:    */     }
/* 300:244 */     Object[][] data = new Object[2 * totalAlignments][totalElements + 1];
/* 301:245 */     String[] eltSlots = new String[totalElements + 1];
/* 302:    */     
/* 303:247 */     eltSlots[0] = "Story";
/* 304:249 */     for (Integer eltIter = Integer.valueOf(0); eltIter.intValue() < totalElements; eltIter = Integer.valueOf(eltIter.intValue() + 1))
/* 305:    */     {
/* 306:250 */       eltSlots[(eltIter.intValue() + 1)] = ("Element " + eltIter.toString());
/* 307:251 */       for (int alignmentIter = 0; alignmentIter < totalAlignments; alignmentIter++)
/* 308:    */       {
/* 309:252 */         data[(2 * alignmentIter + 0)][0] = ((SequenceAlignment)alignmentSets.get(alignmentIter)).bName;
/* 310:253 */         data[(2 * alignmentIter + 1)][0] = ((SequenceAlignment)alignmentSets.get(alignmentIter)).aName;
/* 311:255 */         if ((((SequenceAlignment)alignmentSets.get(alignmentIter)).size() > eltIter.intValue()) && (((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue()) != null) && (((Pair)((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue())).b != null))
/* 312:    */         {
/* 313:256 */           if (generateNiceOutput) {
/* 314:    */             try
/* 315:    */             {
/* 316:258 */               data[(2 * alignmentIter + 0)][(eltIter.intValue() + 1)] = generator.generate((Entity)((Pair)((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue())).b);
/* 317:    */             }
/* 318:    */             catch (Exception e)
/* 319:    */             {
/* 320:260 */               data[(2 * alignmentIter + 0)][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue())).b).asString();
/* 321:    */             }
/* 322:    */           } else {
/* 323:263 */             data[(2 * alignmentIter + 0)][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue())).b).asString();
/* 324:    */           }
/* 325:    */         }
/* 326:    */         else {
/* 327:266 */           data[(2 * alignmentIter + 0)][(eltIter.intValue() + 1)] = "---";
/* 328:    */         }
/* 329:267 */         if ((((SequenceAlignment)alignmentSets.get(alignmentIter)).size() > eltIter.intValue()) && (((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue()) != null) && (((Pair)((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue())).a != null))
/* 330:    */         {
/* 331:268 */           if (generateNiceOutput) {
/* 332:    */             try
/* 333:    */             {
/* 334:270 */               data[(2 * alignmentIter + 1)][(eltIter.intValue() + 1)] = generator.generate((Entity)((Pair)((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue())).a);
/* 335:    */             }
/* 336:    */             catch (Exception e)
/* 337:    */             {
/* 338:272 */               data[(2 * alignmentIter + 1)][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue())).a).asString();
/* 339:    */             }
/* 340:    */           } else {
/* 341:275 */             data[(2 * alignmentIter + 1)][(eltIter.intValue() + 1)] = ((Entity)((Pair)((SequenceAlignment)alignmentSets.get(alignmentIter)).get(eltIter.intValue())).a).asString();
/* 342:    */           }
/* 343:    */         }
/* 344:    */         else {
/* 345:278 */           data[(2 * alignmentIter + 1)][(eltIter.intValue() + 1)] = "---";
/* 346:    */         }
/* 347:    */       }
/* 348:    */     }
/* 349:282 */     return generateTable(data, eltSlots);
/* 350:    */   }
/* 351:    */   
/* 352:    */   public static JScrollPane generateTableFromSequence(Sequence sequence, String title)
/* 353:    */   {
/* 354:286 */     Generator generator = Generator.getGenerator();
/* 355:    */     
/* 356:288 */     int totalAlignments = 1;
/* 357:289 */     int totalElements = sequence.getNumberOfChildren();
/* 358:    */     
/* 359:291 */     Object[][] data = new Object[totalAlignments][totalElements + 1];
/* 360:292 */     String[] eltSlots = new String[totalElements + 1];
/* 361:    */     
/* 362:294 */     eltSlots[0] = "Story";
/* 363:295 */     data[0][0] = title;
/* 364:297 */     for (Integer eltIter = Integer.valueOf(0); eltIter.intValue() < totalElements; eltIter = Integer.valueOf(eltIter.intValue() + 1))
/* 365:    */     {
/* 366:298 */       eltSlots[(eltIter.intValue() + 1)] = ("Element " + eltIter.toString());
/* 367:    */       
/* 368:300 */       data[0][(eltIter.intValue() + 1)] = generator.generate(sequence.getElement(eltIter.intValue()));
/* 369:    */     }
/* 370:303 */     return generateTable(data, eltSlots);
/* 371:    */   }
/* 372:    */   
/* 373:    */   public static JScrollPane generateTable(Object[][] data, String[] cols)
/* 374:    */   {
/* 375:307 */     JTable alignmentTable = new JTable(data, cols);
/* 376:308 */     TextAreaRenderer textAreaRenderer = new TextAreaRenderer();
/* 377:309 */     TableColumnModel cmodel = alignmentTable.getColumnModel();
/* 378:311 */     for (int i = 0; i < cmodel.getColumnCount(); i++)
/* 379:    */     {
/* 380:312 */       cmodel.getColumn(i).setPreferredWidth(175);
/* 381:313 */       cmodel.getColumn(i).setCellRenderer(textAreaRenderer);
/* 382:    */     }
/* 383:317 */     alignmentTable.setAutoResizeMode(0);
/* 384:    */     
/* 385:319 */     JScrollPane scrollPane = new JScrollPane(alignmentTable);
/* 386:    */     
/* 387:321 */     return scrollPane;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public static void setCellColor(JScrollPane scrollPane, int row, int column, Color color)
/* 391:    */   {
/* 392:328 */     ((TextAreaRenderer)((JTable)scrollPane.getViewport().getView()).getCellRenderer(row, column)).setColor(row, column, color);
/* 393:    */   }
/* 394:    */   
/* 395:    */   public static void main(String[] args)
/* 396:    */   {
/* 397:332 */     Sequence GapStory = Demo.ComplexGapStory();
/* 398:333 */     Sequence GiveStory = Demo.VerboseGive();
/* 399:334 */     Sequence ComplexTakeStory = Demo.ComplexTakeStory();
/* 400:    */     
/* 401:336 */     GapFiller gf = new GapFiller();
/* 402:    */     
/* 403:338 */     gf.addPattern(GiveStory);
/* 404:339 */     gf.addPattern(ComplexTakeStory);
/* 405:340 */     Mark.say(new Object[] {GapStory.asString() });
/* 406:341 */     Sequence s = gf.fillGap(GapStory);
/* 407:342 */     Mark.say(new Object[] {s.asString() });
/* 408:    */     
/* 409:344 */     GapViewer viewer = new GapViewer();
/* 410:345 */     viewer.renderSequence(new BetterSignal(new Object[] { s, gf.gapsFilledAt }));
/* 411:346 */     viewer.renderGap(gf.lastAlignments);
/* 412:    */     
/* 413:348 */     JFrame frame = new JFrame("Simple Alignment Viewer");
/* 414:349 */     frame.setDefaultCloseOperation(3);
/* 415:350 */     frame.getContentPane().add(viewer);
/* 416:351 */     frame.pack();
/* 417:352 */     frame.setVisible(true);
/* 418:    */   }
/* 419:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.GapViewer
 * JD-Core Version:    0.7.0.1
 */