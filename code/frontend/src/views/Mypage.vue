<template>
  <div class="mypage">
    <!-- 연습하기, 모험하기 -->
    <div class="leftbox">
      <div class="box" style=" height: 47%; position: relative; cursor: pointer;">
        <div style="width: 100%; height: 100%;">
          <img class="practiceImg" src="../image/practice.png" alt="연습하기">
        </div>
        <div class="practiceTitle">
          연습하기
        </div>
      </div>
      <div style="height: 6%"></div>
      <div class="box" style="height: 47%; position: relative; cursor: pointer;">
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
      <div class="name">
        <div>
          코쿠멍
        </div>
        <v-btn icon><v-icon style="color: black; margin-left: 1%;">mdi-pencil-circle-outline</v-icon></v-btn>
      </div>
      <div style="height: 53%; border-bottom: 1px solid black; position: relative">
        캐릭터
        <v-btn class="changeBtn">캐릭터 바꾸기</v-btn>
      </div>
      <div style="padding: 5%; height: 40%;">
        <!-- 별 모음 -->
        <div style="height: 30%">
          <!-- <div>
            <v-icon style="color: yellow; font-size: 3vw;">mdi-star</v-icon>
          </div> -->
          <div>
            <div class="onestar">
              <v-icon style="font-size: 1.6vw; color: yellow">mdi-star</v-icon>
              {{starpercent[0]}}%
            </div>
            <div class="twostar">
              <v-icon v-for="i in 2" :key="i" style="font-size: 1.6vw; color: yellow">mdi-star</v-icon>
              {{starpercent[1]}}%
            </div>
            <div class="threestar">
              <v-icon v-for="i in 3" :key="i" style="font-size: 1.6vw; color: yellow">mdi-star</v-icon>
              {{starpercent[2]}}%
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery';

export default {
  data() {
    return {
      star: [1, 3, 6],
      starpercent: [],
    }
  },
  mounted() {
    // 별 총합 계산
    var totalstar = 0;
    for(var i=0; i<this.star.length; i++){
      totalstar += this.star[i]
    }
    // 별 비율 계산
    for(var j=0; j<this.star.length; j++){
      var star = this.star[j]
      var number = star / totalstar
      this.starpercent.push(number.toFixed(2)*100)
    }
    // 별의 갯수에 따라 width 설정
    $(".onestar").css("width", `${this.starpercent[0]}%`)
    $(".twostar").css("width", `${this.starpercent[1]}%`)
    $(".threestar").css("width", `${this.starpercent[2]}%`)
    console.log(this.starpercent)
  },

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
.name {
    height: 7%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 1.5vw;
    font-weight: 600;
    border-bottom: 1px solid black;
}
.changeBtn {
  position: absolute;
  top: 5%;
  right: 3%;
}
.onestar {
  display: inline-block;
  text-align: center;
  font-size: 1.2vw;
  padding: 1vh 0;
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  border-right: none;
  background: rgba(218, 138, 103, 0.5);
}
.twostar {
  display: inline-block;
  text-align: center;
  font-size: 1.2vw;
  padding: 1vh 0px;
  background: silver;
}
.threestar {
  display: inline-block;
  text-align: center;
  font-size: 1.2vw;
  padding: 1vh 0px;
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
  border-left: none;
  background: rgba(255, 215, 0, 0.5);
}
</style>