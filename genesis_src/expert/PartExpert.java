/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import tools.Getters;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class PartExpert
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public PartExpert()
/* 14:   */   {
/* 15:17 */     setName("Part expert");
/* 16:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void process(Object object)
/* 20:   */   {
/* 21:22 */     Mark.say(
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
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:41 */       new Object[] { Boolean.valueOf(false), "Entering PartExpert" });
/* 41:23 */     if (!(object instanceof Entity)) {
/* 42:24 */       return;
/* 43:   */     }
/* 44:26 */     Entity t = (Entity)object;
/* 45:28 */     if (t.relationP("have"))
/* 46:   */     {
/* 47:29 */       Entity o = Getters.getObject(t);
/* 48:30 */       if ((o != null) && ((o.isA("body-part")) || (o.isA("body-covering")))) {
/* 49:31 */         Connections.getPorts(this).transmit("viewer", t);
/* 50:   */       } else {
/* 51:34 */         Connections.getPorts(this).transmit("next", t);
/* 52:   */       }
/* 53:   */     }
/* 54:   */     else
/* 55:   */     {
/* 56:38 */       Connections.getPorts(this).transmit("next", t);
/* 57:   */     }
/* 58:   */   }
/* 59:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.PartExpert
 * JD-Core Version:    0.7.0.1
 */