/*  1:   */ package template;
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
/* 13:10 */   public final String MY_INPUT_PORT = "my input port";
/* 14:11 */   public final String MY_OUTPUT_PORT = "my output port";
/* 15:   */   
/* 16:   */   public LocalProcessor()
/* 17:   */   {
/* 18:14 */     setName("Student's processor");
/* 19:15 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/* 20:   */     
/* 21:17 */     Connections.getPorts(this).addSignalProcessor("my input port", "processSignal");
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void processSignal(Object signal)
/* 25:   */   {
/* 26:21 */     if ((signal instanceof Entity))
/* 27:   */     {
/* 28:22 */       Entity t = (Entity)signal;
/* 29:23 */       if (t.sequenceP())
/* 30:   */       {
/* 31:24 */         Sequence s = (Sequence)t;
/* 32:25 */         Mark.say(new Object[] {"Story received:" });
/* 33:26 */         for (Entity e : s.getElements())
/* 34:   */         {
/* 35:27 */           Mark.say(new Object[] {e.asString() });
/* 36:28 */           Connections.getPorts(this).transmit(e);
/* 37:29 */           Connections.getPorts(this).transmit("my output port", e);
/* 38:   */         }
/* 39:   */       }
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     template.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */