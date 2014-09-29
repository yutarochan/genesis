/*  1:   */ package genesis;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ 
/*  7:   */ public class StateMaintainer
/*  8:   */   extends AbstractWiredBox
/*  9:   */ {
/* 10:   */   GenesisGetters genesisGetters;
/* 11:   */   
/* 12:   */   public StateMaintainer(GenesisGetters genesisGetters)
/* 13:   */   {
/* 14:14 */     setName("State maintainer");
/* 15:15 */     this.genesisGetters = genesisGetters;
/* 16:16 */     Connections.getPorts(this).addSignalProcessor("setState");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setState(Object o)
/* 20:   */   {
/* 21:20 */     if (this.genesisGetters != null) {
/* 22:21 */       this.genesisGetters.changeState(o);
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.StateMaintainer
 * JD-Core Version:    0.7.0.1
 */