(ns org.modules.preloads.dev
  (:import [goog.debug Console]))

(when ^boolean goog.DEBUG
  (.setCapturing (Console.) true))
