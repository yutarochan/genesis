/*  1:   */ package links.videos;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import java.net.URL;
/*  5:   */ 
/*  6:   */ public class MovieLink
/*  7:   */ {
/*  8:13 */   private String phrase = null;
/*  9:15 */   private Entity representation = null;
/* 10:   */   private String videoFrames;
/* 11:   */   private MovieDescription movieDescription;
/* 12:   */   
/* 13:   */   public MovieLink(String phrase, MovieDescription movieDescription)
/* 14:   */   {
/* 15:22 */     this.phrase = phrase;
/* 16:23 */     this.movieDescription = movieDescription;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public MovieLink(String phrase, MovieDescription movieDescription, String frames)
/* 20:   */   {
/* 21:33 */     this(phrase, movieDescription);
/* 22:34 */     this.videoFrames = frames;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public MovieDescription getMovieDescription()
/* 26:   */   {
/* 27:38 */     return this.movieDescription;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public URL getUrl()
/* 31:   */   {
/* 32:42 */     return getMovieDescription().getUrl();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public String getPhrase()
/* 36:   */   {
/* 37:46 */     return this.phrase;
/* 38:   */   }
/* 39:   */   
/* 40:   */   public void setPhrase(String phrase)
/* 41:   */   {
/* 42:50 */     this.phrase = phrase;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public Entity getRepresentation()
/* 46:   */   {
/* 47:54 */     return this.representation;
/* 48:   */   }
/* 49:   */   
/* 50:   */   public void setRepresentation(Entity representation)
/* 51:   */   {
/* 52:58 */     this.representation = representation;
/* 53:   */   }
/* 54:   */   
/* 55:   */   public String getVideoFrames()
/* 56:   */   {
/* 57:62 */     return this.videoFrames;
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String toString()
/* 61:   */   {
/* 62:66 */     return "[" + this.phrase + " --> " + getUrl() + "]";
/* 63:   */   }
/* 64:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     links.videos.MovieLink
 * JD-Core Version:    0.7.0.1
 */