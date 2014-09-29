/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ class LinkedPortPair
/*  4:   */ {
/*  5:   */   String sourcePortName;
/*  6:   */   WiredBox sourceBox;
/*  7:   */   String destinationPortName;
/*  8:   */   WiredBox destinationBox;
/*  9:   */   
/* 10:   */   public LinkedPortPair(String sourcePortName, WiredBox sourceBox, String destinationPortName, WiredBox destinationBox)
/* 11:   */   {
/* 12:20 */     this.sourcePortName = sourcePortName;
/* 13:21 */     this.sourceBox = sourceBox;
/* 14:22 */     this.destinationPortName = destinationPortName;
/* 15:23 */     this.destinationBox = destinationBox;
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.LinkedPortPair
 * JD-Core Version:    0.7.0.1
 */