## Unity로 배우는 C#

### < Part 9. SendMessage >

- GameManager

  - `SendMessage`

    **GameManager**에 있는 함수를 호출

    ```csharp
    GameObject.Find("GameManager").SendMessage("함수명");
    ```
  
  - 전반적인 control과 관련된 부분을 한 곳에 모아두어야 좋음
  - SendMessage를 이용하여 발생한 사건을 GameManager로 알려주고 실제 처리는 GameManager에서 하는 것이 좋음 