/*   1:    */ package wrapper;
/*   2:    */ 
/*   3:    */ import com.ascent.gui.frame.ABasicFrame;
/*   4:    */ import com.ascent.gui.frame.auxiliaries.NavigationBar;
/*   5:    */ import com.ascent.gui.swing.WConstants;
/*   6:    */ import genesis.Genesis;
/*   7:    */ import genesis.GenesisControls;
/*   8:    */ import java.awt.Frame;
/*   9:    */ import java.awt.Graphics;
/*  10:    */ import java.awt.Rectangle;
/*  11:    */ import java.awt.event.ActionEvent;
/*  12:    */ import java.awt.event.ActionListener;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.net.URL;
/*  15:    */ import javax.swing.JButton;
/*  16:    */ import javax.swing.JComponent;
/*  17:    */ import javax.swing.JEditorPane;
/*  18:    */ import utils.Mark;
/*  19:    */ import utils.Webstart;
/*  20:    */ 
/*  21:    */ public class TheGenesisSystem
/*  22:    */   extends ABasicFrame
/*  23:    */ {
/*  24:    */   public TheGenesisSystem(String[] args)
/*  25:    */   {
/*  26: 26 */     super(args, TheGenesisSystem.class.getResource("genesis.xml"));
/*  27: 27 */     if (args.length != 0)
/*  28:    */     {
/*  29: 28 */       Webstart.setWebStart(true);
/*  30:    */     }
/*  31:    */     else
/*  32:    */     {
/*  33: 31 */       Webstart.setWebStart(false);
/*  34: 32 */       setDocumentationBase("file://c:/phw/javagit/genesis/webstart");
/*  35:    */     }
/*  36: 35 */     setTitle("Gensis");
/*  37: 36 */     WConstants.setRequiresLogin(false);
/*  38: 37 */     WConstants.setBannerGif(WConstants.getImage(TheGenesisSystem.class, "genesis.gif"), 
/*  39: 38 */       WConstants.getImage(TheGenesisSystem.class, "genesis-gray.gif"));
/*  40: 39 */     GenesisControls.makeSmallVideoRecordingButton.addActionListener(new MyActionListener(null));
/*  41: 40 */     GenesisControls.makeMediumVideoRecordingButton.addActionListener(new MyActionListener(null));
/*  42: 41 */     GenesisControls.makeLargeVideoRecordingButton.addActionListener(new MyActionListener(null));
/*  43: 43 */     if (Webstart.isWebStart()) {
/*  44:    */       try
/*  45:    */       {
/*  46: 46 */         System.out.println("Showing top and getting counter");
/*  47: 47 */         getNavigationBar().add(getCounter());
/*  48:    */       }
/*  49:    */       catch (Exception e)
/*  50:    */       {
/*  51: 51 */         Mark.say(new Object[] {"Protection against strange errors" });
/*  52:    */       }
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   private class MyJEditorPane
/*  57:    */     extends JEditorPane
/*  58:    */   {
/*  59:    */     private MyJEditorPane() {}
/*  60:    */     
/*  61:    */     public void paintComponent(Graphics g)
/*  62:    */     {
/*  63: 62 */       super.paintComponent(g);
/*  64: 63 */       g.setColor(WConstants.navigationBarColor);
/*  65: 64 */       g.fillRect(0, 0, getWidth(), getHeight());
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   private JComponent getCounter()
/*  70:    */   {
/*  71:    */     try
/*  72:    */     {
/*  73: 73 */       MyJEditorPane counter = new MyJEditorPane(null);
/*  74: 74 */       counter.setContentType("text/html");
/*  75: 75 */       counter.setEditable(false);
/*  76: 76 */       URL url = new URL("http://people.csail.mit.edu/phw/genesis-runs.html");
/*  77: 77 */       counter.setPage(url);
/*  78: 78 */       return counter;
/*  79:    */     }
/*  80:    */     catch (Exception e)
/*  81:    */     {
/*  82: 81 */       Mark.say(new Object[] {"Unable to count" });
/*  83:    */     }
/*  84: 83 */     return null;
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void setToVideoRecordingDimensions(Rectangle r)
/*  88:    */   {
/*  89: 88 */     Mark.say(
/*  90:    */     
/*  91:    */ 
/*  92:    */ 
/*  93: 92 */       new Object[] { "Adjusting size" });ABasicFrame.getFrame().setBounds(r);ABasicFrame.getFrame().validate();
/*  94:    */   }
/*  95:    */   
/*  96:    */   private class MyActionListener
/*  97:    */     implements ActionListener
/*  98:    */   {
/*  99:    */     private MyActionListener() {}
/* 100:    */     
/* 101:    */     public void actionPerformed(ActionEvent e)
/* 102:    */     {
/* 103: 99 */       if (e.getSource() == Genesis.makeSmallVideoRecordingButton) {
/* 104:100 */         TheGenesisSystem.this.setToVideoRecordingDimensions(new Rectangle(0, 0, 1024, 768));
/* 105:102 */       } else if (e.getSource() == Genesis.makeMediumVideoRecordingButton) {
/* 106:103 */         TheGenesisSystem.this.setToVideoRecordingDimensions(new Rectangle(0, 0, 1280, 1024));
/* 107:105 */       } else if (e.getSource() == Genesis.makeLargeVideoRecordingButton) {
/* 108:106 */         TheGenesisSystem.this.setToVideoRecordingDimensions(new Rectangle(0, 0, 1600, 1200));
/* 109:    */       }
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static void main(String[] args)
/* 114:    */   {
/* 115:112 */     Mark.say(
/* 116:    */     
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:    */ 
/* 125:    */ 
/* 126:123 */       new Object[] { System.getProperty("os.name") });Mark.say(new Object[] { System.getProperty("os.arch") });new TheGenesisSystem(args).start();
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     wrapper.TheGenesisSystem
 * JD-Core Version:    0.7.0.1
 */