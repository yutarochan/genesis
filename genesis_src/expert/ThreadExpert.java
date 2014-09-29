/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class ThreadExpert
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public ThreadExpert()
/* 14:   */   {
/* 15:16 */     setName("Thread expert");
/* 16:17 */     Connections.getPorts(this).addSignalProcessor("process");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void process(Object object)
/* 20:   */   {
/* 21:21 */     Mark.say(
/* 22:   */     
/* 23:   */ 
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:   */ 
/* 31:   */ 
/* 32:   */ 
/* 33:33 */       new Object[] { Boolean.valueOf(false), "Entering ThreadExpert" });
/* 34:22 */     if (!(object instanceof Entity)) {
/* 35:23 */       return;
/* 36:   */     }
/* 37:25 */     Entity t = (Entity)object;
/* 38:26 */     if ((t.isAPrimed("classification")) && ((t instanceof Relation))) {
/* 39:27 */       Connections.getPorts(this).transmit("viewer", t);
/* 40:   */     } else {
/* 41:30 */       Connections.getPorts(this).transmit("next", t);
/* 42:   */     }
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.ThreadExpert
 * JD-Core Version:    0.7.0.1
 */