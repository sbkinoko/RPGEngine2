package gamescreen.battle.usecase.getmoney

class GetMoneyUseCaseImpl : GetMoneyUseCase {
    override fun invoke(): Int {
        // todo モンスターによって入手する金額を変えられるようにする
        return 10
    }
}
