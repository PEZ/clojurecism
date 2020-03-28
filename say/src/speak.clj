(ns speak
  (:import
   java.util.Locale
   javax.speech.Central
   javax.speech.synthesis.Synthesizer
   javax.speech.synthesis.SynthesizerModeDesc))

(System/setProperty "freetts.voices" "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory")
(Central/registerEngineCentral "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral")

(def synthesizer (Central/createSynthesizer (SynthesizerModeDesc. Locale/US)))
(.allocate synthesizer)
(.resume synthesizer)

(defn speak [text]
  (.speakPlainText synthesizer text nil)
  (.waitEngineState synthesizer Synthesizer/QUEUE_EMPTY))

;(.deallocate synthesizer)

(comment
  (speak "Foo"))
