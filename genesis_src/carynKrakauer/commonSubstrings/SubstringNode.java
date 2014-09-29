/*  1:   */ package carynKrakauer.commonSubstrings;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.Set;
/*  7:   */ 
/*  8:   */ public class SubstringNode
/*  9:   */ {
/* 10:   */   private String reflection;
/* 11:   */   private HashMap<String, ArrayList<SubstringNode>> storyLinks;
/* 12:   */   private HashMap<SubstringNode, HashSet<String>> substringLinks;
/* 13:   */   
/* 14:   */   public SubstringNode(String reflection)
/* 15:   */   {
/* 16:18 */     this.reflection = reflection;
/* 17:   */     
/* 18:20 */     this.storyLinks = new HashMap();
/* 19:21 */     this.substringLinks = new HashMap();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public Set<String> getStoriesContaining()
/* 23:   */   {
/* 24:25 */     return this.storyLinks.keySet();
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Set<String> getLinks(SubstringNode toNode)
/* 28:   */   {
/* 29:29 */     return (Set)this.substringLinks.get(toNode);
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void addStoryLink(String story, SubstringNode link)
/* 33:   */   {
/* 34:34 */     if (this.storyLinks.containsKey(story))
/* 35:   */     {
/* 36:35 */       ((ArrayList)this.storyLinks.get(story)).add(link);
/* 37:   */     }
/* 38:   */     else
/* 39:   */     {
/* 40:38 */       ArrayList<SubstringNode> storyLink = new ArrayList();
/* 41:39 */       storyLink.add(link);
/* 42:40 */       this.storyLinks.put(story, storyLink);
/* 43:   */     }
/* 44:43 */     if (this.substringLinks.containsKey(link))
/* 45:   */     {
/* 46:44 */       ((HashSet)this.substringLinks.get(link)).add(story);
/* 47:   */     }
/* 48:   */     else
/* 49:   */     {
/* 50:47 */       HashSet<String> storySet = new HashSet();
/* 51:48 */       storySet.add(story);
/* 52:49 */       this.substringLinks.put(link, storySet);
/* 53:   */     }
/* 54:   */   }
/* 55:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.commonSubstrings.SubstringNode
 * JD-Core Version:    0.7.0.1
 */