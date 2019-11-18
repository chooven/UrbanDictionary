package net.chooven.urbandictionary.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.chooven.urbandictionary.data.model.view_model.UrbanDictResponseViewModel

@Suppress("UNCHECKED_CAST")
class UrbanDictResponseViewModelFactory(
    private val urbanDictRepository: UrbanDictRepository) : ViewModelProvider.Factory
 {
     override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         if(modelClass.isAssignableFrom(UrbanDictResponseViewModel::class.java)){
             return UrbanDictResponseViewModel(urbanDictRepository) as T
         }
         throw IllegalArgumentException("Unknown ViewModel class")
     }

 }