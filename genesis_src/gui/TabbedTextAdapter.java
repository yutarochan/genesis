/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import connections.AbstractWiredBox;
/*  4:   */ import connections.Connections;
/*  5:   */ import connections.Ports;
/*  6:   */ 
/*  7:   */ public class TabbedTextAdapter
/*  8:   */   extends AbstractWiredBox
/*  9:   */ {
/* 10:   */   public TabbedTextAdapter(String tabName, TabbedTextViewer viewer)
/* 11:   */   {
/* 12:15 */     setName(tabName);
/* 13:16 */     Connections.wire("switch tab", this, "switch tab", viewer);
/* 14:17 */     Connections.wire(this, viewer);
/* 15:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void process(Object o)
/* 19:   */   {
/* 20:22 */     Connections.getPorts(this).transmit("switch tab", getName());
/* 21:23 */     Connections.getPorts(this).transmit(o);
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TabbedTextAdapter
 * JD-Core Version:    0.7.0.1
 */