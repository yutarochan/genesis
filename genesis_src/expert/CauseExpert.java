/*  1:   */ package expert;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import bridge.reps.entities.Relation;
/*  5:   */ import bridge.reps.entities.Sequence;
/*  6:   */ import connections.AbstractWiredBox;
/*  7:   */ import connections.Connections;
/*  8:   */ import connections.Ports;
/*  9:   */ import parameters.Switch;
/* 10:   */ import persistence.JCheckBoxWithMemory;
/* 11:   */ 
/* 12:   */ public class CauseExpert
/* 13:   */   extends AbstractWiredBox
/* 14:   */ {
/* 15:   */   public static final String FROM_STORY_PORT = "from story port";
/* 16:   */   public static final String RULE = "rule";
/* 17:   */   public static final String RULES = "rules";
/* 18:   */   public static final String CLEAR = "clear memory";
/* 19:23 */   private Sequence rules = new Sequence();
/* 20:   */   
/* 21:   */   public CauseExpert()
/* 22:   */   {
/* 23:26 */     setName("Cause expert");
/* 24:27 */     Connections.getPorts(this).addSignalProcessor("processFromParser");
/* 25:28 */     Connections.getPorts(this).addSignalProcessor("from story port", "processFromStory");
/* 26:29 */     Connections.getPorts(this).addSignalProcessor("clear memory", "clearRuleMemory");
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void processFromParser(Object object)
/* 30:   */   {
/* 31:33 */     if (!(object instanceof Entity)) {
/* 32:34 */       return;
/* 33:   */     }
/* 34:36 */     Entity t = (Entity)object;
/* 35:37 */     processCausalRelation(t);
/* 36:38 */     if (!Switch.showBackgroundElements.isSelected()) {
/* 37:39 */       sendDownstream(t);
/* 38:   */     }
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void processFromStory(Object object)
/* 42:   */   {
/* 43:44 */     if (!(object instanceof Entity)) {
/* 44:45 */       return;
/* 45:   */     }
/* 46:47 */     Entity t = (Entity)object;
/* 47:48 */     processCausalRelation(t);
/* 48:49 */     sendDownstream(t);
/* 49:   */   }
/* 50:   */   
/* 51:   */   private boolean processCausalRelation(Entity t)
/* 52:   */   {
/* 53:53 */     if ((t.isAPrimed("cause")) && ((t instanceof Relation)))
/* 54:   */     {
/* 55:54 */       Relation r = (Relation)t;
/* 56:55 */       record(r);
/* 57:56 */       return true;
/* 58:   */     }
/* 59:58 */     return false;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void sendDownstream(Object object)
/* 63:   */   {
/* 64:62 */     if (!(object instanceof Entity)) {
/* 65:63 */       return;
/* 66:   */     }
/* 67:65 */     Entity t = (Entity)object;
/* 68:66 */     if ((t.isAPrimed("cause")) && ((t instanceof Relation)))
/* 69:   */     {
/* 70:67 */       Relation r = (Relation)t;
/* 71:68 */       Entity subject = r.getSubject();
/* 72:69 */       if ((subject.sequenceP()) && (subject.isAPrimed("conjuction"))) {
/* 73:70 */         for (Entity element : ((Sequence)subject).getElements()) {
/* 74:71 */           Connections.getPorts(this).transmit("loop", element);
/* 75:   */         }
/* 76:   */       }
/* 77:74 */       Connections.getPorts(this).transmit("loop", r.getObject());
/* 78:75 */       Connections.getPorts(this).transmit("viewer", t);
/* 79:   */     }
/* 80:   */     else
/* 81:   */     {
/* 82:78 */       Connections.getPorts(this).transmit("next", t);
/* 83:   */     }
/* 84:   */   }
/* 85:   */   
/* 86:   */   public void record(Relation rule)
/* 87:   */   {
/* 88:83 */     Connections.getPorts(this).transmit("rule", rule);
/* 89:84 */     this.rules.addElement(rule);
/* 90:85 */     Connections.getPorts(this).transmit("rules", this.rules);
/* 91:   */   }
/* 92:   */   
/* 93:   */   public void clearRuleMemory(Object o)
/* 94:   */   {
/* 95:89 */     clearRuleMemory();
/* 96:   */   }
/* 97:   */   
/* 98:   */   public void clearRuleMemory()
/* 99:   */   {
/* :0:93 */     this.rules.clearElements();
/* :1:   */   }
/* :2:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.CauseExpert
 * JD-Core Version:    0.7.0.1
 */