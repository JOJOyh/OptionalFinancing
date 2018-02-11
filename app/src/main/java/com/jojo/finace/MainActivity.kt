package com.jojo.finace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jojo.finace.retrofit.Contributor
import com.jojo.finace.retrofit.GitHub
import com.jojo.finace.retrofit.ObserveOnMainCallAdapterFactory
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observeOn = AndroidSchedulers.mainThread() // Or use mainThread() for Android.
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addCallAdapterFactory(ObserveOnMainCallAdapterFactory(observeOn))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        // Create an instance of our GitHub API interface.
        val github = retrofit.create(GitHub::class.java)

        // Create a call instance for looking up Retrofit contributors.
        val call = github.contributors("JOJOyh", "OptionalFinancing")

        // Fetch and print a list of the contributors to the library.
        val callback: Callback<List<Contributor>> = object : Callback<List<Contributor>> {
            override fun onFailure(call: Call<List<Contributor>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<List<Contributor>>?, response: Response<List<Contributor>>?) {
                val contributors = response?.body()
                for (contributor in contributors!!) {
                    println(contributor.login + " (" + contributor.contributions + ")")
                    Logger.d(contributor.login
                            + " (" + contributor.contributions + ")")
                }
            }

        }
        call.enqueue(callback)
        Logger.d("jojo yh ")

    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
