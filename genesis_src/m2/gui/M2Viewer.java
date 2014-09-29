/*  1:   */ package m2.gui;
/*  2:   */ 
/*  3:   */ import connections.Connections;
/*  4:   */ import connections.Ports;
/*  5:   */ import connections.WiredBox;
/*  6:   */ import java.awt.BorderLayout;
/*  7:   */ import java.awt.Dimension;
/*  8:   */ import java.awt.event.ActionEvent;
/*  9:   */ import java.awt.event.ActionListener;
/* 10:   */ import java.io.PrintStream;
/* 11:   */ import java.util.Set;
/* 12:   */ import javax.swing.JButton;
/* 13:   */ import javax.swing.JList;
/* 14:   */ import javax.swing.JPanel;
/* 15:   */ import javax.swing.JScrollPane;
/* 16:   */ import javax.swing.event.ListSelectionEvent;
/* 17:   */ import javax.swing.event.ListSelectionListener;
/* 18:   */ import m2.M2;
/* 19:   */ import m2.Mem;
/* 20:   */ import m2.datatypes.Chain;
/* 21:   */ 
/* 22:   */ public class M2Viewer
/* 23:   */   extends JPanel
/* 24:   */   implements WiredBox
/* 25:   */ {
/* 26:   */   private static final long serialVersionUID = 7584315067624862864L;
/* 27:26 */   M2List m2list = new M2List(null);
/* 28:27 */   ChainViewer chainviewer = new ChainViewer();
/* 29:28 */   JButton refreshButton = new JButton("Refresh");
/* 30:   */   
/* 31:   */   public M2Viewer()
/* 32:   */   {
/* 33:34 */     setLayout(new BorderLayout());
/* 34:   */     
/* 35:36 */     this.m2list.setSelectionMode(0);
/* 36:37 */     this.m2list.setVisibleRowCount(-1);
/* 37:38 */     this.m2list.addListSelectionListener(this.m2list);
/* 38:39 */     JScrollPane listScroller = new JScrollPane(this.m2list);
/* 39:40 */     listScroller.setPreferredSize(new Dimension(250, 80));
/* 40:   */     
/* 41:42 */     this.refreshButton.addActionListener(new RefreshListener(null));
/* 42:   */     
/* 43:44 */     add(this.refreshButton, "North");
/* 44:45 */     add(listScroller, "Center");
/* 45:46 */     add(this.chainviewer, "South");
/* 46:47 */     Connections.getPorts(this).addSignalProcessor("input");
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void display(Set<Chain> chains)
/* 50:   */   {
/* 51:54 */     if (chains != null) {
/* 52:55 */       this.m2list.setListData(chains.toArray());
/* 53:   */     }
/* 54:57 */     this.chainviewer.clear();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void input(Object input)
/* 58:   */   {
/* 59:64 */     if ((input instanceof Set))
/* 60:   */     {
/* 61:65 */       Set<Chain> clean = (Set)input;
/* 62:66 */       if ((!clean.isEmpty()) && ((clean.toArray()[0] instanceof Chain)))
/* 63:   */       {
/* 64:67 */         display(clean);
/* 65:68 */         return;
/* 66:   */       }
/* 67:   */     }
/* 68:71 */     System.err.println("Bad input to M2Viewer: " + input);
/* 69:   */   }
/* 70:   */   
/* 71:   */   private void showSelectedChain()
/* 72:   */   {
/* 73:76 */     if (this.m2list.getSelectedIndex() != -1)
/* 74:   */     {
/* 75:80 */       Chain selected = (Chain)this.m2list.getSelectedValue();
/* 76:81 */       this.chainviewer.viewChain(selected);
/* 77:   */     }
/* 78:   */   }
/* 79:   */   
/* 80:   */   private class M2List
/* 81:   */     extends JList
/* 82:   */     implements ListSelectionListener
/* 83:   */   {
/* 84:   */     private static final long serialVersionUID = -903641950356317204L;
/* 85:   */     
/* 86:   */     private M2List() {}
/* 87:   */     
/* 88:   */     public void valueChanged(ListSelectionEvent e)
/* 89:   */     {
/* 90:88 */       if (!e.getValueIsAdjusting()) {
/* 91:89 */         M2Viewer.this.showSelectedChain();
/* 92:   */       }
/* 93:   */     }
/* 94:   */   }
/* 95:   */   
/* 96:   */   private class RefreshListener
/* 97:   */     implements ActionListener
/* 98:   */   {
/* 99:   */     private RefreshListener() {}
/* :0:   */     
/* :1:   */     public void actionPerformed(ActionEvent arg0)
/* :2:   */     {
/* :3:98 */       Mem mem = M2.getMem();
/* :4:99 */       mem.outputAll();
/* :5:   */     }
/* :6:   */   }
/* :7:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     m2.gui.M2Viewer
 * JD-Core Version:    0.7.0.1
 */