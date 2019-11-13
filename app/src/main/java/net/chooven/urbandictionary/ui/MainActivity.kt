package net.chooven.urbandictionary.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import net.chooven.urbandictionary.R
import net.chooven.urbandictionary.UrbanDictionary
import net.chooven.urbandictionary.data.model.UrbanDefinition
import net.chooven.urbandictionary.data.model.UrbanDictResponse
import net.chooven.urbandictionary.ui.adapter.RecyclerAdapter
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var mCompositeDisposable: CompositeDisposable
    private var searchTerm: String = ""
    private lateinit var definitionsList: List<UrbanDefinition>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager = LinearLayoutManager(this)

        btnSearch.setOnClickListener {
            hideKeyboard()
            loadDefinition()
        }
        imgSortThumbsUp.setOnClickListener {
            imgSortThumbsDown.alpha = 0.5f
            imgSortThumbsUp.alpha = 1.0f
            sortList(true)
        }
        imgSortThumbsDown.setOnClickListener {
            imgSortThumbsUp.alpha = 0.5f
            imgSortThumbsDown.alpha = 1.0f
            sortList(false)
        }
    }
    private fun sortList(byThumbsUp: Boolean){
        definitionsList = if(byThumbsUp) {
            definitionsList.sortedWith(compareBy {it.thumbsUp}).reversed()
        } else {
            definitionsList.sortedWith(compareBy {it.thumbsDown}).reversed()
        }
        recyclerResults.adapter = RecyclerAdapter(this, definitionsList)
        recyclerResults.adapter!!.notifyDataSetChanged()
    }
    private fun loadDefinition() {
        searchTerm = txtSearchTerm.text.toString().trim()
        if(searchTerm.isNotEmpty()) {
            val call = UrbanDictionary.apiServicesProvider.urbanDictService.getDefinitions(
                term = searchTerm
            ).retry(1)
            mCompositeDisposable = CompositeDisposable()

            mCompositeDisposable.add(call
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError))
        } else {
            Toast.makeText(this, "Please enter a Term to search", Toast.LENGTH_LONG).show()
        }
    }
    private fun handleResponse(urbanDictionaryResponse: UrbanDictResponse){
        if(urbanDictionaryResponse.definitions.isNotEmpty()) {
            recyclerResults.visibility = View.VISIBLE
            txtResults.text = resources.getString(R.string.result_count, urbanDictionaryResponse.definitions.size)
            definitionsList = urbanDictionaryResponse.definitions
            recyclerResults.layoutManager = layoutManager
            recyclerResults.adapter = RecyclerAdapter(this, definitionsList)
        } else {
            recyclerResults.visibility = View.GONE
        }

    }
    private fun handleError(error: Throwable) {
        Timber.e(error, "Error in API request for $searchTerm")
        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
    private fun hideKeyboard() {
        try {
            this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            if (this.currentFocus != null && this.currentFocus!!.windowToken != null) {
                (this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.clear()
    }
}
