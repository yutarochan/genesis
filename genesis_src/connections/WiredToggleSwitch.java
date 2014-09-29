/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ public class WiredToggleSwitch
/*  4:   */   extends WiredOnOffSwitch
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = -8619692450571977036L;
/*  7:   */   
/*  8:   */   public WiredToggleSwitch(String name)
/*  9:   */   {
/* 10:14 */     super(name);
/* 11:15 */     Connections.getPorts(this).addSignalProcessor("input", "processWiredToggleSwitch");
/* 12:   */   }
/* 13:   */   
/* 14:   */   public void processWiredToggleSwitch(Object signal)
/* 15:   */   {
/* 16:19 */     if (isSelected()) {
/* 17:20 */       Connections.getPorts(this).transmit("up", signal);
/* 18:   */     } else {
/* 19:23 */       Connections.getPorts(this).transmit("down", signal);
/* 20:   */     }
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String toString()
/* 24:   */   {
/* 25:28 */     return "<Wired toggle switch " + getName() + ">";
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.WiredToggleSwitch
 * JD-Core Version:    0.7.0.1
 */