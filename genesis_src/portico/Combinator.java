/*  1:   */ package portico;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import connections.AbstractWiredBox;
/*  7:   */ import connections.Connections;
/*  8:   */ import connections.Ports;
/*  9:   */ import java.io.PrintStream;
/* 10:   */ 
/* 11:   */ public class Combinator
/* 12:   */   extends AbstractWiredBox
/* 13:   */ {
/* 14:   */   public static final String LEFT = "left";
/* 15:   */   public static final String RIGHT = "right";
/* 16:   */   public static final String COMBINATOR = "combinator";
/* 17:   */   private Entity left;
/* 18:   */   private Entity right;
/* 19:   */   
/* 20:   */   public Combinator()
/* 21:   */   {
/* 22:26 */     Connections.getPorts(this).addSignalProcessor("left", "processLeft");
/* 23:27 */     Connections.getPorts(this).addSignalProcessor("right", "processRight");
/* 24:28 */     Connections.getPorts(this).addSignalProcessor("combinator", "processCombinator");
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void processLeft(Object object)
/* 28:   */   {
/* 29:32 */     if (!(object instanceof Entity)) {
/* 30:33 */       return;
/* 31:   */     }
/* 32:35 */     this.left = ((Entity)object);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void processRight(Object object)
/* 36:   */   {
/* 37:39 */     if (!(object instanceof Entity)) {
/* 38:40 */       return;
/* 39:   */     }
/* 40:42 */     this.right = ((Entity)object);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void processCombinator(Object object)
/* 44:   */   {
/* 45:48 */     if (!(object instanceof String)) {
/* 46:49 */       return;
/* 47:   */     }
/* 48:51 */     String combinator = (String)object;
/* 49:52 */     if (("cause".equals(combinator)) || ("before".equals(combinator)) || ("while".equals(combinator)) || 
/* 50:53 */       ("after".equals(combinator)))
/* 51:   */     {
/* 52:54 */       transmitRelation(combinator);
/* 53:   */     }
/* 54:56 */     else if ("none".equals(combinator))
/* 55:   */     {
/* 56:57 */       Entity result = this.left;
/* 57:58 */       this.left = (this.right = null);
/* 58:59 */       Connections.getPorts(this).transmit(embed(result));
/* 59:   */     }
/* 60:61 */     System.out.println("Link parser transmitting");
/* 61:   */   }
/* 62:   */   
/* 63:   */   private void transmitRelation(String relation)
/* 64:   */   {
/* 65:65 */     Relation result = null;
/* 66:66 */     if (("before".equals(relation)) || ("while".equals(relation)) || ("after".equals(relation)))
/* 67:   */     {
/* 68:67 */       result = new Relation("action", this.left, this.right);
/* 69:68 */       result.addType("time-relation");
/* 70:   */     }
/* 71:70 */     else if ("cause".equals(relation))
/* 72:   */     {
/* 73:71 */       result = new Relation("action", this.right, this.left);
/* 74:   */     }
/* 75:73 */     this.left = (this.right = null);
/* 76:74 */     if (result != null)
/* 77:   */     {
/* 78:75 */       result.addType(relation);
/* 79:76 */       Connections.getPorts(this).transmit(embed(result));
/* 80:   */     }
/* 81:   */   }
/* 82:   */   
/* 83:   */   private Entity embed(Entity t)
/* 84:   */   {
/* 85:81 */     Sequence result = new Sequence("semantic-interpretation");
/* 86:82 */     result.addElement(t);
/* 87:83 */     return result;
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     portico.Combinator
 * JD-Core Version:    0.7.0.1
 */