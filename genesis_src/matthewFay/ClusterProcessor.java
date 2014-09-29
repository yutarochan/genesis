/*   1:    */ package matthewFay;
/*   2:    */ 
/*   3:    */ import Signals.BetterSignal;
/*   4:    */ import bridge.reps.entities.Entity;
/*   5:    */ import bridge.reps.entities.Sequence;
/*   6:    */ import connections.AbstractWiredBox;
/*   7:    */ import connections.Connections;
/*   8:    */ import connections.Connections.NetWireException;
/*   9:    */ import connections.Ports;
/*  10:    */ import java.io.BufferedReader;
/*  11:    */ import java.io.IOException;
/*  12:    */ import java.io.InputStreamReader;
/*  13:    */ import java.io.PrintStream;
/*  14:    */ import java.net.MalformedURLException;
/*  15:    */ import java.net.URL;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import javax.swing.JRadioButton;
/*  19:    */ import parameters.Radio;
/*  20:    */ import utils.Mark;
/*  21:    */ 
/*  22:    */ public class ClusterProcessor
/*  23:    */   extends AbstractWiredBox
/*  24:    */ {
/*  25:    */   public static final String STORY_PORT = "story port";
/*  26:    */   public static final String CLUSTER_PORT = "cluster port";
/*  27:    */   public static final String RESET_PORT = "reset port";
/*  28: 26 */   public static boolean useReflections = false;
/*  29: 27 */   public static boolean singlePort = false;
/*  30: 29 */   private static String CLUSTER_PROCESSOR = "ClusterProcessor";
/*  31:    */   
/*  32:    */   public ClusterProcessor()
/*  33:    */   {
/*  34: 31 */     setName(CLUSTER_PROCESSOR);
/*  35:    */     
/*  36: 33 */     Connections.getPorts(this).addSignalProcessor("story port", "processStory");
/*  37: 34 */     Connections.getPorts(this).addSignalProcessor("cluster port", "doClustering");
/*  38:    */   }
/*  39:    */   
/*  40: 37 */   ArrayList<Sequence> stories = new ArrayList();
/*  41:    */   
/*  42:    */   public void processStory(Object input)
/*  43:    */   {
/*  44: 40 */     if (!Radio.alignmentButton.isSelected()) {
/*  45: 41 */       return;
/*  46:    */     }
/*  47: 42 */     if (!(input instanceof Sequence)) {
/*  48: 43 */       return;
/*  49:    */     }
/*  50: 44 */     Sequence storySignal = (Sequence)input;
/*  51:    */     
/*  52: 46 */     this.stories.add(storySignal);
/*  53:    */   }
/*  54:    */   
/*  55: 49 */   int clusterCount = 9;
/*  56:    */   
/*  57:    */   public void doClustering(Object input)
/*  58:    */   {
/*  59: 51 */     if ((input instanceof Entity)) {
/*  60: 52 */       Mark.say(new Object[] {((Entity)input).asString() });
/*  61:    */     }
/*  62: 53 */     Mark.say(new Object[] {"Do clustering" });
/*  63: 54 */     SequenceClusterer sc = new SequenceClusterer(this.clusterCount);
/*  64: 55 */     ArrayList<KClusterer.Cluster<Sequence>> clusters = sc.cluster(this.stories);
/*  65:    */     
/*  66: 57 */     Mark.say(new Object[] {"Clustering Complete" });
/*  67:    */     Iterator localIterator2;
/*  68: 59 */     for (Iterator localIterator1 = clusters.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
/*  69:    */     {
/*  70: 59 */       KClusterer.Cluster<Sequence> cluster = (KClusterer.Cluster)localIterator1.next();
/*  71: 60 */       Mark.say(new Object[] {"---" });
/*  72: 61 */       Mark.say(new Object[] {"Average Sim: " + cluster.averageSim });
/*  73: 62 */       Mark.say(new Object[] {"Variance: " + cluster.variance });
/*  74: 63 */       Mark.say(new Object[] {"Centroid: " + ((Sequence)cluster.Centroid).asString() });
/*  75: 64 */       localIterator2 = cluster.iterator(); continue;Sequence s = (Sequence)localIterator2.next();
/*  76:    */       
/*  77: 66 */       Mark.say(new Object[] {"Elements: " + s.getNumberOfChildren() + " " + s.asString() });
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81: 71 */   public static String wireServer = "http://glue.csail.mit.edu/WireServer";
/*  82:    */   
/*  83:    */   public static void main(String[] args)
/*  84:    */   {
/*  85: 74 */     InputStreamReader converter = new InputStreamReader(System.in);
/*  86: 75 */     BufferedReader in = new BufferedReader(converter);
/*  87: 76 */     URL serverURL = null;
/*  88:    */     try
/*  89:    */     {
/*  90: 78 */       serverURL = new URL(wireServer);
/*  91:    */     }
/*  92:    */     catch (MalformedURLException e)
/*  93:    */     {
/*  94: 80 */       e.printStackTrace();
/*  95: 81 */       System.exit(1);
/*  96:    */     }
/*  97:    */     try
/*  98:    */     {
/*  99: 85 */       String input = "";
/* 100: 86 */       CLUSTER_PROCESSOR = "ClusterProcessorService";
/* 101: 87 */       Connections.useWireServer(serverURL);
/* 102: 88 */       ClusterProcessor cp = new ClusterProcessor();
/* 103: 89 */       Connections.publish(cp, CLUSTER_PROCESSOR);
/* 104:    */       
/* 105: 91 */       System.out.println("ClusterProcessorService started, input commands");
/* 106:    */       int j;
/* 107:    */       int i;
/* 108: 93 */       for (; !input.toLowerCase().equals("quit"); i < j)
/* 109:    */       {
/* 110: 94 */         input = in.readLine().trim().intern();
/* 111: 95 */         BetterSignal b = new BetterSignal();
/* 112: 96 */         String[] sigargs = input.split(" ");
/* 113:    */         String[] arrayOfString1;
/* 114: 97 */         j = (arrayOfString1 = sigargs).length;i = 0; continue;String s = arrayOfString1[i];
/* 115: 98 */         b.add(s);i++;
/* 116:    */       }
/* 117:    */     }
/* 118:    */     catch (Connections.NetWireException e)
/* 119:    */     {
/* 120:103 */       e.printStackTrace();
/* 121:    */     }
/* 122:    */     catch (IOException e)
/* 123:    */     {
/* 124:105 */       e.printStackTrace();
/* 125:    */     }
/* 126:    */   }
/* 127:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     matthewFay.ClusterProcessor
 * JD-Core Version:    0.7.0.1
 */