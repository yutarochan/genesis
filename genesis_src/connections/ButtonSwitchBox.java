/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ import javax.swing.AbstractButton;
/*  4:   */ 
/*  5:   */ public class ButtonSwitchBox
/*  6:   */   extends AbstractWiredBox
/*  7:   */ {
/*  8:   */   public static final String ON = "on";
/*  9:   */   public static final String OFF = "off";
/* 10:   */   private final AbstractButton button;
/* 11:   */   
/* 12:   */   public ButtonSwitchBox(AbstractButton button)
/* 13:   */   {
/* 14:28 */     this.button = button;
/* 15:   */     
/* 16:30 */     Connections.getPorts(this).addSignalProcessor("input");
/* 17:31 */     Connections.getPorts(this).addSignalProcessor("on", "onInput");
/* 18:32 */     Connections.getPorts(this).addSignalProcessor("off", "offInput");
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void input(Object o)
/* 22:   */   {
/* 23:36 */     Connections.getPorts(this).transmit(o);
/* 24:37 */     Connections.getPorts(this).transmit(
/* 25:38 */       this.button.isSelected() ? "on" : "off", o);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void onInput(Object o)
/* 29:   */   {
/* 30:42 */     if (this.button.isSelected())
/* 31:   */     {
/* 32:43 */       Connections.getPorts(this).transmit(o);
/* 33:44 */       Connections.getPorts(this).transmit("on", o);
/* 34:   */     }
/* 35:   */   }
/* 36:   */   
/* 37:   */   public void offInput(Object o)
/* 38:   */   {
/* 39:49 */     if (!this.button.isSelected())
/* 40:   */     {
/* 41:50 */       Connections.getPorts(this).transmit(o);
/* 42:51 */       Connections.getPorts(this).transmit("off", o);
/* 43:   */     }
/* 44:   */   }
/* 45:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.ButtonSwitchBox
 * JD-Core Version:    0.7.0.1
 */