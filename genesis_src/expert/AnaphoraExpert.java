/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ 
/*  8:   */ public class AnaphoraExpert
/*  9:   */   extends AbstractWiredBox
/* 10:   */ {
/* 11:   */   public AnaphoraExpert()
/* 12:   */   {
/* 13:21 */     setName("Anaphora expert");
/* 14:22 */     Connections.getPorts(this).addSignalProcessor("process");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void process(Object object)
/* 18:   */   {
/* 19:30 */     if (!(object instanceof Entity)) {
/* 20:31 */       return;
/* 21:   */     }
/* 22:35 */     Entity t = (Entity)object;
/* 23:   */     
/* 24:37 */     Connections.getPorts(this).transmit("next", t);
/* 25:38 */     findObjectsForViewer(t);
/* 26:   */   }
/* 27:   */   
/* 28:   */   private void findObjectsForViewer(Entity t)
/* 29:   */   {
/* 30:61 */     if (t.entityP())
/* 31:   */     {
/* 32:62 */       Connections.getPorts(this).transmit("viewer", t);
/* 33:   */     }
/* 34:64 */     else if (t.functionP())
/* 35:   */     {
/* 36:65 */       findObjectsForViewer(t.getSubject());
/* 37:   */     }
/* 38:67 */     else if (t.relationP())
/* 39:   */     {
/* 40:68 */       findObjectsForViewer(t.getSubject());
/* 41:69 */       findObjectsForViewer(t.getObject());
/* 42:   */     }
/* 43:71 */     else if (t.sequenceP())
/* 44:   */     {
/* 45:72 */       for (Entity e : t.getElements()) {
/* 46:73 */         findObjectsForViewer(e);
/* 47:   */       }
/* 48:   */     }
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.AnaphoraExpert
 * JD-Core Version:    0.7.0.1
 */