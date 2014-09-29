/*   1:    */ package kevinWhite;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Thread;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.Vector;
/*  11:    */ 
/*  12:    */ public class TypeLattice
/*  13:    */   extends Lattice<String>
/*  14:    */ {
/*  15: 20 */   private Map<String, Set<String>> ancestry = new HashMap();
/*  16:    */   
/*  17:    */   public TypeLattice(Iterable<Thread> threads)
/*  18:    */   {
/*  19: 29 */     for (Thread th : threads) {
/*  20: 30 */       updateAncestry(th);
/*  21:    */     }
/*  22:    */   }
/*  23:    */   
/*  24:    */   public TypeLattice() {}
/*  25:    */   
/*  26:    */   public Set<String> getParents(String node)
/*  27:    */   {
/*  28: 48 */     return this.ancestry.containsKey(node) ? (Set)this.ancestry.get(node) : new HashSet();
/*  29:    */   }
/*  30:    */   
/*  31:    */   public boolean leq(String node, String ancestor)
/*  32:    */   {
/*  33: 55 */     if (node.equals(ancestor)) {
/*  34: 56 */       return true;
/*  35:    */     }
/*  36: 58 */     for (String parent : getParents(node)) {
/*  37: 59 */       if (leq(parent, ancestor)) {
/*  38: 60 */         return true;
/*  39:    */       }
/*  40:    */     }
/*  41: 63 */     return false;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void updateAncestry(Thread th)
/*  45:    */   {
/*  46: 74 */     String parent = (String)th.get(0);
/*  47: 75 */     int threadSize = th.size();
/*  48: 76 */     for (int index = 1; index < threadSize; index++)
/*  49:    */     {
/*  50: 77 */       String child = (String)th.get(index);
/*  51: 78 */       if (!leq(child, parent))
/*  52:    */       {
/*  53: 80 */         Set<String> parents = getParents(child);
/*  54: 81 */         parents.add(parent);
/*  55: 82 */         this.ancestry.put(child, parents);
/*  56:    */       }
/*  57: 84 */       parent = child;
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public List<Set<String>> topologicalSort()
/*  62:    */   {
/*  63: 94 */     Set<String> nodes = new HashSet();
/*  64:    */     Iterator localIterator2;
/*  65: 95 */     for (Iterator localIterator1 = this.ancestry.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  66:    */     {
/*  67: 95 */       String child = (String)localIterator1.next();
/*  68: 96 */       nodes.add(child);
/*  69: 97 */       localIterator2 = getParents(child).iterator(); continue;String parent = (String)localIterator2.next();
/*  70: 98 */       nodes.add(parent);
/*  71:    */     }
/*  72:101 */     return sort(nodes, new HashSet());
/*  73:    */   }
/*  74:    */   
/*  75:    */   private List<Set<String>> sort(Set<String> nodes, Set<String> done)
/*  76:    */   {
/*  77:112 */     List<Set<String>> result = new Vector();
/*  78:114 */     if (nodes.isEmpty()) {
/*  79:115 */       return result;
/*  80:    */     }
/*  81:118 */     Set<String> this_level = new HashSet();
/*  82:123 */     for (String node : nodes)
/*  83:    */     {
/*  84:124 */       Set<String> ancestors = new HashSet();
/*  85:125 */       ancestors.addAll(getParents(node));
/*  86:126 */       ancestors.removeAll(done);
/*  87:128 */       if (ancestors.isEmpty()) {
/*  88:129 */         this_level.add(node);
/*  89:    */       }
/*  90:    */     }
/*  91:133 */     done.addAll(this_level);
/*  92:134 */     nodes.removeAll(this_level);
/*  93:    */     
/*  94:    */ 
/*  95:137 */     result.add(this_level);
/*  96:    */     
/*  97:139 */     result.addAll(sort(nodes, done));
/*  98:    */     
/*  99:141 */     return result;
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     kevinWhite.TypeLattice
 * JD-Core Version:    0.7.0.1
 */