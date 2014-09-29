/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class PathExpert
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public PathExpert()
/* 13:   */   {
/* 14:16 */     setName("Path expert");
/* 15:17 */     Connections.getPorts(this).addSignalProcessor("process");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void process(Object object)
/* 19:   */   {
/* 20:21 */     if (!(object instanceof Entity)) {
/* 21:22 */       return;
/* 22:   */     }
/* 23:24 */     Entity t = (Entity)object;
/* 24:25 */     if (t.isAPrimed("roles"))
/* 25:   */     {
/* 26:26 */       Sequence s = (Sequence)t;
/* 27:27 */       Connections.getPorts(this).transmit("viewer", s);
/* 28:28 */       for (Entity element : s.getElements()) {
/* 29:29 */         Connections.getPorts(this).transmit("path", element);
/* 30:   */       }
/* 31:   */     }
/* 32:   */   }
/* 33:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.PathExpert
 * JD-Core Version:    0.7.0.1
 */