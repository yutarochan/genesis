/*   1:    */ package dylanHolmes;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Matcher;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import connections.WiredBox;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Vector;
/*  11:    */ import storyProcessor.ReflectionDescription;
/*  12:    */ import utils.Mark;
/*  13:    */ 
/*  14:    */ public class ElaborationViewer
/*  15:    */   extends NetworkViewer<EntityBox>
/*  16:    */ {
/*  17:    */   public static final String FROM_REFLECTION_BAR = "plot unit input";
/*  18:    */   public static final String PROCESS_PATH = "processing path";
/*  19:    */   
/*  20:    */   public EntityBox getBoxByEntity(Entity e, boolean createIfNotFound)
/*  21:    */   {
/*  22: 33 */     EntityBox result = (EntityBox)getBoxByName(e.getName());
/*  23: 34 */     if ((result == null) && (createIfNotFound)) {
/*  24: 35 */       return new EntityBox(e);
/*  25:    */     }
/*  26: 37 */     return result;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public ArrayList<EntityBox> forward(EntityBox root, Entity needle)
/*  30:    */   {
/*  31: 47 */     ArrayList<ArrayList<EntityBox>> pathQueue = new ArrayList();
/*  32:    */     
/*  33:    */ 
/*  34: 50 */     ArrayList<EntityBox> path = new ArrayList();
/*  35: 51 */     path.add(root);
/*  36: 52 */     pathQueue.add(path);
/*  37: 54 */     while (!pathQueue.isEmpty())
/*  38:    */     {
/*  39: 55 */       path = (ArrayList)pathQueue.get(0);
/*  40: 56 */       EntityBox end = (EntityBox)path.get(pathQueue.size() - 1);
/*  41: 57 */       if (matchEntity(needle, end.entity)) {
/*  42: 58 */         return path;
/*  43:    */       }
/*  44:    */     }
/*  45: 63 */     return null;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void updateBoxes(Object signal)
/*  49:    */   {
/*  50: 75 */     ArrayList<WiredBox> boxes = new ArrayList();
/*  51: 77 */     if ((signal instanceof Vector))
/*  52:    */     {
/*  53: 79 */       Vector<Entity> entityList = (Vector)signal;
/*  54: 80 */       Mark.say(new Object[] {"Path received for display:", Integer.valueOf(entityList.size()), "elements" });
/*  55:    */     }
/*  56:    */     else
/*  57:    */     {
/*  58:    */       Vector<Entity> entityList;
/*  59: 82 */       if ((signal instanceof ReflectionDescription))
/*  60:    */       {
/*  61: 84 */         ReflectionDescription completion = (ReflectionDescription)signal;
/*  62: 85 */         entityList = completion.getStoryElementsInvolved().getElements();
/*  63:    */       }
/*  64:    */       else
/*  65:    */       {
/*  66: 88 */         Mark.say(new Object[] {"Unrecognized signal sent to ElaborationViewer." }); return;
/*  67:    */       }
/*  68:    */     }
/*  69:    */     Vector<Entity> entityList;
/*  70: 95 */     for (Entity e : entityList)
/*  71:    */     {
/*  72: 96 */       WiredBox b = getBoxByEntity(e, false);
/*  73: 97 */       if (b != null) {
/*  74: 98 */         boxes.add(b);
/*  75:    */       } else {
/*  76:101 */         Mark.say(new Object[] {"Found NO box for ", e.asStringWithIndexes() });
/*  77:    */       }
/*  78:    */     }
/*  79:105 */     changed(boxes);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public static boolean matchEntity(Entity x, Entity y)
/*  83:    */   {
/*  84:111 */     return Matcher.match(x, y) != null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public ElaborationViewer(String name)
/*  88:    */   {
/*  89:116 */     super(name);
/*  90:117 */     Connections.getPorts(this).addSignalProcessor("process");
/*  91:118 */     Connections.getPorts(this).addSignalProcessor("plot unit input", "processPlotUnit");
/*  92:119 */     Connections.getPorts(this).addSignalProcessor("processing path", "processPath");
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     dylanHolmes.ElaborationViewer
 * JD-Core Version:    0.7.0.1
 */