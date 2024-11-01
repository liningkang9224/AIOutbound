package com.yunke.lib_http.download

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object DownloadUtil {


    /**
     * 文件下载
     *
     */
    fun start(downloadUrl: String, path: String, name: String): Boolean {
        var `in`: InputStream? = null
        var out: FileOutputStream? = null
        var isOk = false
        try {
            val url = URL(downloadUrl)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.doOutput = false
            urlConnection.connectTimeout = 10 * 1000
            urlConnection.readTimeout = 10 * 1000
            urlConnection.setRequestProperty("Connection", "Keep-Alive")
            urlConnection.setRequestProperty("Charset", "UTF-8")
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate")
            urlConnection.connect()
            val bytetotal = urlConnection.contentLength.toLong()
            var bytesum: Long = 0
            var byteread = 0
            `in` = urlConnection.inputStream
            val dir = File(path)
            if (!dir.exists() || dir.isFile) {
                dir.mkdirs()
            }
            val apkFile = File(dir, name)
            out = FileOutputStream(apkFile)
            val buffer = ByteArray(2048)
            var progress = 0
            while (`in`.read(buffer).also { byteread = it } != -1) {
                bytesum += byteread.toLong()
                out.write(buffer, 0, byteread)
                val tempProgress = (bytesum * 100L / bytetotal).toInt()
                if (tempProgress != progress) {
                    progress = tempProgress
                }
            }
            out.flush()
            isOk = true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                `in`?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return isOk
    }
}