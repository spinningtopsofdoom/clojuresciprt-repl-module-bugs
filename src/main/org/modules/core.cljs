(ns org.modules.core
  (:require [cljs.loader :as loader]))

(js/console.log "Loading menu module before cljs-base is loaded through core")

(defn module-manager-exists? []
  (if (exists? js/goog.module.ModuleManager)
    "Google Module Manager Exists"
    "Google Module Manager was not found"))

(loader/load
  :menu
  (fn []
    (js/console.log "Loaded menu module")
    (js/console.log (module-manager-exists?))
    (js/console.log ((resolve 'org.modules.menu/items) #js ["foo" "bar" "baz"]))))

(js/setTimeout
  (fn [] (js/console.log (module-manager-exists?))) 3000)