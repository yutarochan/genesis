/*   1:    */ package expert;
/*   2:    */ 
/*   3:    */ import adamKraft.VideoBrowser;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import frames.CauseFrame;
/*   9:    */ import genesis.GenesisGetters;
/*  10:    */ import genesis.Quantum;
/*  11:    */ import java.io.File;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import links.videos.MovieDescription;
/*  18:    */ import links.videos.MovieLink;
/*  19:    */ import matchers.StandardMatcher;
/*  20:    */ import matchers.Substitutor;
/*  21:    */ import utils.Mark;
/*  22:    */ 
/*  23:    */ public class ImaginationExpert
/*  24:    */   extends AbstractWiredBox
/*  25:    */ {
/*  26:    */   private boolean debug;
/*  27:    */   public static final String Y_N_QUESTION = "question";
/*  28:    */   public static final String IMAGINATION_COMMAND = "imagine";
/*  29:    */   public static final String LEARNED_QUANTUM = "learnedQuantum";
/*  30: 33 */   private static double distanceThreshold = 0.2D;
/*  31:    */   private Entity question;
/*  32:    */   private Entity whatToImagine;
/*  33:    */   private static MovieDescription mostRecentMovieDescription;
/*  34:    */   private GenesisGetters genesisGetters;
/*  35:    */   
/*  36:    */   public ImaginationExpert(GenesisGetters genesisGetters)
/*  37:    */   {
/*  38: 44 */     setName("Imagine expert");
/*  39: 45 */     this.genesisGetters = genesisGetters;
/*  40: 46 */     Connections.getPorts(this).addSignalProcessor("question", "yesNoQuestion");
/*  41: 47 */     Connections.getPorts(this).addSignalProcessor("imagine", "imaginationCommand");
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void yesNoQuestion(Object object)
/*  45:    */   {
/*  46: 51 */     if (!(object instanceof Entity)) {
/*  47: 52 */       return;
/*  48:    */     }
/*  49: 54 */     this.question = ((Entity)object);
/*  50: 55 */     MovieDescription movieDescription = getMostRecentMovieDescription();
/*  51: 56 */     if (movieDescription == null) {
/*  52: 57 */       return;
/*  53:    */     }
/*  54: 59 */     System.out.println("Candidates: " + movieDescription.getEvents().size());
/*  55:    */     
/*  56: 61 */     MovieLink link = getBestMovieEvent(movieDescription, this.question);
/*  57: 62 */     if (link != null)
/*  58:    */     {
/*  59: 63 */       System.out.println("Result is: " + link.getPhrase());
/*  60:    */       
/*  61: 65 */       double distance = StandardMatcher.getBasicMatcher().distance(this.question, link.getRepresentation());
/*  62: 66 */       double best = StandardMatcher.getBasicMatcher().distance(this.question, this.question);
/*  63: 67 */       Mark.say(new Object[] {"Comparing", Double.valueOf(best), Double.valueOf(distance) });
/*  64: 68 */       if (distance == best) {
/*  65: 69 */         learn(true);
/*  66:    */       } else {
/*  67: 79 */         learn(false);
/*  68:    */       }
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void imaginationCommand(Object object)
/*  73:    */   {
/*  74: 85 */     if (!(object instanceof Entity)) {
/*  75: 86 */       return;
/*  76:    */     }
/*  77: 88 */     this.whatToImagine = ((Entity)object);
/*  78: 89 */     if ((this.whatToImagine.relationP("imagine")) && (this.whatToImagine.getSubject().entityP("you")))
/*  79:    */     {
/*  80: 90 */       Mark.say(new Object[] {"Imagining relation" });
/*  81:    */       try
/*  82:    */       {
/*  83: 92 */         Entity action = this.whatToImagine.getObject();
/*  84: 93 */         if (!action.isA("action")) {
/*  85:    */           return;
/*  86:    */         }
/*  87: 95 */         if (action.getFeatures().size() <= 1) {
/*  88:    */           return;
/*  89:    */         }
/*  90: 96 */         String[] types = new String[action.getFeatures().size() - 1];
/*  91: 97 */         for (int i = 0; i < types.length; i++) {
/*  92: 98 */           types[i] = action.getFeatures().get(i + 1).toString();
/*  93:    */         }
/*  94:100 */         new VideoBrowser().showVideoWithMostlyKeywords(types);
/*  95:    */       }
/*  96:    */       catch (IOException e)
/*  97:    */       {
/*  98:105 */         e.printStackTrace();
/*  99:    */       }
/* 100:    */     }
/* 101:108 */     else if ((this.whatToImagine.functionP("imagine")) && (this.whatToImagine.getSubject().entityP("you")))
/* 102:    */     {
/* 103:109 */       Mark.say(new Object[] {"Imagining derivative" });
/* 104:    */       try
/* 105:    */       {
/* 106:111 */         String action = this.whatToImagine.getSubject().getType();
/* 107:112 */         new VideoBrowser().showVideoWithMostlyKeywords(new String[] { action });
/* 108:    */       }
/* 109:    */       catch (IOException e)
/* 110:    */       {
/* 111:115 */         e.printStackTrace();
/* 112:    */       }
/* 113:    */     }
/* 114:    */     else
/* 115:    */     {
/* 116:120 */       ArrayList<MovieDescription> movieDescriptions = this.genesisGetters.getMovieDescriptions();
/* 117:121 */       if ((movieDescriptions == null) || (movieDescriptions.isEmpty())) {
/* 118:122 */         return;
/* 119:    */       }
/* 120:126 */       MovieLink bestTriple = getBestMovieLink(movieDescriptions, this.whatToImagine.getSubject());
/* 121:127 */       if (bestTriple != null)
/* 122:    */       {
/* 123:131 */         URL url = bestTriple.getUrl();
/* 124:132 */         File directory = bestTriple.getMovieDescription().getDirectory();
/* 125:134 */         if (url != null)
/* 126:    */         {
/* 127:135 */           setMostRecentMovie(bestTriple.getMovieDescription());
/* 128:136 */           Connections.getPorts(this).transmit(url);
/* 129:137 */           Connections.getPorts(this).transmit("imagine", this.whatToImagine);
/* 130:    */           
/* 131:139 */           System.out.println("Transmitting from ImagineExpert to MovieViewer " + url);
/* 132:140 */           return;
/* 133:    */         }
/* 134:    */       }
/* 135:143 */       Connections.getPorts(this).transmit("say", "Sorry, I can't");
/* 136:144 */       System.err.println("RLM: said sorry");
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public static MovieLink getBestMovieEvent(MovieDescription movieDescription, Entity imaginedDescription)
/* 141:    */   {
/* 142:150 */     double reference = -1.0D;
/* 143:151 */     MovieLink bestLink = null;
/* 144:152 */     ArrayList<MovieLink> movieLinks = movieDescription.getEvents();
/* 145:154 */     for (Object element : movieLinks)
/* 146:    */     {
/* 147:155 */       MovieLink movieLink = (MovieLink)element;
/* 148:156 */       Entity memory = movieLink.getRepresentation();
/* 149:157 */       if (memory != null)
/* 150:    */       {
/* 151:162 */         double distance = StandardMatcher.getBasicMatcher().distance(imaginedDescription, memory);
/* 152:    */         
/* 153:164 */         Mark.say(new Object[] {"Noting distance " + distance + " for " + movieLink.getPhrase() });
/* 154:166 */         if (distance > reference)
/* 155:    */         {
/* 156:167 */           reference = distance;
/* 157:168 */           bestLink = movieLink;
/* 158:    */         }
/* 159:    */       }
/* 160:    */       else
/* 161:    */       {
/* 162:172 */         Mark.say(new Object[] {"Movie event has null remembered description for " + movieLink.getPhrase() });
/* 163:    */       }
/* 164:    */     }
/* 165:175 */     return bestLink;
/* 166:    */   }
/* 167:    */   
/* 168:    */   private void learn(boolean sign)
/* 169:    */   {
/* 170:179 */     Entity cause = getWhatToImagine();
/* 171:180 */     Entity result = getQuestion();
/* 172:181 */     Entity reconciledResult = Substitutor.reconcile(cause, result);
/* 173:182 */     Quantum quantum = new Quantum(cause, reconciledResult, sign);
/* 174:183 */     Connections.getPorts(this).transmit("learnedQuantum", quantum);
/* 175:184 */     Connections.getPorts(this).transmit(CauseFrame.FRAMETYPE, quantum.getThing());
/* 176:    */   }
/* 177:    */   
/* 178:    */   public Entity getQuestion()
/* 179:    */   {
/* 180:188 */     return this.question;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public Entity getWhatToImagine()
/* 184:    */   {
/* 185:192 */     return this.whatToImagine;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static MovieLink getBestMovieLink(ArrayList<MovieDescription> movieDescriptions, Entity imaginedDescription)
/* 189:    */   {
/* 190:196 */     double reference = -1.0D;
/* 191:197 */     MovieLink bestLink = null;
/* 192:    */     Iterator localIterator2;
/* 193:200 */     for (Iterator localIterator1 = movieDescriptions.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 194:    */     {
/* 195:200 */       MovieDescription movieDescription = (MovieDescription)localIterator1.next();
/* 196:201 */       ArrayList<MovieLink> movieLinks = movieDescription.getSummaries();
/* 197:202 */       localIterator2 = movieLinks.iterator(); continue;MovieLink movieLink = (MovieLink)localIterator2.next();
/* 198:203 */       Entity rememberedDescription = movieLink.getRepresentation();
/* 199:204 */       if (rememberedDescription != null)
/* 200:    */       {
/* 201:205 */         double distance = StandardMatcher.getBasicMatcher().distance(imaginedDescription, rememberedDescription);
/* 202:208 */         if ((reference < 0.0D) || (distance > reference))
/* 203:    */         {
/* 204:209 */           reference = distance;
/* 205:210 */           bestLink = movieLink;
/* 206:    */         }
/* 207:    */       }
/* 208:    */     }
/* 209:217 */     Mark.say(new Object[] {"Best movie with score", Double.valueOf(reference), "is", bestLink });
/* 210:218 */     return bestLink;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public static URL getMostRecentMovie()
/* 214:    */   {
/* 215:222 */     MovieDescription description = getMostRecentMovieDescription();
/* 216:223 */     if (description != null) {
/* 217:224 */       return description.getUrl();
/* 218:    */     }
/* 219:226 */     return null;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public static MovieDescription getMostRecentMovieDescription()
/* 223:    */   {
/* 224:230 */     return mostRecentMovieDescription;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public static void setMostRecentMovie(MovieDescription m)
/* 228:    */   {
/* 229:234 */     mostRecentMovieDescription = m;
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     expert.ImaginationExpert
 * JD-Core Version:    0.7.0.1
 */