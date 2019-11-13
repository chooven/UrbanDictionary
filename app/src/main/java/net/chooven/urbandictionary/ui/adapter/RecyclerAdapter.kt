package net.chooven.urbandictionary.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.chooven.urbandictionary.R
import net.chooven.urbandictionary.data.model.UrbanDefinition
import net.chooven.urbandictionary.util.StringFormatter


class RecyclerAdapter(private val context: Context, private var definitions: List<UrbanDefinition>): RecyclerView.Adapter<RecyclerAdapter.DefinitionItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflatedView = inflater.inflate(R.layout.definition_adapter_item, parent, false)
        return DefinitionItemHolder(inflatedView)
    }

    override fun getItemCount(): Int = definitions.size

    override fun onBindViewHolder(holder: DefinitionItemHolder, position: Int) {
        val item = definitions[position]
        holder.definition = item
        holder.context = context
        holder.bindDefinition()
    }
    class DefinitionItemHolder(view: View): RecyclerView.ViewHolder(view){
        var context: Context? = null
        var definition: UrbanDefinition? = null
        private var txtDate: TextView = view.findViewById(R.id.txtDate)
        private var txtDefinition: TextView = view.findViewById(R.id.txtDefinition)
        private var txtThumbsUp: TextView = view.findViewById(R.id.txtThumbsUp)
        private var txtThumbsDown: TextView = view.findViewById(R.id.txtThumbsDown)
        private var txtAuthor: TextView = view.findViewById(R.id.txtAuthor)
        private var txtExample: TextView = view.findViewById(R.id.txtExample)

        fun bindDefinition(){
            if(definition != null) {
                val dateString = StringFormatter.convertTimestampToDateFormat(definition!!.writtenOn)
                txtDate.text =
                    context!!.resources.getString(R.string.definition_date, dateString)

                txtDefinition.text = definition!!.definition
                txtThumbsUp.text = definition!!.thumbsUp.toString()
                txtThumbsDown.text = definition!!.thumbsDown.toString()
                txtAuthor.text =
                    context!!.resources.getString(R.string.definition_author, definition!!.author)
                txtExample.text =
                    context!!.resources.getString(R.string.definition_examples, definition!!.example)
            }
        }
    }
}