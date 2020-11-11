<template>
  <div class='wrap'>
    <div class="test-container">
      <div class="unity-box">
        <unity class="unity" src="glacier/Build/glacier.json" unityLoader="glacier/Build/UnityLoader.js" ref="myInstance"></unity>
      </div>
      <div class="code-box">
        <div class="block-box">
          <div class="block-menu-bar">
            <div class="menu move-menu" @click="onMove">이동</div>
            <div class="menu obstacle-menu" @click="onObstacle">장애물</div>
          </div>
          <div v-show="isMove" class="block-list">
            <div class="block block0" @click="goUp">앞으로</div>
            <div class="block block1" @click="goRight">오른쪽으로 돌기</div>
            <div class="block block2" @click="goLeft">왼쪽으로 돌기</div>
            <div class="block block3" @click="goDown">뒤로</div>
            <div class="block block3" @click="goJump">점프</div>
            <div class="block block3" @click="goFor">반복문</div>
            <div class="block block3" @click="goFor2">반복문2</div>
            <div class="block block3" @click="goFor3">반복문3</div>
          </div>
        </div>
        <div class="play-box">
          <span v-for="(command,index) in commandList" :key="`com+${index}`">{{ command }}</span><span class="play-start" @click="goMove">출발</span>
        </div>
      </div>
    </div>
    <VueSpeech></VueSpeech>
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
import VueSpeech from '../views/SpeechDetectionStarted.vue'
import Vue from 'vue'
Vue.use(VueSpeech)
export default {
  name: 'Test3',
  data() {
    return {
      isMove: true,
      isObstacle: false,
      commandList: [],
      isClear: false,
      isFail: false,
      stageNum: 4,
      starNum: 1,
      stageType: 1,
    }
  },
  components: {
    Unity,
    ClearModal,
    FailModal,
    VueSpeech,
  },
  computed: {
  },
  created() {
    window.addEventListener('start', this.handleStart)
    window.addEventListener('clear', this.handleClear)
    window.addEventListener('fail', this.handleFail)
    this.stageNum = 5;
    this.stageType = this.$cookies.get('stageInfo').stageType;

  },
  mounted() {
    this.onMove();
  },
  watch: {
  },
  methods: {
    ...mapMutations(['setInStageNum', 'setInStageStar', 'setIsLastStage']),
    onMove() {
      this.isMove = true; this.isObstacle = false
      const MOVE = document.querySelector('.move-menu');
      const OBSTACLE = document.querySelector('.obstacle-menu');
      MOVE.classList.add('on-menu-bar');
      OBSTACLE.classList.remove('on-menu-bar');
    },
    onObstacle() {
      this.isObstacle = true; this.isMove = false
      const MOVE = document.querySelector('.move-menu');
      const OBSTACLE = document.querySelector('.obstacle-menu');
      MOVE.classList.remove('on-menu-bar');
      OBSTACLE.classList.add('on-menu-bar');
    },
    goUp() {
      this.commandList.push('↑')
      this.$refs.myInstance.message('JavascriptHook', 'Up')
    },
    goDown() {
      this.commandList.push('↓')
      this.$refs.myInstance.message('JavascriptHook', 'Down')
    },
    goRight() {
      this.commandList.push('→')
      this.$refs.myInstance.message('JavascriptHook', 'Right')
    },
    goLeft() {
      this.commandList.push('←')
      this.$refs.myInstance.message('JavascriptHook', 'Left')
    },
    goJump() {
      this.commandList.push('점프')
      this.$refs.myInstance.message('JavascriptHook', 'Jump')
    },
    goMove() {
      this.$refs.myInstance.message('JavascriptHook', 'Go')
    },
    goFor() {
      this.commandList.push('반복')
      this.$refs.myInstance.message('JavascriptHook', 'Loop', '2,Jump')
    },
    goFor2() {
      this.commandList.push('반복2')
      this.$refs.myInstance.message('JavascriptHook', 'Loop', '3,Jump,Down,Up')
    },
    goFor3() {
      this.commandList.push('반복3')
      this.$refs.myInstance.message('JavascriptHook', 'Loop', '5,Right,Up')
    },
    LevelLoad() {
      this.commandList = []
      this.$refs.myInstance.message('JavascriptHook', 'Stage', 5)
    },
    reStart() {
      this.commandList = []
      this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
      this.LevelLoad();
    },
    nextLevel() {
      this.commandList = []
      this.stageNum += 1
      this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
      this.LevelLoad();
    },
    handleStart() {
      setTimeout(() => {
        this.LevelLoad();
      }, 10);
    },
    handleClear() {
      this.getStar();
      this.onModal();
    },
    handleFail() {
      this.onModal2();
    },
    onModal() {
      this.setInStageNum(this.stageNum);
      this.setInStageStar(this.starNum);
      this.isClear = true;
      if(this.stageNum == 5) {
        this.setIsLastStage(true)
      }
    },
    onModal2() {
      this.isFail = true;
    },
    getStar() {
      const LENG = this.commandList.length
      if(this.stageNum == 1) {
        if(LENG <= 5) {this.starNum=3} else if(LENG > 5 && LENG <= 7) {this.starNum=2}
      } if(this.stageNum == 2) {
        if(LENG <= 9) {this.starNum=3} else if(LENG > 9 && LENG <= 12) {this.starNum=2}
      } if(this.stageNum == 3) {
        if(LENG <= 13) {this.starNum=3} else if(LENG > 13 && LENG <= 16) {this.starNum=2}
      } if(this.stageNum == 4) {
        if(LENG <= 12) {this.starNum=3} else if(LENG > 12 && LENG <= 15) {this.starNum=2}
      } if(this.stageNum == 5) {
        if(LENG <= 16) {this.starNum=3} else if(LENG > 16 && LENG <= 20) {this.starNum=2}
      }

      // axios
      axios.post(`https://k3b102.p.ssafy.io:9999/cobit/stage/user`,{
        userId : store.state.kakaoUserInfo.id,
        stageId : this.stageType + "" + this.stageNum,
        star : this.starNum 
      })
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
.test-container {
  display: flex;
  margin-top: 100px;
  width: 100%;
}

.test-container .unity-box {
  width: 59%;
  margin-right: 1%;
  height: 450px;
  /* background-color: grey; */
}

.test-container .code-box {
  width: 40%;
  height: 450px;
  /* background-color: bisque; */
  display: flex;
  position: relative;
}

.code-box .block-box {
  width: 40%;
  /* background-color: blue; */
  display: flex;
}

.code-box .play-box {
  width: 60%;
  /* background-color: brown; */
  border: 1px solid #a4d4ff;
  /* position: relative; */
}

.block-box .block-menu-bar {
  width: 30%;
}

.block-box .block-list {
  width: 70%;
  padding: 10px;
  /* background-color: #0F4C81; */
  border: 1px solid #a4d4ff;
  margin: 0 2px;
}

.block-box .block-menu-bar .on-menu-bar {
  background-color: #a4d4ff;
  font-weight: 700;
}

.block-box .block-menu-bar .menu {
  display: flex;
  justify-content: center;
  align-items: center;
  padding-left: 5px;
  height: 50px;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  /* background-color: chocolate; */
  border: 1px solid #a4d4ff;
  margin-bottom: 5px;
  cursor: pointer;
  transition: background-color .5s ease;
}

.block-box .block-list .block {
  padding: 10px 20px;
  border-radius: 20px;
  background-color: #0F4C81;
  margin-bottom: 10px;
  cursor: pointer;
  color: #fff;
  font-size: 14px;
  float: left;
  clear: both;
  position: relative;
}

.block-box .block-list .block::before {
  content: "";
  position: absolute;
  right: 50%;
  top: -14px;
  transform: translate(50%, 0);
  width: 15px;
  height: 15px;
  background-color: #0F4C81;
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
}

.block-box .block-list .block::after {
  content: "";
  position: absolute;
  right: 50%;
  bottom: -14px;
  transform: translate(50%, 0);
  width: 15px;
  height: 15px;
  background-color: #fff;
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
}

.play-box .play {
  position: absolute;
  top: 0;
  left: 0;
  width: 100px;
  height: 50px;
  background-color: bisque;
}
.play-box > span {
  margin-right: 10px;
}
.play-box .play-start {
  width: 100px;
  height: 30px;
  padding: 5px 10px;
  margin: 10px;
  border-radius: 20px;
  background-color: cornflowerblue;
  color: #fff;
  cursor: pointer;
}

.test-container .unity-box .unity {
  width: 100%;
  height: 100%;
}

</style>