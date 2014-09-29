/*  1:   */ package genesis;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ 
/*  7:   */ public class DistributionBox
/*  8:   */   extends AbstractWiredBox
/*  9:   */ {
/* 10:   */   GenesisGetters gauntlet;
/* 11:   */   
/* 12:   */   public DistributionBox(GenesisGetters genesisGetters)
/* 13:   */   {
/* 14:10 */     this.gauntlet = genesisGetters;
/* 15:11 */     Connections.getPorts(this).addSignalProcessor("process");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void process(Object input)
/* 19:   */   {
/* 20:15 */     Connections.getPorts(this).transmit(input);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     genesis.DistributionBox
 * JD-Core Version:    0.7.0.1
 */