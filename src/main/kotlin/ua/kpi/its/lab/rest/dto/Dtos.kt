package ua.kpi.its.lab.rest.dto

data class MagazineRequest(
    var name: String,
    var topic: String,
    var language: String,
    var establishDate: String,
    var issn: Double,
    var price: String,
    var periodicity: Boolean,
    var scientificArticle: ScientificArticleRequest
)

data class MagazineResponse(
    var id: Long,
    var name: String,
    var topic: String,
    var language: String,
    var establishDate: String,
    var issn: Double,
    var price: String,
    var periodicity: Boolean,
    var scientificArticle: ScientificArticleResponse
)

data class ScientificArticleRequest(
    var title: String,
    var author: String,
    var writingDate: Int,
    var wordCount: String,
    var numberLinks: Double,
    var origLang: Boolean
)

data class ScientificArticleResponse(
    var id: Long,
    var title: String,
    var author: String,
    var writingDate: Int,
    var wordCount: String,
    var numberLinks: Double,
    var origLang: Boolean
)