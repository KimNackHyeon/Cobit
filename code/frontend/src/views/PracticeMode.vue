<template>
  <div class='wrap'>
    <div class="practice-container">
      <div class="unity-box">
        <div class="player-box">
          <div class="coin-area">
            <i class="fas fa-coins"></i>{{ coin }}
          </div>
          <div class="heart-area">
            <i v-for="(on, index) in heart" :key="`on-${index}`" class="fas fa-heart"></i>
            <i v-for="(off, index) in (5-heart)" :key="`off-${index}`" class="far fa-heart"></i>
          </div>
        </div>
        <unity class="unity" src="free/Build/free.json" unityLoader="free/Build/UnityLoader.js" ref="myInstance"></unity>
      </div>
      <div class="code-box">
        <div class="block-box">
          <div class="block-menu-bar">
            <div class="menu move-menu" @click="onMove">이동</div>
            <div class="menu obstacle-menu" @click="onObstacle">장애물</div>
          </div>
          <div v-show="isMove" class="block-list">
            <div class="block block0" @click="goUp">위쪽으로</div>
            <div class="block block1" @click="goRight">오른쪽으로</div>
            <div class="block block2" @click="goLeft">왼쪽으로</div>
            <div class="block block3" @click="goDown">아래쪽으로</div>
            <div class="block block3" @click="goJump">점프</div>
          </div>
        </div>
        <div class="play-box">
          <span v-for="(command,index) in commandList" :key="`com+${index}`">{{ command }}</span><span class="play-start" @click="goMove">출발</span>
        </div>
      </div>
    </div>

    <FailModal v-if="isFail" @close="isFail= false" @restart="reStart"/>
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'
import FailModal from '../components/FailModal.vue';

export default {
  name: 'Test3',
  data() {
    return {
      isMove: true,
      isObstacle: false,
      commandList: [],
      isFail: false,
      stageNum: 1,
      starNum: 1,
      heart: 5,
      coin: 0,
    }
  },
  components: {
    Unity,
    FailModal,
  },
  computed: {
  },
  created() {
    window.addEventListener('coin', this.handleCoin)
    window.addEventListener('heart', this.handleHeart)
    window.addEventListener('fail', this.handleFail)
  },
  mounted() {
    this.onMove();
  },
  watch: {
  },
  methods: {
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
    reStart() {
      this.commandList = []
      this.coin = 0;
      this.heart = 5;
      this.$refs.myInstance.message('JavascriptHook', 'RestartGame')
    },
    handleCoin() {
      this.coin = event.detail.coin;
    },
    handleHeart() {
      this.heart = event.detail.heart;
    },
    handleFail() {
      this.onModal();
    },
    onModal() {
      this.isFail = true;
    },
  },
  beforeDestroy () {
    window.removeEventListener('coin', this.handleCoin)
    window.removeEventListener('heart', this.handleHeart)
    window.removeEventListener('fail', this.handleFail)
  },
}
</script>

<style scoped>
.practice-container {
  display: flex;
  margin-top: 100px;
  width: 100%;
}

.practice-container .unity-box {
  width: 59%;
  margin-right: 1%;
  height: 450px;
  position: relative;
  /* background-color: grey; */
}

.unity-box .player-box {
  position: absolute;
  top:-30px;
  left:0;
  width: 100%;
  height: 30px;
  display: flex;
  justify-content: flex-end;
}

.player-box i {
  font-size: 20px;
  margin: 0 2px;
}

.player-box .heart-area {
  color: red;
}

.player-box .coin-area {
  margin-right: 30px;
}

.player-box .coin-area > i {
  color: gold;
  margin-right: 10px;
}

.practice-container .code-box {
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
  top: -20%;
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
  bottom: -20%;
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