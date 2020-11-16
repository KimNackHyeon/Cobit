## Unity로 배우는 C#

### < Part 3. Script >

- **Visual Studio 모듈 추가 방법**
  
  1. Unity Hub 실행
  2. **설치** 카테고리 선택
  3. 버전 클릭 후 **모듈 추가** 선택
  4. Microsoft Visual Studio Community 체크
5. 설치
  
- **Visual Studio 자동완성 추가 방법**

  1. Unity 열기
  2. Unity 에서 Edit -> Preferences -> External Tools
  3. External Script Editor 를 Visual Studio 2019 로 변경

- **변수 종류**

  ```c#
  int age = 10;
  float height = 10.1f;
  string myName = "Nack";
  ```

- **Class 함수**
  
  - `Start()` : 게임이 시작되면 한 번 실행
  - `Update()` : 매 프레임마다 실행
- `Debug.Log()` : Console창에 로그를 찍는 함수
  
- **TransForm Position**

  ```c#
  transform.position = new Vector3(x, y, z); // 벡터 생성
  transform.position.x // 현재 위치의 x 값
  transform.position.x // 현재 위치의 y 값
  transform.position.x // 현재 위치의 z 값
  ```

- **GetComponent**

  > GameObject 가져오기 위한 함수

  ```c#
  GetComponent<component이름>();
  ```

-  **GameObject**

  > GameObject 클래스

  - `GameObject.Find("name")` : GameObject를 가져오는 함수