/*  1:   */ package johnMallery;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class LocalProcessor
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13: 9 */   public final String MY_INPUT_PORT = "my input port";
/* 14:10 */   public final String MY_OUTPUT_PORT = "my output port";
/* 15:   */   
/* 16:   */   public LocalProcessor()
/* 17:   */   {
/* 18:13 */     setName("JCAMA's story processor");
/* 19:14 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/* 20:   */     
/* 21:16 */     Connections.getPorts(this).addSignalProcessor("my input port", "processSignal");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void processSignal(Object signal)
/* 25:   */   {
/* 26:20 */     if ((signal instanceof Entity))
/* 27:   */     {
/* 28:21 */       Entity t = (Entity)signal;
/* 29:22 */       if (t.sequenceP())
/* 30:   */       {
/* 31:23 */         Sequence s = (Sequence)t;
/* 32:24 */         Mark.say(new Object[] {"Story received:" });
/* 33:25 */         for (Entity e : s.getElements())
/* 34:   */         {
/* 35:26 */           Mark.say(new Object[] {e.asString() });
/* 36:27 */           Connections.getPorts(this).transmit(e);
/* 37:28 */           Connections.getPorts(this).transmit("my output port", e);
/* 38:   */         }
/* 39:   */       }
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     johnMallery.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */