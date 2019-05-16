package kristi.heroofmythhaven

interface CharacterAttributes {
    var hp: Int
    var damage: Int
    fun dealDamage(character: CharacterAttributes): Int
}