/*  1:   */ package chrisJones;
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
/* 14:11 */   public final String MY_INPUT_PORT = "my input port";
/* 15:13 */   public final String MY_OUTPUT_PORT = "my output port";
/* 16:   */   
/* 17:   */   public LocalProcessor()
/* 18:   */   {
/* 19:16 */     setName("Chris's local story processor");
/* 20:17 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/* 21:   */     
/* 22:19 */     Connections.getPorts(this).addSignalProcessor("complete story analysis port", "processSignal");
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void processSignal(Object signal)
/* 26:   */   {
/* 27:23 */     if ((signal instanceof Entity))
/* 28:   */     {
/* 29:24 */       Entity t = (Entity)signal;
/* 30:25 */       if (t.sequenceP())
/* 31:   */       {
/* 32:26 */         Sequence s = (Sequence)t;
/* 33:27 */         Mark.say(new Object[] {"Story received:" });
/* 34:28 */         for (Entity e : s.getElements())
/* 35:   */         {
/* 36:29 */           Mark.say(new Object[] {e.asString() });
/* 37:30 */           Connections.getPorts(this).transmit(e);
/* 38:31 */           Connections.getPorts(this).transmit("my output port", e);
/* 39:   */         }
/* 40:   */       }
/* 41:   */     }
/* 42:35 */     else if ((signal instanceof BetterSignal))
/* 43:   */     {
/* 44:37 */       BetterSignal s = (BetterSignal)signal;
/* 45:38 */       Sequence story = (Sequence)s.get(0, Sequence.class);
/* 46:39 */       Sequence explicitElements = (Sequence)s.get(1, Sequence.class);
/* 47:40 */       Sequence inferences = (Sequence)s.get(2, Sequence.class);
/* 48:41 */       Sequence concepts = (Sequence)s.get(3, Sequence.class);
/* 49:42 */       Mark.say(new Object[] {"\n\n\nStory elements" });
/* 50:44 */       for (Entity e : story.getElements()) {
/* 51:45 */         Mark.say(new Object[] {e.asString() });
/* 52:   */       }
/* 53:47 */       Mark.say(new Object[] {"\n\n\nExplicit story elements" });
/* 54:48 */       for (Entity e : explicitElements.getElements()) {
/* 55:49 */         Mark.say(new Object[] {e.asString() });
/* 56:   */       }
/* 57:51 */       Mark.say(new Object[] {"\n\n\nInstantiated commonsense rules" });
/* 58:52 */       for (Entity e : inferences.getElements()) {
/* 59:53 */         Mark.say(new Object[] {e.asString() });
/* 60:   */       }
/* 61:55 */       Mark.say(new Object[] {"\n\n\nInstantiated concept patterns" });
/* 62:56 */       for (Entity e : concepts.getElements()) {
/* 63:57 */         Mark.say(new Object[] {e.asString() });
/* 64:   */       }
/* 65:   */     }
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     chrisJones.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */