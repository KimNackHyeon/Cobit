<template>
  <div class='wrap'>
    <div class="code-block-container">
      <div class="unity-box">
        <unity class="unity" style="width:100%; height:100%;" src="map/Build/map.json" unityLoader="map/Build/UnityLoader.js" ref="myInstance"></unity>
      </div>
      <div class="code-box" @drop="drop" @dragover="dragover">
          <div class="block-menu-bar">
            <div class="menu move-menu" @click="onMove">이동</div>
            <div class="menu obstacle-menu" @click="onObstacle">장애물</div>
          </div>
        <div class="block-box">
           <div v-show="isMove" class="block-list">
            <div v-for="(m,index) in moves" :key="index" class="block" :class="'block'+index" draggable="true" @dragstart="dragstartAdd">
              {{m.move_kor}}
            </div>
          </div>
          <div v-show="isObstacle" class="block-list">
            <div class="block">장애물추가</div>
          </div>
        </div>
        <div id="play-box" class="play-box">
          <div v-for="(m,index) in resultStep" :key="index" class="block" :class="'block'+m.num" draggable="true" @dragstart="dragstart(index,$event)" :style="{position:'absolute',top: 0,left:0,'margin-left':m.marginleft,'margin-top':m.marginTop}">
              {{moves[m.num].move_kor}}
            </div>
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
      moves:[
        {
          num:0,
          move:'forward',
          move_kor:'앞으로 가기'
        },
        {
          num:1,
          move:'right',
          move_kor:'오른쪽으로 가기'
        },
        {
          num:2,
          move:'left',
          move_kor:'왼쪽으로 가기'
        },
        {
          num:3,
          move:'back',
          move_kor:'뒤로 가기'
        },
        {
          num:4,
          move:'turnRight',
          move_kor:'오른쪽으로 90˚ 회전'
        },
        {
          num:5,
          move:'turnLeft',
          move_kor:'왼쪽으로 90˚ 회전'
        },
        {
          num:6,
          move:'jump',
          move_kor:'점프 하기'
        }
      ],
      resultStep:[
      ],
      selectnum:0,
      isAdded: false,
      targetdiv:0,
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
    dragstartAdd(event){
      let posX = event.pageX;
      let posY = event.pageY;
      this.distX = event.srcElement.offsetLeft - posX;
      this.distY = event.srcElement.offsetTop - posY;
      this.selectnum = event.target.className;
      this.isAdded = false;
    },
    dragstart(me,event) {
      this.targetdiv = me;
      // event.target.style.position = 'absolute';
      let posX = event.pageX;
      let posY = event.pageY;
      this.distX = event.srcElement.offsetLeft - posX;
      this.distY = event.srcElement.offsetTop - posY;
      this.isAdded = true;
    },
    dragover(event) {
      event.stopPropagation();
      event.preventDefault();
    },
    drop(event) {
      const target = document.getElementById('play-box');
      const clientRect = target.getBoundingClientRect(); // DomRect 구하기 (각종 좌표값이 들어있는 객체)
      const relativeLeft = clientRect.left;
      const relativeRight = clientRect.right;
      event.stopPropagation();
      event.preventDefault();
      let posX = event.pageX;
      let posY = event.pageY;
      if (posX >= relativeLeft && posX <= relativeRight) {
        if(!this.isAdded){
        var selectedNum = this.selectnum;
        selectedNum = selectedNum.split("block")[2].split(' ')[0]
        this.resultStep.push({num:Number(selectedNum),marginleft:posX + this.distX + 'px',marginTop:posY + this.distY + 'px'});
        }else{
          this.resultStep[this.targetdiv].marginleft = posX + this.distX + 'px';
          this.resultStep[this.targetdiv].marginTop = posY + this.distY + 'px';
        }
      }
    },
  },
  beforeDestroy () {
    // window.removeEventListener('scroll', this.handleScroll)
  },
}
</script>

<style scoped>
.wrap{
  padding:0px;
  width:100%;
  height:100%;
}
.code-block-container {
  display: flex;
  /* margin-top: 100px; */
  width: 100%;
  height:100%;
}

.code-block-container .unity-box {
  width: 60%;
  /* margin-right: 1%; */
  height: 100%;
  /* background-color: grey; */
}

.code-block-container .code-box {
  width: 40%;
  height: 100%;
  /* background-color: bisque; */
  display: flex;
  position: relative;
}

.code-box .block-box {
  width: 30%;
  /* background-color: blue; */
  display: flex;
  height:100%
}

.code-box .play-box {
  width: 70%;
  height:100%;
  /* background-color: brown; */
  border: 1px solid #a4d4ff;
  /* position: relative; */
}

.block-menu-bar {
  margin-left:-100px;
  width: 100px;
}

.block-box .block-list {
  width: 100%;
  padding: 10px;
  /* background-color: #0F4C81; */
  border: 1px solid #a4d4ff;
}

.block-menu-bar .on-menu-bar {
  background-color: #a4d4ff;
  font-weight: 700;
}

.block-menu-bar .menu {
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

.block {
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