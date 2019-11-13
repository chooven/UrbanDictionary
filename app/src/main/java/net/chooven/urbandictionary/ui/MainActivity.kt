package net.chooven.urbandictionary.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import net.chooven.urbandictionary.R
import net.chooven.urbandictionary.data.UrbanDictRepository
import net.chooven.urbandictionary.data.UrbanDictResponseViewModelFactory
import net.chooven.urbandictionary.data.model.UrbanDefinition
import net.chooven.urbandictionary.data.model.UrbanDictResponse
import net.chooven.urbandictionary.data.model.view_model.UrbanDictResponseViewModel
import net.chooven.urbandictionary.ui.adapter.RecyclerAdapter
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private lateinit var mCompositeDisposable: CompositeDisposable
    private lateinit var viewModel: UrbanDictResponseViewModel
    private var searchTerm: String = ""
    private var sortByThumbsUp = true
    private var sortedList: List<UrbanDefinition> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager = LinearLayoutManager(this)
        mCompositeDisposable = CompositeDisposable()

        btnSearch.setOnClickListener {
            hideKeyboard()
            btnSearch.requestFocus()
            loadDefinitions()
        }
        txtSearchTerm.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                layoutSearchTerm.error = null
            }
        }
        imgSortThumbsUp.setOnClickListener {
            sortByThumbsUp = true
            imgSortThumbsDown.alpha = 0.5f
            imgSortThumbsUp.alpha = 1.0f
            viewModel.sortList(true)
        }
        imgSortThumbsDown.setOnClickListener {
            sortByThumbsUp = false
            imgSortThumbsUp.alpha = 0.5f
            imgSortThumbsDown.alpha = 1.0f
            viewModel.sortList(false)
        }
    }
    private fun loadDefinitions() {
        progressBar.visibility = View.VISIBLE
        searchTerm = txtSearchTerm.text.toString().trim()
        if(searchTerm.isNotEmpty()) {
            if(searchTerm.isNotEmpty()) {
                val viewModelFactory = UrbanDictResponseViewModelFactory(UrbanDictRepository(), searchTerm)
                viewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(UrbanDictResponseViewModel::class.java)
                viewModel.response.observe(this, Observer { handleResponse(it) })
            } else {
                Toast.makeText(this@MainActivity, "Please enter a word to search for", Toast.LENGTH_LONG).show()
                txtSearchTerm.requestFocus()
            }
        } else {
            Toast.makeText(this, "Please enter a Term to search", Toast.LENGTH_LONG).show()
            layoutSearchTerm.error = "Search term required"
        }
    }
    private fun handleResponse(urbanDictionaryResponse: UrbanDictResponse){
        progressBar.visibility = View.GONE
        if(urbanDictionaryResponse.definitions.isNotEmpty()) {
            recyclerResults.visibility = View.VISIBLE
            txtResults.text = resources.getString(R.string.result_count, urbanDictionaryResponse.definitions.size)
            adapter = RecyclerAdapter(this, urbanDictionaryResponse.definitions)
            recyclerResults.layoutManager = layoutManager
            recyclerResults.adapter = adapter
        } else {
            recyclerResults.visibility = View.GONE
            txtResults.text = resources.getString(R.string.no_result_count)
        }

    }
    private fun handleError(error: Throwable) {
        progressBar.visibility = View.GONE
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
