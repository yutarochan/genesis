/*   1:    */ package carynKrakauer.generatedPatterns;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.LinkedList;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.Vector;
/*  13:    */ import utils.Mark;
/*  14:    */ 
/*  15:    */ public class PlotEvent
/*  16:    */   implements Comparable<PlotEvent>
/*  17:    */ {
/*  18:    */   private Entity t;
/*  19:    */   private String type;
/*  20:    */   private List<Entity> parents;
/*  21:    */   
/*  22:    */   public static ArrayList<PlotEvent> convertFromSequence(Sequence sequence)
/*  23:    */   {
/*  24: 16 */     ArrayList<PlotEvent> patterns = new ArrayList();
/*  25: 18 */     for (Entity t : sequence.getElements()) {
/*  26: 19 */       patterns.add(new PlotEvent(t));
/*  27:    */     }
/*  28: 22 */     return patterns;
/*  29:    */   }
/*  30:    */   
/*  31:    */   public PlotEvent(Entity t)
/*  32:    */   {
/*  33: 30 */     this.t = t;
/*  34: 31 */     this.type = t.getName().split("-")[0];
/*  35:    */     
/*  36: 33 */     this.parents = new LinkedList();
/*  37: 34 */     this.parents = buildParents(t);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<Entity> getParents()
/*  41:    */   {
/*  42: 38 */     return this.parents;
/*  43:    */   }
/*  44:    */   
/*  45:    */   private List<Entity> buildParents(Entity t)
/*  46:    */   {
/*  47: 45 */     LinkedList<Entity> outParents = new LinkedList();
/*  48: 46 */     LinkedList<Entity> parents = new LinkedList();
/*  49: 47 */     HashSet<Entity> checked = new HashSet();
/*  50:    */     Entity causeElm;
/*  51:    */     Iterator localIterator2;
/*  52:    */     label177:
/*  53: 49 */     for (Iterator localIterator1 = t.getParents().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  54:    */     {
/*  55: 49 */       Entity parent = (Entity)localIterator1.next();
/*  56: 50 */       if ((!parent.getClass().equals(Sequence.class)) || (!parent.getType().equals("thing"))) {
/*  57:    */         break label177;
/*  58:    */       }
/*  59: 51 */       causeElm = parent.getElement(0);
/*  60: 52 */       if ((!(causeElm instanceof Relation)) || 
/*  61: 53 */         (!causeElm.getType().equals("cause"))) {
/*  62:    */         break label177;
/*  63:    */       }
/*  64: 54 */       Relation relElm = (Relation)causeElm;
/*  65: 55 */       Entity conjElm = relElm.getSubject();
/*  66: 56 */       localIterator2 = conjElm.getElements().iterator(); continue;Entity cause = (Entity)localIterator2.next();
/*  67:    */       
/*  68: 58 */       parents.add(cause);
/*  69: 59 */       outParents.add(cause);
/*  70: 60 */       checked.add(cause);
/*  71:    */     }
/*  72: 66 */     for (; parents.size() != 0; causeElm.hasNext())
/*  73:    */     {
/*  74: 67 */       Entity par = (Entity)parents.pop();
/*  75:    */       
/*  76: 69 */       causeElm = par.getParents().iterator(); continue;Entity parent = (Entity)causeElm.next();
/*  77: 70 */       if ((parent.getClass().equals(Sequence.class)) && (parent.getType().equals("thing")))
/*  78:    */       {
/*  79: 75 */         Entity causeElm = parent.getElement(0);
/*  80: 76 */         if (((causeElm instanceof Relation)) && 
/*  81: 77 */           (causeElm.getType().equals("cause")))
/*  82:    */         {
/*  83: 78 */           Relation relElm = (Relation)causeElm;
/*  84: 79 */           Entity conjElm = relElm.getSubject();
/*  85: 80 */           for (Entity cause : conjElm.getElements()) {
/*  86: 82 */             if (!checked.contains(cause))
/*  87:    */             {
/*  88: 86 */               parents.add(cause);
/*  89: 87 */               outParents.add(cause);
/*  90: 88 */               checked.add(cause);
/*  91:    */             }
/*  92:    */           }
/*  93:    */         }
/*  94:    */       }
/*  95:    */     }
/*  96: 96 */     return outParents;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getType()
/* 100:    */   {
/* 101:100 */     return this.type;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Entity getThing()
/* 105:    */   {
/* 106:104 */     return this.t;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getString()
/* 110:    */   {
/* 111:108 */     if (this.type.equals("property")) {
/* 112:109 */       return "property-" + this.t.getObject().asStringWithoutIndexes();
/* 113:    */     }
/* 114:111 */     return this.type;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int compareTo(PlotEvent arg0)
/* 118:    */   {
/* 119:116 */     Mark.say(
/* 120:    */     
/* 121:118 */       new Object[] { "ERROR: CALLING PLOT EVENT COMPARE TO!!!!!!!!!!!" });return 0;
/* 122:    */   }
/* 123:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.generatedPatterns.PlotEvent
 * JD-Core Version:    0.7.0.1
 */