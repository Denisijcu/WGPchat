package com.example.wgpchat.vmmv

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log

import java.util.*


private lateinit var textToSpeech: TextToSpeech


fun textToSpeechFn(text: Context) {

    textToSpeech = TextToSpeech(text) { status ->
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported")
            }
        } else {
            Log.e("TTS", "Initialization failed")
        }
    }


    fun onDestroy() {
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
