(ns scampage.core)

(enable-console-print!)


(defn switch-to-scene [name]
  (.enterScene js/Crafty name))

(defn make-scene [name init-fn uninit-fn]
  (.scene js/Crafty name init-fn uninit-fn))

(defn make-init-fn [init-fn requires]
  (fn []
    (this-as me
             (.requires me requires)
             (init-fn me))))

(defn make-component [name init-fn requires additional-fns]
  (.c js/Crafty name
                  (clj->js
                   (merge 
                    {:init (make-init-fn init-fn requires)}
                    additional-fns))))
(defn set-attr [entity attributes]
  (.attr entity (clj->js attributes)))

(defn set-color
([entity color alpha]
                   (.color entity color alpha)) 

([entity color]
                   (.color entity color)))

(defn set-image [this image repeat]
  (.image this image repeat))

(defn make-entity [name]
  (let [entity
        (.e js/Crafty name)]
    entity))

(defn two-way [this speed jump-speed]
  (.twoway this speed jump-speed))

(defn gravity [this stopping-component]
  (.gravity this stopping-component))

(defn create-reel [this reel-id speed frames]
  (.reel this reel-id 1000 (clj->js frames)))
(defn animate-loop [this reel-id]
  (.animate this reel-id, -1))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(defn init-pavement [this]
  (set-attr this {:x 0 :y (- 320 (+ 32 64)) :w 480 :h 64})
  (set-image this "assets/pavementperspective.png" "repeat-x"))

(defn init-road [this]
  (set-attr this {:x 0 :y (- 320 64) :w 480 :h 64})
;;  (set-color this "rgb(55,55,55)")
  (set-image this "assets/road.png" "repeat-x"))

(defn init-lizzy [this]
  (set-attr this {:x 0 :y 0 :w 97 :h 128})
;;(set-color this "rgb(0,55,0)")
(two-way this 4 4)
(gravity this "Road")
(create-reel this "idle" 1000 [[0 0] [1 0]])
(animate-loop this "idle"))

(make-component "Road" init-road "2D, Canvas, Image, Polygon" {})
(make-component "Pavement" init-pavement "2D, Canvas, Image, Polygon" {})
(make-component "Lizzy" init-lizzy "2D, Canvas, Polygon, Twoway,Gravity,spr_lizzy,SpriteAnimation" {})

(make-scene "Game" (fn []
                     (make-entity "Pavement")
                     (make-entity "Road")
                     (make-entity "Lizzy")) {})

(defn start-game []
  (.init js/Crafty 480 320)
  (.background js/Crafty "#6698FF")
  (js/loadStuff
   #(switch-to-scene "Game")))

(.addEventListener js/window "load" start-game)

