(ns org.modules.core
  (:require [cljs.loader :as loader]))

(loader/load
  :time
  (fn []
    ((resolve 'clicker.time/display-game-time) 100)))
