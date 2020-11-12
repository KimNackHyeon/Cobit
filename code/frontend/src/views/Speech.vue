<template>
  <div class='wrap speech-wrap'>
    <div class="speech-container">
      <div class="unity-box">
        <div class="stagebtn" @click="gostage" style="position:absolute; z-index: 3;"><v-icon>mdi-chevron-left</v-icon>스테이지</div>
        <unity class="unity" src="cobit-stt/Build/cobit-stt.json" unityLoader="cobit-stt/Build/UnityLoader.js" ref="myInstance"></unity>
        <div class="speech-btn">
          <div @click="setSpeech">{{ buttonText }}</div>
          <span></span><vue-speech class="my-speech" v-if="isClick" lang="ko" @onTranscriptionEnd="onEnd"/>
        </div>
      </div>
    </div>
    <ClearModal v-if="isClear" @close="isClear= false" @restart="reStart" @next="nextLevel"/>
    <FailModal v-if="isFail" @close="isFail= false" @restart="reStart"/>
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'
import ClearModal from '../components/ClearModal.vue';
import FailModal from '../components/FailModal.vue';
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
      stageNum: 1,
      count: 0,
      starNum: 1,
      stageType: 1,
      lastSentence: '',
      transcription: '',
      buttonText: "버튼을 누르고 말을 해보세요",
    }
  },
  components: {
    Unity,
    ClearModal,
    FailModal,
  },
  computed: {
  },
  async created() {
    window.addEventListener('start', this.handleStart)
    window.addEventListener('clear', this.handleClear)
    window.addEventListener('fail', this.handleFail)
    this.stageNum = this.$cookies.get('stageInfo').stageNum;
    this.stageType = this.$cookies.get('stageInfo').stageType;
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
    ...mapMutations(['setInStageNum', 'setInStageStar']),
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
      this.stageNum += 1
      this.count = 0
      this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
      this.LevelLoad();
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
      axios.post(`https://k3b102.p.ssafy.io:9999/cobit/stage/user`,{
        userId : store.state.kakaoUserInfo.id,
        stageId : this.stageType + "" + this.stageNum,
        star : this.starNum 
      })
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
  },
  beforeDestroy () {
    window.removeEventListener('start', this.handleStart)
    window.removeEventListener('clear', this.handleClear)
    window.removeEventListener('fail', this.handleFail)
  },
}
</script>

<style scoped>
.speech-wrap {
  padding: 0 !important;
}

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

@keyframes blink {
  10% { opacity: 1; }
  50% { opacity: 0; }
  90% { opacity: 1; } 
}

</style>