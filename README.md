[ここ](https://kmp.jetbrains.com/)のwizardを利用して作成した基本を利用

[前まで作っていたRPG](https://github.com/sbkinoko/RPG_Engine)を作り直す

# ざっくり構成イメージ
## package単位
```mermaid
graph TD;
  Main --> GameScreen;
  Main --> Controller;
  subgraph GameScreen;
    Map;
    Menu;
    Battle;
  end;
  GameScreen --> Core;
  GameScreen --> Controller;
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
