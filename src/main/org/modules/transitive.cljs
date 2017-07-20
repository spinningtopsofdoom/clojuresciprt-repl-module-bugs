(ns org.modules.transitive
  (:require [cljs.loader :as loader]))

(js/setTimeout
  (fn []
    (js/console.log "Loading Module with transitive dependencies")
    (loader/load
      :time
      (fn []
        (js/console.log "Module with transitive dependencies loaded")
        (js/console.log ((resolve 'org.modules.time/display-time) 100))))) 0)