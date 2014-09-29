/*  1:   */ package alexanderCherian;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import utils.Mark;
/*  8:   */ 
/*  9:   */ public class Sample
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public static final String MY_INPUT = "my input";
/* 13:   */   
/* 14:   */   public Sample()
/* 15:   */   {
/* 16:17 */     Connections.getPorts(this).addSignalProcessor("my input", "process");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void process(Object o)
/* 20:   */   {
/* 21:21 */     if ((o instanceof Sequence)) {
/* 22:22 */       Mark.say(new Object[] {"We won!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" });
/* 23:   */     }
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     alexanderCherian.Sample
 * JD-Core Version:    0.7.0.1
 */