/*   1:    */ package connections.views;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.Comparator;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.PriorityQueue;
/*   9:    */ import javax.swing.SwingUtilities;
/*  10:    */ 
/*  11:    */ public class ColorTracker
/*  12:    */ {
/*  13: 18 */   private long delay = 250L;
/*  14: 20 */   private boolean running = false;
/*  15: 22 */   private static int maxRunning = 0;
/*  16: 23 */   private static long min_runner = 0L;
/*  17:    */   private static ColorTracker tracker;
/*  18: 27 */   private HashMap<ColoredBox, ColorTrackerPackage> tracker_map = new HashMap();
/*  19: 28 */   private PriorityQueue<ColorTrackerPackage> tracker_queue = new PriorityQueue(100, new Comparator()
/*  20:    */   {
/*  21:    */     public int compare(ColorTrackerPackage o1, ColorTrackerPackage o2)
/*  22:    */     {
/*  23: 33 */       long a = o1.getQuitTime();long b = o2.getQuitTime();
/*  24: 34 */       if (a == b) {
/*  25: 35 */         return 0;
/*  26:    */       }
/*  27: 37 */       return a > b ? 1 : -1;
/*  28:    */     }
/*  29: 28 */   });
/*  30:    */   
/*  31:    */   public static ColorTracker getTracker()
/*  32:    */   {
/*  33: 43 */     if (tracker == null) {
/*  34: 44 */       tracker = new ColorTracker();
/*  35:    */     }
/*  36: 46 */     return tracker;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public synchronized ArrayList<ColorTrackerPackage> process(Object x)
/*  40:    */   {
/*  41: 50 */     if ((x instanceof ColorTrackerPackage))
/*  42:    */     {
/*  43: 51 */       ColorTrackerPackage tracker = (ColorTrackerPackage)x;
/*  44: 52 */       boolean found = false;
/*  45: 53 */       if (this.tracker_map.containsKey(tracker.getColoredBox()))
/*  46:    */       {
/*  47: 54 */         ColorTrackerPackage existingTracker = (ColorTrackerPackage)this.tracker_map.get(tracker.getColoredBox());
/*  48: 55 */         existingTracker.setQuitTime(tracker.getQuitTime());
/*  49: 56 */         this.tracker_queue.remove(existingTracker);
/*  50: 57 */         this.tracker_queue.add(existingTracker);
/*  51: 58 */         found = true;
/*  52:    */       }
/*  53: 60 */       if (!found)
/*  54:    */       {
/*  55: 61 */         this.tracker_map.put(tracker.getColoredBox(), tracker);
/*  56: 62 */         this.tracker_queue.add(tracker);
/*  57: 63 */         tracker.getColoredBox().setColor(tracker.getTemporaryColor());
/*  58:    */       }
/*  59: 65 */       if (!this.running) {
/*  60: 66 */         new TrackerThread().start();
/*  61:    */       }
/*  62:    */     }
/*  63: 77 */     else if (x == null)
/*  64:    */     {
/*  65: 78 */       long now = System.currentTimeMillis();
/*  66: 79 */       min_runner = 0L;
/*  67: 80 */       ArrayList<ColorTrackerPackage> result = new ArrayList();
/*  68: 81 */       while ((this.tracker_queue.peek() != null) && (((ColorTrackerPackage)this.tracker_queue.peek()).getQuitTime() < now))
/*  69:    */       {
/*  70: 82 */         ColorTrackerPackage tracker = (ColorTrackerPackage)this.tracker_queue.poll();
/*  71: 83 */         this.tracker_map.values().remove(tracker);
/*  72: 84 */         SwingUtilities.invokeLater(new PaintingThread(tracker.getColoredBox(), tracker.getPermanentColor()));
/*  73:    */       }
/*  74: 86 */       if (this.tracker_queue.peek() != null) {
/*  75: 87 */         min_runner = ((ColorTrackerPackage)this.tracker_queue.peek()).getQuitTime() - now;
/*  76:    */       }
/*  77:    */     }
/*  78:100 */     return null;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private class PaintingThread
/*  82:    */     implements Runnable
/*  83:    */   {
/*  84:    */     private ColoredBox box;
/*  85:    */     private Color color;
/*  86:    */     
/*  87:    */     public PaintingThread(ColoredBox box, Color color)
/*  88:    */     {
/*  89:110 */       this.box = box;
/*  90:111 */       this.color = color;
/*  91:    */     }
/*  92:    */     
/*  93:    */     public void run()
/*  94:    */     {
/*  95:115 */       this.box.setColor(this.color);
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   private class TrackerThread
/* 100:    */     extends Thread
/* 101:    */   {
/* 102:    */     public TrackerThread()
/* 103:    */     {
/* 104:122 */       ColorTracker.this.running = true;
/* 105:123 */       ColorTracker.maxRunning = 0;
/* 106:    */     }
/* 107:    */     
/* 108:    */     public void run()
/* 109:    */     {
/* 110:    */       try
/* 111:    */       {
/* 112:    */         do
/* 113:    */         {
/* 114:130 */           Thread.sleep(10L);
/* 115:    */           
/* 116:    */ 
/* 117:133 */           ColorTracker.this.process(null);
/* 118:134 */           ColorTracker.maxRunning = Math.max(ColorTracker.maxRunning, ColorTracker.this.tracker_map.size());
/* 119:136 */         } while (!ColorTracker.this.tracker_map.isEmpty());
/* 120:    */       }
/* 121:    */       catch (InterruptedException e)
/* 122:    */       {
/* 123:143 */         e.printStackTrace();
/* 124:    */       }
/* 125:    */       finally
/* 126:    */       {
/* 127:146 */         ColorTracker.this.running = false;
/* 128:    */       }
/* 129:    */     }
/* 130:    */   }
/* 131:    */ }


/* Location:           C:\Yuya\Development\Genesis\genesis.jar
 * Qualified Name:     connections.views.ColorTracker
 * JD-Core Version:    0.7.0.1
 */