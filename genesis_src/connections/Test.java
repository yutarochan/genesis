/*   1:    */ package connections;
/*   2:    */ 
/*   3:    */ import connections.views.Adapter;
/*   4:    */ import connections.views.ConnectionViewer;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import javax.swing.JFrame;
/*   8:    */ import javax.swing.JScrollPane;
/*   9:    */ 
/*  10:    */ public class Test
/*  11:    */ {
/*  12: 15 */   public static int counter = 100;
/*  13:    */   
/*  14:    */   public static void main(String[] args)
/*  15:    */   {
/*  16: 21 */     JFrame frame = new JFrame();
/*  17: 22 */     ConnectionViewer viewer = Adapter.makeConnectionAdapter().getViewer();
/*  18:    */     
/*  19: 24 */     frame.getContentPane().add(new JScrollPane(viewer), "Center");
/*  20: 25 */     frame.getContentPane().add(viewer.getSlider(), "South");
/*  21: 26 */     frame.setBounds(0, 0, 800, 800);
/*  22: 27 */     frame.setVisible(true);
/*  23: 28 */     new Test().demonstrate();
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void demonstrate()
/*  27:    */   {
/*  28: 33 */     TestBox a = new TestBox("A");
/*  29: 34 */     WiredOnOffSwitch b = new WiredOnOffSwitch("Switch");
/*  30: 35 */     b.setSelected(false);
/*  31: 36 */     TestBox l = new TestBox("L");
/*  32: 37 */     TestBox m = new TestBox("M");
/*  33: 38 */     TestBox n = new TestBox("N");
/*  34: 39 */     TestBox x = new TestBox("X");
/*  35: 40 */     TestBox d1 = new TestBox("Y1");
/*  36: 41 */     TestBox d2 = new TestBox("russia want russia damage a very big computer_networks");
/*  37: 42 */     TestBox e = new TestBox("E");
/*  38: 43 */     TestBox z = new TestBox("Z");
/*  39:    */     
/*  40:    */ 
/*  41:    */ 
/*  42:    */ 
/*  43:    */ 
/*  44: 49 */     Connections.biwire(l, m);
/*  45: 50 */     Connections.wire(m, l);
/*  46: 51 */     Connections.wire(l, n);
/*  47:    */     
/*  48:    */ 
/*  49: 54 */     Connections.wire(x, d1);
/*  50: 55 */     Connections.wire(x, d2);
/*  51: 56 */     Connections.wire(d2, e);
/*  52: 57 */     Connections.wire("foo", x, "bar", d1);
/*  53: 58 */     Connections.wire("up", b, z);
/*  54: 59 */     Connections.wire("down", b, e);
/*  55: 60 */     Connections.biwire(a, b);
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64: 69 */     Connections.getPorts(a).transmit("Hello world");
/*  65: 70 */     Connections.getPorts(l).transmit("Hello world");
/*  66:    */   }
/*  67:    */   
/*  68:    */   public class TestBox
/*  69:    */     extends AbstractWiredBox
/*  70:    */   {
/*  71:    */     public TestBox(String name)
/*  72:    */     {
/*  73: 94 */       super();
/*  74: 95 */       Connections.getPorts(this).addSignalProcessor("aMethod");
/*  75:    */     }
/*  76:    */     
/*  77:    */     public void aMethod(Object signal)
/*  78:    */     {
/*  79:103 */       System.out.println("...");
/*  80:104 */       if (Test.counter-- > 0)
/*  81:    */       {
/*  82:    */         try
/*  83:    */         {
/*  84:106 */           Thread.sleep(100L);
/*  85:    */         }
/*  86:    */         catch (InterruptedException e)
/*  87:    */         {
/*  88:109 */           e.printStackTrace();
/*  89:    */         }
/*  90:111 */         Connections.getPorts(this).transmit(signal);
/*  91:    */       }
/*  92:    */     }
/*  93:    */     
/*  94:    */     public void anotherMethod(Object signal) {}
/*  95:    */   }
/*  96:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.Test
 * JD-Core Version:    0.7.0.1
 */