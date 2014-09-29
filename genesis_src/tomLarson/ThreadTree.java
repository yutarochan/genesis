/*   1:    */ package tomLarson;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Thread;
/*   5:    */ import java.io.PrintStream;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Set;
/*   8:    */ 
/*   9:    */ public class ThreadTree<N extends Node<Type>>
/*  10:    */ {
/*  11:    */   private Set<Node<Type>> nodes;
/*  12:    */   private Node<Type> head;
/*  13:    */   
/*  14:    */   public ThreadTree()
/*  15:    */   {
/*  16: 23 */     this.nodes = new HashSet();
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static ThreadTree makeThreadTree(Thread t)
/*  20:    */   {
/*  21: 27 */     ThreadTree tree = new ThreadTree();
/*  22: 28 */     tree.addThread(t);
/*  23: 29 */     return tree;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static ThreadTree makeThreadTree(Bundle b)
/*  27:    */   {
/*  28: 34 */     ThreadTree tree = new ThreadTree();
/*  29: 35 */     for (Thread t : b) {
/*  30: 36 */       tree.addThread(t);
/*  31:    */     }
/*  32: 38 */     return tree;
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void addRestofThread(Node<Type> n, String[] types, int start)
/*  36:    */   {
/*  37: 45 */     Node<Type> parent = n;
/*  38: 47 */     for (int i = start; i < types.length - 1; i++)
/*  39:    */     {
/*  40: 48 */       Node<Type> child = new Node(types[(i + 1)], 0.0D);
/*  41: 49 */       parent.addChild(child);
/*  42: 50 */       child.setParent(parent);
/*  43: 51 */       this.nodes.add(child);
/*  44: 52 */       parent = child;
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Node getHead()
/*  49:    */   {
/*  50: 57 */     return this.head;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void addThread(Thread t)
/*  54:    */   {
/*  55: 65 */     if (isEmpty())
/*  56:    */     {
/*  57: 66 */       String[] types = getAllTypes(t);
/*  58: 67 */       this.head = new Node(types[0], 0.0D);
/*  59: 68 */       this.nodes.add(this.head);
/*  60: 69 */       addRestofThread(this.head, types, 0);
/*  61:    */     }
/*  62: 71 */     String[] types = getAllTypes(t);
/*  63:    */     
/*  64: 73 */     String top = types[0].toLowerCase();
/*  65: 74 */     if (!top.equalsIgnoreCase(this.head.getName())) {
/*  66: 75 */       throw new RuntimeException("There's a Thing that's not a Thing!");
/*  67:    */     }
/*  68: 77 */     addThread(types, this.head, 1);
/*  69:    */   }
/*  70:    */   
/*  71:    */   private void addThread(String[] types, Node<Type> current, int index)
/*  72:    */   {
/*  73: 83 */     if (index >= types.length)
/*  74:    */     {
/*  75: 84 */       current.propagateImpact();
/*  76:    */     }
/*  77:    */     else
/*  78:    */     {
/*  79: 88 */       for (Node<Type> child : current.getChildren()) {
/*  80: 89 */         if (child.getName().equals(types[index]))
/*  81:    */         {
/*  82: 90 */           addThread(types, child, index + 1);
/*  83: 91 */           return;
/*  84:    */         }
/*  85:    */       }
/*  86: 94 */       addRestofThread(current, types, index - 1);
/*  87: 95 */       current.propagateImpact();
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Type getImpactofThread(Thread t)
/*  92:    */   {
/*  93:106 */     if (isEmpty()) {
/*  94:107 */       return new Type("empty", 0.0D);
/*  95:    */     }
/*  96:109 */     String[] types = getAllTypes(t);
/*  97:110 */     return getImpactofThread(types, this.head, 1, 0.0D);
/*  98:    */   }
/*  99:    */   
/* 100:    */   private Type getImpactofThread(String[] types, Node<Type> current, int index, double sum)
/* 101:    */   {
/* 102:116 */     if (index >= types.length) {
/* 103:116 */       return new Type(current.getName(), sum / types.length);
/* 104:    */     }
/* 105:117 */     for (Node<Type> child : current.getChildren()) {
/* 106:118 */       if (child.getName().equals(types[index])) {
/* 107:120 */         return getImpactofThread(types, child, index + 1, sum + child.getWeight());
/* 108:    */       }
/* 109:    */     }
/* 110:124 */     return new Type(current.getName(), sum / types.length);
/* 111:    */   }
/* 112:    */   
/* 113:    */   private int threadLength(Thread t)
/* 114:    */   {
/* 115:129 */     return getAllTypes(t).length;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isEmpty()
/* 119:    */   {
/* 120:133 */     return this.nodes.isEmpty();
/* 121:    */   }
/* 122:    */   
/* 123:    */   private String[] getAllTypes(Thread t)
/* 124:    */   {
/* 125:137 */     String stringRep = t.getString();
/* 126:138 */     return stringRep.split(" ");
/* 127:    */   }
/* 128:    */   
/* 129:    */   public String toString()
/* 130:    */   {
/* 131:144 */     String s = "";
/* 132:145 */     for (Node n : this.nodes) {
/* 133:146 */       s = 
/* 134:147 */         s + (n.getParent() == null ? "Node: " + n.getName() + ": " + n.getWeight() + ":" + n.getChildren().toString() + "none" + "\n" : new StringBuilder("Node: ").append(n.getName()).append(": ").append(n.getWeight()).append(":").append(n.getChildren().toString()).append(n.getParent().toString()).append("\n").toString());
/* 135:    */     }
/* 136:149 */     return s;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static void main(String[] args)
/* 140:    */   {
/* 141:154 */     Thread t = new Thread();
/* 142:155 */     t.addType("Thing");
/* 143:156 */     t.addType("dog");
/* 144:157 */     t.addType("Mac");
/* 145:158 */     Thread l = new Thread();
/* 146:159 */     l.addType("Thing");
/* 147:160 */     l.addType("dog");
/* 148:161 */     l.addType("Murphy");
/* 149:162 */     ThreadTree tree = makeThreadTree(t);
/* 150:163 */     tree.addThread(l);
/* 151:164 */     System.out.println(tree.toString());
/* 152:    */   }
/* 153:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.ThreadTree
 * JD-Core Version:    0.7.0.1
 */