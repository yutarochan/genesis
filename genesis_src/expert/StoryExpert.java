/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Function;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import utils.Mark;
/*  8:   */ 
/*  9:   */ public class StoryExpert
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public StoryExpert()
/* 13:   */   {
/* 14:16 */     Connections.getPorts(this).addSignalProcessor("answerQuestion");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void answerQuestion(Object o)
/* 18:   */   {
/* 19:20 */     if ((o instanceof Function))
/* 20:   */     {
/* 21:21 */       Function d = (Function)o;
/* 22:22 */       if (d.isA("why")) {
/* 23:23 */         Mark.say(new Object[] {"Received question", d.asString() });
/* 24:   */       }
/* 25:   */     }
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.StoryExpert
 * JD-Core Version:    0.7.0.1
 */