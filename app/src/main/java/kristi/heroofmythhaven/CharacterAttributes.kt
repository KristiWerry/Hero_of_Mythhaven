package kristi.heroofmythhaven

interface CharacterAttributes {
    var hp: Int // Character health points
    var damage: Int // Damage dealt by the character

    // Should return the input characters remaining health after damage has been dealt
    fun dealDamage(character: CharacterAttributes): Int
}