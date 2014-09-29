/*   1:    */ package frames;
/*   2:    */ 
/*   3:    */ import bridge.reps.entities.Bundle;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Function;
/*   6:    */ import bridge.reps.entities.Relation;
/*   7:    */ import bridge.reps.entities.Thread;
/*   8:    */ import bridge.utils.StringUtils;
/*   9:    */ import constants.RecognizedRepresentations;
/*  10:    */ import frames.utilities.Utils;
/*  11:    */ import java.io.PrintStream;
/*  12:    */ import java.util.HashMap;
/*  13:    */ 
/*  14:    */ public class GeometryFrame
/*  15:    */   extends Frame
/*  16:    */ {
/*  17:    */   private static HashMap<String, Relation> map;
/*  18:    */   
/*  19:    */   public static HashMap<String, Relation> getMap()
/*  20:    */   {
/*  21: 21 */     if (map == null)
/*  22:    */     {
/*  23: 22 */       map = new HashMap();
/*  24: 23 */       map.put("The bike stood near the tree.", makeSchema(new Entity("bike"), "point", 
/*  25: 24 */         new Entity("tree"), "point", "locative"));
/*  26: 25 */       map.put("The bike rolled along the walkway.", makeSchema(new Entity("bike"), 
/*  27: 26 */         "point", new Entity("walkway"), "line", "motional"));
/*  28: 27 */       map.put("The bike stood along the fence.", makeSchema(new Entity("bike"), 
/*  29: 28 */         "line", new Entity("fence"), "line", "locative"));
/*  30: 29 */       map.put("The board lay across the railway bed.", makeSchema(new Entity("board"), 
/*  31: 30 */         "line", new Entity("bed"), "plane", "locative"));
/*  32: 31 */       map.put("The bird flew across the field.", makeSchema(new Entity("bird"), 
/*  33: 32 */         "point", new Entity("field"), "plane", "motional"));
/*  34: 33 */       map.put("The trickle flowed along the ledge.", makeSchema(new Entity("trickle"), 
/*  35: 34 */         "line", new Entity("ledge"), "line", "motional"));
/*  36: 35 */       map.put("The snake lay around the tree trunk.", makeSchema(new Entity("snake"), 
/*  37: 36 */         "line", new Entity("trunk"), "cylinder", "locative"));
/*  38: 37 */       map.put("The tablecloth lay over the table.", makeSchema(
/*  39: 38 */         new Entity("tablecloth"), "plane", new Entity("table"), "plane", "locative"));
/*  40: 39 */       map.put("There was oil all along the ledge.", makeSchema(new Entity("oil"), 
/*  41: 40 */         "distributed", new Entity("ledge"), "line", "locative"));
/*  42: 41 */       map.put("The water fell all over the floor.", makeSchema(new Entity("water"), 
/*  43: 42 */         "distributed", new Entity("floor"), "plane", "motional"));
/*  44: 43 */       map.put("The scarecrow stood amidst the cornstalks.", makeSchema(new Entity(
/*  45: 44 */         "scarecrow"), "point", new Entity("cornstalks"), "distributed", "locative"));
/*  46: 45 */       map.put("The beetle crawled among the pebbles.", makeSchema(
/*  47: 46 */         new Entity("beetle"), "point", new Entity("pebbles"), "aggregate", "motional"));
/*  48: 47 */       map.put("The plane flew between the clouds.", makeSchema(new Entity("plane"), 
/*  49: 48 */         "point", new Entity("clouds"), "point-pair", "motional"));
/*  50: 49 */       map.put("The boy ran into the bedroom.", makeSchema(new Entity("boy"), "point", 
/*  51: 50 */         new Entity("bedroom"), "enclosure", "motional"));
/*  52: 51 */       map.put("The salmon swam through the water.", makeSchema(new Entity("salmon"), 
/*  53: 52 */         "point", new Entity("water"), "distributed", "motional"));
/*  54: 53 */       map.put("The dye spread throughout the water.", makeSchema(new Entity("dye"), 
/*  55: 54 */         "distributed", new Entity("water"), "distributed", "motional"));
/*  56:    */     }
/*  57: 56 */     return map;
/*  58:    */   }
/*  59:    */   
/*  60: 59 */   public static final String[] figureGeometries = { "point", "line", "distributed" };
/*  61: 60 */   public static final String[] groundMasses = { "point", "point-pair", "point-set", "aggregate", "distributed" };
/*  62: 61 */   public static final String[] groundSurfaces = { "line", "tube", "enclosure", "plane", "cylinder" };
/*  63: 62 */   public static final String[] groundGeometries = Utils.arraycat(groundMasses, groundSurfaces);
/*  64: 63 */   public static final String[] relationships = { "motional", "locative" };
/*  65:    */   public static final String FIGURETYPE = "figureGeometry";
/*  66:    */   public static final String GROUNDTYPE = "groundGeometry";
/*  67: 66 */   public static final String FRAMETYPE = (String)RecognizedRepresentations.GEOMETRY_THING;
/*  68:    */   private Relation schema;
/*  69:    */   
/*  70:    */   public static Function makeFigOrGround(Entity t, String geometryType, boolean figure)
/*  71:    */   {
/*  72: 77 */     if (figure)
/*  73:    */     {
/*  74: 78 */       if (StringUtils.testType(geometryType, figureGeometries))
/*  75:    */       {
/*  76: 79 */         Function fig = new Function("figureGeometry", t);
/*  77: 80 */         fig.addType(geometryType);
/*  78: 81 */         return fig;
/*  79:    */       }
/*  80: 83 */       System.err.println("Sorry, " + geometryType + " is not a known figure geometry.");
/*  81: 84 */       return null;
/*  82:    */     }
/*  83: 86 */     if (StringUtils.testType(geometryType, groundGeometries))
/*  84:    */     {
/*  85: 87 */       Function ground = new Function("groundGeometry", t);
/*  86: 88 */       ground.addType(geometryType);
/*  87: 89 */       return ground;
/*  88:    */     }
/*  89: 91 */     System.err.println("Sorry, " + geometryType + " is not a known ground geometry.");
/*  90: 92 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public static String getGeometry(Function thing)
/*  94:    */   {
/*  95: 96 */     if ((thing.isA("figureGeometry")) || (thing.isA("groundGeometry"))) {
/*  96: 97 */       return thing.getType();
/*  97:    */     }
/*  98: 99 */     System.err.println("Sorry, " + thing + " is not a valid figure or ground.");
/*  99:100 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public static Relation makeSchema(Entity fig, String figGeometry, Entity gro, String groundGeometry, String relationship)
/* 103:    */   {
/* 104:117 */     if (StringUtils.testType(relationship, relationships))
/* 105:    */     {
/* 106:118 */       Function figure = makeFigOrGround(fig, figGeometry, true);
/* 107:119 */       Function ground = makeFigOrGround(gro, groundGeometry, false);
/* 108:120 */       Relation schema = new Relation(FRAMETYPE, figure, ground);
/* 109:121 */       schema.addFeature(relationship);
/* 110:122 */       return schema;
/* 111:    */     }
/* 112:124 */     System.err.println("Sorry, " + relationship + " is not a known figure-ground relationship.");
/* 113:125 */     return null;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public static String getRelationship(Relation schema)
/* 117:    */   {
/* 118:129 */     return schema.getBundle().getThreadContaining("features").getType();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static Function getFigure(Relation schema)
/* 122:    */   {
/* 123:132 */     if (schema.isA(FRAMETYPE)) {
/* 124:133 */       return (Function)schema.getSubject();
/* 125:    */     }
/* 126:135 */     System.err.println("Sorry, " + schema + " is not a valid Geometry schema.");
/* 127:136 */     return null;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public static Function getGround(Relation schema)
/* 131:    */   {
/* 132:140 */     if (schema.isA(FRAMETYPE)) {
/* 133:141 */       return (Function)schema.getObject();
/* 134:    */     }
/* 135:143 */     System.err.println("Sorry, " + schema + " is not a valid Geometry schema.");
/* 136:144 */     return null;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public static String getFigureGeometry(Relation schema)
/* 140:    */   {
/* 141:148 */     return getGeometry(getFigure(schema));
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static String getGroundGeometry(Relation schema)
/* 145:    */   {
/* 146:151 */     return getGeometry(getGround(schema));
/* 147:    */   }
/* 148:    */   
/* 149:    */   public GeometryFrame(Entity t)
/* 150:    */   {
/* 151:156 */     if (t.isA(FRAMETYPE)) {
/* 152:157 */       this.schema = ((Relation)t);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   public GeometryFrame(GeometryFrame f)
/* 157:    */   {
/* 158:161 */     this.schema = ((Relation)f.getThing().clone());
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Entity getThing()
/* 162:    */   {
/* 163:164 */     return this.schema;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String toString()
/* 167:    */   {
/* 168:167 */     return this.schema.toString();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public boolean isEqual(Object f)
/* 172:    */   {
/* 173:170 */     if ((f instanceof GeometryFrame)) {
/* 174:171 */       return this.schema.isEqual(((GeometryFrame)f).getThing());
/* 175:    */     }
/* 176:173 */     return false;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public static void main(String[] args)
/* 180:    */   {
/* 181:176 */     HashMap<String, Relation> map = getMap();
/* 182:    */   }
/* 183:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     frames.GeometryFrame
 * JD-Core Version:    0.7.0.1
 */