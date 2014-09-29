/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import text.Html;
/*  8:   */ 
/*  9:   */ public class ActionExpert
/* 10:   */   extends AbstractWiredBox
/* 11:   */ {
/* 12:   */   public ActionExpert()
/* 13:   */   {
/* 14:16 */     setName("Action expert");
/* 15:17 */     Connections.getPorts(this).addSignalProcessor("process");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void process(Object object)
/* 19:   */   {
/* 20:21 */     if (Recognizers.action(object))
/* 21:   */     {
/* 22:22 */       Connections.getPorts(this).transmit("viewer", object);
/* 23:23 */       Connections.getPorts(this).transmit("text", Html.p(Recognizers.theThing(object).asString()));
/* 24:   */     }
/* 25:   */     else
/* 26:   */     {
/* 27:26 */       Connections.getPorts(this).transmit("next", object);
/* 28:   */     }
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.ActionExpert
 * JD-Core Version:    0.7.0.1
 */