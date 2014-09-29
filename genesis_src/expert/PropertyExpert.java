/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class PropertyExpert
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public PropertyExpert()
/* 14:   */   {
/* 15:16 */     setName("Property expert");
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
/* 34:34 */       new Object[] { Boolean.valueOf(false), "Entering PropertyExpert" });
/* 35:22 */     if (!(object instanceof Entity)) {
/* 36:23 */       return;
/* 37:   */     }
/* 38:25 */     Entity t = (Entity)object;
/* 39:26 */     if ((t.relationP()) && (t.isAPrimed("property")))
/* 40:   */     {
/* 41:27 */       Relation r = (Relation)t;
/* 42:28 */       Connections.getPorts(this).transmit("viewer", r);
/* 43:   */     }
/* 44:   */     else
/* 45:   */     {
/* 46:31 */       Connections.getPorts(this).transmit("next", t);
/* 47:   */     }
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.PropertyExpert
 * JD-Core Version:    0.7.0.1
 */