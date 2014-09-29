/*  1:   */ package tomLarson;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import gui.WiredPanel;
/*  6:   */ import java.awt.Color;
/*  7:   */ import java.awt.GridLayout;
/*  8:   */ 
/*  9:   */ public class BundleViewer
/* 10:   */   extends WiredPanel
/* 11:   */ {
/* 12:   */   private static final long serialVersionUID = 7213110525847447483L;
/* 13:   */   Ports ports;
/* 14:17 */   BundleViewerHelper bvh = new BundleViewerHelper();
/* 15:18 */   ThreadTree input = null;
/* 16:   */   
/* 17:   */   public BundleViewer()
/* 18:   */   {
/* 19:20 */     Connections.getPorts(this).addSignalProcessor("process");
/* 20:21 */     setLayout(new GridLayout(1, 1));
/* 21:22 */     add(this.bvh);
/* 22:23 */     setBackground(Color.WHITE);
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void process(Object signal)
/* 26:   */   {
/* 27:26 */     if (((signal instanceof ThreadTree)) || (signal == null))
/* 28:   */     {
/* 29:27 */       this.input = ((ThreadTree)signal);
/* 30:   */       
/* 31:   */ 
/* 32:30 */       this.bvh.setInput(this.input);
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public void clear() {}
/* 37:   */   
/* 38:   */   public Ports getPorts()
/* 39:   */   {
/* 40:41 */     if (this.ports == null) {
/* 41:42 */       this.ports = new Ports();
/* 42:   */     }
/* 43:44 */     return this.ports;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public ThreadTree getInput()
/* 47:   */   {
/* 48:46 */     return this.input;
/* 49:   */   }
/* 50:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.BundleViewer
 * JD-Core Version:    0.7.0.1
 */