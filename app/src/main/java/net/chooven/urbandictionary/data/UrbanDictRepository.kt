package net.chooven.urbandictionary.data

import androidx.lifecycle.MutableLiveData
import net.chooven.urbandictionary.UrbanDictionary
import net.chooven.urbandictionary.data.model.UrbanDictResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class UrbanDictRepository {

    fun getDefinition(term: String): MutableLiveData<UrbanDictResponse> {
        val data = MutableLiveData<UrbanDictResponse>()
        UrbanDictionary.apiServicesProvider.urbanDictService.getDefinitions(term).enqueue(object : Callback<UrbanDictResponse> {
            override fun onResponse(call: Call<UrbanDictResponse>, response: Response<UrbanDictResponse>) {
                data.value = response.body()
                Timber.i("Data retrieved Term: $term Count:${data.value?.definitions?.size}")
            }
            // Error case is left out for brevity.
            override fun onFailure(call: Call<UrbanDictResponse>, t: Throwable): Unit = TODO()
        })
        return data
    }
}