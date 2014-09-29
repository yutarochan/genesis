/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Function;
/*  6:   */ import bridge.reps.entities.Relation;
/*  7:   */ import connections.AbstractWiredBox;
/*  8:   */ import connections.Connections;
/*  9:   */ import connections.Ports;
/* 10:   */ import tools.Getters;
/* 11:   */ import utils.Mark;
/* 12:   */ 
/* 13:   */ public class CommandExpert
/* 14:   */   extends AbstractWiredBox
/* 15:   */ {
/* 16:   */   public static final String IMAGINE = "imagine";
/* 17:   */   public static final String PERSUADE = "persuade";
/* 18:   */   
/* 19:   */   public CommandExpert()
/* 20:   */   {
/* 21:22 */     setName("Command expert");
/* 22:23 */     Connections.getPorts(this).addSignalProcessor("process");
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void process(Object object)
/* 26:   */   {
/* 27:27 */     if (!(object instanceof Entity)) {
/* 28:28 */       return;
/* 29:   */     }
/* 30:30 */     Entity entity = (Entity)object;
/* 31:31 */     if (entity.functionP("imagine"))
/* 32:   */     {
/* 33:32 */       Function d = (Function)entity;
/* 34:33 */       Connections.getPorts(this).transmit("viewer", d);
/* 35:   */       
/* 36:35 */       Connections.getPorts(this).transmit("imagine", d);
/* 37:   */     }
/* 38:37 */     else if (entity.relationP("imagine"))
/* 39:   */     {
/* 40:38 */       Relation r = (Relation)entity;
/* 41:39 */       Connections.getPorts(this).transmit("viewer", r);
/* 42:40 */       Mark.say(new Object[] {"Transmitting relation", r.asString() });
/* 43:41 */       Connections.getPorts(this).transmit("imagine", r);
/* 44:   */     }
/* 45:43 */     else if (isAMakeCommand(entity))
/* 46:   */     {
/* 47:44 */       Mark.say(new Object[] {"Transmitting desired property", getDesiredProperty(entity) });
/* 48:45 */       Connections.getPorts(this).transmit("persuade", new BetterSignal(new Object[] { "persuade", getDesiredProperty(entity) }));
/* 49:   */     }
/* 50:   */     else
/* 51:   */     {
/* 52:48 */       Connections.getPorts(this).transmit("next", entity);
/* 53:   */     }
/* 54:   */   }
/* 55:   */   
/* 56:   */   private Object getDesiredProperty(Entity entity)
/* 57:   */   {
/* 58:54 */     if (isAMakeCommand(entity)) {
/* 59:55 */       return Getters.getObject(entity);
/* 60:   */     }
/* 61:57 */     return null;
/* 62:   */   }
/* 63:   */   
/* 64:   */   private boolean isAMakeCommand(Entity entity)
/* 65:   */   {
/* 66:61 */     if ((entity.isA("make")) && 
/* 67:62 */       (entity.getSubject().isA("you")) && 
/* 68:63 */       (Getters.getObject(entity).isA("property"))) {
/* 69:64 */       return true;
/* 70:   */     }
/* 71:68 */     return false;
/* 72:   */   }
/* 73:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.CommandExpert
 * JD-Core Version:    0.7.0.1
 */