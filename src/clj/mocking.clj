(ns clj.mocking)

(def say "hello ")

(defn greet 
  "prints a greeting"
  [name]
  (str say name))

(greet "jane")