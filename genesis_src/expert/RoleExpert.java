/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class RoleExpert
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public RoleExpert()
/* 13:   */   {
/* 14:15 */     setName("Role expert");
/* 15:16 */     Connections.getPorts(this).addSignalProcessor("process");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void process(Object object)
/* 19:   */   {
/* 20:20 */     if (!(object instanceof Entity)) {
/* 21:21 */       return;
/* 22:   */     }
/* 23:23 */     Entity t = (Entity)object;
/* 24:24 */     if (t.relationP("action"))
/* 25:   */     {
/* 26:25 */       Relation r = (Relation)t;
/* 27:   */       
/* 28:27 */       Connections.getPorts(this).transmit("viewer", r);
/* 29:   */     }
/* 30:30 */     Connections.getPorts(this).transmit("next", t);
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.RoleExpert
 * JD-Core Version:    0.7.0.1
 */