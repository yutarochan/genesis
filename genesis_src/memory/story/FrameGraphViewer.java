/*  1:   */ package memory.story;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.util.HashSet;
/*  7:   */ import java.util.Hashtable;
/*  8:   */ import javax.swing.JFrame;
/*  9:   */ 
/* 10:   */ public class FrameGraphViewer
/* 11:   */   extends JFrame
/* 12:   */   implements WiredBox
/* 13:   */ {
/* 14:   */   private static final long serialVersionUID = 1L;
/* 15:   */   private FrameGraph data;
/* 16:   */   private Hashtable<Entity, Integer> nodeMap;
/* 17:   */   
/* 18:   */   public FrameGraphViewer()
/* 19:   */   {
/* 20:26 */     fillNodeMap();
/* 21:27 */     repaint();
/* 22:   */   }
/* 23:   */   
/* 24:   */   private FrameGraph getData()
/* 25:   */   {
/* 26:34 */     return this.data;
/* 27:   */   }
/* 28:   */   
/* 29:   */   private void setData(FrameGraph data)
/* 30:   */   {
/* 31:40 */     this.data = data;
/* 32:   */   }
/* 33:   */   
/* 34:   */   private Hashtable<Entity, Integer> getNodeMap()
/* 35:   */   {
/* 36:46 */     return this.nodeMap;
/* 37:   */   }
/* 38:   */   
/* 39:   */   private void setNodeMap(Hashtable<Entity, Integer> nodeMap)
/* 40:   */   {
/* 41:52 */     this.nodeMap = nodeMap;
/* 42:   */   }
/* 43:   */   
/* 44:   */   private void fillNodeMap()
/* 45:   */   {
/* 46:56 */     setNodeMap(new Hashtable());
/* 47:57 */     HashSet<Entity> nodeSet = getData().getNodes();
/* 48:58 */     int count = 0;
/* 49:60 */     for (Entity node : nodeSet)
/* 50:   */     {
/* 51:61 */       getNodeMap().put(node, Integer.valueOf(count));
/* 52:62 */       count++;
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   public void setParameters(FrameGraph input)
/* 57:   */   {
/* 58:67 */     setData(input);
/* 59:68 */     fillNodeMap();
/* 60:69 */     setVisible(true);
/* 61:70 */     repaint();
/* 62:   */   }
/* 63:   */   
/* 64:   */   public static void main(String[] args)
/* 65:   */   {
/* 66:78 */     FrameGraphViewer view = new FrameGraphViewer();
/* 67:79 */     FrameGraph graph = new FrameGraph();
/* 68:   */     
/* 69:   */ 
/* 70:   */ 
/* 71:   */ 
/* 72:84 */     Entity t1 = new Entity("Ray");
/* 73:85 */     Entity t2 = new Entity("Patrick");
/* 74:86 */     Entity t3 = new Entity("Mike");
/* 75:87 */     Entity t4 = new Entity("Mark");
/* 76:   */     
/* 77:89 */     graph.add(t3, t1, "older");
/* 78:90 */     graph.add(t3, t1, "not as cool");
/* 79:91 */     graph.add(t4, t3, new Relation("older", t4, t3));
/* 80:92 */     graph.add(new Relation("older", t2, t4));
/* 81:   */     
/* 82:94 */     view.setBounds(0, 0, 500, 500);
/* 83:95 */     view.setVisible(true);
/* 84:96 */     view.setParameters(graph);
/* 85:   */   }
/* 86:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     memory.story.FrameGraphViewer
 * JD-Core Version:    0.7.0.1
 */