/*  1:   */ package hibaAwad;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import models.MentalModel;
/*  8:   */ import utils.Mark;
/*  9:   */ 
/* 10:   */ public class MagicBox
/* 11:   */   extends AbstractWiredBox
/* 12:   */ {
/* 13:   */   MentalModel mentalModel;
/* 14:   */   
/* 15:   */   public MagicBox(MentalModel mm)
/* 16:   */   {
/* 17:18 */     this.mentalModel = mm;
/* 18:19 */     Connections.getPorts(this).addSignalProcessor("process");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void process(Object o)
/* 22:   */   {
/* 23:23 */     if ((o instanceof Entity))
/* 24:   */     {
/* 25:24 */       Entity t = (Entity)o;
/* 26:25 */       Mark.say(new Object[] {"MagicBox received", t.asString() });
/* 27:   */       
/* 28:27 */       String traitName = t.getObject().getType();
/* 29:   */       
/* 30:29 */       Mark.say(new Object[] {"Personality trait is", traitName });
/* 31:   */       
/* 32:31 */       MentalModel traitModel = this.mentalModel.getLocalMentalModel(traitName);
/* 33:   */       
/* 34:33 */       Mark.say(new Object[] {"Someone is", traitName });
/* 35:35 */       if (traitModel != null) {
/* 36:36 */         Mark.say(new Object[] {"Found mental model for", traitName });
/* 37:   */       } else {
/* 38:39 */         Mark.say(new Object[] {"Did not find mental model for", traitName });
/* 39:   */       }
/* 40:   */     }
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     hibaAwad.MagicBox
 * JD-Core Version:    0.7.0.1
 */