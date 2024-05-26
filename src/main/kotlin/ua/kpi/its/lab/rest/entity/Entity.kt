package ua.kpi.its.lab.rest.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "magazines")
class Magazine(
    @Column
    var name: String,

    @Column
    var topic: String,

    @Column
    var language: String,

    @Column
    var establishDate: Date,

    @Column
    var issn: Double,

    @Column
    var price: BigDecimal,

    @Column
    var periodicity: Boolean,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "scientificArticles_id", referencedColumnName = "id")
    var scientificArticles: ScientificArticle
) : Comparable<Magazine> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1

    override fun compareTo(other: Magazine): Int {
        val equal = this.topic == other.topic && this.establishDate.time == other.establishDate.time
        return if (equal) 0 else 1
    }

    override fun toString(): String {
        return "Magazine(topic=$topic, establishDate=$establishDate, scientificArticles=$scientificArticles)"
    }
}

@Entity
@Table(name = "scientificArticles")
class ScientificArticle(
    @Column
    var title: String,

    @Column
    var author: String,

    @Column
    var writingDate: Int,

    @Column
    var wordCount: String,

    @Column
    var numberLinks: Double,

    @Column
    var origLang: Boolean,

    @OneToOne(mappedBy = "scientificArticles")
    var magazine: Magazine? = null,
): Comparable<ScientificArticle> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1

    override fun compareTo(other: ScientificArticle): Int {
        val equal = this.author == other.author && this.wordCount == other.wordCount
        return if (equal) 0 else 1
    }

    override fun toString(): String {
        return "ScientificArticle(author=$author, wordCount=$wordCount)"
    }
}
