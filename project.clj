(defproject oiiku-data-validation "0.2.0-SNAPSHOT"
  :description "Data validation library"
  :url "https://github.com/oiiku/oiiku-data-validation"
  :license {:name "New BSD license"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :repositories
  {"oiiku-releases" "https://nexus.oiiku.no/nexus/content/repositories/releases"
   "oiiku-snapshots" "https://nexus.oiiku.no/nexus/content/repositories/snapshots"}
  :deploy-repositories
  {"releases" {:url "https://nexus.oiiku.no/nexus/content/repositories/releases"}
   "snapshots" {:url "https://nexus.oiiku.no/nexus/content/repositories/snapshots"}})
