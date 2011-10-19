(ns leinProjekat.core
  (:gen-class :main true)
  (:use leinProjekat.novi)
  (:import (javax.swing JFrame JPanel JTextField JTextArea JLabel JScrollPane)
        (java.awt BorderLayout Dimension Color)
        (javax.swing.event DocumentEvent DocumentListener)
        (javax.swing.text DefaultHighlighter)
        (javax.swing.text DefaultHighlighter$DefaultHighlightPainter)))




(defn create-hilit[]
  (let [hilit (new DefaultHighlighter)]
   (defn get-hilit [] hilit)
   hilit))

(defn create-upper-text-area []
  (let [upper-text-area (new JTextArea 10 10)]
    (.setText upper-text-area "tast tist test test")
    (.setHighlighter upper-text-area (create-hilit))
    (defn get-upper-text-area [] upper-text-area)
    upper-text-area))

(defn create-scroll-pane[]
  (new JScrollPane (create-upper-text-area)))

(defn create-label []
  (let [label (new JLabel)]
    (.setText label "Enter regex to search: ")
    label)) 

(def text-field (new JTextField))

(defn do-search []
  (try
    (let 
      [vect (get-vector-of-matches 
              (.getText text-field) 
              (.getText (get-upper-text-area)))]
      (.removeAllHighlights (get-hilit))
      (loop [i 0]
        (when (< i (count vect))
          ;(println (str (get vect i) " " (get vect (+ i 1))))
          (.addHighlight (get-hilit)
            (get vect i)
            (get vect (+ i 1))
            (new DefaultHighlighter$DefaultHighlightPainter (. Color LIGHT_GRAY)))
          (recur (+ i 2)))))
    (catch Exception e (.removeAllHighlights (get-hilit)))))

(defn document-listener []
  (proxy [DocumentListener] []
  (changedUpdate [event] (println "!!!!!!!!!!!"))
  (insertUpdate [event] (do-search))
  (removeUpdate [event] (do-search))))

(defn create-text-field []
  (.setText text-field "")
    (.setPreferredSize text-field (new Dimension 320 25))
    (.addDocumentListener (.getDocument text-field) (document-listener))
    (defn get-text-field [] text-field)
    text-field)

(defn create-inside-down-pane []
  (let [inside-down-pane (new JPanel)]
    (.add inside-down-pane (create-label))
    (.add inside-down-pane (create-text-field))
  inside-down-pane))

(defn create-down-pane [] 
  (let [down-pane (new JPanel (new BorderLayout))]
    (.add down-pane 
      (create-inside-down-pane)
      (. BorderLayout BEFORE_FIRST_LINE))
    down-pane))

(defn create-upper-pane []
  (let[upper-pane (new JPanel (new BorderLayout))]
    (.add upper-pane (create-scroll-pane))
    upper-pane))

(defn create-main-pane [] 
  (let [main-pane (new JPanel (new BorderLayout))]
    (.add main-pane 
      (create-upper-pane) 
      (. BorderLayout CENTER))
    (.add main-pane 
      (create-down-pane) 
      (. BorderLayout PAGE_END))
  main-pane))

(defn create-frame [] 
  (let [frame (new JFrame "Regex search")]
    (.add (.getContentPane frame) 
      (create-main-pane) 
      (. BorderLayout CENTER))
    frame))


(defn -main [& args]
  (let [frame (create-frame)]
  (doto frame
    (.setLocation 320 120)
    (.pack)
    (.setVisible true))))

;(get-vector-of-matches "t[ea]st" "tast tist test test")





