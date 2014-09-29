/*   1:    */ package obsolete.mindsEye;
/*   2:    */ 
/*   3:    */ import java.awt.BorderLayout;
/*   4:    */ import java.awt.Color;
/*   5:    */ import java.awt.Container;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import javax.swing.JFrame;
/*   9:    */ import javax.swing.JPanel;
/*  10:    */ import javax.swing.JSlider;
/*  11:    */ 
/*  12:    */ public class MindsEyeViewerController
/*  13:    */   extends JPanel
/*  14:    */ {
/*  15: 22 */   private boolean running = true;
/*  16: 24 */   private boolean paintControl = false;
/*  17:    */   JSlider frameControl;
/*  18:    */   Controller startStopControl;
/*  19:    */   
/*  20:    */   public JSlider getFrameControl()
/*  21:    */   {
/*  22: 40 */     if (this.frameControl == null)
/*  23:    */     {
/*  24: 41 */       this.frameControl = new JSlider();
/*  25: 42 */       this.frameControl.setValue(0);
/*  26:    */     }
/*  27: 44 */     return this.frameControl;
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Controller getStartStopControl()
/*  31:    */   {
/*  32: 48 */     if (this.startStopControl == null)
/*  33:    */     {
/*  34: 49 */       this.startStopControl = new Controller();
/*  35: 50 */       int height = getFrameControl().getPreferredSize().height;
/*  36: 51 */       height = 30;
/*  37: 52 */       this.startStopControl.setPreferredSize(new Dimension(height, height));
/*  38:    */     }
/*  39: 54 */     return this.startStopControl;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public MindsEyeViewerController()
/*  43:    */   {
/*  44: 58 */     setLayout(new BorderLayout());
/*  45: 59 */     add(getFrameControl(), "Center");
/*  46: 60 */     add(getStartStopControl(), "West");
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setImage(int frame)
/*  50:    */   {
/*  51: 64 */     getFrameControl().setValue(frame);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean isRunning()
/*  55:    */   {
/*  56: 68 */     return this.running;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void toggleRunState()
/*  60:    */   {
/*  61: 72 */     this.running = (!this.running);
/*  62:    */     
/*  63:    */ 
/*  64: 75 */     repaint();
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setRunState(boolean b)
/*  68:    */   {
/*  69: 79 */     this.running = b;
/*  70: 80 */     repaint();
/*  71:    */   }
/*  72:    */   
/*  73:    */   class Controller
/*  74:    */     extends JPanel
/*  75:    */   {
/*  76:    */     Controller() {}
/*  77:    */     
/*  78:    */     public void paint(Graphics g)
/*  79:    */     {
/*  80: 85 */       int w = getWidth();
/*  81: 86 */       int h = getHeight();
/*  82: 87 */       int offsetW = w / 3;
/*  83: 88 */       int offsetH = h / 6;
/*  84: 89 */       int deltaW = offsetW / 3;
/*  85: 90 */       int deltaH = 2 * h / 3;
/*  86: 91 */       Color c = g.getColor();
/*  87: 92 */       g.setColor(Color.GRAY);
/*  88: 93 */       if (MindsEyeViewerController.this.running)
/*  89:    */       {
/*  90: 95 */         g.fillRect(offsetW, offsetH, deltaW, deltaH);
/*  91: 96 */         g.fillRect(offsetW + 2 * deltaW, offsetH, deltaW, deltaH);
/*  92:    */       }
/*  93:    */       else
/*  94:    */       {
/*  95:100 */         int[] x = { offsetW, 2 * offsetW, offsetW };
/*  96:101 */         int[] y = { offsetH, h / 2, h - offsetH };
/*  97:102 */         g.fillPolygon(x, y, 3);
/*  98:    */       }
/*  99:104 */       g.setColor(c);
/* 100:    */     }
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean isPaintControl()
/* 104:    */   {
/* 105:109 */     return this.paintControl;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setPaintControl(boolean paintControl)
/* 109:    */   {
/* 110:113 */     this.paintControl = paintControl;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setBounds(int start, int end)
/* 114:    */   {
/* 115:117 */     getFrameControl().setMinimum(start);
/* 116:118 */     getFrameControl().setMaximum(end);
/* 117:119 */     getFrameControl().setMajorTickSpacing((end - start) / 10);
/* 118:120 */     getFrameControl().setMinorTickSpacing((end - start) / 100);
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static void main(String[] ignore)
/* 122:    */   {
/* 123:124 */     JFrame frame = new JFrame();
/* 124:125 */     frame.setBounds(0, 0, 1024, 100);
/* 125:126 */     frame.getContentPane().add(new MindsEyeViewerController(), "Center");
/* 126:127 */     frame.setVisible(true);
/* 127:    */   }
/* 128:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mindsEye.MindsEyeViewerController
 * JD-Core Version:    0.7.0.1
 */