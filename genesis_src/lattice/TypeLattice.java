/*  1:   */ package lattice;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Thread;
/*  4:   */ import java.util.HashMap;
/*  5:   */ import java.util.HashSet;
/*  6:   */ import java.util.Iterator;
/*  7:   */ import java.util.List;
/*  8:   */ import java.util.Map;
/*  9:   */ import java.util.Set;
/* 10:   */ import java.util.Vector;
/* 11:   */ 
/* 12:   */ public class TypeLattice
/* 13:   */   extends Lattice<String>
/* 14:   */ {
/* 15:15 */   private Map<String, Set<String>> ancestry = new HashMap();
/* 16:   */   
/* 17:   */   public TypeLattice(Iterable<Thread> threads)
/* 18:   */   {
/* 19:   */     int size;
/* 20:   */     int index;
/* 21:19 */     for (Iterator localIterator = threads.iterator(); localIterator.hasNext(); index < size)
/* 22:   */     {
/* 23:19 */       Thread th = (Thread)localIterator.next();
/* 24:20 */       String parent = (String)th.get(0);
/* 25:21 */       size = th.size();
/* 26:22 */       index = 1; continue;
/* 27:23 */       String child = (String)th.get(index);
/* 28:24 */       if (!leq(child, parent))
/* 29:   */       {
/* 30:26 */         Set<String> parents = getParents(child);
/* 31:27 */         parents.add(parent);
/* 32:28 */         this.ancestry.put(child, parents);
/* 33:   */       }
/* 34:30 */       parent = child;index++;
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public Set<String> getParents(String node)
/* 39:   */   {
/* 40:39 */     return this.ancestry.containsKey(node) ? (Set)this.ancestry.get(node) : new HashSet();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public boolean leq(String node, String ancestor)
/* 44:   */   {
/* 45:43 */     if (node.equals(ancestor)) {
/* 46:44 */       return true;
/* 47:   */     }
/* 48:46 */     for (String parent : getParents(node)) {
/* 49:47 */       if (leq(parent, ancestor)) {
/* 50:48 */         return true;
/* 51:   */       }
/* 52:   */     }
/* 53:51 */     return false;
/* 54:   */   }
/* 55:   */   
/* 56:   */   public List<Set<String>> topologicalSort()
/* 57:   */   {
/* 58:58 */     Set<String> nodes = new HashSet();
/* 59:   */     Iterator localIterator2;
/* 60:59 */     for (Iterator localIterator1 = this.ancestry.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/* 61:   */     {
/* 62:59 */       String child = (String)localIterator1.next();
/* 63:60 */       nodes.add(child);
/* 64:61 */       localIterator2 = getParents(child).iterator(); continue;String parent = (String)localIterator2.next();
/* 65:62 */       nodes.add(parent);
/* 66:   */     }
/* 67:65 */     return sort(nodes, new HashSet());
/* 68:   */   }
/* 69:   */   
/* 70:   */   private List<Set<String>> sort(Set<String> nodes, Set<String> done)
/* 71:   */   {
/* 72:69 */     List<Set<String>> result = new Vector();
/* 73:71 */     if (nodes.isEmpty()) {
/* 74:72 */       return result;
/* 75:   */     }
/* 76:75 */     Set<String> this_level = new HashSet();
/* 77:76 */     for (String node : nodes)
/* 78:   */     {
/* 79:77 */       Set<String> ancestors = new HashSet();
/* 80:78 */       ancestors.addAll(getParents(node));
/* 81:79 */       ancestors.removeAll(done);
/* 82:81 */       if (ancestors.isEmpty()) {
/* 83:82 */         this_level.add(node);
/* 84:   */       }
/* 85:   */     }
/* 86:86 */     done.addAll(this_level);
/* 87:87 */     nodes.removeAll(this_level);
/* 88:   */     
/* 89:89 */     result.add(this_level);
/* 90:90 */     result.addAll(sort(nodes, done));
/* 91:   */     
/* 92:92 */     return result;
/* 93:   */   }
/* 94:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     lattice.TypeLattice
 * JD-Core Version:    0.7.0.1
 */