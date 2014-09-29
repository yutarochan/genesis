/*  1:   */ package benjaminShaibu;
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
/* 14:15 */   public final String MY_INPUT_PORT = "my input port";
/* 15:17 */   public final String MY_OUTPUT_PORT = "my output port";
/* 16:   */   
/* 17:   */   public LocalProcessor()
/* 18:   */   {
/* 19:24 */     setName("Benjamin's local story processor");
/* 20:   */     
/* 21:26 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/* 22:   */     
/* 23:28 */     Connections.getPorts(this).addSignalProcessor("complete story analysis port", "processSignal");
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void processSignal(Object signal)
/* 27:   */   {
/* 28:38 */     if ((signal instanceof BetterSignal))
/* 29:   */     {
/* 30:39 */       Mark.say(new Object[] {"Running Benjamin's signal processor" });
/* 31:   */       
/* 32:41 */       BetterSignal s = (BetterSignal)signal;
/* 33:42 */       Sequence story = (Sequence)s.get(0, Sequence.class);
/* 34:43 */       Sequence explicitElements = (Sequence)s.get(1, Sequence.class);
/* 35:44 */       Sequence inferences = (Sequence)s.get(2, Sequence.class);
/* 36:45 */       Sequence concepts = (Sequence)s.get(3, Sequence.class);
/* 37:   */       
/* 38:47 */       Mark.say(new Object[] {"\n\n\nStory elements" });
/* 39:48 */       for (Entity e : story.getElements()) {
/* 40:49 */         Mark.say(new Object[] {e.asString() });
/* 41:   */       }
/* 42:51 */       Mark.say(new Object[] {"\n\n\nExplicit story elements" });
/* 43:52 */       for (Entity e : explicitElements.getElements()) {
/* 44:53 */         Mark.say(new Object[] {e.asString() });
/* 45:   */       }
/* 46:55 */       Mark.say(new Object[] {"\n\n\nInstantiated commonsense rules" });
/* 47:56 */       for (Entity e : inferences.getElements()) {
/* 48:57 */         Mark.say(new Object[] {e.asString() });
/* 49:   */       }
/* 50:59 */       Mark.say(new Object[] {"\n\n\nInstantiated concept patterns" });
/* 51:60 */       for (Entity e : concepts.getElements()) {
/* 52:61 */         Mark.say(new Object[] {e.asString() });
/* 53:   */       }
/* 54:   */     }
/* 55:   */   }
/* 56:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     benjaminShaibu.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */