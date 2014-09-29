/*   1:    */ package matthewFay.Depricated;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import java.awt.BorderLayout;
/*   9:    */ import java.awt.GridLayout;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.swing.ButtonGroup;
/*  15:    */ import javax.swing.JButton;
/*  16:    */ import javax.swing.JCheckBox;
/*  17:    */ import javax.swing.JPanel;
/*  18:    */ import javax.swing.JRadioButton;
/*  19:    */ import javax.swing.JTabbedPane;
/*  20:    */ import matthewFay.Demo;
/*  21:    */ import matthewFay.StoryAlignment.Alignment;
/*  22:    */ import matthewFay.StoryAlignment.RankedSequenceAlignmentSet;
/*  23:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  24:    */ import parameters.Radio;
/*  25:    */ import utils.Mark;
/*  26:    */ import windowGroup.WindowGroupManager;
/*  27:    */ 
/*  28:    */ @Deprecated
/*  29:    */ public class StoryAligner
/*  30:    */   extends AbstractWiredBox
/*  31:    */   implements ActionListener
/*  32:    */ {
/*  33:    */   private static final String DO_SCENE_DEMO = "doSceneDemo";
/*  34:    */   private static final String DO_BEST_ALIGNMENTS_DEMO = "doBestAlignmentsDemo";
/*  35:    */   private static final String COMPLETE_SEARCH = "complete";
/*  36:    */   private static final String FASTER_SEARCH = "faster";
/*  37:    */   private static final String LAZY_SEARCH = "lazy";
/*  38:    */   public static final String STORY_PORT = "story port";
/*  39:    */   public static final String STORY_PORT2 = "story port2";
/*  40:    */   public static final String REMEMBER_STORY = "remember story";
/*  41:    */   public static final boolean debugBest = true;
/*  42:    */   public static final String STAGE_DIRECTION = "stage direction";
/*  43:    */   private static final boolean ignoreLowSocres = true;
/*  44:    */   public static final boolean restrictMatches = true;
/*  45:    */   Sequence story1;
/*  46:    */   Sequence story2;
/*  47:    */   List<Sequence> rememberedStories;
/*  48: 47 */   GapFiller gf = new GapFiller();
/*  49: 49 */   JPanel storyAlignmentPanel = null;
/*  50:    */   
/*  51:    */   public JPanel getStoryAlignmentPanel()
/*  52:    */   {
/*  53: 51 */     if (this.storyAlignmentPanel == null)
/*  54:    */     {
/*  55: 52 */       this.storyAlignmentPanel = new JPanel();
/*  56: 53 */       this.storyAlignmentPanel.setName("StoryAlignment");
/*  57:    */       try
/*  58:    */       {
/*  59: 55 */         LocalGenesis.localGenesis().getWindowGroupManager().addJComponent(this.storyAlignmentPanel);
/*  60:    */       }
/*  61:    */       catch (Exception e)
/*  62:    */       {
/*  63: 57 */         Mark.say(new Object[] {"Not in Local Genesis" });
/*  64:    */       }
/*  65:    */     }
/*  66: 60 */     return this.storyAlignmentPanel;
/*  67:    */   }
/*  68:    */   
/*  69: 63 */   JTabbedPane tabbedPane = null;
/*  70:    */   JPanel alignmentPanel;
/*  71:    */   JPanel controlPanel;
/*  72:    */   JPanel treePanel;
/*  73:    */   JCheckBox niceOutputCheckBox;
/*  74:    */   ButtonGroup alignmentTypeGroup;
/*  75:    */   JRadioButton completeAlignmentSearchButton;
/*  76:    */   JRadioButton fasterAlignmentSearchButton;
/*  77:    */   JRadioButton lazyAlignmentSearchButton;
/*  78: 73 */   SequenceAligner.AlignmentType alignmentType = SequenceAligner.AlignmentType.NORMAL;
/*  79:    */   JCheckBox gapFill;
/*  80:    */   JButton sceneDemoButton;
/*  81:    */   JButton bestAlignmentsDemoButton;
/*  82:    */   
/*  83:    */   public StoryAligner()
/*  84:    */   {
/*  85: 81 */     setName("StoryAligner");
/*  86:    */     
/*  87: 83 */     Connections.getPorts(this).addSignalProcessor("story port", "processStory");
/*  88: 84 */     Connections.getPorts(this).addSignalProcessor("story port2", "processStory2");
/*  89:    */     
/*  90: 86 */     Connections.getPorts(this).addSignalProcessor("remember story", "rememberStory");
/*  91: 87 */     Connections.getPorts(this).addSignalProcessor("stage direction", "processDirection");
/*  92:    */     
/*  93:    */ 
/*  94: 90 */     this.rememberedStories = new ArrayList();
/*  95:    */     
/*  96: 92 */     JPanel panel = getStoryAlignmentPanel();
/*  97: 93 */     panel.setLayout(new BorderLayout());
/*  98:    */     
/*  99: 95 */     this.tabbedPane = new JTabbedPane();
/* 100: 96 */     this.alignmentPanel = new JPanel();
/* 101: 97 */     this.alignmentPanel.setLayout(new BorderLayout());
/* 102: 98 */     this.tabbedPane.addTab("Alignments", this.alignmentPanel);
/* 103: 99 */     this.tabbedPane.addTab("Match Tree", this.treePanel = new JPanel(new BorderLayout()));
/* 104:100 */     this.tabbedPane.addTab("Controls", this.controlPanel = new JPanel(new GridLayout(3, 3, 10, 10)));
/* 105:    */     
/* 106:    */ 
/* 107:103 */     this.niceOutputCheckBox = new JCheckBox("English Output");
/* 108:104 */     this.niceOutputCheckBox.setSelected(SequenceAligner.generateNiceOutput);
/* 109:105 */     this.controlPanel.add(this.niceOutputCheckBox);
/* 110:    */     
/* 111:107 */     this.completeAlignmentSearchButton = new JRadioButton("Complete Alignment Search");
/* 112:108 */     this.fasterAlignmentSearchButton = new JRadioButton("Faster Alignment Search");
/* 113:109 */     this.lazyAlignmentSearchButton = new JRadioButton("Lazy Alignment Search");
/* 114:    */     
/* 115:111 */     this.completeAlignmentSearchButton.setSelected(false);
/* 116:112 */     this.fasterAlignmentSearchButton.setSelected(false);
/* 117:113 */     this.lazyAlignmentSearchButton.setSelected(true);
/* 118:    */     
/* 119:115 */     this.completeAlignmentSearchButton.addActionListener(this);
/* 120:116 */     this.fasterAlignmentSearchButton.addActionListener(this);
/* 121:117 */     this.lazyAlignmentSearchButton.addActionListener(this);
/* 122:    */     
/* 123:119 */     this.completeAlignmentSearchButton.setActionCommand("complete");
/* 124:120 */     this.fasterAlignmentSearchButton.setActionCommand("faster");
/* 125:121 */     this.lazyAlignmentSearchButton.setActionCommand("lazy");
/* 126:    */     
/* 127:123 */     this.alignmentTypeGroup = new ButtonGroup();
/* 128:124 */     this.alignmentTypeGroup.add(this.completeAlignmentSearchButton);
/* 129:125 */     this.alignmentTypeGroup.add(this.fasterAlignmentSearchButton);
/* 130:126 */     this.alignmentTypeGroup.add(this.lazyAlignmentSearchButton);
/* 131:    */     
/* 132:128 */     this.controlPanel.add(this.completeAlignmentSearchButton);
/* 133:129 */     this.controlPanel.add(this.fasterAlignmentSearchButton);
/* 134:130 */     this.controlPanel.add(this.lazyAlignmentSearchButton);
/* 135:    */     
/* 136:    */ 
/* 137:133 */     this.gapFill = new JCheckBox("Do Gap Filling");
/* 138:134 */     this.gapFill.setSelected(false);
/* 139:135 */     this.controlPanel.add(this.gapFill);
/* 140:136 */     this.sceneDemoButton = new JButton("Do Scene Alignment Demo");
/* 141:137 */     this.sceneDemoButton.setActionCommand("doSceneDemo");
/* 142:138 */     this.sceneDemoButton.addActionListener(this);
/* 143:139 */     this.controlPanel.add(this.sceneDemoButton);
/* 144:140 */     this.bestAlignmentsDemoButton = new JButton("Do Best Alignments Demo");
/* 145:141 */     this.bestAlignmentsDemoButton.setActionCommand("doBestAlignmentsDemo");
/* 146:142 */     this.bestAlignmentsDemoButton.addActionListener(this);
/* 147:143 */     this.controlPanel.add(this.bestAlignmentsDemoButton);
/* 148:    */     
/* 149:    */ 
/* 150:146 */     panel.add(this.tabbedPane, "Center");
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void processDirection(Object o)
/* 154:    */   {
/* 155:150 */     if (!Radio.alignmentButton.isSelected()) {
/* 156:151 */       return;
/* 157:    */     }
/* 158:152 */     if (o == "reset")
/* 159:    */     {
/* 160:153 */       this.rememberedStories.clear();
/* 161:154 */       this.story1 = null;
/* 162:155 */       this.story2 = null;
/* 163:156 */       this.gf.clearPatternBuffer();
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void actionPerformed(ActionEvent e)
/* 168:    */   {
/* 169:162 */     if (e.getActionCommand().equals("doSceneDemo")) {
/* 170:163 */       SceneRankDemo();
/* 171:    */     }
/* 172:165 */     if (e.getActionCommand().equals("doBestAlignmentsDemo")) {
/* 173:166 */       BestAlignmentsDemo();
/* 174:    */     }
/* 175:168 */     if (e.getActionCommand().equals("complete")) {
/* 176:169 */       this.alignmentType = SequenceAligner.AlignmentType.COMPLETE;
/* 177:    */     }
/* 178:171 */     if (e.getActionCommand().equals("faster")) {
/* 179:172 */       this.alignmentType = SequenceAligner.AlignmentType.FASTER;
/* 180:    */     }
/* 181:174 */     if (e.getActionCommand().equals("lazy")) {
/* 182:175 */       this.alignmentType = SequenceAligner.AlignmentType.NORMAL;
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void rememberStory(Object signal)
/* 187:    */   {
/* 188:180 */     if (!Radio.alignmentButton.isSelected()) {
/* 189:181 */       return;
/* 190:    */     }
/* 191:182 */     if (!(signal instanceof Sequence)) {
/* 192:183 */       return;
/* 193:    */     }
/* 194:184 */     Sequence storySignal = (Sequence)signal;
/* 195:185 */     Mark.say(new Object[] {"Remembering: ", storySignal.asString() });
/* 196:186 */     this.rememberedStories.add((Sequence)storySignal.deepClone());
/* 197:187 */     this.gf.addPattern((Sequence)storySignal.deepClone());
/* 198:188 */     this.story1 = null;
/* 199:189 */     this.story2 = null;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void processStory(Object input)
/* 203:    */   {
/* 204:193 */     if (!Radio.alignmentButton.isSelected()) {
/* 205:194 */       return;
/* 206:    */     }
/* 207:195 */     if (!(input instanceof Sequence)) {
/* 208:196 */       return;
/* 209:    */     }
/* 210:197 */     Sequence storySignal = (Sequence)input;
/* 211:    */     
/* 212:199 */     alignStory(storySignal, 1);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void processStory2(Object input)
/* 216:    */   {
/* 217:203 */     if (!Radio.alignmentButton.isSelected()) {
/* 218:204 */       return;
/* 219:    */     }
/* 220:205 */     if (!(input instanceof Sequence)) {
/* 221:206 */       return;
/* 222:    */     }
/* 223:207 */     Sequence storySignal = (Sequence)input;
/* 224:    */     
/* 225:209 */     alignStory(storySignal, 2);
/* 226:    */   }
/* 227:    */   
/* 228:    */   public SequenceAlignment alignStory(Sequence inputStory, int inputSlot)
/* 229:    */   {
/* 230:213 */     SequenceAligner.generateNiceOutput = this.niceOutputCheckBox.isSelected();
/* 231:214 */     SequenceAlignment alignment = null;
/* 232:215 */     if ((inputSlot == 1) && (this.story1 == null)) {
/* 233:217 */       this.story1 = inputStory;
/* 234:    */     }
/* 235:219 */     if ((inputSlot == 2) && (this.story2 == null)) {
/* 236:221 */       this.story2 = inputStory;
/* 237:    */     }
/* 238:223 */     if ((this.story1 != null) && (this.story2 != null))
/* 239:    */     {
/* 240:225 */       if (this.gapFill.isSelected())
/* 241:    */       {
/* 242:226 */         this.gf.addPattern(this.story1);
/* 243:227 */         this.gf.fillGap(this.story2);
/* 244:    */         
/* 245:229 */         alignment = this.gf.lastAlignment;
/* 246:    */         
/* 247:231 */         this.alignmentPanel.removeAll();
/* 248:232 */         this.alignmentPanel.add(GapViewer.generateTable(alignment), "Center");
/* 249:233 */         this.tabbedPane.repaint();
/* 250:    */       }
/* 251:    */       else
/* 252:    */       {
/* 253:235 */         SequenceAligner.generateNiceOutput = this.niceOutputCheckBox.isSelected();
/* 254:    */         
/* 255:237 */         SequenceAligner aligner = new SequenceAligner();
/* 256:    */         
/* 257:239 */         RankedSequenceAlignmentSet<Entity, Entity> bestAlignments = aligner.align(this.story1, this.story2, this.alignmentType);
/* 258:    */         
/* 259:241 */         this.alignmentPanel.removeAll();
/* 260:242 */         this.alignmentPanel.add(GapViewer.generateFullTable(bestAlignments), "Center");
/* 261:243 */         this.tabbedPane.repaint();
/* 262:    */         
/* 263:245 */         alignment = (SequenceAlignment)bestAlignments.get(0);
/* 264:    */       }
/* 265:247 */       this.story1 = null;
/* 266:248 */       this.story2 = null;
/* 267:    */     }
/* 268:249 */     else if ((this.story2 != null) && (!this.rememberedStories.isEmpty()))
/* 269:    */     {
/* 270:250 */       if (this.gapFill.isSelected())
/* 271:    */       {
/* 272:251 */         this.gf.fillGap(this.story2);
/* 273:    */         
/* 274:253 */         alignment = this.gf.lastAlignment;
/* 275:    */         
/* 276:255 */         this.alignmentPanel.removeAll();
/* 277:256 */         this.alignmentPanel.add(GapViewer.generateTable(alignment), "Center");
/* 278:257 */         this.tabbedPane.repaint();
/* 279:    */       }
/* 280:    */       else
/* 281:    */       {
/* 282:259 */         SequenceAligner.generateNiceOutput = this.niceOutputCheckBox.isSelected();
/* 283:260 */         SequenceAligner aligner = new SequenceAligner();
/* 284:262 */         for (Sequence s : this.rememberedStories) {
/* 285:263 */           Mark.say(new Object[] {"Pattern: ", s.asString() });
/* 286:    */         }
/* 287:266 */         RankedSequenceAlignmentSet<Entity, Entity> alignments = aligner.findBestAlignments(this.rememberedStories, this.story2);
/* 288:267 */         alignments.globalAlignment();
/* 289:    */         
/* 290:269 */         this.alignmentPanel.removeAll();
/* 291:270 */         this.alignmentPanel.add(GapViewer.generateFullTable(alignments), "Center");
/* 292:    */         
/* 293:272 */         this.tabbedPane.repaint();
/* 294:    */       }
/* 295:    */     }
/* 296:275 */     return alignment;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void BestAlignmentsDemo()
/* 300:    */   {
/* 301:279 */     Sequence exchangeStory = Demo.ExchangeStory();
/* 302:280 */     Sequence giveStory = Demo.GiveStory();
/* 303:    */     
/* 304:282 */     SequenceAligner aligner = new SequenceAligner();
/* 305:283 */     RankedSequenceAlignmentSet<Entity, Entity> bestAlignments = aligner.align(giveStory, exchangeStory, SequenceAligner.AlignmentType.COMPLETE);
/* 306:    */     
/* 307:285 */     this.alignmentPanel.removeAll();
/* 308:286 */     this.alignmentPanel.add(GapViewer.generateFullTable(bestAlignments), "Center");
/* 309:287 */     this.tabbedPane.repaint();
/* 310:289 */     for (Alignment<Entity, Entity> a : bestAlignments)
/* 311:    */     {
/* 312:290 */       Mark.say(new Object[] {"Alignment:" });
/* 313:291 */       SequenceAligner.outputAlignment(a);
/* 314:    */     }
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void SceneRankDemo()
/* 318:    */   {
/* 319:296 */     SequenceAligner.generateNiceOutput = this.niceOutputCheckBox.isSelected();
/* 320:    */     
/* 321:298 */     Sequence gapStory = Demo.GapStory();
/* 322:299 */     Sequence giveStory = Demo.GiveStory();
/* 323:300 */     Sequence fleeStory = Demo.FleeStory();
/* 324:301 */     Sequence takeStory = Demo.TakeStory();
/* 325:302 */     Sequence throwCatchStory = Demo.ThrowCatchStory();
/* 326:303 */     Sequence followStory = Demo.FollowStory();
/* 327:304 */     Sequence exchangeStory = Demo.ExchangeStory();
/* 328:    */     
/* 329:306 */     List<Sequence> patterns = new ArrayList();
/* 330:307 */     patterns.add(giveStory);
/* 331:    */     
/* 332:309 */     patterns.add(takeStory);
/* 333:310 */     patterns.add(throwCatchStory);
/* 334:    */     
/* 335:312 */     patterns.add(exchangeStory);
/* 336:    */     
/* 337:314 */     SequenceAligner aligner = new SequenceAligner();
/* 338:315 */     RankedSequenceAlignmentSet<Entity, Entity> alignments = aligner.findBestAlignments(patterns, gapStory);
/* 339:316 */     this.alignmentPanel.removeAll();
/* 340:317 */     this.alignmentPanel.add(GapViewer.generateTable(alignments), "Center");
/* 341:318 */     this.tabbedPane.repaint();
/* 342:    */   }
/* 343:    */   
/* 344:    */   public static void main(String[] args)
/* 345:    */   {
/* 346:322 */     Sequence give = Demo.GiveStory();
/* 347:323 */     Sequence gap = Demo.GapStory();
/* 348:    */     
/* 349:325 */     StoryAligner storyAligner = new StoryAligner();
/* 350:326 */     storyAligner.alignStory(give, 1);
/* 351:327 */     Alignment<Entity, Entity> alignment = storyAligner.alignStory(gap, 2);
/* 352:    */   }
/* 353:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.Depricated.StoryAligner
 * JD-Core Version:    0.7.0.1
 */