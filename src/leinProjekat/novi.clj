(ns leinProjekat.novi)

(defn search? [vect x] 
  (some (fn [n] (== n x)) vect))

(defn get-postion 
  ([vect string text] (get-postion vect string text 0)) 
    ([vect string text indx]
      (let [start (.indexOf text string indx)]
        (if (search? vect start)
          (recur vect string text (+ start (.length string)))
          start))))

(defn get-vector-of-matches 
  ([reg text] (get-vector-of-matches (re-matcher (re-pattern reg) text) [] text reg))
  ([matcher vect text reg]
    (let [result (re-find matcher)]
      (if (or (nil? result) (.equals reg ""))
        vect
        (recur matcher 
               (let [start (get-postion vect result text)
                             end (+ (.length result) start)]
                         (conj vect start end))
               text
               reg)))))

  











