/*   1:    */ package storyProcessor;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Sequence;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Vector;
/*   7:    */ import minilisp.LList;
/*   8:    */ import utils.PairOfEntities;
/*   9:    */ 
/*  10:    */ public class ReflectionDescription
/*  11:    */ {
/*  12:    */   String name;
/*  13:    */   LList<PairOfEntities> bindings;
/*  14:    */   Sequence rules;
/*  15: 23 */   Sequence instantiations = new Sequence();
/*  16: 25 */   Sequence storyElementsInvolved = new Sequence();
/*  17:    */   Sequence story;
/*  18:    */   
/*  19:    */   public ReflectionDescription() {}
/*  20:    */   
/*  21:    */   public ReflectionDescription(String name, LList<PairOfEntities> bindings, Sequence storyElementsInvolved, Sequence instantiations, Sequence story)
/*  22:    */   {
/*  23: 34 */     this.name = name;
/*  24: 35 */     this.bindings = bindings;
/*  25: 36 */     this.storyElementsInvolved = storyElementsInvolved;
/*  26:    */     
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32: 43 */     this.instantiations = instantiations;
/*  33: 44 */     this.story = story;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Sequence getRules()
/*  37:    */   {
/*  38: 52 */     if (this.rules == null)
/*  39:    */     {
/*  40: 54 */       this.rules = new Sequence();
/*  41: 55 */       for (Entity t : this.instantiations.getElements()) {
/*  42: 56 */         if (t.sequenceP("path"))
/*  43:    */         {
/*  44:    */           Iterator localIterator3;
/*  45: 57 */           for (Iterator localIterator2 = t.getElements().iterator(); localIterator2.hasNext(); localIterator3.hasNext())
/*  46:    */           {
/*  47: 57 */             Entity p = (Entity)localIterator2.next();
/*  48:    */             
/*  49:    */ 
/*  50: 60 */             localIterator3 = this.story.getElements().iterator(); continue;Entity s = (Entity)localIterator3.next();
/*  51: 61 */             if ((s.relationP("cause")) && 
/*  52: 62 */               (s.getObject() == p)) {
/*  53: 63 */               this.rules.addElement(s);
/*  54:    */             }
/*  55:    */           }
/*  56:    */         }
/*  57:    */       }
/*  58:    */     }
/*  59: 71 */     return this.rules;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String getName()
/*  63:    */   {
/*  64: 78 */     return this.name;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public LList<PairOfEntities> getBindings()
/*  68:    */   {
/*  69: 86 */     return this.bindings;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public Sequence getInstantiations()
/*  73:    */   {
/*  74: 90 */     return this.instantiations;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Sequence getStory()
/*  78:    */   {
/*  79: 97 */     return this.story;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Sequence getStoryElementsInvolved()
/*  83:    */   {
/*  84:104 */     return this.storyElementsInvolved;
/*  85:    */   }
/*  86:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     storyProcessor.ReflectionDescription
 * JD-Core Version:    0.7.0.1
 */