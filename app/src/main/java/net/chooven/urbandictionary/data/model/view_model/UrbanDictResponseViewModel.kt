package net.chooven.urbandictionary.data.model.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.chooven.urbandictionary.data.UrbanDictRepository
import net.chooven.urbandictionary.data.model.UrbanDictResponse
import timber.log.Timber

class UrbanDictResponseViewModel(private val urbanDictRepository: UrbanDictRepository): ViewModel() {
    /**
     * List of Definitions
     * @return Definitions
     */
    private var _definitions: MutableLiveData<UrbanDictResponse> = MutableLiveData()
    fun getDefinitions(term: String?): LiveData<UrbanDictResponse> {
        if(!term.isNullOrEmpty())
            _definitions = urbanDictRepository.getDefinition(term)
        return _definitions
    }
    fun sortList(byThumbsUp: Boolean): LiveData<UrbanDictResponse> {
        if(byThumbsUp){
            _definitions.value?.definitions?.sortedBy { it.thumbsUp }
        } else {
            _definitions.value?.definitions?.sortedBy { it.thumbsDown }
        }
        return _definitions
    }

}