package net.chooven.urbandictionary.data.model

import com.google.gson.annotations.SerializedName


/**
 * Response from Urban Dictionary API [https://mashape-community-urban-dictionary.p.rapidapi.com]
 */
data class UrbanDictResponse(

    /**
     * List of Definitions
     * @return Definitions
     */
    @SerializedName("list")
    var definitions: List<UrbanDefinition> = listOf()

)