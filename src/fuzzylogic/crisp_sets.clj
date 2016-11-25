(ns fuzzy-clojure.crisp-sets
  (:require
    [clojure.set :as set]))

(def A #{1 2 3})
(def B #{3 4 5})
(def C #{7 8})

(defn belongs-set
  [elem set-a]
  (or (seq (set/intersection #{elem} set-a)) (empty? set-a)))

(defn is-subset
  [set-a set-b]
  (or (= set-b (set/intersection set-a set-b)) (empty? set-a)))

(defn is-equivalent-set
  [set-a set-b]
  (= (count set-a) (count set-b)))

(defn is-disjoint-set
  [set-a set-b]
  (empty? (set/intersection set-a set-b)))

(defn get-complement-set
  [set-a set-b]
  (filter #(not (belongs-set %1 set-a)) set-b))

(defn get-cartesian-product
  ([] '(()))
  ([set-a & set-more]
   (mapcat #(map (partial cons %1)
                 (apply get-cartesian-product set-more))
           set-a)))
