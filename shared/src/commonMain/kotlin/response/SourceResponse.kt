package response

import dataclass.SourceData

@kotlinx.serialization.Serializable
data class SourceResponse(
    val status: String,
    val sources: List<SourceData>
)
