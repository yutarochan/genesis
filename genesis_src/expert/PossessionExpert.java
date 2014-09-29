/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class PossessionExpert
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public PossessionExpert()
/* 14:   */   {
/* 15:16 */     setName("Possession expert");
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
/* 33:   */ 
/* 34:   */ 
/* 35:35 */       new Object[] { Boolean.valueOf(false), "Entering PosessionExpert" });
/* 36:22 */     if (!(object instanceof Entity)) {
/* 37:23 */       return;
/* 38:   */     }
/* 39:25 */     Entity t = (Entity)object;
/* 40:26 */     if ((t.relationP()) && (t.isAPrimed("have")))
/* 41:   */     {
/* 42:27 */       Relation r = (Relation)t;
/* 43:28 */       Connections.getPorts(this).transmit("viewer", r);
/* 44:   */     }
/* 45:   */     else
/* 46:   */     {
/* 47:32 */       Connections.getPorts(this).transmit("next", t);
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.PossessionExpert
 * JD-Core Version:    0.7.0.1
 */