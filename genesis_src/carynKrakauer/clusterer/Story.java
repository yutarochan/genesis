/*  1:   */ package carynKrakauer.clusterer;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.LinkedList;
/*  5:   */ import matthewFay.Utilities.Pair;
/*  6:   */ import storyProcessor.ReflectionDescription;
/*  7:   */ 
/*  8:   */ public class Story
/*  9:   */ {
/* 10:   */   ArrayList<ReflectionDescription> refDesc;
/* 11:   */   Story mean;
/* 12:   */   LinkedList<Pair<Story, Difference>> connections;
/* 13:   */   
/* 14:   */   public Story(ArrayList<ReflectionDescription> refDesc)
/* 15:   */   {
/* 16:15 */     this.refDesc = refDesc;
/* 17:16 */     this.mean = null;
/* 18:17 */     this.connections = new LinkedList();
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setMean(Story newMean)
/* 22:   */   {
/* 23:21 */     this.mean = newMean;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void addConnection(Story story)
/* 27:   */   {
/* 28:25 */     this.connections.add(new Pair(story, new Difference(this, story)));
/* 29:   */   }
/* 30:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.clusterer.Story
 * JD-Core Version:    0.7.0.1
 */