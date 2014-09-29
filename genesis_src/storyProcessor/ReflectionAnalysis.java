/*  1:   */ package storyProcessor;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Sequence;
/*  4:   */ import java.util.ArrayList;
/*  5:   */ import text.Punctuator;
/*  6:   */ 
/*  7:   */ public class ReflectionAnalysis
/*  8:   */ {
/*  9:   */   ArrayList<ReflectionDescription> reflections;
/* 10:   */   Sequence story;
/* 11:   */   String storyName;
/* 12:   */   
/* 13:   */   public ReflectionAnalysis(ArrayList<ReflectionDescription> completions, Sequence story)
/* 14:   */   {
/* 15:26 */     this.reflections = completions;
/* 16:27 */     if ((story == null) && (completions != null) && (!completions.isEmpty())) {
/* 17:28 */       story = ((ReflectionDescription)completions.get(0)).getStory();
/* 18:   */     }
/* 19:30 */     this.storyName = Punctuator.conditionName(StoryProcessor.getTitle(story));
/* 20:31 */     this.story = story;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public ArrayList<ReflectionDescription> getReflectionDescriptions()
/* 24:   */   {
/* 25:40 */     return this.reflections;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public Sequence getStory()
/* 29:   */   {
/* 30:47 */     return this.story;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getStoryName()
/* 34:   */   {
/* 35:54 */     return this.storyName;
/* 36:   */   }
/* 37:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.ReflectionAnalysis
 * JD-Core Version:    0.7.0.1
 */