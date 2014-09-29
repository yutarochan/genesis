/*  1:   */ package newStudent;
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
/* 19:24 */     setName("Local story processor");
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
/* 30:40 */       BetterSignal s = (BetterSignal)signal;
/* 31:41 */       Sequence story = (Sequence)s.get(0, Sequence.class);
/* 32:42 */       Sequence explicitElements = (Sequence)s.get(1, Sequence.class);
/* 33:43 */       Sequence inferences = (Sequence)s.get(2, Sequence.class);
/* 34:44 */       Sequence concepts = (Sequence)s.get(3, Sequence.class);
/* 35:   */       
/* 36:46 */       Mark.say(new Object[] {"\n\n\nStory elements" });
/* 37:47 */       for (Entity e : story.getElements()) {
/* 38:48 */         Mark.say(new Object[] {e.asString() });
/* 39:   */       }
/* 40:50 */       Mark.say(new Object[] {"\n\n\nExplicit story elements" });
/* 41:51 */       for (Entity e : explicitElements.getElements()) {
/* 42:52 */         Mark.say(new Object[] {e.asString() });
/* 43:   */       }
/* 44:54 */       Mark.say(new Object[] {"\n\n\nInstantiated commonsense rules" });
/* 45:55 */       for (Entity e : inferences.getElements()) {
/* 46:56 */         Mark.say(new Object[] {e.asString() });
/* 47:   */       }
/* 48:58 */       Mark.say(new Object[] {"\n\n\nInstantiated concept patterns" });
/* 49:59 */       for (Entity e : concepts.getElements()) {
/* 50:60 */         Mark.say(new Object[] {e.asString() });
/* 51:   */       }
/* 52:   */     }
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     newStudent.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */