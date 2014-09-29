/*   1:    */ package connections.views;
/*   2:    */ 
/*   3:    */ import connections.Port;
/*   4:    */ import java.awt.Color;
/*   5:    */ 
/*   6:    */ public class ViewerWire
/*   7:    */ {
/*   8: 14 */   public static final Color RED_THREAD = Color.red;
/*   9:    */   private ViewerBox source;
/*  10:    */   private ViewerBox target;
/*  11:    */   private Port sourcePort;
/*  12:    */   private String sourcePortName;
/*  13:    */   private String targetPortName;
/*  14:    */   private Color color;
/*  15:    */   private Color permanentColor;
/*  16: 30 */   boolean visible = true;
/*  17:    */   boolean dashed;
/*  18: 34 */   int destinationIndex = 1;
/*  19: 36 */   int destinationCount = 1;
/*  20:    */   
/*  21:    */   public Color getColor()
/*  22:    */   {
/*  23: 39 */     return this.color;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setColor(Color color)
/*  27:    */   {
/*  28: 43 */     this.color = color;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public Color getPermanentColor()
/*  32:    */   {
/*  33: 47 */     return this.permanentColor;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void setPermanentColor(Color permanentColor)
/*  37:    */   {
/*  38: 51 */     this.permanentColor = permanentColor;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public ViewerWire(ViewerBox source, ViewerBox target)
/*  42:    */   {
/*  43: 56 */     this.source = source;
/*  44: 57 */     this.target = target;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public ViewerWire(ViewerBox source, ViewerBox target, int x, int count)
/*  48:    */   {
/*  49: 61 */     this(source, target);
/*  50: 62 */     this.destinationIndex = x;
/*  51: 63 */     this.destinationCount = count;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public ViewerWire(Port sourcePort, ViewerBox source, ViewerBox target)
/*  55:    */   {
/*  56: 72 */     this(source, target);
/*  57: 73 */     this.sourcePort = sourcePort;
/*  58: 74 */     this.sourcePortName = sourcePort.getSourceName();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public ViewerWire(Port sourcePort, ViewerBox source, String targetPortName, ViewerBox target)
/*  62:    */   {
/*  63: 78 */     this(sourcePort, source, target);
/*  64: 79 */     this.targetPortName = targetPortName;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public ViewerBox getSource()
/*  68:    */   {
/*  69: 83 */     return this.source;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public ViewerBox getTarget()
/*  73:    */   {
/*  74: 87 */     return this.target;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String toString()
/*  78:    */   {
/*  79: 91 */     return "<Wire connecting " + getSource().getText() + " to " + getTarget().getText() + ">";
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setVisible(boolean b)
/*  83:    */   {
/*  84: 95 */     this.visible = b;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isVisible()
/*  88:    */   {
/*  89: 99 */     return this.visible;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isDotted()
/*  93:    */   {
/*  94:103 */     if (this.sourcePort != null) {
/*  95:104 */       if ((this.sourcePort.getSourceName() == "output") || (this.sourcePort.getSourceName() == "up"))
/*  96:    */       {
/*  97:105 */         if (this.source.getSwitchState() == ViewerBox.OFF_SWITCH) {
/*  98:106 */           return true;
/*  99:    */         }
/* 100:    */       }
/* 101:109 */       else if ((this.sourcePort.getSourceName() == "down") && 
/* 102:110 */         (this.source.getSwitchState() == ViewerBox.ON_SWITCH)) {
/* 103:111 */         return true;
/* 104:    */       }
/* 105:    */     }
/* 106:115 */     return false;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Port getSourcePort()
/* 110:    */   {
/* 111:119 */     return this.sourcePort;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getSourcePortName()
/* 115:    */   {
/* 116:123 */     return this.sourcePortName;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getTargetPortName()
/* 120:    */   {
/* 121:127 */     return this.targetPortName;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public int getDestinationIndex()
/* 125:    */   {
/* 126:131 */     return this.destinationIndex;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public int getDestinationCount()
/* 130:    */   {
/* 131:135 */     return this.destinationCount;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setDashed(boolean dashed)
/* 135:    */   {
/* 136:139 */     this.dashed = dashed;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean isDashed()
/* 140:    */   {
/* 141:143 */     return this.dashed;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.views.ViewerWire
 * JD-Core Version:    0.7.0.1
 */