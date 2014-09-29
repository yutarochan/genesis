/*  1:   */ package kevinWhite;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredViewer;
/*  7:   */ import java.awt.Color;
/*  8:   */ import java.util.ArrayList;
/*  9:   */ import javax.swing.BorderFactory;
/* 10:   */ import javax.swing.GroupLayout;
/* 11:   */ import javax.swing.GroupLayout.ParallelGroup;
/* 12:   */ import javax.swing.GroupLayout.SequentialGroup;
/* 13:   */ 
/* 14:   */ public class GoalPanel
/* 15:   */   extends WiredViewer
/* 16:   */ {
/* 17:   */   private GroupLayout goalLayout;
/* 18:   */   private GroupLayout.SequentialGroup goalHoriz;
/* 19:   */   private GroupLayout.ParallelGroup goalVert;
/* 20:   */   private GenesisPanel subjectPanel;
/* 21:   */   private GenesisPanel objectivePanel;
/* 22:   */   private GenesisPanel linkPanel;
/* 23:   */   
/* 24:   */   public GoalPanel()
/* 25:   */   {
/* 26:35 */     setBackground(Color.WHITE);
/* 27:36 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/* 28:   */     
/* 29:   */ 
/* 30:39 */     this.subjectPanel = new GenesisPanel("");
/* 31:40 */     this.linkPanel = new GenesisPanel("goal", false);
/* 32:41 */     this.objectivePanel = new GenesisPanel("");
/* 33:   */     
/* 34:43 */     this.linkPanel.setVisible(false);
/* 35:   */     
/* 36:   */ 
/* 37:46 */     this.goalLayout = new GroupLayout(this);
/* 38:47 */     setLayout(this.goalLayout);
/* 39:   */     
/* 40:   */ 
/* 41:50 */     this.goalHoriz = this.goalLayout.createSequentialGroup();
/* 42:   */     
/* 43:52 */     this.goalVert = this.goalLayout.createParallelGroup();
/* 44:   */     
/* 45:   */ 
/* 46:55 */     this.goalHoriz.addComponent(this.subjectPanel);
/* 47:56 */     this.goalVert.addComponent(this.subjectPanel);
/* 48:   */     
/* 49:58 */     this.goalHoriz.addComponent(this.linkPanel);
/* 50:59 */     this.goalVert.addComponent(this.linkPanel);
/* 51:   */     
/* 52:61 */     this.goalHoriz.addComponent(this.objectivePanel);
/* 53:62 */     this.goalVert.addComponent(this.objectivePanel);
/* 54:   */     
/* 55:64 */     this.goalLayout.setHorizontalGroup(this.goalHoriz);
/* 56:65 */     this.goalLayout.setVerticalGroup(this.goalVert);
/* 57:66 */     Connections.getPorts(this).addSignalProcessor("process");
/* 58:   */   }
/* 59:   */   
/* 60:   */   private Entity getObjectRole(Entity roles)
/* 61:   */   {
/* 62:70 */     for (Entity t : roles.getElements()) {
/* 63:71 */       if (t.isA("object")) {
/* 64:72 */         return t;
/* 65:   */       }
/* 66:   */     }
/* 67:75 */     return null;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public void view(Object object)
/* 71:   */   {
/* 72:81 */     if ((object instanceof Entity))
/* 73:   */     {
/* 74:82 */       Entity t = (Entity)object;
/* 75:83 */       this.linkPanel.setDesirability(!t.getFeatures().contains("not"));
/* 76:84 */       this.subjectPanel.displayText(t.getSubject().getType());
/* 77:85 */       Entity roles = t.getObject();
/* 78:86 */       Entity objectRole = getObjectRole(roles);
/* 79:88 */       if (objectRole != null)
/* 80:   */       {
/* 81:89 */         Entity action = objectRole.getSubject();
/* 82:90 */         String name = action.getType();
/* 83:91 */         this.objectivePanel.displayText(name);
/* 84:92 */         this.linkPanel.setVisible(true);
/* 85:   */       }
/* 86:   */     }
/* 87:   */   }
/* 88:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.GoalPanel
 * JD-Core Version:    0.7.0.1
 */