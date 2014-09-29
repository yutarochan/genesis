/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class MoodExpert
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public MoodExpert()
/* 13:   */   {
/* 14:16 */     setName("Mood expert");
/* 15:17 */     Connections.getPorts(this).addSignalProcessor("process");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void process(Object object)
/* 19:   */   {
/* 20:21 */     if (!(object instanceof Entity)) {
/* 21:22 */       return;
/* 22:   */     }
/* 23:24 */     Entity t = (Entity)object;
/* 24:25 */     if ((t.isAPrimed("has-mental-state")) && ((t instanceof Relation)))
/* 25:   */     {
/* 26:26 */       Relation r = (Relation)t;
/* 27:27 */       Connections.getPorts(this).transmit("viewer", r);
/* 28:   */     }
/* 29:   */     else
/* 30:   */     {
/* 31:30 */       Connections.getPorts(this).transmit("next", t);
/* 32:   */     }
/* 33:   */   }
/* 34:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.MoodExpert
 * JD-Core Version:    0.7.0.1
 */