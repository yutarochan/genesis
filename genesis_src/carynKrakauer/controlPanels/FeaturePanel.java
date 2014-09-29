/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.FeatureMatcher;
/*   4:    */ import carynKrakauer.ReflectionLevelMemory;
/*   5:    */ import carynKrakauer.SimilarityProcessor;
/*   6:    */ import carynKrakauer.VectorMatchWrapper;
/*   7:    */ import java.awt.BorderLayout;
/*   8:    */ import java.awt.Dimension;
/*   9:    */ import java.awt.GridLayout;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import javax.swing.Box;
/*  14:    */ import javax.swing.BoxLayout;
/*  15:    */ import javax.swing.JButton;
/*  16:    */ import javax.swing.JComboBox;
/*  17:    */ import javax.swing.JLabel;
/*  18:    */ import javax.swing.JPanel;
/*  19:    */ import javax.swing.JTextPane;
/*  20:    */ import storyProcessor.ReflectionDescription;
/*  21:    */ 
/*  22:    */ public class FeaturePanel
/*  23:    */   extends JPanel
/*  24:    */   implements ActionListener
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27: 30 */   String default_story = "-- Select a Story --";
/*  28:    */   SimilarityProcessor similarityProcessor;
/*  29:    */   JComboBox<String> first_story_box;
/*  30:    */   JComboBox<String> first_feature_box;
/*  31:    */   JComboBox<String> second_story_box;
/*  32:    */   JComboBox<String> second_feature_box;
/*  33:    */   JButton compare_button;
/*  34:    */   JTextPane feature_vector_matches_text_pane;
/*  35:    */   JTextPane feature_vector_value_text_pane;
/*  36:    */   JTextPane feature_leading_to_matches_text_pane;
/*  37:    */   JTextPane feature_leading_to_value_text_pane;
/*  38:    */   
/*  39:    */   public FeaturePanel(SimilarityProcessor similarityProcessor)
/*  40:    */   {
/*  41: 47 */     this.similarityProcessor = similarityProcessor;
/*  42:    */     
/*  43: 49 */     setupFeaturePane();
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void setupFeaturePane()
/*  47:    */   {
/*  48: 53 */     setLayout(new BorderLayout());
/*  49:    */     
/*  50: 55 */     JPanel selection_pane = new JPanel();
/*  51: 56 */     selection_pane.setLayout(new GridLayout(2, 2));
/*  52:    */     
/*  53: 58 */     this.first_story_box = new JComboBox();
/*  54: 59 */     this.first_story_box.addItem(this.default_story);
/*  55: 60 */     this.first_story_box.addActionListener(this);
/*  56: 61 */     this.similarityProcessor.addComboBox(this.first_story_box);
/*  57: 62 */     this.first_feature_box = new JComboBox();
/*  58:    */     
/*  59: 64 */     this.second_story_box = new JComboBox();
/*  60: 65 */     this.second_story_box.addItem(this.default_story);
/*  61: 66 */     this.second_story_box.addActionListener(this);
/*  62: 67 */     this.similarityProcessor.addComboBox(this.second_story_box);
/*  63: 68 */     this.second_feature_box = new JComboBox();
/*  64:    */     
/*  65: 70 */     selection_pane.add(this.first_story_box);
/*  66: 71 */     selection_pane.add(this.second_story_box);
/*  67: 72 */     selection_pane.add(this.first_feature_box);
/*  68: 73 */     selection_pane.add(this.second_feature_box);
/*  69:    */     
/*  70: 75 */     add(selection_pane, "North");
/*  71:    */     
/*  72: 77 */     JPanel compare_pane = new JPanel();
/*  73: 78 */     compare_pane.setLayout(new BoxLayout(compare_pane, 3));
/*  74: 79 */     this.compare_button = new JButton("Compare!");
/*  75: 80 */     this.compare_button.addActionListener(this);
/*  76: 81 */     compare_pane.add(this.compare_button);
/*  77:    */     
/*  78: 83 */     add(compare_pane);
/*  79:    */     
/*  80: 85 */     JPanel feature_output_pane = new JPanel();
/*  81: 86 */     feature_output_pane.setLayout(new BoxLayout(feature_output_pane, 3));
/*  82:    */     
/*  83: 88 */     this.feature_vector_matches_text_pane = new JTextPane();
/*  84: 89 */     this.feature_vector_value_text_pane = new JTextPane();
/*  85: 90 */     this.feature_leading_to_value_text_pane = new JTextPane();
/*  86: 91 */     this.feature_leading_to_matches_text_pane = new JTextPane();
/*  87:    */     
/*  88: 93 */     feature_output_pane.add(new JLabel("Vector Match Components : "), Float.valueOf(0.0F));
/*  89: 94 */     feature_output_pane.add(this.feature_vector_value_text_pane, Float.valueOf(0.0F));
/*  90: 95 */     feature_output_pane.add(new JLabel("Vector Match Value : "), Float.valueOf(0.0F));
/*  91: 96 */     feature_output_pane.add(this.feature_vector_matches_text_pane, Float.valueOf(0.0F));
/*  92:    */     
/*  93: 98 */     feature_output_pane.add(Box.createRigidArea(new Dimension(20, 20)));
/*  94:    */     
/*  95:100 */     feature_output_pane.add(new JLabel("Leading-To Match Components : "), Float.valueOf(0.0F));
/*  96:101 */     feature_output_pane.add(this.feature_leading_to_value_text_pane, Float.valueOf(0.0F));
/*  97:102 */     feature_output_pane.add(new JLabel("Leading-To Match Value : "), Float.valueOf(0.0F));
/*  98:103 */     feature_output_pane.add(this.feature_leading_to_matches_text_pane, Float.valueOf(0.0F));
/*  99:    */     
/* 100:105 */     feature_output_pane.add(Box.createRigidArea(new Dimension(20, 20)));
/* 101:    */     
/* 102:107 */     compare_pane.add(feature_output_pane);
/* 103:    */     
/* 104:109 */     add(compare_pane);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void actionPerformed(ActionEvent ae)
/* 108:    */   {
/* 109:114 */     if (ae.getSource().equals(this.first_story_box))
/* 110:    */     {
/* 111:115 */       this.first_feature_box.removeAllItems();
/* 112:116 */       if (this.first_story_box.getSelectedItem() == null) {
/* 113:117 */         return;
/* 114:    */       }
/* 115:118 */       if (this.similarityProcessor.getMemory().getStoryReflectionDescriptions((String)this.first_story_box.getSelectedItem()) == null) {
/* 116:119 */         return;
/* 117:    */       }
/* 118:120 */       for (ReflectionDescription rd : this.similarityProcessor.getMemory().getStoryReflectionDescriptions((String)this.first_story_box.getSelectedItem())) {
/* 119:121 */         this.first_feature_box.addItem(rd.getName());
/* 120:    */       }
/* 121:    */     }
/* 122:124 */     else if (ae.getSource().equals(this.second_story_box))
/* 123:    */     {
/* 124:125 */       this.second_feature_box.removeAllItems();
/* 125:126 */       if (this.second_story_box.getSelectedItem() == null) {
/* 126:127 */         return;
/* 127:    */       }
/* 128:128 */       if (this.similarityProcessor.getMemory().getStoryReflectionDescriptions((String)this.second_story_box.getSelectedItem()) == null) {
/* 129:129 */         return;
/* 130:    */       }
/* 131:130 */       for (ReflectionDescription rd : this.similarityProcessor.getMemory().getStoryReflectionDescriptions((String)this.second_story_box.getSelectedItem())) {
/* 132:131 */         this.second_feature_box.addItem(rd.getName());
/* 133:    */       }
/* 134:    */     }
/* 135:134 */     else if (ae.getSource().equals(this.compare_button))
/* 136:    */     {
/* 137:135 */       String first_selection = (String)this.first_feature_box.getSelectedItem();
/* 138:136 */       String second_selection = (String)this.second_feature_box.getSelectedItem();
/* 139:138 */       if ((first_selection.equals(this.default_story)) || (second_selection.equals(this.default_story))) {
/* 140:139 */         return;
/* 141:    */       }
/* 142:141 */       if ((first_selection == null) || (second_selection == null)) {
/* 143:142 */         return;
/* 144:    */       }
/* 145:144 */       VectorMatchWrapper match = FeatureMatcher.match_features((String)this.first_story_box.getSelectedItem(), this.first_feature_box.getSelectedIndex(), 
/* 146:145 */         (String)this.second_story_box.getSelectedItem(), this.second_feature_box.getSelectedIndex(), 
/* 147:146 */         this.similarityProcessor.getMemory());
/* 148:    */       
/* 149:148 */       this.feature_vector_matches_text_pane.setText(match.getMatches().toString());
/* 150:149 */       this.feature_vector_value_text_pane.setText(Double.valueOf(match.getValue()).toString());
/* 151:    */       
/* 152:151 */       VectorMatchWrapper leading_to = FeatureMatcher.get_feature_leading_to_comparison((String)this.first_story_box.getSelectedItem(), this.first_feature_box.getSelectedIndex(), 
/* 153:152 */         (String)this.second_story_box.getSelectedItem(), this.second_feature_box.getSelectedIndex(), 
/* 154:153 */         this.similarityProcessor.getMemory());
/* 155:    */       
/* 156:155 */       this.feature_leading_to_matches_text_pane.setText(leading_to.getMatches().toString());
/* 157:156 */       this.feature_leading_to_value_text_pane.setText(leading_to.getValue());
/* 158:    */     }
/* 159:    */   }
/* 160:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.FeaturePanel
 * JD-Core Version:    0.7.0.1
 */