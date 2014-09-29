/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import javax.swing.AbstractButton;
/*  5:   */ 
/*  6:   */ public class WiredDistributorBox
/*  7:   */   extends AbstractWiredBox
/*  8:   */ {
/*  9:15 */   ArrayList<AbstractButton> buttons = new ArrayList();
/* 10:   */   
/* 11:   */   public WiredDistributorBox()
/* 12:   */   {
/* 13:18 */     Connections.getPorts(this).addSignalProcessor("process");
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void process(Object signal)
/* 17:   */   {
/* 18:22 */     for (AbstractButton button : this.buttons) {
/* 19:23 */       if (button.isSelected()) {
/* 20:24 */         Connections.getPorts(this).transmit(button.getText(), signal);
/* 21:   */       }
/* 22:   */     }
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void addPort(AbstractButton b)
/* 26:   */   {
/* 27:30 */     this.buttons.add(b);
/* 28:   */   }
/* 29:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.WiredDistributorBox
 * JD-Core Version:    0.7.0.1
 */