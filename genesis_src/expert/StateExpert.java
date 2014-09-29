/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import connections.AbstractWiredBox;
/*  7:   */ import connections.Connections;
/*  8:   */ import connections.Ports;
/*  9:   */ 
/* 10:   */ public class StateExpert
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public StateExpert()
/* 14:   */   {
/* 15:15 */     setName("State expert");
/* 16:16 */     Connections.getPorts(this).addSignalProcessor("process");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void process(Object object)
/* 20:   */   {
/* 21:20 */     if (!(object instanceof Entity)) {
/* 22:21 */       return;
/* 23:   */     }
/* 24:23 */     Entity t = (Entity)object;
/* 25:24 */     if ((t.isAPrimed("state")) && (t.relationP()))
/* 26:   */     {
/* 27:25 */       Relation r = (Relation)t;
/* 28:26 */       Entity o = r.getObject();
/* 29:27 */       if ((o.isAPrimed("location")) && (o.sequenceP()))
/* 30:   */       {
/* 31:28 */         Sequence s = (Sequence)o;
/* 32:29 */         for (Entity e : s.getElements()) {
/* 33:30 */           Connections.getPorts(this).transmit("direct", e);
/* 34:   */         }
/* 35:   */       }
/* 36:   */     }
/* 37:   */     else
/* 38:   */     {
/* 39:35 */       Connections.getPorts(this).transmit("next", t);
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.StateExpert
 * JD-Core Version:    0.7.0.1
 */