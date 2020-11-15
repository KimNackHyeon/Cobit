<template>
  <div class='wrap speech-wrap'>
    <div class="story" @click="clickStory" v-if="openStory">
      <div v-if="story[stageNum-1].start_modal!=''" style="width:100%; height:20%; position:absolute; bottom:50%; display:flex; justify-content:center;">
        <div style="width:20%; height:100%; background-color:white; box-shadow: 1px 1px 14px #000000b3; border: 4px solid #ffcf00;color:black;" v-html="story[stageNum-1].start_modal"></div>
      </div>
      <div style="width: 100%; height: 25%; position: absolute; bottom: 25%;">
        <img style="width:auto; height:100%;" src="../assets/images/pen_saying.gif">
      </div>
      <div class="script" v-html="story[stageNum-1].start"></div>
    </div>
    <div class="speech-container">
      <div class="unity-box">
        <div class="direction-area">
          <div class="left-arrow"><img src="../assets/images/arrow.png" alt="left"><span>왼쪽</span></div>
          <div class="right-arrow"><img src="../assets/images/arrow.png" alt="right"><span>오른쪽</span></div>
          <div class="up-arrow"><img src="../assets/images/arrow.png" alt="up"><span>위쪽</span></div>
          <div class="down-arrow"><img src="../assets/images/arrow.png" alt="down"><span>아래쪽</span></div>
        </div>
        <div class="stagebtn" @click="gostage" style="position:absolute; z-index: 2;"><v-icon>mdi-chevron-left</v-icon>스테이지</div>
        <unity class="unity" src="jslib/Build/jslib.json" unityLoader="jslib/Build/UnityLoader.js" ref="myInstance"></unity>
        <div class="speech-btn" @click="setSpeech">
          <div >{{ buttonText }}</div>
          <span></span><vue-speech class="my-speech" v-if="isClick" lang="ko" @onTranscriptionEnd="onEnd"/>
        </div>
      </div>
    </div>
    <ClearModal v-if="isClear" @close="isClear= false" @restart="reStart" @next="nextLevel"/>
    <FailModal v-if="isFail" @close="isFail= false" @restart="reStart"/>
    <SpeechModal v-if="isFirst" @close="isFirst= false"/>
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'
import ClearModal from '../components/ClearModal.vue';
import FailModal from '../components/FailModal.vue';
import SpeechModal from '../components/SpeechModal.vue';
import { mapMutations } from 'vuex';
import axios from 'axios';
import store from '../vuex/store'

export default {
  name: 'Speech',
  data() {
    return {
      isClick: false,
      isMove: true,
      isObstacle: false,
      commandList: [],
      isClear: false,
      isFail: false,
      isFirst: false,
      hintCnt: 2,
      stageNum: 1,
      count: 0,
      starNum: 1,
      stageType: 1,
      lastSentence: '',
      transcription: '',
      buttonText: "버튼을 누르고 말을 해보세요",
      openStory:true,
      story:[
        { 
          // start_modal:"cobit에 오신 여러분들 환영해요!<br> 우선, 오른쪽에 있는 컴퓨터에 다가가 왜 고장이 났는지 살펴볼까요?",
        start_modal:"",
          start:"1. 어떻게 풀어야할지 마이크를 누르고 말해봐.<br> 2. 나는 네가 말한대로 움직일게.",
          end:"<h3>컴퓨터의 두뇌, CPU</h3><br>CPU는 컴퓨터의 두뇌에요. '프로세서'라고도 불린답니다. <br> 모든 장치에 제어와 연산을 하도록 조종하는 CPU는 컴퓨터가 빠르게 돌아가는데에 중요한 역할을 한답니다.",
          hint:"basicHint1.png"
          },
          { start_modal:"",
          start:"컴퓨터의 두뇌인 CPU에 대해 알아보았어요.<br>컴퓨터를 조금 더 살펴보기위해 다가가보세요!",
          end:"<h3>컴퓨터의 공부 책상, 메모리</h3><br>공부할 때 교과서나 참고서, 필기도구 등을 책상위에 올려놓고 참고서를 보면서 공책에 필기를 하죠?<br>메모리 또한 컴퓨터가 작업에 필요한 것을 일시적으로 기억해서 CPU가 프로그램을 작동시킬 때 사용할 수 있게 합니다!",
          hint:"basicHint2.png"
          },
          { start_modal:"",
          start:"앗! 이게뭐지?<br>갑자기 이상한게 나타났어요! 이게 뭘까요?<br>알아보기위해 다가가보세요.",
          end:"<h3>I'm the creeper, catch me if you can!</h3>크리퍼, 최초의 컴퓨터 바이러스.<br>컴퓨터 바이러스란, <br>우리몸을 아프게하는 바이러스와 비슷하게 스스로를 복제하여 컴퓨터를 아프게하는 바이러스에요.<br>인터넷 이나 네트워크 또는 이동식 매체를 통해 전파가 되어 내 컴퓨터의 파일시스템을 망가트려요!",
          hint:"basicHint3.png"
          },
          { start_modal:"",
          start:"바이러스라니,무서워요! <br>컴퓨터를 망가뜨리는 바이러스의 종류에는 어떤것이 있을까요?<br>살펴보기위해 다가가보세요.",
          end:"<h3>꿈틀거리는 벌레라는 의미의 '웜(Worm)바이러스'</h3>웜바이러스는 스스로 복제를 해서 내부시스템을 망가트리는 나쁜 바이러스에요.<br>웜바이러스는 인터넷속도에는 큰영향을 주지않지만 파일을 변형하거나 예측불가능한 증상을 만드는게 특징이에요.<br> 웜바이러스에 예방하기위해선 이메일첨부파일을 다운로드를 주의해서 해야해요.",
          hint:"basicHint4.png"
          },
          { start_modal:"",
          start:"또다른 바이러스가 나타났어요!<br> 저 바이러스는 뭘까요? 알아보러 가봅시다!",
          end:"<h3>트로이 전쟁 중 성 안에 몰래 침입한전략에서 비유된 '트로이목마'</h3>바이러스 중 가장 유명한 바이러스이며, 정상적인 프로그램으로 위장해 숨어있다가 실행하면 악성코드를 퍼트리는 바이러스에요.<br>해킹기능이 존재하는 바이러스이니 컴퓨터에 트로이목마가 감염되었다면 내 개인정보가 유출되었다는 의미이니 조심하여야합니다.",
          hint:"basicHint5.png"
          },
      ],
    }
  },
  components: {
    Unity,
    ClearModal,
    FailModal,
    SpeechModal
  },
  computed: {
  },
  async created() {
    window.addEventListener('start', this.handleStart)
    window.addEventListener('clear', this.handleClear)
    window.addEventListener('fail', this.handleFail)
    this.stageNum = this.$cookies.get('stageInfo').stageNum;
    this.stageType = this.$cookies.get('stageInfo').stageType;
    console.log(this.stageNum + " " + this.stageType);
    if(this.$cookies.isKey("access_token")){
      let kakao_account;
      await window.Kakao.API.request({
          url:'/v2/user/me',
          success : res => {
              kakao_account = res.kakao_account;
          },
      });
      await axios.get(`https://k3b102.p.ssafy.io:9999/cobit/user?email=${kakao_account.email}`)
              .then(res => {
                this.$store.commit('setKakaoUserInfo', res.data);
                this.hintCount = res.data.hint;
              });
    }

  },
  mounted() {
  },
  watch: {
  },
  methods: {
    ...mapMutations(['setInStageNum', 'setInStageStar','setSpeechType']),
    LevelLoad() {
      this.commandList = []
      this.$refs.myInstance.message('JavascriptHook', 'Stage', this.stageNum)
    },
    reStart() {
      this.commandList = []
      this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
      this.LevelLoad();
    },
    nextLevel() {
      this.commandList = []
      // this.stageNum += 1
      this.count = 0;
      // this.stageNum = this.$cookies.get('stageInfo').stageNum;
      // this.stageType = this.$cookies.get('stageInfo').stageType;
      // var stageInfo = this.$cookies.get('stageInfo');
      // this.$cookies.set('stageInfo',stageInfo);
      setTimeout(() => {
        location.reload()
      }, 100);
      this.$router.push('/codeblock');
      // this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
      // this.LevelLoad();
    },
    handleStart() {
      setTimeout(() => {
        this.LevelLoad();
        this.loadMyCharacter();
      }, 10);
    },
    handleClear() {
      this.getStar();
      this.onModal();
    },
    handleFail() {
      this.onModal2();
    },
    onEnd ({ lastSentence, transcription }) {
      console.log(lastSentence);
      console.log(transcription);
      this.lastSentence = lastSentence
      this.transcription = transcription
    },
    onModal() {
      this.setInStageNum(this.stageNum);
      this.setInStageStar(this.starNum);
      this.isClear = true;
    },
    onModal2() {
      this.isFail = true;
    },
    onModal3(type){
      if(type == 1) {
        this.setSpeechType(1)
        this.isFirst = true;
      } else {
        this.setSpeechType(2)
        this.isFirst = true;
      } 
    },
    getStar() {
      if(this.stageNum == 1) {
        if(this.count == 1) {this.starNum=3} else if(this.count == 2) {this.starNum=2} else {this.starNum=1}
      } if(this.stageNum == 2) {
        if(this.count == 1) {this.starNum=3} else if(this.count == 2) {this.starNum=2} else {this.starNum=1}
      } if(this.stageNum == 3) {
        if(this.count == 1) {this.starNum=3} else if(this.count == 2) {this.starNum=2} else {this.starNum=1}
      } if(this.stageNum == 4) {
        if(this.count == 1) {this.starNum=3} else if(this.count == 2) {this.starNum=2} else {this.starNum=1}
      } if(this.stageNum == 5) {
        if(this.count == 1) {this.starNum=3} else if(this.count == 2) {this.starNum=2} else {this.starNum=1}
      }

      // axios
      // axios.post(`https://k3b102.p.ssafy.io:9999/cobit/stage/user`,{
      //   userId : store.state.kakaoUserInfo.id,
      //   stageId : this.stageType + "" + this.stageNum,
      //   star : this.starNum 
      // })
    },
    setSpeech() {
      this.isClick = !this.isClick
      if (this.isClick) {
        this.buttonText="말을 마쳤다면 버튼을 눌러주세요."
      } else {
        this.buttonText="버튼을 누르고 말을 해보세요"
        this.count += 1;
        console.log(this.count)
        console.log(this.transcription, '입력값')
        axios.post('https://k3b102.p.ssafy.io:9999/cobit/speech/analyze1', this.transcription )
        .then(res => {
          console.log(res);
          this.buttonText="버튼을 누르고 말을 해보세요"
          for (let index = 0; index < res.data.length; index++) {
            this.$refs.myInstance.message('JavascriptHook', `${res.data[index]}`);
            this.$refs.myInstance.message('JavascriptHook', 'Go');
            
          }
        })
        .catch(error => {
          console.log(error);
        });
      }
    },
    loadMyCharacter(){
      // 캐릭터 정보 불러오기
      console.log("캐릭터 정보 불러오기");
      axios.get(`https://k3b102.p.ssafy.io:9999/cobit/product/user?email=${store.state.kakaoUserInfo.email}`)
      .then(res => {
        console.log(res);
        this.$refs.myInstance.message('body', 'ChangeColor', res.data.color);
        this.$refs.myInstance.message('Penguin', 'ChangeEyebrow', res.data.eyebrow)
        this.$refs.myInstance.message('Penguin', 'ChangeEye', res.data.eye)
        if(res.data.crown){
          this.$refs.myInstance.message('Penguin', 'ChangeItem', res.data.crown)
        }
        if(res.data.shield){
          this.$refs.myInstance.message('Penguin', 'ChangeItem', res.data.shield)
        }
        if(res.data.sword){
          this.$refs.myInstance.message('Penguin', 'ChangeItem', res.data.sword)
        }
      })
    },
    gostage(){
        this.$router.push('/gamemap')
    },
    clickStory(){
      this.openStory = false;
      if(this.stageNum == 1) {
        this.onModal3(1);
      } else if (this.stageNum == 5) {
        this.onModal3(2);
      }
    },
  },
  beforeDestroy () {
    window.removeEventListener('start', this.handleStart)
    window.removeEventListener('clear', this.handleClear)
    window.removeEventListener('fail', this.handleFail)
  },
}
</script>

<style scoped>
.wrap{
  padding:0px;
  width:100%;
  height:100%;
}
/* .speech-wrap {
  padding: 0 !important;
} */

.speech-container {
  display: flex;
  width: 100%;
  justify-content: center;
}

.speech-container .unity-box {
  width: 100vw;
  min-width: 800px;
  height: 100vh;
  position: relative;
  /* background-color: grey; */
}

.speech-container .unity-box .unity {
  width: 100%;
  height: 100%;
}

.unity-box .speech-btn {
  position: absolute;
  bottom: 10px;
  width: 80%;
  margin-left: 10%;
  height: 10vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  font-family: 'BMJUA';
  font-size: 20px;
  cursor: pointer;
  background-color: #a4d4ff;
  border-radius: 20px;
  transition: .3s ease;
}

.unity-box .speech-btn:hover {
  background-color: #c4e1fc;
}

.unity-box .direction-area {
  position: absolute;
  top: 60px;
  right: 80px;
  width: 30px;
  height: 30px;
  z-index: 2;
}

.unity-box .direction-area > div {
  position: absolute;
  width: 100%;
  height: 100%;
}

.unity-box .direction-area > div > img {
  width: 100%;
  height: 100%;
}

.unity-box .direction-area > div > span {
  position: absolute;
  width: 50px;
  text-align: center;
  font-family: 'BMJUA';
  color: #fff;
  text-shadow: -1.5px 0 #000,
                0 1.5px #000,
                1.5px 0 #000,
                0 -1.5px #000;
}

.unity-box .direction-area .up-arrow {
  top: -60%;
}
.unity-box .direction-area .up-arrow > img {
  transform: rotate(270deg);
}
.unity-box .direction-area .up-arrow > span {
  top: -60%;
  left: 50%;
  transform: translate(-50%, 0);
}

.unity-box .direction-area .down-arrow {
  bottom: -60%;
}
.unity-box .direction-area .down-arrow > img {
  transform: rotate(90deg);
}
.unity-box .direction-area .down-arrow > span {
  bottom: -70%;
  left: 50%;
  transform: translate(-50%, 0);
}

.unity-box .direction-area .right-arrow {
  right: -60%;
}
.unity-box .direction-area .right-arrow > span{
  top: 50%;
  right: -160%;
  transform: translate(0, -50%);
}

.unity-box .direction-area .left-arrow {
  left: -60%;
}
.unity-box .direction-area .left-arrow > img {
  transform: rotate(180deg);
}
.unity-box .direction-area .left-arrow > span {
  top: 50%;
  left: -140%;
  transform: translate(0, -50%);
}

.speech-btn .my-speech {
  margin-top: 5px;
  font-weight: 700;
}

.speech-btn .my-speech::before {
  content: "\f0a4";
  font-family: "Font Awesome 5 Free";
  font-size: 17px;
  font-weight: 100;
  color: black;
  animation: blink 1.5s linear infinite;
  margin-right: 10px;

}

.stagebtn {
  top: 10px;
  left: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 150px;
  height: 50px;
  background-color: #a4d4ff;
  font-size: 22px;
  font-weight: 500;
  font-family: BMJUA;
  border-radius: 30px;
  cursor: pointer;
  transition: box-shadow .3s ease;
  box-shadow: 6px 6px 10px -1px #ffffff;
}
.stagebtn:hover {
  box-shadow: 0 0 0 0 rgba(0,0,0,0),
              0 0 0 0 rgba(0,0,0,0),
              inset 4px 4px 6px -1px rgba(0,0,0,0.2),
              inset -3px -3px 4px -1px #ffffff !important;
}
.story{
  width:100vw;
  height:100vh;
  position:absolute;
  bottom:0;
  z-index:4;
}
.script{
    width: 100%;
    height: 25%;
    background-color: rgba(0, 0, 0, 0.66);
    position: absolute;
    bottom: 0px;
    color: white;
    font-size: x-large;
    padding: 5vh 5vw;
}

@keyframes blink {
  10% { opacity: 1; }
  50% { opacity: 0; }
  90% { opacity: 1; } 
}

</style>