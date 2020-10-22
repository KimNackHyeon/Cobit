<template>
  <div class='wrap'>
    <div class="code-block-container">
      <div class="unity-box"></div>
      <div class="code-box">
        <div class="block-box">
          <div class="block-menu-bar">
            <div class="menu move-menu" @click="onMove">이동</div>
            <div class="menu obstacle-menu" @click="onObstacle">장애물</div>
          </div>
          <div v-if="isMove" class="block-list">
            <div class="block">앞으로가기</div>
            <div class="block">오른쪽으로가기</div>
            <div class="block">왼쪽으로가기</div>
            <div class="block">뒤로가기</div>
            <div class="block">오른쪽으로 90˚ 회전</div>
            <div class="block">왼쪽으로 90˚ 회전</div>
            <div class="block">점프하기</div>
          </div>
          <div v-if="isObstacle" class="block-list">
            <div class="block">장애물추가</div>
          </div>
        </div>
        <div class="play-box" @drop="drop" @dragover="dragover">
          <div class="play" draggable="true" @dragstart="dragstart">test1</div>
          <div class="play" draggable="true" @dragstart="dragstart">test2</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CodeBlock',
  data() {
    return {
      isMove: true,
      isObstacle: false,
    }
  },
  components: {
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
    handleScroll() {
      console.log('Base')
    },
  },
  beforeDestroy () {
    // window.removeEventListener('scroll', this.handleScroll)
  },
}
</script>

<style scoped>
.code-block-container {
  display: flex;
  margin-top: 50px;
}

.code-block-container .unity-box {
  width: 49%;
  margin-right: 1%;
  height: 500px;
  background-color: grey;
}

.code-block-container .code-box {
  width: 50%;
  height: 500px;
  /* background-color: bisque; */
  display: flex;
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
  position: relative;
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
  width: 100px;
  height: 50px;
}

</style>