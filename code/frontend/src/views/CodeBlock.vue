<template>
  <div class='wrap'>
    <div class="code-block-container">
      <div class="unity-box">
        <unity class="unity" src="../../unity/Build/unity.json" unityLoader="#" ref="myInstance"></unity>
      </div>
      <div class="code-box" @drop="drop" @dragover="dragover">
        <div class="block-box">
          <div class="block-menu-bar">
            <div class="menu move-menu" @click="onMove">이동</div>
            <div class="menu obstacle-menu" @click="onObstacle">장애물</div>
          </div>
          <div v-show="isMove" class="block-list">
            <div v-for="(item, index) in items.block0" :key="`a+${index}`" class="block block0" draggable="true" @dragstart="dragstart">앞으로가기</div>
            <div v-for="(item, index) in items.block1" :key="`b+${index}`" class="block block1" draggable="true" @dragstart="dragstart">오른쪽으로가기</div>
            <div v-for="(item, index) in items.block2" :key="`c+${index}`" class="block block2" draggable="true" @dragstart="dragstart">왼쪽으로가기</div>
            <div v-for="(item, index) in items.block3" :key="`d+${index}`" class="block block3" draggable="true" @dragstart="dragstart">뒤로가기</div>
            <div v-for="(item, index) in items.block4" :key="`e+${index}`" class="block block4" draggable="true" @dragstart="dragstart">오른쪽으로 90˚ 회전</div>
            <div v-for="(item, index) in items.block5" :key="`f+${index}`" class="block block5" draggable="true" @dragstart="dragstart">왼쪽으로 90˚ 회전</div>
            <div v-for="(item, index) in items.block6" :key="`g+${index}`" class="block block6" draggable="true" @dragstart="dragstart">점프하기</div>
          </div>
          <div v-show="isObstacle" class="block-list">
            <div class="block">장애물추가</div>
          </div>
        </div>
        <div class="play-box">
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Unity from 'vue-unity-webgl'

export default {
  name: 'CodeBlock',
  data() {
    return {
      isMove: true,
      isObstacle: false,
      distX: '',
      distY: '',
      targetClass: '',
      targetClass2: '',
      targetNum: '',
      targetFlag: false,
      items: {
        block0: 1, block1: 1, block2: 1, block3: 1, block4: 1, block5: 1, block6: 1
      },
      classId: 'a',
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
    dragstart(event) {
      // event.target.style.position = 'absolute';
      let posX = event.pageX;
      let posY = event.pageY;
      this.distX = event.srcElement.offsetLeft - posX;
      this.distY = event.srcElement.offsetTop - posY;
      event.target.classList.add(`${this.classId}`)
      this.classId += '0'
      this.targetClass = event.target.classList[2]
      this.targetClass2 = event.target.classList[1]
    },
    dragover(event) {
      event.stopPropagation();
      event.preventDefault();
    },
    drop(event) {
      event.stopPropagation();
      event.preventDefault();
      let posX = event.pageX;
      let posY = event.pageY;
      if (posX >= 1170 && posX <= 1450) {
        if (posY >= 113 && posY <= 520) {
          document.querySelector(`.${this.targetClass}`).style.position = 'absolute';
          document.querySelector(`.${this.targetClass}`).style.top = 0;
          document.querySelector(`.${this.targetClass}`).style.left = 0;
          document.querySelector(`.${this.targetClass}`).style.marginLeft = posX + this.distX + 'px';
          document.querySelector(`.${this.targetClass}`).style.marginTop = posY + this.distY + 'px';
          const CLONE = document.querySelectorAll(`.${this.targetClass2}`)
          for (let i=0; i<CLONE.length; i++) {
            if (CLONE[i].classList.length == 2) {
              this.targetFlag = false
            } else {
              this.targetFlag = true
            }
          }
          if(this.targetFlag) {
            const NAME = this.targetClass2
            this.items[NAME] += 1
          }
        }
      }
      console.log(posX, posY, this.distX, this.distY)
      // $('#mydiv').css('margin-left', posX + this.distX + 'px')
      //     .css('margin-top', posY + this.distY + 'px');
    }
  },
  beforeDestroy () {
    // window.removeEventListener('scroll', this.handleScroll)
  },
}
</script>

<style scoped>
.code-block-container {
  display: flex;
  margin-top: 100px;
  width: 100%;
}

.code-block-container .unity-box {
  width: 59%;
  margin-right: 1%;
  height: 450px;
  /* background-color: grey; */
}

.code-block-container .code-box {
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

.code-block-container .unity-box .unity {
  width: 100%;
  height: 100%;
}

</style>