## Unity로 배우는 C#

### < Part 6. ChildObject와 LocalPosition >

- **부모와 자식 컴포넌트**

  > 오브젝트가 같이 움직이기 위해서 상속관계를 부여

  - GameObject에 다른 GameObject를 삽입하여 부모 자식관계를 형성할 수 있다.
  - 자식은 부모의 속성과 스크립트에 영향을 받는다.

- **LocalPosition**

  > 부모 오브젝트에 대한 상대적인 위치
  - **Inspector**에서 부모 position을 변경해도 자식 position은 그대로 유지된다.
  
    - 자식 position은 부모 position에 상대적인 값을 의미하기 때문 (**LocalPosition**)
  
  - `localPosition`
  
    ```c#
    transform.position // GlobalPosition을 의미
    transform.localPosition // LocalPosition을 의미
    transform.localPosition = new Vector3(transform.localPosition.x,
                                          transform.localPosition.y,
                                          transform.localPosition.z);
    ```