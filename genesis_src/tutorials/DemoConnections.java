/*  1:   */ package tutorials;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import Signals.Signal;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class DemoConnections
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   public DemoConnections()
/* 14:   */   {
/* 15:15 */     Connections.getPorts(this).addSignalProcessor("processInput");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void demonstrate()
/* 19:   */   {
/* 20:19 */     String signalA = "Hello";
/* 21:20 */     String signalB = "World";
/* 22:21 */     Mark.say(new Object[] {"Test instance named source transmits", signalA, signalB });
/* 23:22 */     Connections.getPorts(this).transmit(new Signal(new Object[] { signalA, signalB }));
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processInput(Object input)
/* 27:   */   {
/* 28:26 */     if ((input instanceof Signal))
/* 29:   */     {
/* 30:27 */       BetterSignal signal = (BetterSignal)input;
/* 31:28 */       String x = (String)signal.get(0, String.class);
/* 32:29 */       String y = (String)signal.get(1, String.class);
/* 33:30 */       Mark.say(new Object[] {"Test instance named destination receives", x, y });
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public static void main(String[] args)
/* 38:   */   {
/* 39:35 */     DemoConnections source = new DemoConnections();
/* 40:36 */     DemoConnections destination = new DemoConnections();
/* 41:37 */     Connections.wire(source, destination);
/* 42:38 */     source.demonstrate();
/* 43:   */   }
/* 44:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tutorials.DemoConnections
 * JD-Core Version:    0.7.0.1
 */