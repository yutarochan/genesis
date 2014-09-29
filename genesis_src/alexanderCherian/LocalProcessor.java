/*  1:   */ package alexanderCherian;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ 
/*  9:   */ public class LocalProcessor
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public static final String MY_INPUT_PORT = "my input port";
/* 13:   */   public static final String MY_OUTPUT_PORT = "my output port";
/* 14:   */   
/* 15:   */   public LocalProcessor()
/* 16:   */   {
/* 17:24 */     setName("Local story processor");
/* 18:   */     
/* 19:26 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/* 20:   */     
/* 21:28 */     Connections.getPorts(this).addSignalProcessor("complete story analysis port", "processSignal");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void processSignal(Object signal)
/* 25:   */   {
/* 26:38 */     if ((signal instanceof BetterSignal))
/* 27:   */     {
/* 28:40 */       BetterSignal s = (BetterSignal)signal;
/* 29:41 */       Sequence story = (Sequence)s.get(0, Sequence.class);
/* 30:42 */       Sequence explicitElements = (Sequence)s.get(1, Sequence.class);
/* 31:43 */       Sequence inferences = (Sequence)s.get(2, Sequence.class);
/* 32:44 */       Sequence concepts = (Sequence)s.get(3, Sequence.class);
/* 33:   */       
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:   */ 
/* 41:   */ 
/* 42:   */ 
/* 43:   */ 
/* 44:   */ 
/* 45:   */ 
/* 46:   */ 
/* 47:   */ 
/* 48:   */ 
/* 49:   */ 
/* 50:   */ 
/* 51:63 */       Connections.getPorts(this).transmit("my output port", story);
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     alexanderCherian.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */