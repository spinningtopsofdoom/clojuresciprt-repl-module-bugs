(ns org.modules.cljs-base)

(require '[cljs.repl :as r])
(require '[cljs.build.api :as b])
(require '[cljs.repl.browser :as rb])

(def opts
  {:watch "src/main"
   :output-dir "out"
   :asset-path "/out"
   :optimizations :none
   :preloads '[org.modules.preloads.dev]
   :modules {:core {:entries '#{org.modules.core}
                    :output-to "out/modules.js"}
             :menu {:entries '#{org.modules.menu}
                    :output-to "out/menu.js"}}
   :browser-repl true
   :verbose true})

(b/build "src/main" opts)
(r/repl* (rb/repl-env) opts)
