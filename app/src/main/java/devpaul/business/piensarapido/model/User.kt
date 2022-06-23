package devpaul.business.piensarapido.model

class User(
    var userId: String? = null,
    var name: String? = null,
    var lastname: String? = null,
    var rol: String? = null,
    val email: String? = null,
    val password: String? = null,
    val created: String? = null

) {
    override fun toString(): String {
        return "$name $lastname"
    }
}