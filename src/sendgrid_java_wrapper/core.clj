(ns sendgrid-java-wrapper.core
  (:import (com.sendgrid SendGrid
                         SendGrid$Email
                         SendGrid$Response)))

(defn- prepare-email [from subject html]
  (println "from " from "subject " subject "html " html)
  (-> (SendGrid$Email.)
     (.setFrom from)
     (.setSubject subject)
     (.setHtml html)))

(defn- -send [{username :api_user password :api_key} email]
  (.send (SendGrid. username password)
         email))

(defn send-email
  [auth {to :to from :from subject :subject html :html}]
  (let [email (-> (prepare-email from subject html)
                 (.addTo to))
        response (-send auth email)]
    (.getMessage response)))

(defn bulk-email
  [auth {bcc :bcc from :from subject :subject html :html}]
  (println "[bulk email bcc] " bcc )
  (let [email (-> (prepare-email from subject html)
                 (.setBcc (into-array String bcc)))
        hack (dorun
              (println "the super awesome email" email)
              (flush))
        response (-send auth email)]
    (.getMessage response)))
