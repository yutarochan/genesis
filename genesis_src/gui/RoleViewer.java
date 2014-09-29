/*   1:    */ package gui;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Entity;
/*   4:    */ import bridge.reps.entities.Relation;
/*   5:    */ import java.awt.Color;
/*   6:    */ import java.awt.Container;
/*   7:    */ import java.util.Vector;
/*   8:    */ import javax.swing.BorderFactory;
/*   9:    */ import javax.swing.JFrame;
/*  10:    */ import javax.swing.JLabel;
/*  11:    */ import utils.Mark;
/*  12:    */ 
/*  13:    */ public class RoleViewer
/*  14:    */   extends NegatableJPanel
/*  15:    */ {
/*  16:    */   JLabel guts;
/*  17:    */   
/*  18:    */   public RoleViewer()
/*  19:    */   {
/*  20: 22 */     this.guts = new JLabel();
/*  21: 23 */     add(this.guts);
/*  22: 24 */     setBorder(BorderFactory.createLineBorder(Color.BLACK));
/*  23: 25 */     setBackground(Color.WHITE);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void view(Object o)
/*  27:    */   {
/*  28: 29 */     if ((o instanceof Relation))
/*  29:    */     {
/*  30: 30 */       Relation t = (Relation)o;
/*  31: 31 */       if ((t.relationP()) && (t.getObject().sequenceP("roles")))
/*  32:    */       {
/*  33: 32 */         Vector<Entity> roles = t.getObject().getElements();
/*  34: 33 */         Entity object = theObject(roles);
/*  35: 34 */         roles = withoutObject(roles);
/*  36:    */         
/*  37:    */ 
/*  38:    */ 
/*  39: 38 */         String table = "";
/*  40: 39 */         table = table + "<html>";
/*  41: 40 */         table = table + "<table>";
/*  42: 41 */         table = table + "<tr><td>actor:</td><td>" + t.getSubject().getType() + "</td></tr>";
/*  43: 42 */         table = table + "<tr><td>action:</td><td>" + t.getType() + "</td></tr>";
/*  44: 43 */         if (object != null) {
/*  45: 44 */           table = table + "<tr><td>object:</td><td>" + object.getType() + "</td></tr>";
/*  46:    */         }
/*  47: 46 */         for (Entity element : roles) {
/*  48: 47 */           table = table + "<tr><td>" + element.getType() + ":</td><td>" + element.getSubject().getType() + "</td></tr>";
/*  49:    */         }
/*  50: 61 */         table = table + "</table>";
/*  51: 62 */         table = table + "</html>";
/*  52: 63 */         this.guts.setText(table);
/*  53:    */       }
/*  54:    */       else
/*  55:    */       {
/*  56: 67 */         Mark.err(new Object[] {"Role viewer got frame which is not a role frame", t.asString() });
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   private Vector<Entity> withoutObject(Vector<Entity> elements)
/*  62:    */   {
/*  63: 73 */     Vector<Entity> result = new Vector();
/*  64: 74 */     if (elements != null) {
/*  65: 75 */       for (Entity t : elements) {
/*  66: 76 */         if (!t.functionP("object")) {
/*  67: 77 */           result.add(t);
/*  68:    */         }
/*  69:    */       }
/*  70:    */     }
/*  71: 81 */     return result;
/*  72:    */   }
/*  73:    */   
/*  74:    */   private Entity theObject(Vector<Entity> elements)
/*  75:    */   {
/*  76: 85 */     if (elements != null) {
/*  77: 86 */       for (Entity t : elements) {
/*  78: 87 */         if (t.functionP("object")) {
/*  79: 88 */           return t.getSubject();
/*  80:    */         }
/*  81:    */       }
/*  82:    */     }
/*  83: 92 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public static void main(String[] ignore)
/*  87:    */   {
/*  88: 96 */     JFrame f = new JFrame();
/*  89: 97 */     RoleViewer v = new RoleViewer();
/*  90: 98 */     v.view(new Object());
/*  91: 99 */     f.getContentPane().add(v);
/*  92:100 */     f.setSize(300, 200);
/*  93:101 */     f.setVisible(true);
/*  94:    */   }
/*  95:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     gui.RoleViewer
 * JD-Core Version:    0.7.0.1
 */