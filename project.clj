(defproject
  none-modules
  "0.0.1-SNAPSHOT"
  :description
  "None Module Breakage test"
  :repositories
  [["clojars" {:url "https://repo.clojars.org/"}]
   ["maven-central" {:url "https://repo1.maven.org/maven2"}]]
  :dependencies
  [[org.clojure/clojure "1.8.0"]
   [org.clojure/clojurescript "1.9.820"]
   [com.andrewmcveigh/cljs-time "0.5.0"]]
  :source-paths
  ["src/main"])
