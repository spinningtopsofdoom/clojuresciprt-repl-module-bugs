(ns org.modules.transitive)

(require '[cljs.repl :as r])
(require '[cljs.build.api :as b])
(require '[cljs.repl.browser :as rb])

(def opts
  {:watch "src/main"
   :output-dir "out"
   :asset-path "/out"
   :optimizations :none
   :preloads '[org.modules.preloads.dev]
   :modules {:core {:entries '#{org.modules.transitive}
                    :output-to "out/modules.js"}
             :time {:entries '#{org.modules.time}
                    :output-to "out/time.js"}}
   :browser-repl true
   :verbose true})

(b/build "src/main" opts)
(r/repl* (rb/repl-env) opts)
