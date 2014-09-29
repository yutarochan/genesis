/*  1:   */ package giulianoGiacaglia;
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
/* 14:10 */   public final String OUTPUT_STORY_1 = "output story 1";
/* 15:11 */   public final String OUTPUT_STORY_2 = "output story 2";
/* 16:12 */   public final String OUTPUT_STORY_3 = "output story 3";
/* 17:   */   public static final String STORY_PORT = "story port";
/* 18:   */   public static final String STORY_PORT2 = "story port2";
/* 19:15 */   private int counter = 0;
/* 20:16 */   Sequence story1 = null;
/* 21:17 */   Sequence story2 = null;
/* 22:18 */   Sequence story3 = null;
/* 23:   */   
/* 24:   */   public LocalProcessor()
/* 25:   */   {
/* 26:20 */     setName("Giuliano's story processor");
/* 27:21 */     Connections.getPorts(this).addSignalProcessor("processSignal");
/* 28:22 */     Connections.getPorts(this).addSignalProcessor("story port", "processStory");
/* 29:23 */     Connections.getPorts(this).addSignalProcessor("story port2", "processStory");
/* 30:   */     
/* 31:   */ 
/* 32:   */ 
/* 33:27 */     Connections.getPorts(this).addSignalProcessor("my input port", "processSignal");
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void processStory(Object signal)
/* 37:   */   {
/* 38:32 */     if ((signal instanceof Entity))
/* 39:   */     {
/* 40:33 */       Entity t = (Entity)signal;
/* 41:34 */       Mark.say(new Object[] {"Story: " + this.counter });
/* 42:35 */       if (t.sequenceP())
/* 43:   */       {
/* 44:36 */         Sequence s = (Sequence)t;
/* 45:37 */         Mark.say(new Object[] {"Story received:" });
/* 46:38 */         if (this.counter == 0) {
/* 47:39 */           this.story1 = s;
/* 48:   */         }
/* 49:41 */         if (this.counter == 1) {
/* 50:42 */           this.story2 = s;
/* 51:   */         }
/* 52:44 */         if (this.counter == 2)
/* 53:   */         {
/* 54:45 */           this.story3 = s;
/* 55:46 */           Connections.getPorts(this).transmit("output story 1", this.story1);
/* 56:47 */           Connections.getPorts(this).transmit("output story 2", this.story2);
/* 57:48 */           Connections.getPorts(this).transmit("output story 3", this.story3);
/* 58:   */         }
/* 59:50 */         this.counter += 1;
/* 60:   */       }
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     giulianoGiacaglia.LocalProcessor
 * JD-Core Version:    0.7.0.1
 */