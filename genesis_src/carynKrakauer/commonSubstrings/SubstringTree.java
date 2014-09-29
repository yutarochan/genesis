/*  1:   */ package carynKrakauer.commonSubstrings;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.Set;
/*  6:   */ 
/*  7:   */ public class SubstringTree
/*  8:   */ {
/*  9:   */   private HashMap<String, SubstringNode> nodes;
/* 10:   */   
/* 11:   */   public SubstringTree()
/* 12:   */   {
/* 13:12 */     this.nodes = new HashMap();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public void addStoryToTree(String title, ArrayList<String> story)
/* 17:   */   {
/* 18:17 */     for (int i = 0; i < story.size() - 1; i++)
/* 19:   */     {
/* 20:19 */       if (!this.nodes.containsKey(story.get(i))) {
/* 21:20 */         this.nodes.put((String)story.get(i), new SubstringNode((String)story.get(i)));
/* 22:   */       }
/* 23:24 */       if (!this.nodes.containsKey(story.get(i + 1))) {
/* 24:25 */         this.nodes.put((String)story.get(i + 1), new SubstringNode((String)story.get(i + 1)));
/* 25:   */       }
/* 26:28 */       ((SubstringNode)this.nodes.get(story.get(i))).addStoryLink(title, (SubstringNode)this.nodes.get(story.get(i + 1)));
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public CommonSubstringWrapper findCommonSubstrings(ArrayList<String> story)
/* 31:   */   {
/* 32:35 */     CommonSubstringWrapper bestCommonSubstrings = null;
/* 33:37 */     for (int i = 0; i < story.size(); i++)
/* 34:   */     {
/* 35:38 */       String reflection = (String)story.get(i);
/* 36:39 */       CommonSubstringWrapper commonSubstrings = new CommonSubstringWrapper();
/* 37:41 */       if (this.nodes.containsKey(reflection))
/* 38:   */       {
/* 39:42 */         SubstringNode rNode = (SubstringNode)this.nodes.get(reflection);
/* 40:43 */         Set<String> stories = rNode.getStoriesContaining();
/* 41:44 */         commonSubstrings.addReflection(reflection, stories.size());
/* 42:48 */         for (int j = i; j < story.size(); j++) {
/* 43:49 */           if (this.nodes.containsKey(story.get(j)))
/* 44:   */           {
/* 45:50 */             SubstringNode toNode = (SubstringNode)this.nodes.get(story.get(j));
/* 46:51 */             Set<String> toStories = rNode.getLinks(toNode);
/* 47:52 */             stories.retainAll(toStories);
/* 48:53 */             rNode = toNode;
/* 49:55 */             if (stories.size() == 0) {
/* 50:   */               break;
/* 51:   */             }
/* 52:59 */             commonSubstrings.addReflection((String)story.get(j), stories.size());
/* 53:   */           }
/* 54:   */         }
/* 55:65 */         if ((bestCommonSubstrings == null) || (commonSubstrings.size() > bestCommonSubstrings.size())) {
/* 56:66 */           bestCommonSubstrings = commonSubstrings;
/* 57:   */         }
/* 58:   */       }
/* 59:   */     }
/* 60:71 */     return bestCommonSubstrings;
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.commonSubstrings.SubstringTree
 * JD-Core Version:    0.7.0.1
 */