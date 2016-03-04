(ns uniprot2fasta.core
  (:require [me.raynes.fs :as fs]
            [clojure.java.io :refer [writer reader]]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :refer [join]]
            [clj-uniprot.core :refer [uniprot-seq uniprot->fasta]])
  (:gen-class))

(def cli-options
  [["-i" "--in PATH" "Path to UniProt xml formatted file to be converted."
    :parse-fn #(fs/absolute %)
    :validate [#(fs/exists? %)
               "Input file does not exist."]]
   ["-h" "--help" "Print help message."]])

(defn usage [options-summary]
  (->> ["Converts a UniProt XML formatted file into a fasta file."
        ""
        "Usage: uniprot2fasta.sh [options]"
        ""
        "Options:"
        options-summary
        ""]
       (join \newline)))

(defn error-msg [errors]
  (str "Error:\n"
       (->> errors
            (interpose \newline)
            (apply str))))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (cond
      (:help options)
      (exit 0 (usage summary))
      errors
      (exit 1 (error-msg errors)))
    (let [out (-> (fs/parent (:in options))
                  (fs/file (str (fs/name (:in options)) ".fasta")))]
      (with-open [w (writer out)
                  r (reader (:in options))]
        (doseq [s (uniprot-seq r)]
          (.write w (uniprot->fasta s)))))))
