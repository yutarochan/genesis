/*   1:    */ package obsolete.mindsEye;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import adamKraft.videoUtils.MovieReader;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Dimension;
/*   7:    */ import java.awt.FontMetrics;
/*   8:    */ import java.awt.Graphics;
/*   9:    */ import java.awt.image.BufferedImage;
/*  10:    */ import javax.swing.BorderFactory;
/*  11:    */ import javax.swing.JComponent;
/*  12:    */ 
/*  13:    */ public class MindsEyeMovieViewerScreen
/*  14:    */   extends JComponent
/*  15:    */ {
/*  16:    */   private BufferedImage image;
/*  17:    */   private MovieReader movieReader;
/*  18: 26 */   private int millisPerFrame = 1000 / MindsEyeMoviePlayer.frameDelay;
/*  19:    */   private String movieName;
/*  20: 32 */   public static String TIME = "time";
/*  21: 34 */   public static String SHOW_ALL_COMMENTS = "show all comments";
/*  22: 36 */   public static String EXTERNAL_PLAYER = "external player";
/*  23: 38 */   long frame = 0L;
/*  24: 40 */   boolean pause = false;
/*  25: 42 */   boolean playing = false;
/*  26:    */   
/*  27:    */   public MindsEyeMovieViewerScreen(String name)
/*  28:    */   {
/*  29: 45 */     this();
/*  30: 46 */     this.movieName = name;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public MindsEyeMovieViewerScreen()
/*  34:    */   {
/*  35: 50 */     setBorder(BorderFactory.createBevelBorder(0));
/*  36:    */   }
/*  37:    */   
/*  38:    */   public synchronized void paint(Graphics g)
/*  39:    */   {
/*  40: 56 */     super.paint(g);
/*  41: 57 */     if (this.image == null) {
/*  42: 58 */       return;
/*  43:    */     }
/*  44: 60 */     int border = 5;
/*  45: 61 */     int imageW = this.image.getWidth();
/*  46: 62 */     int imageH = this.image.getHeight();
/*  47: 63 */     int paneW = getWidth() - 2 * border;
/*  48: 64 */     int paneH = getHeight() - 2 * border;
/*  49:    */     
/*  50: 66 */     int xOffset = border;int yOffset = border;
/*  51: 67 */     int deltaW = paneW;int deltaH = paneH;
/*  52: 68 */     if (paneH * imageW > paneW * imageH)
/*  53:    */     {
/*  54: 69 */       deltaH = paneW * imageH / imageW;
/*  55: 70 */       yOffset += (paneH - deltaH) / 2;
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59: 73 */       deltaW = paneH * imageW / imageH;
/*  60: 74 */       xOffset += (paneW - deltaW) / 2;
/*  61:    */     }
/*  62: 77 */     String frameString = this.frame + " frames";
/*  63: 78 */     long sec = this.frame / MindsEyeMoviePlayer.framesPerSecond;
/*  64: 79 */     long min = sec / 60L;
/*  65: 80 */     sec %= 60L;
/*  66:    */     String timeString;
/*  67:    */     String timeString;
/*  68: 82 */     if (min == 0L) {
/*  69: 83 */       timeString = sec + " sec";
/*  70:    */     } else {
/*  71: 86 */       timeString = min + " min, " + (sec < 10L ? "0" + sec : Long.valueOf(sec)) + " sec";
/*  72:    */     }
/*  73: 88 */     FontMetrics fm = g.getFontMetrics();
/*  74: 89 */     int secW = fm.stringWidth(timeString);
/*  75: 90 */     int frameW = fm.stringWidth(frameString);
/*  76: 91 */     int secCorrection = deltaW + xOffset - secW - 10;
/*  77: 92 */     g.drawImage(this.image, xOffset, yOffset, deltaW, deltaH, this);
/*  78: 93 */     Color handle = g.getColor();
/*  79: 95 */     if (this.frame != 0L)
/*  80:    */     {
/*  81: 96 */       g.setColor(Color.WHITE);
/*  82: 97 */       g.fillRect(xOffset + 5, paneH - yOffset + fm.getDescent(), frameW + 10, -fm.getHeight());
/*  83: 98 */       g.fillRect(secCorrection - 5, paneH - yOffset + fm.getDescent(), secW + 10, -fm.getHeight());
/*  84: 99 */       g.setColor(handle);
/*  85:100 */       g.drawString(frameString, xOffset + 10, paneH - yOffset);
/*  86:101 */       g.drawString(timeString, secCorrection, paneH - yOffset);
/*  87:    */       
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:109 */       g.setColor(handle);
/*  95:    */     }
/*  96:111 */     String s = getMovieName();
/*  97:112 */     if ((s != null) && (!s.isEmpty()))
/*  98:    */     {
/*  99:114 */       int offset = fm.stringWidth(s) + 5;
/* 100:115 */       g.setColor(Color.WHITE);
/* 101:116 */       g.fillRect((paneW - offset) / 2, paneH - yOffset + fm.getDescent(), offset, -fm.getHeight());
/* 102:117 */       g.setColor(Color.BLACK);
/* 103:118 */       g.drawString(s, (paneW - offset) / 2, paneH - yOffset);
/* 104:    */     }
/* 105:120 */     g.setColor(handle);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public synchronized void setImage(Object o)
/* 109:    */   {
/* 110:124 */     if ((o instanceof BetterSignal))
/* 111:    */     {
/* 112:125 */       BetterSignal signal = (BetterSignal)o;
/* 113:126 */       setImage((BufferedImage)signal.get(0, BufferedImage.class));
/* 114:127 */       setFrame(((Integer)signal.get(1, Integer.class)).longValue());
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public synchronized void setImage(BufferedImage b)
/* 119:    */   {
/* 120:132 */     this.image = b;
/* 121:133 */     invalidate();
/* 122:134 */     repaint();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public BufferedImage getImage()
/* 126:    */   {
/* 127:138 */     return this.image;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Dimension getPreferredSize()
/* 131:    */   {
/* 132:142 */     if (this.image != null) {
/* 133:    */       try
/* 134:    */       {
/* 135:145 */         return new Dimension(this.image.getWidth(null), this.image.getHeight(null));
/* 136:    */       }
/* 137:    */       catch (Exception e)
/* 138:    */       {
/* 139:148 */         e.printStackTrace();
/* 140:    */       }
/* 141:    */     }
/* 142:151 */     return new Dimension(1000, 1000);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setFrame(long frame)
/* 146:    */   {
/* 147:157 */     this.frame = frame;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setFrameAndRepaint(long frame)
/* 151:    */   {
/* 152:161 */     this.frame = frame;
/* 153:162 */     repaint();
/* 154:    */   }
/* 155:    */   
/* 156:    */   public MovieReader getMovieReader()
/* 157:    */   {
/* 158:168 */     return this.movieReader;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getMovieName()
/* 162:    */   {
/* 163:172 */     return this.movieName;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public static void main(String[] ignore)
/* 167:    */     throws Exception
/* 168:    */   {
/* 169:176 */     MindsEyeMoviePlayer.main(ignore);
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     obsolete.mindsEye.MindsEyeMovieViewerScreen
 * JD-Core Version:    0.7.0.1
 */