# **🤙 프로젝트명 ( 미정 )**

- 팀명 : (B102)
- 팀원 소개

    팀장 : 김낙현(BE)

    팀원 : 권남선(FE), 류혜명(FE), 박건호(BE), 박종준(FE)

- 역할
    - Tech Leader -
    - QA -
    - 개발자 -
    - 기획자 -

## **☝ 주제**

- 프로젝트명 :

## **✌ 기술스택**

- Frontend

    Vue.js, Vuetify, Unity

- Backend

    Spring

## **🖐 기능**

- 

## **🍎 개발규칙**

### **공통**

- 특수문자는 _ 만 허용한다.
- ex) Is_Select(클래스), get_Value(함수), is_Select(변수)

### **클래스명**

- 클래스명은 대문자의 명사로 시작한다.
- ex) IsSelect

### **함수명**

- 소문자의 동사로 시작한다.
- ex) getValue

### **변수명**

- 소문자로 시작하며 여러 단어로 이루어진 경우 각 단어의 첫글자를 대문자로 한다.(카멜표기법)
- ex) isSelect

## **⭐️ Git 규칙**

**branch**

```
master -> develop -> feature/fe(권남선, 류혜명, 박종준) or feature/be(김낙현, 박건호)
```

**merge**

```
- 코딩하기 전 develop 가져오기
git checkout develop 
git pull origin develop

- merge 하기 전 서로 코드 리뷰하기
- merge 후 branch 지우기
```

**commit**

```
1 day 1 commit
커밋 메시지는 => 이슈번호 /(Add/Updata/Delete) 작업 내용 <= 의 구조로 작성
ex) S123301 /Add Login
이슈번호가 없을경우 None 으로 대체
```