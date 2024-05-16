(defproject oiiku-data-validation "0.2.1-SNAPSHOT"
  :description "Data validation library"
  :url "https://github.com/oiiku/oiiku-data-validation"
  :license {:name "New BSD license"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :repositories
  {"github/oiiku-data-validation"
   {:url "https://maven.pkg.github.com/oiiku/oiiku-data-validation"
    :username [:gpg :env/repo_username]
    :password [:gpg :env/repo_password]}}

  ;; Library deployment
  :deploy-repositories
  {"releases"  {:url      "https://maven.pkg.github.com/oiiku/oiiku-data-validation"
                :username [:gpg :env/repo_username]
                :password [:gpg :env/repo_password]}
   "snapshots" {:url      "https://maven.pkg.github.com/oiiku/oiiku-data-validation"
                :username [:gpg :env/repo_username]
                :password [:gpg :env/repo_password]}})
