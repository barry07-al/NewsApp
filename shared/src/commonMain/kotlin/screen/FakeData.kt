package screen

import dataclass.Article
import dataclass.Source
import response.ArticleResponse


fun fakeData(): ArticleResponse {
    val source = Source("example", "Example Source")

    val sampleArticles = listOf(
        Article(source, "Author Name", "Article Title", "Description of the article", "https://www.example.com", "https://www.floridatoday.com/gcdn/presto/2020/02/05/PBRE/3d4c2618-f6eb-4edf-84d2-36f38d352a12-PACE.jpg?crop=1019,574,x4,y0&width=1019&height=574&format=pjpg&auto=webp", "2024-01-27", "This is the full content of the article..."),
        Article(
            source = source,
            author = "John Doe",
            title = "Example Title",
            description = "description example",
            url = "https://example.com",
            urlToImage = "https://media.wired.com/photos/6596014c5f711855ff10a7cd/191:100/w_1280,c_limit/Boston-Dynamics-AI-Institute-Spot-Robot-Business-1238829150.jpg",
            publishedAt = "2024-01-27",
            content = "This is the full content of the article..."
        ),
        Article(
            source = Source(id = "techcrunch", name = "TechCrunch"),
            author = "John Doe",
            title = "Innovations en IA : Vers un futur automatisé",
            description = "L'article explore les dernières avancées en intelligence artificielle et leur potentiel pour transformer les industries.",
            url = "https://example.com/article",
            urlToImage = "https://www.floridatoday.com/gcdn/presto/2020/02/05/PBRE/3d4c2618-f6eb-4edf-84d2-36f38d352a12-PACE.jpg?crop=1019,574,x4,y0&width=1019&height=574&format=pjpg&auto=webp",
            publishedAt = "2024-01-27T11:00:00Z",
            content = """
        Les progrès récents en intelligence artificielle (IA) promettent de révolutionner le monde tel que nous le connaissons. Des chercheurs du monde entier travaillent sur des algorithmes capables d'apprendre de manière autonome, ouvrant la voie à des applications inédites dans presque tous les secteurs d'activité.

        De la santé, où des diagnostics précis peuvent être posés en quelques secondes, à l'automobile, avec le développement des véhicules autonomes, l'IA a le potentiel de rendre notre vie quotidienne à la fois plus sûre et plus facile. Mais ces avancées soulèvent également des questions importantes concernant la sécurité des données, l'éthique et l'emploi.

        Cet article examine où nous en sommes aujourd'hui avec l'IA, où nous pourrions être demain, et comment nous pouvons naviguer de manière responsable vers ce futur prometteur.
    """.trimIndent()
        )
    )
    return ArticleResponse("ok", 2, sampleArticles)
}