(defproject uniprot2fasta "0.1.0-SNAPSHOT"
  :description "Converts a UniProt XML formatted file into a FASTA file."
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [me.raynes/fs "1.4.6"]
                 [clj-uniprot "0.1.2"]
                 [org.clojure/tools.cli "0.3.3"]]
  :main ^:skip-aot uniprot2fasta.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
