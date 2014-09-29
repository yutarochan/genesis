/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import connections.AbstractWiredBox;
/*  6:   */ import connections.Connections;
/*  7:   */ import connections.Ports;
/*  8:   */ import translator.NewRuleSet;
/*  9:   */ import utils.Mark;
/* 10:   */ 
/* 11:   */ public class PlaceExpert
/* 12:   */   extends AbstractWiredBox
/* 13:   */ {
/* 14:   */   public PlaceExpert()
/* 15:   */   {
/* 16:17 */     setName("Place expert");
/* 17:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void process(Object object)
/* 21:   */   {
/* 22:22 */     Mark.say(
/* 23:   */     
/* 24:   */ 
/* 25:   */ 
/* 26:   */ 
/* 27:   */ 
/* 28:   */ 
/* 29:   */ 
/* 30:   */ 
/* 31:31 */       new Object[] { Boolean.valueOf(false), "Entering PlaceExpert" });
/* 32:23 */     if (!(object instanceof Entity)) {
/* 33:24 */       return;
/* 34:   */     }
/* 35:26 */     Entity t = (Entity)object;
/* 36:27 */     if ((t.functionP()) && (t.isAPrimed(NewRuleSet.placePrepositions)))
/* 37:   */     {
/* 38:28 */       Function d = (Function)t;
/* 39:29 */       Connections.getPorts(this).transmit("viewer", d);
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.PlaceExpert
 * JD-Core Version:    0.7.0.1
 */