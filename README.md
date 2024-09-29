[ここ](https://kmp.jetbrains.com/)のwizardを利用して作成した基本を利用

[前まで作っていたRPG](https://github.com/sbkinoko/RPG_Engine)を作り直す

# ざっくり構成イメージ
## package単位
```mermaid
graph TD;
  A-->B;
  A-->C;
  B-->D;
  B-->E;
  B-->F;
  D-->G;
  E-->G;
  F-->G;
  A-->G;
  A[Main];
  B[GameScreen];
  C[Controller];
  D[Map];
  E[Menu];
  F[Battle];
  G[Core];
```

## 各画面のレイヤ構成

```mermaid
graph TD;
  A-->B;
  A-->D;
  B-->C;
  B-->D;
  C-->D;
  A[View];
  B[ViewModel];
  C[UseCase];
  D[Repository];
```
