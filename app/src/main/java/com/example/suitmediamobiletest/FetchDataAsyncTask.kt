package com.example.suitmediamobiletest

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class FetchDataAsyncTask(private val listener: OnDataFetchedListener) {

    interface OnDataFetchedListener {
        fun onDataFetched(result: String)
    }

    fun execute(url: String) {
        val task = FetchDataTask(url, listener)
        val executor = Executors.newSingleThreadExecutor()
        executor.execute(task)
    }

    private class FetchDataTask(private val url: String, private val listener: OnDataFetchedListener) : Runnable {
        override fun run() {
            val result = fetchData(url)
            listener.onDataFetched(result)
        }

        private fun fetchData(url: String): String {
            var result = ""
            try {
                val urlConnection = URL(url).openConnection() as HttpURLConnection
                try {
                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    val buffer = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        buffer.append(line).append("\n")
                    }

                    result = buffer.toString()
                } finally {
                    urlConnection.disconnect()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return result
        }
    }
}
