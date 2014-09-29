/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Relation;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import connections.Ports;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.FontMetrics;
/*   8:    */ import java.awt.Graphics;
/*   9:    */ import java.awt.Graphics2D;
/*  10:    */ import java.awt.geom.AffineTransform;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import javax.swing.BorderFactory;
/*  13:    */ 
/*  14:    */ public class TrajectoryViewer
/*  15:    */   extends NegatableJPanel
/*  16:    */ {
/*  17:    */   String role;
/*  18:    */   String name;
/*  19:    */   private Ports ports;
/*  20:    */   
/*  21:    */   public TrajectoryViewer()
/*  22:    */   {
/*  23: 26 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/*  24:    */     
/*  25: 28 */     setOpaque(false);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void paint(Graphics graphics)
/*  29:    */   {
/*  30: 33 */     super.paint(graphics);
/*  31: 34 */     Graphics2D g = (Graphics2D)graphics;
/*  32: 35 */     int height = getHeight();
/*  33: 36 */     int width = getWidth();
/*  34: 37 */     int thickness = 10;
/*  35: 38 */     int length = 100;
/*  36: 39 */     int headLength = 10;
/*  37: 40 */     int headDelta = 5;
/*  38: 41 */     int square = 6 * width / 10;
/*  39: 42 */     int diameter = 3 * thickness / 2;
/*  40: 43 */     int radius = diameter / 2;
/*  41: 44 */     Color squareColor = new Color(150, 150, 150);
/*  42: 45 */     Color shadowColor = Color.LIGHT_GRAY;
/*  43: 46 */     Color ballColor = Color.BLUE;
/*  44: 47 */     int yCenter = 0;
/*  45: 48 */     int xCenter = 0;
/*  46: 49 */     if ((width == 0) || (height == 0)) {
/*  47: 50 */       return;
/*  48:    */     }
/*  49: 52 */     g.drawRect(0, 0, width - 1, height - 1);
/*  50: 53 */     if (this.role == null) {
/*  51: 54 */       return;
/*  52:    */     }
/*  53: 57 */     FontMetrics fm = g.getFontMetrics();
/*  54: 58 */     g.drawString(this.role, 10, height - 5 - fm.getDescent());
/*  55:    */     
/*  56: 60 */     g.setColor(shadowColor);
/*  57:    */     
/*  58:    */ 
/*  59: 63 */     int xOffset = (width - square) / 2;
/*  60: 64 */     int yOffset = height / 2;
/*  61: 65 */     int[] x = { 0, length - headLength, length - headLength, length, length - headLength, length - headLength };
/*  62: 66 */     int[] y = { 0, 0, -headDelta, thickness / 2, thickness + headDelta, thickness, thickness };
/*  63: 67 */     double multiplier = square / length;
/*  64: 68 */     int tOffset = (int)((height - thickness * multiplier) / 2.0D);
/*  65: 69 */     AffineTransform transform = g.getTransform();
/*  66: 70 */     transform.translate(xOffset, tOffset);
/*  67: 71 */     transform.scale(multiplier, multiplier);
/*  68: 72 */     g.setTransform(transform);
/*  69:    */     
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:    */ 
/*  78: 82 */     g.setColor(squareColor);
/*  79: 83 */     g.fillPolygon(x, y, 7);
/*  80: 84 */     g.setColor(ballColor);
/*  81:    */     
/*  82: 86 */     int size = 15;
/*  83: 87 */     g.fillRect(size, -size, size, size);
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void setParameters(String role)
/*  87:    */   {
/*  88: 99 */     this.role = role;
/*  89:100 */     repaint();
/*  90:    */   }
/*  91:    */   
/*  92:    */   private void clearData()
/*  93:    */   {
/*  94:104 */     this.role = null;
/*  95:105 */     this.name = null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Ports getPorts()
/*  99:    */   {
/* 100:109 */     if (this.ports == null) {
/* 101:110 */       this.ports = new Ports();
/* 102:    */     }
/* 103:112 */     return this.ports;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void view(Object signal)
/* 107:    */   {
/* 108:116 */     if ((signal instanceof Sequence))
/* 109:    */     {
/* 110:117 */       Sequence space = (Sequence)signal;
/* 111:118 */       if (space.isA("eventSpace"))
/* 112:    */       {
/* 113:119 */         Sequence ladder = (Sequence)space.getElement(0);
/* 114:120 */         Relation trajectory = (Relation)ladder.getElement(0);
/* 115:121 */         setParameters(trajectory.getType());
/* 116:    */       }
/* 117:    */     }
/* 118:124 */     else if ((signal instanceof Relation))
/* 119:    */     {
/* 120:125 */       Relation trajectory = (Relation)signal;
/* 121:126 */       setParameters(trajectory.getType());
/* 122:    */     }
/* 123:    */     else
/* 124:    */     {
/* 125:129 */       System.err.println(getClass().getName() + ": Didn't know what to do with input of type " + signal.getClass().toString() + ": " + 
/* 126:130 */         signal + " in TrajectoryViewer");
/* 127:    */     }
/* 128:132 */     setTruthValue(signal);
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.TrajectoryViewer
 * JD-Core Version:    0.7.0.1
 */