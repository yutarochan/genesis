/*  1:   */ package matthewFay.StoryThreading;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import java.util.ArrayList;
/*  6:   */ import java.util.HashMap;
/*  7:   */ import java.util.Iterator;
/*  8:   */ import java.util.List;
/*  9:   */ import java.util.Map;
/* 10:   */ import java.util.Set;
/* 11:   */ import matthewFay.Utilities.EntityHelper;
/* 12:   */ 
/* 13:   */ public class MinimumSpanningCharaterSet
/* 14:   */ {
/* 15:   */   private HashMap<Entity, Sequence> threads;
/* 16:   */   private List<Entity> entities;
/* 17:   */   Map<Entity, EventNode> eventToGraph;
/* 18:   */   List<EventNode> graph;
/* 19:   */   
/* 20:   */   public MinimumSpanningCharaterSet(HashMap<Entity, Sequence> threads)
/* 21:   */   {
/* 22:23 */     this.threads = threads;
/* 23:   */     
/* 24:25 */     this.graph = new ArrayList();
/* 25:26 */     this.eventToGraph = new HashMap();
/* 26:   */     
/* 27:28 */     this.entities = new ArrayList();
/* 28:29 */     for (Object entity : threads.keySet().toArray()) {
/* 29:30 */       this.entities.add((Entity)entity);
/* 30:   */     }
/* 31:   */   }
/* 32:   */   
/* 33:   */   public List<EventNode> constructStoryGraph()
/* 34:   */   {
/* 35:   */     List<Entity> events;
/* 36:   */     int i;
/* 37:39 */     for (Iterator localIterator = this.entities.iterator(); localIterator.hasNext(); i < events.size())
/* 38:   */     {
/* 39:39 */       Entity entity = (Entity)localIterator.next();
/* 40:40 */       Sequence thread = (Sequence)this.threads.get(entity);
/* 41:41 */       events = thread.getAllComponents();
/* 42:42 */       i = 0; continue;
/* 43:43 */       Entity event = (Entity)events.get(i);
/* 44:   */       EventNode node;
/* 45:   */       EventNode node;
/* 46:45 */       if (this.eventToGraph.containsKey(event))
/* 47:   */       {
/* 48:46 */         node = (EventNode)this.eventToGraph.get(event);
/* 49:   */       }
/* 50:   */       else
/* 51:   */       {
/* 52:48 */         node = new EventNode(event);
/* 53:49 */         this.graph.add(node);
/* 54:50 */         this.eventToGraph.put(event, node);
/* 55:   */       }
/* 56:52 */       node.entities.add(entity);
/* 57:53 */       if (i > 0)
/* 58:   */       {
/* 59:54 */         Entity lastEvent = (Entity)events.get(i - 1);
/* 60:55 */         EventNode lastNode = (EventNode)this.eventToGraph.get(lastEvent);
/* 61:56 */         node.pastEventNodes.add(lastNode);
/* 62:57 */         lastNode.futureEventNodes.add(node);
/* 63:   */       }
/* 64:42 */       i++;
/* 65:   */     }
/* 66:61 */     return this.graph;
/* 67:   */   }
/* 68:   */   
/* 69:   */   class EventNode
/* 70:   */   {
/* 71:   */     public Entity event;
/* 72:   */     public List<EventNode> futureEventNodes;
/* 73:   */     public List<EventNode> pastEventNodes;
/* 74:   */     public List<Entity> entities;
/* 75:   */     
/* 76:   */     public EventNode(Entity event)
/* 77:   */     {
/* 78:71 */       this.event = event;
/* 79:72 */       this.futureEventNodes = new ArrayList();
/* 80:73 */       this.pastEventNodes = new ArrayList();
/* 81:74 */       this.entities = new ArrayList();
/* 82:   */     }
/* 83:   */   }
/* 84:   */   
/* 85:   */   public int countEntities(Entity story)
/* 86:   */   {
/* 87:79 */     List<Entity> entities = EntityHelper.getAllEntities(story);
/* 88:80 */     return entities.size();
/* 89:   */   }
/* 90:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.StoryThreading.MinimumSpanningCharaterSet
 * JD-Core Version:    0.7.0.1
 */