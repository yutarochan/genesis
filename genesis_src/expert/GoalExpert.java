/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import connections.WiredBox;
/*  9:   */ import utils.Tool;
/* 10:   */ 
/* 11:   */ public class GoalExpert
/* 12:   */   extends AbstractWiredBox
/* 13:   */ {
/* 14:   */   public GoalExpert()
/* 15:   */   {
/* 16:13 */     setName("Goal expert");
/* 17:14 */     Connections.getPorts(this).addSignalProcessor("process");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void process(Object object)
/* 21:   */   {
/* 22:18 */     if (!(object instanceof Entity)) {
/* 23:19 */       return;
/* 24:   */     }
/* 25:21 */     Entity t = (Entity)object;
/* 26:22 */     if ((t.isAPrimed("goal")) && ((t instanceof Relation)))
/* 27:   */     {
/* 28:23 */       Relation r = (Relation)t;
/* 29:   */       
/* 30:   */ 
/* 31:26 */       Connections.getPorts(this).transmit("viewer", r);
/* 32:27 */       Entity goal = Tool.extractObject(r);
/* 33:28 */       if (goal != null) {
/* 34:29 */         Connections.getPorts(this).transmit("next", goal);
/* 35:31 */       } else if (!r.getObject().entityP()) {
/* 36:32 */         transmitIfNotThing(this, "loop", r.getObject());
/* 37:   */       }
/* 38:   */     }
/* 39:   */     else
/* 40:   */     {
/* 41:36 */       Connections.getPorts(this).transmit("next", t);
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   private void transmitIfNotThing(WiredBox box, String port, Entity t)
/* 46:   */   {
/* 47:42 */     if (!t.entityP()) {
/* 48:43 */       Connections.getPorts(box).transmit(port, t);
/* 49:   */     }
/* 50:   */   }
/* 51:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.GoalExpert
 * JD-Core Version:    0.7.0.1
 */