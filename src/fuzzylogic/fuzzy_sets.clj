(ns fuzzy-clojure.fuzzy-sets
  (:require
    [clojure.set :as set]
    [fuzzy-clojure.crisp-sets :as crisp]))

(def A-names {:Paul 0.1 :John 0.2 :Mary 0.4})
(def B-names {:Paul 0.1 :John 0.5 :Mary 0.1 :Carl 0.9})

(def X {:x1 0.1 :x2 0.9})
(def Y {:y1 0.5 :y2 0.7})


(defn fuzzy-belongs
  [key set-a]
  (get set-a key))

(defn make-crisp
  [set-a]
  (into #{} (keys set-a)))

(defn fuzzy-union
  [set-a set-b]
  (into {}
        (for [key (set/union (make-crisp set-a) (make-crisp set-b))]
          (let [val-a (if (nil? (get set-a key))
                        0.0
                        (get set-a key))
                val-b (if (nil? (get set-b key))
                        0.0
                        (get set-b key))]
            (if (>= val-a val-b)
              [key val-a]
              [key val-b])))))

(defn fuzzy-intersection
  [set-a set-b]
  (into {}
        (for [key (set/union (make-crisp set-a) (make-crisp set-b))]
          (let [val-a (if (nil? (get set-a key))
                        0.0
                        (get set-a key))
                val-b (if (nil? (get set-b key))
                        0.0
                        (get set-b key))]
            (if (<= val-a val-b)
              [key val-a]
              [key val-b])))))

(defn fuzzy-complement
  [set-a]
  (into {}
        (for [key (keys set-a)]
          [key (- 1.0 (get set-a key))])))


(defn fuzzy-cartesian-product
  ([] '(()))
  ([set-a set-b]
   (for [a (into () set-a)
         b (into () set-b)]
     (list (first a) (first b) (min (second a) (second b))))))

