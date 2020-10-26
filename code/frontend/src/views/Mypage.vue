<template>
  <div class="mypage">
    <!-- 연습하기, 모험하기 -->
    <div class="leftbox">
      <div class="box" style=" height: 47%; position: relative">
        <div style="width: 100%; height: 100%;">
          <img class="practiceImg" src="../image/practice.png" alt="연습하기">
        </div>
        <div class="practiceTitle">
          연습하기
        </div>
      </div>
      <div style="height: 6%"></div>
      <div class="box" style="height: 47%; position: relative">
        <div style="width: 100%; height: 100%;">
          <img class="practiceImg" src="../image/practice.png" alt="모험하기">
        </div>
        <div class="practiceTitle">
          모험하기
        </div>
      </div>
    </div>
    <!-- 자신의 캐릭터 -->
    <div class="rightbox box">
      <div>
        코쿠멍
      </div>
      캐릭터
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.mypage {
  display: flex;
  padding: 3%;
  height: 100vh;
  font-family: 'Oswald', sans-serif;
  /* background: #34558b; */
}
.leftbox {
  width: 35vw;
  margin: 3%;
}
.rightbox {
  width: 65vw;
  margin: 3%;
}
.box {
  border: 3px solid black; 
  border-radius: 10px;
  cursor: pointer;
}
.practiceImg {
  width: 100%;
  height: 100%;
  border-radius: 7px;
}
.practiceTitle {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 25%;
  background: rgba(255, 255, 255, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2vw;
  font-weight: 900;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}
</style>