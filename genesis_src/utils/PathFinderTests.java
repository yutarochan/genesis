/*  1:   */ package utils;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.PrintStream;
/*  5:   */ import java.net.URL;
/*  6:   */ import java.util.ArrayList;
/*  7:   */ import org.apache.commons.io.IOUtils;
/*  8:   */ import org.junit.Assert;
/*  9:   */ import org.junit.Test;
/* 10:   */ 
/* 11:   */ public class PathFinderTests
/* 12:   */ {
/* 13:   */   @Test
/* 14:   */   public void testStoryRoot()
/* 15:   */     throws IOException
/* 16:   */   {
/* 17:16 */     System.out.println(PathFinder.storyRootURL());
/* 18:   */   }
/* 19:   */   
/* 20:   */   @Test
/* 21:   */   public void testAbsoluteStorySearch()
/* 22:   */     throws IOException
/* 23:   */   {
/* 24:21 */     System.out.println(PathFinder.storyURL("stories/Start experiment.txt"));
/* 25:   */   }
/* 26:   */   
/* 27:   */   @Test
/* 28:   */   public void testRelativeStorySearch()
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:26 */     System.out.println(PathFinder.storyURL("Start expEriment"));
/* 32:   */   }
/* 33:   */   
/* 34:   */   @Test
/* 35:   */   public void testRelativeStorySearch2()
/* 36:   */     throws IOException
/* 37:   */   {
/* 38:31 */     System.out.println(PathFinder.storyURL("Start experiment.TXT"));
/* 39:   */   }
/* 40:   */   
/* 41:   */   @Test
/* 42:   */   public void testTrickyStorySearch()
/* 43:   */     throws IOException
/* 44:   */   {
/* 45:36 */     System.out.println(PathFinder.storyURL("macbeth plot"));
/* 46:   */   }
/* 47:   */   
/* 48:   */   @Test
/* 49:   */   public void testStoryAccess()
/* 50:   */     throws IOException
/* 51:   */   {
/* 52:41 */     System.out.println(IOUtils.toString(PathFinder.storyURL("Start experiment").openStream()));
/* 53:   */   }
/* 54:   */   
/* 55:   */   @Test
/* 56:   */   public void testSearchFailure()
/* 57:   */   {
/* 58:   */     try
/* 59:   */     {
/* 60:47 */       PathFinder.storyURL("Not a valid Storyalsidj");
/* 61:48 */       Assert.fail("Exception not Thrown for non-existant story");
/* 62:   */     }
/* 63:   */     catch (Exception e)
/* 64:   */     {
/* 65:52 */       System.out.println("did not find non-existant story");
/* 66:   */     }
/* 67:   */   }
/* 68:   */   
/* 69:   */   @Test
/* 70:   */   public void listResourceTest()
/* 71:   */     throws IOException
/* 72:   */   {
/* 73:59 */     ArrayList<URL> matches = PathFinder.listFiles(PathFinder.lookupURL("images"), ".jpg");
/* 74:60 */     System.out.println(matches.size() + " matches found.");
/* 75:62 */     if (matches.isEmpty()) {
/* 76:63 */       Assert.fail();
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     utils.PathFinderTests
 * JD-Core Version:    0.7.0.1
 */