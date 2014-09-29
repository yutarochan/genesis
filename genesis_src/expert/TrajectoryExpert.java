/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import translator.NewRuleSet;
/*  9:   */ 
/* 10:   */ public class TrajectoryExpert
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public TrajectoryExpert()
/* 14:   */   {
/* 15:17 */     setName("Trajectory expert");
/* 16:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void process(Object object)
/* 20:   */   {
/* 21:22 */     if (!(object instanceof Entity)) {
/* 22:23 */       return;
/* 23:   */     }
/* 24:25 */     Entity t = (Entity)object;
/* 25:26 */     if ((t.relationP()) && (t.isA(NewRuleSet.trajectoryWords)))
/* 26:   */     {
/* 27:27 */       Relation r = (Relation)t;
/* 28:28 */       Connections.getPorts(this).transmit("viewer", r);
/* 29:29 */       Connections.getPorts(this).transmit("path", r.getObject());
/* 30:   */     }
/* 31:   */     else
/* 32:   */     {
/* 33:32 */       Connections.getPorts(this).transmit("next", t);
/* 34:   */     }
/* 35:   */   }
/* 36:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.TrajectoryExpert
 * JD-Core Version:    0.7.0.1
 */