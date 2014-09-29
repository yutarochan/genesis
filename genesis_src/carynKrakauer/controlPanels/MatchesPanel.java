/*   1:    */ package carynKrakauer.controlPanels;
/*   2:    */ 
/*   3:    */ import carynKrakauer.InOrderVectorMatcher;
/*   4:    */ import carynKrakauer.RarityUtil;
/*   5:    */ import carynKrakauer.ReflectionLevelMemory;
/*   6:    */ import carynKrakauer.SimilarityProcessor;
/*   7:    */ import carynKrakauer.VectorMatchWrapper;
/*   8:    */ import carynKrakauer.VectorMatcher;
/*   9:    */ import java.awt.BorderLayout;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import java.util.HashMap;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.Set;
/*  15:    */ import javax.swing.BoxLayout;
/*  16:    */ import javax.swing.ButtonGroup;
/*  17:    */ import javax.swing.JButton;
/*  18:    */ import javax.swing.JComboBox;
/*  19:    */ import javax.swing.JPanel;
/*  20:    */ import javax.swing.JRadioButton;
/*  21:    */ import javax.swing.JScrollPane;
/*  22:    */ import javax.swing.JTextPane;
/*  23:    */ import storyProcessor.ReflectionDescription;
/*  24:    */ 
/*  25:    */ public class MatchesPanel
/*  26:    */   extends JPanel
/*  27:    */   implements ActionListener
/*  28:    */ {
/*  29:    */   private SimilarityProcessor similarityProcessor;
/*  30:    */   JButton find_matches_button;
/*  31:    */   ButtonGroup match_group;
/*  32:    */   JRadioButton find_highest_match_choice;
/*  33:    */   JRadioButton find_all_matches_choice;
/*  34:    */   JScrollPane match_scroll_pane;
/*  35:    */   JTextPane match_text_pane;
/*  36:    */   JComboBox story_selector;
/*  37:    */   
/*  38:    */   public MatchesPanel(SimilarityProcessor similarityProcessor, JComboBox story_selector)
/*  39:    */   {
/*  40: 38 */     this.similarityProcessor = similarityProcessor;
/*  41: 39 */     this.story_selector = story_selector;
/*  42:    */     
/*  43: 41 */     setupMatchPane();
/*  44:    */   }
/*  45:    */   
/*  46:    */   private void setupMatchPane()
/*  47:    */   {
/*  48: 45 */     setLayout(new BorderLayout());
/*  49:    */     
/*  50: 47 */     JPanel selections_panel = new JPanel();
/*  51: 48 */     selections_panel.setLayout(new BoxLayout(selections_panel, 2));
/*  52:    */     
/*  53: 50 */     this.story_selector = new JComboBox();
/*  54: 51 */     selections_panel.add(this.story_selector);
/*  55: 52 */     this.similarityProcessor.addComboBox(this.story_selector);
/*  56:    */     
/*  57: 54 */     this.find_highest_match_choice = new JRadioButton("Highest Match Only");
/*  58: 55 */     this.find_highest_match_choice.addActionListener(this);
/*  59: 56 */     this.find_all_matches_choice = new JRadioButton("All Matches");
/*  60: 57 */     this.find_all_matches_choice.addActionListener(this);
/*  61:    */     
/*  62: 59 */     this.match_group = new ButtonGroup();
/*  63: 60 */     this.match_group.add(this.find_highest_match_choice);
/*  64: 61 */     this.match_group.add(this.find_all_matches_choice);
/*  65:    */     
/*  66: 63 */     this.find_highest_match_choice.setSelected(true);
/*  67:    */     
/*  68: 65 */     JPanel match_choice_pane = new JPanel();
/*  69: 66 */     match_choice_pane.setLayout(new BoxLayout(match_choice_pane, 3));
/*  70: 67 */     match_choice_pane.add(this.find_highest_match_choice);
/*  71: 68 */     match_choice_pane.add(this.find_all_matches_choice);
/*  72: 69 */     selections_panel.add(match_choice_pane);
/*  73:    */     
/*  74: 71 */     this.find_matches_button = new JButton("Find match(es)!");
/*  75: 72 */     this.find_matches_button.addActionListener(this);
/*  76: 73 */     selections_panel.add(this.find_matches_button);
/*  77:    */     
/*  78: 75 */     add(selections_panel, "North");
/*  79:    */     
/*  80: 77 */     this.match_text_pane = new JTextPane();
/*  81: 78 */     this.match_text_pane.setEditable(false);
/*  82: 79 */     this.match_scroll_pane = new JScrollPane(this.match_text_pane, 22, 31);
/*  83:    */     
/*  84: 81 */     add(this.match_scroll_pane, "Center");
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void actionPerformed(ActionEvent ae)
/*  88:    */   {
/*  89: 86 */     if (ae.getSource().equals(this.find_matches_button))
/*  90:    */     {
/*  91: 87 */       String story = (String)this.story_selector.getSelectedItem();
/*  92: 88 */       String output = "Matches found for " + story + "\n\n";
/*  93:    */       
/*  94: 90 */       HashMap<String, VectorMatchWrapper> matches = VectorMatcher.highest_matches((String)this.story_selector.getSelectedItem(), this.find_all_matches_choice.isSelected(), this.similarityProcessor.getMemory());
/*  95: 91 */       Object[] highest_matches = VectorMatcher.highest_matches(story, false, this.similarityProcessor.getMemory()).keySet().toArray();
/*  96:    */       
/*  97: 93 */       output = output.concat("Feature rarity: \n");
/*  98:    */       float count;
/*  99: 94 */       for (ReflectionDescription refDesc : this.similarityProcessor.getMemory().getStoryReflectionDescriptions(story))
/* 100:    */       {
/* 101: 95 */         reflection = refDesc.getName();
/* 102: 96 */         count = ((Integer)this.similarityProcessor.getMemory().getConceptPatternStoryCounts().get(reflection)).intValue();
/* 103: 97 */         float rarity = count / this.similarityProcessor.getMemory().getStoryCount();
/* 104: 98 */         output = output.concat("    " + reflection + ": " + rarity + "\n");
/* 105:    */       }
/* 106:100 */       output = output.concat("\n");
/* 107:    */       
/* 108:102 */       String reflection = (count = highest_matches).length;
/* 109:102 */       for (Object localObject1 = 0; localObject1 < reflection; localObject1++)
/* 110:    */       {
/* 111:102 */         Object highest_match = count[localObject1];
/* 112:103 */         output = output.concat("  The highest match is " + (String)highest_match + " because it has the following plot units in common: " + ((VectorMatchWrapper)matches.get(highest_match)).getMatches() + "\n\n");
/* 113:    */       }
/* 114:106 */       for (localObject1 = matches.keySet().iterator(); ((Iterator)localObject1).hasNext();)
/* 115:    */       {
/* 116:106 */         String s = (String)((Iterator)localObject1).next();
/* 117:107 */         output = output.concat(new StringBuilder("  Story: ").append(s).toString()) + "\n";
/* 118:108 */         output = output.concat(new StringBuilder("    Matches: ").append(((VectorMatchWrapper)matches.get(s)).getMatches()).toString()) + "\n";
/* 119:109 */         output = output.concat(new StringBuilder("    Value: ").append(((VectorMatchWrapper)matches.get(s)).getValue()).toString()) + "\n";
/* 120:110 */         output = output.concat("    Rarity: " + 
/* 121:111 */           RarityUtil.rarity_in_common((String)this.story_selector.getSelectedItem(), s, this.similarityProcessor.getMemory()) + "\n");
/* 122:112 */         output = output.concat("    In order score: " + 
/* 123:113 */           InOrderVectorMatcher.matchVectorsInOrder((String)this.story_selector.getSelectedItem(), s, this.similarityProcessor.getMemory()).getValue() + "\n");
/* 124:114 */         output = output.concat("    In order matches: " + 
/* 125:115 */           InOrderVectorMatcher.matchVectorsInOrder((String)this.story_selector.getSelectedItem(), s, this.similarityProcessor.getMemory()).getMatches() + "\n\n");
/* 126:    */       }
/* 127:117 */       this.match_text_pane.setText(output);
/* 128:    */     }
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     carynKrakauer.controlPanels.MatchesPanel
 * JD-Core Version:    0.7.0.1
 */