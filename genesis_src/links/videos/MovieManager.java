/*   1:    */ package links.videos;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import connections.AbstractWiredBox;
/*   6:    */ import connections.Connections;
/*   7:    */ import connections.Ports;
/*   8:    */ import genesis.GenesisGetters;
/*   9:    */ import java.io.BufferedReader;
/*  10:    */ import java.io.IOException;
/*  11:    */ import java.io.InputStreamReader;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.net.URISyntaxException;
/*  14:    */ import java.net.URL;
/*  15:    */ import java.net.URLDecoder;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Vector;
/*  20:    */ import org.apache.commons.io.FilenameUtils;
/*  21:    */ import start.Start;
/*  22:    */ import text.Html;
/*  23:    */ import text.Punctuator;
/*  24:    */ import translator.Translator;
/*  25:    */ import utils.Mark;
/*  26:    */ import utils.PathFinder;
/*  27:    */ 
/*  28:    */ public class MovieManager
/*  29:    */   extends AbstractWiredBox
/*  30:    */ {
/*  31: 34 */   public static String CLEAR = "clear";
/*  32:    */   private GenesisGetters gauntlet;
/*  33:    */   ArrayList<MovieDescription> movieDescriptions;
/*  34:    */   
/*  35:    */   class GetTheMovies
/*  36:    */     extends Thread
/*  37:    */   {
/*  38:    */     GetTheMovies() {}
/*  39:    */     
/*  40:    */     public void run()
/*  41:    */     {
/*  42:    */       try
/*  43:    */       {
/*  44: 40 */         Connections.getPorts(MovieManager.this).transmit("switch tab", "Video annotation");
/*  45: 41 */         MovieManager.this.movieDescriptions = new ArrayList();
/*  46:    */         
/*  47: 43 */         List<URL> fileNames = PathFinder.listFiles("visualmemory/annotations", ".txt");
/*  48: 44 */         List<URL> movieURLs = PathFinder.listFiles("visualmemory/videos", "mov");
/*  49: 45 */         movieURLs.addAll(PathFinder.listFiles("visualmemory/videos", "mpg"));
/*  50:    */         BufferedReader reader;
/*  51:    */         String line;
/*  52: 47 */         for (Iterator localIterator = fileNames.iterator(); localIterator.hasNext(); (line = reader.readLine()) != null)
/*  53:    */         {
/*  54: 47 */           URL annotationURL = (URL)localIterator.next();
/*  55: 48 */           String txtFileName = FilenameUtils.getBaseName(URLDecoder.decode(annotationURL.toString(), "utf-8"));
/*  56: 49 */           URL movieURL = PathFinder.lookupURL("visualmemory/videos/" + txtFileName + ".mov");
/*  57: 50 */           if (movieURL == null) {
/*  58: 51 */             movieURL = PathFinder.lookupURL("visualmemory/videos/" + txtFileName + ".mpg");
/*  59:    */           }
/*  60: 53 */           if (movieURL == null) {
/*  61: 54 */             Mark.say(new Object[] {"No movie named ", txtFileName });
/*  62:    */           }
/*  63: 56 */           Mark.say(new Object[] {"MovieURL is " + movieURL });
/*  64: 57 */           Connections.getPorts(MovieManager.this).transmit(Html.h1("Annotations of " + txtFileName + ":"));
/*  65:    */           
/*  66: 59 */           MovieDescription movieDescription = new MovieDescription(movieURL);
/*  67: 60 */           MovieManager.this.movieDescriptions.add(movieDescription);
/*  68: 61 */           Mark.say(new Object[] {"Now movie count is", Integer.valueOf(MovieManager.this.movieDescriptions.size()), annotationURL, "-->", movieURL });
/*  69: 62 */           reader = new BufferedReader(new InputStreamReader(annotationURL.openStream()));
/*  70:    */           
/*  71: 64 */           continue;
/*  72:    */           String line;
/*  73: 65 */           String prefix = "summary:";
/*  74: 66 */           boolean test = line.toLowerCase().startsWith(prefix);
/*  75: 67 */           MovieLink movieLink = null;
/*  76: 68 */           if (test)
/*  77:    */           {
/*  78: 69 */             line = line.substring(prefix.length()).trim();
/*  79:    */             
/*  80:    */ 
/*  81: 72 */             movieLink = new MovieLink(line, movieDescription);
/*  82: 73 */             movieDescription.addSummary(movieLink);
/*  83:    */           }
/*  84: 75 */           else if (line.indexOf(":") > 0)
/*  85:    */           {
/*  86: 76 */             int index = line.indexOf(":");
/*  87: 77 */             String frames = line.substring(0, index);
/*  88: 78 */             line = line.substring(index + 1).trim();
/*  89:    */             
/*  90:    */ 
/*  91: 81 */             movieLink = new MovieLink(line, movieDescription, frames);
/*  92: 82 */             movieDescription.addEvent(movieLink);
/*  93:    */           }
/*  94: 84 */           if (movieLink != null)
/*  95:    */           {
/*  96: 85 */             String phrase = movieLink.getPhrase();
/*  97: 86 */             Sequence sequence = null;
/*  98: 87 */             Entity instantiation = null;
/*  99:    */             
/* 100: 89 */             sequence = MovieManager.this.gauntlet.getStartParser().parse(phrase);
/* 101: 90 */             if (sequence != null) {
/* 102: 91 */               instantiation = MovieManager.this.gauntlet.getNewSemanticTranslator().interpret(sequence);
/* 103:    */             }
/* 104: 93 */             if (instantiation.isA("semantic-interpretation"))
/* 105:    */             {
/* 106: 94 */               if (instantiation.sequenceP())
/* 107:    */               {
/* 108: 95 */                 Sequence s = (Sequence)instantiation;
/* 109: 96 */                 if (!s.getElements().isEmpty()) {
/* 110: 97 */                   instantiation = s.getElement(0);
/* 111:    */                 }
/* 112:    */               }
/* 113:    */             }
/* 114:107 */             else if (instantiation != null)
/* 115:    */             {
/* 116:108 */               movieLink.setRepresentation(instantiation);
/* 117:109 */               Connections.getPorts(MovieManager.this).transmit(Html.normal(Punctuator.addPeriod(phrase)));
/* 118:    */             }
/* 119:    */             else
/* 120:    */             {
/* 121:112 */               System.out.println("No parse for " + phrase + "!!!");
/* 122:    */             }
/* 123:    */           }
/* 124:    */         }
/* 125:    */       }
/* 126:    */       catch (Exception e)
/* 127:    */       {
/* 128:121 */         e.printStackTrace();
/* 129:    */       }
/* 130:    */       finally
/* 131:    */       {
/* 132:124 */         if (MovieManager.this.gauntlet != null) {
/* 133:125 */           MovieManager.this.gauntlet.openInterface();
/* 134:    */         }
/* 135:    */       }
/* 136:128 */       Connections.getPorts(MovieManager.this).transmit("switch tab", "silence");
/* 137:    */       
/* 138:130 */       System.out.println("Loaded: " + MovieManager.this.movieDescriptions.size());
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   public static void main(String[] ignore)
/* 143:    */     throws URISyntaxException, IOException
/* 144:    */   {
/* 145:139 */     new MovieManager().loadMovieDescriptions();
/* 146:    */   }
/* 147:    */   
/* 148:    */   private MovieManager() {}
/* 149:    */   
/* 150:    */   public MovieManager(GenesisGetters genesisGetters)
/* 151:    */   {
/* 152:150 */     setName("Video manager");
/* 153:151 */     this.gauntlet = genesisGetters;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public ArrayList<MovieDescription> getMovieDescriptions()
/* 157:    */   {
/* 158:155 */     return this.movieDescriptions;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void loadMovieDescriptions()
/* 162:    */   {
/* 163:    */     try
/* 164:    */     {
/* 165:160 */       if (this.gauntlet != null) {
/* 166:161 */         this.gauntlet.closeInterface();
/* 167:    */       }
/* 168:163 */       Thread thread = new GetTheMovies();
/* 169:164 */       thread.start();
/* 170:    */     }
/* 171:    */     catch (Exception localException) {}
/* 172:    */   }
/* 173:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     links.videos.MovieManager
 * JD-Core Version:    0.7.0.1
 */