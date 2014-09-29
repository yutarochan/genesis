/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ 
/*  8:   */ public class BensGauntletComponent
/*  9:   */   extends AbstractWiredBox
/* 10:   */ {
/* 11:   */   public BensGauntletComponent()
/* 12:   */   {
/* 13: 7 */     Connections.getPorts(this).addSignalProcessor("dispatch");
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void dispatch(Object o)
/* 17:   */   {
/* 18:11 */     if ((o instanceof Entity))
/* 19:   */     {
/* 20:12 */       Entity t = (Entity)o;
/* 21:13 */       if (t.isA(ForceFrame.FRAMETYPE)) {
/* 22:14 */         Connections.getPorts(this).transmit("force", t);
/* 23:16 */       } else if (t.isA(GeometryFrame.FRAMETYPE)) {
/* 24:17 */         Connections.getPorts(this).transmit("geometry", t);
/* 25:19 */       } else if (t.isA(BlockFrame.FRAMETYPE)) {
/* 26:20 */         Connections.getPorts(this).transmit("block", t);
/* 27:22 */       } else if (t.isA(TimeFrame.FRAMETYPE)) {
/* 28:23 */         Connections.getPorts(this).transmit("time", t);
/* 29:   */       }
/* 30:   */     }
/* 31:   */   }
/* 32:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.BensGauntletComponent
 * JD-Core Version:    0.7.0.1
 */