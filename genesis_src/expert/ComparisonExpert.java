/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class ComparisonExpert
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public ComparisonExpert()
/* 13:   */   {
/* 14:15 */     setName("Comparison expert");
/* 15:16 */     Connections.getPorts(this).addSignalProcessor("process");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void process(Object object)
/* 19:   */   {
/* 20:20 */     if (!(object instanceof Entity)) {
/* 21:21 */       return;
/* 22:   */     }
/* 23:23 */     Entity t = (Entity)object;
/* 24:24 */     if ((t.isAPrimed("comparison")) && ((t instanceof Relation)))
/* 25:   */     {
/* 26:25 */       Relation r = (Relation)t;
/* 27:26 */       Connections.getPorts(this).transmit("viewer", r);
/* 28:   */     }
/* 29:   */     else
/* 30:   */     {
/* 31:29 */       Connections.getPorts(this).transmit("next", t);
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.ComparisonExpert
 * JD-Core Version:    0.7.0.1
 */