package devpaul.business.piensarapido.model

class User(
    var name: String,
    var lastname: String,
    val email: String,
    val password: String
) {
    override fun toString(): String {
        return "$name $lastname"
    }
}