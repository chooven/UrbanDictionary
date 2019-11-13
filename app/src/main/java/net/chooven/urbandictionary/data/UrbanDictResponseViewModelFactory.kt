package net.chooven.urbandictionary.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.chooven.urbandictionary.data.model.view_model.UrbanDictResponseViewModel

class UrbanDictResponseViewModelFactory(
    private val urbanDictRepository: UrbanDictRepository,
    private val term: String) : ViewModelProvider.Factory
 {
     override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         if(modelClass.isAssignableFrom(UrbanDictResponseViewModel::class.java)){
             return UrbanDictResponseViewModel(urbanDictRepository, term) as T
         }
         throw IllegalArgumentException("Unknown ViewModel class")
     }

 }