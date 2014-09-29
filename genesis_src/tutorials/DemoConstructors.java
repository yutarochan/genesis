/*  1:   */ package tutorials;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import bridge.reps.entities.Relation;
/*  6:   */ import bridge.reps.entities.Sequence;
/*  7:   */ import tools.Constructors;
/*  8:   */ import tools.JFactory;
/*  9:   */ import utils.Mark;
/* 10:   */ 
/* 11:   */ public class DemoConstructors
/* 12:   */   extends Constructors
/* 13:   */ {
/* 14:   */   public static void main(String[] ignore)
/* 15:   */   {
/* 16:16 */     Entity x = new Entity("ball");
/* 17:17 */     x.addType("baseball");
/* 18:18 */     Mark.say(new Object[] {x.toXML() });
/* 19:19 */     Mark.say(new Object[] {x });
/* 20:20 */     Mark.say(new Object[] {Boolean.valueOf(x.isA("ball")) });
/* 21:21 */     Mark.say(new Object[] {Boolean.valueOf(x.isA("person")) });
/* 22:   */     
/* 23:   */ 
/* 24:24 */     Entity table = new Entity("table");
/* 25:25 */     Entity door = new Entity("door");
/* 26:26 */     Function f = new Function("top", table);
/* 27:27 */     Mark.say(new Object[] {f });
/* 28:28 */     Mark.say(new Object[] {f.getSubject() });
/* 29:   */     
/* 30:   */ 
/* 31:31 */     Entity d = new Entity("door");
/* 32:32 */     Entity w = new Entity("window");
/* 33:33 */     Relation r = new Relation("between", d, w);
/* 34:34 */     Mark.say(new Object[] {r });
/* 35:35 */     Mark.say(new Object[] {r.getSubject() });
/* 36:36 */     Mark.say(new Object[] {r.getObject() });
/* 37:   */     
/* 38:   */ 
/* 39:39 */     Sequence roles = new Sequence("roles");
/* 40:40 */     roles.addElement(new Function("object", new Entity("Peter")));
/* 41:41 */     roles.addElement(new Function("with", new Entity("knife")));
/* 42:42 */     Relation k = new Relation("kill", new Entity("John"), roles);
/* 43:43 */     Mark.say(new Object[] {roles });
/* 44:44 */     Mark.say(new Object[] {k });
/* 45:   */     
/* 46:   */ 
/* 47:47 */     Entity John = new Entity("John");
/* 48:48 */     Entity Peter = new Entity("Peter");
/* 49:49 */     Entity bird = new Entity("bird");
/* 50:50 */     Entity knife = new Entity("knife");
/* 51:   */     
/* 52:   */ 
/* 53:53 */     Relation rf = Constructors.makeRoleFrame(John, "kill");
/* 54:54 */     Mark.say(new Object[] {rf });
/* 55:55 */     rf = Constructors.makeRoleFrame(John, "kill", Peter);
/* 56:56 */     Mark.say(new Object[] {rf });
/* 57:57 */     rf = Constructors.addRole(Constructors.makeRoleFrame(John, "kill", Peter), "with", knife);
/* 58:58 */     Mark.say(new Object[] {rf });
/* 59:   */     
/* 60:   */ 
/* 61:61 */     Entity tree = new Entity("tree");
/* 62:   */     
/* 63:63 */     Function place = JFactory.createPlace("top", tree);
/* 64:   */     
/* 65:65 */     Function pathElement = JFactory.createPathElement("from", place);
/* 66:   */     
/* 67:67 */     Sequence path = JFactory.createPath();
/* 68:   */     
/* 69:69 */     path.addElement(pathElement);
/* 70:   */     
/* 71:71 */     Entity trajectory = JFactory.createTrajectory(bird, "fly", path);
/* 72:   */     
/* 73:73 */     Mark.say(new Object[] {"Trajectory role frame: " + trajectory });
/* 74:   */     
/* 75:75 */     Function origin = JFactory.createPathElement("to", JFactory.createPlace("at", new Entity("rock")));
/* 76:   */     
/* 77:77 */     JFactory.addPathElement(path, origin);
/* 78:   */     
/* 79:79 */     Mark.say(new Object[] {"Amended trajectory role frame: " + trajectory });
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tutorials.DemoConstructors
 * JD-Core Version:    0.7.0.1
 */