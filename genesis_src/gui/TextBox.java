/*  1:   */ package gui;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import genesis.GenesisGetters;
/*  7:   */ import java.awt.Color;
/*  8:   */ import java.awt.Container;
/*  9:   */ import java.awt.Font;
/* 10:   */ import javax.swing.JFrame;
/* 11:   */ import javax.swing.JLabel;
/* 12:   */ import windowGroup.WindowGroupManager;
/* 13:   */ 
/* 14:   */ public class TextBox
/* 15:   */   extends JLabel
/* 16:   */   implements WiredBox
/* 17:   */ {
/* 18:   */   GenesisGetters genesisGetters;
/* 19:   */   
/* 20:   */   public TextBox(GenesisGetters genesisGetters)
/* 21:   */   {
/* 22:22 */     super("", 0);
/* 23:23 */     this.genesisGetters = genesisGetters;
/* 24:24 */     Font font = getFont();
/* 25:25 */     setFont(new Font(font.getFamily(), 1, 15));
/* 26:26 */     setBackground(Color.YELLOW);
/* 27:27 */     setOpaque(true);
/* 28:28 */     Connections.getPorts(this).addSignalProcessor("process");
/* 29:   */   }
/* 30:   */   
/* 31:   */   public void process(Object o)
/* 32:   */   {
/* 33:32 */     if ((o instanceof String))
/* 34:   */     {
/* 35:33 */       setText(o.toString());
/* 36:34 */       this.genesisGetters.getWindowGroupManager().setGuts(this.genesisGetters.getRightPanel(), this);
/* 37:   */       
/* 38:36 */       Connections.getPorts(this).transmit(o.toString());
/* 39:   */     }
/* 40:   */   }
/* 41:   */   
/* 42:   */   public static void main(String[] args)
/* 43:   */   {
/* 44:   */     try
/* 45:   */     {
/* 46:42 */       JFrame frame = new JFrame();
/* 47:43 */       frame.setBounds(200, 200, 400, 500);
/* 48:44 */       frame.setVisible(true);
/* 49:45 */       TextBox talker = new TextBox(new GenesisGetters());
/* 50:46 */       frame.getContentPane().add(talker);
/* 51:47 */       talker.process("I have learned that the man touched the woman because the man ran into the woman");
/* 52:   */     }
/* 53:   */     catch (Exception e)
/* 54:   */     {
/* 55:50 */       e.printStackTrace();
/* 56:   */     }
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TextBox
 * JD-Core Version:    0.7.0.1
 */