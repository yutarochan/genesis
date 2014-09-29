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
/* 15:   */ public class PartPanel
/* 16:   */   extends WiredViewer
/* 17:   */ {
/* 18:   */   private GroupLayout goalLayout;
/* 19:   */   private GroupLayout.SequentialGroup goalHoriz;
/* 20:   */   private GroupLayout.ParallelGroup goalVert;
/* 21:   */   private GenesisPanel subjectPanel;
/* 22:   */   private GenesisPanel partPanel;
/* 23:   */   private GenesisPanel linkPanel;
/* 24:   */   
/* 25:   */   public PartPanel()
/* 26:   */   {
/* 27:31 */     setBackground(Color.WHITE);
/* 28:32 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/* 29:   */     
/* 30:   */ 
/* 31:35 */     this.subjectPanel = new GenesisPanel("");
/* 32:36 */     this.linkPanel = new GenesisPanel("part", false);
/* 33:37 */     this.partPanel = new GenesisPanel("");
/* 34:   */     
/* 35:   */ 
/* 36:   */ 
/* 37:   */ 
/* 38:   */ 
/* 39:43 */     this.linkPanel.setVisible(false);
/* 40:   */     
/* 41:   */ 
/* 42:46 */     this.goalLayout = new GroupLayout(this);
/* 43:47 */     setLayout(this.goalLayout);
/* 44:   */     
/* 45:   */ 
/* 46:50 */     this.goalHoriz = this.goalLayout.createSequentialGroup();
/* 47:   */     
/* 48:52 */     this.goalVert = this.goalLayout.createParallelGroup();
/* 49:   */     
/* 50:   */ 
/* 51:55 */     this.goalHoriz.addComponent(this.subjectPanel);
/* 52:56 */     this.goalVert.addComponent(this.subjectPanel);
/* 53:   */     
/* 54:58 */     this.goalHoriz.addComponent(this.linkPanel);
/* 55:59 */     this.goalVert.addComponent(this.linkPanel);
/* 56:   */     
/* 57:61 */     this.goalHoriz.addComponent(this.partPanel);
/* 58:62 */     this.goalVert.addComponent(this.partPanel);
/* 59:   */     
/* 60:64 */     this.goalLayout.setHorizontalGroup(this.goalHoriz);
/* 61:65 */     this.goalLayout.setVerticalGroup(this.goalVert);
/* 62:66 */     Connections.getPorts(this).addSignalProcessor("process");
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void view(Object object)
/* 66:   */   {
/* 67:72 */     if ((object instanceof Entity))
/* 68:   */     {
/* 69:73 */       Entity t = (Entity)object;
/* 70:76 */       if (t.getFeatures().contains("not")) {
/* 71:77 */         this.linkPanel.setDesirability(false);
/* 72:   */       } else {
/* 73:80 */         this.linkPanel.setDesirability(true);
/* 74:   */       }
/* 75:82 */       this.partPanel.displayText(t.getSubject().getType());
/* 76:83 */       this.subjectPanel.displayText(Getters.getObject(t).getType());
/* 77:84 */       this.linkPanel.setVisible(true);
/* 78:85 */       this.partPanel.displayText(t.getSubject().getType());
/* 79:   */     }
/* 80:   */   }
/* 81:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.PartPanel
 * JD-Core Version:    0.7.0.1
 */