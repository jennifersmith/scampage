(ns scampage.core)

(enable-console-print!)

(println "Hello world!")

(defn switch-to-scene [name]
  (.enterScene js/Crafty name))


;;(make-scene "Game" game-scene game-scene-uninit)

(defn start-game []
  (.init js/Crafty 480 320)
  (.background js/Crafty "#6698FF")
  ;;(switch-to-scene "Intro")
  )

(.addEventListener js/window "load" start-game)

