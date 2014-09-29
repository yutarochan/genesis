/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.ReflectionLevelMemory;
/*   4:    */ import carynKrakauer.StudyResults;
/*   5:    */ import carynKrakauer.VectorMatchWrapper;
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
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Set;
/*  19:    */ import matthewFay.Utilities.Pair;
/*  20:    */ 
/*  21:    */ public class GridVisualPanel
/*  22:    */   extends MatrixJPanel
/*  23:    */   implements MouseMotionListener
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   private boolean grayscale;
/*  27:    */   private boolean triangle;
/*  28: 28 */   int mouse_x = -1;
/*  29: 29 */   int mouse_y = -1;
/*  30:    */   ReflectionLevelMemory memory;
/*  31:    */   
/*  32:    */   public GridVisualPanel(ReflectionLevelMemory memory, boolean grayscale, boolean triangle)
/*  33:    */   {
/*  34: 35 */     this.memory = memory;
/*  35: 36 */     addMouseMotionListener(this);
/*  36:    */     
/*  37: 38 */     this.grayscale = grayscale;
/*  38: 39 */     this.triangle = triangle;
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void drawPlotComparisons()
/*  42:    */   {
/*  43: 43 */     removeAll();
/*  44:    */     
/*  45: 45 */     repaint();
/*  46: 46 */     updateUI();
/*  47:    */   }
/*  48:    */   
/*  49:    */   private double calcStandardDev()
/*  50:    */   {
/*  51: 51 */     double total = 0.0D;
/*  52: 52 */     int num = 0;
/*  53: 53 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/*  54:    */     {
/*  55: 54 */       String story1 = (String)this.memory.getStoryNames().get(j);
/*  56: 55 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/*  57:    */       {
/*  58: 56 */         String story2 = (String)this.memory.getStoryNames().get(i);
/*  59: 57 */         int studyResult = ((Integer)((HashMap)StudyResults.getStudyResults().get(story1)).get(story2)).intValue();
/*  60: 58 */         double studyVal = studyResult / 11.0F / StudyResults.getMaxStudyResult();
/*  61: 59 */         double val = this.memory.getDefinedConceptPatternComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j)).getValue();
/*  62: 60 */         val /= maxMatch();
/*  63:    */         
/*  64: 62 */         double diff = Math.pow(val - studyVal, 2.0D);
/*  65: 63 */         total += diff;
/*  66: 64 */         num++;
/*  67:    */       }
/*  68:    */     }
/*  69: 67 */     return Math.sqrt(total / num);
/*  70:    */   }
/*  71:    */   
/*  72:    */   private HashMap<String, HashMap<String, Integer>> getRanks()
/*  73:    */   {
/*  74: 71 */     HashMap<String, ArrayList<Pair<Double, String>>> ranks = new HashMap();
/*  75: 72 */     for (int i = 0; i < this.memory.getStoryCount(); i++)
/*  76:    */     {
/*  77: 73 */       String story1 = (String)this.memory.getStoryNames().get(i);
/*  78: 74 */       story1Ranks = new ArrayList();
/*  79: 75 */       ranks.put(story1, story1Ranks);
/*  80: 76 */       for (int j = 0; j < this.memory.getStoryCount(); j++)
/*  81:    */       {
/*  82: 77 */         String story2 = (String)this.memory.getStoryNames().get(j);
/*  83:    */         
/*  84: 79 */         VectorMatchWrapper matchWrapper = this.memory.getDefinedConceptPatternComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j));
/*  85: 80 */         if (matchWrapper != null)
/*  86:    */         {
/*  87: 83 */           double val = matchWrapper.getValue();
/*  88:    */           
/*  89: 85 */           boolean found = false;
/*  90: 86 */           for (int k = 0; k < story1Ranks.size(); k++) {
/*  91: 87 */             if (((Double)((Pair)story1Ranks.get(k)).a).doubleValue() < val)
/*  92:    */             {
/*  93: 88 */               story1Ranks.add(k, new Pair(Double.valueOf(val), story2));
/*  94: 89 */               found = true;
/*  95: 90 */               break;
/*  96:    */             }
/*  97:    */           }
/*  98: 93 */           if (!found) {
/*  99: 94 */             story1Ranks.add(new Pair(Double.valueOf(val), story2));
/* 100:    */           }
/* 101:    */         }
/* 102:    */       }
/* 103:    */     }
/* 104: 99 */     HashMap<String, HashMap<String, Integer>> outRanks = new HashMap();
/* 105:    */     ArrayList<Pair<Double, String>> ordering;
/* 106:    */     int i;
/* 107:101 */     for (ArrayList<Pair<Double, String>> story1Ranks = ranks.keySet().iterator(); story1Ranks.hasNext(); i < ordering.size())
/* 108:    */     {
/* 109:101 */       String story1 = (String)story1Ranks.next();
/* 110:102 */       outRanks.put(story1, new HashMap());
/* 111:103 */       ordering = (ArrayList)ranks.get(story1);
/* 112:104 */       int current_rank = 0;
/* 113:105 */       double current_val = -1.0D;
/* 114:106 */       i = 0; continue;
/* 115:107 */       if (((Double)((Pair)ordering.get(i)).a).doubleValue() != current_val)
/* 116:    */       {
/* 117:108 */         current_rank = i;
/* 118:109 */         current_val = ((Double)((Pair)ordering.get(i)).a).doubleValue();
/* 119:    */       }
/* 120:111 */       ((HashMap)outRanks.get(story1)).put((String)((Pair)ordering.get(i)).b, Integer.valueOf(current_rank));i++;
/* 121:    */     }
/* 122:115 */     return outRanks;
/* 123:    */   }
/* 124:    */   
/* 125:    */   private double getRankSD()
/* 126:    */   {
/* 127:119 */     HashMap<String, HashMap<String, Integer>> ranks = getRanks();
/* 128:    */     
/* 129:121 */     double total = 0.0D;
/* 130:122 */     int num = 0;
/* 131:123 */     for (int j = 0; j < this.memory.getStoryCount(); j++)
/* 132:    */     {
/* 133:124 */       String story1 = (String)this.memory.getStoryNames().get(j);
/* 134:125 */       for (int i = j + 1; i < this.memory.getStoryCount(); i++)
/* 135:    */       {
/* 136:126 */         String story2 = (String)this.memory.getStoryNames().get(i);
/* 137:    */         
/* 138:128 */         int myRank = ((Integer)((HashMap)ranks.get(story1)).get(story2)).intValue();
/* 139:129 */         int studyRank = StudyResults.getRank(story1, story2);
/* 140:    */         
/* 141:131 */         double diff = Math.pow(myRank - studyRank, 2.0D);
/* 142:132 */         total += diff;
/* 143:133 */         num++;
/* 144:    */       }
/* 145:    */     }
/* 146:137 */     return Math.sqrt(total / num);
/* 147:    */   }
/* 148:    */   
/* 149:    */   private double maxMatch()
/* 150:    */   {
/* 151:141 */     double maxMatch = -1.0D;
/* 152:142 */     for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 153:143 */       for (int j = i + 1; j < this.memory.getStoryCount(); j++)
/* 154:    */       {
/* 155:144 */         VectorMatchWrapper matchWrapper = this.memory.getDefinedConceptPatternComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j));
/* 156:145 */         if (matchWrapper != null)
/* 157:    */         {
/* 158:148 */           double matchSize = matchWrapper.getValue();
/* 159:149 */           if (matchSize > maxMatch) {
/* 160:150 */             maxMatch = matchSize;
/* 161:    */           }
/* 162:    */         }
/* 163:    */       }
/* 164:    */     }
/* 165:154 */     return maxMatch;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void paintComponent(Graphics g)
/* 169:    */   {
/* 170:159 */     super.paintComponent(g);
/* 171:    */     
/* 172:161 */     setBackground(Color.white);
/* 173:    */     
/* 174:163 */     g.setFont(new Font("Dialog", 0, 12));
/* 175:    */     
/* 176:165 */     paintComparisons(g);
/* 177:    */   }
/* 178:    */   
/* 179:    */   private void paintComparisons(Graphics g)
/* 180:    */   {
/* 181:171 */     int width = getWidth();
/* 182:172 */     int height = getHeight();
/* 183:    */     
/* 184:174 */     Font font = new Font("Dialog", 0, 12);
/* 185:175 */     Graphics2D g2 = (Graphics2D)g;
/* 186:176 */     int x_start = 0;
/* 187:178 */     for (String name : this.memory.getStoryNames())
/* 188:    */     {
/* 189:179 */       Rectangle2D bounds = font.getStringBounds(name, g2.getFontRenderContext());
/* 190:180 */       x_start = (int)Math.max(x_start, bounds.getWidth());
/* 191:    */     }
/* 192:183 */     DecimalFormat df = new DecimalFormat("#.##");
/* 193:184 */     g.setColor(Color.black);
/* 194:185 */     double stdDevError = getRankSD();
/* 195:186 */     writeStdDevError(g, df, stdDevError);
/* 196:    */     
/* 197:188 */     int y_start = 20;
/* 198:    */     
/* 199:190 */     int word_width = (int)((width - x_start) / this.memory.getStoryCount());
/* 200:191 */     int word_height = (int)((height - 40) / this.memory.getStoryCount());
/* 201:192 */     int x_pos = x_start;
/* 202:193 */     int y_pos = y_start;
/* 203:    */     
/* 204:195 */     double widestName = this.memory.getWidestNameWidth(font, g2.getFontRenderContext());
/* 205:196 */     if (widestName >= word_width * 2)
/* 206:    */     {
/* 207:197 */       g.setFont(new Font("Dialog", 1, 16));
/* 208:198 */       for (int i = 1; i <= this.memory.getStoryCount(); i++)
/* 209:    */       {
/* 210:199 */         g.drawString(i, (int)(x_pos + word_width * 0.5F), y_pos);
/* 211:200 */         x_pos += word_width;
/* 212:    */       }
/* 213:203 */       g.setFont(new Font("Dialog", 1, 12));
/* 214:204 */       x_pos = 0;
/* 215:205 */       y_pos = y_start + word_height;
/* 216:206 */       int index = 1;
/* 217:207 */       for (String storyName : this.memory.getStoryNames())
/* 218:    */       {
/* 219:208 */         g.drawString(index + ": " + storyName, x_pos, y_pos);
/* 220:209 */         y_pos += word_height;
/* 221:210 */         index++;
/* 222:    */       }
/* 223:    */     }
/* 224:    */     else
/* 225:    */     {
/* 226:214 */       g.setFont(new Font("Dialog", 0, 12));
/* 227:215 */       int index = 0;
/* 228:216 */       for (String storyName : this.memory.getStoryNames())
/* 229:    */       {
/* 230:217 */         g.drawString(storyName, x_pos, y_pos - y_pos / 2 * (index % 2));
/* 231:218 */         x_pos += word_width;
/* 232:219 */         index++;
/* 233:    */       }
/* 234:222 */       x_pos = 0;
/* 235:223 */       y_pos = y_start + word_height;
/* 236:224 */       for (String storyName : this.memory.getStoryNames())
/* 237:    */       {
/* 238:225 */         g.drawString(storyName, x_pos, y_pos);
/* 239:226 */         y_pos += word_height;
/* 240:    */       }
/* 241:    */     }
/* 242:231 */     for (int j = 0; j < this.memory.getStoryCount(); j++) {
/* 243:232 */       for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 244:233 */         if ((!this.triangle) || (i <= j))
/* 245:    */         {
/* 246:237 */           VectorMatchWrapper matchWrapper = this.memory.getDefinedConceptPatternComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j));
/* 247:238 */           if (matchWrapper != null)
/* 248:    */           {
/* 249:241 */             double match = matchWrapper.getValue();
/* 250:242 */             if (this.grayscale) {
/* 251:243 */               g.setColor(new Color((int)(255.0D * match), (int)(255.0D * match), (int)(255.0D * match)));
/* 252:246 */             } else if (match == 0.0D) {
/* 253:247 */               g.setColor(Color.black);
/* 254:    */             } else {
/* 255:250 */               g.setColor(new Color(Color.HSBtoRGB(0.4F, 1.0F - (float)match, (float)match)));
/* 256:    */             }
/* 257:254 */             g.fillRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 258:    */             
/* 259:256 */             g.setColor(Color.black);
/* 260:257 */             g.drawRect(x_start + i * word_width, y_start + j * word_height, word_width, word_height);
/* 261:    */             
/* 262:259 */             g.setFont(new Font("Dialog", 0, 12));
/* 263:260 */             if (match != 0.0D) {
/* 264:261 */               df = new DecimalFormat("#.##");
/* 265:    */             }
/* 266:    */           }
/* 267:    */         }
/* 268:    */       }
/* 269:    */     }
/* 270:267 */     double mean = 0.0D;
/* 271:268 */     Object matches = new ArrayList();
/* 272:    */     double match;
/* 273:269 */     for (int i = 0; i < this.memory.getStoryCount(); i++) {
/* 274:270 */       for (int j = i + 1; j < this.memory.getStoryCount(); j++)
/* 275:    */       {
/* 276:271 */         VectorMatchWrapper matchWrapper = this.memory.getDefinedConceptPatternComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j));
/* 277:272 */         if (matchWrapper != null)
/* 278:    */         {
/* 279:275 */           match = matchWrapper.getValue();
/* 280:276 */           ((List)matches).add(Double.valueOf(match));
/* 281:277 */           mean += match;
/* 282:    */         }
/* 283:    */       }
/* 284:    */     }
/* 285:281 */     mean /= ((List)matches).size();
/* 286:282 */     double variance = 0.0D;
/* 287:283 */     for (Double match : (List)matches) {
/* 288:284 */       variance += Math.pow(match.doubleValue() - mean, 2.0D);
/* 289:    */     }
/* 290:286 */     variance /= ((List)matches).size();
/* 291:287 */     double stdDev = Math.sqrt(variance);
/* 292:297 */     if (this.mouse_x != -1)
/* 293:    */     {
/* 294:298 */       g.setFont(new Font("Dialog", 0, 12));
/* 295:299 */       g.setColor(Color.black);
/* 296:300 */       int i = (int)((this.mouse_x - x_start) / word_width);
/* 297:301 */       int j = (int)((this.mouse_y - y_start) / word_height);
/* 298:302 */       if (i <= j) {
/* 299:    */         try
/* 300:    */         {
/* 301:304 */           VectorMatchWrapper match = this.memory.getDefinedConceptPatternComparison((String)this.memory.getStoryNames().get(i), (String)this.memory.getStoryNames().get(j));
/* 302:305 */           g.drawString(match.getValue() + ": " + match.getMatches(), this.mouse_x, this.mouse_y);
/* 303:    */         }
/* 304:    */         catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
/* 305:    */       }
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void mouseDragged(MouseEvent event) {}
/* 310:    */   
/* 311:    */   public void mouseMoved(MouseEvent event)
/* 312:    */   {
/* 313:321 */     this.mouse_x = event.getX();
/* 314:322 */     this.mouse_y = event.getY();
/* 315:    */     
/* 316:324 */     repaint();
/* 317:325 */     updateUI();
/* 318:    */   }
/* 319:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.GridVisualPanel
 * JD-Core Version:    0.7.0.1
 */