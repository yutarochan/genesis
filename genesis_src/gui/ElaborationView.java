/*    1:     */ package gui;
/*    2:     */ 
/*    3:     */ import Signals.BetterSignal;
/*    4:     */ import bridge.reps.entities.Entity;
/*    5:     */ import bridge.reps.entities.Sequence;
/*    6:     */ import connections.Connections;
/*    7:     */ import connections.Ports;
/*    8:     */ import connections.WiredBox;
/*    9:     */ import java.awt.BasicStroke;
/*   10:     */ import java.awt.Color;
/*   11:     */ import java.awt.Font;
/*   12:     */ import java.awt.FontMetrics;
/*   13:     */ import java.awt.Graphics;
/*   14:     */ import java.awt.Graphics2D;
/*   15:     */ import java.awt.Point;
/*   16:     */ import java.awt.Rectangle;
/*   17:     */ import java.awt.Stroke;
/*   18:     */ import java.awt.event.MouseEvent;
/*   19:     */ import java.awt.event.MouseListener;
/*   20:     */ import java.awt.event.MouseMotionListener;
/*   21:     */ import java.awt.geom.AffineTransform;
/*   22:     */ import java.awt.geom.NoninvertibleTransformException;
/*   23:     */ import java.awt.geom.Point2D;
/*   24:     */ import java.util.ArrayList;
/*   25:     */ import java.util.HashMap;
/*   26:     */ import java.util.HashSet;
/*   27:     */ import java.util.Iterator;
/*   28:     */ import java.util.List;
/*   29:     */ import java.util.Set;
/*   30:     */ import java.util.TreeSet;
/*   31:     */ import java.util.Vector;
/*   32:     */ import java.util.regex.Pattern;
/*   33:     */ import javax.swing.JPanel;
/*   34:     */ import parameters.Switch;
/*   35:     */ import persistence.JCheckBoxWithMemory;
/*   36:     */ import start.Generator;
/*   37:     */ import storyProcessor.ReflectionDescription;
/*   38:     */ import tools.Predicates;
/*   39:     */ import translator.Translator;
/*   40:     */ import utils.Mark;
/*   41:     */ 
/*   42:     */ public class ElaborationView
/*   43:     */   extends JPanel
/*   44:     */   implements WiredBox, MouseListener, MouseMotionListener
/*   45:     */ {
/*   46:  30 */   private int boxWidth = 110;
/*   47:  32 */   private int boxHeight = 80;
/*   48:  34 */   private int boxHPadding = 20;
/*   49:  36 */   private int boxVPadding = 40;
/*   50:  38 */   private int leftAndRightOffset = 20;
/*   51:  40 */   private int topAndBottomOffset = 50;
/*   52:  42 */   private int leftShift = 0;
/*   53:  44 */   private int upShift = 0;
/*   54:  46 */   private double magnification = 1.0D;
/*   55:     */   private ArrayList<Box> connectedBoxList;
/*   56:     */   private ArrayList<Box> unconnectedBoxList;
/*   57:     */   private ArrayList<Connection> connectionList;
/*   58:  54 */   private HashMap<String, Box> boxSet = new HashMap();
/*   59:  56 */   private HashMap<String, Connection> connectionSet = new HashMap();
/*   60:  58 */   private HashMap<String, String> sentenceCache = new HashMap();
/*   61:  60 */   private final Color crossColor = Color.red;
/*   62:  62 */   private final Color blackBox = Color.black;
/*   63:  64 */   private final Color assertionBox = Color.white;
/*   64:  66 */   private final Color predictionBox = Color.yellow;
/*   65:  68 */   private final Color explanationBox = Color.orange;
/*   66:  70 */   private final Color abductionBox = Color.pink;
/*   67:  72 */   private final Color enablerBox = Color.green;
/*   68:  74 */   private final Color proximityBox = Color.gray;
/*   69:  76 */   private final Color leadsToBox = Color.cyan;
/*   70:  78 */   private final Color meansBox = Color.magenta;
/*   71:  80 */   private final Color conceptBox = Color.green;
/*   72:  82 */   private final Stroke basicStroke = new BasicStroke(1.0F);
/*   73:  84 */   private final Stroke boldStroke = new BasicStroke(3.0F);
/*   74:  86 */   private final Stroke dottedStroke = new BasicStroke(2.0F, 0, 0, 10.0F, new float[] { 3.0F }, 0.0F);
/*   75:  88 */   private final Stroke boldDottedStroke = new BasicStroke(3.0F, 0, 0, 10.0F, new float[] { 6.0F }, 0.0F);
/*   76:  90 */   private float idealRatio = 2.0F;
/*   77:     */   private int rowCount;
/*   78:     */   private int rowWidth;
/*   79:     */   private int rowHeight;
/*   80:     */   public static final String STORY = "story port";
/*   81:     */   public static final String CONCEPT = "concept port";
/*   82:     */   public static final String INSPECTOR = "inspection port";
/*   83:     */   public static final String SUMMARY = "summary";
/*   84: 106 */   private boolean alwaysShowAllElements = false;
/*   85:     */   private AffineTransform inverse;
/*   86:     */   
/*   87:     */   public ElaborationView()
/*   88:     */   {
/*   89: 109 */     addMouseListener(this);
/*   90: 110 */     addMouseMotionListener(this);
/*   91: 111 */     setBackground(Color.white);
/*   92: 112 */     Connections.getPorts(this).addSignalProcessor("story port", "processStory");
/*   93:     */     
/*   94: 114 */     Connections.getPorts(this).addSignalProcessor("concept port", "processConcept");
/*   95:     */     
/*   96: 116 */     Connections.getPorts(this).addSignalProcessor("inspection port", "processFragment");
/*   97:     */     
/*   98: 118 */     Connections.getPorts(this).addSignalProcessor("summary", "processSummary");
/*   99:     */     
/*  100: 120 */     Connections.getPorts(this).addSignalProcessor("reset", "clear");
/*  101:     */   }
/*  102:     */   
/*  103:     */   private void addUnconnectedBoxes()
/*  104:     */   {
/*  105: 125 */     int maxWidth = Math.max(5, maxX());
/*  106: 126 */     int maxHeight = maxY();
/*  107: 127 */     int row = maxHeight + 2;
/*  108: 128 */     int column = 0;
/*  109: 129 */     for (Box box : getUnconnectedBoxList())
/*  110:     */     {
/*  111: 130 */       if (column > maxWidth)
/*  112:     */       {
/*  113: 131 */         column = 0;
/*  114: 132 */         row++;
/*  115:     */       }
/*  116: 134 */       box.setY(row);
/*  117: 135 */       box.setX(column++);
/*  118: 136 */       addBox(box);
/*  119:     */     }
/*  120:     */   }
/*  121:     */   
/*  122:     */   public void processFragment(Object o)
/*  123:     */   {
/*  124:     */     Entity e;
/*  125: 141 */     if ((o instanceof ReflectionDescription))
/*  126:     */     {
/*  127: 142 */       ReflectionDescription rd = (ReflectionDescription)o;
/*  128: 143 */       List<Entity> fragment = rd.getStoryElementsInvolved().getElements();
/*  129: 146 */       if (rd.getStory() == null) {
/*  130: 147 */         return;
/*  131:     */       }
/*  132: 149 */       List<Entity> story = rd.getStory().getElements();
/*  133: 150 */       List<Entity> causes = new ArrayList();
/*  134: 151 */       Sequence result = new Sequence();
/*  135: 152 */       for (Iterator localIterator1 = story.iterator(); localIterator1.hasNext();)
/*  136:     */       {
/*  137: 152 */         e = (Entity)localIterator1.next();
/*  138: 153 */         if (!Predicates.isCause(e))
/*  139:     */         {
/*  140: 155 */           if (fragment.contains(e)) {
/*  141: 156 */             result.addElement(e);
/*  142:     */           }
/*  143:     */         }
/*  144: 161 */         else if (fragment.contains(e.getObject())) {
/*  145: 162 */           for (Entity antecedent : e.getSubject().getElements()) {
/*  146: 164 */             if (fragment.contains(antecedent))
/*  147:     */             {
/*  148: 165 */               result.addElement(e);
/*  149: 166 */               break;
/*  150:     */             }
/*  151:     */           }
/*  152:     */         }
/*  153:     */       }
/*  154: 172 */       processStory(result);
/*  155:     */     }
/*  156: 174 */     else if ((o instanceof BetterSignal))
/*  157:     */     {
/*  158: 175 */       BetterSignal bs = (BetterSignal)o;
/*  159: 176 */       if (bs.size() == 2)
/*  160:     */       {
/*  161: 177 */         Set keepers = (Set)bs.get(0, Set.class);
/*  162: 178 */         Sequence story = (Sequence)bs.get(1, Sequence.class);
/*  163: 179 */         Sequence result = new Sequence();
/*  164: 180 */         for (Entity e : story.getElements()) {
/*  165: 182 */           if (keepers.contains(e)) {
/*  166: 183 */             result.addElement(e);
/*  167:     */           }
/*  168:     */         }
/*  169: 186 */         processStory(result);
/*  170:     */       }
/*  171:     */     }
/*  172:     */   }
/*  173:     */   
/*  174:     */   public void clear(Object o)
/*  175:     */   {
/*  176: 194 */     if (o == "reset")
/*  177:     */     {
/*  178: 196 */       this.sentenceCache.clear();
/*  179: 197 */       processStory(o);
/*  180:     */     }
/*  181:     */   }
/*  182:     */   
/*  183:     */   public void processStory(Object o)
/*  184:     */   {
/*  185: 203 */     getConnectedBoxList().clear();
/*  186: 204 */     getUnconnectedBoxList().clear();
/*  187: 205 */     getConnectionList().clear();
/*  188: 206 */     this.boxSet.clear();
/*  189: 207 */     this.connectionSet.clear();
/*  190: 208 */     this.offsetX = 0;
/*  191: 209 */     this.offsetY = 0;
/*  192: 210 */     this.magnification = 1.0D;
/*  193:     */     try
/*  194:     */     {
/*  195: 212 */       if ((o instanceof Sequence))
/*  196:     */       {
/*  197: 213 */         Sequence sequence = (Sequence)o;
/*  198:     */         
/*  199: 215 */         HashSet<Entity> connectedEntities = new HashSet();
/*  200: 216 */         for (Entity element : sequence.getElements()) {
/*  201: 217 */           processEntity(element, connectedEntities);
/*  202:     */         }
/*  203: 219 */         for (Entity element : sequence.getElements()) {
/*  204: 220 */           if ((!isCausalConnection(element)) && (!connectedEntities.contains(element))) {
/*  205: 221 */             getUnconnectedBoxList().add(getBox(element));
/*  206:     */           }
/*  207:     */         }
/*  208:     */       }
/*  209: 225 */       compress();
/*  210: 226 */       if ((this.alwaysShowAllElements) || (Switch.showDisconnectedSwitch.isSelected())) {
/*  211: 227 */         addUnconnectedBoxes();
/*  212:     */       }
/*  213:     */     }
/*  214:     */     catch (Exception e)
/*  215:     */     {
/*  216: 231 */       Mark.err(new Object[] {"Blew out in ElaborationView.processStory" });
/*  217: 232 */       e.printStackTrace();
/*  218:     */     }
/*  219: 234 */     repaint();
/*  220:     */   }
/*  221:     */   
/*  222:     */   public void processConcept(Object object)
/*  223:     */   {
/*  224: 238 */     if ((object instanceof ReflectionDescription))
/*  225:     */     {
/*  226: 239 */       ReflectionDescription reflectionDescription = (ReflectionDescription)object;
/*  227:     */       
/*  228: 241 */       ArrayList<Box> boxes = getConnectedBoxList();
/*  229: 242 */       for (Box b : boxes) {
/*  230: 243 */         b.setTemporaryColor(null);
/*  231:     */       }
/*  232: 246 */       boxes = new ArrayList();
/*  233: 247 */       List<Entity> elements = reflectionDescription.getStoryElementsInvolved().getElements();
/*  234: 248 */       elements = expandCauses(elements);
/*  235: 250 */       for (Entity t : elements)
/*  236:     */       {
/*  237: 251 */         Box box = getBox(t);
/*  238: 252 */         if (box != null)
/*  239:     */         {
/*  240: 253 */           boxes.add(box);
/*  241: 254 */           box.setTemporaryColor(this.conceptBox);
/*  242:     */         }
/*  243:     */         else
/*  244:     */         {
/*  245: 257 */           Mark.say(new Object[] {"Found NO box for", t.asStringWithIndexes() });
/*  246:     */         }
/*  247:     */       }
/*  248:     */     }
/*  249: 261 */     repaint();
/*  250:     */   }
/*  251:     */   
/*  252:     */   public void processSummary(Object object)
/*  253:     */   {
/*  254: 265 */     if ((object instanceof BetterSignal))
/*  255:     */     {
/*  256: 266 */       BetterSignal bs = (BetterSignal)object;
/*  257: 267 */       Set<Entity> entitySet = (Set)bs.get(0, Set.class);
/*  258:     */       
/*  259: 269 */       ArrayList<Box> boxes = getConnectedBoxList();
/*  260: 270 */       for (Box b : boxes) {
/*  261: 271 */         b.setTemporaryColor(null);
/*  262:     */       }
/*  263: 274 */       boxes = new ArrayList();
/*  264: 275 */       List<Entity> elements = new ArrayList();
/*  265: 276 */       elements.addAll(entitySet);
/*  266:     */       
/*  267: 278 */       elements = expandCauses(elements);
/*  268: 279 */       for (Entity t : elements)
/*  269:     */       {
/*  270: 280 */         Box box = getBox(t);
/*  271: 281 */         if (box != null)
/*  272:     */         {
/*  273: 282 */           boxes.add(box);
/*  274: 283 */           box.setTemporaryColor(this.conceptBox);
/*  275:     */         }
/*  276:     */         else
/*  277:     */         {
/*  278: 286 */           Mark.say(new Object[] {"Found NO box for", t.asStringWithIndexes() });
/*  279:     */         }
/*  280:     */       }
/*  281:     */     }
/*  282:     */     else
/*  283:     */     {
/*  284: 291 */       Mark.say(new Object[] {"Ready to work on processSummary" });
/*  285:     */     }
/*  286: 293 */     repaint();
/*  287:     */   }
/*  288:     */   
/*  289:     */   private List<Entity> expandCauses(List<Entity> elements)
/*  290:     */   {
/*  291: 297 */     ArrayList<Entity> result = new ArrayList();
/*  292: 298 */     for (Entity e : elements) {
/*  293: 301 */       if ((e.isA("cause")) && (e.getSubject().isA("conjuction")))
/*  294:     */       {
/*  295: 302 */         Vector<Entity> antecedents = e.getSubject().getElements();
/*  296: 303 */         Entity consequent = e.getObject();
/*  297: 304 */         for (Entity a : antecedents) {
/*  298: 305 */           result.add(a);
/*  299:     */         }
/*  300: 307 */         result.add(consequent);
/*  301:     */       }
/*  302:     */       else
/*  303:     */       {
/*  304: 310 */         result.add(e);
/*  305:     */       }
/*  306:     */     }
/*  307: 313 */     return result;
/*  308:     */   }
/*  309:     */   
/*  310:     */   private Entity translate(String string)
/*  311:     */     throws Exception
/*  312:     */   {
/*  313: 317 */     Entity s = Translator.getTranslator().translate(string);
/*  314: 318 */     if ((s.sequenceP()) && (s.getElements().size() == 1)) {
/*  315: 319 */       return s.getElement(0);
/*  316:     */     }
/*  317: 321 */     Mark.err(new Object[] {"Translation produces > 1 elements from", string });
/*  318: 322 */     return null;
/*  319:     */   }
/*  320:     */   
/*  321:     */   String hash(Entity e)
/*  322:     */   {
/*  323: 326 */     return e.asStringWithIndexes();
/*  324:     */   }
/*  325:     */   
/*  326:     */   private void processEntity(Entity entity, HashSet<Entity> connectedEntities)
/*  327:     */   {
/*  328:     */     try
/*  329:     */     {
/*  330: 331 */       if (isCausalConnection(entity))
/*  331:     */       {
/*  332: 332 */         Connection connection = (Connection)this.connectionSet.get(hash(entity));
/*  333: 333 */         Box consequentBox = null;
/*  334: 334 */         if (connection == null)
/*  335:     */         {
/*  336: 335 */           Entity consequent = entity.getObject();
/*  337: 336 */           TreeSet<Box> antecedentBoxes = new TreeSet();
/*  338: 337 */           for (Entity antecedent : entity.getSubject().getElements())
/*  339:     */           {
/*  340: 338 */             antecedentBoxes.add(addBox(getBox(antecedent)));
/*  341: 339 */             connectedEntities.add(antecedent);
/*  342:     */           }
/*  343: 341 */           consequentBox = addBox(getBox(consequent));
/*  344: 342 */           connectedEntities.add(consequent);
/*  345: 343 */           for (Box antecedentBox : antecedentBoxes)
/*  346:     */           {
/*  347: 344 */             connection = connect(antecedentBox, consequentBox);
/*  348: 345 */             if (entity.isA("prediction"))
/*  349:     */             {
/*  350: 346 */               consequentBox.setColor(this.predictionBox);
/*  351: 347 */               connection.setColor(this.blackBox);
/*  352:     */             }
/*  353: 350 */             else if (entity.isA("proximity"))
/*  354:     */             {
/*  355: 351 */               consequentBox.setColor(this.proximityBox);
/*  356: 352 */               connection.setColor(this.proximityBox);
/*  357: 353 */               connection.setDotted(true);
/*  358:     */             }
/*  359: 355 */             else if (entity.isA("explanation"))
/*  360:     */             {
/*  361: 356 */               consequentBox.setColor(this.explanationBox);
/*  362: 357 */               connection.setColor(this.explanationBox);
/*  363: 358 */               connection.setDotted(true);
/*  364:     */             }
/*  365: 360 */             else if (entity.isA("abduction"))
/*  366:     */             {
/*  367: 362 */               antecedentBox.setColor(this.abductionBox);
/*  368: 363 */               connection.setColor(this.abductionBox);
/*  369: 364 */               connection.setDotted(true);
/*  370:     */             }
/*  371: 366 */             else if (entity.isA("enabler"))
/*  372:     */             {
/*  373: 368 */               antecedentBox.setColor(this.enablerBox);
/*  374: 369 */               connection.setColor(this.enablerBox);
/*  375: 370 */               connection.setDotted(true);
/*  376:     */             }
/*  377: 372 */             else if (entity.isA("means"))
/*  378:     */             {
/*  379: 373 */               antecedentBox.setColor(this.meansBox);
/*  380: 374 */               connection.setColor(this.meansBox);
/*  381: 375 */               connection.setDotted(true);
/*  382:     */             }
/*  383: 377 */             else if (entity.isA("entail"))
/*  384:     */             {
/*  385: 378 */               consequentBox.setColor(this.leadsToBox);
/*  386: 379 */               connection.setColor(this.leadsToBox);
/*  387: 380 */               connection.setDotted(true);
/*  388:     */             }
/*  389:     */           }
/*  390:     */         }
/*  391:     */       }
/*  392:     */     }
/*  393:     */     catch (Exception e)
/*  394:     */     {
/*  395: 388 */       Mark.err(new Object[] {"Blew out in ElaborationView.processEntity" });
/*  396: 389 */       e.printStackTrace();
/*  397:     */     }
/*  398:     */   }
/*  399:     */   
/*  400:     */   public boolean isCausalConnection(Entity entity)
/*  401:     */   {
/*  402: 394 */     return (entity.isA("cause")) && (entity.getSubject().sequenceP("conjuction"));
/*  403:     */   }
/*  404:     */   
/*  405:     */   private String getSentence(String hash)
/*  406:     */   {
/*  407: 398 */     return (String)this.sentenceCache.get(hash);
/*  408:     */   }
/*  409:     */   
/*  410:     */   public Box getBox(Entity entity)
/*  411:     */   {
/*  412: 402 */     String hash = hash(entity);
/*  413: 403 */     Box box = (Box)this.boxSet.get(hash);
/*  414: 404 */     if (box == null)
/*  415:     */     {
/*  416:     */       String sentence;
/*  417:     */       try
/*  418:     */       {
/*  419: 407 */         String sentence = getSentence(hash);
/*  420: 408 */         if (sentence == null)
/*  421:     */         {
/*  422: 409 */           sentence = Generator.getGenerator().generate(entity);
/*  423:     */           
/*  424: 411 */           this.sentenceCache.put(hash, sentence);
/*  425:     */         }
/*  426:     */       }
/*  427:     */       catch (Exception e)
/*  428:     */       {
/*  429: 415 */         Mark.err(new Object[] {"Could not generate from", entity });
/*  430: 416 */         sentence = entity.toString();
/*  431:     */       }
/*  432: 418 */       int x = maxX() + 1;
/*  433: 420 */       if (this.boxSet.size() == 0) {
/*  434: 421 */         x = 0;
/*  435:     */       }
/*  436: 423 */       box = new Box(sentence, x, 0);
/*  437: 424 */       if (entity.isA("action")) {
/*  438: 425 */         box.setEvent(true);
/*  439:     */       }
/*  440: 427 */       this.boxSet.put(hash(entity), box);
/*  441:     */     }
/*  442: 429 */     return box;
/*  443:     */   }
/*  444:     */   
/*  445:     */   private int maxX()
/*  446:     */   {
/*  447: 433 */     int result = 0;
/*  448: 434 */     for (Box box : getConnectedBoxList()) {
/*  449: 435 */       result = Math.max(result, box.x);
/*  450:     */     }
/*  451: 437 */     return result;
/*  452:     */   }
/*  453:     */   
/*  454:     */   private int maxY()
/*  455:     */   {
/*  456: 441 */     int result = 0;
/*  457: 442 */     for (Box box : getConnectedBoxList()) {
/*  458: 443 */       result = Math.max(result, box.y);
/*  459:     */     }
/*  460: 445 */     return result;
/*  461:     */   }
/*  462:     */   
/*  463:     */   private void compress()
/*  464:     */   {
/*  465:     */     Box box;
/*  466: 450 */     for (int i = 1; i < getConnectedBoxList().size(); i++)
/*  467:     */     {
/*  468: 451 */       box = (Box)getConnectedBoxList().get(i);
/*  469: 452 */       if (!box.isEvent())
/*  470:     */       {
/*  471: 455 */         int initialX = box.getX();
/*  472: 456 */         int newX = 0;
/*  473: 458 */         if (box.getInputs().size() > 0)
/*  474:     */         {
/*  475: 459 */           for (Box input : box.getInputs()) {
/*  476: 461 */             newX = Math.max(newX, input.getX());
/*  477:     */           }
/*  478: 463 */           newX++;
/*  479:     */         }
/*  480: 465 */         dropToColumn(newX, box);
/*  481: 466 */         pullToLeft(initialX + 1);
/*  482:     */       }
/*  483:     */     }
/*  484: 470 */     for (Box box : getConnectedBoxList())
/*  485:     */     {
/*  486: 471 */       ArrayList<Box> inputs = box.getInputs();
/*  487: 473 */       if (inputs.size() > 1) {
/*  488: 474 */         for (int i = 1; i < inputs.size(); i++)
/*  489:     */         {
/*  490: 475 */           int initialX = ((Box)inputs.get(i)).getX();
/*  491:     */           
/*  492: 477 */           drop((Box)inputs.get(i));
/*  493: 478 */           pullToLeft(initialX + 1);
/*  494:     */         }
/*  495:     */       }
/*  496:     */     }
/*  497: 483 */     for (int c = 0; c < maxX(); c++) {
/*  498: 484 */       dropDeadEndsToBottom(c);
/*  499:     */     }
/*  500: 505 */     realign();
/*  501:     */   }
/*  502:     */   
/*  503:     */   private void dropDeadEndsToBottom(int c)
/*  504:     */   {
/*  505: 510 */     ArrayList<Box> result = new ArrayList();
/*  506: 511 */     ArrayList<Box> column = new ArrayList();
/*  507: 512 */     for (Box b : getConnectedBoxList()) {
/*  508: 513 */       if (b.getX() == c) {
/*  509: 514 */         column.add(b);
/*  510:     */       }
/*  511:     */     }
/*  512: 517 */     for (Box b : column) {
/*  513: 518 */       if (b.getOutputs().size() > 0) {
/*  514: 519 */         result.add(b);
/*  515:     */       }
/*  516:     */     }
/*  517: 522 */     for (Box b : column) {
/*  518: 523 */       if (b.getOutputs().size() == 0) {
/*  519: 524 */         result.add(b);
/*  520:     */       }
/*  521:     */     }
/*  522: 527 */     for (int i = 0; i < result.size(); i++) {
/*  523: 528 */       ((Box)result.get(i)).setY(i);
/*  524:     */     }
/*  525:     */   }
/*  526:     */   
/*  527:     */   private void realign()
/*  528:     */   {
/*  529: 533 */     Point lowerRight = getMaxPoint();
/*  530: 534 */     int maxX = lowerRight.x;
/*  531: 535 */     int maxY = lowerRight.y;
/*  532:     */     
/*  533: 537 */     this.rowCount = calculateRows(maxX, maxY, this.idealRatio);
/*  534:     */     
/*  535:     */ 
/*  536: 540 */     breakApart(this.rowCount);
/*  537:     */   }
/*  538:     */   
/*  539:     */   private int calculateRows(int maxX, int maxY, float idealRatio)
/*  540:     */   {
/*  541: 545 */     int bestN = 0;
/*  542: 546 */     double previousRatio = 0.0D;
/*  543: 547 */     for (int n = 1;; n++)
/*  544:     */     {
/*  545: 548 */       double ratio = maxX / (n * n * maxY);
/*  546: 549 */       if ((n == 1) || (Math.abs(ratio - idealRatio) < Math.abs(previousRatio - idealRatio)))
/*  547:     */       {
/*  548: 550 */         bestN = n;
/*  549: 551 */         previousRatio = ratio;
/*  550:     */       }
/*  551:     */       else
/*  552:     */       {
/*  553: 554 */         return bestN;
/*  554:     */       }
/*  555:     */     }
/*  556:     */   }
/*  557:     */   
/*  558:     */   private void breakApart(int rows)
/*  559:     */   {
/*  560: 560 */     int originalWidth = maxX() + 1;
/*  561: 561 */     this.rowHeight = (maxY() + 1);
/*  562:     */     
/*  563: 563 */     this.rowWidth = ((int)Math.ceil(originalWidth / rows));
/*  564: 567 */     for (Box b : getConnectedBoxList())
/*  565:     */     {
/*  566: 568 */       int boxX = b.getX();
/*  567: 569 */       int boxY = b.getY();
/*  568: 570 */       int newX = boxX % this.rowWidth;
/*  569: 571 */       int newY = boxY + boxX / this.rowWidth * (this.rowHeight + 1);
/*  570:     */       
/*  571: 573 */       setBox(b, newX, newY, "breakApart");
/*  572:     */     }
/*  573:     */   }
/*  574:     */   
/*  575:     */   private void pullToLeft(int i)
/*  576:     */   {
/*  577: 582 */     if (i <= 0) {
/*  578: 583 */       return;
/*  579:     */     }
/*  580: 586 */     for (Box box : getConnectedBoxList()) {
/*  581: 587 */       if (box.getX() == i - 1) {
/*  582: 590 */         return;
/*  583:     */       }
/*  584:     */     }
/*  585: 593 */     for (Box box : getConnectedBoxList()) {
/*  586: 594 */       if (box.getX() >= i) {
/*  587: 595 */         setBox(box, box.getX() - 1, box.getY(), "pullToLeft");
/*  588:     */       }
/*  589:     */     }
/*  590:     */   }
/*  591:     */   
/*  592:     */   private void drop(Box box)
/*  593:     */   {
/*  594: 604 */     int c = box.getX() - 1;
/*  595: 605 */     dropToColumn(c, box);
/*  596:     */   }
/*  597:     */   
/*  598:     */   private void dropToColumn(int c, Box box)
/*  599:     */   {
/*  600: 613 */     if (c < 0) {
/*  601: 614 */       return;
/*  602:     */     }
/*  603: 617 */     int r = 0;
/*  604: 618 */     for (Box x : getConnectedBoxList()) {
/*  605: 619 */       if ((x.getX() == c) && (x != box)) {
/*  606: 623 */         r++;
/*  607:     */       }
/*  608:     */     }
/*  609: 627 */     setBox(box, c, r, "dropToColumn");
/*  610:     */   }
/*  611:     */   
/*  612:     */   private void setBox(Box box, int c, int r, String s)
/*  613:     */   {
/*  614: 632 */     for (Box b : getConnectedBoxList()) {
/*  615: 633 */       if (b != box) {
/*  616: 636 */         if ((b.getX() == c) && (b.getY() == r))
/*  617:     */         {
/*  618: 644 */           setBox(box, c, r + 1, s);
/*  619: 645 */           return;
/*  620:     */         }
/*  621:     */       }
/*  622:     */     }
/*  623: 648 */     box.setX(c);
/*  624: 649 */     box.setY(r);
/*  625:     */   }
/*  626:     */   
/*  627:     */   private Connection connect(Box s, Box d)
/*  628:     */   {
/*  629: 653 */     Connection connection = new Connection(s, d);
/*  630: 654 */     s.addOutput(d);
/*  631: 655 */     d.addInput(s);
/*  632:     */     
/*  633: 657 */     getConnectionList().add(connection);
/*  634: 658 */     s.addOutputConnection(connection);
/*  635: 659 */     d.addInputConnection(connection);
/*  636: 660 */     return connection;
/*  637:     */   }
/*  638:     */   
/*  639:     */   private Box addBox(Box box)
/*  640:     */   {
/*  641: 664 */     if (!getConnectedBoxList().contains(box))
/*  642:     */     {
/*  643: 665 */       getConnectedBoxList().add(box);
/*  644: 666 */       validate();
/*  645:     */     }
/*  646: 668 */     return box;
/*  647:     */   }
/*  648:     */   
/*  649:     */   public void paint(Graphics g)
/*  650:     */   {
/*  651: 674 */     super.paint(g);
/*  652:     */     
/*  653: 676 */     Graphics2D graphics = (Graphics2D)g;
/*  654:     */     
/*  655:     */ 
/*  656: 679 */     int width = getWidth();
/*  657: 680 */     int height = getHeight();
/*  658:     */     
/*  659:     */ 
/*  660:     */ 
/*  661:     */ 
/*  662: 685 */     AffineTransform transform = new AffineTransform();
/*  663: 686 */     Point lowerRight = getMaxPoint();
/*  664:     */     
/*  665: 688 */     int maxWidth = lowerRight.x + this.boxWidth;
/*  666: 689 */     int maxHeight = lowerRight.y;
/*  667:     */     
/*  668: 691 */     double scale = 1.0D;
/*  669:     */     
/*  670: 693 */     int hTranslation = this.leftAndRightOffset;
/*  671: 694 */     int vTranslation = this.topAndBottomOffset;
/*  672: 696 */     if (width * maxHeight < height * maxWidth)
/*  673:     */     {
/*  674: 697 */       scale = width / maxWidth;
/*  675:     */       
/*  676: 699 */       vTranslation = (int)((height - maxHeight * scale) / 2.0D);
/*  677: 700 */       hTranslation = 0;
/*  678:     */     }
/*  679:     */     else
/*  680:     */     {
/*  681: 703 */       scale = height / maxHeight;
/*  682: 704 */       hTranslation = (int)((width - maxWidth * scale) / 2.0D);
/*  683:     */       
/*  684: 706 */       vTranslation = 0;
/*  685:     */     }
/*  686: 711 */     transform.translate(this.offsetX, this.offsetY);
/*  687:     */     
/*  688:     */ 
/*  689:     */ 
/*  690: 715 */     transform.translate(-width / 2, -height / 2);
/*  691:     */     
/*  692: 717 */     transform.scale(this.magnification, this.magnification);
/*  693:     */     
/*  694: 719 */     transform.translate(width / this.magnification / 2.0D, height / this.magnification / 2.0D);
/*  695:     */     
/*  696:     */ 
/*  697:     */ 
/*  698: 723 */     transform.translate(hTranslation, vTranslation);
/*  699:     */     
/*  700: 725 */     transform.scale(scale, scale);
/*  701:     */     try
/*  702:     */     {
/*  703: 731 */       this.inverse = transform.createInverse();
/*  704:     */     }
/*  705:     */     catch (NoninvertibleTransformException e)
/*  706:     */     {
/*  707: 734 */       e.printStackTrace();
/*  708:     */     }
/*  709: 737 */     int wBox = (int)(transform.getScaleX() * this.boxWidth);
/*  710: 738 */     int hBox = (int)(transform.getScaleY() * this.boxHeight);
/*  711: 739 */     drawBoxes(graphics, transform, wBox, hBox);
/*  712:     */     
/*  713: 741 */     drawConnections(graphics, transform);
/*  714: 747 */     if (this.mouseDown) {
/*  715: 748 */       drawCross(graphics, width, height);
/*  716:     */     }
/*  717:     */   }
/*  718:     */   
/*  719:     */   public void drawConnections(Graphics2D graphics, AffineTransform transform)
/*  720:     */   {
/*  721: 754 */     for (int row = 0; row < this.rowCount * (this.rowHeight + 1); row++)
/*  722:     */     {
/*  723: 755 */       ArrayList<Connection> columnSpanners = new ArrayList();
/*  724: 756 */       ArrayList<Connection> rowSpanners = new ArrayList();
/*  725: 757 */       ArrayList<Connection> adjacents = new ArrayList();
/*  726: 758 */       for (Connection connection : getConnectionList())
/*  727:     */       {
/*  728: 759 */         Box source = connection.source;
/*  729: 760 */         Box destination = connection.destination;
/*  730: 761 */         if (source.getY() == row) {
/*  731: 764 */           if (destination.getX() != source.getX() + 1)
/*  732:     */           {
/*  733: 765 */             if (source.getY() / this.rowHeight != destination.getY() / this.rowHeight) {
/*  734: 766 */               rowSpanners.add(connection);
/*  735:     */             } else {
/*  736: 769 */               columnSpanners.add(connection);
/*  737:     */             }
/*  738:     */           }
/*  739:     */           else {
/*  740: 773 */             adjacents.add(connection);
/*  741:     */           }
/*  742:     */         }
/*  743:     */       }
/*  744: 777 */       for (Connection connection : adjacents) {
/*  745: 778 */         drawConnection(graphics, transform, connection);
/*  746:     */       }
/*  747: 781 */       int columnSpannerCount = columnSpanners.size();
/*  748: 782 */       int rowSpannerCount = rowSpanners.size();
/*  749: 783 */       int endSpacing = this.boxVPadding / 5;
/*  750: 784 */       int topOfBoxSpacing = this.boxVPadding / (rowSpannerCount + columnSpannerCount + 1);
/*  751: 785 */       for (int i = 0; i < columnSpanners.size(); i++)
/*  752:     */       {
/*  753: 786 */         int offset = topOfBoxSpacing + i * topOfBoxSpacing;
/*  754: 787 */         drawColumnConnection(graphics, transform, (Connection)columnSpanners.get(i), offset);
/*  755:     */       }
/*  756: 790 */       int spannersProcessed = 0;
/*  757: 791 */       for (int i = 0; i < rowSpanners.size(); i++)
/*  758:     */       {
/*  759: 792 */         int offset = topOfBoxSpacing + (i + columnSpannerCount) * topOfBoxSpacing;
/*  760: 793 */         spannersProcessed++;
/*  761: 794 */         drawRowConnection(graphics, transform, (Connection)rowSpanners.get(i), offset, spannersProcessed * endSpacing);
/*  762:     */       }
/*  763:     */     }
/*  764:     */   }
/*  765:     */   
/*  766:     */   public void drawBoxes(Graphics2D graphics, AffineTransform transform, int wBox, int hBox)
/*  767:     */   {
/*  768: 801 */     for (int i = 0; i < getConnectedBoxList().size(); i++) {
/*  769: 802 */       drawBox(graphics, transform, wBox, hBox, (Box)getConnectedBoxList().get(i));
/*  770:     */     }
/*  771:     */   }
/*  772:     */   
/*  773:     */   private Point getMaxPoint()
/*  774:     */   {
/*  775: 807 */     int maxX = maxX();
/*  776: 808 */     int maxY = maxY();
/*  777: 809 */     int x = 2 * this.leftAndRightOffset + (maxX + 1) * this.boxWidth + maxX * this.boxHPadding;
/*  778: 810 */     int y = 2 * this.topAndBottomOffset + (maxY + 1) * (this.boxHeight + this.boxVPadding);
/*  779: 811 */     return new Point(x, y);
/*  780:     */   }
/*  781:     */   
/*  782:     */   private Point getBoxOrigin(Box box)
/*  783:     */   {
/*  784: 815 */     int x = this.leftAndRightOffset + box.getX() * (this.boxWidth + this.boxHPadding);
/*  785: 816 */     int y = this.topAndBottomOffset + box.getY() * (this.boxHeight + this.boxVPadding);
/*  786: 817 */     return new Point(x, y);
/*  787:     */   }
/*  788:     */   
/*  789:     */   public void drawConnection(Graphics2D graphics, AffineTransform transform, Connection connection)
/*  790:     */   {
/*  791: 824 */     Box s = connection.source;
/*  792: 825 */     Box d = connection.destination;
/*  793: 826 */     if (connection.getTemporaryColor() != null) {
/*  794: 827 */       graphics.setColor(connection.getTemporaryColor());
/*  795:     */     } else {
/*  796: 830 */       graphics.setColor(connection.getColor());
/*  797:     */     }
/*  798: 833 */     graphics.setStroke(connection.getStroke());
/*  799:     */     
/*  800:     */ 
/*  801: 836 */     int dX = this.leftAndRightOffset + d.getX() * (this.boxWidth + this.boxHPadding);
/*  802: 837 */     int dY = this.topAndBottomOffset + d.getY() * (this.boxHeight + this.boxVPadding) + this.boxHeight / 2;
/*  803:     */     
/*  804: 839 */     int sX = this.leftAndRightOffset + s.getX() * (this.boxWidth + this.boxHPadding) + this.boxWidth;
/*  805: 840 */     int sY = this.topAndBottomOffset + s.getY() * (this.boxHeight + this.boxVPadding) + this.boxHeight / 2;
/*  806: 841 */     drawLine(graphics, transform, sX, sY, dX, dY);
/*  807:     */     
/*  808: 843 */     graphics.setColor(this.blackBox);
/*  809:     */   }
/*  810:     */   
/*  811:     */   public void drawColumnConnection(Graphics2D graphics, AffineTransform transform, Connection connection, int delta)
/*  812:     */   {
/*  813: 847 */     Box s = connection.source;
/*  814: 848 */     Box d = connection.destination;
/*  815: 849 */     if (connection.getTemporaryColor() != null) {
/*  816: 850 */       graphics.setColor(connection.getTemporaryColor());
/*  817:     */     } else {
/*  818: 853 */       graphics.setColor(connection.getColor());
/*  819:     */     }
/*  820: 856 */     graphics.setStroke(connection.getStroke());
/*  821:     */     
/*  822: 858 */     int sX = this.leftAndRightOffset + s.getX() * (this.boxWidth + this.boxHPadding) + this.boxWidth - delta;
/*  823: 859 */     int sY = this.topAndBottomOffset + s.getY() * (this.boxHeight + this.boxVPadding);
/*  824:     */     
/*  825: 861 */     int dX = this.leftAndRightOffset + d.getX() * (this.boxWidth + this.boxHPadding);
/*  826: 862 */     int dY = this.topAndBottomOffset + d.getY() * (this.boxHeight + this.boxVPadding) + this.boxHeight / 2;
/*  827:     */     
/*  828: 864 */     int p1X = sX;
/*  829: 865 */     int p1Y = this.topAndBottomOffset + s.getY() * (this.boxHeight + this.boxVPadding) - delta;
/*  830: 866 */     drawLine(graphics, transform, sX, sY, p1X, p1Y);
/*  831:     */     
/*  832: 868 */     int p2Y = p1Y;
/*  833: 869 */     int p2X = this.leftAndRightOffset + (d.getX() - 1) * (this.boxWidth + this.boxHPadding) + this.boxWidth + this.boxHPadding / 4;
/*  834: 870 */     drawLine(graphics, transform, p1X, p1Y, p2X, p2Y);
/*  835:     */     
/*  836: 872 */     int p3X = p2X;
/*  837: 873 */     int p3Y = p2Y;
/*  838: 874 */     drawLine(graphics, transform, p3X, p3Y, dX, dY);
/*  839:     */     
/*  840: 876 */     graphics.setColor(this.blackBox);
/*  841:     */   }
/*  842:     */   
/*  843:     */   public void drawRowConnection(Graphics2D graphics, AffineTransform transform, Connection connection, int delta, int deltaX)
/*  844:     */   {
/*  845: 880 */     Box s = connection.source;
/*  846: 881 */     Box d = connection.destination;
/*  847: 882 */     if (connection.getTemporaryColor() != null) {
/*  848: 883 */       graphics.setColor(connection.getTemporaryColor());
/*  849:     */     } else {
/*  850: 886 */       graphics.setColor(connection.getColor());
/*  851:     */     }
/*  852: 889 */     graphics.setStroke(connection.getStroke());
/*  853:     */     
/*  854: 891 */     int sX = this.leftAndRightOffset + s.getX() * (this.boxWidth + this.boxHPadding) + this.boxWidth - delta;
/*  855: 892 */     int sY = this.topAndBottomOffset + s.getY() * (this.boxHeight + this.boxVPadding);
/*  856:     */     
/*  857: 894 */     int dX = this.leftAndRightOffset + d.getX() * (this.boxWidth + this.boxHPadding);
/*  858: 895 */     int dY = this.topAndBottomOffset + d.getY() * (this.boxHeight + this.boxVPadding) + this.boxHeight / 2;
/*  859:     */     
/*  860:     */ 
/*  861: 898 */     int p1X = sX;
/*  862: 899 */     int p1Y = this.topAndBottomOffset + s.getY() * (this.boxHeight + this.boxVPadding) - delta;
/*  863:     */     
/*  864: 901 */     drawLine(graphics, transform, sX, sY, p1X, p1Y);
/*  865:     */     
/*  866:     */ 
/*  867:     */ 
/*  868: 905 */     int p2X = this.leftAndRightOffset + s.getX() * (this.boxWidth + this.boxHPadding) + this.boxWidth - this.boxHPadding + delta;
/*  869: 906 */     int p2Y = p1Y;
/*  870:     */     
/*  871:     */ 
/*  872:     */ 
/*  873: 910 */     p2X = this.leftAndRightOffset + this.rowWidth * (this.boxWidth + this.boxHPadding) + deltaX;
/*  874: 911 */     p2Y = p1Y;
/*  875:     */     
/*  876: 913 */     drawLine(graphics, transform, p1X, p1Y, p2X, p2Y);
/*  877:     */     
/*  878:     */ 
/*  879:     */ 
/*  880:     */ 
/*  881:     */ 
/*  882: 919 */     int p3X = p2X;
/*  883: 920 */     int p3Y = this.topAndBottomOffset + d.y / (this.rowHeight + 1) * (this.rowHeight + 1) * (this.boxHeight + this.boxVPadding) - this.boxHeight - this.boxVPadding / 2 + deltaX;
/*  884:     */     
/*  885: 922 */     drawLine(graphics, transform, p2X, p2Y, p3X, p3Y);
/*  886:     */     
/*  887:     */ 
/*  888:     */ 
/*  889: 926 */     int p4X = dX - this.boxHPadding;
/*  890: 927 */     int p4Y = this.topAndBottomOffset + d.y / this.rowHeight * this.rowHeight * (this.boxHeight + this.boxVPadding) + this.boxHeight - this.boxVPadding / 2;
/*  891:     */     
/*  892: 929 */     drawLine(graphics, transform, p3X, p3Y, p4X, p4Y);
/*  893:     */     
/*  894:     */ 
/*  895:     */ 
/*  896: 933 */     drawLine(graphics, transform, p4X, p4Y, dX, dY);
/*  897:     */     
/*  898: 935 */     graphics.setColor(this.blackBox);
/*  899:     */   }
/*  900:     */   
/*  901:     */   private void drawLine(Graphics2D graphics, AffineTransform transform, int sX, int sY, int dX, int dY)
/*  902:     */   {
/*  903: 939 */     Point s = new Point(sX, sY);
/*  904: 940 */     Point d = new Point(dX, dY);
/*  905: 941 */     transform.transform(s, s);
/*  906: 942 */     transform.transform(d, d);
/*  907: 943 */     graphics.drawLine(s.x, s.y, d.x, d.y);
/*  908:     */   }
/*  909:     */   
/*  910:     */   private void drawBox(Graphics graphics, AffineTransform transform, int width, int height, Box box)
/*  911:     */   {
/*  912: 950 */     int bX = box.getX();
/*  913: 951 */     int bY = box.getY();
/*  914:     */     
/*  915: 953 */     int x = this.leftAndRightOffset + bX * (this.boxWidth + this.boxHPadding);
/*  916: 954 */     int y = this.topAndBottomOffset + bY * (this.boxHeight + this.boxVPadding);
/*  917:     */     
/*  918:     */ 
/*  919: 957 */     Point point = new Point(x, y);
/*  920: 958 */     transform.transform(point, point);
/*  921: 959 */     x = point.x;
/*  922: 960 */     y = point.y;
/*  923:     */     
/*  924:     */ 
/*  925:     */ 
/*  926: 964 */     point = new Point(this.boxWidth, this.boxHeight);
/*  927: 966 */     if (box.getTemporaryColor() != null) {
/*  928: 967 */       graphics.setColor(box.getTemporaryColor());
/*  929:     */     } else {
/*  930: 970 */       graphics.setColor(box.getColor());
/*  931:     */     }
/*  932: 972 */     graphics.fillRect(x, y, width, height);
/*  933: 973 */     graphics.setColor(box.getOutlineColor());
/*  934: 974 */     graphics.drawRect(x, y, width, height);
/*  935:     */     
/*  936: 976 */     Rectangle rectangle = new Rectangle(x, y, width, height);
/*  937: 977 */     Font font = graphics.getFont();
/*  938: 978 */     graphics.setFont(new Font(font.getName(), 1, font.getSize() + 5));
/*  939: 979 */     drawLabel(graphics, box.text, rectangle);
/*  940: 980 */     graphics.setFont(font);
/*  941:     */     
/*  942: 982 */     graphics.setColor(this.blackBox);
/*  943:     */   }
/*  944:     */   
/*  945:     */   private class Connection
/*  946:     */   {
/*  947:     */     public ElaborationView.Box source;
/*  948:     */     public ElaborationView.Box destination;
/*  949:     */     private Color color;
/*  950:     */     private Color temporaryColor;
/*  951: 994 */     private boolean bold = false;
/*  952: 996 */     private boolean dotted = false;
/*  953:     */     
/*  954:     */     public Stroke getStroke()
/*  955:     */     {
/*  956: 999 */       if (this.bold)
/*  957:     */       {
/*  958:1000 */         if (this.dotted) {
/*  959:1001 */           return ElaborationView.this.boldDottedStroke;
/*  960:     */         }
/*  961:1004 */         return ElaborationView.this.boldStroke;
/*  962:     */       }
/*  963:1008 */       if (this.dotted) {
/*  964:1009 */         return ElaborationView.this.dottedStroke;
/*  965:     */       }
/*  966:1012 */       return ElaborationView.this.basicStroke;
/*  967:     */     }
/*  968:     */     
/*  969:     */     public boolean isBold()
/*  970:     */     {
/*  971:1018 */       return this.bold;
/*  972:     */     }
/*  973:     */     
/*  974:     */     public void setBold(boolean bold)
/*  975:     */     {
/*  976:1022 */       this.bold = bold;
/*  977:     */     }
/*  978:     */     
/*  979:     */     public boolean isDotted()
/*  980:     */     {
/*  981:1026 */       return this.dotted;
/*  982:     */     }
/*  983:     */     
/*  984:     */     public void setDotted(boolean dotted)
/*  985:     */     {
/*  986:1030 */       this.dotted = dotted;
/*  987:     */     }
/*  988:     */     
/*  989:     */     public Color getColor()
/*  990:     */     {
/*  991:1034 */       if (this.color == null) {
/*  992:1035 */         this.color = ElaborationView.this.blackBox;
/*  993:     */       }
/*  994:1037 */       return this.color;
/*  995:     */     }
/*  996:     */     
/*  997:     */     public Color getTemporaryColor()
/*  998:     */     {
/*  999:1041 */       return this.temporaryColor;
/* 1000:     */     }
/* 1001:     */     
/* 1002:     */     public void setTemporaryColor(Color temporaryColor)
/* 1003:     */     {
/* 1004:1045 */       this.temporaryColor = temporaryColor;
/* 1005:     */     }
/* 1006:     */     
/* 1007:     */     public void setColor(Color color)
/* 1008:     */     {
/* 1009:1049 */       this.color = color;
/* 1010:     */     }
/* 1011:     */     
/* 1012:     */     public Connection(ElaborationView.Box source, ElaborationView.Box destination)
/* 1013:     */     {
/* 1014:1054 */       this.source = source;
/* 1015:1055 */       this.destination = destination;
/* 1016:     */     }
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   private class Box
/* 1020:     */     implements Comparable
/* 1021:     */   {
/* 1022:     */     private String text;
/* 1023:     */     private int x;
/* 1024:     */     private int y;
/* 1025:     */     private ArrayList<Box> inputs;
/* 1026:     */     private ArrayList<Box> outputs;
/* 1027:     */     private Color color;
/* 1028:     */     private Color outlineColor;
/* 1029:     */     private Color temporaryColor;
/* 1030:     */     private boolean event;
/* 1031:1076 */     private ArrayList<ElaborationView.Connection> inputConnections = new ArrayList();
/* 1032:1078 */     private ArrayList<ElaborationView.Connection> outputConnections = new ArrayList();
/* 1033:     */     
/* 1034:     */     public Box(String text, int x, int y)
/* 1035:     */     {
/* 1036:1082 */       this.text = text;
/* 1037:1083 */       this.x = x;
/* 1038:1084 */       this.y = y;
/* 1039:     */     }
/* 1040:     */     
/* 1041:     */     public void addInputConnection(ElaborationView.Connection connection)
/* 1042:     */     {
/* 1043:1088 */       this.inputConnections.add(connection);
/* 1044:     */     }
/* 1045:     */     
/* 1046:     */     public void addOutputConnection(ElaborationView.Connection connection)
/* 1047:     */     {
/* 1048:1093 */       this.outputConnections.add(connection);
/* 1049:     */     }
/* 1050:     */     
/* 1051:     */     public ArrayList<ElaborationView.Connection> getInputConnections()
/* 1052:     */     {
/* 1053:1097 */       return this.inputConnections;
/* 1054:     */     }
/* 1055:     */     
/* 1056:     */     public ArrayList<ElaborationView.Connection> getOutputConnections()
/* 1057:     */     {
/* 1058:1101 */       return this.outputConnections;
/* 1059:     */     }
/* 1060:     */     
/* 1061:     */     public void setEvent(boolean b)
/* 1062:     */     {
/* 1063:1105 */       this.event = b;
/* 1064:     */     }
/* 1065:     */     
/* 1066:     */     public boolean isEvent()
/* 1067:     */     {
/* 1068:1109 */       return this.event;
/* 1069:     */     }
/* 1070:     */     
/* 1071:     */     public Color getColor()
/* 1072:     */     {
/* 1073:1120 */       if (this.color == null) {
/* 1074:1121 */         this.color = ElaborationView.this.assertionBox;
/* 1075:     */       }
/* 1076:1123 */       return this.color;
/* 1077:     */     }
/* 1078:     */     
/* 1079:     */     public void setColor(Color color)
/* 1080:     */     {
/* 1081:1127 */       this.color = color;
/* 1082:     */     }
/* 1083:     */     
/* 1084:     */     public Color getOutlineColor()
/* 1085:     */     {
/* 1086:1131 */       if (this.outlineColor == null) {
/* 1087:1132 */         this.outlineColor = ElaborationView.this.blackBox;
/* 1088:     */       }
/* 1089:1134 */       return this.outlineColor;
/* 1090:     */     }
/* 1091:     */     
/* 1092:     */     public void setOutlineColor(Color outlineColor)
/* 1093:     */     {
/* 1094:1138 */       this.outlineColor = outlineColor;
/* 1095:     */     }
/* 1096:     */     
/* 1097:     */     public Color getTemporaryColor()
/* 1098:     */     {
/* 1099:1142 */       return this.temporaryColor;
/* 1100:     */     }
/* 1101:     */     
/* 1102:     */     public void setTemporaryColor(Color temporaryColor)
/* 1103:     */     {
/* 1104:1146 */       this.temporaryColor = temporaryColor;
/* 1105:     */     }
/* 1106:     */     
/* 1107:     */     public int getX()
/* 1108:     */     {
/* 1109:1150 */       return this.x;
/* 1110:     */     }
/* 1111:     */     
/* 1112:     */     public int getY()
/* 1113:     */     {
/* 1114:1154 */       return this.y;
/* 1115:     */     }
/* 1116:     */     
/* 1117:     */     public void setX(int x)
/* 1118:     */     {
/* 1119:1158 */       this.x = x;
/* 1120:     */     }
/* 1121:     */     
/* 1122:     */     public void setY(int y)
/* 1123:     */     {
/* 1124:1162 */       this.y = y;
/* 1125:     */     }
/* 1126:     */     
/* 1127:     */     public ArrayList<Box> getInputs()
/* 1128:     */     {
/* 1129:1166 */       if (this.inputs == null) {
/* 1130:1167 */         this.inputs = new ArrayList();
/* 1131:     */       }
/* 1132:1169 */       return this.inputs;
/* 1133:     */     }
/* 1134:     */     
/* 1135:     */     public void addInput(Box box)
/* 1136:     */     {
/* 1137:1173 */       if (!getInputs().contains(box)) {
/* 1138:1174 */         getInputs().add(box);
/* 1139:     */       }
/* 1140:     */     }
/* 1141:     */     
/* 1142:     */     public ArrayList<Box> getOutputs()
/* 1143:     */     {
/* 1144:1179 */       if (this.outputs == null) {
/* 1145:1180 */         this.outputs = new ArrayList();
/* 1146:     */       }
/* 1147:1182 */       return this.outputs;
/* 1148:     */     }
/* 1149:     */     
/* 1150:     */     public void addOutput(Box box)
/* 1151:     */     {
/* 1152:1186 */       if (!getOutputs().contains(box)) {
/* 1153:1187 */         getOutputs().add(box);
/* 1154:     */       }
/* 1155:     */     }
/* 1156:     */     
/* 1157:     */     public String toString()
/* 1158:     */     {
/* 1159:1192 */       return "<" + this.text + ">";
/* 1160:     */     }
/* 1161:     */     
/* 1162:     */     public int compareTo(Object o)
/* 1163:     */     {
/* 1164:1197 */       Box that = (Box)o;
/* 1165:1198 */       if (getX() < that.getX()) {
/* 1166:1199 */         return -1;
/* 1167:     */       }
/* 1168:1201 */       if (getX() > that.getX()) {
/* 1169:1202 */         return 1;
/* 1170:     */       }
/* 1171:1204 */       if (getY() < that.getY()) {
/* 1172:1205 */         return -1;
/* 1173:     */       }
/* 1174:1207 */       if (getY() > that.getY()) {
/* 1175:1208 */         return 1;
/* 1176:     */       }
/* 1177:1210 */       return 0;
/* 1178:     */     }
/* 1179:     */   }
/* 1180:     */   
/* 1181:     */   private void highlight(Box box)
/* 1182:     */   {
/* 1183:1216 */     extinguish();
/* 1184:1217 */     for (Connection c : box.getInputConnections())
/* 1185:     */     {
/* 1186:1218 */       c.setTemporaryColor(Color.red);
/* 1187:1219 */       c.setBold(true);
/* 1188:     */     }
/* 1189:1222 */     for (Connection c : box.getOutputConnections())
/* 1190:     */     {
/* 1191:1223 */       c.setTemporaryColor(Color.green);
/* 1192:1224 */       c.setBold(true);
/* 1193:     */     }
/* 1194:     */   }
/* 1195:     */   
/* 1196:     */   private void extinguish()
/* 1197:     */   {
/* 1198:1229 */     for (Connection c : getConnectionList())
/* 1199:     */     {
/* 1200:1230 */       c.setBold(false);
/* 1201:1231 */       c.setTemporaryColor(null);
/* 1202:     */     }
/* 1203:     */   }
/* 1204:     */   
/* 1205:     */   public ArrayList<Box> getConnectedBoxList()
/* 1206:     */   {
/* 1207:1236 */     if (this.connectedBoxList == null) {
/* 1208:1237 */       this.connectedBoxList = new ArrayList();
/* 1209:     */     }
/* 1210:1239 */     return this.connectedBoxList;
/* 1211:     */   }
/* 1212:     */   
/* 1213:     */   public ArrayList<Box> getUnconnectedBoxList()
/* 1214:     */   {
/* 1215:1243 */     if (this.unconnectedBoxList == null) {
/* 1216:1244 */       this.unconnectedBoxList = new ArrayList();
/* 1217:     */     }
/* 1218:1246 */     return this.unconnectedBoxList;
/* 1219:     */   }
/* 1220:     */   
/* 1221:     */   public ArrayList<Connection> getConnectionList()
/* 1222:     */   {
/* 1223:1250 */     if (this.connectionList == null) {
/* 1224:1251 */       this.connectionList = new ArrayList();
/* 1225:     */     }
/* 1226:1253 */     return this.connectionList;
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   private void drawLabel(Graphics g, String label, Rectangle rectangle)
/* 1230:     */   {
/* 1231:1260 */     String[] words = Pattern.compile(" ").split(label);
/* 1232:1261 */     FontMetrics fm = g.getFontMetrics();
/* 1233:1262 */     int width = rectangle.width;
/* 1234:1263 */     ArrayList<String> result = new ArrayList();
/* 1235:1264 */     String row = "";
/* 1236:1265 */     int spaceWidth = fm.stringWidth(" ");
/* 1237:1266 */     int maxWidth = 0;
/* 1238:1267 */     for (String word : words)
/* 1239:     */     {
/* 1240:1268 */       int rowWidth = fm.stringWidth(row);
/* 1241:1269 */       int wordWidth = fm.stringWidth(word);
/* 1242:1270 */       if (rowWidth == 0)
/* 1243:     */       {
/* 1244:1271 */         row = word;
/* 1245:     */       }
/* 1246:1273 */       else if (rowWidth + spaceWidth + wordWidth < width)
/* 1247:     */       {
/* 1248:1274 */         row = row + " " + word;
/* 1249:     */       }
/* 1250:     */       else
/* 1251:     */       {
/* 1252:1277 */         result.add(row);
/* 1253:1278 */         int thisWidth = fm.stringWidth(row);
/* 1254:1279 */         if (thisWidth > maxWidth) {
/* 1255:1280 */           maxWidth = thisWidth;
/* 1256:     */         }
/* 1257:1282 */         row = word;
/* 1258:     */       }
/* 1259:     */     }
/* 1260:1285 */     if (!row.isEmpty()) {
/* 1261:1286 */       result.add(row);
/* 1262:     */     }
/* 1263:1288 */     int lineCount = result.size();
/* 1264:1289 */     int lineHeight = g.getFontMetrics().getHeight();
/* 1265:1290 */     int height = lineCount * lineHeight;
/* 1266:1292 */     if ((maxWidth > rectangle.width - 4) || (height > rectangle.height - 4))
/* 1267:     */     {
/* 1268:1293 */       Font font = g.getFont();
/* 1269:1294 */       if (font.getSize() - 1 >= 1)
/* 1270:     */       {
/* 1271:1295 */         g.setFont(new Font(font.getName(), 1, font.getSize() - 1));
/* 1272:1296 */         drawLabel(g, label, rectangle);
/* 1273:     */       }
/* 1274:     */     }
/* 1275:     */     else
/* 1276:     */     {
/* 1277:1300 */       lineHeight = g.getFontMetrics().getHeight();
/* 1278:1301 */       int heightOffset = (lineCount - 1) * lineHeight / 2;
/* 1279:1302 */       for (int i = 0; i < lineCount; i++)
/* 1280:     */       {
/* 1281:1303 */         String line = (String)result.get(i);
/* 1282:1304 */         int stringWidth = g.getFontMetrics().stringWidth(line);
/* 1283:1305 */         int x = rectangle.x + rectangle.width / 2 - stringWidth / 2;
/* 1284:1306 */         int y = rectangle.y + rectangle.height / 2;
/* 1285:     */         
/* 1286:1308 */         g.drawString(line, x, y - heightOffset + i * lineHeight);
/* 1287:     */       }
/* 1288:     */     }
/* 1289:     */   }
/* 1290:     */   
/* 1291:     */   public Box getBox(MouseEvent e)
/* 1292:     */   {
/* 1293:1314 */     Point input = new Point(e.getX(), e.getY());
/* 1294:1315 */     Point2D output = this.inverse.transform(input, null);
/* 1295:1316 */     int x = (int)output.getX();
/* 1296:1317 */     int y = (int)output.getY();
/* 1297:1319 */     for (Box box : getConnectedBoxList())
/* 1298:     */     {
/* 1299:1320 */       Point p = getBoxOrigin(box);
/* 1300:1321 */       if ((x >= p.x) && (x <= p.x + this.boxWidth) && (y >= p.y) && (y <= p.y + this.boxHeight)) {
/* 1301:1323 */         return box;
/* 1302:     */       }
/* 1303:     */     }
/* 1304:1326 */     return null;
/* 1305:     */   }
/* 1306:     */   
/* 1307:     */   private void magnify(double m)
/* 1308:     */   {
/* 1309:1331 */     double previousMagnification = this.magnification;
/* 1310:1332 */     int halfWidth = getWidth() / 2;
/* 1311:1333 */     int halfHeight = getHeight() / 2;
/* 1312:     */     
/* 1313:1335 */     this.magnification = (m * this.magnification);
/* 1314:     */     
/* 1315:1337 */     this.offsetX = ((int)(this.magnification * (this.offsetX - halfWidth) / previousMagnification) + halfWidth);
/* 1316:1338 */     this.offsetY = ((int)(this.magnification * (this.offsetY - halfHeight) / previousMagnification) + halfHeight);
/* 1317:     */     
/* 1318:1340 */     repaint();
/* 1319:     */   }
/* 1320:     */   
/* 1321:     */   private void drawCross(Graphics g, int width, int height)
/* 1322:     */   {
/* 1323:1344 */     int r = 5;
/* 1324:1345 */     int w = width / 2;
/* 1325:1346 */     int h = height / 2;
/* 1326:1347 */     g.setColor(this.crossColor);
/* 1327:     */     
/* 1328:1349 */     g.drawLine(w - r, h, w + r, h);
/* 1329:1350 */     g.drawLine(w, h - 4, w, h + r);
/* 1330:1351 */     g.setColor(this.blackBox);
/* 1331:     */   }
/* 1332:     */   
/* 1333:     */   public void setAlwaysShowAllElements(boolean alwaysShowAllElements)
/* 1334:     */   {
/* 1335:1356 */     this.alwaysShowAllElements = alwaysShowAllElements;
/* 1336:     */   }
/* 1337:     */   
/* 1338:1361 */   private int offsetX = 0;
/* 1339:1363 */   private int offsetY = 0;
/* 1340:1365 */   private int pressedAtX = -1;
/* 1341:1367 */   private int pressedAtY = -1;
/* 1342:1369 */   private int offsetXWhenPressed = -1;
/* 1343:1371 */   private int offsetYWhenPressed = -1;
/* 1344:     */   private boolean mouseDown;
/* 1345:     */   
/* 1346:     */   public void mousePressed(MouseEvent e)
/* 1347:     */   {
/* 1348:1377 */     this.pressedAtX = e.getX();
/* 1349:1378 */     this.pressedAtY = e.getY();
/* 1350:1379 */     this.offsetXWhenPressed = this.offsetX;
/* 1351:1380 */     this.offsetYWhenPressed = this.offsetY;
/* 1352:1381 */     this.mouseDown = true;
/* 1353:1382 */     repaint();
/* 1354:     */   }
/* 1355:     */   
/* 1356:     */   public void mouseReleased(MouseEvent e)
/* 1357:     */   {
/* 1358:1387 */     this.pressedAtX = -1;
/* 1359:1388 */     this.pressedAtY = -1;
/* 1360:1389 */     this.offsetXWhenPressed = -1;
/* 1361:1390 */     this.offsetYWhenPressed = -1;
/* 1362:1391 */     this.mouseDown = false;
/* 1363:1392 */     repaint();
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   public void mouseDragged(MouseEvent e)
/* 1367:     */   {
/* 1368:1397 */     if ((this.pressedAtX != -1) && (this.pressedAtY != -1))
/* 1369:     */     {
/* 1370:1398 */       setOffsetX(e.getX() - this.pressedAtX + this.offsetXWhenPressed);
/* 1371:1399 */       setOffsetY(e.getY() - this.pressedAtY + this.offsetYWhenPressed);
/* 1372:     */     }
/* 1373:     */   }
/* 1374:     */   
/* 1375:     */   public void setOffsetX(int offsetX)
/* 1376:     */   {
/* 1377:1404 */     this.offsetX = offsetX;
/* 1378:1405 */     repaint();
/* 1379:     */   }
/* 1380:     */   
/* 1381:     */   public void setOffsetY(int offsetY)
/* 1382:     */   {
/* 1383:1409 */     this.offsetY = offsetY;
/* 1384:1410 */     repaint();
/* 1385:     */   }
/* 1386:     */   
/* 1387:     */   public void mouseClicked(MouseEvent e)
/* 1388:     */   {
/* 1389:1414 */     if (e.getButton() == 1) {
/* 1390:1415 */       magnify(1.2D);
/* 1391:1417 */     } else if (e.getButton() == 3) {
/* 1392:1418 */       magnify(0.8333333333333334D);
/* 1393:     */     }
/* 1394:     */   }
/* 1395:     */   
/* 1396:1423 */   Box highlighted = null;
/* 1397:     */   
/* 1398:     */   public void mouseMoved(MouseEvent e)
/* 1399:     */   {
/* 1400:1427 */     Box box = getBox(e);
/* 1401:1428 */     if (box != this.highlighted)
/* 1402:     */     {
/* 1403:1429 */       if (box != null) {
/* 1404:1431 */         highlight(box);
/* 1405:     */       } else {
/* 1406:1434 */         extinguish();
/* 1407:     */       }
/* 1408:1436 */       this.highlighted = box;
/* 1409:1437 */       repaint();
/* 1410:     */     }
/* 1411:     */   }
/* 1412:     */   
/* 1413:     */   public void mouseEntered(MouseEvent e) {}
/* 1414:     */   
/* 1415:     */   public void mouseExited(MouseEvent e) {}
/* 1416:     */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.ElaborationView
 * JD-Core Version:    0.7.0.1
 */