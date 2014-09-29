/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.awt.Dimension;
/*   5:    */ import java.awt.Font;
/*   6:    */ import java.awt.FontMetrics;
/*   7:    */ import java.awt.Graphics;
/*   8:    */ import java.awt.image.BufferedImage;
/*   9:    */ import java.io.File;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.net.URL;
/*  12:    */ import java.util.Arrays;
/*  13:    */ import javax.imageio.ImageIO;
/*  14:    */ import javax.swing.JFrame;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import kevinWhite.pictures.Pictures;
/*  17:    */ import utils.Mark;
/*  18:    */ 
/*  19:    */ public class GenesisPanel
/*  20:    */   extends JPanel
/*  21:    */ {
/*  22: 24 */   private String filename = "";
/*  23: 26 */   private String text = "";
/*  24: 28 */   private boolean paintVert = false;
/*  25: 30 */   private boolean isPositive = true;
/*  26: 32 */   private int panelType = -1;
/*  27:    */   private Dimension initDimensions;
/*  28:    */   private String[] expertNames;
/*  29:    */   private BufferedImage go_sign;
/*  30:    */   private BufferedImage stop_sign;
/*  31:    */   private BufferedImage puzzle_piece;
/*  32:    */   private BufferedImage broken_piece;
/*  33:    */   private BufferedImage title_deed;
/*  34:    */   private BufferedImage invalid_deed;
/*  35:    */   private BufferedImage my_briefcase;
/*  36:    */   private BufferedImage your_briefcase;
/*  37:    */   
/*  38:    */   public GenesisPanel(String file)
/*  39:    */   {
/*  40: 47 */     setBackground(Color.WHITE);
/*  41: 48 */     if (file.contains(".")) {
/*  42: 49 */       this.filename = file;
/*  43:    */     } else {
/*  44: 53 */       this.text = file;
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public GenesisPanel(String panelName, boolean vertical)
/*  49:    */   {
/*  50: 75 */     setBackground(Color.WHITE);
/*  51: 76 */     this.paintVert = vertical;
/*  52: 77 */     this.expertNames = new String[] { "class", "trajectory", "path", "place", "transition", "transfer", "cause", "goal", "persuasion", "coercion", 
/*  53: 78 */       "belief", "mood", "part", "property", "possession", "job", "social", "time", "comparison", "roleframe", "image" };
/*  54: 79 */     Arrays.sort(this.expertNames);
/*  55: 80 */     this.panelType = Arrays.binarySearch(this.expertNames, panelName.toLowerCase());
/*  56:    */   }
/*  57:    */   
/*  58:    */   public Dimension getPreferredSize()
/*  59:    */   {
/*  60:106 */     int width = 200;
/*  61:107 */     int height = 200;
/*  62:108 */     this.initDimensions = new Dimension(width, height);
/*  63:109 */     return this.initDimensions;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void paintComponent(Graphics g)
/*  67:    */   {
/*  68:117 */     super.paintComponent(g);
/*  69:118 */     int height = getHeight();
/*  70:119 */     int width = getWidth();
/*  71:120 */     if ((this.filename.equals("")) && (!this.text.equals("")))
/*  72:    */     {
/*  73:121 */       Font stdFont = g.getFont().deriveFont(18.0F);
/*  74:122 */       g.setFont(stdFont);
/*  75:123 */       FontMetrics fm = g.getFontMetrics();
/*  76:124 */       int txtWidth = fm.stringWidth(this.text);
/*  77:125 */       int widthOffset = width - txtWidth;
/*  78:126 */       float fontSize = g.getFont().getSize();
/*  79:128 */       if (txtWidth > width) {
/*  80:129 */         while (txtWidth > width)
/*  81:    */         {
/*  82:130 */           fontSize -= 1.0F;
/*  83:131 */           Font tempFont = g.getFont().deriveFont(fontSize);
/*  84:132 */           g.setFont(tempFont);
/*  85:133 */           txtWidth = g.getFontMetrics().stringWidth(this.text);
/*  86:134 */           widthOffset = width - txtWidth;
/*  87:    */         }
/*  88:    */       }
/*  89:138 */       g.drawString(this.text, widthOffset / 2, height / 2);
/*  90:    */     }
/*  91:142 */     else if (!this.filename.equals(""))
/*  92:    */     {
/*  93:    */       try
/*  94:    */       {
/*  95:144 */         BufferedImage img = ImageIO.read(new File(this.filename));
/*  96:145 */         paintAux(g, img, width, height, 0, 0);
/*  97:    */       }
/*  98:    */       catch (IOException e)
/*  99:    */       {
/* 100:148 */         String tempTxt = "No image or text available.";
/* 101:149 */         FontMetrics fm = g.getFontMetrics();
/* 102:150 */         int txtWidth = fm.stringWidth(tempTxt);
/* 103:151 */         int offset = width - txtWidth;
/* 104:152 */         g.drawString(tempTxt, offset / 2, height / 2);
/* 105:    */       }
/* 106:    */     }
/* 107:156 */     else if (this.panelType != -1)
/* 108:    */     {
/* 109:157 */       switch (this.panelType)
/* 110:    */       {
/* 111:    */       case 5: 
/* 112:160 */         paintAux(g, getSign(this.isPositive), width, height, 0, 0);
/* 113:161 */         break;
/* 114:    */       case 7: 
/* 115:164 */         paintAux(g, getBriefcase(this.isPositive), width, height, 0, 0);
/* 116:165 */         break;
/* 117:    */       case 9: 
/* 118:168 */         paintAux(g, getPiece(this.isPositive), width, height, 0, 0);
/* 119:169 */         break;
/* 120:    */       case 13: 
/* 121:172 */         paintAux(g, getDeed(this.isPositive), width, height, 0, 0);
/* 122:173 */         break;
/* 123:    */       case 6: 
/* 124:    */       case 8: 
/* 125:    */       case 10: 
/* 126:    */       case 11: 
/* 127:    */       case 12: 
/* 128:    */       default: 
/* 129:176 */         Mark.say(new Object[] {this.expertNames[this.panelType] + "expert does not use a GenesisPanel." });
/* 130:177 */         String tempTxt = "No image or text available.";
/* 131:178 */         FontMetrics fm = g.getFontMetrics();
/* 132:179 */         int txtWidth = fm.stringWidth(tempTxt);
/* 133:180 */         int offset = width - txtWidth;
/* 134:181 */         g.drawString(tempTxt, offset / 2, height / 2);
/* 135:    */       }
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   private void paintAux(Graphics g, BufferedImage image, int width, int height, int globalXOffset, int globalYOffset)
/* 140:    */   {
/* 141:204 */     double rPanel = width / height;
/* 142:205 */     double rImage = image.getWidth() / image.getHeight();
/* 143:206 */     double scale = 0.0D;
/* 144:207 */     if (!this.paintVert)
/* 145:    */     {
/* 146:208 */       scale = width / image.getWidth();
/* 147:209 */       if (rImage < rPanel) {
/* 148:210 */         scale = height / image.getHeight();
/* 149:    */       }
/* 150:    */     }
/* 151:    */     else
/* 152:    */     {
/* 153:215 */       scale = height / image.getHeight();
/* 154:216 */       if (rImage > rPanel) {
/* 155:217 */         scale = width / image.getWidth();
/* 156:    */       }
/* 157:    */     }
/* 158:220 */     int imageWidth = (int)(scale * image.getWidth());
/* 159:221 */     int imageHeight = (int)(scale * image.getHeight());
/* 160:    */     
/* 161:223 */     int localXOffset = (width - imageWidth) / 2;
/* 162:224 */     int localYOffset = (height - imageHeight) / 2;
/* 163:    */     
/* 164:226 */     g.drawImage(image, globalXOffset + localXOffset, globalYOffset + localYOffset, imageWidth, imageHeight, null);
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected Dimension getInitDimensions()
/* 168:    */   {
/* 169:230 */     return this.initDimensions;
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected void displayText(String chars)
/* 173:    */   {
/* 174:240 */     this.filename = "";
/* 175:241 */     this.text = chars;
/* 176:242 */     repaint();
/* 177:    */   }
/* 178:    */   
/* 179:    */   protected void setDesirability(boolean want)
/* 180:    */   {
/* 181:254 */     this.isPositive = want;
/* 182:255 */     repaint();
/* 183:    */   }
/* 184:    */   
/* 185:    */   private BufferedImage getSign(boolean want)
/* 186:    */   {
/* 187:264 */     if (want) {
/* 188:265 */       return getGo_Sign();
/* 189:    */     }
/* 190:268 */     return getStop_Sign();
/* 191:    */   }
/* 192:    */   
/* 193:    */   private BufferedImage getPiece(boolean want)
/* 194:    */   {
/* 195:273 */     if (want) {
/* 196:274 */       return getPuzzle_Piece();
/* 197:    */     }
/* 198:277 */     return getBroken_Piece();
/* 199:    */   }
/* 200:    */   
/* 201:    */   private BufferedImage getDeed(boolean want)
/* 202:    */   {
/* 203:282 */     if (want) {
/* 204:283 */       return getTitle_Deed();
/* 205:    */     }
/* 206:286 */     return getInvalid_Deed();
/* 207:    */   }
/* 208:    */   
/* 209:    */   private BufferedImage getBriefcase(boolean want)
/* 210:    */   {
/* 211:291 */     if (want) {
/* 212:292 */       return getMy_Briefcase();
/* 213:    */     }
/* 214:295 */     return getYour_Briefcase();
/* 215:    */   }
/* 216:    */   
/* 217:    */   public BufferedImage getGo_Sign()
/* 218:    */   {
/* 219:302 */     if (this.go_sign == null)
/* 220:    */     {
/* 221:303 */       URL location = Pictures.class.getResource("go_sign.jpg");
/* 222:    */       try
/* 223:    */       {
/* 224:305 */         this.go_sign = ImageIO.read(location);
/* 225:    */       }
/* 226:    */       catch (IOException e)
/* 227:    */       {
/* 228:308 */         Mark.say(new Object[] {"go_sign.jpg was not found." });
/* 229:309 */         e.printStackTrace();
/* 230:    */       }
/* 231:    */     }
/* 232:312 */     return this.go_sign;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public BufferedImage getStop_Sign()
/* 236:    */   {
/* 237:318 */     if (this.stop_sign == null)
/* 238:    */     {
/* 239:319 */       URL location = Pictures.class.getResource("stop_sign.jpg");
/* 240:    */       try
/* 241:    */       {
/* 242:321 */         this.stop_sign = ImageIO.read(location);
/* 243:    */       }
/* 244:    */       catch (IOException e)
/* 245:    */       {
/* 246:324 */         Mark.say(new Object[] {"stop_sign.jpg was not found." });
/* 247:325 */         e.printStackTrace();
/* 248:    */       }
/* 249:    */     }
/* 250:328 */     return this.stop_sign;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public BufferedImage getPuzzle_Piece()
/* 254:    */   {
/* 255:334 */     if (this.puzzle_piece == null)
/* 256:    */     {
/* 257:335 */       URL location = Pictures.class.getResource("puzzle_piece.jpg");
/* 258:    */       try
/* 259:    */       {
/* 260:337 */         this.puzzle_piece = ImageIO.read(location);
/* 261:    */       }
/* 262:    */       catch (IOException e)
/* 263:    */       {
/* 264:340 */         Mark.say(new Object[] {"puzzle_piece.jpg was not found." });
/* 265:341 */         e.printStackTrace();
/* 266:    */       }
/* 267:    */     }
/* 268:344 */     return this.puzzle_piece;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public BufferedImage getBroken_Piece()
/* 272:    */   {
/* 273:350 */     if (this.broken_piece == null)
/* 274:    */     {
/* 275:351 */       URL location = Pictures.class.getResource("broken_puzzle_piece.jpg");
/* 276:    */       try
/* 277:    */       {
/* 278:353 */         this.broken_piece = ImageIO.read(location);
/* 279:    */       }
/* 280:    */       catch (IOException e)
/* 281:    */       {
/* 282:356 */         Mark.say(new Object[] {"broken_puzzle_piece.jpg was not found." });
/* 283:357 */         e.printStackTrace();
/* 284:    */       }
/* 285:    */     }
/* 286:360 */     return this.broken_piece;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public BufferedImage getTitle_Deed()
/* 290:    */   {
/* 291:366 */     if (this.title_deed == null)
/* 292:    */     {
/* 293:367 */       URL location = Pictures.class.getResource("title_deed.jpg");
/* 294:    */       try
/* 295:    */       {
/* 296:369 */         this.title_deed = ImageIO.read(location);
/* 297:    */       }
/* 298:    */       catch (IOException e)
/* 299:    */       {
/* 300:372 */         Mark.say(new Object[] {"title_deed.jpg was not found." });
/* 301:373 */         e.printStackTrace();
/* 302:    */       }
/* 303:    */     }
/* 304:376 */     return this.title_deed;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public BufferedImage getInvalid_Deed()
/* 308:    */   {
/* 309:382 */     if (this.invalid_deed == null)
/* 310:    */     {
/* 311:383 */       URL location = Pictures.class.getResource("invalid_deed.jpg");
/* 312:    */       try
/* 313:    */       {
/* 314:385 */         this.invalid_deed = ImageIO.read(location);
/* 315:    */       }
/* 316:    */       catch (IOException e)
/* 317:    */       {
/* 318:388 */         Mark.say(new Object[] {"invalid_deed.jpg was not found." });
/* 319:389 */         e.printStackTrace();
/* 320:    */       }
/* 321:    */     }
/* 322:392 */     return this.invalid_deed;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public BufferedImage getMy_Briefcase()
/* 326:    */   {
/* 327:398 */     if (this.my_briefcase == null)
/* 328:    */     {
/* 329:399 */       URL location = Pictures.class.getResource("my_briefcase.jpg");
/* 330:    */       try
/* 331:    */       {
/* 332:401 */         this.my_briefcase = ImageIO.read(location);
/* 333:    */       }
/* 334:    */       catch (IOException e)
/* 335:    */       {
/* 336:404 */         Mark.say(new Object[] {"my_briefcase.jpg was not found." });
/* 337:405 */         e.printStackTrace();
/* 338:    */       }
/* 339:    */     }
/* 340:408 */     return this.my_briefcase;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public BufferedImage getYour_Briefcase()
/* 344:    */   {
/* 345:414 */     if (this.your_briefcase == null)
/* 346:    */     {
/* 347:415 */       URL location = Pictures.class.getResource("your_briefcase.jpg");
/* 348:    */       try
/* 349:    */       {
/* 350:417 */         this.your_briefcase = ImageIO.read(location);
/* 351:    */       }
/* 352:    */       catch (IOException e)
/* 353:    */       {
/* 354:420 */         Mark.say(new Object[] {"your_briefcase.jpg was not found." });
/* 355:421 */         e.printStackTrace();
/* 356:    */       }
/* 357:    */     }
/* 358:424 */     return this.your_briefcase;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public static void main(String[] args)
/* 362:    */   {
/* 363:433 */     GenesisPanel horizPanel = new GenesisPanel("goal", false);
/* 364:434 */     horizPanel.setDesirability(true);
/* 365:435 */     JFrame hf = new JFrame("hDemo");
/* 366:436 */     hf.setDefaultCloseOperation(3);
/* 367:437 */     hf.add(horizPanel);
/* 368:438 */     hf.pack();
/* 369:439 */     hf.setVisible(true);
/* 370:    */     
/* 371:441 */     String file = "source/kevinWhite/pictures/title_deed.jpg";
/* 372:442 */     String invalidFile = "source/kevinWhite/pictures/Corn.jpg";
/* 373:443 */     String text = "Aisle 10";
/* 374:444 */     GenesisPanel vertPanel = new GenesisPanel(invalidFile);
/* 375:445 */     JFrame vf = new JFrame("vDemo");
/* 376:446 */     vf.setDefaultCloseOperation(3);
/* 377:447 */     vf.add(vertPanel);
/* 378:448 */     vf.pack();
/* 379:449 */     vf.setVisible(true);
/* 380:    */   }
/* 381:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.GenesisPanel
 * JD-Core Version:    0.7.0.1
 */