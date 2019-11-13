package net.chooven.urbandictionary.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.chooven.urbandictionary.R
import net.chooven.urbandictionary.data.model.UrbanDefinition
import net.chooven.urbandictionary.util.StringFormatter


class RecyclerAdapter(private var definitions: List<UrbanDefinition>): RecyclerView.Adapter<RecyclerAdapter.DefinitionItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val inflatedView = inflater.inflate(R.layout.definition_adapter_item, parent, false)
        return DefinitionItemHolder(inflatedView)
    }

    override fun getItemCount(): Int = definitions.size

    override fun onBindViewHolder(holder: DefinitionItemHolder, position: Int) {
        holder.bindDefinition(definitions[position])
    }
    class DefinitionItemHolder(view: View): RecyclerView.ViewHolder(view){
        var txtDate: TextView = view.findViewById(R.id.txtDate)
        var txtDefinition: TextView = view.findViewById(R.id.txtDefinition)
        var txtThumbsUp: TextView = view.findViewById(R.id.txtDefinition)
        var txtThumbsDown: TextView = view.findViewById(R.id.txtDefinition)
        var txtAuthor: TextView = view.findViewById(R.id.txtAuthor)
        var txtExample: TextView = view.findViewById(R.id.txtExample)

        fun bindDefinition(definition: UrbanDefinition){
            txtDate.text =  StringFormatter.convertTimestampToDayAndHourFormat(definition.writtenOn)
            txtDefinition.text = definition.definition
            txtThumbsUp.text = definition.thumbsUp.toString()
            txtThumbsDown.text = definition.thumbsDown.toString()
            txtAuthor.text = definition.author
            txtExample.text = definition.example
        }
    }
}