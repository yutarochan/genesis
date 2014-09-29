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
/* 13:   */ import tools.Getters;
/* 14:   */ 
/* 15:   */ public class PossessionPanel
/* 16:   */   extends WiredViewer
/* 17:   */ {
/* 18:   */   private GroupLayout goalLayout;
/* 19:   */   private GroupLayout.SequentialGroup goalHoriz;
/* 20:   */   private GroupLayout.ParallelGroup goalVert;
/* 21:   */   private GenesisPanel subjectPanel;
/* 22:   */   private GenesisPanel possessionPanel;
/* 23:   */   private GenesisPanel linkPanel;
/* 24:   */   
/* 25:   */   public PossessionPanel()
/* 26:   */   {
/* 27:30 */     setBackground(Color.WHITE);
/* 28:31 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/* 29:   */     
/* 30:   */ 
/* 31:34 */     this.subjectPanel = new GenesisPanel("");
/* 32:35 */     this.linkPanel = new GenesisPanel("possession", false);
/* 33:36 */     this.linkPanel.setBackground(Color.BLACK);
/* 34:37 */     this.possessionPanel = new GenesisPanel("");
/* 35:   */     
/* 36:39 */     this.linkPanel.setVisible(false);
/* 37:   */     
/* 38:   */ 
/* 39:42 */     this.goalLayout = new GroupLayout(this);
/* 40:43 */     setLayout(this.goalLayout);
/* 41:   */     
/* 42:   */ 
/* 43:46 */     this.goalHoriz = this.goalLayout.createSequentialGroup();
/* 44:   */     
/* 45:48 */     this.goalVert = this.goalLayout.createParallelGroup();
/* 46:   */     
/* 47:   */ 
/* 48:51 */     this.goalHoriz.addComponent(this.subjectPanel);
/* 49:52 */     this.goalVert.addComponent(this.subjectPanel);
/* 50:   */     
/* 51:54 */     this.goalHoriz.addComponent(this.linkPanel);
/* 52:55 */     this.goalVert.addComponent(this.linkPanel);
/* 53:   */     
/* 54:57 */     this.goalHoriz.addComponent(this.possessionPanel);
/* 55:58 */     this.goalVert.addComponent(this.possessionPanel);
/* 56:   */     
/* 57:60 */     this.goalLayout.setHorizontalGroup(this.goalHoriz);
/* 58:61 */     this.goalLayout.setVerticalGroup(this.goalVert);
/* 59:62 */     Connections.getPorts(this).addSignalProcessor("process");
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void view(Object object)
/* 63:   */   {
/* 64:68 */     if ((object instanceof Entity))
/* 65:   */     {
/* 66:70 */       Entity t = (Entity)object;
/* 67:   */       
/* 68:   */ 
/* 69:73 */       this.subjectPanel.displayText(t.getSubject().getType());
/* 70:74 */       if (t.getFeatures().contains("not")) {
/* 71:75 */         this.linkPanel.setDesirability(false);
/* 72:   */       } else {
/* 73:78 */         this.linkPanel.setDesirability(true);
/* 74:   */       }
/* 75:80 */       this.linkPanel.setVisible(true);
/* 76:81 */       this.possessionPanel.displayText(Getters.getObject(t).getType());
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.PossessionPanel
 * JD-Core Version:    0.7.0.1
 */