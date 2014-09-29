/*   1:    */ package bridge.views.frameviews.classic;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.util.Vector;
/*   5:    */ 
/*   6:    */ public class FrameBundle
/*   7:    */ {
/*   8:  7 */   private Color barColor = Color.gray;
/*   9:    */   
/*  10:    */   public void setBarColor(Color c)
/*  11:    */   {
/*  12: 10 */     this.barColor = c;
/*  13:    */   }
/*  14:    */   
/*  15:    */   public Color getBarColor()
/*  16:    */   {
/*  17: 14 */     return this.barColor;
/*  18:    */   }
/*  19:    */   
/*  20: 17 */   private String title = null;
/*  21:    */   
/*  22:    */   public void setTitle(String t)
/*  23:    */   {
/*  24: 20 */     this.title = t;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getTitle()
/*  28:    */   {
/*  29: 24 */     return this.title;
/*  30:    */   }
/*  31:    */   
/*  32: 27 */   private String topText = "";
/*  33: 29 */   private String bottomText = "";
/*  34: 31 */   private String id = "";
/*  35: 33 */   private Vector<FrameBundle> bundles = new Vector();
/*  36:    */   
/*  37:    */   public void setTop(String t)
/*  38:    */   {
/*  39: 36 */     this.topText = t;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getTop()
/*  43:    */   {
/*  44: 40 */     return this.topText;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setBottom(String t)
/*  48:    */   {
/*  49: 44 */     this.bottomText = t;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void addFrameBundle(FrameBundle b)
/*  53:    */   {
/*  54: 48 */     this.bundles.add(b);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Vector getFrameBundles()
/*  58:    */   {
/*  59: 52 */     return this.bundles;
/*  60:    */   }
/*  61:    */   
/*  62: 55 */   private boolean showNoThreads = false;
/*  63: 57 */   private boolean negated = false;
/*  64:    */   
/*  65:    */   public int depth()
/*  66:    */   {
/*  67: 63 */     int result = 1;
/*  68: 64 */     for (int i = 0; i < this.bundles.size(); i++)
/*  69:    */     {
/*  70: 65 */       FrameBundle f = (FrameBundle)this.bundles.elementAt(i);
/*  71: 66 */       int x = f.depth();
/*  72:    */       
/*  73: 68 */       result += x;
/*  74:    */     }
/*  75: 70 */     return result;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public FrameBundle(String top, Vector bottom, boolean negated)
/*  79:    */   {
/*  80: 79 */     this.topText = top;
/*  81: 80 */     this.bottomText = "";
/*  82: 81 */     bottom = sort(bottom);
/*  83: 82 */     for (int i = 0; i < bottom.size() - 1; i++) {
/*  84: 83 */       this.bottomText = (this.bottomText + (String)bottom.elementAt(i) + (i == 0 ? ": " : ", "));
/*  85:    */     }
/*  86: 85 */     this.bottomText += (String)bottom.elementAt(bottom.size() - 1);
/*  87: 86 */     setNegated(negated);
/*  88:    */   }
/*  89:    */   
/*  90:    */   private Vector sort(Vector<String> bottom)
/*  91:    */   {
/*  92: 90 */     Vector result = new Vector();
/*  93: 91 */     result.add(bottom.firstElement());
/*  94: 92 */     this.id = ((String)bottom.firstElement());
/*  95: 93 */     bottom.remove(0);
/*  96: 94 */     if (this.showNoThreads) {
/*  97: 95 */       return result;
/*  98:    */     }
/*  99: 97 */     for (String s : bottom) {
/* 100: 98 */       if (s.startsWith("feature")) {
/* 101: 99 */         result.add(s);
/* 102:    */       }
/* 103:    */     }
/* 104:102 */     for (String s : bottom) {
/* 105:103 */       if (!s.startsWith("feature")) {
/* 106:104 */         result.add(s);
/* 107:    */       }
/* 108:    */     }
/* 109:108 */     return result;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String toString()
/* 113:    */   {
/* 114:112 */     return toString(0);
/* 115:    */   }
/* 116:    */   
/* 117:    */   private String toString(int depth)
/* 118:    */   {
/* 119:116 */     String filler = "";
/* 120:117 */     for (int i = 0; i < depth; i++) {
/* 121:118 */       filler = filler + "  ";
/* 122:    */     }
/* 123:119 */     String s = filler + this.topText + "\n";
/* 124:120 */     for (int i = 0; i < this.bundles.size(); i++)
/* 125:    */     {
/* 126:121 */       Object o = this.bundles.get(i);
/* 127:123 */       if ((o instanceof FrameBundle))
/* 128:    */       {
/* 129:124 */         String substring = ((FrameBundle)o).toString(depth + 1);
/* 130:125 */         s = s + substring + "\n";
/* 131:    */       }
/* 132:    */       else
/* 133:    */       {
/* 134:128 */         String substring = o.toString();
/* 135:129 */         s = s + filler + substring + "\n";
/* 136:    */       }
/* 137:    */     }
/* 138:132 */     s = s + filler + this.bottomText;
/* 139:133 */     return s;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setShowNoThreads(boolean b)
/* 143:    */   {
/* 144:137 */     this.showNoThreads = b;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getListenerBottom()
/* 148:    */   {
/* 149:141 */     return this.bottomText;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getBottom()
/* 153:    */   {
/* 154:145 */     if (this.showNoThreads) {
/* 155:146 */       return this.id;
/* 156:    */     }
/* 157:148 */     return this.bottomText;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isNegated()
/* 161:    */   {
/* 162:152 */     return this.negated;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setNegated(boolean negated)
/* 166:    */   {
/* 167:156 */     this.negated = negated;
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     bridge.views.frameviews.classic.FrameBundle
 * JD-Core Version:    0.7.0.1
 */