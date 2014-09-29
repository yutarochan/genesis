/*  1:   */ package kevinWhite;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ import connections.WiredViewer;
/*  7:   */ import java.awt.Color;
/*  8:   */ import javax.swing.BorderFactory;
/*  9:   */ import javax.swing.GroupLayout;
/* 10:   */ import javax.swing.GroupLayout.ParallelGroup;
/* 11:   */ import javax.swing.GroupLayout.SequentialGroup;
/* 12:   */ 
/* 13:   */ public class JobPanel
/* 14:   */   extends WiredViewer
/* 15:   */ {
/* 16:   */   private GroupLayout goalLayout;
/* 17:   */   private GroupLayout.SequentialGroup goalHoriz;
/* 18:   */   private GroupLayout.ParallelGroup goalVert;
/* 19:   */   private GenesisPanel subjectPanel;
/* 20:   */   private GenesisPanel partPanel;
/* 21:   */   private GenesisPanel linkPanel;
/* 22:   */   
/* 23:   */   public JobPanel()
/* 24:   */   {
/* 25:32 */     setBackground(Color.WHITE);
/* 26:33 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/* 27:   */     
/* 28:   */ 
/* 29:36 */     this.subjectPanel = new GenesisPanel("");
/* 30:37 */     this.linkPanel = new GenesisPanel("job", false);
/* 31:38 */     this.partPanel = new GenesisPanel("");
/* 32:   */     
/* 33:   */ 
/* 34:   */ 
/* 35:   */ 
/* 36:   */ 
/* 37:44 */     this.linkPanel.setVisible(false);
/* 38:   */     
/* 39:   */ 
/* 40:47 */     this.goalLayout = new GroupLayout(this);
/* 41:48 */     setLayout(this.goalLayout);
/* 42:   */     
/* 43:   */ 
/* 44:51 */     this.goalHoriz = this.goalLayout.createSequentialGroup();
/* 45:   */     
/* 46:53 */     this.goalVert = this.goalLayout.createParallelGroup();
/* 47:   */     
/* 48:   */ 
/* 49:56 */     this.goalHoriz.addComponent(this.subjectPanel);
/* 50:57 */     this.goalVert.addComponent(this.subjectPanel);
/* 51:   */     
/* 52:59 */     this.goalHoriz.addComponent(this.linkPanel);
/* 53:60 */     this.goalVert.addComponent(this.linkPanel);
/* 54:   */     
/* 55:62 */     this.goalHoriz.addComponent(this.partPanel);
/* 56:63 */     this.goalVert.addComponent(this.partPanel);
/* 57:   */     
/* 58:65 */     this.goalLayout.setHorizontalGroup(this.goalHoriz);
/* 59:66 */     this.goalLayout.setVerticalGroup(this.goalVert);
/* 60:67 */     Connections.getPorts(this).addSignalProcessor("process");
/* 61:   */   }
/* 62:   */   
/* 63:   */   public void view(Object object)
/* 64:   */   {
/* 65:73 */     if ((object instanceof Entity))
/* 66:   */     {
/* 67:74 */       Entity t = (Entity)object;
/* 68:76 */       if (t.getFeatures() != null) {
/* 69:77 */         this.linkPanel.setDesirability(false);
/* 70:   */       } else {
/* 71:80 */         this.linkPanel.setDesirability(true);
/* 72:   */       }
/* 73:82 */       this.partPanel.displayText(t.getObject().getType());
/* 74:83 */       this.subjectPanel.displayText(t.getSubject().getType());
/* 75:84 */       this.linkPanel.setVisible(true);
/* 76:   */     }
/* 77:   */   }
/* 78:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.JobPanel
 * JD-Core Version:    0.7.0.1
 */