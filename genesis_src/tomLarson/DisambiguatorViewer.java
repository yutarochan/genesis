/*  1:   */ package tomLarson;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import gui.WiredPanel;
/*  6:   */ import java.awt.Color;
/*  7:   */ import java.awt.GridLayout;
/*  8:   */ 
/*  9:   */ public class DisambiguatorViewer
/* 10:   */   extends WiredPanel
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 7213110525847447483L;
/* 13:   */   Ports ports;
/* 14:17 */   DisambiguatorViewerHelper dvh = new DisambiguatorViewerHelper();
/* 15:18 */   ThreadTree input = null;
/* 16:   */   
/* 17:   */   public DisambiguatorViewer()
/* 18:   */   {
/* 19:20 */     Connections.getPorts(this).addSignalProcessor("process");
/* 20:21 */     setLayout(new GridLayout(1, 1));
/* 21:22 */     add(this.dvh);
/* 22:23 */     setBackground(Color.WHITE);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void process(Object signal)
/* 26:   */   {
/* 27:26 */     if (((signal instanceof ThreadTree)) || (signal == null))
/* 28:   */     {
/* 29:27 */       this.input = ((ThreadTree)signal);
/* 30:   */       
/* 31:29 */       this.dvh.setInput(this.input);
/* 32:   */     }
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void clear() {}
/* 36:   */   
/* 37:   */   public Ports getPorts()
/* 38:   */   {
/* 39:40 */     if (this.ports == null) {
/* 40:41 */       this.ports = new Ports();
/* 41:   */     }
/* 42:43 */     return this.ports;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public ThreadTree getInput()
/* 46:   */   {
/* 47:45 */     return this.input;
/* 48:   */   }
/* 49:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.DisambiguatorViewer
 * JD-Core Version:    0.7.0.1
 */