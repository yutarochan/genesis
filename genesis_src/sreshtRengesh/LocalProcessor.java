/*  1:   */ package sreshtRengesh;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import connections.AbstractWiredBox;
/*  7:   */ import connections.Connections;
/*  8:   */ import connections.Ports;
/*  9:   */ import utils.Mark;
/* 10:   */ 
/* 11:   */ public class LocalProcessor
/* 12:   */   extends AbstractWiredBox
/* 13:   */ {
/* 14:   */   public LocalProcessor()
/* 15:   */   {
/* 16:11 */     setName("Sresht's local story processor");
/* 17:12 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/* 18:   */     
/* 19:14 */     Connections.getPorts(this).addSignalProcessor(LocalGenesis.STORY, "processStory");
/* 20:15 */     Connections.getPorts(this).addSignalProcessor(LocalGenesis.METAPHOR, "processMetaphor");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void processStory(Object signal)
/* 24:   */   {
/* 25:19 */     if ((signal instanceof Entity))
/* 26:   */     {
/* 27:20 */       Entity t = (Entity)signal;
/* 28:21 */       if (t.sequenceP())
/* 29:   */       {
/* 30:22 */         Sequence s = (Sequence)t;
/* 31:23 */         Mark.say(new Object[] {"Story received:" });
/* 32:24 */         for (Entity e : s.getElements()) {
/* 33:25 */           Mark.say(new Object[] {e.asString() });
/* 34:   */         }
/* 35:   */       }
/* 36:   */     }
/* 37:31 */     else if ((signal instanceof BetterSignal))
/* 38:   */     {
/* 39:32 */       Mark.say(new Object[] {"Received story" });
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void processMetaphor(Object signal)
/* 44:   */   {
/* 45:61 */     if ((signal instanceof Entity))
/* 46:   */     {
/* 47:62 */       Entity t = (Entity)signal;
/* 48:63 */       if (t.sequenceP())
/* 49:   */       {
/* 50:64 */         Sequence s = (Sequence)t;
/* 51:65 */         Mark.say(new Object[] {"Story received:" });
/* 52:66 */         for (Entity e : s.getElements()) {
/* 53:67 */           Mark.say(new Object[] {e.asString() });
/* 54:   */         }
/* 55:   */       }
/* 56:   */     }
/* 57:73 */     else if ((signal instanceof BetterSignal))
/* 58:   */     {
/* 59:74 */       Mark.say(new Object[] {"Received metaphor" });
/* 60:   */     }
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     sreshtRengesh.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */