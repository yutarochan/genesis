/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.ReflectionLevelMemory;
/*   4:    */ import carynKrakauer.StudyResults;
/*   5:    */ import carynKrakauer.generatedPatterns.ConceptPatternMatchWrapper;
/*   6:    */ import java.awt.Color;
/*   7:    */ import java.awt.Font;
/*   8:    */ import java.awt.Graphics;
/*   9:    */ import java.awt.Graphics2D;
/*  10:    */ import java.awt.event.MouseEvent;
/*  11:    */ import java.awt.event.MouseMotionListener;
/*  12:    */ import java.awt.geom.Rectangle2D;
/*  13:    */ import java.text.DecimalFormat;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.Set;
/*  18:    */ import matthewFay.Utilities.Pair;
/*  19:    */ import utils.Mark;
/*  20:    */ 
/*  21:    */ public class GeneratedGridVisualPanel
/*  22:    */   extends MatrixJPanel
/*  23:    */   implements MouseMotionListener
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   private boolean grayscale;
/*  27:    */   private boolean triangle;
/*  28:    */   private boolean vectorMatch;
/*  29: 30 */   private int mouse_x = -1;
/*  30: 31 */   private int mouse_y = -1;
/*  31:    */   private ReflectionLevelMemory memory;
/*  32:    */   private int currentSize;
/*  33:    */   
/*  34:    */   public GeneratedGridVisualPanel(ReflectionLevelMemory memory, int defaultSize, boolean vectorMatch, boolean grayscale, boolean triangle)
/*  35:    */   {
/*  36: 39 */     this.memory = memory;
/*  37: 40 */     addMouseMotionListener(this);
/*  38: 41 */     this.currentSize = defaultSize;
/*  39:    */     
/*  40: 43 */     this.grayscale = grayscale;
/*  41: 44 */     this.triangle = triangle;
/*  42: 45 */     this.vectorMatch = vectorMatch;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void drawPlotComparisons(int size)
/*  46:    */   {
/*  47: 49 */     this.currentSize = size;
/*  48:    */     
/*  49: 51 */     removeAll();
/*  50:    */     
/*  51: 53 */     repaint();
/*  52: 54 */     updateUI();
/*  53:    */   }
/*  54:    */   
/*  55:    */   private double calcStandardDev()
/*  56:    */   {
/*  57: 59 */     double total = 0.0D;
/*  58: 60 */     int num = 0;
/*  59: 61 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/*  60:    */     {
/*  61: 62 */       String story1 = (String)this.memory.getStoryNames().get(j);
/*  62: 63 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/*  63:    */       {
/*  64: 64 */         String story2 = (String)this.memory.getStoryNames().get(i);
/*  65: 65 */         int studyResult = ((Integer)((HashMap)StudyResults.getStudyResults().get(story1)).get(story2)).intValue();
/*  66: 66 */         double studyVal = studyResult / 11.0F / StudyResults.getMaxStudyResult();
/*  67: 67 */         double val = 0.0D;
/*  68: 68 */         if (this.vectorMatch) {
/*  69: 69 */           val = this.memory.getPlotUnitComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j), this.currentSize).getValue();
/*  70:    */         } else {
/*  71: 72 */           val = this.memory.getPlotUnitComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j), this.currentSize).getMatchSize();
/*  72:    */         }
/*  73: 74 */         val /= getMaxMatch();
/*  74:    */         
/*  75:    */ 
/*  76: 77 */         double diff = Math.pow(val - studyVal, 2.0D);
/*  77: 78 */         total += diff;
/*  78: 79 */         num++;
/*  79:    */       }
/*  80:    */     }
/*  81: 82 */     return Math.sqrt(total / num);
/*  82:    */   }
/*  83:    */   
/*  84:    */   private HashMap<String, HashMap<String, Integer>> getRanks()
/*  85:    */   {
/*  86: 86 */     HashMap<String, ArrayList<Pair<Double, String>>> ranks = new HashMap();
/*  87: 87 */     for (int i = 0; i < this.memory.getStoryCount(); i++)
/*  88:    */     {
/*  89: 88 */       String story1 = (String)this.memory.getStoryNames().get(i);
/*  90: 89 */       story1Ranks = new ArrayList();
/*  91: 90 */       ranks.put(story1, story1Ranks);
/*  92: 91 */       for (int j = 0; j < this.memory.getStoryCount(); j++)
/*  93:    */       {
/*  94: 92 */         String story2 = (String)this.memory.getStoryNames().get(j);
/*  95:    */         
/*  96: 94 */         double val = 0.0D;
/*  97: 95 */         if (story1.equals(story2))
/*  98:    */         {
/*  99: 96 */           val = 1.0D;
/* 100:    */         }
/* 101: 98 */         else if (this.vectorMatch)
/* 102:    */         {
/* 103: 99 */           Mark.say(new Object[] {story1 + " " + story2 + " " + this.currentSize });
/* 104:100 */           val = this.memory.getPlotUnitComparison(story1, story2, this.currentSize).getValue();
/* 105:    */         }
/* 106:    */         else
/* 107:    */         {
/* 108:103 */           val = this.memory.getPlotUnitComparison(story1, story2, this.currentSize).getMatchSize();
/* 109:    */         }
/* 110:106 */         boolean found = false;
/* 111:107 */         for (int k = 0; k < story1Ranks.size(); k++) {
/* 112:108 */           if (((Double)((Pair)story1Ranks.get(k)).a).doubleValue() < val)
/* 113:    */           {
/* 114:109 */             story1Ranks.add(k, new Pair(Double.valueOf(val), story2));
/* 115:110 */             found = true;
/* 116:111 */             break;
/* 117:    */           }
/* 118:    */         }
/* 119:114 */         if (!found) {
/* 120:115 */           story1Ranks.add(new Pair(Double.valueOf(val), story2));
/* 121:    */         }
/* 122:    */       }
/* 123:    */     }
/* 124:120 */     HashMap<String, HashMap<String, Integer>> outRanks = new HashMap();
/* 125:    */     ArrayList<Pair<Double, String>> ordering;
/* 126:    */     int i;
/* 127:122 */     for (ArrayList<Pair<Double, String>> story1Ranks = ranks.keySet().iterator(); story1Ranks.hasNext(); i < ordering.size())
/* 128:    */     {
/* 129:122 */       String story1 = (String)story1Ranks.next();
/* 130:123 */       outRanks.put(story1, new HashMap());
/* 131:124 */       ordering = (ArrayList)ranks.get(story1);
/* 132:125 */       int current_rank = 0;
/* 133:126 */       double current_val = -1.0D;
/* 134:127 */       i = 0; continue;
/* 135:128 */       if (((Double)((Pair)ordering.get(i)).a).doubleValue() != current_val)
/* 136:    */       {
/* 137:129 */         current_rank = i;
/* 138:130 */         current_val = ((Double)((Pair)ordering.get(i)).a).doubleValue();
/* 139:    */       }
/* 140:132 */       ((HashMap)outRanks.get(story1)).put((String)((Pair)ordering.get(i)).b, Integer.valueOf(current_rank));i++;
/* 141:    */     }
/* 142:136 */     return outRanks;
/* 143:    */   }
/* 144:    */   
/* 145:    */   private double getRankSD()
/* 146:    */   {
/* 147:140 */     HashMap<String, HashMap<String, Integer>> ranks = getRanks();
/* 148:    */     
/* 149:142 */     double total = 0.0D;
/* 150:143 */     int num = 0;
/* 151:144 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/* 152:    */     {
/* 153:145 */       String story1 = (String)this.memory.getStoryNames().get(j);
/* 154:146 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/* 155:    */       {
/* 156:147 */         String story2 = (String)this.memory.getStoryNames().get(i);
/* 157:    */         
/* 158:149 */         int myRank = ((Integer)((HashMap)ranks.get(story1)).get(story2)).intValue();
/* 159:150 */         int studyRank = StudyResults.getRank(story1, story2);
/* 160:    */         
/* 161:152 */         double diff = Math.pow(myRank - studyRank, 2.0D);
/* 162:153 */         total += diff;
/* 163:154 */         num++;
/* 164:    */       }
/* 165:    */     }
/* 166:158 */     return Math.sqrt(total / num);
/* 167:    */   }
/* 168:    */   
/* 169:    */   private double getMaxMatch()
/* 170:    */   {
/* 171:162 */     double maxMatch = -1.0D;
/* 172:163 */     for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 173:164 */       for (int j = i + 1; j < this.memory.getStoryCount(); j++)
/* 174:    */       {
/* 175:165 */         ConceptPatternMatchWrapper matchWrapper = this.memory.getPlotUnitComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j), this.currentSize);
/* 176:166 */         if (matchWrapper != null)
/* 177:    */         {
/* 178:169 */           double matchSize = matchWrapper.getMatchSize();
/* 179:170 */           if (this.vectorMatch) {
/* 180:171 */             matchSize = matchWrapper.getValue();
/* 181:    */           }
/* 182:173 */           if (matchSize > maxMatch) {
/* 183:174 */             maxMatch = matchSize;
/* 184:    */           }
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:178 */     return maxMatch;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void paint(Graphics g)
/* 192:    */   {
/* 193:183 */     super.paintComponent(g);
/* 194:    */     
/* 195:185 */     setBackground(Color.white);
/* 196:    */     
/* 197:    */ 
/* 198:188 */     paintComparisons(g);
/* 199:    */   }
/* 200:    */   
/* 201:    */   private void paintComparisons(Graphics g)
/* 202:    */   {
/* 203:192 */     int width = getWidth();
/* 204:193 */     int height = getHeight();
/* 205:    */     
/* 206:195 */     Font font = new Font("Dialog", 0, 12);
/* 207:196 */     Graphics2D g2 = (Graphics2D)g;
/* 208:197 */     int x_start = 0;
/* 209:199 */     for (String name : this.memory.getStoryNames())
/* 210:    */     {
/* 211:200 */       Rectangle2D bounds = font.getStringBounds(name, g2.getFontRenderContext());
/* 212:201 */       x_start = (int)Math.max(x_start, bounds.getWidth());
/* 213:    */     }
/* 214:204 */     DecimalFormat df = new DecimalFormat("#.##");
/* 215:205 */     g.setColor(Color.black);
/* 216:206 */     double stdDevError = getRankSD();
/* 217:207 */     writeStdDevError(g, df, stdDevError);
/* 218:    */     
/* 219:209 */     int y_start = 20;
/* 220:210 */     int word_width = (int)((width - x_start) / this.memory.getStoryCount());
/* 221:211 */     int word_height = (int)((height - 40) / this.memory.getStoryCount());
/* 222:212 */     int x_pos = x_start;
/* 223:213 */     int y_pos = y_start;
/* 224:    */     
/* 225:215 */     double widestName = this.memory.getWidestNameWidth(font, g2.getFontRenderContext());
/* 226:216 */     if (widestName >= word_width * 2)
/* 227:    */     {
/* 228:217 */       g.setFont(new Font("Dialog", 1, 16));
/* 229:218 */       for (int i = 1; i <= this.memory.getStoryCount(); i++)
/* 230:    */       {
/* 231:219 */         g.drawString(i, (int)(x_pos + word_width * 0.5F), y_pos);
/* 232:220 */         x_pos += word_width;
/* 233:    */       }
/* 234:223 */       g.setFont(new Font("Dialog", 1, 12));
/* 235:224 */       x_pos = 0;
/* 236:225 */       y_pos = y_start + word_height;
/* 237:226 */       int index = 1;
/* 238:227 */       for (String storyName : this.memory.getStoryNames())
/* 239:    */       {
/* 240:228 */         g.drawString(index + ": " + storyName, x_pos, y_pos);
/* 241:229 */         y_pos += word_height;
/* 242:230 */         index++;
/* 243:    */       }
/* 244:    */     }
/* 245:    */     else
/* 246:    */     {
/* 247:234 */       g.setFont(new Font("Dialog", 0, 12));
/* 248:235 */       int index = 0;
/* 249:236 */       for (String storyName : this.memory.getStoryNames())
/* 250:    */       {
/* 251:237 */         g.drawString(storyName, x_pos, y_pos - y_pos / 2 * (index % 2));
/* 252:238 */         x_pos += word_width;
/* 253:239 */         index++;
/* 254:    */       }
/* 255:242 */       x_pos = 0;
/* 256:243 */       y_pos = y_start + word_height;
/* 257:244 */       for (String storyName : this.memory.getStoryNames())
/* 258:    */       {
/* 259:245 */         g.drawString(storyName, x_pos, y_pos);
/* 260:246 */         y_pos += word_height;
/* 261:    */       }
/* 262:    */     }
/* 263:250 */     double maxMatch = getMaxMatch();
/* 264:252 */     if (maxMatch == -1.0D)
/* 265:    */     {
/* 266:253 */       for (int i = 0; i < this.memory.getStoryCount(); i++)
/* 267:    */       {
/* 268:254 */         if (this.grayscale) {
/* 269:255 */           g.setColor(Color.white);
/* 270:    */         } else {
/* 271:258 */           g.setColor(Color.green);
/* 272:    */         }
/* 273:260 */         g.fillRect(x_start + i * word_width, y_start + i * word_height, word_width, word_height);
/* 274:    */         
/* 275:262 */         g.setColor(Color.black);
/* 276:263 */         g.drawRect(x_start + i * word_width, y_start + i * word_height, word_width, word_height);
/* 277:    */       }
/* 278:265 */       return;
/* 279:    */     }
/* 280:269 */     for (int j = 0; j < this.memory.getStoryCount(); j++) {
/* 281:270 */       for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 282:271 */         if ((!this.triangle) || (i <= j)) {
/* 283:275 */           if (i == j)
/* 284:    */           {
/* 285:276 */             if (this.grayscale) {
/* 286:277 */               g.setColor(Color.white);
/* 287:    */             } else {
/* 288:280 */               g.setColor(Color.white);
/* 289:    */             }
/* 290:282 */             g.fillRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 291:    */             
/* 292:284 */             g.setColor(Color.black);
/* 293:285 */             g.drawRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 294:    */           }
/* 295:    */           else
/* 296:    */           {
/* 297:289 */             ConceptPatternMatchWrapper matchWrapper = this.memory.getPlotUnitComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j), this.currentSize);
/* 298:291 */             if (matchWrapper != null)
/* 299:    */             {
/* 300:294 */               double match = -1.0D;
/* 301:295 */               if (!this.vectorMatch) {
/* 302:296 */                 match = matchWrapper.getMatchSize() / (float)maxMatch;
/* 303:    */               } else {
/* 304:299 */                 match = matchWrapper.getValue() / maxMatch;
/* 305:    */               }
/* 306:302 */               if (this.grayscale) {
/* 307:303 */                 g.setColor(new Color((int)(255.0D * match), (int)(255.0D * match), (int)(255.0D * match)));
/* 308:306 */               } else if (match == 0.0D) {
/* 309:307 */                 g.setColor(Color.black);
/* 310:    */               } else {
/* 311:310 */                 g.setColor(new Color((int)(255.0D * (1.0D - match)), (int)(255.0D * match), 0));
/* 312:    */               }
/* 313:313 */               g.setColor(new Color(Color.HSBtoRGB(0.4F, 1.0F - (float)match, (float)match)));
/* 314:314 */               g.fillRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 315:    */               
/* 316:316 */               g.setColor(Color.black);
/* 317:317 */               g.drawRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 318:319 */               if (this.grayscale) {
/* 319:320 */                 g.setColor(Color.GREEN);
/* 320:    */               } else {
/* 321:323 */                 g.setColor(Color.white);
/* 322:    */               }
/* 323:327 */               g.setFont(new Font("Dialog", 0, 12));
/* 324:328 */               int matchSize = matchWrapper.getMatchSize();
/* 325:329 */               if (matchSize != 0) {
/* 326:330 */                 if (!this.vectorMatch) {
/* 327:331 */                   g.drawString(matchSize, x_start + i * word_width, y_start + j * word_height + word_height);
/* 328:    */                 } else {
/* 329:334 */                   g.drawString(df.format(matchWrapper.getValue()), x_start + i * word_width, y_start + j * word_height + word_height);
/* 330:    */                 }
/* 331:    */               }
/* 332:    */             }
/* 333:    */           }
/* 334:    */         }
/* 335:    */       }
/* 336:    */     }
/* 337:340 */     if (this.mouse_x != -1)
/* 338:    */     {
/* 339:341 */       g.setFont(new Font("Dialog", 0, 12));
/* 340:342 */       g.setColor(Color.green);
/* 341:343 */       int i = (int)((this.mouse_x - x_start) / word_width);
/* 342:344 */       int j = (int)((this.mouse_y - y_start) / word_height);
/* 343:345 */       if (i <= j) {
/* 344:    */         try
/* 345:    */         {
/* 346:347 */           ConceptPatternMatchWrapper match = this.memory.getPlotUnitComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j), this.currentSize);
/* 347:    */           try
/* 348:    */           {
/* 349:349 */             g.drawString(match.getMatchesAsString(), this.mouse_x, this.mouse_y);
/* 350:    */           }
/* 351:    */           catch (Exception localException) {}
/* 352:    */           return;
/* 353:    */         }
/* 354:    */         catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
/* 355:    */       }
/* 356:    */     }
/* 357:    */   }
/* 358:    */   
/* 359:    */   public void mouseDragged(MouseEvent event) {}
/* 360:    */   
/* 361:    */   public void mouseMoved(MouseEvent event)
/* 362:    */   {
/* 363:364 */     this.mouse_x = event.getX();
/* 364:365 */     this.mouse_y = event.getY();
/* 365:    */     
/* 366:367 */     repaint();
/* 367:368 */     updateUI();
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void update(Integer size)
/* 371:    */   {
/* 372:373 */     drawPlotComparisons(size.intValue());
/* 373:    */   }
/* 374:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.GeneratedGridVisualPanel
 * JD-Core Version:    0.7.0.1
 */