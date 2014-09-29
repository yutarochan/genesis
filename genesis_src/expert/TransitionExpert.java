/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import translator.NewRuleSet;
/*  9:   */ 
/* 10:   */ public class TransitionExpert
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public TransitionExpert()
/* 14:   */   {
/* 15:17 */     setName("Transition expert");
/* 16:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void process(Object object)
/* 20:   */   {
/* 21:22 */     if (!(object instanceof Entity)) {
/* 22:23 */       return;
/* 23:   */     }
/* 24:25 */     Entity t = (Entity)object;
/* 25:26 */     if ((t.relationP()) && (t.isAPrimed(NewRuleSet.transitionWords)))
/* 26:   */     {
/* 27:27 */       Function d = (Function)t;
/* 28:28 */       Connections.getPorts(this).transmit("viewer", t);
/* 29:30 */       if (!d.getSubject().entityP()) {
/* 30:31 */         Connections.getPorts(this).transmit("next", d.getSubject());
/* 31:   */       }
/* 32:   */     }
/* 33:   */     else
/* 34:   */     {
/* 35:36 */       Connections.getPorts(this).transmit("next", t);
/* 36:   */     }
/* 37:   */   }
/* 38:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.TransitionExpert
 * JD-Core Version:    0.7.0.1
 */