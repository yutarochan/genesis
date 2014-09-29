/*  1:   */ package frames;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Function;
/*  5:   */ import bridge.reps.entities.Relation;
/*  6:   */ import constants.RecognizedRepresentations;
/*  7:   */ import frames.utilities.CAFactory;
/*  8:   */ import groups.Graph;
/*  9:   */ import java.io.PrintStream;
/* 10:   */ 
/* 11:   */ public class CAFrame
/* 12:   */   extends Frame
/* 13:   */ {
/* 14:13 */   public Graph caGraph = new Graph(CAFactory.graphType);
/* 15:15 */   public static String FRAMETYPE = (String)RecognizedRepresentations.CA;
/* 16:   */   
/* 17:   */   public CAFrame() {}
/* 18:   */   
/* 19:   */   public CAFrame(Entity t)
/* 20:   */   {
/* 21:21 */     this();
/* 22:22 */     if (t.isA(FRAMETYPE)) {
/* 23:23 */       this.caGraph = ((Graph)t);
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Graph getCAGraph()
/* 28:   */   {
/* 29:28 */     return this.caGraph;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public Entity getThing()
/* 33:   */   {
/* 34:32 */     return getCAGraph();
/* 35:   */   }
/* 36:   */   
/* 37:   */   public boolean addEvent(String type, Entity e)
/* 38:   */   {
/* 39:36 */     Function event = CAFactory.makeNewEvent(type, e);
/* 40:37 */     if (event != null) {
/* 41:38 */       return this.caGraph.addElt(event);
/* 42:   */     }
/* 43:40 */     return false;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public boolean addEvent(Function e)
/* 47:   */   {
/* 48:44 */     if (!e.isA(CAFactory.eventType))
/* 49:   */     {
/* 50:45 */       System.err.println("Sorry, argument supplied to CAFrame.addEvent was not a valid ca event.");
/* 51:46 */       return false;
/* 52:   */     }
/* 53:48 */     return this.caGraph.addElt(e);
/* 54:   */   }
/* 55:   */   
/* 56:   */   public boolean addRelation(String type, String subtype, Function subject, Function object)
/* 57:   */   {
/* 58:52 */     return this.caGraph.addRel(CAFactory.makeNewLink(type, subtype, subject, object));
/* 59:   */   }
/* 60:   */   
/* 61:   */   public boolean addRelation(String type, Function subject, Relation object)
/* 62:   */   {
/* 63:56 */     if ((type != "gates") && (type != "concurrent") && (type != "motivates"))
/* 64:   */     {
/* 65:57 */       System.err.println("Sorry, " + type + " is not a valid ca relation with relation object.");
/* 66:58 */       return false;
/* 67:   */     }
/* 68:60 */     return this.caGraph.addRel(CAFactory.makeNewLink(type, "", subject, object));
/* 69:   */   }
/* 70:   */   
/* 71:   */   public boolean addRelation(Relation r)
/* 72:   */   {
/* 73:64 */     if (!r.isA(CAFactory.relationType))
/* 74:   */     {
/* 75:65 */       System.err.println("Sorry, argument supplied to CAFrame.addRelaiton was not a valid ca relation.");
/* 76:66 */       return false;
/* 77:   */     }
/* 78:68 */     return this.caGraph.addRel(r);
/* 79:   */   }
/* 80:   */   
/* 81:   */   public String toString()
/* 82:   */   {
/* 83:71 */     return this.caGraph.toString();
/* 84:   */   }
/* 85:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.CAFrame
 * JD-Core Version:    0.7.0.1
 */