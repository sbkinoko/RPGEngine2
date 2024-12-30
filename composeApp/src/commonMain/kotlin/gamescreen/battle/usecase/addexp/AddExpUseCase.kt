package gamescreen.battle.usecase.addexp

interface AddExpUseCase {

    operator fun invoke(exp: Int): List<String>
}
