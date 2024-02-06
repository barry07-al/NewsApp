package response

import dataclass.SourceData

data class SourceResponse(
    val status: String,
    val sources: List<SourceData>
)
