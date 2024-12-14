package gamescreen.battle.usecase.getmoney

import core.repository.money.MoneyRepository

class GetMoneyUseCaseImpl(
    private val moneyRepository: MoneyRepository,
) : GetMoneyUseCase {
    override fun invoke() {
        // todo モンスターによって入手する金額を変えられるようにする
        moneyRepository.addMoney(10)
    }
}
