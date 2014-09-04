(ns scampage.core)

(enable-console-print!)

(println "Hello world!")

(defn start-game []
  (.init js/Crafty 480 320)
  (.background js/Crafty "#6698FF")
;;  (switch-to-scene "Intro")
  )

(.addEventListener js/window "load" start-game)
