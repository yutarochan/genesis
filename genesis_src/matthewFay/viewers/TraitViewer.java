/*  1:   */ package matthewFay.viewers;
/*  2:   */ 
/*  3:   */ import Signals.BetterSignal;
/*  4:   */ import ati.ParallelJPanel;
/*  5:   */ import bridge.reps.entities.Entity;
/*  6:   */ import bridge.reps.entities.Sequence;
/*  7:   */ import connections.Connections;
/*  8:   */ import connections.Ports;
/*  9:   */ import connections.WiredBox;
/* 10:   */ import gui.ElaborationView;
/* 11:   */ import java.awt.BorderLayout;
/* 12:   */ import java.util.ArrayList;
/* 13:   */ import java.util.HashMap;
/* 14:   */ import java.util.HashSet;
/* 15:   */ import java.util.List;
/* 16:   */ import java.util.Map;
/* 17:   */ import java.util.Set;
/* 18:   */ import javax.swing.JCheckBox;
/* 19:   */ import javax.swing.JPanel;
/* 20:   */ import javax.swing.JTabbedPane;
/* 21:   */ import matthewFay.CharacterModeling.representations.Trait;
/* 22:   */ 
/* 23:   */ public class TraitViewer
/* 24:   */   extends JPanel
/* 25:   */   implements WiredBox
/* 26:   */ {
/* 27:28 */   private static TraitViewer viewer = null;
/* 28:   */   
/* 29:   */   public static TraitViewer getTraitViewer()
/* 30:   */   {
/* 31:30 */     if (viewer == null) {
/* 32:31 */       viewer = new TraitViewer();
/* 33:   */     }
/* 34:33 */     return viewer;
/* 35:   */   }
/* 36:   */   
/* 37:36 */   private String name = "Trait Viewer";
/* 38:   */   private JTabbedPane tabbed_pane;
/* 39:   */   private ParallelJPanel control_panel;
/* 40:   */   
/* 41:   */   public String getName()
/* 42:   */   {
/* 43:38 */     return this.name;
/* 44:   */   }
/* 45:   */   
/* 46:45 */   public JCheckBox generalize_trait_description = new JCheckBox("Generalize Trait Descriptions", false);
/* 47:47 */   private List<Trait> traits = new ArrayList();
/* 48:48 */   private Map<Trait, ElaborationView> trait_tabs = new HashMap();
/* 49:   */   
/* 50:   */   public TraitViewer()
/* 51:   */   {
/* 52:51 */     super(new BorderLayout());
/* 53:52 */     this.tabbed_pane = new JTabbedPane();
/* 54:   */     
/* 55:54 */     this.control_panel = new ParallelJPanel();
/* 56:55 */     this.control_panel.addLeft(this.generalize_trait_description);
/* 57:56 */     this.tabbed_pane.addTab("Controls", this.control_panel);
/* 58:   */     
/* 59:58 */     add(this.tabbed_pane);
/* 60:   */     
/* 61:60 */     Connections.getPorts(this).addSignalProcessor("processTrait");
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void processTrait(Object o)
/* 65:   */   {
/* 66:64 */     if ((o instanceof Trait)) {
/* 67:65 */       addTrait((Trait)o);
/* 68:   */     }
/* 69:   */   }
/* 70:   */   
/* 71:   */   public void addTrait(Trait trait)
/* 72:   */   {
/* 73:70 */     String trait_name = trait.getName();
/* 74:71 */     if (!this.traits.contains(trait))
/* 75:   */     {
/* 76:72 */       this.traits.add(trait);
/* 77:   */       
/* 78:74 */       ElaborationView elaboration_viewer = new ElaborationView();
/* 79:75 */       elaboration_viewer.setAlwaysShowAllElements(true);
/* 80:   */       
/* 81:77 */       Connections.wire(trait_name, this, "inspection port", elaboration_viewer);
/* 82:   */       
/* 83:79 */       this.tabbed_pane.addTab(trait_name, elaboration_viewer);
/* 84:   */       
/* 85:81 */       this.trait_tabs.put(trait, elaboration_viewer);
/* 86:   */     }
/* 87:85 */     trait.markDirty();
/* 88:   */     
/* 89:87 */     Set<Entity> elts = new HashSet(trait.getElements());
/* 90:88 */     Sequence seq = new Sequence();
/* 91:89 */     for (Entity e : trait.getElements()) {
/* 92:90 */       seq.addElement(e);
/* 93:   */     }
/* 94:91 */     BetterSignal bs = new BetterSignal(new Object[] { elts, seq });
/* 95:92 */     Connections.getPorts(this).transmit(trait_name, bs);
/* 96:   */   }
/* 97:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.viewers.TraitViewer
 * JD-Core Version:    0.7.0.1
 */