/*   1:    */ package adamKraft;
/*   2:    */ 
/*   3:    */ import connections.WiredBox;
/*   4:    */ import java.awt.Container;
/*   5:    */ import java.awt.GridLayout;
/*   6:    */ import java.awt.event.ActionEvent;
/*   7:    */ import java.awt.event.ActionListener;
/*   8:    */ import java.io.IOException;
/*   9:    */ import java.util.ArrayList;
/*  10:    */ import java.util.Vector;
/*  11:    */ import javax.swing.DefaultListModel;
/*  12:    */ import javax.swing.JCheckBox;
/*  13:    */ import javax.swing.JFrame;
/*  14:    */ import javax.swing.JList;
/*  15:    */ import javax.swing.JPanel;
/*  16:    */ import javax.swing.JScrollPane;
/*  17:    */ import javax.swing.event.ListSelectionEvent;
/*  18:    */ import javax.swing.event.ListSelectionListener;
/*  19:    */ import utils.Mark;
/*  20:    */ 
/*  21:    */ public class VideoSelectorPanel
/*  22:    */   extends JPanel
/*  23:    */   implements WiredBox
/*  24:    */ {
/*  25:    */   private JPanel verbPanel;
/*  26:    */   private JList videoPanel;
/*  27:    */   private ArrayList<JCheckBox> boxes;
/*  28:    */   private VideoBrowser videoBrowser;
/*  29:    */   ArrayList<String> allVideos;
/*  30:    */   ArrayList<String> selectedVideos;
/*  31:    */   private ActionListener checkBoxListener;
/*  32:    */   private Vector<String> listElements;
/*  33:    */   
/*  34:    */   public VideoSelectorPanel()
/*  35:    */   {
/*  36: 38 */     setLayout(new GridLayout(1, 0));
/*  37: 39 */     add(getVerbPanel());
/*  38: 40 */     add(new JScrollPane(getVideoPanel()));
/*  39: 41 */     initializeVerbs();
/*  40: 42 */     getAllVideos();
/*  41:    */   }
/*  42:    */   
/*  43:    */   private void initializeVerbs()
/*  44:    */   {
/*  45: 46 */     new Verb("Approach");
/*  46: 47 */     new Verb("Arrive");
/*  47: 48 */     new Verb("Attach");
/*  48: 49 */     new Verb("Bounce");
/*  49: 50 */     new Verb("Bury");
/*  50: 51 */     new Verb("Carry");
/*  51: 52 */     new Verb("Catch");
/*  52: 53 */     new Verb("Chase");
/*  53: 54 */     new Verb("Close");
/*  54: 55 */     new Verb("Collide");
/*  55: 56 */     new Verb("Dig");
/*  56: 57 */     new Verb("Drop");
/*  57: 58 */     new Verb("Enter");
/*  58: 59 */     new Verb("Exchange");
/*  59: 60 */     new Verb("Exit");
/*  60: 61 */     new Verb("Fall");
/*  61: 62 */     new Verb("Flee");
/*  62: 63 */     new Verb("Fly");
/*  63: 64 */     new Verb("Follow");
/*  64: 65 */     new Verb("Get");
/*  65: 66 */     new Verb("Give");
/*  66: 67 */     new Verb("Go");
/*  67: 68 */     new Verb("Hand");
/*  68: 69 */     new Verb("Haul");
/*  69: 70 */     new Verb("Have");
/*  70: 71 */     new Verb("Hit");
/*  71: 72 */     new Verb("Hold");
/*  72: 73 */     new Verb("Jump");
/*  73: 74 */     new Verb("Kick");
/*  74: 75 */     new Verb("Leave");
/*  75: 76 */     new Verb("Lift");
/*  76: 77 */     new Verb("Move");
/*  77: 78 */     new Verb("Open");
/*  78: 79 */     new Verb("Pass");
/*  79: 80 */     new Verb("Pick up");
/*  80: 81 */     new Verb("Push");
/*  81: 82 */     new Verb("Put down");
/*  82: 83 */     new Verb("Raise");
/*  83: 84 */     new Verb("Receive");
/*  84: 85 */     new Verb("Replace");
/*  85: 86 */     new Verb("Run");
/*  86: 87 */     new Verb("Snach");
/*  87: 88 */     new Verb("Stop");
/*  88: 89 */     new Verb("Take");
/*  89: 90 */     new Verb("Throw");
/*  90: 91 */     new Verb("Touch");
/*  91: 92 */     new Verb("Turn");
/*  92: 93 */     new Verb("Walk");
/*  93:    */   }
/*  94:    */   
/*  95:    */   class Verb
/*  96:    */     extends JCheckBox
/*  97:    */   {
/*  98:    */     public Verb(String name)
/*  99:    */     {
/* 100:102 */       super();
/* 101:103 */       super.setName(name);
/* 102:104 */       VideoSelectorPanel.this.getBoxes().add(this);
/* 103:105 */       VideoSelectorPanel.this.getVerbPanel().add(this);
/* 104:106 */       addActionListener(VideoSelectorPanel.this.getCheckBoxListener());
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public JPanel getVerbPanel()
/* 109:    */   {
/* 110:111 */     if (this.verbPanel == null)
/* 111:    */     {
/* 112:112 */       this.verbPanel = new JPanel();
/* 113:113 */       this.verbPanel.setLayout(new GridLayout(8, 6));
/* 114:    */     }
/* 115:115 */     return this.verbPanel;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public JList getVideoPanel()
/* 119:    */   {
/* 120:119 */     if (this.videoPanel == null)
/* 121:    */     {
/* 122:120 */       this.videoPanel = new JList();
/* 123:121 */       DefaultListModel dlm = new DefaultListModel();
/* 124:122 */       this.videoPanel.setModel(dlm);
/* 125:123 */       this.videoPanel.setSelectionMode(0);
/* 126:124 */       this.videoPanel.addListSelectionListener(new LocalListListener());
/* 127:    */     }
/* 128:126 */     return this.videoPanel;
/* 129:    */   }
/* 130:    */   
/* 131:    */   class LocalListListener
/* 132:    */     implements ListSelectionListener
/* 133:    */   {
/* 134:    */     LocalListListener() {}
/* 135:    */     
/* 136:    */     public void valueChanged(ListSelectionEvent e)
/* 137:    */     {
/* 138:132 */       if (e.getValueIsAdjusting()) {
/* 139:133 */         return;
/* 140:    */       }
/* 141:135 */       int source = VideoSelectorPanel.this.getVideoPanel().getSelectedIndex();
/* 142:136 */       Mark.say(new Object[] {"Index is", Integer.valueOf(source) });
/* 143:137 */       String fileName = (String)VideoSelectorPanel.this.getListElements().get(source);
/* 144:138 */       Mark.say(new Object[] {"Selected file is", fileName });
/* 145:    */     }
/* 146:    */   }
/* 147:    */   
/* 148:    */   public ArrayList<JCheckBox> getBoxes()
/* 149:    */   {
/* 150:146 */     if (this.boxes == null) {
/* 151:147 */       this.boxes = new ArrayList();
/* 152:    */     }
/* 153:149 */     return this.boxes;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public ArrayList<String> getAllVideos()
/* 157:    */   {
/* 158:153 */     if (this.allVideos == null)
/* 159:    */     {
/* 160:    */       try
/* 161:    */       {
/* 162:155 */         this.allVideos = new ArrayList();
/* 163:156 */         for (String video : VideoBrowser.getVideoBrowser().getVideoTitles("MULTIPLE_VERB_02/")) {
/* 164:157 */           this.allVideos.add(video);
/* 165:    */         }
/* 166:    */       }
/* 167:    */       catch (IOException e)
/* 168:    */       {
/* 169:162 */         Mark.err(new Object[] {"Error thrown in VideoSelectionPanel.getVideos" });
/* 170:    */       }
/* 171:164 */       Mark.say(new Object[] {"Video count is", Integer.valueOf(this.allVideos.size()) });
/* 172:    */     }
/* 173:166 */     return this.allVideos;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public ArrayList<String> getSelectedVideos()
/* 177:    */   {
/* 178:170 */     if (this.selectedVideos == null) {
/* 179:171 */       this.selectedVideos = new ArrayList();
/* 180:    */     }
/* 181:173 */     return this.selectedVideos;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Vector<String> getListElements()
/* 185:    */   {
/* 186:176 */     if (this.listElements == null) {
/* 187:177 */       this.listElements = new Vector();
/* 188:    */     }
/* 189:179 */     return this.listElements;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public ActionListener getCheckBoxListener()
/* 193:    */   {
/* 194:183 */     if (this.checkBoxListener == null) {
/* 195:184 */       this.checkBoxListener = new CheckBoxListener();
/* 196:    */     }
/* 197:186 */     return this.checkBoxListener;
/* 198:    */   }
/* 199:    */   
/* 200:    */   class CheckBoxListener
/* 201:    */     implements ActionListener
/* 202:    */   {
/* 203:    */     CheckBoxListener() {}
/* 204:    */     
/* 205:    */     public void actionPerformed(ActionEvent e)
/* 206:    */     {
/* 207:191 */       VideoSelectorPanel.this.getSelectedVideos().clear();
/* 208:192 */       ((DefaultListModel)VideoSelectorPanel.this.getVideoPanel().getModel()).clear();
/* 209:193 */       Mark.say(new Object[] {"" });
/* 210:194 */       VideoSelectorPanel.this.getListElements().clear();
/* 211:195 */       for (String x : VideoSelectorPanel.this.getAllVideos())
/* 212:    */       {
/* 213:196 */         String video = x.toLowerCase();
/* 214:197 */         boolean include = true;
/* 215:198 */         for (JCheckBox box : VideoSelectorPanel.this.getBoxes())
/* 216:    */         {
/* 217:199 */           String name = box.getText().toLowerCase();
/* 218:200 */           if ((box.isSelected()) && (video.indexOf(name) < 0)) {
/* 219:201 */             include = false;
/* 220:    */           }
/* 221:    */         }
/* 222:207 */         if (include)
/* 223:    */         {
/* 224:208 */           Mark.say(new Object[] {"Including:", x });
/* 225:209 */           VideoSelectorPanel.this.getListElements().add(x);
/* 226:    */         }
/* 227:    */       }
/* 228:212 */       VideoSelectorPanel.this.getVideoPanel().setListData(VideoSelectorPanel.this.getListElements());
/* 229:    */     }
/* 230:    */   }
/* 231:    */   
/* 232:    */   public static void main(String[] args)
/* 233:    */   {
/* 234:217 */     JFrame test = new JFrame();
/* 235:218 */     test.getContentPane().add(new VideoSelectorPanel());
/* 236:219 */     test.setBounds(0, 0, 600, 400);
/* 237:220 */     test.setVisible(true);
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     adamKraft.VideoSelectorPanel
 * JD-Core Version:    0.7.0.1
 */