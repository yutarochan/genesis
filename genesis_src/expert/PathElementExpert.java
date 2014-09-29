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
/* 11:   */ public class PathElementExpert
/* 12:   */   extends AbstractWiredBox
/* 13:   */ {
/* 14:   */   public PathElementExpert()
/* 15:   */   {
/* 16:17 */     setName("Path element expert");
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
/* 31:   */ 
/* 32:   */ 
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:40 */       new Object[] { Boolean.valueOf(false), "Entered path element expert", object });
/* 41:23 */     if (!(object instanceof Entity)) {
/* 42:24 */       return;
/* 43:   */     }
/* 44:26 */     Entity t = (Entity)object;
/* 45:27 */     if (t.functionP())
/* 46:   */     {
/* 47:28 */       Function d = (Function)t;
/* 48:29 */       if (d.isA(NewRuleSet.pathPrepositions))
/* 49:   */       {
/* 50:30 */         Connections.getPorts(this).transmit("viewer", d);
/* 51:31 */         if ((d.getSubject().functionP()) && (d.getSubject().isAPrimed(NewRuleSet.placePrepositions))) {
/* 52:32 */           Connections.getPorts(this).transmit("path", d.getSubject());
/* 53:   */         }
/* 54:   */       }
/* 55:   */     }
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.PathElementExpert
 * JD-Core Version:    0.7.0.1
 */