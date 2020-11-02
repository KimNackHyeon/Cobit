<template>
  <div class='wrap'>
    <div class="test-container">
      <div class="unity-box">
        <unity class="unity" src="Test2/Build/Test2.json" unityLoader="Test2/Build/UnityLoader.js" ref="myInstance"></unity>
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
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'

export default {
  name: 'Test3',
  data() {
    return {
      isMove: true,
      isObstacle: false,
      commandList: [],
    }
  },
  components: {
    Unity
  },
  computed: {
  },
  created() {
    // window.addEventListener('scroll', this.handleScroll)
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
    }
  },
  beforeDestroy () {
    // window.removeEventListener('scroll', this.handleScroll)
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
  padding: 10px;
  border-radius: 20px;
  background-color: #0F4C81;
  margin-bottom: 10px;
  cursor: pointer;
  color: #fff;
  font-size: 14px;
  float: left;
  clear: both;
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