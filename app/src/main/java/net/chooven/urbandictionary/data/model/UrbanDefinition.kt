package net.chooven.urbandictionary.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Definition from Urban Dictionary API (term)
 */
class UrbanDefinition {

    /**
     * Text definition of term
     * @return Definition
     */
    @SerializedName("definition")
    var definition: String = ""

    /**
     * Int Number of Thumbs Up
     * @return Thumbs Up count
     */
    @SerializedName("thumbs_up")
    var thumbsUp: Int = 0

    /**
     * Int Number of Thumbs Down
     * @return Thumbs Down count
     */
    @SerializedName("thumbs_down")
    var thumbsDown: Int = 0


    /**
     * String Author name
     * @return Author Name
     */
    @SerializedName("author")
    var author: String = ""

    /**
     * String searched for
     * @return Term defined
     */
    @SerializedName("word")
    var term: String = ""

    /**
     * Int definition id
     * @return Def Id
     */
    @SerializedName("defid")
    var defId: Int = 0

    /**
     * Date definition written on
     * @return Date of Definition
     */
    @SerializedName("written_on")
    var writtenOn: Date? = null

    /**
     * Sting Use of term
     * @return Usage of term
     */
    @SerializedName("example")
    var example: String = ""

}