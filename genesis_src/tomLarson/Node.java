/*   1:    */ package tomLarson;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Set;
/*   5:    */ 
/*   6:    */ public class Node<T extends Type>
/*   7:    */ {
/*   8:    */   private Type type;
/*   9:    */   private Node parent;
/*  10:    */   private Set<Node> children;
/*  11:    */   public static final int propdist = 10;
/*  12:    */   public static final double impactDecreaseFactor = 0.5D;
/*  13:    */   public static final double startImpact = 1.0D;
/*  14:    */   
/*  15:    */   public Node(T type)
/*  16:    */   {
/*  17: 26 */     this.type = type;
/*  18: 27 */     this.parent = null;
/*  19: 28 */     this.children = new HashSet();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Node(String name, double weight)
/*  23:    */   {
/*  24: 37 */     this.type = new Type(name, weight);
/*  25: 38 */     this.parent = null;
/*  26: 39 */     this.children = new HashSet();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void propagateImpact()
/*  30:    */   {
/*  31: 49 */     propagateImpact(1.0D, 10);
/*  32:    */   }
/*  33:    */   
/*  34:    */   private void propagateImpact(double impact, int moreLevels)
/*  35:    */   {
/*  36: 54 */     if (moreLevels <= 0) {
/*  37: 54 */       return;
/*  38:    */     }
/*  39: 56 */     setWeight(getWeight() + impact);
/*  40: 57 */     Node parent = getParent();
/*  41: 58 */     if (parent != null) {
/*  42: 59 */       parent.propagateImpact(impact * 0.5D, moreLevels - 1);
/*  43:    */     }
/*  44: 61 */     for (Node child : getChildren()) {
/*  45: 62 */       child.propagateImpact(impact * 0.5D, moreLevels - 1);
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String getName()
/*  50:    */   {
/*  51: 71 */     return this.type.getName();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public boolean equals(Object o)
/*  55:    */   {
/*  56: 75 */     if ((o instanceof Node))
/*  57:    */     {
/*  58: 76 */       Node node = (Node)o;
/*  59: 77 */       return node.getName().equals(this.type.getName());
/*  60:    */     }
/*  61: 79 */     return false;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setWeight(double newWeight)
/*  65:    */   {
/*  66: 83 */     this.type.setWeight(newWeight);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setParent(Node n)
/*  70:    */   {
/*  71: 90 */     this.parent = n;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void addChild(Node n)
/*  75:    */   {
/*  76: 98 */     this.children.add(n);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Node getParent()
/*  80:    */   {
/*  81:102 */     return this.parent;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Set<Node> getChildren()
/*  85:    */   {
/*  86:106 */     return this.children;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public double getWeight()
/*  90:    */   {
/*  91:110 */     return this.type.getWeight();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String toString()
/*  95:    */   {
/*  96:115 */     return this.type.getName();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public Type getType()
/* 100:    */   {
/* 101:119 */     return this.type;
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     tomLarson.Node
 * JD-Core Version:    0.7.0.1
 */