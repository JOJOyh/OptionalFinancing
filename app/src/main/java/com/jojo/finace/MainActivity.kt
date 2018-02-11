package com.jojo.finace

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jojo.finace.constants.Constants
import com.jojo.finace.retrofit2.ObserveOnMainCallAdapterFactory
import com.jojo.finace.retrofit2.SinaFund
import com.orhanobut.logger.Logger
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observeOn = AndroidSchedulers.mainThread() // Or use mainThread() for Android.
        val sinaRetrofit = Retrofit.Builder()
                .baseUrl(Constants.SINA_FUND_BASE_URL)
                .addCallAdapterFactory(ObserveOnMainCallAdapterFactory(observeOn))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(io()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        // Create an instance of our GitHub API interface.
        val sina = sinaRetrofit.create(SinaFund::class.java)
        val sinaCall = sina.funds("fu_002130,fu_000007")
        // Create a call instance for looking up Retrofit contributors.

        val sinaCallback: Callback<String> = object: Callback<String>{
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Logger.e(t.toString())
            }

            override fun onResponse(call: Call<String>?,
                                    response: Response<String>?) {
                val contributors = response?.body()
                val ss = contributors!!.replace("\n","").split(";")
                Logger.d(ss)
                var sb=StringBuilder()
                for (s in ss){
                    if (s.isEmpty())continue
                    val funds = s.replace("var hq_str_fu_","").split("=")
                    if (funds.size<2)continue
                    var code = funds[0]
                    var contents = funds[1].replace("\"","").split(",")
                    if (contents.isEmpty()||contents.size<8)continue
                    var name = contents[0]
                    var time = contents[1]
                    var latestValuation = contents[2]
                    var netUnitValue = contents[3]
                    var cumulativeNetUnitValue = contents[4]
                    var cumulativeDividends = contents[5]
                    var quoteChange = contents[6]
                    var date = contents[7]
                    sb.append("基金代码：").append(code).append("\n")
                            .append("基金名称：").append(name).append("\n")
                            .append("时间：").append(date).append(" ").append(time).append("\n")
                            .append("最新估值：").append(latestValuation).append("\n")
                            .append("单位净值：").append(netUnitValue).append("\n")
                            .append("累计单位净值：").append(cumulativeNetUnitValue).append("\n")
                            .append("累计分红：").append(cumulativeDividends).append("\n")
                            .append("涨跌幅：").append(quoteChange).append("%\n\n")
                    //content[0]).append(content[1].substring(1,content[1].length-2)
                }
                sample_text.text = sb.toString()

            }

        }
        sinaCall.enqueue(sinaCallback)
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
