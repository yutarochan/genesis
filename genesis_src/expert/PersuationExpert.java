/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import utils.Tool;
/*  9:   */ 
/* 10:   */ public class PersuationExpert
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public PersuationExpert()
/* 14:   */   {
/* 15:16 */     setName("Persuation expert");
/* 16:17 */     Connections.getPorts(this).addSignalProcessor("process");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void process(Object object)
/* 20:   */   {
/* 21:21 */     if (!(object instanceof Entity)) {
/* 22:22 */       return;
/* 23:   */     }
/* 24:24 */     Entity t = (Entity)object;
/* 25:25 */     if ((t.relationP("persuade")) || (t.relationP("ask")))
/* 26:   */     {
/* 27:26 */       Relation r = (Relation)t;
/* 28:27 */       Connections.getPorts(this).transmit("viewer", r);
/* 29:   */       
/* 30:29 */       Entity goal = Tool.extractObject(r);
/* 31:30 */       if (goal != null) {
/* 32:31 */         Connections.getPorts(this).transmit("next", goal);
/* 33:   */       } else {
/* 34:34 */         Connections.getPorts(this).transmit("loop", r.getObject());
/* 35:   */       }
/* 36:   */     }
/* 37:   */     else
/* 38:   */     {
/* 39:39 */       Connections.getPorts(this).transmit("next", t);
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.PersuationExpert
 * JD-Core Version:    0.7.0.1
 */