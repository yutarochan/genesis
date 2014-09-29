/*   1:    */ package adamKraft.videoUtils;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.Dimension;
/*   6:    */ import java.awt.Graphics;
/*   7:    */ import java.awt.event.WindowAdapter;
/*   8:    */ import java.awt.event.WindowEvent;
/*   9:    */ import java.awt.image.BufferedImage;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import javax.swing.JFrame;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class MovieReader
/*  16:    */   extends UsesNativeVideoLib
/*  17:    */ {
/*  18:    */   public static final int FULL_RESOLUTION = 0;
/*  19:    */   public static final int QUARTER_RESOLUTION = 1;
/*  20:    */   public static final int SIXTEENTH_RESOLUTION = 2;
/*  21:    */   public static final int SIXTYFOURTH_RESOLUTION = 3;
/*  22:    */   public static final int CAMERA_ZERO = 0;
/*  23:    */   public static final int CAMERA_ONE = 1;
/*  24: 36 */   private static int counter = 0;
/*  25: 38 */   private boolean error = false;
/*  26:    */   private int id;
/*  27:    */   private int width;
/*  28:    */   private int height;
/*  29:    */   private int channels;
/*  30:    */   
/*  31:    */   protected static synchronized int getCounter()
/*  32:    */   {
/*  33: 41 */     return counter++;
/*  34:    */   }
/*  35:    */   
/*  36: 52 */   private int pyrDown = 1;
/*  37:    */   private boolean isCam;
/*  38:    */   private int camDevice;
/*  39:    */   private String fileName;
/*  40: 60 */   protected long frameCount = 0L;
/*  41:    */   private boolean streamEnded;
/*  42:    */   
/*  43:    */   public synchronized void setScaleDown(int scaleDown)
/*  44:    */   {
/*  45: 65 */     this.pyrDown = scaleDown;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public MovieReader()
/*  49:    */     throws IOException
/*  50:    */   {}
/*  51:    */   
/*  52:    */   public MovieReader(int deviceID)
/*  53:    */     throws IOException
/*  54:    */   {
/*  55: 73 */     this(deviceID, 0);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public MovieReader(int deviceID, int scaleDown)
/*  59:    */     throws IOException
/*  60:    */   {
/*  61: 77 */     this(true, deviceID, null, scaleDown);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public MovieReader(String fileName)
/*  65:    */     throws IOException
/*  66:    */   {
/*  67: 81 */     this(fileName, 0);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public MovieReader(String fileName, int scaleDown)
/*  71:    */     throws IOException
/*  72:    */   {
/*  73: 85 */     this(false, -1, fileName, scaleDown);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public MovieReader(boolean cam, int device, String fname, int scaleDown)
/*  77:    */     throws IOException
/*  78:    */   {
/*  79: 91 */     this.id = getCounter();
/*  80: 92 */     this.isCam = cam;
/*  81: 93 */     this.camDevice = device;
/*  82: 94 */     this.fileName = fname;
/*  83: 95 */     this.pyrDown = scaleDown;
/*  84: 96 */     init();
/*  85:    */   }
/*  86:    */   
/*  87:    */   private void init()
/*  88:    */     throws IOException
/*  89:    */   {
/*  90:100 */     synchronized (MovieReader.class)
/*  91:    */     {
/*  92:101 */       clearExceptions();
/*  93:102 */       nativeCaptureAllocate(this.id, this.fileName, this.isCam, this.camDevice);
/*  94:103 */       this.frameCount = 0L;
/*  95:104 */       this.streamEnded = false;
/*  96:105 */       if (this.error) {
/*  97:106 */         throw new IOException("could not open " + this.fileName);
/*  98:    */       }
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   protected void clearExceptions()
/* 103:    */   {
/* 104:112 */     this.error = false;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void close()
/* 108:    */   {
/* 109:116 */     nativeCaptureFree(this.id);
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected void finalize()
/* 113:    */   {
/* 114:120 */     close();
/* 115:    */   }
/* 116:    */   
/* 117:    */   native byte[] nativeGetNextFrame(int paramInt);
/* 118:    */   
/* 119:    */   native boolean nativePrefetchFrame(int paramInt);
/* 120:    */   
/* 121:    */   native void nativeSkipFrame(int paramInt);
/* 122:    */   
/* 123:    */   native void nativeSeekToFrame(int paramInt1, int paramInt2);
/* 124:    */   
/* 125:    */   native void nativeCaptureAllocate(int paramInt1, String paramString, boolean paramBoolean, int paramInt2);
/* 126:    */   
/* 127:    */   native void nativeCaptureFree(int paramInt);
/* 128:    */   
/* 129:    */   public byte[] getNextRawFrame()
/* 130:    */     throws IOException
/* 131:    */   {
/* 132:136 */     synchronized (MovieReader.class)
/* 133:    */     {
/* 134:137 */       if (this.streamEnded) {
/* 135:138 */         return null;
/* 136:    */       }
/* 137:141 */       if (!nativePrefetchFrame(this.id))
/* 138:    */       {
/* 139:142 */         this.streamEnded = true;
/* 140:143 */         return getNextRawFrame();
/* 141:    */       }
/* 142:146 */       if (this.error) {
/* 143:147 */         throw new IOException("error getting a frame (stage 1)");
/* 144:    */       }
/* 145:149 */       byte[] result = nativeGetNextFrame(this.id);
/* 146:150 */       if (this.error) {
/* 147:151 */         throw new IOException("error getting a frame (stage 2)");
/* 148:    */       }
/* 149:153 */       if ((result == null) || (this.width == 0) || (this.height == 0)) {
/* 150:154 */         throw new IOException(
/* 151:155 */           "An error happened that was outside the opencv exception-handling mechanism. HIGHGUI error maybe? hardware sadness? Are you trying to run in a VM?");
/* 152:    */       }
/* 153:157 */       this.frameCount += 1L;
/* 154:158 */       return result;
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected boolean skipFrame()
/* 159:    */   {
/* 160:165 */     if (!nativePrefetchFrame(this.id)) {
/* 161:166 */       return false;
/* 162:    */     }
/* 163:168 */     nativeSkipFrame(this.id);
/* 164:169 */     this.frameCount += 1L;
/* 165:170 */     return true;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void seek(long frameNumber)
/* 169:    */     throws IOException
/* 170:    */   {
/* 171:181 */     if ((this.isCam) && (frameNumber < this.frameCount)) {
/* 172:182 */       throw new IOException("you can't seek backward in a video stream from a camera.");
/* 173:    */     }
/* 174:184 */     synchronized (MovieReader.class)
/* 175:    */     {
/* 176:185 */       nativeSeekToFrame(this.id, (int)frameNumber);
/* 177:189 */       if (!this.error)
/* 178:    */       {
/* 179:190 */         this.frameCount = frameNumber;
/* 180:    */       }
/* 181:    */       else
/* 182:    */       {
/* 183:193 */         while (frameNumber > this.frameCount) {
/* 184:195 */           if (!skipFrame())
/* 185:    */           {
/* 186:196 */             System.out.println(this.frameCount);
/* 187:197 */             throw new IOException("attempt to seek past end of stream!");
/* 188:    */           }
/* 189:    */         }
/* 190:200 */         if (frameNumber < this.frameCount)
/* 191:    */         {
/* 192:201 */           nativeCaptureFree(this.id);
/* 193:202 */           init();
/* 194:203 */           seek(frameNumber);
/* 195:    */         }
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   public synchronized BufferedImage getNextFrame()
/* 201:    */     throws IOException
/* 202:    */   {
/* 203:210 */     byte[] data = getNextRawFrame();
/* 204:211 */     if (data == null) {
/* 205:212 */       return null;
/* 206:    */     }
/* 207:216 */     BufferedImage b = null;
/* 208:219 */     if (this.channels == 3) {
/* 209:221 */       b = new BufferedImage(this.width, this.height, 2);
/* 210:223 */     } else if (this.channels == 1) {
/* 211:225 */       b = new BufferedImage(this.width, this.height, 10);
/* 212:    */     } else {
/* 213:229 */       throw new IOException("can't deal with " + this.channels + " color channels. get the raw data and process it yourself.");
/* 214:    */     }
/* 215:237 */     for (int y = 0; y < this.height; y++) {
/* 216:238 */       for (int x = 0; x < this.width; x++) {
/* 217:245 */         if (this.channels == 3)
/* 218:    */         {
/* 219:246 */           int c = data[(this.channels * y * this.width + this.channels * x)] & 0xFF;
/* 220:247 */           c |= (data[(this.channels * y * this.width + this.channels * x + 1)] & 0xFF) << 8;
/* 221:248 */           c |= (data[(this.channels * y * this.width + this.channels * x + 2)] & 0xFF) << 16;
/* 222:249 */           c |= 0xFF000000;
/* 223:250 */           b.setRGB(x, y, c);
/* 224:    */         }
/* 225:252 */         else if (this.channels == 1)
/* 226:    */         {
/* 227:253 */           int c = data[(this.channels * y * this.width + this.channels * x + 2)] & 0xFF;
/* 228:254 */           int d = c | c << 8 | c << 16 | 0xFF000000;
/* 229:255 */           b.setRGB(x, y, d);
/* 230:    */         }
/* 231:    */       }
/* 232:    */     }
/* 233:261 */     return b;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public long getFrameCount()
/* 237:    */   {
/* 238:265 */     return this.frameCount;
/* 239:    */   }
/* 240:    */   
/* 241:    */   protected static class TestApp
/* 242:    */     extends Component
/* 243:    */   {
/* 244:    */     BufferedImage img;
/* 245:    */     
/* 246:    */     public synchronized void paint(Graphics g)
/* 247:    */     {
/* 248:272 */       g.drawImage(this.img, 0, 0, null);
/* 249:    */     }
/* 250:    */     
/* 251:    */     public synchronized void setImg(BufferedImage b)
/* 252:    */     {
/* 253:276 */       this.img = b;
/* 254:277 */       invalidate();
/* 255:278 */       repaint();
/* 256:    */     }
/* 257:    */     
/* 258:    */     public TestApp(BufferedImage b)
/* 259:    */     {
/* 260:282 */       this.img = b;
/* 261:    */     }
/* 262:    */     
/* 263:    */     public Dimension getPreferredSize()
/* 264:    */     {
/* 265:286 */       if (this.img == null) {
/* 266:287 */         return new Dimension(100, 100);
/* 267:    */       }
/* 268:290 */       return new Dimension(this.img.getWidth(null), this.img.getHeight(null));
/* 269:    */     }
/* 270:    */   }
/* 271:    */   
/* 272:    */   public static void main(String[] args)
/* 273:    */     throws Exception
/* 274:    */   {
/* 275:306 */     MovieReader m = new MovieReader("C:\\Users\\phw\\DARPA_VIDEO_CACHE\\STOPS_20111214_CR1_26_C6.mov");
/* 276:    */     
/* 277:308 */     Mark.say(new Object[] {m.getPath() });
/* 278:    */     
/* 279:310 */     Mark.say(new Object[] {Integer.valueOf(m.id) });
/* 280:    */     
/* 281:312 */     byte[] result = m.getNextRawFrame();
/* 282:    */     
/* 283:    */ 
/* 284:    */ 
/* 285:    */ 
/* 286:    */ 
/* 287:318 */     BufferedImage b = m.getNextFrame();
/* 288:319 */     JFrame f = new JFrame("Image reader test app");
/* 289:320 */     f.addWindowListener(new WindowAdapter()
/* 290:    */     {
/* 291:    */       public void windowClosing(WindowEvent e)
/* 292:    */       {
/* 293:322 */         System.exit(0);
/* 294:    */       }
/* 295:328 */     });
/* 296:329 */     Mark.say(new Object[] {"Tick" });
/* 297:330 */     b = m.getNextFrame();
/* 298:331 */     if (b != null)
/* 299:    */     {
/* 300:332 */       TestApp a = new TestApp(b);
/* 301:333 */       f.getContentPane().add(a);
/* 302:334 */       f.pack();
/* 303:335 */       f.setVisible(true);
/* 304:336 */       a.setImg(b);
/* 305:    */     }
/* 306:    */     else
/* 307:    */     {
/* 308:340 */       Mark.say(new Object[] {"No next frame returned" });
/* 309:    */     }
/* 310:    */   }
/* 311:    */   
/* 312:    */   public String getPath()
/* 313:    */   {
/* 314:375 */     return this.fileName;
/* 315:    */   }
/* 316:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.videoUtils.MovieReader
 * JD-Core Version:    0.7.0.1
 */