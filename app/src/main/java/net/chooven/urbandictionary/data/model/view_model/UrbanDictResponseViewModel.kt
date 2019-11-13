package net.chooven.urbandictionary.data.model.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import net.chooven.urbandictionary.data.UrbanDictRepository
import net.chooven.urbandictionary.data.model.UrbanDictResponse

class UrbanDictResponseViewModel(urbanDictRepository: UrbanDictRepository,
                                 term: String): ViewModel() {
    /**
     * List of Definitions
     * @return Definitions
     */
    var response: LiveData<UrbanDictResponse> = urbanDictRepository.getDefinition(term)

    fun sortList(byThumbsUp: Boolean): LiveData<UrbanDictResponse> {
        if(byThumbsUp){
            response.value?.definitions?.sortedBy { it.thumbsUp }
        } else {
            response.value?.definitions?.sortedBy { it.thumbsDown }
        }
        return response
    }

}