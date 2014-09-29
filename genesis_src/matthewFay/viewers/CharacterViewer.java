/*   1:    */ package matthewFay.viewers;
/*   2:    */ 
/*   3:    */ import ati.ParallelJPanel;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import connections.WiredBox;
/*   6:    */ import java.awt.BorderLayout;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.event.ActionEvent;
/*   9:    */ import java.awt.event.ActionListener;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.HashSet;
/*  14:    */ import java.util.Iterator;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import java.util.Set;
/*  18:    */ import javax.swing.JButton;
/*  19:    */ import javax.swing.JCheckBox;
/*  20:    */ import javax.swing.JLabel;
/*  21:    */ import javax.swing.JPanel;
/*  22:    */ import javax.swing.JTabbedPane;
/*  23:    */ import matthewFay.CharacterModeling.CharacterProcessor;
/*  24:    */ import matthewFay.CharacterModeling.representations.Trait;
/*  25:    */ import matthewFay.StoryAlignment.Aligner;
/*  26:    */ import matthewFay.StoryAlignment.Alignment;
/*  27:    */ import matthewFay.StoryAlignment.SequenceAlignment;
/*  28:    */ import matthewFay.StoryAlignment.SortableAlignmentList;
/*  29:    */ import matthewFay.StoryGeneration.PlotWeaver;
/*  30:    */ import matthewFay.Utilities.DefaultHashMap;
/*  31:    */ import matthewFay.Utilities.Generalizer;
/*  32:    */ import matthewFay.Utilities.HashMatrix;
/*  33:    */ import matthewFay.representations.BasicCharacterModel;
/*  34:    */ import minilisp.LList;
/*  35:    */ import utils.Mark;
/*  36:    */ import utils.PairOfEntities;
/*  37:    */ 
/*  38:    */ public class CharacterViewer
/*  39:    */   extends JPanel
/*  40:    */   implements WiredBox, ActionListener, MatrixGridViewer.MatrixClickListener
/*  41:    */ {
/*  42: 43 */   public static String TRAIT = "trait port";
/*  43:    */   private JTabbedPane tabbedPane;
/*  44:    */   private JPanel characterAlignmentComparePanel;
/*  45:    */   private JPanel characterVectorComparePanel;
/*  46:    */   private JPanel characterAlignmentDetailsPanel;
/*  47:    */   private ParallelJPanel controlPanel;
/*  48: 53 */   private JButton compareCharactersAlignmentButton = new JButton("Compare Characters via Alignment");
/*  49: 54 */   private JButton compareCharactersVectorButton = new JButton("Compare Characters via Event Vectors");
/*  50: 55 */   private JButton saveCharacterDocument = new JButton("Save Character stories");
/*  51: 56 */   private JButton clearCharactersButton = new JButton("Clear Character Library");
/*  52: 57 */   private JButton weaveCharactersButton = new JButton("Weave all Characters");
/*  53: 59 */   private JButton expLearnTraitButton = new JButton("Learn vicious trait");
/*  54: 61 */   public static JCheckBox disableCharacterProcessor = new JCheckBox("Disable Character Processor", false);
/*  55: 62 */   private JCheckBox constrainVectorGeneralization = new JCheckBox("Constrain Event Generalization", false);
/*  56: 64 */   private JLabel characterCountLabel = new JLabel("0 Characters in Library");
/*  57:    */   
/*  58:    */   public String getName()
/*  59:    */   {
/*  60: 67 */     return "Character Viewer";
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void paint(Graphics g)
/*  64:    */   {
/*  65: 72 */     this.characterCountLabel.setText(CharacterProcessor.getCharacterLibrary().size() + " Characters in Library");
/*  66: 73 */     super.paint(g);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public CharacterViewer()
/*  70:    */   {
/*  71: 77 */     super(new BorderLayout());
/*  72:    */     
/*  73: 79 */     this.tabbedPane = new JTabbedPane();
/*  74:    */     
/*  75: 81 */     this.controlPanel = new ParallelJPanel();
/*  76: 82 */     this.controlPanel.addLeft(this.compareCharactersAlignmentButton);
/*  77: 83 */     this.controlPanel.addLeft(this.compareCharactersVectorButton);
/*  78: 84 */     this.controlPanel.addLeft(this.expLearnTraitButton);
/*  79:    */     
/*  80: 86 */     this.controlPanel.addCenter(this.saveCharacterDocument);
/*  81: 87 */     this.controlPanel.addCenter(this.constrainVectorGeneralization);
/*  82: 88 */     this.controlPanel.addCenter(disableCharacterProcessor);
/*  83: 89 */     this.controlPanel.addRight(this.clearCharactersButton);
/*  84: 90 */     this.controlPanel.addRight(this.characterCountLabel);
/*  85: 91 */     this.tabbedPane.add("Controls", this.controlPanel);
/*  86:    */     
/*  87: 93 */     this.controlPanel.addLeft(this.weaveCharactersButton);
/*  88:    */     
/*  89: 95 */     this.characterAlignmentComparePanel = new JPanel();
/*  90: 96 */     this.tabbedPane.add("Alignment Comparison", this.characterAlignmentComparePanel);
/*  91: 97 */     this.characterVectorComparePanel = new JPanel();
/*  92: 98 */     this.tabbedPane.add("Vector Comparison", this.characterVectorComparePanel);
/*  93: 99 */     this.characterAlignmentDetailsPanel = new JPanel(new BorderLayout());
/*  94:100 */     this.tabbedPane.add("Alignment Details", this.characterAlignmentDetailsPanel);
/*  95:    */     
/*  96:102 */     this.compareCharactersAlignmentButton.setActionCommand("compare_align");
/*  97:103 */     this.compareCharactersAlignmentButton.addActionListener(this);
/*  98:104 */     this.compareCharactersVectorButton.setActionCommand("compare_vector");
/*  99:105 */     this.compareCharactersVectorButton.addActionListener(this);
/* 100:    */     
/* 101:107 */     this.expLearnTraitButton.setActionCommand("exp_learn_trait");
/* 102:108 */     this.expLearnTraitButton.addActionListener(this);
/* 103:    */     
/* 104:110 */     this.saveCharacterDocument.setActionCommand("save_characters");
/* 105:111 */     this.saveCharacterDocument.addActionListener(this);
/* 106:112 */     this.clearCharactersButton.setActionCommand("clear_library");
/* 107:113 */     this.clearCharactersButton.addActionListener(this);
/* 108:    */     
/* 109:115 */     this.weaveCharactersButton.setActionCommand("weave_characters");
/* 110:116 */     this.weaveCharactersButton.addActionListener(this);
/* 111:    */     
/* 112:118 */     add(this.tabbedPane);
/* 113:    */   }
/* 114:    */   
/* 115:    */   private void doCompareCharactersAlignment()
/* 116:    */   {
/* 117:122 */     HashMatrix<String, String, Float> comparison_scores = new HashMatrix();
/* 118:123 */     Aligner aligner = new Aligner();
/* 119:    */     Iterator localIterator2;
/* 120:124 */     for (Iterator localIterator1 = CharacterProcessor.getCharacterLibrary().values().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 121:    */     {
/* 122:124 */       BasicCharacterModel c1 = (BasicCharacterModel)localIterator1.next();
/* 123:125 */       localIterator2 = CharacterProcessor.getCharacterLibrary().values().iterator(); continue;BasicCharacterModel c2 = (BasicCharacterModel)localIterator2.next();
/* 124:126 */       if (!comparison_scores.contains(c1.getSimpleName(), c2.getSimpleName()))
/* 125:    */       {
/* 126:127 */         LList<PairOfEntities> bindings = new LList();
/* 127:128 */         bindings = bindings.cons(new PairOfEntities(c1.getEntity(), c2.getEntity()));
/* 128:129 */         SortableAlignmentList alignments = aligner.align(c1.getParticipantEvents(), c2.getParticipantEvents(), bindings);
/* 129:130 */         if (!alignments.isEmpty())
/* 130:    */         {
/* 131:131 */           float adjusted_score = ((Alignment)alignments.get(0)).score / ((Alignment)alignments.get(0)).size();
/* 132:132 */           comparison_scores.put(c1.getSimpleName(), c2.getSimpleName(), Float.valueOf(adjusted_score));
/* 133:133 */           comparison_scores.put(c2.getSimpleName(), c1.getSimpleName(), Float.valueOf(adjusted_score));
/* 134:    */         }
/* 135:    */       }
/* 136:    */     }
/* 137:138 */     this.tabbedPane.remove(this.characterAlignmentComparePanel);
/* 138:139 */     this.characterAlignmentComparePanel = new MatrixGridViewer(comparison_scores);
/* 139:140 */     ((MatrixGridViewer)this.characterAlignmentComparePanel).addMatrixClickListener(this);
/* 140:141 */     this.tabbedPane.add("Alignment Comparison", this.characterAlignmentComparePanel);
/* 141:142 */     this.tabbedPane.setSelectedComponent(this.characterAlignmentComparePanel);
/* 142:    */   }
/* 143:    */   
/* 144:    */   private void doCompareCharactersVector()
/* 145:    */   {
/* 146:146 */     HashMatrix<String, String, Float> comparison_scores = new HashMatrix();
/* 147:    */     Iterator localIterator2;
/* 148:147 */     for (Iterator localIterator1 = CharacterProcessor.getCharacterLibrary().values().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 149:    */     {
/* 150:147 */       BasicCharacterModel c1 = (BasicCharacterModel)localIterator1.next();
/* 151:148 */       localIterator2 = CharacterProcessor.getCharacterLibrary().values().iterator(); continue;BasicCharacterModel c2 = (BasicCharacterModel)localIterator2.next();
/* 152:149 */       if (!comparison_scores.contains(c1.getSimpleName(), c2.getSimpleName()))
/* 153:    */       {
/* 154:150 */         float adjusted_score = 0.0F;
/* 155:152 */         if (this.constrainVectorGeneralization.isSelected()) {
/* 156:153 */           adjusted_score = cosine(c1.getSemiGeneralizedEventCounts(), c2.getSemiGeneralizedEventCounts());
/* 157:    */         } else {
/* 158:155 */           adjusted_score = cosine(c1.getGeneralizedEventCounts(), c2.getGeneralizedEventCounts());
/* 159:    */         }
/* 160:157 */         comparison_scores.put(c1.getSimpleName(), c2.getSimpleName(), Float.valueOf(adjusted_score));
/* 161:158 */         comparison_scores.put(c2.getSimpleName(), c1.getSimpleName(), Float.valueOf(adjusted_score));
/* 162:    */       }
/* 163:    */     }
/* 164:162 */     this.tabbedPane.remove(this.characterVectorComparePanel);
/* 165:163 */     this.characterVectorComparePanel = new MatrixGridViewer(comparison_scores);
/* 166:164 */     this.tabbedPane.add("Vector Comparison", this.characterVectorComparePanel);
/* 167:165 */     this.tabbedPane.setSelectedComponent(this.characterVectorComparePanel);
/* 168:    */   }
/* 169:    */   
/* 170:    */   private float cosine(DefaultHashMap<String, Integer> a, DefaultHashMap<String, Integer> b)
/* 171:    */   {
/* 172:169 */     Set<String> dimensions = new HashSet();
/* 173:170 */     for (String key : a.keySet()) {
/* 174:171 */       dimensions.add(key);
/* 175:    */     }
/* 176:172 */     for (String key : b.keySet()) {
/* 177:173 */       dimensions.add(key);
/* 178:    */     }
/* 179:174 */     float cosine = 0.0F;
/* 180:175 */     float absum = 0.0F;
/* 181:176 */     float aasum = 0.0F;
/* 182:177 */     float bbsum = 0.0F;
/* 183:178 */     for (String dimension : dimensions)
/* 184:    */     {
/* 185:179 */       absum += ((Integer)a.get(dimension)).intValue() * ((Integer)b.get(dimension)).intValue();
/* 186:180 */       aasum += ((Integer)a.get(dimension)).intValue() * ((Integer)a.get(dimension)).intValue();
/* 187:181 */       bbsum += ((Integer)b.get(dimension)).intValue() * ((Integer)b.get(dimension)).intValue();
/* 188:    */     }
/* 189:183 */     cosine = absum * absum / (aasum * bbsum);
/* 190:184 */     return cosine;
/* 191:    */   }
/* 192:    */   
/* 193:    */   private void saveCharacterStories()
/* 194:    */   {
/* 195:188 */     String character_list = "characters = [";
/* 196:189 */     String action_sets = "action_sets = [";
/* 197:190 */     ArrayList<BasicCharacterModel> characters = new ArrayList(CharacterProcessor.getCharacterLibrary().values());
/* 198:191 */     for (BasicCharacterModel character : characters)
/* 199:    */     {
/* 200:192 */       character_list = character_list + "\"" + character.getEntity() + "\"";
/* 201:193 */       action_sets = action_sets + "\"";
/* 202:194 */       for (Entity event : character.getParticipantEvents())
/* 203:    */       {
/* 204:195 */         String event_string = Generalizer.generalize(event, character.getEntity()).asString().replace(" ", "_");
/* 205:196 */         if (this.constrainVectorGeneralization.isSelected()) {
/* 206:197 */           event_string = Generalizer.generalize(event, character.getEntity(), CharacterProcessor.getCharacterLibrary().keySet()).asString().replace(" ", "_");
/* 207:    */         }
/* 208:199 */         action_sets = action_sets + event_string + " ";
/* 209:    */       }
/* 210:201 */       action_sets = action_sets + "\"";
/* 211:202 */       if (characters.indexOf(character) != characters.size() - 1)
/* 212:    */       {
/* 213:203 */         character_list = character_list + ",\n\t";
/* 214:204 */         action_sets = action_sets + ",\n\t";
/* 215:    */       }
/* 216:    */     }
/* 217:207 */     character_list = character_list + "]\n";
/* 218:208 */     action_sets = action_sets + "]\n";
/* 219:209 */     Mark.say(new Object[] {character_list });
/* 220:210 */     Mark.say(new Object[] {action_sets });
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void doWeave()
/* 224:    */   {
/* 225:214 */     Map<Entity, BasicCharacterModel> cms = CharacterProcessor.getCharacterLibrary();
/* 226:215 */     List<BasicCharacterModel> characters = new ArrayList(cms.values());
/* 227:    */     
/* 228:217 */     PlotWeaver weaver = new PlotWeaver(characters);
/* 229:218 */     List<Entity> woven_plot = weaver.weavePlots();
/* 230:    */     
/* 231:220 */     Mark.say(new Object[] {"Woven plot:" });
/* 232:221 */     for (Entity plot_elt : woven_plot) {
/* 233:222 */       Mark.say(new Object[] {plot_elt.toEnglish() });
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void expLearnTrait()
/* 238:    */   {
/* 239:227 */     BasicCharacterModel macbeth = CharacterProcessor.findBestCharacterModel("macbeth");
/* 240:228 */     BasicCharacterModel claudius = CharacterProcessor.findBestCharacterModel("claudius");
/* 241:229 */     BasicCharacterModel duncan = CharacterProcessor.findBestCharacterModel("duncan");
/* 242:231 */     if ((macbeth == null) || (claudius == null) || (duncan == null))
/* 243:    */     {
/* 244:232 */       Mark.err(new Object[] {"Cannot proceeed, macbeth, claudius, or duncan not found." });
/* 245:233 */       return;
/* 246:    */     }
/* 247:237 */     Mark.say(new Object[] {"Trait Learning Demo: Vicious" });
/* 248:238 */     Mark.say(new Object[] {"---------" });
/* 249:239 */     Mark.say(new Object[] {"Macbeth and Claudius are vicious" });
/* 250:240 */     Trait vicious = new Trait("Vicious");
/* 251:241 */     vicious.addPositiveExample(macbeth);
/* 252:242 */     vicious.addPositiveExample(claudius);
/* 253:    */     
/* 254:244 */     Mark.say(new Object[] {"Current vicious trait:" });
/* 255:245 */     for (Entity e : vicious.getElements()) {
/* 256:246 */       Mark.say(new Object[] {e });
/* 257:    */     }
/* 258:249 */     Mark.say(new Object[] {"Duncan is not vicious" });
/* 259:250 */     vicious.addNegativeExample(duncan);
/* 260:    */     
/* 261:252 */     Mark.say(new Object[] {"Final vicious trait:" });
/* 262:253 */     for (Entity e : vicious.getElements()) {
/* 263:254 */       Mark.say(new Object[] {e });
/* 264:    */     }
/* 265:257 */     TraitViewer.getTraitViewer().addTrait(vicious);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public void actionPerformed(ActionEvent action)
/* 269:    */   {
/* 270:269 */     String command = action.getActionCommand();
/* 271:270 */     if (command.equals("compare_align"))
/* 272:    */     {
/* 273:271 */       doCompareCharactersAlignment();
/* 274:    */     }
/* 275:272 */     else if (command.equals("compare_vector"))
/* 276:    */     {
/* 277:273 */       doCompareCharactersVector();
/* 278:    */     }
/* 279:274 */     else if (command.equals("save_characters"))
/* 280:    */     {
/* 281:275 */       saveCharacterStories();
/* 282:    */     }
/* 283:276 */     else if (command.equals("clear_library"))
/* 284:    */     {
/* 285:277 */       CharacterProcessor.getCharacterLibrary().clear();
/* 286:278 */       this.characterCountLabel.setText(CharacterProcessor.getCharacterLibrary().size() + " Characters in Library");
/* 287:    */     }
/* 288:279 */     else if (command.equals("weave_characters"))
/* 289:    */     {
/* 290:280 */       doWeave();
/* 291:    */     }
/* 292:281 */     else if (command.equals("exp_learn_trait"))
/* 293:    */     {
/* 294:282 */       expLearnTrait();
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   private void doShowCharacterAlignmentDetails(String x_name, String y_name)
/* 299:    */   {
/* 300:288 */     this.characterAlignmentDetailsPanel.removeAll();
/* 301:    */     
/* 302:290 */     BasicCharacterModel character1 = null;
/* 303:291 */     BasicCharacterModel character2 = null;
/* 304:292 */     for (BasicCharacterModel c1 : CharacterProcessor.getCharacterLibrary().values()) {
/* 305:293 */       if (c1.getSimpleName().equals(x_name))
/* 306:    */       {
/* 307:294 */         character1 = c1;
/* 308:295 */         break;
/* 309:    */       }
/* 310:    */     }
/* 311:298 */     for (BasicCharacterModel c2 : CharacterProcessor.getCharacterLibrary().values()) {
/* 312:299 */       if (c2.getSimpleName().equals(y_name))
/* 313:    */       {
/* 314:300 */         character2 = c2;
/* 315:301 */         break;
/* 316:    */       }
/* 317:    */     }
/* 318:305 */     if ((character1 != null) && (character2 != null))
/* 319:    */     {
/* 320:306 */       Mark.say(new Object[] {"Found: " + character1.getSimpleName() + " " + character2.getSimpleName() });
/* 321:    */       
/* 322:308 */       Aligner aligner = new Aligner();
/* 323:309 */       Object bindings = new LList();
/* 324:310 */       bindings = ((LList)bindings).cons(new PairOfEntities(character1.getEntity(), character2.getEntity()));
/* 325:311 */       SortableAlignmentList alignments = aligner.align(character1.getParticipantEvents(), character2.getParticipantEvents(), (LList)bindings);
/* 326:312 */       if (!alignments.isEmpty())
/* 327:    */       {
/* 328:313 */         SequenceAlignment sa = (SequenceAlignment)alignments.get(0);
/* 329:314 */         this.characterAlignmentDetailsPanel.add(AlignmentViewer.generateTable(sa, true), "Center");
/* 330:    */       }
/* 331:316 */       this.tabbedPane.setSelectedComponent(this.characterAlignmentDetailsPanel);
/* 332:    */     }
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void handleMatrixClickEvent(MatrixGridViewer.MatrixClickEvent e)
/* 336:    */   {
/* 337:323 */     doShowCharacterAlignmentDetails(e.x_name, e.y_name);
/* 338:    */   }
/* 339:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.viewers.CharacterViewer
 * JD-Core Version:    0.7.0.1
 */