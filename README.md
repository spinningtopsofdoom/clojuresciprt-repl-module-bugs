# ClojureScript Module Loader Repl Bugs

These bugs only effect modules in the repl (`:none` optimizations) and are on the current ClojureScript master (version 1.9.820 on 20-Jul-2017) 

## `:cljs-base` double loading

If a project has these modules 

```clj
:modules {:core {:entries '#{org.modules.core}
                 :output-to "out/modules.js"}
          :menu {:entries '#{org.modules.menu}
                 :output-to "out/menu.js"}}
```

and this root (`org.modules.core`) namespace

```clj
(ns org.modules.core
  (:require [cljs.loader :as loader]))

(loader/load
  :menu
  (fn [] (js/console.log "Module loaded)))
```

Then the `:cljs-base` module will be loaded twice. 

The issue is that the loading of `:menu` causes `:cljs-base` to be loaded as well. Loading the root namespace marks `:cljs-base` as loaded but only at the end of the file. Under the hood ClojureScript adds

```clj
(cljs.loader/set-loaded! :core)
```   

at the end of the `:core` module. By then it's too late, the `:menu` module has already started loading.

This is only a problem in the REPL due to the `:cljs-base` module being loaded through `document.write`.

## Library namespaces loading before their dependencies

Loading ClojureScript module that has library dependencies will fail due to the library namespaces loading before their dependencies.

For example say the have a project with a `cljs-time` (`[com.andrewmcveigh/cljs-time "0.5.0"]`) dependency, there modules 

```clj
:modules {:core {:entries '#{org.modules.transitive}
                 :output-to "out/modules.js"}
          :time {:entries '#{org.modules.time}
                 :output-to "out/time.js"}}
```

and this code

```clj
(ns org.modules.time
  (:require [cljs-time.core :as ct]
            [cljs.loader :as loader]))

(defn display-time [time]
  time)
```

```clj
(ns org.modules.transitive
  (:require [cljs.loader :as loader]))

(loader/load
  :time
  (fn []
    (js/console.log ((resolve 'org.modules.time/display-time) 100))))
```

When the `:time` module is loaded the `cljs-time.core` is loaded, but any dependencies it may have (e.g. `cljs-time.core.internal`) are loaded afterwards causing the loading of the `:time` module to fail. After a backoff delay (around 5 seconds) the Google Closure Module Manager retries loading the `:time` module, this time successfully. Until the `:time` module is loaded successfully all module loading is subject to this backoff delay.