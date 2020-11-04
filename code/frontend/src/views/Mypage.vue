<template>
  <div class="mypage">
    <!-- 연습하기, 모험하기 -->
    <div class="leftbox">
      <div class="box" style=" height: 47%; position: relative; cursor: pointer;">
        <div style="width: 100%; height: 100%;">
          <img class="practiceImg" src="../assets/images/practice.png" alt="연습하기">
        </div>
        <div class="practiceTitle">
          연습하기
        </div>
      </div>
      <div style="height: 6%"></div>
      <div class="box" style="height: 47%; position: relative; cursor: pointer;">
        <div style="width: 100%; height: 100%;">
          <img class="practiceImg" src="../assets/images/practice.png" alt="모험하기">
        </div>
        <div class="practiceTitle" @click="moveGame">
          모험하기
        </div>
      </div>
    </div>
    <!-- 자신의 캐릭터 -->
    <div class="rightbox box">
      <!-- 캐릭터 이름 -->
      <div class="name" style="color:white;">
        <div>
          {{name}}
        </div>
        <v-btn icon @click="onRename"><v-icon class="pencilicon" style="color: white; margin-left: 1%;">mdi-pencil</v-icon></v-btn>
      </div>
      <!-- 캐릭터 이름 수정 -->
      <v-app class="vapp"></v-app>
      <v-dialog max-width="35%" min-height="50%" v-model="renamemodal">
        <v-card flat tile>
          <v-card-title style="justify-content: center; position: relative">
              <div style="font-size: 25px; font-family: 'BMJUA';">
                캐릭터 이름 변경
              </div>
              <div style="position: absolute; right: 3%; top: 22%;">
                <v-btn icon @click="renamemodal=false"><v-icon style="font-size: 28px;">mdi-close</v-icon></v-btn>
              </div>
          </v-card-title>
          <v-divider></v-divider>
          <v-card-text style="height:220px;" class="pa-1 modaltext">
            <div style="text-align: center;">
              <p style="font-size:16px; font-family: 'BMJUA';" class="my-2">새로운 캐릭터 이름을 적어주세요.</p>
              <p style="font-size:16px; font-family: 'BMJUA';" class="my-2"><span style="color: #64B5FF">공백</span>이나 <span style="color: #64B5FF">특수문자</span>를 사용할 수 없습니다.</p>
            </div>
            <div style="margin-top: 5%; width: 80%; height: 23%; display: flex; justify-content: center; position: relative">
              <input class="nameinput" type="text" v-model="newname">
              <div style="position: absolute; right: 12%; top: 10%;">
                <v-btn icon @click="delNewname"><v-icon style="font-size: 25px; color: black;">mdi-close</v-icon></v-btn>
              </div>
            </div>
          </v-card-text>
        </v-card>
        <v-card-actions style="background-color: white; justify-content:center; height: 10vh"> 
          <!-- style="background-color:rgb(22, 150, 245); height: 80%; width: 25%; font-size: 1.7vw;" -->
          <v-btn text color="black" @click="onNewName" class="confirmbtn" style="width: 75%; font-size: 20px">확인</v-btn>
        </v-card-actions>
      </v-dialog>
      <div style="height: 45vh; position: relative">
        <unity src="../unity2/Build/unity2.json" unityLoader="#"></unity>
        <router-link to="/changecharacter">
          <button class="changeBtn">캐릭터 바꾸기</button>
        </router-link>
      </div>
      <div style="padding: 4%; height: 40%;">
        <!-- 별 모음 -->
        <div style="height: 28%; display: flex; align-items: center">
          <!-- <div>
            <v-icon style="color: yellow; font-size: 3vw;">mdi-star</v-icon>
          </div> -->
          <div style="width: 100%">
            <div style="float: left; width: 10%; text-align: center;">
              <v-icon style="font-size: 35px; color: yellow;">mdi-star</v-icon>
            </div>
            <div class="starbar">
              <div class="mystar"></div>
              <div class="startotal"></div>
              <div class="starnum"><span>{{startCount}} / 90</span></div>
            </div>
            <!-- <div class="star onestar">
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
            </div> -->
          </div>
        </div>
        <div style="height: 4%"></div>
        <!-- 칭호 -->
        <div style="height: 28%; display: flex; align-items: center;">
          <div style="display: inline-block; width: 10%; text-align: center;">
            <v-icon style="font-size: 4.5vh; color: white">mdi-account</v-icon>
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
            <v-icon style="font-size: 5vh; color: white;">mdi-calendar-multiple-check</v-icon>
            <!-- <div class="icon">
              <div class="calendar">
                25
                <div class="holes"></div>
                <div class="flip"></div>
              </div>
            </div> -->
            <div style="display: inline-block; margin-left: 3%; font-size: 25px; color: white">출석</div>
          </div>
          <v-dialog max-width="90vw" min-height="90vh" v-model="attendmodal">
            <v-card flat tile style="height: 80vh;">
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
                    <img class="ribbonimg" src="../assets/images/red-ribbon4.png" alt="">
                    <div class="itemtitle">보상 아이템</div>
                    <div class="itemimg">
                    </div>
                    <div class="itemname">황금 모자</div>
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
                            <v-icon v-if="attendDay.includes((i-1)*7 + j)" style="font-size: 50px; color: red">mdi-check-circle-outline</v-icon>
                            <!-- <v-icon v-if="noattendDay.includes((i-1)*7 + j)" style="font-size: 50px;">mdi-check-circle-outline</v-icon> -->
                            <v-icon v-else-if="today == ((i-1)*7 + j)" style="font-size: 50px;" @click="attendCheck">mdi-check-circle-outline</v-icon>
                            <!-- <v-icon v-else style="font-size: 50px;">mdi-check-circle-outline</v-icon> -->
                          </div>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </v-card>
            <v-card-actions style="background-color: white; justify-content:center; height: 10vh"> 
              <v-btn text color="black" @click="attendmodal=false" class="confirmbtn" style="width: 30%; font-size: 25px">확인</v-btn>
            </v-card-actions>
          </v-dialog>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'
import "../css/mypage.scss";
import $ from 'jquery';
import store from '../vuex/store'
import axios from 'axios'
import Swal from 'sweetalert2'

export default {
  data() {
    return {
      // star: [1, 3, 6],
      star: 10,
      starpercent: [],
      name: null,
      newname: "",
      renamemodal: false,
      nickname: "코딩 어린이",
      attendmodal: false,
      attendDay: [],
      noattendDay: [],
      totalAttendDay: '',
      startCount:'',
      today: 5,
      nMonth:11,
      isAttend: false
    }
  },
  components: {
    Unity
  },

  mounted() {
    console.log(this.name);
    // 별 총합 계산
    // var totalstar = 0;
    // for(var i=0; i<this.star.length; i++){
    //   totalstar += this.star[i]
    // }
    // 별 비율 계산
    // for(var j=0; j<this.star.length; j++){
    //   var star = this.star[j]
    //   var number = star / totalstar
    //   this.starpercent.push(number.toFixed(2)*100)
    // }
    // 별의 갯수에 따라 width 설정
    const starratio = (this.star / 90) * 100
    $(".mystar").css("width", `${starratio}%`)
    $(".startotal").css("width", `${100 - starratio}%`)
  },
  methods: {
    onRename(){
      this.renamemodal = !this.renamemodal
    },
    delNewname() {
      this.newname = "";
    },
    onNewName() {
      if(this.newname.length > 0) {
        this.name = this.newname
        this.newname = ''
        this.renamemodal = false
      }

      axios.put(`https://k3b102.p.ssafy.io:9999/cobit/user`,{
        email : store.state.kakaoUserInfo.email,
        nickname : this.name
      }).then(res => {
        console.log(res);
        store.state.kakaoUserInfo.nickname = this.name;
      });
      
    },
    onAttend() {
      this.attendmodal = !this.attendmodal
    },
    attendCheck(){
      if(!this.isAttend){
        this.isAttend = true;
        axios.post(`https://k3b102.p.ssafy.io:9999/cobit/user/attend`,{
          email : store.state.kakaoUserInfo.email,
          day : this.today,
          month : this.nMonth,
        }).then(()=>{
          Swal.fire(
            '출석완료!',
            '',
            'success'
          )
          this.loadAttend();
        })
      }
    },
    loadAttend(){
      // 출석 정보 가져오기
      var date = new Date();
      axios.get(`https://k3b102.p.ssafy.io:9999/cobit/user/attend`,{
        params : {
          email : store.state.kakaoUserInfo.email,
          month : date.getMonth()+1
        }
      }).then(res=>{
        console.log(res);
        this.attendDay = [];
        res.data.forEach(d => {
          this.attendDay.push(d.day);
        });
        // 총 출석일 계산
        this.totalAttendDay = this.attendDay.length;
        var date = new Date();
        this.today = date.getDate();
        this.nMonth = date.getMonth() +1;
      })
    },
    moveGame(){
      this.$router.push('/gamemap');
    }
  },
  created(){
    if(this.$cookies.isKey("access_token")){
      console.log("로그인")
      window.Kakao.API.request({
          url:'/v2/user/me',
          success : res => {
              const kakao_account = res.kakao_account;
              axios.get(`https://k3b102.p.ssafy.io:9999/cobit/user?email=${kakao_account.email}`)
              .then(res => {
                console.log(res);
                this.$store.commit('setKakaoUserInfo', res.data);
                this.name = store.state.kakaoUserInfo.nickname;
                this.startCount = store.state.kakaoUserInfo.star;
                this.loadAttend();
              })
          },
      })
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
  padding: 2%;
  height: 100vh;
  font-family: 'BMJUA';
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
  font-size: 30px;
  font-weight: 900;
  font-family: 'BMJUA';
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}
.name {
  color: white;
  height: 7vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 28px;
  /* font-weight: 600; */
  font-family: 'BMJUA';
  border-bottom: 1px solid black;
}
.pencilicon:hover {
  color: #a4d4ff;
}
.confirmbtn {
  height: 75% !important;
  /* font-size: 20px !important; */
  font-family: BMJUA;
  background-color: #a4d4ff !important;
  border-radius: 30px;
  transition: box-shadow .3s ease;
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2),
              -6px -6px 10px -1px #ffffff;
}
.confirmbtn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px rgba(255, 255, 255, 0.5) !important;
}
.changeBtn {
  position: absolute;
  top: 5%;
  right: 3%;
  color: black !important;
  font-size: 20px;
  font-weight: 600;
  font-family: BMJUA;
  background-color: rgba(164, 212, 255, 0.7) !important;
  padding: 2.5vh !important;
  border-radius: 10px;
}
.changeBtn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px rgba(255, 255, 255, 0.5) !important;
}
.starbar {
  display: inline-block;
  width: 88%;
  /* background: white; */
  border-radius: 15px;
  /* text-align: center; */
  font-size: 25px;
  font-weight: 600;
  /* margin: 1%; */
  /* padding: 1%; */
  color: black;
  position: relative;
}
.mystar {
  height: 5vh;
  display: inline-block;
  background: #a4d4ff;
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
}
.startotal {
  height: 5vh;
  display: inline-block;
  background-color: white;
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
}
.starnum {
  display: inline-block;
  position: absolute;
  top: 5px;
  left: 45%;
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
  border: 1px solid white;
  border-radius: 15px;
  font-weight: 600;
  color: white;
}
.attendBox {
  padding: 1vh 6vh;
  border-radius: 30px;
  /* background-color:  lightsteelblue; */
  width: 30vw;
  text-align: center;
  /* font-size: 2.3vh;  */
  /* font-weight: 600; */
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
  border-radius: 30px;
  width: 80%; 
  height: 90%; 
  text-align: center;
  font-family: 'BMJUA';
  font-size: 18px;
}
.attendtitle {
  height: 8%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 30px;
  margin: 0 5%;
  /* background: linear-gradient(to right, white 1%, #34558b 10%, #34558b 50%, #34558b 90%, white 99%);
  color: white; */
  font-weight: 600;
  font-family: 'BMJUA';
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
  font-family: 'BMJUA';
}
.attendright {
  float: left;
  height: 100%;
  margin-left: 2%;
  width: 73%;
  box-sizing: border-box;
  font-family: 'BMJUA';
  /* border: 1px solid gray; */
}
.attenditemBox {
  border: 1px solid gray;
  height: 65%;
  border-radius: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
}
td {
  border: 1px solid gray;
}
.attendtext {
  padding: 3%;
  height: 27%;
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
.ribbonimg {
  width: 260px;
  position: absolute;
  top: -75px;
}
.itemimg {
  width: 65%;
  height: 40%;
  background: url("../assets/images/Hat3.png");
  background-repeat: no-repeat;
  background-size: contain;
}
.itemtitle {
  position: absolute;
  font-size: 25px;
  font-family: 'BMJUA';
  top: -15px;
  color: gold;
}
.itemname {
  position: absolute;
  font-size: 25px;
  font-family: 'BMJUA';
  bottom: 25px;
}
</style>