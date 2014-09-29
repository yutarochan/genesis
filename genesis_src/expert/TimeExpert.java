/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ 
/*  8:   */ public class TimeExpert
/*  9:   */   extends AbstractWiredBox
/* 10:   */ {
/* 11:   */   public TimeExpert()
/* 12:   */   {
/* 13:15 */     setName("Time expert");
/* 14:16 */     Connections.getPorts(this).addSignalProcessor("process");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void process(Object object)
/* 18:   */   {
/* 19:20 */     if (!(object instanceof Entity)) {
/* 20:21 */       return;
/* 21:   */     }
/* 22:23 */     Entity t = (Entity)object;
/* 23:24 */     if ((t.isAPrimed("time-relation")) && (t.relationP()))
/* 24:   */     {
/* 25:25 */       Connections.getPorts(this).transmit("viewer", t);
/* 26:26 */       Connections.getPorts(this).transmit("loop", t.getSubject());
/* 27:27 */       Connections.getPorts(this).transmit("loop", t.getObject());
/* 28:   */     }
/* 29:29 */     else if (t.functionP("milestone"))
/* 30:   */     {
/* 31:30 */       Connections.getPorts(this).transmit("viewer", t);
/* 32:   */     }
/* 33:   */     else
/* 34:   */     {
/* 35:33 */       Connections.getPorts(this).transmit("next", t);
/* 36:   */     }
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.TimeExpert
 * JD-Core Version:    0.7.0.1
 */