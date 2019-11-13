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
    //var definitions: List<UrbanDictResponse> = urbanDictRepository.getDefinition(term)
}