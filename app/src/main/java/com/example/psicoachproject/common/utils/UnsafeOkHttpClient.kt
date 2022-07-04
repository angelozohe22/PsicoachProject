package com.example.psicoachproject.common.utils

import android.content.Context
import okhttp3.OkHttpClient
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class UnsafeOkHttpClient {
    companion object {
        fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                        return arrayOf()
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("TLSv1")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .writeTimeout(120, TimeUnit.SECONDS)
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { _, _ -> true }



                return builder
            } catch (e: Exception) {
                println("--- aqui >>> ${e.message.toString()}")
                throw RuntimeException(e)
            }
        }

        fun initializeSSLContext(mContext: Context) {
            try {
                SSLContext.getInstance("TLSv1")
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }

//            try {
//                ProviderInstaller.installIfNeeded(mContext.applicationContext)
//            } catch (e: GooglePlayServicesRepairableException) {
//                e.printStackTrace()
//            } catch (e: GooglePlayServicesNotAvailableException) {
//                e.printStackTrace()
//            }
        }
    }
}