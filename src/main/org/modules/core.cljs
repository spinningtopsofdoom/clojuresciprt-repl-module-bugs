(ns org.modules.core
  (:require [cljs.loader :as loader]))

(loader/load
  :time
  (fn []
    ((resolve 'org.modules.time/display-time) 100)))
