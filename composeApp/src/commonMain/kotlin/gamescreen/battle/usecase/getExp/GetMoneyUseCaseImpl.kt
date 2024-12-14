package gamescreen.battle.usecase.getExp

class GetExpUseCaseImpl : GetExpUseCase {
    override fun invoke(): Int {
        // todo モンスターによって入手する経験値を変えられるようにする
        return 10
    }
}
