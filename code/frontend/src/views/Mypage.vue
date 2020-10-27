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
          {{name}}
        </div>
        <v-btn icon><v-icon style="color: black; margin-left: 1%;">mdi-pencil</v-icon></v-btn>
      </div>
      <div style="height: 53%; border-bottom: 1px solid black; position: relative">
        캐릭터
        <v-btn class="changeBtn">캐릭터 바꾸기</v-btn>
      </div>
      <div style="padding: 4%; height: 40%;">
        <!-- 별 모음 -->
        <div style="height: 28%; display: flex; align-items: center">
          <!-- <div>
            <v-icon style="color: yellow; font-size: 3vw;">mdi-star</v-icon>
          </div> -->
          <div style="width: 100%">
            <div class="star onestar">
              <v-icon style="font-size: 1.6vw; color: yellow">mdi-star</v-icon>
              {{starpercent[0]}}%
            </div>
            <div class="star twostar">
              <v-icon v-for="i in 2" :key="i" style="font-size: 1.6vw; color: yellow">mdi-star</v-icon>
              {{starpercent[1]}}%
            </div>
            <div class="star threestar">
              <v-icon v-for="i in 3" :key="i" style="font-size: 1.6vw; color: yellow">mdi-star</v-icon>
              {{starpercent[2]}}%
            </div>
          </div>
        </div>
        <div style="height: 4%"></div>
        <!-- 칭호 -->
        <div style="height: 28%; display: flex; align-items: center;">
          <div style="display: inline-block; width: 10%; text-align: center;">
            <v-icon style="font-size: 4.5vh;">mdi-account</v-icon>
          </div>
          <div class="nicknameBox">
            <div class="nickname">
              <v-icon style="color: gold;">mdi-trophy-variant</v-icon>
              {{nickname}}
            </div>
          </div>
        </div>
        <!-- 출석 -->
        <div style="height: 40%; display: flex; align-items: center; justify-content: center;">
          <div class="attendBox">
            <v-icon style="font-size: 5vh; color: black;">mdi-calendar-multiple-check</v-icon>
            <div style="display: inline-block; margin-left: 3%;">출석</div>
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
      name: "코쿠멍",
      nickname: "코딩 어린이",
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
  methods:{
      apitest(){
        
        window.Kakao.API.request({
            url:'/v2/vision/face/detect',
            headers:{
              'Content-Type': 'application/json'
            },
            data : {
              image_url:'/img/김낙현.jpg',
              // KakaoAK:'a90caea0345c3be6cd63ed25b9c44977'
            },
            success : res => {
              console.log(res);
              },
            fail : error => {
                console.log(error);
            }
        })
      },
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
    font-size: 2.5vh;
    font-weight: 600;
    border-bottom: 1px solid black;
}
.changeBtn {
  position: absolute;
  top: 5%;
  right: 3%;
  color: white !important;
  font-size: 1.7vh !important;
  font-weight: 600;
  background-color: rgb(52, 85, 139) !important;
  padding: 3vh !important;
}
.star {
  display: inline-block;
  text-align: center;
  font-size: 1.2vw;
  font-weight: 600;
  padding: 1vh 0;
}
.onestar {
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  border-right: none;
  background: rgba(218, 138, 103, 0.5);
}
.twostar {
  background: silver;
}
.threestar {
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
  border-left: none;
  background: rgba(255, 215, 0, 0.5);
}
.nicknameBox {
  display: inline-block;
  width: 90%;
  text-align: center;
  padding: 1vh;
}
.nickname {
  padding: 1vh;
  border: 1px solid gray;
  border-radius: 15px;
  font-weight: 600;
}
.attendBox {
  padding: 1vh 6vh;
  border-radius: 30px;
  background-color:  lightsteelblue;;
  width: 30vw;
  text-align: center;
  font-size: 2.3vh; 
  font-weight: 600;
  cursor: pointer;
}
</style>