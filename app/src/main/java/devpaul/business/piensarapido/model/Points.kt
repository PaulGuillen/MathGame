package devpaul.business.piensarapido.model

class Points(
    var userId: String,
    var name: String,
    var lastname: String,
    var bestPoints: String,
    var lastTry: String,
    var lastTimePlayed: String,
    var lasTimeAcces: String,
    var incorrectAnswers: Int? = null,
    var numberofQuestions: Int? = null,
    var correctAnswers: Int? = null,
    var timePlayed: Int? = null,
    var type : String? = null,
    var level : String? = null
)