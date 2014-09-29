/*  1:   */ package tools;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Bundle;
/*  4:   */ import bridge.reps.entities.Entity;
/*  5:   */ import bridge.reps.entities.Function;
/*  6:   */ import bridge.reps.entities.Relation;
/*  7:   */ import bridge.reps.entities.Sequence;
/*  8:   */ import bridge.reps.entities.Thread;
/*  9:   */ import links.words.BundleGenerator;
/* 10:   */ import start.Start;
/* 11:   */ import utils.Mark;
/* 12:   */ 
/* 13:   */ public class Constructors
/* 14:   */ {
/* 15:   */   public static Entity makeEntity(String x)
/* 16:   */   {
/* 17:23 */     return Start.getStart().makeThing(x);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public static Entity makeRoles()
/* 21:   */   {
/* 22:29 */     Sequence roles = new Sequence("roles");
/* 23:30 */     return roles;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public static Entity makeRoles(Entity object, String relation)
/* 27:   */   {
/* 28:37 */     Sequence roles = new Sequence("roles");
/* 29:38 */     roles.addElement(new Function("object", object));
/* 30:39 */     return roles;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public static Relation makeRoleFrame(Entity subject, String verb)
/* 34:   */   {
/* 35:43 */     Sequence roles = new Sequence("roles");
/* 36:44 */     return new Relation(verb, subject, roles);
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static Relation makeRoleFrame(Entity subject, String relation, Entity object)
/* 40:   */   {
/* 41:48 */     Sequence roles = new Sequence("roles");
/* 42:49 */     roles.addElement(new Function("object", object));
/* 43:50 */     Relation r = new Relation(relation, subject, roles);
/* 44:51 */     Bundle bundle = BundleGenerator.getBundle(relation);
/* 45:52 */     r.setBundle(bundle);
/* 46:53 */     return r;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public static Relation addRole(Relation frame, String preposition, Entity roleFiller)
/* 50:   */   {
/* 51:57 */     Entity roles = frame.getObject();
/* 52:58 */     if ((roles != null) && (roles.sequenceP("roles"))) {
/* 53:59 */       roles.addElement(new Function(preposition, roleFiller));
/* 54:   */     } else {
/* 55:62 */       Mark.err(new Object[] {"Problem in Constructors.addRole, object", roles, "is not a role sequence" });
/* 56:   */     }
/* 57:64 */     return frame;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public static Function constructTransition(String type, Entity property)
/* 61:   */   {
/* 62:75 */     Thread thread = new Thread();
/* 63:76 */     thread.add("action");
/* 64:77 */     Function result = new Function(property);
/* 65:78 */     result.replacePrimedThread(thread);
/* 66:79 */     result.addType("transition");
/* 67:80 */     result.addType(type);
/* 68:81 */     return result;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public static Function constructAppearTransition(Entity property)
/* 72:   */   {
/* 73:85 */     return constructTransition("appear", property);
/* 74:   */   }
/* 75:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tools.Constructors
 * JD-Core Version:    0.7.0.1
 */