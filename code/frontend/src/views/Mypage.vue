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
      <!-- 캐릭터 이름 -->
      <div class="name">
        <div>
          {{name}}
        </div>
        <v-btn icon @click="onRename"><v-icon style="color: black; margin-left: 1%;">mdi-pencil</v-icon></v-btn>
      </div>
      <!-- 캐릭터 이름 수정 -->
      <v-app class="vapp"></v-app>
      <v-dialog max-width="50%" min-height="30%" v-model="renamemodal">
        <v-card flat tile>
          <v-card-title style="justify-content: center; position: relative">
              <div style="font-size: 2vw">
                캐릭터 이름 변경
              </div>
              <div style="position: absolute; right: 3%; top: 22%;">
                <v-btn icon @click="renamemodal=false"><v-icon style="font-size: 3vw;">mdi-close</v-icon></v-btn>
              </div>
          </v-card-title>
          <v-divider></v-divider>
          <v-card-text style="height:220px;" class="pa-1 modaltext">
            <div style="text-align: center;">
              <p style="font-size:1.6vw" class="my-2">새로운 캐릭터 이름을 적어주세요.</p>
              <p style="font-size:1.6vw" class="my-2"><strong>공백</strong>이나 <strong>특수문자</strong>를 사용할 수 없습니다.</p>
            </div>
            <div style="margin-top: 5%; width: 80%; height: 23%;">
              <input class="nameinput" type="text" v-model="newname" style="">
            </div>
          </v-card-text>
        </v-card>
        <v-card-actions style="background-color: white; justify-content:center; height: 10vh"> 
          <v-btn text color="white" @click="onNewName" style="background-color:rgb(22, 150, 245); height: 80%; width: 25%; font-size: 1.7vw;">확인</v-btn>
        </v-card-actions>
      </v-dialog>
      <div style="height: 53%; border-bottom: 1px solid black; position: relative">
        <!-- <unity src="../unity2/Build/unity2.json" unityLoader="#"></unity> -->
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
          <div class="attendBox" @click="onAttend">
            <v-icon style="font-size: 5vh; color: black;">mdi-calendar-multiple-check</v-icon>
            <!-- <div class="icon">
              <div class="calendar">
                25
                <div class="holes"></div>
                <div class="flip"></div>
              </div>
            </div> -->
            <div style="display: inline-block; margin-left: 3%;">출석</div>
          </div>
          <v-dialog max-width="90vw" min-height="90vh" v-model="attendmodal">
            <v-card flat tile style="height: 80vh">
              <div style="height: 3%"></div>
              <!-- 제목 -->
              <div class="attendtitle">
                매일 매일 출석 체크
              </div>
              <div style="height: 85%; padding: 2vw 4vw;">
                <!-- 왼쪽 박스 -->
                <div class="attendleft">
                  <!-- 출석일수 -->
                  <div class="attendnumBox">
                    <div class="attendtext">
                      출석일수
                    </div>
                    <div class="attendnum">
                      {{totalAttendDay}}일
                    </div>
                  </div>
                  <div style="height: 5%"></div>
                  <!-- 보상 아이템 -->
                  <div class="attenditemBox">
                    <div class="itemimg">
                    </div>
                  </div>
                </div>
                <!-- 오른쪽 박스 -->
                <div class="attendright">
                  <table style="text-align: center; border-collapse: collapse; height: 100%">
                    <tbody>
                      <tr v-for="i in 4" :key="i" style="height: 25%">
                        <td v-for="j in 7" :key="j" style="width: 10vw">
                          <div style="height: 25%; border-bottom: 1px solid gray">
                            {{(i-1)*7 + j}}
                          </div>
                          <div style="height: 75%; display: flex; justify-content: center;">
                            <v-icon v-if="attendDay.includes((i-1)*7 + j)" style="font-size: 7vh; color: red">mdi-check-circle-outline</v-icon>
                            <v-icon v-if="noattendDay.includes((i-1)*7 + j)" style="font-size: 7vh;">mdi-check-circle-outline</v-icon>
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </v-card>
            <v-card-actions style="background-color: white; justify-content:center; height: 10vh"> 
              <v-btn text color="white" @click="attendmodal=false" style="background-color:rgb(22, 150, 245); height: 80%; width: 25%; font-size: 1.7vw;">확인</v-btn>
            </v-card-actions>
          </v-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// import Unity from 'vue-unity-webgl'
import "../css/mypage.scss";
import $ from 'jquery';

export default {
  data() {
    return {
      star: [1, 3, 6],
      starpercent: [],
      name: "코쿠멍",
      newname: "",
      renamemodal: false,
      nickname: "코딩 어린이",
      attendmodal: false,
      attendDay: [1, 3, 4],
      noattendDay: [],
      totalAttendDay: '',
    }
  },
  // components: {
  //   Unity
  // },
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
    // 총 출석일 계산
    this.totalAttendDay = this.attendDay.length;
    // 출석체크 도장
    let today = new Date();
    let date = today.getDate();
    console.log(date)
    for (var k=1; k<29; k++){
      if (!this.attendDay.includes(k) && k<date) {
        this.noattendDay.push(k)
      }
    }
    console.log(this.noattendDay)
    // for(var k=1; k<29; k++){
    //   if(this.attendDay.includes(k)) {
    //     console.log(`.check${k}`)
    //     $(`.check${k}`).addClass("active")
    //   }
    //   else {
    //     // $(`.check${k}`).css("color", "unset");
    //   }
    // }
  },
  methods: {
    onRename(){
      this.renamemodal = !this.renamemodal
    },
    onNewName() {
      if(this.newname.length > 0) {
        this.name = this.newname
        this.newname = ''
        this.renamemodal = false
      }
      
    },
    onAttend() {
      this.attendmodal = !this.attendmodal
    }
  }

}
</script>

<style scoped>
.active {
  color: red !important;
}
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
  /* background-color:  lightsteelblue; */
  width: 30vw;
  text-align: center;
  font-size: 2.3vh; 
  font-weight: 600;
  cursor: pointer;
}
.modaltext {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.nameinput {
  border: 1px solid black;
  border-radius: 10px;
  width: 100%; 
  height: 100%; 
  text-align: center;
  font-size: 1.8vw;
}
.attendtitle {
  height: 8%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2.5vw;
  margin: 0 5%;
  /* background: linear-gradient(to right, white 1%, #34558b 10%, #34558b 50%, #34558b 90%, white 99%);
  color: white; */
  font-weight: 600;
}
.attendleft {
  float: left;
  height: 100%;
  width: 25%;
}
.attendnumBox {
  border: 1px solid gray;
  border-radius: 5px;
  text-align: center;
  height: 30%;
  font-size: 2.5vh;
}
.attendright {
  float: left;
  height: 100%;
  margin-left: 2%;
  width: 73%;
  box-sizing: border-box;
  /* border: 1px solid gray; */
}
.attenditemBox {
  border: 1px solid gray;
  height: 65%;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
}
td {
  border: 1px solid gray;
}
.attendtext {
  padding: 3%;
  height: 25%;
  border-bottom: 1px solid gray;
}
.attendnum {
  margin: 3%;
  height: 70%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 5vh;
}
.itemimg {
  width: 90%;
  height: 40%;
  background: url("../image/Hat3.png");
  background-repeat: no-repeat;
  background-size: contain;
}
</style>