(ns leinProjekat.core (:gen-class :main true) (:use seesaw.core))


;(defn -main [& args]
;  (println "Hello world!"))


(defn -main [& args]
  (invoke-later 
    (-> (frame :title "Hello", 
           :content "Hello, Seesaw",
           :on-close :exit)
     pack!
     show!)))