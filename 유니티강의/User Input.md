## Unity로 배우는 C#

### < Part 3. User Input >

- **키보드 입력**
  - **Input.GetAxis("Horizontal or Vertical")**

    > 사용자로 부터 입력 받기

    - `Horizontal` : 좌우
      - 오른쪽 방향키( :arrow_right: ) : 1
      - 왼쪽 방향키( :arrow_left: ) : -1
    - `Vertical` :  상하
      - 위쪽 방향키( :arrow_up: ) : 1
      - 아래쪽 방향키( :arrow_down: ) : -1

  - **Input.GetKeyDown(KeyCode)**

    > 원하는 키를 입력했을 때 작동하는 함수

  - **Input.GetKeyUp(KeyCode)**

    > 원하는 키를 떼었을 때 작동하는 함수

  - **Input.GetKey(KeyCode)**

    > 원하는 키를 누르고있을 동안 작동하는 함수

- **마우스 입력**

  - **Input.touchCount**

    > 클릭 횟수

  - **Input.GetMouseButton(num)**

    > 마우스 클릭하는 동안 작동하는 함수

  - **Input.GetMouseButtonDown(num)**

    > 마우스를 클릭했을 때 작동하는 함수

  - **Input.GetMouseButtonUp(num)**

    > 마우스를 떼었을 때 작동하는 함수

    - `num = 0` : 마우스 왼쪽 버튼

    - `num = 1` : 마우스 오른쪽 버튼
  - `num = 2` : 마우스 휠 버튼
  
- **Input.mousePosition**
  
  > 마우스 클릭 위치