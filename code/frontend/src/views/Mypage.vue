<template>
  <div class="mypage">
    <!-- 연습하기, 모험하기 -->
    <div class="leftbox">
      <div class="box" style=" height: 47%; position: relative; cursor: pointer;" @click="movePractice">
        <div style="width: 100%; height: 100%;">
          <img class="practiceImg" src="../assets/images/practice.png" alt="연습하기">
        </div>
        <div class="practiceTitle">
          연습하기
        </div>
      </div>
      <div style="height: 6%"></div>
      <div class="box" style="height: 47%; position: relative; cursor: pointer;" @click="moveGame">
        <div style="width: 100%; height: 100%;">
          <img class="practiceImg" src="../assets/images/adventure.png" alt="모험하기">
        </div>
        <div class="practiceTitle">
          모험하기
        </div>
      </div>
    </div>
    <!-- 자신의 캐릭터 -->
    <div class="rightbox box">
      <!-- 캐릭터 이름 -->
      <div class="name" style="color:white;">
        <div v-if="name">
          {{name}}
        </div>
        <div v-else>
          손님
        </div>
        <v-btn v-if="userEmail!=null" icon @click="onRename"><v-icon class="pencilicon" style="color: white; margin-left: 1%;">mdi-pencil</v-icon></v-btn>
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
        <unity src="../unity2/Build/unity2.json" unityLoader="#" ref="myInstance" :hideFooter="true"></unity>
        <!-- <router-link to="/changecharacter"> -->
        <button @click="goChantecharacter" :disabled="userEmail==null" class="changeBtn">캐릭터 바꾸기</button>
        <div v-if="userEmail==null" class="nouserbtn">
          <i class="fas fa-lock nouserbtnicon"></i>
        </div>
        <!-- </router-link> -->
      </div>
      <div class="nouser" v-if="userEmail==null" style="padding: 4%; height: 38.8%;">
        <div>
          <i class="fas fa-lock lockicon"></i>
        </div>
        <div class="locktitle">로그인이 필요한 서비스입니다.</div>
      </div>
      <div v-else style="padding: 4%; height: 40%;">
        <!-- 별 모음 -->
        <div style="height: 28%; display: flex; align-items: center">
          <!-- <div>
            <v-icon style="color: yellow; font-size: 3vw;">mdi-star</v-icon>
          </div> -->
          <div style="width: 100%">
            <div style="float: left; width: 10%; text-align: center;" class="bar">
              <i class="fas fa-star"></i>
              <!-- <v-icon style="font-size: 35px; color: yellow;"><i class="fas fa-star"></i></v-icon> -->
            </div>
            <div class="starbar">
              <div class="mystar"></div>
              <div class="startotal"></div>
              <div class="starnum"><span>{{starCount}} / 30</span></div>
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
            <v-card  flat tile style="height: 90vh;">
              <div class="attenddialog">
                <div style="height: 3%"></div>
                <!-- 제목 -->
                <div class="attendtitle">
                  매일 매일 출석 체크
                </div>
                <!-- <div style="text-align: center">
                  <img style="width: 20%" src="../assets/images/attend.png" alt="출석체크">
                </div> -->
                <div style="height: 80%; padding: 2vw 4vw;">
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
                      <div class="itemname">곰 캐릭터</div>
                    </div>
                  </div>
                  <!-- 오른쪽 박스 -->
                  <div class="attendright">
                    <table style="text-align: center; border-collapse: collapse; height: 100%">
                      <tbody>
                        <tr v-for="i in 4" :key="i" style="height: 25%">
                          <td v-for="j in 7" :key="j" style="width: 10vw">
                            <div class="attendday">
                              {{(i-1)*7 + j}}
                            </div>
                            <div class="attendstemp">
                              <!-- <img style="width: 50%; height: 5%" src="../assets/images/hint.png" alt="hintimg"> -->
                              <v-icon v-if="attendDay.includes((i-1)*7 + j)" style="font-size: 50px; color: red">mdi-check-circle-outline</v-icon>
                              <v-icon v-else-if="today == ((i-1)*7 + j)" style="font-size: 50px;" @click="attendCheck">mdi-check-circle-outline</v-icon>
                              <v-icon v-if="noattendDay.includes((i-1)*7 + j)" style="font-size: 50px;">mdi-check-circle-outline</v-icon>
                              <!-- <v-icon v-else style="font-size: 50px;">mdi-check-circle-outline</v-icon> -->
                            </div>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div style="height: 9%; display: flex; justify-content: center;">
                  <v-btn text color="black" @click="attendmodal=false" class="confirmbtn" style="width: 30%; font-size: 25px">확인</v-btn>
                </div>
              </div>
            </v-card>
          </v-dialog>
        </div>
      </div>
    </div>
    <DifficultyModal v-if="showModal2" @close="showModal2= false" :stage2="stage2" :stage3="stage3"/>
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'
import "../css/mypage.scss";
import $ from 'jquery';
import store from '../vuex/store'
import axios from 'axios'
import Swal from 'sweetalert2'
import DifficultyModal from '../components/DifficultyModal.vue';

export default {
  data() {
    return {
      userEmail: '',
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
      starCount: 0,
      today: 5,
      nMonth:11,
      isAttend: false,
      showModal2: false,
      stage2: false,
      stage3: false,
    }
  },
  components: {
    Unity,
    DifficultyModal,
  },
  watch: {
     starCount(){
      // console.log("watch");
      // console.log(this.starCount);
      if(this.starCount == 0) {
      $(".startotal").css("border-radius", "15px")
      $(".startotal").css("width", "100%")
      }
      else if(this.starCount == 63){
        $(".mystar").css("border-radius", "15px")
        $(".mystar").css("width", "100%")
      }
      else {
        const starratio = (this.starCount / 30) * 100
        $(".mystar").css("width", `${starratio}%`)
        $(".startotal").css("width", `${100 - starratio}%`)
      }
    }
  },

  mounted() {
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
      }).then(() => {
        // console.log(res);
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
          Swal.fire({
            title: '출석완료!',
            text: '힌트 아이템을 받았습니다.',
            imageUrl: require('../assets/images/hint.png'),
            imageWidth: 400,
            imageHeight: 200,
            imageAlt: 'Custom image',
          })
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
        // console.log(res);
        this.attendDay = [];
        res.data.forEach(d => {
          this.attendDay.push(d.day);
        });
        // 총 출석일 계산
        this.totalAttendDay = this.attendDay.length;
        var date = new Date();
        this.today = date.getDate();
        this.nMonth = date.getMonth() +1;
        // 출석 실패한 날짜
        for(var i=1; i<29; i++){
          if(!this.attendDay.includes(i) && i<this.today){
            this.noattendDay.push(i)
          }
        }
        // console.log(this.noattendDay)
      })
    },
    moveGame(){
      // this.$router.push('/gamemap');
      if(store.state.kakaoUserInfo.id == null){
        this.showModal2 = true;
      }else{
        axios.get(`https://k3b102.p.ssafy.io:9999/cobit/user/stage`,{
          params:{
            id : store.state.kakaoUserInfo.id
          }
        })
        .then(res => {
          // console.log(res);
          this.stage2 = res.data.includes(2);
          this.stage3 = res.data.includes(3);
          this.showModal2 = true;
        })
      }
    },
    loadMyCharacter(){
      // 캐릭터 정보 불러오기
      // console.log("캐릭터 정보 불러오기");
      axios.get(`https://k3b102.p.ssafy.io:9999/cobit/product/user?email=${store.state.kakaoUserInfo.email}`)
      .then(res => {
        // console.log(res);
        this.$refs.myInstance.message('body', 'ChangeColor', res.data.color);
        this.$refs.myInstance.message('stand', 'ChangeEyebrow', res.data.eyebrow)
        this.$refs.myInstance.message('stand', 'ChangeEye', res.data.eye)
        if(res.data.crown){
          this.$refs.myInstance.message('stand', 'ChangeItem', res.data.crown)
        }
        if(res.data.shield){
          this.$refs.myInstance.message('stand', 'ChangeItem', res.data.shield)
        }
        if(res.data.sword){
          this.$refs.myInstance.message('stand', 'ChangeItem', res.data.sword)
        }
      })
    },
    goChantecharacter(){
      this.$router.push("/changecharacter")
    },
    handleStart(){
      if(this.userEmail != null){
        this.loadMyCharacter();
      }
    //   setTimeout(() => {
    //   this.loadMyCharacter();
    // }, 10);
    },
    movePractice(){
      this.$router.push('/practice')
    },
  },
  async created(){
    window.addEventListener('start', this.handleStart);
    if(this.$cookies.isKey("access_token")){
      // console.log("로그인")
      let kakao_account;
      await window.Kakao.API.request({
          url:'/v2/user/me',
          success : res => {
              kakao_account = res.kakao_account;
          },
      });
      await axios.get(`https://k3b102.p.ssafy.io:9999/cobit/user?email=${kakao_account.email}`)
              .then(res => {
                // console.log(res);
                this.$store.commit('setKakaoUserInfo', res.data);
                this.name = store.state.kakaoUserInfo.nickname;
                this.userEmail = store.state.kakaoUserInfo.email;
                this.starCount = store.state.kakaoUserInfo.star;
              });
      await this.loadAttend();
    } else{
      this.userEmail = null;
    }
  },
  beforeDestroy() {
    window.removeEventListener('start', this.handleStart)
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
  background-color: #ffffff1a;
  text-shadow: 0px 0px 9px black;
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
  box-shadow: 6px 6px 10px -1px rgba(0,0,0,0.2);
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
  background-color: #00000061;
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
  background-color: #ffffff36;
}
td {
  border: 1px solid gray;
}
.attendtext {
  padding: 3%;
  height: 27%;
  border-bottom: 1px solid gray;
  background-color: #e6a6308a;
}
.attendnum {
  height: 73%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 5vh;
  background-color: #ffffff36;
}
.ribbonimg {
  width: 260px;
  position: absolute;
  top: -75px;
}
.itemimg {
  width: 90%;
  height: 70%;
  background: url("../assets/images/attenditem.png");
  background-repeat: no-repeat;
  background-size: contain;
}
.itemtitle {
  position: absolute;
  font-size: 25px;
  font-family: 'BMJUA';
  top: -15px;
  color: gold;
  background-color: white;
}
.itemname {
  position: absolute;
  font-size: 25px;
  font-family: 'BMJUA';
  bottom: 15px;
}
.attendday {
  height: 25%; 
  border-bottom: 1px solid gray;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #ecb75682;
}
.attendstemp {
  height: 75%; display: flex; 
  justify-content: center;
  background-color: #ffffff36;
  /* background-image: url("../assets/images/hint.png");
  background-size: cover; */
}
.bar .fa-star {
  /* position: absolute; */
  font-size: 35px;
  color: rgb(243, 243, 0);
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
}
.nouser {
  padding: 4%;
  height: 38.8%;
  background-color: rgba(128, 128, 128, 0.9);
  border-bottom-left-radius: 7px;
  border-bottom-right-radius: 7px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.lockicon {
  font-size: 55px;
  color: white;
}
.locktitle {
  margin-top: 15px;
  font-size: 23px;
}
.nouserbtn {
  position: absolute;
  top: 5%;
  right: 3%;
  background-color: rgba(128, 128, 128, 0.8);
  border-radius: 10px;
  width: 138.5px;
  height: 58.5px;
  display: flex;
  justify-content: center;
  align-items: center;
}
.nouserbtnicon {
  color: white;
  font-size: 30px;
}
.attenddialog {
  height: 100%;
  background-image: url("../assets/images/attendback3.jpg");
  background-size: cover;
}
</style>