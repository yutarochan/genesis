/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.WiredViewer;
/*  6:   */ import gui.EventViewer;
/*  7:   */ import java.util.ArrayList;
/*  8:   */ import java.util.List;
/*  9:   */ 
/* 10:   */ public class EventFrame
/* 11:   */   extends Frame
/* 12:   */ {
/* 13: 8 */   public static String FRAMETYPE = "event";
/* 14: 9 */   private List<Entity> things = new ArrayList();
/* 15:   */   
/* 16:   */   public EventFrame() {}
/* 17:   */   
/* 18:   */   public EventFrame(Entity thing)
/* 19:   */   {
/* 20:13 */     assert (thing.isA(FRAMETYPE));
/* 21:14 */     this.things = thing.getElements();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public EventFrame(List<Entity> things)
/* 25:   */   {
/* 26:17 */     this.things.addAll(things);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void add(Entity thing)
/* 30:   */   {
/* 31:20 */     this.things.add(thing);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Entity getThing()
/* 35:   */   {
/* 36:24 */     Sequence s = new Sequence(FRAMETYPE);
/* 37:25 */     for (Entity t : this.things) {
/* 38:26 */       s.addElement(t);
/* 39:   */     }
/* 40:28 */     return s;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public WiredViewer getThingViewer()
/* 44:   */   {
/* 45:32 */     return new EventViewer();
/* 46:   */   }
/* 47:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.EventFrame
 * JD-Core Version:    0.7.0.1
 */