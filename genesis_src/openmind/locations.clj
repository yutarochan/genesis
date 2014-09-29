(ns openmind.locations
  (:use clojure.contrib.def)
  (:use clojure.contrib.str-utils)
  (:use clojure.contrib.sql)
  (:import org.gjt.mm.mysql.Driver)
  (:use clojure.contrib.sql)
  (:use [clojureql [core :only [table]]])
  (:import java.io.File)
  (:use [clojure.java.io :only [copy]])
  (:use [clojure.contrib.shell-out :only [sh]])
  (:use [clojure.string :only [split]])
  (:use [coderloop [utils :only [md5]]])
  (:import java.sql.Date)
  (:import start.Start)
  (:import (genesis Genesis))
  (:import start.PhraseFactory)
  (:import start.Entity)
  (:import start.RoleFrame)
  (:use clojure.contrib.import-static)
  (:import (java.io File))
  (:import (org.apache.commons.io FileUtils))
  (:import (javax.imageio ImageIO) )
  (:import (javax.swing JFrame))
  (:import (java.awt Color BorderLayout))
  (:import (ij.plugin PlugIn))
  (:import (ij ImagePlus IJ))
  (:import (java.lang Math))
  (:import (java.awt Polygon))
  (:import (java.awt.geom Line2D$Double))
  (:import expert.IdiomExpert)
  (:import storyProcessor.StoryProcessor)
  (:import bridge.reps.entities.Sequence)
  (:use clojureDemo.appeture)
  (:import (ij Macro))
  (:import (java.io BufferedReader InputStreamReader))
  (:import (java.awt.image BufferedImage))
  (:import (genesis Genesis))
  (:import (utils Mark))
  (:import (connections Connections WiredBox))
  (:import (specialBoxes BasicBox MultiFunctionBox))
  (:import (java.awt Polygon))
  (:import (java.awt.geom Line2D$Double))
  (:use clojure.contrib.str-utils)
  (:use [clojure.contrib.repl-utils :only [show]])
  (:use clojure.repl))

(import-static java.lang.Math pow)

(def *port* 3306)
(def *db-host* "rlm.mit.edu")
(def *host* *db-host*)
(def *db-name* "genesis")
(def *db* {:classname "com.mysql.jdbc.Driver"
	   :subprotocol "mysql"
	   :subname (str "//" *db-host* ":" *port* "/" *db-name*)
	   :user "genesis"
	   :password "g3nesis"})

(def databases [:essay_scientists :scientists :ideas :scientist_whereabouts])

;;--------------------- Print Objects as Sentences ---------------------

(def locations @(table *db* :locations))

;; we need to remove invalid things from locations that trip up START.
(def locations (filter (fn [{:keys [room obj]}]
			 (not (or 
			       (.contains room "]")
			       (.contains obj "]")))) locations))

;; there are no proper nouns, so everything is lowercased beforehand.
;; (START interprets things that are capitalized as proper nouns)
(defn start-convert [string]
  (.toLowerCase  (re-gsub #" " "_" (re-gsub  #"\s+s\s+" "'s " string))))

(def locations (map (fn [{:keys [room obj]}]
		      {:room (start-convert room)
		       :obj  (start-convert obj)
		       }) locations))
;; remove duplicates
(def locations
     (vec (set (map (fn [{room :room obj :obj}] {:room room :obj obj}) locations))))
;; now locations is completely defined.


;; here are a couple of things that you can do with the locations data.
;; convert to "english", Wizards and Warriors Style.
(defn locations->str [{:keys [room obj]}]
  (str "IN " room " THOU HAST DISCOVERED " obj "."))
(defn print-locations [locations]
  (dorun (map (comp println locations->str) locations)))

;; filter the data by some criteria.
(defn select-room [room]
  (filter (comp (partial = room) :room) locations))
(defn print-table [table-name]
  (dorun (map println @(table *db* table-name))))


;;---------------------  Working with the Start Parser  -----------------


(defvar println-repl (bound-fn [& args] (apply println args))
  "always prints to the repl thread")


(defn triples-str
  "returns the raw string output from the start parser"
  [sentence]
  (filter (comp not (partial re-matches #".*<.*"))
	  (split   (.processSentence (Start/getStart) sentence) #"\n")))


(defn triples
  "returns a list of triples as parsed by the start parser"
  [sentence]
  (map
   (fn [triple]
     (map
      keyword
      (split
       (re-gsub #"[\[\]]" "" triple)
       #" ")))
   (triples-str sentence)))

(defn make-sentence
  "use Winston's PhraseFactory to generate an english sentence using START"
  [#^String triples-str]
  (.generate
   (PhraseFactory/getPhraseFactory) triples-str (make-array Entity 0)))

(defn round-trip
  "put a sentence through triple decomposition and then back through the start parser
   should be more or less an identity operation."
  [sentence]
  (make-sentence (apply str (triples-str sentence))))

(defn location->english* [{obj :obj room :room :as data}]
  (make-sentence
     (apply
      str
      [(str "[" room "+2 contain+1 " obj "+3]")
       (str "[" obj "+3 has_det indefinite]")
       (str "[contain+1 has_modal may]")])))

(defn-memo location->english**
  "this is the same as location->english* except that it uses
   Winston's improved RoleFrame representation."
  [{obj :obj room :room :as data}]
  ;; replace room and obj's spaces with underscores to make START happy.
   (let [obj  (re-gsub #" " "_" obj)
	 room (re-gsub #" " "_" room)]
     (.generate
      (PhraseFactory/getPhraseFactory) 
      (.may
       (RoleFrame.
	(RoleFrame. room)
	"contains"
	(.makeIndefinite (RoleFrame. obj)))))))

(defn-memo location->english
  "Constructs akward sentences (for now) which make Genesis happy"
  [{obj :obj room :room :as data}]
  (make-sentence
     (apply
      str
      [(str "[" "contain+1 " "if+1 " "is-a+2" "]" )
       (str "[" "xx " "contain+1 " obj "+4"  "]" )
       (str "[" "xx " "is-a+2 " room "+3 " "]" )
       (str "[" obj "+4 "  "has_det " "indefinite" "]" )
       (str "[" "contain+1 " "has_modal " "may" "]" )
       (str "[" room "+3 " "has_det " "indefinite" "]" )
       (str "[" "is-a+2 " "has_tense " "present" "]" )
       ])))

(defn generate-file [locations]
  (spit (File. "/home/r/Genesis/locations.txt")
	(reduce str (interpose "\n" (map location->english locations)))))

(def english-locations (map location->english locations))

(defn trans-print [x] (println x) x)

(defn find-errors [locations]
  (filter (comp (partial re-find #"<P>" )
		trans-print location->english) locations))


;; these are some sentences generated from START that are
;; pretty weird.
(def dubious
     ["The bathroom may contain a toothpaste."
      "The garage may contain a trash."
      "The dining room may contain a China."
      "The basement may contain an old furniture."
      "The living room may contain a christmas tree attractive."
      "Home office may contain an Internet."])


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;              Genesis Stuff
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn startInFrame-rm 
  [genesis]
  (.start genesis)
  (let [frame (JFrame.)]
    (doto frame
      (.setTitle "Genesis")
      (.setBounds 0 0 1024 768)
      (doto (.getContentPane)
	(.setBackground Color/WHITE)
	(.setLayout (BorderLayout.))
	(.add genesis))
      (.setJMenuBar (.getMenuBar genesis))
      (.setVisible true))
    frame))


(defn run-genesis
  ([]  (startInFrame-rm (Genesis.)))
  ([genesis] (startInFrame-rm genesis)))

(defn write 
  [reference]
  (fn [x] (dosync 
	   (println-repl "wrote "  " to " "ref.")
	   (ref-set reference x))))

(defn writer-box
  [reference]
  (let [box (proxy [MultiFunctionBox] [] 
	      (getName [] "ref-set\n [clojure]")
	      (process1 [obj] ((write reference) obj)))]
     (.addSignalProcessor (Connections/getPorts box) "process1")
     box))


(defn promise-box
  [prom]
  (let [box (proxy [MultiFunctionBox] [] 
	      (getName [] "promise\n [clojure]")
	      (process1 [obj]
			(println-repl "delivering promise")
			(deliver prom obj)))]
     (.addSignalProcessor (Connections/getPorts box) "process1")
     box))


(defn atom-promise-writer-box [a]
  (let [box (proxy [MultiFunctionBox] [] 
	      (getName [] "promise*\n [clojure]")
	      (process1 [obj]
			(deliver @a obj)))]
    (.addSignalProcessor (Connections/getPorts box) "process1")
    box))
  


(defn print-box
  [message]
  (let [box (proxy [MultiFunctionBox] []
	      (getName [] "Basic Print Box [clojure]")
	      (process1 [obj]

			(println-repl (str "******* BEGIN ******" message))
			(println-repl obj)
			(println-repl (str "******* END ********" message))
			))]
    (.addSignalProcessor (Connections/getPorts box) "process1")
    box))



;;(def count (atom 0))





(defn get-name [sequency-thing]
  (.replaceAll (.getName sequency-thing) (.getNameSuffix sequency-thing) ""))

(defn test-contain-relation
  [{obj :obj room :room} #^bridge.reps.entities.Sequence sequence]
  ;; first, unwrap the sequence and extract the explanation/prediction
  (let [output (trans-print
		(.asStringWithoutIndexes sequence))
	trailer
	(str " (sequence conjuction (classification "
	     room " room)) (contain room (sequence roles (object " obj ")))))")]
    (or (= output (str "(sequence thing (explanation" trailer))
	(= output (str "(sequence thing (prediction" trailer)))))

(defn-memo is-a-statement [room]
  (make-sentence
   (str
    "[room-1 is-a-3 "room"-1] "
    "[room-1 has_number singular] "
    "[room-1 has_det definite] "
    "["room"-1 has_number singular] "
    "["room"-1 has_det indefinite] "
    "[is-a-3 is_main yes] "
    "[is-a-3 has_person 3] "
    "[is-a-3 has_tense present] "
    "["room"-1 has_category noun]")))

(defn-memo contains-statement [obj]
  (make-sentence
   (str 
    "[room+223 contain+1 "obj"+224]"
    "[room+223 has_number singular]"
    "[room+223 has_det definite]"
    "["obj"+224 has_number singular]"
    "["obj"+224 has_det indefinite]"
    "[contain+1 is_main yes]"
    "[contain+1 has_person 3]"
    "[contain+1 has_tense present]"
    "["obj"+224 has_category noun]")))


(defn-memo generate-test-story
  [{obj :obj room :room :as location}]
  ["Insert file Start experiment."
   "Start commonsense knowledge."
   (location->english location) 
   "Start story titled \"Test\"."
   (is-a-statement room) 
   (contains-statement obj) 
   "The end."])

;; objective -- determine how to put text into Genesis.

(defn insert-text [genesis location]
  (doall (map #(.prime (.getTextEntryBox genesis) %)
	      (generate-test-story location))))

(def return-promise-atom (atom (promise)))

(defn-memo get-genesis []
  (let [genesis (Genesis.)]
    (Connections/wire
     StoryProcessor/FINAL_INFERENCES
     (.getStoryProcessor1 genesis) (atom-promise-writer-box 
				    return-promise-atom))
    (run-genesis genesis)
    genesis))


(defn test-location 
  "create our own special Genesis"
  [location]
  (let [genesis (get-genesis)
	inference-output (promise)]
    (reset! return-promise-atom inference-output)
    (insert-text genesis location)
    (future (Thread/sleep 4000) (deliver inference-output (Sequence.))) 
    (test-contain-relation location @inference-output)))

