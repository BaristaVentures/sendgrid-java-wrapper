(ns sendgrid-java-wrapper.core
  (:import (com.sendgrid SendGrid
                         SendGrid$Email
                         SendGrid$Response)))

(defn- prepare-email [from subject html]
  (println "from " from "subject " subject "html " html "from-name" from-name)
  (-> (SendGrid$Email.)
     (.setFrom from)
     (.setSubject subject)
     (.setHtml html))
     (.setFromName from-name))

(defn- -send [{username :api_user password :api_key} email]
  (.send (SendGrid. username password)
         email))

(defn send-email
  [auth {to :to from :from from-name :from-name subject :subject html :html}]
  (let [email (-> (prepare-email from subject html from-name)
                 (.addTo to))
        response (-send auth email)]
    (.getMessage response)))

(defn send-email-groupid
  [auth {to :to from :from from-name :from-name subject :subject html :html} group-id]
  (let [email (-> (prepare-email from subject html from-name)
                 (.addTo to)
                 (.setASMGroupId group-id))
        response (-send auth email)]
    (.getMessage response)))

(defn bulk-email
  [auth {bcc :bcc from :from  from-name :from-name subject :subject html :html}]
  (println "[bulk email bcc] " bcc )
  (let [email (-> (prepare-email from subject html from-name)
                 (.setBcc (into-array String bcc)))
        hack (dorun
              (println "the super awesome email" (bean email))
              (flush))
        response (-send auth email)]
    (.getMessage response)))

(defn bulk-email-groupid
  [auth {bcc :bcc from :from  from-name :from-name subject :subject html :html} group-id]
  (println "[bulk email bcc] " bcc )
  (let [email (-> (prepare-email from subject html from-name)
                 (.setBcc (into-array String bcc))
                 (.setASMGroupId group-id))
        hack (dorun
              (println "the super awesome email" (bean email))
              (flush))
        response (-send auth email)]
    (.getMessage response)))