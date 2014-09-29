/*  1:   */ package matthewFay.CharacterModeling;
/*  2:   */ 
/*  3:   */ import bridge.reps.entities.Entity;
/*  4:   */ import connections.AbstractWiredBox;
/*  5:   */ import connections.Connections;
/*  6:   */ import connections.Ports;
/*  7:   */ import java.util.HashMap;
/*  8:   */ import java.util.Map;
/*  9:   */ import javax.swing.JCheckBox;
/* 10:   */ import matthewFay.CharacterModeling.representations.Trait;
/* 11:   */ import matthewFay.representations.BasicCharacterModel;
/* 12:   */ import matthewFay.viewers.CharacterViewer;
/* 13:   */ import utils.Mark;
/* 14:   */ 
/* 15:   */ public class TraitProcessor
/* 16:   */   extends AbstractWiredBox
/* 17:   */ {
/* 18:   */   public static Entity isTraitQuestion(Entity element)
/* 19:   */   {
/* 20:22 */     return null;
/* 21:   */   }
/* 22:   */   
/* 23:25 */   private static TraitProcessor trait_processor = null;
/* 24:   */   
/* 25:   */   public static TraitProcessor getTraitProcessor()
/* 26:   */   {
/* 27:27 */     if (trait_processor == null) {
/* 28:28 */       trait_processor = new TraitProcessor();
/* 29:   */     }
/* 30:29 */     return trait_processor;
/* 31:   */   }
/* 32:   */   
/* 33:32 */   private Map<String, Trait> traits_by_name = null;
/* 34:   */   
/* 35:   */   public Trait getTrait(String trait_name)
/* 36:   */   {
/* 37:34 */     return (Trait)this.traits_by_name.get(trait_name);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public TraitProcessor()
/* 41:   */   {
/* 42:38 */     setName("Near Miss Trait Processor");
/* 43:   */     
/* 44:40 */     Connections.getPorts(this).addSignalProcessor("process");
/* 45:   */     
/* 46:42 */     this.traits_by_name = new HashMap();
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void process(Object o)
/* 50:   */   {
/* 51:46 */     if (CharacterViewer.disableCharacterProcessor.isSelected()) {
/* 52:47 */       return;
/* 53:   */     }
/* 54:51 */     if (!(o instanceof Entity)) {
/* 55:52 */       return;
/* 56:   */     }
/* 57:53 */     Entity element = (Entity)o;
/* 58:55 */     if (Trait.isTraitAssignment(element)) {
/* 59:56 */       doTraitAssignment(element);
/* 60:   */     }
/* 61:   */   }
/* 62:   */   
/* 63:   */   private void doTraitAssignment(Entity element)
/* 64:   */   {
/* 65:62 */     String trait_name = element.getObject().getType();
/* 66:63 */     Entity character_entity = element.getSubject();
/* 67:64 */     BasicCharacterModel character = CharacterProcessor.findBestCharacterModel(character_entity);
/* 68:65 */     if (character != null)
/* 69:   */     {
/* 70:66 */       if (!this.traits_by_name.containsKey(trait_name)) {
/* 71:67 */         this.traits_by_name.put(trait_name, new Trait(trait_name));
/* 72:   */       }
/* 73:69 */       boolean positive_example = !element.hasFeature("not");
/* 74:   */       
/* 75:71 */       Mark.say(new Object[] {"Adding " + character_entity + " as " + positive_example + " example for trait, " + trait_name });
/* 76:72 */       if (positive_example)
/* 77:   */       {
/* 78:73 */         ((Trait)this.traits_by_name.get(trait_name)).addPositiveExample(character);
/* 79:74 */         character.addTrait((Trait)this.traits_by_name.get(trait_name), true);
/* 80:   */       }
/* 81:   */       else
/* 82:   */       {
/* 83:77 */         ((Trait)this.traits_by_name.get(trait_name)).addNegativeExample(character);
/* 84:78 */         character.addTrait((Trait)this.traits_by_name.get(trait_name), false);
/* 85:   */       }
/* 86:81 */       Connections.getPorts(this).transmit(this.traits_by_name.get(trait_name));
/* 87:   */     }
/* 88:   */   }
/* 89:   */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.CharacterModeling.TraitProcessor
 * JD-Core Version:    0.7.0.1
 */