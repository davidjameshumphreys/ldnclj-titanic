(ns ldnclj-titanic.incanter-version
  (:use [incanter.core]
        [incanter.io]
        [incanter.stats]
        [incanter.charts])
  )

(def train (read-dataset "./resource/train.csv" :header true :keyword-headers true))
(def dead ($where {:survived 0} train))
(def alive ($where {:survived 1} train))

(defn remove-empties [coll]
  (remove (partial = "") coll))

(defn zero-empties [coll]
  (map #(if (= "" %) 0 %) coll)
  )
(view (histogram  (remove (fn[a] (= a "")) ($ [:age] dead))))
(view (histogram  (remove (fn[a] (= a "")) ($ [:age] dead))))

#_ ((view (scatter-plot (zero-empties ($ [:age] dead)) (zero-empties ($ [:fare] dead)))))

(with-data dead
    (doto (scatter-plot (zero-empties ($ [:age] dead)) (zero-empties ($ [:fare] dead)))
          (add-points   (zero-empties ($ [:age] alive)) (zero-empties ($ [:fare] alive)))
          ;;(add-points :Sepal.Length :Sepal.Width :data ($where {:Species "virginica"}))
          view))


#_(with-data train
  (doto
      (histogram  (zero-empties ($where {:survived 1}($ [:sex :survived] ))) )
    view
    )
  
  )

;; In this form we don't need to have the intermediate dataset forms.
(with-data train
    (doto (scatter-plot (zero-empties ($ [:age])) (zero-empties ($ [:fare] ($where {:survived 0}))))
          (add-points   (zero-empties ($ [:age])) (zero-empties ($ [:fare] ($where {:survived 1}))))
          view))