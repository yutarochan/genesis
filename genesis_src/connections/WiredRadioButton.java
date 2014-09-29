/*  1:   */ package connections;
/*  2:   */ 
/*  3:   */ import java.util.concurrent.atomic.AtomicBoolean;
/*  4:   */ import java.util.prefs.Preferences;
/*  5:   */ import javax.swing.JRadioButton;
/*  6:   */ import javax.swing.event.ChangeEvent;
/*  7:   */ import javax.swing.event.ChangeListener;
/*  8:   */ 
/*  9:   */ public class WiredRadioButton
/* 10:   */   extends JRadioButton
/* 11:   */   implements WiredBox
/* 12:   */ {
/* 13:   */   private AtomicBoolean trackingValue;
/* 14:20 */   protected static String key = Connections.class.getCanonicalName();
/* 15:   */   private static final long serialVersionUID = 570145500004368561L;
/* 16:   */   
/* 17:   */   public WiredRadioButton(String name, AtomicBoolean atomicBoolean)
/* 18:   */   {
/* 19:23 */     super(name, Preferences.userRoot().getBoolean(key + name, true));
/* 20:24 */     setName(name);
/* 21:25 */     Connections.getPorts(this).addSignalProcessor("input", "processWiredOnOffSwitch");
/* 22:26 */     this.trackingValue = atomicBoolean;
/* 23:27 */     if (this.trackingValue != null) {
/* 24:28 */       this.trackingValue.set(isSelected());
/* 25:   */     }
/* 26:30 */     addChangeListener(new MyPropertyChangeListener(this));
/* 27:31 */     setOpaque(false);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public WiredRadioButton(String name)
/* 31:   */   {
/* 32:35 */     this(name, false);
/* 33:   */   }
/* 34:   */   
/* 35:   */   public WiredRadioButton(String name, boolean state)
/* 36:   */   {
/* 37:39 */     super(name);
/* 38:40 */     setName(name);
/* 39:41 */     setOpaque(false);
/* 40:42 */     setSelected(state);
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void processWiredOnOffSwitch(Object signal)
/* 44:   */   {
/* 45:46 */     if (isSelected()) {
/* 46:47 */       Connections.getPorts(this).transmit("output", signal);
/* 47:   */     }
/* 48:   */   }
/* 49:   */   
/* 50:   */   protected class MyPropertyChangeListener
/* 51:   */     implements ChangeListener
/* 52:   */   {
/* 53:   */     WiredRadioButton button;
/* 54:   */     
/* 55:   */     public MyPropertyChangeListener(WiredRadioButton button)
/* 56:   */     {
/* 57:56 */       this.button = button;
/* 58:   */     }
/* 59:   */     
/* 60:   */     public void stateChanged(ChangeEvent e)
/* 61:   */     {
/* 62:60 */       String name = this.button.getText();
/* 63:61 */       Preferences.userRoot().putBoolean(WiredRadioButton.key + name, this.button.isSelected());
/* 64:62 */       if (WiredRadioButton.this.trackingValue != null) {
/* 65:63 */         WiredRadioButton.this.trackingValue.set(this.button.isSelected());
/* 66:   */       }
/* 67:   */     }
/* 68:   */   }
/* 69:   */   
/* 70:   */   public String toString()
/* 71:   */   {
/* 72:70 */     return "<Wired on-off switch " + getName() + ">";
/* 73:   */   }
/* 74:   */   
/* 75:   */   public void open()
/* 76:   */   {
/* 77:74 */     setEnabled(true);
/* 78:   */   }
/* 79:   */   
/* 80:   */   public void close()
/* 81:   */   {
/* 82:78 */     setEnabled(false);
/* 83:   */   }
/* 84:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.WiredRadioButton
 * JD-Core Version:    0.7.0.1
 */