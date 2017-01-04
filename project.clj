(defproject oiiku-data-validation "0.1.1"
  :description "Data validation library"
  :url "https://github.com/oiiku/oiiku-data-validation"
  :license {:name "New BSD license"}
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :repositories
  {"oiiku-releases" "http://148.251.86.208:8081/nexus/content/repositories/releases"
   "oiiku-snapshots" "http://148.251.86.208:8081/nexus/content/repositories/snapshots"}
  :deploy-repositories
  {"releases" {:url "http://148.251.86.208:8081/nexus/content/repositories/releases"}
   "snapshots" {:url "http://148.251.86.208:8081/nexus/content/repositories/snapshots"}})
