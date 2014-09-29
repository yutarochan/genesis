/*  1:   */ package kevinWhite;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import genesis.Genesis;
/*  5:   */ import gui.WiredBlinkingBox;
/*  6:   */ import utils.Mark;
/*  7:   */ 
/*  8:   */ public class LocalGenesis
/*  9:   */   extends Genesis
/* 10:   */ {
/* 11:   */   LocalProcessor localProcessor;
/* 12:   */   WiredBlinkingBox kevinsBlinker;
/* 13:   */   WiredBlinkingBox jobPanel;
/* 14:   */   
/* 15:   */   public LocalGenesis()
/* 16:   */   {
/* 17:21 */     Mark.say(new Object[] {"Kevin's constructor" });
/* 18:   */     
/* 19:   */ 
/* 20:   */ 
/* 21:   */ 
/* 22:26 */     Connections.wire("complete story events port", getLocalProcessor(), getJobPanel());
/* 23:   */   }
/* 24:   */   
/* 25:   */   public LocalProcessor getLocalProcessor()
/* 26:   */   {
/* 27:30 */     if (this.localProcessor == null) {
/* 28:31 */       this.localProcessor = new LocalProcessor();
/* 29:   */     }
/* 30:33 */     return this.localProcessor;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public WiredBlinkingBox getKevinsBlinker()
/* 34:   */   {
/* 35:39 */     Mark.say(
/* 36:   */     
/* 37:   */ 
/* 38:   */ 
/* 39:   */ 
/* 40:44 */       new Object[] { "Constructing Kevin's blinker" });
/* 41:40 */     if (this.kevinsBlinker == null) {
/* 42:41 */       this.kevinsBlinker = new WiredBlinkingBox("Goal2", getGoalExpert(), new GoalPanel(), getExpertsPanel());
/* 43:   */     }
/* 44:43 */     return this.kevinsBlinker;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public WiredBlinkingBox getJobPanel()
/* 48:   */   {
/* 49:67 */     Mark.say(
/* 50:   */     
/* 51:   */ 
/* 52:   */ 
/* 53:   */ 
/* 54:72 */       new Object[] { "Constructing Job Panel" });
/* 55:68 */     if (this.jobPanel == null) {
/* 56:69 */       this.jobPanel = new WiredBlinkingBox("Job2", getJobExpert(), new JobPanel(), getExpertsPanel());
/* 57:   */     }
/* 58:71 */     return this.jobPanel;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public static void main(String[] args)
/* 62:   */   {
/* 63:76 */     LocalGenesis myGenesis = new LocalGenesis();
/* 64:77 */     myGenesis.startInFrame();
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.LocalGenesis
 * JD-Core Version:    0.7.0.1
 */