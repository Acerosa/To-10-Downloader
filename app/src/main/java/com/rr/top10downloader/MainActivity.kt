package com.rr.top10downloader

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {
    //This is a tag used for logging so we know the activity is refering to.
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Logging the method call
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called")

        //Calling the inner class
        val downloadData = DownloadData()
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=10/xml")
        Log.d(TAG,"onCreate done")
    }
    //Inner class to download the data
    private inner class DownloadData: AsyncTask<String, Void, String>(){
        private val TAG = "DownloadData"

        // The function to download the xml feed
        override fun doInBackground(vararg url: String?): String {
            Log.d(TAG, "doInBackground: starts with ${url[0]}")
            val rssFeed = downloadXML(url[0])
            if (rssFeed.isEmpty()){
                Log.e(TAG,  "doInBackground: Error downloading")
            }
            return rssFeed
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d(TAG, "onPostExecute: parameter is ${result} ")
        }
    }

    // xml function download
    private fun downloadXML(urlPath: String?) : String{
        //turn the xml in to a string
        val xmlResult = StringBuilder()

        // Making the connection
        try {
            val url = URL(urlPath)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val response  = connection.responseCode
            Log.d(TAG, "downLoadXML: Thea response code is $response")

//            val inputStream = connection.inputStream
//            val inputStreamReader = InputStreamReader(inputStream)
//            val   reader = BufferedReader(inputStreamReader)

            //reading the connection response
            val reader = BufferedReader(InputStreamReader(connection.inputStream))

            val inputBuffer = CharArray(500)
            var charsRead = 0
            while (charsRead >= 0){
                charsRead = reader.read(inputBuffer)
                if (charsRead > 0){
                    xmlResult.append(String(inputBuffer, 0, charsRead))
                }
            }
            reader.close()
            Log.d(TAG, "Receiver: ${xmlResult.length} bytes")
            return xmlResult.toString()
        } catch (e: MalformedURLException){
            Log.d(TAG, "downloadXML: Invalid URL ${e.message}")
        }catch (e: IOException){
            Log.d(TAG, "downloadXML: IO Exception reading data: ${e.message}")
        }catch (e: IOException){
            Log.d(TAG, "unknown error : ${e.message}")
        }
        return "" // If it get to here there as been a problem. Returning an empty String
    }
}