;; gorilla-repl.fileformat = 1

;; **
;;; 
;; **

;; @@
(use 'nstools.ns)
(ns+ CS492_PROJECT
     (:like anglican-user.worksheet)
     (:require [clojure-csv.core :as csv]
               [clojure.java.io :as io]
               [gorilla-plot.core :as plot]))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[nil,nil]"}
;; <=

;; @@
(defn index-of [item coll]
  (count (take-while (partial not= item) coll)))

(defn take-csv
  [fname]
  (with-open [file (io/reader fname)]
    (csv/parse-csv (slurp file))))

; Files
(def vote_result_1521 (take-csv "bayesian-ballot/data/vote_result_1521.csv"))
(def vote_result vote_result_1521)

; Key member
(def members (rest (first vote_result)))
(def key-idx (index-of "염동열" members))

; Encoding
(defn encode-row [vr-row]
  (let [key-choice (nth vr-row key-idx)]
    (map (fn [x] (if (= key-choice x) 1 0)) vr-row)))

; ((true true true true true false ...) ...)
(def encoded-vote
  (map encode-row (filter
                    (fn [row] (not (= (nth row key-idx) "abs")))
                    (map rest (rest vote_result)))))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/index-of</span>","value":"#'CS492_PROJECT/index-of"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/take-csv</span>","value":"#'CS492_PROJECT/take-csv"}],"value":"[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/vote_result_1521</span>","value":"#'CS492_PROJECT/vote_result_1521"}],"value":"[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/vote_result</span>","value":"#'CS492_PROJECT/vote_result"}],"value":"[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/members</span>","value":"#'CS492_PROJECT/members"}],"value":"[[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result],#'CS492_PROJECT/members]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/key-idx</span>","value":"#'CS492_PROJECT/key-idx"}],"value":"[[[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result],#'CS492_PROJECT/members],#'CS492_PROJECT/key-idx]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/encode-row</span>","value":"#'CS492_PROJECT/encode-row"}],"value":"[[[[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result],#'CS492_PROJECT/members],#'CS492_PROJECT/key-idx],#'CS492_PROJECT/encode-row]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/encoded-vote</span>","value":"#'CS492_PROJECT/encoded-vote"}],"value":"[[[[[[[#'CS492_PROJECT/index-of,#'CS492_PROJECT/take-csv],#'CS492_PROJECT/vote_result_1521],#'CS492_PROJECT/vote_result],#'CS492_PROJECT/members],#'CS492_PROJECT/key-idx],#'CS492_PROJECT/encode-row],#'CS492_PROJECT/encoded-vote]"}
;; <=

;; @@
(defquery expr [data]
  (let [a (+ 1(sample (poisson 1)))
        b (+ 1(sample (poisson 1)))]
    (loop [currfreq 0
           currtotal 0
           currlist data]
      (if (empty currlist)
        (sample (beta a b))
        (let [nextfreq (+ currfreq (first currlist))
              nexttotal (inc currtotal)
              nextlist (rest currlist)
              prob (/ nextfreq nexttotal)]
          (observe (beta a b) prob))))))

(def n_sample 1000)
(defn plot-member [i]
  (plot/histogram (map :result
                       (take n_sample
                             (take-nth 50
                                       (drop 1000 (doquery :lmh expr [(nth encoded-vote i)])))))
                :bins 20
                :normalise :probability))

(for [i (range 0 2)]
  (plot-member i))
;; @@
;; =>
;;; {"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"list-like","open":"","close":"","separator":"</pre><pre>","items":[{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/expr</span>","value":"#'CS492_PROJECT/expr"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/n_sample</span>","value":"#'CS492_PROJECT/n_sample"}],"value":"[#'CS492_PROJECT/expr,#'CS492_PROJECT/n_sample]"},{"type":"html","content":"<span class='clj-var'>#&#x27;CS492_PROJECT/plot-member</span>","value":"#'CS492_PROJECT/plot-member"}],"value":"[[#'CS492_PROJECT/expr,#'CS492_PROJECT/n_sample],#'CS492_PROJECT/plot-member]"},{"type":"list-like","open":"<span class='clj-lazy-seq'>(</span>","close":"<span class='clj-lazy-seq'>)</span>","separator":" ","items":[{"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"409bc25c-9425-4dc0-86c3-1505083d44b0","values":[{"x":0.0002854122221672972,"y":0},{"x":0.020273589299659386,"y":0.014},{"x":0.040261766377151474,"y":0.016},{"x":0.06024994345464356,"y":0.014},{"x":0.08023812053213565,"y":0.016},{"x":0.10022629760962774,"y":0.014},{"x":0.12021447468711983,"y":0.016},{"x":0.1402026517646119,"y":0.019},{"x":0.160190828842104,"y":0.015},{"x":0.1801790059195961,"y":0.026},{"x":0.2001671829970882,"y":0.02},{"x":0.22015536007458028,"y":0.021},{"x":0.24014353715207237,"y":0.019},{"x":0.26013171422956444,"y":0.028},{"x":0.2801198913070565,"y":0.02},{"x":0.30010806838454857,"y":0.02},{"x":0.32009624546204063,"y":0.021},{"x":0.3400844225395327,"y":0.021},{"x":0.36007259961702476,"y":0.021},{"x":0.3800607766945168,"y":0.027},{"x":0.4000489537720089,"y":0.022},{"x":0.42003713084950095,"y":0.029},{"x":0.440025307926993,"y":0.015},{"x":0.4600134850044851,"y":0.027},{"x":0.48000166208197714,"y":0.021},{"x":0.4999898391594692,"y":0.022},{"x":0.5199780162369613,"y":0.019},{"x":0.5399661933144534,"y":0.027},{"x":0.5599543703919455,"y":0.021},{"x":0.5799425474694376,"y":0.025},{"x":0.5999307245469298,"y":0.02},{"x":0.6199189016244219,"y":0.022},{"x":0.639907078701914,"y":0.018},{"x":0.6598952557794061,"y":0.028},{"x":0.6798834328568982,"y":0.021},{"x":0.6998716099343903,"y":0.017},{"x":0.7198597870118825,"y":0.023},{"x":0.7398479640893746,"y":0.014},{"x":0.7598361411668667,"y":0.018},{"x":0.7798243182443588,"y":0.02},{"x":0.799812495321851,"y":0.025},{"x":0.8198006723993431,"y":0.018},{"x":0.8397888494768352,"y":0.018},{"x":0.8597770265543273,"y":0.011},{"x":0.8797652036318194,"y":0.021},{"x":0.8997533807093115,"y":0.014},{"x":0.9197415577868037,"y":0.019},{"x":0.9397297348642958,"y":0.026},{"x":0.9597179119417879,"y":0.017},{"x":0.97970608901928,"y":0.017},{"x":0.9996942660967721,"y":0.017},{"x":1.0196824431742642,"y":0}]}],"marks":[{"type":"line","from":{"data":"409bc25c-9425-4dc0-86c3-1505083d44b0"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"409bc25c-9425-4dc0-86c3-1505083d44b0","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"409bc25c-9425-4dc0-86c3-1505083d44b0","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"409bc25c-9425-4dc0-86c3-1505083d44b0\", :values ({:x 2.854122221672972E-4, :y 0} {:x 0.020273589299659386, :y 0.014} {:x 0.040261766377151474, :y 0.016} {:x 0.06024994345464356, :y 0.014} {:x 0.08023812053213565, :y 0.016} {:x 0.10022629760962774, :y 0.014} {:x 0.12021447468711983, :y 0.016} {:x 0.1402026517646119, :y 0.019} {:x 0.160190828842104, :y 0.015} {:x 0.1801790059195961, :y 0.026} {:x 0.2001671829970882, :y 0.02} {:x 0.22015536007458028, :y 0.021} {:x 0.24014353715207237, :y 0.019} {:x 0.26013171422956444, :y 0.028} {:x 0.2801198913070565, :y 0.02} {:x 0.30010806838454857, :y 0.02} {:x 0.32009624546204063, :y 0.021} {:x 0.3400844225395327, :y 0.021} {:x 0.36007259961702476, :y 0.021} {:x 0.3800607766945168, :y 0.027} {:x 0.4000489537720089, :y 0.022} {:x 0.42003713084950095, :y 0.029} {:x 0.440025307926993, :y 0.015} {:x 0.4600134850044851, :y 0.027} {:x 0.48000166208197714, :y 0.021} {:x 0.4999898391594692, :y 0.022} {:x 0.5199780162369613, :y 0.019} {:x 0.5399661933144534, :y 0.027} {:x 0.5599543703919455, :y 0.021} {:x 0.5799425474694376, :y 0.025} {:x 0.5999307245469298, :y 0.02} {:x 0.6199189016244219, :y 0.022} {:x 0.639907078701914, :y 0.018} {:x 0.6598952557794061, :y 0.028} {:x 0.6798834328568982, :y 0.021} {:x 0.6998716099343903, :y 0.017} {:x 0.7198597870118825, :y 0.023} {:x 0.7398479640893746, :y 0.014} {:x 0.7598361411668667, :y 0.018} {:x 0.7798243182443588, :y 0.02} {:x 0.799812495321851, :y 0.025} {:x 0.8198006723993431, :y 0.018} {:x 0.8397888494768352, :y 0.018} {:x 0.8597770265543273, :y 0.011} {:x 0.8797652036318194, :y 0.021} {:x 0.8997533807093115, :y 0.014} {:x 0.9197415577868037, :y 0.019} {:x 0.9397297348642958, :y 0.026} {:x 0.9597179119417879, :y 0.017} {:x 0.97970608901928, :y 0.017} {:x 0.9996942660967721, :y 0.017} {:x 1.0196824431742642, :y 0})}], :marks [{:type \"line\", :from {:data \"409bc25c-9425-4dc0-86c3-1505083d44b0\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"409bc25c-9425-4dc0-86c3-1505083d44b0\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"409bc25c-9425-4dc0-86c3-1505083d44b0\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"},{"type":"vega","content":{"width":400,"height":247.2187957763672,"padding":{"top":10,"left":55,"bottom":40,"right":10},"data":[{"name":"8cb1cb64-ab9d-4030-b194-9e467ce40852","values":[{"x":0.0015395134380221387,"y":0},{"x":0.021508600124505485,"y":0.02},{"x":0.04147768681098883,"y":0.011},{"x":0.06144677349747218,"y":0.021},{"x":0.08141586018395552,"y":0.021},{"x":0.10138494687043886,"y":0.02},{"x":0.1213540335569222,"y":0.015},{"x":0.14132312024340554,"y":0.025},{"x":0.16129220692988888,"y":0.011},{"x":0.18126129361637222,"y":0.017},{"x":0.20123038030285556,"y":0.022},{"x":0.2211994669893389,"y":0.019},{"x":0.24116855367582224,"y":0.019},{"x":0.2611376403623056,"y":0.019},{"x":0.2811067270487889,"y":0.018},{"x":0.30107581373527226,"y":0.019},{"x":0.3210449004217556,"y":0.019},{"x":0.34101398710823894,"y":0.022},{"x":0.3609830737947223,"y":0.026},{"x":0.3809521604812056,"y":0.014},{"x":0.40092124716768895,"y":0.02},{"x":0.4208903338541723,"y":0.022},{"x":0.44085942054065563,"y":0.021},{"x":0.460828507227139,"y":0.024},{"x":0.4807975939136223,"y":0.022},{"x":0.5007666806001057,"y":0.022},{"x":0.520735767286589,"y":0.018},{"x":0.5407048539730723,"y":0.021},{"x":0.5606739406595557,"y":0.03},{"x":0.580643027346039,"y":0.017},{"x":0.6006121140325223,"y":0.02},{"x":0.6205812007190057,"y":0.027},{"x":0.640550287405489,"y":0.028},{"x":0.6605193740919724,"y":0.021},{"x":0.6804884607784557,"y":0.023},{"x":0.700457547464939,"y":0.026},{"x":0.7204266341514224,"y":0.018},{"x":0.7403957208379057,"y":0.013},{"x":0.7603648075243891,"y":0.021},{"x":0.7803338942108724,"y":0.028},{"x":0.8003029808973557,"y":0.022},{"x":0.8202720675838391,"y":0.019},{"x":0.8402411542703224,"y":0.016},{"x":0.8602102409568058,"y":0.016},{"x":0.8801793276432891,"y":0.02},{"x":0.9001484143297724,"y":0.023},{"x":0.9201175010162558,"y":0.016},{"x":0.9400865877027391,"y":0.018},{"x":0.9600556743892225,"y":0.01},{"x":0.9800247610757058,"y":0.013},{"x":0.9999938477621891,"y":0.026},{"x":1.0199629344486725,"y":0.001},{"x":1.039932021135156,"y":0}]}],"marks":[{"type":"line","from":{"data":"8cb1cb64-ab9d-4030-b194-9e467ce40852"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"interpolate":{"value":"step-before"},"fill":{"value":"steelblue"},"fillOpacity":{"value":0.4},"stroke":{"value":"steelblue"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"8cb1cb64-ab9d-4030-b194-9e467ce40852","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"8cb1cb64-ab9d-4030-b194-9e467ce40852","field":"data.y"}}],"axes":[{"type":"x","scale":"x"},{"type":"y","scale":"y"}]},"value":"#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"8cb1cb64-ab9d-4030-b194-9e467ce40852\", :values ({:x 0.0015395134380221387, :y 0} {:x 0.021508600124505485, :y 0.02} {:x 0.04147768681098883, :y 0.011} {:x 0.06144677349747218, :y 0.021} {:x 0.08141586018395552, :y 0.021} {:x 0.10138494687043886, :y 0.02} {:x 0.1213540335569222, :y 0.015} {:x 0.14132312024340554, :y 0.025} {:x 0.16129220692988888, :y 0.011} {:x 0.18126129361637222, :y 0.017} {:x 0.20123038030285556, :y 0.022} {:x 0.2211994669893389, :y 0.019} {:x 0.24116855367582224, :y 0.019} {:x 0.2611376403623056, :y 0.019} {:x 0.2811067270487889, :y 0.018} {:x 0.30107581373527226, :y 0.019} {:x 0.3210449004217556, :y 0.019} {:x 0.34101398710823894, :y 0.022} {:x 0.3609830737947223, :y 0.026} {:x 0.3809521604812056, :y 0.014} {:x 0.40092124716768895, :y 0.02} {:x 0.4208903338541723, :y 0.022} {:x 0.44085942054065563, :y 0.021} {:x 0.460828507227139, :y 0.024} {:x 0.4807975939136223, :y 0.022} {:x 0.5007666806001057, :y 0.022} {:x 0.520735767286589, :y 0.018} {:x 0.5407048539730723, :y 0.021} {:x 0.5606739406595557, :y 0.03} {:x 0.580643027346039, :y 0.017} {:x 0.6006121140325223, :y 0.02} {:x 0.6205812007190057, :y 0.027} {:x 0.640550287405489, :y 0.028} {:x 0.6605193740919724, :y 0.021} {:x 0.6804884607784557, :y 0.023} {:x 0.700457547464939, :y 0.026} {:x 0.7204266341514224, :y 0.018} {:x 0.7403957208379057, :y 0.013} {:x 0.7603648075243891, :y 0.021} {:x 0.7803338942108724, :y 0.028} {:x 0.8003029808973557, :y 0.022} {:x 0.8202720675838391, :y 0.019} {:x 0.8402411542703224, :y 0.016} {:x 0.8602102409568058, :y 0.016} {:x 0.8801793276432891, :y 0.02} {:x 0.9001484143297724, :y 0.023} {:x 0.9201175010162558, :y 0.016} {:x 0.9400865877027391, :y 0.018} {:x 0.9600556743892225, :y 0.01} {:x 0.9800247610757058, :y 0.013} {:x 0.9999938477621891, :y 0.026} {:x 1.0199629344486725, :y 0.001} {:x 1.039932021135156, :y 0})}], :marks [{:type \"line\", :from {:data \"8cb1cb64-ab9d-4030-b194-9e467ce40852\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"8cb1cb64-ab9d-4030-b194-9e467ce40852\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"8cb1cb64-ab9d-4030-b194-9e467ce40852\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}}"}],"value":"(#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"409bc25c-9425-4dc0-86c3-1505083d44b0\", :values ({:x 2.854122221672972E-4, :y 0} {:x 0.020273589299659386, :y 0.014} {:x 0.040261766377151474, :y 0.016} {:x 0.06024994345464356, :y 0.014} {:x 0.08023812053213565, :y 0.016} {:x 0.10022629760962774, :y 0.014} {:x 0.12021447468711983, :y 0.016} {:x 0.1402026517646119, :y 0.019} {:x 0.160190828842104, :y 0.015} {:x 0.1801790059195961, :y 0.026} {:x 0.2001671829970882, :y 0.02} {:x 0.22015536007458028, :y 0.021} {:x 0.24014353715207237, :y 0.019} {:x 0.26013171422956444, :y 0.028} {:x 0.2801198913070565, :y 0.02} {:x 0.30010806838454857, :y 0.02} {:x 0.32009624546204063, :y 0.021} {:x 0.3400844225395327, :y 0.021} {:x 0.36007259961702476, :y 0.021} {:x 0.3800607766945168, :y 0.027} {:x 0.4000489537720089, :y 0.022} {:x 0.42003713084950095, :y 0.029} {:x 0.440025307926993, :y 0.015} {:x 0.4600134850044851, :y 0.027} {:x 0.48000166208197714, :y 0.021} {:x 0.4999898391594692, :y 0.022} {:x 0.5199780162369613, :y 0.019} {:x 0.5399661933144534, :y 0.027} {:x 0.5599543703919455, :y 0.021} {:x 0.5799425474694376, :y 0.025} {:x 0.5999307245469298, :y 0.02} {:x 0.6199189016244219, :y 0.022} {:x 0.639907078701914, :y 0.018} {:x 0.6598952557794061, :y 0.028} {:x 0.6798834328568982, :y 0.021} {:x 0.6998716099343903, :y 0.017} {:x 0.7198597870118825, :y 0.023} {:x 0.7398479640893746, :y 0.014} {:x 0.7598361411668667, :y 0.018} {:x 0.7798243182443588, :y 0.02} {:x 0.799812495321851, :y 0.025} {:x 0.8198006723993431, :y 0.018} {:x 0.8397888494768352, :y 0.018} {:x 0.8597770265543273, :y 0.011} {:x 0.8797652036318194, :y 0.021} {:x 0.8997533807093115, :y 0.014} {:x 0.9197415577868037, :y 0.019} {:x 0.9397297348642958, :y 0.026} {:x 0.9597179119417879, :y 0.017} {:x 0.97970608901928, :y 0.017} {:x 0.9996942660967721, :y 0.017} {:x 1.0196824431742642, :y 0})}], :marks [{:type \"line\", :from {:data \"409bc25c-9425-4dc0-86c3-1505083d44b0\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"409bc25c-9425-4dc0-86c3-1505083d44b0\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"409bc25c-9425-4dc0-86c3-1505083d44b0\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}} #gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"8cb1cb64-ab9d-4030-b194-9e467ce40852\", :values ({:x 0.0015395134380221387, :y 0} {:x 0.021508600124505485, :y 0.02} {:x 0.04147768681098883, :y 0.011} {:x 0.06144677349747218, :y 0.021} {:x 0.08141586018395552, :y 0.021} {:x 0.10138494687043886, :y 0.02} {:x 0.1213540335569222, :y 0.015} {:x 0.14132312024340554, :y 0.025} {:x 0.16129220692988888, :y 0.011} {:x 0.18126129361637222, :y 0.017} {:x 0.20123038030285556, :y 0.022} {:x 0.2211994669893389, :y 0.019} {:x 0.24116855367582224, :y 0.019} {:x 0.2611376403623056, :y 0.019} {:x 0.2811067270487889, :y 0.018} {:x 0.30107581373527226, :y 0.019} {:x 0.3210449004217556, :y 0.019} {:x 0.34101398710823894, :y 0.022} {:x 0.3609830737947223, :y 0.026} {:x 0.3809521604812056, :y 0.014} {:x 0.40092124716768895, :y 0.02} {:x 0.4208903338541723, :y 0.022} {:x 0.44085942054065563, :y 0.021} {:x 0.460828507227139, :y 0.024} {:x 0.4807975939136223, :y 0.022} {:x 0.5007666806001057, :y 0.022} {:x 0.520735767286589, :y 0.018} {:x 0.5407048539730723, :y 0.021} {:x 0.5606739406595557, :y 0.03} {:x 0.580643027346039, :y 0.017} {:x 0.6006121140325223, :y 0.02} {:x 0.6205812007190057, :y 0.027} {:x 0.640550287405489, :y 0.028} {:x 0.6605193740919724, :y 0.021} {:x 0.6804884607784557, :y 0.023} {:x 0.700457547464939, :y 0.026} {:x 0.7204266341514224, :y 0.018} {:x 0.7403957208379057, :y 0.013} {:x 0.7603648075243891, :y 0.021} {:x 0.7803338942108724, :y 0.028} {:x 0.8003029808973557, :y 0.022} {:x 0.8202720675838391, :y 0.019} {:x 0.8402411542703224, :y 0.016} {:x 0.8602102409568058, :y 0.016} {:x 0.8801793276432891, :y 0.02} {:x 0.9001484143297724, :y 0.023} {:x 0.9201175010162558, :y 0.016} {:x 0.9400865877027391, :y 0.018} {:x 0.9600556743892225, :y 0.01} {:x 0.9800247610757058, :y 0.013} {:x 0.9999938477621891, :y 0.026} {:x 1.0199629344486725, :y 0.001} {:x 1.039932021135156, :y 0})}], :marks [{:type \"line\", :from {:data \"8cb1cb64-ab9d-4030-b194-9e467ce40852\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"8cb1cb64-ab9d-4030-b194-9e467ce40852\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"8cb1cb64-ab9d-4030-b194-9e467ce40852\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}})"}],"value":"[[[#'CS492_PROJECT/expr,#'CS492_PROJECT/n_sample],#'CS492_PROJECT/plot-member],(#gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"409bc25c-9425-4dc0-86c3-1505083d44b0\", :values ({:x 2.854122221672972E-4, :y 0} {:x 0.020273589299659386, :y 0.014} {:x 0.040261766377151474, :y 0.016} {:x 0.06024994345464356, :y 0.014} {:x 0.08023812053213565, :y 0.016} {:x 0.10022629760962774, :y 0.014} {:x 0.12021447468711983, :y 0.016} {:x 0.1402026517646119, :y 0.019} {:x 0.160190828842104, :y 0.015} {:x 0.1801790059195961, :y 0.026} {:x 0.2001671829970882, :y 0.02} {:x 0.22015536007458028, :y 0.021} {:x 0.24014353715207237, :y 0.019} {:x 0.26013171422956444, :y 0.028} {:x 0.2801198913070565, :y 0.02} {:x 0.30010806838454857, :y 0.02} {:x 0.32009624546204063, :y 0.021} {:x 0.3400844225395327, :y 0.021} {:x 0.36007259961702476, :y 0.021} {:x 0.3800607766945168, :y 0.027} {:x 0.4000489537720089, :y 0.022} {:x 0.42003713084950095, :y 0.029} {:x 0.440025307926993, :y 0.015} {:x 0.4600134850044851, :y 0.027} {:x 0.48000166208197714, :y 0.021} {:x 0.4999898391594692, :y 0.022} {:x 0.5199780162369613, :y 0.019} {:x 0.5399661933144534, :y 0.027} {:x 0.5599543703919455, :y 0.021} {:x 0.5799425474694376, :y 0.025} {:x 0.5999307245469298, :y 0.02} {:x 0.6199189016244219, :y 0.022} {:x 0.639907078701914, :y 0.018} {:x 0.6598952557794061, :y 0.028} {:x 0.6798834328568982, :y 0.021} {:x 0.6998716099343903, :y 0.017} {:x 0.7198597870118825, :y 0.023} {:x 0.7398479640893746, :y 0.014} {:x 0.7598361411668667, :y 0.018} {:x 0.7798243182443588, :y 0.02} {:x 0.799812495321851, :y 0.025} {:x 0.8198006723993431, :y 0.018} {:x 0.8397888494768352, :y 0.018} {:x 0.8597770265543273, :y 0.011} {:x 0.8797652036318194, :y 0.021} {:x 0.8997533807093115, :y 0.014} {:x 0.9197415577868037, :y 0.019} {:x 0.9397297348642958, :y 0.026} {:x 0.9597179119417879, :y 0.017} {:x 0.97970608901928, :y 0.017} {:x 0.9996942660967721, :y 0.017} {:x 1.0196824431742642, :y 0})}], :marks [{:type \"line\", :from {:data \"409bc25c-9425-4dc0-86c3-1505083d44b0\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"409bc25c-9425-4dc0-86c3-1505083d44b0\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"409bc25c-9425-4dc0-86c3-1505083d44b0\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}} #gorilla_repl.vega.VegaView{:content {:width 400, :height 247.2188, :padding {:top 10, :left 55, :bottom 40, :right 10}, :data [{:name \"8cb1cb64-ab9d-4030-b194-9e467ce40852\", :values ({:x 0.0015395134380221387, :y 0} {:x 0.021508600124505485, :y 0.02} {:x 0.04147768681098883, :y 0.011} {:x 0.06144677349747218, :y 0.021} {:x 0.08141586018395552, :y 0.021} {:x 0.10138494687043886, :y 0.02} {:x 0.1213540335569222, :y 0.015} {:x 0.14132312024340554, :y 0.025} {:x 0.16129220692988888, :y 0.011} {:x 0.18126129361637222, :y 0.017} {:x 0.20123038030285556, :y 0.022} {:x 0.2211994669893389, :y 0.019} {:x 0.24116855367582224, :y 0.019} {:x 0.2611376403623056, :y 0.019} {:x 0.2811067270487889, :y 0.018} {:x 0.30107581373527226, :y 0.019} {:x 0.3210449004217556, :y 0.019} {:x 0.34101398710823894, :y 0.022} {:x 0.3609830737947223, :y 0.026} {:x 0.3809521604812056, :y 0.014} {:x 0.40092124716768895, :y 0.02} {:x 0.4208903338541723, :y 0.022} {:x 0.44085942054065563, :y 0.021} {:x 0.460828507227139, :y 0.024} {:x 0.4807975939136223, :y 0.022} {:x 0.5007666806001057, :y 0.022} {:x 0.520735767286589, :y 0.018} {:x 0.5407048539730723, :y 0.021} {:x 0.5606739406595557, :y 0.03} {:x 0.580643027346039, :y 0.017} {:x 0.6006121140325223, :y 0.02} {:x 0.6205812007190057, :y 0.027} {:x 0.640550287405489, :y 0.028} {:x 0.6605193740919724, :y 0.021} {:x 0.6804884607784557, :y 0.023} {:x 0.700457547464939, :y 0.026} {:x 0.7204266341514224, :y 0.018} {:x 0.7403957208379057, :y 0.013} {:x 0.7603648075243891, :y 0.021} {:x 0.7803338942108724, :y 0.028} {:x 0.8003029808973557, :y 0.022} {:x 0.8202720675838391, :y 0.019} {:x 0.8402411542703224, :y 0.016} {:x 0.8602102409568058, :y 0.016} {:x 0.8801793276432891, :y 0.02} {:x 0.9001484143297724, :y 0.023} {:x 0.9201175010162558, :y 0.016} {:x 0.9400865877027391, :y 0.018} {:x 0.9600556743892225, :y 0.01} {:x 0.9800247610757058, :y 0.013} {:x 0.9999938477621891, :y 0.026} {:x 1.0199629344486725, :y 0.001} {:x 1.039932021135156, :y 0})}], :marks [{:type \"line\", :from {:data \"8cb1cb64-ab9d-4030-b194-9e467ce40852\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :interpolate {:value \"step-before\"}, :fill {:value \"steelblue\"}, :fillOpacity {:value 0.4}, :stroke {:value \"steelblue\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"8cb1cb64-ab9d-4030-b194-9e467ce40852\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"8cb1cb64-ab9d-4030-b194-9e467ce40852\", :field \"data.y\"}}], :axes [{:type \"x\", :scale \"x\"} {:type \"y\", :scale \"y\"}]}})]"}
;; <=

;; @@

;; @@
