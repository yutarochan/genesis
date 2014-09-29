/*   1:    */ package frames;
/*   2:    */ 
/*   3:    */ import ADTs.DirectedMultiGraph;
/*   4:    */ import java.util.HashSet;
/*   5:    */ 
/*   6:    */ public class NetFrame<T>
/*   7:    */ {
/*   8:    */   private DirectedMultiGraph<T, Relation> isNet;
/*   9:    */   private DirectedMultiGraph<T, Relation> hasNet;
/*  10:    */   
/*  11:    */   private static enum Relation
/*  12:    */   {
/*  13: 30 */     is,  has;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public NetFrame()
/*  17:    */   {
/*  18: 37 */     this.isNet = new DirectedMultiGraph();
/*  19: 38 */     this.hasNet = new DirectedMultiGraph();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public void addHas(T subject, T object)
/*  23:    */   {
/*  24: 49 */     this.hasNet.addEdge(subject, object, Relation.has);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public void addIs(T subject, T object)
/*  28:    */   {
/*  29: 63 */     this.isNet.addEdge(subject, object, Relation.is);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public HashSet<T> getHas(T subject)
/*  33:    */   {
/*  34: 74 */     return this.hasNet.getSuccessors(subject);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public HashSet<T> getIs(T subject)
/*  38:    */   {
/*  39: 85 */     return this.isNet.getSuccessors(subject);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public HashSet<T> getWhatIs(T object)
/*  43:    */   {
/*  44: 98 */     return this.isNet.getPredecessors(object);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public HashSet<T> getWhatHas(T object)
/*  48:    */   {
/*  49:110 */     return this.hasNet.getPredecessors(object);
/*  50:    */   }
/*  51:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.NetFrame
 * JD-Core Version:    0.7.0.1
 */