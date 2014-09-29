/*  1:   */ package core;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import bridge.reps.entities.Sequence;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.io.PrintStream;
/*  7:   */ import org.junit.Assert;
/*  8:   */ import org.junit.Test;
/*  9:   */ import utils.Mark;
/* 10:   */ 
/* 11:   */ public class StoryTests
/* 12:   */ {
/* 13:   */   public static final String darfurTest = "test/darfur-test.txt";
/* 14:   */   public static final String intendedDarfurParse = "(sequence story (start you (sequence roles (object story))) (classification nation sudan) (classification region darfur) (classification militia janjaweed) (want sudan (sequence roles (object (cleanse janjaweed (sequence roles (object darfur)))))))";
/* 15:   */   public static final String if_then_basic = "test/if-then-basic.txt";
/* 16:   */   public static final String if_then_basic_intendedParse = "(sequence simple_if-then_rule (start you (sequence roles (object (story simple_if-then_rule)))) (is_called story simple_if-then_rule) (classification person macbeth) (classification person macduff) (kill macbeth (sequence roles (object macduff))) (prediction (sequence conjuction (kill macbeth (sequence roles (object macduff)))) (property macduff dead)) (property macduff dead))";
/* 17:   */   public static final String clearMemory = "test/clear-memory.txt";
/* 18:   */   public static final String clearMemoryIntendedParse = "(sequence null_story (start you (sequence roles (object (story null_story)))) (is_called story null_story) (classification person robert))";
/* 19:   */   
/* 20:   */   public static void testStory(String storyPath, String intendedParse)
/* 21:   */   {
/* 22:   */     try
/* 23:   */     {
/* 24:27 */       Thread.sleep(1000L);
/* 25:28 */       BetterSignal parsedStory = 
/* 26:29 */         HeadlessGenesis.getHeadlessGenesis().processStoryFile(storyPath);
/* 27:   */       
/* 28:31 */       Sequence story = (Sequence)parsedStory.get(0, Sequence.class);
/* 29:   */       
/* 30:33 */       String actualParse = story.asStringWithoutIndexes();
/* 31:34 */       Mark.say(new Object[] {actualParse });
/* 32:35 */       boolean aligned = intendedParse.equals(actualParse);
/* 33:36 */       System.out.println(aligned ? "Success!" : "Failure!");
/* 34:37 */       System.out.println("Expected : " + intendedParse);
/* 35:38 */       System.out.println("Actual   : " + actualParse);
/* 36:39 */       Assert.assertTrue(aligned);
/* 37:   */     }
/* 38:   */     catch (IOException|InterruptedException e)
/* 39:   */     {
/* 40:41 */       e.printStackTrace();
/* 41:42 */       Assert.fail();
/* 42:   */     }
/* 43:   */   }
/* 44:   */   
/* 45:   */   @Test(timeout=20000L)
/* 46:   */   public void testBasicParsing()
/* 47:   */   {
/* 48:50 */     testStory("test/darfur-test.txt", "(sequence story (start you (sequence roles (object story))) (classification nation sudan) (classification region darfur) (classification militia janjaweed) (want sudan (sequence roles (object (cleanse janjaweed (sequence roles (object darfur)))))))");
/* 49:   */   }
/* 50:   */   
/* 51:   */   @Test(timeout=20000L)
/* 52:   */   public void testBasicIfThenRule()
/* 53:   */   {
/* 54:57 */     testStory("test/if-then-basic.txt", "(sequence simple_if-then_rule (start you (sequence roles (object (story simple_if-then_rule)))) (is_called story simple_if-then_rule) (classification person macbeth) (classification person macduff) (kill macbeth (sequence roles (object macduff))) (prediction (sequence conjuction (kill macbeth (sequence roles (object macduff)))) (property macduff dead)) (property macduff dead))");
/* 55:   */   }
/* 56:   */   
/* 57:   */   @Test(timeout=20000L)
/* 58:   */   public void testMemoryClearing()
/* 59:   */     throws InterruptedException
/* 60:   */   {
/* 61:64 */     testStory("test/clear-memory.txt", "(sequence null_story (start you (sequence roles (object (story null_story)))) (is_called story null_story) (classification person robert))");
/* 62:65 */     testStory("test/clear-memory.txt", "(sequence null_story (start you (sequence roles (object (story null_story)))) (is_called story null_story) (classification person robert))");
/* 63:66 */     testStory("test/clear-memory.txt", "(sequence null_story (start you (sequence roles (object (story null_story)))) (is_called story null_story) (classification person robert))");
/* 64:   */   }
/* 65:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     core.StoryTests
 * JD-Core Version:    0.7.0.1
 */